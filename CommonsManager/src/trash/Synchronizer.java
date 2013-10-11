//package trash;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//
///**
// * Cette classe permet de distribuer des tokens de synchronisation uniques. Pour
// * ce faire, il suffit de réclamer un token, de l'utiliser en tant qu'objet de
// * synchronisation et de le rendre une fois utilisé afin de libérer ses ressources<BR>
// * <BR>
// * @param <TOKEN> Type du token de synchronisation<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class Synchronizer<TOKEN>
//{
//  /** Map de référence des tokens en cours d'utilisation */
//  private Bid4WinMap<TOKEN, TokenReference<TOKEN>> tokenMap =
//      new Bid4WinMap<TOKEN, TokenReference<TOKEN>>();
//
//  /**
//   * Cette méthode permet de récupérer le token de synchronisation égal à celui
//   * en paramètre. Il faudra bien utiliser le token retourné et non celui en paramètre
//   * afin d'être sur d'utiliser le token partagé dans l'application
//   * @param token Token réclamé
//   * @return Le token égal à celui en paramètre à utiliser pour toute synchronisation
//   * @throws ModelArgumentException Si le token en argument est nul
//   */
//  public synchronized TOKEN claimToken(TOKEN token) throws ModelArgumentException
//  {
//    // Récupère le token potentiellement déjà référencé
//    TokenReference<TOKEN> tokenReference = this.tokenMap.get(token);
//    if(tokenReference == null)
//    {
//      // Crée la nouvelle référence du token
//      tokenReference = new TokenReference<TOKEN>(token);
//    }
//    // Augmente le nombre d'utilisateur du token
//    tokenReference.addUser();
//    // Retourne le token à utiliser
//    return tokenReference.getToken();
//  }
//
//  /**
//   * Cette méthode permet de restituer le token de synchronisation. L'appel à cette
//   * méthode est requise en fin d'utilisation du token afin de libérer ses ressources
//   * @param token Token rendu
//   * @throws ModelArgumentException Si le token en argument est nul ou plus utilisé
//   */
//  public synchronized void returnToken(TOKEN token) throws ModelArgumentException
//  {
//    // Récupère la référence du token
//    TokenReference<TOKEN> tokenReference = UtilObject.checkNotNull(
//        "tokenReference", this.tokenMap.get(token));
//    // Diminue le nombre d'utilisateur du token
//    if(tokenReference.removeUser() == 0)
//    {
//      // Le token n'est plus utilisé, on retire sa référence
//      this.tokenMap.remove(token);
//    }
//  }
//}
