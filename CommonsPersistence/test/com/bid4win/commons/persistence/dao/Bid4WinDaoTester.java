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
 * @param <ENTITY> Entit� sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entit� sujet du test<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinDaoTester<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>,
                                       GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /**
   * Getter du DAO � tester
   * @return Le DAO � tester
   */
  protected abstract IBid4WinDaoStub<ENTITY, ID> getDao();

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� non nulle en fonction de
   * son identifiant
   * @param id Identifiant de l'entit� � r�cup�rer
   * @return L'unique entit� non nulle r�cup�r�e en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  protected ENTITY getById(ID id) throws PersistenceException, NotFoundEntityException
  {
    return this.getDao().getById(id);
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� potentiellement nulle en
   * fonction de son identifiant
   * @param id Identifiant de l'entit� � r�cup�rer
   * @return L'unique entit� potentiellement nulle r�cup�r�e en fonction de son
   * identifiant
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  protected ENTITY findById(ID id) throws PersistenceException
  {
    return this.getDao().findById(id);
  }
  /**
   * Cette fonction permet de r�cup�rer le set d'entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � r�cup�rer
   * @return Le set d'entit�s correspondant aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'un des identifiants en argument
   */
  protected Bid4WinSet<ENTITY> getListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException
  {
    return new Bid4WinSet<ENTITY>(this.getDao().getById(idSet).values());
  }
  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des entit�s
   * @return La liste compl�te des entit�s
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  protected Bid4WinList<ENTITY> findAll() throws PersistenceException
  {
    return this.getDao().findAll();
  }
  /**
   * Cette fonction permet d'ajouter l'entit� en argument
   * @param entity Entit� � ajouter
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  protected ENTITY add(ENTITY entity) throws PersistenceException
  {
    return this.getDao().add(entity);
  }
  /**
   * Cette fonction permet de modifier l'entit� en argument
   * @param entity Entit� � modifier
   * @return L'entit� g�r�e par le manager et dont les changements seront suivis
   * @throws PersistenceException Si une exception non attendue est lev�e ou si
   * l'entit� n'a jamais �t� persist�e auparavant
   * @throws NotPersistedEntityException Si l'entit� n'a jamais �t� persist�e
   * auparavant
   */
  protected ENTITY update(ENTITY entity) throws PersistenceException, NotPersistedEntityException
  {
    return this.getDao().update(entity);
  }
  /**
   * Cette fonction permet de supprimer l'entit� en argument
   * @param entity Entit� � supprimer
   * @return L'entit� supprim�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  protected ENTITY remove(ENTITY entity) throws PersistenceException
  {
    return this.getDao().remove(entity);
  }
  /**
   * Cette fonction permet de supprimer une entit� en fonction de son identifiant
   * @param id Identifiant de l'entit� � supprimer
   * @return L'entit� supprim�e en fonction de son identifiant
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   */
  protected ENTITY removeById(ID id) throws PersistenceException, NotFoundEntityException
  {
    return this.getDao().removeById(id);
  }
  /**
   * Cette fonction permet de supprimer le set d'entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet Set d'identifiants des entit�s � supprimer
   * @return Le set d'entit�s supprim�es correspondants aux identifiants en argument
   * @throws PersistenceException Si une exception non attendue est lev�e
   * @throws NotFoundEntityException Si aucune entit� n'a pu �tre supprim�e avec
   * l'un des identifiants en argument
   */
  protected Bid4WinSet<ENTITY> removeListById(Bid4WinSet<ID> idSet) throws PersistenceException, NotFoundEntityException
  {
    return this.getDao().removeListById(idSet);
  }
  /**
   * Cette fonction permet de supprimer la liste de toutes les entit�s
   * @return La liste de toutes les entit�s supprim�es
   * @throws PersistenceException Si une exception non attendue est lev�e
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
      // Retire toutes les entit�s du type de celles sujet du test
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
      // Retire toutes les entit�s du type de celles sujet du test
      this.removeAll();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }
}
