package com.bid4win.commons.persistence.entity.account;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdPattern;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class AccountBasedKeyStub
       extends AccountBasedKey<AccountBasedKeyStub, AccountAbstractStub>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedKeyStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param idPattern Pattern � utiliser pour la g�n�ration des cl�s
   * @param account Compte utilisateur � lier � la cl�
   * @throws UserException Si le pattern � utiliser pour la g�n�ration des cl�s
   * ou le compte utilisateur en argument est nul
   */
  protected AccountBasedKeyStub(IdPattern idPattern, AccountAbstractStub account)
            throws UserException
  {
    super(idPattern, account);
  }
  /**
   * Constructeur
   * @param account Compte utilisateur � lier � la cl�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public AccountBasedKeyStub(AccountAbstractStub account) throws UserException
  {
    super(account);
  }
}
