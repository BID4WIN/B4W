package com.bid4win.commons.persistence.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinCollectionAbstract;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
import com.bid4win.commons.persistence.entity.collection.Bid4WinEntityReferenceSet;
import com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityComparator;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityListComparator;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityMapComparator;
import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntitySetComparator;
import com.bid4win.commons.persistence.entity.renderer.Bid4WinEntityCollectionRenderer;
import com.bid4win.commons.persistence.entity.renderer.Bid4WinEntityMapRenderer;
import com.bid4win.commons.persistence.entity.renderer.Bid4WinEntityRenderer;

/**
 * Cette classe est la classe de base de toute entité du projet. Elle défini les
 * méthodes et comportement nécessaires ou utiles. Toute entité aura une version
 * et un identifiant dont il faudra définir le getter afin de préciser sa définition
 * en base de données<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <ID> Définition du type de l'identifiant de l'entité<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEntity<CLASS extends Bid4WinEntity<CLASS, ID>, ID>
       extends Bid4WinObject<CLASS>
{
  /** Identifiant de l'entité */
  @Transient private ID id = null;
  /** Version de l'entité */
  @Transient private int version = -1;
  /** Date de création de l'entité */
  @Transient private Bid4WinDate createDate = null;
  /** Date de modification de l'entité */
  @Transient private Bid4WinDate updateDate = null;
  /** champ permettant le forçage de la modification de l'entité */
  @Transient private int updateForce = 0;

  /**
   * Constructeur par défaut
   */
  protected Bid4WinEntity()
  {
    super();
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de l'entité
   */
  protected Bid4WinEntity(ID id)
  {
    super();
    this.setId(id);
  }

  /**
   * Cette méthode permet de savoir si l'entité doit être considérée comme nouvelle
   * c'est à dire jamais persistée auparavant
   * @return True si l'entité n'a jamais été persistée, false sinon
   */
  public boolean isNewEntity()
  {
    return this.getVersion() == -1;
  }
  /**
   * Cette méthode permet d'être sûr de récupérer l'entité réelle et non pas un
   * potentiel proxy
   * @return L'entité réelle et non un potentiel proxy
   */
  @SuppressWarnings("unchecked")
  public CLASS self()
  {
    return (CLASS)this;
  }

  /**
   * La fonction est implémentée afin de créer le code de hachage à partir de la
   * valeur de l'identifiant de l'entité s'il existe
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected final int hashCodeInternal()
  {
    if(this.getId() == null)
    {
      return super.hashCodeDefault();
    }
    return this.getId().hashCode();
  }
  /**
   * Cette méthode est implémentée afin de définir l'égalité entre deux entités
   * par l'égalité de leurs identifiants
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  protected final boolean equalsInternal(CLASS toBeCompared)
  {
    return this.hashCode() == toBeCompared.hashCode();
  }

  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * identique à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi identiques. Elle se basera sur le retour
   * de la méthode same(Bid4WinEntity, Bid4WinList<Bid4WinRelationNode>) en utilisant
   * la liste fournie par la méthode getFullRelationNodeList() qui est donc à
   * surcharger afin d'obtenir une comparaison par défaut plus complète
   * @param toBeCompared Entité à comparer à l'entité courante
   * @return True si l'entité en argument est identique à l'entité courante, false
   * sinon
   */
  public final boolean identical(Bid4WinEntity<?, ?> toBeCompared)
  {
    return this.identical(toBeCompared, this.getFullRelationNodeList());
  }
  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * identique à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi identiques, de même que les relations
   * définies en paramètre en même temps que leur profondeur. Elle se basera sur
   * le retour de la méthode sameInternal() (tout cela au travers du comparateur
   * d'entités par défaut Bid4WinEntityComparator)
   * @param toBeCompared Entité à comparer à l'entité courante
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @return True si l'entité en argument est identique à l'entité courante, false
   * sinon
   */
  public final boolean identical(Bid4WinEntity<?, ?> toBeCompared,
                                 Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.identical(toBeCompared, nodeList, new Bid4WinMatchReferenceMap());
  }
  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * identique à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi identiques, de même que les relations
   * définies en paramètre en même temps que leur profondeur. Elle se basera sur
   * le retour de la méthode sameInternal() (tout cela au travers du comparateur
   * d'entités par défaut Bid4WinEntityComparator)
   * @param toBeCompared Entité à comparer à l'entité courante
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @return True si l'entité en argument est identique à l'entité courante, false
   * sinon
   */
  public final boolean identical(Bid4WinEntity<?, ?> toBeCompared,
                                 Bid4WinList<Bid4WinRelationNode> nodeList,
                                 Bid4WinMatchReferenceMap referenced)
  {
    return Bid4WinEntityComparator.getInstanceEntity().same(
        this, toBeCompared, nodeList, referenced, true);
  }

  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * équivalente à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions mis à part, sont eux aussi équivalents. Elle se basera sur le
   * retour de la méthode same(Bid4WinEntity, Bid4WinList<Bid4WinRelationNode>)
   * en utilisant la liste fournie par la méthode getFullRelationNodeList() qui
   * est donc à surcharger afin d'obtenir une comparaison par défaut plus complète
   * @param toBeCompared Entité à comparer à l'entité courante
   * @return True si l'entité en argument est équivalente à l'entité courante, false
   * sinon
   */
  public final boolean same(Bid4WinEntity<?, ?> toBeCompared)
  {
    return this.same(toBeCompared, this.getFullRelationNodeList());
  }
  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * équivalente à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions mis à part, sont eux aussi équivalents, de même que les relations
   * définies en paramètre en même temps que leur profondeur. Elle se basera sur
   * le retour de la méthode sameInternal() (tout cela au travers du comparateur
   * d'entités par défaut Bid4WinEntityComparator)
   * @param toBeCompared Entité à comparer à l'entité courante
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @return True si l'entité en argument est équivalente à l'entité courante, false
   * sinon
   */
  public final boolean same(Bid4WinEntity<?, ?> toBeCompared,
                            Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.same(toBeCompared, nodeList, new Bid4WinMatchReferenceMap());
  }
  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * équivalente à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions mis à part, sont eux aussi équivalents, de même que les relations
   * définies en paramètre en même temps que leur profondeur. Elle se basera sur
   * le retour de la méthode sameInternal() (tout cela au travers du comparateur
   * d'entités par défaut Bid4WinEntityComparator)
   * @param toBeCompared Entité à comparer à l'entité courante
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @return True si l'entité en argument est équivalente à l'entité courante, false
   * sinon
   */
  public final boolean same(Bid4WinEntity<?, ?> toBeCompared,
                            Bid4WinList<Bid4WinRelationNode> nodeList,
                            Bid4WinMatchReferenceMap referenced)
  {
    return Bid4WinEntityComparator.getInstanceEntity().same(
        this, toBeCompared, nodeList, referenced, false);
  }
  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * équivalente à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions compris selon les cas d'utilisation, sont eux aussi équivalents,
   * de même que les relations définies en paramètre en même temps que leur profondeur,
   * sans avoir à vérifier autre chose (nullité, classe) car ces tests doivent être
   * effectués par les méthodes appelantes. Le flag identical indique le context
   * d'utilisation de la méthode
   * @param toBeCompared Entité avec laquelle on doit tester l'équivalence
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si l'entité en paramètre est considérée équivalente à l'entité
   * courante (liens éventuels avec d'autres entités pris en compte), false sinon
   */
  public final boolean sameInternal(CLASS toBeCompared,
                                    Bid4WinList<Bid4WinRelationNode> nodeList,
                                    Bid4WinMatchReferenceMap referenced,
                                    boolean identical)
  {
    // Les entités ont déjà été comparées, on ne fera que retourner le résultat
    // précédent
    if(referenced.isReferenced(this, toBeCompared))
    {
      return referenced.match(this, toBeCompared);
    }
    // Référence la comparaison courante comme un succès
    referenced.match(this, toBeCompared, true);
    // Effectue la comparaison sans prise en compte des relations puis la comparaison
    // des relations choisies
    if(!this.sameRelationNoneInternal(toBeCompared, identical) ||
       !this.sameRelationInternal(toBeCompared, nodeList, referenced, identical))
    {
      // Référence la comparaison courante comme un échec
      referenced.match(this, toBeCompared, false);
      return false;
    }
    return true;
  }

  /**
   * Cette méthode permet de tester si l'entité en argument peut être considérée
   * équivalente à l'entité courante, c'est à dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi équivalents, sans avoir à vérifier autre
   * chose (nullité, classe, relations à d'autres entités) car ces tests doivent
   * être effectués par les méthodes appelantes. Le flag identical indique le context
   * d'utilisation de la méthode.
   * Cette méthode est donc à surcharger afin de compléter les tests à effectuer
   * @param toBeCompared Entité avec laquelle on doit tester l'équivalence
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si l'entité en paramètre est considérée équivalente à l'entité
   * courante (liens éventuels avec d'autres entités non pris en compte), false
   * sinon
   */
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    // Si l'identicité est demandé, on teste les identifiants et versions
    if(identical)
    {
      return Bid4WinComparator.getInstance().equals(this.getId(), toBeCompared.getId()) &&
             this.getVersion() == toBeCompared.getVersion();
    }
    return true;
  }
  /**
   * Cette méthode permet de tester si les relations (définies en paramètre avec
   * leur profondeur) de l'entité en argument peuvent être considérées équivalentes
   * à celles de l'entité courante, sans avoir à vérifier autre chose (nullité,
   * classe) car ces tests doivent être effectués par les méthodes appelantes. Le
   * flag identical indique le context d'utilisation de la méthode.
   * @param toBeCompared Entité avec les relations de laquelle on doit tester l'
   * équivalence des relations de l'entité courante
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si les relations de l'entité en paramètre sont considérées
   * équivalentes à celles de l'entité courante, false sinon
   */
  private boolean sameRelationInternal(CLASS toBeCompared,
                                       Bid4WinList<Bid4WinRelationNode> nodeList,
                                       Bid4WinMatchReferenceMap referenced,
                                       boolean identical)
  {
    // TODO verifier pour le null ...
    if(nodeList == null)
    {
      nodeList = this.getFullRelationNodeList();
    }
    // Compare toutes les relations définies en argument
    for(Bid4WinRelationNode node : nodeList)
    {
      // Compare la relation courante
      if(!this.sameRelationInternal(toBeCompared, node, referenced, identical))
      {
        return false;
      }
    }
    return true;
  }
  /**
   * Cette méthode permet de tester si la relation (définie en paramètre avec sa
   * profondeur) de l'entité en argument peut être considérée équivalente à celle
   * de l'entité courante, sans avoir à vérifier autre chose (nullité, classe) car
   * ces tests doivent être effectués par les méthodes appelantes. Le flag identical
   * indique le context d'utilisation de la méthode.
   * @param toBeCompared Entité avec la relation de laquelle on doit tester l'
   * équivalence de la relation de l'entité courante
   * @param node Noeud de la relation participant à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si la relation de l'entité en paramètre est considérée équivalente
   * à celle de l'entité courante, false sinon
   */
  private boolean sameRelationInternal(CLASS toBeCompared,
                                       Bid4WinRelationNode node,
                                       Bid4WinMatchReferenceMap referenced,
                                       boolean identical)
  {
    Bid4WinRelation relation = node.getRelation();
    // Compare la relation
    if(relation.getType().belongsTo(Type.SIMPLE))
    {
      return this.sameRelationSimpleInternal(toBeCompared, node, referenced, identical);
    }
    else if(relation.getType().belongsTo(Type.SET))
    {
      return this.sameRelationSetInternal(toBeCompared, node, referenced, identical);
    }
    else if(relation.getType().belongsTo(Type.LIST))
    {
      return this.sameRelationListInternal(toBeCompared, node, referenced, identical);
    }
    else if(relation.getType().belongsTo(Type.MAP))
    {
      return this.sameRelationMapInternal(toBeCompared, node, referenced, identical);
    }
    else
    {
      return false;
    }
  }
  /**
   * Cette méthode permet de tester si la relation de type simple (définie en
   * paramètre avec sa profondeur) de l'entité en argument peut être considérée
   * équivalente à celle de l'entité courante, sans avoir à vérifier autre chose
   * (nullité, classe) car ces tests doivent être effectués par les méthodes appelantes.
   * Le flag identical indique le context d'utilisation de la méthode.
   * @param toBeCompared Entité avec la relation de laquelle on doit tester l'
   * équivalence de la relation de l'entité courante
   * @param node Noeud de la relation participant à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si la relation de type simple de l'entité en paramètre est considérée
   * équivalente à celle de l'entité courante, false sinon
   */
  private boolean sameRelationSimpleInternal(CLASS toBeCompared,
                                             Bid4WinRelationNode node,
                                             Bid4WinMatchReferenceMap referenced,
                                             boolean identical)
  {
    // Récupère les deux relations de type simple et les compare
    Bid4WinEntity<?, ?> entity1 = this.getRelationSimple(node.getRelation());
    Bid4WinEntity<?, ?> entity2 = toBeCompared.getRelationSimple(node.getRelation());
    return Bid4WinEntityComparator.getInstanceEntity().same(
        entity1, entity2, node.getNodeList(), referenced, identical);
  }
  /**
   * Cette méthode permet de tester si la relation de type set (définie en paramètre
   * avec sa profondeur) de l'entité en argument peut être considérée équivalente
   * à celle de l'entité courante, sans avoir à vérifier autre chose (nullité, classe)
   * car ces tests doivent être effectués par les méthodes appelantes. Le flag
   * identical indique le context d'utilisation de la méthode.
   * @param toBeCompared Entité avec la relation de laquelle on doit tester l'
   * équivalence de la relation de l'entité courante
   * @param node Noeud de la relation participant à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si la relation de type set de l'entité en paramètre est considérée
   * équivalente à celle de l'entité courante, false sinon
   */
  private boolean sameRelationSetInternal(CLASS toBeCompared,
                                          Bid4WinRelationNode node,
                                          Bid4WinMatchReferenceMap referenced,
                                          boolean identical)
  {
    // Récupère les deux relations de type set et les compare
    Set<? extends Bid4WinEntity<?, ?>> set1 = this.getRelationSet(node.getRelation());
    Set<? extends Bid4WinEntity<?, ?>> set2 = toBeCompared.getRelationSet(node.getRelation());
    return Bid4WinEntitySetComparator.getInstanceEntitySet().same(
        set1, set2, node.getNodeList(), referenced, identical);
  }
  /**
   * Cette méthode permet de tester si la relation de type liste (définie en paramètre
   * avec sa profondeur) de l'entité en argument peut être considérée équivalente
   * à celle de l'entité courante, sans avoir à vérifier autre chose (nullité, classe)
   * car ces tests doivent être effectués par les méthodes appelantes. Le flag
   * identical indique le context d'utilisation de la méthode.
   * @param toBeCompared Entité avec la relation de laquelle on doit tester l'
   * équivalence de la relation de l'entité courante
   * @param node Noeud de la relation participant à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si la relation de type liste de l'entité en paramètre est considérée
   * équivalente à celle de l'entité courante, false sinon
   */
  private boolean sameRelationListInternal(CLASS toBeCompared,
                                           Bid4WinRelationNode node,
                                           Bid4WinMatchReferenceMap referenced,
                                           boolean identical)
  {
    // Récupère les deux relations de type liste et les compare
    List<? extends Bid4WinEntity<?, ?>> list1 = this.getRelationList(node.getRelation());
    List<? extends Bid4WinEntity<?, ?>> list2 = toBeCompared.getRelationList(node.getRelation());
    return Bid4WinEntityListComparator.getInstanceEntityList().same(
        list1, list2, node.getNodeList(), referenced, identical);
  }
  /**
   * Cette méthode permet de tester si la relation de type map (définie en paramètre
   * avec sa profondeur) de l'entité en argument peut être considérée équivalente
   * à celle de l'entité courante, sans avoir à vérifier autre chose (nullité, classe)
   * car ces tests doivent être effectués par les méthodes appelantes. Le flag
   * identical indique le context d'utilisation de la méthode.
   * @param toBeCompared Entité avec la relation de laquelle on doit tester l'
   * équivalence de la relation de l'entité courante
   * @param node Noeud de la relation participant à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
   * à dire identicité ou équivalence
   * @return True si la relation de map liste de l'entité en paramètre est considérée
   * équivalente à celle de l'entité courante, false sinon
   */
  private boolean sameRelationMapInternal(CLASS toBeCompared,
                                          Bid4WinRelationNode node,
                                          Bid4WinMatchReferenceMap referenced,
                                          boolean identical)
  {
    // Récupère les deux relations de type map et les compare
    Map<?, ? extends Bid4WinEntity<?, ?>> map1 = this.getRelationMap(node.getRelation());
    Map<?, ? extends Bid4WinEntity<?, ?>> map2 = toBeCompared.getRelationMap(node.getRelation());
    return Bid4WinEntityMapComparator.getInstanceEntityMap().same(
        map1, map2, node.getNodeList(), referenced, identical);
  }

  /**
   * Cette méthode permet de définir un classement par défaut de deux entités en
   * fonction de leurs identifiants respectifs. Dans le cas d'identifiants identiques,
   * les version seront utilisées pour définir un classement de l'entité la plus
   * ancienne à la plus récente
   * @param toBeCompared Entité à comparer à l'entité courante
   * @return Une valeur négative, positive ou zéro selon que l'entité courante
   * est considérée plus petite que, plus grande que ou égale à l'entité en argument
   */
  protected int compareTo(CLASS toBeCompared)
  {
    if(toBeCompared == null)
    {
      return 1;
    }
    int comparison = Bid4WinComparator.getInstance().compare(this.getId(),
                                                             toBeCompared.getId());
    if(comparison == 0)
    {
      comparison = this.getVersion() - toBeCompared.getVersion();
    }
    return comparison;
  }

  /**
   * Cette méthode permet d'effectuer le rendu de l'objet en chaîne de caractères.
   * Elle se basera sur le retour de la méthode render((Bid4WinList<Bid4WinRelationNode>)
   * en utilisant la liste fournie par la méthode getFullRelationNodeList() qui
   * est donc à surcharger afin d'obtenir un rendu par défaut plus complet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public final StringBuffer render()
  {
    return this.render(this.getFullRelationNodeList());
  }
  /**
   * Cette méthode permet d'effectuer le rendu de l'objet en chaîne de caractères
   * et de choisir les relations qui y participeront ainsi que leur profondeur
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de l'objet en chaîne de caractères
   */
  public final StringBuffer render(Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.render(nodeList, null);
  }
  /**
   * Cette méthode permet d'effectuer le rendu de l'objet en chaîne de caractères
   * et de choisir les relations qui y participeront ainsi que leur profondeur
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de l'objet en chaîne de caractères
   */
  public final StringBuffer render(Bid4WinList<Bid4WinRelationNode> nodeList,
                                   Bid4WinEntityReferenceSet referenced)
  {
    if(referenced == null)
    {
      referenced = new Bid4WinEntityReferenceSet();
    }
    // L'entité a déjà participé au rendu, on ne fera que le rendu de son identification
    else if(referenced.isReferenced(this))
    {
      return this.renderId();
    }
    // Référence l'entité courante
    referenced.referenceEntity(this);
    // Effectue le rendu de l'objet sans relation
    StringBuffer buffer = this.renderRelationNone();
    // Ajoute le rendu des relations et retourne le résultat
    return UtilString.addParagraph(buffer, this.renderRelation(nodeList, referenced));
  }

  /**
   * Cette méthode permet d'effectuer le rendu simple de l'object courant sans
   * prise en compte de ses relations et est donc à surcharger afin de le compléter
   * @return Le rendu simple de l'objet en chaîne de caractères
   */
  protected StringBuffer renderRelationNone()
  {
    return this.renderId();
  }
  /**
   * Cette méthode permet d'effectuer le rendu de l'identification de l'object
   * courant
   * @return Le rendu de l'identification de l'objet en chaîne de caractères
   */
  private StringBuffer renderId()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("ID=").append(this.getId());
    buffer.append(" VERSION=").append(this.getVersion());
    buffer.append(" HASH=").append(this.hashCodeDefault());
    return buffer;
  }
  /**
   * Cette méthode permet d'effectuer le rendu des relations définies par la liste
   * de noeuds en argument en même temps que leur profondeur
   * @param nodeList Liste de noeuds des relations participants au rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu des relations définies par la liste de noeuds en argument
   * en même temps que leur profondeur
   */
  private StringBuffer renderRelation(Bid4WinList<Bid4WinRelationNode> nodeList,
                                      Bid4WinEntityReferenceSet referenced)
  {
    StringBuffer buffer = new StringBuffer("RELATIONS");
    // Ajoute le rendu des relations
    for(Bid4WinRelationNode node : nodeList)
    {
      // Effectue et ajoute le rendu de la relation courante
      UtilString.addParagraph(buffer, this.renderRelation(node, referenced));
    }
    // Retourne le rendu des relations
    return buffer;
  }
  /**
   * Cette méthode permet d'effectuer le rendu de la relation définie par le noeud
   * en argument
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de la relation définie par le noeud en argument
   */
  private StringBuffer renderRelation(Bid4WinRelationNode node,
                                      Bid4WinEntityReferenceSet referenced)
  {
    Bid4WinRelation relation = node.getRelation();
    // Effectue le rendu de la relation
    StringBuffer paragraph = new StringBuffer("NULL");
    if(relation.getType().belongsTo(Type.SIMPLE))
    {
      paragraph = this.renderRelationSimple(node, referenced);
    }
    else if(relation.getType().belongsTo(Type.COLLECTION))
    {
      paragraph = this.renderRelationCollection(node, referenced);
    }
    else if(relation.getType().belongsTo(Type.MAP))
    {
      paragraph = this.renderRelationMap(node, referenced);
    }
    // Retourne le rendu de la relation
    return UtilString.addLine(new StringBuffer(relation.getName() + "="), paragraph);
  }
  /**
   * Cette méthode permet d'effectuer le rendu de la relation de type objet simple
   * définie par le noeud en argument. Celle-ci peut être redéfinie afin d'obtenir
   * un rendu spécifique sur certaines relations
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de la relation de type objet simple définie par le noeud en
   * argument
   */
  private StringBuffer renderRelationSimple(Bid4WinRelationNode node,
                                            Bid4WinEntityReferenceSet referenced)
  {
    // Récupère l'entité associée
    Bid4WinEntity<?, ?> toRender = this.getRelationSimple(node.getRelation());
    // Gère la possible nullité de l'entité. Ce n'est pas nécessaire mais c'est
    // pour avoir juste NULL sans crochet
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de l'entité
    StringBuffer buffer = new StringBuffer("[");
    UtilString.addParagraph(buffer,
                            Bid4WinEntityRenderer.getInstanceEntity().render(toRender,
                                                                             node.getNodeList(),
                                                                             referenced),
                            1);
    return UtilString.addParagraph(buffer, "]");
  }
  /**
   * Cette méthode permet d'effectuer le rendu de la relation de type collection
   * définie par le noeud en argument. Celle-ci peut être redéfinie afin d'obtenir
   * un rendu spécifique sur certaines relations
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de la relation de type collection définie par le noeud en
   * argument
   */
  private StringBuffer renderRelationCollection(Bid4WinRelationNode node,
                                                Bid4WinEntityReferenceSet referenced)
  {
    // Récupère la collection associée
    Collection<? extends Bid4WinEntity<?, ?>> toRender = this.getRelationCollection(node.getRelation());
    // Effectue le rendu de la collection
    return Bid4WinEntityCollectionRenderer.getInstanceEntityCollection().render(
        toRender, node.getNodeList(), referenced);
  }
  /**
   * Cette méthode permet d'effectuer le rendu de la relation de type map définie
   * par le noeud en argument. Celle-ci peut être redéfinie afin d'obtenir un rendu
   * spécifique sur certaines relations
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de la relation de type map définie par le noeud en argument
   */
  private StringBuffer renderRelationMap(Bid4WinRelationNode node,
                                         Bid4WinEntityReferenceSet referenced)
  {
    // Récupère la map associée
    Map<?, ? extends Bid4WinEntity<?, ?>> toRender = this.getRelationMap(node.getRelation());
    // Effectue le rendu de la map
    return Bid4WinEntityMapRenderer.getInstanceEntityMap().render(
        toRender, node.getNodeList(), referenced);
  }

  /**
   * Cette méthode permet de s'assurer du chargement "complet" des relations de
   * l'objet. Elle se basera sur la méthode loadRelation((Bid4WinList<Bid4WinRelationNode>)
   * en utilisant la liste fournie par la méthode getFullRelationNodeList() qui
   * est donc à surcharger afin d'obtenir un chargement par défaut plus complet
   * @return L'entité courante dont les relations sont bien chargées
   */
  public final CLASS loadRelation()
  {
    return this.loadRelation(this.getFullRelationNodeList());
  }
  /**
   * Cette méthode permet de s'assurer du chargement des relations définies en
   * paramètre avec leur profondeur dans le cas de relations en lazy loading.
   * Attention, c'est méthode ne pourra pas charger les données manquantes en
   * dehors du context transactionnel d'où est issue l'entité courante
   * @param nodeList Liste de noeuds des relations à charger
   * @return L'entité courante dont les relations choisies sont bien chargées
   */
  public final CLASS loadRelation(Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.loadRelation(nodeList, new Bid4WinEntityReferenceSet());
  }
  /**
   * Cette méthode permet de s'assurer du chargement des relations définies en
   * paramètre avec leur profondeur dans le cas de relations en lazy loading.
   * Attention, c'est méthode ne pourra pas charger les données manquantes en
   * dehors du context transactionnel d'où est issue l'entité courante
   * @param nodeList Liste de noeuds des relations à charger
   * @param referenced Set d'entités référencées comme ayant déjà été chargées
   * @return L'entité courante dont les relations choisies sont bien chargées
   */
  @SuppressWarnings("unchecked")
  public final CLASS loadRelation(Bid4WinList<Bid4WinRelationNode> nodeList,
                                  Bid4WinEntityReferenceSet referenced)
  {
    // L'entité a déjà été chargée
    if(referenced.isReferenced(this))
    {
      return (CLASS)this;
    }
    // Dans le cas d'une relation simple chargée passivement, l'entité est en fait
    // encapsulée dans un proxy javassist qui ne sera complètement chargé que s'
    // il est accédé
    this.getId();
    // Référence l'entité comme chargée
    referenced.referenceEntity(this);
    // Charge les relations demandées
    for(Bid4WinRelationNode node : nodeList)
    {
      this.loadRelation(node, referenced);
    }
    return (CLASS)this;
  }
  /**
   * Cette méthode permet de s'assurer du chargement de la relation définie en
   * paramètre avec sa profondeur dans le cas d'une relation en lazy loading.
   * Attention, c'est méthode ne pourra pas charger les données manquantes en
   * dehors du context transactionnel d'où est issue l'entité courante
   * @param node Noeud de la relation à charger
   * @param referenced Set d'entités référencées comme ayant déjà été chargées
   */
  private void loadRelation(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    Bid4WinRelation relation = node.getRelation();
    // Charge la relation selon son type
    if(relation.getType().belongsTo(Type.SIMPLE))
    {
      this.loadRelationSimple(node, referenced);
    }
    else if(relation.getType().belongsTo(Type.COLLECTION))
    {
      this.loadRelationCollection(node, referenced);
    }
    else if(relation.getType().belongsTo(Type.MAP))
    {
      this.loadRelationMap(node, referenced);
    }
  }
  /**
   * Cette méthode permet de s'assurer du chargement de la relation de type simple
   * définie en paramètre avec sa profondeur dans le cas d'une relations en lazy
   * loading. Attention, c'est méthode ne pourra pas charger les données manquantes
   * en dehors du context transactionnel d'où est issue l'entité courante
   * @param node Noeud de la relation de type simple à charger
   * @param referenced Set d'entités référencées comme ayant déjà été chargées
   */
  private void loadRelationSimple(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    // Récupère la relation
    Bid4WinEntity<?, ?> entity = this.getRelationSimple(node.getRelation());
    // Propage le chargement si nécessaire
    Bid4WinEntityLoader.getInstance().loadRelation(entity, node.getNodeList(), referenced);
  }
  /**
   * Cette méthode permet de s'assurer du chargement de la relation de type collection
   * définie en paramètre avec sa profondeur dans le cas d'une relations en lazy
   * loading. Attention, c'est méthode ne pourra pas charger les données manquantes
   * en dehors du context transactionnel d'où est issue l'entité courante
   * @param node Noeud de la relation de type collection à charger
   * @param referenced Set d'entités référencées comme ayant déjà été chargées
   */
  private void loadRelationCollection(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    // Récupère la relation
    Collection<? extends Bid4WinEntity<?, ?>> collection = this.getRelationCollection(
        node.getRelation());
    // Charge la relation et propage le chargement si nécessaire
    Bid4WinEntityLoader.getInstance().loadRelation(collection, node.getNodeList(), referenced);
  }
  /**
   * Cette méthode permet de s'assurer du chargement de la relation de type map
   * définie en paramètre avec sa profondeur dans le cas d'une relations en lazy
   * loading. Attention, c'est méthode ne pourra pas charger les données manquantes
   * en dehors du context transactionnel d'où est issue l'entité courante
   * @param node Noeud de la relation de type map à charger
   * @param referenced Set d'entités référencées comme ayant déjà été chargées
   */
  private void loadRelationMap(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    // Récupère la relation
    Map<?, ? extends Bid4WinEntity<?, ?>> map = this.getRelationMap(node.getRelation());
    // Charge la relation et propage le chargement si nécessaire
    Bid4WinEntityLoader.getInstance().loadRelation(map, node.getNodeList(), referenced);
  }

  /**
   * Cette méthode doit remonter la liste "complète" des noeuds de relations de
   * l'objet. Cette liste est celle qui sera utilisée par les implémentations par
   * défaut de same(), identical(), render() et loadRelation() et est donc à surcharger
   * pour la compléter en n'oubliant les profondeurs de relation
   * @return La liste "complète" des noeuds de relations de l'objet
   */
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    return new Bid4WinList<Bid4WinRelationNode>();
  }

  // TODO les get et set relations sont accessibles au travers du même package
  /**
   * Cette fonction permet de récupérer l'entité correspondant à la relation en
   * argument. Elle doit être redéfinie pour toute nouvelle relation de type simple
   * à remonter
   * @param relation Définition de la relation à laquelle participe l'entité à
   * récupérer
   * @return L'entité correspondant à la relation en argument ou null le cas échéant
   * @throws Bid4WinRuntimeException Si la relation n'est pas gérée par l'entité
   */
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
            throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette méthode permet de positionner l'entité correspondant à la relation en
   * argument. Elle doit être redéfinie pour toute nouvelle relation de type simple
   * à positionner
   * @param relation Définition de la relation à laquelle participe l'entité à
   * positionner
   * @param entity Entité à positionner
   * @throws Bid4WinRuntimeException Si la relation n'est pas gérée par l'entité
   */
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
            throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString() +
                                     " for entity type " + entity.getClass().getSimpleName());
  }
  /**
   * Cette fonction permet de récupérer la collection correspondant à la relation
   * en argument
   * @param relation Définition de la relation à laquelle participe la collection
   * à récupérer
   * @return La collection correspondant à la relation en argument ou null le cas
   * échéant
   */
  private Bid4WinCollectionAbstract<? extends Bid4WinEntity<?, ?>, ?, ?> getRelationCollection(Bid4WinRelation relation)
  {
    if(relation.getType().belongsTo(Type.SET))
    {
      return this.getRelationSet(relation);
    }
    else if(relation.getType().belongsTo(Type.LIST))
    {
      return this.getRelationList(relation);
    }
    else
    {
      return null;
    }
  }
  /**
   * Cette fonction permet de récupérer le set correspondant à la relation en argument.
   * Elle doit être redéfinie pour toute nouvelle relation de type set à remonter
   * @param relation Définition de la relation à laquelle participe le set à récupérer
   * @return Le set correspondant à la relation en argument ou null le cas échéant
   * @throws Bid4WinRuntimeException Si la relation n'est pas gérée par l'entité
   */
  protected Bid4WinSet<? extends Bid4WinEntity<?, ?>> getRelationSet(Bid4WinRelation relation)
            throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette fonction permet de récupérer la liste correspondant à la relation en
   * argument. Elle doit être redéfinie pour toute nouvelle relation de type liste
   * à remonter
   * @param relation Définition de la relation à laquelle participe la liste à
   * récupérer
   * @return La liste correspondant à la relation en argument ou null le cas échéant
   * @throws Bid4WinRuntimeException Si la relation n'est pas gérée par l'entité
   */
  protected Bid4WinList<? extends Bid4WinEntity<?, ?>> getRelationList(Bid4WinRelation relation)
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette fonction permet de récupérer la map correspondant à la relation en argument.
   * Elle doit être redéfinie pour toute nouvelle relation de type map à remonter
   * @param relation Définition de la relation à laquelle participe la map à récupérer
   * @return La map correspondant à la relation en argument ou null le cas échéant
   * @throws Bid4WinRuntimeException Si la relation n'est pas gérée par l'entité
   */
  protected Bid4WinMap<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette méthode permet de récupérer la clé associée à l'entité en paramètre pour
   * son classement dans la map correspondant à la relation en argument
   * @param relation Définition de la relation à laquelle participe la map pour
   * laquelle récupérer la clé de classement de l'entité en argument
   * @param value Entité pour laquelle récupérer la clé de classement dans la map
   * correspondant à la relation en argument
   * @return La clé associée à l'entité en paramètre pour sont classement dans
   * la map correspondant à la relation en argument
   * @throws Bid4WinRuntimeException Si la relation n'est pas gérée par l'entité
   */
  protected Object getRelationMapKey(Bid4WinRelation relation, Bid4WinEntity<?, ?> value)
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString() +
                                      " for entity value " + value.getClass().getSimpleName());
  }

  /**
   *
   * TODO A COMMENTER
   * @param relation TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  @SuppressWarnings("unused")
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    return MessageRef.UNKNOWN;
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation TODO A COMMENTER
   * @param partialCodes TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public final MessageRef getMessageRef(Bid4WinRelation relation, String ... partialCodes)
  {
    return this.getMessageRefBase(relation).getMessageRef(partialCodes);
  }

  /**
   * Cette méthode permet de positionner l'entité en argument sur la relation de
   * type simple donnée de l'entité courante. Cette fonction ne doit pas être
   * appelée directement mais fait partie de la création du lien inverse d'une
   * relation bidirectionnelle
   * @param link Définition de la relation de type simple de l'entité courante sur
   * laquelle positionner celle en argument
   * @param backLink Relation de type simple définissant le lien inverse de l'entité
   * en argument vers l'entité courante
   * @param toBeAdded Entité à positionner sur la relation de type simple donnée
   * @throws ProtectionException Si l'entité courante est protégée
   * @throws UserException Si l'entité en argument est nulle ou ne référence pas
   * l'entité courante ou si la relation donnée référence déjà une entité
   */
  private void addRelationSimple(Bid4WinRelation link,
                                 Bid4WinRelation backLink,
                                 Bid4WinEntity<?, ?> toBeAdded)
          throws ProtectionException, UserException
  {
    // Vérifie la protection de l'entité courante
    this.checkProtection();
    // Récupération de la base de message à utiliser pour l'entité en argument
    MessageRef base = this.getMessageRefBase(link);
    // Vérifie que l'entité à ajouter n'est pas nulle
    UtilObject.checkNotNull("toBeAdded", toBeAdded,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Vérifie que la relation de l'entité courante n'est pas déjà établie
    UtilObject.checkNull("linked", this.getRelationSimple(link),
                         base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Vérifie que l'entité courante est référencée par l'entité en argument
    Bid4WinEntity<?, ?> entity = toBeAdded.getRelationSimple(backLink);
    UtilObject.checkNotNull("referenced", entity, this.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilBoolean.checkTrue("referenced", this == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    // Définie la relation entre l'entité courante et l'entité en argument
    this.setRelationSimple(link, toBeAdded);
  }
  /**
   * Cette méthode permet de retirer l'entité en argument de la relation de type
   * simple donnée de l'entité courante. Cette fonction ne doit pas être appelée
   * directement mais fait partie de la suppression du lien inverse d'une relation
   * bidirectionnelle
   * @param link Définition de la relation de type simple de l'entité courante de
   * laquelle retirer celle en argument
   * @param backLink Relation de type simple définissant le lien inverse de l'entité
   * en argument vers l'entité courante
   * @param toBeRemoved Entité à retirer de la relation de type simple donnée
   * @throws ProtectionException Si l'entité courante est protégée
   * @throws UserException Si l'entité en argument est nulle, référence une entité
   * ou n'est pas référencée par la relation donnée
   */
  private void removeRelationSimple(Bid4WinRelation link,
                                    Bid4WinRelation backLink,
                                    Bid4WinEntity<?, ?> toBeRemoved)
          throws ProtectionException, UserException
  {
    // Vérifie la protection de l'entité courante
    this.checkProtection();
    // Récupération de la base de message à utiliser pour l'entité en argument
    MessageRef base = this.getMessageRefBase(link);
    // Vérifie que l'entité à retirer n'est pas nulle
    UtilObject.checkNotNull("toBeRemoved", toBeRemoved,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Vérifie que l'entité courante référence bien l'entité en argument
    Bid4WinEntity<?, ?> entity = this.getRelationSimple(link);
    UtilObject.checkNotNull("contained", entity, this.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
    UtilBoolean.checkTrue("contained", toBeRemoved == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    // Vérifie que l'entité en argument ne référence plus d'entité
    UtilObject.checkNull("referenced", toBeRemoved.getRelationSimple(backLink),
                         this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Supprime la relation entre l'entité courante et l'entité en argument
    this.setRelationSimple(link, null);
  }
  /**
   * Cette méthode permet d'ajouter l'entité en argument à la collection de l'
   * entité courante définie par la relation donnée. Cette fonction ne doit pas
   * être appelée directement mais fait partie de la création du lien inverse d'
   * une relation bidirectionnelle
   * @param link Relation définissant la collection de l'entité courante à laquelle
   * ajouter celle en argument
   * @param backLink Relation de type simple définissant le lien inverse de l'entité
   * en argument vers l'entité courante
   * @param toBeAdded Entité à ajouter à la collection définie par la relation
   * donnée
   * @throws ProtectionException Si la collection à laquelle ajouter l'entité est
   * protégée
   * @throws UserException Si l'entité en argument est nulle, ne référence pas l'
   * entité courante ou est déjà référencée par la collection définie par la relation
   */
  @SuppressWarnings("unchecked")
  private void addRelationCollection(Bid4WinRelation link,
                                     Bid4WinRelation backLink,
                                     Bid4WinEntity<?, ?> toBeAdded)
          throws ProtectionException, UserException
  {
    // Vérifie que l'entité courante est référencée par l'entité en argument
    Bid4WinEntity<?, ?> entity = toBeAdded.getRelationSimple(backLink);
    UtilObject.checkNotNull("referenced", entity, this.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilBoolean.checkTrue("referenced", this == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    // Ajoute l'entité en argument à la relation de collection de l'entité courante
    this.add((Bid4WinCollectionAbstract<Bid4WinEntity<?,?>, ?, ?>)this.getRelationCollection(link),
             toBeAdded, this.getMessageRefBase(link));
  }
  /**
   * Cette méthode permet de retirer l'entité en argument de la collection de l'
   * entité courante définie par la relation donnée. Cette fonction ne doit pas
   * être appelée directement mais fait partie de la suppression du lien inverse
   * d'une relation bidirectionnelle
   * @param link Relation définissant la collection de l'entité courante de laquelle
   * retirer celle en argument
   * @param backLink Relation de type simple définissant le lien inverse de l'entité
   * en argument vers l'entité courante
   * @param toBeRemoved Entité à retirer de la collection définie par la relation
   * donnée
   * @throws ProtectionException Si la collection de laquelle retirer l'entité est
   * protégée
   * @throws UserException Si l'entité en argument est nulle, référence une entité
   * ou n'est pas référencée par la collection définie par la relation
   */
  @SuppressWarnings("unchecked")
  private void removeRelationCollection(Bid4WinRelation link,
                                        Bid4WinRelation backLink,
                                        Bid4WinEntity<?, ?> toBeRemoved)
          throws ProtectionException, UserException
  {
    // Vérifie que l'entité en argument ne référence plus d'entité
    if(toBeRemoved != null)
    {
      UtilObject.checkNull("referenced", toBeRemoved.getRelationSimple(backLink),
                           this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    }
    // Retire l'entité en argument de la relation de collection de l'entité courante
    this.remove((Bid4WinCollectionAbstract<Bid4WinEntity<?,?>, ?, ?>)this.getRelationCollection(link),
                toBeRemoved, this.getMessageRefBase(link));
  }
  /**
   * Cette méthode permet d'ajouter l'entité en argument à la map de l'entité courante
   * définie par la relation donnée. Cette fonction ne doit pas être appelée directement
   * mais fait partie de la création du lien invere d'une relation bidirectionnelle
   * @param link Relation définissant la map de l'entité courante à laquelle ajouter
   * celle en argument
   * @param backLink Relation de type simple définissant le lien inverse de l'entité
   * en argument vers l'entité courante
   * @param toBeAdded Entité à ajouter à la map définie par la relation donnée
   * @throws ProtectionException Si la map à laquelle ajouter l'entité est protégée
   * @throws UserException Si l'entité en argument est nulle ne référence pas l'
   * entité courante ou est déjà référencée par la map définie par la relation
   */
  @SuppressWarnings("unchecked")
  private void addRelationMap(Bid4WinRelation link,
                              Bid4WinRelation backLink,
                              Bid4WinEntity<?, ?> toBeAdded)
          throws ProtectionException, UserException
  {
    // Vérifie que l'entité courante est référencée par l'entité en argument
    Bid4WinEntity<?, ?> entity = toBeAdded.getRelationSimple(backLink);
    UtilObject.checkNotNull("referenced", entity, this.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilBoolean.checkTrue("referenced", this == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
    // Ajoute l'entité en argument à la relation de map de l'entité courante
    this.add((Bid4WinMap<Object, Bid4WinEntity<?, ?>>)this.getRelationMap(link),
             this.getRelationMapKey(link, toBeAdded), toBeAdded,
             this.getMessageRefBase(link));
  }
  /**
   * Cette méthode permet de retirer l'entité en argument de la map de l'entité
   * courante définie par la relation donnée. Cette fonction ne doit pas être
   * appelée directement mais fait partie de la suppression du lien inverse d'une
   * relation bidirectionnelle
   * @param link Relation définissant la map de l'entité courante de laquelle retirer
   * celle en argument
   * @param backLink Relation de type simple définissant le lien inverse de l'entité
   * en argument vers l'entité courante
   * @param toBeRemoved Entité à retirer de la map définie par la relation donnée
   * @throws ProtectionException Si la map de laquelle retirer l'entité est protégée
   * @throws UserException Si l'entité en argument est nulle, référence une entité
   * ou n'est pas référencée par la map définie par la relation
   */
  @SuppressWarnings("unchecked")
  private void removeRelationMap(Bid4WinRelation link,
                                 Bid4WinRelation backLink,
                                 Bid4WinEntity<?, ?> toBeRemoved)
          throws ProtectionException, UserException
  {
    // Vérifie que l'entité en argument ne référence plus d'entité
    if(toBeRemoved != null)
    {
      UtilObject.checkNull("referenced", toBeRemoved.getRelationSimple(backLink),
                           this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    }
    // Retire l'entité en argument de la relation de collection de l'entité courante
    this.remove((Bid4WinMap<Object, Bid4WinEntity<?, ?>>)this.getRelationMap(link),
                this.getRelationMapKey(link, toBeRemoved), toBeRemoved,
                this.getMessageRefBase(link));
  }

  /**
   * Cette méthode permet de créer le lien de l'entité courante vers celle en
   * argument pour la relation de type simple donnée
   * @param link Définition de la relation de type simple à créer
   * @param toBeLinked Entité à référencer par l'entité courante
   * @throws ProtectionException Si l'entité courante est protégée
   * @throws UserException Si l'entité en argument est nulle ou si l'entité courante
   * est déjà liée
   */
  protected final void linkTo(Bid4WinRelation link, Bid4WinEntity<?, ?> toBeLinked)
            throws ProtectionException, UserException
  {
    // Vérifie la protection de l'entité courante
    this.checkProtection();
    // Récupération de la base de message à utiliser pour l'entité en argument
    MessageRef base = this.getMessageRefBase(link);
    // Vérifie que l'entité en argument n'est pas nulle
    UtilObject.checkNotNull("toBeLinked", toBeLinked,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Vérifie que l'entité courante n'est pas déjà liée
    UtilObject.checkNull("linked", this.getRelationSimple(link),
                         base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Crée le lien vers l'entité en argument
    this.setRelationSimple(link, toBeLinked);
  }
  /**
   * Cette méthode permet de supprimer le lien de l'entité courante vers celle
   * définie par la relation de type simple en argument
   * @param link Définition de la relation de type simple à supprimer
   * @return L'entité anciennement référencée par l'entité courante
   * @throws ProtectionException Si l'entité courante est protégée
   * @throws UserException Si l'entité courante n'est pas liée
   */
  protected final Bid4WinEntity<?, ?> unlinkFrom(Bid4WinRelation link)
            throws ProtectionException, UserException
  {
    // Vérifie la protection de l'entité courante
    this.checkProtection();
    // Récupération de la base de message à utiliser pour l'entité en argument
    MessageRef base = this.getMessageRefBase(link);
    // Vérifie l'existance du lien avec une entité
    Bid4WinEntity<?, ?> entity = UtilObject.checkNotNull("linked",
                                                         this.getRelationSimple(link),
                                                         base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Retire le lien avec l'entité
    this.setRelationSimple(link, null);
    // Retourne l'ancienne entité liée
    return entity;
  }
  /**
   * Cette méthode permet de créer le lien bidirectionnel entre l'entité courante
   * et celle en argument avec les relations données
   * @param link Relation de type simple définissant le lien de l'entité courante
   * vers l'entité en argument
   * @param backLink Relation définissant le lien inverse de l'entité en argument
   * vers l'entité courante
   * @param toBeLinked Entité à lier à l'entité courante
   * @throws ProtectionException Si l'entité courante ou celle en argument est
   * protégée
   * @throws UserException Si l'entité en argument est nulle ou référence déjà l'
   * entité courante ou si cette dernière est déjà liée
   */
  protected final void linkTo(Bid4WinRelation link, Bid4WinRelation backLink,
                              Bid4WinEntity<?, ?> toBeLinked)
            throws ProtectionException, UserException
  {
    // Crée le lien de l'entité courante vers l'entité en argument
    this.linkTo(link, toBeLinked);
    // Crée le lien inverse de l'entité en argument vers l'entité courante
    try
    {
      if(backLink.getType().belongsTo(Type.COLLECTION))
      {
        toBeLinked.addRelationCollection(backLink, link, this);
      }
      else if(backLink.getType().belongsTo(Type.MAP))
      {
        toBeLinked.addRelationMap(backLink, link, this);
      }
      else
      {
        toBeLinked.addRelationSimple(backLink, link, this);
      }
    }
    // Reviens à l'état initial
    catch(ProtectionException ex)
    {
      this.unlinkFrom(link);
      throw ex;
    }
    // Reviens à l'état initial
    catch(UserException ex)
    {
      this.unlinkFrom(link);
      throw ex;
    }
  }
  /**
   * Cette méthode permet de supprimer le lien bidirectionnel de l'entité courante
   * défini par les relations en argument
   * @param link Relation de type simple définissant le lien de l'entité courante
   * vers l'entité liée
   * @param backLink Relation définissant le lien inverse de l'entité liée vers
   * l'entité courante
   * @return L'entité anciennement liée à l'entité courante
   * @throws ProtectionException Si l'entité courante ou celle liée est protégée
   * @throws UserException Si l'entité courante n'est pas liée ou pas référencée
   * par l'entité liée
   */
  protected final Bid4WinEntity<?, ?> unlinkFrom(Bid4WinRelation link,
                                                 Bid4WinRelation backLink)
            throws ProtectionException, UserException
  {
    // Retire le lien de l'entité courante vers l'entité en argument
    Bid4WinEntity<?, ?> entity = this.unlinkFrom(link);
    // Retire le lien inverse de l'entité en argument vers l'entité courante
    try
    {
      if(backLink.getType().belongsTo(Type.COLLECTION))
      {
        entity.removeRelationCollection(backLink, link, this);
      }
      else if(backLink.getType().belongsTo(Type.MAP))
      {
        entity.removeRelationMap(backLink, link, this);
      }
      else
      {
        entity.removeRelationSimple(backLink, link, this);
      }
      return entity;
    }
    // Reviens à l'état initial
    catch(ProtectionException ex)
    {
      this.linkTo(link, entity);
      throw ex;
    }
    // Reviens à l'état initial
    catch(UserException ex)
    {
      this.linkTo(link, entity);
      throw ex;
    }
  }

  /**
   * Cette méthode permet de forcer, si ce champ est implémenté, la modification
   * de l'entité courante impliquant l'incrémentation de sa version
   * @return L'entité modifiée seulement si ce champ est implémenté
   * @throws ProtectionException Si l'entité courante est protégée
   */
  @SuppressWarnings("unchecked")
  public CLASS forceUpdate() throws ProtectionException
  {
    // Vérifie la protection de l'entité
    this.checkProtection();
    this.setUpdateForce(this.getVersion() + 1);
    return (CLASS)this;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de l'entité
   * @return L'identifiant de l'entité
   */
  public ID getId()
  {
    return this.id;
  }
  /**
   * Setter de l'identifiant de l'entité
   * @param id Identifiant de l'entité à positionner
   */
  private void setId(ID id)
  {
    this.id = id;
  }

  /**
   * Getter du champ permettant le forçage de la modification de l'entité
   * @return Le champ permettant le forçage de la modification de l'entité
   */
  public int getUpdateForce()
  {
    return this.updateForce;
  }
  /**
   * Setter du champ permettant le forçage de la modification de l'entité
   * @param updateForce Champ permettant le forçage de la modification de l'entité
   */
  private void setUpdateForce(int updateForce)
  {
    this.updateForce = updateForce;
  }

  /**
   * Getter de la version de l'entité
   * @return La version de l'entité
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Version()
  @Column(name = "VERSION", length = 3, nullable = false, unique = false)
  public int getVersion()
  {
    return this.version;
  }
  /**
   * Setter de la version de l'entité
   * @param version Version de l'entité à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setVersion(int version)
  {
    this.version = version;
  }

  /**
   * Getter de la date de création de l'entité
   * @return La date de création de l'entité
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREATE_DATE", nullable = false, unique = false)
  public Bid4WinDate getCreateDate()
  {
    return this.createDate;
  }
  /**
   * Setter de la date de création de l'entité
   * @param createDate Date de création de l'entité à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setCreateDate(Bid4WinDate createDate)
  {
    this.createDate = createDate;
  }
  /**
   * Cette méthode est appelée à la création en base de l'entité et permet de
   * positionner le suivi de modification
   */
  @SuppressWarnings(value = "unused")
  // Annotation pour la persistence
  @PrePersist
  private void onCreate()
  {
    this.createDate = new Bid4WinDate();
    this.updateDate = this.createDate;
  }

  /**
   * Getter de la date de modification de l'entité
   * @return La date de modification de l'entité
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "UPDATE_DATE", nullable = false, unique = false)
  public Bid4WinDate getUpdateDate()
  {
    return this.updateDate;
  }
  /**
   * Setter de la date de modification de l'entité
   * @param updateDate Date de modification de l'entité à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setUpdateDate(Bid4WinDate updateDate)
  {
    this.updateDate = updateDate;
  }
  /**
   * Cette méthode est appelée à la modification en base de l'entité et permet de
   * mettre à jour le suivi de modification
   */
  @SuppressWarnings(value = "unused")
  // Annotation pour la persistence
  @PreUpdate
  private void onUpdate()
  {
    this.updateDate = new Bid4WinDate();
  }

  //*************************************************************************//
  //************ Définition de la classe de comparaison donnant *************//
  //************     accès aux méthodes internes des entités    *************//
  //*************************************************************************//
  /**
   * Cette classe donne accès aux méthodes internes de comparaison des entités<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class InternalEntityComparator extends InternalObjectComparator
  {
    /**
     * Cette méthode permet de tester l'équivalence entre les deux entités en paramètre,
     * c'est à dire si tous leurs champs, identifiants et versions compris selon
     * les cas d'utilisation, sont eux aussi équivalents, de même que les relations
     * définies en paramètre en même temps que leur profondeur, sans avoir à vérifier
     * autre chose (nullité, classe) car ces tests doivent être effectués par les
     * méthodes appelantes. Le flag identical indique le context d'utilisation de
     * la méthode. Elle se basera sur le retour de l'appel à la méthode sameInternal(CLASS ...)
     * de la première entité à comparer
     * @param <TYPE> Définition du type des objets à comparer
     * @param object1 Premier objet à comparer
     * @param object2 Deuxième objet à comparer
     * @param nodeList Liste de noeuds de relations participants à la comparaison
     * @param referenced Map de résultats de comparaisons entre entités déjà référencés
     * @param identical Flag indiquant le context d'utilisation de la méthode, c'est
     * à dire identicité ou équivalence
     * @return True si les deux objets en paramètre sont considérés équivalents
     * (liens éventuels avec d'autres entités pris en compte), false sinon
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <TYPE extends Bid4WinEntity> boolean sameInternal(
        TYPE object1, TYPE object2, Bid4WinList<Bid4WinRelationNode> nodeList,
        Bid4WinMatchReferenceMap referenced, boolean identical)
    {
      return object1.sameInternal(object2, nodeList, referenced, identical);
    }
  }
}
