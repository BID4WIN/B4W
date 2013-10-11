package com.bid4win.tools.dependency.model;

import java.io.File;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Classe d�finissant une r�f�rence utilis�e pour la repr�sentation d'�l�ments
 * constituants un projet<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Reference<CLASS extends Reference<CLASS>>
       extends Bid4WinEntity<CLASS, String> implements Comparable<CLASS>
{
  /** Racine de la r�f�rence */
  private File rootPath;

  /**
   * Constructeur par copie
   * @param reference Reference � partir de laquelle cr�er la nouvelle r�f�rence
   * @throws UserException Si la racine de la r�f�rence en argument est invalide
   */
  public Reference(Reference<?> reference) throws UserException
  {
    this(reference.getRootPathFile(), reference.getName());
  }
  /**
   * Constructeur
   * @param rootPath Racine de la r�f�rence
   * @param name Nom de la r�f�rence
   */
  public Reference(String rootPath, String name)
  {
    this(new File(rootPath), name);
  }
  /**
   * Constructeur
   * @param rootPath Racine de la r�f�rence
   * @param name Nom de la r�f�rence
   */
  public Reference(File rootPath, String name)
  {
    super(name);
    this.setRootPathFile(rootPath);
  }
  /**
   * Constructeur
   * @param path Emplacement de la r�f�rence
   */
  public Reference(File path)
  {
    this(path.getParent(), path.getName());
  }

  /**
   * Cette m�thode permet de construire la partie correspondant � la r�f�rence
   * dans le composant associ�
   * @param deployPath Chemin de d�ploiement utilis� pour la r�f�rence
   * @return La partie correspondant � la r�f�rence dans le composant associ�
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
   * Cette m�thode permet de r�cup�rer le nom de l'archive (ie le jar) correspondant
   * � la r�f�rence courante
   * @return Le nom de l'archive (ie le jar) correspondant � la r�f�rence courante
   */
  public StringBuffer getArchiveName()
  {
    return new StringBuffer(this.getName());
  }

  /**
   * Getter du chemin complet de la r�f�rence courante
   * @return Le chemin complet de la r�f�rence courante
   * @throws UserException Si le chemin complet de la r�f�rence courante est invalide
   */
  public File getPathFile() throws UserException
  {
    return new File(this.getPath());
  }
  /**
   * Getter du chemin complet de la r�f�rence courante
   * @return Le chemin complet de la r�f�rence courante
   * @throws UserException Si le chemin complet de la r�f�rence courante est invalide
   */
  public String getPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(
        ResourceRef.RESOURCE, this.getRootPath(), this.getName());
  }

  /**
   * Getter de la racine de la r�f�rence
   * @return La racine de la r�f�rence
   * @throws UserException Si la racine de la r�f�rence est invalide
   */
  public String getRootPath() throws UserException
  {
    return this.getRootPathFile().getAbsolutePath();
  }
  /**
   * Getter de la racine de la r�f�rence
   * @return La racine de la r�f�rence
   * @throws UserException Si la racine de la r�f�rence est invalide
   */
  @SuppressWarnings("unused")
  public File getRootPathFile() throws UserException
  {
    return this.rootPath;
  }
  /**
   * Setter interne de la racine de la r�f�rence
   * @param rootPath Racine de la r�f�rence � positionner
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
   * Permet d'effectuer le rendu simple de la r�f�rence courant sans prise en compte
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
   * La comparaison est d�fini en utilisant le nom des r�f�rences afin de les
   * classer par ordre alphab�tique
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
