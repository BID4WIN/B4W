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
 * Cette classe d�fini un contact t�l�phonique<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Phone extends Bid4WinEmbeddable<Phone>
{
  /** Indicatif t�l�phonique */
  @Transient private String code = null;
  /** Num�ro de t�l�phone */
  @Transient private String number = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Phone()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param code Indicatif t�l�phonique
   * @param number Num�ro de t�l�phone
   * @throws UserException TODO A COMMENTER
   */
  public Phone(String code, String number) throws UserException
  {
    super();
    this.defineCode(code);
    this.defineNumber(number);
  }

  /**
   * Red�fini l'�galit� interne de deux contacts t�l�phoniques par l'�galit� de
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
   * Red�fini la transformation en cha�ne de caract�res d'un contact t�l�phonique
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments du contact t�l�phonique
    buffer.append("+").append(this.getCode());
    buffer.append("-").append(this.getNumber());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de d�finir l'indicatif t�l�phonique
   * @param code D�finition de l'indicatif t�l�phonique
   * @throws ProtectionException Si le contact t�l�phonique courant est prot�g�
   * @throws UserException TODO A COMMENTER
   */
  public void defineCode(String code) throws ProtectionException, UserException
  {
    // V�rifie la protection de l'utilisateur courant
    this.checkProtection();
    this.setCode(UtilPhone.checkCode(UtilString.trimNotNull(code)));
  }
  /**
   * Cette m�thode permet de d�finir le num�ro de t�l�phone
   * @param number D�finition du num�ro de t�l�phone
   * @throws ProtectionException Si le contact t�l�phonique courant est prot�g�
   * @throws UserException TODO A COMMENTER
   */
  public void defineNumber(String number) throws ProtectionException, UserException
  {
    // V�rifie la protection de l'utilisateur courant
    this.checkProtection();
    this.setNumber(UtilPhone.checkNumber(UtilString.trimNotNull(number)));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'indicatif t�l�phonique
   * @return L'indicatif t�l�phonique
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PHONE_CODE", length = 3, nullable = true, unique = false)
  public String getCode()
  {
    return this.code;
  }
  /**
   * Setter de l'indicatif t�l�phonique
   * @param code Indicatif t�l�phonique � positionner
   */
  private void setCode(String code)
  {
    this.code = code;
  }
  /**
   * Getter du num�ro de t�l�phone
   * @return Le num�ro de t�l�phone
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PHONE_CODE", length = 3, nullable = true, unique = false)
  public String getNumber()
  {
    return this.number;
  }
  /**
   * Setter du num�ro de t�l�phone
   * @param number Num�ro de t�l�phone � positionner
   */
  private void setNumber(String number)
  {
    this.number = number;
  }
}
