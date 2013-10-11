package com.bid4win.commons.persistence.dao.account;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub_;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractStub;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Stub du DAO pour les entités de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountDao")
@Scope("singleton")
public class AccountAbstractDao extends AccountAbstractDao_<AccountAbstractStub,
                                                            ConnectionAbstractStub,
                                                            ConnectionHistoryAbstractStub>
{
  /**
   *
   * TODO A COMMENTER
   */
  protected AccountAbstractDao()
  {
    super(AccountAbstractStub.class);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getCredentialPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<Credential> getCredentialPath(Root<AccountAbstractStub> root)
  {
    return root.get(com.bid4win.commons.persistence.entity.account.AccountAbstractStub_.credential);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getEmailPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<Email> getEmailPath(Root<AccountAbstractStub> root)
  {
    return root.get(AccountAbstractStub_.email);
  }
}
