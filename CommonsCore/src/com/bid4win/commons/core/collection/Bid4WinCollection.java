package com.bid4win.commons.core.collection;

import java.util.ArrayList;
import java.util.Collection;

import com.bid4win.commons.core.security.ObjectProtection;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <OBJECT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinCollection<OBJECT>
       extends Bid4WinCollectionAbstract<OBJECT, Collection<OBJECT>, Bid4WinCollection<OBJECT>>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -836044418919160024L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinCollection()
  {
    super();
  }
  /**
   * Constructeur utilisant la capacité initiale par défaut et la protection en
   * argument
   * @param protection Protection à utiliser pour la collection
   */
  public Bid4WinCollection(ObjectProtection protection)
  {
    super(protection);
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la collection
   */
  public Bid4WinCollection(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer à remplir la collection.
   * La collection construite aura une capacité initiale de un
   * @param object Objet à ajouter à la collection
   */
  public Bid4WinCollection(OBJECT object)
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
  public Bid4WinCollection(OBJECT object, int initialCapacity)
  {
    super(object, initialCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacité initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets à ajouter à la collection
   */
  public Bid4WinCollection(Collection<? extends OBJECT> collection)
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
  public Bid4WinCollection(Collection<? extends OBJECT> collection, int additionalCapacity)
  {
    super(collection, additionalCapacity);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection. La
   * collection construite aura comme capacité initiale la taille du tableau en
   * argument
   * @param objects Objets à ajouter à la collection
   */
  public Bid4WinCollection(OBJECT ... objects)
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
  public Bid4WinCollection(OBJECT[] array, int additionalCapacity)
  {
    super(array, additionalCapacity);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilisée comme collection interne, la collection construite aura
   * comme capacité initiale la taille de la collection en argument et sera remplie
   * avec les éléments de celle-ci
   * @param collection Collection d'objets à ajouter à la collection à construire
   * @param useAsInternal Flag indiquant si la collection en argument doit être
   * utilisée comme collection interne ou non
   */
  public Bid4WinCollection(Collection<OBJECT> collection, boolean useAsInternal)
  {
    super(collection, useAsInternal);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilisée comme collection interne, la collection construite aura
   * comme capacité initiale la taille de la collection en argument augmentée de
   * la capacité initiale supplémentaire choisie et sera remplie avec les éléments
   * de celle-ci
   * @param collection Collection d'objets à ajouter à la collection à construire
   * @param useAsInternal Flag indiquant si la collection en argument doit être
   * utilisée comme collection interne ou non
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille de celle en argument dans le cas où celle-ci n'est
   * pas utilisée comme collection interne
   */
  public Bid4WinCollection(Collection<OBJECT> collection, boolean useAsInternal, int additionalCapacity)
  {
    super(collection, useAsInternal, additionalCapacity);
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
    return Collection.class;
  }
 /**
   *
   * TODO A COMMENTER
   * @param initialCapacity {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinCollectionAbstract#createInternalCollection(int)
   */
  @Override
  protected final Collection<OBJECT> createInternalCollection(int initialCapacity)
  {
    return new ArrayList<OBJECT>();
  }
}
