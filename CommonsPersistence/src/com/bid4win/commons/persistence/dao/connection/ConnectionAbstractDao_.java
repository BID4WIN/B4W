package com.bid4win.commons.persistence.dao.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;

/**
 * DAO pour les entités de la classe ConnectionAbstract<BR>
 * <BR>
 * @param <CONNECTION> Définition du type de connexion gérée par le DAO<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à la connexion
 * gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ConnectionAbstractDao_<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                             HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultipleDao_<CONNECTION, String, ACCOUNT>
{
  /** Référence du DAO des historiques de connexion */
  @Autowired
  @Qualifier("ConnectionHistoryDao")
  private ConnectionHistoryAbstractDao_<HISTORY, ACCOUNT> historyDao;

  /**
   * Constructeur
   * @param connectionClass Classe de la connexion gérée par le DAO
   */
  protected ConnectionAbstractDao_(Class<CONNECTION> connectionClass)
  {
    super(connectionClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public CONNECTION findById(String id) throws PersistenceException
  {
    return super.findById(id);
  }
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public CONNECTION getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id);
  }

  /**
   *
   * TODO A COMMENTER
   * @param connection {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDao_#add(com.bid4win.commons.persistence.entity.account.AccountBasedEntity)
   */
  @Override
  public CONNECTION add(CONNECTION connection) throws PersistenceException
  {
    if(!connection.isActive())
    {
      throw new PersistenceException("Cannot add inactive connection");
    }
    return super.add(connection);
  }
  /**
   * Cette fonction permet de modifier la connexion en argument. Si celle-ci vient
   * d'être terminée, son historique sera créée et si la rémanence est inactive,
   * cette connexion sera supprimée
   * @param connection {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public CONNECTION update(CONNECTION connection) throws PersistenceException
  {
    // Récupère l'historique potentiel à créer
    HISTORY history = connection.getHistory();
    if(history != null && history.isNewEntity())
    {
      this.getHistoryDao().add(history);
    }
    // La connexion est active ou rémanente, on doit la conserver pour une utilisation
    // postérieure
    if(connection.isActive() || connection.getData().isRemanent())
    {
      // Modifie la connexion en argument
      return super.update(connection);
    }
    // La connexion peut être supprimée
    return this.remove(super.update(connection));
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
