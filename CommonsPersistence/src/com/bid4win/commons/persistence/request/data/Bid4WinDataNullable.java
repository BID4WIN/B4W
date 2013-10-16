package com.bid4win.commons.persistence.request.data;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <FIELD> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDataNullable<ENTITY extends Bid4WinEntity<?, ?>, FIELD>
       extends Bid4WinData<ENTITY, FIELD>
{
  /**
   *
   * TODO A COMMENTER
   * @param field TODO A COMMENTER
   */
  public Bid4WinDataNullable(Bid4WinField<? super ENTITY, ?, FIELD, ?> field)
  {
    this(field, true);
  }
  /**
   *
   * TODO A COMMENTER
   * @param field TODO A COMMENTER
   * @param nullExpected TODO A COMMENTER
   */
  @SuppressWarnings("unchecked")
  public Bid4WinDataNullable(Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                             boolean nullExpected)
  {
    super(field, nullExpected, (FIELD)null);
  }
}
