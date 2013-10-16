package com.bid4win.commons.persistence.dao.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Stub du DAO pour les entités de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountAbstractDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class AccountAbstractDaoStub extends AccountAbstractDao/*<AccountAbstractStub,
                                                                ConnectionAbstractStub,
                                                                ConnectionHistoryAbstractStub>*/
       implements IAccountAbstractDaoStub<AccountAbstractStub>
{
  /**
   *
   * TODO A COMMENTER
   */
  protected AccountAbstractDaoStub()
  {
   // super(AccountAbstractStub.class);
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getCredentialPath(javax.persistence.criteria.Root)
   */
/*  @Override
  protected Bid4WinField<AccountAbstractStub, Login> getLoginField()
  {
    // TODO Auto-generated method stub
    return AccountAbstractStub_.LOGIN;
  }
  @Override
  protected Bid4WinField<AccountAbstractStub, Email> getEmailField()
  {
    // TODO Auto-generated method stub
    return AccountAbstractStub_.EMAIL;
  }
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
  /////////////////////////////////////////////////////////////////////////////
  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getById(java.lang.String)
   */
  @Override
  public AccountAbstractStub getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param login {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getOneByLogin(com.bid4win.commons.persistence.entity.account.security.Login)
   */
  @Override
  public AccountAbstractStub getOneByLogin(Login login) throws PersistenceException
  {
    return super.getOneByLogin(login).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param login {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findOneByLogin(com.bid4win.commons.persistence.entity.account.security.Login)
   */
  @Override
  public AccountAbstractStub findOneByLogin(Login login) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByLogin(login));
  }
  /**
   *
   * TODO A COMMENTER
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getOneByEmail(com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  public AccountAbstractStub getOneByEmail(Email email) throws PersistenceException
  {
    return super.getOneByEmail(email).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param email {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findOneByEmail(com.bid4win.commons.persistence.entity.contact.Email)
   */
  @Override
  public AccountAbstractStub findOneByEmail(Email email) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByEmail(email));
  }
  /**
   *
   * TODO A COMMENTER
   * @param loginOrEmail {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getOneByLoginOrEmail(java.lang.String)
   */
  @Override
  public AccountAbstractStub getOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, NotFoundEntityException//, UserException
  {
    return super.getOneByLoginOrEmail(loginOrEmail).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param loginOrEmail {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findOneByLoginOrEmail(java.lang.String)
   */
  @Override
  public AccountAbstractStub findOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException//, UserException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByLoginOrEmail(loginOrEmail));
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#findAll()
   */
  @Override
  public Bid4WinList<AccountAbstractStub> findAll() throws PersistenceException
  {
    Bid4WinList<AccountAbstractStub> list = super.findAll();
    return Bid4WinEntityLoader.getInstance().loadRelation(list);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#add(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AccountAbstractStub add(AccountAbstractStub account) throws PersistenceException
  {
    return super.add(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#update(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AccountAbstractStub update(AccountAbstractStub account) throws PersistenceException
  {
    return super.update(account);
  }
  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajoutées pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public AccountAbstractStub findById(String id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
  }
  /**
   * Cette fonction permet de récupérer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinMap<String, AccountAbstractStub> getById(Bid4WinSet<String> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
  }
  /**
   * Cette fonction permet de supprimer un compte utilisateur en fonction de son
   * identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AccountAbstractStub removeById(String id) throws PersistenceException
  {
    return super.removeById(id);
  }
  /**
   * Il est nécessaire de dé-référencer le lot de droits à enchérir avant de le supprimer
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AccountAbstractStub remove(AccountAbstractStub account) throws PersistenceException
  {
    return super.remove(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param idList TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws NotFoundEntityException TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<AccountAbstractStub> removeListById(Bid4WinSet<String> idList) throws NotFoundEntityException, PersistenceException
  {
    return super.removeListById(idList);
  }
  /**
   * Cette fonction permet de supprimer un compte utilisateur en fonction de son
   * identifiant de connection
   * @param login Identifiant de connexion du compte utilisateur à supprimer
   * @return Le compte utilisateur supprimé
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws ModelArgumentException Si le compte utilisateur n'a pas pu être trouvé
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public AccountAbstractStub removeByLogin(Login login) throws PersistenceException, ModelArgumentException
  {
    AccountAbstractStub account = UtilObject.checkNotNull("account", this.findOneByLogin(login));
    return this.remove(account);
  }
  /**
   * Cette fonction permet de supprimer la liste de tous les comptes utilisateur
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<AccountAbstractStub> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
}
