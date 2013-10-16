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
 * Cette classe d�fini les conditions de base d'une vente aux ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class TermsAbstract<CLASS extends TermsAbstract<CLASS>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** D�finition de la pr�cision apr�s la virgule par d�faut de la valeur d'un incr�ment d'ench�re */
  public final static int PRECISION = 3;

  /** Nombre de cr�dits � utiliser par l'utilisateur � chaque ench�re */
  @Transient
  private int creditNbPerBid = 0;
  /** Montant d'un incr�ment d'ench�re */
  private Amount bidIncrement = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected TermsAbstract()
  {
    super();
  }
  /**
   * Constructeur
   * @param creditNbPerBid Nombre de cr�dits � utiliser par l'utilisateur � chaque
   * ench�re
   * @param bidIncrementValue Montant d'un incr�ment d'ench�re dans la monnaie par
   * d�faut
   * @throws UserException Si le nombre de cr�dits � utiliser est n�gatif ou le
   * montant d'un incr�ment d'ench�re inf�rieur ou �gal � z�ro
   */
  public TermsAbstract(int creditNbPerBid, double bidIncrementValue) throws UserException
  {
    super();
    this.defineCreditNbPerBid(creditNbPerBid);
    this.defineBidIncrementValue(bidIncrementValue);
  }

  /**
   * Red�fini l'�galit� interne de deux jeux de conditions de vente aux ench�res
   * par l'�galit� de leur contenu
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
   * Red�fini la transformation en cha�ne de caract�res d'un jeu de condition de
   * vente aux ench�res lisiblement
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
   * Cette m�thode permet de d�finir le nombre de cr�dits � utiliser l'utilisateur
   * � chaque ench�re
   * @param creditNbPerBid D�finition du nombre de cr�dits � utiliser par l'utilisateur
   * � chaque ench�re
   * @throws ProtectionException Si les conditions d'ench�re courantes sont prot�g�es
   * @throws UserException Si on d�finie un nombre de cr�dits � utiliser n�gatif
   */
  private void defineCreditNbPerBid(int creditNbPerBid) throws ProtectionException, UserException
  {
    // V�rifie la protection des conditions d'ench�re courantes
    this.checkProtection();
    this.setCreditNbPerBid(UtilNumber.checkMinValue("creditNbPerBid", creditNbPerBid, 0, true,
                                                    AuctionRef.TERMS_INVALID_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir la valeur d'un incr�ment d'ench�re
   * @param bidIncrementValue D�finition de la valeur d'un incr�ment d'ench�re dans
   * la monnaie par d�faut
   * @throws ProtectionException Si les conditions d'ench�re courantes sont prot�g�es
   * @throws UserException Si la valeur d'un incr�ment d'ench�re est inf�rieure
   * � z�ro
   */
  private void defineBidIncrementValue(double bidIncrementValue)
          throws ProtectionException, UserException
  {
    // V�rifie la protection des conditions d'ench�re courantes
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
   * Getter du nombre de cr�dits � utiliser par l'utilisateur � chaque ench�re
   * @return Le nombre de cr�dits � utiliser par l'utilisateur � chaque ench�re
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB_PER_BID", length = 3, nullable = false, unique = false)
  public int getCreditNbPerBid()
  {
    return this.creditNbPerBid;
  }
  /**
   * Setter du nombre de cr�dits � utiliser par l'utilisateur � chaque ench�re
   * @param creditNbPerBid Nombre de cr�dits � utiliser par l'utilisateur � chaque
   * ench�re � positionner
   */
  private void setCreditNbPerBid(int creditNbPerBid)
  {
    this.creditNbPerBid = creditNbPerBid;
  }

  /**
   * Getter du montant d'un incr�ment d'ench�re
   * @return Le montant d'un incr�ment d'ench�re
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
   * Setter du montant d'un incr�ment d'ench�re
   * @param bidIncrement montant d'un incr�ment d'ench�re � positionner
   */
  private void setBidIncrement(Amount bidIncrement)
  {
    this.bidIncrement = bidIncrement;
  }
}
