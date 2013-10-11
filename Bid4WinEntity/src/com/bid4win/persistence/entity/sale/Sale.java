package com.bid4win.persistence.entity.sale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.SaleRef;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleGeneratedID;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class Sale<CLASS extends Sale<CLASS>>
       extends AccountBasedEntityMultipleGeneratedID<CLASS, Account>
{
  /** Produit vendu */
  @Transient
  private Product product = null;
  /** Prix du produit vendu */
  @Transient
  private Price productPrice = null;
  /** �tape courante de la vente */
  @Transient
  private
  SaleStep step = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Sale()
  {
    super();
  }
  /**
   * Constructeur d'une vente sans compte utilisateur b�n�ficiaire
   * @param product Produit vendu
   * @throws UserException Si le produit en argument est nul
   */
  protected Sale(Product product) throws UserException
  {
    super((IdPattern)null);
    this.defineStep(Step.PROCESSING);
    this.linkToProduct(product);
    this.validateProductPrice();
  }
  /**
   * Constructeur complet de la vente
   * @param account Compte utilisateur b�n�ficiaire de la vente
   * @param product Produit vendu
   * @throws UserException Si le compte utilisateur ou le produit en argument est
   * nul
   */
  protected Sale(Account account, Product product)
            throws UserException
  {
    super(account);
    this.defineStep(Step.PROCESSING);
    this.linkToProduct(product);
    this.validateProductPrice();
  }

  /**
   * Red�fini l'�quivalence interne de deux ventes sans prise en compte de leurs
   * relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           this.getProductPrice().equals(toBeCompared.getProductPrice()) &&
           this.getStep().equals(toBeCompared.getStep());
  }
  /**
   * Permet d'effectuer le rendu simple de la vente courante sans prise en compte
   * de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de la vente
    buffer.append(this.getStep().render());
    buffer.append(" PRODUCT_PRICE=[").append(this.getProductPrice().render()).append("]");
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute � la liste des noeuds de relations de la vente le lien vers son produit
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(Sale_Relations.NODE_PRODUCT);
    return nodeList;
  }
  /**
   * Permet de r�cup�rer le produit de la vente s'il correspond � la relation en
   * argument.
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(Sale_Relations.RELATION_PRODUCT))
    {
      return this.getProduct();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le produit de la vente s'il correspond � la relation
   * en argument. Elle doit �tre red�finie pour toute nouvelle relation de type
   * simple � positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(Sale_Relations.RELATION_PRODUCT))
    {
      this.setProduct((Product)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Cette m�thode permet de d�finir le produit vendu
   * @param product D�finition du produit vendu
   * @throws ProtectionException Si la vente courante est prot�g�e
   * @throws UserException Si on d�fini un produit nul ou d�j� d�fini
   */
  protected void linkToProduct(Product product) throws ProtectionException, UserException
  {
    this.linkTo(Sale_Relations.RELATION_PRODUCT, product);
  }
  /**
   * Cette m�thode permet de valider le prix du produit vendu au moment de l'appel
   * � cette m�thode au cas o� sa d�finition dans le produit aurait chang�
   * @throws ProtectionException Si la vente courante est prot�g�e
   * @throws UserException Si la vente courante n'est pas en construction
   */
  protected void validateProductPrice() throws ProtectionException, UserException
  {
    // V�rifie la protection de la vente courante
    this.checkProtection();
    // La vente doit �tre en construction
    UtilBoolean.checkTrue("processing", this.isProcessing(),
                          SaleRef.SALE_STEP_INVALID_ERROR);
    // Positionne le nouveau prix
    this.setProductPrice(this.getProduct().getPrice().copy());
  }

  /**
   * Getter de l'�tape courante de la vente courante
   * @return L'�tape courante de la vente courante
   */
  public Step getStep()
  {
    if(this.getSaleStep() == null)
    {
      return Step.PROCESSING;
    }
    return this.getSaleStep().getType();
  }
  /**
   * Cette m�thode permet de savoir si la vente courante est en construction
   * @return True si la vente courante est en construction, false sinon
   */
  public boolean isProcessing()
  {
    return this.getStep().belongsTo(Step.PROCESSING);
  }
  /**
   * Cette m�thode permet de savoir si la vente courante est pay�e
   * @return True si la vente courante est pay�e, false sinon
   */
  public boolean isPaid()
  {
    return this.getStep().belongsTo(Step.OK);
  }
  /**
   * Cette m�thode permet de savoir si la vente courante est exp�di�e
   * @return True si la vente courante est exp�di�e, false sinon
   */
  public boolean isShipped()
  {
    return this.getStep().belongsTo(Step.SHIPPED) || this.getStep().belongsTo(Step.DONE);
  }
  /**
   * Cette m�thode permet de savoir si la vente courante est termin�e
   * @return True si la vente courante est termin�e, false sinon
   */
  public boolean isDone()
  {
    return this.getStep().belongsTo(Step.DONE);
  }
  /**
   * Cette m�thode prend en charge la modification de l'�tape courante de la vente
   * en le validant par rapport � l'�tape pr�c�dente
   * @param step Nouvelle �tape de la vente
   * @return La vente modifi�e
   * @throws ProtectionException Si la vente courante est prot�g�e
   * @throws UserException Si la nouvelle �tape de la vente est invalide
   */
  @SuppressWarnings("unchecked")
  protected CLASS defineStep(Step step) throws ProtectionException, UserException
  {
    // V�rifie la protection de la vente aux ench�res courante
    this.checkProtection();
    UtilObject.checkNotNull("step", step, SaleRef.SALE_STEP_MISSING_ERROR);
    if(step.equals(Step.PROCESSING) || step.equals(Step.PAID) || step.belongsTo(Step.KO))
    {
      UtilObject.checkEquals("step", this.getStep(), Step.PROCESSING,
                            SaleRef.SALE_STEP_INVALID_ERROR);
    }
    else if(step.equals(Step.SHIPPED))
    {
      UtilObject.checkEquals("step", this.getStep(), Step.PAID,
                             SaleRef.SALE_STEP_INVALID_ERROR);
    }
    else
    {
      UtilObject.checkEquals("step", this.getStep(), Step.SHIPPED,
                             SaleRef.SALE_STEP_INVALID_ERROR);
    }
    this.setSaleStep(new SaleStep(step));
    return (CLASS)this;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du produit vendu
   * @return Le produit vendu
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "PRODUCT_ID", nullable = false, unique = false)
  public Product getProduct()
  {
    return this.product;
  }
  /**
   * Setter du produit vendu
   * @param product Produit vendu
   */
  private void setProduct(Product product)
  {
    this.product = product;
  }

  /**
   * Getter du prix du produit vendu
   * @return Le prix du produit vendu
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  @AttributeOverride(name = "embeddedMap", column = @Column(name = "PRODUCT_PRICE"))
  public Price getProductPrice()
  {
    return this.productPrice;
  }
  /**
   * Setter du prix du produit vendu
   * @param productPrice Prix du produit vendu � positionner
   */
  private void setProductPrice(Price productPrice)
  {
    this.productPrice = productPrice;
  }

  /**
   * Getter de l'�tape courante de la vente
   * @return L'�tape courante de la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  private SaleStep getSaleStep()
  {
    return this.step;
  }
  /**
   * Setter de l'�tape courante de la vente
   * @param step �tape courante de la vente � positionner
   */
  private void setSaleStep(SaleStep step)
  {
    this.step = step;
  }
}
