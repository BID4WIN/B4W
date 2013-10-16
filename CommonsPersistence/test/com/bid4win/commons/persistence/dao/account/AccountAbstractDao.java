package com.bid4win.commons.persistence.dao.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimple;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub_Fields;
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


  @Override
  protected Bid4WinFieldSimpleToSimple<AccountAbstractStub, Credential, String> getLoginValueField()
  {
    return AccountAbstractStub_Fields.LOGIN_VALUE;
  }

  @Override
  protected Bid4WinFieldSimpleToSimple<AccountAbstractStub, Email, String> getEmailAddressField()
  {
    return AccountAbstractStub_Fields.EMAIL_ADDRESS;
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getCredentialPath(javax.persistence.criteria.Root)
   */
  /*@Override
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
  /*@Override
  protected Path<Email> getEmailPath(Root<AccountAbstractStub> root)
  {
    return root.get(AccountAbstractStub_.email);
  }
  @Override
  /*protected Bid4WinField<AccountAbstractStub, Login> getLoginField()
  {
    // TODO Auto-generated method stub
    return AccountAbstractStub_.LOGIN;
  }
  @Override
  /protected Bid4WinField<AccountAbstractStub, Email> getEmailField()
  {
    // TODO Auto-generated method stub
    return AccountAbstractStub_.EMAIL;
  }*/
}
