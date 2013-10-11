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
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityComparator<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinObjectComparator<ENTITY>
{
  /** C'est l'instance utilis�e comme singleton */
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
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityComparator<Bid4WinEntity<?, ?>> getInstanceEntity()
  {
    return Bid4WinEntityComparator.instance;
  }

  /** TODO A COMMENTER */
  private InternalEntityComparator internalComparator = new InternalEntityComparator();

  /**
   * Cette m�thode est finale car il ne peut exister qu'un test d'�galit� entre
   * deux entit�s
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
   * Cette m�thode permet de tester, avec la possibilit� qu'ils soient nuls, l'
   * �quivalence ou l'identicit� des entit�s en argument selon la valeur du param�tre
   * identical, en incluant les relations d�finies en param�tre en m�me temps que
   * leur profondeur. Cette m�thode se basera sur la m�thode sameInternal() de la
   * premi�re entit� en argument
   * @param entity1 Premi�re entit� � comparer
   * @param entity2 Deuxi�me entit� � comparer
   * @param nodeList Liste de noeuds de relations participants � la comparaison
   * @param referenced Map de r�sultats de comparaisons entre entit�s d�j� r�f�renc�s
   * @param identical Flag indiquant si on souhaite tester l'�quivalence ou l'
   * identicit� des deux entit�s en param�tre
   * @return True si les deux entit�s sont consid�r�es �quivalentes ou identiques,
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
    // Les entit�s � comparer ne sont pas de la m�me classe (Attention, elles
    // peuvent �tre encapsul�e dans des proxies
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
    // V�rifie l'�quivalence interne des deux entit�s
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
