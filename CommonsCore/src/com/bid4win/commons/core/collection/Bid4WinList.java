package com.bid4win.commons.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import com.bid4win.commons.core.comparator.Bid4WinComparator;

/**
 * Cette classe est la classe de base de toute liste du projet. Elle se base sur
 * une liste interne dont elle utilise les m�canismes tout en red�finissant certains
 * comportements<BR>
 * <BR>
 * @param <OBJECT> D�finition du type des objets contenus dans la liste<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinList<OBJECT>
       extends Bid4WinCollection<OBJECT, List<OBJECT>, Bid4WinList<OBJECT>>
       implements List<OBJECT>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinList()
  {
    super();
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
   * Cette m�thode permet de cr�er une liste pouvant �tre utilis�e comme collection
   * interne par la collection courante
   * @param initialCapacity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollection#createInternal(int)
   */
  @Override
  protected List<OBJECT> createInternal(int initialCapacity)
  {
    return new ArrayList<OBJECT>(initialCapacity);
  }

  /**
   * Cette m�thode permet de trier la liste courante en utilisant le comparateur
   * par d�faut du projet
   * @return La liste courante tri�e
   */
  public Bid4WinList<OBJECT> sort()
  {
    Collections.sort(this, Bid4WinComparator.getInstance());
    return this;
  }
  /**
   * Cette m�thode permet de trier la liste courante en utilisant le comparateur
   * donn�
   * @param comparator Comparateur � utiliser pour trier la liste courante
   * @return La liste courante tri�e
   */
  public Bid4WinList<OBJECT> sort(Bid4WinComparator<OBJECT> comparator)
  {
    Collections.sort(this, comparator);
    return this;
  }

  /**
   * Ajoute l'objet en argument � la collection interne � la position indiqu�e
   * @param index {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, OBJECT toBeAdded)
  {
    this.getInternal().add(index, toBeAdded);
  }
  /**
   * Ajoute la collection d'objets en argument � la collection interne � la position
   * indiqu�e
   * @param index {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int index, Collection<? extends OBJECT> toBeAdded)
  {
    return this.getInternal().addAll(index, toBeAdded);
  }
  /**
   * Cette m�thode positionne l'objet en argument dans la liste interne � la position
   * indiqu�e
   * @param index {@inheritDoc}
   * @param toBeSet {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public OBJECT set(int index, OBJECT toBeSet)
  {
    return this.getInternal().set(index, toBeSet);
  }
  /**
   * Cette m�thode retire l'objet � la position indiqu�e de la liste interne
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#remove(int)
   */
  @Override
  public OBJECT remove(int index)
  {
    return this.getInternal().remove(index);
  }
  /**
   * Cette m�thode permet de retirer le premier objet de la liste
   * @return Le premier objet de la liste retir� ou null si la liste n'en contient
   * aucun
   */
  public OBJECT removeFirst()
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
   */
  public OBJECT removeLast()
  {
    if(this.size() != 0)
    {
      return this.remove(this.size() -1);
    }
    return null;
  }
  /**
   * Cette m�thode cr�e et retourne une sous-liste construite avec les �l�ments
   * de la liste interne entre les positions indiqu�es
   * @param from {@inheritDoc}
   * @param to {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#subList(int, int)
   */
  @Override
  public Bid4WinList<OBJECT> subList(int from, int to)
  {
    List<OBJECT> subList = this.getInternal().subList(from, to);
    if(subList instanceof Bid4WinList<?>)
    {
      return (Bid4WinList<OBJECT>)subList;
    }
    return new Bid4WinList<OBJECT>(subList, true);
  }
  /**
   * Cette m�thode cr�e et retourne une sous-liste construite avec les �l�ments
   * de la liste interne de la position indiqu�e � la fin de la liste
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
    return this.getInternal().get(index);
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
    return this.getInternal().indexOf(object);
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
    return this.getInternal().lastIndexOf(object);
  }
  /**
   * Retourne l'it�rateur de listes d'objets de la collection interne. Attention,
   * les actions sur celui-ci se r�percuterons sur la collection et inversement
   * @return {@inheritDoc}
   * @see java.util.List#listIterator()
   */
  @Override
  public ListIterator<OBJECT> listIterator()
  {
    return this.getInternal().listIterator();
  }
  /**
   * Retourne l'it�rateur de listes d'objets de la collection interne commen�ant
   * � la position indiqu�e. Attention, les actions sur celui-ci se r�percuterons
   * sur la collection et inversement
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.List#listIterator(int)
   */
  @Override
  public ListIterator<OBJECT> listIterator(int index)
  {
    return this.getInternal().listIterator(index);
  }
}
