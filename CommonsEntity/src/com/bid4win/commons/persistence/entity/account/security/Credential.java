package com.bid4win.commons.persistence.entity.account.security;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ConnectionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�fini un certificat de connexion correspondant au couple identifiant
 * / mot de passe associ� � un r�le<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Credential extends Bid4WinEmbeddable<Credential>
{
  /** Identifiant de connexion */
  @Transient private Login login = null;
  /** Mot de passe de connexion */
  @Transient private Password password = null;
  /** R�le du certificat de connexion */
  @Transient private Role role = Role.NONE;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Credential()
  {
    super();
  }
  /**
   * Constructeur
   * @param login Identifiant de connexion
   * @param password Mot de passe de connexion
   * @param role R�le du certificat de connexion
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Credential(Login login, Password password, Role role) throws UserException
  {
    super();
    this.defineLogin(login);
    this.definePassword(password);
    this.defineRole(role);
  }
  /**
   * Constructeur sans r�le
   * @param login Identifiant de connexion
   * @param password Mot de passe de connexion
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Credential(Login login, Password password) throws UserException
  {
    this(login, password, Role.NONE);
  }

  /**
   * Red�fini l'�galit� interne de deux certificats de connexion par l'�galit� de
   * leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(Credential toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getLogin(),
                                                        toBeCompared.getLogin()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getPassword(),
                                                        toBeCompared.getPassword()) &&
           this.getRole() == toBeCompared.getRole();
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un certificat de connexion
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments du certificat de connexion
    buffer.append(this.getLogin().render());
    buffer.append(" ").append(this.getPassword().render());
    buffer.append(" ROLE=").append(this.getRole().getCode());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de d�finir l'identifiant de connexion du certificat
   * @param login D�finition de l'identifiant de connexion du certificat
   * @throws ProtectionException Si le certificat courant est prot�g�
   * @throws UserException Si on d�fini un identifiant de connexion nul
   */
  private void defineLogin(Login login) throws ProtectionException, UserException
  {
    // V�rifie la protection du certificat courant
    this.checkProtection();
    this.setLogin(UtilObject.checkNotNull("login", login,
                                          ConnectionRef.LOGIN_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir le mot de passe de connexion du certificat
   * @param password D�finition du mot de passe de connexion du certificat
   * @throws ProtectionException Si le certificat courant est prot�g�
   * @throws UserException Si on d�fini un mot de passe de connexion nul
   */
  public void definePassword(Password password) throws ProtectionException, UserException
  {
    // V�rifie la protection du certificat courant
    this.checkProtection();
    this.setPassword(UtilObject.checkNotNull("password", password,
                                             ConnectionRef.PASSWORD_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir le r�le du certificat de connexion
   * @param role D�finition du r�le du certificat de connexion
   * @throws ProtectionException Si le certificat courant est prot�g�
   * @throws UserException Si on d�fini un r�le nul
   */
  public void defineRole(Role role) throws ProtectionException, UserException
  {
    // V�rifie la protection du certificat courant
    this.checkProtection();
    this.setRole(UtilObject.checkNotNull("role", role, MessageRef.UNKNOWN_MISSING_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de connexion du certificat
   * @return L'identifiant de connexion du certificat
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public Login getLogin()
  {
    return this.login;
  }
  /**
   * Setter de l'identifiant de connexion du certificat
   * @param login Identifiant de connexion du certificat � positionner
   */
  private void setLogin(Login login)
  {
    this.login = login;
  }

  /**
   * Getter du mot de passe de connexion du certificat
   * @return Le mot de passe de connexion du certificat
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public Password getPassword()
  {
    return this.password;
  }
  /**
   * Setter du mot de passe de connexion du certificat
   * @param password Mot de passe de connexion du certificat � positionner
   */
  private void setPassword(Password password)
  {
    this.password = password;
  }

  /**
   * Getter du r�le du certificat de connexion
   * @return Le r�le du certificat de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "ROLE", length = 5, nullable = false, unique = false)
  public Role getRole()
  {
    return this.role;
  }
  /**
   * Setter du r�le du certificat de connexion
   * @param role R�le du certificat de connexion � positionner
   */
  private void setRole(Role role)
  {
    this.role = role;
  }
}
