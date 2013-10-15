package com.bid4win.commons.core.collection;

import java.io.Serializable;
import java.util.Collection;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer;
import com.bid4win.commons.core.security.ObjectProtection;
import com.bid4win.commons.core.security.ProtectableObject;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe est la classe de base de toute collection du projet. Elle se base
 * sur une collection interne dont elle utilise les mécanismes tout en redéfinissant
 * certains comportements. Elle pourra aussi être protégée contre toute modification
 * même si elle ne le sera pas par défaut<BR>
 * <BR>
 * @param <OBJECT> Définition du type des objets contenus dans la collection<BR>
 * @param <COLLECTION_TYPE> Définition du type des collections pouvant être utilisées
 * comme collection interne<BR>
 * @param <CLASS> Doit définir la classe de collection réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinCollectionAbstract<OBJECT,
                                                COLLECTION_TYPE extends Collection<OBJECT>,
                                                CLASS extends Bid4WinCollectionAbstract<OBJECT, COLLECTION_TYPE, CLASS>>
       extends ProtectableObject
       implements Collection<OBJECT>, Cloneable, Serializable
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -8559735053376993448L;
  /** Capacité initiale par défaut de la collection interne */
  private final static int DEFAULT_INITIAL_CAPACITY = 10;

  /** Collection interne sur laquelle se base la collection courante */
  private COLLECTION_TYPE internal = null;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinCollectionAbstract()
  {
    // Utilisation de la capacité initiale par défaut
    this(0);
  }
  /**
   * Constructeur utilisant la capacité initiale par défaut et la protection en
   * argument
   * @param protection Protection à utiliser pour la collection
   */
  public Bid4WinCollectionAbstract(ObjectProtection protection)
  {
    super(protection);
    // Utilisation de la capacité initiale par défaut
    this.setInternalCollection(this.createInternalCollection(DEFAULT_INITIAL_CAPACITY));
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la collection
   */
  public Bid4WinCollectionAbstract(int initialCapacity)
  {
    // Si la capacité initiale est inférieure à un, on utilisera la capacité
    // initiale par défaut
    super(null);
    this.setInternalCollection(this.createInternalCollection(initialCapacity < 1 ?
                                                             DEFAULT_INITIAL_CAPACITY :
                                                             initialCapacity));
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer à remplir la collection.
   * La collection construite aura une capacité initiale de un
   * @param object Objet à ajouter à la collection
   */
  public Bid4WinCollectionAbstract(OBJECT object)
  {
    this(object, 1);
  }
  /**
   * Constructeur utilisant l'objet en argument pour commencer à remplir la collection
   * avec précision de sa capacité initiale. Si la capacité initiale fournie est
   * inférieure à un, on utilisera la capacité initiale par défaut
   * @param object Objet à ajouter à la collection
   * @param initialCapacity Capacité initiale de la collection
   */
  public Bid4WinCollectionAbstract(OBJECT object, int initialCapacity)
  {
    this(initialCapacity);
    this.add(object);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection.
   * La collection construite aura comme capacité initiale la taille de la collection
   * en argument
   * @param collection Collection d'objets à ajouter à la collection
   */
  public Bid4WinCollectionAbstract(Collection<? extends OBJECT> collection)
  {
    this(collection, 0);
  }
  /**
   * Constructeur utilisant la collection en argument pour remplir la collection
   * avec précision de la capacité initiale supplémentaire de la collection par
   * rapport à la taille de celle en argument
   * @param collection Collection d'objets à ajouter à la collection
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille de celle en argument
   */
  public Bid4WinCollectionAbstract(Collection<? extends OBJECT> collection, int additionalCapacity)
  {
    // Si la capacité initiale supplémentaire fournie est inférieure à 0, on
    // n'en prendra pas compte
    this(collection.size() + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.addAll(collection);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection. La
   * collection construite aura comme capacité initiale la taille du tableau en
   * argument
   * @param objects Objets à ajouter à la collection
   */
  public Bid4WinCollectionAbstract(OBJECT ... objects)
  {
    this(objects, 0);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la collection avec
   * précision de la capacité initiale supplémentaire de la collection par rapport
   * à la taille du tableau
   * @param array Tableau d'objets à ajouter à la collection
   * @param additionalCapacity Capacité initiale supplémentaire de la collection
   * par rapport à la taille du tableau
   */
  public Bid4WinCollectionAbstract(OBJECT[] array, int additionalCapacity)
  {
    // Si la capacité initiale supplémentaire fournie est inférieure à 0, on
    // n'en prendra pas compte
    this(array.length + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.addAll(array);
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
  public Bid4WinCollectionAbstract(COLLECTION_TYPE collection, boolean useAsInternal)
  {
    this(collection, useAsInternal, 0);
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
  public Bid4WinCollectionAbstract(COLLECTION_TYPE collection, boolean useAsInternal, int additionalCapacity)
  {
    super(null);
    COLLECTION_TYPE internal = collection;
    if(!useAsInternal)
    {
      internal = this.createInternalCollection(collection.size() +
                                               (additionalCapacity < 0 ? 0 : additionalCapacity));
      internal.addAll(collection);
    }
    this.setInternalCollection(internal);
  }

  /**
   * Ajoute l'objet en argument à la collection interne
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la collection est protégée contre les modifications
   * @see java.util.Collection#add(java.lang.Object)
   */
  @Override
  public boolean add(OBJECT toBeAdded) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalCollection().add(toBeAdded);
  }
  /**
   * Ajoute la collection d'objets en argument à la collection interne
   * @param toBeAdded {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la collection est protégée contre les modifications
   * @see java.util.Collection#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends OBJECT> toBeAdded) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalCollection().addAll(toBeAdded);
  }
  /**
   * Ajoute le tableau d'objets en argument à la collection interne
   * @param toBeAdded Objets à ajouter à la collection interne
   * @throws ProtectionException Si la collection est protégée contre les modifications
   */
  public void addAll(OBJECT ... toBeAdded) throws ProtectionException
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
   * @throws ProtectionException Si la collection est protégée contre les modifications
   * @see java.util.Collection#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object toBeRemoved) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalCollection().remove(toBeRemoved);
  }
  /**
   * Retire la collection d'objets en argument de la collection interne
   * @param toBeRemoved {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la collection est protégée contre les modifications
   * @see java.util.Collection#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> toBeRemoved) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalCollection().removeAll(toBeRemoved);
  }
  /**
   * Retire tous les objets ne se trouvant pas dans la collection d'objets en
   * argument de la collection interne
   * @param toBeRetained {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException Si la collection est protégée contre les modifications
   * @see java.util.Collection#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> toBeRetained) throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalCollection().retainAll(toBeRetained);
  }
  /**
   * Vide la collection interne
   * @throws ProtectionException Si la collection est protégée contre les modifications
   * @see java.util.Collection#clear()
   */
  @Override
  public void clear() throws ProtectionException
  {
    this.checkProtection();
    this.getInternalCollection().clear();
  }
  /**
   * Vérifie si la collection interne contient l'objet en argument
   * @param toBeTested {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object toBeTested)
  {
    return this.getInternalCollection().contains(toBeTested);
  }
  /**
   * Vérifie si la collection interne contient tous les objets de la collection
   * en argument
   * @param toBeTested {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Collection#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection<?> toBeTested)
  {
    return this.getInternalCollection().containsAll(toBeTested);
  }
  /**
   * Retourne le code de hachage de la collection interne
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return this.getInternalCollection().hashCode();
  }
  /**
   * Test l'égalité entre la collection interne et l'objet en argument
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object toBeCompared)
  {
    if(toBeCompared == null ||
       !(this.getInternalCollectionClass().isInstance(toBeCompared) ||
         toBeCompared instanceof Bid4WinCollectionAbstract))
    {
      return false;
    }
    if(toBeCompared instanceof Bid4WinCollectionAbstract)
    {
      return this.getInternalCollection().equals(((Bid4WinCollectionAbstract<?, ?, ?>)toBeCompared).getInternalCollection());
    }
    return this.getInternalCollection().equals(toBeCompared);
  }
  /**
   * Vérifie si la collection interne est vide
   * @return {@inheritDoc}
   * @see java.util.Collection#isEmpty()
   */
  @Override
  public boolean isEmpty()
  {
    return this.getInternalCollection().isEmpty();
  }
  /**
   * Retourne la taille de la collection interne
   * @return {@inheritDoc}
   * @see java.util.Collection#size()
   */
  @Override
  public int size()
  {
    return this.getInternalCollection().size();
  }
  /**
   * Retourne l'itérateur d'objets de la collection interne. Attention, les actions
   * sur celui-ci se répercuterons sur la collection et inversement et il sera
   * protégé identiquement à la collection dont il est issu
   * @return {@inheritDoc}
   * @see java.util.Collection#iterator()
   */
  @Override
  public Bid4WinIterator<OBJECT> iterator()
  {
    Bid4WinIterator<OBJECT> iterator = new Bid4WinIterator<OBJECT>(
        this.getInternalCollection().iterator());
    // Protège l'itérateur comme la collection dont il est issu
    iterator.protect(this.getProtection());
    return iterator;
  }
  /**
   * Retourne l'itérateur d'objets de la collection interne. Attention, cet itérateur
   * sera automatiquement protégé contre toute modification.
   * @return Un itérateur protégé sur les éléments de la collection courante
   */
  public Bid4WinIterator<OBJECT> iteratorProtected()
  {
    Bid4WinIterator<OBJECT> iterator = new Bid4WinIterator<OBJECT>(
        this.getInternalCollection().iterator());
    // Protège l'itérateur contre toute modification
    iterator.protectFromNothing();
    return iterator;
  }
  /**
   * Retourne un tableau contenant tous les objets de la collection interne
   * @return {@inheritDoc}
   * @see java.util.Collection#toArray()
   */
  @Override
  public Object[] toArray()
  {// TODO voir si reprendre de TypifiedList ...
    return this.getInternalCollection().toArray();
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
    return this.getInternalCollection().toArray(array);
  }

  /**
   * Défini le clonage d'une collection. Cette nouvelle collection sera remplie
   * des mêmes elements que la collection clonée mais leurs evolutions seront
   * décorrélées
   * @return {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  @Override
  public CLASS clone()
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
  public CLASS clone(boolean immutable)
  {
    try
    {
      if(!immutable)
      {
        return (CLASS)this.getClass().getDeclaredConstructor(Collection.class)
            .newInstance(this.getInternalCollection());
      }
      CLASS clone = (CLASS)this.getClass().getDeclaredConstructor(this.getInternalCollectionClass(),
                                                                  boolean.class)
          .newInstance(this.getInternalCollection(), true);
      clone.protectFromNothing();
      return clone;
    }
    catch(Throwable ex)
    {
      throw new Bid4WinRuntimeException(ex);
    }
  }

  /**
   * Redéfini la méthode toString() pour rendre plus lisible toute collection
   * @return {@inheritDoc}
   * @see java.util.AbstractCollection#toString()
   */
  @Override
  public String toString()
  {
    return this.render().toString();
  }
  /**
   * Redéfini la méthode toString() pour rendre plus lisible toute collection
   * @return La chaîne de caractères représentant lisiblement la collection courante
   */
  public StringBuffer render()
  {
    return Bid4WinCollectionRenderer.getInstanceCollection().renderMultipleLine(this);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract Class<COLLECTION_TYPE> getInternalCollectionClass();
  /**
   * Getter de la collection interne sur laquelle se base la collection courante
   * @return La collection interne sur laquelle se base la collection courante
   */
  private COLLECTION_TYPE getInternalCollection()
  {
    return this.internal;
  }
  /**
   * Setter de la collection interne sur laquelle doit se baser la collection courante
   * @param internal Collection interne à positionner
   * @throws ProtectionException Si la liste interne est déjà définie
   */
  protected void setInternalCollection(COLLECTION_TYPE internal) throws ProtectionException
  {
    if(this.internal != null)
    {
      ProtectionException.protectedObject(this.getClass(), 1);
    }
    this.internal = internal;
  }
  /**
   * Cette méthode permet de créer une collection pouvant être utilisée comme
   * collection interne par la collection courante
   * @param initialCapacity Capacité initiale de la collection à créer
   * @return La collection créée
   */
  protected abstract COLLECTION_TYPE createInternalCollection(int initialCapacity);
  /**
   * Cette méthode permet de récupérer la collection interne sur laquelle se base
   * la collection courante avec vérification de la protection de cette dernière
   * @return La collection interne sur laquelle se base la collection courante
   * @throws ProtectionException Si la collection courante est protégée
   */
  public final COLLECTION_TYPE getInternal() throws ProtectionException
  {
    this.checkProtection();
    return this.getInternalCollection();
  }
}