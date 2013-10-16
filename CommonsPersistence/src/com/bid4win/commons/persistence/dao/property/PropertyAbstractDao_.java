package com.bid4win.commons.persistence.dao.property;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract_Fields;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO générique pour les entités de la classe PropertyAbstract<BR>
 * <BR>
 * @param <PROPERTY> Doit définir la classe des propriétés gérées<BR>
 * @param <PROPERTY_ROOT> Doit définir la classe des propriétés racines gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractDao_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                          PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>>
       extends Bid4WinDao_<PROPERTY, Integer>
{
  /**
   * Constructeur
   * @param propertyClass Classe de la propriété gérée par le DAO
   */
  protected PropertyAbstractDao_(Class<PROPERTY> propertyClass)
  {
    super(propertyClass);
  }

  /**
   * Cette fonction permet d'ajouter la propriété en argument en mettant à jour
   * soit sa propriété racine, soit sa propriété parent. Attention cependant, la
   * référence de la propriété parent (ou racine) n'est pas mise à jour et n'est
   * donc plus valide
   * @param property {@inheritDoc}
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante ou si on ajoute une propriété sans parent ni racine
   * ou si celle-ci n'a jamais été persistée
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY add(PROPERTY property) throws PersistenceException
  {
    // Ajoute la propriété
    property = super.add(property);
    // Met à jour sa propriété parent
    if(property.hasProperty())
    {
      this.update(property.getProperty());
    }
    else if(!property.hasRoot())
    {
      throw new PersistenceException("Could not add property without parent or root");
    }
    // Met à jour la racine de l'arbre de propriétés pour augmenter sa version
    this.getRootDao().update(property.getTreeRoot());
    // Retourne la propriété ajoutée
    return property;
  }

  /**
   * Cette fonction permet de modifier la propriété en argument mais ne doit pas
   * être utilisée pour ajouter des sous-propriétés
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante ou si on modifie une propriété sans parent ni racine
   * @throws NotPersistedEntityException Si la propriété n'a jamais été persistée
   * auparavant
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY update(PROPERTY property) throws PersistenceException, NotPersistedEntityException
  {
    if(!property.hasProperty() && !property.hasRoot())
    {
      throw new PersistenceException("Could not update property without parent or root");
    }
    // Met à jour la racine de l'arbre de propriétés pour augmenter sa version
    this.getRootDao().update(property.getTreeRoot());
    // Modifie la propriété en paramètre
    return super.update(property);
  }

  /**
   * Cette fonction permet de supprimer la propriété en argument
   * @param property {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY remove(PROPERTY property) throws PersistenceException
  {
    try
    {
      // Met à jour la propriété parent (ou racine) en dé-référençant la propriété
      // à supprimer
      if(property.hasProperty())
      {
        // Met à jour la racine de l'arbre de propriétés pour augmenter sa version
        this.getRootDao().update(property.getTreeRoot());
        this.update(property.unlinkFromProperty());
      }
      else if(property.hasRoot())
      {
        this.getRootDao().update(property.unlinkFromRoot());
      }
    }
    catch(Bid4WinException ex)
    {
      throw new PersistenceException(ex);
    }
    // Supprime la propriété en paramètre
    return super.remove(property);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getCriteriaForAll()
   */
  @Override
  protected Bid4WinCriteria<PROPERTY> getCriteriaForAll()
  {
    return new Bid4WinData<PROPERTY, Integer>(PropertyAbstract_Fields.ROOT_ID_JOINED,
                                              this.getRootDao().getUniqueId());
  }

  /**
   * Cette fonction permet d'ajouter des conditions pour la récupération de la
   * liste complète des propriétés
   * @param criteria {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#addConditionForAll(javax.persistence.criteria.CriteriaQuery)
   */
  /*@Override
  protected final Root<PROPERTY> addConditionForAll(CriteriaQuery<PROPERTY> criteria)
  {
    Root<PROPERTY> property_ = super.addConditionForAll(criteria);
    criteria.where(this.getFirstLevelCondition(property_));
    return property_;
  }

  /**
   * Cette méthode permet de définir la condition à utiliser pour filter uniquement
   * les propriétés de premier niveau, c'est à dire rattachées directement à la
   * racine qui leur correspond
   * @param property_ TODO A COMMENTER
   * @return La condition à utiliser pour filter uniquement les propriétés de premier
   * niveau
   */
  /*protected Predicate getFirstLevelCondition(Root<PROPERTY> property_)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();
    Predicate condition = builder.equal(
        property_.get(PropertyAbstract_.root).get(PropertyRootAbstract_.id),
        this.getRootDao().getUniqueId());
    return condition;
  }*/


  /**
   * Getter du DAO des propriétés racines
   * @return Le DAO des propriétés racines
   */
  public abstract PropertyRootAbstractDao_<PROPERTY_ROOT, PROPERTY> getRootDao();
}
