package com.bid4win.commons.persistence.dao.property;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractStub;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
@TransactionConfiguration(transactionManager = "Bid4WinTransactionManager")
public class PropertyRootAbstractDaoTest
       extends PropertyRootAbstractDaoTester<PropertyRootAbstractStub, PropertyAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("PropertyRootAbstractDaoStub")
  private PropertyRootAbstractDaoStub dao;
  /** Référence du DAO des propriétés */
  @Autowired
  @Qualifier("PropertyAbstractDaoStub")
  private PropertyAbstractDaoStub propertyDao;


  /**
   * Getter du DAO des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#getPropertyDao()
   */
  @Override
  protected PropertyAbstractDaoStub getPropertyDao()
  {
    return this.propertyDao;
  }
  /**
   * Getter du DAO a tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyRootAbstractDaoTester#getDao()
   */
  @Override
  protected PropertyRootAbstractDaoStub getDao()
  {
    return this.dao;
  }
}
