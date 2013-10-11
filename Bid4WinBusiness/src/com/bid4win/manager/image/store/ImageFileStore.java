package com.bid4win.manager.image.store;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore;
import com.bid4win.commons.core.io.resource.Bid4WinResource;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStore;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.image.resource.Image;

/**
 * Cette classe défini le magasin de base des images stockées sous la forme de
 * fichiers. Il permet de gérer les différentes tailles d'images utilisables et
 * leur ressources associées<BR>
 * <BR>
 * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ImageFileStore<RESOURCE extends Bid4WinFileResourceMultiPart<ImageType, Format, Image>>
       extends Bid4WinFileResourceMultiPartStore<RESOURCE, ImageType, Format, Image>
{
  /** Nom de base des images de travail */
  private static final String WORKING_IMAGE = "working";

  /**
   * Cette méthode permet de stocker l'image définie en argument. Cette image sera
   * re-dimensionnée pour tous ses formats
   * @param inputStream {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#store(java.io.InputStream, com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart)
   */
  @Override
  public void store(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    // Bloque les ressources
    this.lock();
    Bid4WinMap<Format, InputStream> inputStreamMap = new Bid4WinMap<Format, InputStream>();
    try
    {
      // Crée les images à stocker dans tous les formats
      this.createImages(inputStream, resource.getType());
      // Ouvre les flux sur ces images
      this.openImageStreams(inputStreamMap);
      // Copie tous les formats d'images dans le magasin
      this.store(inputStreamMap, resource);
    }
    catch (ModelArgumentException ex)
    {
      throw new CommunicationException(ex);
    }
    finally
    {
      this.closeStreams(inputStreamMap.values());
      // Supprime les images de travail
      this.deleteImages();
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de stocker l'image dans tous les formats définis en argument
   * @param inputStreamMap Flux définissant chaque format d'image
   * @param resource Ressource correspondant à l'image à stocker
   * @throws PersistenceException Si la ressource (ie sa portion par défaut) est
   * déjà référencée ou si un problème intervient lors sa création
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public void store(Bid4WinMap<Format, InputStream> inputStreamMap, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    // Bloque les ressources
    this.lock();
    Bid4WinSet<Format> formatSet = new Bid4WinSet<Format>();
    try
    {
      // Commence avec la portion de ressource par défaut
      Format defaultFormat = this.getPartTypeDefault();
      this.getPartStore().store(inputStreamMap.get(defaultFormat),
                                resource.getPart(defaultFormat));
      formatSet.add(defaultFormat);
      // Continue avec toutes les autres portions de ressource
      for(Format format : inputStreamMap.keySet())
      {
        if(!format.equals(defaultFormat))
        {
          this.getPartStore().store(inputStreamMap.get(format),
                                    resource.getPart(format));
          formatSet.add(format);
        }
      }
      formatSet.clear();
    }
    finally
    {
      this.undo(resource, formatSet);
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de remplacer l'image définie en argument. Cette image sera
   * re-dimensionnée pour tous ses formats
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
    // Bloque les ressources
    this.lock();
    Bid4WinMap<Format, InputStream> inputStreamMap = new Bid4WinMap<Format, InputStream>();
    try
    {
      // Crée les images à stocker dans tous les formats
      this.createImages(inputStream, resource.getType());
      // Ouvre les flux sur ces images
      this.openImageStreams(inputStreamMap);
      // Remplace tous les formats d'images dans le magasin
      this.replace(inputStreamMap, resource);
    }
    catch (ModelArgumentException ex)
    {
      throw new CommunicationException(ex);
    }
    finally
    {
      this.closeStreams(inputStreamMap.values());
      // Supprime les images de travail
      this.deleteImages();
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de remplacer l'image dans tous les formats définis en
   * argument
   * @param inputStreamMap Flux définissant chaque format d'image
   * @param resource Ressource correspondant à l'image à remplacer
   * @throws PersistenceException Si la ressource (ie sa portion par défaut) n'
   * existe pas ou correspond à un répertoire ou si un problème intervient lors
   * de son remplacement
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public void replace(Bid4WinMap<Format, InputStream> inputStreamMap, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException
  {
    // Bloque les ressources
    this.lock();
    try
    {
      // Commence avec la portion de ressource par défaut
      Format defaultFormat = this.getPartTypeDefault();
      this.getPartStore().replace(inputStreamMap.get(defaultFormat),
                                  resource.getPart(defaultFormat));
      // Continue avec toutes les autres portions de ressource
      // TODO VOIR SI ECHEC AUX SUIVANTS
      for(Format format : inputStreamMap.keySet())
      {
        if(!format.equals(defaultFormat))
        {
          this.getPartStore().replace(inputStreamMap.get(format),
                                      resource.getPart(format));
        }
      }
    }
    finally
    {
      this.unlock();
    }
  }
  /**
   * Cette méthode permet de copier une image à partir du magasin défini en argument.
   * Cette image sera re-dimensionnée pour tous ses formats
   * @param <STORE> {@inheritDoc}
   * @param <SOURCE> {@inheritDoc}
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinFileResource)
   */
  @Override
  public <STORE extends Bid4WinResourceStore<SOURCE, ImageType>,
          SOURCE extends Bid4WinResource<ImageType>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException
  {
    // Bloque les ressources
    this.lock();
    InputStream inputStream = null;
    try
    {
      // Vérifie le type des ressources
      UtilObject.checkEquals("type", source.getType(), resource.getType(),
                             resource.getMessageRef(MessageRef.SUFFIX_TYPE,
                                                    MessageRef.SUFFIX_INVALID_ERROR));
      // Récupère l'image source
      File workingFile = this.createFile(this.getWorkingImageFile());
      OutputStream outputStream = this.openOutputStream(workingFile);
      try
      {
        store.retreive(outputStream, source);
      }
      finally
      {
        this.closeStream(outputStream);
      }
      // Crée les images à stocker
      inputStream = this.openInputStream(workingFile);
      this.store(inputStream, resource);
    }
    catch(CommunicationException ex)
    {
      throw new PersistenceException(ex);
    }
    finally
    {
      this.closeStream(inputStream);
      this.deleteImages();
      this.unlock();
    }
  }

  /**
   * Getter du format d'image par défaut
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartTypeDefault()
   */
  @Override
  public Format getPartTypeDefault()
  {
    return ImageStorage.DEFAULT_FORMAT;
  }

  /**
   * Cette méthode doit être définie afin de créer les différents formats de l'
   * image en paramètre
   * @param inputStream Flux de l'image à créer dans les différents formats
   * @param imageType Type de l'image à créer dans les différents formats
   * @throws PersistenceException TODO A COMMENTER
   * @throws CommunicationException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected abstract void createImages(InputStream inputStream, ImageType imageType)
            throws PersistenceException, CommunicationException, ModelArgumentException, UserException;
  /**
   * Cette méthode permet de supprimer les portions d'images potentiellement créées
   */
  protected void deleteImages()
  {
    try
    {
      this.getWorkingImageFile().delete();
    }
    catch(UserException ex)
    {
      // TODO
    }
    catch(SecurityException ex)
    {
      // TODO
    }
    for(Format format : this.getPartTypeSetFull())
    {
      try
      {
        this.getWorkingImageFile(format).delete();
      }
      catch(UserException ex)
      {
        // TODO
      }
      catch(SecurityException ex)
      {
        // TODO
      }
    }
  }
  /**
   * Cette méthode permet d'ouvrir les flux sur les différents formats de l'image
   * en paramètre
   * @param inputStreamMap Map des flux ouverts
   * @throws PersistenceException Si un problème intervient lors de l'accès à un
   * des fichiers
   * @throws UserException TODO A COMMENTER
   */
  protected void openImageStreams(Bid4WinMap<Format, InputStream> inputStreamMap)
            throws PersistenceException, UserException
  {
    for(Format format : this.getPartTypeSetFull())
    {
      if(inputStreamMap.get(format) == null)
      {
        inputStreamMap.put(format, this.openInputStream(this.getWorkingImageFile(format)));
      }
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String getWorkingImagePath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.IMAGE, this.getWorkingPath(), ImageFileStore.WORKING_IMAGE);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected File getWorkingImageFile() throws UserException
  {
    return new File(this.getWorkingImagePath());
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String getWorkingImagePath(Format format) throws UserException
  {
    String workingImagePath = this.getWorkingImagePath();
    String workingImageDirectory = new File(workingImagePath).getParent();
    String workingImageFilename = UtilFile.addExtension(workingImagePath,
                                                        format.getCode().toLowerCase(),
                                                        ResourceRef.IMAGE);
    return UtilFile.concatAbsolutePath(
        ResourceRef.IMAGE, workingImageDirectory, workingImageFilename);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected File getWorkingImageFile(Format format) throws UserException
  {
    return new File(this.getWorkingImagePath(format));
  }
  /**
   * Getter du magasin de gestion de stockage de tous les formats d'image stockés
   * sous forme de fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartStore()
   */
  @Override
  public abstract OutwardlyManagedFileResourceStore<Image, ImageType> getPartStore();
}
