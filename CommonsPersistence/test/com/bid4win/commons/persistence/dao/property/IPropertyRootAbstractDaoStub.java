package com.bid4win.commons.persistence.dao.property;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IPropertyRootAbstractDaoStub<PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                              PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>>
       extends IBid4WinDaoStub<PROPERTY_ROOT, Integer>
{
  /**
   * Cette fonction permet de remonter l'unique propriété racine existante et de
   * la créer le cas échéant
   * @return L'unique propriété racine existante (ou créée le cas échéant)
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public PROPERTY_ROOT getRoot() throws PersistenceException;
  
  /**
   * Cette fonction doit définie la clé unique de la racine des propriétés gérées
   * @return La clé unique de la racine des propriétés gérées
   */
  public int getUniqueId();
}
