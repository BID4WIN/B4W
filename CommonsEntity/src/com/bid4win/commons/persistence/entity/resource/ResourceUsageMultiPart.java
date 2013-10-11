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
 * Cette classe d�finie l'utilisation d'une ressource divis�e en portion<BR>
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
public abstract class ResourceUsageMultiPart<CLASS extends ResourceUsageMultiPart<CLASS, TYPE, STORAGE, PART_TYPE, PART>,
                                             TYPE extends ResourceType<TYPE>,
                                             STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, CLASS, PART_TYPE, PART>,
                                             PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                             PART extends ResourcePart<PART, TYPE, PART_TYPE>>
       extends ResourceUsage<CLASS, TYPE, STORAGE>
       implements Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>
{
  /** Set des portions de l'utilisation de ressource */
  @Transient
  private Bid4WinSet<PART_TYPE> partTypeSet = new Bid4WinSet<PART_TYPE>();

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ResourceUsageMultiPart()
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
  public ResourceUsageMultiPart(String path, String name, STORAGE storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
    for(PART_TYPE partType : storage.getPartTypeSet())
    {
      this.addPartType(partType);
    }
  }

  /**
   * Cette m�thode doit �tre impl�ment�e de fa�on � r�cup�rer le type de portion
   * d�fini par d�faut. Attention, le type doit �tre le m�me que celui d�fini pour
   * le magasin et le stockage de ressources associ� !!!
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
   * Cette m�thode permet de savoir si l'utilisation de ressource courante r�f�rence
   * le type de portion en argument
   * @param partType Type de portion � rechercher
   * @return True si l'utilisation de ressource courante r�f�rence le type de portion
   * en argument, false sinon
   */
  public boolean hasPartType(PART_TYPE partType)
  {
    return this.getPartTypeSet().contains(partType);
  }
  /**
   * Cette m�thode permet d'ajouter � l'utilisation de ressource courante le type
   * de portion en argument
   * @param partType Type de portion � ajouter
   * @throws ProtectionException Si l'utilisation de ressource courante est prot�g�e
   * @throws UserException Si la portion de ressource en argument est nulle ou
   * d�j� r�f�renc�e
   */
  protected void addPartType(PART_TYPE partType) throws ProtectionException, UserException
  {
    this.add(this.getPartTypeSet(), partType, this.getPartTypeMessageRef());
  }
  /**
   * Cette m�thode permet de retirer de l'utilisation de ressource courante le type
   * de portion en argument
   * @param partType Type de portion � retirer
   * @throws ProtectionException Si l'utilisation de ressource courante est prot�g�e
   * @throws UserException Si la portion de ressource en argument est nulle ou
   * pas r�f�renc�e
   */
  protected void removePartType(PART_TYPE partType) throws ProtectionException, UserException
  {
    this.remove(this.getPartTypeSet(), partType, this.getPartTypeMessageRef());
  }

  /**
   * Getter du set des portions de l'utilisation de ressource
   * @return Le set des portions de l'utilisation de ressource
   */
  protected Bid4WinSet<PART_TYPE> getPartTypeSet()
  {
    return this.partTypeSet;
  }
  /**
   * Setter du set des portions de l'utilisation de ressource
   * @param partTypeSet Set des portions de l'utilisation de ressource � positionner
   */
  @SuppressWarnings("unused")
  private void setPartTypeSet(Bid4WinSet<PART_TYPE> partTypeSet)
  {
    this.partTypeSet = partTypeSet;
  }
}
