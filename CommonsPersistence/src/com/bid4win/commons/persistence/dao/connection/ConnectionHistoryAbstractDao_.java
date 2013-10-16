package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDao_;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinField;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO pour les entit�s de la classe ConnectionHistoryAbstract<BR>
 * <BR>
 * @param <HISTORY> D�finition du type d'historique de connexion g�r� par le DAO<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur li� � la connexion
 * g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ConnectionHistoryAbstractDao_<HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                    ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultipleDao_<HISTORY, Long, ACCOUNT>
{
  /**
   * Constructeur
   * @param connectionClass Classe de l'historique de connexion g�r� par le DAO
   */
  protected ConnectionHistoryAbstractDao_(Class<HISTORY> connectionClass)
  {
    super(connectionClass);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'historiques de connexions en
   * fonction de l'identifiant de session associ�e
   * @param sessionId Identifiant de sessions des connexions � rechercher
   * @return La liste des historiques de connexions trouv�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public Bid4WinList<HISTORY> findListBySessionId(String sessionId) throws PersistenceException
  {
    return super.findList(this.getSessionIdData(sessionId), null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * historiques de connexions dont l'identifiant de session associ�e est pr�cis�
   * en argument
   * @param sessionId Identifiant de sessions des connexions � rechercher
   * @return Les crit�res permettant de rechercher les historiques de connexions
   * en fonction de l'identifiant de session associ�e
   */
  protected Bid4WinData<HISTORY, String> getSessionIdData(String sessionId)
  {
    return new Bid4WinData<HISTORY, String>(this.getSessionIdField(), sessionId);
  }
  /**
   * A d�finir car :
   * Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si d�fini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @return TODO A COMMENTER
   */
  protected abstract Bid4WinField<? super HISTORY, ?, String, ?> getSessionIdField();

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
