package com.bid4win.persistence.entity.locale.inner;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 * Cette classe défini un type de contenu internationalisé interne à l'application<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class InnerContentType extends ResourceType<InnerContentType>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -4719608238256218803L;

  /** Type JSP */
  public final static InnerContentType JSP = new InnerContentType("JSP", "jsp", "jsp");
  /** Type JSP */
  public final static InnerContentType XML = new InnerContentType("XML", "xml", "xml");

  /** Définition du type d'utilisation de contenu par défaut */
  public final static UsageType DEFAULT = Bid4WinObjectType.getDefaultType(UsageType.class);

  /**
   * Cette méthode permet de récupérer le type de contenu internationalisé dont
   * le code est donné en argument
   * @param code Code du type de contenu internationalisé à récupérer
   * @return Le type de contenu internationalisé correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond à aucun type de
   * contenu connu
   */
  public static InnerContentType getInnerContentType(String code) throws UserException
  {
    return Bid4WinObjectType.getType(InnerContentType.class, code);
  }
  /**
   * Cette méthode permet de récupérer tous les types de contenus internationalisés
   * existants
   * @return Le set complet de tous les types de contenus internationalisé définis
   */
  public static Bid4WinSet<InnerContentType> getInnerContentTypeSet()
  {
    return Bid4WinObjectType.getTypeSet(InnerContentType.class);
  }

  /**
   * Constructeur
   * @param code Code du type de contenu internationalisé interne à l'application
   * @param extension Extension liée au type de ressource internationalisé interne
   * à l'application
   * @param pathPrefixes Potentiels classement spécifiques du type de ressource
   * internationalisé interne à l'application
   */
  private InnerContentType(String code, String extension, String ... pathPrefixes)
  {
    super(code, extension, pathPrefixes);
  }
}
