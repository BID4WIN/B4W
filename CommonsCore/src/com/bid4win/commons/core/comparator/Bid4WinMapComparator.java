package com.bid4win.commons.core.comparator;

import java.util.Map;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini le comparateur par d�faut utilis� entre maps<BR>
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
public class Bid4WinMapComparator<KEY, KEY_COMPARATOR extends Bid4WinComparator<KEY>,
                                  VALUE, VALUE_COMPARATOR extends Bid4WinComparator<VALUE>>
       extends Bid4WinComparator<Map<? extends KEY, ? extends VALUE>>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                            Object, Bid4WinComparator<Object>>
      instance = new Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                          Object, Bid4WinComparator<Object>>(
          Bid4WinComparator.getInstance(), Bid4WinComparator.getInstance());
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
  public static Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                     Object, Bid4WinComparator<Object>>
         getInstanceMap()
  {
    return Bid4WinMapComparator.instance;
  }

  /** Classe de comparaison par d�faut des cl�s de la maps */
  private KEY_COMPARATOR keyComparator = null;
  /** Classe de comparaison par d�faut des valeurs de la maps */
  private VALUE_COMPARATOR valueComparator = null;

  /**
   * Constructeur
   * @param keyComparator Classe de comparaison par d�faut des cl�s de la map
   * @param valueComparator Classe de comparaison par d�faut des valeurs de la map
   */
  public Bid4WinMapComparator(KEY_COMPARATOR keyComparator, VALUE_COMPARATOR valueComparator)
  {
    this.keyComparator = keyComparator;
    this.valueComparator = valueComparator;
  }

  /**
   * Getter de la classe de comparaison par d�faut des cl�s de la map
   * @return La classe de comparaison par d�faut des cl�s de la map
   */
  public KEY_COMPARATOR getKeyComparator()
  {
    return this.keyComparator;
  }
  /**
   * Getter de la classe de comparaison par d�faut des valeurs de la map
   * @return La classe de comparaison par d�faut des valeurs de la map
   */
  public VALUE_COMPARATOR getValueComparator()
  {
    return this.valueComparator;
  }
}
