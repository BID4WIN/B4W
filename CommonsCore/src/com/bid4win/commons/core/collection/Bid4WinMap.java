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
 * une map interne dont elle utilise les m�canismes tout en red�finissant certains
 * comportements. Elle pourra aussi �tre prot�g�e contre toute modification m�me
 * si elle ne le sera pas par d�faut<BR>
 * <BR>
 * @param <KEY> D�finition du type des cl�s contenues dans la map<BR>
 * @param <VALUE> D�finition du type des valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinMap<KEY, VALUE> extends ProtectableObject
       implements Map<KEY, VALUE>, Serializable
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
   * Constructeur utilisant la capacit� initiale par d�faut et la protection en
   * argument
   * @param protection Protection � utiliser pour la map
   */
  public Bid4WinMap(ObjectProtection protection)
  {
    super(protection);
    // Utilisation de la capacit� initiale par d�faut
    this.setInternalMap(this.createInternalMap(DEFAULT_INITIAL_CAPACITY));
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
    super(null);
    this.setInternalMap(this.createInternalMap(initialCapacity < 1 ?
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
   * Ajoute le couple d'objet en argument � la map interne
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la map est prot�g�e contre les modifications
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public VALUE put(KEY key, VALUE value) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalMap().put(key, value);
  }
  /**
   * Ajoute la map en argument � la map interne
   * @param toBePut {@inheritDoc}
   * @throws ProtectionException Si la map est prot�g�e contre les modifications
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends KEY, ? extends VALUE> toBePut) throws ProtectionException
  {
    this.checkProtection();
    this.getInternalMap().putAll(toBePut);
  }
  /**
   * Retire la valeur r�f�renc�e par la cl� en argument de la map interne
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la map est prot�g�e contre les modifications
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
   * @throws ProtectionException Si la map est prot�g�e contre les modifications
   * @see java.util.Map#clear()
   */
  @Override
  public void clear() throws ProtectionException
  {
    this.checkProtection();
    this.getInternalMap().clear();
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
    return this.getInternalMap().containsKey(key);
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
   * Test l'�galit� entre la map interne et l'objet en argument
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
   * V�rifie si la map interne est vide
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
   * Retourne le set de cl�s de la map interne. Attention, les actions sur celui-ci
   * se r�percuterons sur la map et inversement et il sera prot�g� identiquement
   * � la map dont il est issu
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
   * Retourne le set de cl�s de la map interne. Attention, ce set sera automatiquement
   * prot�g� contre toute modification.
   * @return Le set de cl�s de la map interne
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
   * sur celle-ci se r�percuterons sur la map et inversement et elle sera prot�g�e
   * identiquement � la map dont elle est issue
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
   * sera automatiquement prot�g�e contre toute modification.
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
   * Retourne le set de couples cl�/valeur de la map interne. Attention, les actions
   * sur celui-ci se r�percuterons sur la map et inversement et il sera prot�g�
   * identiquement � la map dont il est issu
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
   * Retourne le set de couples cl�/valeur de la map interne. Attention, ce set
   * sera automatiquement prot�g� contre toute modification.
   * @return Un set des entr�es de la map courante
   */
  public Bid4WinEntrySet<KEY, VALUE> entrySetProtected()
  {
    Bid4WinEntrySet<KEY, VALUE> set = new Bid4WinEntrySet<KEY, VALUE>(
        this.getInternalMap().entrySet());
    set.protectFromNothing();
    return set;
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
    return this.getInternalMap().get(key);
  }

  /**
   * Cette m�thode permet de cr�er une nouvelle instance de la classe qui sera
   * remplie avec les �l�ments de la map en argument
   * @param map Map avec les �l�ments de laquelle remplir la nouvelle instance
   * @return Une nouvelle instance de la classe qui sera remplie avec les �l�ments
   * de la map en argument
   * @throws RuntimeArgumentException Si la construction de la nouvelle instance
   * �choue (par de constructeur bas� sur une map par exemple)
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
   * D�fini le clonage d'une map. Cette nouvelle map sera remplie des m�mes entr�es
   * que la map clon�e mais leurs evolutions seront d�corr�l�es
   * @return {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  @Override
  public Bid4WinMap<KEY, VALUE> clone()
  {
    return this.clone(false);
  }
  /**
   * D�fini le clonage d'une collection. Si le clonage est dit immuable, la modification
   * directe de la nouvelle collection ne sera pas permise et elle suivra les modifications
   * de la collection clon�e. Dans le cas contraire, elle sera remplie des m�mes
   * elements que la collection clon�e mais leurs evolutions seront d�corr�l�es
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
  private Map<KEY, VALUE> getInternalMap()
  {
    return this.internal;
  }
  /**
   * Setter de la map interne sur laquelle doit se baser la map courante
   * @param internal Map interne � positionner
   */
  private void setInternalMap(Map<KEY, VALUE> internal)
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
  protected Map<KEY, VALUE> createInternalMap(int initialCapacity)
  {
    return new HashMap<KEY, VALUE>(initialCapacity);
  }
  /**
   * Cette m�thode permet de r�cup�rer la map interne sur laquelle se base la map
   * courante avec v�rification de la protection de cette derni�re
   * @return La map interne sur laquelle se base la collection courante
   * @throws ProtectionException Si la map courante est prot�g�e
   */
  public final Map<KEY, VALUE> getInternal() throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalMap();
  }

  //*************************************************************************//
  //***************** D�finition du set d'entr�es d'une map *****************//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <KEY> TODO A COMMENTER<BR>
   * @param <VALUE> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
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
  //************* D�finition de l'it�rateur d'entr�es d'une map *************//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <KEY> TODO A COMMENTER<BR>
   * @param <VALUE> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
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
     * sera prot�g� identiquement � l'it�rateur dont il est issu
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
  //******************* D�finition de l'entr�e d'une map ********************//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <KEY> TODO A COMMENTER<BR>
   * @param <VALUE> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
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
     * @throws ProtectionException Si l'entr�e est prot�g�e contre les modifications
     * @see java.util.Map.Entry#setValue(java.lang.Object)
     */
    @Override
    public VALUE setValue(VALUE value) throws ProtectionException
    {
      this.checkProtection();
      return this.getInternal().setValue(value);
    }

    /**
     * Getter de l'entr�e interne sur laquelle se base l'entr�e courante
     * @return L'entr�e interne sur laquelle se base l'entr�e courante
     */
    private Entry<KEY, VALUE> getInternal()
    {
      return this.internal;
    }
    /**
     * Setter de l'entr�e interne sur laquelle doit se baser l'entr�e courante
     * @param internal Entr�e interne � positionner
     */
    private void setInternal(Entry<KEY, VALUE> internal)
    {
      this.internal = internal;
    }
  }
}