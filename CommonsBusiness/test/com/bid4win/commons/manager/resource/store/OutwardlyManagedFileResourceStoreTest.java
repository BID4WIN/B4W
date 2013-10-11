package com.bid4win.commons.manager.resource.store;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceStoreTest;
import com.bid4win.commons.persistence.entity.resource.FileResourceStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class OutwardlyManagedFileResourceStoreTest
       extends OutwardlyManagedFileResourceStoreTester<FileResourceStub, ResourceTypeStub>
{
  /** Référence de la classe de validation des ressources */
  @Autowired
  @Qualifier("FileResourceValidatorStub")
  private FileResourceValidatorStub validator;

  /**
   * Getter du nom du premier fichier de test
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getFilename1()
   */
  @Override
  protected String getFilename1()
  {
    return Bid4WinFileResourceStoreTest.FILE1;
  }
  /**
   * Getter du nom du deuxième fichier de test
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getFilename2()
   */
  @Override
  protected String getFilename2()
  {
    return Bid4WinFileResourceStoreTest.FILE2;
  }

  /**
   * Getter du premier type de ressource à utiliser
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType1()
   */
  @Override
  protected ResourceTypeStub getType1()
  {
    return ResourceTypeStub.TYPE1;
  }
  /**
   * Getter du deuxième type de ressource à utiliser
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType2()
   */
  @Override
  protected ResourceTypeStub getType2()
  {
    return ResourceTypeStub.TYPE2;
  }

  /**
   * Constructeur des ressources à tester
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#createResource(java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override
  protected FileResourceStub createResource(String path, String name, ResourceTypeStub type) throws UserException
  {
    return new FileResourceStub(path, name, type);
  }

  /**
   * Getter de l'objet à utiliser pour valider les résultats
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getValidator()
   */
  @Override
  protected FileResourceValidatorStub getValidator()
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
  protected OutwardlyManagedFileResourceStore<FileResourceStub, ResourceTypeStub> getOutwardlyManagedStore()
  {
    return (OutwardlyManagedFileResourceStore<FileResourceStub, ResourceTypeStub>)this.getStore();
  }
}
