package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * Cette classe d�fini l'historique d'une ench�re plac�e sur une vente avec pr�-
 * inscription<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
public class PrebookedBidHistory
       extends BidHistory<PrebookedBidHistory, PrebookedAuction, PrebookedBid>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected PrebookedBidHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Ench�re dont il faut cr�er l'historique
   * @throws UserException Si l'ench�re en argument est nulle
   */
  protected PrebookedBidHistory(PrebookedBid bid) throws UserException
  {
    super(bid);
  }
}
