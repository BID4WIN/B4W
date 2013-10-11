package com.bid4win.commons.persistence.entity.property;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une propriété de base<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class PropertyBaseTest extends PropertyBaseTester<PropertyRootAbstractStub, PropertyRootAbstractStub,
                                                         PropertyAbstractStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#createBase()
   */
  @Override
  protected PropertyRootAbstractStub createBase()
  {
    return new PropertyRootAbstractStub();
  }
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
}
