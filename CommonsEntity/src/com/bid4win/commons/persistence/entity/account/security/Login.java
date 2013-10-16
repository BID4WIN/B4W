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
 * Cette classe défini un identifiant de connexion<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Login extends Bid4WinEmbeddable<Login>
{
  /** Valeur de l'identifiant de connexion */
  @Transient private String value = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private Login()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'identifiant de connexion
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public Login(String value) throws UserException
  {
    super();
    this.defineValue(value);
  }

  /**
   * Redéfini l'égalité interne de deux identifiants de connexion par l'égalité
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
   * Redéfini la transformation en chaîne de caractères d'un identifiant de connexion
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments de l'identifiant de connexion
    buffer.append("LOGIN=" + this.getValue());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet de définir la valeur de l'identifiant de connexion
   * @param value Définition de la valeur de l'identifiant de connexion
   * @throws ProtectionException Si l'identifiant de connexion courant est protégé
   * @throws UserException Si on défini une valeur d'identifiant invalide
   */
  private void defineValue(String value) throws ProtectionException, UserException
  {
    // Vérifie la protection de l'identifiant de connexion courant
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
   * @param value Valeur de l'identifiant de connexion à positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }
}
