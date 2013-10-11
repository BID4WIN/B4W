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
 * @author Emeric Fill�tre
 */
public interface IPropertyRootAbstractDaoStub<PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                              PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>>
       extends IBid4WinDaoStub<PROPERTY_ROOT, Integer>
{
  /**
   * Cette fonction permet de remonter l'unique propri�t� racine existante et de
   * la cr�er le cas �ch�ant
   * @return L'unique propri�t� racine existante (ou cr��e le cas �ch�ant)
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public PROPERTY_ROOT getRoot() throws PersistenceException;
  
  /**
   * Cette fonction doit d�finie la cl� unique de la racine des propri�t�s g�r�es
   * @return La cl� unique de la racine des propri�t�s g�r�es
   */
  public int getUniqueId();
}
