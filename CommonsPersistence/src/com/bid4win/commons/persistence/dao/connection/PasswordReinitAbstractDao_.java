package com.bid4win.commons.persistence.dao.connection;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.account.AccountBasedKeyDao_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstract;

/**
 * DAO pour les entit�s de la classe PasswordReinit<BR>
 * <BR>
 * @param <REINIT> D�finition du type de r�-initialisation de mot de passe g�r�e
 * par le DAO<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur li� � la r�-initialisation
 * de mot de passe g�r�e par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PasswordReinitAbstractDao_<REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedKeyDao_<REINIT, ACCOUNT>
{
  /**
   * Constructeur
   * @param reinitClass Classe de r�-initialisation de mot de passe g�r�e par le
   * DAO
   */
  protected PasswordReinitAbstractDao_(Class<REINIT> reinitClass)
  {
    super(reinitClass);
  }

  /**
   * Cette fonction permet de supprimer la r�-initialisation de mot de passe en
   * argument
   * @param passwordReinit {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public REINIT remove(REINIT passwordReinit) throws PersistenceException
  {
    // Supprime la r�-initialisation de mot de passe en param�tre
    return super.remove(passwordReinit);
  }
}
