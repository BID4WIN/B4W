package com.bid4win.commons.persistence.entity.comparator;

import java.util.Collections;
import java.util.List;

import com.bid4win.commons.core.collection.Bid4WinList;
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
public class Bid4WinEntityListComparator<ENTITY extends Bid4WinEntity<?, ?>,
                                         COMPARATOR extends Bid4WinEntityComparator<ENTITY>>
       extends Bid4WinEntityCollectionComparator<ENTITY, List<? extends ENTITY>, COMPARATOR>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinEntityListComparator<Bid4WinEntity<?, ?>,
                                                   Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntityListComparator<Bid4WinEntity<?, ?>,
                                                 Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>(
          Bid4WinEntityComparator.getInstanceEntity());
  /**
   * Le comparateur est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du comparateur
   */
  public static Bid4WinEntityListComparator<Bid4WinEntity<?, ?>,
                                            Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
         getInstanceEntityList()
  {
    return Bid4WinEntityListComparator.instance;
  }

  /**
   * Constructeur
   * @param objectComparator Classe de comparaison par d�faut des objets de la collection
   */
  public Bid4WinEntityListComparator(COMPARATOR objectComparator)
  {
    super(objectComparator);
  }

  /**
   * Cette m�thode permet de tester, avec la possibilit� qu'elles soient nulles,
   * l'�quivalence ou l'identicit� des listes d'entit�s en argument selon la valeur
   * du param�tre identical, en incluant leurs relations d�finies en param�tre en
   * m�me temps que leur profondeur
   * @param list1 {@inheritDoc}
   * @param list2 {@inheritDoc}
   * @param compatator {@inheritDoc}
   * @param nodeList {@inheritDoc}
   * @param referenced {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityCollectionComparator#same(java.util.Collection, java.util.Collection, com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityComparator, com.bid4win.commons.core.collection.Bid4WinList, com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap, boolean)
   */
  @Override
  public boolean same(List<? extends ENTITY> list1, List<? extends ENTITY> list2,
                      COMPARATOR compatator, Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    // Pour comparer plus rapidement
    if(list1 == list2)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(list1 == null || list2 == null)
    {
      return false;
    }
    // Pour que deux listes soit similaires, il faut qu'elles aient les m�me tailles
    if(list1.size() != list2.size())
    {
      return false;
    }
    // Parcours la premi�re liste et recherche les entit�s similaires dans le deuxi�me
    for(int i = 0 ; i < list1.size() ; i++)
    {
      if(!compatator.same(list1.get(i), list2.get(i), nodeList, referenced, identical))
      {
        return false;
      }
    }
    return true;
  }

  /**
   *
   * TODO A COMMENTER
   * @param list TODO A COMMENTER
   */
  public void sort(List<ENTITY> list)
  {
    this.sort(list, this.getObjectComparator());
  }
  /**
   *
   * TODO A COMMENTER
   * @param list TODO A COMMENTER
   * @param compatator TODO A COMMENTER
   */
  public void sort(List<ENTITY> list, COMPARATOR compatator)
  {
    if(list == null)
    {
      return;
    }
    Collections.sort(list, compatator);
  }
}
