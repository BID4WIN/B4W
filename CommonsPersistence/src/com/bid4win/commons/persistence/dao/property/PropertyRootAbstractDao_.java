package com.bid4win.commons.persistence.dao.property;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract_Fields;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO générique pour les entités de la classe PropertyRootAbstract<BR>
 * <BR>
 * @param <PROPERTY_ROOT> Doit définir la classe des propriétés racines gérées<BR>
 * @param <PROPERTY> Doit définir la classe des propriétés gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyRootAbstractDao_<PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                               PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>>
       extends Bid4WinDao_<PROPERTY_ROOT, Integer>
{
  /**
   * Constructeur
   * @param propertyClass Classe de la propriété racine gérée par le DAO
   */
  protected PropertyRootAbstractDao_(Class<PROPERTY_ROOT> propertyClass)
  {
    super(propertyClass);
  }

  /**
   * Cette fonction permet de remonter l'unique propriété racine existante et de
   * la créer le cas échéant
   * @return L'unique propriété racine existante (ou créée le cas échéant)
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public PROPERTY_ROOT getRoot() throws PersistenceException
  {
    try
    {
      return this.getById(this.getUniqueId());
    }
    catch(NotFoundEntityException ex)
    {
      return this.add(this.createPropertyRoot());
    }
  }
  /**
   * Cette fonction permet de modifier la propriété racine en argument. La propriété
   * racine sera modifiée même si elle est identique via un forçage
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY_ROOT update(PROPERTY_ROOT root) throws PersistenceException
  {
    // Force la modification de la propriété racine en paramètre
    return super.update(root.forceUpdate());
  }

  /**
  *
  * TODO A COMMENTER
  * @return {@inheritDoc}
  * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getCriteriaForAll()
  */
 @Override
 protected Bid4WinCriteria<PROPERTY_ROOT> getCriteriaForAll()
 {
   return new Bid4WinData<PROPERTY_ROOT, Integer>(PropertyRootAbstract_Fields.ID,
                                                  this.getUniqueId());
 }

  /**
   *
   * TODO A COMMENTER
   * @param criteria {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#addConditionForAll(javax.persistence.criteria.CriteriaQuery)
   */
/*  @Override
  protected Root<PROPERTY_ROOT> addConditionForAll(CriteriaQuery<PROPERTY_ROOT> criteria)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    Root<PROPERTY_ROOT> propertyRoot_ = super.addConditionForAll(criteria);
    Path<Integer> id_ = propertyRoot_.get(PropertyRootAbstract_.id);
    Predicate condition = builder.equal(id_, this.getUniqueId());
    criteria.where(condition);
    return propertyRoot_;
  }*/
  /**
   * Cette fonction doit définie la clé unique de la racine des propriétés gérées
   * @return La clé unique de la racine des propriétés gérées
   */
  public abstract int getUniqueId();
  /**
   * Cette fonction doit créer le bon type de racine des propriétés gérées
   * @return La racine des propriétés gérées du bon type
   */
  protected abstract PROPERTY_ROOT createPropertyRoot();
}
