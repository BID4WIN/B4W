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
 * Cette classe d�fini un lot de cr�dits pour un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@AttributeOverride(name = "nb", column = @Column(name = "INITIAL_NB"))
public class CreditBundle extends CreditBundleAbstract<CreditBundle>
{
  /** Lien vers le compte utilisateur du lot de cr�dits */
  @Transient private Account accountLink = null;
  /** Nombre courant de cr�dits du lot */
  @Transient private int currentNb = 0;
  /** Identifiant d'historisation du lot de cr�dits */
  @Transient private Long historyId = null;
  /** Historisation du lot de cr�dits */
  @Transient private CreditBundleHistory history = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditBundle()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param totalValue Valeur r�elle totale des cr�dits du lot dans la monnaie par
   * d�faut
   * @param nb Nombre de cr�dits du lot
   * @throws ProtectionException Si le comte utilisateur en argument est prot�g�
   * @throws UserException Si le compte utilisateur des cr�dits ou leur provenance
   * est nul ou si leur valeur r�elle unitaire est n�gative ou si leur nombre est
   * inf�rieur � un
   */
  public CreditBundle(Account account, CreditOrigin origin, double totalValue, int nb)
         throws ProtectionException, UserException
  {
    super(account, origin, totalValue, nb);
    // D�fini le nombre courant de cr�dits du lot
    this.defineCurrentNb(nb);
    // Lie le lot de cr�dits � son compte utilisateur
    this.linkToAccount();
  }

  /**
   * Red�fini l'�quivalence interne de deux lots de cr�dits sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
   * Permet d'effectuer le rendu simple du lot de cr�dits courant sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments du lot de cr�dits
    buffer.append(" CURRENT_NB=" + this.getCurrentNb());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Ajoute � la liste des noeuds de relations du lot de cr�dits le lien vers son
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
   * Permet de r�cup�rer le lien vers le compte utilisateur du lot de cr�dits s'
   * il correspond � la relation en argument.
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
   * Permet de positionner le lien vers le compte utilisateur du lot de cr�dits
   * s'il correspondant � la relation en argument. Elle doit �tre red�finie pour
   * toute nouvelle relation de type simple � positionner
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
   * Cette m�thode permet d'utiliser un certain nombre de cr�dits du lot. Si le
   * lot est vide une fois les cr�dits utilis�s, il sera retir� du compte utilisateur
   * @param nb Nombre de cr�dits � utiliser
   * @return Le lot courant des cr�dits utilis�s
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si le nombre de cr�dits � utiliser est inf�rieur � un
   * ou s'il n'y a pas assez de cr�dits dans le lot
   */
  public CreditBundle use(int nb) throws ProtectionException, UserException
  {
    // Cette m�thode ne peut �tre appel�e que depuis le compte utilisateur car
    // celui-ci doit suivre l'�volution des cr�dits utilis�s
    if(!UtilSystem.getStackTraceElement(1).getClassName().equals(Account.class.getName()) ||
       !UtilSystem.getStackTraceElement(1).getMethodName().equals("useCredit"))
    {
      throw new ProtectionException("In protected against unauthorized access");
    }
    return this.useInternal(nb);
  }
  /**
   * Cette m�thode permet d'utiliser un certain nombre de cr�dits du lot. Si le
   * lot est vide une fois les cr�dits utilis�s, il sera retir� du compte utilisateur
   * @param nb Nombre de cr�dits � utiliser
   * @return Le lot courant des cr�dits utilis�s
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si le nombre de cr�dits � utiliser est inf�rieur � un
   * ou s'il n'y a pas assez de cr�dits dans le lot
   */
  protected CreditBundle useInternal(int nb) throws ProtectionException, UserException
  {
    // V�rification du nombre de cr�dits � utiliser
    UtilNumber.checkMinValue("nb", nb, 1, true,
                             AccountRef.CREDIT_NB_INVALID_ERROR);
    try
    {
      // Diminution du nombre de cr�dits du lot
      this.defineCurrentNb(this.getCurrentNb() - nb);
    }
    catch(UserException ex)
    {
      throw new UserException(AccountRef.CREDIT_NB_INSUFFICIENT_ERROR, ex);
    }
    // Si le lot est compl�tement utilis�, on le retire du compte utilisateur
    if(this.getCurrentNb() == 0)
    {
      this.unlinkFromAccount();
    }
    return this;
  }

