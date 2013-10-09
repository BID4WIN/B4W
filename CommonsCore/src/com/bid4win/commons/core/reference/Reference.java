package com.bid4win.commons.core.reference;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Reference<CLASS extends Reference<CLASS>> extends Bid4WinObjectType<CLASS>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -5361741675284014173L;

  /**
   * Constructeur
   * @param code Code de la référence
   */
  protected Reference(String code)
  {
    super(UtilString.trimNotNull(code).toLowerCase());
  }
  /**
   * Constructeur
   * @param code Code de la référence
   * @param parent Parent de la référence
   */
  protected Reference(String code, CLASS parent)
  {
    super(parent.getCode() + "." + UtilString.trimNotNull(code).toLowerCase(), parent);
  }

  /**
   * Cette méthode permet de récupérer la sous-référence correspondant à celle
   * courante parcourue suivant la liste de sous-codes donnés
   * @param partialCodes Sous-codes permettant de parcourir la référence courante
   * jusqu'à la sous-référence choisie
   * @return La sous-référence correspondant à celle courante parcourue suivant
   * la liste de sous-codes donnés
   * @throws Bid4WinRuntimeException Si la sous-référence ne peut être trouvée
   */
  @SuppressWarnings("unchecked")
  public CLASS getSubMessageRef(String ... partialCodes)
  {
    CLASS reference = (CLASS)this;
    String code = this.getCode();
    for(String partialCode : partialCodes)
    {
      code += "." + partialCode;
      reference = reference.getSubtype(code);
      if(reference == null)
      {
        throw new Bid4WinRuntimeException("Unknown message reference " + code);
      }
    }
    return reference;
  }
}
