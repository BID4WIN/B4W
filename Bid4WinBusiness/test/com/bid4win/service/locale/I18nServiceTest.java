package com.bid4win.service.locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.commons.service.property.PropertyAbstractServiceTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.locale.I18nDaoStub;
import com.bid4win.persistence.dao.locale.I18nRootDaoStub;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class I18nServiceTest
       extends PropertyAbstractServiceTester<I18n, I18nRoot, SessionData, Account, EntityGenerator>
{
  /** Référence du service à tester */
  @Autowired
  @Qualifier("I18nService")
  private I18nService service;
  /** Référence du DAO de test des propriétés racines */
  @Autowired
  @Qualifier("I18nRootDaoStub")
  private I18nRootDaoStub dao;
  /** Référence du DAO de test des propriétés */
  @Autowired
  @Qualifier("I18nDaoStub")
  private I18nDaoStub propertyDao;

  /**
   * Test of addLanguage(String), of class I18nManager.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddLanguage_String() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("addLanguage", String.class);

    // Création de la situation de départ
    I18n a1 = this.addProperty("a1.b1.c", "a1.b1.c");
    this.addProperty("a1.b2.c", "a1.b2.c");
    a1 = this.getProperty(a1.getFullKey());
    I18n a2 = this.addProperty("a2.b1.c", "a2.b1.c");
    I18nRoot root = this.getDao().getRoot();
    assertEquals("Wrong root version", 3, root.getVersion());
    assertEquals("Wrong root property set", 1, root.getPropertyNb());

    I18n fr = this.getService().addLanguage(Language.FRENCH.getCode());
    root = this.getDao().getRoot();
    assertEquals("Wrong root version", 4, root.getVersion());
    assertEquals("Wrong root property set", 2, root.getPropertyNb());
    I18n result_fr = root.getProperty(fr.getKey());
    assertNotNull("Wrong root property", result_fr);
    assertEquals("Wrong result id", fr.getId(), result_fr.getId());
    assertEquals("Wrong result version", 0, result_fr.getVersion());
    assertEquals("Wrong result key", "fr", result_fr.getKey());
    assertEquals("Wrong result value", Language.FRENCH.getName(), result_fr.getValue());

    assertEquals("Wrong i81n nb", 2, result_fr.getPropertyNb());

    I18n result_a1 = result_fr.getProperty(a1.getKey());
    assertNotNull("Wrong i18n", result_a1);
    this.comparePropertyForNewLanguage(a1, result_a1);

    I18n result_a2 = result_fr.getProperty(a2.getKey());
    assertNotNull("Wrong i18n", result_a2);
    this.comparePropertyForNewLanguage(a2, result_a2);

    // Test d'ajout d'une langue déjà référencée
    try
    {
      this.getService().addLanguage(Language.FRENCH.getCode());
      fail("Addition of already existing language should have failed");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param from TODO A COMMENTER
   * @param to TODO A COMMENTER
   */
  protected void comparePropertyForNewLanguage(I18n from, I18n to)
  {
    assertEquals("Wrong value", UtilString.trimNotNull(I18n.TODO + from.getValue()), to.getValue());
    assertEquals("Wrong properties nb", from.getPropertyNb(), to.getPropertyNb());
    for(I18n i18nFrom : from.getPropertySet())
    {
      I18n i18nTo = to.getProperty(i18nFrom.getKey());
      assertNotNull("Wrong i18n", i18nTo);
      this.comparePropertyForNewLanguage(i18nFrom, i18nTo);
    }
  }

  /**
   * Test of addI18n(String), of class I18nManager.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddI18n_String() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("addI18n", String.class);

    // Création de la situation de départ
    this.getPropertyDao().add(new I18n(Language.FRENCH.getCode(), Language.FRENCH.getName(), this.getRoot()));
    I18nRoot root = this.getDao().getRoot();
    assertEquals("Wrong root version", 1, root.getVersion());
    assertEquals("Wrong root property set", 2, root.getPropertyNb());

    this.getService().addI18n("a.b.c");
    root = this.getDao().getRoot();
    assertEquals("Wrong root version", 2, root.getVersion());
    assertEquals("Wrong root property set", 2, root.getPropertyNb());
    assertEquals("Wrong language version", 1, root.getProperty(Language.DEFAULT.getCode()).getVersion());
    assertEquals("Wrong language version", 1, root.getProperty(Language.FRENCH.getCode()).getVersion());
    I18n result = this.getService().getI18n(Language.DEFAULT, "a.b.c");
    assertNotNull("Wrong result", result);
    result = this.getService().getI18n(Language.FRENCH, "a.b.c");
    assertNotNull("Wrong result", result);
  }
  /**
   * Test of removeI18n(String), of class I18nManager.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemoveI18n_String() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("removeI18n", String.class);

    // Création de la situation de départ
    this.getService().addLanguage(Language.FRENCH.getCode());
    I18nRoot root = this.getDao().getRoot();
    assertEquals("Wrong root version", 1, root.getVersion());
    assertEquals("Wrong root property set", 2, root.getPropertyNb());
    this.getService().addI18n("a.b.c");

    this.getService().removeI18n("a.b.c");
    root = this.getDao().getRoot();
    assertEquals("Wrong root version", 3, root.getVersion());
    assertEquals("Wrong root property set", 2, root.getPropertyNb());

    I18n result = this.getProperty(UtilProperty.addKey(Language.DEFAULT.getCode(),
                                                       "a.b"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 0, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.FRENCH.getCode(), "a.b"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 0, result.getPropertyNb());
    // Test de suppression avec une propriété non vide
    try
    {
      this.getService().removeI18n("a");
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Test de retrait de la propriété parent
    this.getService().removeI18n("a.b");
    result = this.getProperty(UtilProperty.addKey(Language.DEFAULT.getCode(), "a"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 0, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.FRENCH.getCode(), "a"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 0, result.getPropertyNb());

    this.getService().removeI18n("a");
    assertEquals("Language should be empty", 0,
                 this.getRoot().getProperty(Language.DEFAULT.getCode()).getPropertyNb());
    assertEquals("Language should be empty", 0,
                 this.getRoot().getProperty(Language.FRENCH.getCode()).getPropertyNb());
  }
  /**
   * Test of updateKey(String, String), of class I18nManager.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdateKey_String_String() throws Bid4WinException
  {
    // Validation de la vérification des droits administrateur
    this.checkAdminRestriction("updateKey", String.class, String.class);

    // Création de la situation de départ
    this.getService().addLanguage(Language.FRENCH.getCode());
    I18nRoot root = this.getDao().getRoot();
    assertEquals("Wrong root version", 1, root.getVersion());
    assertEquals("Wrong root property set", 2, root.getPropertyNb());
    this.getService().addI18n("a.b.c");

    this.getService().updateKey("a.b", "d");
    assertEquals("Wrong size", 2,
        this.getRoot().getProperty(Language.DEFAULT.getCode()).getPropertyNb());
    assertEquals("Wrong version", 2,
                 this.getProperty(Language.DEFAULT.getCode()).getVersion());
    assertEquals("Wrong size", 2,
        this.getRoot().getProperty(Language.FRENCH.getCode()).getPropertyNb());
    assertEquals("Wrong version", 2,
                 this.getProperty(Language.FRENCH.getCode()).getVersion());
    I18n result = this.getProperty(UtilProperty.addKey(Language.DEFAULT.getCode(),
                                                       "a"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 0, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.FRENCH.getCode(), "a"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 0, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.DEFAULT.getCode(), "d"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 1, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.FRENCH.getCode(), "d"));
    assertEquals("Wrong version", 1, result.getVersion());
    assertEquals("Wrong result", 1, result.getPropertyNb());

    this.getService().updateKey("d", "a.b.c");
    assertEquals("Wrong size", 1,
        this.getRoot().getProperty(Language.DEFAULT.getCode()).getPropertyNb());
    assertEquals("Wrong version", 3,
                 this.getProperty(Language.DEFAULT.getCode()).getVersion());
    assertEquals("Wrong size", 1,
        this.getRoot().getProperty(Language.FRENCH.getCode()).getPropertyNb());
    assertEquals("Wrong version", 3,
                 this.getProperty(Language.FRENCH.getCode()).getVersion());
    result = this.getProperty(UtilProperty.addKey(Language.DEFAULT.getCode(), "a"));
    assertEquals("Wrong version", 2, result.getVersion());
    assertEquals("Wrong result", 1, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.FRENCH.getCode(), "a"));
    assertEquals("Wrong version", 2, result.getVersion());
    assertEquals("Wrong result", 1, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.DEFAULT.getCode(), "a.b.c"));
    assertEquals("Wrong version", 2, result.getVersion());
    assertEquals("Wrong result", 1, result.getPropertyNb());
    result = this.getProperty(UtilProperty.addKey(Language.FRENCH.getCode(), "a.b.c"));
    assertEquals("Wrong version", 2, result.getVersion());
    assertEquals("Wrong result", 1, result.getPropertyNb());
  }

  /**
   * Test of import(InputStream), of class I18nManager.
   * @throws Exception Issue not expected during this test
   */
/*  @Test
  public void testImport_InputStream() throws Exception
  {
    System.out.println("### import(InputStream)");
    File file = new File("./test/com/bid4win/manager/locale/i18n.test1");
    this.getManager().importI18n(new FileInputStream(file));

    file = new File("./test/com/bid4win/manager/locale/i18n.test2");
    this.getManager().importI18n(new FileInputStream(file));

    System.out.println("### import(InputStream)");
  }*/

  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#addProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected I18n addProperty(String key, String value) throws Bid4WinException
  {
    I18n i18n;
    try
    {
      i18n = this.getProperty(Language.DEFAULT.getCode());
    }
    catch(UserException ex)
    {
      i18n = super.addProperty(Language.DEFAULT.getCode(), Language.DEFAULT.getName());
    }
    return this.getPropertyDao().add(i18n.addProperty(key, value));
  }

  /**
   * Getter du DAO de test des propriétés racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  public I18nRootDaoStub getDao()
  {
    return this.dao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getPropertyDao()
   */
  @Override
  protected I18nDaoStub getPropertyDao()
  {
    return this.propertyDao;
  }
  /**
   * Getter du manager des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getService()
   */
  @Override
  public I18nService getService()
  {
    return this.service;
  }
}
