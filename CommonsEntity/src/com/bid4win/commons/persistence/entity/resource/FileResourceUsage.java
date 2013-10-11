package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe définie l'utilisation d'une ressource sous la forme d'un fichier<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types d'utilisation ressource<BR>
 * @param <STORAGE> Doit définir le stockage de la ressource associée à cette utilisation<BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class FileResourceUsage<CLASS extends FileResourceUsage<CLASS, TYPE, STORAGE>,
                               TYPE extends ResourceType<TYPE>,
                               STORAGE extends ResourceStorage<STORAGE, TYPE, CLASS>>
       extends ResourceUsage<CLASS, TYPE, STORAGE> implements Bid4WinFileResource<TYPE>
{
  /**
   * Constructeur pour création par introspection
   */
  protected FileResourceUsage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement d'utilisation de la ressource
   * @param name Nom d'utilisation de la ressource
   * @param storage Stockage de la ressource associée à cette utilisation
   * @throws ProtectionException Si le stockage de ressource en argument est protégé
   * @throws ModelArgumentException Si le stockage de ressource en argument est nul
   * @throws UserException Si le nom ou l'emplacement d'utilisation de la ressource
   * est invalide
   */
  public FileResourceUsage(String path, String name, STORAGE storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
  }
}
