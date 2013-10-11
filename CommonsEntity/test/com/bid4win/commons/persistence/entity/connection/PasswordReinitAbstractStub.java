package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PasswordReinitAbstractStub
       extends PasswordReinitAbstract<PasswordReinitAbstractStub, AccountAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private PasswordReinitAbstractStub()
  {
    super();
  }

  /**
   * Constructeur
   * @param account Compte utilisateur du mot de passe à réinitialiser
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public PasswordReinitAbstractStub(AccountAbstractStub account) throws UserException
  {
    super(account);
  }
}
