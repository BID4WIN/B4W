package com.bid4win.commons.core.comparator;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini le comparateur par défaut utilisé entre maps dont les valeurs
 * sont des des objets spécifiques au projet<BR>
 * <BR>
 * @param <KEY> Definition des clés des maps à comparer<BR>
 * @param <KEY_COMPARATOR>  Définition du comparateur utilisé pour comparer les
 * clés des maps
 * @param <VALUE> Definition des valeurs des maps à comparer<BR>
 * @param <VALUE_COMPARATOR> Définition du comparateur utilisé pour comparer les
 * valeurs des maps<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectMapComparator<KEY, KEY_COMPARATOR extends Bid4WinComparator<KEY>,
                                        VALUE extends Bid4WinObject<?>,
                                        VALUE_COMPARATOR extends Bid4WinObjectComparator<VALUE>>
       extends Bid4WinMapComparator<KEY, KEY_COMPARATOR, VALUE, VALUE_COMPARATOR>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinObjectMapComparator<Object, Bid4WinComparator<Object>,
                                                  Bid4WinObject<?>, Bid4WinObjectComparator<Bid4WinObject<?>>>
      instance = new Bid4WinObjectMapComparator<Object, Bid4WinComparator<Object>,
                                                Bid4WinObject<?>, Bid4WinObjectComparator<Bid4WinObject<?>>>(
          Bid4WinComparator.getInstance(), Bid4WinObjectComparator.getInstanceObject());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.comparator.Bid4WinMapComparator#getInstanceMap()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                     Object, Bid4WinComparator<Object>>
         getInstanceMap() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
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
   * @param keyComparator Classe de comparaison par défaut des clés de la map
   * @param valueComparator Classe de comparaison par défaut des valeurs de la map
   */
  public Bid4WinObjectMapComparator(KEY_COMPARATOR keyComparator, VALUE_COMPARATOR valueComparator)
  {
    super(keyComparator, valueComparator);
  }
}
