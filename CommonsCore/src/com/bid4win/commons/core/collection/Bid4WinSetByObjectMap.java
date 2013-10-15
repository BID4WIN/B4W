package com.bid4win.commons.core.collection;

import java.util.Map;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe permet d'organiser des listes d'objets par cl�<BR>
 * <BR>
 * @param <KEY> D�finition du type des cl�s contenues dans la map<BR>
 * @param <TYPE> D�finition du type des objets contenus dans les sets de valeurs
 * de la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinSetByObjectMap<KEY, TYPE> extends Bid4WinMap<KEY, Bid4WinSet<TYPE>>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 6184491593925450946L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinSetByObjectMap()
  {
    // Utilisation de la capacit� initiale par d�faut
    super();
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinSetByObjectMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. L'objet sera encapsul� dans un set du type d�fini par la map et c'est
   * en fait ce set qui sera ajout� � la map et potentiellement fusionn� avec un
   * set d�j� r�f�renc�. La map construite aura une capacit� initiale de un
   * @param key Cl� r�f�ren�ant le set � ajouter � la map
   * @param object Objet � encapsuler dans un set avant d'�tre ajout� � la map
   */
  public Bid4WinSetByObjectMap(KEY key, TYPE object)
  {
    this(key, object, 1);
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale utilisant le couple d'
   * objets en argument pour commencer � remplir la map. L'objet sera encapsul�
   * dans un set du type d�fini par la map et c'est en fait ce set qui sera ajout�
   * � la map et potentiellement fusionn� avec un set d�j� r�f�renc�. Si la capacit�
   * initiale fournie est inf�rieure � un, on utilisera la capacit� initiale par
   * d�faut
   * @param key Cl� r�f�ren�ant le set � ajouter � la map
   * @param object Objet � encapsuler dans un set avant d'�tre ajout� � la map
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinSetByObjectMap(KEY key, TYPE object, int initialCapacity)
  {
    super(initialCapacity);
    this.putObject(key, object);
  }

  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. Le set sera ajout� � la map et potentiellement fusionn� avec un set
   * d�j� r�f�renc�. La map construite aura une capacit� initiale de un
   * @param key Cl� r�f�ren�ant le set � ajouter � la map
   * @param value Set � ajouter � la map
   */
  public Bid4WinSetByObjectMap(KEY key, Bid4WinSet<TYPE> value)
  {
    super(key, value);
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale utilisant le couple d'
   * objets en argument pour commencer � remplir la map. Le set sera ajout� � la
   * map et potentiellement fusionn� avec un set d�j� r�f�renc�. Si la capacit�
   * initiale fournie est inf�rieure � un, on utilisera la capacit� initiale par
   * d�faut
   * @param key Cl� r�f�ren�ant le set � ajouter � la map
   * @param value Set � ajouter � la map
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinSetByObjectMap(KEY key, Bid4WinSet<TYPE> value, int initialCapacity)
  {
    super(key, value, initialCapacity);
  }

  /**
   * Cette m�thode red�fini celle de la classe m�re afin de ne jamais remonter de
   * set nul mais un set vide a la place
   * @param key {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.HashMap#get(java.lang.Object)
   */
  @Override
  public Bid4WinSet<TYPE> get(Object key)
  {
    Bid4WinSet<TYPE> value = super.get(key);
    if(value == null)
    {
      value = new Bid4WinSet<TYPE>();
    }
    return value;
  }

  /**
   * Cette m�thode permet d'ajouter directement un objet � la map. L'objet sera
   * d'abord encapsul� dans un set du type d�fini par la map et c'est en fait ce
   * set qui sera ajout� � la map et potentiellement fusionn� avec un set d�j�
   * r�f�renc�
   * @param key Cl� r�f�ren�ant le set � ajouter � la map
   * @param object Objet � encapsuler dans un set avant d'�tre ajout� � la map
   * @return Le set encapsulant l'objet ajout� potentiellement compl�t� des objets
   * d�j� contenus avec la m�me cl�
   */
  public Bid4WinSet<TYPE> putObject(KEY key, TYPE object)
  {
    return this.put(key, new Bid4WinSet<TYPE>(object));
  }

  /**
   * Cette m�thode red�fini celle de la classe m�re afin de ne pas �craser le set
   * d�j� existant mais d'y rajouter le contenu de celui en argument
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return Le set issu de l'ajout aux donn�es d�j� r�f�renc�es, celles en argument
   * @throws IllegalArgumentException {@inheritDoc}
   * @throws ProtectionException Si la map ou le set auquel ajouter les valeurs
   * de celui en argument sont prot�g�s contre les modifications
   * @see com.bid4win.commons.core.collection.Bid4WinMap#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public Bid4WinSet<TYPE> put(KEY key, Bid4WinSet<TYPE> value) throws ProtectionException
  {
    // R�cup�re le set potentiellement existant pour cette cl�
    Bid4WinSet<TYPE> set = this.get(key);
    if(set.size() == 0)
    {
      // Ajoute directement le set une fois clon�
      set = value.clone();
      set.protect(this.getProtection());
      super.put(key, set);
    }
    else
    {
      // Ajoute le contenu du set au set d�j� pr�sent
      set.addAll(value);
    }
    return set;
  }

  /**
   * Cette m�thode red�fini celle de la classe m�re afin de ne pas �craser les
   * sets d�j� existant mais d'y rajouter le contenu de ceux en argument
   * @param toBePut {@inheritDoc}
   * @throws ProtectionException Si la map ou le set auquel ajouter les valeurs
   * de celui en argument sont prot�g�s contre les modifications
   * @see com.bid4win.commons.core.collection.Bid4WinMap#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends KEY, ? extends Bid4WinSet<TYPE>> toBePut)
         throws ProtectionException
  {
    // Ajoute les sets un par un pour les regrouper
    for(Entry<? extends KEY, ? extends Bid4WinSet<TYPE>> entry : toBePut.entrySet())
    {
      this.put(entry.getKey(), entry.getValue());
    }
  }
}
