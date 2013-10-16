package com.bid4win.commons.persistence.entity.resource;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Cette classe d�fini le stockage d'une ressource<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de stockage ressource<BR>
 * @param <USAGE> Doit d�finir les utilisations de ressources associ�es � ce stockage<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class ResourceStorage<CLASS extends ResourceStorage<CLASS, TYPE, USAGE>,
                             TYPE extends ResourceType<TYPE>,
                             USAGE extends ResourceUsage<USAGE, TYPE, CLASS>>
       extends Resource<CLASS, TYPE>
{
  /** Set des utilisations de ressources associ�es au stockage courant */
  @Transient private Bid4WinSet<USAGE> usageSet;
  {
    this.setUsageSetInternal(new Bid4WinSet<USAGE>());
  }

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected ResourceStorage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @throws UserException Si le nom ou l'emplacement de stockage de la ressource
   * est invalide
   */
  public ResourceStorage(String path, String name, TYPE type) throws UserException
  {
    super(path, name, type);
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
    if(relation.equals(ResourceStorage_Relations.RELATION_USAGE_SET))
    {
      return ResourceRef.RESOURCE;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   * Ajoute � la liste des noeuds de relations de l'entit� le lien vers son set
   * d'utilisations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(ResourceStorage_Relations.NODE_USAGE_SET);
    return nodeList;
  }
  /**
   * Permet de r�cup�rer le set d'utilisations de l'entit� s'il correspondant �
   * la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type set � remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSet(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinSet<? extends Bid4WinEntity<?, ?>> getRelationSet(Bid4WinRelation relation)
  {
    if(relation.equals(ResourceStorage_Relations.RELATION_USAGE_SET))
    {
      return this.getUsageSetInternal();
    }
    return super.getRelationSet(relation);
  }

  /**
   * Cette m�thode permet de r�cup�rer l'utilisation du stockage de ressource
   * courant dont l'identifiant est pass� en argument
   * @param usageId Identifiant de l'utilisation du stockage de ressource courant
   * � r�cup�rer
   * @return L'utilisation du stockage de ressource courant dont l'identifiant est
   * pass� en argument
   * @throws UserException Si le stockage de ressource courant n'a aucune utilisation
   * dont l'identifiant correspond � celui en argument
   */
  public USAGE getUsage(long usageId) throws UserException
  {
    for(USAGE usage : this.getUsageSetInternal())
    {
      if(usage.getId().equals(usageId))
      {
        return usage;
      }
    }
    throw new UserException(this.getMessageRef("usage", MessageRef.SUFFIX_UNKNOWN_ERROR));
  }
  /**
   * Getter des utilisations de ressources associ�es au stockage courant
   * @return Les utilisations de ressources associ�es au stockage courant
   */
  public final Bid4WinSet<USAGE> getUsages()
  {
    return this.getUsageSetInternal().clone(true);
  }
  /**
   * Getter du set interne des utilisations de ressources associ�es au stockage
   * courant
   * @return Le set interne des utilisations de ressources associ�es au stockage
   * courant
   */
  private Bid4WinSet<USAGE> getUsageSetInternal()
  {
    return this.usageSet;
  }
  /**
   * Setter du set interne des utilisations de ressources associ�es au stockage
   * courant
   * @param usageSet Set interne des utilisations de ressources associ�es au stockage
   * courant � positionner
   * @throws ProtectionException Si la protection du set en param�tre �choue
   */
  private void setUsageSetInternal(Bid4WinSet<USAGE> usageSet) throws ProtectionException
  {
    usageSet.protect(this.getProtection());
    this.usageSet = usageSet;
  }

  /**
   * Red�fini la m�thode afin d'ajouter la mise � jour des type de toutes les
   * utilisations du stockage de ressource
   * @param type {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#defineType(com.bid4win.commons.persistence.entity.resource.ResourceType)
   */
  @Override
  public void defineType(TYPE type) throws ProtectionException, UserException
  {
    super.defineType(type);
    // A l'instanciation de la super classe, le set d'utilisations n'est pas encore
    // initialis�
    if(this.getUsageSetInternal() != null)
    {
      for(USAGE usage : this.getUsageSetInternal())
      {
        usage.defineType(type);
      }
    }
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du set persistant des utilisations de ressources associ�es au stockage
   * courant
   * @return Le set persistant des utilisations de ressources associ�es au stockage
   * courant
   */
  @SuppressWarnings("unused")
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "storage", fetch = FetchType.LAZY, cascade = {})
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private Set<USAGE> getUsageSetDatabase()
  {
    return this.getUsageSetInternal().getInternal();
  }
  /**
   * Setter du set persistant des utilisations de ressources associ�es au stockage
   * courant
   * @param usageSet Set persistant des utilisations de ressources associ�es au
   * stockage courant � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setUsageSetDatabase(Set<USAGE> usageSet)
  {
    this.setUsageSetInternal(new Bid4WinSet<USAGE>(usageSet, true));
  }
}
