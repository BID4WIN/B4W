package com.bid4win.persistence.dao.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedKeyDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.Subscription;

/**
 * DAO pour les entités de la classe Subscription<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("SubscriptionDao")
@Scope("singleton")
public class SubscriptionDao extends AccountBasedKeyDao_<Subscription, Account>
{
  /**
   * Constructeur
   */
  protected SubscriptionDao()
  {
    super(Subscription.class);
  }

  /**
   * Cette fonction permet de modifier l'inscription en argument
   * @param subscription {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public Subscription update(Subscription subscription) throws PersistenceException
  {
    // Modifie l'inscription en paramètre
    return super.update(subscription);
  }
}
