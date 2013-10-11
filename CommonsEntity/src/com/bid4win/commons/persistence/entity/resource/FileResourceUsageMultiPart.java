package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.resource.store.FileResourcePart;

/**
 * Cette classe d�finie l'utilisation d'une ressource divis�e en portion accessible
 * sous la forme d'un fichier<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types d'utilisation ressource<BR>
 * @param <STORAGE> Doit d�finir le stockage de la ressource associ�e � cette utilisation<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * @param <PART> Doit d�finir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class FileResourceUsageMultiPart<CLASS extends FileResourceUsageMultiPart<CLASS, TYPE, STORAGE, PART_TYPE, PART>,
                                                 TYPE extends ResourceType<TYPE>,
                                                 STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, CLASS, PART_TYPE, PART>,
                                                 PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                 PART extends FileResourcePart<PART, TYPE, PART_TYPE>>
       extends ResourceUsageMultiPart<CLASS, TYPE, STORAGE, PART_TYPE, PART>
       implements Bid4WinFileResourceMultiPart<TYPE, PART_TYPE, PART>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected FileResourceUsageMultiPart()
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
  public FileResourceUsageMultiPart(String path, String name, STORAGE storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
  }
}
