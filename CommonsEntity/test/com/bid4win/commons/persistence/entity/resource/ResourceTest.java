package com.bid4win.commons.persistence.entity.resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.EntityGeneratorStub;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test d'une ressource de base<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class ResourceTest
       extends ResourceTester<ResourceStub, ResourceTypeStub, AccountAbstractStub, EntityGeneratorStub>
{
  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}v
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceTester#createResource(java.lang.String, java.lang.String, com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  protected ResourceStub createResource(String path, String name, ResourceTypeStub type) throws UserException
  {
    return new ResourceStub(path, name, type);
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
