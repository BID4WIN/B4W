package com.bid4win.commons.persistence.dao.property;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.IBid4WinDaoStub;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface IPropertyAbstractDaoStub<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                          PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>>
       extends IBid4WinDaoStub<PROPERTY, Integer>
{
  /**
   * Cette fonction permet de récupérer la propriété unique correspondant à la clé
   * en argument
   * @param key Clé de la propriété à récupérer
   * @return La propriété unique récupérée
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public PROPERTY findOneByKey(String key) throws PersistenceException;
}
