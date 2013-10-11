package com.bid4win.persistence.entity.property;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstractTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class PropertyTest
       extends PropertyAbstractTester<Property, PropertyRoot, Account, EntityGenerator>
{
  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#createProperty(java.lang.String, java.lang.String)
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
   * @param parent {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstractTester#createProperty(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  public Property createProperty(String key, String value, Property parent) throws UserException
  {
    return new Property(key, value, parent);
  }
  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @param root {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstractTester#createProperty(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.property.PropertyRootAbstract)
   */
  @Override
  public Property createProperty(String key, String value, PropertyRoot root) throws UserException
  {
    return new Property(key, value, root);
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeCopied {@inheritDoc}
   * @param property {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstractTester#createProperty(com.bid4win.commons.persistence.entity.property.PropertyAbstract, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  public Property createProperty(Property toBeCopied, Property property) throws UserException, ModelArgumentException
  {
    return new Property(toBeCopied, property);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstractTester#createRoot()
   */
  @Override
  public PropertyRoot createRoot()
  {
    return new PropertyRoot();
  }
}
