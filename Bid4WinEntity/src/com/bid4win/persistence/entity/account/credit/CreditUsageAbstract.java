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
 * Cette classe d�fini l'utilisation d'un certain nombre de cr�dits d'un lot en
 * particulier. Celle-ci fait partie d'une implication globale de cr�dits dans une
 * action donn�e<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <BUNDLE> D�finition du lot des cr�dits utilis�s<BR>
 * @param <INVOLVEMENT> D�finition de l'implication des cr�dits utilis�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class CreditUsageAbstract<CLASS extends CreditUsageAbstract<CLASS, BUNDLE, INVOLVEMENT>,
                                          BUNDLE extends CreditBundleAbstract<BUNDLE>,
                                          INVOLVEMENT extends CreditInvolvement<INVOLVEMENT, CLASS, BUNDLE, ?>>
       extends Bid4WinEntityAutoID<CLASS>
{
  /** Identifiant du lot de provenance des cr�dits utilis�s */
  @Transient
  private Long bundleId = null;
  /** Lot de provenance des cr�dits utilis�s */
  @Transient
  private BUNDLE bundle = null;
  /** Implication des cr�dits utilis�s */
  @Transient
  private INVOLVEMENT involvement = null;
  /** Nombre de cr�dits utilis�s */
  @Transient
  private int usedNb = 0;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditUsageAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param bundle Lot de provenance des cr�dits utilis�s
   * @param involvement Implication des cr�dits utilis�s
   * @param usedNb Nombre de cr�dits utilis�s
   * @throws ProtectionException Si l'implication globale en argument est prot�g�e
   * @throws UserException Si l'implication globale ou la provenance des cr�dits
   * est nulle ou si leur nombre est inf�rieur � un
   */
  protected CreditUsageAbstract(BUNDLE bundle, INVOLVEMENT involvement, int usedNb)
            throws ProtectionException, UserException
  {
    super();
    // D�fini le lot de provenance des cr�dits utilis�s
    this.linkToBundle(bundle);
    // D�fini l'implication des cr�dits utilis�s
    this.linkToInvolvement(involvement);
    try
    {
      // D�fini le nombre de cr�dits utilis�s
      this.addUsedNb(usedNb);
    }
    catch(UserException ex)
    {
      this.unlinkFromInvolvement();
      throw ex;
    }
  }

  /**
   * Red�fini l'�quivalence interne de deux utilisations de cr�dits d'un lot sans
   * prise en compte de leurs relations afin d'y ajouter le test de leurs donn�es
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
   * Permet d'effectuer le rendu simple de l'utilisation de cr�dits d'un lot courante
   * sans prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    buffer.append(" USED_NB=" + this.getUsedNb());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute � la liste des noeuds de relations de l'utilisation de cr�dits d'un
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
   * Permet de r�cup�rer l'implication de l'utilisation de cr�dits d'un lot si
   * elle correspond � la relation en argument. Elle doit �tre red�finie pour
   * toute nouvelle relation de type simple � remonter
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
   * Permet de positionner l'implication de l'utilisation de cr�dits d'un lot si
   * elle correspondant � la relation en argument. Elle doit �tre red�finie pour
   * toute nouvelle relation de type simple � positionner
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
   * Cette m�thode permet de lier l'utilisation de cr�dits courante au lot en argument
   * @param bundle Lot auquel lier l'utilisation de cr�dits courante
   * @throws ProtectionException Si l'utilisation de cr�dits courante est prot�g�e
   * @throws UserException Si le lot en argument est nul ou si l'utilisation de
   * cr�dits courante est d�j� li�e � un lot
   */
  private void linkToBundle(BUNDLE bundle) throws ProtectionException, UserException
  {
    this.linkTo(CreditUsageAbstract_Relations.RELATION_BUNDLE, bundle);
    this.setBundleId(bundle.getId());
  }
  /**
   * Cette m�thode permet de lier l'utilisation de cr�dits courante � l'implication
   * en argument
   * @param involvement Implication � laquelle lier l'utilisation de cr�dits courante
   * @throws ProtectionException Si l'utilisation de cr�dits courante ou l'implication
   * en argument est prot�g�e
   * @throws UserException Si l'implication en argument est nulle ou si l'utilisation
   * de cr�dits courante est d�j� li�e � une implication
   */
  private void linkToInvolvement(INVOLVEMENT involvement) throws ProtectionException, UserException
  {
    this.linkTo(CreditUsageAbstract_Relations.RELATION_INVOLVEMENT,
                CreditInvolvement_Relations.RELATION_USAGE_MAP,
                involvement);
  }
  /**
   * Cette m�thode permet de d�lier l'utilisation de cr�dits courante de son implication
   * @throws ProtectionException Si l'utilisation de cr�dits courante ou son implication
   * est prot�g�e
   * @throws UserException Si l'utilisation de cr�dits courante n'est pas li�e �
   * une implication ou si celle-ci ne la r�f�rence pas
   */
  private void unlinkFromInvolvement() throws ProtectionException, UserException
  {
    this.unlinkFrom(CreditUsageAbstract_Relations.RELATION_INVOLVEMENT,
                    CreditInvolvement_Relations.RELATION_USAGE_MAP);
  }
  /**
   * Cette m�thode permet d'augmenter le nombre de cr�dits utilis�s
   * @param usedNb Nombre de cr�dits utilis�s en plus
   * @throws ProtectionException Si l'utilisation de cr�dits courante ou son implication
   * est prot�g�e
   * @throws UserException Si on ajoute un nombre de cr�dits inf�rieur � un
   */
  protected void addUsedNb(int usedNb) throws ProtectionException, UserException
  {
    // V�rifie la protection du lot de cr�dits courant
    this.checkProtection();
    // Augmente le nombre total de cr�dits utilis�s
    this.getInvolvement().addUsedNb(usedNb);
    this.setUsedNb(this.getUsedNb() + usedNb);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant du lot de provenance des cr�dits utilis�s
   * @return L'identifiant du lot de provenance des cr�dits utilis�s
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "BUNDLE_ID", length = 10, nullable = false, unique = false,
          // Utilise la m�me colonne que le lien r�el vers la table des lots de cr�dits
          insertable = false, updatable = false)
  public Long getBundleId()
  {
    return this.bundleId;
  }
  /**
   * Setter de l'identifiant du lot de provenance des cr�dits utilis�s
   * @param bundleId Identifiant du lot de provenance des cr�dits utilis�s � positionner
   */
  private void setBundleId(Long bundleId)
  {
    this.bundleId = bundleId;
  }

  /**
   * Getter du lot de provenance des cr�dits utilis�s
   * @return Le lot de provenance des cr�dits utilis�s
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
   * Setter du lot de provenance des cr�dits utilis�s
   * @param bundle Lot de provenance des cr�dits utilis�s � positionner
   */
  private void setBundle(BUNDLE bundle)
  {
    this.bundle = bundle;
  }

  /**
   * Getter de l'implication des cr�dits utilis�s
   * @return L'implication des cr�dits utilis�s
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
   * Setter de l'implication des cr�dits utilis�s
   * @param involvement Implication des cr�dits utilis�s � positionner
   */
  private void setInvolvement(INVOLVEMENT involvement)
  {
    this.involvement = involvement;
  }

  /**
   * Getter du nombre de cr�dits utilis�s
   * @return Le nombre de cr�dits utilis�s
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "USED_NB", length = 3, nullable = false, unique = false)
  public int getUsedNb()
  {
    return this.usedNb;
  }
  /**
   * Setter du nombre de cr�dits utilis�s
   * @param usedNb Nombre de cr�dits utilis�s � positionner
   */
  private void setUsedNb(int usedNb)
  {
    this.usedNb = usedNb;
  }
}
