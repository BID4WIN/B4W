package com.bid4win.persistence.entity.account.preference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Classe de test des jeux de préférences d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class PreferenceBundleTest extends Bid4WinEntityTester
{
  /**
   * Test of PreferenceBundle(), of class Preference.
   */
  @Test
  public void testPreferenceBundle_0args()
  {
    PreferenceBundle preference = new PreferenceBundle();
    assertTrue("Wrong language", Language.DEFAULT == preference.getLanguage());
  }
  /**
   * Test of equalsInternal(PreferenceBundle), of class PreferenceBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testEqualsInternal_Credential() throws Bid4WinException
  {
    PreferenceBundle preference1 = new PreferenceBundle();
    PreferenceBundle preference2 = new PreferenceBundle();
    assertTrue(preference1.equalsInternal(preference2));
    assertTrue(preference2.equalsInternal(preference1));

    preference1.defineLanguage(Language.FRENCH);
    assertFalse(preference1.equalsInternal(preference2));
    assertFalse(preference2.equalsInternal(preference1));

    preference2.defineLanguage(Language.FRENCH);
    assertTrue(preference1.equalsInternal(preference2));
    assertTrue(preference2.equalsInternal(preference1));
  }
  /**
   * Test of defineLanguage(Language), of class PreferenceBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testDefineLanguage_Language() throws Bid4WinException
  {
    PreferenceBundle preference = new PreferenceBundle();
    assertTrue("Wrong language", Language.DEFAULT == preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH != preference.getLanguage());
    preference.defineLanguage(Language.FRENCH);
    assertTrue("Wrong language", Language.DEFAULT != preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH == preference.getLanguage());
    preference.defineLanguage(Language.ENGLISH);
    assertTrue("Wrong language", Language.ENGLISH == preference.getLanguage());
    try
    {
      preference.defineLanguage(null);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Test of importPreferences(PreferenceRoot), of class PreferenceBundle.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testImportPreferences_PreferenceRoot() throws Bid4WinException
  {
    PreferenceBundle preference = new PreferenceBundle();
    assertTrue("Wrong language", Language.DEFAULT == preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH != preference.getLanguage());

    PreferenceRoot root = new PreferenceRoot();
    preference.importPreferences(root);
    assertTrue("Wrong language", Language.DEFAULT == preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH != preference.getLanguage());

    new Preference(PreferenceBundle.KEY_LANGUAGE, Language.FRENCH.getCode(), root);
    preference.importPreferences(root);
    assertTrue("Wrong language", Language.DEFAULT != preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH == preference.getLanguage());

    preference.importPreferences(null);
    assertTrue("Wrong language", Language.DEFAULT != preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH == preference.getLanguage());

    root = new PreferenceRoot();
    new Preference(PreferenceBundle.KEY_LANGUAGE, Language.ENGLISH.getCode(), root);
    preference.importPreferences(root);
    assertTrue("Wrong language", Language.DEFAULT == preference.getLanguage());
    assertTrue("Wrong language", Language.FRENCH != preference.getLanguage());

    root = new PreferenceRoot();
    new Preference(PreferenceBundle.KEY_LANGUAGE, "Wrong code", root);
    try
    {
      preference.importPreferences(root);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong language", Language.DEFAULT == preference.getLanguage());
      assertTrue("Wrong language", Language.FRENCH != preference.getLanguage());
    }
  }
}
