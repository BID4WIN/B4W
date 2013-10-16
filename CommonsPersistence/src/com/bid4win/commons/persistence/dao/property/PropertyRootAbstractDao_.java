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
 * DAO g�n�rique pour les entit�s de la classe PropertyRootAbstract<BR>
 * <BR>
 * @param <PROPERTY_ROOT> Doit d�finir la classe des propri�t�s racines g�r�es<BR>
 * @param <PROPERTY> Doit d�finir la classe des propri�t�s g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyRootAbstractDao_<PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                               PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>>
       extends Bid4WinDao_<PROPERTY_ROOT, Integer>
{
  /**
   * Constructeur
   * @param propertyClass Classe de la propri�t� racine g�r�e par le DAO
   */
  protected PropertyRootAbstractDao_(Class<PROPERTY_ROOT> propertyClass)
  {
    super(propertyClass);
  }

  /**
   * Cette fonction permet de remonter l'unique propri�t� racine existante et de
   * la cr�er le cas �ch�ant
   * @return L'unique propri�t� racine existante (ou cr��e le cas �ch�ant)
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * Cette fonction permet de modifier la propri�t� racine en argument. La propri�t�
   * racine sera modifi�e m�me si elle est identique via un for�age
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY_ROOT update(PROPERTY_ROOT root) throws PersistenceException
  {
    // Force la modification de la propri�t� racine en param�tre
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
   * Cette fonction doit d�finie la cl� unique de la racine des propri�t�s g�r�es
   * @return La cl� unique de la racine des propri�t�s g�r�es
   */
  public abstract int getUniqueId();
  /**
   * Cette fonction doit cr�er le bon type de racine des propri�t�s g�r�es
   * @return La racine des propri�t�s g�r�es du bon type
   */
  protected abstract PROPERTY_ROOT createPropertyRoot();
}
