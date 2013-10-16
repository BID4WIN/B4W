package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Cette classe défini l'utilisation d'une ressource<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types d'utilisation ressource<BR>
 * @param <STORAGE> Doit définir le stockage de la ressource associée à cette utilisation<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class ResourceUsage<CLASS extends ResourceUsage<CLASS, TYPE, STORAGE>,
                           TYPE extends ResourceType<TYPE>,
                           STORAGE extends ResourceStorage<STORAGE, TYPE, CLASS>>
       extends Resource<CLASS, TYPE>
{
  /** Stockage de la ressource associée à cette utilisation */
  @Transient private STORAGE storage = null;

  /**
   * Constructeur pour création par introspection
   */
  protected ResourceUsage()
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
  public ResourceUsage(String path, String name, STORAGE storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, UtilObject.checkNotNull("storage", storage).getType());
    this.linkToStorage(storage);
  }

  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(ResourceUsage_Relations.RELATION_STORAGE))
    {
      return ResourceRef.RESOURCE;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   * Cette méthode permet de classer deux utilisations de ressource en fonction
   * de l'identifiant de leur stockage respectif
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#compareTo(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public int compareTo(CLASS toBeCompared)
  {
    if(toBeCompared == null)
    {
      return 1;
    }
    int comparison = this.getStorage().getId().compareTo(toBeCompared.getId());
    if(comparison == 0)
    {
      comparison = super.compareTo(toBeCompared);
    }
    return comparison;
  }
  /**
   * Ajoute à la liste des noeuds de relations de l'entité le lien vers son stockage
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(ResourceUsage_Relations.NODE_STORAGE);
    return nodeList;
  }
  /**
   * Permet de récupérer le stockage de l'entité s'il correspondant à la relation
   * en argument. Elle doit être redéfinie pour toute nouvelle relation de type
   * simple à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSet(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(ResourceUsage_Relations.RELATION_STORAGE))
    {
      return this.getStorage();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le stockage de l'entité s'il correspondant à la relation
   * en argument. Elle doit être redéfinie pour toute nouvelle relation de type
   * simple à positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(ResourceUsage_Relations.RELATION_STORAGE))
    {
      this.setStorage((STORAGE)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Cette méthode permet de lier l'utilisation de ressource courante au stockage
   * en argument
   * @param storage Stockage à lier à l'utilisation de ressource courante
   * @throws ProtectionException Si l'utilisation de ressource courante ou le
   * stockage en argument est protégé
   * @throws UserException Si le stockage en argument est nul ou référence déjà
   * l'utilisation de ressource courante ou si celle-ci est déjà liée
   */
  private void linkToStorage(STORAGE storage) throws ProtectionException, UserException
  {
    this.linkTo(ResourceUsage_Relations.RELATION_STORAGE,
                ResourceStorage_Relations.RELATION_USAGE_SET,
                storage);
  }
  /**
   * Cette méthode permet de délier l'utilisation de ressource courante de son
   * stockage
   * @return Le stockage précédemment lié à l'utilisation de ressource courante
   * @throws ProtectionException Si l'utilisation de ressource courante ou son
   * stockage est protégé
   * @throws UserException Si l'utilisation de ressource courante n'est pas liée
   * à un stockage ou si celui-ci ne la référence pas
   */
  @SuppressWarnings("unchecked")
  public STORAGE unlinkFromStorage() throws ProtectionException, UserException
  {
    return (STORAGE)this.unlinkFrom(ResourceUsage_Relations.RELATION_STORAGE,
                                    ResourceStorage_Relations.RELATION_USAGE_SET);
  }

  /**
   * Permet de savoir si l'utilisation de ressource a un stockage
   * @return True si l'utilisation de ressource a un stockage, false sinon
   */
  public boolean hasStorage()
  {
    if(this.getStorage() == null)
    {
      return false;
    }
    return true;
  }

  /**
   * Redéfini la méthode afin de prévenir la définition d'un type autre que celui
   * du stockage de la ressource de l'utilisation courante
   * @param type {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#defineType(com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  public void defineType(TYPE type) throws ProtectionException, UserException
  {
    if(this.hasStorage())
    {
      type = this.getStorage().getType();
    }
    super.defineType(type);
  }

  /**
   * Cette méthode permet de spécifier le nom réel de stockage de la ressource car
   * dans le cas d'une utilisation de ressource, ce nom sera construit en ajoutant
   * l'identifiant et la valeur de forçage de modification au nom de la ressource
   * afin de suivre son évolution et de ne pas entrer en conflit avec une ancienne
   * utilisation de même nom. En effet, les utilisations de ressources sont utilisées
   * entre autre pour charger dynamiquement une application avec des ressources
   * existant hors de son périmètre. Celles-ci ne suivent donc pas obligatoirement
   * le cycle de vie de ces ressources (en partie dans le cas de ressources de type
   * fichier qui doivent être synchronisées explicitement)
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#getRealName()
   */
  @Override
  public String getRealName()
  {
    // L'identifiant est utilisé pour différencier deux utilisations de ressource
    // et la valeur de forçage pour différencier deux états de stockage d'une même
    // utilisation
    return super.getRealName() + "_" + this.getId() + "_" + this.getUpdateForce();
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/

  /**
   * Getter du stockage de la ressource
   * @return Le stockage de la ressource
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "STORAGE_ID", nullable = false, unique = false)
  public STORAGE getStorage()
  {
    return this.storage;
  }
  /**
   * Setter du stockage de la ressource
   * @param storage Stockage de la ressource à positionner
   */
  private void setStorage(STORAGE storage)
  {
    this.storage = storage;
  }
}
