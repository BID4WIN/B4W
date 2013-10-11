package com.bid4win.commons.persistence.dao.account;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDao_;
import com.bid4win.commons.persistence.dao.connection.ConnectionHistoryAbstractDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Credential_;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * DAO pour les entit�s de la classe Account<BR>
 * <BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur g�r� par le DAO<BR>
 * @param <CONNECTION> D�finition du type de connexion<BR>
 * @param <HISTORY> D�finition du type d'historique de connexion<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AccountAbstractDao_<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                          CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                          HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>>
       extends Bid4WinDao_<ACCOUNT, String>
{
  /** R�f�rence du DAO des connexions */
  @Autowired
  @Qualifier("ConnectionDao")
  private ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> connectionDao;
  /** R�f�rence du DAO des historiques de connexions */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> historyDao;

  /**
   * Constructeur
   * @param accountClass Classe du compte utilisateur g�r� par le DAO
   */
  protected AccountAbstractDao_(Class<ACCOUNT> accountClass)
  {
    super(accountClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#lockById(java.lang.Object)
   */
  @Override
  public ACCOUNT lockById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.lockById(id);
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique compte utilisateur potentiellement
   * nul en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public ACCOUNT findById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.findById(id);
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique compte utilisateur en fonction
   * de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public ACCOUNT getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id);
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique compte utilisateur en fonction
   * de son identifiant de connexion
   * @param login Identifiant de connexion du compte utilisateur � rechercher
   * @return Le compte utilisateur trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun compte utilisateur ne correspond �
   * l'identifiant de connexion en argument
   */
  public ACCOUNT getOneByLogin(Login login) throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getCriteriaForLogin(login));
  }
  /**
   * Cette fonction permet de r�cup�rer l'eventuel compte utilisateur en fonction
   * de son identifiant de connexion
   * @param login Identifiant de connexion du compte utilisateur � rechercher
   * @return Le compte utilisateur �ventuellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public ACCOUNT findOneByLogin(Login login) throws PersistenceException
  {
    return super.findOne(this.getCriteriaForLogin(login));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher le
   * compte utilisateur dont l'identifiant de connexion est pr�cis� en argument
   * @param login Identifiant de connexion du compte utilisateur � rechercher
   * @return Les crit�res permettant de rechercher le compte utilisateur en fonction
   * de son identifiant de connexion
   */
  protected CriteriaQuery<ACCOUNT> getCriteriaForLogin(Login login)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<ACCOUNT> criteria = this.createCriteria();
    Root<ACCOUNT> account_ = criteria.from(this.getEntityClass());
    Path<Login> login_ = this.getCredentialPath(account_).get(Credential_.login);
    Predicate condition = builder.equal(login_, login);
    // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
    // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
    // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
    //root.get(AccountAbstract_.credential).get(Credential_.login), login);
    criteria.where(condition);
    return criteria;
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique compte utilisateur en fonction
   * de son adresse email
   * @param email Adresse email du compte utilisateur � rechercher
   * @return Le compte utilisateur trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun compte utilisateur ne correspond �
   * l'adresse email en argument
   */
  public ACCOUNT getOneByEmail(Email email) throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getCriteriaForEmail(email));
  }
  /**
   * Cette fonction permet de r�cup�rer l'eventuel compte utilisateur en fonction
   * de son adresse email
   * @param email Adresse email du compte utilisateur � rechercher
   * @return Le compte utilisateur �ventuellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public ACCOUNT findOneByEmail(Email email) throws PersistenceException
  {
    return super.findOne(this.getCriteriaForEmail(email));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher le
   * compte utilisateur dont l'adresse email est pr�cis�e en argument
   * @param email Adresse email du compte utilisateur � rechercher
   * @return Les crit�res permettant de rechercher le compte utilisateur en fonction
   * de son adresse email
   */
  protected CriteriaQuery<ACCOUNT> getCriteriaForEmail(Email email)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<ACCOUNT> criteria = this.createCriteria();
    Root<ACCOUNT> account_ = criteria.from(this.getEntityClass());
    Path<Email> email_ = this.getEmailPath(account_);
    Predicate condition = builder.equal(email_, email);
    // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
    // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
    // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
    //root.get(AccountAbstract_.credential).get(Credential_.login), login);
    criteria.where(condition);
    return criteria;
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique compte utilisateur en fonction
   * de son identifiant de connexion ou de son adresse email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * � rechercher
   * @return Le compte utilisateur trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun compte utilisateur ne correspond aux
   * crit�res en argument
   * @throws UserException Si le param�tre ne correspond ni � un login, ni � un
   * email valide
   */
  public ACCOUNT getOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    return super.getOne(this.getCriteriaForLoginOrEmail(loginOrEmail));
  }
  /**
   * Cette fonction permet de r�cup�rer l'eventuel compte utilisateur en fonction
   * de son identifiant de connexion ou de son adresse email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * � rechercher
   * @return Le compte utilisateur �ventuellement trouv�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le param�tre ne correspond ni � un login, ni � un
   * email valide
   */
  public ACCOUNT findOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, UserException
  {
    return super.findOne(this.getCriteriaForLoginOrEmail(loginOrEmail));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher le
   * compte utilisateur dont l'identifiant de connexion ou l'adresse email est
   * pr�cis� en argument
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * � rechercher
   * @return Les crit�res permettant de rechercher le compte utilisateur en fonction
   * de son identifiant de connexion
   * @throws UserException Si le param�tre ne correspond ni � un login, ni � un
   * email valide
   */
  protected CriteriaQuery<ACCOUNT> getCriteriaForLoginOrEmail(String loginOrEmail)
            throws UserException
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<ACCOUNT> criteria = this.createCriteria();
    Root<ACCOUNT> account_ = criteria.from(this.getEntityClass());
    Predicate condition = null;
    try
    {
      Login login = new Login(loginOrEmail);
      Path<Login> login_ = this.getCredentialPath(account_).get(Credential_.login);
      condition = builder.equal(login_, login);
    }
    catch(UserException ex1)
    {
      try
      {
        Email email = new Email(loginOrEmail);
        Path<Email> email_ = this.getEmailPath(account_);
        condition = builder.equal(email_, email);
      }
      catch(UserException ex2)
      {
        throw new UserException(ConnectionRef.CONNECTION_LOGIN_OR_EMAIL_UNKNOWN_ERROR);
      }
    }
    criteria.where(condition);
    return criteria;
  }

  /**
   * A d�finir car :
   * Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si d�fini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<Credential> getCredentialPath(Root<ACCOUNT> root);
  /**
   * A d�finir car :
   * Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si d�fini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Path<Email> getEmailPath(Root<ACCOUNT> root);

  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des comptes utilisateur
   * @return La liste compl�te des comptes utilisateur trouv�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<ACCOUNT> findAll() throws PersistenceException
  {
    return new Bid4WinList<ACCOUNT>(super.findAll(), true);
  }

  /**
   * Cette fonction permet d'ajouter le compte utilisateur en argument
   * @param account {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public ACCOUNT add(ACCOUNT account) throws PersistenceException
  {
    // Ajoute le compte utilisateur en param�tre
    return super.add(account);
  }

  /**
   * Cette fonction permet de modifier le compte utilisateur en argument
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public ACCOUNT update(ACCOUNT account) throws PersistenceException
  {
    // Modifie le compte utilisateur en param�tre
    return super.update(account);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected ACCOUNT remove(ACCOUNT account) throws PersistenceException
  {
    for(CONNECTION connection : this.getConnectionDao().findListByAccount(account))
    {
      this.getConnectionDao().update(connection.stopConnection(DisconnectionReason.NONE));
    }
    for(HISTORY history : this.getHistoryDao().findListByAccount(account))
    {
      this.getHistoryDao().remove(history);
    }
    return super.remove(account);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> getConnectionDao()
  {
    return this.connectionDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> getHistoryDao()
  {
    return this.historyDao;
  }
}
