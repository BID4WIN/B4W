package com.bid4win.commons.persistence.dao.property;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
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
public class PropertyAbstractDaoTest
       extends PropertyAbstractDaoTester<PropertyAbstractStub, PropertyRootAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("PropertyAbstractDaoStub")
  private PropertyAbstractDaoStub dao;
  /** Référence du DAO de la racine des propriétés à tester */
  @Autowired
  @Qualifier("PropertyRootAbstractDaoStub")
  private PropertyRootAbstractDaoStub rootDao;

  /**
   * Getter du DAO des propriétés
   * @return Le DAO des propriétés
   */
  @Override
  protected PropertyAbstractDaoStub getDao()
  {
    return this.dao;
  }
  /**
   * Getter du DAO de la racine des propriétés
   * @return Le DAO de la racine des propriétés
   */
  @Override
  protected PropertyRootAbstractDaoStub getRootDao()
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
  protected PropertyAbstractStub createProperty(String key, String value) throws UserException
  {
    return new PropertyAbstractStub(key, value);
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
  protected PropertyAbstractStub createProperty(String key, String value,
                                                PropertyAbstractStub property)
            throws UserException, ModelArgumentException
  {
    return new PropertyAbstractStub(key, value, property);
  }
  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.property.PropertyAbstractDaoTester#createProperty(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.property.PropertyRootAbstract)
   */
  @Override
  protected PropertyAbstractStub createProperty(String key, String value,
                                                PropertyRootAbstractStub root)
            throws UserException, ModelArgumentException
  {
    return new PropertyAbstractStub(key, value, root);
  }
}
