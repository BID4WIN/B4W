package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�fini la politique d'annulation de base d'une vente aux ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CancelPolicyAbstract<CLASS extends CancelPolicyAbstract<CLASS>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Seuil du nombre de cr�dits utilis�s sur la vente aux ench�res en dessous duquel elle sera annul�e */
  @Transient
  private int creditNbThreshold = 0;
  /** Nombre de cr�dits bonus par cr�dit pay� utilis� sur une vente aux ench�res annul�e */
  @Transient
  private int creditNbPaidBonus = 0;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CancelPolicyAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbThreshold Seuil du nombre de cr�dits utilis�s sur la vente aux
   * ench�res en dessous duquel elle sera annul�e
   * @param creditNbPaidBonus Nombre de cr�dits bonus par cr�dit pay� utilis� sur
   * une vente aux ench�res annul�e
   * @throws UserException Si le seuil ou le nombre de cr�dits bonus est n�gatif
   */
  public CancelPolicyAbstract(double creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super();
    this.defineCreditNbThreshold(creditNbThreshold);
    this.defineCreditNbPaidBonus(creditNbPaidBonus);
  }

  /**
   * Red�fini l'�galit� interne de deux politiques d'annulation de vente aux ench�res
   * par l'�galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return this.getCreditNbThreshold() == toBeCompared.getCreditNbThreshold() &&
           this.getCreditNbPaidBonus() == toBeCompared.getCreditNbPaidBonus();
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'une politique d'annulation
   * de vente aux ench�res lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append("CREDIT_NB_THRESHOLD=" + this.getCreditNbThreshold());
    buffer.append(" CREDIT_NB_PAID_BONUS=" + this.getCreditNbThreshold());
    return buffer;
  }

  /**
   * Cette fonction doit permettre de recalculer le seuil de la politique d'annulation
   * en fonction du ratio en param�tre
   * @param ratio Ratio � appliquer au seuil de la politique d'annulation
   * @throws ProtectionException Si les conditions d'ench�re courantes sont prot�g�es
   * @throws UserException Si on d�finie un seuil n�gatif
   */
  protected void recalculate(double ratio) throws ProtectionException, UserException
  {
    this.defineCreditNbThreshold(this.getCreditNbThreshold() * ratio);
  }

  /**
   * Cette m�thode permet de d�finir le seuil du nombre de cr�dits utilis�s sur
   * la vente aux ench�res en dessous duquel elle sera annul�e
   * @param creditNbThreshold D�finition du seuil du nombre de cr�dits utilis�s
   * sur la vente aux ench�res en dessous duquel elle sera annul�e
   * @throws ProtectionException Si les conditions d'ench�re courantes sont prot�g�es
   * @throws UserException Si on d�finie un seuil n�gatif
   */
  private void defineCreditNbThreshold(double creditNbThreshold)
          throws ProtectionException, UserException
  {
    // V�rifie la protection des conditions d'ench�re courantes
    this.checkProtection();
    this.setCreditNbThreshold(
        UtilNumber.checkMinValue("creditNbThreshold", (int)creditNbThreshold, 0, true,
                                 AuctionRef.TERMS_INVALID_ERROR));
    if(this.getCreditNbThreshold() < creditNbThreshold)
    {
      this.setCreditNbThreshold(this.getCreditNbThreshold() + 1);
    }
  }
  /**
   * Cette m�thode permet de d�finir le nombre de cr�dits bonus par cr�dit pay�
   * utilis� sur une vente aux ench�res annul�e
   * @param creditNbPaidBonus D�finition nombre de cr�dits bonus par cr�dit pay�
   * utilis� sur une vente aux ench�res annul�e
   * @throws ProtectionException Si les conditions d'ench�re courantes sont prot�g�es
   * @throws UserException Si on d�finie un nombre de cr�dits bonus n�gatif
   */
  private void defineCreditNbPaidBonus(int creditNbPaidBonus)
          throws ProtectionException, UserException
  {
    // V�rifie la protection des conditions d'ench�re courantes
    this.checkProtection();
    this.setCreditNbPaidBonus(
        UtilNumber.checkMinValue("creditNbPaidBonus", creditNbPaidBonus, 0, true,
                                 AuctionRef.TERMS_INVALID_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du seuil du nombre de cr�dits utilis�s sur la vente aux ench�res en
   * dessous duquel elle sera annul�e
   * @return Le seuil du nombre de cr�dits utilis�s sur la vente aux ench�res en
   * dessous duquel elle sera annul�e
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB_THRESHOLD", length = 5, nullable = false, unique = false)
  public int getCreditNbThreshold()
  {
    return this.creditNbThreshold;
  }
  /**
   * Setter du seuil du nombre de cr�dits utilis�s sur la vente aux ench�res en
   * dessous duquel elle sera annul�e
   * @param creditNbThreshold Seuil du nombre de cr�dits utilis�s sur la vente aux
   * ench�res en dessous duquel elle sera annul�e � positionner
   */
  private void setCreditNbThreshold(int creditNbThreshold)
  {
    this.creditNbThreshold = creditNbThreshold;
  }

  /**
   * Getter du nombre de cr�dits bonus par cr�dit pay� utilis� sur une vente aux
   * ench�res annul�e
   * @return Le nombre de cr�dits bonus par cr�dit pay� utilis� sur une vente aux
   * ench�res annul�e
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB_PAID_BONUS", length = 1, nullable = false, unique = false)
  public int getCreditNbPaidBonus()
  {
    return this.creditNbPaidBonus;
  }
  /**
   * Setter du nombre de cr�dits bonus par cr�dit pay� utilis� sur une vente aux
   * ench�res annul�e
   * @param creditNbPaidBonus Nombre de cr�dits bonus par cr�dit pay� utilis� sur
   * une vente aux ench�res annul�e � positionner
   */
  private void setCreditNbPaidBonus(int creditNbPaidBonus)
  {
    this.creditNbPaidBonus = creditNbPaidBonus;
  }
}
