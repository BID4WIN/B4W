package com.bid4win.commons.core.collection;

import java.util.Map;

/**
 * Cette classe défini une map contenant récursivement des maps de même type<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinStringRecursiveMap
       extends Bid4WinRecursiveMap<Bid4WinStringRecursiveMap, String>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 6878947812470934543L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinStringRecursiveMap()
  {
    super();
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinStringRecursiveMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. La map construite aura une capacité initiale de un
   * @param key Clé référençant la valeur à ajouter à la map
   * @param map Valeur à ajouter à la map
   */
  public Bid4WinStringRecursiveMap(String key, Bid4WinStringRecursiveMap map)
  {
    super(key, map);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map avec précision de sa capacité initiale. Si la capacité initiale fournie
   * est inférieure à un, on utilisera la capacité initiale par défaut
   * @param key Clé référençant la valeur à ajouter à la map
   * @param map Valeur à ajouter à la map
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinStringRecursiveMap(String key, Bid4WinStringRecursiveMap map, int initialCapacity)
  {
    super(key, map, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacité initiale la taille de la map en argument
   * @param map Map d'objets à ajouter à la map à construire
   */
  public Bid4WinStringRecursiveMap(Map<? extends String, ? extends Bid4WinStringRecursiveMap> map)
  {
    super(map);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map avec précision
   * de la capacité initiale supplémentaire de la map par rapport à la taille de
   * celle en argument
   * @param map Map d'objets à ajouter à la map à construire
   * @param additionalCapacity Capacité initiale supplémentaire de la map par rapport
   * à la taille de celle en argument
   */
  public Bid4WinStringRecursiveMap(Map<? extends String, ? extends Bid4WinStringRecursiveMap> map, int additionalCapacity)
  {
    super(map, additionalCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilisée comme map
   * interne, la map construite aura comme capacité initiale la taille de la map
   * en argument et sera remplie avec les éléments de celle-ci
   * @param map Map d'objets à ajouter à la map à construire
   * @param useAsInternal Flag indiquant si la map en argument doit être utilisée
   * comme map interne ou non
   */
  public Bid4WinStringRecursiveMap(Map<String, Bid4WinStringRecursiveMap> map, boolean useAsInternal)
  {
    super(map, useAsInternal);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilisée comme map
   * interne, la map construite aura comme capacité initiale la taille de la map
   * en argument augmentée de la capacité initiale supplémentaire choisie et sera
   * remplie avec les éléments de celle-ci
   * @param map Map d'objets à ajouter à la map à construire
   * @param useAsInternal Flag indiquant si la map en argument doit être utilisée
   * comme map interne ou non
   * @param additionalCapacity Capacité initiale supplémentaire de la map par rapport
   * à la taille de celle en argument dans le cas où celle-ci n'est pas utilisée
   * comme map interne
   */
  public Bid4WinStringRecursiveMap(Map<String, Bid4WinStringRecursiveMap> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }
}
