package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.reference.MessageRef.AuctionRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.credit.collection.CreditMap;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.persistence.entity.sale.Sale;
import com.bid4win.persistence.entity.sale.Step;

/**
 * Cette classe d�fini l'abstraction de toute vente aux ench�res<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <BID> D�finition des ench�res associ�es � la vente<BR>
 * @param <SCHEDULE> D�finition les �l�ments de planification de la vente aux ench�res<BR>
 * @param <TERMS> D�finition des conditions de la vente aux ench�res<BR>
 * @param <CANCEL_POLICY> D�finition de la politique d'annulation de la vente aux
 * ench�res<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AuctionAbstract<CLASS extends AuctionAbstract<CLASS, BID, SCHEDULE, TERMS, CANCEL_POLICY>,
                                      BID extends BidAbstract<BID, CLASS, ?>,
                                      SCHEDULE extends ScheduleAbstract<SCHEDULE>,
                                      TERMS extends TermsAbstract<TERMS>,
                                      CANCEL_POLICY extends CancelPolicyAbstract<CANCEL_POLICY>>
       extends Sale<CLASS>
{
  // TODO schedule avec opening date par d�faut +
  // prebookingDate pour les prebooked


  /** Taux de change applicables sur la vente aux ench�res */
  @Transient
  private ExchangeRates exchangeRates = null;
  /** Status de la vente aux ench�res */
  @Transient
  private AuctionStatus status = null;
  /** �l�ments de planification de la vente aux ench�res */
  @Transient
  private SCHEDULE schedule = null;
  /** Conditions de la vente aux ench�res */
  @Transient
  private TERMS terms = null;
  /** Politique d'annulation de la vente aux ench�res */
  @Transient
  private CANCEL_POLICY cancelPolicy = null;
  /** Nombre d'ench�res plac�es sur la vente */
  @Transient
  private int bidNb = 0;
  /** �tat d'historiation des ench�res la vente */
  @Transient
  private boolean bidHistorized = false;
  /** Nombre de cr�dits impliqu�s sur la vente aux ench�res */
  @Transient
  private int involvedCreditNb = 0;
  /** Valeur des implications sur la vente aux ench�res */
  @Transient
  private Amount involvementValue = null;
  /** �tat d'historiation des implications sur la vente aux ench�res */
  @Transient
  private boolean involvementHistorized = false;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AuctionAbstract()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux ench�res
   * @param product Produit vendu aux ench�res
   * @param schedule �l�ments de planification de base de la vente aux ench�res
   * @param terms Conditions de base de la vente aux ench�res
   * @throws UserException Si le produit, les �l�ments de planification ou les
   * conditions en argument sont nuls
   */
  protected AuctionAbstract(Product product, SCHEDULE schedule, TERMS terms)
            throws UserException
  {
    super(product);
    this.setInvolvementValue(new Amount(0));
    this.unvalidate(schedule, terms);
  }

  /**
   * Cette m�thode permet de d�finir la base des r�f�rences de messages li�es aux
   * ventes aux ench�res
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return AuctionRef.AUCTION;
  }

  /**
   * Red�fini l'�quivalence interne de deux ventes aux ench�res sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getExchangeRates(),
                                                              toBeCompared.getExchangeRates()) &&
           this.getStatus().equals(toBeCompared.getStatus()) &&
           this.getSchedule().equals(toBeCompared.getSchedule()) &&
           this.getTerms().equals(toBeCompared.getTerms()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getCancelPolicy(),
                                                              toBeCompared.getCancelPolicy()) &&
           this.getBidNb() == toBeCompared.getBidNb() &&
           this.isBidHistorized() == toBeCompared.isBidHistorized() &&
           this.getInvolvedCreditNb() == toBeCompared.getInvolvedCreditNb() &&
           this.getInvolvementValue().equals(toBeCompared.getInvolvementValue()) &&
           this.isInvolvementHistorized() == toBeCompared.isInvolvementHistorized();
  }
  /**
   * Permet d'effectuer le rendu simple de la vente aux ench�res courante sans
   * prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de la vente aux ench�res
    StringBuffer buffer1 = new StringBuffer(" BID_NB=" + this.getBidNb());
    buffer1.append(" INVOLVED_CREDIT_NB=" + this.getInvolvedCreditNb());
    buffer1.append(" INVOLVEMENT_VALUE=").append(this.getInvolvementValue().render());
    if(this.getExchangeRates() != null)
    {
      buffer1.append(" EXCHANGE_RATES=[").append(this.getExchangeRates().render()).append("]");
    }

    StringBuffer buffer2 = new StringBuffer(" ").append(this.getStatus().render());
    buffer2.append(" ").append(this.getSchedule().render());
    buffer2.append(" ").append(this.getTerms().render());
    if(this.getCancelPolicy() != null)
    {
      buffer2.append(" ").append(this.getCancelPolicy().render());
    }
    // Retourne le rendu
    return UtilString.addLine(buffer, UtilString.addParagraph(buffer1, buffer2));
  }

  /**
   * Cette m�thode permet de valider le prix du produit vendu aux ench�res au moment
   * de l'appel � cette m�thode au cas o� sa d�finition dans le produit aurait chang�
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.sale.Sale#validateProductPrice()
   */
  @Override
  public void validateProductPrice() throws ProtectionException, UserException
  {
    // La vente aux ench�res doit �tre en construction
    UtilBoolean.checkTrue("working", this.isWorking(),
                          AuctionRef.STATUS_INVALID_ERROR);
    // Positionne le nouveau prix
    super.validateProductPrice();
  }

  /**
   * Cette m�thode doit �tre d�finie afin de remonter l'ench�re remportant la vente
   * @return L'ench�re remportant la vente
   * @throws UserException Si aucune ench�re ne remporte la vente
   */
  public abstract BID getWinningBid() throws UserException;
  /**
   * Cette fonction permet d'ajouter une ench�re � la vente courante
   * @param account Compte utilisateur de l'ench�re � ajouter � la vente courante
   * @param bidDate Date de positionnement de l'ench�re
   * @return L'ench�re ajout�e � la vente courante
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si le comte utilisateur en argument est nul ou est d�j�
   * le dernier ench�risseur ou si la vente aux ench�res n'est pas d�marr�e
   */
  public BID addBid(Account account, Bid4WinDate bidDate) throws ProtectionException, UserException
  {
    // La vente aux ench�res doit �tre d�marr�e
    UtilBoolean.checkTrue("started", this.isStarted(),
                          AuctionRef.STATUS_NOT_STARTED_ERROR);
    // La date d'ench�res ne doit pas �tre nulle
    UtilObject.checkNotNull("bidDate", bidDate, AuctionRef.SCHEDULE_INVALID_ERROR);
    // Cr�e l'ench�re et l'ajoute � la vente
    BID bid = this.createBid(this.checkBidder(account), bidDate);
    // Augmente le nombre d'ench�res de la vente
    this.defineBidNb(this.getBidNb() + 1);
    return bid;
  }
  /**
   * Cette m�thode permet de v�rifier que le compte utilisateur en argument n'est
   * pas nul et peut �tre red�finie afin d'ajouter les contr�les sp�cifiques �
   * chaque type de vente aux ench�res
   * @param account Compte utilisateur � v�rifier
   * @return Le compte utilisateur v�rifi�
   * @throws UserException Si le comte utilisateur en argument est nul
   */
  protected Account checkBidder(Account account) throws UserException
  {
    // V�rifie que le compte utilisateur de l'ench�re � ajouter n'est pas nul
    return UtilObject.checkNotNull("account", account, AccountRef.ACCOUNT_MISSING_ERROR);
  }
  /**
   * Cette m�thode doit �tre d�finie afin de cr�er l'ench�re associ�e � la vente
   * en cours pour le compte utilisateur en argument
   * @param account Compte utilisateur de l'ench�re
   * @param bidDate Date de positionnement de l'ench�re
   * @throws ProtectionException Si la vente aux ench�res est prot�g�e
   * @throws UserException Si la cr�ation de l'ench�re est impossible
   */
  protected abstract BID createBid(Account account, Bid4WinDate bidDate) throws ProtectionException, UserException;



  /**
   * Getter du status de la vente aux ench�res courante
   * @return Le status de la vente aux ench�res courante
   */
  public Status getStatus()
  {
    if(this.getAuctionStatus() == null)
    {
      return Status.WORKING;
    }
    return this.getAuctionStatus().getType();
  }
  /**
   * Cette m�thode permet de savoir si la vente aux ench�res courante est en construction
   * @return True si la vente aux ench�res courante est en construction, false sinon
   */
  public boolean isWorking()
  {
    return this.getStatus().equals(Status.WORKING);
  }
  /**
   * Cette m�thode permet de savoir si la vente aux ench�res courante a �t� valid�e
   * @return True si la vente aux ench�res courante a �t� valid�e, false sinon
   */
  public boolean isValid()
  {
    return !this.isWorking();
  }
  /**
   * Cette m�thode permet de savoir si la vente aux ench�res courante est ouverte
   * @return True si la vente aux ench�res courante est ouverte, false sinon
   */
  public boolean isOpened()
  {
    return this.getStatus().belongsTo(Status.OPENED);
  }
  /**
   * Cette m�thode permet de savoir si la vente aux ench�res courante est d�marr�e
   * @return True si la vente aux ench�res courante est d�marr�e, false sinon
   */
  public boolean isStarted()
  {
    return this.getStatus().equals(Status.STARTED);
  }
  /**
   * Cette m�thode permet de savoir si la vente aux ench�res courante est termin�e
   * @return True si la vente aux ench�res courante est termin�e, false sinon
   */
  public boolean isEnded()
  {
    return this.getStatus().belongsTo(Status.ENDED);
  }
  /**
   * Cette m�thode permet de valider la vente aux ench�res courante. Le prix du
   * produit vendu sera alors fig� et la vente sera d�marr�e automatiquement selon
   * sa planification
   * @param cancelPolicy Politique d'annulation de la vente aux ench�res
   * @param exchangeRates Taux de change applicables sur la vente aux ench�res
   * @return La vente aux ench�res valid�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si la vente aux ench�res a d�j� �t� d�marr�e ou si la
   * politique d'annulation en argument est nulle
   */
  public CLASS validate(CANCEL_POLICY cancelPolicy, ExchangeRates exchangeRates)
         throws ProtectionException, UserException
  {
    UtilObject.checkNotNull("cancelPolicy", cancelPolicy,
                            AuctionRef.TERMS_MISSING_ERROR);
    UtilObject.checkNotNull("exchangeRates", exchangeRates,
                            CurrencyRef.EXCHANGE_RATE_MISSING_ERROR);
    CLASS result = this.defineStatus(Status.WAITING);
    this.setCancelPolicy(cancelPolicy);
    this.setExchangeRates(exchangeRates);
    // Valide les prix de produits non d�finis dans certaines monnaies
    for(Amount rate : this.getExchangeRates().getEmbeddedSet())
    {
      // R�cup�re le montant dans la monnaie courante
      Amount amount = this.getProductPrice().getAmount(rate.getCurrency());
      // Le montant n'est pas d�fini pour la monnaie courante
      if(!amount.getCurrency().equals(rate.getCurrency()))
      {
        // Calcule le montant dans la monnaie courante et met � jour le prix du produit
        amount = this.getExchangeRates().changeTo(this.getProductPrice().getAmount(),
                                                  rate.getCurrency());
        this.getProductPrice().addEmbedded(amount);
      }
    }
    return result;
  }
  /**
   * Cette m�thode permet d'invalider la vente aux ench�res courante
   * @param schedule �l�ments de planification de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res
   * @return La vente aux ench�res en construction
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si la vente aux ench�res a d�j� �t� d�marr�e ou si les
   * �l�ments de planification ou les conditions des la vente aux ench�res sont nuls
   */
  public CLASS unvalidate(SCHEDULE schedule, TERMS terms) throws ProtectionException, UserException
  {
    UtilObject.checkNotNull("schedule", schedule, AuctionRef.SCHEDULE_MISSING_ERROR);
    UtilObject.checkNotNull("terms", terms, AuctionRef.TERMS_MISSING_ERROR);
    CLASS auction = this.defineStatus(Status.WORKING);
    this.setSchedule(schedule);
    this.setTerms(terms);
    this.setCancelPolicy(null);
    return auction;
  }
  /**
   * Cette m�thode permet de d�marrer la vente aux ench�res courante
   * @return La vente aux ench�res d�marr�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si la vente aux ench�res n'a pas �t� valid�e ou a d�j�
   * �t� ouverte
   */
  public CLASS start() throws ProtectionException, UserException
  {
    return this.defineStatus(Status.STARTED);
  }
  /**
   * Cette m�thode permet de cl�turer la vente aux ench�res courante
   * @return La vente aux ench�res cl�tur�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si la vente aux ench�res n'est pas d�marr�e ou si aucune
   * ench�re ne remporte la vente
   */
  public CLASS close() throws ProtectionException, UserException
  {
    CLASS result = this.defineStatus(Status.CLOSED);
    this.linkToAccount(this.getWinningBid().getAccount());
    return result;
  }
  /**
   * Cette m�thode permet d'annuler la vente aux ench�res courante
   * @return La vente aux ench�res annul�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si la vente aux ench�res n'est pas d�marr�e
   */
  public CLASS cancel() throws ProtectionException, UserException
  {
    return this.defineStatus(Status.CANCELED);
  }
  /**
   * Cette m�thode prend en charge la modification du status de la vente aux ench�res
   * courante en le validant par rapport au status pr�c�dent
   * @param status Nouveau status de la vente aux ench�res
   * @return La vente aux ench�res modifi�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si le nouveau status de la vente aux ench�res est invalide
   */
  @SuppressWarnings("unchecked")
  protected CLASS defineStatus(Status status) throws ProtectionException, UserException
  {
    // V�rifie la protection de la vente aux ench�res courante
    this.checkProtection();
    UtilObject.checkNotNull("status", status, AuctionRef.STATUS_MISSING_ERROR);
    if(status.equals(Status.WORKING) || status.equals(Status.WAITING))
    {
      UtilObject.checkAmong("status", this.getStatus(),
                            new Bid4WinSet<Status>(Status.WORKING, Status.WAITING),
                            AuctionRef.STATUS_INVALID_ERROR);
    }
    else if(status.equals(Status.PAUSED))
    {
      UtilObject.checkEquals("status", this.getStatus(), Status.STARTED,
                             AuctionRef.STATUS_INVALID_ERROR);
    }
    else if(status.belongsTo(Status.OPENED))
    {
      UtilObjectType.checkBelongsTo("status", this.getStatus(), Status.VALID,
                                    AuctionRef.STATUS_INVALID_ERROR);
    }
    else
    {
      UtilObjectType.checkBelongsTo("status", this.getStatus(), Status.OPENED,
                                    AuctionRef.STATUS_INVALID_ERROR);
      UtilObjectType.checkNotBelongsTo("status", this.getStatus(), Status.PAUSED,
                                       AuctionRef.STATUS_INVALID_ERROR);
    }
    this.setAuctionStatus(new AuctionStatus(status));
    return (CLASS)this;
  }
  /**
   *
   * TODO A COMMENTER
   * @param step {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.sale.Sale#defineStep(com.bid4win.persistence.entity.sale.Step)
   */
  @Override
  protected CLASS defineStep(Step step) throws ProtectionException, UserException
  {
    if(step != null && !step.belongsTo(Step.PROCESSING))
    {
      UtilObjectType.checkBelongsTo("status", this.getStatus(), Status.ENDED,
                                    AuctionRef.STATUS_INVALID_ERROR);
    }
    return super.defineStep(step);
  }
  /**
   * Cette m�thode permet de d�finir le nombre d'ench�res plac�es sur la vente
   * @param bidNb D�finition du nombre d'ench�res plac�es sur la vente
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si on d�fini un nombre inf�rieur � z�ro
   */
  protected void defineBidNb(int bidNb) throws ProtectionException, UserException
  {
    // V�rifie la protection de la vente aux ench�res courante
    this.checkProtection();
    this.setBidNb(UtilNumber.checkMinValue("bidNb", bidNb, 0, true,
                  AuctionRef.BID_NB_INVALID_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir que les ench�res de la vente courante sont
   * historis�es
   * @return La vente des ench�res historis�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si les ench�res sont d�j� historis�es ou si la vente
   * n'est pas termin�e
   */
  @SuppressWarnings("unchecked")
  public CLASS historizeBids() throws ProtectionException, UserException
  {
    // V�rifie la protection de la vente aux ench�res courante
    this.checkProtection();
    // La vente aux ench�res doit �tre termin�e
    UtilBoolean.checkTrue("ended", this.isEnded(),
                          AuctionRef.STATUS_NOT_ENDED_ERROR);
    UtilBoolean.checkFalse("bidHistorized", this.isBidHistorized(),
                           AuctionRef.AUCTION_HISTORIZED_ERROR);
    this.setBidHistorized(true);
    return (CLASS)this;
  }
  /**
   * Cette m�thode permet d'augmenter le nombre de cr�dits impliqu�s et leur valeur
   * sur la vente aux ench�res courante
   * @param creditMap Map de cr�dits nouvellement impliqu�s sur la vente aux ench�res
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si le calcul de l'implication globale de cr�dits �choue
   * ou si la vente n'est pas ouverte
   */
  public void addInvolvedCredit(CreditMap creditMap) throws UserException
  {
    // V�rifie la protection de la vente aux ench�res courante
    this.checkProtection();
    UtilBoolean.checkTrue("opened", this.isOpened(),
                          AuctionRef.STATUS_NOT_STARTED_ERROR);
    if(creditMap == null)
    {
      return;
    }
    // Augmente le nombre de cr�dits impliqu�s sur la vente aux ench�res courante
    this.setInvolvedCreditNb(this.getInvolvedCreditNb() + creditMap.getTotalNb());
    // Augmente la valeur des implications sur la vente aux ench�res courante
    this.setInvolvementValue(this.getInvolvementValue().add(creditMap.getTotalValue()));
  }
  /**
   * Cette m�thode permet de d�finir que les implications sur la vente aux ench�res
   * courante sont historis�es
   * @param invovlvementValue Valeur totale de toutes les implications historis�es
   * @return La vente aux ench�res des implications historis�e
   * @throws ProtectionException Si la vente aux ench�res courante est prot�g�e
   * @throws UserException Si implications sont d�j� historis�es ou si la vente
   * n'est pas termin�e
   */
  @SuppressWarnings("unchecked")
  public CLASS historizeInvolvements(Amount invovlvementValue)
         throws ProtectionException, UserException
  {
    // V�rifie la protection de la vente aux ench�res courante
    this.checkProtection();
    // La vente aux ench�res doit �tre termin�e
    UtilBoolean.checkTrue("ended", this.isEnded(),
                          AuctionRef.STATUS_NOT_ENDED_ERROR);
    UtilBoolean.checkFalse("involvementHistorized", this.isInvolvementHistorized(),
                           AuctionRef.AUCTION_HISTORIZED_ERROR);
    UtilObject.checkNotNull("invovlvementValue", invovlvementValue,
                            AuctionRef.VALUE_MISSING_ERROR);
    this.setInvolvementHistorized(true);
    this.setInvolvementValue(invovlvementValue);
    return (CLASS)this;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter des taux de change applicables sur la vente aux ench�res
   * @return Les taux de change applicables sur la vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  @AttributeOverride(name = "embeddedMap", column = @Column(name = "EXCHANGE_RATES"))
  public ExchangeRates getExchangeRates()
  {
    return this.exchangeRates;
  }
  /**
   * Setter des taux de change applicables sur la vente aux ench�res
   * @param exchangeRates Taux de change applicables sur la vente aux ench�res �
   * positionner
   */
  private void setExchangeRates(ExchangeRates exchangeRates)
  {
    this.exchangeRates = exchangeRates;
  }

  /**
   * Getter du status de la vente aux ench�res
   * @return Le status de la vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  private AuctionStatus getAuctionStatus()
  {
    return this.status;
  }
  /**
   * Setter du status de la vente aux ench�res
   * @param status Status de la vente aux ench�res � positionner
   */
  private void setAuctionStatus(AuctionStatus status)
  {
    this.status = status;
  }

  /**
   * Getter des �l�ments de planification de la vente aux ench�res
   * @return Les �l�ments de planification de la vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public SCHEDULE getSchedule()
  {
    return this.schedule;
  }
  /**
   * Setter des �l�ments de planification de la vente aux ench�res
   * @param schedule �l�ments de planification de la vente aux ench�res � positionner
   */
  private void setSchedule(SCHEDULE schedule)
  {
    this.schedule = schedule;
  }

  /**
   * Getter des conditions de la vente aux ench�res
   * @return Les conditions de la vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public TERMS getTerms()
  {
    return this.terms;
  }
  /**
   * Setter des conditions de la vente aux ench�res
   * @param terms Conditions de la vente aux ench�res � positionner
   */
  private void setTerms(TERMS terms)
  {
    this.terms = terms;
  }

  /**
   * Getter de la politique d'annulation de la vente aux ench�res
   * @return La politique d'annulation de la vente aux ench�res, nulle jusqu'�
   * la validation de cette derni�re
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public CANCEL_POLICY getCancelPolicy()
  {
    return this.cancelPolicy;
  }
  /**
   * Setter de la politique d'annulation de la vente aux ench�res
   * @param cancelPolicy Politique d'annulation de la vente aux ench�res � positionner
   */
  private void setCancelPolicy(CANCEL_POLICY cancelPolicy)
  {
    this.cancelPolicy = cancelPolicy;
  }

  /**
   * Getter du nombre d'ench�res plac�es sur la vente
   * @return Le nombre d'ench�res plac�es sur la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "BID_NB", length = 6, nullable = false, unique = false)
  public int getBidNb()
  {
    return this.bidNb;
  }
  /**
   * Setter du nombre d'ench�res plac�es sur la vente
   * @param bidNb Nombre d'ench�res plac�es sur la vente � positionner
   */
  private void setBidNb(int bidNb)
  {
    this.bidNb = bidNb;
  }

  /**
   * Getter de l'�tat d'historiation des ench�res la vente
   * @return L'�tat d'historiation des ench�res la vente
   */
  @Access(AccessType.PROPERTY)
  @Column(name = "BID_HISTORIZED", nullable = false, unique = false)
  public boolean isBidHistorized()
  {
    return this.bidHistorized;
  }
  /**
   * Setter de l'�tat d'historiation des ench�res la vente
   * @param bidHistorized �tat d'historiation des ench�res la vente � positionner
   */
  private void setBidHistorized(boolean bidHistorized)
  {
    this.bidHistorized = bidHistorized;
  }

  /**
   * Getter du nombre de cr�dits impliqu�s sur la vente aux ench�res
   * @return Le nombre de cr�dits impliqu�s sur la vente aux ench�res
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "INVOLVED_CREDIT_NB", length = 6, nullable = false, unique = false)
  public int getInvolvedCreditNb()
  {
    return this.involvedCreditNb;
  }
  /**
   * Setter du nombre de cr�dits impliqu�s sur la vente aux ench�res
   * @param involvedCreditNb Nombre de cr�dits impliqu�s sur la vente aux ench�res
   * � positionner
   */
  private void setInvolvedCreditNb(int involvedCreditNb)
  {
    this.involvedCreditNb = involvedCreditNb;
  }

  /**
   * Getter de l'�tat d'historiation des implications sur la vente aux ench�res
   * @return L'�tat d'historiation des implications sur la vente aux ench�res
   */
  @Access(AccessType.PROPERTY)
  @Column(name = "INVOLVEMENT_HISTORIZED", nullable = false, unique = false)
  public boolean isInvolvementHistorized()
  {
    return this.involvementHistorized;
  }
  /**
   * Setter de l'�tat d'historiation des implications sur la vente aux ench�res
   * @param involvementHistorized �tat d'historiation des implications sur la
   * vente aux ench�res � positionner
   */
  private void setInvolvementHistorized(boolean involvementHistorized)
  {
    this.involvementHistorized = involvementHistorized;
  }

  /**
   * Getter de la valeur des implications sur la vente aux ench�res
   * @return La valeur des implications sur la vente aux ench�res
   */
  @Access(AccessType.PROPERTY)
  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "type", column = @Column(name = "INVOLVEMENT_CURRENCY")),
                       @AttributeOverride(name = "value", column = @Column(name = "INVOLVEMENT_VALUE"))})
  public Amount getInvolvementValue()
  {
    return this.involvementValue;
  }
  /**
   * Setter de la valeur des implications sur la vente aux ench�res
   * @param involvementValue Valeur des implications sur la vente � positionner
   */
  private void setInvolvementValue(Amount involvementValue)
  {
    this.involvementValue = involvementValue;
  }
}