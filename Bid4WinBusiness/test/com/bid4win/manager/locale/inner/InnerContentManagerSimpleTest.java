package com.bid4win.manager.locale.inner;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
*
* TODO A COMMENTER<BR>
* <BR>
* @author Emeric Fillâtre
*/
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class InnerContentManagerSimpleTest extends InnerContentManagerTester
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("InnerContentManagerStub")
  private InnerContentManagerStub manager;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getManager()
   */
  @Override
  public InnerContentManagerStub getManager()
  {
    return this.manager;
  }
}
