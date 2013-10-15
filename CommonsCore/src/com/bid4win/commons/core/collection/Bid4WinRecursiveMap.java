package com.bid4win.commons.core.collection;

import java.util.Map;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe défini une map contenant récursivement des maps de même type<BR>
 * <BR>
 * @param <MAP> Doit définir la classe réellement instanciée<BR>
 * @param <KEY> Définition du type des valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinRecursiveMap<MAP extends Bid4WinRecursiveMap<MAP, KEY>, KEY>
       extends Bid4WinMap<KEY, MAP>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 8215107521531762123L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public Bid4WinRecursiveMap()
  {
    super();
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinRecursiveMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. La map construite aura une capacité initiale de un
   * @param key Clé référençant la valeur à ajouter à la map
   * @param map Valeur à ajouter à la map
   */
  public Bid4WinRecursiveMap(KEY key, MAP map)
  {
    super(key, map);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map avec précision de sa capacité initiale. Si la capacité initiale fournie
   * est inférieure à un, on utilisera la capacité initiale par défaut
   * @param key Clé référençant la valeur à ajouter à la map
   * @param map Valeur à ajouter à la map
   * @param initialCapacity Capacité initiale de la map
   */
  public Bid4WinRecursiveMap(KEY key, MAP map, int initialCapacity)
  {
    super(key, map, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacité initiale la taille de la map en argument
   * @param map Map d'objets à ajouter à la map à construire
   */
  public Bid4WinRecursiveMap(Map<? extends KEY, ? extends MAP> map)
  {
    super(map);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map avec précision
   * de la capacité initiale supplémentaire de la map par rapport à la taille de
   * celle en argument
   * @param map Map d'objets à ajouter à la map à construire
   * @param additionalCapacity Capacité initiale supplémentaire de la map par rapport
   * à la taille de celle en argument
   */
  public Bid4WinRecursiveMap(Map<? extends KEY, ? extends MAP> map, int additionalCapacity)
  {
    super(map, additionalCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilisée comme map
   * interne, la map construite aura comme capacité initiale la taille de la map
   * en argument et sera remplie avec les éléments de celle-ci
   * @param map Map d'objets à ajouter à la map à construire
   * @param useAsInternal Flag indiquant si la map en argument doit être utilisée
   * comme map interne ou non
   */
  public Bid4WinRecursiveMap(Map<KEY, MAP> map, boolean useAsInternal)
  {
    super(map, useAsInternal);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilisée comme map
   * interne, la map construite aura comme capacité initiale la taille de la map
   * en argument augmentée de la capacité initiale supplémentaire choisie et sera
   * remplie avec les éléments de celle-ci
   * @param map Map d'objets à ajouter à la map à construire
   * @param useAsInternal Flag indiquant si la map en argument doit être utilisée
   * comme map interne ou non
   * @param additionalCapacity Capacité initiale supplémentaire de la map par rapport
   * à la taille de celle en argument dans le cas où celle-ci n'est pas utilisée
   * comme map interne
   */
  public Bid4WinRecursiveMap(Map<KEY, MAP> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }

  /**
   * Cette méthode permet d'ajouter récursivement le contenu de la map en paramètre
   * à celui de la map courante. Si des maps sont déjà défini pour certaines clé,
   * le contenu de celles correspondantes en paramètre leur sera récursivement
   * ajouté
   * @param toBeAdded Map dont le contenu doit être ajouté récursivement à celui
   * de la map en paramètre
   * @throws ProtectionException Si la map courante ou celle à laquelle ajouter
   * les valeurs de celle en argument sont protégées contre les modifications
   */
  public void add(MAP toBeAdded) throws ProtectionException
  {
    for(Entry<KEY, MAP> entry : toBeAdded.entrySet())
    {
      this.add(entry.getKey(), entry.getValue());
    }
  }
  /**
   * Cette méthode permet d'ajouter la map en paramètre. Si une map est déjà déjà
   * référencée avec la même clé, le contenu de celle en paramètre lui sera récursivement
   * ajouté
   * @param key Clé pour laquelle ajouter la map en paramètre
   * @param value Map à ajouter
   * @return La map ajoutée si aucune n'était référencée ou celle déjà présente
   * complétée du contenu de celle en paramètre ajouté récursivement
   * @throws ProtectionException Si la map courante ou celle à laquelle ajouter
   * les valeurs de celle en argument sont protégées contre les modifications
   */
  @SuppressWarnings({"unchecked", "null"})
  public MAP add(KEY key, MAP value) throws ProtectionException
  {
    MAP existing = this.get(key);
    if(existing == null && value != null)
    {
      existing = (MAP)value.clone();
      existing.protect(this.getProtection());
      this.put(key, value);
      return value;
    }
    if(value == null)
    {
      return value;
    }
    existing.add(value);
    return existing;
  }
}
