//package trash;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//
///**
// * Cette classe correspond à une référence de token utilisée pour gérer l'unicité
// * des tokens ainsi que leur utilisation<BR>
// * <BR>
// * @param <TOKEN> Type du token référencé<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class TokenReference<TOKEN>
//{
//  /** Token référencé */
//  private TOKEN token = null;
//  /** Nombre d'utilisateur du token */
//  private int userNb = 0;
//
//  /**
//   * Constructeur
//   * @param token Token à référencer
//   * @throws ModelArgumentException Si le token en argument est nul
//   */
//  public TokenReference(TOKEN token) throws ModelArgumentException
//  {
//    this.setToken(token);
//  }
//
//  /**
//   * Getter du token référencé
//   * @return Le token référencé
//   */
//  public TOKEN getToken()
//  {
//    return this.token;
//  }
//  /**
//   * Setter interne du token référencé
//   * @param token Token référencé à positionner
//   * @throws ModelArgumentException Si on positionne un token nul
//   */
//  private void setToken(TOKEN token) throws ModelArgumentException
//  {
//    this.token = UtilObject.checkNotNull("token", token);
//  }
//
//  /**
//   * Getter du nombre d'utilisateur du token
//   * @return Le nombre d'utilisateur du token
//   */
//  public int getUserNb()
//  {
//    return this.userNb;
//  }
//  /**
//   * Permet d'ajouter un utilisateur au token
//   * @return Le nombre courant d'utilisateur du token après ajout
//   */
//  protected int addUser()
//  {
//    return this.userNb++;
//  }
//  /**
//   * Permet de retirer un utilisateur du token
//   * @return Le nombre courant d'utilisateur du token après retrait
//   * @throws ModelArgumentException Si on essaye de retirer un utilisateur alors
//   * qu'il n'y en a plus
//   */
//  protected int removeUser() throws ModelArgumentException
//  {
//    UtilNumber.checkMinValue("userNb", this.userNb, 1, true);
//    return this.userNb--;
//  }
//}
