package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 * Cette classe définie le stockage d'une ressource divisée en portion<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de stockage de ressource<BR>
 * @param <USAGE> Doit définir les utilisations de ressources associées à ce stockage<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class ResourceStorageMultiPart<CLASS extends ResourceStorageMultiPart<CLASS, TYPE, USAGE, PART_TYPE, PART>,
                                               TYPE extends ResourceType<TYPE>,
                                               USAGE extends ResourceUsageMultiPart<USAGE, TYPE, CLASS, PART_TYPE, PART>,
                                               PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                               PART extends ResourcePart<PART, TYPE, PART_TYPE>>
       extends ResourceStorage<CLASS, TYPE, USAGE>
       implements Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>
{
  /** Set des portions du stockage de ressource */
  @Transient private Bid4WinSet<PART_TYPE> partTypeSet = new Bid4WinSet<PART_TYPE>(this.getProtection());
  {
    this.getPartTypeSet().add(this.getPartTypeDefault());
  }

  /**
   * Constructeur pour création par introspection
   */
  protected ResourceStorageMultiPart()
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
  public ResourceStorageMultiPart(String path, String name, TYPE type) throws UserException
  {
    super(path, name, type);
  }

  /**
   * Cette méthode doit être implémentée de façon à récupérer le type de portion
   * défini par défaut. Attention, le type doit être le même que celui défini pour
   * le magasin !!!
   * @return Le type de portion défini par défaut
   */
  public abstract PART_TYPE getPartTypeDefault();
  /**
   *
   * TODO A COMMENTER
   * @param partialCodes TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public final MessageRef getPartTypeMessageRef(String ... partialCodes)
  {
    return this.getPartTypeDefault().getMessageRef(partialCodes);
  }

  /**
   * Cette méthode permet de savoir si le stockage de ressource courant référence
   * le type de portion en argument
   * @param partType Type de portion à rechercher
   * @return True si le stockage de ressource courant référence le type de portion
   * en argument, false sinon
   */
  public boolean hasPartType(PART_TYPE partType)
  {
    return this.getPartTypeSet().contains(partType);
  }
  /**
   * Cette méthode permet d'ajouter au stockage de ressource courant le type de
   * portion en argument
   * @param partType Type de portion à ajouter
   * @throws ProtectionException Si le stockage de ressource courant est protégé
   * @throws UserException Si la portion de ressource en argument est nulle ou
   * déjà référencée
   */
  public void addPartType(PART_TYPE partType) throws ProtectionException, UserException
  {
    this.add(this.getPartTypeSet(), partType, this.getPartTypeMessageRef());
    for(USAGE usage : this.getUsages())
    {
      usage.definePartTypeSet();
    }
  }
  /**
   * Cette méthode permet de retirer du stockage de ressource courant le type de
   * portion en argument
   * @param partType Type de portion à retirer
   * @throws ProtectionException Si le stockage de ressource courant est protégé
   * @throws UserException Si la portion de ressource en argument est nulle, pas
   * référencée ou correspond à la portion de ressource par défaut
   */
  public void removePartType(PART_TYPE partType) throws ProtectionException, UserException
  {
    UtilObject.checkDiffers("default", partType, this.getPartTypeDefault(),
                            this.getPartTypeMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    this.remove(this.getPartTypeSet(), partType, this.getPartTypeMessageRef());
    for(USAGE usage : this.getUsages())
    {
      usage.definePartTypeSet();
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinSet<PART_TYPE> getPartTypes()
  {
    return this.getPartTypeSet().clone(true);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du set des portions du stockage de ressource
   * @return Le set des portions du stockage de ressource
   */
  private Bid4WinSet<PART_TYPE> getPartTypeSet()
  {
    return this.partTypeSet;
  }
  /**
   * Setter du set des portions du stockage de ressource
   * @param partTypeSet Set des portions du stockage de ressource à positionner
   */
  @SuppressWarnings("unused")
  private void setPartTypeSet(Bid4WinSet<PART_TYPE> partTypeSet)
  {
    this.partTypeSet = partTypeSet;
  }
}
