package trash;
//package com.bid4win.commons.persistence.dao.auction;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.persistence.entity.EntityGenerator;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.account.BidRightBundle;
//import com.bid4win.persistence.entity.auction.Auction;
//import com.bid4win.persistence.entity.auction.Bid;
//
///**
// * 
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:META-INF/config/spring-test.xml")
//@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
//public class BidDaoTest //extends AccountBasedTester<Bid, Long>
//{
////  /** Identifiant de la vente utilisée pour les tests */
////  private String auctionId = null;
////  /** Référence du DAO à tester */
////  @Autowired
////  @Qualifier("BidDaoStub")
////  private BidDaoStub dao;
////  /** Référence du DAO des ventes aux enchères*/
////  @Autowired
////  @Qualifier("AuctionDaoStub")
////  private AuctionDaoStub auctionDao;
////
////  
////  /**
////   * Test of add(Bid), of class BidDao.
////   * @throws Bid4WinException Issue not expected during this test
////   */
////  @Test
////  public void testAdd_Bid() throws Bid4WinException
////  {
////    System.out.println("### add(Bid))");
////
////    // Récupère la vente aux enchères du test
////    Auction auction = this.getAuctionDao().getById(this.getAuctionId());
////    
////    Bid4WinList<Long> bidIdList = new Bid4WinList<Long>();
////    for(int i = 0 ; i < auction.getLastBidNbMax() ; i++)
////    {
////      // Récupère la vente aux enchères du test
////      auction = this.getAuctionDao().getById(this.getAuctionId());
////      // Récupère le compte utilisateur du test
////      Account account = this.getAccountDao().getById(this.getAccountId(i % this.getAccountNb()));
////      // Crée l'enchère
////      Bid bid = new Bid(new BidRightBundle(account, 1), auction);
////      // Ajoute l'enchère
////      this.getDao().add(bid);
////      // Conserve l'identifiant
////      bidIdList.add(bid.getId());
////    }
////    // Récupère la vente aux enchères modifiée
////    Auction result = this.getAuctionDao().getById(this.getAuctionId());
////    // Test la vente aux enchères modifiée
////    assertEquals("Wrong version", auction.getLastBidNbMax(), result.getVersion());
////    assertEquals("Wrong bid nb", auction.getLastBidNbMax(), result.getBidNb());
////    assertEquals("Wrong last bid nb", auction.getLastBidNbMax(), result.getLastBidNb());
////    for(int i = 0 ; i < auction.getLastBidNbMax() ; i++)
////    {
////      // Récupère le compte utilisateur du test
////      Account account = this.getAccountDao().getById(this.getAccountId(i % this.getAccountNb()));
////      // Récupère l'enchère ajoutée
////      Bid bid = this.getDao().getById(bidIdList.get(i));
////      // Test l'enchère ajoutée
////      assertEquals("Wrong version", 0, bid.getVersion());
////      assertEquals("Wrong position", i + 1, bid.getPosition());
////      assertTrue("Wrong auction", result.identical(bid.getAuctionLink()));
////      assertEquals("Wrong auction ID", result.getId(), bid.getAuctionId());
////      assertTrue("Wrong account", account.identical(bid.getAccountLink()));
////      assertEquals("Wrong account", account.identical(bid.getAccount()));
////      assertEquals("Wrong version", 0, account.getVersion());
////      assertTrue("Wrong bid", bid.identical(result.getBid(bid.getPosition())));
////    }
////    
////    // Récupère la vente aux enchères du test
////    auction = this.getAuctionDao().getById(this.getAuctionId());
////    // Récupère le compte utilisateur du test
////    Account account = this.getAccountDao().getById(this.getAccountId(0 % this.getAccountNb()));
////    // Crée l'enchère
////    Bid bid = new Bid(new BidRightBundle(account, 1), auction);
////    // Ajoute l'enchère
////    this.getDao().add(bid);
////    // Conserve l'identifiant
////    bidIdList.add(bid.getId());
////    
////    // Récupère la vente aux enchères modifiée
////    result = this.getAuctionDao().getById(this.getAuctionId());
////    // Test la vente aux enchères modifiée
////    assertEquals("Wrong version", auction.getLastBidNbMax() + 1, result.getVersion());
////    assertEquals("Wrong bid nb", auction.getLastBidNbMax() + 1, result.getBidNb());
////    assertEquals("Wrong last bid nb", auction.getLastBidNbMax(), result.getLastBidNb());
////    for(int i = 1 ; i < auction.getLastBidNbMax() + 1; i++)
////    {
////      // Récupère le compte utilisateur du test
////      account = this.getAccountDao().getById(this.getAccountId(i % this.getAccountNb()));
////      // Récupère l'enchère ajoutée
////      bid = this.getDao().getById(bidIdList.get(i));
////      // Test l'enchère ajoutée
////      assertEquals("Wrong version", 0, bid.getVersion());
////      assertEquals("Wrong position", i + 1, bid.getPosition());
////      assertEquals("Wrong auction", result, bid.getAuctionLink());
////      assertEquals("Wrong auction ID", result.getId(), bid.getAuctionId());
////      assertEquals("Wrong account", account, bid.getAccountLink());
////      assertEquals("Wrong account", account.identical(bid.getAccount()));
////      assertEquals("Wrong version", 0, account.getVersion());
////      assertEquals("Wrong bid", bid, result.getBid(bid.getPosition()));
////    }
////    
////    // Récupère le compte utilisateur du test
////    account = this.getAccountDao().getById(this.getAccountId(0 % this.getAccountNb()));
////    // Récupère l'enchère déliée
////    bid = this.getDao().getById(bidIdList.get(0));
////    // Test l'enchère déliée
////    assertEquals("Wrong version", 1, bid.getVersion());
////    assertEquals("Wrong position", 1, bid.getPosition());
////    assertNull("Wrong auction", bid.getAuctionLink());
////    assertEquals("Wrong auction ID", result.getId(), bid.getAuctionId());
////    assertNull("Wrong account", bid.getAccountLink());
////    assertEquals("Wrong account", account.identical(bid.getAccount()));
////    assertEquals("Wrong version", 0, account.getVersion());
////    assertNull("Wrong bid", result.getBid(bid.getPosition()));
////    System.out.println("### add(Bid)");
////  }
////  /**
////   * Test of update(BidRightBundle), of class BidRightBundleDao.
////   * @throws Bid4WinException Issue not expected during this test
////   */
/////*  @Test
////  public void testUpdate_BidRightBundle() throws Bid4WinException
////  {
////    System.out.println("### update(BidRightBundle))");
////    
////    // Récupère le compte utilisateur du test
////    Account account = this.getAccountDao().getById(this.getAccountId());
////    // Ajoute un lot de droits à enchérir
////    BidRightBundle bundle = new BidRightBundle(account, 10);
////    BidRightBundle result = this.getDao().add(bundle);
////    // Vérifie le lot de droits à enchérir ajouté
////    assertTrue("Wrong ID", 0 < result.getId());
////    assertEquals("Wrong version", 0, result.getVersion());
////    assertTrue("Wrong result", result.identical(bundle));
////    // Récupère le compte utilisateur du lot de droit à enchérir ajouté
////    Account test = this.getAccountDao().getById(this.getAccountId());
////    // Vérifie le compte utilisateur du lot de droits à enchérir ajouté
////    assertEquals("Wrong version", 1, test.getVersion());
////
////    for(int i = 0 ; i < bundle.getInitialNb() - 1 ; i++)
////    {
////      // Utilise un droit à enchérir du lot
////      bundle.use();
////      bundle = this.getDao().update(bundle);
////      // Vérifie le lot de droits à enchérir utilisé
////      assertTrue("Wrong ID", 0 < bundle.getId());
////      assertEquals("Wrong version", 1 + i, bundle.getVersion());
////      assertTrue("Should still be linked", bundle.isLinkedToAccount());
////      // Récupère le compte utilisateur du lot de droit à enchérir ajouté
////      test = this.getAccountDao().getById(this.getAccountId());
////      assertEquals("Wrong version", 1, test.getVersion());
////    }
////    
////    // Utilise le dernier droit à enchérir du lot
////    bundle.use();
////    bundle = this.getDao().update(bundle);
////    // Vérifie le lot de droits à enchérir utilisé
////    assertTrue("Wrong ID", 0 < bundle.getId());
////    assertEquals("Wrong version", 10, bundle.getVersion());
////    assertFalse("Should not be linked anymore", bundle.isLinkedToAccount());
////    // Récupère le compte utilisateur du lot de droit à enchérir utilisé
////    test = this.getAccountDao().getById(this.getAccountId());
////    // Vérifie le compte utilisateur du lot de droits à enchérir utilisé
////    assertEquals("Wrong version", 2, test.getVersion());
////
////    // Modifie encore le lot de droits à enchérir
////    bundle = this.getDao().update(bundle);
////    // Vérifie le lot de droits à enchérir utilisé
////    assertTrue("Wrong ID", 0 < bundle.getId());
////    assertEquals("Wrong version", 11, bundle.getVersion());
////    assertFalse("Should still not be linked", bundle.isLinkedToAccount());
////    // Récupère le compte utilisateur du lot de droit à enchérir utilisé
////    test = this.getAccountDao().getById(this.getAccountId());
////    // Vérifie le compte utilisateur du lot de droits à enchérir utilisé
////    assertEquals("Wrong version", account.getVersion() + 1, test.getVersion());
////    
////    System.out.println("### update(BidRightBundle)");
////  }*/
////  
////  /**
////   * Test setup method
////   * @throws Exception Issue not expected during test setup
////   */
////  @Override
////  @Before
////  public void setUp() throws Exception
////  {
////    super.setUp();
////    try
////    {
////      this.setAuctionId(this.getAuctionDao().add(new Auction("123")).getId());
////    }
////    catch(Exception ex)
////    {
////      ex.printStackTrace();
////      throw ex;
////    }
////    try
////    {
////      for(int i = 1 ; i < 5 ; i++)
////      {
////        this.addAccount(EntityGenerator.getInstance().createAccount(i));
////      }
////    }
////    catch(Exception ex)
////    {
////      ex.printStackTrace();
////      throw ex;
////    }
////  }
////  /**
////   * Test teardown method
////   * @throws Exception Issue not expected during test teardown
////   */
////  @Override
////  @After
////  public void tearDown() throws Exception
////  {
////    try
////    {
////      this.getDao().removeListByAuctionId(this.getAuctionId());
////    }
////    catch(Exception ex)
////    {
////      ex.printStackTrace();
////      throw ex;
////    }
////    try
////    {
////      this.getAuctionDao().removeById(this.getAuctionId());
////    }
////    catch(Exception ex)
////    {
////      ex.printStackTrace();
////      throw ex;
////    }
////    super.tearDown();
////  }
////
////  /**
////   * Getter du DAO des enchères
////   * @return Le DAO des enchères
////   */
////  public BidDaoStub getDao()
////  {
////    return this.dao;
////  }
////  /**
////   * Getter du DAO des ventes aux enchères
////   * @return Le DAO des ventes aux enchères
////   */
////  public AuctionDaoStub getAuctionDao()
////  {
////    return this.auctionDao;
////  }
////  /**
////   * Getter de l'identifiant de la vente utilisée pour les tests
////   * @return L'identifiant de la vente utilisée pour les tests
////   */
////  public String getAuctionId()
////  {
////    return this.auctionId;
////  }
////  /**
////   * Setter de l'identifiant de la vente utilisée pour les tests
////   * @param auctionId Identifiant de la vente utilisée pour les tests
////   */
////  public void setAuctionId(String auctionId)
////  {
////    this.auctionId = auctionId;
////  }
//}