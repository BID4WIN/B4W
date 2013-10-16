package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 * Cette classe définie l'utilisation d'une ressource divisée en portion<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types d'utilisation ressource<BR>
 * @param <STORAGE> Doit définir le stockage de la ressource associée à cette utilisation<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class ResourceUsageMultiPart<CLASS extends ResourceUsageMultiPart<CLASS, TYPE, STORAGE, PART_TYPE, PART>,
                                             TYPE extends ResourceType<TYPE>,
                                             STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, CLASS, PART_TYPE, PART>,
                                             PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                             PART extends ResourcePart<PART, TYPE, PART_TYPE>>
       extends ResourceUsage<CLASS, TYPE, STORAGE>
       implements Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>
{
  /** Set des portions de l'utilisation de ressource */
  @Transient private Bid4WinSet<PART_TYPE> partTypeSet = new Bid4WinSet<PART_TYPE>(this.getProtection());

  /**
   * Constructeur pour création par introspection
   */
  protected ResourceUsageMultiPart()
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
  public ResourceUsageMultiPart(String path, String name, STORAGE storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
    this.definePartTypeSet();
  }

  /**
   * Cette méthode doit être implémentée de façon à récupérer le type de portion
   * défini par défaut. Attention, le type doit être le même que celui défini pour
   * le magasin et le stockage de ressources associé !!!
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
   * Cette méthode permet de savoir si l'utilisation de ressource courante référence
   * le type de portion en argument
   * @param partType Type de portion à rechercher
   * @return True si l'utilisation de ressource courante référence le type de portion
   * en argument, false sinon
   */
  public boolean hasPartType(PART_TYPE partType)
  {
    return this.getPartTypeSet().contains(partType);
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
  /**
   * Met à jour le set des portions de l'utilisation de ressource en récupérant
   * celui de son stockage
   */
  protected void definePartTypeSet()
  {
    this.setPartTypeSet(this.getStorage().getPartTypes());
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du set des portions de l'utilisation de ressource
   * @return Le set des portions de l'utilisation de ressource
   */
  private Bid4WinSet<PART_TYPE> getPartTypeSet()
  {
    return this.partTypeSet;
  }
  /**
   * Setter du set des portions de l'utilisation de ressource
   * @param partTypeSet Set des portions de l'utilisation de ressource à positionner
   */
  @SuppressWarnings("unused")
  private void setPartTypeSet(Bid4WinSet<PART_TYPE> partTypeSet)
  {
    this.partTypeSet = partTypeSet;
  }
}
