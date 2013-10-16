package com.bid4win.commons.persistence.entity.comparator;

import java.util.Collection;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.comparator.Bid4WinObjectCollectionComparator;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <COLLECTION> TODO A COMMENTER<BR>
 * @param <COMPARATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityCollectionComparator<ENTITY extends Bid4WinEntity<?, ?>,
                                               COLLECTION extends Collection<? extends ENTITY>,
                                               COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinObjectCollectionComparator<ENTITY, COLLECTION, COMPARATOR>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinEntityCollectionComparator<Bid4WinEntity<?, ?>, Collection<? extends Bid4WinEntity<?, ?>>,
                                                         Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntityCollectionComparator<Bid4WinEntity<?, ?>, Collection<? extends Bid4WinEntity<?, ?>>,
                                                       Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>(
          Bid4WinEntityComparator.getInstanceEntity());
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.core.comparator.Bid4WinObjectCollectionComparator#getInstanceObjectCollection()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinObjectCollectionComparator<Bid4WinObject<?>, Collection<? extends Bid4WinObject<?>>,
                                                  Bid4WinObjectComparator<Bid4WinObject<?>>>
         getInstanceObjectCollection()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static Bid4WinEntityCollectionComparator<Bid4WinEntity<?, ?>, Collection<? extends Bid4WinEntity<?, ?>>,
                                                  Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
         getInstanceEntityCollection()
  {
    return Bid4WinEntityCollectionComparator.instance;
  }

  /**
   * Constructeur
   * @param objectComparator Classe de comparaison par d�faut des objets de la collection
   */
  public Bid4WinEntityCollectionComparator(COMPARATOR objectComparator)
  {
    super(objectComparator);
  }

  /**
   *
   * TODO A COMMENTER
   * @param collection1 TODO A COMMENTER
   * @param collection2 TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean same(COLLECTION collection1, COLLECTION collection2)
  {
    // TODO verifier pour le null ...
    return this.same(collection1, collection2, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param collection1 TODO A COMMENTER
   * @param collection2 TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean identical(COLLECTION collection1, COLLECTION collection2)
  {
    // TODO verifier pour le null ...
    return this.identical(collection1, collection2, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param collection1 TODO A COMMENTER
   * @param collection2 TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean same(COLLECTION collection1, COLLECTION collection2,
                      Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.same(collection1, collection2, nodeList, new Bid4WinMatchReferenceMap(), false);
  }
  /**
   *
   * TODO A COMMENTER
   * @param collection1 TODO A COMMENTER
   * @param collection2 TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean identical(COLLECTION collection1, COLLECTION collection2,
                           Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.same(collection1, collection2, nodeList, new Bid4WinMatchReferenceMap(), true);
  }

  /**
   * Cette m�thode permet de tester, avec la possibilit� qu'ils soient nuls, l'
   * �quivalence ou l'identicit� des listes d'entit�s en argument selon la valeur
   * du param�tre identical, en incluant leurs relations d�finies en param�tre en
   * m�me temps que leur profondeur
   * @param collection1 Premi�re liste d'entit�s � comparer
   * @param collection2 Deuxi�me liste d'entit�s � comparer
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant si on souhaite tester l'�quivalence ou l'
   * identicit� des deux listes d'entit�s en param�tre
   * @return True si les deux listes d'entit�s sont consid�r�es �quivalentes ou
   * identiques, false sinon
   */
  public boolean same(COLLECTION collection1, COLLECTION collection2,
                      Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    return this.same(collection1, collection2, this.getObjectComparator(),
                     nodeList, referenced, identical);
  }

  /**
   * Cette m�thode permet de tester, avec la possibilit� qu'elles soient nulles,
   * l'�quivalence ou l'identicit� des collections d'entit�s en argument selon la
   * valeur du param�tre identical, en incluant leurs relations d�finies en param�tre
   * en m�me temps que leur profondeur
   * @param collection1 Premi�re liste d'entit�s � comparer
   * @param collection2 Deuxi�me liste d'entit�s � comparer
   * @param compatator Classe � utiliser pour comparer chaque entit�
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant si on souhaite tester l'�quivalence ou l'
   * identicit� des deux listes d'entit�s en param�tre
   * @return True si les deux listes d'entit�s sont consid�r�es �quivalentes ou
   * identiques, false sinon
   */
  public boolean same(COLLECTION collection1, COLLECTION collection2,
                      COMPARATOR compatator, Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    // Pour comparer plus rapidement
    if(collection1 == collection2)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(collection1 == null || collection2 == null)
    {
      return false;
    }
    // Pour que deux collections soit similaires, il faut qu'elles aient les m�me tailles
    if(collection1.size() != collection2.size())
    {
      return false;
    }
    // Parcours la premi�re collection et recherche les entit�s similaires dans
    // la deuxi�me
    Bid4WinCollection<ENTITY> collection = new Bid4WinCollection<ENTITY>(collection2);
    for(ENTITY entity1 : collection1)
    {
      boolean found = false;
      for(ENTITY entity2 : collection)
      {
        // On a trouv� une entit� similaire
        if(compatator.same(entity1, entity2, nodeList, referenced, identical))
        {
          found = true;
          // On diminue la collection de recherche
          collection.remove(entity2);
          break;
        }
      }
      // Aucune entit� similaire n'a �t� trouv�e, les collections ne le sont donc pas
      if(!found)
      {
        return false;
      }
    }
    return true;
  }
}
