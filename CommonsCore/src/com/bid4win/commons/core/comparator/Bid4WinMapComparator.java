package com.bid4win.commons.core.comparator;

import java.util.Map;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini le comparateur par défaut utilisé entre maps<BR>
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
public class Bid4WinMapComparator<KEY, KEY_COMPARATOR extends Bid4WinComparator<KEY>,
                                  VALUE, VALUE_COMPARATOR extends Bid4WinComparator<VALUE>>
       extends Bid4WinComparator<Map<? extends KEY, ? extends VALUE>>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                            Object, Bid4WinComparator<Object>>
      instance = new Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                          Object, Bid4WinComparator<Object>>(
          Bid4WinComparator.getInstance(), Bid4WinComparator.getInstance());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.comparator.Bid4WinComparator#getInstance()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinComparator<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le comparateur est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du comparateur
   */
  public static Bid4WinMapComparator<Object, Bid4WinComparator<Object>,
                                     Object, Bid4WinComparator<Object>>
         getInstanceMap()
  {
    return Bid4WinMapComparator.instance;
  }

  /** Classe de comparaison par défaut des clés de la maps */
  private KEY_COMPARATOR keyComparator = null;
  /** Classe de comparaison par défaut des valeurs de la maps */
  private VALUE_COMPARATOR valueComparator = null;

  /**
   * Constructeur
   * @param keyComparator Classe de comparaison par défaut des clés de la map
   * @param valueComparator Classe de comparaison par défaut des valeurs de la map
   */
  public Bid4WinMapComparator(KEY_COMPARATOR keyComparator, VALUE_COMPARATOR valueComparator)
  {
    this.keyComparator = keyComparator;
    this.valueComparator = valueComparator;
  }

  /**
   * Getter de la classe de comparaison par défaut des clés de la map
   * @return La classe de comparaison par défaut des clés de la map
   */
  public KEY_COMPARATOR getKeyComparator()
  {
    return this.keyComparator;
  }
  /**
   * Getter de la classe de comparaison par défaut des valeurs de la map
   * @return La classe de comparaison par défaut des valeurs de la map
   */
  public VALUE_COMPARATOR getValueComparator()
  {
    return this.valueComparator;
  }
}
