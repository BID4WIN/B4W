package com.bid4win.persistence.entity.locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractTester;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 * Classe de test de la racine des propriétés d'internationalisation<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class I18nRootTest
       extends PropertyRootAbstractTester<I18nRoot, I18n, Account, EntityGenerator>
{
  /**
  *
  * TODO A COMMENTER
  * @return {@inheritDoc}
  * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#createBase()
  */
  @Override
  protected I18nRoot createBase()
  {
    return new I18nRoot();
  }
  /**
  *
  * TODO A COMMENTER
  * @param key {@inheritDoc}
  * @param value {@inheritDoc}
  * @return {@inheritDoc}
  * @throws UserException {@inheritDoc}
  * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#createProperty(java.lang.String, java.lang.String)
  */
  @Override
  protected I18n createProperty(String key, String value) throws UserException
  {
    return new I18n(key, value);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#getBaseKey(int)
   */
  @Override
  protected String getBaseKey(int index)
  {
    return this.getGenerator().getLanguage(index).getCode();
  }


  /**
   * Test of I18nRoot(...) method, of class I18nRoot.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#testConstructor_etc()
   */
  @Override
  @Test
  public void testConstructor_etc() throws Bid4WinException
  {
    super.testConstructor_etc();

    I18nRoot root = new I18nRoot();
    assertEquals("Wrong ID", I18nRoot.UNIQUE_ID, root.getId().intValue());
    assertEquals("Wrong property nb", 1, root.getPropertyNb());
    I18n i18n = root.getProperty(Language.DEFAULT.getCode());
    assertNotNull("Wrong default language property", i18n);
    assertEquals("Wrong default language value", Language.DEFAULT.getName(), i18n.getValue());
  }
  /**
   * Test of addProperty(String, String), of class I18nRoot.
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#testAddProperty_String_String()
   */
  @Override
  @Test
  public void testAddProperty_String_String() throws Bid4WinException
  {
    super.testAddProperty_String_String();

    I18nRoot root = new I18nRoot();
    try
    {
      root.addProperty(Language.ITALIAN.getCode(), null);
      I18n i18n = root.getProperty(Language.ITALIAN.getCode());
      assertNotNull("Wrong language property", i18n);
      assertEquals("Wrong language value", Language.ITALIAN.getName(), i18n.getValue());
      String key = UtilProperty.computeKey(Language.ITALIAN.getCode(), "a", "b", "c");
      String value = "value c";
      root.addProperty(key, value);
      i18n = root.getProperty(key);
      assertNotNull("Wrong language property", i18n);
      assertEquals("Wrong property value", value, i18n.getValue());
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      String key = UtilProperty.computeKey(Language.SPANISH.getCode(), "a", "b", "c");
      String value = "value c";
      root.addProperty(key, value);
      fail("Should fail with language not already referenced");
    }
    catch(Bid4WinException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      String key = "toto";
      String value = "value c";
      root.addProperty(key, value);
      fail("Should fail with first level not being a language");
    }
    catch(Bid4WinException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
