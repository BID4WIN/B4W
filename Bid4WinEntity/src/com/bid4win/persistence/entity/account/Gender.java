package com.bid4win.persistence.entity.account;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinCollection;

/**
 * Cette classe d�fini le genre d'un individu<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Gender extends Bid4WinObjectType<Gender>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 4857152036825646377L;

  /** Genre d'un individu masculin */
  public final static Gender MR = new Gender("MR");
  /** Genre d'un individu f�minin */
  public final static Gender MME = new Gender("MME");
  /** Genre d'un individu f�minin c�libataire */
  public final static Gender MISS = new Gender("MISS", MME);

  /** D�finition du genre d'un individu par d�faut */
  public final static Gender DEFAULT = Bid4WinObjectType.getDefaultType(Gender.class);
  /**
   * Cette m�thode permet de r�cup�rer tous les genres d'individus existants
   * @return Tous les genres d'individus d�finis
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
