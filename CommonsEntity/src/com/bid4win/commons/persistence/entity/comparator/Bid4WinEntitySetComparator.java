package com.bid4win.commons.persistence.entity.comparator;

import java.util.Collection;
import java.util.Set;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

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
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityCollectionComparator#getInstanceObjectCollection()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinEntityCollectionComparator<Bid4WinEntity<?, ?>, Collection<? extends Bid4WinEntity<?, ?>>,
                                                  Bid4WinEntityComparator<Bid4WinEntity<?, ?>>>
         getInstanceEntityCollection()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
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
}
