package com.bid4win.commons.core;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectTypeGroup;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectTypeGroupStub extends Bid4WinObjectTypeGroup<Bid4WinObjectTypeGroupStub>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 7028377409355035535L;
  /**
   *
   * TODO A COMMENTER
   * @param code TODO A COMMENTER
   */
  protected Bid4WinObjectTypeGroupStub(String code)
  {
    super(code);
  }
  /**
   *
   * TODO A COMMENTER
   * @param code TODO A COMMENTER
   * @param parents TODO A COMMENTER
   */
  protected Bid4WinObjectTypeGroupStub(String code, Bid4WinObjectTypeGroupStub ... parents)
  {
    super(code, parents);
  }
}
