package com.bid4win.commons.persistence.entity.connection;

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
 * Cette classe d�fini une adresse IP<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class IpAddress extends Bid4WinEmbeddable<IpAddress>
{
  /** Valeur de l'adresse IP */
  @Transient private String value = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private IpAddress()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'adresse IP
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public IpAddress(String value) throws UserException
  {
    super();
    this.defineValue(value);
  }

  /**
   * Red�fini l'�galit� interne de deux adresses IP par l'�galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(IpAddress toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getValue(),
                                                  toBeCompared.getValue());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'une adresse IP lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de l'email
    buffer.append("IP=" + this.getValue());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de d�finir la valeur de l'adresse IP
   * @param value D�finition de la valeur de l'adresse IP
   * @throws ProtectionException Si l'adresse IP courante est prot�g�e
   * @throws UserException Si on d�fini une adresse IP invalide
   */
  private void defineValue(String value) throws ProtectionException, UserException
  {
    // V�rifie l'adresse IP courante
    this.checkProtection();
    value = UtilString.trimNotNull(value).toUpperCase();
    this.setValue(UtilIpAddress.checkAddress(value));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur de l'adresse IP
   * @return La valeur de l'adresse IP
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "IP", length = 39, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de l'adresse IP
   * @param value Valeur de l'adresse IP � positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }
}
