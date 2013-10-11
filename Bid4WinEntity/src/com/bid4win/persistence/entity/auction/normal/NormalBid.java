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
 * Cette classe d�fini une ench�re plac�e sur une vente normale<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class NormalBid extends Bid<NormalBid, NormalAuction, NormalBidHistory>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected NormalBid()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur de l'ench�re
   * @auction Vente de l'ench�re normale
   * @position Position de l'ench�re dans la vente normale
   * @throws ProtectionException Si la vente aux ench�res est prot�g�e
   * @throws UserException Si le compte utilisateur ou la vente est nul ou si la
   * position est inf�rieure � un
   */
  protected NormalBid(Account account, NormalAuction auction, int position)
            throws ProtectionException, UserException
  {
    super(account, auction, position);
  }

  /**
   * Cette m�thode permet de cr�er l'historique associ�e � l'ench�re courante
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
