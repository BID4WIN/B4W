package com.bid4win.persistence.entity.account.credit;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.Account_Relations;

/**
 * Cette classe défini un lot de crédits pour un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@AttributeOverride(name = "nb", column = @Column(name = "INITIAL_NB"))
public class CreditBundle extends CreditBundleAbstract<CreditBundle>
{
  /** Lien vers le compte utilisateur du lot de crédits */
  @Transient private Account accountLink = null;
  /** Nombre courant de crédits du lot */
  @Transient private int currentNb = 0;
  /** Identifiant d'historisation du lot de crédits */
  @Transient private Long historyId = null;
  /** Historisation du lot de crédits */
  @Transient private CreditBundleHistory history = null;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditBundle()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param totalValue Valeur réelle totale des crédits du lot dans la monnaie par
   * défaut
   * @param nb Nombre de crédits du lot
   * @throws ProtectionException Si le comte utilisateur en argument est protégé
   * @throws UserException Si le compte utilisateur des crédits ou leur provenance
   * est nul ou si leur valeur réelle unitaire est négative ou si leur nombre est
   * inférieur à un
   */
  public CreditBundle(Account account, CreditOrigin origin, double totalValue, int nb)
         throws ProtectionException, UserException
  {
    super(account, origin, totalValue, nb);
    // Défini le nombre courant de crédits du lot
    this.defineCurrentNb(nb);
    // Lie le lot de crédits à son compte utilisateur
    this.linkToAccount();
  }

  /**
   * Redéfini l'équivalence interne de deux lots de crédits sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs données propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.persistence.entity.account.credit.CreditBundleAbstract#sameRelationNoneInternal(com.bid4win.persistence.entity.account.credit.CreditBundleAbstract, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CreditBundle toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           this.isHistorized() == toBeCompared.isHistorized() &&
           this.getCurrentNb() == toBeCompared.getCurrentNb();
  }
  /**
   * Permet d'effectuer le rendu simple du lot de crédits courant sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments du lot de crédits
    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Ajoute à la liste des noeuds de relations du lot de crédits le lien vers son
   * compte utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(CreditBundle_Relations.NODE_ACCOUNT_LINK);
    nodeList.add(CreditBundle_Relations.NODE_HISTORY);
    return nodeList;
  }
  /**
   * Permet de récupérer le lien vers le compte utilisateur du lot de crédits s'
   * il correspond à la relation en argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(CreditBundle_Relations.RELATION_ACCOUNT_LINK))
    {
      return this.getAccountLink();
    }
    if(relation.equals(CreditBundle_Relations.RELATION_HISTORY))
    {
      return this.getHistory();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le lien vers le compte utilisateur du lot de crédits
   * s'il correspondant à la relation en argument. Elle doit être redéfinie pour
   * toute nouvelle relation de type simple à positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(CreditBundle_Relations.RELATION_ACCOUNT_LINK))
    {
      this.setAccountLink((Account)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(CreditBundle_Relations.RELATION_ACCOUNT_LINK))
    {
      return AccountRef.ACCOUNT;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   * Cette méthode permet d'utiliser un certain nombre de crédits du lot. Si le
   * lot est vide une fois les crédits utilisés, il sera retiré du compte utilisateur
   * @param nb Nombre de crédits à utiliser
   * @return Le lot courant des crédits utilisés
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si le nombre de crédits à utiliser est inférieur à un
   * ou s'il n'y a pas assez de crédits dans le lot
   */
  public CreditBundle use(int nb) throws ProtectionException, UserException
  {
    // Cette méthode ne peut être appelée que depuis le compte utilisateur car
    // celui-ci doit suivre l'évolution des crédits utilisés
    if(!UtilSystem.getStackTraceElement(1).getClassName().equals(Account.class.getName()) ||
       !UtilSystem.getStackTraceElement(1).getMethodName().equals("useCredit"))
    {
      throw new ProtectionException("In protected against unauthorized access");
    }
    return this.useInternal(nb);
  }
  /**
   * Cette méthode permet d'utiliser un certain nombre de crédits du lot. Si le
   * lot est vide une fois les crédits utilisés, il sera retiré du compte utilisateur
   * @param nb Nombre de crédits à utiliser
   * @return Le lot courant des crédits utilisés
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si le nombre de crédits à utiliser est inférieur à un
   * ou s'il n'y a pas assez de crédits dans le lot
   */
  protected CreditBundle useInternal(int nb) throws ProtectionException, UserException
  {
    // Vérification du nombre de crédits à utiliser
    UtilNumber.checkMinValue("nb", nb, 1, true,
                             AccountRef.CREDIT_NB_INVALID_ERROR);
    try
    {
      // Diminution du nombre de crédits du lot
      this.defineCurrentNb(this.getCurrentNb() - nb);
    }
    catch(UserException ex)
    {
      throw new UserException(AccountRef.CREDIT_NB_INSUFFICIENT_ERROR, ex);
    }
    // Si le lot est complètement utilisé, on le retire du compte utilisateur
    if(this.getCurrentNb() == 0)
    {
      this.unlinkFromAccount();
    }
    return this;
  }

  /**
   * Permet de savoir si le lot de crédits courant est lié à un compte utilisateur
   * @return True si le lot de crédits courant est lié à un compte utilisateur,
   * false sinon
   */
  public boolean isLinkedToAccount()
  {
    return this.getAccountLink() != null;
  }
  /**
   * Cette méthode permet de lier le lot de crédits courant à son compte utilisateur
   * si le nombre de crédits restant est supérieur à zéro. Dans le cas contraire,
   * rien n'est fait
   * @throws ProtectionException Si le lot de crédits courant ou son compte utilisateur
   * est protégé
   * @throws UserException Si le compte utilisateur du lot de crédits est nul ou
   * le référence déjà ou si ce dernier est déjà lié ou vide
   */
  public void linkToAccount() throws ProtectionException, UserException
  {
    if(this.getCurrentNb() == 0)
    {
      throw new UserException(AccountRef.CREDIT_NB_INVALID_ERROR);
    }
    this.linkTo(CreditBundle_Relations.RELATION_ACCOUNT_LINK,
                Account_Relations.RELATION_CREDIT_BUNDLE_LIST,
                this.getAccount());
  }
  /**
   *
   * Cette méthode permet de délier le lot de crédits courant de son compte utilisateur
   * s'il ne reste plus de crédits
   * @return {@inheritDoc}
   * @throws ProtectionException Si le lot de crédits courant ou son compte utilisateur
   * est protégé
   * @throws UserException {@inheritDoc} ou si le lot de crédits courant n'est pas
   * complètement utilisé ou n'est pas référencé par son compte utilisateur
   */
  @Override
  public Account unlinkFromAccount() throws ProtectionException, UserException
  {
    if(this.getCurrentNb() != 0)
    {
      throw new UserException(AccountRef.CREDIT_NB_INVALID_ERROR);
    }
    return (Account)this.unlinkFrom(CreditBundle_Relations.RELATION_ACCOUNT_LINK,
                                    Account_Relations.RELATION_CREDIT_BUNDLE_LIST);
  }

  /**
   * Getter du nombre initial de crédits du lot
   * @return Le nombre initial de crédits du lot
   */
  public int getInitialNb()
  {
    return this.getNb();
  }
  /**
   * Getter du nombre de crédits utilisés sur le lot
   * @return Le nombre de crédits utilisés sur le lot
   */
  public int getUsedNb()
  {
    return this.getInitialNb() - this.getCurrentNb();
  }
  /**
   * Cette méthode permet de définir le nombre courant de crédits du lot
   * @param currentNb Définition du nombre courant de crédits du lot
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si on défini un nombre de crédits inférieur à zéro
   */
  protected void defineCurrentNb(int currentNb) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    this.setCurrentNb(UtilNumber.checkMinValue("currentNb", currentNb, 0, true,
                                               AccountRef.CREDIT_NB_INVALID_ERROR));
  }
  /**
   * Cette méthode permet de définir la valeur totale des crédits du lot
   * @param totalValue {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc} ou si le lot de crédits est historisé
   * @see com.bid4win.persistence.entity.account.credit.CreditBundleAbstract#defineTotalValue(double)
   */
  @Override
  public void defineTotalValue(double totalValue) throws ProtectionException, UserException
  {
    UtilBoolean.checkFalse("historized", this.isHistorized(),
                           AccountRef.CREDIT_HISTORIZED_ERROR);
    super.defineTotalValue(totalValue);
  }
  /**
   * Getter de l'état d'historiation du lot de crédits
   * @return L'état d'historiation du lot de crédits
   */
  public boolean isHistorized()
  {
    if(this.getHistoryId() != null || this.getHistory() != null)
    {
      return true;
    }
    return false;
  }
  /**
   * Cette méthode permet d'historiser le lot de crédits courant
   * @return L'historisation du lot de crédits courant
   * @throws ProtectionException Si le lot de crédit courant est protégé
   * @throws UserException Si le lot de crédits courant est déjà historisé
   */
  public CreditBundleHistory historize() throws ProtectionException, UserException
  {
    return this.defineHistory(new CreditBundleHistory(this));
  }
  /**
   * Cette méthode permet de positionner l'identifiant de l'historisation du lot
   * de crédits courant
   * @return Le lot de crédit courant
   * @throws UserException Si le lot de crédit courant n'est pas historisé ou si
   * l'identifiant de son historisation est nul
   */
  public CreditBundle defineHistoryId() throws UserException
  {
    UtilObject.checkNotNull("history", this.getHistory(),
                            AccountRef.CREDIT_NOT_HISTORIZED_ERROR);
    this.setHistoryId(UtilObject.checkNotNull("historyId", this.getHistory().getId(),
                      AccountRef.CREDIT_NOT_HISTORIZED_ERROR));
    return this;
  }
  /**
   *
   * TODO A COMMENTER
   * @param history TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected final CreditBundleHistory defineHistory(CreditBundleHistory history) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    UtilBoolean.checkFalse("historized", this.isHistorized(),
                           AccountRef.CREDIT_HISTORIZED_ERROR);
    this.setHistory(history);
    return history;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du lien vers le compte utilisateur du lot de crédits
   * @return Le lien vers le compte utilisateur du lot de crédits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "ACCOUNT_ID_LINK", nullable = true, unique = false)
  public Account getAccountLink()
  {
    return this.accountLink;
  }
  /**
   * Setter du lien vers le compte utilisateur du lot de crédits
   * @param account Compte utilisateur avec lequel lier le lot de crédits
   */
  private void setAccountLink(Account account)
  {
    this.accountLink = account;
  }

  /**
   * Getter du nombre courant de crédits du lot
   * @return Le nombre courant de crédits du lot
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CURRENT_NB", length = 3, nullable = false, unique = false)
  public int getCurrentNb()
  {
    return this.currentNb;
  }
  /**
   * Setter du nombre courant de crédits du lot
   * @param currentNb Nombre courant de crédits du lot à positionner
   */
  private void setCurrentNb(int currentNb)
  {
    this.currentNb = currentNb;
  }

  /**
   * Getter de l'identifiant d'historisation du lot de crédits
   * @return L'identifiant d'historisation du lot de crédits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "HISTORY_ID", length = 10, nullable = true, unique = true,
          // Utilise la même colonne que le lien réel vers la table des historisations
          insertable = false, updatable = false)
  public Long getHistoryId()
  {
    return this.historyId;
  }
  /**
   * Setter de l'identifiant d'historisation du lot de crédits
   * @param historyId Identifiant d'historisation du lot de crédits à positionner
   */
  private void setHistoryId(Long historyId)
  {
    this.historyId = historyId;
  }

  /**
   * Getter de l'historisation du lot de crédits
   * @return l'historisation du lot de crédits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "HISTORY_ID", nullable = true, unique = true)
  public CreditBundleHistory  getHistory()
  {
    return this.history;
  }
  /**
   * Setter de l'historisation du lot de crédits
   * @param history Historisation du lot de crédits à positionner
   */
  private void setHistory(CreditBundleHistory history)
  {
    this.history = history;
  }
}
