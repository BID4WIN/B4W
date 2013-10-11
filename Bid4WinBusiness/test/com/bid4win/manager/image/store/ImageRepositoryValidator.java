package com.bid4win.manager.image.store;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.manager.resource.store.Bid4WinFileRepositoryMultiPartValidator;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.image.resource.Image;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageRepositoryValidator")
@Scope("singleton")
public class ImageRepositoryValidator
       extends Bid4WinFileRepositoryMultiPartValidator<ImageStorage, ImageType, Format, Image>
{
  /** Référence du magasin d'images à valider */
  @Autowired
  @Qualifier("ImageRepository")
  private ImageRepository store;

  /**
   * Cette méthode permet de valider que l'image en argument correspond bien au
   * résultat attendu dans tous les formats
   * @param testPath {@inheritDoc}
   * @param expected {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartValidator#assertEquals(java.lang.String, java.io.File, com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart)
   */
  @Override
  public void assertEquals(String testPath, File expected, ImageStorage resource) throws Exception
  {
    this.assertEquals(testPath, expected, resource, this.getStore().getPartTypeSetFull());
  }
  /**
   *
   * TODO A COMMENTER
   * @param expected {@inheritDoc}
   * @param result {@inheritDoc}
   * @param resource {@inheritDoc}
   * @param format {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartValidator#checkResult(java.io.File, java.io.File, com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart, com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  protected void checkResult(File expected, File result, ImageStorage resource, Format format) throws Exception
  {
    if(format.equals(Format.SOURCE))
    {
      super.checkResult(expected, result, resource, format);
    }
    else
    {
      BufferedImage image = ImageIO.read(result);
      Assert.assertTrue("Wrong size for " + format.getCode() + " format of " + resource.getFullPath(),
                        format.getSize() == image.getWidth() || format.getSize() == image.getHeight());
      // TODO check ratio
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public ImageRepository getStore()
  {
    return this.store;
  }
}
