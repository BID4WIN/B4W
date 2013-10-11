package com.bid4win.commons.manager.resource.store;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceStoreTester;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class OutwardlyManagedFileResourceStoreTester<RESOURCE extends Bid4WinFileResource<TYPE>,
                                                              TYPE extends ResourceType<TYPE>>
       extends Bid4WinFileResourceStoreTester<RESOURCE, TYPE>
{
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStoreTester#testLock_0args()
   */
  @Override
  @Test
  public void testLock_0args() throws Exception
  {
    try
    {
      this.getOutwardlyManagedStore().getWorkingPath();
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    super.testLock_0args();
    while(this.getOutwardlyManagedStore().unlock() > 0)
    {
      // On vide la pile de blocage
    }
    try
    {
      this.getOutwardlyManagedStore().getWorkingPath();
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    this.getOutwardlyManagedStore().lock();
    assertFalse("Wrong lock file directory",
                new File(this.getOutwardlyManagedStore().getWorkingPath()).equals(this.getLockFile().getParentFile()));
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStoreTester#testUnlock_0args()
   */
  @Override
  @Test
  public void testUnlock_0args() throws Exception
  {
    super.testUnlock_0args();
    assertFalse("Lock file parent directory should not exist",
                this.getLockFile().getParentFile().exists());
    try
    {
      this.getOutwardlyManagedStore().getWorkingPath();
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract OutwardlyManagedFileResourceStore<?, ?> getOutwardlyManagedStore();
}
