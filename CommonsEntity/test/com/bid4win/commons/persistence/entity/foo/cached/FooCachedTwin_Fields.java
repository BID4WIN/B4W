package com.bid4win.commons.persistence.entity.foo.cached;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID;
import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimpleParent;

/**
 * Metamodel de la class FooCachedTwin<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooCachedTwin_Fields extends FooCached_Fields
{
  /** TODO A COMMENTER */
  public static final Bid4WinFieldSimple<FooCachedTwin, FooCachedTwin> TWIN =
      new Bid4WinFieldSimple<FooCachedTwin, FooCachedTwin>(null, FooCachedTwin_.twin);
  /** TODO A COMMENTER */
  public static final Bid4WinFieldSimpleToSimpleParent<FooCachedTwin, Bid4WinEntityAutoID<?>, FooCachedTwin, Long> TWIN_ID =
      new Bid4WinFieldSimpleToSimpleParent<FooCachedTwin, Bid4WinEntityAutoID<?>, FooCachedTwin, Long>(
          TWIN, Bid4WinEntityAutoID_Fields.ID);
}