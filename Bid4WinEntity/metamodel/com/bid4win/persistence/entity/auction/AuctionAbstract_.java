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
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AuctionAbstract.class)
public class AuctionAbstract_ extends Sale_
{
  /** D�finition des taux de change applicables sur la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, ExchangeRates> exchangeRates;
  /** D�finition du status de la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, AuctionStatus> auctionStatus;
  /** D�finition des �l�ments de planification de la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, ScheduleAbstract<?>> schedule;
  /** D�finition des conditions de la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, TermsAbstract<?>> terms;
  /** D�finition de la politique d'annulation de la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, CancelPolicyAbstract<?>> cancelPolicy;
  /** D�finition du nombre d'ench�res plac�es sur la vente */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Integer> bidNb;
  /** D�finition de l'�tat d'historiation des ench�res la vente */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Boolean> bidHistorized;
  /** D�finition de la valeur du nombre de cr�dits impliqu�s sur la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Integer> involvedCreditNb;
  /** D�finition de la valeur des implications sur la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Amount> involvementValue;
  /** D�finition de l'�tat d'historiation des implications sur la vente aux ench�res */
  public static volatile SingularAttribute<AuctionAbstract<?, ?, ?, ?, ?>, Boolean> involvementHistorized;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec le produit vendu aux ench�res */
    AuctionAbstract_Relations.NODE_PRODUCT.addNode(Product_Relations.NODE_IMAGE_USAGE_LIST);
    Bid4WinEntity_.stopProtection();
  }
}
