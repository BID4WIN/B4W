package com.bid4win.commons.persistence.dao;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ID> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4winTestInitializer_<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID,
                                              ACCOUNT extends AccountAbstract<ACCOUNT>,
                                              GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinEntityTester<ACCOUNT, GENERATOR>
{
  /** Liste d'identifiants des entités utilisées pour les tests */
  private Bid4WinList<ID> idList = new Bid4WinList<ID>();

  /**
   * Doit définir le DAO de gestion des entités
   * @return Le DAO de gestion des entités à utiliser
   */
  protected abstract IBid4WinDaoStub<ENTITY, ID> getDao();

  /**
   * Getter du nombre d'entités utilisés pour les tests
   * @return Le nombre d'entités utilisés pour les tests
   */
  public int getNb()
  {
    return this.idList.size();
  }
  /**
   * Getter de l'identifiant de l'entité utilisée pour les tests
   * @return L'identifiant de l'entité utilisée pour les tests
   */
  public ID getId()
  {
    return this.getId(0);
  }
  /**
   * Getter de l'identifiant de l'entité utilisée pour les tests positionnée à l'
   * index en argument
   * @param index Index de l'entité dont il faut remonter l'identifiant
   * @return L'identifiant de l'entité utilisée pour les tests positionnée à l'
   * index en argument
   */
  public ID getId(int index)
  {
    return this.idList.get(index);
  }
  /**
   * Getter du set d'identifiants des entités utilisées pour les tests
   * @return Le set d'identifiants des entités utilisées pour les tests
   */
  private Bid4WinSet<ID> getIdSet()
  {
    return new Bid4WinSet<ID>(this.idList);
  }
  /**
   * Ajoute un identifiant d'entité utilisée pour les tests
   * @param id Identifiant de l'entité utilisée pour les tests
   * @return La position de l'identifiant ajouté dans la liste
   */
  private int addId(ID id)
  {
    this.idList.add(id);
    return this.idList.size() - 1;
  }
  /**
   * Retire un identifiant d'entité utilisée pour les tests
   * @param id Identifiant de l'entité utilisée pour les tests
   */
  private void removeId(ID id)
  {
    this.idList.remove(id);
  }
  /**
   * Cette méthode permet de récupérer l'entité positionnée à l'index en argument
   * @param index Index de l'entité à récupérer
   * @return L'entité positionnée à l'index en argument
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public ENTITY getEntity(int index) throws PersistenceException
  {
    return this.getDao().getById(this.getId(index));
  }
  /**
   * Getter du set des entités utilisées pour les tests
   * @return Le set des entités utilisées pour les tests
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinSet<ENTITY> getEntitySet() throws PersistenceException
  {
    return new Bid4WinSet<ENTITY>(this.getDao().getById(this.getIdSet()).values());
  }

  /**
   * Cette méthode permet d'ajouter une entité pour les tests
   * @param entity Entité à ajouter pour les tests
   * @return L'index de l'entité ajoutée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected int add(ENTITY entity) throws PersistenceException
  {
    return this.addId(this.getDao().add(entity).getId());
  }
  /**
   *
   * TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public ENTITY update(ENTITY entity) throws PersistenceException
  {
    return this.getDao().update(entity);
  }
  /**
   * Cette méthode permet de supprimer l'entité dont l'identifiant est en argument
   * @param id Identifiant de l'entité à supprimer
   * @throws PersistenceException Si une exception non attendue est levée
   */
  private void remove(ID id) throws PersistenceException
  {
    try
    {
      this.remove(this.getDao().getById(id));
    }
    catch(NotFoundEntityException ex)
    {
      this.removeId(id);
    }
  }
  /**
   * Cette méthode permet de supprimer l'entité en argument
   * @param entity Entité à supprimer
   * @throws PersistenceException Si une exception non attendue est levée
   */
  protected void remove(ENTITY entity) throws PersistenceException
  {
    this.getDao().removeById(entity.getId());
    this.removeId(entity.getId());
  }

  /**
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract ENTITY createEntity(int index) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  public void setUp(int nb) throws Exception
  {
    try
    {
      // Crée les comptes utilisateurs par défaut
      for(int i = 0 ; i < nb ; i++)
      {
        this.add(this.createEntity(i));
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#tearDown()
   */
  @Override
  public void tearDown() throws Exception
  {
    super.tearDown();
    try
    {
      // Supprime toutes les entités créées pour les tests
      for(ID id : this.getIdSet())
      {
        this.remove(id);
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }
}
