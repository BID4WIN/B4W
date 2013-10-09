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
 * Cette classe représente la définition de tout magasin de gestion de stockage
 * de ressources sous forme de fichiers<BR>
 * <BR>
 * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinFileResourceStore<RESOURCE extends Bid4WinFileResource<TYPE>,
                                               TYPE>
       implements Bid4WinResourceStore<RESOURCE, TYPE>
{
  /** Chemin d'accès au fichier de lock */
  protected static final String WORKING_PATH = ".bid4win";
  /** Nom du fichier de lock */
  private static final String LOCK_FILE = ".lock";
  /** Nombre de lock de la session en cours */
  private static final ThreadLocal<Integer> LOCK_NB = new ThreadLocal<Integer>();

  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une map vide sera retournée si le fichier référencé
   * par le chemin en paramètre n'est pas un répertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif à la racine du magasin des sous
   * répertoires à recherche
   * @return Les sous-répertoires de celui défini par le chemin en paramètre
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
        // Pas d'exception à remonter sur la récupération du fichier de lock
      }
    }
    return subDirectories;
  }

  /**
   * Cette méthode permet de savoir si la ressource définie en argument est référencée
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
   * Cette méthode permet de récupérer la ressource définie en argument
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
    // Récupère la ressource
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
   * Cette méthode permet de récupérer la ressource définie en argument
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
   * Cette méthode permet de stocker la ressource définie en argument
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
      // Crée la ressource
      File file = this.createFile(this.getAbsoluteFile(resource));
      boolean success = false;
      try
      {
        // Copie le flux d'entrée dans la ressource
        this.replace(inputStream, resource);
        success = true;
      }
      catch(Bid4WinIOWriteException ex)
      {
        throw new PersistenceException(ex);
      }
      finally
      {
        // Annule la création de la ressource
        if(!success)
        {
          this.deleteFile(file);
        }
      }
    }
    finally
    {
      // Libère les ressources
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de remplacer la ressource définie en argument
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
      // Récupère la ressource
      OutputStream outputStream = this.openOutputStream(this.getFile(resource));
      try
      {
        // Copie le flux d'entrée dans la ressource
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
      // Libère les ressources
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de copier la ressource définie en argument
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
      // Vérifie le type des ressources
      UtilObject.checkEquals("type", from.getType(), to.getType(),
                             to.getMessageRef(MessageRef.SUFFIX_TYPE,
                                              MessageRef.SUFFIX_INVALID_ERROR));
      // Récupère la ressource à copier
      InputStream inputStream = this.openInputStream(this.getFile(from));
      try
      {
        // Copie la ressource à copier dans la ressource cible
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
      // Libère les ressources
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de copier une ressource à partir du magasin défini en
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
      // Vérifie le type des ressources
      UtilObject.checkEquals("type", source.getType(), resource.getType(),
                             resource.getMessageRef(MessageRef.SUFFIX_TYPE,
                                                    MessageRef.SUFFIX_INVALID_ERROR));
      // Crée la ressource cible
      File file = this.createFile(this.getAbsoluteFile(resource));
      boolean success = false;
      OutputStream outputStream = null;
      try
      {
        // Récupère la ressource
        outputStream =  this.openOutputStream(file);
        // Copie la ressource à partir du magasin donné
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
        // Annule la création de la ressource cible
        if(!success)
        {
          this.deleteFile(file);
        }
      }
    }
    finally
    {
      // Libère les ressources
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de déplacer la ressource définie en argument
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
      // Copie la ressource à déplacer dans la ressource cible
      this.copy(from, to);
      boolean success = false;
      try
      {
        // Supprime la ressource à déplacer
        this.remove(from);
        success = true;
      }
      finally
      {
        // Annule la création de la ressource cible
        if(!success)
        {
          this.remove(to);
        }
      }
    }
    finally
    {
      // Libère les ressources
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de supprimer la ressource définie en argument
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
      // Libère les ressources
      this.unlock();
    }
  }

  /**
   * Cette méthode permet de récupérer le fichier correspondant à la resource en
   * argument
   * @param resource Ressource dont on recherche le fichier
   * @return Le fichier correspondant à la resource en argument
   * @throws PersistenceException Si le fichier n'existe pas ou correspond à un
   * répertoire ou au fichier de lock
   * @throws UserException Si l'emplacement défini par la ressource est invalide
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
      // Pas d'exception à remonter sur la récupération du fichier de lock
    }
    return file;
  }
  /**
   * Cette méthode permet de créer le fichier défini en paramètre ainsi que l'
   * arborescence nécessaire pour le stocker
   * @param file Définition du fichier à créer
   * @return La référence du fichier créé
   * @throws PersistenceException Si la définition référence un fichier existant
   * ou si un problème intervient lors de la création du fichier ou de l'arborescence
   * nécessaire pour le stocker échoue
   */
  protected File createFile(File file) throws PersistenceException
  {
    try
    {
      // Crée le fichier et vérifie s'il n'existait pas déjà
      if(!UtilFile.createFile(file))
      {
        throw new PersistenceException("Cannot create already existing " +
                                       file.getAbsolutePath() + " file");
      }
      return file;
    }
    catch(PersistenceException ex)
    {
      // Supprime l'arborescence potentiellement créée
      UtilFile.deleteFile(file.getParentFile());
      throw ex;
    }
  }
  /**
   * Cette méthode permet de supprimer le fichier défini en paramètre ainsi que
   * son arborescence tant que celle-ci est vide
   * @param file Définition du fichier à supprimer
   * @return La référence du fichier supprimé
   * @throws PersistenceException Si la définition référence un fichier inexistant
   * ou un repertoire ou si un problème intervient lors de la suppression du fichier
   */
  private File deleteFile(File file) throws PersistenceException
  {
    // On ne supprime jamais directement un répertoire
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
   * Cette méthode permet de construire le chemin d'accès complet à la ressource
   * définie en argument
   * @param resource Ressource dont on cherche le chemin d'accès complet
   * @return Le chemin d'accès complet à la ressource définie en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  private String getAbsolutePath(RESOURCE resource) throws UserException
  {
    return UtilFile.concatAbsolutePath(
        resource.getMessageRef(), this.getRootPath(), resource.getFullPath());
  }
  /**
   * Cette méthode permet de construire le chemin d'accès complet à la ressource
   * définie en argument
   * @param resource Ressource dont on cherche le chemin d'accès complet
   * @return Le chemin d'accès complet à la ressource définie en argument
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  protected File getAbsoluteFile(RESOURCE resource) throws UserException
  {
    return new File(this.getAbsolutePath(resource));
  }
  /**
   * Getter de la racine des fichiers stockés
   * @return La racine des fichiers stockés
   * @throws UserException Si la racine des fichiers stockés est invalide
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
   * Getter de la référence du fichier de lock
   * @return La référence du fichier de lock
   * @throws UserException Si l'emplacement du fichier de lock est invalide
   */
  protected File getLockFile() throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE, this.getWorkingPath(),
                                             Bid4WinFileResourceStore.LOCK_FILE);
  }

  /**
   * Cette méthode permet de récupérer un flux de lecture sur le fichier défini
   * en paramètre
   * @param file Définition du fichier sur lequel ouvrir un flux de lecture
   * @return Le flux de lecture ouvert sur le fichier défini en paramètre
   * @throws PersistenceException Si un problème intervient lors de l'accès au
   * fichier
   */
  protected InputStream openInputStream(File file) throws PersistenceException
  {
    try
    {
      // Ouvre le flux d'entrée
      return new FileInputStream(file);
    }
    catch(Throwable th)
    {
      throw new PersistenceException("Cannot open input stream " + file.getAbsolutePath(),
                                     th);
    }
  }
  /**
   * Cette méthode permet de récupérer un flux d'écriture sur le fichier défini
   * en paramètre
   * @param file Définition du fichier sur lequel ouvrir un flux d'écriture
   * @return Le flux d'écriture ouvert sur le fichier défini en paramètre
   * @throws PersistenceException Si un problème intervient lors de l'accès au
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
   * Cette méthode permet de fermer les flux en paramètre
   * @param streamSet Flux à fermer
   * @throws PersistenceException Si un problème intervient lors de la fermeture
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
   * Cette méthode permet de fermer le flux en paramètre
   * @param stream Flux à fermer
   * @throws PersistenceException Si un problème intervient lors de la fermeture
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
   * Cette méthode permet de bloquer les ressources afin d'éviter des modifications
   * concurrentes. La main ne sera rendue que lorsque le blocage sera effectif
   * La gestion de blocages successifs par le même thread se fait en incrémentant
   * un nombre de blocage ce qui permet de relâcher le blocage une fois effectué
   * le nombre équivalent d'appels à la méthode de déblocage
   * @return Le nombre de blocage effectué par le thread courant
   */
  protected int lock()
  {
    // Gère l'utilisation successive du même lock par un seul thread
    int lockNb = this.addLockNb();
    if(lockNb > 1)
    {
      return lockNb;
    }
    while(true)
    {
      try
      {
        // Essaye de créer le fichier de lock TODO VOIR SI BESOIN D'UTILISER FileLock !!!!
        this.createFile(this.getLockFile());
        // Tentative réussie, le lock est pris
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
   * Cette méthode permet de libérer les ressources bloquées en fin de modification
   * La gestion de blocages successifs par le même thread se fait en incrémentant
   * un nombre de blocage ce qui permet de relâcher le blocage une fois effectué
   * le nombre équivalent d'appels à la méthode de déblocage
   * @return Le nombre de blocage encore en cours pour le thread courant
   */
  protected int unlock()
  {
    // Gère l'utilisation successive du même lock par un seul thread
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
      // TODO Gérer l'exception
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
   * Cette méthode permet d'incrémenter le nombre de blocage en cours pour le
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
   * Cette méthode permet de décrémenter le nombre de blocage en cours pour le
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
