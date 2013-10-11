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
 * @param <ENTITY> Entit� sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entit� sujet du test<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface IBid4WinDaoStub<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
{
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� non nulle en fonction de
   * son identifiant
   * @param id Identifiant de l'entit� � r�cup�rer
   * @return L'unique entit� non nulle r�cup�r�e en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  public ENTITY getById(ID id) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� potentiellement nulle en
   * fonction de son identifiant
   * @param id Identifiant de l'entit� � r�cup�rer
   * @return L'unique entit� potentiellement nulle r�cup�r�e en fonction de son
   * identifiant
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public ENTITY findById(ID id) throws PersistenceException;
  /**
   * Cette fonction permet de r�cup�rer les entit�s dont les identifiants sont
   * pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � r�cup�rer
   * @return Les entit� correspondant aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'un des identifiants en argument
   */
  public Bid4WinMap<ID, ENTITY> getById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des entit�s
   * @return La liste compl�te des entit�s
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<ENTITY> findAll() throws PersistenceException;
  /**
   * Cette fonction permet d'ajouter l'entit� en argument
   * @param entity Entit� � ajouter
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public ENTITY add(ENTITY entity) throws PersistenceException;
  /**
   * Cette fonction permet de modifier l'entit� en argument
   * @param entity Entit� � modifier
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est lev�e ou si
   * l'entit� n'a jamais �t� persist�e auparavant
   * @throws NotPersistedEntityException Si l'entit� n'a jamais �t� persist�e
   * auparavant
   */
  public ENTITY update(ENTITY entity) throws PersistenceException, NotPersistedEntityException;
  /**
   * Cette fonction permet de supprimer l'entit� en argument
   * @param entity Entit� � supprimer
   * @return L'entit� supprim�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public ENTITY remove(ENTITY entity) throws PersistenceException;
  /**
   * Cette fonction permet de supprimer une entit� en fonction de son identifiant
   * @param id Identifiant de l'entit� � supprimer
   * @return L'entit� supprim�e en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  public ENTITY removeById(ID id) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de supprimer le set d'entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � supprimer
   * @return Le set d'entit�s supprim�es correspondants aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre supprim�e avec
   * l'un des identifiants en argument
   */
  public Bid4WinSet<ENTITY> removeListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException;
  /**
   * Cette fonction permet de supprimer la liste de toutes les entit�s
   * @return La liste de toutes les entit�s supprim�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<ENTITY> removeAll() throws PersistenceException;
}
