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
 * Cette classe défini la portion d'image pour un format utilisée sous forme de
 * fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Image extends FileResourcePart<Image, ImageType, Format>
{
  /**
   * Constructeur à partir de la référence du stockage d'une image
   * @param storage Référence du stockage d'image
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
   * Constructeur à partir de la référence de l'utilisation d'une image
   * @param usage Référence de l'utilisation d'image
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
   * Constructeur à partir d'une image
   * @param image Image à partir de laquelle construire la nouvelle
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
   * Getter de la portion d'image correspondant à celle courante pour le format
   * donné
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
      // Cette exception n'est lancée que si la ressource en argument est nulle
      return null;
    }
  }
}
