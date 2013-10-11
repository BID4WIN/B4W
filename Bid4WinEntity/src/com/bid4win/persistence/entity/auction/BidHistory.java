package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;

/**
 * Cette classe défini un historique d'enchère placée sur une vente<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <AUCTION> Définition de la vente associée à l'enchère historisée<BR>
 * @param <BID> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class BidHistory<CLASS extends BidHistory<CLASS, AUCTION, BID>,
                        AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                        BID extends BidAbstract<BID, AUCTION, CLASS>>
       extends BidAbstract<CLASS, AUCTION, CLASS>
{
  /** Date d'enchérissement */
  @Transient
  private Bid4WinDate bidDate = null;

  /**
   * Constructeur pour création par introspection
   */
  protected BidHistory()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bid Enchère dont il faut créer l'historique
   * @throws UserException Si l'enchère en argument est nulle
   */
  protected BidHistory(BID bid) throws UserException
  {
    super(UtilObject.checkNotNull("bid", bid,
                                  AuctionRef.AUCTION_BID_MISSING_ERROR).getAccount(),
          bid.getAuction(), bid.getPosition());
    this.setBidDate(bid.getCreateDate());
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.BidAbstract#toHistory()
   * @Deprecated TODO A COMMENTER
   */
  @Deprecated
  @Override
  public CLASS toHistory() throws UserException
  {
    throw new UserException(AuctionRef.AUCTION_HISTORIZED_ERROR);
  }
  /**
   *
   * TODO A COMMENTER
   * @param bid TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isHistoryOf(BID bid)
  {
    if(bid == null)
    {
      return false;
    }
    return Bid4WinComparator.getInstance().equals(this.getAccount().getId(),
                                                  bid.getAccount().getId()) &&
           Bid4WinComparator.getInstance().equals(this.getAuction().getId(),
                                                  bid.getAuction().getId()) &&
           Bid4WinComparator.getInstance().equals(this.getBidDate(), bid.getCreateDate()) &&
           this.getPosition() == bid.getPosition();
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la date d'enchérissement
   * @return La date d'enchérissement
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "BID_DATE", nullable = false, unique = false)
  public Bid4WinDate getBidDate()
  {
    return this.bidDate;
  }
  /**
   * Setter de la date d'enchérissement
   * @param bidDate Date d'enchérissement à positionner
   */
  private void setBidDate(Bid4WinDate bidDate)
  {
    this.bidDate = bidDate;
  }
}
