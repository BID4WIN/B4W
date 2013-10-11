package com.bid4win.persistence.entity.auction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType_;

/**
 * Metamodel de la classe AuctionStatus<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AuctionStatus.class)
public class AuctionStatus_ extends Bid4WinEmbeddableWithType_
{
  /** D�finition du status de la vente aux ench�res normale */
  public static volatile SingularAttribute<AuctionStatus, Status> type;

}
