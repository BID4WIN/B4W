package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette classe d�fini un type de provenance de cr�dits<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Origin extends Bid4WinObjectType<Origin>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -2848163166522916784L;

  /** Cr�dits en provenance d'un achat */
  public final static Origin PURCHASE = new Origin("PURCHASE");
  /** Cr�dits en provenance d'un parrainage */
  public final static Origin SPONSORSHIP = new Origin("SPONSORSHIP");
  /** Cr�dits en provenance d'un remboursement */
  public final static Origin REFUND = new Origin("REFUND");
  /** Cr�dits en provenance d'un remboursement d'une vente aux ench�res */
  public final static Origin REFUND_AUCTION = new Origin("AUCTION", REFUND);
  /** Cr�dits en provenance d'un remboursement d'une vente aux ench�res normale */
  public final static Origin REFUND_AUCTION_NORMAL = new Origin("NORMAL", REFUND_AUCTION);
  /** Cr�dits en provenance d'un remboursement d'une vente aux ench�res avec pr�-inscription */
  public final static Origin REFUND_AUCTION_PREBOOKED = new Origin("PREBOOKED", REFUND_AUCTION);

  /**
   * Constructeur
   * @param code Code de la provenance de cr�dits
   */
  private Origin(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de la provenance de cr�dits
   * @param parent Parent de la provenance de cr�dits
   */
  private Origin(String code, Origin parent)
  {
    super(code, parent);
  }
}
