//package com.bid4win.communication.action.auction;
//
//import java.util.Map;
//
//import org.apache.struts2.convention.annotation.Action;
//
//import com.bid4win.communication.action.json.JSONAction;
//import com.bid4win.communication.json.factory.JSONAuctionFactory;
//
///**
// * Action class. Handles requests for property fetching.
// *
// * @author Maxime Ollagnier
// */
//public class GetAuctionAction extends JSONAction
//{
//  /**
//   * TODO A COMMENTER
//   *
//   * @return {@inheritDoc}
//   * @see com.opensymphony.xwork2.ActionSupport#execute()
//   */
//  @Action("GetAuctionAction")
//  @Override
//  public String execute()
//  {
//    try
//    {
//      String auctionId = this.findParameter("id");
//      String searchString = this.findParameter("searchString");
//      Map<String, Auction> auctionMap = null;
//      if(searchString != null)
//      {
//        auctionMap = AuctionManager.getInstance().getAuctionMapByString(searchString);
//      }
//      else
//      {
//        auctionMap = AuctionManager.getInstance().getAuctionMap(auctionId);
//      }
//      this.putJSONObject(JSONAuctionFactory.getInstance().getJSONAuctions(auctionMap));
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