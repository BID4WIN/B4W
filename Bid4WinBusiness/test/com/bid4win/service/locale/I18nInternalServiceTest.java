package com.bid4win.service.locale;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.service.property.PropertyAbstractInternalServiceTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.dao.locale.I18nRootDaoStub;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class I18nInternalServiceTest
       extends PropertyAbstractInternalServiceTester<I18n, I18nRoot,
                                                     SessionData, Account, EntityGenerator>
{
  /** Référence du service à tester */
  @Autowired
  @Qualifier("I18nInternalService")
  private I18nInternalService service;
  /** Référence du DAO de test des propriétés racines */
  @Autowired
  @Qualifier("I18nRootDaoStub")
  private I18nRootDaoStub dao;

  /**
   * Getter du DAO de test des propriétés racines
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  public I18nRootDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Getter du manager des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractServiceTester#getService()
   */
  @Override
  public I18nInternalService getService()
  {
    return this.service;
  }
}
