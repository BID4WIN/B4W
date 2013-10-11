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
 * Manager de gestion g�n�rique des propri�t�s incluant leur gestion m�tier<BR>
 * <BR>
 * @param <PROPERTY> D�finition du type de propri�t�s g�r�es par le manager<BR>
 * @param <PROPERTY_ROOT> D�finition du type des propri�t�s racines g�r�es par le
 * manager<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyAbstractManager_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                               PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinManager_<ACCOUNT, PropertyAbstractManager_<PROPERTY, PROPERTY_ROOT, ACCOUNT>>
{
  /**
   * Cette m�thode permet de r�cup�rer l'unique propri�t� racine
   * @return L'unique propri�t� racine
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public PROPERTY_ROOT getRoot() throws PersistenceException
  {
    return this.getRootDao().getRoot();
  }
  /**
   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�
   * @param key Cl� de la propri�t� � r�cup�rer
   * @return La propri�t� r�cup�r�e en fonction de sa cl�
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� demand�e est inexistante
   */
  public PROPERTY getProperty(String key) throws PersistenceException, UserException
  {
    // Recherche la propri�t� demand�e
    PROPERTY_ROOT root = this.getRoot();
    PROPERTY property = root.getProperty(key);
    // Retourne la propri�t� demand�e en v�rifiant son existence
    return UtilObject.checkNotNull("property", property,
                                   root.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
  }

  /**
   * Cette m�thode permet de modifier la valeur d'une propri�t�
   * @param key Cl� de la propri�t� � modifier
   * @param value La nouvelle valeur de la propri�t�
   * @return La propri�t� une fois modifi�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� � modifier est inexistante
   */
  public PROPERTY updateProperty(String key, String value)
         throws PersistenceException, UserException
  {
    // R�cup�re la propri�t� � modifier
    PROPERTY property = this.getProperty(key);
    // Modifie la propri�t� et la retourne
    property.defineValue(value);
    return this.getPropertyDao().update(property);
  }

  /**
   * Cette m�thode doit permettre d'importer toutes les propri�t�s en param�tre,
   * celles d�j� existantes voyant leur valeur mise � jour
   * @param properties Propri�t�s � importer
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si une cl� est invalide
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
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
   * Getter de la r�f�rence du DAO des propri�t�s
   * @return La r�f�rence du DAO des propri�t�s
   */
  protected abstract PropertyAbstractDao_<PROPERTY, PROPERTY_ROOT> getPropertyDao();
  /**
   * Getter de la r�f�rence du DAO des propri�t�s racines
   * @return La r�f�rence du DAO des propri�t�s racines
   */
  protected abstract PropertyRootAbstractDao_<PROPERTY_ROOT,PROPERTY> getRootDao();
}
