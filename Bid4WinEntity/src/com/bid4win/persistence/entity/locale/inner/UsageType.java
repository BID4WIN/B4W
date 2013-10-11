package com.bid4win.persistence.entity.locale.inner;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe défini les différents type d'utilisation de contenu internationalisé
 * interne à l'application<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UsageType extends Bid4WinObjectType<UsageType>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 2692740061357689430L;

  /** Utilisation de contenu simple */
  public final static UsageType SIMPLE = new UsageType("SIMPLE");
  /** Utilisation de contenu pour un produit */
  public final static UsageType PRODUCT = new UsageType("PRODUCT");

  /** Définition du type d'utilisation de contenu par défaut */
  public final static UsageType DEFAULT = Bid4WinObjectType.getDefaultType(UsageType.class);

  /**
   * Cette méthode permet de récupérer le type d'utilisation de contenu dont le
   * code est donné en argument
   * @param code Code du type d'utilisation de contenu à récupérer
   * @return Le type d'utilisation de contenu correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond à aucun type d'
   * utilisation de contenu connu
   */
  public static UsageType getUsageType(String code) throws UserException
  {
    return Bid4WinObjectType.getType(UsageType.class, code);
  }
  /**
   * Cette méthode permet de récupérer tous les types d'utilisation de contenu
   * existants
   * @return Le set complet de tous les types d'utilisation de contenu définis
   */
  public static Bid4WinSet<UsageType> getUsageTypeSet()
  {
    return Bid4WinObjectType.getTypeSet(UsageType.class);
  }

  /**
   * Constructeur
   * @param code Code du type d'utilisation de contenu
   */
  private UsageType(String code)
  {
    super(code);
  }
}
