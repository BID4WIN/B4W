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
 * @author Emeric Fill�tre
 */
public class Reference<CLASS extends Reference<CLASS>> extends Bid4WinObjectType<CLASS>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -5361741675284014173L;

  /**
   * Constructeur
   * @param code Code de la r�f�rence
   */
  protected Reference(String code)
  {
    super(UtilString.trimNotNull(code).toLowerCase());
  }
  /**
   * Constructeur
   * @param code Code de la r�f�rence
   * @param parent Parent de la r�f�rence
   */
  protected Reference(String code, CLASS parent)
  {
    super(parent.getCode() + "." + UtilString.trimNotNull(code).toLowerCase(), parent);
  }

  /**
   * Cette m�thode permet de r�cup�rer la sous-r�f�rence correspondant � celle
   * courante parcourue suivant la liste de sous-codes donn�s
   * @param partialCodes Sous-codes permettant de parcourir la r�f�rence courante
   * jusqu'� la sous-r�f�rence choisie
   * @return La sous-r�f�rence correspondant � celle courante parcourue suivant
   * la liste de sous-codes donn�s
   * @throws Bid4WinRuntimeException Si la sous-r�f�rence ne peut �tre trouv�e
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
