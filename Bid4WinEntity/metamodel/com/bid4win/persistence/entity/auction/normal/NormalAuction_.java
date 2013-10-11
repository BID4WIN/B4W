package com.bid4win.persistence.entity.auction.normal;

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
 * Metamodel de la classe NormalAuction<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(NormalAuction.class)
public class NormalAuction_ extends Auction_
{
  /** D�finition du prix du produit vendu aux ench�res */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, Price> productPrice;
  /** D�finition de l'�tape de la vente */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, SaleStep> saleStep;
  /** D�finition des taux de change applicables sur la vente aux ench�res */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, ExchangeRates> exchangeRates;
  /** D�finition du status de la vente aux ench�res normale */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, AuctionStatus> auctionStatus;
  /** D�finition des �l�ments de planification de la vente aux ench�res normale */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, NormalSchedule> schedule;
  /** D�finition des conditions de la vente aux ench�res normale */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, NormalTerms> terms;
  /** D�finition de la politique d'annulation de la vente aux ench�res normale */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, NormalCancelPolicy> cancelPolicy;
  /** D�finition de la valeur des implications sur la vente aux ench�res normale */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<NormalAuction, Amount> involvementValue;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
