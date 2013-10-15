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
 * une liste interne dont elle utilise les m�canismes tout en red�finissant certains
 * comportements. Elle pourra aussi �tre prot�g�e contre toute modification m�me
 * si elle ne le sera pas par d�faut<BR>
 * <BR>
 * @param <OBJECT> D�finition du type des objets contenus dans la liste<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinList<OBJECT>
       extends Bid4WinCollectionAbstract<OBJECT, List<OBJECT>, Bid4WinList<OBJECT>>
       implements List<OBJECT>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 1L;

  /** Liste interne sur laquelle se base la collection courante */
  private List<OBJECT> internal;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinList()
  {
    super();
  }
  /**
   * Constructeur utilisant la capacit� initiale par d�faut et la protection en
   * argument
   * @param protection Protection � utiliser pour la liste
   */
  public Bid4WinList(ObjectProtection protection)
  {
    super(protection);
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la collection
   */
  public Bid4WinList(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer � remplir la collection.
   * La collection construite aura une capacit� initiale de un
   * @param object Objet � ajouter � la collection
   */
  public Bid4WinList(OBJECT object)
  {
    super(object);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer � remplir la collection
   * avec pr�cision de sa capacit� initiale. Si la capacit� initiale fournie est
   * inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param object Objet � ajouter � la collection
   * @param initialCapacity Capacit� initiale de la collection
   */
  public Bid4WinList(OBJECT object, int initialCapacity)
  {
    super(object, initialCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacit� initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets � ajouter � la collection
   */
  public Bid4WinList(Collection<? extends OBJECT> collection)
  {
    super(collection);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * avec pr�cision de la capacit� initiale suppl�mentaire de la collection par
   * rapport � la taille de celle en argument
   * @param collection Collection d'objets � ajouter � la collection
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille de celle en argument
   */
  public Bid4WinList(Collection<? extends OBJECT> collection, int additionalCapacity)
  {
    // Si la capacit� initiale suppl�mentaire fournie est inf�rieure � 0, on
    // n'en prendra pas compte
    super(collection, additionalCapacity);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection. La
   * collection construite aura comme capacit� initiale la taille du tableau en
   * argument
   * @param objects Objets � ajouter � la collection
   */
  public Bid4WinList(OBJECT ... objects)
  {
    super(objects);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection avec
   * pr�cision de la capacit� initiale suppl�mentaire de la collection par rapport
   * � la taille du tableau
   * @param array Tableau d'objets � ajouter � la collection
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille du tableau
   */
  public Bid4WinList(OBJECT[] array, int additionalCapacity)
  {
    // Si la capacit� initiale suppl�mentaire fournie est inf�rieure � 0, on
    // n'en prendra pas compte
    super(array, additionalCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilis�e comme collection interne, la collection construite aura
   * comme capacit� initiale la taille de la collection en argument et sera remplie
   * avec les �l�ments de celle-ci
   * @param list Collection d'objets � ajouter � la collection � construire
   * @param useAsInternal Flag indiquant si la collection en argument doit �tre
   * utilis�e comme collection interne ou non
   */
  public Bid4WinList(List<OBJECT> list, boolean useAsInternal)
  {
    super(list, useAsInternal);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilis�e comme collection interne, la collection construite aura
   * comme capacit� initiale la taille de la collection en argument augment�e de
   * la capacit� initiale suppl�mentaire choisie et sera remplie avec les �l�ments
   * de celle-ci
   * @param list Collection d'objets � ajouter � la collection � construire
   * @param useAsInternal Flag indiquant si la collection en argument doit �tre
   * utilis�e comme collection interne ou non
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille de celle en argument dans le cas o� celle-ci n'est
   * pas utilis�e comme collection interne
   */
  public Bid4WinList(List<OBJECT> list, boolean useAsInternal, int additionalCapacity)
  {
    super(list, useAsInternal, additionalCapacity);
  }

  /**
   * Cette m�thode permet de trier la liste courante en utilisant le comparateur
   * par d�faut du projet
   * @return La liste courante tri�e
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
   */
  public Bid4WinList<OBJECT> sort() throws ProtectionException
  {
    return this.sort(Bid4WinComparator.getInstance());
  }
  /**
   * Cette m�thode permet de trier la liste courante en utilisant le comparateur
   * donn�
   * @param comparator Comparateur � utiliser pour trier la liste courante
   * @return La liste courante tri�e
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
   */
  public Bid4WinList<OBJECT> sort(Bid4WinComparator<? super OBJECT> comparator) throws ProtectionException
  {
    this.checkProtection();
    Collections.sort(this, comparator);
    return this;
  }

  /**
   * Ajoute l'objet en argument � la collection interne � la position indiqu�e
   * @param index {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, OBJECT toBeAdded) throws ProtectionException
  {
    this.checkProtection();
    this.getInternalList().add(index, toBeAdded);
  }
  /**
   * Ajoute la collection d'objets en argument � la collection interne � la position
   * indiqu�e
   * @param index {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
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
   * Cette m�thode positionne l'objet en argument dans la liste interne � la position
   * indiqu�e
   * @param index {@inheritDoc}
   * @param toBeSet {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public OBJECT set(int index, OBJECT toBeSet) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalList().set(index, toBeSet);
  }
  /**
   * Cette m�thode retire l'objet � la position indiqu�e de la liste interne
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
   * @see java.util.List#remove(int)
   */
  @Override
  public OBJECT remove(int index) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalList().remove(index);
  }
  /**
   * Cette m�thode permet de retirer le premier objet de la liste
   * @return Le premier objet de la liste retir� ou null si la liste n'en contient
   * aucun
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
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
   * Cette m�thode permet de retirer le dernier objet de la liste
   * @return Le dernier objet de la liste retir� ou null si la liste n'en contient
   * aucun
   * @throws ProtectionException Si la liste est prot�g�e contre les modifications
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
   * Cette m�thode cr�e et retourne une sous-liste construite avec les �l�ments
   * de la liste interne entre les positions indiqu�es. Cette sous-liste sera prot�g�e
   * au m�me titre que la liste dont elle est issue
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
   * Cette m�thode cr�e et retourne une sous-liste construite avec les �l�ments
   * de la liste interne de la position indiqu�e � la fin de la liste. Cette sous
   * liste sera prot�g�e au m�me titre que la liste dont elle est issue
   * @param from Position de d�but de la sous-liste
   * @return La sous-liste construite
   */
  public Bid4WinList<OBJECT> subList(int from)
  {
    return this.subList(from, this.size());
  }
  /**
   * Retourne la valeur positionn� � l'index indiqu� dans la liste
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
   * Retourne le premier �l�ment de la liste ou null si la liste est vide
   * @return Le premier �l�ment de la liste ou null si la liste est vide
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
   * Retourne le dernier �l�ment de la liste ou null si la liste est vide
   * @return Le dernier �l�ment de la liste ou null si la liste est vide
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
   * Retourne la premi�re position de l'objet en argument dans la liste interne
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
   * Retourne la derni�re position de l'objet en argument dans la liste interne
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
   * Retourne l'it�rateur de listes d'objets de la collection interne. Attention,
   * les actions sur celui-ci se r�percuterons sur la collection et inversement
   * et il sera prot�g� identiquement � la liste dont il est issu
   * @return {@inheritDoc}
   * @see java.util.List#listIterator()
   */
  @Override
  public Bid4WinListIterator<OBJECT> listIterator()
  {
    Bid4WinListIterator<OBJECT> iterator = new Bid4WinListIterator<OBJECT>(
        this.getInternalList().listIterator());
    // Prot�ge l'it�rateur comme la liste dont il est issu
    iterator.protect(this.getProtection());
    return iterator;
  }
  /**
   * Retourne l'it�rateur de listes d'objets de la collection interne commen�ant
   * � la position indiqu�e. Attention, les actions sur celui-ci se r�percuterons
   * sur la collection et inversement et il sera prot�g� identiquement � la liste
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
    // Prot�ge l'it�rateur comme la liste dont il est issu
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
   * Cette m�thode permet de cr�er une liste pouvant �tre utilis�e comme collection
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
