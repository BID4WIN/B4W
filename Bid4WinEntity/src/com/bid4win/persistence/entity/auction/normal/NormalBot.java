package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Bot;
import com.bid4win.persistence.entity.price.Amount;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
public class NormalBot extends Bot<NormalBot, NormalAuction, NormalBid>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected NormalBot()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du robot d'ench�re
   * @param auction Vente du robot d'ench�res
   * @param minBidPosition Position minimum autoris�e des ench�res du robot dans
   * la vente
   * @param maxBidPosition Position maximum autoris�e des ench�res du robot dans
   * la vente
   * @param maxBidNb Nombre d'ench�res maximum autoris�es pour le robot sur la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul, si la plage
   * d'ench�res est invalide ou si le nombre maximum d'ench�res est inf�rieur � un
   */
  public NormalBot(Account account, NormalAuction auction,
                   int minBidPosition, int maxBidPosition, int maxBidNb)
         throws UserException
  {
    super(account, auction, minBidPosition, maxBidPosition, maxBidNb);
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du robot d'ench�re
   * @param auction Vente du robot d'ench�res
   * @param minBid Minimum autoris� des ench�res du robot dans la vente ou null
   * pour aucun minimum
   * @param maxBid Maximum autoris� des ench�res du robot dans la vente ou null
   * pour aucun maximum
   * @param maxBidNb Nombre d'ench�res maximum autoris�es pour le robot sur la vente
   * @throws UserException Si le compte utilisateur ou la vente est nul, si la plage
   * d'ench�res est invalide ou si le nombre maximum d'ench�res est inf�rieur � un
   */
  public NormalBot(Account account, NormalAuction auction,
                   Amount minBid, Amount maxBid, int maxBidNb)
         throws UserException
  {
    super(account, auction, minBid, maxBid, maxBidNb);
  }
}
