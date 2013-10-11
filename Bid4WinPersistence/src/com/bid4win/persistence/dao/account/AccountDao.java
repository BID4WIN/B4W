package com.bid4win.persistence.dao.account;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.account.AccountAbstractDao_;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.Account_;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;

/**
 * DAO pour les entités de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountDao")
@Scope("singleton")
public class AccountDao extends AccountAbstractDao_<Account, Connection, ConnectionHistory>
{
  /**
   * Constructeur
   */
  protected AccountDao()
  {
    super(Account.class);
  }

  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getCredentialPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<Credential> getCredentialPath(Root<Account> root)
  {
    return root.get(Account_.credential);
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getEmailPath(javax.persistence.criteria.Root)
   */
  @Override
  protected Path<Email> getEmailPath(Root<Account> root)
  {
    return root.get(Account_.email);
  }
}
