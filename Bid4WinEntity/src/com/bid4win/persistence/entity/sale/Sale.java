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
 * @author Emeric Fillâtre
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
  /** Étape courante de la vente */
  @Transient
  private
  SaleStep step = null;

  /**
   * Constructeur pour création par introspection
   */
  protected Sale()
  {
    super();
  }
  /**
   * Constructeur d'une vente sans compte utilisateur bénéficiaire
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
   * @param account Compte utilisateur bénéficiaire de la vente
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
   * Redéfini l'équivalence interne de deux ventes sans prise en compte de leurs
   * relations afin d'y ajouter le test de leurs données propres
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
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments de la vente
    buffer.append(this.getStep().render());
    buffer.append(" PRODUCT_PRICE=[").append(this.getProductPrice().render()).append("]");
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute à la liste des noeuds de relations de la vente le lien vers son produit
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
   * Permet de récupérer le produit de la vente s'il correspond à la relation en
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
   * Permet de positionner le produit de la vente s'il correspond à la relation
   * en argument. Elle doit être redéfinie pour toute nouvelle relation de type
   * simple à positionner
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
   * Cette méthode permet de définir le produit vendu
   * @param product Définition du produit vendu
   * @throws ProtectionException Si la vente courante est protégée
   * @throws UserException Si on défini un produit nul ou déjà défini
   */
  protected void linkToProduct(Product product) throws ProtectionException, UserException
  {
    this.linkTo(Sale_Relations.RELATION_PRODUCT, product);
  }
  /**
   * Cette méthode permet de valider le prix du produit vendu au moment de l'appel
   * à cette méthode au cas où sa définition dans le produit aurait changé
   * @throws ProtectionException Si la vente courante est protégée
   * @throws UserException Si la vente courante n'est pas en construction
   */
  protected void validateProductPrice() throws ProtectionException, UserException
  {
    // Vérifie la protection de la vente courante
    this.checkProtection();
    // La vente doit être en construction
    UtilBoolean.checkTrue("processing", this.isProcessing(),
                          SaleRef.SALE_STEP_INVALID_ERROR);
    // Positionne le nouveau prix
    this.setProductPrice(this.getProduct().getPrice().copy());
  }

  /**
   * Getter de l'étape courante de la vente courante
   * @return L'étape courante de la vente courante
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
   * Cette méthode permet de savoir si la vente courante est en construction
   * @return True si la vente courante est en construction, false sinon
   */
  public boolean isProcessing()
  {
    return this.getStep().belongsTo(Step.PROCESSING);
  }
  /**
   * Cette méthode permet de savoir si la vente courante est payée
   * @return True si la vente courante est payée, false sinon
   */
  public boolean isPaid()
  {
    return this.getStep().belongsTo(Step.OK);
  }
  /**
   * Cette méthode permet de savoir si la vente courante est expédiée
   * @return True si la vente courante est expédiée, false sinon
   */
  public boolean isShipped()
  {
    return this.getStep().belongsTo(Step.SHIPPED) || this.getStep().belongsTo(Step.DONE);
  }
  /**
   * Cette méthode permet de savoir si la vente courante est terminée
   * @return True si la vente courante est terminée, false sinon
   */
  public boolean isDone()
  {
    return this.getStep().belongsTo(Step.DONE);
  }
  /**
   * Cette méthode prend en charge la modification de l'étape courante de la vente
   * en le validant par rapport à l'étape précédente
   * @param step Nouvelle étape de la vente
   * @return La vente modifiée
   * @throws ProtectionException Si la vente courante est protégée
   * @throws UserException Si la nouvelle étape de la vente est invalide
   */
  @SuppressWarnings("unchecked")
  protected CLASS defineStep(Step step) throws ProtectionException, UserException
  {
    // Vérifie la protection de la vente aux enchères courante
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
   * @param productPrice Prix du produit vendu à positionner
   */
  private void setProductPrice(Price productPrice)
  {
    this.productPrice = productPrice;
  }

  /**
   * Getter de l'étape courante de la vente
   * @return L'étape courante de la vente
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  private SaleStep getSaleStep()
  {
    return this.step;
  }
  /**
   * Setter de l'étape courante de la vente
   * @param step Étape courante de la vente à positionner
   */
  private void setSaleStep(SaleStep step)
  {
    this.step = step;
  }
}
