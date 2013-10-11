package com.bid4win.persistence.entity.account.credit;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Cette classe défini l'utilisation d'un certain nombre de crédits d'un lot en
 * particulier. Celle-ci fait partie d'une implication globale de crédits dans une
 * action donnée<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <BUNDLE> Définition du lot des crédits utilisés<BR>
 * @param <INVOLVEMENT> Définition de l'implication des crédits utilisés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditUsageAbstract<CLASS extends CreditUsageAbstract<CLASS, BUNDLE, INVOLVEMENT>,
                                          BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                          INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, CLASS, BUNDLE, ?>>
       extends Bid4WinEntityAutoID<CLASS>
{
  /** Identifiant du lot de provenance des crédits utilisés */
  @Transient
  private Long bundleId = null;
  /** Lot de provenance des crédits utilisés */
  @Transient
  private BUNDLE bundle = null;
  /** Implication des crédits utilisés */
  @Transient
  private INVOLVEMENT involvement = null;
  /** Nombre de crédits utilisés */
  @Transient
  private int usedNb = 0;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditUsageAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bundle Lot de provenance des crédits utilisés
   * @param involvement Implication des crédits utilisés
   * @param usedNb Nombre de crédits utilisés
   * @throws ProtectionException Si l'implication globale en argument est protégée
   * @throws UserException Si l'implication globale ou la provenance des crédits
   * est nulle ou si leur nombre est inférieur à un
   */
  protected CreditUsageAbstract(BUNDLE bundle, INVOLVEMENT involvement, int usedNb)
            throws ProtectionException, UserException
  {
    super();
    // Défini le lot de provenance des crédits utilisés
    this.linkToBundle(bundle);
    // Défini l'implication des crédits utilisés
    this.linkToInvolvement(involvement);
    try
    {
      // Défini le nombre de crédits utilisés
      this.addUsedNb(usedNb);
    }
    catch(UserException ex)
    {
      this.unlinkFromInvolvement();
      throw ex;
    }
  }

  /**
   * Redéfini l'équivalence interne de deux utilisations de crédits d'un lot sans
   * prise en compte de leurs relations afin d'y ajouter le test de leurs données
   * propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           this.getUsedNb() == toBeCompared.getUsedNb();
  }
  /**
   * Permet d'effectuer le rendu simple de l'utilisation de crédits d'un lot courante
   * sans prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    buffer.append(" USED_NB=" + this.getUsedNb());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute à la liste des noeuds de relations de l'utilisation de crédits d'un
   * lot le lien vers son implication si tel est le cas
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(CreditUsageAbstract_Relations.NODE_BUNDLE);
    nodeList.add(CreditUsageAbstract_Relations.NODE_INVOLVEMENT);
    return nodeList;
  }
  /**
   * Permet de récupérer l'implication de l'utilisation de crédits d'un lot si
   * elle correspond à la relation en argument. Elle doit être redéfinie pour
   * toute nouvelle relation de type simple à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(CreditUsageAbstract_Relations.RELATION_BUNDLE))
    {
      return this.getBundle();
    }
    if(relation.equals(CreditUsageAbstract_Relations.RELATION_INVOLVEMENT))
    {
      return this.getInvolvement();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner l'implication de l'utilisation de crédits d'un lot si
   * elle correspondant à la relation en argument. Elle doit être redéfinie pour
   * toute nouvelle relation de type simple à positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(CreditUsageAbstract_Relations.RELATION_BUNDLE))
    {
      this.setBundle((BUNDLE)entity);
    }
    else if(relation.equals(CreditUsageAbstract_Relations.RELATION_INVOLVEMENT))
    {
      this.setInvolvement((INVOLVEMENT)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Cette méthode permet de lier l'utilisation de crédits courante au lot en argument
   * @param bundle Lot auquel lier l'utilisation de crédits courante
   * @throws ProtectionException Si l'utilisation de crédits courante est protégée
   * @throws UserException Si le lot en argument est nul ou si l'utilisation de
   * crédits courante est déjà liée à un lot
   */
  private void linkToBundle(BUNDLE bundle) throws ProtectionException, UserException
  {
    this.linkTo(CreditUsageAbstract_Relations.RELATION_BUNDLE, bundle);
    this.setBundleId(bundle.getId());
  }
  /**
   * Cette méthode permet de lier l'utilisation de crédits courante à l'implication
   * en argument
   * @param involvement Implication à laquelle lier l'utilisation de crédits courante
   * @throws ProtectionException Si l'utilisation de crédits courante ou l'implication
   * en argument est protégée
   * @throws UserException Si l'implication en argument est nulle ou si l'utilisation
   * de crédits courante est déjà liée à une implication
   */
  private void linkToInvolvement(INVOLVEMENT involvement) throws ProtectionException, UserException
  {
    this.linkTo(CreditUsageAbstract_Relations.RELATION_INVOLVEMENT,
                CreditInvolvement_Relations.RELATION_USAGE_MAP,
                involvement);
  }
  /**
   * Cette méthode permet de délier l'utilisation de crédits courante de son implication
   * @throws ProtectionException Si l'utilisation de crédits courante ou son implication
   * est protégée
   * @throws UserException Si l'utilisation de crédits courante n'est pas liée à
   * une implication ou si celle-ci ne la référence pas
   */
  private void unlinkFromInvolvement() throws ProtectionException, UserException
  {
    this.unlinkFrom(CreditUsageAbstract_Relations.RELATION_INVOLVEMENT,
                    CreditInvolvement_Relations.RELATION_USAGE_MAP);
  }
  /**
   * Cette méthode permet d'augmenter le nombre de crédits utilisés
   * @param usedNb Nombre de crédits utilisés en plus
   * @throws ProtectionException Si l'utilisation de crédits courante ou son implication
   * est protégée
   * @throws UserException Si on ajoute un nombre de crédits inférieur à un
   */
  protected void addUsedNb(int usedNb) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    // Augmente le nombre total de crédits utilisés
    this.getInvolvement().addUsedNb(usedNb);
    this.setUsedNb(this.getUsedNb() + usedNb);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant du lot de provenance des crédits utilisés
   * @return L'identifiant du lot de provenance des crédits utilisés
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "BUNDLE_ID", length = 10, nullable = false, unique = false,
          // Utilise la même colonne que le lien réel vers la table des lots de crédits
          insertable = false, updatable = false)
  public Long getBundleId()
  {
    return this.bundleId;
  }
  /**
   * Setter de l'identifiant du lot de provenance des crédits utilisés
   * @param bundleId Identifiant du lot de provenance des crédits utilisés à positionner
   */
  private void setBundleId(Long bundleId)
  {
    this.bundleId = bundleId;
  }

  /**
   * Getter du lot de provenance des crédits utilisés
   * @return Le lot de provenance des crédits utilisés
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "BUNDLE_ID", nullable = false, unique = false)
  public BUNDLE getBundle()
  {
    return this.bundle;
  }
  /**
   * Setter du lot de provenance des crédits utilisés
   * @param bundle Lot de provenance des crédits utilisés à positionner
   */
  private void setBundle(BUNDLE bundle)
  {
    this.bundle = bundle;
  }

  /**
   * Getter de l'implication des crédits utilisés
   * @return L'implication des crédits utilisés
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "INVOLVEMENT_ID", nullable = false, unique = false)
  public INVOLVEMENT getInvolvement()
  {
    return this.involvement;
  }
  /**
   * Setter de l'implication des crédits utilisés
   * @param involvement Implication des crédits utilisés à positionner
   */
  private void setInvolvement(INVOLVEMENT involvement)
  {
    this.involvement = involvement;
  }

  /**
   * Getter du nombre de crédits utilisés
   * @return Le nombre de crédits utilisés
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "USED_NB", length = 3, nullable = false, unique = false)
  public int getUsedNb()
  {
    return this.usedNb;
  }
  /**
   * Setter du nombre de crédits utilisés
   * @param usedNb Nombre de crédits utilisés à positionner
   */
  private void setUsedNb(int usedNb)
  {
    this.usedNb = usedNb;
  }
}
