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
 * Cette classe défini l'abstraction de toute vente aux enchères<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <BID> Définition des enchères associées à la vente<BR>
 * @param <SCHEDULE> Définition les éléments de planification de la vente aux enchères<BR>
 * @param <TERMS> Définition des conditions de la vente aux enchères<BR>
 * @param <CANCEL_POLICY> Définition de la politique d'annulation de la vente aux
 * enchères<BR>
 * <BR>
 * @author Emeric Fillâtre
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
  // TODO schedule avec opening date par défaut +
  // prebookingDate pour les prebooked


  /** Taux de change applicables sur la vente aux enchères */
  @Transient
  private ExchangeRates exchangeRates = null;
  /** Status de la vente aux enchères */
  @Transient
  private AuctionStatus status = null;
  /** Éléments de planification de la vente aux enchères */
  @Transient
  private SCHEDULE schedule = null;
  /** Conditions de la vente aux enchères */
  @Transient
  private TERMS terms = null;
  /** Politique d'annulation de la vente aux enchères */
  @Transient
  private CANCEL_POLICY cancelPolicy = null;
  /** Nombre d'enchères placées sur la vente */
  @Transient
  private int bidNb = 0;
  /** État d'historiation des enchères la vente */
  @Transient
  private boolean bidHistorized = false;
  /** Nombre de crédits impliqués sur la vente aux enchères */
  @Transient
  private int involvedCreditNb = 0;
  /** Valeur des implications sur la vente aux enchères */
  @Transient
  private Amount involvementValue = null;
  /** État d'historiation des implications sur la vente aux enchères */
  @Transient
  private boolean involvementHistorized = false;

  /**
   * Constructeur pour création par introspection
   */
  protected AuctionAbstract()
  {
    super();
  }
  /**
   * Constructeur complet de la vente aux enchères
   * @param product Produit vendu aux enchères
   * @param schedule Éléments de planification de base de la vente aux enchères
   * @param terms Conditions de base de la vente aux enchères
   * @throws UserException Si le produit, les éléments de planification ou les
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
   * Cette méthode permet de définir la base des références de messages liées aux
   * ventes aux enchères
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return AuctionRef.AUCTION;
  }

  /**
   * Redéfini l'équivalence interne de deux ventes aux enchères sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs données propres
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
   * Permet d'effectuer le rendu simple de la vente aux enchères courante sans
   * prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments de la vente aux enchères
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
   * Cette méthode permet de valider le prix du produit vendu aux enchères au moment
   * de l'appel à cette méthode au cas où sa définition dans le produit aurait changé
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.persistence.entity.sale.Sale#validateProductPrice()
   */
  @Override
  public void validateProductPrice() throws ProtectionException, UserException
  {
    // La vente aux enchères doit être en construction
    UtilBoolean.checkTrue("working", this.isWorking(),
                          AuctionRef.STATUS_INVALID_ERROR);
    // Positionne le nouveau prix
    super.validateProductPrice();
  }

  /**
   * Cette méthode doit être définie afin de remonter l'enchère remportant la vente
   * @return L'enchère remportant la vente
   * @throws UserException Si aucune enchère ne remporte la vente
   */
  public abstract BID getWinningBid() throws UserException;
  /**
   * Cette fonction permet d'ajouter une enchère à la vente courante
   * @param account Compte utilisateur de l'enchère à ajouter à la vente courante
   * @param bidDate Date de positionnement de l'enchère
   * @return L'enchère ajoutée à la vente courante
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si le comte utilisateur en argument est nul ou est déjà
   * le dernier enchérisseur ou si la vente aux enchères n'est pas démarrée
   */
  public BID addBid(Account account, Bid4WinDate bidDate) throws ProtectionException, UserException
  {
    // La vente aux enchères doit être démarrée
    UtilBoolean.checkTrue("started", this.isStarted(),
                          AuctionRef.STATUS_NOT_STARTED_ERROR);
    // La date d'enchères ne doit pas être nulle
    UtilObject.checkNotNull("bidDate", bidDate, AuctionRef.SCHEDULE_INVALID_ERROR);
    // Crée l'enchère et l'ajoute à la vente
    BID bid = this.createBid(this.checkBidder(account), bidDate);
    // Augmente le nombre d'enchères de la vente
    this.defineBidNb(this.getBidNb() + 1);
    return bid;
  }
  /**
   * Cette méthode permet de vérifier que le compte utilisateur en argument n'est
   * pas nul et peut être redéfinie afin d'ajouter les contrôles spécifiques à
   * chaque type de vente aux enchères
   * @param account Compte utilisateur à vérifier
   * @return Le compte utilisateur vérifié
   * @throws UserException Si le comte utilisateur en argument est nul
   */
  protected Account checkBidder(Account account) throws UserException
  {
    // Vérifie que le compte utilisateur de l'enchère à ajouter n'est pas nul
    return UtilObject.checkNotNull("account", account, AccountRef.ACCOUNT_MISSING_ERROR);
  }
  /**
   * Cette méthode doit être définie afin de créer l'enchère associée à la vente
   * en cours pour le compte utilisateur en argument
   * @param account Compte utilisateur de l'enchère
   * @param bidDate Date de positionnement de l'enchère
   * @throws ProtectionException Si la vente aux enchères est protégée
   * @throws UserException Si la création de l'enchère est impossible
   */
  protected abstract BID createBid(Account account, Bid4WinDate bidDate) throws ProtectionException, UserException;



  /**
   * Getter du status de la vente aux enchères courante
   * @return Le status de la vente aux enchères courante
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
   * Cette méthode permet de savoir si la vente aux enchères courante est en construction
   * @return True si la vente aux enchères courante est en construction, false sinon
   */
  public boolean isWorking()
  {
    return this.getStatus().equals(Status.WORKING);
  }
  /**
   * Cette méthode permet de savoir si la vente aux enchères courante a été validée
   * @return True si la vente aux enchères courante a été validée, false sinon
   */
  public boolean isValid()
  {
    return !this.isWorking();
  }
  /**
   * Cette méthode permet de savoir si la vente aux enchères courante est ouverte
   * @return True si la vente aux enchères courante est ouverte, false sinon
   */
  public boolean isOpened()
  {
    return this.getStatus().belongsTo(Status.OPENED);
  }
  /**
   * Cette méthode permet de savoir si la vente aux enchères courante est démarrée
   * @return True si la vente aux enchères courante est démarrée, false sinon
   */
  public boolean isStarted()
  {
    return this.getStatus().equals(Status.STARTED);
  }
  /**
   * Cette méthode permet de savoir si la vente aux enchères courante est terminée
   * @return True si la vente aux enchères courante est terminée, false sinon
   */
  public boolean isEnded()
  {
    return this.getStatus().belongsTo(Status.ENDED);
  }
  /**
   * Cette méthode permet de valider la vente aux enchères courante. Le prix du
   * produit vendu sera alors figé et la vente sera démarrée automatiquement selon
   * sa planification
   * @param cancelPolicy Politique d'annulation de la vente aux enchères
   * @param exchangeRates Taux de change applicables sur la vente aux enchères
   * @return La vente aux enchères validée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si la vente aux enchères a déjà été démarrée ou si la
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
    // Valide les prix de produits non définis dans certaines monnaies
    for(Amount rate : this.getExchangeRates().getEmbeddedSet())
    {
      // Récupère le montant dans la monnaie courante
      Amount amount = this.getProductPrice().getAmount(rate.getCurrency());
      // Le montant n'est pas défini pour la monnaie courante
      if(!amount.getCurrency().equals(rate.getCurrency()))
      {
        // Calcule le montant dans la monnaie courante et met à jour le prix du produit
        amount = this.getExchangeRates().changeTo(this.getProductPrice().getAmount(),
                                                  rate.getCurrency());
        this.getProductPrice().addEmbedded(amount);
      }
    }
    return result;
  }
  /**
   * Cette méthode permet d'invalider la vente aux enchères courante
   * @param schedule Éléments de planification de la vente aux enchères
   * @param terms Conditions de la vente aux enchères
   * @return La vente aux enchères en construction
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si la vente aux enchères a déjà été démarrée ou si les
   * éléments de planification ou les conditions des la vente aux enchères sont nuls
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
   * Cette méthode permet de démarrer la vente aux enchères courante
   * @return La vente aux enchères démarrée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si la vente aux enchères n'a pas été validée ou a déjà
   * été ouverte
   */
  public CLASS start() throws ProtectionException, UserException
  {
    return this.defineStatus(Status.STARTED);
  }
  /**
   * Cette méthode permet de clôturer la vente aux enchères courante
   * @return La vente aux enchères clôturée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si la vente aux enchères n'est pas démarrée ou si aucune
   * enchère ne remporte la vente
   */
  public CLASS close() throws ProtectionException, UserException
  {
    CLASS result = this.defineStatus(Status.CLOSED);
    this.linkToAccount(this.getWinningBid().getAccount());
    return result;
  }
  /**
   * Cette méthode permet d'annuler la vente aux enchères courante
   * @return La vente aux enchères annulée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si la vente aux enchères n'est pas démarrée
   */
  public CLASS cancel() throws ProtectionException, UserException
  {
    return this.defineStatus(Status.CANCELED);
  }
  /**
   * Cette méthode prend en charge la modification du status de la vente aux enchères
   * courante en le validant par rapport au status précédent
   * @param status Nouveau status de la vente aux enchères
   * @return La vente aux enchères modifiée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si le nouveau status de la vente aux enchères est invalide
   */
  @SuppressWarnings("unchecked")
  protected CLASS defineStatus(Status status) throws ProtectionException, UserException
  {
    // Vérifie la protection de la vente aux enchères courante
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
   * Cette méthode permet de définir le nombre d'enchères placées sur la vente
   * @param bidNb Définition du nombre d'enchères placées sur la vente
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si on défini un nombre inférieur à zéro
   */
  protected void defineBidNb(int bidNb) throws ProtectionException, UserException
  {
    // Vérifie la protection de la vente aux enchères courante
    this.checkProtection();
    this.setBidNb(UtilNumber.checkMinValue("bidNb", bidNb, 0, true,
                  AuctionRef.BID_NB_INVALID_ERROR));
  }
  /**
   * Cette méthode permet de définir que les enchères de la vente courante sont
   * historisées
   * @return La vente des enchères historisée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si les enchères sont déjà historisées ou si la vente
   * n'est pas terminée
   */
  @SuppressWarnings("unchecked")
  public CLASS historizeBids() throws ProtectionException, UserException
  {
    // Vérifie la protection de la vente aux enchères courante
    this.checkProtection();
    // La vente aux enchères doit être terminée
    UtilBoolean.checkTrue("ended", this.isEnded(),
                          AuctionRef.STATUS_NOT_ENDED_ERROR);
    UtilBoolean.checkFalse("bidHistorized", this.isBidHistorized(),
                           AuctionRef.AUCTION_HISTORIZED_ERROR);
    this.setBidHistorized(true);
    return (CLASS)this;
  }
  /**
   * Cette méthode permet d'augmenter le nombre de crédits impliqués et leur valeur
   * sur la vente aux enchères courante
   * @param creditMap Map de crédits nouvellement impliqués sur la vente aux enchères
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si le calcul de l'implication globale de crédits échoue
   * ou si la vente n'est pas ouverte
   */
  public void addInvolvedCredit(CreditMap creditMap) throws UserException
  {
    // Vérifie la protection de la vente aux enchères courante
    this.checkProtection();
    UtilBoolean.checkTrue("opened", this.isOpened(),
                          AuctionRef.STATUS_NOT_STARTED_ERROR);
    if(creditMap == null)
    {
      return;
    }
    // Augmente le nombre de crédits impliqués sur la vente aux enchères courante
    this.setInvolvedCreditNb(this.getInvolvedCreditNb() + creditMap.getTotalNb());
    // Augmente la valeur des implications sur la vente aux enchères courante
    this.setInvolvementValue(this.getInvolvementValue().add(creditMap.getTotalValue()));
  }
  /**
   * Cette méthode permet de définir que les implications sur la vente aux enchères
   * courante sont historisées
   * @param invovlvementValue Valeur totale de toutes les implications historisées
   * @return La vente aux enchères des implications historisée
   * @throws ProtectionException Si la vente aux enchères courante est protégée
   * @throws UserException Si implications sont déjà historisées ou si la vente
   * n'est pas terminée
   */
  @SuppressWarnings("unchecked")
  public CLASS historizeInvolvements(Amount invovlvementValue)
         throws ProtectionException, UserException
  {
    // Vérifie la protection de la vente aux enchères courante
    this.checkProtection();
    // La vente aux enchères doit être terminée
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
   * Getter des taux de change applicables sur la vente aux enchères
   * @return Les taux de change applicables sur la vente aux enchères
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
   * Setter des taux de change applicables sur la vente aux enchères
   * @param exchangeRates Taux de change applicables sur la vente aux enchères à
   * positionner
   */
  private void setExchangeRates(ExchangeRates exchangeRates)
  {
    this.exchangeRates = exchangeRates;
  }

  /**
   * Getter du status de la vente aux enchères
   * @return Le status de la vente aux enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  private AuctionStatus getAuctionStatus()
  {
    return this.status;
  }
  /**
   * Setter du status de la vente aux enchères
   * @param status Status de la vente aux enchères à positionner
   */
  private void setAuctionStatus(AuctionStatus status)
  {
    this.status = status;
  }

  /**
   * Getter des éléments de planification de la vente aux enchères
   * @return Les éléments de planification de la vente aux enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public SCHEDULE getSchedule()
  {
    return this.schedule;
  }
  /**
   * Setter des éléments de planification de la vente aux enchères
   * @param schedule Éléments de planification de la vente aux enchères à positionner
   */
  private void setSchedule(SCHEDULE schedule)
  {
    this.schedule = schedule;
  }

  /**
   * Getter des conditions de la vente aux enchères
   * @return Les conditions de la vente aux enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public TERMS getTerms()
  {
    return this.terms;
  }
  /**
   * Setter des conditions de la vente aux enchères
   * @param terms Conditions de la vente aux enchères à positionner
   */
  private void setTerms(TERMS terms)
  {
    this.terms = terms;
  }

  /**
   * Getter de la politique d'annulation de la vente aux enchères
   * @return La politique d'annulation de la vente aux enchères, nulle jusqu'à
   * la validation de cette dernière
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public CANCEL_POLICY getCancelPolicy()
  {
    return this.cancelPolicy;
  }
  /**
   * Setter de la politique d'annulation de la vente aux enchères
   * @param cancelPolicy Politique d'annulation de la vente aux enchères à positionner
   */
  private void setCancelPolicy(CANCEL_POLICY cancelPolicy)
  {
    this.cancelPolicy = cancelPolicy;
  }

  /**
   * Getter du nombre d'enchères placées sur la vente
   * @return Le nombre d'enchères placées sur la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "BID_NB", length = 6, nullable = false, unique = false)
  public int getBidNb()
  {
    return this.bidNb;
  }
  /**
   * Setter du nombre d'enchères placées sur la vente
   * @param bidNb Nombre d'enchères placées sur la vente à positionner
   */
  private void setBidNb(int bidNb)
  {
    this.bidNb = bidNb;
  }

  /**
   * Getter de l'état d'historiation des enchères la vente
   * @return L'état d'historiation des enchères la vente
   */
  @Access(AccessType.PROPERTY)
  @Column(name = "BID_HISTORIZED", nullable = false, unique = false)
  public boolean isBidHistorized()
  {
    return this.bidHistorized;
  }
  /**
   * Setter de l'état d'historiation des enchères la vente
   * @param bidHistorized État d'historiation des enchères la vente à positionner
   */
  private void setBidHistorized(boolean bidHistorized)
  {
    this.bidHistorized = bidHistorized;
  }

  /**
   * Getter du nombre de crédits impliqués sur la vente aux enchères
   * @return Le nombre de crédits impliqués sur la vente aux enchères
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "INVOLVED_CREDIT_NB", length = 6, nullable = false, unique = false)
  public int getInvolvedCreditNb()
  {
    return this.involvedCreditNb;
  }
  /**
   * Setter du nombre de crédits impliqués sur la vente aux enchères
   * @param involvedCreditNb Nombre de crédits impliqués sur la vente aux enchères
   * à positionner
   */
  private void setInvolvedCreditNb(int involvedCreditNb)
  {
    this.involvedCreditNb = involvedCreditNb;
  }

  /**
   * Getter de l'état d'historiation des implications sur la vente aux enchères
   * @return L'état d'historiation des implications sur la vente aux enchères
   */
  @Access(AccessType.PROPERTY)
  @Column(name = "INVOLVEMENT_HISTORIZED", nullable = false, unique = false)
  public boolean isInvolvementHistorized()
  {
    return this.involvementHistorized;
  }
  /**
   * Setter de l'état d'historiation des implications sur la vente aux enchères
   * @param involvementHistorized État d'historiation des implications sur la
   * vente aux enchères à positionner
   */
  private void setInvolvementHistorized(boolean involvementHistorized)
  {
    this.involvementHistorized = involvementHistorized;
  }

  /**
   * Getter de la valeur des implications sur la vente aux enchères
   * @return La valeur des implications sur la vente aux enchères
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
   * Setter de la valeur des implications sur la vente aux enchères
   * @param involvementValue Valeur des implications sur la vente à positionner
   */
  private void setInvolvementValue(Amount involvementValue)
  {
    this.involvementValue = involvementValue;
  }
}