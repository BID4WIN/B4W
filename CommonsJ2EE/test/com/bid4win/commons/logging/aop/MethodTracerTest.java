package com.bid4win.commons.logging.aop;

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
public class MethodTracerTest
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("methodTracerStub")
  private MethodTracerStub stub;

  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void methodTest()
  {
    this.stub.methodTest0(0);
    this.stub.methodTest2(0);
  }
}
