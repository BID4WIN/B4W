package com.bid4win.commons.persistence.dao.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountInitializer")
@Scope("singleton")
public class AccountInitializerStub extends AccountInitializer_<AccountAbstractStub, EntityGeneratorStub>
{
  /** Référence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountAbstractDaoStub")
  private AccountAbstractDaoStub dao;

  /**
   * Getter du DAO des comptes utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4winTestInitializer_#getDao()
   */
  @Override
  protected AccountAbstractDaoStub getDao()
  {
    return this.dao;
  }
}
