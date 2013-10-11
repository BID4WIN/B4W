package com.bid4win.manager.image.store;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.manager.resource.store.FileResourceUsageMultiPartLinkedValidator;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.image.resource.Image;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("ImageUsageLinkedValidator")
@Scope("singleton")
public class ImageUsageLinkedValidator
       extends FileResourceUsageMultiPartLinkedValidator<ImageUsage, ImageType, ImageStorage, Format, Image>
{
  /** R�f�rence du magasin de stockage d'images */
  @Autowired
  @Qualifier("ImageRepository")
  private ImageRepository repository = null;
  /** R�f�rence du magasin d'images � valider */
  @Autowired
  @Qualifier("ImageUsageStore")
  private ImageUsageStore store;

  /**
   * Cette m�thode permet de valider que l'image en argument correspond bien au
   * r�sultat attendu dans tous les formats
   * @param testPath {@inheritDoc}
   * @param expected {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartValidator#assertEquals(java.lang.String, java.io.File, com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart)
   */
  @Override
  public void assertEquals(String testPath, File expected, ImageUsage resource) throws Exception
  {
    this.assertEquals(testPath, expected, resource, this.getStore().getPartTypeSetFull());
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator#getStore()
   */
  @Override
  public ImageUsageStore getStore()
  {
    return this.store;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.store.FileResourceUsageMultiPartLinkedValidator#getRepository()
   */
  @Override
  public ImageRepository getRepository()
  {
    return this.repository;
  }
}
