package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Bot;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
public class PrebookedBot extends Bot<PrebookedBot, PrebookedAuction, PrebookedBid>
{
  /**
   * Constructeur pour création par introspection
   */
  protected PrebookedBot()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du robot d'enchère
   * @param auction Vente du robot d'enchères
   * @param minBidPosition Position minimum autorisée des enchères du robot dans
   * la vente
   * @param maxBidPosition Position maximum autorisée des enchères du robot dans
   * la vente
   * @param maxBidNb Nombre d'enchères maximum autorisées pour le robot sur la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul, si la plage
   * d'enchères est invalide ou si le nombre maximum d'enchères est inférieur à un
   */
  public PrebookedBot(Account account, PrebookedAuction auction,
                   int minBidPosition, int maxBidPosition, int maxBidNb)
         throws UserException
  {
    super(account, auction, minBidPosition, maxBidPosition, maxBidNb);
  }
}
