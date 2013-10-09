package com.bid4win.commons.core.io.resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.exception.Bid4WinIOReadException;
import com.bid4win.commons.core.io.exception.Bid4WinIOWriteException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Cette classe repr�sente la d�finition de tout magasin de gestion de stockage
 * de ressources sous forme de fichiers<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir le type de ressources g�r�es<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinFileResourceStore<RESOURCE extends Bid4WinFileResource<TYPE>,
                                               TYPE>
       implements Bid4WinResourceStore<RESOURCE, TYPE>
{
  /** Chemin d'acc�s au fichier de lock */
  protected static final String WORKING_PATH = ".bid4win";
  /** Nom du fichier de lock */
  private static final String LOCK_FILE = ".lock";
  /** Nombre de lock de la session en cours */
  private static final ThreadLocal<Integer> LOCK_NB = new ThreadLocal<Integer>();

  /**
   * Cette m�thode permet de r�cup�rer les sous-r�pertoires de celui d�fini par
   * le chemin en param�tre. Une map vide sera retourn�e si le fichier r�f�renc�
   * par le chemin en param�tre n'est pas un r�pertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif � la racine du magasin des sous
   * r�pertoires � recherche
   * @return Les sous-r�pertoires de celui d�fini par le chemin en param�tre
   * @throws UserException Si l'emplacement du repertoire parent est invalide
   */
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath) throws UserException
  {
    parentPath = UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getRootPath(), parentPath);
    Bid4WinStringRecursiveMap subDirectories = UtilFile.getSubdirectories(parentPath);
    if(parentPath.equals(this.getRootPath()))
    {
      try
      {
        subDirectories.remove(this.getLockFile().getParentFile().getName());
      }
      catch(UserException ex)
      {
        // Pas d'exception � remonter sur la r�cup�ration du fichier de lock
      }
    }
    return subDirectories;
  }

  /**
   * Cette m�thode permet de savoir si la ressource d�finie en argument est r�f�renc�e
   * dans le magasin
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#exists(com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public boolean exists(RESOURCE resource) throws UserException
  {
    File file = this.getAbsoluteFile(resource);
    return file.exists() && file.isFile();
  }
  /**
   * Cette m�thode permet de r�cup�rer la ressource d�finie en argument
   * @param outputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#retreive(java.io.OutputStream, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void retreive(OutputStream outputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    // R�cup�re la ressource
    InputStream inputStream = this.openInputStream(this.getFile(resource));
    try
    {
      // Copie la ressource dans le flux de sortie
      UtilFile.copy(inputStream, outputStream);
    }
    catch(Bid4WinIOReadException ex)
    {
      throw new PersistenceException(ex);
    }
    finally
    {
      this.closeStream(inputStream);
    }
  }
  /**
   * Cette m�thode permet de r�cup�rer la ressource d�finie en argument
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#retreive(com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public InputStream retreive(RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BufferedOutputStream buffer = new BufferedOutputStream(outputStream);
    try
    {
      this.retreive(buffer, resource);
      return new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
    }
    finally
    {
      this.closeStream(buffer);
      this.closeStream(outputStream);
    }
  }
  /**
   * Cette m�thode permet de stocker la ressource d�finie en argument
   * @param inputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#store(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void store(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // Cr�e la ressource
      File file = this.createFile(this.getAbsoluteFile(resource));
      boolean success = false;
      try
      {
        // Copie le flux d'entr�e dans la ressource
        this.replace(inputStream, resource);
        success = true;
      }
      catch(Bid4WinIOWriteException ex)
      {
        throw new PersistenceException(ex);
      }
      finally
      {
        // Annule la cr�ation de la ressource
        if(!success)
        {
          this.deleteFile(file);
        }
      }
    }
    finally
    {
      // Lib�re les ressources
      this.unlock();
    }
  }
  /**
   * Cette m�thode permet de remplacer la ressource d�finie en argument
   * @param inputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#replace(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void replace(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // R�cup�re la ressource
      OutputStream outputStream = this.openOutputStream(this.getFile(resource));
      try
      {
        // Copie le flux d'entr�e dans la ressource
        UtilFile.copy(inputStream, outputStream);
      }
      catch(Bid4WinIOWriteException ex)
      {
        throw new PersistenceException(ex);
      }
      finally
      {
        this.closeStream(outputStream);
      }
    }
    finally
    {
      // Lib�re les ressources
      this.unlock();
    }
  }
  /**
   * Cette m�thode permet de copier la ressource d�finie en argument
   * @param from {@inheritDoc}
   * @param to {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void copy(RESOURCE from, RESOURCE to) throws PersistenceException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // V�rifie le type des ressources
      UtilObject.checkEquals("type", from.getType(), to.getType(),
                             to.getMessageRef(MessageRef.SUFFIX_TYPE,
                                              MessageRef.SUFFIX_INVALID_ERROR));
      // R�cup�re la ressource � copier
      InputStream inputStream = this.openInputStream(this.getFile(from));
      try
      {
        // Copie la ressource � copier dans la ressource cible
        this.store(inputStream, to);
      }
      catch(CommunicationException ex)
      {
        throw new PersistenceException(ex);
      }
      finally
      {
        this.closeStream(inputStream);
      }
    }
    finally
    {
      // Lib�re les ressources
      this.unlock();
    }
  }
  /**
   * Cette m�thode permet de copier une ressource � partir du magasin d�fini en
   * argument
   * @param <STORE> {@inheritDoc}
   * @param <SOURCE> {@inheritDoc}
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>, SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // V�rifie le type des ressources
      UtilObject.checkEquals("type", source.getType(), resource.getType(),
                             resource.getMessageRef(MessageRef.SUFFIX_TYPE,
                                                    MessageRef.SUFFIX_INVALID_ERROR));
      // Cr�e la ressource cible
      File file = this.createFile(this.getAbsoluteFile(resource));
      boolean success = false;
      OutputStream outputStream = null;
      try
      {
        // R�cup�re la ressource
        outputStream =  this.openOutputStream(file);
        // Copie la ressource � partir du magasin donn�
        store.retreive(outputStream, source);
        success = true;
      }
      catch(CommunicationException ex)
      {
        throw new PersistenceException(ex);
      }
      finally
      {
        this.closeStream(outputStream);
        // Annule la cr�ation de la ressource cible
        if(!success)
        {
          this.deleteFile(file);
        }
      }
    }
    finally
    {
      // Lib�re les ressources
      this.unlock();
    }
  }
  /**
   * Cette m�thode permet de d�placer la ressource d�finie en argument
   * @param from {@inheritDoc}
   * @param to {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#move(com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void move(RESOURCE from, RESOURCE to) throws PersistenceException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // Copie la ressource � d�placer dans la ressource cible
      this.copy(from, to);
      boolean success = false;
      try
      {
        // Supprime la ressource � d�placer
        this.remove(from);
        success = true;
      }
      finally
      {
        // Annule la cr�ation de la ressource cible
        if(!success)
        {
          this.remove(to);
        }
      }
    }
    finally
    {
      // Lib�re les ressources
      this.unlock();
    }
  }
  /**
   * Cette m�thode permet de supprimer la ressource d�finie en argument
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStore#remove(com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void remove(RESOURCE resource) throws PersistenceException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // Supprime la ressource
      this.deleteFile(this.getFile(resource));
    }
    finally
    {
      // Lib�re les ressources
      this.unlock();
    }
  }

  /**
   * Cette m�thode permet de r�cup�rer le fichier correspondant � la resource en
   * argument
   * @param resource Ressource dont on recherche le fichier
   * @return Le fichier correspondant � la resource en argument
   * @throws PersistenceException Si le fichier n'existe pas ou correspond � un
   * r�pertoire ou au fichier de lock
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  private File getFile(RESOURCE resource) throws PersistenceException, UserException
  {
    File file = this.getAbsoluteFile(resource);
    if(!file.exists() || file.isDirectory())
    {
      throw new PersistenceException("Cannot find " + resource.getMessageRef().getCode() +
                                     " " + resource.getFullPath());
    }
    try
    {
      if(file.equals(this.getLockFile()))
      {
        throw new PersistenceException("Cannot find " + resource.getMessageRef().getCode() +
                                       " " + resource.getFullPath());
      }
    }
    catch(UserException ex)
    {
      // Pas d'exception � remonter sur la r�cup�ration du fichier de lock
    }
    return file;
  }
  /**
   * Cette m�thode permet de cr�er le fichier d�fini en param�tre ainsi que l'
   * arborescence n�cessaire pour le stocker
   * @param file D�finition du fichier � cr�er
   * @return La r�f�rence du fichier cr��
   * @throws PersistenceException Si la d�finition r�f�rence un fichier existant
   * ou si un probl�me intervient lors de la cr�ation du fichier ou de l'arborescence
   * n�cessaire pour le stocker �choue
   */
  protected File createFile(File file) throws PersistenceException
  {
    try
    {
      // Cr�e le fichier et v�rifie s'il n'existait pas d�j�
      if(!UtilFile.createFile(file))
      {
        throw new PersistenceException("Cannot create already existing " +
                                       file.getAbsolutePath() + " file");
      }
      return file;
    }
    catch(PersistenceException ex)
    {
      // Supprime l'arborescence potentiellement cr��e
      UtilFile.deleteFile(file.getParentFile());
      throw ex;
    }
  }
  /**
   * Cette m�thode permet de supprimer le fichier d�fini en param�tre ainsi que
   * son arborescence tant que celle-ci est vide
   * @param file D�finition du fichier � supprimer
   * @return La r�f�rence du fichier supprim�
   * @throws PersistenceException Si la d�finition r�f�rence un fichier inexistant
   * ou un repertoire ou si un probl�me intervient lors de la suppression du fichier
   */
  private File deleteFile(File file) throws PersistenceException
  {
    // On ne supprime jamais directement un r�pertoire
    if(file.isDirectory())
    {
      throw new PersistenceException("Cannot delete " + file.getAbsolutePath() +
                                     " file because it's a directory");
    }
    // On supprime le fichier et son arborescence tant qu'elle est vide
    if(!UtilFile.deleteFile(file))
    {
      throw new PersistenceException("Cannot find " + file.getAbsolutePath() +
                                     " file to be deleted");
    }
    return file;
  }

  /**
   * Cette m�thode permet de construire le chemin d'acc�s complet � la ressource
   * d�finie en argument
   * @param resource Ressource dont on cherche le chemin d'acc�s complet
   * @return Le chemin d'acc�s complet � la ressource d�finie en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  private String getAbsolutePath(RESOURCE resource) throws UserException
  {
    return UtilFile.concatAbsolutePath(
        resource.getMessageRef(), this.getRootPath(), resource.getFullPath());
  }
  /**
   * Cette m�thode permet de construire le chemin d'acc�s complet � la ressource
   * d�finie en argument
   * @param resource Ressource dont on cherche le chemin d'acc�s complet
   * @return Le chemin d'acc�s complet � la ressource d�finie en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  protected File getAbsoluteFile(RESOURCE resource) throws UserException
  {
    return new File(this.getAbsolutePath(resource));
  }
  /**
   * Getter de la racine des fichiers stock�s
   * @return La racine des fichiers stock�s
   * @throws UserException Si la racine des fichiers stock�s est invalide
   */
  protected abstract String getRootPath() throws UserException;
  /**
   * Getter de l'emplacement de travail du magasin
   * @return L'emplacement de travail du magasin
   * @throws UserException Si l'emplacement de travail est invalide
   */
  protected String getWorkingPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE, this.getRootPath(),
                                       Bid4WinFileResourceStore.WORKING_PATH);
  }
  /**
   * Getter de la r�f�rence du fichier de lock
   * @return La r�f�rence du fichier de lock
   * @throws UserException Si l'emplacement du fichier de lock est invalide
   */
  protected File getLockFile() throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE, this.getWorkingPath(),
                                             Bid4WinFileResourceStore.LOCK_FILE);
  }

  /**
   * Cette m�thode permet de r�cup�rer un flux de lecture sur le fichier d�fini
   * en param�tre
   * @param file D�finition du fichier sur lequel ouvrir un flux de lecture
   * @return Le flux de lecture ouvert sur le fichier d�fini en param�tre
   * @throws PersistenceException Si un probl�me intervient lors de l'acc�s au
   * fichier
   */
  protected InputStream openInputStream(File file) throws PersistenceException
  {
    try
    {
      // Ouvre le flux d'entr�e
      return new FileInputStream(file);
    }
    catch(Throwable th)
    {
      throw new PersistenceException("Cannot open input stream " + file.getAbsolutePath(),
                                     th);
    }
  }
  /**
   * Cette m�thode permet de r�cup�rer un flux d'�criture sur le fichier d�fini
   * en param�tre
   * @param file D�finition du fichier sur lequel ouvrir un flux d'�criture
   * @return Le flux d'�criture ouvert sur le fichier d�fini en param�tre
   * @throws PersistenceException Si un probl�me intervient lors de l'acc�s au
   * fichier
   */
  protected OutputStream openOutputStream(File file) throws PersistenceException
  {
    try
    {
      // Ouvre le flux de sortie
      return new FileOutputStream(file);
    }
    catch(Throwable th)
    {
      throw new PersistenceException("Cannot open output streamn " + file.getAbsolutePath(),
                                     th);
    }
  }
  /**
   * Cette m�thode permet de fermer les flux en param�tre
   * @param streamSet Flux � fermer
   * @throws PersistenceException Si un probl�me intervient lors de la fermeture
   * du flux
   */
  protected void closeStreams(Collection<? extends Closeable> streamSet) throws PersistenceException
  {
    PersistenceException exception = null;
    for(Closeable stream : streamSet)
    {
      try
      {
        this.closeStream(stream);
      }
      catch(PersistenceException ex)
      {
        if(exception == null)
        {
          exception = ex;
        }
      }
    }
    if(exception != null)
    {
      throw exception;
    }
  }
  /**
   * Cette m�thode permet de fermer le flux en param�tre
   * @param stream Flux � fermer
   * @throws PersistenceException Si un probl�me intervient lors de la fermeture
   * du flux
   */
  protected void closeStream(Closeable stream) throws PersistenceException
  {
    if(stream != null)
    {
      try
      {
        stream.close();
      }
      catch(IOException ex)
      {
        throw new PersistenceException(ex);
      }
    }
  }

  /**
   * Cette m�thode permet de bloquer les ressources afin d'�viter des modifications
   * concurrentes. La main ne sera rendue que lorsque le blocage sera effectif
   * La gestion de blocages successifs par le m�me thread se fait en incr�mentant
   * un nombre de blocage ce qui permet de rel�cher le blocage une fois effectu�
   * le nombre �quivalent d'appels � la m�thode de d�blocage
   * @return Le nombre de blocage effectu� par le thread courant
   */
  protected int lock()
  {
    // G�re l'utilisation successive du m�me lock par un seul thread
    int lockNb = this.addLockNb();
    if(lockNb > 1)
    {
      return lockNb;
    }
    while(true)
    {
      try
      {
        // Essaye de cr�er le fichier de lock TODO VOIR SI BESOIN D'UTILISER FileLock !!!!
        this.createFile(this.getLockFile());
        // Tentative r�ussie, le lock est pris
        return lockNb;
      }
      catch(Bid4WinException ex)
      {
        try
        {
          // Attend avant une nouvelle tentative
          Thread.sleep(100);
        }
        catch(InterruptedException interruptedException)
        {
          interruptedException.printStackTrace();
        }
      }
    }
  }
  /**
   * Cette m�thode permet de lib�rer les ressources bloqu�es en fin de modification
   * La gestion de blocages successifs par le m�me thread se fait en incr�mentant
   * un nombre de blocage ce qui permet de rel�cher le blocage une fois effectu�
   * le nombre �quivalent d'appels � la m�thode de d�blocage
   * @return Le nombre de blocage encore en cours pour le thread courant
   */
  protected int unlock()
  {
    // G�re l'utilisation successive du m�me lock par un seul thread
    int lockNb = this.removeLockNb();
    if(lockNb > 0)
    {
      return lockNb;
    }
    try
    {
      // Essaye de supprimer le fichier de lock
      this.getLockFile().delete();
    }
    catch(Throwable th)
    {
      // TODO G�rer l'exception
      th.printStackTrace();
    }
    return lockNb;
  }

  /**
   * Getter du nombre de blocage en cours pour le thread courant
   * @return Le nombre de blocage en cours pour le thread courant
   */
  protected int getLockNb()
  {
    Integer lockNb = Bid4WinFileResourceStore.LOCK_NB.get();
    if(lockNb == null)
    {
      return 0;
    }
    return lockNb;
  }
  /**
   * Cette m�thode permet d'incr�menter le nombre de blocage en cours pour le
   * thread courant
   * @return Le nombre de blocage en cours pour le thread courant
   */
  private int addLockNb()
  {
    int lockNb = this.getLockNb() + 1;
    Bid4WinFileResourceStore.LOCK_NB.set(lockNb);
    return lockNb;
  }
  /**
   * Cette m�thode permet de d�cr�menter le nombre de blocage en cours pour le
   * thread courant
   * @return Le nombre de blocage en cours pour le thread courant
   */
  private int removeLockNb()
  {
    int lockNb = this.getLockNb();
    if(lockNb != 0)
    {
      lockNb--;
      if(lockNb == 0)
      {
        Bid4WinFileResourceStore.LOCK_NB.remove();
      }
      else
      {
        Bid4WinFileResourceStore.LOCK_NB.set(lockNb);
      }
    }
    return lockNb;
  }
}
