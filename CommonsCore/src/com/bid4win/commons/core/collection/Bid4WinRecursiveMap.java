package com.bid4win.commons.core.collection;

import java.util.Map;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe d�fini une map contenant r�cursivement des maps de m�me type<BR>
 * <BR>
 * @param <MAP> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <KEY> D�finition du type des valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinRecursiveMap<MAP extends Bid4WinRecursiveMap<MAP, KEY>, KEY>
       extends Bid4WinMap<KEY, MAP>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 8215107521531762123L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinRecursiveMap()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinRecursiveMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. La map construite aura une capacit� initiale de un
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param map Valeur � ajouter � la map
   */
  public Bid4WinRecursiveMap(KEY key, MAP map)
  {
    super(key, map);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map avec pr�cision de sa capacit� initiale. Si la capacit� initiale fournie
   * est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param map Valeur � ajouter � la map
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinRecursiveMap(KEY key, MAP map, int initialCapacity)
  {
    super(key, map, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacit� initiale la taille de la map en argument
   * @param map Map d'objets � ajouter � la map � construire
   */
  public Bid4WinRecursiveMap(Map<? extends KEY, ? extends MAP> map)
  {
    super(map);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map avec pr�cision
   * de la capacit� initiale suppl�mentaire de la map par rapport � la taille de
   * celle en argument
   * @param map Map d'objets � ajouter � la map � construire
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la map par rapport
   * � la taille de celle en argument
   */
  public Bid4WinRecursiveMap(Map<? extends KEY, ? extends MAP> map, int additionalCapacity)
  {
    super(map, additionalCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilis�e comme map
   * interne, la map construite aura comme capacit� initiale la taille de la map
   * en argument et sera remplie avec les �l�ments de celle-ci
   * @param map Map d'objets � ajouter � la map � construire
   * @param useAsInternal Flag indiquant si la map en argument doit �tre utilis�e
   * comme map interne ou non
   */
  public Bid4WinRecursiveMap(Map<KEY, MAP> map, boolean useAsInternal)
  {
    super(map, useAsInternal);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map en l'utilisant
   * comme map interne ou non. Si la map fournie n'est pas utilis�e comme map
   * interne, la map construite aura comme capacit� initiale la taille de la map
   * en argument augment�e de la capacit� initiale suppl�mentaire choisie et sera
   * remplie avec les �l�ments de celle-ci
   * @param map Map d'objets � ajouter � la map � construire
   * @param useAsInternal Flag indiquant si la map en argument doit �tre utilis�e
   * comme map interne ou non
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la map par rapport
   * � la taille de celle en argument dans le cas o� celle-ci n'est pas utilis�e
   * comme map interne
   */
  public Bid4WinRecursiveMap(Map<KEY, MAP> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }

  /**
   * Cette m�thode permet d'ajouter r�cursivement le contenu de la map en param�tre
   * � celui de la map courante. Si des maps sont d�j� d�fini pour certaines cl�,
   * le contenu de celles correspondantes en param�tre leur sera r�cursivement
   * ajout�
   * @param toBeAdded Map dont le contenu doit �tre ajout� r�cursivement � celui
   * de la map en param�tre
   * @throws ProtectionException Si la map courante ou celle � laquelle ajouter
   * les valeurs de celle en argument sont prot�g�es contre les modifications
   */
  public void add(MAP toBeAdded) throws ProtectionException
  {
    for(Entry<KEY, MAP> entry : toBeAdded.entrySet())
    {
      this.add(entry.getKey(), entry.getValue());
    }
  }
  /**
   * Cette m�thode permet d'ajouter la map en param�tre. Si une map est d�j� d�j�
   * r�f�renc�e avec la m�me cl�, le contenu de celle en param�tre lui sera r�cursivement
   * ajout�
   * @param key Cl� pour laquelle ajouter la map en param�tre
   * @param value Map � ajouter
   * @return La map ajout�e si aucune n'�tait r�f�renc�e ou celle d�j� pr�sente
   * compl�t�e du contenu de celle en param�tre ajout� r�cursivement
   * @throws ProtectionException Si la map courante ou celle � laquelle ajouter
   * les valeurs de celle en argument sont prot�g�es contre les modifications
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
