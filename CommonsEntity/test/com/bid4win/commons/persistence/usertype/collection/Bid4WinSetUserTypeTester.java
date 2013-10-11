package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserTypeTester;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <OBJECT> TODO A COMMENTER<BR>
 * @param <USER_TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinSetUserTypeTester<OBJECT extends Serializable,
                                               USER_TYPE extends Bid4WinSetUserType<OBJECT>>
       extends Bid4WinStringUserTypeTester<Bid4WinSet<OBJECT>, USER_TYPE>
{
  // Pas de définition spécifique
}
