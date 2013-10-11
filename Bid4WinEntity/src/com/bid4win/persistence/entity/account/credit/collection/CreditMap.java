package com.bid4win.persistence.entity.account.credit.collection;

import java.util.Map;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.price.Amount;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class CreditMap extends Bid4WinMap<CreditBundle, Integer>
{
  /** TODO A COMMENTER */
  private static final long serialVersionUID = -5371046159364280820L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public CreditMap()
  {
    super(0);
  }
  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la map
   */
  public CreditMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map. La map construite aura une capacité initiale de un
   * @param bundle Lot de provenance des crédits
   * @param nb Nombre de crédits
   */
  public CreditMap(CreditBundle bundle, Integer nb)
  {
    super(bundle, nb);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer à remplir
   * la map avec précision de sa capacité initiale. Si la capacité initiale fournie
   * est inférieure à un, on utilisera la capacité initiale par défaut
   * @param bundle Lot de provenance des crédits
   * @param nb Nombre de crédits
   * @param initialCapacity Capacité initiale de la map
   */
  public CreditMap(CreditBundle bundle, Integer nb, int initialCapacity)
  {
    super(bundle, nb, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacité initiale la taille de la map en argument
   * @param map Map d'objets à ajouter à la map à construire
   */
  public CreditMap(Map<? extends CreditBundle, ? extends Integer> map)
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
  public CreditMap(Map<? extends CreditBundle, ? extends Integer> map, int additionalCapacity)
  {
    // Si la capacité initiale supplémentaire fournie est inférieure à 0, on
    // n'en prendra pas compte
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
  public CreditMap(Map<CreditBundle, Integer> map, boolean useAsInternal)
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
  public CreditMap(Map<CreditBundle, Integer> map, boolean useAsInternal, int additionalCapacity)
  {
    super(map, useAsInternal, additionalCapacity);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getTotalNb()
  {
    int result = 0;
    for(Integer nb : this.values())
    {
      if(nb > 0)
      {
        result += nb;
      }
    }
    return result;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public Amount getTotalValue() throws UserException
  {
    Amount result = new Amount(0);
    for(Entry<CreditBundle, Integer> entry : this.entrySet())
    {
      int nb = entry.getValue();
      if(nb > 0)
      {
        result = result.add(entry.getKey().getUnitValue().multiply(nb));
      }
    }
    return result;
  }
}
