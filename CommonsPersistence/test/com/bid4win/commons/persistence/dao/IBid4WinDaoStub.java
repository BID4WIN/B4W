package com.bid4win.commons.persistence.dao;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entité sujet du test<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IBid4WinDaoStub<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
{
  /**
   * Cette fonction permet de récupérer l'unique entité non nulle en fonction de
   * son identifiant
   * @param id Identifiant de l'entité à récupérer
   * @return L'unique entité non nulle récupérée en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'identifiant en argument
   */
  public ENTITY getById(ID id) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id Identifiant de l'entité à récupérer
   * @return L'unique entité potentiellement nulle récupérée en fonction de son
   * identifiant
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public ENTITY findById(ID id) throws PersistenceException;
  /**
   * Cette fonction permet de récupérer les entités dont les identifiants sont
   * précisés en argument
   * @param idSet Set d'identifiants des entités à récupérer
   * @return Les entité correspondant aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'un des identifiants en argument
   */
  public Bid4WinMap<ID, ENTITY> getById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return La liste complète des entités
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<ENTITY> findAll() throws PersistenceException;
  /**
   * Cette fonction permet d'ajouter l'entité en argument
   * @param entity Entité à ajouter
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public ENTITY add(ENTITY entity) throws PersistenceException;
  /**
   * Cette fonction permet de modifier l'entité en argument
   * @param entity Entité à modifier
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est levée ou si
   * l'entité n'a jamais été persistée auparavant
   * @throws NotPersistedEntityException Si l'entité n'a jamais été persistée
   * auparavant
   */
  public ENTITY update(ENTITY entity) throws PersistenceException, NotPersistedEntityException;
  /**
   * Cette fonction permet de supprimer l'entité en argument
   * @param entity Entité à supprimer
   * @return L'entité supprimée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public ENTITY remove(ENTITY entity) throws PersistenceException;
  /**
   * Cette fonction permet de supprimer une entité en fonction de son identifiant
   * @param id Identifiant de l'entité à supprimer
   * @return L'entité supprimée en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'identifiant en argument
   */
  public ENTITY removeById(ID id) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de supprimer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet Set d'identifiants des entités à supprimer
   * @return Le set d'entités supprimées correspondants aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être supprimée avec
   * l'un des identifiants en argument
   */
  public Bid4WinSet<ENTITY> removeListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de supprimer la liste de toutes les entités
   * @return La liste de toutes les entités supprimées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<ENTITY> removeAll() throws PersistenceException;
}
