package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedKeyDao_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;

/**
 * DAO pour les entités de la classe PasswordReinit<BR>
 * <BR>
 * @param <REINIT> Définition du type de ré-initialisation de mot de passe gérée
 * par le DAO<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à la ré-initialisation
 * de mot de passe gérée par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PasswordReinitAbstractDao_<REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedKeyDao_<REINIT, ACCOUNT>
{
  /**
   * Constructeur
   * @param reinitClass Classe de ré-initialisation de mot de passe gérée par le
   * DAO
   */
  protected PasswordReinitAbstractDao_(Class<REINIT> reinitClass)
  {
    super(reinitClass);
  }

  /**
   * Cette fonction permet de supprimer la ré-initialisation de mot de passe en
   * argument
   * @param passwordReinit {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public REINIT remove(REINIT passwordReinit) throws PersistenceException
  {
    // Supprime la ré-initialisation de mot de passe en paramètre
    return super.remove(passwordReinit);
  }
}
