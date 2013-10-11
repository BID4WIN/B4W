//package com.bid4win.communication.action.auction;
//
//import org.apache.struts2.convention.annotation.Action;
//
//import com.bid4win.commons.core.Bid4WinDate;
//import com.bid4win.commons.core.Bid4WinDateFormat;
//import com.bid4win.communication.action.json.JSONAction;
//import com.bid4win.communication.action.product.Product;
//import com.bid4win.communication.action.product.ProductManager;
//import com.bid4win.communication.json.factory.JSONAuctionFactory;
//
///**
// * Action class. Handles a request for a auction creation.
// *
// * @author Maxime Ollagnier
// */
//public class SaveAuctionAction extends JSONAction
//{
//  /**
//   * Asks the AuctionManager to add a new auction whom attributes are passed
//   * within the request. The JSON result is filled with the added JSON auction.
//   * @return {@inheritDoc}
//   * @see com.opensymphony.xwork2.ActionSupport#execute()
//   */
//  @Action("SaveAuctionAction")
//  @Override
//  public String execute()
//  {
//    try
//    {
//      String id = this.getParameter("id");
//      Product product = ProductManager.getInstance().getProduct(this.getParameter("productId"));
//      double price = Double.parseDouble(this.getParameter("price"));
//      boolean closed = Boolean.getBoolean(this.getParameter("closed"));
//      long countDown = Long.parseLong(this.getParameter("countDown"));
//      Bid4WinDate startDate = Bid4WinDateFormat.parseDDIMMIYYYY(this.getParameter("startDate"));
//
//      Auction savedAuction = new Auction(id, product, price, closed, countDown, startDate);
//      AuctionManager.getInstance().addAuction(savedAuction);
//      AuctionManager.getInstance().persist();
//
//      this.putJSONObject(JSONAuctionFactory.getInstance().getJSONAuction(savedAuction));
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