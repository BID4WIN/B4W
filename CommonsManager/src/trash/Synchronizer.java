//package trash;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//
///**
// * Cette classe permet de distribuer des tokens de synchronisation uniques. Pour
// * ce faire, il suffit de r�clamer un token, de l'utiliser en tant qu'objet de
// * synchronisation et de le rendre une fois utilis� afin de lib�rer ses ressources<BR>
// * <BR>
// * @param <TOKEN> Type du token de synchronisation<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class Synchronizer<TOKEN>
//{
//  /** Map de r�f�rence des tokens en cours d'utilisation */
//  private Bid4WinMap<TOKEN, TokenReference<TOKEN>> tokenMap =
//      new Bid4WinMap<TOKEN, TokenReference<TOKEN>>();
//
//  /**
//   * Cette m�thode permet de r�cup�rer le token de synchronisation �gal � celui
//   * en param�tre. Il faudra bien utiliser le token retourn� et non celui en param�tre
//   * afin d'�tre sur d'utiliser le token partag� dans l'application
//   * @param token Token r�clam�
//   * @return Le token �gal � celui en param�tre � utiliser pour toute synchronisation
//   * @throws ModelArgumentException Si le token en argument est nul
//   */
//  public synchronized TOKEN claimToken(TOKEN token) throws ModelArgumentException
//  {
//    // R�cup�re le token potentiellement d�j� r�f�renc�
//    TokenReference<TOKEN> tokenReference = this.tokenMap.get(token);
//    if(tokenReference == null)
//    {
//      // Cr�e la nouvelle r�f�rence du token
//      tokenReference = new TokenReference<TOKEN>(token);
//    }
//    // Augmente le nombre d'utilisateur du token
//    tokenReference.addUser();
//    // Retourne le token � utiliser
//    return tokenReference.getToken();
//  }
//
//  /**
//   * Cette m�thode permet de restituer le token de synchronisation. L'appel � cette
//   * m�thode est requise en fin d'utilisation du token afin de lib�rer ses ressources
//   * @param token Token rendu
//   * @throws ModelArgumentException Si le token en argument est nul ou plus utilis�
//   */
//  public synchronized void returnToken(TOKEN token) throws ModelArgumentException
//  {
//    // R�cup�re la r�f�rence du token
//    TokenReference<TOKEN> tokenReference = UtilObject.checkNotNull(
//        "tokenReference", this.tokenMap.get(token));
//    // Diminue le nombre d'utilisateur du token
//    if(tokenReference.removeUser() == 0)
//    {
//      // Le token n'est plus utilis�, on retire sa r�f�rence
//      this.tokenMap.remove(token);
//    }
//  }
//}
