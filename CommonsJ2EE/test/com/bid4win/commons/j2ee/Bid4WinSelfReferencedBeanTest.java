package com.bid4win.commons.j2ee;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinSelfReferencedBeanTest
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("Bid4WinSelfReferencedBeanStub")
  private Bid4WinSelfReferencedBeanStub bean;

  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void testInjection()
  {
    assertTrue("Wrong self reference", this.bean == this.bean.self());
    this.bean.self().proxiedMethod();
  }
}
