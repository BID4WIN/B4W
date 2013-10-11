package com.bid4win.manager.image.store;

import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinResource;
import com.bid4win.commons.core.io.resource.Bid4WinResourceStore;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.resource.Format;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageUsageStore")
@Scope("singleton")
public class ImageUsageStore extends ImageFileStore<ImageUsage>
{
  /** Référence du magasin de formats d'images */
  @Autowired
  @Qualifier("ImageUsageStorePart")
  private ImageUsageStorePart partStore = null;

  /**
   *
   * TODO A COMMENTER
   * @param <STORE> {@inheritDoc}
   * @param <SOURCE> {@inheritDoc}
   * @param store {@inheritDoc}
   * @param source {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStore#copy(com.bid4win.commons.core.io.resource.Bid4WinResourceStore, com.bid4win.commons.core.io.resource.Bid4WinResource, com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart)
   */
  @Override
  public <STORE extends Bid4WinResourceStore<SOURCE, ImageType>,
          SOURCE extends Bid4WinResource<ImageType>>
         void copy(STORE store, SOURCE source, ImageUsage resource)
         throws PersistenceException, UserException
  {
    if(store instanceof ImageRepository && source instanceof ImageStorage)
    {
      this.copy((ImageRepository)store, (ImageStorage)source, resource);
    }
    else
    {
      super.copy(store, source, resource);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param inputStream {@inheritDoc}
   * @param imageType {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws CommunicationException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStore#createImages(java.io.InputStream, com.bid4win.persistence.entity.image.ImageType)
   */
  @Override
  protected void createImages(InputStream inputStream, ImageType imageType)
            throws PersistenceException, CommunicationException, ModelArgumentException, UserException
  {
    OutputStream outputStream = null;
    InputStream workingInputStream = null;
    try
    {
      Format defaultFormat = this.getPartTypeDefault();
      // Crée l'image d'origine
      outputStream = this.openOutputStream(this.getWorkingImageFile(defaultFormat));
      UtilFile.copy(inputStream, outputStream);
      this.closeStream(outputStream);
      // Crée les autres formats d'image
      for(Format format : this.getPartTypeSet())
      {
        workingInputStream = this.openInputStream(this.getWorkingImageFile(defaultFormat));
        outputStream = this.openOutputStream(this.getWorkingImageFile(format));
        UtilFile.copy(workingInputStream, outputStream);
        this.closeStream(outputStream);
        this.closeStream(workingInputStream);
      }
    }
    finally
    {
      this.closeStream(outputStream);
      this.closeStream(workingInputStream);
    }
  }

  /**
   * Getter du magasin de gestion de stockage des formats d'image sous forme de
   * fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStore#getPartStore()
   */
  @Override
  public ImageUsageStorePart getPartStore()
  {
    return this.partStore;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getRootPath()
   */
 /* @Override
  protected String getRootPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(new File("").getAbsolutePath(), "WORKING-IMAGE-USAGE");
  }*/
}
