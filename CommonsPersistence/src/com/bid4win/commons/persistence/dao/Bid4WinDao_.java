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
 * DAO générique du projet<BR>
 * <BR>
 * @param <ENTITY> Entité gérée par le DAO<BR>
 * @param <ID> Identifiant de l'entité gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDao_ <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
{
  /** Entity Manager générique du projet */
  // Annotation pour la persistence
  @PersistenceContext(name = "Bid4WinEntityManagerFactory")
  private EntityManager entityManager;

  /** Classe de l'entité gérée par le DAO */
  private Class<ENTITY> entityClass;

  /**
   * Constructeur
   * @param entityClass Classe de l'entité gérée par le DAO
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
   * Cette fonction permet de verrouiller l'entité en paramètre et de récupérer
   * son dernier état persisté
   * @param entity Entité à verrouiller
   * @return L'entité verrouillée dans son dernier état persisté
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de verrouiller l'entité dont l'identifiant est passé
   * en paramètre
   * @param id Identifiant de l'entité à verrouiller
   * @return L'entité verrouillée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
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
   * Cette fonction permet de verrouiller la liste d'entités correspondants aux
   * critères en argument
   * @param criteria Critères permettant la récupération de la liste d'entités à
   * verrouiller
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette méthode permet de rafraîchir l'état de l'entité en paramètre
   * @param entity Entité à rafraîchir
   * @return L'entité rafraîchie
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de récupérer l'unique entité non nulle en fonction de
   * son identifiant
   * @param id Identifiant de l'entité à récupérer
   * @return L'unique entité non nulle récupérée en fonction de son identifiant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
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
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id Identifiant de l'entité à récupérer
   * @return L'unique entité potentiellement nulle récupérée en fonction de son
   * identifiant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de récupérer les entités dont les identifiants sont
   * précisés en argument
   * @param idSet Set d'identifiants des entités à récupérer
   * @return Les entités correspondant aux identifiants en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
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
   * Cette fonction permet de récupérer les potentielles entités dont les identifiants
   * sont précisés en argument
   * @param idSet Set d'identifiants des entités à récupérer
   * @return Les potentielles entités correspondant aux identifiants en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de récupérer l'unique entité non nulle correspondant
   * aux critères en argument
   * @param criteria Critères permettant la récupération de l'unique entité choisie
   * @return L'unique entité non nulle correspondant aux critères en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité ne correspond aux critères
   * en argument
   * @throws NotUniqueEntityException Si plusieurs entités correspondent aux critères
   * en argument
   */
  protected ENTITY getOne(CriteriaQuery<ENTITY> criteria)
            throws PersistenceException, NotFoundEntityException, NotUniqueEntityException
  {
    // On crée la requête et on l'execute
    ENTITY result = this.findOne(criteria);
    // Le requête doit avoir remonté un élément
    if(result == null)
    {
      throw new NotFoundEntityException(this.getEntityClass());
    }
    return result;
  }
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle
   * correspondant aux critères en argument
   * @param criteria Critères permettant la récupération de l'unique entité choisie
   * @return L'unique entité potentiellement nulle correspondant aux critères en
   * argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotUniqueEntityException Si plusieurs entités correspondent aux critères
   * en argument
   */
  protected ENTITY findOne(CriteriaQuery<ENTITY> criteria)
            throws PersistenceException, NotUniqueEntityException
  {
    // On crée la requête et on l'execute
    Bid4WinList<ENTITY> result = this.findList(criteria);
    // Le requête ne doit pas avoir remonté plus d'un élément
    if(result.size() > 1)
    {
      throw new NotUniqueEntityException(this.getEntityClass());
    }
    // On retourne son résultat
    if(result.size() == 0)
    {
      return null;
    }
    return result.get(0);
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités correspondant aux critères
   * en argument
   * @param criteria Critères permettant la récupération de la liste d'entités
   * choisies
   * @return La liste d'entités correspondants aux critères en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> findList(CriteriaQuery<ENTITY> criteria) throws PersistenceException
  {
    try
    {
      // On crée la requête
      TypedQuery<ENTITY> query = this.createQuery(criteria);
      // On exécute la requête et retourne son résultat
      return new Bid4WinList<ENTITY>(query.getResultList(), true);
    }
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return La liste complète des entités
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> findAll() throws PersistenceException
  {
    // On crée les critères de la requête
    CriteriaQuery<ENTITY> criteria = this.createCriteria();
    this.addConditionForAll(criteria);
    // On exécute la requête et retourne son résultat
    return this.findList(criteria);
  }
  /**
   * Cette fonction permet d'ajouter des conditions pour la récupération de la
   * liste complète des entités
   * @param criteria Critères auxquelles il faut ajouter des conditions si besoin
   */
  protected Root<ENTITY> addConditionForAll(CriteriaQuery<ENTITY> criteria)
  {
    // Par défaut, aucun critère n'est nécessaire
    return criteria.from(this.getEntityClass());
  }

  /**
   * Cette fonction permet d'ajouter l'entité en argument
   * @param entity Entité à ajouter
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de modifier l'entité en argument
   * @param entity Entité à modifier
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotPersistedEntityException Si l'entité n'a jamais été persistée
   * auparavant
   */
  protected ENTITY update(ENTITY entity) throws PersistenceException, NotPersistedEntityException
  {
    try
    {
      // On ne peut pas modifier une entité non persistée
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
   * Cette fonction permet de forcer la modification de l'entité en argument. Cela
   * implique que l'entité doit avoir défini son champs updateForce comme persisté
   * @param entity Entité dont la modification doit être forcée
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotPersistedEntityException Si l'entité n'a jamais été persistée
   * auparavant
   */
  protected ENTITY forceUpdate(ENTITY entity) throws PersistenceException, NotPersistedEntityException
  {
    return this.update(entity.forceUpdate());
  }
  /**
   * Cette fonction permet de supprimer l'entité en argument
   * @param entity Entité à supprimer
   * @return L'entité supprimée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de supprimer la liste d'entités en argument
   * @param entityList Liste d'entités à supprimer
   * @return La liste d'entités supprimées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de supprimer une entité en fonction de son identifiant
   * @param id Identifiant de l'entité à supprimer
   * @return L'entité supprimée en fonction de son identifiant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'identifiant en argument
   */
  protected ENTITY removeById(ID id) throws PersistenceException, NotFoundEntityException
  {
    ENTITY entity = this.getById(id);
    this.remove(entity);
    return entity;
  }
  /**
   * Cette fonction permet de supprimer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet Set d'identifiants des entités à supprimer
   * @return Le set d'entités supprimées correspondants aux identifiants en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité n'a pu être supprimée avec
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
   * Cette fonction permet de supprimer la liste d'entités correspondants aux
   * critères en argument
   * @param criteria Critères permettant la récupération de la liste d'entités à
   * supprimer
   * @return La liste d'entités supprimées correspondants aux critères en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * Cette fonction permet de supprimer la liste de toutes les entités
   * @return La liste de toutes les entités supprimées
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> removeAll() throws PersistenceException
  {
    CriteriaQuery<ENTITY> criteria = this.createCriteria();
    this.addConditionForAll(criteria);
    return this.removeList(criteria);
  }

  /**
   * Getter de l'entity manager générique du projet
   * @return L'entity manager générique du projet
   */
  protected EntityManager getEntityManager()
  {
    return this.entityManager;
  }
  /**
   * Getter de la classe de l'entité gérée par le DAO
   * @return La classe de l'entité gérée par le DAO
   */
  public Class<ENTITY> getEntityClass()
  {
    return this.entityClass;
  }
  /**
   * Getter du constructeur de critère
   * @return Le constructeur de critères
   */
  protected CriteriaBuilder getCriteriaBuilder()
  {
    return this.getEntityManager().getCriteriaBuilder();
  }
  /**
   * Permet de créer une nouvelle définition de critères
   * @return La nouvelle définition de critères créée
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
   * Permet de créer une nouvelle requête correspondant aux critères en argument
   * @param criteria Critères pour la construction de la requête
   * @return La nouvelle requête créée
   */
  protected TypedQuery<ENTITY> createQuery(CriteriaQuery<ENTITY> criteria)
  {
    return this.getEntityManager().createQuery(criteria);
  }

  /**
   * Setter de la classe de l'entité gérée par le DAO
   * @param entityClass Classe de l'entité gérée par le DAO à positionner
   */
  private void setEntityClass(Class<ENTITY> entityClass)
  {
    this.entityClass = entityClass;
  }
}
