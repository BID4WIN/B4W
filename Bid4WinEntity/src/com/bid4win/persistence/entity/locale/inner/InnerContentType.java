package com.bid4win.persistence.entity.locale.inner;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 * Cette classe d�fini un type de contenu internationalis� interne � l'application<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class InnerContentType extends ResourceType<InnerContentType>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -4719608238256218803L;

  /** Type JSP */
  public final static InnerContentType JSP = new InnerContentType("JSP", "jsp", "jsp");
  /** Type JSP */
  public final static InnerContentType XML = new InnerContentType("XML", "xml", "xml");

  /** D�finition du type d'utilisation de contenu par d�faut */
  public final static UsageType DEFAULT = Bid4WinObjectType.getDefaultType(UsageType.class);

  /**
   * Cette m�thode permet de r�cup�rer le type de contenu internationalis� dont
   * le code est donn� en argument
   * @param code Code du type de contenu internationalis� � r�cup�rer
   * @return Le type de contenu internationalis� correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond � aucun type de
   * contenu connu
   */
  public static InnerContentType getInnerContentType(String code) throws UserException
  {
    return Bid4WinObjectType.getType(InnerContentType.class, code);
  }
  /**
   * Cette m�thode permet de r�cup�rer tous les types de contenus internationalis�s
   * existants
   * @return Le set complet de tous les types de contenus internationalis� d�finis
   */
  public static Bid4WinSet<InnerContentType> getInnerContentTypeSet()
  {
    return Bid4WinObjectType.getTypeSet(InnerContentType.class);
  }

  /**
   * Constructeur
   * @param code Code du type de contenu internationalis� interne � l'application
   * @param extension Extension li�e au type de ressource internationalis� interne
   * � l'application
   * @param pathPrefixes Potentiels classement sp�cifiques du type de ressource
   * internationalis� interne � l'application
   */
  private InnerContentType(String code, String extension, String ... pathPrefixes)
  {
    super(code, extension, pathPrefixes);
  }
}
