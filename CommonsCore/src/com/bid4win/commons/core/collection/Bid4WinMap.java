package com.bid4win.commons.core.collection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.exception.RuntimeArgumentException;
import com.bid4win.commons.core.renderer.Bid4WinMapRenderer;
import com.bid4win.commons.core.security.ObjectProtection;
import com.bid4win.commons.core.security.ProtectableObject;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe est la classe de base de toute map du projet. Elle se base sur
 * une map interne dont elle utilise les mécanismes tout en redéfinissant certains
 * comportements. Elle pourra aussi être protégée contre toute modification même
 * si elle ne le sera pas par défaut<BR>
 * <BR>
 * @param <KEY> Définition du type des clés contenues dans la map<BR>
 * @param <VALUE> Définition du type des valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinMap<KEY, VALUE> extends ProtectableObject
       implements Map<KEY, VALUE>, Serializable
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
   * Constructeur utilisant la capacité initiale par défaut et la protection en
   * argument
   * @param protection Protection à utiliser pour la map
   */
  public Bid4WinMap(ObjectProtection protection)
  {
    super(protection);
    // Utilisation de la capacité initiale par défaut
    this.setInternalMap(this.createInternalMap(DEFAULT_INITIAL_CAPACITY));
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
    super(null);
    this.setInternalMap(this.createInternalMap(initialCapacity < 1 ?
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
    super(null);
    Map<KEY, VALUE> internal = map;
    if(!useAsInternal)
    {
      internal = this.createInternalMap(map.size() +
                                        (additionalCapacity < 0 ? 0 : additionalCapacity));
      internal.putAll(map);
    }
    this.setInternalMap(internal);
  }

  /**
   * Ajoute le couple d'objet en argument à la map interne
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la map est protégée contre les modifications
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public VALUE put(KEY key, VALUE value) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalMap().put(key, value);
  }
  /**
   * Ajoute la map en argument à la map interne
   * @param toBePut {@inheritDoc}
   * @throws ProtectionException Si la map est protégée contre les modifications
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends KEY, ? extends VALUE> toBePut) throws ProtectionException
  {
    this.checkProtection();
    this.getInternalMap().putAll(toBePut);
  }
  /**
   * Retire la valeur référencée par la clé en argument de la map interne
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la map est protégée contre les modifications
   * @see java.util.Map#remove(java.lang.Object)
   */
  @Override
  public VALUE remove(Object key) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalMap().remove(key);
  }
  /**
   * Vide la map interne
   * @throws ProtectionException Si la map est protégée contre les modifications
   * @see java.util.Map#clear()
   */
  @Override
  public void clear() throws ProtectionException
  {
    this.checkProtection();
    this.getInternalMap().clear();
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
    return this.getInternalMap().containsKey(key);
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
    return this.getInternalMap().containsValue(value);
  }
  /**
   * Retourne le code de hachage de la map interne
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return this.getInternalMap().hashCode();
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
    return this.getInternalMap().equals(toBeCompared);
  }
  /**
   * Vérifie si la map interne est vide
   * @return {@inheritDoc}
   * @see java.util.Map#isEmpty()
   */
  @Override
  public boolean isEmpty()
  {
    return this.getInternalMap().isEmpty();
  }
  /**
   * Retourne la taille de la map interne
   * @return {@inheritDoc}
   * @see java.util.Map#size()
   */
  @Override
  public int size()
  {
    return this.getInternalMap().size();
  }

  /**
   * Retourne le set de clés de la map interne. Attention, les actions sur celui-ci
   * se répercuterons sur la map et inversement et il sera protégé identiquement
   * à la map dont il est issu
   * @return {@inheritDoc}
   * @see java.util.Map#keySet()
   */
  @Override
  public Bid4WinSet<KEY> keySet()
  {
    Bid4WinSet<KEY> keySet = new Bid4WinSet<KEY>(
        this.getInternalMap().keySet(), true);
    keySet.protect(this.getProtection());
    return keySet;
  }
  /**
   * Retourne le set de clés de la map interne. Attention, ce set sera automatiquement
   * protégé contre toute modification.
   * @return Le set de clés de la map interne
   */
  public Bid4WinSet<KEY> keySetProtected()
  {
    Bid4WinSet<KEY> keySet = new Bid4WinSet<KEY>(
        this.getInternalMap().keySet(), true);
    keySet.protectFromNothing();
    return keySet;
  }
  /**
   * Retourne la collection de valeurs de la map interne. Attention, les actions
   * sur celle-ci se répercuterons sur la map et inversement et elle sera protégée
   * identiquement à la map dont elle est issue
   * @return {@inheritDoc}
   * @see java.util.Map#values()
   */
  @Override
  public Bid4WinCollection<VALUE> values()
  {
    Bid4WinCollection<VALUE> collection =  new Bid4WinCollection<VALUE>(
        this.getInternalMap().values(), true);
    collection.protect(this.getProtection());
    return collection;
  }
  /**
   * Retourne la collection de valeurs de la map interne. Attention, cette collection
   * sera automatiquement protégée contre toute modification.
   * @return La collection de valeurs de la map interne
   */
  public Bid4WinCollection<VALUE> valuesProtected()
  {
    Bid4WinCollection<VALUE> collection =  new Bid4WinCollection<VALUE>(
        this.getInternalMap().values(), true);
    collection.protectFromNothing();
    return collection;
  }
  /**
   * Retourne le set de couples clé/valeur de la map interne. Attention, les actions
   * sur celui-ci se répercuterons sur la map et inversement et il sera protégé
   * identiquement à la map dont il est issu
   * @return {@inheritDoc}
   * @see java.util.Map#entrySet()
   */
  @Override
  public Bid4WinEntrySet<KEY, VALUE> entrySet()
  {
    Bid4WinEntrySet<KEY, VALUE> set = new Bid4WinEntrySet<KEY, VALUE>(
        this.getInternalMap().entrySet());
    set.protect(this.getProtection());
    return set;
  }
  /**
   * Retourne le set de couples clé/valeur de la map interne. Attention, ce set
   * sera automatiquement protégé contre toute modification.
   * @return Un set des entrées de la map courante
   */
  public Bid4WinEntrySet<KEY, VALUE> entrySetProtected()
  {
    Bid4WinEntrySet<KEY, VALUE> set = new Bid4WinEntrySet<KEY, VALUE>(
        this.getInternalMap().entrySet());
    set.protectFromNothing();
    return set;
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
    return this.getInternalMap().get(key);
  }

  /**
   * Cette méthode permet de créer une nouvelle instance de la classe qui sera
   * remplie avec les éléments de la map en argument
   * @param map Map avec les éléments de laquelle remplir la nouvelle instance
   * @return Une nouvelle instance de la classe qui sera remplie avec les éléments
   * de la map en argument
   * @throws RuntimeArgumentException Si la construction de la nouvelle instance
   * échoue (par de constructeur basé sur une map par exemple)
   */
  @SuppressWarnings("unchecked")
  public Bid4WinMap<KEY, VALUE> newInstance(Map<? extends KEY, ? extends VALUE> map)
         throws RuntimeArgumentException
  {
    try
    {
      return this.getClass().getDeclaredConstructor(Map.class).newInstance(map);
    }
    catch(Exception ex)
    {
      throw new RuntimeArgumentException(ex);
    }
  }
  /**
   * Défini le clonage d'une map. Cette nouvelle map sera remplie des mêmes entrées
   * que la map clonée mais leurs evolutions seront décorrélées
   * @return {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  @Override
  public Bid4WinMap<KEY, VALUE> clone()
  {
    return this.clone(false);
  }
  /**
   * Défini le clonage d'une collection. Si le clonage est dit immuable, la modification
   * directe de la nouvelle collection ne sera pas permise et elle suivra les modifications
   * de la collection clonée. Dans le cas contraire, elle sera remplie des mêmes
   * elements que la collection clonée mais leurs evolutions seront décorrélées
   * @param immutable Indique si le clonage est dit immuable ou non
   * @return Le clone de la collection courante
   */
  @SuppressWarnings("unchecked")
  public Bid4WinMap<KEY, VALUE> clone(boolean immutable)
  {
    try
    {
      if(!immutable)
      {
        return this.getClass().getDeclaredConstructor(Map.class)
            .newInstance(this.getInternalMap());
      }
      Bid4WinMap<KEY, VALUE> clone = this.getClass().getDeclaredConstructor(Map.class,
                                                                            boolean.class)
          .newInstance(this.getInternalMap(), true);
      clone.protectFromNothing();
      return clone;
    }
    catch(Throwable ex)
    {
      throw new Bid4WinRuntimeException(ex);
    }
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
  private Map<KEY, VALUE> getInternalMap()
  {
    return this.internal;
  }
  /**
   * Setter de la map interne sur laquelle doit se baser la map courante
   * @param internal Map interne à positionner
   */
  private void setInternalMap(Map<KEY, VALUE> internal)
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
  protected Map<KEY, VALUE> createInternalMap(int initialCapacity)
  {
    return new HashMap<KEY, VALUE>(initialCapacity);
  }
  /**
   * Cette méthode permet de récupérer la map interne sur laquelle se base la map
   * courante avec vérification de la protection de cette dernière
   * @return La map interne sur laquelle se base la collection courante
   * @throws ProtectionException Si la map courante est protégée
   */
  public final Map<KEY, VALUE> getInternal() throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalMap();
  }

  //*************************************************************************//
  //***************** Définition du set d'entrées d'une map *****************//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <KEY> TODO A COMMENTER<BR>
   * @param <VALUE> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Bid4WinEntrySet<KEY, VALUE> extends Bid4WinSet<Entry<KEY, VALUE>>
  {
    /** TODO A COMMENTER */
    private static final long serialVersionUID = 3471062947758176198L;

    /** Set interne sur lequel se base le set courant */
    private Set<Entry<KEY, VALUE>> internal;

    /**
     *
     * TODO A COMMENTER
     * @param internal TODO A COMMENTER
     */
    protected Bid4WinEntrySet(Set<Entry<KEY, VALUE>> internal)
    {
      super(internal, true);
    }

    /**
     *
     * TODO A COMMENTER
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.collection.Bid4WinCollection#iterator()
     */
    @Override
    public Bid4WinEntryIterator<KEY, VALUE> iterator()
    {
      Bid4WinEntryIterator<KEY, VALUE> iterator = new Bid4WinEntryIterator<KEY, VALUE>(
          this.getInternalSet().iterator());
      iterator.protect(this.getProtection());
      return iterator;
    }
    /**
     *
     * TODO A COMMENTER
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.collection.Bid4WinCollection#iteratorProtected()
     */
    @Override
    public Bid4WinEntryIterator<KEY, VALUE> iteratorProtected()
    {
      Bid4WinEntryIterator<KEY, VALUE> iterator = new Bid4WinEntryIterator<KEY, VALUE>(
          this.getInternalSet().iterator());
      iterator.protectFromNothing();
      return iterator;
    }

    /**
     *
     * TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    private Set<Entry<KEY, VALUE>> getInternalSet()
    {
      return this.internal;
    }
    /**
     *
     * TODO A COMMENTER
     * @param internal TODO A COMMENTER
     */
    private void setInternalSet(Set<Entry<KEY, VALUE>> internal)
    {
      this.internal = internal;
    }
    /**
     *
     * TODO A COMMENTER
     * @param internal {@inheritDoc}
     * @throws ProtectionException {@inheritDoc}
     * @see com.bid4win.commons.core.collection.Bid4WinCollection#setInternalCollection(java.util.Collection)
     */
    @Override
    protected void setInternalCollection(Set<Entry<KEY, VALUE>> internal) throws ProtectionException
    {
      super.setInternalCollection(internal);
      this.setInternalSet(internal);
    }
  }
  //*************************************************************************//
  //************* Définition de l'itérateur d'entrées d'une map *************//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <KEY> TODO A COMMENTER<BR>
   * @param <VALUE> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Bid4WinEntryIterator<KEY, VALUE> extends Bid4WinIterator<Entry<KEY, VALUE>>
  {
    /**
     *
     * TODO A COMMENTER
     * @param internal TODO A COMMENTER
     */
    protected Bid4WinEntryIterator(Iterator<Entry<KEY, VALUE>> internal)
    {
      super(internal);
    }
    /**
     *
     * TODO A COMMENTER
     * sera protégé identiquement à l'itérateur dont il est issu
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.collection.Bid4WinIterator#next()
     */
    @Override
    public Bid4WinEntry<KEY, VALUE> next()
    {
      return new Bid4WinEntry<KEY, VALUE>(super.next(), this.getProtection());
    }
  }
  //*************************************************************************//
  //******************* Définition de l'entrée d'une map ********************//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <KEY> TODO A COMMENTER<BR>
   * @param <VALUE> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Bid4WinEntry<KEY, VALUE> extends ProtectableObject implements Entry<KEY, VALUE>
  {
    /** TODO A COMMENTER */
    private Entry<KEY, VALUE> internal = null;

    /**
     *
     * TODO A COMMENTER
     * @param internal TODO A COMMENTER
     * @param protection TODO A COMMENTER
     */
    protected Bid4WinEntry(Entry<KEY, VALUE> internal, ObjectProtection protection)
    {
      super(protection);
      this.setInternal(internal);
    }

    /**
     *
     * TODO A COMMENTER
     * @return {@inheritDoc}
     * @see java.util.Map.Entry#getKey()
     */
    @Override
    public KEY getKey()
    {
      return this.getInternal().getKey();
    }
    /**
     *
     * TODO A COMMENTER
     * @return {@inheritDoc}
     * @see java.util.Map.Entry#getValue()
     */
    @Override
    public VALUE getValue()
    {
      return this.getInternal().getValue();
    }
    /**
     *
     * TODO A COMMENTER
     * @param value {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ProtectionException Si l'entrée est protégée contre les modifications
     * @see java.util.Map.Entry#setValue(java.lang.Object)
     */
    @Override
    public VALUE setValue(VALUE value) throws ProtectionException
    {
      this.checkProtection();
      return this.getInternal().setValue(value);
    }

    /**
     * Getter de l'entrée interne sur laquelle se base l'entrée courante
     * @return L'entrée interne sur laquelle se base l'entrée courante
     */
    private Entry<KEY, VALUE> getInternal()
    {
      return this.internal;
    }
    /**
     * Setter de l'entrée interne sur laquelle doit se baser l'entrée courante
     * @param internal Entrée interne à positionner
     */
    private void setInternal(Entry<KEY, VALUE> internal)
    {
      this.internal = internal;
    }
  }
}