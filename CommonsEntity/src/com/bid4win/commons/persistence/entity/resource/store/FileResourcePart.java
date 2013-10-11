package com.bid4win.commons.persistence.entity.resource.store;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourcePart;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart;

/**
 * Cette classe défini la base des portions de ressource utilisées sous forme de
 * fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FileResourcePart<CLASS extends FileResourcePart<CLASS, TYPE, PART_TYPE>,
                                       TYPE extends ResourceType<TYPE>,
                                       PART_TYPE extends Bid4WinObjectType<PART_TYPE>>
       extends ResourcePart<CLASS, TYPE, PART_TYPE>
       implements Bid4WinFileResourcePart<CLASS, TYPE, PART_TYPE>
{
  /**
   * Constructeur à partir de la référence d'un stockage de ressource
   * @param storage Référence du stockage de ressource
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public FileResourcePart(ResourceStorageMultiPart<?, TYPE, ?, PART_TYPE, CLASS> storage,
                          PART_TYPE partType)
         throws ModelArgumentException, UserException
  {
    super(storage, partType);
  }
  /**
   * Constructeur à partir de la référence d'une utilisation de ressource
   * @param usage Référence de l'utilisation de ressource
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public FileResourcePart(ResourceUsageMultiPart<?, TYPE, ?, PART_TYPE, CLASS> usage,
                          PART_TYPE partType)
         throws ModelArgumentException, UserException
  {
    super(usage, partType);
  }
  /**
   * Constructeur à partir d'une portion de ressource
   * @param part Portion de ressource à partir de laquelle construire la nouvelle
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la portion de ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public FileResourcePart(CLASS part, PART_TYPE partType)
         throws ModelArgumentException, UserException
  {
    super(part, partType);
  }
}
