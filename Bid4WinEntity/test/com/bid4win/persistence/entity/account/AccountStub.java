package com.bid4win.persistence.entity.account;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class AccountStub extends Account
{
  /** TODO A COMMENTER */
  private String id = null;

  /**
   *
   * TODO A COMMENTER
   * @param id TODO A COMMENTER
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AccountStub(String id, Credential credential, Email email) throws Bid4WinException
  {
    super(credential, email);
    this.id = id;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountAbstract#getId()
   */
  @Override
  public String getId()
  {
    return this.id;
  }
}
