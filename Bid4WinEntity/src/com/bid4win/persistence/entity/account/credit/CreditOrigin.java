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
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "ORIGIN", length = 15))
public class CreditOrigin extends Bid4WinEmbeddableWithType<CreditOrigin, Origin>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 772628250785504226L;

  /** R�f�rence de provenance des cr�dits */
  @Transient
  private String reference = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private CreditOrigin()
  {
    super();
  }
  /**
   * Constructeur
   * @param type Type de provenance des cr�dits
   * @param reference R�f�rence de provenance des cr�dits
   * @throws UserException Si la r�f�rence de provenance des cr�dits est invalide
   */
  public CreditOrigin(Origin type, String reference) throws UserException
  {
    super(type);
    this.defineReference(reference);
  }

  /**
   * Red�fini l'�galit� interne de deux provenances de lot de cr�dits par l'�galit�
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
   * Red�fini la transformation en cha�ne de caract�res d'une provenance de lot
   * de cr�dits lisiblement
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
   * Cette m�thode permet de d�finir la r�f�rence de provenance des cr�dits
   * @param reference D�finition de la r�f�rence de provenance des cr�dits
   * @throws ProtectionException Si la provenance courante est prot�g�e
   * @throws UserException Si la r�f�rence de provenance des cr�dits est invalide
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
   * Getter de la r�f�rence de provenance des cr�dits
   * @return La r�f�rence de provenance des cr�dits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REFERENCE", length = 12, nullable = false, unique = false)
  public String getReference()
  {
    return this.reference;
  }
  /**
   * Setter de la r�f�rence de provenance des cr�dits
   * @param reference R�f�rence de provenance des cr�dits � positionner
   */
  private void setReference(String reference)
  {
    this.reference = reference;
  }
}
