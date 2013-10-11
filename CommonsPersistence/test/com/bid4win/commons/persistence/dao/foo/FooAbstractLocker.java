package com.bid4win.commons.persistence.dao.foo;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <FOO> Entité sur laquelle poser le lock<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooAbstractLocker<FOO extends FooAbstract<FOO>>
{

  /**
   *
   * TODO A COMMENTER
   * @param thread TODO A COMMENTER
   * @param id TODO A COMMENTER
   * @param wait TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  @Transactional(readOnly = true)
  public void lock(FooAbstractLockerThread<FOO> thread, int id, Object wait) throws PersistenceException
  {
    thread.getTest().getDao().lockById(id);
    thread.setLockDone();
    synchronized(wait)
    {
      try
      {
        wait.wait();
      }
      catch(InterruptedException ex)
      {
        ex.printStackTrace();
      }
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param wait TODO A COMMENTER
   */
  public void end(Object wait)
  {
    synchronized(wait)
    {
      wait.notify();
    }
  }
}
