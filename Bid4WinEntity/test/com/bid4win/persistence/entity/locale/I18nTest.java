package com.bid4win.persistence.entity.locale;

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
public class I18nTest
       extends PropertyAbstractTester<I18n, I18nRoot, Account, EntityGenerator>
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
  protected I18n createProperty(String key, String value) throws UserException
  {
    return new I18n(key, value);
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
  public I18n createProperty(String key, String value, I18n parent) throws UserException
  {
    return new I18n(key, value, parent);
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
  public I18n createProperty(String key, String value, I18nRoot root) throws UserException
  {
    return new I18n(key, value, root);
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
  public I18n createProperty(I18n toBeCopied, I18n property) throws UserException, ModelArgumentException
  {
    return new I18n(toBeCopied, property);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstractTester#createRoot()
   */
  @Override
  public I18nRoot createRoot()
  {
    return new I18nRoot();
  }
  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBaseTester#getBaseKey(int)
   */
  @Override
  protected String getBaseKey(int index)
  {
    return this.getGenerator().getLanguage(index).getCode();
  }
}
