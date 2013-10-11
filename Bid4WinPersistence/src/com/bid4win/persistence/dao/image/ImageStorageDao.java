package com.bid4win.persistence.dao.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;

/**
 * DAO pour les entités de la classe ImageStorage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ImageStorageDao")
@Scope("singleton")
public class ImageStorageDao extends ResourceStorageDao_<ImageStorage, ImageType, ImageUsage>
{
  /** Référence du DAO des utilisations d'image */
  @Autowired
  @Qualifier("ImageUsageDao")
  private ImageUsageDao usageDao;

  /**
   * Constructeur
   */
  protected ImageStorageDao()
  {
    super(ImageStorage.class);
  }

  /**
   * Getter du DAO des utilisations d'image
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceStorageDao_#getUsageDao()
   */
  @Override
  public ImageUsageDao getUsageDao()
  {
    return this.usageDao;
  }
}
