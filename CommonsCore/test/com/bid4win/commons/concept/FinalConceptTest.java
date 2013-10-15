package com.bid4win.commons.concept;

import org.junit.Test;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FinalConceptTest
{
  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void test()
  {
    Concept arg = new Concept("arg");
    @SuppressWarnings("unused")
    Concept result = this.finalReturn(arg);

    int i = 12;
    result = this.simpleArg(arg, i);

    arg = new Concept("arg");
    result = this.finalArg(arg);

    arg = new Concept("arg");
    result = this.finalParam(arg);

    arg = new Concept("arg");
    result = this.finalArgAndParam(arg);
  }

  /**
   *
   * TODO A COMMENTER
   * @param arg TODO A COMMENTER
   * @param i TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  @SuppressWarnings("unused")
  public Concept simpleArg(Concept arg, int i)
  {
    i += 1;
    arg.setValue(arg.getValue() + "simple1");
    arg = new Concept(arg.getValue());
    arg.setValue(arg.getValue() + "simple2");
    return arg;
  }
  /**
   *
   * TODO A COMMENTER
   * @param arg TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public final Concept finalReturn(Concept arg)
  {
    arg.setValue(arg.getValue() + "simple");
    return arg;
  }

  /**
   *
   * TODO A COMMENTER
   * @param arg TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Concept finalArg(final Concept arg)
  {
    arg.setValue(arg.getValue() + "simple");
    return arg;
  }
  /**
   *
   * TODO A COMMENTER
   * @param arg TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Concept finalParam(Concept arg)
  {
    final Concept param = arg;
    param.setValue(param.getValue() + "final");
    return param;
  }
  /**
   *
   * TODO A COMMENTER
   * @param arg TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Concept finalArgAndParam(final Concept arg)
  {
    final Concept param = arg;
    param.setValue(param.getValue() + "final");
    return param;
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Concept
  {
    /**  TODO A COMMENTER */
    private String value;

    /**
     *
     * TODO A COMMENTER
     * @param value TODO A COMMENTER
     */
    public Concept(String value)
    {
      this.setValue(value);
    }
    /**
     *
     * TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    public String getValue()
    {
      return this.value;
    }
    /**
     *
     * TODO A COMMENTER
     * @param value TODO A COMMENTER
     */
    public void setValue(String value)
    {
      this.value = value;
    }
  }
}
