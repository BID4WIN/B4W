package com.bid4win.persistence.usertype.locale.collection;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.collection.Bid4WinSetUserTypeTester;
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
public class LanguageSetUserTypeTest
       extends Bid4WinSetUserTypeTester<Language, LanguageSetUserType>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected LanguageSetUserType getUserType()
  {
    return new LanguageSetUserType();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected Bid4WinSet<Language> getType() throws Bid4WinException
  {
    Bid4WinSet<Language> set = new Bid4WinSet<Language>();
    set.add(Language.FRENCH);
    set.add(Language.ITALIAN);
    set.add(Language.ENGLISH);
    return set;
  }
}
