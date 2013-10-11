package com.bid4win.commons.logging.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("methodTracerStub")
public class MethodTracerStub
{
  /** TODO A COMMENTER */
  @Autowired
  ApplicationContext context;

  /**
   *
   * TODO A COMMENTER
   * @param nb TODO A COMMENTER
   */
  @MethodTracer
  public void methodTest0(int nb)
  {
    String string = this.createTab(nb);
    System.out.println(string + ">> IN methodTest0()");
    System.out.println(string + "Before bean call");
    ((MethodTracerStub)this.context.getBean("methodTracerStub")).methodTest1(nb + 1);
    System.out.println(string + "After bean call");
    System.out.println(string + "Before direct call");
    this.methodTest1(nb + 1);
    System.out.println(string + "After direct call");
    System.out.println(string + ">> OUT methodTest0()");
  }
  /**
   *
   * TODO A COMMENTER
   * @param nb TODO A COMMENTER
   */
  @MethodTracer
  public void methodTest1(int nb)
  {
    String string = this.createTab(nb);
    System.out.println(string + ">> IN methodTest1()");
    System.out.println(string + "Before bean call");
    ((MethodTracerStub)this.context.getBean("methodTracerStub")).methodTest2(nb + 1);
    System.out.println(string + "After bean call");
    System.out.println(string + "Before direct call");
    this.methodTest2(nb + 1);
    System.out.println(string + "After direct call");
    System.out.println(string + ">> OUT methodTest1()");
  }
  /**
   *
   * TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String createTab(int nb)
  {
    String tab = "";
    for(int i = 0 ; i < nb ; i++)
    {
      tab += "   ";
    }
    return tab;
  }
  /**
   *
   * TODO A COMMENTER
   * @param nb TODO A COMMENTER
   */
  @MethodTracer
  public void methodTest2(int nb)
  {
    String string = this.createTab(nb);
    System.out.println(string + ">> IN methodTest2()");
    System.out.println(string + ">> OUT methodTest2()");
  }
}
