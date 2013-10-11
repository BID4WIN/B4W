package com.bid4win.persistence.dao.property;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.property.IPropertyRootAbstractDaoStub;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 * Stub du DAO pour les entit�s de la classe PropertyRoot avec impl�mentation
 * de l'interface IPropertyRootAbstractDao pour les tests g�n�riques<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("PropertyRootDaoStub")
@Scope("singleton")
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class PropertyRootDaoStub extends PropertyRootDao
       implements IPropertyRootAbstractDaoStub<PropertyRoot, Property>
{
  /////////////////////////////////////////////////////////////////////////////
  //////////// Red�finies pour �tre test�es en ajoutant la gestion ////////////
  ////////////  de la transaction et le chargement des relations   ////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#getRoot()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyRoot getRoot() throws PersistenceException
  {
    return super.getRoot().loadRelation();
  }
  /**
   *
   * TODO A COMMENTER
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_#update(com.bid4win.commons.persistence.entity.property.PropertyRootAbstract)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyRoot update(PropertyRoot root) throws PersistenceException
  {
    return super.update(root);
  }
  /////////////////////////////////////////////////////////////////////////////
  ////////////////////////// Ajout�es pour les tests //////////////////////////
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Cette fonction permet de r�cup�rer l'unique propri�t� racine en fonction de
   * son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyRoot getById(Integer id) throws PersistenceException
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
  public PropertyRoot findById(Integer id) throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
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
  public Bid4WinMap<Integer, PropertyRoot> getById(Bid4WinSet<Integer> idSet)
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
  public Bid4WinList<PropertyRoot> findAll() throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
  }
  /**
   *
   * TODO A COMMENTER
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyRoot add(PropertyRoot property) throws PersistenceException
  {
    return super.add(property);
  }
  /**
   * Cette fonction permet de supprimer l'entit� en argument
   * @param entity {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyRoot remove(PropertyRoot entity) throws PersistenceException
  {
    return super.remove(entity);
  }
  /**
   * Cette fonction permet de supprimer une entit� en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PropertyRoot removeById(Integer id) throws PersistenceException, NotFoundEntityException
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
  public Bid4WinSet<PropertyRoot> removeListById(Bid4WinSet<Integer> idSet)
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
  public Bid4WinList<PropertyRoot> removeAll() throws PersistenceException
  {
    return super.removeAll();
  }
}
