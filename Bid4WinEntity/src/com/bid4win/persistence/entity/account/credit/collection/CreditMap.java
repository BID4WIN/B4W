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
 * @author Emeric Fill�tre
 */
public class CreditMap extends Bid4WinMap<CreditBundle, Integer>
{
  /** TODO A COMMENTER */
  private static final long serialVersionUID = -5371046159364280820L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public CreditMap()
  {
    super(0);
  }
  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la map
   */
  public CreditMap(int initialCapacity)
  {
    super(initialCapacity);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map. La map construite aura une capacit� initiale de un
   * @param bundle Lot de provenance des cr�dits
   * @param nb Nombre de cr�dits
   */
  public CreditMap(CreditBundle bundle, Integer nb)
  {
    super(bundle, nb);
  }
  /**
   * Constructeur utilisant le couple d'objets en argument pour commencer � remplir
   * la map avec pr�cision de sa capacit� initiale. Si la capacit� initiale fournie
   * est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param bundle Lot de provenance des cr�dits
   * @param nb Nombre de cr�dits
   * @param initialCapacity Capacit� initiale de la map
   */
  public CreditMap(CreditBundle bundle, Integer nb, int initialCapacity)
  {
    super(bundle, nb, initialCapacity);
  }
  /**
   * Constructeur utilisant la map en argument pour remplir la map. La map construite
   * aura comme capacit� initiale la taille de la map en argument
   * @param map Map d'objets � ajouter � la map � construire
   */
  public CreditMap(Map<? extends CreditBundle, ? extends Integer> map)
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
  public CreditMap(Map<? extends CreditBundle, ? extends Integer> map, int additionalCapacity)
  {
    // Si la capacit� initiale suppl�mentaire fournie est inf�rieure � 0, on
    // n'en prendra pas compte
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
  public CreditMap(Map<CreditBundle, Integer> map, boolean useAsInternal)
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
