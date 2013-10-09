package com.bid4win.commons.core.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe est la classe de base de tout set du projet. Elle se base sur un
 * set interne dont elle utilise les m�canismes tout en red�finissant certains
 * comportements<BR>
 * <BR>
 * @param <OBJECT> D�finition du type des objets contenus dans le set<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinSet<OBJECT>
       extends Bid4WinCollection<OBJECT, Set<OBJECT>, Bid4WinSet<OBJECT>>
       implements Set<OBJECT>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -2813682219203993166L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinSet()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la collection
   */
  public Bid4WinSet(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer � remplir la collection.
   * La collection construite aura une capacit� initiale de un
   * @param object Objet � ajouter � la collection
   */
  public Bid4WinSet(OBJECT object)
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
  public Bid4WinSet(OBJECT object, int initialCapacity)
  {
    super(object, initialCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacit� initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets � ajouter � la collection
   */
  public Bid4WinSet(Collection<? extends OBJECT> collection)
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
  public Bid4WinSet(Collection<? extends OBJECT> collection, int additionalCapacity)
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
  public Bid4WinSet(OBJECT ... objects)
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
  public Bid4WinSet(OBJECT[] array, int additionalCapacity)
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
   * @param set Collection d'objets � ajouter � la collection � construire
   * @param useAsInternal Flag indiquant si la collection en argument doit �tre
   * utilis�e comme collection interne ou non
   */
  public Bid4WinSet(Set<OBJECT> set, boolean useAsInternal)
  {
    super(set, useAsInternal);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilis�e comme collection interne, la collection construite aura
   * comme capacit� initiale la taille de la collection en argument augment�e de
   * la capacit� initiale suppl�mentaire choisie et sera remplie avec les �l�ments
   * de celle-ci
   * @param set Collection d'objets � ajouter � la collection � construire
   * @param useAsInternal Flag indiquant si la collection en argument doit �tre
   * utilis�e comme collection interne ou non
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille de celle en argument dans le cas o� celle-ci n'est
   * pas utilis�e comme collection interne
   */
  public Bid4WinSet(Set<OBJECT> set, boolean useAsInternal, int additionalCapacity)
  {
    super(set, useAsInternal, additionalCapacity);
  }

  /**
   * Cette m�thode permet de cr�er un set pouvant �tre utilis� comme collection
   * interne par la collection courante
   * @param initialCapacity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollection#createInternal(int)
   */
  @Override
  protected Set<OBJECT> createInternal(int initialCapacity)
  {
    return new HashSet<OBJECT>(initialCapacity);
  }

}
