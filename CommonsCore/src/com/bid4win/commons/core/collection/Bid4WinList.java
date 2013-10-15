package com.bid4win.commons.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.security.ObjectProtection;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe est la classe de base de toute liste du projet. Elle se base sur
 * une liste interne dont elle utilise les mécanismes tout en redéfinissant certains
 * comportements. Elle pourra aussi être protégée contre toute modification même
 * si elle ne le sera pas par défaut<BR>
 * <BR>
 * @param <OBJECT> Définition du type des objets contenus dans la liste<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinList<OBJECT>
       extends Bid4WinCollectionAbstract<OBJECT, List<OBJECT>, Bid4WinList<OBJECT>>
       implements List<OBJECT>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 1L;

  /** Liste interne sur laquelle se base la collection courante */
  private List<OBJECT> internal;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinList()
  {
    super();
  }
  /**
   * Constructeur utilisant la capacité initiale par défaut et la protection en
   * argument
   * @param protection Protection à utiliser pour la liste
   */
  public Bid4WinList(ObjectProtection protection)
  {
    super(protection);
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la collection
   */
  public Bid4WinList(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer à remplir la collection.
   * La collection construite aura une capacité initiale de un
   * @param object Objet à ajouter à la collection
   */
  public Bid4WinList(OBJECT object)
  {
    super(object);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer à remplir la collection
   * avec précision de sa capacité initiale. Si la capacité initiale fournie est
   * inférieure à un, on utilisera la capacité initiale par défaut
   * @param object Objet à ajouter à la collection
   * @param initialCapacity Capacité initiale de la collection
   */
  public Bid4WinList(OBJECT object, int initialCapacity)
  {
    super(object, initialCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacité initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets à ajouter à la collection
   */
  public Bid4WinList(Collection<? extends OBJECT> collection)
  {
    super(collection);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * avec précision de la capacité initiale supplémentaire de la collection par
   * rapport à la taille de celle en argument
   * @param collection Collection d'objets à ajouter à la collection
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille de celle en argument
   */
  public Bid4WinList(Collection<? extends OBJECT> collection, int additionalCapacity)
  {
    // Si la capacité initiale supplémentaire fournie est inférieure à 0, on
    // n'en prendra pas compte
    super(collection, additionalCapacity);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection. La
   * collection construite aura comme capacité initiale la taille du tableau en
   * argument
   * @param objects Objets à ajouter à la collection
   */
  public Bid4WinList(OBJECT ... objects)
  {
    super(objects);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection avec
   * précision de la capacité initiale supplémentaire de la collection par rapport
   * à la taille du tableau
   * @param array Tableau d'objets à ajouter à la collection
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille du tableau
   */
  public Bid4WinList(OBJECT[] array, int additionalCapacity)
  {
    // Si la capacité initiale supplémentaire fournie est inférieure à 0, on
    // n'en prendra pas compte
    super(array, additionalCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilisée comme collection interne, la collection construite aura
   * comme capacité initiale la taille de la collection en argument et sera remplie
   * avec les éléments de celle-ci
   * @param list Collection d'objets à ajouter à la collection à construire
   * @param useAsInternal Flag indiquant si la collection en argument doit être
   * utilisée comme collection interne ou non
   */
  public Bid4WinList(List<OBJECT> list, boolean useAsInternal)
  {
    super(list, useAsInternal);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilisée comme collection interne, la collection construite aura
   * comme capacité initiale la taille de la collection en argument augmentée de
   * la capacité initiale supplémentaire choisie et sera remplie avec les éléments
   * de celle-ci
   * @param list Collection d'objets à ajouter à la collection à construire
   * @param useAsInternal Flag indiquant si la collection en argument doit être
   * utilisée comme collection interne ou non
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille de celle en argument dans le cas où celle-ci n'est
   * pas utilisée comme collection interne
   */
  public Bid4WinList(List<OBJECT> list, boolean useAsInternal, int additionalCapacity)
  {
    super(list, useAsInternal, additionalCapacity);
  }

  /**
   * Cette méthode permet de trier la liste courante en utilisant le comparateur
   * par défaut du projet
   * @return La liste courante triée
   * @throws ProtectionException Si la liste est protégée contre les modifications
   */
  public Bid4WinList<OBJECT> sort() throws ProtectionException
  {
    return this.sort(Bid4WinComparator.getInstance());
  }
  /**
   * Cette méthode permet de trier la liste courante en utilisant le comparateur
   * donné
   * @param comparator Comparateur à utiliser pour trier la liste courante
   * @return La liste courante triée
   * @throws ProtectionException Si la liste est protégée contre les modifications
   */
  public Bid4WinList<OBJECT> sort(Bid4WinComparator<? super OBJECT> comparator) throws ProtectionException
  {
    this.checkProtection();
    Collections.sort(this, comparator);
    return this;
  }

  /**
   * Ajoute l'objet en argument à la collection interne à la position indiquée
   * @param index {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException Si la liste est protégée contre les modifications
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, OBJECT toBeAdded) throws ProtectionException
  {
    this.checkProtection();
    this.getInternalList().add(index, toBeAdded);
  }
  /**
   * Ajoute la collection d'objets en argument à la collection interne à la position
   * indiquée
   * @param index {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la liste est protégée contre les modifications
   * @see java.util.List#addAll(int, java.util.Collection) throws ProtectionException
   */
  @Override
  public boolean addAll(int index, Collection<? extends OBJECT> toBeAdded)
         throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalList().addAll(index, toBeAdded);
  }
  /**
   * Cette méthode positionne l'objet en argument dans la liste interne à la position
   * indiquée
   * @param index {@inheritDoc}
   * @param toBeSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la liste est protégée contre les modifications
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public OBJECT set(int index, OBJECT toBeSet) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalList().set(index, toBeSet);
  }
  /**
   * Cette méthode retire l'objet à la position indiquée de la liste interne
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la liste est protégée contre les modifications
   * @see java.util.List#remove(int)
   */
  @Override
  public OBJECT remove(int index) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalList().remove(index);
  }
  /**
   * Cette méthode permet de retirer le premier objet de la liste
   * @return Le premier objet de la liste retiré ou null si la liste n'en contient
   * aucun
   * @throws ProtectionException Si la liste est protégée contre les modifications
   */
  public OBJECT removeFirst() throws ProtectionException
  {
    if(this.size() != 0)
    {
      return this.remove(0);
    }
    return null;
  }
  /**
   * Cette méthode permet de retirer le dernier objet de la liste
   * @return Le dernier objet de la liste retiré ou null si la liste n'en contient
   * aucun
   * @throws ProtectionException Si la liste est protégée contre les modifications
   */
  public OBJECT removeLast() throws ProtectionException
  {
    if(this.size() != 0)
    {
      return this.remove(this.size() -1);
    }
    return null;
  }
  /**
   * Cette méthode crée et retourne une sous-liste construite avec les éléments
   * de la liste interne entre les positions indiquées. Cette sous-liste sera protégée
   * au même titre que la liste dont elle est issue
   * @param from {@inheritDoc}
   * @param to {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#subList(int, int)
   */
  @Override
  public Bid4WinList<OBJECT> subList(int from, int to)
  {
    Bid4WinList<OBJECT> subList = new Bid4WinList<OBJECT>(
        this.getInternalList().subList(from, to), true);
    subList.protect(this.getProtection());
    return subList;
  }
  /**
   * Cette méthode crée et retourne une sous-liste construite avec les éléments
   * de la liste interne de la position indiquée à la fin de la liste. Cette sous
   * liste sera protégée au même titre que la liste dont elle est issue
   * @param from Position de début de la sous-liste
   * @return La sous-liste construite
   */
  public Bid4WinList<OBJECT> subList(int from)
  {
    return this.subList(from, this.size());
  }
  /**
   * Retourne la valeur positionné à l'index indiqué dans la liste
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#get(int)
   */
  @Override
  public OBJECT get(int index)
  {
    return this.getInternalList().get(index);
  }
  /**
   * Retourne le premier élément de la liste ou null si la liste est vide
   * @return Le premier élément de la liste ou null si la liste est vide
   */
  public OBJECT getFirst()
  {
    if(this.size() == 0)
    {
      return null;
    }
    return this.get(0);
  }
  /**
   * Retourne le dernier élément de la liste ou null si la liste est vide
   * @return Le dernier élément de la liste ou null si la liste est vide
   */
  public OBJECT getLast()
  {
    if(this.size() == 0)
    {
      return null;
    }
    return this.get(this.size() - 1);
  }
  /**
   * Retourne la première position de l'objet en argument dans la liste interne
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object object)
  {
    return this.getInternalList().indexOf(object);
  }
  /**
   * Retourne la dernière position de l'objet en argument dans la liste interne
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  @Override
  public int lastIndexOf(Object object)
  {
    return this.getInternalList().lastIndexOf(object);
  }
  /**
   * Retourne l'itérateur de listes d'objets de la collection interne. Attention,
   * les actions sur celui-ci se répercuterons sur la collection et inversement
   * et il sera protégé identiquement à la liste dont il est issu
   * @return {@inheritDoc}
   * @see java.util.List#listIterator()
   */
  @Override
  public Bid4WinListIterator<OBJECT> listIterator()
  {
    Bid4WinListIterator<OBJECT> iterator = new Bid4WinListIterator<OBJECT>(
        this.getInternalList().listIterator());
    // Protège l'itérateur comme la liste dont il est issu
    iterator.protect(this.getProtection());
    return iterator;
  }
  /**
   * Retourne l'itérateur de listes d'objets de la collection interne commençant
   * à la position indiquée. Attention, les actions sur celui-ci se répercuterons
   * sur la collection et inversement et il sera protégé identiquement à la liste
   * dont il est issu
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#listIterator(int)
   */
  @Override
  public Bid4WinListIterator<OBJECT> listIterator(int index)
  {
    Bid4WinListIterator<OBJECT> iterator = new Bid4WinListIterator<OBJECT>(
        this.getInternalList().listIterator(index));
    // Protège l'itérateur comme la liste dont il est issu
    iterator.protect(this.getProtection());
    return iterator;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private List<OBJECT> getInternalList()
  {
    return this.internal;
  }
  /**
   *
   * TODO A COMMENTER
   * @param internal TODO A COMMENTER
   */
  private void setInternalList(List<OBJECT> internal)
  {
    this.internal = internal;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstract#getInternalCollectionClass()
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class getInternalCollectionClass()
  {
    return List.class;
  }
  /**
   *
   * TODO A COMMENTER
   * @param internal {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstract#setInternalCollection(java.util.Collection)
   */
  @Override
  protected void setInternalCollection(List<OBJECT> internal) throws ProtectionException
  {
    super.setInternalCollection(internal);
    this.setInternalList(internal);
  }
  /**
   * Cette méthode permet de créer une liste pouvant être utilisée comme collection
   * interne par la collection courante
   * @param initialCapacity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstract#createInternalCollection(int)
   */
  @Override
  protected final List<OBJECT> createInternalCollection(int initialCapacity)
  {
    return new ArrayList<OBJECT>(initialCapacity);
  }
}
