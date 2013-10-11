package com.bid4win.persistence.entity.account.credit;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette classe défini un type de provenance de crédits<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Origin extends Bid4WinObjectType<Origin>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -2848163166522916784L;

  /** Crédits en provenance d'un achat */
  public final static Origin PURCHASE = new Origin("PURCHASE");
  /** Crédits en provenance d'un parrainage */
  public final static Origin SPONSORSHIP = new Origin("SPONSORSHIP");
  /** Crédits en provenance d'un remboursement */
  public final static Origin REFUND = new Origin("REFUND");
  /** Crédits en provenance d'un remboursement d'une vente aux enchères */
  public final static Origin REFUND_AUCTION = new Origin("AUCTION", REFUND);
  /** Crédits en provenance d'un remboursement d'une vente aux enchères normale */
  public final static Origin REFUND_AUCTION_NORMAL = new Origin("NORMAL", REFUND_AUCTION);
  /** Crédits en provenance d'un remboursement d'une vente aux enchères avec pré-inscription */
  public final static Origin REFUND_AUCTION_PREBOOKED = new Origin("PREBOOKED", REFUND_AUCTION);

  /**
   * Constructeur
   * @param code Code de la provenance de crédits
   */
  private Origin(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de la provenance de crédits
   * @param parent Parent de la provenance de crédits
   */
  private Origin(String code, Origin parent)
  {
    super(code, parent);
  }
}
