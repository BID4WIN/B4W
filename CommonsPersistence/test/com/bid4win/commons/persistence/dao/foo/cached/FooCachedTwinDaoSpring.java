package com.bid4win.commons.persistence.dao.foo.cached;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedTwin;
import com.bid4win.commons.persistence.entity.foo.cached.FooCachedTwin_Fields;
import com.bid4win.commons.persistence.request.Bid4WinPagination;
import com.bid4win.commons.persistence.request.Bid4WinResult;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO pour les entit�s de la classe FooCachedTwin<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class FooCachedTwinDaoSpring extends FooCachedDaoSpring<FooCachedTwin>
{
  /** D�fini la liste de noeuds correspondant aux relations des deux jumeaux */
/*  private final static Bid4WinList<Bid4WinRelationNode> NODE_LIST =
      new Bid4WinList<Bid4WinRelationNode>(FooCachedTwin_.NODE_TWIN);*/

  /**
   * Constructeur
   */
  protected FooCachedTwinDaoSpring()
  {
    super(FooCachedTwin.class);
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public FooCachedTwin getById(Long id) throws PersistenceException
  {
    FooCachedTwin twin = super.getById(id);
    return twin.loadRelation(/*NODE_LIST*/);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findOneByValue(java.lang.String)
   */
  @Override
  public FooCachedTwin findOneByValue(String value) throws PersistenceException
  {
    FooCachedTwin twin = super.findOneByValue(value);
    return (twin != null ? twin.loadRelation(/*NODE_LIST*/) : null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value {@inheritDoc}
   * @param pagination {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpring#findListByValue(java.lang.String, com.bid4win.commons.persistence.request.Bid4WinPagination)
   */
  @Override
  public Bid4WinResult<FooCachedTwin> findListByValue(String value, Bid4WinPagination<FooCachedTwin> pagination) throws PersistenceException
  {
    Bid4WinResult<FooCachedTwin> twinList = super.findListByValue(value, pagination);
    for(FooCachedTwin twin : twinList)
    {
      twin.loadRelation(/*NODE_LIST*/);
    }
    return twinList;
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� li�e au jumeau dont l'
   * identifiant est en argument
   * @param twinId Identifiant du jumeau de l'entit� unique � r�cup�rer
   * @return Entit� r�cup�r�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public FooCachedTwin getByTwin(Long twinId) throws PersistenceException
  {
    return super.getOne(this.getTwinData(twinId)).loadRelation(/*NODE_LIST*/);
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� li�e au jumeau dont l'
   * identifiant est en argument
   * @param twinId Identifiant du jumeau de l'entit� unique � r�cup�rer
   * @return Entit� r�cup�r�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public FooCachedTwin findOneByTwin(Long twinId) throws PersistenceException
  {
    FooCachedTwin twin = super.findOne(this.getTwinData(twinId));
    return (twin != null ? twin.loadRelation(/*NODE_LIST*/) : null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s en fonction de l'identifiant de leur jumeau
   * @param twinId Identifiant du jumeau de l'entit� unique � r�cup�rer
   * @return Les crit�res permettant de rechercher des entit�s en fonction de l'
   * identifiant de leur jumeau
   */
  protected Bid4WinData<FooCachedTwin, Long> getTwinData(Long twinId)
  {
    return new Bid4WinData<FooCachedTwin, Long>(FooCachedTwin_Fields.TWIN_ID,
                                                twinId);
  }
}
