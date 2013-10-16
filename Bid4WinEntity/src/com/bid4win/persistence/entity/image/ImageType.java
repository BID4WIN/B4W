package com.bid4win.persistence.entity.image;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 * Cette classe défini un type d'image<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ImageType extends ResourceType<ImageType>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -1322813350484570797L;

  /** Type JPG */
  public final static ImageType JPG = new ImageType("JPG");
  /** Type JPEG */
  public final static ImageType JPEG = new ImageType("JPEG", ImageType.JPG);
  /** Type PNG */
  public final static ImageType PNG = new ImageType("PNG");
  /** Type BMP */
  public final static ImageType BMP = new ImageType("BMP");
  /** Type GIF */
  public final static ImageType GIF = new ImageType("GIF");

  /** Définition du type d'image par défaut */
  public final static ImageType DEFAULT = Bid4WinObjectType.getDefaultType(ImageType.class);

  /**
   * Cette méthode permet de récupérer le type d'image dont le code est donné en
   * argument
   * @param code Code du type d'image à récupérer
   * @return Le type d'image correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond à aucun type d'
   * image connu
   */
  public static ImageType getImageType(String code) throws UserException
  {
    return Bid4WinObjectType.getType(ImageType.class, code.toUpperCase());
  }
  /**
   * Cette méthode permet de récupérer le type d'image en fonction du nom du fichier
   * en argument
   * @param filename Nom du fichier duquel extraire le type d'image
   * @return Le type d'image défini par le nom de fichier en paramètre
   * @throws UserException Si le nom de fichier ne respecte pas le format attendu
   * ou si son extension ne correspond à aucun type d'image connu
   */
  public static ImageType getImageTypeFromFilename(String filename) throws UserException
  {
    return ImageType.getImageType(UtilFile.getExtension(UtilString.trimNotNull(filename),
                                                        ResourceRef.IMAGE));
  }
  /**
   * Cette méthode permet de récupérer tous les types d'image existants
   * @return Tous les types d'image définis
   */
  public static Bid4WinCollection<ImageType> getImageTypes()
  {
    return Bid4WinObjectType.getTypes(ImageType.class);
  }

  /**
   * Constructeur
   * @param code Code du type d'image
   */
  private ImageType(String code)
  {
    super(code, code, "img");
  }
  /**
   * Constructeur
   * @param code Code du type d'image
   * @param parent Parent du type d'image
   */
  private ImageType(String code, ImageType parent)
  {
    super(code, code, parent, "img");
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceType#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return ResourceRef.IMAGE_TYPE;
  }
}
