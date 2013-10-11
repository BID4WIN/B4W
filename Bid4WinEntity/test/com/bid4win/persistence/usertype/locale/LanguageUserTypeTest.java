package com.bid4win.persistence.usertype.locale;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.locale.Language;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class LanguageUserTypeTest
       extends Bid4WinStringUserTypeTester<Language, LanguageUserType>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected LanguageUserType getUserType()
  {
    return new LanguageUserType();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected Language getType()
  {
    return Language.ITALIAN;
  }
}
