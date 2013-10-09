package com.bid4win.commons.core.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bid4win.commons.core.renderer.Bid4WinMapRenderer;

/**
 * Cette classe est la classe de base de toute map du projet. Elle se base sur
 * une map interne dont elle utilise les mécanismes tout en redéfinissant certains
 * comportements<BR>
 * <BR>
 * @param <KEY> Définition du type des clés contenues dans la map<BR>
 * @param <VALUE> Définition du type des valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinMap<KEY, VALUE> implements Map<KEY, VALUE>, Serializable
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 3572273748018352635L;
  /** Capacité initiale par défaut de la map interne */
  private final static int DEFAULT_INITIAL_CAPACITY = 10;

  /** Map interne sur laquelle se base la map courante */
  private Map<KEY, VALUE> internal = null;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinMap()
  {
    // Utilisation de la capacité initiale par défaut
    this(0);
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinMap(int initialCapacity)
  {
    // Si la capacité initiale est inférieure à un, on utilisera la capacité
    // initiale par défaut
    super();
    this.setInternal(this.createInternal(initialCapacity < 1 ?
                                         DEFAULT_INITIAL_CAPACITY :
                                         initialCapacity));
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. La map construite aura une capacité initiale de un
   * @param key Clé référençant la valeur à ajouter à la map
   * @param value Valeur à ajouter à la map
   */
  public Bid4WinMap(KEY key, VALUE value)
  {
    this(key, value, 1);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map avec précision de sa capacité initiale. Si la capacité initiale fournie
   * est inférieure à un, on utilisera la capacité initiale par défaut
   * @param key Clé référençant la valeur à ajouter à la map
   * @param value Valeur à ajouter à la map
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinMap(KEY key, VALUE value, int initialCapacity)
  {
    this(initialCapacity);
    this.put(key, value);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacité initiale la taille de la map en argument
   * @param map Map d'objets à ajouter à la map à construire
   */
  public Bid4WinMap(Map<? extends KEY, ? extends VALUE> map)
  {
    this(map, 0);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map avec précision
   * de la capacité initiale supplémentaire de la map par rapport à la taille de
   * celle en argument
   * @param map Map d'objets à ajouter à la map à construire
   * @param additionalCapacity Capacité initiale supplémentaire de la map par rapport
   * à la taille de celle en argument
   */
  public Bid4WinMap(Map<? extends KEY, ? extends VALUE> map, int additionalCapacity)
  {
    // Si la capacité initiale supplémentaire fournie est inférieure à 0, on
    // n'en prendra pas compte
    this(map.size() + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.putAll(map);
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
  public Bid4WinMap(Map<KEY, VALUE> map, boolean useAsInternal)
  {
    this(map, useAsInternal, 0);
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
  public Bid4WinMap(Map<KEY, VALUE> map, boolean useAsInternal, int additionalCapacity)
  {
    super();
    Map<KEY, VALUE> internal = map;
    if(!useAsInternal)
    {
      internal = this.createInternal(map.size() + (additionalCapacity < 0 ? 0 : additionalCapacity));
      internal.putAll(map);
    }
    this.setInternal(internal);
  }

  /**
   * Ajoute le couple d'objet en argument à la map interne
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public VALUE put(KEY key, VALUE value)
  {
    return this.getInternal().put(key, value);
  }
  /**
   * Ajoute la map en argument à la map interne
   * @param toBePut {@inheritDoc}
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends KEY, ? extends VALUE> toBePut)
  {
    this.getInternal().putAll(toBePut);
  }
  /**
   * Retire la valeur référencée par la clé en argument de la map interne
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Map#remove(java.lang.Object)
   */
  @Override
  public VALUE remove(Object key)
  {
    return this.getInternal().remove(key);
  }
  /**
   * Vide la map interne
   * @see java.util.Map#clear()
   */
  @Override
  public void clear()
  {
    this.getInternal().clear();
  }
  /**
   * Vérifie si la map interne contient la clé en argument
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  @Override
  public boolean containsKey(Object key)
  {
    return this.getInternal().containsKey(key);
  }
  /**
   * Vérifie si la map interne contient la valeur en argument
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  @Override
  public boolean containsValue(Object value)
  {
    return this.getInternal().containsValue(value);
  }
  /**
   * Retourne le code de hachage de la map interne
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return this.getInternal().hashCode();
  }
  /**
   * Test l'égalité entre la map interne et l'objet en argument
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object toBeCompared)
  {
    return this.getInternal().equals(toBeCompared);
  }
  /**
   * Vérifie si la map interne est vide
   * @return {@inheritDoc}
   * @see java.util.Map#isEmpty()
   */
  @Override
  public boolean isEmpty()
  {
    return this.getInternal().isEmpty();
  }
  /**
   * Retourne la taille de la map interne
   * @return {@inheritDoc}
   * @see java.util.Map#size()
   */
  @Override
  public int size()
  {
    return this.getInternal().size();
  }
  /**
   * Retourne le set de clés de la map interne. Attention, les actions sur celui-ci
   * se répercuterons sur la map et inversement
   * @return {@inheritDoc}
   * @see java.util.Map#keySet()
   */
  @Override
  public Bid4WinSet<KEY> keySet()
  {
    return new Bid4WinSet<KEY>(this.getInternal().keySet(), true);
  }
  /**
   * Retourne la collection de valeurs de la map interne. Attention, les actions
   * sur celle-ci se répercuterons sur la map et inversement
   * @return {@inheritDoc}
   * @see java.util.Map#values()
   */
  @Override
  public Collection<VALUE> values()
  {
    // TODO voir comment utiliser une collection bid4win
    return this.getInternal().values();
  }
  /**
   * Retourne le set de couples clé/valeur de la map interne. Attention, les actions
   * sur celui-ci se répercuterons sur la map et inversement
   * @return {@inheritDoc}
   * @see java.util.Map#entrySet()
   */
  @Override
  public Bid4WinSet<Entry<KEY, VALUE>> entrySet()
  {
    return new Bid4WinSet<Entry<KEY, VALUE>>(this.getInternal().entrySet(), true);
  }
  /**
   * Retourne la valeur référencée par la clé en argument dans la map
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Map#get(java.lang.Object)
   */
  @Override
  public VALUE get(Object key)
  {
    return this.getInternal().get(key);
  }

  /**
   * Redéfini la méthode toString() pour rendre plus lisible toute map
   * @return {@inheritDoc}
   * @see java.util.AbstractMap#toString()
   */
  @Override
  public final String toString()
  {
    return this.render().toString();
  }

  /**
   * Redéfini la méthode toString() pour rendre plus lisible toute map
   * @return La chaîne de caractères représentant lisiblement la map courante
   */
  public StringBuffer render()
  {
    return Bid4WinMapRenderer.getInstanceMap().render(this);
  }

  /**
   * Getter de la map interne sur laquelle se base la map courante
   * @return La map interne sur laquelle se base la map courante
   */
  public Map<KEY, VALUE> getInternal()
  {
    return this.internal;
  }
  /**
   * Setter de la map interne sur laquelle doit se baser la map courante
   * @param internal Map interne à positionner
   */
  private void setInternal(Map<KEY, VALUE> internal)
  {
    this.internal = internal;
  }
  /**
   * Cette méthode permet de créer une map pouvant être utilisée comme map interne
   * par la map courante. Si la capacité initiale fournie est inférieure à un, on
   * utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map à créer
   * @return La map créée
   */
  protected Map<KEY, VALUE> createInternal(int initialCapacity)
  {
    return new HashMap<KEY, VALUE>(initialCapacity);
  }
}