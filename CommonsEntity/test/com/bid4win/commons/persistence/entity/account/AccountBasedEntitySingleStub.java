package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AccountBasedEntitySingleStub
       extends AccountBasedEntitySingleAutoID<AccountBasedEntitySingleStub, AccountAbstractStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedEntitySingleStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public AccountBasedEntitySingleStub(AccountAbstractStub account) throws UserException
  {
    super(account);
  }
}
