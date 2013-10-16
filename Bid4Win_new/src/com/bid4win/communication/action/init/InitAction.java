package com.bid4win.communication.action.init;

import org.apache.struts2.convention.annotation.Action;

import com.bid4win.communication.action.tiles.TilesAction;

/**
 * Server initialization action.
 *
 * @author Maxime Ollagnier
 */
public class InitAction extends TilesAction
{
  public static final String INIT = "InitAction";

  /** Serial */
  private static final long serialVersionUID = 4298114599841866736L;

  /**
   * Initializes
   *
   * @return {@inheritDoc}
   * @see com.opensymphony.xwork2.ActionSupport#execute()
   */
  @Action(INIT)
  @Override
  public String execute()
  {
    return SUCCESS;
  }
}