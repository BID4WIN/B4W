package com.bid4win.commons.core.collection;

import java.util.Map;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe permet d'organiser des listes d'objets par clé<BR>
 * <BR>
 * @param <KEY> Définition du type des clés contenues dans la map<BR>
 * @param <TYPE> Définition du type des objets contenus dans les sets de valeurs
 * de la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinSetByObjectMap<KEY, TYPE> extends Bid4WinMap<KEY, Bid4WinSet<TYPE>>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 6184491593925450946L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinSetByObjectMap()
  {
    // Utilisation de la capacité initiale par défaut
    super();
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinSetByObjectMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. L'objet sera encapsulé dans un set du type défini par la map et c'est
   * en fait ce set qui sera ajouté à la map et potentiellement fusionné avec un
   * set déjà référencé. La map construite aura une capacité initiale de un
   * @param key Clé référençant le set à ajouter à la map
   * @param object Objet à encapsuler dans un set avant d'être ajouté à la map
   */
  public Bid4WinSetByObjectMap(KEY key, TYPE object)
  {
    this(key, object, 1);
  }
  /**
   * Constructeur avec précision de la capacité initiale utilisant le couple d'
   * objets en argument pour commencer à remplir la map. L'objet sera encapsulé
   * dans un set du type défini par la map et c'est en fait ce set qui sera ajouté
   * à la map et potentiellement fusionné avec un set déjà référencé. Si la capacité
   * initiale fournie est inférieure à un, on utilisera la capacité initiale par
   * défaut
   * @param key Clé référençant le set à ajouter à la map
   * @param object Objet à encapsuler dans un set avant d'être ajouté à la map
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinSetByObjectMap(KEY key, TYPE object, int initialCapacity)
  {
    super(initialCapacity);
    this.putObject(key, object);
  }

  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. Le set sera ajouté à la map et potentiellement fusionné avec un set
   * déjà référencé. La map construite aura une capacité initiale de un
   * @param key Clé référençant le set à ajouter à la map
   * @param value Set à ajouter à la map
   */
  public Bid4WinSetByObjectMap(KEY key, Bid4WinSet<TYPE> value)
  {
    super(key, value);
  }
  /**
   * Constructeur avec précision de la capacité initiale utilisant le couple d'
   * objets en argument pour commencer à remplir la map. Le set sera ajouté à la
   * map et potentiellement fusionné avec un set déjà référencé. Si la capacité
   * initiale fournie est inférieure à un, on utilisera la capacité initiale par
   * défaut
   * @param key Clé référençant le set à ajouter à la map
   * @param value Set à ajouter à la map
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinSetByObjectMap(KEY key, Bid4WinSet<TYPE> value, int initialCapacity)
  {
    super(key, value, initialCapacity);
  }

  /**
   * Cette méthode redéfini celle de la classe mère afin de ne jamais remonter de
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
   * Cette méthode permet d'ajouter directement un objet à la map. L'objet sera
   * d'abord encapsulé dans un set du type défini par la map et c'est en fait ce
   * set qui sera ajouté à la map et potentiellement fusionné avec un set déjà
   * référencé
   * @param key Clé référençant le set à ajouter à la map
   * @param object Objet à encapsuler dans un set avant d'être ajouté à la map
   * @return Le set encapsulant l'objet ajouté potentiellement complété des objets
   * déjà contenus avec la même clé
   */
  public Bid4WinSet<TYPE> putObject(KEY key, TYPE object)
  {
    return this.put(key, new Bid4WinSet<TYPE>(object));
  }

  /**
   * Cette méthode redéfini celle de la classe mère afin de ne pas écraser le set
   * déjà existant mais d'y rajouter le contenu de celui en argument
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return Le set issu de l'ajout aux données déjà référencées, celles en argument
   * @throws IllegalArgumentException {@inheritDoc}
   * @throws ProtectionException Si la map ou le set auquel ajouter les valeurs
   * de celui en argument sont protégés contre les modifications
   * @see com.bid4win.commons.core.collection.Bid4WinMap#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public Bid4WinSet<TYPE> put(KEY key, Bid4WinSet<TYPE> value) throws ProtectionException
  {
    // Récupère le set potentiellement existant pour cette clé
    Bid4WinSet<TYPE> set = this.get(key);
    if(set.size() == 0)
    {
      // Ajoute directement le set une fois cloné
      set = value.clone();
      set.protect(this.getProtection());
      super.put(key, set);
    }
    else
    {
      // Ajoute le contenu du set au set déjà présent
      set.addAll(value);
    }
    return set;
  }

  /**
   * Cette méthode redéfini celle de la classe mère afin de ne pas écraser les
   * sets déjà existant mais d'y rajouter le contenu de ceux en argument
   * @param toBePut {@inheritDoc}
   * @throws ProtectionException Si la map ou le set auquel ajouter les valeurs
   * de celui en argument sont protégés contre les modifications
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
