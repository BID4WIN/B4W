package com.bid4win.tools.dependency.model;

import java.io.File;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Classe définissant une référence utilisée pour la représentation d'éléments
 * constituants un projet<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Reference<CLASS extends Reference<CLASS>>
       extends Bid4WinEntity<CLASS, String> implements Comparable<CLASS>
{
  /** Racine de la référence */
  private File rootPath;

  /**
   * Constructeur par copie
   * @param reference Reference à partir de laquelle créer la nouvelle référence
   * @throws UserException Si la racine de la référence en argument est invalide
   */
  public Reference(Reference<?> reference) throws UserException
  {
    this(reference.getRootPathFile(), reference.getName());
  }
  /**
   * Constructeur
   * @param rootPath Racine de la référence
   * @param name Nom de la référence
   */
  public Reference(String rootPath, String name)
  {
    this(new File(rootPath), name);
  }
  /**
   * Constructeur
   * @param rootPath Racine de la référence
   * @param name Nom de la référence
   */
  public Reference(File rootPath, String name)
  {
    super(name);
    this.setRootPathFile(rootPath);
  }
  /**
   * Constructeur
   * @param path Emplacement de la référence
   */
  public Reference(File path)
  {
    this(path.getParent(), path.getName());
  }

  /**
   * Cette méthode permet de construire la partie correspondant à la référence
   * dans le composant associé
   * @param deployPath Chemin de déploiement utilisé pour la référence
   * @return La partie correspondant à la référence dans le composant associé
   */
  protected StringBuffer toXmlComponent(String deployPath)
  {
    StringBuffer buffer = new StringBuffer("<dependent-module");
    buffer.append(" archiveName=\"").append(this.getArchiveName()).append("\"");
    buffer.append(" deploy-path=\"").append(deployPath).append("\"");
    buffer.append(" handle=\"module:").append(this.getXmlComponentHandleModule()).append("\"");
    buffer.append(">");
    UtilString.addParagraph(buffer, new StringBuffer("<dependency-type>uses</dependency-type>"), 1);
    return UtilString.addParagraph(buffer, "</dependent-module>");
  }
  /**
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract StringBuffer getXmlComponentHandleModule();
  /**
   * Cette méthode permet de récupérer le nom de l'archive (ie le jar) correspondant
   * à la référence courante
   * @return Le nom de l'archive (ie le jar) correspondant à la référence courante
   */
  public StringBuffer getArchiveName()
  {
    return new StringBuffer(this.getName());
  }

  /**
   * Getter du chemin complet de la référence courante
   * @return Le chemin complet de la référence courante
   * @throws UserException Si le chemin complet de la référence courante est invalide
   */
  public File getPathFile() throws UserException
  {
    return new File(this.getPath());
  }
  /**
   * Getter du chemin complet de la référence courante
   * @return Le chemin complet de la référence courante
   * @throws UserException Si le chemin complet de la référence courante est invalide
   */
  public String getPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getRootPath(), this.getName());
  }

  /**
   * Getter de la racine de la référence
   * @return La racine de la référence
   * @throws UserException Si la racine de la référence est invalide
   */
  public String getRootPath() throws UserException
  {
    return this.getRootPathFile().getAbsolutePath();
  }
  /**
   * Getter de la racine de la référence
   * @return La racine de la référence
   * @throws UserException Si la racine de la référence est invalide
   */
  @SuppressWarnings("unused")
  public File getRootPathFile() throws UserException
  {
    return this.rootPath;
  }
  /**
   * Setter interne de la racine de la référence
   * @param rootPath Racine de la référence à positionner
   */
  private void setRootPathFile(File rootPath)
  {
    this.rootPath = rootPath;
  }

  /**
   * Getter du nom du projet
   * @return Le nom du projet
   */
  public String getName()
  {
    return this.getId();
  }

  /**
   * Permet d'effectuer le rendu simple de la référence courant sans prise en compte
   * de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  public StringBuffer renderRelationNone()
  {
    return new StringBuffer("NAME=" + this.getName());
  }
  /**
   * La comparaison est défini en utilisant le nom des références afin de les
   * classer par ordre alphabétique
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(CLASS toBeCompared)
  {
    return this.getName().toUpperCase().compareTo(toBeCompared.getName().toUpperCase());
  }
}
