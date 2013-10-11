package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.commons.persistence.entity.resource.store.FileResourcePart;

/**
 * Cette classe d�finie le stockage d'une ressource divis�e en portion accessible
 * sous la forme d'un fichier<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de stockage de ressource<BR>
 * @param <USAGE> Doit d�finir les utilisations de ressources associ�es � ce stockage<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * @param <PART> Doit d�finir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class FileResourceStorageMultiPart<CLASS extends FileResourceStorageMultiPart<CLASS, TYPE, USAGE, PART_TYPE, PART>,
                                                   TYPE extends ResourceType<TYPE>,
                                                   USAGE extends ResourceUsageMultiPart<USAGE, TYPE, CLASS, PART_TYPE, PART>,
                                                   PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                   PART extends FileResourcePart<PART, TYPE, PART_TYPE>>
       extends ResourceStorageMultiPart<CLASS, TYPE, USAGE, PART_TYPE, PART>
       implements Bid4WinFileResourceMultiPart<TYPE, PART_TYPE, PART>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected FileResourceStorageMultiPart()
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
  public FileResourceStorageMultiPart(String path, String name, TYPE type) throws UserException
  {
    super(path, name, type);
  }
}
