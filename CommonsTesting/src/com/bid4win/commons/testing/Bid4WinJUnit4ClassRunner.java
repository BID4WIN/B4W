package com.bid4win.commons.testing;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinJUnit4ClassRunner extends SpringJUnit4ClassRunner
{
  /** TODO A COMMENTER */
  private static boolean initialized = false;
  /** TODO A COMMENTER */
  private static Bid4WinTestTraceListener listener = new Bid4WinTestTraceListener();

  /**
   * Constructs a new <code>SpringJUnit4ClassRunner</code> and initializes a
   * {@link TestContextManager} to provide Spring testing functionality to
   * standard JUnit tests.
   * @param clazz the Class object corresponding to the test class to be run
   * @throws InitializationError TODO A COMMENTER
   * @see #createTestContextManager(Class)
   */
  public Bid4WinJUnit4ClassRunner(Class<?> clazz) throws InitializationError
  {
    super(clazz);
  }

  /**
   *
   * TODO A COMMENTER
   * @param notifier {@inheritDoc}
   * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner#run(org.junit.runner.notification.RunNotifier)
   */
  @Override
  public void run(RunNotifier notifier)
  {
    if(!initialized)
    {
      notifier.addListener(listener);
      initialized = true;
    }
    super.run(notifier);
  }
}
