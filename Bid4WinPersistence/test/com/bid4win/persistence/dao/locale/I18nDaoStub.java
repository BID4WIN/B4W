package com.bid4win.persistence.dao.locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.property.IPropertyAbstractDaoStub;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.persistence.entity.locale.I18n_;

/**
 * Stub du DAO pour les entit�s de la classe I18n avec impl�mentation de l'interface
 * IPropertyAbstractDao pour les tests g�n�riques<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("I18nDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class I18nDaoStub extends I18nDao
       implements IPropertyAbstractDaoStub<I18n, I18nRoot>
{
  /////////////////////////////////////////////////////////////////////////////
  //////////// Red�finies pour �tre test�es en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @param i18n {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#add(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n add(I18n i18n) throws PersistenceException
  {
    return super.add(i18n);
  }
  /**
   *
   * TODO A COMMENTER
   * @param i18n {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#update(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n update(I18n i18n) throws PersistenceException
  {
    return super.update(i18n).loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param i18n {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_#remove(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n remove(I18n i18n) throws PersistenceException
  {
    return super.remove(i18n).loadRelation();
  }
  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajout�es pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de r�cup�rer l'unique propri�t� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n getById(Integer id) throws PersistenceException
  {
    return super.getById(id).loadRelation();
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
  public I18n findById(Integer id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
  }
  /**
   * Cette fonction permet de r�cup�rer la propri�t� unique correspondant � la cl�
   * en argument
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.IPropertyAbstractDaoStub#findOneByKey(java.lang.String)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n findOneByKey(String key) throws PersistenceException
  {
    I18n i18n = super.findOne(this.getCriteriaForKey(key));
    return (i18n != null ? i18n.loadRelation() : null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher la
   * propri�t� unique correspondant � la cl� en param�tre
   * @param key Cl� de la propri�t� � rechercher
   * @return Les crit�res permettant de rechercher la propri�t� unique correspondant
   * � la cl� en param�tre
   */
  protected CriteriaQuery<I18n> getCriteriaForKey(String key)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<I18n> criteria = this.createCriteria();
    Root<I18n> i18n_ = criteria.from(this.getEntityClass());
    Predicate condition = builder.equal(i18n_.get(I18n_.key), key);
    criteria.where(condition);
    return criteria;
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
  public Bid4WinMap<Integer, I18n> getById(Bid4WinSet<Integer> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
  }
  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des entit�s
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<I18n> findAll() throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
  }
  /**
   * Cette fonction permet de supprimer une propri�t� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public I18n removeById(Integer id) throws PersistenceException
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
  public Bid4WinSet<I18n> removeListById(Bid4WinSet<Integer> idSet)
         throws PersistenceException, NotFoundEntityException
  {
    return super.removeListById(idSet);
  }
  /**
   * Cette fonction permet de supprimer la liste de toutes les propri�t�s
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<I18n> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
}
