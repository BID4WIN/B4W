package com.bid4win.persistence.dao.account;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountAbstractDao_;
import com.bid4win.commons.persistence.entity.Bid4WinField;
import com.bid4win.commons.persistence.request.data.Bid4WinData;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.Account_Fields;
import com.bid4win.persistence.entity.connection.Connection;
import com.bid4win.persistence.entity.connection.ConnectionHistory;

/**
 * DAO pour les entités de la classe Account<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("AccountDao")
@Scope("singleton")
public class AccountDao extends AccountAbstractDao_<Account, Connection, ConnectionHistory>
{
  /**
   * Constructeur
   */
  protected AccountDao()
  {
    super(Account.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @param sponsor TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinList<Account> findListBySponsor(Account sponsor) throws PersistenceException
  {
    if(sponsor == null)
    {
      return new Bid4WinList<Account>(0);
    }
    return this.findListBySponsorId(sponsor.getId());
  }
  /**
   *
   * TODO A COMMENTER
   * @param sponsorId TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  public Bid4WinList<Account> findListBySponsorId(String sponsorId) throws PersistenceException
  {
    return super.findList(this.getSponsorIdData(sponsorId), null);
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher les
   * comptes utilisateur dont l'identifiant du parrain est précisé en argument
   * @param sponsorId Identifiant du parrain des comptes utilisateur à rechercher
   * @return Les critères permettant de rechercher les comptes utilisateur en fonction
   * de l'identifiant de leur parrain
   */
  protected Bid4WinData<Account, String> getSponsorIdData(String sponsorId)
  {
    return new Bid4WinData<Account, String>(Account_Fields.SPONSOR_ID, sponsorId);
  }

  /***
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getLoginValueField()
   */
  @Override
  protected Bid4WinField<Account, ?, String, ?> getLoginValueField()
  {
    return Account_Fields.LOGIN_VALUE;
  }
  /**
   * A définir car :
   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
   * en compte si défini dans le metamodel de la super class : bug HHH-5024
   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountAbstractDao_#getEmailAddressField()
   */
  @Override
  protected Bid4WinField<Account, ?, String, ?> getEmailAddressField()
  {
    return Account_Fields.EMAIL_ADDRESS;
  }
}
