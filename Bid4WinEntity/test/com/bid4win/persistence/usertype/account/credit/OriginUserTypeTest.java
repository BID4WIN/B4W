package com.bid4win.persistence.usertype.account.credit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.account.credit.Origin;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class OriginUserTypeTest
       extends Bid4WinStringUserTypeTester<Origin, OriginUserType>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getUserType()
   */
  @Override
  protected OriginUserType getUserType()
  {
    return new OriginUserType();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester#getType()
   */
  @Override
  protected Origin getType()
  {
    return Origin.REFUND;
  }
}
