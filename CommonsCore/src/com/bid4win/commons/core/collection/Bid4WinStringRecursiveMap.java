package com.bid4win.commons.core.collection;

import java.util.Map;

/**
 * Cette classe d�fini une map contenant r�cursivement des maps de m�me type<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinStringRecursiveMap
       extends Bid4WinRecursiveMap<Bid4WinStringRecursiveMap, String>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 6878947812470934543L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinStringRecursiveMap()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinStringRecursiveMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. La map construite aura une capacit� initiale de un
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param map Valeur � ajouter � la map
   */
  public Bid4WinStringRecursiveMap(String key, Bid4WinStringRecursiveMap map)
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
  public Bid4WinStringRecursiveMap(String key, Bid4WinStringRecursiveMap map, int initialCapacity)
  {
    super(key, map, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacit� initiale la taille de la map en argument
   * @param map Map d'objets � ajouter � la map � construire
   */
  public Bid4WinStringRecursiveMap(Map<? extends String, ? extends Bid4WinStringRecursiveMap> map)
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
  public Bid4WinStringRecursiveMap(Map<? extends String, ? extends Bid4WinStringRecursiveMap> map, int additionalCapacity)
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
  public Bid4WinStringRecursiveMap(Map<String, Bid4WinStringRecursiveMap> map, boolean useAsInternal)
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
  public Bid4WinStringRecursiveMap(Map<String, Bid4WinStringRecursiveMap> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }
}
