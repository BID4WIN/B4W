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
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityMapComparator<KEY, KEY_COMPARATOR extends Bid4WinComparator<KEY>,
                                        ENTITY extends Bid4WinEntity<?, ?>,
                                        ENTITY_COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinObjectMapComparator<KEY, KEY_COMPARATOR, ENTITY, ENTITY_COMPARATOR>
{
  /** C'est l'instance utilis�e comme singleton */
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
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
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
   * @param keyComparator Classe de comparaison par d�faut des cl�s de la map
   * @param entityComparator Classe de comparaison par d�faut des entit�s de la map
   */
  public Bid4WinEntityMapComparator(KEY_COMPARATOR keyComparator, ENTITY_COMPARATOR entityComparator)
  {
    super(keyComparator, entityComparator);
  }

  /**
   * Cette m�thode permet de tester, avec la possibilit� qu'ils soient nuls, l'
   * �quivalence ou l'identicit� des maps d'entit�s en argument selon la valeur
   * du param�tre identical, en incluant leurs relations d�finies en param�tre en
   * m�me temps que leur profondeur
   * @param map1 Premi�re map d'entit�s � comparer
   * @param map2 Deuxi�me map d'entit�s � comparer
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant si on souhaite tester l'�quivalence ou l'
   * identicit� des deux maps d'entit�s en param�tre
   * @return True si les deux maps d'entit�s sont consid�r�es �quivalentes ou
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
   * Cette m�thode permet de tester, avec la possibilit� qu'ils soient nuls, l'
   * �quivalence ou l'identicit� des maps d'entit�s en argument selon la valeur
   * du param�tre identical, en incluant leurs relations d�finies en param�tre en
   * m�me temps que leur profondeur
   * @param map1 Premi�re map d'entit�s � comparer
   * @param map2 Deuxi�me map d'entit�s � comparer
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param entityComparator Classe � utiliser pour comparer chaque entit� de la
   * map
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant si on souhaite tester l'�quivalence ou l'
   * identicit� des deux maps d'entit�s en param�tre
   * @return True si les deux maps d'entit�s sont consid�r�es �quivalentes ou
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
    // Pour que deux maps soit similaires, il faut qu'elles aient les m�me tailles
    if(map1.size() != map2.size())
    {
      return false;
    }
    // Parcours la premi�re map et recherche les entit�s similaires dans la deuxi�me
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
        // Aucune entit� similaire trouv�e
        return false;
      }
    }
    return true;
  }
}
