package com.bid4win.commons.core.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe est la classe de base de tout set du projet. Elle se base sur un
 * set interne dont elle utilise les mécanismes tout en redéfinissant certains
 * comportements<BR>
 * <BR>
 * @param <OBJECT> Définition du type des objets contenus dans le set<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinSet<OBJECT>
       extends Bid4WinCollection<OBJECT, Set<OBJECT>, Bid4WinSet<OBJECT>>
       implements Set<OBJECT>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -2813682219203993166L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinSet()
  {
    super();
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la collection
   */
  public Bid4WinSet(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer à remplir la collection.
   * La collection construite aura une capacité initiale de un
   * @param object Objet à ajouter à la collection
   */
  public Bid4WinSet(OBJECT object)
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
  public Bid4WinSet(OBJECT object, int initialCapacity)
  {
    super(object, initialCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacité initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets à ajouter à la collection
   */
  public Bid4WinSet(Collection<? extends OBJECT> collection)
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
  public Bid4WinSet(Collection<? extends OBJECT> collection, int additionalCapacity)
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
  public Bid4WinSet(OBJECT ... objects)
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
  public Bid4WinSet(OBJECT[] array, int additionalCapacity)
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
   * @param set Collection d'objets à ajouter à la collection à construire
   * @param useAsInternal Flag indiquant si la collection en argument doit être
   * utilisée comme collection interne ou non
   */
  public Bid4WinSet(Set<OBJECT> set, boolean useAsInternal)
  {
    super(set, useAsInternal);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilisée comme collection interne, la collection construite aura
   * comme capacité initiale la taille de la collection en argument augmentée de
   * la capacité initiale supplémentaire choisie et sera remplie avec les éléments
   * de celle-ci
   * @param set Collection d'objets à ajouter à la collection à construire
   * @param useAsInternal Flag indiquant si la collection en argument doit être
   * utilisée comme collection interne ou non
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille de celle en argument dans le cas où celle-ci n'est
   * pas utilisée comme collection interne
   */
  public Bid4WinSet(Set<OBJECT> set, boolean useAsInternal, int additionalCapacity)
  {
    super(set, useAsInternal, additionalCapacity);
  }

  /**
   * Cette méthode permet de créer un set pouvant être utilisé comme collection
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
