package com.bid4win.persistence.dao.account.credit.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.dao.account.AccountDao;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditUsage;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuction;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementAuctionHistory;
import com.bid4win.persistence.entity.auction.AuctionAbstract;

/**
 * DAO pour les entit�s de la classe CreditInvolvementAuction<BR>
 * <BR>
 * @param <INVOLVEMENT> D�finition du type d'implication g�r�e par le DAO<BR>
 * @param <USAGE> D�finition du type d'utilisation de cr�dits des implications
 * g�r�es par le DAO<BR>
 * @param <AUCTION> D�finition du type de vente aux ench�res des implications g�r�es
 * par le DAO<BR>
 * @param <HISTORY> D�finition du type d'historique des implications g�r�es par
 * le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class CreditInvolvementAuctionDao_<INVOLVEMENT extends CreditInvolvementAuction<INVOLVEMENT, USAGE, AUCTION, HISTORY>,
                                                   USAGE extends CreditUsage<USAGE, INVOLVEMENT>,
                                                   AUCTION extends AuctionAbstract<AUCTION, ?, ?, ?, ?>,
                                                   HISTORY extends CreditInvolvementAuctionHistory<HISTORY, ?, AUCTION, INVOLVEMENT>>
       extends CreditInvolvementAuctionAbstractDao_<INVOLVEMENT, USAGE, CreditBundle, AUCTION, HISTORY>
{
  /** R�f�rence du DAO des comptes utilisateur */
  @Autowired
  @Qualifier("AccountDao")
  private AccountDao accountDao;

  /**
   * Constructeur
   * @param involvementClass Classe de l'implication de cr�dits g�r�e par le DAO
   */
  protected CreditInvolvementAuctionDao_(Class<INVOLVEMENT> involvementClass)
  {
    super(involvementClass);
  }

  /**
   * Cette fonction permet d'ajouter l'implication en argument ainsi que toutes
   * ses utilisations de cr�dits et de mettre � jour le compte utilisateur correspondant
   * @param involvement {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#add(com.bid4win.persistence.entity.account.credit.CreditInvolvement)
   */
  @Override
  public INVOLVEMENT add(INVOLVEMENT involvement) throws PersistenceException
  {
    // Ajoute le l'implication de cr�dits en param�tre
    involvement = super.add(involvement);
    // Met � jour le compte utilisateur li�
    this.getAccountDao().update(involvement.getAccount());
    return involvement;
  }
  /**
   * Cette fonction permet de modifier l'implication en argument ainsi que toutes
   * ses utilisations de cr�dits (les nouvelles seront elles ajout�es)
   * @param involvement {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.persistence.dao.account.credit.CreditInvolvementDao_#update(com.bid4win.persistence.entity.account.credit.CreditInvolvement)
   */
  @Override
  public INVOLVEMENT update(INVOLVEMENT involvement) throws PersistenceException
  {
    return super.update(involvement);
  }

  /**
   * Cette m�thode permet d'historiser toutes les implications d'une vente aux ench�res
   * @param auction Vente aux ench�res des implications � historiser
   * @return Les implications historis�es
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException TODO A COMMENTER
   */
  public Bid4WinList<INVOLVEMENT> historize(AUCTION auction)
         throws PersistenceException, UserException
  {
    Bid4WinList<INVOLVEMENT> list  = this.findListByAuction(auction);
    for(INVOLVEMENT involvement : list)
    {
      this.historize(involvement);
    }
    return list;
  }

  /**
   * Getter du DAO des comptes utilisateur
   * @return Le DAO des comptes utilisateur
   */
  protected AccountDao getAccountDao()
  {
    return this.accountDao;
  }
}
