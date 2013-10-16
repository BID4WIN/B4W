package com.bid4win.commons.persistence.dao.foo;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoAutoID_;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;
import com.bid4win.commons.persistence.entity.foo.FooAbstract_Fields;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.Bid4WinPagination;
import com.bid4win.commons.persistence.request.Bid4WinResult;
import com.bid4win.commons.persistence.request.data.Bid4WinData;
import com.bid4win.commons.persistence.request.data.Bid4WinDataComparable;
import com.bid4win.commons.persistence.request.data.Bid4WinDataComparableDate;

/**
 * DAO pour les entit�s de la classe FooAbstract<BR>
 * <BR>
 * @param <FOO> Entit� g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public abstract class FooAbstractDaoSpring<FOO extends FooAbstract<FOO>>
       extends Bid4WinDaoAutoID_<FOO> implements IBid4WinDaoStub<FOO, Long>
{
  /**
   * Constructeur pour sp�cialisation
   * @param entityClass Class de l'entit� g�r�e par le DAO
   */
  protected FooAbstractDaoSpring(Class<FOO> entityClass)
  {
    super(entityClass);
  }

  /**
   *
   * TODO A COMMENTER
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#refresh(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public FOO refresh(FOO entity) throws PersistenceException
  {
    return super.refresh(entity);
  }

  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#lockById(java.lang.Object)
   */
  @Override
  public FOO lockById(Long id) throws PersistenceException
  {
    return super.lockById(id);
  }

  /**
   * Cette fonction permet de r�cup�rer l'unique entit� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public FOO getById(Long id) throws PersistenceException
  {
    return super.getById(id);
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� potentiellement nulle en
   * fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
   */
  @Override
  public FOO findById(Long id) throws PersistenceException
  {
    return super.findById(id);
  }
  /**
   * Cette fonction permet de r�cup�rer le set d'entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  public Bid4WinMap<Long, FOO> getById(Bid4WinSet<Long> idSet) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(idSet);
  }
  /**
   * Cette fonction permet de r�cup�rer l'unique entit� correspondant � la valeur
   * en argument
   * @param value Valeur de l'entit� unique � r�cup�rer
   * @return Entit� r�cup�r�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public FOO findOneByValue(String value) throws PersistenceException
  {
    return super.findOne(this.getValueData(value));
  }
  /**
   *
   * TODO A COMMENTER
   * @param criteria {@inheritDoc}
   * @param pagination {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findList(com.bid4win.commons.persistence.request.Bid4WinCriteria, com.bid4win.commons.persistence.request.Bid4WinPagination)
   */
  @Override
  public Bid4WinResult<FOO> findList(Bid4WinCriteria<FOO> criteria, Bid4WinPagination<FOO> pagination) throws PersistenceException
  {
    return super.findList(criteria, pagination);
  }
  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s correspondant � la valeur
   * en argument
   * @param value Valeur des entit�s � r�cup�rer
   * @param pagination TODO A COMMENTER
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinResult<FOO> findListByValue(String value, Bid4WinPagination<FOO> pagination) throws PersistenceException
  {
    return super.findList(this.getValueData(value), pagination);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s en fonction de leur valeur
   * @param value Valeur des entit�s � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de leur
   * valeur
   */
  protected Bid4WinData<FOO, String> getValueData(String value)
  {
    return new Bid4WinData<FOO, String>(FooAbstract_Fields.VALUE, value);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s correspondant au r�le
   * en argument
   * @param role R�le des entit�s � r�cup�rer
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinResult<FOO> findListByRole(Role role) throws PersistenceException
  {
    return super.findList(this.getRoleData(role), null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s en fonction de leur r�le
   * @param role R�le des entit�s � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de leur
   * role
   */
  protected Bid4WinData<FOO, Role> getRoleData(Role role)
  {
    return new Bid4WinData<FOO, Role>(FooAbstract_Fields.ROLE, role);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s correspondant � la date
   * de modification en argument. !!! Attention, c'est m�thode n'est sens�e retourner
   * aucun r�sultat car lors d'une requ�te de type CriteriaQuery, hibernate n'utilise
   * pas les potentieltype utilisateurs sur des donn�es de type Date
   * @param date Date de modification des entit�s � r�cup�rer
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinResult<FOO> findListByUpdateDate(Bid4WinDate date) throws PersistenceException
  {
    return super.findList(this.getUpdateDateData(date), null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s en fonction de leur date de modification
   * @param date Date de modification des entit�s � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de leur
   * date de modification
   */
  protected Bid4WinData<FOO, Bid4WinDate> getUpdateDateData(Bid4WinDate date)
  {
    return new Bid4WinDataComparable<FOO, Bid4WinDate>(FooAbstract_Fields.UPDATE_DATE, date);
  }
  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s correspondant � la date
   * en argument
   * @param date Date des entit�s � r�cup�rer
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinResult<FOO> findListByDate(Bid4WinDate date) throws PersistenceException
  {
    return super.findList(this.getDateData(date), null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s en fonction de leur date
   * @param date Date des entit�s � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de leur
   * date
   */
  protected Bid4WinDataComparableDate<FOO> getDateData(Bid4WinDate date)
  {
    return new Bid4WinDataComparableDate<FOO>(FooAbstract_Fields.DATE, date);
  }


  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des entit�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<FOO> findAll() throws PersistenceException
  {
    return super.findAll();
  }

  /**
   * Cette fonction permet d'ajouter l'entit� en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO add(FOO entity) throws PersistenceException
  {
    return super.add(entity);
  }
  /**
   * Cette fonction permet d'ajouter l'entit� en argument tout en simulant une
   * erreur et donc normalement un rollback de la transaction
   * @param entity Entit� � ajouter
   * @throws PersistenceException Exception lanc�e volontairement et normalement
   * � l'origine du rollback
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void addWithRollback(FOO entity) throws PersistenceException
  {
    this.add(entity);
    throw new PersistenceException();
  }

  /**
   * Cette fonction permet de modifier l'entit� en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO update(FOO entity) throws PersistenceException
  {
    return super.update(entity);
  }

  /**
   * Cette fonction permet de supprimer l'entit� en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO remove(FOO entity) throws PersistenceException
  {
    return super.remove(entity);
  }

  /**
   * Cette fonction permet de supprimer une entit� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public FOO removeById(Long id) throws PersistenceException
  {
    return super.removeById(id);
  }

  /**
   * Cette fonction permet de supprimer le set d'entit�s dont les identifiants
   * sont pr�cis�s en argument
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinSet<FOO> removeListById(Bid4WinSet<Long> idSet) throws PersistenceException, NotFoundEntityException
  {
    return super.removeListById(idSet);
  }

  /**
   * Cette fonction permet de supprimer la liste de toutes les entit�s
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<FOO> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
}
