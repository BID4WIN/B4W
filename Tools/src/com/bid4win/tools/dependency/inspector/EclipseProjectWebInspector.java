package com.bid4win.tools.dependency.inspector;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.tools.dependency.DependencyManager;
import com.bid4win.tools.dependency.EclipseProjectWebItem;
import com.bid4win.tools.dependency.model.EclipseProjectWeb;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class EclipseProjectWebInspector extends EclipseProjectWebItem
{
  /**
   * 
   * TODO A COMMENTER
   * @param manager TODO A COMMENTER
   */
  public EclipseProjectWebInspector(DependencyManager manager)
  {
    super("Inspect projects classpath", manager);
  }

  /**
   * 
   * TODO A COMMENTER
   * @param project {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.tools.dependency.EclipseProjectWebItem#execute(com.bid4win.tools.dependency.model.EclipseProjectWeb)
   */
  @Override
  public void execute(EclipseProjectWeb project) throws Bid4WinException
  {
    System.out.println("###################### " + project.getName() + " ######################");
    System.out.println(project.toString());
  }
}
