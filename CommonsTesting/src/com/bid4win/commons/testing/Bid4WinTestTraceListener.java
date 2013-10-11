package com.bid4win.commons.testing;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinTestTraceListener extends RunListener
{
  /** TODO A COMMENTER */
  private String testedClass = "";

  /**
   *
   * TODO A COMMENTER
   */
  public Bid4WinTestTraceListener()
  {
    super();
  }
  /**
   *
   * TODO A COMMENTER
   * @param testedClass TODO A COMMENTER
   */
  protected Bid4WinTestTraceListener(String testedClass)
  {
    this.setTestedClass(testedClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @param description {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see org.junit.runner.notification.RunListener#testStarted(org.junit.runner.Description)
   */
  @Override
  public void testStarted(Description description) throws Exception
  {
    this.traceClassTestBefore(description);
    this.traceTestBefore();
    // Affiche la méthode testée
    this.traceTestedMethod(description);
    super.testStarted(description);
  }

  /**
   *
   * TODO A COMMENTER
   * @param description {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see org.junit.runner.notification.RunListener#testFinished(org.junit.runner.Description)
   */
  @Override
  public void testFinished(Description description) throws Exception
  {
    super.testFinished(description);
    // Affiche la méthode testée
    this.traceTestedMethod(description);
  }

  /**
   *
   * TODO A COMMENTER
   * @param description TODO A COMMENTER
   */
  private void traceClassTestBefore(Description description)
  {
    String testClass = this.getTestClass(description);
    String testedClass = this.getTestedClass(testClass);
    if(!this.getTestedClass().equals(testedClass))
    {
        System.out.println();
        System.out.println("###########################################################################");
        System.out.println("##### " + testClass);
        this.setTestedClass(testedClass);
    }
  }
  /**
   *
   * TODO A COMMENTER
   */
  private void traceTestBefore()
  {
    System.out.println();
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
  }

  /**
   *
   * TODO A COMMENTER
   * @param description TODO A COMMENTER
   */
  private void traceTestedMethod(Description description)
  {
    System.out.println("@@@@@ " + this.getTestedMethod(description));
  }
  /**
   *
   * TODO A COMMENTER
   * @param description TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String getTestedMethod(Description description)
  {
    String testName = this.getTestName(description);
    String[] definition = testName.split("_");
    StringBuffer trace = new StringBuffer();
    // Ajoute le nom de la méthode testée
    if(definition.length != 0)
    {
      trace.append(this.getTestedMethodName(definition[0]));
    }
    trace.append("(");
    // Ajoute les arguments de la méthode testée
    for(int i = 1 ; i < definition.length ; i++)
    {
      if(definition[i].equals("etc"))
      {
        trace.append("...");
      }
      else if(!definition[i].equals("0args"))
      {
        if(i != 1)
        {
          trace.append(", ");
        }
        trace.append(this.getTestedArgument(definition[i]));
      }
    }
    return trace.append(")").toString();
  }
  /**
   *
   * TODO A COMMENTER
   * @param testMethodName TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String getTestedMethodName(String testMethodName)
  {
    if(testMethodName.startsWith("test"))
    {
      if(testMethodName.length() != "test".length())
      {
        testMethodName = testMethodName.substring("test".length());
      }
      else
      {
        return "";
      }
    }
    if(testMethodName.equals("Constructor"))
    {
      testMethodName = this.getTestedClassName();
    }
    else if(!testMethodName.equals(this.getTestedClassName()) &&
            testMethodName.length() > 0)
    {
       String temp = testMethodName;
       testMethodName = testMethodName.substring(0, 1).toLowerCase();
       if(temp.length() > 1)
       {
         testMethodName += temp.substring(1);
       }
    }
    return testMethodName;
  }

  /**
   *
   * TODO A COMMENTER
   * @param argument TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private StringBuffer getTestedArgument(String argument)
  {
    if(argument.equals("CLASS"))
    {
      argument = this.getTestedClassName();
    }
    if(argument.startsWith("And"))
    {
      return this.getTestedArgument(argument.substring("And".length())).append(", ");
    }
    if(argument.startsWith("ArrayOf"))
    {
      return this.getTestedArgument(argument.substring("ArrayOf".length())).append("[]");
    }
    if(argument.startsWith("CollectionOf"))
    {
      return new StringBuffer("Collection<").append(
          this.getTestedArgument(argument.substring("CollectionOf".length()))).append(">");
    }
    if(argument.startsWith("SetOf"))
    {
      return new StringBuffer("Set<").append(
          this.getTestedArgument(argument.substring("SetOf".length()))).append(">");
    }
    if(argument.startsWith("ListOf"))
    {
      return new StringBuffer("List<").append(
          this.getTestedArgument(argument.substring("ListOf".length()))).append(">");
    }
    if(argument.startsWith("MapOf"))
    {
      return new StringBuffer("Map<").append(
          this.getTestedArgument(argument.substring("MapOf".length()))).append(">");
    }
    if(argument.startsWith("Bid4WinCollectionOf"))
    {
      return new StringBuffer("Bid4WinCollection<").append(
          this.getTestedArgument(argument.substring("Bid4WinCollectionOf".length()))).append(">");
    }
    if(argument.startsWith("Bid4WinSetOf"))
    {
      return new StringBuffer("Bid4WinSet<").append(
          this.getTestedArgument(argument.substring("Bid4WinSetOf".length()))).append(">");
    }
    if(argument.startsWith("Bid4WinListOf"))
    {
      return new StringBuffer("Bid4WinList<").append(
          this.getTestedArgument(argument.substring("Bid4WinListOf".length()))).append(">");
    }
    if(argument.startsWith("Bid4WinMapOf"))
    {
      return new StringBuffer("Bid4WinMap<").append(
          this.getTestedArgument(argument.substring("Bid4WinMapOf".length()))).append(">");
    }
    return new StringBuffer(argument);
  }


  /**
   *
   * TODO A COMMENTER
   * @param description TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String getTestClass(Description description)
  {
    String displayName = description.getDisplayName();
    return displayName.substring(displayName.indexOf("(") + 1, displayName.indexOf(")"));
  }
  /**
   *
   * TODO A COMMENTER
   * @param description TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String getTestName(Description description)
  {
    String displayName = description.getDisplayName();
    return displayName.substring(0, displayName.indexOf("("));
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String getTestedClass(String testClass)
  {
    if(testClass.endsWith("Test"))
    {
      return testClass.substring(0, testClass.length() - 4);
    }
    return testClass;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String getTestedClass()
  {
    return this.testedClass;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private String getTestedClassName()
  {
    String testedClass = this.getTestedClass();
    return testedClass.substring(testedClass.lastIndexOf(".") + 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param testedClass TODO A COMMENTER
   */
  private void setTestedClass(String testedClass)
  {
    this.testedClass = testedClass;
  }
}
