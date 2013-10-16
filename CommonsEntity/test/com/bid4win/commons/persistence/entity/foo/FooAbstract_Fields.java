package com.bid4win.commons.persistence.entity.foo;

import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID_Fields;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.core.Bid4WinDateForRequest;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooAbstract_Fields extends Bid4WinEntityAutoID_Fields
{
  /** Definition du champ correspondant � la valeur de l'objet */
  public static final Bid4WinFieldSimple<FooAbstract<?>, String> VALUE =
      new Bid4WinFieldSimple<FooAbstract<?>, String>(null, FooAbstract_.value);
  /** Definition du champ correspondant � la date de l'objet */
  public static final Bid4WinFieldSimple<FooAbstract<?>, Bid4WinDateForRequest> DATE =
      new Bid4WinFieldSimple<FooAbstract<?>, Bid4WinDateForRequest>(null, FooAbstract_.dateForRequest);
  /** Definition du champ correspondant au r�le de l'objet */
  public static final Bid4WinFieldSimple<FooAbstract<?>, Role> ROLE =
      new Bid4WinFieldSimple<FooAbstract<?>, Role>(null, FooAbstract_.role);
}
