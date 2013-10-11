package com.bid4win.tools.dependency.model;

import com.bid4win.commons.core.exception.UserException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Output extends Source
{
  /**
   * Constructeur par copie
   * @param output Entr�e de d�ploiement � partir de laquelle cr�er la nouvelle
   * @throws UserException Si la racine de l'entr�e de d�ploiement en argument est
   * invalide
   */
  protected Output(Output output) throws UserException
  {
    super(output);
  }
  /**
   * Constructeur
   * @param project Projet auquel appartient l'entr�e de d�ploiement
   * @param name Nom de l'entr�e de d�ploiement
   */
  protected Output(Project<?> project, String name)
  {
    super(project, name);
  }

  /**
   * Red�finition de la m�thode permettant de construire la partie correspondant
   * � l'entr�e de d�ploiement dans le composant associ�
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
