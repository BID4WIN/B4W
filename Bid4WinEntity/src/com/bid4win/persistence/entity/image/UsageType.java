package com.bid4win.persistence.entity.image;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe d�fini les diff�rents type d'utilisation d'image dans l'application<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UsageType extends Bid4WinObjectType<UsageType>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 2692740061357689430L;

  /** Utilisation d'image simple */
  public final static UsageType SIMPLE = new UsageType("SIMPLE");
  /** Utilisation d'image pour un produit */
  public final static UsageType PRODUCT = new UsageType("PRODUCT");

  /** D�finition du type d'utilisation d'image par d�faut */
  public final static UsageType DEFAULT = Bid4WinObjectType.getDefaultType(UsageType.class);

  /**
   * Cette m�thode permet de r�cup�rer le type d'utilisation d'image dont le code
   * est donn� en argument
   * @param code Code du type d'utilisation d'image � r�cup�rer
   * @return Le type d'utilisation d'image correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond � aucun type d'
   * utilisation d'image connu
   */
  public static UsageType getUsageType(String code) throws UserException
  {
    return Bid4WinObjectType.getType(UsageType.class, code);
  }
  /**
   * Cette m�thode permet de r�cup�rer tous les types d'utilisation d'image existants
   * @return Le set complet de tous les types d'utilisation d'image d�finis
   */
  public static Bid4WinSet<UsageType> getUsageTypeSet()
  {
    return Bid4WinObjectType.getTypeSet(UsageType.class);
  }

  /**
   * Constructeur
   * @param code Code du type d'utilisation
   */
  private UsageType(String code)
  {
    super(code);
  }
}
