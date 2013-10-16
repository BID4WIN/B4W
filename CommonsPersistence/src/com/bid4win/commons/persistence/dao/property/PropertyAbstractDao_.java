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
 * DAO g�n�rique pour les entit�s de la classe PropertyAbstract<BR>
 * <BR>
 * @param <PROPERTY> Doit d�finir la classe des propri�t�s g�r�es<BR>
 * @param <PROPERTY_ROOT> Doit d�finir la classe des propri�t�s racines g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyAbstractDao_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                          PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>>
       extends Bid4WinDao_<PROPERTY, Integer>
{
  /**
   * Constructeur
   * @param propertyClass Classe de la propri�t� g�r�e par le DAO
   */
  protected PropertyAbstractDao_(Class<PROPERTY> propertyClass)
  {
    super(propertyClass);
  }

  /**
   * Cette fonction permet d'ajouter la propri�t� en argument en mettant � jour
   * soit sa propri�t� racine, soit sa propri�t� parent. Attention cependant, la
   * r�f�rence de la propri�t� parent (ou racine) n'est pas mise � jour et n'est
   * donc plus valide
   * @param property {@inheritDoc}
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante ou si on ajoute une propri�t� sans parent ni racine
   * ou si celle-ci n'a jamais �t� persist�e
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY add(PROPERTY property) throws PersistenceException
  {
    // Ajoute la propri�t�
    property = super.add(property);
    // Met � jour sa propri�t� parent
    if(property.hasProperty())
    {
      this.update(property.getProperty());
    }
    else if(!property.hasRoot())
    {
      throw new PersistenceException("Could not add property without parent or root");
    }
    // Met � jour la racine de l'arbre de propri�t�s pour augmenter sa version
    this.getRootDao().update(property.getTreeRoot());
    // Retourne la propri�t� ajout�e
    return property;
  }

  /**
   * Cette fonction permet de modifier la propri�t� en argument mais ne doit pas
   * �tre utilis�e pour ajouter des sous-propri�t�s
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante ou si on modifie une propri�t� sans parent ni racine
   * @throws NotPersistedEntityException Si la propri�t� n'a jamais �t� persist�e
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
    // Met � jour la racine de l'arbre de propri�t�s pour augmenter sa version
    this.getRootDao().update(property.getTreeRoot());
    // Modifie la propri�t� en param�tre
    return super.update(property);
  }

  /**
   * Cette fonction permet de supprimer la propri�t� en argument
   * @param property {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public PROPERTY remove(PROPERTY property) throws PersistenceException
  {
    try
    {
      // Met � jour la propri�t� parent (ou racine) en d�-r�f�ren�ant la propri�t�
      // � supprimer
      if(property.hasProperty())
      {
        // Met � jour la racine de l'arbre de propri�t�s pour augmenter sa version
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
    // Supprime la propri�t� en param�tre
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
   * Cette fonction permet d'ajouter des conditions pour la r�cup�ration de la
   * liste compl�te des propri�t�s
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
   * Cette m�thode permet de d�finir la condition � utiliser pour filter uniquement
   * les propri�t�s de premier niveau, c'est � dire rattach�es directement � la
   * racine qui leur correspond
   * @param property_ TODO A COMMENTER
   * @return La condition � utiliser pour filter uniquement les propri�t�s de premier
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
   * Getter du DAO des propri�t�s racines
   * @return Le DAO des propri�t�s racines
   */
  public abstract PropertyRootAbstractDao_<PROPERTY_ROOT, PROPERTY> getRootDao();
}
