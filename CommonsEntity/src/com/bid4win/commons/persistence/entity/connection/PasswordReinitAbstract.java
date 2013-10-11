package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;

/**
 * Cette classe défini une ré-initialisation de mot de passe<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur du mot de passe à
 * réinitialiser<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class PasswordReinitAbstract<CLASS extends PasswordReinitAbstract<CLASS, ACCOUNT>,
                                    ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedKey<CLASS, ACCOUNT>
{
  /**
   * Constructeur pour création par introspection
   */
  protected PasswordReinitAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur du mot de passe à réinitialiser
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public PasswordReinitAbstract(ACCOUNT account) throws UserException
  {
    super(account);
  }
}
