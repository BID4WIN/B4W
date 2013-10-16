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
 * Cette classe est la classe de base de toute entit� du projet. Elle d�fini les
 * m�thodes et comportement n�cessaires ou utiles. Toute entit� aura une version
 * et un identifiant dont il faudra d�finir le getter afin de pr�ciser sa d�finition
 * en base de donn�es<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <ID> D�finition du type de l'identifiant de l'entit�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEntity<CLASS extends Bid4WinEntity<CLASS, ID>, ID>
       extends Bid4WinObject<CLASS>
{
  /** Identifiant de l'entit� */
  @Transient private ID id = null;
  /** Version de l'entit� */
  @Transient private int version = -1;
  /** Date de cr�ation de l'entit� */
  @Transient private Bid4WinDate createDate = null;
  /** Date de modification de l'entit� */
  @Transient private Bid4WinDate updateDate = null;
  /** champ permettant le for�age de la modification de l'entit� */
  @Transient private int updateForce = 0;

  /**
   * Constructeur par d�faut
   */
  protected Bid4WinEntity()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   */
  protected Bid4WinEntity(ID id)
  {
    super();
    this.setId(id);
  }

  /**
   * Cette m�thode permet de savoir si l'entit� doit �tre consid�r�e comme nouvelle
   * c'est � dire jamais persist�e auparavant
   * @return True si l'entit� n'a jamais �t� persist�e, false sinon
   */
  public boolean isNewEntity()
  {
    return this.getVersion() == -1;
  }
  /**
   * Cette m�thode permet d'�tre s�r de r�cup�rer l'entit� r�elle et non pas un
   * potentiel proxy
   * @return L'entit� r�elle et non un potentiel proxy
   */
  @SuppressWarnings("unchecked")
  public CLASS self()
  {
    return (CLASS)this;
  }

  /**
   * La fonction est impl�ment�e afin de cr�er le code de hachage � partir de la
   * valeur de l'identifiant de l'entit� s'il existe
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
   * Cette m�thode est impl�ment�e afin de d�finir l'�galit� entre deux entit�s
   * par l'�galit� de leurs identifiants
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
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * identique � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi identiques. Elle se basera sur le retour
   * de la m�thode same(Bid4WinEntity, Bid4WinList<Bid4WinRelationNode>) en utilisant
   * la liste fournie par la m�thode getFullRelationNodeList() qui est donc �
   * surcharger afin d'obtenir une comparaison par d�faut plus compl�te
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @return True si l'entit� en argument est identique � l'entit� courante, false
   * sinon
   */
  public final boolean identical(Bid4WinEntity<?, ?> toBeCompared)
  {
    return this.identical(toBeCompared, this.getFullRelationNodeList());
  }
  /**
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * identique � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi identiques, de m�me que les relations
   * d�finies en param�tre en m�me temps que leur profondeur. Elle se basera sur
   * le retour de la m�thode sameInternal() (tout cela au travers du comparateur
   * d'entit�s par d�faut Bid4WinEntityComparator)
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @return True si l'entit� en argument est identique � l'entit� courante, false
   * sinon
   */
  public final boolean identical(Bid4WinEntity<?, ?> toBeCompared,
                                 Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.identical(toBeCompared, nodeList, new Bid4WinMatchReferenceMap());
  }
  /**
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * identique � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi identiques, de m�me que les relations
   * d�finies en param�tre en m�me temps que leur profondeur. Elle se basera sur
   * le retour de la m�thode sameInternal() (tout cela au travers du comparateur
   * d'entit�s par d�faut Bid4WinEntityComparator)
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @return True si l'entit� en argument est identique � l'entit� courante, false
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
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * �quivalente � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions mis � part, sont eux aussi �quivalents. Elle se basera sur le
   * retour de la m�thode same(Bid4WinEntity, Bid4WinList<Bid4WinRelationNode>)
   * en utilisant la liste fournie par la m�thode getFullRelationNodeList() qui
   * est donc � surcharger afin d'obtenir une comparaison par d�faut plus compl�te
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @return True si l'entit� en argument est �quivalente � l'entit� courante, false
   * sinon
   */
  public final boolean same(Bid4WinEntity<?, ?> toBeCompared)
  {
    return this.same(toBeCompared, this.getFullRelationNodeList());
  }
  /**
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * �quivalente � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions mis � part, sont eux aussi �quivalents, de m�me que les relations
   * d�finies en param�tre en m�me temps que leur profondeur. Elle se basera sur
   * le retour de la m�thode sameInternal() (tout cela au travers du comparateur
   * d'entit�s par d�faut Bid4WinEntityComparator)
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @return True si l'entit� en argument est �quivalente � l'entit� courante, false
   * sinon
   */
  public final boolean same(Bid4WinEntity<?, ?> toBeCompared,
                            Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.same(toBeCompared, nodeList, new Bid4WinMatchReferenceMap());
  }
  /**
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * �quivalente � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions mis � part, sont eux aussi �quivalents, de m�me que les relations
   * d�finies en param�tre en m�me temps que leur profondeur. Elle se basera sur
   * le retour de la m�thode sameInternal() (tout cela au travers du comparateur
   * d'entit�s par d�faut Bid4WinEntityComparator)
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @return True si l'entit� en argument est �quivalente � l'entit� courante, false
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
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * �quivalente � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions compris selon les cas d'utilisation, sont eux aussi �quivalents,
   * de m�me que les relations d�finies en param�tre en m�me temps que leur profondeur,
   * sans avoir � v�rifier autre chose (nullit�, classe) car ces tests doivent �tre
   * effectu�s par les m�thodes appelantes. Le flag identical indique le context
   * d'utilisation de la m�thode
   * @param toBeCompared Entit� avec laquelle on doit tester l'�quivalence
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si l'entit� en param�tre est consid�r�e �quivalente � l'entit�
   * courante (liens �ventuels avec d'autres entit�s pris en compte), false sinon
   */
  public final boolean sameInternal(CLASS toBeCompared,
                                    Bid4WinList<Bid4WinRelationNode> nodeList,
                                    Bid4WinMatchReferenceMap referenced,
                                    boolean identical)
  {
    // Les entit�s ont d�j� �t� compar�es, on ne fera que retourner le r�sultat
    // pr�c�dent
    if(referenced.isReferenced(this, toBeCompared))
    {
      return referenced.match(this, toBeCompared);
    }
    // R�f�rence la comparaison courante comme un succ�s
    referenced.match(this, toBeCompared, true);
    // Effectue la comparaison sans prise en compte des relations puis la comparaison
    // des relations choisies
    if(!this.sameRelationNoneInternal(toBeCompared, identical) ||
       !this.sameRelationInternal(toBeCompared, nodeList, referenced, identical))
    {
      // R�f�rence la comparaison courante comme un �chec
      referenced.match(this, toBeCompared, false);
      return false;
    }
    return true;
  }

  /**
   * Cette m�thode permet de tester si l'entit� en argument peut �tre consid�r�e
   * �quivalente � l'entit� courante, c'est � dire que tous leurs champs, identifiants
   * et versions compris, sont eux aussi �quivalents, sans avoir � v�rifier autre
   * chose (nullit�, classe, relations � d'autres entit�s) car ces tests doivent
   * �tre effectu�s par les m�thodes appelantes. Le flag identical indique le context
   * d'utilisation de la m�thode.
   * Cette m�thode est donc � surcharger afin de compl�ter les tests � effectuer
   * @param toBeCompared Entit� avec laquelle on doit tester l'�quivalence
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si l'entit� en param�tre est consid�r�e �quivalente � l'entit�
   * courante (liens �ventuels avec d'autres entit�s non pris en compte), false
   * sinon
   */
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    // Si l'identicit� est demand�, on teste les identifiants et versions
    if(identical)
    {
      return Bid4WinComparator.getInstance().equals(this.getId(), toBeCompared.getId()) &&
             this.getVersion() == toBeCompared.getVersion();
    }
    return true;
  }
  /**
   * Cette m�thode permet de tester si les relations (d�finies en param�tre avec
   * leur profondeur) de l'entit� en argument peuvent �tre consid�r�es �quivalentes
   * � celles de l'entit� courante, sans avoir � v�rifier autre chose (nullit�,
   * classe) car ces tests doivent �tre effectu�s par les m�thodes appelantes. Le
   * flag identical indique le context d'utilisation de la m�thode.
   * @param toBeCompared Entit� avec les relations de laquelle on doit tester l'
   * �quivalence des relations de l'entit� courante
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si les relations de l'entit� en param�tre sont consid�r�es
   * �quivalentes � celles de l'entit� courante, false sinon
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
    // Compare toutes les relations d�finies en argument
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
   * Cette m�thode permet de tester si la relation (d�finie en param�tre avec sa
   * profondeur) de l'entit� en argument peut �tre consid�r�e �quivalente � celle
   * de l'entit� courante, sans avoir � v�rifier autre chose (nullit�, classe) car
   * ces tests doivent �tre effectu�s par les m�thodes appelantes. Le flag identical
   * indique le context d'utilisation de la m�thode.
   * @param toBeCompared Entit� avec la relation de laquelle on doit tester l'
   * �quivalence de la relation de l'entit� courante
   * @param node Noeud de la relation participant � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si la relation de l'entit� en param�tre est consid�r�e �quivalente
   * � celle de l'entit� courante, false sinon
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
   * Cette m�thode permet de tester si la relation de type simple (d�finie en
   * param�tre avec sa profondeur) de l'entit� en argument peut �tre consid�r�e
   * �quivalente � celle de l'entit� courante, sans avoir � v�rifier autre chose
   * (nullit�, classe) car ces tests doivent �tre effectu�s par les m�thodes appelantes.
   * Le flag identical indique le context d'utilisation de la m�thode.
   * @param toBeCompared Entit� avec la relation de laquelle on doit tester l'
   * �quivalence de la relation de l'entit� courante
   * @param node Noeud de la relation participant � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si la relation de type simple de l'entit� en param�tre est consid�r�e
   * �quivalente � celle de l'entit� courante, false sinon
   */
  private boolean sameRelationSimpleInternal(CLASS toBeCompared,
                                             Bid4WinRelationNode node,
                                             Bid4WinMatchReferenceMap referenced,
                                             boolean identical)
  {
    // R�cup�re les deux relations de type simple et les compare
    Bid4WinEntity<?, ?> entity1 = this.getRelationSimple(node.getRelation());
    Bid4WinEntity<?, ?> entity2 = toBeCompared.getRelationSimple(node.getRelation());
    return Bid4WinEntityComparator.getInstanceEntity().same(
        entity1, entity2, node.getNodeList(), referenced, identical);
  }
  /**
   * Cette m�thode permet de tester si la relation de type set (d�finie en param�tre
   * avec sa profondeur) de l'entit� en argument peut �tre consid�r�e �quivalente
   * � celle de l'entit� courante, sans avoir � v�rifier autre chose (nullit�, classe)
   * car ces tests doivent �tre effectu�s par les m�thodes appelantes. Le flag
   * identical indique le context d'utilisation de la m�thode.
   * @param toBeCompared Entit� avec la relation de laquelle on doit tester l'
   * �quivalence de la relation de l'entit� courante
   * @param node Noeud de la relation participant � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si la relation de type set de l'entit� en param�tre est consid�r�e
   * �quivalente � celle de l'entit� courante, false sinon
   */
  private boolean sameRelationSetInternal(CLASS toBeCompared,
                                          Bid4WinRelationNode node,
                                          Bid4WinMatchReferenceMap referenced,
                                          boolean identical)
  {
    // R�cup�re les deux relations de type set et les compare
    Set<? extends Bid4WinEntity<?, ?>> set1 = this.getRelationSet(node.getRelation());
    Set<? extends Bid4WinEntity<?, ?>> set2 = toBeCompared.getRelationSet(node.getRelation());
    return Bid4WinEntitySetComparator.getInstanceEntitySet().same(
        set1, set2, node.getNodeList(), referenced, identical);
  }
  /**
   * Cette m�thode permet de tester si la relation de type liste (d�finie en param�tre
   * avec sa profondeur) de l'entit� en argument peut �tre consid�r�e �quivalente
   * � celle de l'entit� courante, sans avoir � v�rifier autre chose (nullit�, classe)
   * car ces tests doivent �tre effectu�s par les m�thodes appelantes. Le flag
   * identical indique le context d'utilisation de la m�thode.
   * @param toBeCompared Entit� avec la relation de laquelle on doit tester l'
   * �quivalence de la relation de l'entit� courante
   * @param node Noeud de la relation participant � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si la relation de type liste de l'entit� en param�tre est consid�r�e
   * �quivalente � celle de l'entit� courante, false sinon
   */
  private boolean sameRelationListInternal(CLASS toBeCompared,
                                           Bid4WinRelationNode node,
                                           Bid4WinMatchReferenceMap referenced,
                                           boolean identical)
  {
    // R�cup�re les deux relations de type liste et les compare
    List<? extends Bid4WinEntity<?, ?>> list1 = this.getRelationList(node.getRelation());
    List<? extends Bid4WinEntity<?, ?>> list2 = toBeCompared.getRelationList(node.getRelation());
    return Bid4WinEntityListComparator.getInstanceEntityList().same(
        list1, list2, node.getNodeList(), referenced, identical);
  }
  /**
   * Cette m�thode permet de tester si la relation de type map (d�finie en param�tre
   * avec sa profondeur) de l'entit� en argument peut �tre consid�r�e �quivalente
   * � celle de l'entit� courante, sans avoir � v�rifier autre chose (nullit�, classe)
   * car ces tests doivent �tre effectu�s par les m�thodes appelantes. Le flag
   * identical indique le context d'utilisation de la m�thode.
   * @param toBeCompared Entit� avec la relation de laquelle on doit tester l'
   * �quivalence de la relation de l'entit� courante
   * @param node Noeud de la relation participant � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
   * � dire identicit� ou �quivalence
   * @return True si la relation de map liste de l'entit� en param�tre est consid�r�e
   * �quivalente � celle de l'entit� courante, false sinon
   */
  private boolean sameRelationMapInternal(CLASS toBeCompared,
                                          Bid4WinRelationNode node,
                                          Bid4WinMatchReferenceMap referenced,
                                          boolean identical)
  {
    // R�cup�re les deux relations de type map et les compare
    Map<?, ? extends Bid4WinEntity<?, ?>> map1 = this.getRelationMap(node.getRelation());
    Map<?, ? extends Bid4WinEntity<?, ?>> map2 = toBeCompared.getRelationMap(node.getRelation());
    return Bid4WinEntityMapComparator.getInstanceEntityMap().same(
        map1, map2, node.getNodeList(), referenced, identical);
  }

  /**
   * Cette m�thode permet de d�finir un classement par d�faut de deux entit�s en
   * fonction de leurs identifiants respectifs. Dans le cas d'identifiants identiques,
   * les version seront utilis�es pour d�finir un classement de l'entit� la plus
   * ancienne � la plus r�cente
   * @param toBeCompared Entit� � comparer � l'entit� courante
   * @return Une valeur n�gative, positive ou z�ro selon que l'entit� courante
   * est consid�r�e plus petite que, plus grande que ou �gale � l'entit� en argument
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
   * Cette m�thode permet d'effectuer le rendu de l'objet en cha�ne de caract�res.
   * Elle se basera sur le retour de la m�thode render((Bid4WinList<Bid4WinRelationNode>)
   * en utilisant la liste fournie par la m�thode getFullRelationNodeList() qui
   * est donc � surcharger afin d'obtenir un rendu par d�faut plus complet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public final StringBuffer render()
  {
    return this.render(this.getFullRelationNodeList());
  }
  /**
   * Cette m�thode permet d'effectuer le rendu de l'objet en cha�ne de caract�res
   * et de choisir les relations qui y participeront ainsi que leur profondeur
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de l'objet en cha�ne de caract�res
   */
  public final StringBuffer render(Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.render(nodeList, null);
  }
  /**
   * Cette m�thode permet d'effectuer le rendu de l'objet en cha�ne de caract�res
   * et de choisir les relations qui y participeront ainsi que leur profondeur
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de l'objet en cha�ne de caract�res
   */
  public final StringBuffer render(Bid4WinList<Bid4WinRelationNode> nodeList,
                                   Bid4WinEntityReferenceSet referenced)
  {
    if(referenced == null)
    {
      referenced = new Bid4WinEntityReferenceSet();
    }
    // L'entit� a d�j� particip� au rendu, on ne fera que le rendu de son identification
    else if(referenced.isReferenced(this))
    {
      return this.renderId();
    }
    // R�f�rence l'entit� courante
    referenced.referenceEntity(this);
    // Effectue le rendu de l'objet sans relation
    StringBuffer buffer = this.renderRelationNone();
    // Ajoute le rendu des relations et retourne le r�sultat
    return UtilString.addParagraph(buffer, this.renderRelation(nodeList, referenced));
  }

  /**
   * Cette m�thode permet d'effectuer le rendu simple de l'object courant sans
   * prise en compte de ses relations et est donc � surcharger afin de le compl�ter
   * @return Le rendu simple de l'objet en cha�ne de caract�res
   */
  protected StringBuffer renderRelationNone()
  {
    return this.renderId();
  }
  /**
   * Cette m�thode permet d'effectuer le rendu de l'identification de l'object
   * courant
   * @return Le rendu de l'identification de l'objet en cha�ne de caract�res
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
   * Cette m�thode permet d'effectuer le rendu des relations d�finies par la liste
   * de noeuds en argument en m�me temps que leur profondeur
   * @param nodeList Liste de noeuds des relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu des relations d�finies par la liste de noeuds en argument
   * en m�me temps que leur profondeur
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
   * Cette m�thode permet d'effectuer le rendu de la relation d�finie par le noeud
   * en argument
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de la relation d�finie par le noeud en argument
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
   * Cette m�thode permet d'effectuer le rendu de la relation de type objet simple
   * d�finie par le noeud en argument. Celle-ci peut �tre red�finie afin d'obtenir
   * un rendu sp�cifique sur certaines relations
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de la relation de type objet simple d�finie par le noeud en
   * argument
   */
  private StringBuffer renderRelationSimple(Bid4WinRelationNode node,
                                            Bid4WinEntityReferenceSet referenced)
  {
    // R�cup�re l'entit� associ�e
    Bid4WinEntity<?, ?> toRender = this.getRelationSimple(node.getRelation());
    // G�re la possible nullit� de l'entit�. Ce n'est pas n�cessaire mais c'est
    // pour avoir juste NULL sans crochet
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de l'entit�
    StringBuffer buffer = new StringBuffer("[");
    UtilString.addParagraph(buffer,
                            Bid4WinEntityRenderer.getInstanceEntity().render(toRender,
                                                                             node.getNodeList(),
                                                                             referenced),
                            1);
    return UtilString.addParagraph(buffer, "]");
  }
  /**
   * Cette m�thode permet d'effectuer le rendu de la relation de type collection
   * d�finie par le noeud en argument. Celle-ci peut �tre red�finie afin d'obtenir
   * un rendu sp�cifique sur certaines relations
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de la relation de type collection d�finie par le noeud en
   * argument
   */
  private StringBuffer renderRelationCollection(Bid4WinRelationNode node,
                                                Bid4WinEntityReferenceSet referenced)
  {
    // R�cup�re la collection associ�e
    Collection<? extends Bid4WinEntity<?, ?>> toRender = this.getRelationCollection(node.getRelation());
    // Effectue le rendu de la collection
    return Bid4WinEntityCollectionRenderer.getInstanceEntityCollection().render(
        toRender, node.getNodeList(), referenced);
  }
  /**
   * Cette m�thode permet d'effectuer le rendu de la relation de type map d�finie
   * par le noeud en argument. Celle-ci peut �tre red�finie afin d'obtenir un rendu
   * sp�cifique sur certaines relations
   * @param node Noeud de la relation dont on souhaite le rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de la relation de type map d�finie par le noeud en argument
   */
  private StringBuffer renderRelationMap(Bid4WinRelationNode node,
                                         Bid4WinEntityReferenceSet referenced)
  {
    // R�cup�re la map associ�e
    Map<?, ? extends Bid4WinEntity<?, ?>> toRender = this.getRelationMap(node.getRelation());
    // Effectue le rendu de la map
    return Bid4WinEntityMapRenderer.getInstanceEntityMap().render(
        toRender, node.getNodeList(), referenced);
  }

  /**
   * Cette m�thode permet de s'assurer du chargement "complet" des relations de
   * l'objet. Elle se basera sur la m�thode loadRelation((Bid4WinList<Bid4WinRelationNode>)
   * en utilisant la liste fournie par la m�thode getFullRelationNodeList() qui
   * est donc � surcharger afin d'obtenir un chargement par d�faut plus complet
   * @return L'entit� courante dont les relations sont bien charg�es
   */
  public final CLASS loadRelation()
  {
    return this.loadRelation(this.getFullRelationNodeList());
  }
  /**
   * Cette m�thode permet de s'assurer du chargement des relations d�finies en
   * param�tre avec leur profondeur dans le cas de relations en lazy loading.
   * Attention, c'est m�thode ne pourra pas charger les donn�es manquantes en
   * dehors du context transactionnel d'o� est issue l'entit� courante
   * @param nodeList Liste de noeuds des relations � charger
   * @return L'entit� courante dont les relations choisies sont bien charg�es
   */
  public final CLASS loadRelation(Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.loadRelation(nodeList, new Bid4WinEntityReferenceSet());
  }
  /**
   * Cette m�thode permet de s'assurer du chargement des relations d�finies en
   * param�tre avec leur profondeur dans le cas de relations en lazy loading.
   * Attention, c'est m�thode ne pourra pas charger les donn�es manquantes en
   * dehors du context transactionnel d'o� est issue l'entit� courante
   * @param nodeList Liste de noeuds des relations � charger
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� �t� charg�es
   * @return L'entit� courante dont les relations choisies sont bien charg�es
   */
  @SuppressWarnings("unchecked")
  public final CLASS loadRelation(Bid4WinList<Bid4WinRelationNode> nodeList,
                                  Bid4WinEntityReferenceSet referenced)
  {
    // L'entit� a d�j� �t� charg�e
    if(referenced.isReferenced(this))
    {
      return (CLASS)this;
    }
    // Dans le cas d'une relation simple charg�e passivement, l'entit� est en fait
    // encapsul�e dans un proxy javassist qui ne sera compl�tement charg� que s'
    // il est acc�d�
    this.getId();
    // R�f�rence l'entit� comme charg�e
    referenced.referenceEntity(this);
    // Charge les relations demand�es
    for(Bid4WinRelationNode node : nodeList)
    {
      this.loadRelation(node, referenced);
    }
    return (CLASS)this;
  }
  /**
   * Cette m�thode permet de s'assurer du chargement de la relation d�finie en
   * param�tre avec sa profondeur dans le cas d'une relation en lazy loading.
   * Attention, c'est m�thode ne pourra pas charger les donn�es manquantes en
   * dehors du context transactionnel d'o� est issue l'entit� courante
   * @param node Noeud de la relation � charger
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� �t� charg�es
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
   * Cette m�thode permet de s'assurer du chargement de la relation de type simple
   * d�finie en param�tre avec sa profondeur dans le cas d'une relations en lazy
   * loading. Attention, c'est m�thode ne pourra pas charger les donn�es manquantes
   * en dehors du context transactionnel d'o� est issue l'entit� courante
   * @param node Noeud de la relation de type simple � charger
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� �t� charg�es
   */
  private void loadRelationSimple(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    // R�cup�re la relation
    Bid4WinEntity<?, ?> entity = this.getRelationSimple(node.getRelation());
    // Propage le chargement si n�cessaire
    Bid4WinEntityLoader.getInstance().loadRelation(entity, node.getNodeList(), referenced);
  }
  /**
   * Cette m�thode permet de s'assurer du chargement de la relation de type collection
   * d�finie en param�tre avec sa profondeur dans le cas d'une relations en lazy
   * loading. Attention, c'est m�thode ne pourra pas charger les donn�es manquantes
   * en dehors du context transactionnel d'o� est issue l'entit� courante
   * @param node Noeud de la relation de type collection � charger
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� �t� charg�es
   */
  private void loadRelationCollection(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    // R�cup�re la relation
    Collection<? extends Bid4WinEntity<?, ?>> collection = this.getRelationCollection(
        node.getRelation());
    // Charge la relation et propage le chargement si n�cessaire
    Bid4WinEntityLoader.getInstance().loadRelation(collection, node.getNodeList(), referenced);
  }
  /**
   * Cette m�thode permet de s'assurer du chargement de la relation de type map
   * d�finie en param�tre avec sa profondeur dans le cas d'une relations en lazy
   * loading. Attention, c'est m�thode ne pourra pas charger les donn�es manquantes
   * en dehors du context transactionnel d'o� est issue l'entit� courante
   * @param node Noeud de la relation de type map � charger
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� �t� charg�es
   */
  private void loadRelationMap(Bid4WinRelationNode node, Bid4WinEntityReferenceSet referenced)
  {
    // R�cup�re la relation
    Map<?, ? extends Bid4WinEntity<?, ?>> map = this.getRelationMap(node.getRelation());
    // Charge la relation et propage le chargement si n�cessaire
    Bid4WinEntityLoader.getInstance().loadRelation(map, node.getNodeList(), referenced);
  }

  /**
   * Cette m�thode doit remonter la liste "compl�te" des noeuds de relations de
   * l'objet. Cette liste est celle qui sera utilis�e par les impl�mentations par
   * d�faut de same(), identical(), render() et loadRelation() et est donc � surcharger
   * pour la compl�ter en n'oubliant les profondeurs de relation
   * @return La liste "compl�te" des noeuds de relations de l'objet
   */
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    return new Bid4WinList<Bid4WinRelationNode>();
  }

  // TODO les get et set relations sont accessibles au travers du m�me package
  /**
   * Cette fonction permet de r�cup�rer l'entit� correspondant � la relation en
   * argument. Elle doit �tre red�finie pour toute nouvelle relation de type simple
   * � remonter
   * @param relation D�finition de la relation � laquelle participe l'entit� �
   * r�cup�rer
   * @return L'entit� correspondant � la relation en argument ou null le cas �ch�ant
   * @throws Bid4WinRuntimeException Si la relation n'est pas g�r�e par l'entit�
   */
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
            throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette m�thode permet de positionner l'entit� correspondant � la relation en
   * argument. Elle doit �tre red�finie pour toute nouvelle relation de type simple
   * � positionner
   * @param relation D�finition de la relation � laquelle participe l'entit� �
   * positionner
   * @param entity Entit� � positionner
   * @throws Bid4WinRuntimeException Si la relation n'est pas g�r�e par l'entit�
   */
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
            throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString() +
                                     " for entity type " + entity.getClass().getSimpleName());
  }
  /**
   * Cette fonction permet de r�cup�rer la collection correspondant � la relation
   * en argument
   * @param relation D�finition de la relation � laquelle participe la collection
   * � r�cup�rer
   * @return La collection correspondant � la relation en argument ou null le cas
   * �ch�ant
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
   * Cette fonction permet de r�cup�rer le set correspondant � la relation en argument.
   * Elle doit �tre red�finie pour toute nouvelle relation de type set � remonter
   * @param relation D�finition de la relation � laquelle participe le set � r�cup�rer
   * @return Le set correspondant � la relation en argument ou null le cas �ch�ant
   * @throws Bid4WinRuntimeException Si la relation n'est pas g�r�e par l'entit�
   */
  protected Bid4WinSet<? extends Bid4WinEntity<?, ?>> getRelationSet(Bid4WinRelation relation)
            throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette fonction permet de r�cup�rer la liste correspondant � la relation en
   * argument. Elle doit �tre red�finie pour toute nouvelle relation de type liste
   * � remonter
   * @param relation D�finition de la relation � laquelle participe la liste �
   * r�cup�rer
   * @return La liste correspondant � la relation en argument ou null le cas �ch�ant
   * @throws Bid4WinRuntimeException Si la relation n'est pas g�r�e par l'entit�
   */
  protected Bid4WinList<? extends Bid4WinEntity<?, ?>> getRelationList(Bid4WinRelation relation)
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette fonction permet de r�cup�rer la map correspondant � la relation en argument.
   * Elle doit �tre red�finie pour toute nouvelle relation de type map � remonter
   * @param relation D�finition de la relation � laquelle participe la map � r�cup�rer
   * @return La map correspondant � la relation en argument ou null le cas �ch�ant
   * @throws Bid4WinRuntimeException Si la relation n'est pas g�r�e par l'entit�
   */
  protected Bid4WinMap<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    throw new Bid4WinRuntimeException("Unknown relation " + relation.toString());
  }
  /**
   * Cette m�thode permet de r�cup�rer la cl� associ�e � l'entit� en param�tre pour
   * son classement dans la map correspondant � la relation en argument
   * @param relation D�finition de la relation � laquelle participe la map pour
   * laquelle r�cup�rer la cl� de classement de l'entit� en argument
   * @param value Entit� pour laquelle r�cup�rer la cl� de classement dans la map
   * correspondant � la relation en argument
   * @return La cl� associ�e � l'entit� en param�tre pour sont classement dans
   * la map correspondant � la relation en argument
   * @throws Bid4WinRuntimeException Si la relation n'est pas g�r�e par l'entit�
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
   * Cette m�thode permet de positionner l'entit� en argument sur la relation de
   * type simple donn�e de l'entit� courante. Cette fonction ne doit pas �tre
   * appel�e directement mais fait partie de la cr�ation du lien inverse d'une
   * relation bidirectionnelle
   * @param link D�finition de la relation de type simple de l'entit� courante sur
   * laquelle positionner celle en argument
   * @param backLink Relation de type simple d�finissant le lien inverse de l'entit�
   * en argument vers l'entit� courante
   * @param toBeAdded Entit� � positionner sur la relation de type simple donn�e
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   * @throws UserException Si l'entit� en argument est nulle ou ne r�f�rence pas
   * l'entit� courante ou si la relation donn�e r�f�rence d�j� une entit�
   */
  private void addRelationSimple(Bid4WinRelation link,
                                 Bid4WinRelation backLink,
                                 Bid4WinEntity<?, ?> toBeAdded)
          throws ProtectionException, UserException
  {
    // V�rifie la protection de l'entit� courante
    this.checkProtection();
    // R�cup�ration de la base de message � utiliser pour l'entit� en argument
    MessageRef base = this.getMessageRefBase(link);
    // V�rifie que l'entit� � ajouter n'est pas nulle
    UtilObject.checkNotNull("toBeAdded", toBeAdded,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // V�rifie que la relation de l'entit� courante n'est pas d�j� �tablie
    UtilObject.checkNull("linked", this.getRelationSimple(link),
                         base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // V�rifie que l'entit� courante est r�f�renc�e par l'entit� en argument
    Bid4WinEntity<?, ?> entity = toBeAdded.getRelationSimple(backLink);
    UtilObject.checkNotNull("referenced", entity, this.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilBoolean.checkTrue("referenced", this == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    // D�finie la relation entre l'entit� courante et l'entit� en argument
    this.setRelationSimple(link, toBeAdded);
  }
  /**
   * Cette m�thode permet de retirer l'entit� en argument de la relation de type
   * simple donn�e de l'entit� courante. Cette fonction ne doit pas �tre appel�e
   * directement mais fait partie de la suppression du lien inverse d'une relation
   * bidirectionnelle
   * @param link D�finition de la relation de type simple de l'entit� courante de
   * laquelle retirer celle en argument
   * @param backLink Relation de type simple d�finissant le lien inverse de l'entit�
   * en argument vers l'entit� courante
   * @param toBeRemoved Entit� � retirer de la relation de type simple donn�e
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   * @throws UserException Si l'entit� en argument est nulle, r�f�rence une entit�
   * ou n'est pas r�f�renc�e par la relation donn�e
   */
  private void removeRelationSimple(Bid4WinRelation link,
                                    Bid4WinRelation backLink,
                                    Bid4WinEntity<?, ?> toBeRemoved)
          throws ProtectionException, UserException
  {
    // V�rifie la protection de l'entit� courante
    this.checkProtection();
    // R�cup�ration de la base de message � utiliser pour l'entit� en argument
    MessageRef base = this.getMessageRefBase(link);
    // V�rifie que l'entit� � retirer n'est pas nulle
    UtilObject.checkNotNull("toBeRemoved", toBeRemoved,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // V�rifie que l'entit� courante r�f�rence bien l'entit� en argument
    Bid4WinEntity<?, ?> entity = this.getRelationSimple(link);
    UtilObject.checkNotNull("contained", entity, this.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
    UtilBoolean.checkTrue("contained", toBeRemoved == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    // V�rifie que l'entit� en argument ne r�f�rence plus d'entit�
    UtilObject.checkNull("referenced", toBeRemoved.getRelationSimple(backLink),
                         this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Supprime la relation entre l'entit� courante et l'entit� en argument
    this.setRelationSimple(link, null);
  }
  /**
   * Cette m�thode permet d'ajouter l'entit� en argument � la collection de l'
   * entit� courante d�finie par la relation donn�e. Cette fonction ne doit pas
   * �tre appel�e directement mais fait partie de la cr�ation du lien inverse d'
   * une relation bidirectionnelle
   * @param link Relation d�finissant la collection de l'entit� courante � laquelle
   * ajouter celle en argument
   * @param backLink Relation de type simple d�finissant le lien inverse de l'entit�
   * en argument vers l'entit� courante
   * @param toBeAdded Entit� � ajouter � la collection d�finie par la relation
   * donn�e
   * @throws ProtectionException Si la collection � laquelle ajouter l'entit� est
   * prot�g�e
   * @throws UserException Si l'entit� en argument est nulle, ne r�f�rence pas l'
   * entit� courante ou est d�j� r�f�renc�e par la collection d�finie par la relation
   */
  @SuppressWarnings("unchecked")
  private void addRelationCollection(Bid4WinRelation link,
                                     Bid4WinRelation backLink,
                                     Bid4WinEntity<?, ?> toBeAdded)
          throws ProtectionException, UserException
  {
    // V�rifie que l'entit� courante est r�f�renc�e par l'entit� en argument
    Bid4WinEntity<?, ?> entity = toBeAdded.getRelationSimple(backLink);
    UtilObject.checkNotNull("referenced", entity, this.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilBoolean.checkTrue("referenced", this == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_INVALID_ERROR));
    // Ajoute l'entit� en argument � la relation de collection de l'entit� courante
    this.add((Bid4WinCollectionAbstract<Bid4WinEntity<?,?>, ?, ?>)this.getRelationCollection(link),
             toBeAdded, this.getMessageRefBase(link));
  }
  /**
   * Cette m�thode permet de retirer l'entit� en argument de la collection de l'
   * entit� courante d�finie par la relation donn�e. Cette fonction ne doit pas
   * �tre appel�e directement mais fait partie de la suppression du lien inverse
   * d'une relation bidirectionnelle
   * @param link Relation d�finissant la collection de l'entit� courante de laquelle
   * retirer celle en argument
   * @param backLink Relation de type simple d�finissant le lien inverse de l'entit�
   * en argument vers l'entit� courante
   * @param toBeRemoved Entit� � retirer de la collection d�finie par la relation
   * donn�e
   * @throws ProtectionException Si la collection de laquelle retirer l'entit� est
   * prot�g�e
   * @throws UserException Si l'entit� en argument est nulle, r�f�rence une entit�
   * ou n'est pas r�f�renc�e par la collection d�finie par la relation
   */
  @SuppressWarnings("unchecked")
  private void removeRelationCollection(Bid4WinRelation link,
                                        Bid4WinRelation backLink,
                                        Bid4WinEntity<?, ?> toBeRemoved)
          throws ProtectionException, UserException
  {
    // V�rifie que l'entit� en argument ne r�f�rence plus d'entit�
    if(toBeRemoved != null)
    {
      UtilObject.checkNull("referenced", toBeRemoved.getRelationSimple(backLink),
                           this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    }
    // Retire l'entit� en argument de la relation de collection de l'entit� courante
    this.remove((Bid4WinCollectionAbstract<Bid4WinEntity<?,?>, ?, ?>)this.getRelationCollection(link),
                toBeRemoved, this.getMessageRefBase(link));
  }
  /**
   * Cette m�thode permet d'ajouter l'entit� en argument � la map de l'entit� courante
   * d�finie par la relation donn�e. Cette fonction ne doit pas �tre appel�e directement
   * mais fait partie de la cr�ation du lien invere d'une relation bidirectionnelle
   * @param link Relation d�finissant la map de l'entit� courante � laquelle ajouter
   * celle en argument
   * @param backLink Relation de type simple d�finissant le lien inverse de l'entit�
   * en argument vers l'entit� courante
   * @param toBeAdded Entit� � ajouter � la map d�finie par la relation donn�e
   * @throws ProtectionException Si la map � laquelle ajouter l'entit� est prot�g�e
   * @throws UserException Si l'entit� en argument est nulle ne r�f�rence pas l'
   * entit� courante ou est d�j� r�f�renc�e par la map d�finie par la relation
   */
  @SuppressWarnings("unchecked")
  private void addRelationMap(Bid4WinRelation link,
                              Bid4WinRelation backLink,
                              Bid4WinEntity<?, ?> toBeAdded)
          throws ProtectionException, UserException
  {
    // V�rifie que l'entit� courante est r�f�renc�e par l'entit� en argument
    Bid4WinEntity<?, ?> entity = toBeAdded.getRelationSimple(backLink);
    UtilObject.checkNotNull("referenced", entity, this.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    UtilBoolean.checkTrue("referenced", this == entity.self(),
                          this.getMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
    // Ajoute l'entit� en argument � la relation de map de l'entit� courante
    this.add((Bid4WinMap<Object, Bid4WinEntity<?, ?>>)this.getRelationMap(link),
             this.getRelationMapKey(link, toBeAdded), toBeAdded,
             this.getMessageRefBase(link));
  }
  /**
   * Cette m�thode permet de retirer l'entit� en argument de la map de l'entit�
   * courante d�finie par la relation donn�e. Cette fonction ne doit pas �tre
   * appel�e directement mais fait partie de la suppression du lien inverse d'une
   * relation bidirectionnelle
   * @param link Relation d�finissant la map de l'entit� courante de laquelle retirer
   * celle en argument
   * @param backLink Relation de type simple d�finissant le lien inverse de l'entit�
   * en argument vers l'entit� courante
   * @param toBeRemoved Entit� � retirer de la map d�finie par la relation donn�e
   * @throws ProtectionException Si la map de laquelle retirer l'entit� est prot�g�e
   * @throws UserException Si l'entit� en argument est nulle, r�f�rence une entit�
   * ou n'est pas r�f�renc�e par la map d�finie par la relation
   */
  @SuppressWarnings("unchecked")
  private void removeRelationMap(Bid4WinRelation link,
                                 Bid4WinRelation backLink,
                                 Bid4WinEntity<?, ?> toBeRemoved)
          throws ProtectionException, UserException
  {
    // V�rifie que l'entit� en argument ne r�f�rence plus d'entit�
    if(toBeRemoved != null)
    {
      UtilObject.checkNull("referenced", toBeRemoved.getRelationSimple(backLink),
                           this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    }
    // Retire l'entit� en argument de la relation de collection de l'entit� courante
    this.remove((Bid4WinMap<Object, Bid4WinEntity<?, ?>>)this.getRelationMap(link),
                this.getRelationMapKey(link, toBeRemoved), toBeRemoved,
                this.getMessageRefBase(link));
  }

  /**
   * Cette m�thode permet de cr�er le lien de l'entit� courante vers celle en
   * argument pour la relation de type simple donn�e
   * @param link D�finition de la relation de type simple � cr�er
   * @param toBeLinked Entit� � r�f�rencer par l'entit� courante
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   * @throws UserException Si l'entit� en argument est nulle ou si l'entit� courante
   * est d�j� li�e
   */
  protected final void linkTo(Bid4WinRelation link, Bid4WinEntity<?, ?> toBeLinked)
            throws ProtectionException, UserException
  {
    // V�rifie la protection de l'entit� courante
    this.checkProtection();
    // R�cup�ration de la base de message � utiliser pour l'entit� en argument
    MessageRef base = this.getMessageRefBase(link);
    // V�rifie que l'entit� en argument n'est pas nulle
    UtilObject.checkNotNull("toBeLinked", toBeLinked,
                            base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // V�rifie que l'entit� courante n'est pas d�j� li�e
    UtilObject.checkNull("linked", this.getRelationSimple(link),
                         base.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    // Cr�e le lien vers l'entit� en argument
    this.setRelationSimple(link, toBeLinked);
  }
  /**
   * Cette m�thode permet de supprimer le lien de l'entit� courante vers celle
   * d�finie par la relation de type simple en argument
   * @param link D�finition de la relation de type simple � supprimer
   * @return L'entit� anciennement r�f�renc�e par l'entit� courante
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   * @throws UserException Si l'entit� courante n'est pas li�e
   */
  protected final Bid4WinEntity<?, ?> unlinkFrom(Bid4WinRelation link)
            throws ProtectionException, UserException
  {
    // V�rifie la protection de l'entit� courante
    this.checkProtection();
    // R�cup�ration de la base de message � utiliser pour l'entit� en argument
    MessageRef base = this.getMessageRefBase(link);
    // V�rifie l'existance du lien avec une entit�
    Bid4WinEntity<?, ?> entity = UtilObject.checkNotNull("linked",
                                                         this.getRelationSimple(link),
                                                         base.getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    // Retire le lien avec l'entit�
    this.setRelationSimple(link, null);
    // Retourne l'ancienne entit� li�e
    return entity;
  }
  /**
   * Cette m�thode permet de cr�er le lien bidirectionnel entre l'entit� courante
   * et celle en argument avec les relations donn�es
   * @param link Relation de type simple d�finissant le lien de l'entit� courante
   * vers l'entit� en argument
   * @param backLink Relation d�finissant le lien inverse de l'entit� en argument
   * vers l'entit� courante
   * @param toBeLinked Entit� � lier � l'entit� courante
   * @throws ProtectionException Si l'entit� courante ou celle en argument est
   * prot�g�e
   * @throws UserException Si l'entit� en argument est nulle ou r�f�rence d�j� l'
   * entit� courante ou si cette derni�re est d�j� li�e
   */
  protected final void linkTo(Bid4WinRelation link, Bid4WinRelation backLink,
                              Bid4WinEntity<?, ?> toBeLinked)
            throws ProtectionException, UserException
  {
    // Cr�e le lien de l'entit� courante vers l'entit� en argument
    this.linkTo(link, toBeLinked);
    // Cr�e le lien inverse de l'entit� en argument vers l'entit� courante
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
    // Reviens � l'�tat initial
    catch(ProtectionException ex)
    {
      this.unlinkFrom(link);
      throw ex;
    }
    // Reviens � l'�tat initial
    catch(UserException ex)
    {
      this.unlinkFrom(link);
      throw ex;
    }
  }
  /**
   * Cette m�thode permet de supprimer le lien bidirectionnel de l'entit� courante
   * d�fini par les relations en argument
   * @param link Relation de type simple d�finissant le lien de l'entit� courante
   * vers l'entit� li�e
   * @param backLink Relation d�finissant le lien inverse de l'entit� li�e vers
   * l'entit� courante
   * @return L'entit� anciennement li�e � l'entit� courante
   * @throws ProtectionException Si l'entit� courante ou celle li�e est prot�g�e
   * @throws UserException Si l'entit� courante n'est pas li�e ou pas r�f�renc�e
   * par l'entit� li�e
   */
  protected final Bid4WinEntity<?, ?> unlinkFrom(Bid4WinRelation link,
                                                 Bid4WinRelation backLink)
            throws ProtectionException, UserException
  {
    // Retire le lien de l'entit� courante vers l'entit� en argument
    Bid4WinEntity<?, ?> entity = this.unlinkFrom(link);
    // Retire le lien inverse de l'entit� en argument vers l'entit� courante
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
    // Reviens � l'�tat initial
    catch(ProtectionException ex)
    {
      this.linkTo(link, entity);
      throw ex;
    }
    // Reviens � l'�tat initial
    catch(UserException ex)
    {
      this.linkTo(link, entity);
      throw ex;
    }
  }

  /**
   * Cette m�thode permet de forcer, si ce champ est impl�ment�, la modification
   * de l'entit� courante impliquant l'incr�mentation de sa version
   * @return L'entit� modifi�e seulement si ce champ est impl�ment�
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   */
  @SuppressWarnings("unchecked")
  public CLASS forceUpdate() throws ProtectionException
  {
    // V�rifie la protection de l'entit�
    this.checkProtection();
    this.setUpdateForce(this.getVersion() + 1);
    return (CLASS)this;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de l'entit�
   * @return L'identifiant de l'entit�
   */
  public ID getId()
  {
    return this.id;
  }
  /**
   * Setter de l'identifiant de l'entit�
   * @param id Identifiant de l'entit� � positionner
   */
  private void setId(ID id)
  {
    this.id = id;
  }

  /**
   * Getter du champ permettant le for�age de la modification de l'entit�
   * @return Le champ permettant le for�age de la modification de l'entit�
   */
  public int getUpdateForce()
  {
    return this.updateForce;
  }
  /**
   * Setter du champ permettant le for�age de la modification de l'entit�
   * @param updateForce Champ permettant le for�age de la modification de l'entit�
   */
  private void setUpdateForce(int updateForce)
  {
    this.updateForce = updateForce;
  }

  /**
   * Getter de la version de l'entit�
   * @return La version de l'entit�
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
   * Setter de la version de l'entit�
   * @param version Version de l'entit� � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setVersion(int version)
  {
    this.version = version;
  }

  /**
   * Getter de la date de cr�ation de l'entit�
   * @return La date de cr�ation de l'entit�
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREATE_DATE", nullable = false, unique = false)
  public Bid4WinDate getCreateDate()
  {
    return this.createDate;
  }
  /**
   * Setter de la date de cr�ation de l'entit�
   * @param createDate Date de cr�ation de l'entit� � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setCreateDate(Bid4WinDate createDate)
  {
    this.createDate = createDate;
  }
  /**
   * Cette m�thode est appel�e � la cr�ation en base de l'entit� et permet de
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
   * Getter de la date de modification de l'entit�
   * @return La date de modification de l'entit�
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "UPDATE_DATE", nullable = false, unique = false)
  public Bid4WinDate getUpdateDate()
  {
    return this.updateDate;
  }
  /**
   * Setter de la date de modification de l'entit�
   * @param updateDate Date de modification de l'entit� � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setUpdateDate(Bid4WinDate updateDate)
  {
    this.updateDate = updateDate;
  }
  /**
   * Cette m�thode est appel�e � la modification en base de l'entit� et permet de
   * mettre � jour le suivi de modification
   */
  @SuppressWarnings(value = "unused")
  // Annotation pour la persistence
  @PreUpdate
  private void onUpdate()
  {
    this.updateDate = new Bid4WinDate();
  }

  //*************************************************************************//
  //************ D�finition de la classe de comparaison donnant *************//
  //************     acc�s aux m�thodes internes des entit�s    *************//
  //*************************************************************************//
  /**
   * Cette classe donne acc�s aux m�thodes internes de comparaison des entit�s<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class InternalEntityComparator extends InternalObjectComparator
  {
    /**
     * Cette m�thode permet de tester l'�quivalence entre les deux entit�s en param�tre,
     * c'est � dire si tous leurs champs, identifiants et versions compris selon
     * les cas d'utilisation, sont eux aussi �quivalents, de m�me que les relations
     * d�finies en param�tre en m�me temps que leur profondeur, sans avoir � v�rifier
     * autre chose (nullit�, classe) car ces tests doivent �tre effectu�s par les
     * m�thodes appelantes. Le flag identical indique le context d'utilisation de
     * la m�thode. Elle se basera sur le retour de l'appel � la m�thode sameInternal(CLASS ...)
     * de la premi�re entit� � comparer
     * @param <TYPE> D�finition du type des objets � comparer
     * @param object1 Premier objet � comparer
     * @param object2 Deuxi�me objet � comparer
     * @param nodeList Liste de noeuds de relations participants � la comparaison
     * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
     * @param identical Flag indiquant le context d'utilisation de la m�thode, c'est
     * � dire identicit� ou �quivalence
     * @return True si les deux objets en param�tre sont consid�r�s �quivalents
     * (liens �ventuels avec d'autres entit�s pris en compte), false sinon
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
