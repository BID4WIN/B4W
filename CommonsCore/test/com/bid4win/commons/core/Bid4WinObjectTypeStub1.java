package com.bid4win.commons.core;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectTypeStub1 extends Bid4WinObjectType<Bid4WinObjectTypeStub1>
{
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE1 = new Bid4WinObjectTypeStub1("TYPE1");
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE2 = new Bid4WinObjectTypeStub1("TYPE2");
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE3 = new Bid4WinObjectTypeStub1("TYPE3");
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE1_1 = new Bid4WinObjectTypeStub1("TYPE1_1", TYPE1);
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE1_2 = new Bid4WinObjectTypeStub1("TYPE1_2", TYPE1);
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE4 = new Bid4WinObjectTypeStub1("TYPE4");
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE2_1 = new Bid4WinObjectTypeStub1("TYPE2_1", TYPE2);
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE1_2_1 = new Bid4WinObjectTypeStub1("TYPE1_2_1", TYPE1_2);
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE5 = new Bid4WinObjectTypeStub1("TYPE5");
  /** TODO A COMMENTER */
  public final static Bid4WinObjectTypeStub1 TYPE1_2_2 = new Bid4WinObjectTypeStub1("TYPE1_2_2", TYPE1_2);

  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 5384012778017257326L;
  /**
   *
   * TODO A COMMENTER
   * @param code TODO A COMMENTER
   */
  protected Bid4WinObjectTypeStub1(String code)
  {
    super(code);
  }
  /**
   *
   * TODO A COMMENTER
   * @param code TODO A COMMENTER
   * @param parent TODO A COMMENTER
   */
  protected Bid4WinObjectTypeStub1(String code, Bid4WinObjectTypeStub1 parent)
  {
    super(code, parent);
  }
}
