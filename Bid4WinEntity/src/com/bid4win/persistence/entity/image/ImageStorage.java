package com.bid4win.persistence.entity.image;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage_Relations;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.image.resource.Image;

/**
 * Cette classe défini la référence de stockage d'une image<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@AttributeOverride(name = "type", column = @Column(name = "IMAGE_TYPE"))
public class ImageStorage
       extends FileResourceStorageMultiPart<ImageStorage, ImageType, ImageUsage, Format, Image>
{
  /** TODO A COMMENTER  */
  public final static Format DEFAULT_FORMAT = Format.DEFAULT;

  /**
   * Constructeur pour création par introspection
   */
  protected ImageStorage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de l'image
   * @param name Nom de l'image
   * @param type Type de l'image
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de l'
   * image est invalide
   */
  public ImageStorage(String path, String name, ImageType type) throws UserException
  {
    super(path, name, type);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return ResourceRef.IMAGE;
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorage#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(ResourceStorage_Relations.RELATION_USAGE_SET))
    {
      return ResourceRef.IMAGE;
    }
    return super.getMessageRefBase(relation);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart#getPartTypeDefault()
   */
  @Override
  public Format getPartTypeDefault()
  {
    return ImageStorage.DEFAULT_FORMAT;
  }
  /**
   * Getter de la portion d'image correspondant au format en paramètre
   * @param format {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public Image getPart(Format format) throws UserException
  {
    try
    {
      return new Image(this, format);
    }
    catch(ModelArgumentException ex)
    {
      // Cette exception n'est lancée que si la ressource en argument est nulle
      return null;
    }
  }
}
