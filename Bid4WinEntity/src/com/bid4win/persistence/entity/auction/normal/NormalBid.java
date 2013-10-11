package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.auction.Bid;

/**
 *
 * Cette classe défini une enchère placée sur une vente normale<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class NormalBid extends Bid<NormalBid, NormalAuction, NormalBidHistory>
{
  /**
   * Constructeur pour création par introspection
   */
  protected NormalBid()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'enchère
   * @auction Vente de l'enchère normale
   * @position Position de l'enchère dans la vente normale
   * @throws ProtectionException Si la vente aux enchères est protégée
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inférieure à un
   */
  protected NormalBid(Account account, NormalAuction auction, int position)
            throws ProtectionException, UserException
  {
    super(account, auction, position);
  }

  /**
   * Cette méthode permet de créer l'historique associée à l'enchère courante
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.auction.BidAbstract#toHistory()
   */
  @Override
  public NormalBidHistory toHistory() throws UserException
  {
    return new NormalBidHistory(this);
  }
}
