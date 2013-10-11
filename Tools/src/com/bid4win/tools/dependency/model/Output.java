package com.bid4win.tools.dependency.model;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Output extends Source
{
  /**
   * Constructeur par copie
   * @param output Entrée de déploiement à partir de laquelle créer la nouvelle
   * @throws UserException Si la racine de l'entrée de déploiement en argument est
   * invalide
   */
  protected Output(Output output) throws UserException
  {
    super(output);
  }
  /**
   * Constructeur
   * @param project Projet auquel appartient l'entrée de déploiement
   * @param name Nom de l'entrée de déploiement
   */
  protected Output(Project<?> project, String name)
  {
    super(project, name);
  }

  /**
   * Redéfinition de la méthode permettant de construire la partie correspondant
   * à l'entrée de déploiement dans le composant associé
   * @param deployPath {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#toXmlComponent(java.lang.String)
   */
  @Override
  protected StringBuffer toXmlComponent(String deployPath)
  {
    StringBuffer buffer = new StringBuffer("<property name=\"java-output-path\" value=\"");
    buffer.append(this.getXmlComponentHandleModule()).append("\"");
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
    return new StringBuffer("/" + this.getProject().getName() + "/" + this.getName());
  }
}
