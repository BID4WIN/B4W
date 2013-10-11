package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;

/**
 * Cette classe d�finie une ressource accessible sous la forme d'un fichier<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class FileResource<CLASS extends FileResource<CLASS, TYPE>,
                          TYPE extends ResourceType<TYPE>>
       extends Resource<CLASS, TYPE> implements Bid4WinFileResource<TYPE>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected FileResource()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * ressource est invalide
   */
  public FileResource(String path, String name, TYPE type) throws UserException
  {
    super(path, name, type);
  }
}
