package com.bid4win.commons.service.property;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoStub;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractStub;
import com.bid4win.commons.service.connection.SessionDataAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class PropertyAbstractInternalServiceTest
       extends PropertyAbstractInternalServiceTester<PropertyAbstractStub,
                                                     PropertyRootAbstractStub,
                                                     SessionDataAbstractStub,
                                                     AccountAbstractStub,
                                                     ConnectionAbstractStub,
                                                     EntityGeneratorStub>
{
  /** Référence TODO */
  @Autowired
  @Qualifier("PropertyAbstractInternalServiceStub")
  private PropertyAbstractInternalServiceStub service;
  /** Référence TODO */
  @Autowired
  @Qualifier("PropertyRootAbstractDaoStub")
  private PropertyRootAbstractDaoStub rootDao;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getService()
   */
  @Override
  public PropertyAbstractInternalServiceStub getService()
  {
    return this.service;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getDao()
   */
  @Override
  protected PropertyRootAbstractDaoStub getDao()
  {
    return this.rootDao;
  }
}
