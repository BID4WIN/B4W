package com.bid4win.commons.persistence.entity.resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObjectTypeStub1;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une portion de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ResourcePartTest
       extends ResourcePartTester<ResourcePartStub, ResourceTypeStub, Bid4WinObjectTypeStub1, ResourceStorageMultiPartStub, ResourceUsageMultiPartStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param storage {@inheritDoc}
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourcePartTester#createResource(com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart, com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  protected ResourcePartStub createResource(
      ResourceStorageMultiPartStub storage, Bid4WinObjectTypeStub1 partType)
      throws UserException, ModelArgumentException
  {
    return new ResourcePartStub(storage, partType);
  }
  /**
   *
   * TODO A COMMENTER
   * @param usage {@inheritDoc}
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourcePartTester#createResource(com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart, com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  protected ResourcePartStub createResource(ResourceUsageMultiPartStub usage,
      Bid4WinObjectTypeStub1 partType) throws UserException,
      ModelArgumentException
  {
    return new ResourcePartStub(usage, partType);
  }
  /**
   *
   * TODO A COMMENTER
   * @param part {@inheritDoc}
   * @param partType {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourcePartTester#createResource(com.bid4win.commons.persistence.entity.resource.store.ResourcePart, com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  protected ResourcePartStub createResource(ResourcePartStub part,
      Bid4WinObjectTypeStub1 partType) throws UserException,
      ModelArgumentException
  {
    return new ResourcePartStub(part, partType);
  }
  /**
   *
   * TODO A COMMENTER
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourcePartTester#createStorage(com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected ResourceStorageMultiPartStub createStorage(ResourceTypeStub type)
      throws UserException
  {
    return new ResourceStorageMultiPartStub("1", "2", type);
  }
  /**
   *
   * TODO A COMMENTER
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourcePartTester#createUsage(com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected ResourceUsageMultiPartStub createUsage(ResourceTypeStub type)
      throws UserException
  {
    try
    {
      return new ResourceUsageMultiPartStub("3", "4", new ResourceStorageMultiPartStub("1", "2", type));
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(MessageRef.UNKNOWN_ERROR, ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourcePartTester#getPartType()
   */
  @Override
  protected Bid4WinObjectTypeStub1 getPartType()
  {
    return Bid4WinObjectTypeStub1.TYPE1_2_1;
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
}
