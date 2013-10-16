package com.bid4win.commons.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.dao.exception.NotUniqueEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.Bid4WinCriteriaList;
import com.bid4win.commons.persistence.request.Bid4WinPagination;
import com.bid4win.commons.persistence.request.Bid4WinRequest;
import com.bid4win.commons.persistence.request.Bid4WinRequestExecutor;
import com.bid4win.commons.persistence.request.Bid4WinResult;

/**
 * DAO générique du projet<BR>
 * <BR>
 * @param <ENTITY> Entité gérée par le DAO<BR>
 * @param <ID> Identifiant de l'entité gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinDao_<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
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
   * Cette fonction permet de vider le cache de session des modifications en attente
   * et de les envoyer à la base de données
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
      // Rafraîchit l'entité en la bloquant
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
      // Récupère en la bloquant l'entité en fonction de son identifiant
      ENTITY entity = this.getEntityManager().find(this.getEntityClass(), id,
                                                   LockModeType.PESSIMISTIC_WRITE);
      // Aucune entité n'existe avec cet identifiant
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
   * Cette fonction permet de verrouiller la liste d'entités correspondants à la
   * définition de requête en argument
   * @param request Définition de la requête permettant la récupération de la liste
   * d'entités à verrouiller
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected void lockList(Bid4WinRequest<ENTITY> request) throws PersistenceException
  {
    this.lockList(request.getCriteria(), request.getPagination());
  }
  /**
   *
   * TODO A COMMENTER
   * @param criteria TODO A COMMENTER
   * @param pagination TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected void lockList(Bid4WinCriteria<ENTITY> criteria,
                          Bid4WinPagination<ENTITY> pagination)
            throws PersistenceException
  {
    for(ENTITY entity : this.findList(criteria, pagination))
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
   * à la définition de requête en argument
   * @param criteria Définition des critères permettant la récupération de l'unique
   * entité choisie
   * @return L'unique entité non nulle correspondant à la définition de requête
   * en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune entité ne correspond à la définition
   * de requête en argument
   * @throws NotUniqueEntityException Si plusieurs entités correspondent à la définition
   * de requête en argument
   */
  protected ENTITY getOne(Bid4WinCriteria<ENTITY> criteria)
            throws PersistenceException, NotFoundEntityException, NotUniqueEntityException
  {
    // On exécute la requête et récupère son résultat
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
   * correspondant à la définition de requête en argument
   * @param criteria Définition des critères permettant la récupération de l'unique
   * entité choisie
   * @return L'unique entité potentiellement nulle correspondant à la définition
   * de requête en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotUniqueEntityException Si plusieurs entités correspondent à la définition
   * de requête en argument
   */
  @SuppressWarnings({"unchecked"})
  protected ENTITY findOne(Bid4WinCriteria<ENTITY> criteria)
            throws PersistenceException, NotUniqueEntityException
  {
    try
    {
      Bid4WinRequestExecutor<ENTITY> executor = new Bid4WinRequestExecutor<ENTITY>(
          this.getEntityManager(), this.getEntityClass(), criteria);
      List<ENTITY> result = executor.execute();
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
    catch(RuntimeException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités correspondant à la définition
   * de requête en argument
   * @param request Définition de la requête permettant la récupération de la liste
   * d'entités choisies
   * @return La liste d'entités correspondants à la définition de requête en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinResult<ENTITY> findList(Bid4WinRequest<ENTITY> request)
            throws PersistenceException
  {
    return this.findList(request.getCriteria(), request.getPagination());
  }
  /**
   * Cette fonction permet de récupérer la liste d'entités correspondant à la définition
   * de requête en argument
   * @param criteria Définition des critères permettant la récupération de la liste
   * d'entités choisies
   * @param pagination Définition de la pagination à utiliser pour la liste de résultats
   * @return La liste d'entités correspondants à la définition de requête en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  @SuppressWarnings({"unchecked"})
  protected Bid4WinResult<ENTITY> findList(Bid4WinCriteria<ENTITY> criteria,
                                           Bid4WinPagination<ENTITY> pagination)
            throws PersistenceException
  {
    Bid4WinCriteria<ENTITY> criteriaForAll = this.getCriteriaForAll();
    if(criteriaForAll != null)
    {
      if(criteria == null)
      {
        criteria = criteriaForAll;
      }
      else
      {
        criteria  = new Bid4WinCriteriaList<ENTITY>(criteria, criteriaForAll);
      }
    }
    Bid4WinRequestExecutor<ENTITY> executor = new Bid4WinRequestExecutor<ENTITY>(
        this.getEntityManager(), this.getEntityClass(), criteria, pagination);
    return executor.execute();
  }

  // TODO [] getDefaultOrders()

  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return La liste complète des entités
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinList<ENTITY> findAll() throws PersistenceException
  {
    // On exécute la requête et retourne son résultat
    return this.findAll(null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param pagination TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected Bid4WinList<ENTITY> findAll(Bid4WinPagination<ENTITY> pagination)
            throws PersistenceException
  {
    // On exécute la requête et retourne son résultat
    return this.findList(null, pagination);
  }

  /**
   * Cette fonction permet de construire les critères de récupération de la liste
   * complète des entités. Cette méthode retourne null par défaut et n'a besoin
   * d'être redéfinie que dans certains cas (chaînage d'entités ou table utilisée
   * pour différents types d'entités sans définition d'héritage hibernate)
   * @return La requête de récupération de la liste complète des entités
   */
  protected Bid4WinCriteria<ENTITY> getCriteriaForAll()
  {
    return null;
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
   * implique que l'entité doit avoir défini son champ updateForce comme persisté
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
  protected Bid4WinSet<ENTITY> removeListById(Bid4WinSet<ID> idSet)
            throws PersistenceException, NotFoundEntityException
  {
    Bid4WinSet<ENTITY> result = new Bid4WinSet<ENTITY>(idSet.size());
    for(ID id : idSet)
    {
      result.add(this.removeById(id));
    }
    return result;
  }
  /**
   * Cette fonction permet de supprimer la liste d'entités correspondants à la définition
   * de requête en argument
   * @param request Définition de la requête permettant la récupération de la liste
   * d'entités à supprimer
   * @return La liste d'entités supprimées correspondants à la définition de requête
   * en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  protected Bid4WinResult<ENTITY> removeList(Bid4WinRequest<ENTITY> request)
            throws PersistenceException
  {
    return this.removeList(request.getCriteria(), request.getPagination());
  }
  /**
   *
   * TODO A COMMENTER
   * @param criteria TODO A COMMENTER
   * @param pagination TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected Bid4WinResult<ENTITY> removeList(Bid4WinCriteria<ENTITY> criteria,
                                             Bid4WinPagination<ENTITY> pagination)
            throws PersistenceException
  {
    Bid4WinResult<ENTITY> list = this.findList(criteria, pagination);
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
    return this.removeAll(null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param pagination TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected Bid4WinList<ENTITY> removeAll(Bid4WinPagination<ENTITY> pagination)
            throws PersistenceException
  {
    // On exécute la requête et retourne son résultat
    return this.removeList(null, pagination);
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
   * Setter de la classe de l'entité gérée par le DAO
   * @param entityClass Classe de l'entité gérée par le DAO à positionner
   */
  private void setEntityClass(Class<ENTITY> entityClass)
  {
    this.entityClass = entityClass;
  }
}
