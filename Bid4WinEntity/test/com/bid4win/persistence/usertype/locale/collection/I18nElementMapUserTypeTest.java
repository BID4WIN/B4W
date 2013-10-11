package com.bid4win.persistence.usertype.locale.collection;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.collection.Bid4WinMapUserTypeTester;
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
public class I18nElementMapUserTypeTest
       extends Bid4WinMapUserTypeTester<Language, I18nElement, I18nElementMapUserType>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected I18nElementMapUserType getUserType()
  {
    return new I18nElementMapUserType();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected Bid4WinMap<Language, I18nElement> getType() throws Bid4WinException
  {
    Bid4WinMap<Language, I18nElement> map = new Bid4WinMap<Language, I18nElement>();
    int i = 0;
    map.put(Language.FRENCH, new I18nElement(Language.FRENCH, "test" + i++));
    map.put(Language.ITALIAN, new I18nElement(Language.ITALIAN, "test" + i++));
    map.put(Language.ENGLISH, new I18nElement(Language.ENGLISH, "test" + i++));
    return map;
  }
}
