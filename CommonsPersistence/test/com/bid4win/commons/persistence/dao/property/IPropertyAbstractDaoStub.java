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
 * @author Emeric Fill�tre
 */
public interface IPropertyAbstractDaoStub<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                          PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>>
       extends IBid4WinDaoStub<PROPERTY, Integer>
{
  /**
   * Cette fonction permet de r�cup�rer la propri�t� unique correspondant � la cl�
   * en argument
   * @param key Cl� de la propri�t� � r�cup�rer
   * @return La propri�t� unique r�cup�r�e
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public PROPERTY findOneByKey(String key) throws PersistenceException;
}
