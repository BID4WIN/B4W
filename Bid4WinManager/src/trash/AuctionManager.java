//package trash;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.manager.account.AccountManager;
//import com.bid4win.model.auction.Auction;
//import com.bid4win.model.auction.BidRights;
//import com.bid4win.model.security.Account;
//
///**
// * Cette classe est le manager d'une vente aux ench�res<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class AuctionManager
//{
//  /** Vente aux ench�res g�r�e par le manager */
//  private Auction auction = null;
//
//  /**
//   * Constructeur
//   * @param auction Vente aux ench�res g�r�e par le manager
//   * @throws ModelArgumentException Si les param�tres entr�s sont invalides
//   */
//  public AuctionManager(Auction auction) throws ModelArgumentException
//  {
//    super();
//    this.setAuction(auction);
//  }
//
//  public void bid(long accountId, int bidNb)
//  {
//    try
//    {
//      Account account = AccountManager.getInstance().getLock(accountId);
//      BidRights bidRights = account.bid(bidNb);
//    }
//    catch(Bid4WinException ex)
//    {
//      //TODO
//    }
//    finally
//    {
//      AccountManager.getInstance().release(accountId);
//    }
//  }
//
//  /**
//   * Getter de la vente aux ench�res g�r�e par le manager
//   * @return La vente aux ench�res g�r�e par le manager
//   */
//  public Auction getAuction()
//  {
//    return this.auction;
//  }
//
//  /**
//   * Setter de la vente aux ench�res g�r�e par le manager
//   * @param auction Vente aux ench�res � positionner
//   * @throws ModelArgumentException Si on positionne une vente aux ench�res
//   * nulle
//   */
//  private void setAuction(Auction auction) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("auction", auction);
//    this.auction = auction;
//  }
//}
