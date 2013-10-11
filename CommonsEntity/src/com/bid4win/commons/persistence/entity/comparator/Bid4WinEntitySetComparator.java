package com.bid4win.commons.persistence.entity.comparator;

import java.util.Set;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <COMPARATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntitySetComparator<ENTITY extends Bid4WinEntity<?, ?>,
                                        COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinEntityCollectionComparator<ENTITY, Set<? extends ENTITY>, COMPARATOR>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinEntitySetComparator<Bid4WinEntity<?, ?>,
                                                   Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntitySetComparator<Bid4WinEntity<?, ?>,
                                                 Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>(
          Bid4WinEntityComparator.getInstanceEntity());
  /**
   * Le comparateur est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du comparateur
   */
  public static Bid4WinEntitySetComparator<Bid4WinEntity<?, ?>,
                                           Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
         getInstanceEntitySet()
  {
    return Bid4WinEntitySetComparator.instance;
  }

  /**
   * Constructeur
   * @param objectComparator Classe de comparaison par d�faut des objets de la collection
   */
  public Bid4WinEntitySetComparator(COMPARATOR objectComparator)
  {
    super(objectComparator);
  }

  /**
   * Cette m�thode permet de tester, avec la possibilit� qu'ils soient nuls, l'
   * �quivalence ou l'identicit� des listes d'entit�s en argument selon la valeur
   * du param�tre identical, en incluant leurs relations d�finies en param�tre en
   * m�me temps que leur profondeur
   * @param set1 {@inheritDoc}
   * @param set2 {@inheritDoc}
   * @param compatator {@inheritDoc}
   * @param nodeList {@inheritDoc}
   * @param referenced {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityCollectionComparator#same(java.util.Collection, java.util.Collection, com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityComparator, com.bid4win.commons.core.collection.Bid4WinList, com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap, boolean)
   */
  @Override
  public boolean same(Set<? extends ENTITY> set1, Set<? extends ENTITY> set2,
                      COMPARATOR compatator, Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    // Pour comparer plus rapidement
    if(set1 == set2)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(set1 == null || set2 == null)
    {
      return false;
    }
    // Pour que deux sets soit similaires, il faut qu'ils aient les m�me tailles
    if(set1.size() != set2.size())
    {
      return false;
    }
    // Parcours le premier set et recherche les entit�s similaires dans le
    // deuxi�me
    set2 = new Bid4WinSet<ENTITY>(set2);
    for(ENTITY entity1 : set1)
    {
      boolean found = false;
      for(ENTITY entity2 : set2)
      {
        // On a trouv� une entit� similaire
        if(compatator.same(entity1, entity2, nodeList, referenced, identical))
        {
          found = true;
          // On diminue le set de recherche
          set2.remove(entity2);
          break;
        }
      }
      // Aucune entit� similaire n'a �t� trouv�e, les set ne le sont donc pas
      if(!found)
      {
        return false;
      }
    }
    return true;
  }
}
