package com.bid4win.tools.dependency.model;

import java.io.File;

import com.bid4win.commons.core.exception.UserException;

/**
 * Classe d�finissant une source d'un projet, c'est � dire une des entr�es directes
 * de son classpath<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Source extends Reference<Source>
{
  /** Projet auquel appartient la source */
  private Project<?> project;

  /**
   * Constructeur par copie
   * @param source Source � partir de laquelle cr�er la nouvelle source
   * @throws UserException Si la racine de la source en argument est invalide
   */
  protected Source(Source source) throws UserException
  {
    super(source);
    this.setProject(source.getProject());
  }
  /**
   * Constructeur
   * @param project Projet auquel appartient la source
   * @param name Nom de la source
   */
  protected Source(Project<?> project, String name)
  {
    super((File)null, name);
    this.setProject(project);
  }

  /**
   * Red�finition de la m�thode permettant de construire la partie correspondant
   * � la source dans le composant associ�
   * @param deployPath {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#toXmlComponent(java.lang.String)
   */
  @Override
  protected StringBuffer toXmlComponent(String deployPath)
  {
    StringBuffer buffer = new StringBuffer("<wb-resource");
    buffer.append(" deploy-path=\"" + deployPath + "\"");
    buffer.append(" source-path=\"").append(this.getXmlComponentHandleModule()).append("\"");
    return buffer.append("/>");
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#getXmlComponentHandleModule()
   */
  @Override
  public StringBuffer getXmlComponentHandleModule()
  {
    return new StringBuffer("/" + this.getName());
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#getRootPathFile()
   */
  @Override
  public File getRootPathFile() throws UserException
  {
    return this.getProject().getPathFile();
  }
  /**
   * Getter du projet auquel appartient la source
   * @return Le projet auquel appartient la source
   */
  public Project<?> getProject()
  {
    return this.project;
  }
  /**
   * Setter interne du projet auquel appartient la source
   * @param project Projet auquel appartient la source
   */
  private void setProject(Project<?> project)
  {
    this.project = project;
  }
}
