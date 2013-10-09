package com.bid4win.commons.core.comparator;

import java.util.Collection;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini le comparateur par d�faut utilis� entre collections<BR>
 * <BR>
 * @param <OBJECT> Definition des objets contenus dans les collections � comparer<BR>
 * @param <COLLECTION> D�finition des collections � comparer<BR>
 * @param <COMPARATOR> D�finition du comparateur utilis� pour comparer les objets
 * des collections<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinCollectionComparator<OBJECT, COLLECTION extends Collection<? extends OBJECT>,
                                         COMPARATOR extends Bid4WinComparator<OBJECT>>
       extends Bid4WinComparator<COLLECTION>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                                   Bid4WinComparator<Object>>
      instance = new Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                                 Bid4WinComparator<Object>>(
          Bid4WinComparator.getInstance());
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.comparator.Bid4WinComparator#getInstance()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinComparator<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le comparateur est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du comparateur
   */
  public static Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                            Bid4WinComparator<Object>> getInstanceCollection()
  {
    return Bid4WinCollectionComparator.instance;
  }

  /** Classe de comparaison par d�faut des objets de la collection */
  private COMPARATOR objectComparator = null;

  /**
   * Constructeur
   * @param objectComparator Classe de comparaison par d�faut des objets de la collection
   */
  public Bid4WinCollectionComparator(COMPARATOR objectComparator)
  {
    this.objectComparator = objectComparator;
  }

  /**
   * Getter de la classe de comparaison par d�faut des objets de la collection
   * @return La classe de comparaison par d�faut des objets de la collection
   */
  public COMPARATOR getObjectComparator()
  {
    return this.objectComparator;
  }
}
