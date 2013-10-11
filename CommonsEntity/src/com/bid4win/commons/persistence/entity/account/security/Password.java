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
 * Cette classe d�fini un mot de passe<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Password extends Bid4WinEmbeddable<Password>
{
  /** Valeur crypt�e du mot de passe */
  @Transient
  private String value = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Password()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur crypt�e du mot de passe
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Password(String value) throws UserException
  {
    super();
    this.defineValue(value);
  }

  /**
   * Red�fini l'�galit� interne de deux mots de passe par l'�galit� de leur contenu
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
   * Red�fini la transformation en cha�ne de caract�res d'un mot de passe lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments du mot de passe
    buffer.append("PASSWORD=******");
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de d�finir la valeur crypt�e du mot de passe de connexion
   * @param value D�finition de la valeur crypt�e du mot de passe de connexion
   * @throws ProtectionException Si le mot de passe de connexion courant est prot�g�
   * @throws UserException Si on d�fini une valeur crypt�e de mot de passe invalide
   */
  private void defineValue(String value) throws ProtectionException, UserException
  {
    // V�rifie la protection du mot de passe de connexion courant
    this.checkProtection();
    value = UtilString.trimNotNull(value).toLowerCase();
    this.setValue(UtilPassword.checkHashedPassword(value));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur crypt�e du mot de passe
   * @return La valeur crypt�e du mot de passe
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PASSWORD", length = 30, nullable = false, unique = false)
  protected String getValue()
  {
    return this.value;
  }

  /**
   * Setter de la valeur crypt�e du mot de passe
   * @param value Valeur crypt�e du mot de passe � positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }
}
