package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Cette classe défini les conditions de base d'une vente aux enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class TermsAbstract<CLASS extends TermsAbstract<CLASS>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Définition de la précision après la virgule par défaut de la valeur d'un incrément d'enchère */
  public final static int PRECISION = 3;

  /** Nombre de crédits à utiliser par l'utilisateur à chaque enchère */
  @Transient
  private int creditNbPerBid = 0;
  /** Montant d'un incrément d'enchère */
  private Amount bidIncrement = null;

  /**
   * Constructeur pour création par introspection
   */
  protected TermsAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbPerBid Nombre de crédits à utiliser par l'utilisateur à chaque
   * enchère
   * @param bidIncrementValue Montant d'un incrément d'enchère dans la monnaie par
   * défaut
   * @throws UserException Si le nombre de crédits à utiliser est négatif ou le
   * montant d'un incrément d'enchère inférieur ou égal à zéro
   */
  public TermsAbstract(int creditNbPerBid, double bidIncrementValue) throws UserException
  {
    super();
    this.defineCreditNbPerBid(creditNbPerBid);
    this.defineBidIncrementValue(bidIncrementValue);
  }

  /**
   * Redéfini l'égalité interne de deux jeux de conditions de vente aux enchères
   * par l'égalité de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return this.getCreditNbPerBid() == toBeCompared.getCreditNbPerBid() &&
           this.getBidIncrement().equals(toBeCompared.getBidIncrement());
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'un jeu de condition de
   * vente aux enchères lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append("CREDIT_NB_PER_BID=" + this.getCreditNbPerBid());
    buffer.append(" BID_INCREMENT=[").append(this.getBidIncrement().render()).append("]");
    return buffer;
  }

  /**
   * Cette méthode permet de définir le nombre de crédits à utiliser l'utilisateur
   * à chaque enchère
   * @param creditNbPerBid Définition du nombre de crédits à utiliser par l'utilisateur
   * à chaque enchère
   * @throws ProtectionException Si les conditions d'enchère courantes sont protégées
   * @throws UserException Si on définie un nombre de crédits à utiliser négatif
   */
  private void defineCreditNbPerBid(int creditNbPerBid) throws ProtectionException, UserException
  {
    // Vérifie la protection des conditions d'enchère courantes
    this.checkProtection();
    this.setCreditNbPerBid(UtilNumber.checkMinValue("creditNbPerBid", creditNbPerBid, 0, true,
                                                    AuctionRef.TERMS_INVALID_ERROR));
  }
  /**
   * Cette méthode permet de définir la valeur d'un incrément d'enchère
   * @param bidIncrementValue Définition de la valeur d'un incrément d'enchère dans
   * la monnaie par défaut
   * @throws ProtectionException Si les conditions d'enchère courantes sont protégées
   * @throws UserException Si la valeur d'un incrément d'enchère est inférieure
   * à zéro
   */
  private void defineBidIncrementValue(double bidIncrementValue)
          throws ProtectionException, UserException
  {
    // Vérifie la protection des conditions d'enchère courantes
    this.checkProtection();
    Amount amount = new Amount(bidIncrementValue);
    UtilNumber.checkMinValue("bidIncrementValue", amount.getValue(), 0, false,
                             AuctionRef.TERMS_INVALID_ERROR);
    this.setBidIncrement(amount);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du nombre de crédits à utiliser par l'utilisateur à chaque enchère
   * @return Le nombre de crédits à utiliser par l'utilisateur à chaque enchère
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB_PER_BID", length = 3, nullable = false, unique = false)
  public int getCreditNbPerBid()
  {
    return this.creditNbPerBid;
  }
  /**
   * Setter du nombre de crédits à utiliser par l'utilisateur à chaque enchère
   * @param creditNbPerBid Nombre de crédits à utiliser par l'utilisateur à chaque
   * enchère à positionner
   */
  private void setCreditNbPerBid(int creditNbPerBid)
  {
    this.creditNbPerBid = creditNbPerBid;
  }

  /**
   * Getter du montant d'un incrément d'enchère
   * @return Le montant d'un incrément d'enchère
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "type", column = @Column(name = "BID_INCREMENT_CURRENCY")),
                       @AttributeOverride(name = "value", column = @Column(name = "BID_INCREMENT_VALUE"))})
  public Amount getBidIncrement()
  {
    return this.bidIncrement;
  }
  /**
   * Setter du montant d'un incrément d'enchère
   * @param bidIncrement montant d'un incrément d'enchère à positionner
   */
  private void setBidIncrement(Amount bidIncrement)
  {
    this.bidIncrement = bidIncrement;
  }
}
