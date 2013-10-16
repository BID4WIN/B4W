package com.bid4win.service.property;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.service.property.PropertyAbstractInternalServiceTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.property.PropertyRootDaoStub;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class PropertyInternalServiceTest
       extends PropertyAbstractInternalServiceTester<Property, PropertyRoot, SessionData,
                                                     Account, Connection, EntityGenerator>
{
  /** R�f�rence du service � tester */
  @Autowired
  @Qualifier("PropertyInternalService")
  private PropertyInternalService service;
  /** R�f�rence du DAO de test des propri�t�s racines */
  @Autowired
  @Qualifier("PropertyRootDaoStub")
  private PropertyRootDaoStub dao;

  /**
   * Getter du DAO de test des propri�t�s racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  public PropertyRootDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Getter du manager des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getService()
   */
  @Override
  public PropertyInternalService getService()
  {
    return this.service;
  }
}
