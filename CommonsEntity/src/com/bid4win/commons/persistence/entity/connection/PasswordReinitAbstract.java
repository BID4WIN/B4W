package com.bid4win.commons.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;

/**
 * Cette classe d�fini une r�-initialisation de mot de passe<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur du mot de passe �
 * r�initialiser<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class PasswordReinitAbstract<CLASS extends PasswordReinitAbstract<CLASS, ACCOUNT>,
                                    ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedKey<CLASS, ACCOUNT>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected PasswordReinitAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur du mot de passe � r�initialiser
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public PasswordReinitAbstract(ACCOUNT account) throws UserException
  {
    super(account);
  }
}
