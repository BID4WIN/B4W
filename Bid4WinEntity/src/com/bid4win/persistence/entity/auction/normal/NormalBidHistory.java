package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.BidHistory;

/**
 * Cette classe d�fini l'historique d'une ench�re plac�e sur une vente normale<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
public class NormalBidHistory extends BidHistory<NormalBidHistory, NormalAuction, NormalBid>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected NormalBidHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Ench�re dont il faut cr�er l'historique
   * @throws UserException Si l'ench�re en argument est nulle
   */
  protected NormalBidHistory(NormalBid bid) throws UserException
  {
    super(bid);
  }
}
