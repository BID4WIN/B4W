package com.bid4win.tools.dependency.model;

import java.io.File;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Classe définissant une librairie pouvant être référencée dans un projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Librairy extends Reference<Librairy>
{
  /** Path relatif de la librairie */
  private String relativePath;

  /**
   * Constructeur par copie
   * @param librairy Librairie à partir de laquelle créer la nouvelle référence
   * @throws UserException Si la racine de la librairie en argument est invalide
   */
  public Librairy(Librairy librairy) throws UserException
  {
    this(librairy.getRootPathFile(), librairy.getRelativePath(), librairy.getName());
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param relativePath Path relatif de la librairie
   * @param name Nom de la librairie
   */
  public Librairy(String rootPath, String relativePath, String name)
  {
    this(new File(rootPath), relativePath, name);
  }
  /**
   * Constructeur
   * @param rootPath Racine du projet
   * @param relativePath Path relatif de la librairie
   * @param name Nom de la librairie
   */
  public Librairy(File rootPath, String relativePath, String name)
  {
    super(rootPath, name);
    this.setRelativePath(relativePath);
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
    return new StringBuffer("/classpath/lib/").append(this.getRelativePath()).append("/").append(this.getName());
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.tools.dependency.model.Reference#getPath()
   */
  @Override
  public String getPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getRootPath(), this.getRelativePath(), this.getName());
  }

  /**
   * Getter du path relatif de la librairie
   * @return Le path relatif de la librairie
   */
  public String getRelativePath()
  {
    return this.relativePath;
  }
  /**
   * Setter interne du path relatif de la librairie
   * @param relativePath relatif de la librairie à positionner
   */
  public void setRelativePath(String relativePath)
  {
    this.relativePath = relativePath;
  }
}
