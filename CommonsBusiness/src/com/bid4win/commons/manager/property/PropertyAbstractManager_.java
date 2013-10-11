package com.bid4win.commons.manager.property;

import java.util.Properties;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.manager.Bid4WinManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.property.PropertyAbstractDao_;
import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDao_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 * Manager de gestion générique des propriétés incluant leur gestion métier<BR>
 * <BR>
 * @param <PROPERTY> Définition du type de propriétés gérées par le manager<BR>
 * @param <PROPERTY_ROOT> Définition du type des propriétés racines gérées par le
 * manager<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractManager_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                               PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, PropertyAbstractManager_<PROPERTY, PROPERTY_ROOT, ACCOUNT>>
{
  /**
   * Cette méthode permet de récupérer l'unique propriété racine
   * @return L'unique propriété racine
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public PROPERTY_ROOT getRoot() throws PersistenceException
  {
    return this.getRootDao().getRoot();
  }
  /**
   * Cette méthode permet de récupérer une propriété en fonction de sa clé
   * @param key Clé de la propriété à récupérer
   * @return La propriété récupérée en fonction de sa clé
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété demandée est inexistante
   */
  public PROPERTY getProperty(String key) throws PersistenceException, UserException
  {
    // Recherche la propriété demandée
    PROPERTY_ROOT root = this.getRoot();
    PROPERTY property = root.getProperty(key);
    // Retourne la propriété demandée en vérifiant son existence
    return UtilObject.checkNotNull("property", property,
                                   root.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
  }

  /**
   * Cette méthode permet de modifier la valeur d'une propriété
   * @param key Clé de la propriété à modifier
   * @param value La nouvelle valeur de la propriété
   * @return La propriété une fois modifiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété à modifier est inexistante
   */
  public PROPERTY updateProperty(String key, String value)
         throws PersistenceException, UserException
  {
    // Récupère la propriété à modifier
    PROPERTY property = this.getProperty(key);
    // Modifie la propriété et la retourne
    property.defineValue(value);
    return this.getPropertyDao().update(property);
  }

  /**
   * Cette méthode doit permettre d'importer toutes les propriétés en paramètre,
   * celles déjà existantes voyant leur valeur mise à jour
   * @param properties Propriétés à importer
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si une clé est invalide
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   */
  public abstract void importProperties(Properties properties)
         throws PersistenceException, UserException, ModelArgumentException;

  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> {@inheritDoc}
   * @param <ID> {@inheritDoc}
   * @param entityClass {@inheritDoc}
   * @param idSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.manager.Bid4WinManager_#findById(java.lang.Class, com.bid4win.commons.core.collection.Bid4WinSet)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> findById(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
         throws PersistenceException
  {
    Bid4WinMap<ID, ENTITY> entityMap = super.findById(entityClass, idSet);
    if(entityClass.equals(this.getPropertyRootClass()) && idSet.contains(this.getPropertyRootId()))
    {
      entityMap.put((ID)this.getPropertyRootId(), (ENTITY)this.getRootDao().getRoot());
    }
    return entityMap;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Class<PROPERTY_ROOT> getPropertyRootClass()
  {
    return this.getRootDao().getEntityClass();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Integer getPropertyRootId()
  {
    return this.getRootDao().getUniqueId();
  }

  /**
   * Getter de la référence du DAO des propriétés
   * @return La référence du DAO des propriétés
   */
  protected abstract PropertyAbstractDao_<PROPERTY, PROPERTY_ROOT> getPropertyDao();
  /**
   * Getter de la référence du DAO des propriétés racines
   * @return La référence du DAO des propriétés racines
   */
  protected abstract PropertyRootAbstractDao_<PROPERTY_ROOT,PROPERTY> getRootDao();
}
