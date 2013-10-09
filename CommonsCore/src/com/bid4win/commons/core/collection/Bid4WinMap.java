package com.bid4win.commons.core.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bid4win.commons.core.renderer.Bid4WinMapRenderer;

/**
 * Cette classe est la classe de base de toute map du projet. Elle se base sur
 * une map interne dont elle utilise les m�canismes tout en red�finissant certains
 * comportements<BR>
 * <BR>
 * @param <KEY> D�finition du type des cl�s contenues dans la map<BR>
 * @param <VALUE> D�finition du type des valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinMap<KEY, VALUE> implements Map<KEY, VALUE>, Serializable
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 3572273748018352635L;
  /** Capacit� initiale par d�faut de la map interne */
  private final static int DEFAULT_INITIAL_CAPACITY = 10;

  /** Map interne sur laquelle se base la map courante */
  private Map<KEY, VALUE> internal = null;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinMap()
  {
    // Utilisation de la capacit� initiale par d�faut
    this(0);
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinMap(int initialCapacity)
  {
    // Si la capacit� initiale est inf�rieure � un, on utilisera la capacit�
    // initiale par d�faut
    super();
    this.setInternal(this.createInternal(initialCapacity < 1 ?
                                         DEFAULT_INITIAL_CAPACITY :
                                         initialCapacity));
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. La map construite aura une capacit� initiale de un
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param value Valeur � ajouter � la map
   */
  public Bid4WinMap(KEY key, VALUE value)
  {
    this(key, value, 1);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map avec pr�cision de sa capacit� initiale. Si la capacit� initiale fournie
   * est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param value Valeur � ajouter � la map
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinMap(KEY key, VALUE value, int initialCapacity)
  {
    this(initialCapacity);
    this.put(key, value);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacit� initiale la taille de la map en argument
   * @param map Map d'objets � ajouter � la map � construire
   */
  public Bid4WinMap(Map<? extends KEY, ? extends VALUE> map)
  {
    this(map, 0);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map avec pr�cision
   * de la capacit� initiale suppl�mentaire de la map par rapport � la taille de
   * celle en argument
   * @param map Map d'objets � ajouter � la map � construire
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la map par rapport
   * � la taille de celle en argument
   */
  public Bid4WinMap(Map<? extends KEY, ? extends VALUE> map, int additionalCapacity)
  {
    // Si la capacit� initiale suppl�mentaire fournie est inf�rieure � 0, on
    // n'en prendra pas compte
    this(map.size() + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.putAll(map);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilis�e comme map
   * interne, la map construite aura comme capacit� initiale la taille de la map
   * en argument et sera remplie avec les �l�ments de celle-ci
   * @param map Map d'objets � ajouter � la map � construire
   * @param useAsInternal Flag indiquant si la map en argument doit �tre utilis�e
   * comme map interne ou non
   */
  public Bid4WinMap(Map<KEY, VALUE> map, boolean useAsInternal)
  {
    this(map, useAsInternal, 0);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilis�e comme map
   * interne, la map construite aura comme capacit� initiale la taille de la map
   * en argument augment�e de la capacit� initiale suppl�mentaire choisie et sera
   * remplie avec les �l�ments de celle-ci
   * @param map Map d'objets � ajouter � la map � construire
   * @param useAsInternal Flag indiquant si la map en argument doit �tre utilis�e
   * comme map interne ou non
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la map par rapport
   * � la taille de celle en argument dans le cas o� celle-ci n'est pas utilis�e
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
   * Ajoute le couple d'objet en argument � la map interne
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
   * Ajoute la map en argument � la map interne
   * @param toBePut {@inheritDoc}
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends KEY, ? extends VALUE> toBePut)
  {
    this.getInternal().putAll(toBePut);
  }
  /**
   * Retire la valeur r�f�renc�e par la cl� en argument de la map interne
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
   * V�rifie si la map interne contient la cl� en argument
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
   * V�rifie si la map interne contient la valeur en argument
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
   * Test l'�galit� entre la map interne et l'objet en argument
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
   * V�rifie si la map interne est vide
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
   * Retourne le set de cl�s de la map interne. Attention, les actions sur celui-ci
   * se r�percuterons sur la map et inversement
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
   * sur celle-ci se r�percuterons sur la map et inversement
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
   * Retourne le set de couples cl�/valeur de la map interne. Attention, les actions
   * sur celui-ci se r�percuterons sur la map et inversement
   * @return {@inheritDoc}
   * @see java.util.Map#entrySet()
   */
  @Override
  public Bid4WinSet<Entry<KEY, VALUE>> entrySet()
  {
    return new Bid4WinSet<Entry<KEY, VALUE>>(this.getInternal().entrySet(), true);
  }
  /**
   * Retourne la valeur r�f�renc�e par la cl� en argument dans la map
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
   * Red�fini la m�thode toString() pour rendre plus lisible toute map
   * @return {@inheritDoc}
   * @see java.util.AbstractMap#toString()
   */
  @Override
  public final String toString()
  {
    return this.render().toString();
  }

  /**
   * Red�fini la m�thode toString() pour rendre plus lisible toute map
   * @return La cha�ne de caract�res repr�sentant lisiblement la map courante
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
   * @param internal Map interne � positionner
   */
  private void setInternal(Map<KEY, VALUE> internal)
  {
    this.internal = internal;
  }
  /**
   * Cette m�thode permet de cr�er une map pouvant �tre utilis�e comme map interne
   * par la map courante. Si la capacit� initiale fournie est inf�rieure � un, on
   * utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map � cr�er
   * @return La map cr��e
   */
  protected Map<KEY, VALUE> createInternal(int initialCapacity)
  {
    return new HashMap<KEY, VALUE>(initialCapacity);
  }
}