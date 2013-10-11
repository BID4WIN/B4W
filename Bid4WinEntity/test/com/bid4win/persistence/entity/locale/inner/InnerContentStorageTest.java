package com.bid4win.persistence.entity.locale.inner;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPartTester;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.EntityGenerator;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class InnerContentStorageTest
       extends ResourceStorageMultiPartTester<InnerContentStorage, InnerContentType, InnerContentUsage,
                                              Language, InnerContent, Account, EntityGenerator>
{
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#createResource(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected InnerContentStorage createResource(String path, String name, InnerContentType type) throws UserException
  {
    return new InnerContentStorage(path, name, type);
  }
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageTester#createUsage(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
   */
  @Override
  protected InnerContentUsage createUsage(String path, String name, InnerContentStorage storage) throws UserException, ModelArgumentException
  {
    return new InnerContentUsage(path, name, storage);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#getType()
   */
  @Override
  protected InnerContentType getType()
  {
    return InnerContentType.XML;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPartTester#getPartType()
   */
  @Override
  protected Language getPartType()
  {
    return Language.ITALIAN;
  }
}
