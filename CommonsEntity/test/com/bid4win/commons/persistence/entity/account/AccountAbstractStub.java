package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AccountAbstractStub extends AccountAbstract<AccountAbstractStub>
{
  /**
   * Constructeur pour création par introspection ou en proxy pour chargement différé
   */
  protected AccountAbstractStub()
  {
    super();
  }

  /**
   * 
   * TODO A COMMENTER
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public AccountAbstractStub(Credential credential, Email email) throws ModelArgumentException
  {
    super(credential, email);
  }
  /**
   * 
   * TODO A COMMENTER
   * @param id Identifiant du compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public AccountAbstractStub(String id, Credential credential, Email email)
         throws ModelArgumentException
  {
    super(id, credential, email);
  }
}
