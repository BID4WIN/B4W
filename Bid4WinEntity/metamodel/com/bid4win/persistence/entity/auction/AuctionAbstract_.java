package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.product.Product_Relations;
import com.bid4win.persistence.entity.sale.Sale_;

/**
 * Metamodel de la classe AuctionAbstract<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(AuctionAbstract.class)
public class AuctionAbstract_ extends Sale_
{
  /** Définition des taux de change applicables sur la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, ExchangeRates> exchangeRates;
  /** Définition du status de la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, AuctionStatus> auctionStatus;
  /** Définition des éléments de planification de la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, ScheduleAbstract<?>> schedule;
  /** Définition des conditions de la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, TermsAbstract<?>> terms;
  /** Définition de la politique d'annulation de la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, CancelPolicyAbstract<?>> cancelPolicy;
  /** Définition du nombre d'enchères placées sur la vente */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Integer> bidNb;
  /** Définition de l'état d'historiation des enchères la vente */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Boolean> bidHistorized;
  /** Définition de la valeur du nombre de crédits impliqués sur la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Integer> involvedCreditNb;
  /** Définition de la valeur des implications sur la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Amount> involvementValue;
  /** Définition de l'état d'historiation des implications sur la vente aux enchères */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Boolean> involvementHistorized;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec le produit vendu aux enchères */
    AuctionAbstract_Relations.NODE_PRODUCT.addNode(Product_Relations.NODE_IMAGE_USAGE_LIST);
    Bid4WinEntity_.stopProtection();
  }
}
