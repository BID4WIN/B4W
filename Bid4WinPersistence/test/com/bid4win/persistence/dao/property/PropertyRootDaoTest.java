package com.bid4win.persistence.dao.property;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class PropertyRootDaoTest
       extends PropertyRootAbstractDaoTester<PropertyRoot, Property, Account, EntityGenerator>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("PropertyRootDaoStub")
  private PropertyRootDaoStub dao;
  /** Référence du DAO des propriétés */
  @Autowired
  @Qualifier("PropertyDaoStub")
  private PropertyDaoStub propertyDao;

  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#getDao()
   */
  @Override
  protected PropertyRootDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Getter du DAO des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#getPropertyDao()
   */
  @Override
  protected PropertyDaoStub getPropertyDao()
  {
    return this.propertyDao;
  }
}
