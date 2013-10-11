package com.bid4win.persistence.dao.connection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.persistence.dao.connection.PasswordReinitAbstractDao_;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.connection.PasswordReinit;

/**
 * DAO pour les entités de la classe PasswordReinit<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("PasswordReinitDao")
@Scope("singleton")
public class PasswordReinitDao extends PasswordReinitAbstractDao_<PasswordReinit, Account>
{
  /**
   * Constructeur
   */
  protected PasswordReinitDao()
  {
    super(PasswordReinit.class);
  }
}
