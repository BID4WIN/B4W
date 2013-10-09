package com.bid4win.commons.core.collection;

import java.util.Map;

/**
 * Cette classe est la classe de base de toute map du projet dont les types des
 * cl� et des valeurs sont identiques<BR>
 * <BR>
 * @param <TYPE> D�finition du type des cl�s et valeurs contenues dans la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinSameTypeMap<TYPE> extends Bid4WinMap<TYPE, TYPE>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 8576366934273041760L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public Bid4WinSameTypeMap()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinSameTypeMap(int initialCapacity)
  {
    // Si la capacit� initiale est inf�rieure � un, on utilisera la capacit�
    // initiale par d�faut
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. La map construite aura une capacit� initiale de un
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param value Valeur � ajouter � la map
   */
  public Bid4WinSameTypeMap(TYPE key, TYPE value)
  {
    super(key, value);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map avec pr�cision de sa capacit� initiale. Si la capacit� initiale fournie
   * est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param key Cl� r�f�ren�ant la valeur � ajouter � la map
   * @param value Valeur � ajouter � la map
   * @param initialCapacity Capacit� initiale de la map
   */
  public Bid4WinSameTypeMap(TYPE key, TYPE value, int initialCapacity)
  {
    super(key, value, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacit� initiale la taille de la map en argument
   * @param map Map d'objets � ajouter � la map � construire
   */
  public Bid4WinSameTypeMap(Map<? extends TYPE, ? extends TYPE> map)
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
  public Bid4WinSameTypeMap(Map<? extends TYPE, ? extends TYPE> map, int additionalCapacity)
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
  public Bid4WinSameTypeMap(Map<TYPE, TYPE> map, boolean useAsInternal)
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
  public Bid4WinSameTypeMap(Map<TYPE, TYPE> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la map. La map
   * construite aura comme capacit� initiale la taille de la premi�re dimension
   * du tableau en argument et recherchera les couples cl�/valeur suivant la deuxi�me
   * @param keysAndValues Tableau de couples cl�/valeur � ajouter � la map � construire
   */
  public Bid4WinSameTypeMap(TYPE[][] keysAndValues)
  {
    super(keysAndValues.length);
    this.putAll(keysAndValues);
  }
  /**
   * Constructeur utilisant le tableau en argument pour remplir la map avec pr�cision
   * de la capacit� initiale suppl�mentaire de la map par rapport � la taille de
   * la premi�re dimension du tableau  en argument et recherchera les couples
   * cl�/valeur suivant la deuxi�me
   * @param keysAndValues Tableau de couples cl�/valeur � ajouter � la map � construire
   * @param additionalCapacity Capacit� initiale suppl�mentaire de la map par rapport
   * � la taille de la premi�re dimension du tableau en argument
   */
  public Bid4WinSameTypeMap(TYPE[][] keysAndValues, int additionalCapacity)
  {
    super(keysAndValues.length + (additionalCapacity < 0 ? 0 : additionalCapacity));
    this.putAll(keysAndValues);
  }

  /**
   * Cette m�thode permet d'ajouter tableau en argument pour remplir la map en
   * recherchant les couples cl�/valeur suivant la deuxi�me dimension du tableau
   * @param keysAndValues Tableau de couples cl�/valeur � ajouter � la map � construire
   */
  public void putAll(TYPE[][] keysAndValues)
  {
    for(int i = 0 ; i < keysAndValues.length ; i++)
    {
      this.put(keysAndValues[i][0], keysAndValues[i][1]);
    }
  }
}
