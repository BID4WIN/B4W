package com.bid4win.commons.persistence.entity.comparator;

import java.util.Collection;

import com.bid4win.commons.core.Bid4WinObject;
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
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinEntityCollectionComparator<ENTITY extends Bid4WinEntity<?, ?>,
                                                        COLLECTION extends Collection<? extends ENTITY>,
                                                        COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinObjectCollectionComparator<ENTITY, COLLECTION, COMPARATOR>
{
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
   * Constructeur
   * @param objectComparator Classe de comparaison par défaut des objets de la collection
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
   * Cette méthode permet de tester, avec la possibilité qu'ils soient nuls, l'
   * équivalence ou l'identicité des listes d'entités en argument selon la valeur
   * du paramètre identical, en incluant leurs relations définies en paramètre en
   * même temps que leur profondeur
   * @param collection1 Première liste d'entités à comparer
   * @param collection2 Deuxième liste d'entités à comparer
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant si on souhaite tester l'équivalence ou l'
   * identicité des deux listes d'entités en paramètre
   * @return True si les deux listes d'entités sont considérées équivalentes ou
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
   * Cette méthode permet de tester, avec la possibilité qu'elles soient nulles,
   * l'équivalence ou l'identicité des collections d'entités en argument selon la
   * valeur du paramètre identical, en incluant leurs relations définies en paramètre
   * en même temps que leur profondeur
   * @param collection1 Première liste d'entités à comparer
   * @param collection2 Deuxième liste d'entités à comparer
   * @param compatator Classe à utiliser pour comparer chaque entité
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant si on souhaite tester l'équivalence ou l'
   * identicité des deux listes d'entités en paramètre
   * @return True si les deux listes d'entités sont considérées équivalentes ou
   * identiques, false sinon
   */
  public abstract boolean same(COLLECTION collection1, COLLECTION collection2,
                               COMPARATOR compatator, Bid4WinList<Bid4WinRelationNode> nodeList,
                               Bid4WinMatchReferenceMap referenced, boolean identical);
}
