package com.bid4win.manager.image;

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
public class ImageManagerSimpleTest extends ImageManagerTester
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageManagerStub")
  private ImageManagerStub manager;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManagerTester#getManager()
   */
  @Override
  public ImageManagerStub getManager()
  {
    return this.manager;
  }
}
