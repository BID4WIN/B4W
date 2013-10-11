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
 * @author Emeric Fillâtre
 */
public class Bid4WinEntitySetComparator<ENTITY extends Bid4WinEntity<?, ?>,
                                        COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinEntityCollectionComparator<ENTITY, Set<? extends ENTITY>, COMPARATOR>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinEntitySetComparator<Bid4WinEntity<?, ?>,
                                                   Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntitySetComparator<Bid4WinEntity<?, ?>,
                                                 Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>(
          Bid4WinEntityComparator.getInstanceEntity());
  /**
   * Le comparateur est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
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
   * @param objectComparator Classe de comparaison par défaut des objets de la collection
   */
  public Bid4WinEntitySetComparator(COMPARATOR objectComparator)
  {
    super(objectComparator);
  }

  /**
   * Cette méthode permet de tester, avec la possibilité qu'ils soient nuls, l'
   * équivalence ou l'identicité des listes d'entités en argument selon la valeur
   * du paramètre identical, en incluant leurs relations définies en paramètre en
   * même temps que leur profondeur
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
    // Pour que deux sets soit similaires, il faut qu'ils aient les même tailles
    if(set1.size() != set2.size())
    {
      return false;
    }
    // Parcours le premier set et recherche les entités similaires dans le
    // deuxième
    set2 = new Bid4WinSet<ENTITY>(set2);
    for(ENTITY entity1 : set1)
    {
      boolean found = false;
      for(ENTITY entity2 : set2)
      {
        // On a trouvé une entité similaire
        if(compatator.same(entity1, entity2, nodeList, referenced, identical))
        {
          found = true;
          // On diminue le set de recherche
          set2.remove(entity2);
          break;
        }
      }
      // Aucune entité similaire n'a été trouvée, les set ne le sont donc pas
      if(!found)
      {
        return false;
      }
    }
    return true;
  }
}
