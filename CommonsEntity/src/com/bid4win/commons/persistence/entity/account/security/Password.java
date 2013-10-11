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
 * Cette classe défini un mot de passe<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Password extends Bid4WinEmbeddable<Password>
{
  /** Valeur cryptée du mot de passe */
  @Transient
  private String value = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private Password()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur cryptée du mot de passe
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public Password(String value) throws UserException
  {
    super();
    this.defineValue(value);
  }

  /**
   * Redéfini l'égalité interne de deux mots de passe par l'égalité de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(Password toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue());
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'un mot de passe lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments du mot de passe
    buffer.append("PASSWORD=******");
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet de définir la valeur cryptée du mot de passe de connexion
   * @param value Définition de la valeur cryptée du mot de passe de connexion
   * @throws ProtectionException Si le mot de passe de connexion courant est protégé
   * @throws UserException Si on défini une valeur cryptée de mot de passe invalide
   */
  private void defineValue(String value) throws ProtectionException, UserException
  {
    // Vérifie la protection du mot de passe de connexion courant
    this.checkProtection();
    value = UtilString.trimNotNull(value).toLowerCase();
    this.setValue(UtilPassword.checkHashedPassword(value));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur cryptée du mot de passe
   * @return La valeur cryptée du mot de passe
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PASSWORD", length = 30, nullable = false, unique = false)
  protected String getValue()
  {
    return this.value;
  }

  /**
   * Setter de la valeur cryptée du mot de passe
   * @param value Valeur cryptée du mot de passe à positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }
}
