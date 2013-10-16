package com.bid4win.persistence.entity.image.resource;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Cette classe défini un format d'image<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Format extends Bid4WinObjectType<Format>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 1447365544234114243L;
  /** Maximum acceptable percentage of the proportion tolerance */
  public final static int MAX_PROPORTION_TOLERANCE = 5;

  /** Format d'origine */
  public final static Format SOURCE = new Format("SOURCE", 0);
  /** Format petite taille */
  public final static Format SMALL = new Format("SMALL", 160);
  /** Format moyenne taille */
  public final static Format MEDIUM = new Format("MEDIUM", 320);
  /** Format grande taille */
  public final static Format LARGE = new Format("LARGE", 640);

  /** Définition du format par défaut */
  public final static Format DEFAULT = Bid4WinObjectType.getDefaultType(Format.class);

  /**
   * Cette méthode permet de récupérer le format dont le code est donné en argument
   * @param code Code du format à récupérer
   * @return Le format correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond à aucun format
   * connu
   */
  public static Format getFormat(String code) throws UserException
  {
    return Bid4WinObjectType.getType(Format.class, code.toUpperCase());
  }
  /**
   * Cette méthode permet de récupérer tous les format existants
   * @return Tous les formats définis
   */
  public static Bid4WinCollection<Format> getFormats()
  {
    return Bid4WinObjectType.getTypes(Format.class);
  }

  /** Taille (largeur/hauteur) standard pour le format d'image */
  private int size;

  /**
   * Constructeur
   * @param code Code du format
   * @param size Taille (largeur/hauteur) standard pour le format d'image ou zéro
   * si aucune taille n'est spécifiée
   */
  private Format(String code, int size)
  {
    super(code);
    this.setSize(size);
  }

  /**
   * Redéfini la transformation en chaîne de caractères d'un format lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer("FORMAT=" + this.getCode());
  }

  /**
   * Getter de la taille (largeur/hauteur) standard pour le format d'image
   * @return La taille (largeur/hauteur) standard pour le format d'image ou zéro
   * si aucune taille n'est spécifiée
   */
  public int getSize()
  {
    return this.size;
  }
  /**
   * Setter de la taille (largeur/hauteur) standard pour le format d'image
   * @param size Taille (largeur/hauteur) standard pour le format d'image ou zéro
   * si aucune taille n'est spécifiée
   */
  private void setSize(int size)
  {
    this.size = Math.max(0, size);
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
    return ResourceRef.IMAGE_FORMAT;
  }
}
