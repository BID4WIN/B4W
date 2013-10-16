package com.bid4win.commons.persistence.dao.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoGeneratedID_;
import com.bid4win.commons.persistence.dao.connection.ConnectionAbstractDao_;
import com.bid4win.commons.persistence.dao.connection.ConnectionHistoryAbstractDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinField;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.Bid4WinCriteriaList;
import com.bid4win.commons.persistence.request.Bid4WinRequest;
import com.bid4win.commons.persistence.request.Bid4WinResult;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO pour les entités de la classe Account<BR>
 * <BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur géré par le DAO<BR>
 * @param <CONNECTION> Définition du type de connexion<BR>
 * @param <HISTORY> Définition du type d'historique de connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountAbstractDao_<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                          CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                          HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>>
       extends Bid4WinDaoGeneratedID_<ACCOUNT>
{
  /** Référence du DAO des connexions */
  @Autowired
  @Qualifier("ConnectionDao")
  private ConnectionAbstractDao_<CONNECTION, HISTORY, ACCOUNT> connectionDao;
  /** Référence du DAO des historiques de connexions */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> historyDao;

  /**
   * Constructeur
   * @param accountClass Classe du compte utilisateur géré par le DAO
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
   * Cette fonction permet de récupérer l'unique compte utilisateur potentiellement
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
   * Cette fonction permet de récupérer l'unique compte utilisateur en fonction
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
   * Cette fonction permet de récupérer l'unique compte utilisateur en fonction
   * de son identifiant de connexion
   * @param login Identifiant de connexion du compte utilisateur à rechercher
   * @return Le compte utilisateur trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun compte utilisateur ne correspond à
   * l'identifiant de connexion en argument
   */
  public ACCOUNT getOneByLogin(Login login) throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getLoginValueData(login.getValue()));
  }
  /**
   * Cette fonction permet de récupérer l'eventuel compte utilisateur en fonction
   * de son identifiant de connexion
   * @param login Identifiant de connexion du compte utilisateur à rechercher
   * @return Le compte utilisateur éventuellement trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public ACCOUNT findOneByLogin(Login login) throws PersistenceException
  {
    return super.findOne(this.getLoginValueData(login.getValue()));
  }
  /**
   * Cette méthode permet de construire la définition de requête permettant de
   * rechercher le compte utilisateur dont l'identifiant de connexion est précisé
   * en argument
   * @param login Identifiant de connexion du compte utilisateur à rechercher
   * @return La définition de requête permettant de rechercher le compte utilisateur
   * en fonction de son identifiant de connexion
   */
  protected Bid4WinData<ACCOUNT, String> getLoginValueData(String value)
  {
    return new Bid4WinData<ACCOUNT, String>(this.getLoginValueField(), value);
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @return TODO A COMMENTER
   */
  protected abstract Bid4WinField<? super ACCOUNT, ?, String, ?> getLoginValueField();

  /**
   * Cette fonction permet de récupérer l'unique compte utilisateur en fonction
   * de son adresse email
   * @param email Adresse email du compte utilisateur à rechercher
   * @return Le compte utilisateur trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun compte utilisateur ne correspond à
   * l'adresse email en argument
   */
  public ACCOUNT getOneByEmail(Email email) throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getEmailAddressData(email.getAddress()));
  }
  /**
   * Cette fonction permet de récupérer l'eventuel compte utilisateur en fonction
   * de son adresse email
   * @param email Adresse email du compte utilisateur à rechercher
   * @return Le compte utilisateur éventuellement trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public ACCOUNT findOneByEmail(Email email) throws PersistenceException
  {
    return super.findOne(this.getEmailAddressData(email.getAddress()));
  }
  /**
   * Cette méthode permet de construire la définition de requête permettant de
   * rechercher le compte utilisateur dont l'adresse email est précisée en argument
   * @param email Adresse email du compte utilisateur à rechercher
   * @return La définition de requête permettant de rechercher le compte utilisateur
   * en fonction de son adresse email
   */
  protected Bid4WinData<ACCOUNT, String> getEmailAddressData(String address)
  {
    return new Bid4WinData<ACCOUNT, String>(this.getEmailAddressField(), address);
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @return TODO A COMMENTER
   */
  protected abstract Bid4WinField<? super ACCOUNT, ?, String, ?> getEmailAddressField();

  /**
   * Cette fonction permet de récupérer l'unique compte utilisateur en fonction
   * de son identifiant de connexion ou de son adresse email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * à rechercher
   * @return Le compte utilisateur trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun compte utilisateur ne correspond aux
   * critères en argument
   */
  public ACCOUNT getOneByLoginOrEmail(String loginOrEmail)
         throws PersistenceException, NotFoundEntityException
  {
    return super.getOne(this.getLoginOrEmailCriteria(loginOrEmail));
  }
  /**
   * Cette fonction permet de récupérer l'eventuel compte utilisateur en fonction
   * de son identifiant de connexion ou de son adresse email
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * à rechercher
   * @return Le compte utilisateur éventuellement trouvé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public ACCOUNT findOneByLoginOrEmail(String loginOrEmail) throws PersistenceException
  {
    return super.findOne(this.getLoginOrEmailCriteria(loginOrEmail));
  }
  /**
   * Cette méthode permet de construire la définition de requête permettant de
   * rechercher le compte utilisateur dont l'identifiant de connexion ou l'adresse
   * email est précisé en argument
   * @param loginOrEmail Identifiant de connexion ou email du compte utilisateur
   * à rechercher
   * @return La définition de requête permettant de rechercher le compte utilisateur
   * en fonction de son identifiant de connexion
   */
  @SuppressWarnings("unchecked")
  protected Bid4WinCriteria<ACCOUNT> getLoginOrEmailCriteria(String loginOrEmail)
  {
    return new Bid4WinCriteriaList<ACCOUNT>(
        false, this.getLoginValueData(loginOrEmail), this.getEmailAddressData(loginOrEmail));
  }

  /**
   * Cette fonction permet de récupérer la liste des comptes utilisateur correspondant
   * à la définition de requête en argument
   * @param request {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findList(com.bid4win.commons.persistence.request.Bid4WinRequest)
   */
  @Override
  public Bid4WinResult<ACCOUNT> findList(Bid4WinRequest<ACCOUNT> request) throws PersistenceException
  {
    return super.findList(request);
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
    // Ajoute le compte utilisateur en paramètre
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
    // Modifie le compte utilisateur en paramètre
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
