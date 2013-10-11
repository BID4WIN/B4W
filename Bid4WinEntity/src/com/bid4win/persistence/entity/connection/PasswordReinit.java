package com.bid4win.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;
import com.bid4win.persistence.entity.account.Account;

/**
 * Cette classe défini une ré-initialisation de mot de passe<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
public class PasswordReinit extends PasswordReinitAbstract<PasswordReinit, Account>
{
  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private PasswordReinit()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur du mot de passe à réinitialiser
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public PasswordReinit(Account account) throws UserException
  {
    super(account);
  }
}
