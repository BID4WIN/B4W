//package trash;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.manager.account.AccountManager;
//import com.bid4win.model.security.Account;
//import com.bid4win.model.security.Credential;
//
///**
// * Manager de gestion des comptes utilisateur<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class Temp
//{
//  /** C'est l'instance unique utilis�e comme singleton */
//  private static AccountManager instance;
//  static
//  {
//    AccountManager.instance = new AccountManager();
//  }
//  /**
//   * Le manager est un singleton. On passe donc par cette m�thode pour r�cup�rer
//   * l'unique objet en m�moire
//   * @return L'instance du manager
//   */
//  public static AccountManager getInstance()
//  {
//    return AccountManager.instance;
//  }
//
//  private Object USER_SYNCH = new Object();
//  private Object LOCK_SYNCH = new Object();
//
//  /**
//   * Constructeur prot�g� car accessible seulement comme un singleton
//   */
//  protected AccountManager()
//  {
//    super();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer un compte utilisateur en fonction de son
//   * certificat de connexion
//   * @param credential Certificat de connexion du compte � r�cup�rer
//   * @return Le compte r�cup�r� en fonction de son certificat de connexion
//   * @throws ModelArgumentException
//   */
//  public Account getAccount(Credential credential) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("credential", credential);
//    Account account = null;
//    synchronized(this.USER_SYNCH)
//    {
//      account = null; //TODO r�cuperer dans le cache
//      if(account == null) // TODO recuperer en base
//      {
//        account = null;
//      }
//    }
//    if(account == null || account.getCredential().differs(credential))
//    {
//      // TODO meilleur message
//      throw new ModelArgumentException("Account could not be found with given crenedtial");
//    }
//    return account;
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer un compte utilisateur en fonction de son
//   * identifiant
//   * @param accountId Identifiant du compte � r�cup�rer
//   * @return Le compte r�cup�r� en fonction de son identifiant
//   * @throws ModelArgumentException
//   */
//  public Account getAccount(long accountId) throws ModelArgumentException
//  {
//    Account account = null;
//    synchronized(this.USER_SYNCH)
//    {
//      account = null; //TODO r�cuperer dans le cache
//      if(account == null) // TODO recuperer en base
//      {
//        account = null;
//      }
//    }
//    if(account == null)
//    {
//      // TODO meilleur message
//      throw new ModelArgumentException("Account could not be found with given crenedtial");
//    }
//    return account;
//  }
//  /**
//   * Cette m�thode permet de locker un compte utilisateur en fonction de son
//   * identifiant
//   * @param accountId Identifiant du compte � locker
//   * @return Le compte lock� en fonction de son identifiant
//   * @throws Bid4WinException
//   */
//  public Account getLock(long accountId) throws Bid4WinException
//  {
//    Account account = this.getAccount(accountId);
//    synchronized(this.LOCK_SYNCH)
//    {
//      // TODO poser le lock
//    }
//    return account;
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param accountId
//   */
//  public synchronized void release(long accountId)
//  {
//    synchronized(this.LOCK_SYNCH)
//    {
//      // TODO supprimer le lock
//    }
//  }
//}
