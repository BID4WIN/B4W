package com.bid4win.persistence.entity.property;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstractTester;
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
public class PropertyRootTest
       extends PropertyRootAbstractTester<PropertyRoot, Property, Account, EntityGenerator>
{
 /**
  *
  * TODO A COMMENTER
  * @return {@inheritDoc}
  * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#createBase()
  */
  @Override
  protected PropertyRoot createBase()
  {
    return new PropertyRoot();
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
  protected Property createProperty(String key, String value) throws UserException
  {
    return new Property(key, value);
  }
}
