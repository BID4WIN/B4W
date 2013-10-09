package com.bid4win.commons.core.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.bid4win.commons.core.exception.RuntimeArgumentException;
import com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer;

/**
 * Cette classe est la classe de base de toute collection du projet. Elle se base
 * sur une collection interne dont elle utilise les m�canismes tout en red�finissant
 * certains comportements<BR>
 * <BR>
 * @param <OBJECT> D�finition du type des objets contenus dans la collection<BR>
 * @param <COLLECTION_TYPE> D�finition du type des collections pouvant �tre utilis�es
 * comme collection interne<BR>
 * @param <CLASS> Doit d�finir la classe de collection r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinCollection<OBJECT,
                                        COLLECTION_TYPE extends Collection<OBJECT>,
                                        CLASS extends Bid4WinCollection<OBJECT, COLLECTION_TYPE, CLASS>>
       implements Collection<OBJECT>, Cloneable, Serializable
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -8559735053376993448L;
  /** Capacit� initiale par d�faut de la collection interne */
  private final static int DEFAULT_INITIAL_CAPACITY = 10;

  /** Collection interne sur laquelle se base la collection courante */
  private COLLECTION_TYPE internal = null;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinCollection()
  {
    // Utilisation de la capacit� initiale par d�faut
    this(0);
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la collection
   */
  public Bid4WinCollection(int initialCapacity)
  {
    // Si la capacit� initiale est inf�rieure � un, on utilisera la capacit�
    // initiale par d�faut
    super();
    this.setInternal(this.createInternal(initialCapacity < 1 ?
                                         DEFAULT_INITIAL_CAPACITY :
                                         initialCapacity));
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer � remplir la collection.
   * La collection construite aura une capacit� initiale de un
   * @param object Objet � ajouter � la collection
   */
  public Bid4WinCollection(OBJECT object)
  {
    this(object, 1);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer � remplir la collection
   * avec pr�cision de sa capacit� initiale. Si la capacit� initiale fournie est
   * inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param object Objet � ajouter � la collection
   * @param initialCapacity Capacit� initiale de la collection
   */
  public Bid4WinCollection(OBJECT object, int initialCapacity)
  {
    this(initialCapacity);
    this.add(object);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacit� initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets � ajouter � la collection
   */
  public Bid4WinCollection(Collection<? extends OBJECT> collection)
  {
    this(collection, 0);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * avec pr�cision de la capacit� initiale suppl�mentaire de la collection par
   * rapport � la taille de celle en argument
   * @param collection Collection d'objets � ajouter � la collection
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille de celle en argument
   */
  public Bid4WinCollection(Collection<? extends OBJECT> collection, int additionalCapacity)
  {
    // Si la capacit� initiale suppl�mentaire fournie est inf�rieure � 0, on
    // n'en prendra pas compte
    this(collection.size() + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.addAll(collection);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection. La
   * collection construite aura comme capacit� initiale la taille du tableau en
   * argument
   * @param objects Objets � ajouter � la collection
   */
  public Bid4WinCollection(OBJECT ... objects)
  {
    this(objects, 0);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection avec
   * pr�cision de la capacit� initiale suppl�mentaire de la collection par rapport
   * � la taille du tableau
   * @param array Tableau d'objets � ajouter � la collection
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille du tableau
   */
  public Bid4WinCollection(OBJECT[] array, int additionalCapacity)
  {
    // Si la capacit� initiale suppl�mentaire fournie est inf�rieure � 0, on
    // n'en prendra pas compte
    this(array.length + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.addAll(array);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilis�e comme collection interne, la collection construite aura
   * comme capacit� initiale la taille de la collection en argument et sera remplie
   * avec les �l�ments de celle-ci
   * @param collection Collection d'objets � ajouter � la collection � construire
   * @param useAsInternal Flag indiquant si la collection en argument doit �tre
   * utilis�e comme collection interne ou non
   */
  public Bid4WinCollection(COLLECTION_TYPE collection, boolean useAsInternal)
  {
    this(collection, useAsInternal, 0);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * en l'utilisant comme collection interne ou non. Si la collection fournie
   * n'est pas utilis�e comme collection interne, la collection construite aura
   * comme capacit� initiale la taille de la collection en argument augment�e de
   * la capacit� initiale suppl�mentaire choisie et sera remplie avec les �l�ments
   * de celle-ci
   * @param collection Collection d'objets � ajouter � la collection � construire
   * @param useAsInternal Flag indiquant si la collection en argument doit �tre
   * utilis�e comme collection interne ou non
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la collection
   * par rapport � la taille de celle en argument dans le cas o� celle-ci n'est
   * pas utilis�e comme collection interne
   */
  public Bid4WinCollection(COLLECTION_TYPE collection, boolean useAsInternal, int additionalCapacity)
  {
    super();
    COLLECTION_TYPE internal = collection;
    if(!useAsInternal)
    {
      internal = this.createInternal(collection.size() + (additionalCapacity < 0 ? 0 : additionalCapacity));
      internal.addAll(collection);
    }
    this.setInternal(internal);
  }

  /**
   * Ajoute l'objet en argument � la collection interne
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#add(java.lang.Object)
   */
  @Override
  public boolean add(OBJECT toBeAdded)
  {
    return this.getInternal().add(toBeAdded);
  }
  /**
   * Ajoute la collection d'objets en argument � la collection interne
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends OBJECT> toBeAdded)
  {
    return this.getInternal().addAll(toBeAdded);
  }
  /**
   * Ajoute le tableau d'objets en argument � la collection interne
   * @param toBeAdded Objets � ajouter � la collection interne
   */
  public void addAll(OBJECT ... toBeAdded)
  {
    for(OBJECT object : toBeAdded)
    {
      this.add(object);
    }
  }
  /**
   * Retire l'objet en argument de la collection interne
   * @param toBeRemoved {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object toBeRemoved)
  {
    return this.getInternal().remove(toBeRemoved);
  }
  /**
   * Retire la collection d'objets en argument de la collection interne
   * @param toBeRemoved {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> toBeRemoved)
  {
    return this.getInternal().removeAll(toBeRemoved);
  }
  /**
   * Retire tous les objets ne se trouvant pas dans la collection d'objets en
   * argument de la collection interne
   * @param toBeRetained {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> toBeRetained)
  {
    return this.getInternal().retainAll(toBeRetained);
  }
  /**
   * Vide la collection interne
   * @see java.util.Collection#clear()
   */
  @Override
  public void clear()
  {
    this.getInternal().clear();
  }
  /**
   * V�rifie si la collection interne contient l'objet en argument
   * @param toBeTested {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object toBeTested)
  {
    return this.getInternal().contains(toBeTested);
  }
  /**
   * V�rifie si la collection interne contient tous les objets de la collection
   * en argument
   * @param toBeTested {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection<?> toBeTested)
  {
    return this.getInternal().containsAll(toBeTested);
  }
  /**
   * Retourne le code de hachage de la collection interne
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return this.getInternal().hashCode();
  }
  /**
   * Test l'�galit� entre la collection interne et l'objet en argument
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
   * V�rifie si la collection interne est vide
   * @return {@inheritDoc}
   * @see java.util.Collection#isEmpty()
   */
  @Override
  public boolean isEmpty()
  {
    return this.getInternal().isEmpty();
  }
  /**
   * Retourne la taille de la collection interne
   * @return {@inheritDoc}
   * @see java.util.Collection#size()
   */
  @Override
  public int size()
  {
    return this.getInternal().size();
  }
  /**
   * Retourne l'it�rateur d'objets de la collection interne. Attention, les actions
   * sur celui-ci se r�percuterons sur la collection et inversement
   * @return {@inheritDoc}
   * @see java.util.Collection#iterator()
   */
  @Override
  public Iterator<OBJECT> iterator()
  {
    return this.getInternal().iterator();
  }
  /**
   * Retourne un tableau contenant tous les objets de la collection interne
   * @return {@inheritDoc}
   * @see java.util.Collection#toArray()
   */
  @Override
  public Object[] toArray()
  {// TODO voir si reprendre de TypifiedList ...
    return this.getInternal().toArray();
  }
  /**
   * Retourne un tableau contenant tous les objets de la collection interne
   * @param <TYPE> {@inheritDoc}
   * @param array {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#toArray(TYPE[])
   */
  @Override
  public <TYPE> TYPE[] toArray(TYPE[] array)
  {
    return this.getInternal().toArray(array);
  }

  /**
   * Cette m�thode permet de cr�er une nouvelle instance de la classe qui sera
   * remplie avec les �l�ments de la collection en argument
   * @param collection Collection avec les �l�ments de laquelle remplir la nouvelle
   * instance
   * @return Une nouvelle instance de la classe qui sera remplie avec les �l�ments
   * de la collection en argument
   * @throws RuntimeArgumentException Si la construction de la nouvelle instance
   * �choue (par de constructeur bas� sur une collection par exemple)
   */
  @SuppressWarnings("unchecked")
  public CLASS newInstance(Collection<? extends OBJECT> collection)
         throws RuntimeArgumentException
  {
    try
    {
      return (CLASS)this.getClass().getDeclaredConstructor(
          Collection.class).newInstance(collection);
    }
    catch(Exception ex)
    {
      throw new RuntimeArgumentException(ex);
    }
  }
  /**
   * D�fini le clonage d'une collection
   * @return {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  @Override
  public CLASS clone()
  {
    return this.newInstance(this.getInternal());
  }

  /**
   * Red�fini la m�thode toString() pour rendre plus lisible toute collection
   * @return {@inheritDoc}
   * @see java.util.AbstractCollection#toString()
   */
  @Override
  public String toString()
  {
    return this.render().toString();
  }

  /**
   * Red�fini la m�thode toString() pour rendre plus lisible toute collection
   * @return La cha�ne de caract�res repr�sentant lisiblement la collection courante
   */
  public StringBuffer render()
  {
    return Bid4WinCollectionRenderer.getInstanceCollection().renderMultipleLine(this);
  }

  /**
   * Getter de la collection interne sur laquelle se base la collection courante
   * @return La collection interne sur laquelle se base la collection courante
   */
  public COLLECTION_TYPE getInternal()
  {
    return this.internal;
  }
  /**
   * Setter de la collection interne sur laquelle doit se baser la collection courante
   * @param internal Collection interne � positionner
   */
  private void setInternal(COLLECTION_TYPE internal)
  {
    this.internal = internal;
  }
  /**
   * Cette m�thode permet de cr�er une collection pouvant �tre utilis�e comme
   * collection interne par la collection courante
   * @param initialCapacity Capacit� initiale de la collection � cr�er
   * @return La collection cr��e
   */
  protected abstract COLLECTION_TYPE createInternal(int initialCapacity);
}