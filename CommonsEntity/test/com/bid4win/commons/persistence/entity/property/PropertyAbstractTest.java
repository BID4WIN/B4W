package com.bid4win.commons.persistence.entity.property;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une propriété<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class PropertyAbstractTest
       extends PropertyAbstractTester<PropertyAbstractStub, PropertyRootAbstractStub,
                                      AccountAbstractStub, EntityGeneratorStub>
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
  protected PropertyAbstractStub createProperty(String key, String value) throws UserException
  {
    return new PropertyAbstractStub(key, value);
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
  public PropertyAbstractStub createProperty(String key, String value, PropertyAbstractStub parent) throws UserException
  {
    return new PropertyAbstractStub(key, value, parent);
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
  public PropertyAbstractStub createProperty(String key, String value, PropertyRootAbstractStub root) throws UserException
  {
    return new PropertyAbstractStub(key, value, root);
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
  public PropertyAbstractStub createProperty(PropertyAbstractStub toBeCopied, PropertyAbstractStub property) throws UserException, ModelArgumentException
  {
    return new PropertyAbstractStub(toBeCopied, property);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstractTester#createRoot()
   */
  @Override
  public PropertyRootAbstractStub createRoot()
  {
    return new PropertyRootAbstractStub(9);
  }
}
