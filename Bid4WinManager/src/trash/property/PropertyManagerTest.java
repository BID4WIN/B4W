//package trash.property;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.manager.exception.AuthenticationException;
//import com.bid4win.commons.manager.exception.AuthorizationException;
//import com.bid4win.commons.persistence.dao.account.IAccountAbstractDaoStub;
//import com.bid4win.commons.persistence.dao.property.IPropertyAbstractDaoStub;
//import com.bid4win.manager.property.PropertyManager;
//import com.bid4win.persistence.dao.account.AccountDaoStub;
//import com.bid4win.persistence.dao.property.PropertyDaoStub;
//import com.bid4win.persistence.dao.property.PropertyRootDaoStub;
//import com.bid4win.persistence.entity.EntityGenerator;
//import com.bid4win.persistence.entity.account.Account;
//import com.bid4win.persistence.entity.property.Property;
//import com.bid4win.persistence.entity.property.PropertyRoot;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:META-INF/config/spring-test.xml")
//public class PropertyManagerTest
//       extends PropertyAbstractManagerTester<Property, PropertyRoot, Account>
//{
//  /** Référence du manager à tester */
//  @Autowired
//  @Qualifier("PropertyManager")
//  private PropertyManager manager;
//  /** Référence du DAO de test des propriétés racines */
//  @Autowired
//  @Qualifier("PropertyRootDaoStub")
//  private PropertyRootDaoStub dao;
//  /** Référence du DAO de test des propriétés */
//  @Autowired
//  @Qualifier("PropertyDaoStub")
//  private PropertyDaoStub propertyDao;
//  /** Référence du DAO de test des comptes utilisateurs */
//  @Autowired
//  @Qualifier("AccountDaoStub")
//  private AccountDaoStub accountDao;
//
//  /**
//   * Test of addProperty(String, String), of class PropertyManager.
//   * @throws Bid4WinException Issue not expected during this test
//   */
//  @Test
//  public void testAddProperty_String_String() throws Bid4WinException
//  {
//    System.out.println("### addProperty(String, String)");
//    PropertyRoot root = this.getDao().getRoot();
//    assertEquals("Wrong root version", 0, root.getVersion());
//    assertEquals("Wrong root property set", 0, root.getPropertyNb());
//    // Test sans utilisateur connecté
//    try
//    {
//      this.getManager().addProperty("a", "a value");
//      fail("Should fail without any connected account");
//    }
//    catch(AuthenticationException ex)
//    {
//      ex.printStackTrace();
//      root = this.getDao().getRoot();
//      assertEquals("Wrong root version", 0, root.getVersion());
//      assertEquals("Wrong root property set", 0, root.getPropertyNb());
//    }
//    catch(Bid4WinException ex)
//    {
//      ex.printStackTrace();
//      fail("Wrong error :" + ex.getMessage());
//    }
//    this.connectAccount(0);
//    // Test avec un utilisateur connecté aux habilitations insuffisantes
//    try
//    {
//      this.getManager().addProperty("a", "a value");
//      fail("Should fail connected account not being " + this.getManager().getAdminRole());
//    }
//    catch(AuthorizationException ex)
//    {
//      ex.printStackTrace();
//      root = this.getDao().getRoot();
//      assertEquals("Wrong root version", 0, root.getVersion());
//      assertEquals("Wrong root property set", 0, root.getPropertyNb());
//    }
//    catch(Bid4WinException ex)
//    {
//      ex.printStackTrace();
//      fail("Wrong error :" + ex.getMessage());
//    }
//    this.updateRole(0, this.getManager().getAdminRole());
//    // Test avec un utilisateur connecté aux habilitations suffisantes
//    Property a = this.getManager().addProperty("a", "a value");
//    root = this.getDao().getRoot();
//    assertEquals("Wrong root version", 1, root.getVersion());
//    assertEquals("Wrong root property set", 1, root.getPropertyNb());
//    Property result_a = root.getProperty(a.getKey());
//    assertNotNull("Wrong root property", result_a);
//    assertEquals("Wrong result id", a.getId(), result_a.getId());
//    assertEquals("Wrong result version", 0, result_a.getVersion());
//    assertEquals("Wrong result key", "a", result_a.getKey());
//    assertEquals("Wrong result value", "a value", result_a.getValue());
//
//    Property b = this.getManager().addProperty("a.b.c", "c value");
//    root = this.getDao().getRoot();
//    assertEquals("Wrong root version", 1, root.getVersion());
//    assertEquals("Wrong root property set", 1, root.getPropertyNb());
//    result_a = root.getProperty(a.getKey());
//    assertNotNull("Wrong root property", result_a);
//    assertEquals("Wrong result id", a.getId(), result_a.getId());
//    assertEquals("Wrong result version", 1, result_a.getVersion());
//    assertEquals("Wrong result key", "a", result_a.getKey());
//    assertEquals("Wrong result value", "a value", result_a.getValue());
//    Property result_b = result_a.getProperty(b.getKey());
//    assertNotNull("Wrong root property", result_b);
//    assertEquals("Wrong result id", b.getId(), result_b.getId());
//    assertEquals("Wrong result version", 0, result_b.getVersion());
//    assertEquals("Wrong result key", "b", result_b.getKey());
//    assertEquals("Wrong result value", "", result_b.getValue());
//    Property result_c = result_b.getProperty("c");
//    assertNotNull("Wrong root property", result_c);
//    assertTrue("Wrong result id", 0 < result_c.getId());
//    assertEquals("Wrong result version", 0, result_c.getVersion());
//    assertEquals("Wrong result key", "c", result_c.getKey());
//    assertEquals("Wrong result value", "c value", result_c.getValue());
//    // Test d'ajout d'une propriété déjà référencée
//    try
//    {
//      this.getManager().addProperty("a.b", "b value");
//      fail("Addition of already existing key should have failed");
//    }
//    catch(UserException ex)
//    {
//      ex.printStackTrace();
//      root = this.getDao().getRoot();
//      assertEquals("Wrong root version", 1, root.getVersion());
//      assertEquals("Wrong root property set", 1, root.getPropertyNb());
//    }
//    System.out.println("### addProperty(String, String)");
//  }
//
//  /**
//   * Test of updateKey(String, String), of class PropertyManager.
//   * @throws Bid4WinException Issue not expected during this test
//   */
//  @Test
//  public void testUpdateKey_String_String() throws Bid4WinException
//  {
//    System.out.println("### updateKey(String, String)");
//    PropertyRoot root = this.getDao().getRoot();
//    assertEquals("Wrong root version", 0, root.getVersion());
//    assertEquals("Wrong root property set", 0, root.getPropertyNb());
//    Property a = this.addProperty("a.b.c", "c value");
//    Property d = this.addProperty("a.b.c.d", "d value");
//    assertEquals("Wrong root version", 1, root.getVersion());
//    assertEquals("Wrong root property set", 1, root.getPropertyNb());
//    // Test sans utilisateur connecté
//    try
//    {
//      this.getManager().updateKey("a.b.c", "a.b.d");
//      fail("Should fail without any connected account");
//    }
//    catch(AuthenticationException ex)
//    {
//      ex.printStackTrace();
//      root = this.getDao().getRoot();
//      assertEquals("Wrong root version", 1, root.getVersion());
//      assertEquals("Wrong root property set", 1, root.getPropertyNb());
//    }
//    catch(Bid4WinException ex)
//    {
//      ex.printStackTrace();
//      fail("Wrong error :" + ex.getMessage());
//    }
//    this.connectAccount(0);
//    // Test avec un utilisateur connecté aux habilitations insuffisantes
//    try
//    {
//      this.getManager().updateKey("a.b.c", "a.b.d");
//      fail("Should fail connected account not being " + this.getManager().getAdminRole());
//    }
//    catch(AuthorizationException ex)
//    {
//      ex.printStackTrace();
//      root = this.getDao().getRoot();
//      assertEquals("Wrong root version", 1, root.getVersion());
//      assertEquals("Wrong root property set", 1, root.getPropertyNb());
//    }
//    catch(Bid4WinException ex)
//    {
//      ex.printStackTrace();
//      fail("Wrong error :" + ex.getMessage());
//    }
//    this.updateRole(0, this.getManager().getAdminRole());
//    // Test avec un utilisateur connecté aux habilitations suffisantes
//    Property result_c = this.getManager().updateKey("a.b.c", "a.b.d");
//    assertEquals("Wrong result version", 2, result_c.getVersion());
//    assertEquals("Wrong property size", 1, result_c.getPropertyNb());
//    assertEquals("Wrong result key", "d", result_c.getKey());
//    Property result_b = this.getManager().getProperties("a.b");
//    assertEquals("Wrong result version", 1, result_b.getVersion());
//    assertEquals("Wrong property size", 1, result_b.getPropertyNb());
//
//    result_c = this.getManager().updateKey("a.b.d", "d");
//    assertEquals("Wrong result version", 3, result_c.getVersion());
//    assertEquals("Wrong property size", 1, result_c.getPropertyNb());
//    assertEquals("Wrong result key", "d", result_c.getKey());
//    result_b = this.getManager().getProperties("a.b");
//    assertEquals("Wrong result version", 2, result_b.getVersion());
//    assertEquals("Wrong property size", 0, result_b.getPropertyNb());
//    root = this.getDao().getRoot();
//    assertEquals("Wrong result version", 2, root.getVersion());
//    assertEquals("Wrong property size", 2, root.getPropertyNb());
//
//    result_c = this.getManager().updateKey("d", "a.b.c.d");
//    assertEquals("Wrong result version", 4, result_c.getVersion());
//    assertEquals("Wrong property size", 1, result_c.getPropertyNb());
//    assertEquals("Wrong result key", "d", result_c.getKey());
//    root = this.getDao().getRoot();
//    assertEquals("Wrong result version", 3, root.getVersion());
//    assertEquals("Wrong property size", 1, root.getPropertyNb());
//    result_b = this.getManager().getProperties("a.b");
//    assertEquals("Wrong result version", 3, result_b.getVersion());
//    assertEquals("Wrong property size", 1, result_b.getPropertyNb());
//    result_b = this.getManager().getProperties("a.b.c");
//    assertEquals("Wrong result version", 0, result_b.getVersion());
//    assertEquals("Wrong property size", 1, result_b.getPropertyNb());
//
//    try
//    {
//      this.getManager().updateKey("a.b.c.d", "a.b");
//      fail("Should fail with existing key");
//    }
//    catch(UserException ex)
//    {
//      ex.printStackTrace();
//    }
//    System.out.println("### updateKey(String, String)");
//  }
//
//  /**
//   * Getter du DAO de test des propriétés racines
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTest#getDao()
//   */
//  @Override
//  public PropertyRootDaoStub getDao()
//  {
//    return this.dao;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.PropertyAbstractManagerTester#getPropertyDao()
//   */
//  @Override
//  protected IPropertyAbstractDaoStub<Property, PropertyRoot> getPropertyDao()
//  {
//    return this.propertyDao;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractBasedTester#getAccountDao()
//   */
//  @Override
//  protected IAccountAbstractDaoStub<Account> getAccountDao()
//  {
//    return this.accountDao;
//  }
//  /**
//   * Getter du manager des propriétés
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.PropertyAbstractManagerTester#getManager()
//   */
//  @Override
//  public PropertyManager getManager()
//  {
//    return this.manager;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractBasedTester#getGenerator()
//   */
//  @Override
//  protected EntityGenerator getGenerator()
//  {
//    return EntityGenerator.getInstance();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param key {@inheritDoc}
//   * @param value {@inheritDoc}
//   * @param root {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws Bid4WinException {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.PropertyAbstractManagerTester#createProperty(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.property.PropertyRootAbstract)
//   */
//  @Override
//  protected Property createProperty(String key, String value, PropertyRoot root)
//            throws Bid4WinException
//  {
//    return new Property(key, value, root);
//  }
//}
