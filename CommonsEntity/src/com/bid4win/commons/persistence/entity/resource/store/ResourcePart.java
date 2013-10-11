package com.bid4win.commons.persistence.entity.resource.store;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinResourcePart;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart;

/**
 * Cette classe défini la base des portions de ressource utilisées par les magasins
 * de stockage correspondant<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourcePart<CLASS extends ResourcePart<CLASS, TYPE, PART_TYPE>,
                                   TYPE extends ResourceType<TYPE>,
                                   PART_TYPE extends Bid4WinObjectType<PART_TYPE>>
       extends Resource<CLASS, TYPE>
       implements Bid4WinResourcePart<CLASS, TYPE, PART_TYPE>
{
  /** Type de la portion de ressource */
  private PART_TYPE partType;

  /**
   * Constructeur
   * @param path Emplacement de stockage de la portion de ressource
   * @param name Nom de la portion de ressource
   * @param type Type de la ressource
   * @param partType Type de la portion de ressource
   * @param partTypeBase Base de message des types de la portion de ressource
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  private ResourcePart(String path, String name, TYPE type, PART_TYPE partType, MessageRef partTypeBase)
          throws UserException
  {
    super(path, name, type);
    this.definePartType(partType, partTypeBase);
  }
  /**
   * Constructeur à partir de la référence d'un stockage de ressource
   * @param storage Référence du stockage de ressource
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public ResourcePart(ResourceStorageMultiPart<?, TYPE, ?, PART_TYPE, CLASS> storage,
                      PART_TYPE partType)
         throws ModelArgumentException, UserException
  {
    this(UtilObject.checkNotNull("storage", storage).getRealPath(), storage.getRealName(),
         storage.getType(), partType, storage.getPartTypeMessageRef());
  }
  /**
   * Constructeur à partir de la référence d'une utilisation de ressource
   * @param usage Référence de l'utilisation de ressource
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public ResourcePart(ResourceUsageMultiPart<?, TYPE, ?, PART_TYPE, CLASS> usage,
                      PART_TYPE partType)
         throws ModelArgumentException, UserException
  {
    this(UtilObject.checkNotNull("usage", usage).getRealPath(), usage.getRealName(),
         usage.getType(), partType, usage.getPartTypeMessageRef());
  }
  /**
   * Constructeur à partir d'une portion de ressource
   * @param part Portion de ressource à partir de laquelle construire la nouvelle
   * @param partType Type de la portion de ressource
   * @throws ModelArgumentException Si la portion de ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public ResourcePart(CLASS part, PART_TYPE partType)
         throws ModelArgumentException, UserException
  {
    this(UtilObject.checkNotNull("part", part).getPath(), part.getName(),
         part.getType(), partType, part.getPartType().getMessageRef());
  }

  /**
   * Défini le nom réel de la portion de ressource en lui ajoutant son type
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#getRealName()
   */
  @Override
  public String getRealName()
  {
    return super.getRealName() + "_" + this.getPartType().getCode();
  }
  /**
   * Redéfini la méthode pour positionner les portions de ressource différemment
   * des autres si leur type le défini
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#getRealPath()
   */
  @Override
  public String getRealPath() throws UserException
  {
    return UtilFile.concatRelativePath(
        this.getMessageRef(), this.getType().getPathPrefix(), super.getRealPath());
  }

  /**
   * Cette méthode permet de définir le type de la portion de ressource
   * @param partType Définition du type de la portion de ressource
   * @param partTypeBase Base de message des types de la portion de ressource
   * @throws ProtectionException Si la portion de ressource courante est protégée
   * @throws UserException Si on défini un type de portion de ressource nul
   */
  private void definePartType(PART_TYPE partType, MessageRef partTypeBase) throws ProtectionException, UserException
  {
    // Vérifie la protection de la ressource courante
    this.checkProtection();
    this.setPartType(UtilObject.checkNotNull("partType", partType,
                                             partTypeBase.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR)));
  }

  /**
   * Getter du type de la portion de ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPartType()
   */
  @Override
  public PART_TYPE getPartType()
  {
    return this.partType;
  }
  /**
   * Setter du type de la portion de ressource
   * @param partType Type de la portion de ressource à positionner
   */
  private void setPartType(PART_TYPE partType)
  {
    this.partType = partType;
  }
}
