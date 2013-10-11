package com.bid4win.commons.manager.property;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoStub;
import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractStub;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PropertyAbstractManagerStub")
@Scope("singleton")
public class PropertyAbstractManagerStub
       extends PropertyAbstractManager_<PropertyAbstractStub,
                                        PropertyRootAbstractStub,
                                        AccountAbstractStub>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("PropertyAbstractDaoStub")
  private PropertyAbstractDaoStub propertyDao;
  /** Référence TODO */
  @Autowired
  @Qualifier("PropertyRootAbstractDaoStub")
  private PropertyRootAbstractDaoStub rootDao;

  /**
   *
   * TODO A COMMENTER
   * @param properties {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#importProperties(java.util.Properties)
   */
  @Override
  public void importProperties(Properties properties)
      throws PersistenceException, UserException, ModelArgumentException
  {
    // TODO Auto-generated method stub

  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#getPropertyDao()
   */
  @Override
  protected PropertyAbstractDaoStub getPropertyDao()
  {
    return this.propertyDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#getRootDao()
   */
  @Override
  protected PropertyRootAbstractDaoStub getRootDao()
  {
    return this.rootDao;
  }
}
