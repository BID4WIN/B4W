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
 * Cette classe défini la politique d'annulation de base d'une vente aux enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CancelPolicyAbstract<CLASS extends CancelPolicyAbstract<CLASS>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Seuil du nombre de crédits utilisés sur la vente aux enchères en dessous duquel elle sera annulée */
  @Transient
  private int creditNbThreshold = 0;
  /** Nombre de crédits bonus par crédit payé utilisé sur une vente aux enchères annulée */
  @Transient
  private int creditNbPaidBonus = 0;

  /**
   * Constructeur pour création par introspection
   */
  protected CancelPolicyAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbThreshold Seuil du nombre de crédits utilisés sur la vente aux
   * enchères en dessous duquel elle sera annulée
   * @param creditNbPaidBonus Nombre de crédits bonus par crédit payé utilisé sur
   * une vente aux enchères annulée
   * @throws UserException Si le seuil ou le nombre de crédits bonus est négatif
   */
  public CancelPolicyAbstract(double creditNbThreshold, int creditNbPaidBonus) throws UserException
  {
    super();
    this.defineCreditNbThreshold(creditNbThreshold);
    this.defineCreditNbPaidBonus(creditNbPaidBonus);
  }

  /**
   * Redéfini l'égalité interne de deux politiques d'annulation de vente aux enchères
   * par l'égalité de leur contenu
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
   * Redéfini la transformation en chaîne de caractères d'une politique d'annulation
   * de vente aux enchères lisiblement
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
   * en fonction du ratio en paramètre
   * @param ratio Ratio à appliquer au seuil de la politique d'annulation
   * @throws ProtectionException Si les conditions d'enchère courantes sont protégées
   * @throws UserException Si on définie un seuil négatif
   */
  protected void recalculate(double ratio) throws ProtectionException, UserException
  {
    this.defineCreditNbThreshold(this.getCreditNbThreshold() * ratio);
  }

  /**
   * Cette méthode permet de définir le seuil du nombre de crédits utilisés sur
   * la vente aux enchères en dessous duquel elle sera annulée
   * @param creditNbThreshold Définition du seuil du nombre de crédits utilisés
   * sur la vente aux enchères en dessous duquel elle sera annulée
   * @throws ProtectionException Si les conditions d'enchère courantes sont protégées
   * @throws UserException Si on définie un seuil négatif
   */
  private void defineCreditNbThreshold(double creditNbThreshold)
          throws ProtectionException, UserException
  {
    // Vérifie la protection des conditions d'enchère courantes
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
   * Cette méthode permet de définir le nombre de crédits bonus par crédit payé
   * utilisé sur une vente aux enchères annulée
   * @param creditNbPaidBonus Définition nombre de crédits bonus par crédit payé
   * utilisé sur une vente aux enchères annulée
   * @throws ProtectionException Si les conditions d'enchère courantes sont protégées
   * @throws UserException Si on définie un nombre de crédits bonus négatif
   */
  private void defineCreditNbPaidBonus(int creditNbPaidBonus)
          throws ProtectionException, UserException
  {
    // Vérifie la protection des conditions d'enchère courantes
    this.checkProtection();
    this.setCreditNbPaidBonus(
        UtilNumber.checkMinValue("creditNbPaidBonus", creditNbPaidBonus, 0, true,
                                 AuctionRef.TERMS_INVALID_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du seuil du nombre de crédits utilisés sur la vente aux enchères en
   * dessous duquel elle sera annulée
   * @return Le seuil du nombre de crédits utilisés sur la vente aux enchères en
   * dessous duquel elle sera annulée
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB_THRESHOLD", length = 5, nullable = false, unique = false)
  public int getCreditNbThreshold()
  {
    return this.creditNbThreshold;
  }
  /**
   * Setter du seuil du nombre de crédits utilisés sur la vente aux enchères en
   * dessous duquel elle sera annulée
   * @param creditNbThreshold Seuil du nombre de crédits utilisés sur la vente aux
   * enchères en dessous duquel elle sera annulée à positionner
   */
  private void setCreditNbThreshold(int creditNbThreshold)
  {
    this.creditNbThreshold = creditNbThreshold;
  }

  /**
   * Getter du nombre de crédits bonus par crédit payé utilisé sur une vente aux
   * enchères annulée
   * @return Le nombre de crédits bonus par crédit payé utilisé sur une vente aux
   * enchères annulée
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB_PAID_BONUS", length = 1, nullable = false, unique = false)
  public int getCreditNbPaidBonus()
  {
    return this.creditNbPaidBonus;
  }
  /**
   * Setter du nombre de crédits bonus par crédit payé utilisé sur une vente aux
   * enchères annulée
   * @param creditNbPaidBonus Nombre de crédits bonus par crédit payé utilisé sur
   * une vente aux enchères annulée à positionner
   */
  private void setCreditNbPaidBonus(int creditNbPaidBonus)
  {
    this.creditNbPaidBonus = creditNbPaidBonus;
  }
}
