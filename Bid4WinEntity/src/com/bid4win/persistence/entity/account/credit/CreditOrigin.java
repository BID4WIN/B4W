package com.bid4win.persistence.entity.account.credit;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "ORIGIN", length = 15))
public class CreditOrigin extends Bid4WinEmbeddableWithType<CreditOrigin, Origin>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 772628250785504226L;

  /** Référence de provenance des crédits */
  @Transient
  private String reference = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private CreditOrigin()
  {
    super();
  }
  /**
   * Constructeur
   * @param type Type de provenance des crédits
   * @param reference Référence de provenance des crédits
   * @throws UserException Si la référence de provenance des crédits est invalide
   */
  public CreditOrigin(Origin type, String reference) throws UserException
  {
    super(type);
    this.defineReference(reference);
  }

  /**
   * Redéfini l'égalité interne de deux provenances de lot de crédits par l'égalité
   * de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(CreditOrigin toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getReference(), toBeCompared.getReference());
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'une provenance de lot
   * de crédits lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append(" REFERENCE=").append(this.getReference());
    return buffer;
  }

  /**
   * Cette méthode permet de définir la référence de provenance des crédits
   * @param reference Définition de la référence de provenance des crédits
   * @throws ProtectionException Si la provenance courante est protégée
   * @throws UserException Si la référence de provenance des crédits est invalide
   */
  private void defineReference(String reference) throws ProtectionException, UserException
  {
    this.checkProtection();
    reference = UtilString.trimNotNull(reference);
    UtilNumber.checkMinValue("size", reference.length(), 1, true,
                             AccountRef.ACCOUNT_CREDIT_REFERENCE_INVALID_ERROR);
    this.setReference(reference);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la référence de provenance des crédits
   * @return La référence de provenance des crédits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REFERENCE", length = 12, nullable = false, unique = false)
  public String getReference()
  {
    return this.reference;
  }
  /**
   * Setter de la référence de provenance des crédits
   * @param reference Référence de provenance des crédits à positionner
   */
  private void setReference(String reference)
  {
    this.reference = reference;
  }
}