  /**
   * Permet de savoir si le lot de cr�dits courant est li� � un compte utilisateur
   * @return True si le lot de cr�dits courant est li� � un compte utilisateur,
   * false sinon
   */
  public boolean isLinkedToAccount()
  {
    return this.getAccountLink() != null;
  }
  /**
   * Cette m�thode permet de lier le lot de cr�dits courant � son compte utilisateur
   * si le nombre de cr�dits restant est sup�rieur � z�ro. Dans le cas contraire,
   * rien n'est fait
   * @throws ProtectionException Si le lot de cr�dits courant ou son compte utilisateur
   * est prot�g�
   * @throws UserException Si le compte utilisateur du lot de cr�dits est nul ou
   * le r�f�rence d�j� ou si ce dernier est d�j� li� ou vide
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
   * Cette m�thode permet de d�lier le lot de cr�dits courant de son compte utilisateur
   * s'il ne reste plus de cr�dits
   * @return {@inheritDoc}
   * @throws ProtectionException Si le lot de cr�dits courant ou son compte utilisateur
   * est prot�g�
   * @throws UserException {@inheritDoc} ou si le lot de cr�dits courant n'est pas
   * compl�tement utilis� ou n'est pas r�f�renc� par son compte utilisateur
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
   * Getter du nombre initial de cr�dits du lot
   * @return Le nombre initial de cr�dits du lot
   */
  public int getInitialNb()
  {
    return this.getNb();
  }
  /**
   * Getter du nombre de cr�dits utilis�s sur le lot
   * @return Le nombre de cr�dits utilis�s sur le lot
   */
  public int getUsedNb()
  {
    return this.getInitialNb() - this.getCurrentNb();
  }
  /**
   * Cette m�thode permet de d�finir le nombre courant de cr�dits du lot
   * @param currentNb D�finition du nombre courant de cr�dits du lot
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si on d�fini un nombre de cr�dits inf�rieur � z�ro
   */
  protected void defineCurrentNb(int currentNb) throws ProtectionException, UserException
  {
    // V�rifie la protection du lot de cr�dits courant
    this.checkProtection();
    this.setCurrentNb(UtilNumber.checkMinValue("currentNb", currentNb, 0, true,
                                               AccountRef.CREDIT_NB_INVALID_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir la valeur totale des cr�dits du lot
   * @param totalValue {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc} ou si le lot de cr�dits est historis�
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
   * Getter de l'�tat d'historiation du lot de cr�dits
   * @return L'�tat d'historiation du lot de cr�dits
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
   * Cette m�thode permet d'historiser le lot de cr�dits courant
   * @return L'historisation du lot de cr�dits courant
   * @throws ProtectionException Si le lot de cr�dit courant est prot�g�
   * @throws UserException Si le lot de cr�dits courant est d�j� historis�
   */
  public CreditBundleHistory historize() throws ProtectionException, UserException
  {
    return this.defineHistory(new CreditBundleHistory(this));
  }
  /**
   * Cette m�thode permet de positionner l'identifiant de l'historisation du lot
   * de cr�dits courant
   * @return Le lot de cr�dit courant
   * @throws UserException Si le lot de cr�dit courant n'est pas historis� ou si
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
    // V�rifie la protection du lot de cr�dits courant
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
   * Getter du lien vers le compte utilisateur du lot de cr�dits
   * @return Le lien vers le compte utilisateur du lot de cr�dits
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
   * Setter du lien vers le compte utilisateur du lot de cr�dits
   * @param account Compte utilisateur avec lequel lier le lot de cr�dits
   */
  private void setAccountLink(Account account)
  {
    this.accountLink = account;
  }

  /**
   * Getter du nombre courant de cr�dits du lot
   * @return Le nombre courant de cr�dits du lot
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CURRENT_NB", length = 3, nullable = false, unique = false)
  public int getCurrentNb()
  {
    return this.currentNb;
  }
  /**
   * Setter du nombre courant de cr�dits du lot
   * @param currentNb Nombre courant de cr�dits du lot � positionner
   */
  private void setCurrentNb(int currentNb)
  {
    this.currentNb = currentNb;
  }

  /**
   * Getter de l'identifiant d'historisation du lot de cr�dits
   * @return L'identifiant d'historisation du lot de cr�dits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "HISTORY_ID", length = 10, nullable = true, unique = true,
          // Utilise la m�me colonne que le lien r�el vers la table des historisations
          insertable = false, updatable = false)
  public Long getHistoryId()
  {
    return this.historyId;
  }
  /**
   * Setter de l'identifiant d'historisation du lot de cr�dits
   * @param historyId Identifiant d'historisation du lot de cr�dits � positionner
   */
  private void setHistoryId(Long historyId)
  {
    this.historyId = historyId;
  }

  /**
   * Getter de l'historisation du lot de cr�dits
   * @return l'historisation du lot de cr�dits
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
   * Setter de l'historisation du lot de cr�dits
   * @param history Historisation du lot de cr�dits � positionner
   */
  private void setHistory(CreditBundleHistory history)
  {
    this.history = history;
  }
}
