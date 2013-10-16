package com.bid4win.persistence.entity.account;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;

/**
 * Cette classe défini le genre d'un individu<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Gender extends Bid4WinObjectType<Gender>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 4857152036825646377L;

  /** Genre d'un individu masculin */
  public final static Gender MR = new Gender("MR");
  /** Genre d'un individu féminin */
  public final static Gender MME = new Gender("MME");
  /** Genre d'un individu féminin célibataire */
  public final static Gender MISS = new Gender("MISS", MME);

  /** Définition du genre d'un individu par défaut */
  public final static Gender DEFAULT = Bid4WinObjectType.getDefaultType(Gender.class);
  /**
   * Cette méthode permet de récupérer tous les genres d'individus existants
   * @return Tous les genres d'individus définis
   */
  public static Bid4WinCollection<Gender> getGenders()
  {
    return Bid4WinObjectType.getTypes(Gender.class);
  }

  /**
   * Constructeur
   * @param code Code du genre d'un individu
   */
  private Gender(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code du genre d'un individu
   * @param parent Parent du genre d'un individu
   */
  private Gender(String code, Gender parent)
  {
    super(code, parent);
  }
}
