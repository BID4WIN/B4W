package com.bid4win.manager.image.store;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStore;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class ImageRepositoryTest extends ImageFileStoreTester<ImageStorage>
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("ImageRepositoryValidator")
  private ImageRepositoryValidator validator;

  /**
   *
   * TODO A COMMENTER
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#createResource(java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override
  protected ImageStorage createResource(String path, String name, ImageType type) throws UserException
  {
    return new ImageStorage(path, name, type);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getValidator()
   */
  @Override
  protected ImageRepositoryValidator getValidator()
  {
    return this.validator;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStoreTester#getOutwardlyManagedStore()
   */
  @Override
  protected OutwardlyManagedFileResourceStore<?, ?> getOutwardlyManagedStore()
  {
    return this.getValidator().getStore().getPartStore();
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    this.getValidator().setUpProperty();
    super.setUp();
  }
}
