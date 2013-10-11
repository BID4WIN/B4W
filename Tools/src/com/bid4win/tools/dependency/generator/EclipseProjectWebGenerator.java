package com.bid4win.tools.dependency.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.Bid4WinFileReader;
import com.bid4win.commons.core.io.Bid4WinFileWriter;
import com.bid4win.commons.core.io.KeyboardSelection;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.tools.dependency.DependencyManager;
import com.bid4win.tools.dependency.EclipseProjectWebItem;
import com.bid4win.tools.dependency.model.EclipseProject;
import com.bid4win.tools.dependency.model.EclipseProjectWeb;
import com.bid4win.tools.dependency.model.Librairy;

/**
 * Cette classe permet de générer le classpath d'un projet WEB<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class EclipseProjectWebGenerator extends EclipseProjectWebItem
{
  /**
   *
   * TODO A COMMENTER
   * @param manager TODO A COMMENTER
   */
  public EclipseProjectWebGenerator(DependencyManager manager)
  {
    super("Generate projects classpath", manager);
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
    System.out.println("Update " + project.getName() + " files (y/n):");
    boolean update = KeyboardSelection.getInstance().parseBooleanYesOrNo();
    System.out.println("Generate for JETTY (y/n):");
    boolean jetty = KeyboardSelection.getInstance().parseBooleanYesOrNo();
    this.generate(project, update, jetty);
  }

  /**
   *
   * TODO A COMMENTER
   * @param project TODO A COMMENTER
   * @param update TODO A COMMENTER
   * @param jetty TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws CommunicationException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected void generate(EclipseProjectWeb project, boolean update, boolean jetty)
            throws PersistenceException, CommunicationException, ModelArgumentException, UserException
  {
    if(update)
    {
      for(EclipseProject subProject : project.getRecursiveSubProjectList())
      {
        this.apply(subProject.toXmlProject().toString(), subProject.getProjectPath());
        this.apply(subProject.toXmlComponent().toString(), subProject.getComponentPath());
        this.apply(subProject.toXmlFacet().toString(), subProject.getFacetPath());
        this.apply(subProject.toPropertiesPrefs().toString(), subProject.getPrefsPath());
      }
    }
    this.apply(project.toXmlProject().toString(), project.getProjectPath(), update);
    this.apply(project.toXmlComponent().toString(), project.getComponentPath(), update);
    // Déploiement des libraires pour un serveur JETTY
    File libDir = new File(project.getLibPath());
    if(libDir.exists())
    {
      for(File file : libDir.listFiles())
      {
        if(file.isFile())
        {
          file.delete();
        }
      }
    }
    else
    {
      libDir.mkdirs();
    }
    if(jetty)
    {
      for(Librairy librairy : project.getRecursiveLibrairySet())
      {
        UtilFile.copy(librairy.getPathFile(),
                      UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE,
                                                        libDir.getAbsolutePath(),
                                                        librairy.getName()));
      }
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param filename TODO A COMMENTER
   * @param update TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws CommunicationException TODO A COMMENTER
   */
  protected void apply(String result, String filename, boolean update)
            throws PersistenceException, CommunicationException
  {
    if(update)
    {
      this.apply(result, filename);
    }
    else
    {
      System.out.println("###################### " + filename + " ######################");
      System.out.println(result);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param filename TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   * @throws CommunicationException TODO A COMMENTER
   */
  protected void apply(String result, String filename)
            throws PersistenceException, CommunicationException
  {
    File existing = new File(filename);
    // On va comparer l'existant avec le résultat attendu avant de le modifier
    if(existing.exists())
    {
      try
      {
        Bid4WinFileReader reader1 = new Bid4WinFileReader(filename);
        BufferedReader reader2 = new BufferedReader(new StringReader(result));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        while(line1 != null && line2 != null)
        {
          if(!Bid4WinComparator.getInstance().equals(line1, line2))
          {
            break;
          }
          line1 = reader1.readLine();
          line2 = reader2.readLine();
        }
        // L'existant n'a pas à être modifié
        if(Bid4WinComparator.getInstance().equals(line1, line2))
        {
          System.out.println(filename + " IDENTICAL");
          return;
        }
      }
      catch(IOException ex)
      {
        System.out.println("!!! Cannot READ " + filename + ": " + ex.getMessage());
        return;
      }
    }
    Bid4WinFileWriter writer = null;
    try
    {
      writer = new Bid4WinFileWriter(filename);
      writer.write(result);
      System.out.println(filename + " UPDATED");
    }
    catch(CommunicationException ex)
    {
      System.out.println("!!! Cannot WRITE " + filename + ": " + ex.getMessage());
    }
    finally
    {
      if(writer != null)
      {
        writer.close();
      }
    }
  }
}
