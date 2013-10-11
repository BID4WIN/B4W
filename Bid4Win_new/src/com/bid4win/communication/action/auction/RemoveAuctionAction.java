//package com.bid4win.communication.action.auction;
//
//import org.apache.struts2.convention.annotation.Action;
//
//import com.bid4win.communication.action.json.JSONAction;
//import com.bid4win.communication.json.factory.JSONAuctionFactory;
//
///**
// * Action class. Handles a request for a auction deletion.
// *
// * @author Maxime Ollagnier
// */
//public class RemoveAuctionAction extends JSONAction
//{
//  /**
//   * Asks the AuctionManager to remove the auction whom id is passed within the
//   * request. The JSON result is filled with the removed JSON auction.
//   * @return {@inheritDoc}
//   * @see com.opensymphony.xwork2.ActionSupport#execute()
//   */
//  @Action("RemoveAuctionAction")
//  @Override
//  public String execute()
//  {
//    try
//    {
//      String auctionId = this.getParameter("id");
//      Auction removedAuction = null;
//
//      removedAuction = AuctionManager.getInstance().removeAuction(auctionId);
//      AuctionManager.getInstance().persist();
//
//      this.putJSONObject(JSONAuctionFactory.getInstance().getJSONAuction(removedAuction));
//      this.setSuccess(true);
//    }
//    catch(Exception e)
//    {
//      this.putErrorMessage(e.getMessage());
//      e.printStackTrace();
//    }
//    return SUCCESS;
//  }
//}