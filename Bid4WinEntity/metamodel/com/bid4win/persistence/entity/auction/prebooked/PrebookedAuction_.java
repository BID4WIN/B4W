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
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PrebookedAuction.class)
public class PrebookedAuction_ extends Auction_
{
  /** Définition du prix du produit vendu aux enchères */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, Price> productPrice;
  /** Définition de l'étape de la vente */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, SaleStep> saleStep;
  /** Définition des taux de change applicables sur la vente aux enchères */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, ExchangeRates> exchangeRates;
  /** Définition du status de la vente aux enchères avec pré-inscription */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, AuctionStatus> auctionStatus;
  /** Définition des éléments de planification de la vente aux enchères avec pré-inscription */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, PrebookedSchedule> schedule;
  /** Définition des conditions de l'historique de vente aux enchères avec pré-inscription */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, PrebookedTerms> terms;
  /** Définition de la politique d'annulation de la vente aux enchères normale */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, PrebookedCancelPolicy> cancelPolicy;
  /** Définition de la valeur des implications sur la vente aux enchères avec pré-inscription */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<PrebookedAuction, Amount> involvementValue;

  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
