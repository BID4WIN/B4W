package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.io.Bid4WinFileReader;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test des propriétés triées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinSortedPropertiesTest extends Bid4WinCoreTester
{
  /** TODO A COMMENTER */
  private Bid4WinSet<File> fileSet = new Bid4WinSet<File>();

  /**
   * Test of keys() method, of class Bid4WinSortedProperties.
   */
  @Test
  public void testkeys_0args()
  {
    Bid4WinSortedProperties properties = new Bid4WinSortedProperties();
    properties.put("2", "");
    properties.put("1", "");
    properties.put("8", "");
    properties.put("5", "");
    properties.put("9", "");
    properties.put("7", "");
    Enumeration<?> enumeration = properties.keys();
    String lastKey = null;
    while(enumeration.hasMoreElements())
    {
      String key = (String)enumeration.nextElement();
      if(lastKey != null)
      {
        assertTrue("Bad order", 0 > lastKey.compareTo(key));
      }
      lastKey = key;
    }
  }
  /**
   * Test of store(OutputStream, String) method, of class Bid4WinSortedProperties.
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void testStore_OutputStream_String() throws Exception
  {
    Bid4WinSortedProperties properties = new Bid4WinSortedProperties();
    properties.put("2", "");
    properties.put("1", "");
    properties.put("8", "");
    properties.put("5", "");
    properties.put("9", "");
    properties.put("7", "");
    File file = this.addFile("d:/" + IdGenerator.generateId() + ".properties");
    properties.store(new FileOutputStream(file), "");
    Bid4WinFileReader reader = new Bid4WinFileReader(file);
    try
    {
      String line1 = reader.readLine();
      String line2 = null;
      while(line1 != null)
      {
        line2 = reader.readLine();
        if(!line1.startsWith("#"))
        {
          String key1 = line1.substring(0, line1.indexOf("="));
          if(line2 != null)
          {
            if(!line2.startsWith("#"))
            {
              String key2 = line2.substring(0, line2.indexOf("="));
              assertTrue("Wrong order", key1.compareTo(key2) < 0);
            }
            else
            {
              line2 = line1;
            }
          }
        }
        line1 = line2;
      }
    }
    finally
    {
      reader.close();
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param fileName TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public File addFile(String fileName)
  {
    return this.addFile(new File(fileName));
  }
  /**
   *
   * TODO A COMMENTER
   * @param file TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public File addFile(File file)
  {
    this.fileSet.add(file);
    return file;
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
    for(File file : this.fileSet)
    {
      if(file.exists())
      {
        file.delete();
      }
    }
  }
}
