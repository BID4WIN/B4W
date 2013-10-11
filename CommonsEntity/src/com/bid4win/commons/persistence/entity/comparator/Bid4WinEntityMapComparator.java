package com.bid4win.commons.persistence.entity.comparator;

import java.util.Map;
import java.util.Map.Entry;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.comparator.Bid4WinObjectMapComparator;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <KEY_COMPARATOR> TODO A COMMENTER<BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ENTITY_COMPARATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityMapComparator<KEY, KEY_COMPARATOR extends Bid4WinComparator<KEY>,
                                        ENTITY extends Bid4WinEntity<?, ?>,
                                        ENTITY_COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinObjectMapComparator<KEY, KEY_COMPARATOR, ENTITY, ENTITY_COMPARATOR>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinEntityMapComparator<Object, Bid4WinComparator<Object>,
                                                  Bid4WinEntity<?, ?>,
                                                  Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntityMapComparator<Object, Bid4WinComparator<Object>,
                                                Bid4WinEntity<?, ?>,
                                                Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>(
          Bid4WinComparator.getInstance(), Bid4WinEntityComparator.getInstanceEntity());
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.core.comparator.Bid4WinObjectMapComparator#getInstanceObjectMap()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinObjectMapComparator<Object, Bid4WinComparator<Object>,
                                           Bid4WinObject<?>, Bid4WinObjectComparator<Bid4WinObject<?>>>
         getInstanceObjectMap()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityMapComparator<Object, Bid4WinComparator<Object>,
                                           Bid4WinEntity<?, ?>,
                                           Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
         getInstanceEntityMap()
  {
    return Bid4WinEntityMapComparator.instance;
  }

  /**
   * Constructeur
   * @param keyComparator Classe de comparaison par défaut des clés de la map
   * @param entityComparator Classe de comparaison par défaut des entités de la map
   */
  public Bid4WinEntityMapComparator(KEY_COMPARATOR keyComparator, ENTITY_COMPARATOR entityComparator)
  {
    super(keyComparator, entityComparator);
  }

  /**
   * Cette méthode permet de tester, avec la possibilité qu'ils soient nuls, l'
   * équivalence ou l'identicité des maps d'entités en argument selon la valeur
   * du paramètre identical, en incluant leurs relations définies en paramètre en
   * même temps que leur profondeur
   * @param map1 Première map d'entités à comparer
   * @param map2 Deuxième map d'entités à comparer
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant si on souhaite tester l'équivalence ou l'
   * identicité des deux maps d'entités en paramètre
   * @return True si les deux maps d'entités sont considérées équivalentes ou
   * identiques, false sinon
   */
  public boolean same(Map<? extends KEY, ? extends ENTITY> map1,
                      Map<? extends KEY, ? extends ENTITY> map2,
                      Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    return this.same(map1, map2, this.getValueComparator(), nodeList, referenced, identical);
  }
  /**
   * Cette méthode permet de tester, avec la possibilité qu'ils soient nuls, l'
   * équivalence ou l'identicité des maps d'entités en argument selon la valeur
   * du paramètre identical, en incluant leurs relations définies en paramètre en
   * même temps que leur profondeur
   * @param map1 Première map d'entités à comparer
   * @param map2 Deuxième map d'entités à comparer
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param entityComparator Classe à utiliser pour comparer chaque entité de la
   * map
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant si on souhaite tester l'équivalence ou l'
   * identicité des deux maps d'entités en paramètre
   * @return True si les deux maps d'entités sont considérées équivalentes ou
   * identiques, false sinon
   */
  public boolean same(Map<? extends KEY, ? extends ENTITY> map1,
                      Map<? extends KEY, ? extends ENTITY> map2,
                      ENTITY_COMPARATOR entityComparator,
                      Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    // Pour comparer plus rapidement
    if(map1 == map2)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(map1 == null || map2 == null)
    {
      return false;
    }
    // Pour que deux maps soit similaires, il faut qu'elles aient les même tailles
    if(map1.size() != map2.size())
    {
      return false;
    }
    // Parcours la première map et recherche les entités similaires dans la deuxième
    map2 = new Bid4WinMap<KEY, ENTITY>(map2);
    for(Entry<? extends KEY, ? extends ENTITY> entry1 : map1.entrySet())
    {
      ENTITY entity2 = map2.get(entry1.getKey());
      if(entityComparator.same(entry1.getValue(), entity2, nodeList, referenced, identical))
      {
        // On diminue la taille de la map de recherche
        map2.remove(entry1.getKey());
      }
      else
      {
        // Aucune entité similaire trouvée
        return false;
      }
    }
    return true;
  }
}
