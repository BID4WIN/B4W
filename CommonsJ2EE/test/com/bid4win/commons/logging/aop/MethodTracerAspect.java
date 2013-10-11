package com.bid4win.commons.logging.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Aspect
@Component
public class MethodTracerAspect
{
  /**
   *
   * TODO A COMMENTER
   */
  @Pointcut("@annotation(com.bid4win.commons.logging.aop.MethodTracer)")
  public void methodTracer()
  {
    // Un pointcut est une méthode vide
  }
  /**
   *
   * TODO A COMMENTER
   * @param point TODO A COMMENTER
   */
  @Before("methodTracer()")
  public void methodInTracer(JoinPoint point)
  {
    this.methodTracer(point, "IN ");
  }
  /**
   *
   * TODO A COMMENTER
   * @param point TODO A COMMENTER
   */
  @After("methodTracer()")
  public void methodOutTracer(JoinPoint point)
  {
    this.methodTracer(point, "OUT");
  }
  /**
   *
   * TODO A COMMENTER
   * @param point TODO A COMMENTER
   * @param prefix TODO A COMMENTER
   */
  public void methodTracer(JoinPoint point, String prefix)
  {
    System.out.println(prefix + " " + point.getSignature().getName());
  }
}
