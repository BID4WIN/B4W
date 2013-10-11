package com.bid4win.persistence.entity.image.resource;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.resource.store.FileResourcePart;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.image.ImageUsage;

/**
 * Cette classe d�fini la portion d'image pour un format utilis�e sous forme de
 * fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Image extends FileResourcePart<Image, ImageType, Format>
{
  /**
   * Constructeur � partir de la r�f�rence du stockage d'une image
   * @param storage R�f�rence du stockage d'image
   * @param format Format de l'image
   * @throws ModelArgumentException Si le stockage d'image en argument est nul
   * @throws UserException Si le nom, l'emplacement de stockage ou le format de
   * la portion d'image est invalide
   */
  public Image(ImageStorage storage, Format format)
         throws ModelArgumentException, UserException
  {
    super(storage, format);
  }
  /**
   * Constructeur � partir de la r�f�rence de l'utilisation d'une image
   * @param usage R�f�rence de l'utilisation d'image
   * @param format Format de l'image
   * @throws ModelArgumentException Si l'utilisation d'image en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le format de
   * la portion d'image est invalide
   */
  public Image(ImageUsage usage, Format format)
         throws ModelArgumentException, UserException
  {
    super(usage, format);
  }
  /**
   * Constructeur � partir d'une image
   * @param image Image � partir de laquelle construire la nouvelle
   * @param format Format de l'image
   * @throws ModelArgumentException Si la portion d'image en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de l'image est
   * invalide ou le format nul
   */
  public Image(Image image, Format format) throws ModelArgumentException, UserException
  {
    super(image, format);
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
   * Getter du format de la portion d'image
   * @return Le format de la portion d'image
   */
  public Format getFormat()
  {
    return this.getPartType();
  }
  /**
   * Getter de la portion d'image correspondant � celle courante pour le format
   * donn�
   * @param format {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
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
      // Cette exception n'est lanc�e que si la ressource en argument est nulle
      return null;
    }
  }
}
