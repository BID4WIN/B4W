package com.bid4win.persistence.usertype.locale;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.locale.I18nElement;
import com.bid4win.persistence.entity.locale.Language;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class I18nElementUserTypeTest
       extends Bid4WinStringUserTypeTester<I18nElement, I18nElementUserType>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected I18nElementUserType getUserType()
  {
    return new I18nElementUserType();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected I18nElement getType() throws Bid4WinException
  {
    return new I18nElement(Language.ITALIAN, "test value");
  }
}
