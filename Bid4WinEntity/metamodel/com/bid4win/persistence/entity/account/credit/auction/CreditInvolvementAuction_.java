package com.bid4win.persistence.entity.account.credit.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;

/**
 * Metamodel de la classe CreditInvolvementAuction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditInvolvementAuction.class)
public class CreditInvolvementAuction_ extends CreditInvolvementAuctionAbstract_
{
  /** Définition de l'identifiant d'historisation de l'implication de crédits */
  public static volatile SingularAttribute<CreditInvolvementAuction<?, ?, ?, ?>, Long> historyId;
  /** Définition de l'historisation de l'implication de crédits */
  public static volatile SingularAttribute<CreditInvolvementAuction<?, ?, ?, ?>, CreditInvolvementAuctionHistory<?, ?, ?, ?>> history;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec l'historisation du lot de crédits */
    CreditInvolvementAuction_Relations.NODE_HISTORY.addNode(CreditInvolvementAuctionHistory_Relations.NODE_ACCOUNT);
    CreditInvolvementAuction_Relations.NODE_HISTORY.addNode(CreditInvolvementAuctionHistory_Relations.NODE_AUCTION);
    CreditInvolvementAuction_Relations.NODE_HISTORY.addNode(CreditInvolvementAuctionHistory_Relations.NODE_USAGE_MAP);
    Bid4WinEntity_.stopProtection();
  }
}
