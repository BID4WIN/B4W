package com.bid4win.communication.json.factory;


/**
 * Singleton Factory class. Handles the conversion of Auction between model and
 * JSON
 *
 * @author Maxime Ollagnier
 */
public class JSONAuctionFactory
{
//  /** JSON property id attribute name */
//  public final static String ID = "id";
//  /** JSON property product attribute name */
//  public final static String PRODUCT = "product";
//  /** JSON property product ID attribute name */
//  public final static String PRODUCT_ID = "productId";
//  /** JSON property price attribute name */
//  public final static String PRICE = "price";
//  /** JSON property closed attribute name */
//  public final static String CLOSED = "closed";
//  /** JSON property count down attribute name */
//  public final static String COUNT_DOWN = "countDown";
//  /** JSON property start date attribute name */
//  public final static String START_DATE = "startDate";
//  // TODO bidHistory
//
//  /** Unique instance used as singleton */
//  private static JSONAuctionFactory instance;
//  static
//  {
//    JSONAuctionFactory.instance = new JSONAuctionFactory();
//  }
//
//  /**
//   * Getter of the unique instance in memory
//   * @return The unique instance in memory
//   */
//  public static JSONAuctionFactory getInstance()
//  {
//    return JSONAuctionFactory.instance;
//  }
//
//  /**
//   * Protected constructor
//   */
//  protected JSONAuctionFactory()
//  {
//    super();
//  }
//
//  /**
//   * Returns a JSON object generated from the given auction
//   * @param auction The auction to generate the JSON object from
//   * @return The generated JSON object
//   */
//  public JSONObject getJSONAuction(Auction auction)
//  {
//    JSONObject jsonAuction = new JSONObject();
//
//    jsonAuction.put(JSONAuctionFactory.ID, auction.getId());
//    jsonAuction.put(JSONAuctionFactory.PRODUCT, JSONProductFactory.getInstance().getJSONProduct(auction.getProduct()));
//    jsonAuction.put(JSONAuctionFactory.PRICE, auction.getPrice());
//    jsonAuction.put(JSONAuctionFactory.CLOSED, auction.isClosed());
//    jsonAuction.put(JSONAuctionFactory.COUNT_DOWN, auction.getCountDown());
//    jsonAuction.put(JSONAuctionFactory.START_DATE, UtilDate.formatDDIMMIYYYY(auction.getStartDate()));
//    // TODO bidHistory
//
//    return jsonAuction;
//  }
//
//  /**
//   * Returns an object generated from the given json
//   * @param json The json object to generate the model object from
//   * @return The generated model object
//   */
//  public Auction getAuction(JSONObject json)
//  {
//    // TODO bidHistory
//    Auction auction = null;
//    try
//    {
//      String id = json.getString(JSONAuctionFactory.ID);
//      Product product = JSONProductFactory.getInstance().getProduct(json.getJSONObject(JSONAuctionFactory.PRODUCT));
//      double price = json.getDouble(JSONAuctionFactory.PRICE);
//      boolean closed = json.getBoolean(JSONAuctionFactory.CLOSED);
//      long countDown = json.getLong(JSONAuctionFactory.COUNT_DOWN);
//      Bid4WinDate startDate = Bid4WinDateFormat.parseDDIMMIYYYY(json.getString(JSONAuctionFactory.START_DATE));
//
//      auction = new Auction(id, product, price, closed, countDown, startDate);
//    }
//    catch(Exception e)
//    {
//      e.printStackTrace();
//    }
//
//    return auction;
//  }
//
//  /**
//   * Returns a JSON object generated from the given auction list
//   * @param auctionMap The auction map to generate the JSON object from
//   * @return The generated JSON object
//   */
//  public JSONObject getJSONAuctions(Map<String, Auction> auctionMap)
//  {
//    if(auctionMap == null)
//    {
//      return null;
//    }
//    JSONObject jsonAuctions = new JSONObject();
//    for(Map.Entry<String, Auction> e : auctionMap.entrySet())
//    {
//      jsonAuctions.put(e.getKey(), this.getJSONAuction(e.getValue()));
//    }
//    return jsonAuctions;
//  }
}
