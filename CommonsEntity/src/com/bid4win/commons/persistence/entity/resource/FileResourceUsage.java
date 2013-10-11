package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe d�finie l'utilisation d'une ressource sous la forme d'un fichier<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types d'utilisation ressource<BR>
 * @param <STORAGE> Doit d�finir le stockage de la ressource associ�e � cette utilisation<BR>
 * @author Emeric Fill�tre
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
   * Constructeur pour cr�ation par introspection
   */
  protected FileResourceUsage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement d'utilisation de la ressource
   * @param name Nom d'utilisation de la ressource
   * @param storage Stockage de la ressource associ�e � cette utilisation
   * @throws ProtectionException Si le stockage de ressource en argument est prot�g�
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
