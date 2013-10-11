package com.bid4win.persistence.entity.auction;

import com.bid4win.commons.persistence.entity.comparator.Bid4WinEntityComparator;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <BOT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class BotListOptimizer<BOT extends Bot<BOT, ? extends Auction<?, ?, ?, ?, ?>, ? extends Bid<?, ?, ?>>>
       extends Bid4WinEntityComparator<BOT>
{
  /**
   *
   * TODO A COMMENTER
   *
   * Cette méthode permet de comparer les objets en argument en utilisant leur valeur
   * de hachage s'ils n'implémentent l'interface Comparable. Elle retournera une
   * valeur négative, positive ou zéro si le premier argument est considéré plus
   * petit que, plus grand que ou égal au second
   * @param bot1 {@inheritDoc}
   * @param bot2 {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.comparator.Bid4WinComparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(BOT bot1, BOT bot2)
  {
    // Pour comparer plus rapidement
    if(bot1 == bot2)
    {
      return 0;
    }
    // Prend en compte les valeurs nulles
    if(bot1 == null)
    {
      return -1;
    }
    else if(bot2 == null)
    {
      return 1;
    }
    // On recherche les critères limitant les robots
    int positionLimit1 = bot1.getPositionIncrementLimit();
    int positionLimit2 = bot2.getPositionIncrementLimit();
    int bidNbLimit1 = bot1.getBidIncrementLimit();
    int bidNbLimit2 = bot2.getBidIncrementLimit();
    boolean blockedByPosition1 = true;
    if(positionLimit1 == Bot.NO_MAX || (positionLimit1 / 2) >= bidNbLimit1)
    {
      blockedByPosition1 = false;
    }
    boolean blockedByPosition2 = true;
    if(positionLimit2 == Bot.NO_MAX || (positionLimit2 / 2) >= bidNbLimit2)
    {
      blockedByPosition2 = false;
    }
    // Si au moins un des robots est plus limité par ses bornes que par son nombre
    // de crédits, il faut l'utiliser en priorité pour maximiser le nombre de crédits
    // utilisés
    if(blockedByPosition1)
    {
      // Les deux robots sont limités par leur borne, il faut donc déterminer celui
      // à utiliser en priorité
      if(blockedByPosition2)
      {
        // On choisi le robot dont la limite est plus basse dans le cas d'une limite différente
        if(positionLimit1 != positionLimit2)
        {
          return positionLimit1 - positionLimit2;
        }
        // On choisi le robot le plus anciennement utilisé dans le cas d'une limite identique
        return bot1.getLastBidPosition() - bot2.getLastBidPosition();
      }
      return -1;
    }
    if(blockedByPosition2)
    {
      return 1;
    }
    // Les deux robots sont limités par le même nombre d'enchères à utiliser
    if(bidNbLimit1 == bidNbLimit2)
    {
      int creditNbLimit1 = bot1.getCreditNbLimit();
      int creditNbLimit2 = bot2.getCreditNbLimit();
      // On va préférer l'utilisateur avec le plus de crédits pour laisser l'option
      // à l'autre utilisateur d'améliorer son robot
      if(creditNbLimit1 != creditNbLimit2)
      {
        return - (creditNbLimit1 - creditNbLimit2);
      }
      // On choisi le robot le plus anciennement utilisé dans le cas d'une limite identique
      return bot1.getLastBidPosition() - bot2.getLastBidPosition();
    }
    // On choisi le robot avec la limite la plus haute
    return - (bidNbLimit1 - bidNbLimit2);
  }

}
