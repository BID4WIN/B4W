package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;

/**
 * Cette classe définie le stockage d'une ressource accessible sous la forme d'un
 * fichier<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de stockage de ressource<BR>
 * @param <USAGE> Doit définir les utilisations de ressources associées à ce stockage<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class FileResourceStorage<CLASS extends FileResourceStorage<CLASS, TYPE, USAGE>,
                                 TYPE extends ResourceType<TYPE>,
                                 USAGE extends ResourceUsage<USAGE, TYPE, CLASS>>
       extends ResourceStorage<CLASS, TYPE, USAGE> implements Bid4WinFileResource<TYPE>
{
  /**
   * Constructeur pour création par introspection
   */
  protected FileResourceStorage()
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
  public FileResourceStorage(String path, String name, TYPE type) throws UserException
  {
    super(path, name, type);
  }
}
