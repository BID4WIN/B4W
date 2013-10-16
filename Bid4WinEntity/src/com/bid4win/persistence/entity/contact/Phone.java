package com.bid4win.persistence.entity.contact;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe défini un contact téléphonique<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Phone extends Bid4WinEmbeddable<Phone>
{
  /** Indicatif téléphonique */
  @Transient private String code = null;
  /** Numéro de téléphone */
  @Transient private String number = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private Phone()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param code Indicatif téléphonique
   * @param number Numéro de téléphone
   * @throws UserException TODO A COMMENTER
   */
  public Phone(String code, String number) throws UserException
  {
    super();
    this.defineCode(code);
    this.defineNumber(number);
  }

  /**
   * Redéfini l'égalité interne de deux contacts téléphoniques par l'égalité de
   * leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(Phone toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getCode().equals(toBeCompared.getCode()) &&
           this.getNumber().equals(toBeCompared.getNumber());
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'un contact téléphonique
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments du contact téléphonique
    buffer.append("+").append(this.getCode());
    buffer.append("-").append(this.getNumber());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet de définir l'indicatif téléphonique
   * @param code Définition de l'indicatif téléphonique
   * @throws ProtectionException Si le contact téléphonique courant est protégé
   * @throws UserException TODO A COMMENTER
   */
  public void defineCode(String code) throws ProtectionException, UserException
  {
    // Vérifie la protection de l'utilisateur courant
    this.checkProtection();
    this.setCode(UtilPhone.checkCode(UtilString.trimNotNull(code)));
  }
  /**
   * Cette méthode permet de définir le numéro de téléphone
   * @param number Définition du numéro de téléphone
   * @throws ProtectionException Si le contact téléphonique courant est protégé
   * @throws UserException TODO A COMMENTER
   */
  public void defineNumber(String number) throws ProtectionException, UserException
  {
    // Vérifie la protection de l'utilisateur courant
    this.checkProtection();
    this.setNumber(UtilPhone.checkNumber(UtilString.trimNotNull(number)));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'indicatif téléphonique
   * @return L'indicatif téléphonique
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PHONE_CODE", length = 3, nullable = true, unique = false)
  public String getCode()
  {
    return this.code;
  }
  /**
   * Setter de l'indicatif téléphonique
   * @param code Indicatif téléphonique à positionner
   */
  private void setCode(String code)
  {
    this.code = code;
  }
  /**
   * Getter du numéro de téléphone
   * @return Le numéro de téléphone
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PHONE_CODE", length = 3, nullable = true, unique = false)
  public String getNumber()
  {
    return this.number;
  }
  /**
   * Setter du numéro de téléphone
   * @param number Numéro de téléphone à positionner
   */
  private void setNumber(String number)
  {
    this.number = number;
  }
}
