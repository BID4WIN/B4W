package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * Cette classe défini l'historique d'une enchère placée sur une vente normale<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
public class NormalBidHistory extends BidHistory<NormalBidHistory, NormalAuction, NormalBid>
{
  /**
   * Constructeur pour création par introspection
   */
  protected NormalBidHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Enchère dont il faut créer l'historique
   * @throws UserException Si l'enchère en argument est nulle
   */
  protected NormalBidHistory(NormalBid bid) throws UserException
  {
    super(bid);
  }
}
