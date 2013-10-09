package com.bid4win.commons.core.io.resource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test pour le magasin sous forme de fichier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinFileResourceStoreTest
       extends Bid4WinFileResourceStoreTester<Bid4WinFileResourceStub, String>
{
  /** Premier fichier de test */
  public static final String FILE1 = "logo02.png";
  /** Deuxième fichier de test */
  public static final String FILE2 = "README";
  /** Référence de la classe de validation des ressources */
  @Autowired
  @Qualifier("Bid4WinFileResourceValidatorStub")
  private Bid4WinFileResourceValidatorStub validator;

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
  protected String getType1()
  {
    return "TYPE1";
  }
  /**
   * Getter du deuxième type de ressource à utiliser
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getType2()
   */
  @Override
  protected String getType2()
  {
    return "TYPE2";
  }

  /**
   * Constructeur des ressources à tester
   * @param path {@inheritDoc}
   * @param name {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#createResource(java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override
  protected Bid4WinFileResourceStub createResource(String path, String name, String type)
  {
    return new Bid4WinFileResourceStub(path, name, type);
  }

  /**
   * Getter de l'objet à utiliser pour valider les résultats
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getValidator()
   */
  @Override
  protected Bid4WinFileResourceValidatorStub getValidator()
  {
    return this.validator;
  }
}
