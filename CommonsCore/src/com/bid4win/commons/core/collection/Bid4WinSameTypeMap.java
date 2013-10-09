package com.bid4win.commons.core.collection;

import java.util.Map;

/**
 * Cette classe est la classe de base de toute map du projet dont les types des
 * clé et des valeurs sont identiques<BR>
 * <BR>
 * @param <TYPE> Définition du type des clés et valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinSameTypeMap<TYPE> extends Bid4WinMap<TYPE, TYPE>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 8576366934273041760L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinSameTypeMap()
  {
    super();
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinSameTypeMap(int initialCapacity)
  {
    // Si la capacité initiale est inférieure à un, on utilisera la capacité
    // initiale par défaut
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. La map construite aura une capacité initiale de un
   * @param key Clé référençant la valeur à ajouter à la map
   * @param value Valeur à ajouter à la map
   */
  public Bid4WinSameTypeMap(TYPE key, TYPE value)
  {
    super(key, value);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map avec précision de sa capacité initiale. Si la capacité initiale fournie
   * est inférieure à un, on utilisera la capacité initiale par défaut
   * @param key Clé référençant la valeur à ajouter à la map
   * @param value Valeur à ajouter à la map
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinSameTypeMap(TYPE key, TYPE value, int initialCapacity)
  {
    super(key, value, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacité initiale la taille de la map en argument
   * @param map Map d'objets à ajouter à la map à construire
   */
  public Bid4WinSameTypeMap(Map<? extends TYPE, ? extends TYPE> map)
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
  public Bid4WinSameTypeMap(Map<? extends TYPE, ? extends TYPE> map, int additionalCapacity)
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
  public Bid4WinSameTypeMap(Map<TYPE, TYPE> map, boolean useAsInternal)
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
  public Bid4WinSameTypeMap(Map<TYPE, TYPE> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la map. La map
   * construite aura comme capacité initiale la taille de la première dimension
   * du tableau en argument et recherchera les couples clé/valeur suivant la deuxième
   * @param keysAndValues Tableau de couples clé/valeur à ajouter à la map à construire
   */
  public Bid4WinSameTypeMap(TYPE[][] keysAndValues)
  {
    super(keysAndValues.length);
    this.putAll(keysAndValues);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la map avec précision
   * de la capacité initiale supplémentaire de la map par rapport à la taille de
   * la première dimension du tableau  en argument et recherchera les couples
   * clé/valeur suivant la deuxième
   * @param keysAndValues Tableau de couples clé/valeur à ajouter à la map à construire
   * @param additionalCapacity Capacité initiale supplémentaire de la map par rapport
   * à la taille de la première dimension du tableau en argument
   */
  public Bid4WinSameTypeMap(TYPE[][] keysAndValues, int additionalCapacity)
  {
    super(keysAndValues.length + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.putAll(keysAndValues);
  }

  /**
   * Cette méthode permet d'ajouter tableau en argument pour remplir la map en
   * recherchant les couples clé/valeur suivant la deuxième dimension du tableau
   * @param keysAndValues Tableau de couples clé/valeur à ajouter à la map à construire
   */
  public void putAll(TYPE[][] keysAndValues)
  {
    for(int i = 0 ; i < keysAndValues.length ; i++)
    {
      this.put(keysAndValues[i][0], keysAndValues[i][1]);
    }
  }
}
