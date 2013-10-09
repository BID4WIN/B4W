package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Définition des ressources bouchonnées pour le test du magasin sous forme de fichier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinFileResourceStub extends Bid4WinObject<Bid4WinFileResourceStub>
       implements Bid4WinFileResource<String>
{
  /** Emplacement de stockage de la ressource */
  private String path = null;
  /** Nom de la ressource */
  private String name = null;
  /** Type de la ressource */
  private String type = null;

  /**
   * Constructeur pour création par introspection
   */
  protected Bid4WinFileResourceStub()
  {
    super();
  }

  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   */
  public Bid4WinFileResourceStub(String path, String name, String type)
  {
    this.definePath(path);
    this.defineName(name);
    this.defineType(type);
  }

  /**
   * Getter du chemin d'accès complet à la ressource
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResource#getFullPath()
   */
  @Override
  public String getFullPath() throws UserException
  {
    return UtilFile.concatRelativePath(ResourceRef.RESOURCE, this.getPath(), this.getName());
  }

  /**
   * Getter de l'emplacement de stockage de la ressource
   * @return L'emplacement de stockage de la ressource
   */
  public String getPath()
  {
    return this.path;
  }
  /**
   * Setter de l''emplacement de stockage de la ressource
   * @param path Emplacement de stockage de la ressource
   */
  private void definePath(String path)
  {
    this.path = path;
  }
  /**
   * Getter du nom de la ressource
   * @return Le nom de la ressource
   */
  public String getName()
  {
    return this.name;
  }
  /**
   * Setter du nom de la ressource
   * @param name Nom de la ressource
   */
  private void defineName(String name)
  {
    this.name = name;
  }
  /**
   * Getter du type de la ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResource#getType()
   */
  @Override
  public String getType()
  {
    return this.type;
  }
  /**
   * Setter du type de la ressource
   * @param type Type de la ressource
   */
  private void defineType(String type)
  {
    this.type = type;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return ResourceRef.RESOURCE;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    // TODO Auto-generated method stub
    return 0;
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(Bid4WinFileResourceStub toBeCompared)
  {
    return this.getPath().equals(toBeCompared.getPath()) &&
           this.getName().equals(toBeCompared.getName()) &&
           this.getType().equals(toBeCompared.getType());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = new StringBuffer("PATH=").append(this.getPath());
    buffer.append(" NAME=").append(this.getName());
    buffer.append(" TYPE=").append(this.getType());
    return buffer;
  }
}
