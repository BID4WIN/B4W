package com.bid4win.commons.core.io.resource;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe représente la définition de tout magasin de gestion de stockage
 * sous forme de fichiers de ressources divisées en portion<BR>
 * <BR>
 * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinFileResourceMultiPartStore<RESOURCE extends Bid4WinFileResourceMultiPart<TYPE, PART_TYPE, PART>,
                                                        TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                        PART extends Bid4WinFileResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinFileResourceStore<RESOURCE, TYPE>
       implements Bid4WinResourceMultiPartStore<RESOURCE, TYPE, PART_TYPE, PART>
{
  /**
   * Cette méthode permet de récupérer les sous-répertoires de celui défini par
   * le chemin en paramètre. Une map vide sera retournée si l'emplacement donné
   * ne référence pas un répertoire ou n'existe pas
   * @param parentPath {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getSubdirectories(java.lang.String)
   */
  @Override
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath) throws UserException
  {
    return this.getPartStore().getSubdirectories(parentPath);
  }
  /**
   * Cette méthode permet de savoir si la ressource (ie sa portion par défaut)
   * définie en argument est référencée dans le magasin
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#exists(com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public boolean exists(RESOURCE resource) throws UserException
  {
    return this.exists(resource.getPart(this.getPartTypeDefault()));
  }
  /**
   * Cette méthode permet de savoir si la portion de ressource définie en argument
   * est référencée dans le magasin
   * @param part {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#exists(com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public boolean exists(PART part) throws UserException
  {
    return this.getPartStore().exists(part);
  }
  /**
   * Cette méthode permet de récupérer la ressource (ie sa portion par défaut)
   * définie en argument
   * @param outputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#retreive(java.io.OutputStream, com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public void retreive(OutputStream outputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    this.retreive(outputStream, resource.getPart(this.getPartTypeDefault()));
  }
  /**
   * Cette méthode permet de récupérer la portion de ressource définie en argument.
   * Si celle-ci n'est pas référencée, la portion de ressource par défaut sera récupérée
   * @param outputStream {@inheritDoc}
   * @param part {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#retreive(java.io.OutputStream, com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public void retreive(OutputStream outputStream, PART part)
         throws PersistenceException, CommunicationException, UserException
  {
    if(!this.exists(part))
    {
      this.getPartStore().retreive(outputStream, part.getPart(this.getPartTypeDefault()));
    }
    else
    {
      this.getPartStore().retreive(outputStream, part);
    }
  }
  /**
   * Cette méthode permet de récupérer la ressource (ie sa portion par défaut)
   * définie en argument
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
    return this.retreive(resource.getPart(this.getPartTypeDefault()));
  }
  /**
   * Cette méthode permet de récupérer la portion de ressource définie en argument.
   * Si celle-ci n'est pas référencée, la portion de ressource par défaut sera récupérée
   * @param part {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#retreive(com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public InputStream retreive(PART part)
         throws PersistenceException, CommunicationException, UserException
  {
    if(!this.exists(part))
    {
      return this.getPartStore().retreive(part.getPart(this.getPartTypeDefault()));
    }
    return this.getPartStore().retreive(part);
  }
  /**
   * Cette méthode permet de stocker la ressource (ie sa portion par défaut) définie
   * en argument
   * @param inputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#store(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public void store(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    this.store(inputStream, resource.getPart(this.getPartTypeDefault()));
  }
  /**
   * Cette méthode permet de stocker la portion de ressource définie en argument
   * @param inputStream {@inheritDoc}
   * @param part {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#store(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public void store(InputStream inputStream, PART part)
         throws PersistenceException, CommunicationException, UserException
  {
    this.lock();
    try
    {
      this.getPartStore().store(inputStream, this.checkPartDefaultPresence(part));
    }
    finally
    {
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de remplacer la ressource (ie sa portion par défaut)
   * définie en argument
   * @param inputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#replace(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public void replace(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    this.replace(inputStream, resource.getPart(this.getPartTypeDefault()));
  }
  /**
   * Cette méthode permet de remplacer la portion de ressource définie en argument
   * @param inputStream {@inheritDoc}
   * @param part {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#replace(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public void replace(InputStream inputStream, PART part)
         throws PersistenceException, CommunicationException, UserException
  {
    this.getPartStore().replace(inputStream, part);
  }
  /**
   * Cette méthode permet de copier la ressource (ie toutes ses portions) définie
   * en argument
   * @param from {@inheritDoc}
   * @param to {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#copy(com.bid4win.commons.core.io.resource.Bid4WinFileResource, com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public void copy(RESOURCE from, RESOURCE to) throws PersistenceException, UserException
  {
    this.copy(this, from, to);
  }
  /**
   * Cette méthode permet de copier la portion de ressource définie en argument
   * @param fromPart {@inheritDoc}
   * @param toPart {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourcePart, com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public void copy(PART fromPart, PART toPart) throws PersistenceException, UserException
  {
    this.copy(this, fromPart, toPart);
  }
  /**
   * Cette méthode permet de copier une ressource à partir du magasin défini en
   * argument. La ressource sera copiée dans la portion par défaut de la ressource
   * cible
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>, SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException
  {
    this.copy(store, source, resource.getPart(this.getPartTypeDefault()));
  }
  /**
   * Cette méthode permet de copier une portion de ressource à partir du magasin
   * défini en argument
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param part {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>, SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, PART part)
         throws PersistenceException, UserException
  {
    this.lock();
    try
    {
      this.getPartStore().copy(store, source, this.checkPartDefaultPresence(part));
    }
    finally
    {
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de copier une ressource (ie toutes ses portions) à partir
   * du magasin défini en argument
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore, com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart, com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart)
   */
  @Override
  public <STORE extends Bid4WinResourceMultiPartStore<SOURCE, TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE_PART extends Bid4WinResourcePart<SOURCE_PART, TYPE, PART_TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException
  {
    // Bloque les ressources
    this.lock();
    Bid4WinSet<PART_TYPE> undoSet = new Bid4WinSet<PART_TYPE>();
    try
    {
      // Commence avec la portion de ressource par défaut
      PART_TYPE defaultPartType = this.getPartTypeDefault();
      this.copy(store, source.getPart(defaultPartType), resource.getPart(defaultPartType));
      undoSet.add(defaultPartType);
      // Continue avec toutes les autres portions de ressource
      for(PART_TYPE partType : this.getPartTypes())
      {
        if(store.exists(source.getPart(partType)))
        {
          this.copy(store, source.getPart(partType), resource.getPart(partType));
          undoSet.add(partType);
        }
      }
      undoSet.clear();
    }
    finally
    {
      this.undo(resource, undoSet);
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de copier une portion de ressource à partir du magasin
   * défini en argument
   * @param store {@inheritDoc}
   * @param sourcePart {@inheritDoc}
   * @param resourcePart {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore, com.bid4win.commons.core.io.resource.Bid4WinResourcePart, com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public <STORE extends Bid4WinResourceMultiPartStore<SOURCE, TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE_PART extends Bid4WinResourcePart<SOURCE_PART, TYPE, PART_TYPE>>
         void copy(STORE store, SOURCE_PART sourcePart, PART resourcePart)
         throws PersistenceException, UserException
  {
    this.lock();
    try
    {
      this.checkPartDefaultPresence(resourcePart);
      this.getPartStore().copy(store.getPartStore(), sourcePart, resourcePart);
    }
    finally
    {
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de supprimer la ressource (ie toutes ses portions) définie
   * en argument
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#remove(com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public void remove(RESOURCE resource) throws PersistenceException, UserException
  {
    this.lock();
    try
    {
      // Commence avec toutes les portions de ressource autres que par défaut
      for(PART_TYPE partType : this.getPartTypes())
      {
        PART part = resource.getPart(partType);
        if(this.exists(part))
        {
          this.remove(part);
        }
      }
      // Termine avec la portion de ressource par défaut
      this.remove(resource.getPart(this.getPartTypeDefault()));
    }
    finally
    {
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de supprimer la portion de ressource définie en argument
   * @param part {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#remove(com.bid4win.commons.core.io.resource.Bid4WinResourcePart)
   */
  @Override
  public void remove(PART part) throws PersistenceException, UserException
  {
    this.getPartStore().remove(this.checkPartDefaultSeclusion(part));
  }

  /**
   * Cette méthode permet de vérifier la présence de la portion de ressource par
   * défaut correspondant à celle en argument
   * @param part Portion de ressource pour laquelle on doit vérifier la présence
   * de celle par défaut
   * @return La portion de ressource vérifiée
   * @throws PersistenceException Si la portion de ressource en argument n'est pas
   * celle par défaut et que cette dernière est inexistante
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  protected PART checkPartDefaultPresence(PART part) throws PersistenceException, UserException
  {
    PART_TYPE defaultPartType = this.getPartTypeDefault();
    if(!part.getPartType().equals(defaultPartType) && !this.exists(part.getPart(defaultPartType)))
    {
      File file = this.getPartStore().getAbsoluteFile(part);
      throw new PersistenceException("Cannot find " + file.getAbsolutePath() +
                                     " corresponding default file");
    }
    return part;
  }
  /**
   * Cette méthode permet de vérifier la solitude de la partie de ressource en
   * argument si celle-ci correspond à celle par défaut
   * @param part Partie de ressource à vérifier
   * @return La partie de ressource vérifiée
   * @throws PersistenceException Si la partie de ressource en argument est celle
   * par défaut et d'autres existent pour la ressource correspondante
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  protected PART checkPartDefaultSeclusion(PART part) throws PersistenceException, UserException
  {
    if(part.getPartType().equals(this.getPartTypeDefault()))
    {
      for(PART_TYPE partType : this.getPartTypes())
      {
        if(this.exists(part.getPart(partType)))
        {
          File file = this.getPartStore().getAbsoluteFile(part);
          throw new PersistenceException("Existing " + file.getAbsolutePath() +
                                         " corresponding " + partType.getCode() + " file");
        }
      }
    }
    return part;
  }
  /**
   * Cette méthode permet d'annuler la création sur les portions de ressources en
   * paramètre, c'est à dire de les supprimer
   * @param resource Ressource des portions à supprimer
   * @param undoSet Definition des portions de ressources à supprimer
   */
  protected void undo(RESOURCE resource, Bid4WinSet<PART_TYPE> undoSet)
  {
    for(PART_TYPE partType : undoSet)
    {
      try
      {
        this.remove(resource.getPart(partType));
      }
      catch(Exception ex)
      {
        // On ne propage pas cette exception
        ex.printStackTrace();
      }
    }
  }

  /**
   * Récupère la racine des fichiers stockés du magasin de gestion de stockage
   * des portions de ressource
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getRootPath()
   */
  @Override
  protected final String getRootPath() throws UserException
  {
    return this.getPartStore().getRootPath();
  }
  /**
   * Récupère l'emplacement de travail du magasin de gestion de stockage des portions
   * de ressource
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getWorkingPath()
   */
  @Override
  protected String getWorkingPath() throws UserException
  {
    return this.getPartStore().getWorkingPath();
  }
  /**
   * Délègue la gestion du fichier de lock au magasin de gestion de stockage des
   * portions de ressource
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getLockFile()
   */
  @Override
  protected final File getLockFile() throws UserException
  {
    return this.getPartStore().getLockFile();
  }
  /**
   * Délègue le blocage des resources au magasin de gestion de stockage des portions
   * de ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#lock()
   */
  @Override
  protected final int lock()
  {
    return this.getPartStore().lock();
  }
  /**
   * Délègue le déblocage des resources au magasin de gestion de stockage des portions
   * de ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#unlock()
   */
  @Override
  protected int unlock()
  {
    return this.getPartStore().unlock();
  }

  /**
   * Getter des types de portions de ressource autre que celui par défaut
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#getPartTypes()
   */
  @Override
  public final Bid4WinCollection<PART_TYPE> getPartTypes()
  {
    Bid4WinCollection<PART_TYPE> result = new Bid4WinCollection<PART_TYPE>(this.getPartTypesFull());
    result.remove(this.getPartTypeDefault());
    return result;
  }
  /**
   * Getter de tous les types de portions de ressource. Attention, la collection
   * sera protégée contre toute modification
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#getPartTypesFull()
   */
  @SuppressWarnings("unchecked")
  @Override
  public final Bid4WinCollection<PART_TYPE> getPartTypesFull()
  {
    return Bid4WinObjectType.getTypes((Class<PART_TYPE>)this.getPartTypeDefault().getClass());
  }
  /**
   * Getter du magasin de gestion de stockage des portions de ressource sous forme
   * de fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore#getPartStore()
   */
  @Override
  public abstract Bid4WinFileResourceStore<PART, TYPE> getPartStore();
}
