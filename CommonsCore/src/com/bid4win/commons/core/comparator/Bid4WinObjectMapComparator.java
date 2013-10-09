package com.bid4win.commons.core.comparator;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini le comparateur par d�faut utilis� entre maps dont les valeurs
 * sont des des objets sp�cifiques au projet<BR>
 * <BR>
 * @param <KEY> Definition des cl�s des maps � comparer<BR>
 * @param <KEY_COMPARATOR>  D�finition du comparateur utilis� pour comparer les
 * cl�s des maps
 * @param <VALUE> Definition des valeurs des maps � comparer<BR>
 * @param <VALUE_COMPARATOR> D�finition du comparateur utilis� pour comparer les
 * valeurs des maps<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectMapComparator<KEY, KEY_COMPARATOR extends Bid4WinComparator<KEY>,
                                        VALUE extends Bid4WinObject<?>,
                                        VALUE_COMPARATOR extends Bid4WinObjectComparator<VALUE>>
       extends Bid4WinMapComparator<KEY, KEY_COMPARATOR, VALUE, VALUE_COMPARATOR>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinObjectMapComparator<Object, Bid4WinComparator<Object>,
                                                  Bid4WinObject<?>, Bid4WinObjectComparator<Bid4WinObject<?>>>
      instance = new Bid4WinObjectMapComparator<Object, Bid4WinComparator<Object>,
                                                Bid4WinObject<?>, Bid4WinObjectComparator<Bid4WinObject<?>>>(
          Bid4WinComparator.getInstance(), Bid4WinObjectComparator.getInstanceObject());
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.comparator.Bid4WinMapComparator#getInstanceMap()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                     Object, Bid4WinComparator<Object>>
         getInstanceMap() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinObjectMapComparator<Object, Bid4WinComparator<Object>,
                                           Bid4WinObject<?>, Bid4WinObjectComparator<Bid4WinObject<?>>>
         getInstanceObjectMap()
  {
    return Bid4WinObjectMapComparator.instance;
  }

  /**
   * Constructeur
   * @param keyComparator Classe de comparaison par d�faut des cl�s de la map
   * @param valueComparator Classe de comparaison par d�faut des valeurs de la map
   */
  public Bid4WinObjectMapComparator(KEY_COMPARATOR keyComparator, VALUE_COMPARATOR valueComparator)
  {
    super(keyComparator, valueComparator);
  }
}
