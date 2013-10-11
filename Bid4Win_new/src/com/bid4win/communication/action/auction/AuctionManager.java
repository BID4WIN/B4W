//package com.bid4win.communication.action.auction;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//import org.json.JSONObject;
//
//import com.bid4win.commons.core.Bid4WinDate;
//import com.bid4win.communication.action.product.Product;
//import com.bid4win.communication.action.product.ProductManager;
//import com.bid4win.communication.json.factory.JSONAuctionFactory;
//
///**
// * STUB
// *
// * @author Maxime Ollagnier
// */
//public class AuctionManager
//{
//  private Map<String, Auction> auctionMap = null;
//
//  private final static AuctionManager instance = new AuctionManager();
//
//  public static AuctionManager getInstance()
//  {
//    return AuctionManager.instance;
//  }
//
//  protected AuctionManager()
//  {
//    super();
//    auctionMap = new HashMap<String, Auction>();
//
//    /* ## Initialization with stub auctions ## */
//    for(int i = 0 ; i < 3 ; i++)
//    {
//      String id = null;
//      String productId = "productId" + i;
//      Product product = ProductManager.getInstance().getProductMap(productId).get(productId);
//      double price = 1.07 * i;
//      boolean closed = (i % 2) == 0;
//      long countDown = 1000 * i * (i % 2);
//      Bid4WinDate startDate = product.getCreationDate();
//
//      this.addAuction(new Auction(id, product, price, closed, countDown, startDate));
//    }
//    this.persist();
//    /* ## Initialization with stub auctions ## */
//
//    this.init();
//  }
//
//  public void init()
//  {
//    try
//    {
//      BufferedReader buff = new BufferedReader(new FileReader("d:/bid4win.auctions"));
//      String line;
//      while((line = buff.readLine()) != null)
//      {
//        JSONObject jsonAuction = new JSONObject(line);
//        Auction auction = JSONAuctionFactory.getInstance().getAuction(jsonAuction);
//        this.addAuction(auction);
//      }
//      buff.close();
//    }
//    catch(FileNotFoundException e)
//    {
//      e.printStackTrace();
//    }
//    catch(IOException e)
//    {
//      e.printStackTrace();
//    }
//    catch(ParseException e)
//    {
//      e.printStackTrace();
//    }
//  }
//
//  public void persist()
//  {
//    try
//    {
//      File yourFile = new File("d:/bid4win.auctions");
//      yourFile.delete();
//      BufferedWriter buff = new BufferedWriter(new FileWriter("d:/bid4win.auctions", true));
//
//      for(Map.Entry<String, Auction> e : this.auctionMap.entrySet())
//      {
//        Auction auction = e.getValue();
//        buff.append(JSONAuctionFactory.getInstance().getJSONAuction(auction).toString() + "\n");
//      }
//
//      buff.close();
//
//    }
//    catch(FileNotFoundException e)
//    {
//      e.printStackTrace();
//    }
//    catch(IOException e)
//    {
//      e.printStackTrace();
//    }
//  }
//
//  public Auction getAuction(String auctionId)
//  {
//    return this.auctionMap.get(auctionId);
//  }
//
//  public Map<String, Auction> getAuctionMap(String auctionId)
//  {
//    Map<String, Auction> resAuctionMap = new HashMap<String, Auction>();
//    Auction auction = this.getAuction(auctionId);
//    if(auction != null)
//    {
//      resAuctionMap.put(auction.getId(), auction);
//    }
//    else
//    {
//      return this.auctionMap;
//    }
//    return resAuctionMap;
//  }
//
//  public Map<String, Auction> getAuctionMapByString(String searchString)
//  {
//    Map<String, Auction> resAuctionMap = new HashMap<String, Auction>();
//    for(Iterator i = this.auctionMap.values().iterator() ; i.hasNext() ;)
//    {
//      Auction auction = (Auction)(i.next());
//      boolean matchLabel = false;
//      for(int j = 0 ; j < auction.getProduct().getLabelList().size() ; j++)
//      {
//        String labelName = auction.getProduct().getLabelList().get(j).getName();
//        if(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(labelName).find())
//        {
//          matchLabel = true;
//        }
//      }
//      if(matchLabel || Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(auction.getProduct().getName()).find())
//      {
//        resAuctionMap.put(auction.getId(), auction);
//      }
//    }
//    return resAuctionMap;
//  }
//
//  public void addAuction(Auction auction)
//  {
//    if(auction.getId() == null || auction.getId().equals(""))
//    {
//      int index = 0;
//      while(this.auctionMap.containsKey("auctionId" + index))
//      {
//        index++;
//      }
//      auction.setId("auctionId" + index);
//    }
//    this.auctionMap.put(auction.getId(), auction);
//  }
//
//  public Auction removeAuction(String auctionId)
//  {
//    Auction removedAuction = this.auctionMap.remove(auctionId);
//    return removedAuction;
//  }
//
//  public void updateAuction(Auction auction)
//  {
//    this.addAuction(auction);
//  }
//}
