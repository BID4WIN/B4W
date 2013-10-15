package com.bid4win.commons.core.io.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.UtilFileTest;

/**
 * Cette classe est la classe de base pour tester le fonctionnement d'un magasin
 * de ressources<BR>
 * <BR>
 * @param <RESOURCE> Définition des ressources du magasin à tester<BR>
 * @param <TYPE> Définition de la classe de type des ressources du magasin à tester<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinResourceStoreTester<RESOURCE extends Bid4WinResource<TYPE>, TYPE>
       extends Bid4WinCoreTester
{
  /**
   * C'est la méthode de test complète des magasins de ressources
   * @throws Exception Issue not expected during this test
   */
  @Test
  public void test_complete_test_for_store() throws Exception
  {
    String path = UtilString.EMPTY;
    String name = this.getFilename1();

    // Test de recherche d'un fichier inexistant
    RESOURCE resource1 = this.createResource(path, name, this.getType1());
    assertFalse(this.getStore().exists(resource1));
    File file = this.getTestFile(this.getFilename1());

    // Test de remplacement d'un fichier inexistant
    FileInputStream inputStream = new FileInputStream(file);
    try
    {
      this.getStore().replace(inputStream, resource1);
      fail("Should fail");
    }
    catch(PersistenceException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse(this.getStore().exists(resource1));
    }
    finally
    {
      inputStream.close();
    }

    // Test de suppression d'un fichier inexistant
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().remove(resource1);
      fail("Should fail");
    }
    catch(PersistenceException ex)
    {
      System.out.println(ex.getMessage());
      assertFalse(this.getStore().exists(resource1));
    }
    finally
    {
      inputStream.close();
    }

    // Test d'ajout d'un fichier à la racine
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().store(inputStream, resource1);
      assertTrue(this.getStore().exists(resource1));
      this.assertEquals(file, resource1);
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    finally
    {
      inputStream.close();
    }

    // Test d'ajout d'un fichier dans une arborescence
    path = "test/test1";
    RESOURCE resource2 = this.createResource(path, name, this.getType1());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().store(inputStream, resource2);
      assertTrue(this.getStore().exists(resource2));
      this.assertEquals(file, resource2);
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    finally
    {
      inputStream.close();
    }

    // Test d'ajout d'un fichier existant
    inputStream = new FileInputStream(this.getTestFile(this.getFilename2()));
    try
    {
      this.getStore().store(inputStream, resource2);
      fail("Should fail");
    }
    catch(PersistenceException ex)
    {
      System.out.println(ex.getMessage());
    }
    finally
    {
      inputStream.close();
    }
    assertTrue(this.getStore().exists(resource2));
    this.assertEquals(file, resource2);

    // Test de remplacement d'un fichier existant
    file = this.getTestFile(this.getFilename2());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().replace(inputStream, resource2);
      assertTrue(this.getStore().exists(resource2));
      this.assertEquals(file, resource2);
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    finally
    {
      inputStream.close();
    }

    // Test d'ajout d'un fichier dans une arborescence
    name = this.getFilename2();
    RESOURCE resource3 = this.createResource(path, name, this.getType1());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().store(inputStream, resource3);
      assertTrue(this.getStore().exists(resource3));
      this.assertEquals(file, resource3);
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    finally
    {
      inputStream.close();
    }

    // Test de remplacement d'un fichier existant
    file = this.getTestFile(this.getFilename1());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().replace(inputStream, resource3);
      assertTrue(this.getStore().exists(resource3));
      this.assertEquals(file, resource3);
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    finally
    {
      inputStream.close();
    }

    // Test de suppression d'un fichier
    try
    {
      this.getStore().remove(resource1);
      assertFalse(this.getStore().exists(resource1));
      this.getStore().remove(resource2);
      assertFalse(this.getStore().exists(resource2));
      this.getStore().remove(resource3);
      assertFalse(this.getStore().exists(resource3));
    }
    catch(PersistenceException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }

    // Test de copie d'un fichier
    file = this.getTestFile(this.getFilename1());
    resource1 = this.createResource("1/1", "1", this.getType1());
    resource2 = this.createResource("1/1", "2", this.getType1());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().store(inputStream, resource1);
      assertTrue(this.getStore().exists(resource1));
      this.assertEquals(file, resource1);

      this.getStore().copy(resource1, resource2);
      assertTrue(this.getStore().exists(resource1));
      this.assertEquals(file, resource1);
      assertTrue(this.getStore().exists(resource2));
      this.assertEquals(file, resource2);
    }
    finally
    {
      inputStream.close();
    }

    // Test de copie d'un fichier avec un mauvais type
    if(this.getType1().equals(this.getType2()))
    {
      System.out.println("Both type are indeed the same ...");
    }
    else
    {
      try
      {
        resource3 = this.createResource("1/1", "3", this.getType2());
        this.getStore().copy(resource1, resource3);
        fail("Should fail");
      }
      catch(UserException ex)
      {
        System.out.println(ex.getMessage());
      }
      assertFalse(this.getStore().exists(resource3));
    }

    // Test de déplacement d'un fichier
    file = this.getTestFile(this.getFilename1());
    resource3 = this.createResource("1/2", "1", this.getType1());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().move(resource1, resource3);
      assertFalse(this.getStore().exists(resource1));
      assertTrue(this.getStore().exists(resource2));
      this.assertEquals(file, resource2);
      assertTrue(this.getStore().exists(resource3));
      this.assertEquals(file, resource3);
    }
    finally
    {
      inputStream.close();
    }

    // Test de copie d'un fichier à partir d'un autre magasin
    RESOURCE resource4 = this.createResource("2/1", "1", this.getType1());
    inputStream = new FileInputStream(file);
    try
    {
      this.getStore().copy(this.getStore(), resource3, resource4);
      assertFalse(this.getStore().exists(resource1));
      assertTrue(this.getStore().exists(resource2));
      this.assertEquals(file, resource2);
      assertTrue(this.getStore().exists(resource3));
      this.assertEquals(file, resource3);
      assertTrue(this.getStore().exists(resource4));
      this.assertEquals(file, resource4);
    }
    finally
    {
      inputStream.close();
    }

    // Test de copie d'un fichier à partir d'un autre magasin avec un mauvais type
    if(this.getType1().equals(this.getType2()))
    {
      System.out.println("Both type are indeed the same ...");
    }
    else
    {
      try
      {
        resource4 = this.createResource("2/1", "2", this.getType2());
        this.getStore().copy(this.getStore(), resource3, resource4);
        fail("Should fail");
      }
      catch(UserException ex)
      {
        System.out.println(ex.getMessage());
      }
      assertFalse(this.getStore().exists(resource4));
    }
  }

  /**
   * Permet de tester que la ressource en argument correspond bien dans le magasin
   * au fichier donné
   * @param expected Résultat attendu pour la ressource en argument
   * @param resource Ressource à tester
   * @throws Exception Problème lors de la validation de la ressource
   */
  protected void assertEquals(File expected, RESOURCE resource) throws Exception
  {
    this.getValidator().assertEquals(this.getTestPath(), expected, resource);
  }

  /**
   * Cette méthode doit créer les ressources utilisées par le magasin à tester
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @return La ressource correspondant aux argument
   * @throws UserException Si la création de la ressource échoue
   */
  protected abstract RESOURCE createResource(String path, String name, TYPE type) throws UserException;
  /**
   * Getter du nom du premier fichier de test
   * @return Le nom du premier fichier de test
   */
  protected abstract String getFilename1();
  /**
   * Getter du nom du deuxième fichier de test
   * @return Le deuxième fichier de test
   */
  protected abstract String getFilename2();
  /**
   * Getter du premier type de ressource à utiliser
   * @return Le premier type de ressource à utiliser
   */
  protected abstract TYPE getType1();
  /**
   * Getter du deuxième type de ressource à utiliser
   * @return Le deuxième type de ressource à utiliser
   */
  protected abstract TYPE getType2();

  /**
   * Getter du magasin à tester
   * @return Le magasin à tester
   */
  protected Bid4WinResourceStore<RESOURCE, TYPE> getStore()
  {
    return this.getValidator().getStore();
  }
  /**
   * Getter de l'objet à utiliser pour valider les résultats
   * @return L'objet à utiliser pour valider les résultats
   */
  protected abstract Bid4WinResourceValidator<RESOURCE, TYPE> getValidator();

  /**
   *
   * TODO A COMMENTER
   * @param paths TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String concatRelativePath(String ... paths) throws UserException
  {
    return UtilFile.concatRelativePath(this.createResource(UtilString.EMPTY, "name", this.getType1()).getMessageRef(),
                                       paths);
  }
  /**
   *
   * TODO A COMMENTER
   * @param paths TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String concatAbsolutePath(String ... paths) throws UserException
  {
    return UtilFile.concatAbsolutePath(this.createResource(UtilString.EMPTY, "name", this.getType1()).getMessageRef(),
                                       paths);
  }
  /**
   * Getter du repertoire de test
   * @return Le repertoire de test
   * @throws UserException Si le repertoire est invalide
   */
  protected String getTestPath() throws UserException
  {
    return this.concatAbsolutePath(UtilFileTest.getProjectPath("CommonsCore"),
                                   "test" , "resources");
  }
  /**
   * Getter de l'emplacement des fichiers de ressources utilisés pour les tests
   * @return L'emplacement des fichiers de ressources utilisés pour les tests
   * @throws UserException Si l'emplacement est invalide
   */
  protected String getTestResourcePath() throws UserException
  {
    return this.concatAbsolutePath(this.getTestPath(), "test");
  }
  /**
   * Getter du fichier de test précisé en argument
   * @param filename Nom du fichier de test à récupérer
   * @return Le fichier de test précisé en argument
   * @throws UserException Si l'emplacement du fichier de test est invalide
   */
  protected File getTestFile(String filename) throws UserException
  {
    return new File(this.concatAbsolutePath(this.getTestResourcePath(), filename));
  }

  /**
   * Test setup method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    this.getValidator().cleanAll(this.getTestPath());
  }
  /**
   * Test teardown method
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.testing.Bid4WinTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    this.getValidator().cleanAll(this.getTestPath());
  }
}
