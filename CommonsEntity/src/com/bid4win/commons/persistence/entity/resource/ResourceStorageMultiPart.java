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
 * Cette classe d�finie le stockage d'une ressource divis�e en portion<BR>
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
   * Constructeur pour cr�ation par introspection
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
   * Cette m�thode doit �tre impl�ment�e de fa�on � r�cup�rer le type de portion
   * d�fini par d�faut. Attention, le type doit �tre le m�me que celui d�fini pour
   * le magasin !!!
   * @return Le type de portion d�fini par d�faut
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
   * Cette m�thode permet de savoir si le stockage de ressource courant r�f�rence
   * le type de portion en argument
   * @param partType Type de portion � rechercher
   * @return True si le stockage de ressource courant r�f�rence le type de portion
   * en argument, false sinon
   */
  public boolean hasPartType(PART_TYPE partType)
  {
    return this.getPartTypeSet().contains(partType);
  }
  /**
   * Cette m�thode permet d'ajouter au stockage de ressource courant le type de
   * portion en argument
   * @param partType Type de portion � ajouter
   * @throws ProtectionException Si le stockage de ressource courant est prot�g�
   * @throws UserException Si la portion de ressource en argument est nulle ou
   * d�j� r�f�renc�e
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
   * Cette m�thode permet de retirer du stockage de ressource courant le type de
   * portion en argument
   * @param partType Type de portion � retirer
   * @throws ProtectionException Si le stockage de ressource courant est prot�g�
   * @throws UserException Si la portion de ressource en argument est nulle, pas
   * r�f�renc�e ou correspond � la portion de ressource par d�faut
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
   * @param partTypeSet Set des portions du stockage de ressource � positionner
   */
  @SuppressWarnings("unused")
  private void setPartTypeSet(Bid4WinSet<PART_TYPE> partTypeSet)
  {
    this.partTypeSet = partTypeSet;
  }
}
