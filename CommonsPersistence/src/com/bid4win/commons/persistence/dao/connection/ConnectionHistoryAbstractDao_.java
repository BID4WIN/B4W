package com.bid4win.commons.persistence.dao.connection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract_;

/**
 * DAO pour les entités de la classe ConnectionHistoryAbstract<BR>
 * <BR>
 * @param <HISTORY> Définition du type d'historique de connexion géré par le DAO<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à la connexion
 * gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConnectionHistoryAbstractDao_<HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                           ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultipleDao_<HISTORY, Long, ACCOUNT>
{
  /**
   * Constructeur
   * @param connectionClass Classe de l'historique de connexion géré par le DAO
   */
  protected ConnectionHistoryAbstractDao_(Class<HISTORY> connectionClass)
  {
    super(connectionClass);
  }

  /**
   * Cette fonction permet de récupérer la liste d'historiques de connexions en
   * fonction de l'identifiant de session associée
   * @param sessionId Identifiant de sessions des connexions à rechercher
   * @return La liste des historiques de connexions trouvés
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<HISTORY> findListBySessionId(String sessionId) throws PersistenceException
  {
    return super.findList(this.getCriteriaForSessionId(sessionId));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * historiques de connexions dont l'identifiant de session associée est précisé
   * en argument
   * @param sessionId Identifiant de sessions des connexions à rechercher
   * @return Les critères permettant de rechercher les historiques de connexions
   * en fonction de l'identifiant de session associée
   */
  protected CriteriaQuery<HISTORY> getCriteriaForSessionId(String sessionId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<HISTORY> criteria = this.createCriteria();
    Root<HISTORY> history_ = criteria.from(this.getEntityClass());
    Path<String> sessionId_ = history_.get(ConnectionHistoryAbstract_.sessionId);
    Predicate condition = builder.equal(sessionId_, sessionId);
    criteria.where(condition);
    return criteria;
  }

  /**
   *
   * TODO A COMMENTER
   * @param history {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotPersistedEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public HISTORY update(HISTORY history) throws NotPersistedEntityException, PersistenceException
  {
    return super.update(history);
  }
  /**
   *
   * TODO A COMMENTER
   * @param history {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotPersistedEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public HISTORY remove(HISTORY history) throws NotPersistedEntityException, PersistenceException
  {
    return super.remove(history);
  }
}
