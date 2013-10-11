package com.bid4win.communication.action.tiles;

import org.apache.struts2.convention.annotation.Result;

import com.bid4win.communication.action.BaseAction;

/**
 * Server initialization action.
 *
 * @author Maxime Ollagnier
 */
@Result(name = "success", type = "tile", params = {"location", "baseLayout.tile"})
public class TilesAction extends BaseAction
{
  /** Serial */
  private static final long serialVersionUID = -6360550330885279243L;
}