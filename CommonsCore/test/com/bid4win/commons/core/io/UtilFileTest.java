package com.bid4win.commons.core.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * TODO COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class UtilFileTest extends Bid4WinCoreTester
{
  /**
   * Test of cleanRelativePath(String) method, of class UtilFile.
   */
  @Test
  public void testCleanRelativePath_String()
  {
    String s = UtilFile.PATH_SEPARATOR;
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanRelativePath(null));
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanRelativePath("   "));
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanRelativePath(s));
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanRelativePath(s+s));
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanRelativePath(s+s+s));
    assertEquals("Wrong result", "a"+s+"b", UtilFile.cleanRelativePath(s+s+s+"a"+s+s+s+"b"+s+s+s));
  }
  /**
   * Test of cleanAbsolutePath(String) method, of class UtilFile.
   */
  @Test
  public void testCleanAbsolutePath_String()
  {
    String s = UtilFile.PATH_SEPARATOR;
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanAbsolutePath(null));
    assertEquals("Wrong result", UtilString.EMPTY, UtilFile.cleanAbsolutePath("   "));
    assertEquals("Wrong result", s, UtilFile.cleanAbsolutePath(s));
    assertEquals("Wrong result", s+s, UtilFile.cleanAbsolutePath(s+s));
    assertEquals("Wrong result", s+s, UtilFile.cleanAbsolutePath(s+s+s));
    assertEquals("Wrong result", s+s+"a"+s+"b", UtilFile.cleanAbsolutePath(s+s+s+"a"+s+s+s+"b"+s+s+s));
  }
  /**
   * Test of checkFilename(String, MessageRef) method, of class UtilFile.
   */
  @Test
  public void testCheckFilename_String_MessageRef()
  {
    try
    {
      UtilFile.checkFilename(null, ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkFilename("   ", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkFilename(" a b ", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkFilename("a..b", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      assertEquals("Wrong result", "a.b", UtilFile.checkFilename(" a.b ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF", UtilFile.checkFilename("  abc123-_DEF  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF.com", UtilFile.checkFilename("  abc123-_DEF.com  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF.com.fr", UtilFile.checkFilename("  abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", ".abc123-_DEF.com.fr", UtilFile.checkFilename("  .abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of checkRelativePath(String, MessageRef) method, of class UtilFile.
   */
  @Test
  public void testCheckRelativePath_String_MessageRef()
  {
    String s = UtilFile.PATH_SEPARATOR;
    try
    {
      UtilFile.checkRelativePath(" a b ", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkRelativePath("a..b", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkRelativePath(s+".."+s, ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkRelativePath(null, ResourceRef.RESOURCE));
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkRelativePath("   ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF", UtilFile.checkRelativePath("  abc123-_DEF  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF.com", UtilFile.checkRelativePath("  abc123-_DEF.com  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF.com.fr", UtilFile.checkRelativePath("  abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", ".abc123-_DEF.com.fr", UtilFile.checkRelativePath("  .abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkRelativePath(s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkRelativePath(s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkRelativePath(s+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", "a", UtilFile.checkRelativePath(s+s+s+"a"+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", ".", UtilFile.checkRelativePath(s+s+s+"."+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", "a.b", UtilFile.checkRelativePath("a.b", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "a"+s+"b", UtilFile.checkRelativePath(s+s+s+"a"+s+s+s+"b"+s+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", "ghi456-_JKL"+s+"789_-mnoPQR"+s+"abc123-_DEF.com.fr",
                   UtilFile.checkRelativePath(s+s+s+"ghi456-_JKL"+s+s+s+"789_-mnoPQR"+s+s+s+"abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", ".a"+s+"b"+s+"abc123-_DEF.com.fr",
                   UtilFile.checkRelativePath(s+s+s+".a"+s+s+s+"b"+s+s+s+"abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of checkAbsolutePath(String, MessageRef) method, of class UtilFile.
   */
  @Test
  public void testCheckAbsolutePath_String_MessageRef()
  {
    String s = UtilFile.PATH_SEPARATOR;
    try
    {
      UtilFile.checkAbsolutePath(" a b ", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkAbsolutePath("a..b", ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.checkAbsolutePath(s+".."+s, ResourceRef.RESOURCE);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkAbsolutePath(null, ResourceRef.RESOURCE));
      assertEquals("Wrong result", UtilString.EMPTY, UtilFile.checkAbsolutePath("   ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF", UtilFile.checkAbsolutePath("  abc123-_DEF  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF.com", UtilFile.checkAbsolutePath("  abc123-_DEF.com  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", "abc123-_DEF.com.fr", UtilFile.checkAbsolutePath("  abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", ".abc123-_DEF.com.fr", UtilFile.checkAbsolutePath("  .abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", s, UtilFile.checkAbsolutePath(s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s, UtilFile.checkAbsolutePath(s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s, UtilFile.checkAbsolutePath(s+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s+"a", UtilFile.checkAbsolutePath(s+s+s+"a"+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s+".", UtilFile.checkAbsolutePath(s+s+s+"."+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", "a.b", UtilFile.checkAbsolutePath("a.b", ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s+"a"+s+"b", UtilFile.checkAbsolutePath(s+s+s+"a"+s+s+s+"b"+s+s+s, ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s+"ghi456-_JKL"+s+"789_-mnoPQR"+s+"abc123-_DEF.com.fr",
                   UtilFile.checkAbsolutePath(s+s+s+"ghi456-_JKL"+s+s+s+"789_-mnoPQR"+s+s+s+"abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
      assertEquals("Wrong result", s+s+".a"+s+"b"+s+"abc123-_DEF.com.fr",
                   UtilFile.checkAbsolutePath(s+s+s+".a"+s+s+s+"b"+s+s+s+"abc123-_DEF.com.fr  ", ResourceRef.RESOURCE));
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }

  /**
   * Test of computeSubdirectories(Bid4WinStringRecursiveMap, String) method, of class UtilFile.
   */
  @Test
  public void testComputeSubdirectories_Bid4WinStringRecursiveMap_String()
  {
    try
    {
      Bid4WinStringRecursiveMap subdirectories = new Bid4WinStringRecursiveMap();
      Bid4WinStringRecursiveMap result = UtilFile.computeSubdirectories(subdirectories, UtilString.EMPTY);
      assertEquals(new Bid4WinStringRecursiveMap(), result);
      assertEquals(new Bid4WinStringRecursiveMap(), subdirectories);


      result = UtilFile.computeSubdirectories(subdirectories,
          UtilFile.concatRelativePath(ResourceRef.RESOURCE, "1", "2", "3", "4"));
      Bid4WinStringRecursiveMap expected =
          new Bid4WinStringRecursiveMap("1",
              new Bid4WinStringRecursiveMap("2",
                  new Bid4WinStringRecursiveMap("3",
                      new Bid4WinStringRecursiveMap("4",
                          new Bid4WinStringRecursiveMap()))));
      assertEquals(new Bid4WinStringRecursiveMap(), result);
      assertEquals(expected, subdirectories);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }

  /**
   * Test of computeSubdirectories(Bid4WinList) method, of class UtilFile.
   */
  @Test
  public void testComputeSubdirectories_Bid4WinList()
  {
    try
    {
      Bid4WinStringRecursiveMap result =
          UtilFile.computeSubdirectories(new Bid4WinList<String>());
      assertEquals(new Bid4WinStringRecursiveMap(), result);

      result = UtilFile.computeSubdirectories(new Bid4WinList<String>(UtilString.EMPTY));
      assertEquals(new Bid4WinStringRecursiveMap(), result);

      result = UtilFile.computeSubdirectories(
          new Bid4WinList<String>("1/1_1/1_1_1", "1/1_1/1_1_2/1_1_2_1", "2", UtilString.EMPTY));
      Bid4WinStringRecursiveMap expected = new Bid4WinStringRecursiveMap();
      Bid4WinStringRecursiveMap expected_1 = new Bid4WinStringRecursiveMap();
      expected.put("1", expected_1);
      Bid4WinStringRecursiveMap expected_1_1 = new Bid4WinStringRecursiveMap();
      expected_1.put("1_1", expected_1_1);
      Bid4WinStringRecursiveMap expected_1_1_1 = new Bid4WinStringRecursiveMap();
      expected_1_1.put("1_1_1", expected_1_1_1);
      Bid4WinStringRecursiveMap expected_1_1_2 = new Bid4WinStringRecursiveMap();
      expected_1_1.put("1_1_2", expected_1_1_2);
      Bid4WinStringRecursiveMap expected_1_1_2_1 = new Bid4WinStringRecursiveMap();
      expected_1_1_2.put("1_1_2_1", expected_1_1_2_1);
      Bid4WinStringRecursiveMap expected_2 = new Bid4WinStringRecursiveMap();
      expected.put("2", expected_2);
      assertEquals(expected, result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
  }
  /**
   * Test of computeSubdirectories(Bid4WinList, String) method, of class UtilFile.
   */
  @Test
  public void testComputeSubdirectories_Bid4WinList_String()
  {
    try
    {
      Bid4WinStringRecursiveMap result =
          UtilFile.computeSubdirectories(new Bid4WinList<String>());
      assertEquals(new Bid4WinStringRecursiveMap(), result);

      result = UtilFile.computeSubdirectories(new Bid4WinList<String>(UtilString.EMPTY));
      assertEquals(new Bid4WinStringRecursiveMap(), result);

      result = UtilFile.computeSubdirectories(
          new Bid4WinList<String>("1/1_1/1_1_1", "1/1_1/1_1_2/1_1_2_1"), UtilString.EMPTY);
      Bid4WinStringRecursiveMap expected = new Bid4WinStringRecursiveMap();
      Bid4WinStringRecursiveMap expected_1 = new Bid4WinStringRecursiveMap();
      expected.put("1", expected_1);
      Bid4WinStringRecursiveMap expected_1_1 = new Bid4WinStringRecursiveMap();
      expected_1.put("1_1", expected_1_1);
      Bid4WinStringRecursiveMap expected_1_1_1 = new Bid4WinStringRecursiveMap();
      expected_1_1.put("1_1_1", expected_1_1_1);
      Bid4WinStringRecursiveMap expected_1_1_2 = new Bid4WinStringRecursiveMap();
      expected_1_1.put("1_1_2", expected_1_1_2);
      Bid4WinStringRecursiveMap expected_1_1_2_1 = new Bid4WinStringRecursiveMap();
      expected_1_1_2.put("1_1_2_1", expected_1_1_2_1);
      assertEquals(expected, result);

      result = UtilFile.computeSubdirectories(
          new Bid4WinList<String>("1/1_1/1_1_1", "1/1_1/1_1_2/1_1_2_1"), "1/1_1");
      assertEquals(expected_1_1, result);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      UtilFile.computeSubdirectories(
        new Bid4WinList<String>("1/1_1/1_1_1", "1/1_1/1_1_2/1_1_2_1", "2"), "1/1_1");
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      UtilFile.computeSubdirectories(
        new Bid4WinList<String>("1/1_1/1_1_1", "1/1_1/1_1_2/1_1_2_1", UtilString.EMPTY), "1/1_1");
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Cette méthode permet de récupérer le chemin d'accès au projet en argument
   * dans l'espace de travail courant
   * @param projectName Nom du projet recherché
   * @return Le chemin d'accès au projet en argument dans l'espace de travail courant
   * @throws UserException Si le projet ne peut être trouvé
   */
  public static String getProjectPath(String projectName) throws UserException
  {
    String path = new java.io.File(UtilString.EMPTY).getAbsoluteFile().getParent();
    path = UtilFile.concatAbsolutePath(ResourceRef.RESOURCE, path, projectName);
    File file = new File(path);
    UtilBoolean.checkTrue("path", file.exists(), ResourceRef.RESOURCE_UNKNOWN_ERROR);
    return path;
  }
}