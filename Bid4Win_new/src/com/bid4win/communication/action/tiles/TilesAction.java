package com.bid4win.communication.action.tiles;

import org.apache.struts2.convention.annotation.Result;

import com.bid4win.communication.action.BaseAction;
import com.opensymphony.xwork2.Action;

/**
 * Server initialization action.
 *
 * @author Maxime Ollagnier
 */
@Result(name = Action.SUCCESS, type = BaseAction.TYPE_TILE, params = {"location", "baseLayout.tile"})
public class TilesAction extends BaseAction
{
  /** Serial */
  private static final long serialVersionUID = -6360550330885279243L;
}