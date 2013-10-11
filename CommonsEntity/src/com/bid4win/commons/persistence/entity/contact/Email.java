package com.bid4win.commons.persistence.entity.contact;

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
 * Cette classe défini un email<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Email extends Bid4WinEmbeddable<Email>
{
  /** Adresse de l'email */
  @Transient
  private String address = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private Email()
  {
    super();
  }
  /**
   * Constructeur
   * @param address Adresse de l'email
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public Email(String address) throws UserException
  {
    super();
    this.defineAddress(address);
  }

  /**
   * Redéfini l'égalité interne de deux emails par l'égalité de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(Email toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getAddress(),
                                                  toBeCompared.getAddress());
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'un email lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments de l'email
    buffer.append("EMAIL=" + this.getAddress());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet de définir l'adresse de l'email
   * @param address Définition de l'adresse de l'email
   * @throws ProtectionException Si l'email courant est protégé
   * @throws UserException Si on défini une adresse d'email invalide
   */
  private void defineAddress(String address) throws ProtectionException, UserException
  {
    // Vérifie la protection de l'email courant
    this.checkProtection();
    address = UtilString.trimNotNull(address).toLowerCase();
    this.setAddress(UtilEmail.checkAddress(address));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'adresse de l'email
   * @return L'adresse de l'email
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
  public String getAddress()
  {
    return this.address;
  }
  /**
   * Setter de l'adresse de l'email
   * @param address Adresse de l'email à positionner
   */
  private void setAddress(String address)
  {
    this.address = address;
  }
}
