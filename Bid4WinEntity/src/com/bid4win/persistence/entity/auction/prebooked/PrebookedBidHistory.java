package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * Cette classe défini l'historique d'une enchère placée sur une vente avec pré-
 * inscription<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
public class PrebookedBidHistory
       extends BidHistory<PrebookedBidHistory, PrebookedAuction, PrebookedBid>
{
  /**
   * Constructeur pour création par introspection
   */
  protected PrebookedBidHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Enchère dont il faut créer l'historique
   * @throws UserException Si l'enchère en argument est nulle
   */
  protected PrebookedBidHistory(PrebookedBid bid) throws UserException
  {
    super(bid);
  }
}
