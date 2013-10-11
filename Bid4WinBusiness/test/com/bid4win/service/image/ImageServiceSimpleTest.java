package com.bid4win.service.image;

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
public class ImageServiceSimpleTest extends ImageServiceTester
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageService")
  private ImageService service;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceRepositoryServiceTester#getService()
   */
  @Override
  public ImageService getService()
  {
    return this.service;
  }
}
