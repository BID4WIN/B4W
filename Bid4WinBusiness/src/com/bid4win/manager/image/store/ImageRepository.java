package com.bid4win.manager.image.store;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.exception.Bid4WinIOReadException;
import com.bid4win.commons.core.io.exception.Bid4WinIOWriteException;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.resource.Format;
import com.mortennobel.imagescaling.ResampleOp;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("ImageRepository")
@Scope("singleton")
public class ImageRepository extends ImageFileStore<ImageStorage>
{
  /** R�f�rence du magasin de formats d'images */
  @Autowired
  @Qualifier("ImageRepositoryPart")
  private ImageRepositoryPart partStore = null;

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
    try
    {
      // Cr�e l'image source
      File defaultFile = this.getWorkingImageFile(this.getPartTypeDefault());
      OutputStream outputStream = this.openOutputStream(defaultFile);
      try
      {
        // Copie le flux d'entr�e dans l'image source
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
      inputStream = this.openInputStream(defaultFile);
      try
      {
        // Lit l'image source
        BufferedImage image = ImageIO.read(inputStream);
        // Cr�e les images re-dimensionn�es
        for(Format format : this.getPartTypeSet())
        {
          BufferedImage resizedImage = this.resizeImage(image, format);
          ImageIO.write(resizedImage, imageType.getPrimitive().getCode(),
                        this.getWorkingImageFile(format));
        }
      }
      finally
      {
        inputStream.close();
      }
    }
    catch (IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Cette fonction re-dimensionne l'image en argument au format donn�
   * @param image Image � re-dimensionner
   * @param format Format souhait�
   * @return L'image re-dimensionn�e au format souhait�
   */
  protected BufferedImage resizeImage(BufferedImage image, Format format)
  {
    // Aucun traitement n�cessaire
    if(format.getSize() < 1)
    {
      return image;
    }
    double width = image.getWidth();
    double height = image.getHeight();
    double proportion = (Math.abs(width - height) / Math.max(width, height)) * 100;
    // Les proportions permettent la cr�ation d'une image carr�e
    if(proportion < Format.MAX_PROPORTION_TOLERANCE)
    {
      width = format.getSize();
      height = format.getSize();
    }
    // On re-dimensionne suivant le plus grand c�t�
    else
    {
      if(width > height)
      {
        height = height * format.getSize() / width;
        width = format.getSize();
      }
      else
      {
        width = width * format.getSize() / height;
        height = format.getSize();
      }
    }
    ResampleOp resampleOp = new ResampleOp((int)width, (int)height);
    return resampleOp.filter(image, null);
  }
  /**
   * Getter du magasin de gestion de stockage des formats d'image sous forme de
   * fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.manager.image.store.ImageFileStore#getPartStore()
   */
  @Override
  public ImageRepositoryPart getPartStore()
  {
    return this.partStore;
  }
}
