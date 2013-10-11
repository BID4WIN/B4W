package com.bid4win.commons.j2ee;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.logging.aop.MethodTracer;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("Bid4WinSelfReferencedBeanStub")
@Scope("singleton")
public class Bid4WinSelfReferencedBeanStub
extends Bid4WinSelfReferencedBean<Bid4WinSelfReferencedBeanStub>
{
  /**
   *
   * TODO A COMMENTER
   */
  @MethodTracer
  public void proxiedMethod()
  {
    // TODO A COMMENTER
  }
}
