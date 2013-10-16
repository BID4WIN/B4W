package com.bid4win.commons.persistence.entity.account.security;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�fini un identifiant de connexion<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Login extends Bid4WinEmbeddable<Login>
{
  /** Valeur de l'identifiant de connexion */
  @Transient private String value = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Login()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'identifiant de connexion
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Login(String value) throws UserException
  {
    super();
    this.defineValue(value);
  }

  /**
   * Red�fini l'�galit� interne de deux identifiants de connexion par l'�galit�
   * de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(Login toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un identifiant de connexion
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de l'identifiant de connexion
    buffer.append("LOGIN=" + this.getValue());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de d�finir la valeur de l'identifiant de connexion
   * @param value D�finition de la valeur de l'identifiant de connexion
   * @throws ProtectionException Si l'identifiant de connexion courant est prot�g�
   * @throws UserException Si on d�fini une valeur d'identifiant invalide
   */
  private void defineValue(String value) throws ProtectionException, UserException
  {
    // V�rifie la protection de l'identifiant de connexion courant
    this.checkProtection();
    this.setValue(UtilLogin.checkLogin(UtilString.trimNotNull(value).toLowerCase()));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur de l'identifiant de connexion
   * @return La valeur de l'identifiant de connexion
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "LOGIN", length = 30, nullable = false, unique = true)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de l'identifiant de connexion
   * @param value Valeur de l'identifiant de connexion � positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }
}
