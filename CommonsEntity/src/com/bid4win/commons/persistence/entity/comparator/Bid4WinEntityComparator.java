package com.bid4win.commons.persistence.entity.comparator;

import org.hibernate.proxy.HibernateProxy;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.Bid4WinEntity.InternalEntityComparator;
import com.bid4win.commons.persistence.entity.collection.Bid4WinMatchReferenceMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityComparator<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinObjectComparator<ENTITY>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinEntityComparator<Bid4WinEntity<?, ?>>
      instance = new Bid4WinEntityComparator<Bid4WinEntity<?, ?>>();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.core.comparator.Bid4WinObjectComparator#getInstanceObject()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinObjectComparator<Bid4WinObject<?>> getInstanceObject()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityComparator<Bid4WinEntity<?, ?>> getInstanceEntity()
  {
    return Bid4WinEntityComparator.instance;
  }

  /** TODO A COMMENTER */
  private InternalEntityComparator internalComparator = new InternalEntityComparator();

  /**
   * Cette méthode est finale car il ne peut exister qu'un test d'égalité entre
   * deux entités
   * @param entity1 {@inheritDoc}
   * @param entity2 {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.comparator.Bid4WinObjectComparator#equalsInternal(com.bid4win.commons.core.Bid4WinObject, com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected final boolean equalsInternal(ENTITY entity1, ENTITY entity2)
  {
    return super.equalsInternal(entity1, entity2);
  }

  /**
   * Cette méthode permet de tester, avec la possibilité qu'ils soient nuls, l'
   * équivalence ou l'identicité des entités en argument selon la valeur du paramètre
   * identical, en incluant les relations définies en paramètre en même temps que
   * leur profondeur. Cette méthode se basera sur la méthode sameInternal() de la
   * première entité en argument
   * @param entity1 Première entité à comparer
   * @param entity2 Deuxième entité à comparer
   * @param nodeList Liste de noeuds de relations participants à la comparaison
   * @param referenced Map de résultats de comparaisons entre entités déjà référencés
   * @param identical Flag indiquant si on souhaite tester l'équivalence ou l'
   * identicité des deux entités en paramètre
   * @return True si les deux entités sont considérées équivalentes ou identiques,
   * false sinon
   */
  public boolean same(ENTITY entity1, ENTITY entity2, Bid4WinList<Bid4WinRelationNode> nodeList,
                      Bid4WinMatchReferenceMap referenced, boolean identical)
  {
    // Pour comparer plus rapidement
    if(entity1 == entity2)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(entity1 == null || entity2 == null)
    {
      return false;
    }
    // Les entités à comparer ne sont pas de la même classe (Attention, elles
    // peuvent être encapsulée dans des proxies
    Class<?> entityClass1 = entity1.getClass();
    Class<?> entityClass2 = entity2.getClass();
    if(!(entityClass1.equals(entityClass2)))
    {
      Bid4WinSet<Class<?>> interfaces = new Bid4WinSet<Class<?>>(entityClass1.getInterfaces());
      if(interfaces.contains(HibernateProxy.class))
      {
        entityClass1 = entityClass1.getSuperclass();
      }
      interfaces = new Bid4WinSet<Class<?>>(entityClass2.getInterfaces());
      if(interfaces.contains(HibernateProxy.class))
      {
        entityClass2 = entityClass2.getSuperclass();
      }
      if(!(entityClass1.equals(entityClass2)))
      {
        return false;
      }
    }
    // Vérifie l'équivalence interne des deux entités
    return this.getInternalComparator().sameInternal(
        entity1, entity2, nodeList, referenced, identical);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.comparator.Bid4WinObjectComparator#getInternalComparator()
   */
  @Override
  protected InternalEntityComparator getInternalComparator()
  {
    return this.internalComparator;
  }
}
