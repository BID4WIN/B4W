package com.bid4win.commons.persistence.usertype.core;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinDateUserTypeWithTimeTest extends Bid4WinDateUserTypeTester
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.core.Bid4WinDateUserTypeTester#isTimeNeeded()
   */
  @Override
  protected boolean isTimeNeeded()
  {
    return true;
  }
}
