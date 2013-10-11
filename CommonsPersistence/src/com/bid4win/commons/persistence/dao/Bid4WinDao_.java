package com.bid4win.commons.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.dao.exception.NotUniqueEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * DAO g�n�rique du projet<BR>
 * <BR>
 * @param <ENTITY> Entit� g�r�e par le DAO<BR>
 * @param <ID> Identifiant de l'entit� g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinDao_ <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
{
  /** Entity Manager g�n�rique du projet */
  // Annotation pour la persistence
  @PersistenceContext(name = "Bid4WinEntityManagerFactory")
  private EntityManager entityManager;

  /** Classe de l'entit� g�r�e par le DAO */
  private Class<ENTITY> entityClass;

  /**
   * Constructeur
   * @param entityClass Classe de l'entit� g�r�e par le DAO
   */
  protected Bid4WinDao_(Class<ENTITY> entityClass)
  {
    super();
    this.setEntityClass(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   */
  public void flush()
  {
    this.getEntityManager().flush();
  }
  /**
   * Cette fonction permet de verrouiller l'entit� en param�tre et de r�cup�rer
   * son dernier �tat persist�
   * @param entity Entit� � verrouiller
   * @return L'entit� verrouill�e dans son dernier �tat persist�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected ENTITY lock(ENTITY entity) throws PersistenceException
  {
    try
    {
      this.getEntityManager().refresh(entity, LockModeType.PESSIMISTIC_WRITE);
      return entity;
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette fonction permet de verrouiller l'entit� dont l'identifiant est pass�
   * en param�tre
   * @param id Identifiant de l'entit� � verrouiller
   * @return L'entit� verrouill�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  protected ENTITY lockById(ID id) throws PersistenceException, NotFoundEntityException
  {
    try
    {
      ENTITY entity = this.getEntityManager().find(this.getEntityClass(), id,
                                                   LockModeType.PESSIMISTIC_WRITE);
      if(entity == null)
      {
        throw new NotFoundEntityException(this.getEntityClass(), id);
      }
      return entity;
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette fonction permet de verrouiller la liste d'entit�s correspondants aux
   * crit�res en argument
   * @param criteria Crit�res permettant la r�cup�ration de la liste d'entit�s �
   * verrouiller
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected void lockList(CriteriaQuery<ENTITY> criteria) throws PersistenceException
  {
    for(ENTITY entity : this.findList(criteria))
    {
      this.lock(entity);
    }
  }

  /**
   * Cette m�thode permet de rafra�chir l'�tat de l'entit� en param�tre
   * @param entity Entit� � rafra�chir
   * @return L'entit� rafra�chie
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected ENTITY refresh(ENTITY entity) throws PersistenceException
  {
    try
    {
      this.getEntityManager().refresh(entity);
      return entity;
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� non nulle en fonction de
   * son identifiant
   * @param id Identifiant de l'entit� � r�cup�rer
   * @return L'unique entit� non nulle r�cup�r�e en fonction de son identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  protected ENTITY getById(ID id) throws PersistenceException, NotFoundEntityException
  {
    ENTITY entity = this.findById(id);
    if(entity == null)
    {
      throw new NotFoundEntityException(this.getEntityClass(), id);
    }
    return entity;
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� potentiellement nulle en
   * fonction de son identifiant
   * @param id Identifiant de l'entit� � r�cup�rer
   * @return L'unique entit� potentiellement nulle r�cup�r�e en fonction de son
   * identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected ENTITY findById(ID id) throws PersistenceException
  {
    try
    {
      return this.getEntityManager().find(this.getEntityClass(), id);
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette fonction permet de r�cup�rer les entit�s dont les identifiants sont
   * pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � r�cup�rer
   * @return Les entit�s correspondant aux identifiants en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'un des identifiants en argument
   */
  protected Bid4WinMap<ID, ENTITY> getById(Bid4WinSet<ID> idSet)
            throws PersistenceException, NotFoundEntityException
  {
    Bid4WinMap<ID, ENTITY> result = new Bid4WinMap<ID, ENTITY>(idSet.size());
    for(ID id : idSet)
    {
      result.put(id, this.getById(id));
    }
    return result;
  }
  /**
   * Cette fonction permet de r�cup�rer les potentielles entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � r�cup�rer
   * @return Les potentielles entit�s correspondant aux identifiants en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinMap<ID, ENTITY> findById(Bid4WinSet<ID> idSet) throws PersistenceException
  {
    Bid4WinMap<ID, ENTITY> result = new Bid4WinMap<ID, ENTITY>(idSet.size());
    for(ID id : idSet)
    {
      ENTITY entity = this.findById(id);
      if(entity != null)
      {
        result.put(id, entity);
      }
    }
    return result;
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� non nulle correspondant
   * aux crit�res en argument
   * @param criteria Crit�res permettant la r�cup�ration de l'unique entit� choisie
   * @return L'unique entit� non nulle correspondant aux crit�res en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� ne correspond aux crit�res
   * en argument
   * @throws NotUniqueEntityException Si plusieurs entit�s correspondent aux crit�res
   * en argument
   */
  protected ENTITY getOne(CriteriaQuery<ENTITY> criteria)
            throws PersistenceException, NotFoundEntityException, NotUniqueEntityException
  {
    // On cr�e la requ�te et on l'execute
    ENTITY result = this.findOne(criteria);
    // Le requ�te doit avoir remont� un �l�ment
    if(result == null)
    {
      throw new NotFoundEntityException(this.getEntityClass());
    }
    return result;
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� potentiellement nulle
   * correspondant aux crit�res en argument
   * @param criteria Crit�res permettant la r�cup�ration de l'unique entit� choisie
   * @return L'unique entit� potentiellement nulle correspondant aux crit�res en
   * argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotUniqueEntityException Si plusieurs entit�s correspondent aux crit�res
   * en argument
   */
  protected ENTITY findOne(CriteriaQuery<ENTITY> criteria)
            throws PersistenceException, NotUniqueEntityException
  {
    // On cr�e la requ�te et on l'execute
    Bid4WinList<ENTITY> result = this.findList(criteria);
    // Le requ�te ne doit pas avoir remont� plus d'un �l�ment
    if(result.size() > 1)
    {
      throw new NotUniqueEntityException(this.getEntityClass());
    }
    // On retourne son r�sultat
    if(result.size() == 0)
    {
      return null;
    }
    return result.get(0);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s correspondant aux crit�res
   * en argument
   * @param criteria Crit�res permettant la r�cup�ration de la liste d'entit�s
   * choisies
   * @return La liste d'entit�s correspondants aux crit�res en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> findList(CriteriaQuery<ENTITY> criteria) throws PersistenceException
  {
    try
    {
      // On cr�e la requ�te
      TypedQuery<ENTITY> query = this.createQuery(criteria);
      // On ex�cute la requ�te et retourne son r�sultat
      return new Bid4WinList<ENTITY>(query.getResultList(), true);
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des entit�s
   * @return La liste compl�te des entit�s
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> findAll() throws PersistenceException
  {
    // On cr�e les crit�res de la requ�te
    CriteriaQuery<ENTITY> criteria = this.createCriteria();
    this.addConditionForAll(criteria);
    // On ex�cute la requ�te et retourne son r�sultat
    return this.findList(criteria);
  }
  /**
   * Cette fonction permet d'ajouter des conditions pour la r�cup�ration de la
   * liste compl�te des entit�s
   * @param criteria Crit�res auxquelles il faut ajouter des conditions si besoin
   */
  protected Root<ENTITY> addConditionForAll(CriteriaQuery<ENTITY> criteria)
  {
    // Par d�faut, aucun crit�re n'est n�cessaire
    return criteria.from(this.getEntityClass());
  }

  /**
   * Cette fonction permet d'ajouter l'entit� en argument
   * @param entity Entit� � ajouter
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected ENTITY add(ENTITY entity) throws PersistenceException
  {
    try
    {
      this.getEntityManager().persist(entity);
      return entity;
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette fonction permet de modifier l'entit� en argument
   * @param entity Entit� � modifier
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotPersistedEntityException Si l'entit� n'a jamais �t� persist�e
   * auparavant
   */
  protected ENTITY update(ENTITY entity) throws PersistenceException, NotPersistedEntityException
  {
    try
    {
      // On ne peut pas modifier une entit� non persist�e
      if(entity.isNewEntity())
      {
        throw new NotPersistedEntityException(this.getEntityClass(), "update");
      }
      return this.getEntityManager().merge(entity);
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette fonction permet de forcer la modification de l'entit� en argument. Cela
   * implique que l'entit� doit avoir d�fini son champs updateForce comme persist�
   * @param entity Entit� dont la modification doit �tre forc�e
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotPersistedEntityException Si l'entit� n'a jamais �t� persist�e
   * auparavant
   */
  protected ENTITY forceUpdate(ENTITY entity) throws PersistenceException, NotPersistedEntityException
  {
    return this.update(entity.forceUpdate());
  }
  /**
   * Cette fonction permet de supprimer l'entit� en argument
   * @param entity Entit� � supprimer
   * @return L'entit� supprim�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected ENTITY remove(ENTITY entity) throws PersistenceException
  {
    try
    {
      this.getEntityManager().remove(entity);
      return entity;
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette fonction permet de supprimer la liste d'entit�s en argument
   * @param entityList Liste d'entit�s � supprimer
   * @return La liste d'entit�s supprim�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinSet<ENTITY> removeList(Bid4WinSet<ENTITY> entityList)
            throws PersistenceException
  {
    Bid4WinSet<ENTITY> result = new Bid4WinSet<ENTITY>(entityList.size());
    for(ENTITY entity : entityList)
    {
      result.add(this.remove(entity));
    }
    return result;
  }
  /**
   * Cette fonction permet de supprimer une entit� en fonction de son identifiant
   * @param id Identifiant de l'entit� � supprimer
   * @return L'entit� supprim�e en fonction de son identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  protected ENTITY removeById(ID id) throws PersistenceException, NotFoundEntityException
  {
    ENTITY entity = this.getById(id);
    this.remove(entity);
    return entity;
  }
  /**
   * Cette fonction permet de supprimer le set d'entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � supprimer
   * @return Le set d'entit�s supprim�es correspondants aux identifiants en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre supprim�e avec
   * l'un des identifiants en argument
   */
  protected Bid4WinSet<ENTITY> removeListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException
  {
    Bid4WinSet<ENTITY> result = new Bid4WinSet<ENTITY>(idSet.size());
    for(ID id : idSet)
    {
      result.add(this.removeById(id));
    }
    return result;
  }
  /**
   * Cette fonction permet de supprimer la liste d'entit�s correspondants aux
   * crit�res en argument
   * @param criteria Crit�res permettant la r�cup�ration de la liste d'entit�s �
   * supprimer
   * @return La liste d'entit�s supprim�es correspondants aux crit�res en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> removeList(CriteriaQuery<ENTITY> criteria) throws PersistenceException
  {
    Bid4WinList<ENTITY> list = this.findList(criteria);
    for(ENTITY entity : list)
    {
      this.remove(entity);
    }
    return list;
  }
  /**
   * Cette fonction permet de supprimer la liste de toutes les entit�s
   * @return La liste de toutes les entit�s supprim�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> removeAll() throws PersistenceException
  {
    CriteriaQuery<ENTITY> criteria = this.createCriteria();
    this.addConditionForAll(criteria);
    return this.removeList(criteria);
  }

  /**
   * Getter de l'entity manager g�n�rique du projet
   * @return L'entity manager g�n�rique du projet
   */
  protected EntityManager getEntityManager()
  {
    return this.entityManager;
  }
  /**
   * Getter de la classe de l'entit� g�r�e par le DAO
   * @return La classe de l'entit� g�r�e par le DAO
   */
  public Class<ENTITY> getEntityClass()
  {
    return this.entityClass;
  }
  /**
   * Getter du constructeur de crit�re
   * @return Le constructeur de crit�res
   */
  protected CriteriaBuilder getCriteriaBuilder()
  {
    return this.getEntityManager().getCriteriaBuilder();
  }
  /**
   * Permet de cr�er une nouvelle d�finition de crit�res
   * @return La nouvelle d�finition de crit�res cr��e
   */
  protected CriteriaQuery<ENTITY> createCriteria()
  {
    return this.createCriteria(this.getEntityClass());
  }
  /**
   *
   * TODO A COMMENTER
   * @param entityClass TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected <T> CriteriaQuery<T> createCriteria(Class<T> entityClass)
  {
    return this.getCriteriaBuilder().createQuery(entityClass);
  }
  /**
   * Permet de cr�er une nouvelle requ�te correspondant aux crit�res en argument
   * @param criteria Crit�res pour la construction de la requ�te
   * @return La nouvelle requ�te cr��e
   */
  protected TypedQuery<ENTITY> createQuery(CriteriaQuery<ENTITY> criteria)
  {
    return this.getEntityManager().createQuery(criteria);
  }

  /**
   * Setter de la classe de l'entit� g�r�e par le DAO
   * @param entityClass Classe de l'entit� g�r�e par le DAO � positionner
   */
  private void setEntityClass(Class<ENTITY> entityClass)
  {
    this.entityClass = entityClass;
  }
}
