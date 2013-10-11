package com.bid4win.commons.persistence.dao;

import org.junit.After;
import org.junit.Before;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entité sujet du test<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinDaoTester<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>,
                                       GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /**
   * Getter du DAO à tester
   * @return Le DAO à tester
   */
  protected abstract IBid4WinDaoStub<ENTITY, ID> getDao();

  /**
   * Cette fonction permet de récupérer l'unique entité non nulle en fonction de
   * son identifiant
   * @param id Identifiant de l'entité à récupérer
   * @return L'unique entité non nulle récupérée en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'identifiant en argument
   */
  protected ENTITY getById(ID id) throws PersistenceException, NotFoundEntityException
  {
    return this.getDao().getById(id);
  }
  /**
   * Cette fonction permet de récupérer l'unique entité potentiellement nulle en
   * fonction de son identifiant
   * @param id Identifiant de l'entité à récupérer
   * @return L'unique entité potentiellement nulle récupérée en fonction de son
   * identifiant
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected ENTITY findById(ID id) throws PersistenceException
  {
    return this.getDao().findById(id);
  }
  /**
   * Cette fonction permet de récupérer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet Set d'identifiants des entités à récupérer
   * @return Le set d'entités correspondant aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'un des identifiants en argument
   */
  protected Bid4WinSet<ENTITY> getListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException
  {
    return new Bid4WinSet<ENTITY>(this.getDao().getById(idSet).values());
  }
  /**
   * Cette fonction permet de récupérer la liste complète des entités
   * @return La liste complète des entités
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected Bid4WinList<ENTITY> findAll() throws PersistenceException
  {
    return this.getDao().findAll();
  }
  /**
   * Cette fonction permet d'ajouter l'entité en argument
   * @param entity Entité à ajouter
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected ENTITY add(ENTITY entity) throws PersistenceException
  {
    return this.getDao().add(entity);
  }
  /**
   * Cette fonction permet de modifier l'entité en argument
   * @param entity Entité à modifier
   * @return L'entité gérée par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est levée ou si
   * l'entité n'a jamais été persistée auparavant
   * @throws NotPersistedEntityException Si l'entité n'a jamais été persistée
   * auparavant
   */
  protected ENTITY update(ENTITY entity) throws PersistenceException, NotPersistedEntityException
  {
    return this.getDao().update(entity);
  }
  /**
   * Cette fonction permet de supprimer l'entité en argument
   * @param entity Entité à supprimer
   * @return L'entité supprimée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected ENTITY remove(ENTITY entity) throws PersistenceException
  {
    return this.getDao().remove(entity);
  }
  /**
   * Cette fonction permet de supprimer une entité en fonction de son identifiant
   * @param id Identifiant de l'entité à supprimer
   * @return L'entité supprimée en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être récupérée avec
   * l'identifiant en argument
   */
  protected ENTITY removeById(ID id) throws PersistenceException, NotFoundEntityException
  {
    return this.getDao().removeById(id);
  }
  /**
   * Cette fonction permet de supprimer le set d'entités dont les identifiants
   * sont précisés en argument
   * @param idSet Set d'identifiants des entités à supprimer
   * @return Le set d'entités supprimées correspondants aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est levée
   * @throws NotFoundEntityException Si aucune entité n'a pu être supprimée avec
   * l'un des identifiants en argument
   */
  protected Bid4WinSet<ENTITY> removeListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException
  {
    return this.getDao().removeListById(idSet);
  }
  /**
   * Cette fonction permet de supprimer la liste de toutes les entités
   * @return La liste de toutes les entités supprimées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected Bid4WinList<ENTITY> removeAll() throws PersistenceException
  {
    return this.getDao().removeAll();
  }

  /**
   * Test setup method
   * @throws Exception Issue not expected during test setup
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    try
    {
      // Retire toutes les entités du type de celles sujet du test
      this.removeAll();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }
  /**
   * Test teardown method
   * @throws Exception Issue not expected during test teardown
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    try
    {
      // Retire toutes les entités du type de celles sujet du test
      this.removeAll();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }
}
