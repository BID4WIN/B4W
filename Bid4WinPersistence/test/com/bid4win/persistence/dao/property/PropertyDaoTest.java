package com.bid4win.persistence.dao.property;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester;
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
public class PropertyDaoTest
       extends PropertyAbstractDaoTester<Property, PropertyRoot, Account, EntityGenerator>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("PropertyDaoStub")
  private PropertyDaoStub dao;
  /** Référence du DAO de la racine des propriétés à tester */
  @Autowired
  @Qualifier("PropertyRootDaoStub")
  private PropertyRootDaoStub rootDao;

  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester#getDao()
   */
  @Override
  protected PropertyDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Getter du DAO de la racine des propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester#getRootDao()
   */
  @Override
  protected PropertyRootDaoStub getRootDao()
  {
    return this.rootDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected Property createProperty(String key, String value) throws UserException
  {
    return new Property(key, value);
  }
  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester#createProperty(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  protected Property createProperty(String key, String value, Property property)
            throws UserException, ModelArgumentException
  {
    return new Property(key, value, property);
  }
  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester#createProperty(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.property.PropertyRootAbstract)
   */
  @Override
  protected Property createProperty(String key, String value, PropertyRoot root)
            throws UserException, ModelArgumentException
  {
    return new Property(key, value, root);
  }
}
