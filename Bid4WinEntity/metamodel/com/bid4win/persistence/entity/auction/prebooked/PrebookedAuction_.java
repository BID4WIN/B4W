package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.auction.AuctionStatus;
import com.bid4win.persistence.entity.auction.Auction_;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.sale.SaleStep;

/**
 * Metamodel de la classe PrebookedAuction<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(PrebookedAuction.class)
public class PrebookedAuction_ extends Auction_
{
  /** D�finition du prix du produit vendu aux ench�res */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, Price> productPrice;
  /** D�finition de l'�tape de la vente */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, SaleStep> saleStep;
  /** D�finition des taux de change applicables sur la vente aux ench�res */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, ExchangeRates> exchangeRates;
  /** D�finition du status de la vente aux ench�res avec pr�-inscription */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, AuctionStatus> auctionStatus;
  /** D�finition des �l�ments de planification de la vente aux ench�res avec pr�-inscription */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, PrebookedSchedule> schedule;
  /** D�finition des conditions de l'historique de vente aux ench�res avec pr�-inscription */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, PrebookedTerms> terms;
  /** D�finition de la politique d'annulation de la vente aux ench�res normale */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, PrebookedCancelPolicy> cancelPolicy;
  /** D�finition de la valeur des implications sur la vente aux ench�res avec pr�-inscription */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, Amount> involvementValue;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
