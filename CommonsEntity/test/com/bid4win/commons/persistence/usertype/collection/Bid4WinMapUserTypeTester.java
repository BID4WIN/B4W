package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <VALUE> TODO A COMMENTER<BR>
 * @param <USER_TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinMapUserTypeTester<KEY extends Serializable,
                                               VALUE extends Serializable,
                                               USER_TYPE extends Bid4WinMapUserType<KEY, VALUE>>
       extends Bid4WinStringUserTypeTester<Bid4WinMap<KEY, VALUE>, USER_TYPE>
{
  // Pas de définition spécifique
}
