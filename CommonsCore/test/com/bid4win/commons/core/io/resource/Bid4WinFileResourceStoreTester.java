package com.bid4win.commons.core.io.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinFileResourceStoreTester<RESOURCE extends Bid4WinFileResource<TYPE>,
                                                     TYPE>
       extends Bid4WinResourceStoreTester<RESOURCE, TYPE>
{
  /** TODO A COMMENTER */
  private File lockFile = null;
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected File getLockFile()
  {
    return this.lockFile;
  }
  /**
   *
   * TODO A COMMENTER
   * @param lockFile TODO A COMMENTER
   */
  protected void setLockFile(File lockFile)
  {
    this.lockFile = lockFile;
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void testLock_0args() throws Exception
  {
    int expected = 0;
    int lockNb = this.getStore().lock();
    expected++;
    File lockFile1 = this.getStore().getLockFile();
    assertTrue("Wrong lock nb", expected == lockNb);
    assertTrue("Lock file should exist", lockFile1.exists());
    assertTrue("Wrong lock file directory",
               new File(this.getStore().getWorkingPath()).equals(lockFile1.getParentFile()));

    lockNb = this.getStore().lock();
    expected++;
    File lockFile2 = this.getStore().getLockFile();
    assertTrue("Wrong lock nb", expected == lockNb);
    assertTrue("Lock file should not have changed", lockFile1.equals(lockFile2));
    assertTrue("Lock file should exist", lockFile1.exists());

    lockNb = this.getStore().lock();
    expected++;
    lockFile2 = this.getStore().getLockFile();
    assertTrue("Wrong lock nb", expected == lockNb);
    assertTrue("Lock file should not have changed", lockFile1.equals(lockFile2));
    assertTrue("Lock file should exist", lockFile1.exists());

    this.getStore().unlock();
    expected--;
    lockNb = this.getStore().lock();
    expected++;
    lockFile2 = this.getStore().getLockFile();
    assertTrue("Wrong lock nb", expected == lockNb);
    assertTrue("Lock file should not have changed", lockFile1.equals(lockFile2));
    assertTrue("Lock file should exist", lockFile1.exists());
    this.setLockFile(lockFile1);
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void testUnlock_0args() throws Exception
  {
    int expected = 0;
    int lockNb = this.getStore().unlock();
    assertTrue("Wrong lock nb", expected == lockNb);

    lockNb = this.getStore().lock();
    expected++;
    lockNb = this.getStore().lock();
    expected++;
    lockNb = this.getStore().lock();
    expected++;
    File lockFile = this.getStore().getLockFile();
    assertTrue("Wrong lock nb", expected == lockNb);
    assertTrue("Lock file should exist", lockFile.exists());

    while(lockNb != 1)
    {
      lockNb = this.getStore().unlock();
      expected--;
      assertTrue("Wrong lock nb", expected == lockNb);
      assertTrue("Lock file should exist", lockFile.exists());
    }

    lockNb = this.getStore().unlock();
    expected--;
    assertTrue("Wrong lock nb", expected == lockNb);
    assertFalse("Lock file should not exist", lockFile.exists());
    this.setLockFile(lockFile);
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  @Test
  public void testGetSubdirectories_String() throws Exception
  {
    // S'assure de la création du magasin
    Bid4WinStringRecursiveMap result = this.getStore().getSubdirectories("");
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories("1");
    assertTrue("", 0 == result.size());

    Bid4WinStringRecursiveMap root = new Bid4WinStringRecursiveMap();

    int i = 0;
    FileInputStream inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    RESOURCE resource = this.createResource(this.concatRelativePath("1", "1_1", "1_1_1"),
                                            "" + i++, this.getType1());
    try
    {
      this.getStore().store(inputStream, resource);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_1 = new Bid4WinStringRecursiveMap();
    root.put("1", root_1);
    Bid4WinStringRecursiveMap root_1_1 = new Bid4WinStringRecursiveMap();
    root_1.put("1_1", root_1_1);
    Bid4WinStringRecursiveMap root_1_1_1 = new Bid4WinStringRecursiveMap();
    root_1_1.put("1_1_1", root_1_1_1);

    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    resource = this.createResource(this.concatRelativePath("1", "1_1", "1_1_1"),
                                   "" + i++, this.getType1());
    try
    {
      this.getStore().store(inputStream, resource);
    }
    finally
    {
      inputStream.close();
    }
    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    resource = this.createResource(this.concatRelativePath("1", "1_1", "1_1_2"),
                                   "" + i++, this.getType1());
    try
    {
      this.getStore().store(inputStream, resource);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_1_1_2 = new Bid4WinStringRecursiveMap();
    root_1_1.put("1_1_2", root_1_1_2);


    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    resource = this.createResource(this.concatRelativePath("1", "1_1"),
                                   "" + i++, this.getType1());
    try
    {
      this.getStore().store(inputStream, resource);
    }
    finally
    {
      inputStream.close();
    }
    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    resource = this.createResource(this.concatRelativePath("2", "2_1"),
                                   "" + i++, this.getType1());
    try
    {
      this.getStore().store(inputStream, resource);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_2 = new Bid4WinStringRecursiveMap();
    root.put("2", root_2);
    Bid4WinStringRecursiveMap root_2_1 = new Bid4WinStringRecursiveMap();
    root_2.put("2_1", root_2_1);

    this.getStore().lock();
    File lockDir = this.getStore().getLockFile().getParentFile();
    inputStream = new FileInputStream(this.getTestFile(this.getFilename1()));
    resource = this.createResource(this.concatRelativePath("2", lockDir.getName()),
                                   "" + i++, this.getType1());
    try
    {
      this.getStore().store(inputStream, resource);
    }
    finally
    {
      inputStream.close();
    }
    Bid4WinStringRecursiveMap root_2_lockDir = new Bid4WinStringRecursiveMap();
    root_2.put(lockDir.getName(), root_2_lockDir);

    result = this.getStore().getSubdirectories("");
    assertTrue("", result.equals(root));
    result = this.getStore().getSubdirectories("1");
    assertTrue("", result.equals(root_1));
    result = this.getStore().getSubdirectories(this.concatRelativePath("1", "1_1"));
    assertTrue("", result.equals(root_1_1));
    result = this.getStore().getSubdirectories(this.concatRelativePath("1", "1_1", "1_1_1"));
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories(this.concatRelativePath("1", "1_1", "1_1_2"));
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories("2");
    assertTrue("", result.equals(root_2));
    result = this.getStore().getSubdirectories(this.concatRelativePath("2", "2_1"));
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories("3");
    assertTrue("", 0 == result.size());

    this.getStore().unlock();
    result = this.getStore().getSubdirectories("");
    assertTrue("", result.equals(root));
    result = this.getStore().getSubdirectories("1");
    assertTrue("", result.equals(root_1));
    result = this.getStore().getSubdirectories(this.concatRelativePath("1", "1_1"));
    assertTrue("", result.equals(root_1_1));
    result = this.getStore().getSubdirectories(this.concatRelativePath("1", "1_1", "1_1_1"));
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories(this.concatRelativePath("1", "1_1", "1_1_2"));
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories("2");
    assertTrue("", result.equals(root_2));
    result = this.getStore().getSubdirectories(this.concatRelativePath("2", "2_1"));
    assertTrue("", 0 == result.size());
    result = this.getStore().getSubdirectories("3");
    assertTrue("", 0 == result.size());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#getStore()
   */
  @Override
  protected Bid4WinFileResourceStore<RESOURCE, TYPE> getStore()
  {
    return (Bid4WinFileResourceStore<RESOURCE, TYPE>)super.getStore();
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    while(this.getStore().getLockNb() > 0)
    {
      // On vide la pile de blocage
      this.getStore().unlock();
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceStoreTester#tearDown()
   */
  @Override
  @After
  public void tearDown() throws Exception
  {
    super.tearDown();
    while(this.getStore().getLockNb() > 0)
    {
      // On vide la pile de blocage
      this.getStore().unlock();
    }
  }
}
