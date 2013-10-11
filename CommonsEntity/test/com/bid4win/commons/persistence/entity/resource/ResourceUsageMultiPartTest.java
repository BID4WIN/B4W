package com.bid4win.commons.persistence.entity.resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'un stockage de ressource divisé en portions<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ResourceUsageMultiPartTest
       extends ResourceUsageMultiPartTester<ResourceUsageMultiPartStub, ResourceTypeStub, ResourceStorageMultiPartStub, Bid4WinObjectTypeStub1, ResourcePartStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageTester#createResource(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceStorage)
   */
  @Override
  protected ResourceUsageMultiPartStub createResource(String path, String name, ResourceStorageMultiPartStub storage) throws UserException, ModelArgumentException
  {
    return new ResourceUsageMultiPartStub(path, name, storage);
  }
  /**
   *
   * TODO A COMMENTER
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageTester#createStorage(com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected ResourceStorageMultiPartStub createStorage(ResourceTypeStub type) throws UserException
  {
    return new ResourceStorageMultiPartStub("1", "2", type);
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#getType()
   */
  @Override
  protected ResourceTypeStub getType()
  {
    return ResourceTypeStub.TYPE2;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPartTester#getPartType()
   */
  @Override
  protected Bid4WinObjectTypeStub1 getPartType()
  {
    return Bid4WinObjectTypeStub1.TYPE1_2_2;
  }
}
