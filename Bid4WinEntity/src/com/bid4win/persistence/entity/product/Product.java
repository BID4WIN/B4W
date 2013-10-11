package com.bid4win.persistence.entity.product;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.reference.MessageRef.ProductRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.persistence.entity.image.ImageUsage;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;
import com.bid4win.persistence.entity.price.Price;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Product extends Bid4WinEntityGeneratedID<Product>
{
  /** R�f�rence du produit */
  @Transient
  private String reference = null;
  /** Nom du produit dans diff�rentes langues dont celle d�finie par d�faut */
  @Transient
  private I18nGroup names = null;
  /** R�sum� de la description du produit dans diff�rentes langues dont celle d�finie par d�faut */
  @Transient
  private I18nGroup summaries = null;
  /** Prix du produit dans diff�rentes monnaies dont celle d�finie par d�faut */
  @Transient
  private Price price = null;
  /** Liste des images utilis�es par le produit */
  @Transient
  private Bid4WinList<ImageUsage> imageUsageList = new Bid4WinList<ImageUsage>();
  /** Liste des descriptions utilis�es par le produit */
  @Transient
  private Bid4WinList<InnerContentUsage> innerContentUsageList = new Bid4WinList<InnerContentUsage>();

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Product()
  {
    super();
  }
  /**
   * Constructeur bas� sur le g�n�rateur d'identifiants par d�faut
   * @param reference R�f�rence du produit
   * @param names Nom du produit dans diff�rentes langues dont celle d�finie par
   * d�faut
   * @param summaries R�sum� de la description du produit dans diff�rentes langues
   * dont celle d�finie par d�faut
   * @param price Prix du produit dans diff�rentes monnaies dont celle d�finie par
   * d�faut
   * @throws UserException Si la d�finition du nom ou du prix du produit est nulle
   */
  public Product(String reference, I18nGroup names, I18nGroup summaries, Price price)
         throws UserException
  {
    super((IdPattern)null);
    this.defineReference(reference);
    this.defineNames(names);
    this.defineSummaries(summaries);
    this.definePrice(price);
  }

  /**
   * Ajoute � la liste des noeuds de relations du produit le lien vers ses listes
   * d'utilisations d'image et de page HTML
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(Product_Relations.NODE_IMAGE_USAGE_LIST);
    nodeList.add(Product_Relations.NODE_INNER_CONTENT_USAGE_LIST);
    return nodeList;
  }
  /**
   * Permet de r�cup�rer la liste d'utilisations d'image ou de page HTML du produit
   * si elles correspondent � la relation en argument. Elle doit �tre red�finie
   * pour toute nouvelle relation de type liste � remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationList(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected List<? extends Bid4WinEntity<?, ?>> getRelationList(Bid4WinRelation relation)
  {
    if(relation.equals(Product_Relations.RELATION_IMAGE_USAGE_LIST))
    {
      return this.getImageUsageListInternal();
    }
    else if(relation.equals(Product_Relations.RELATION_INNER_CONTENT_USAGE_LIST))
    {
      return this.getInnerContentUsageListInternal();
    }
    return super.getRelationList(relation);
  }

  /**
   * Red�fini l'�quivalence interne de deux produits sans prise en compte de leurs
   * relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(Product toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getReference(), toBeCompared.getReference()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getNames(), toBeCompared.getNames()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getSummaries(), toBeCompared.getSummaries()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getPrice(), toBeCompared.getPrice());
  }
  /**
   * Permet d'effectuer le rendu simple du produit courant sans prise en compte
   * de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments du produit
    buffer.append(" REFERENCE=").append(this.getReference());
    buffer.append(" NAMES=").append(this.getNames().render());
    buffer.append(" SUMMARIES=").append(this.getSummaries().render());
    buffer.append(" PRICES=").append(this.getPrice().render());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Getter du nom du produit dans la langue d�finie par d�faut
   * @return Le nom du produit dans la langue d�finie par d�faut
   */
  public String getName()
  {
    return this.getNames().getValue();
  }
  /**
   * Getter du nom du produit dans la langue en param�tre
   * @param language Langue pour laquelle r�cup�rer le nom du produit
   * @return Le nom du produit dans la langue en param�tre ou celle d�finie par
   * d�faut si la langue choisie n'est pas renseign�e
   */
  public String getName(Language language)
  {
    return this.getNames().getValue(language);
  }
  /**
   * Getter du r�sum� de la description du produit dans la langue d�finie par d�faut
   * @return Le r�sum� de la description du produit dans la langue d�finie par d�faut
   */
  public String getSummary()
  {
    return this.getSummaries().getValue();
  }
  /**
   * Getter du r�sum� de la description du produit dans la langue en param�tre
   * @param language Langue pour laquelle r�cup�rer le r�sum� de la description
   * du produit
   * @return Le r�sum� de la description du produit dans la langue en param�tre
   * ou celle d�finie par d�faut si la langue choisie n'est pas renseign�e
   */
  public String getSummary(Language language)
  {
    return this.getSummaries().getValue(language);
  }

  /**
   * Cette m�thode permet de d�finir la r�f�rence du produit
   * @param reference D�finition de la r�f�rence du produit
   * @throws ProtectionException Si le produit courant est prot�g�
   */
  public void defineReference(String reference) throws ProtectionException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setReference(UtilString.trimNotNull(reference));
  }
  /**
   * Cette m�thode permet de d�finir le nom du produit dans diff�rentes langues
   * @param names D�finition du nom du produit dans diff�rentes langues
   * @throws ProtectionException Si le produit courant est prot�g�
   * @throws UserException Si la d�finition du nom du produit est nulle
   */
  public void defineNames(I18nGroup names) throws ProtectionException, UserException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setNames(UtilObject.checkNotNull("names", names,
                                          ProductRef.PRODUCT_NAME_MISSING_ERROR));
    // Red�fini le nommage des images du produit
    for(ImageUsage usage : this.getImageUsageList())
    {
      usage.defineName(this.getName());
    }
    // Red�fini le nommage des descriptions du produit
    for(InnerContentUsage usage : this.getInnerContentUsageList())
    {
      usage.defineName(this.getName());
    }
  }
  /**
   * Cette m�thode permet de d�finir le r�sum� de la description du produit dans
   * diff�rentes langues
   * @param summaries D�finition du r�sum� de la description du produit dans diff�rentes
   * langues
   * @throws ProtectionException Si le produit courant est prot�g�
   * @throws UserException Si la d�finition du r�sum� de la description du produit
   * est nulle
   */
  public void defineSummaries(I18nGroup summaries) throws ProtectionException, UserException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setSummaries(UtilObject.checkNotNull("summaries", summaries,
                                               ProductRef.PRODUCT_SUMMARY_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir le prix du produit dans diff�rentes monnaies
   * @param price D�finition du prix du produit dans diff�rentes monnaies
   * @throws ProtectionException Si le produit courant est prot�g�
   * @throws UserException Si la d�finition du prix du produit est nulle
   */
  public void definePrice(Price price) throws ProtectionException, UserException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setPrice(UtilObject.checkNotNull("price", price,
                                           CurrencyRef.CURRENCY_PRICE_MISSING_ERROR));
  }

  /**
   * Cette m�thode permet de conna�tre le nombre d'images r�f�renc�es par le produit
   * @return Le nombre d'images r�f�renc�es par l'entit�
   */
  public int getImageNb()
  {
    return this.getImageUsageList().size();
  }
  /**
   * Cette m�thode permet de r�cup�rer l'image r�f�renc�e dont l'identifiant
   * correspond � celui en argument
   * @param id Identifiant de l'image � r�cup�rer
   * @return L'image r�f�renc�e dont l'identifiant correspond � celui en argument
   * @throws UserException Si aucune image r�f�renc�e ne correspond � l'identifiant
   * en argument
   */
  public ImageUsage getImage(long id) throws UserException
  {
    for(ImageUsage usage : this.getImageUsageList())
    {
      if(usage.getId().equals(id))
      {
        return usage;
      }
    }
    throw new UserException(ResourceRef.IMAGE_USAGE_UNKNOWN_ERROR);
  }
  /**
   * Getter de la liste d'images r�f�renc�es par le produit
   * @return La liste d'images r�f�renc�es par le produit
   */
  public Bid4WinList<ImageUsage> getImageList()
  {
    return this.getImageUsageList().clone();
  }
  /**
   * Cette m�thode permet de conna�tre le nombre de pages de descriptions r�f�renc�es
   * par le produit
   * @return Le nombre de pages de descriptions r�f�renc�es par l'entit�
   */
  public int getDescriptionNb()
  {
    return this.getInnerContentUsageList().size();
  }
  /**
   * Cette m�thode permet de r�cup�rer la page de description r�f�renc�e dont l'
   * identifiant correspond � celui en argument
   * @param id Identifiant de la page de description � r�cup�rer
   * @return La page de description r�f�renc�e dont l'identifiant correspond �
   * celui en argument
   * @throws UserException Si aucune page de description r�f�renc�e ne correspond
   * � l'identifiant en argument
   */
  public InnerContentUsage getDescription(long id) throws UserException
  {
    for(InnerContentUsage usage : this.getInnerContentUsageList())
    {
      if(usage.getId().equals(id))
      {
        return usage;
      }
    }
    throw new UserException(ResourceRef.RESOURCE_USAGE_UNKNOWN_ERROR);
  }
  /**
   * Getter de la liste de descriptions r�f�renc�es par le produit
   * @return La liste de descriptions r�f�renc�es par le produit
   */
  public Bid4WinList<InnerContentUsage> getDescriptionList()
  {
    return this.getInnerContentUsageList().clone();
  }

  /**
   * Cette m�thode permet de modifier la position d'une image r�f�renc�e
   * @param oldIndex Ancienne position
   * @param newIndex Nouvelle position
   * @throws ProtectionException Si l'entit� courante ou une des images dont la
   * position est modifi�e est prot�g�e
   * @throws UserException Si les index en argument ne sont pas valides
   */
  public void orderImage(int oldIndex, int newIndex) throws ProtectionException, UserException
  {
    // V�rifie la protection du produit courant
    this.checkProtection();
    UtilNumber.checkMinValue("oldIndex", oldIndex, 0, true,
                             ResourceRef.IMAGE_USAGE_UNKNOWN_ERROR);
    UtilNumber.checkMaxValue("oldIndex", oldIndex, this.getImageUsageList().size(),
                             false, ResourceRef.IMAGE_USAGE_UNKNOWN_ERROR);
    UtilNumber.checkMinValue("newIndex", oldIndex, 0, true,
                             ResourceRef.IMAGE_USAGE_UNKNOWN_ERROR);
    UtilNumber.checkMaxValue("newIndex", oldIndex, this.getImageUsageList().size(),
                             false, ResourceRef.IMAGE_USAGE_UNKNOWN_ERROR);
    ImageUsage usage = this.getImageUsageListInternal().remove(oldIndex);
    this.getImageUsageListInternal().add(newIndex, usage);
    this.reorderImageUsageList();
  }
  /**
   * Cette m�thode permet de d�finir la position des images r�f�renc�es en fonction
   * de leur ordre dans la liste
   * @throws ProtectionException Si l'une des images r�f�renc�es est prot�g�e
   */
  private void reorderImageUsageList() throws ProtectionException
  {
    int position = 0;
    for(ImageUsage usage : this.getImageUsageListInternal())
    {
      usage.definePosition(position++);
    }
  }
  /**
   * Getter de la liste des utilisations d'images du produit courant
   * @return La liste des utilisations d'images du produit courant
   */
  private Bid4WinList<ImageUsage> getImageUsageList()
  {
    return this.imageUsageList;
  }
  /**
   * Setter de la liste des utilisations d'images du produit courant
   * @param imageUsageList Liste des utilisations d'images � positionner
   */
  private void setImageUsageList(Bid4WinList<ImageUsage> imageUsageList)
  {
    this.imageUsageList = imageUsageList;
  }

  /**
   * Cette m�thode permet de modifier la position d'une description r�f�renc�e
   * @param oldIndex Ancienne position
   * @param newIndex Nouvelle position
   * @throws ProtectionException Si l'entit� courante ou une des descriptions dont
   * la position est modifi�e est prot�g�e
   * @throws UserException Si les index en argument ne sont pas valides
   */
  public void orderDescription(int oldIndex, int newIndex) throws ProtectionException, UserException
  {
    // V�rifie la protection du produit courant
    this.checkProtection();
    UtilNumber.checkMinValue("oldIndex", oldIndex, 0, true,
                             ResourceRef.RESOURCE_USAGE_UNKNOWN_ERROR);
    UtilNumber.checkMaxValue("oldIndex", oldIndex, this.getInnerContentUsageList().size(),
                             false, ResourceRef.RESOURCE_USAGE_UNKNOWN_ERROR);
    UtilNumber.checkMinValue("newIndex", oldIndex, 0, true,
                             ResourceRef.RESOURCE_USAGE_UNKNOWN_ERROR);
    UtilNumber.checkMaxValue("newIndex", oldIndex, this.getInnerContentUsageList().size(),
                             false, ResourceRef.RESOURCE_USAGE_UNKNOWN_ERROR);
    InnerContentUsage usage = this.getInnerContentUsageListInternal().remove(oldIndex);
    this.getInnerContentUsageListInternal().add(newIndex, usage);
    this.reorderInnerContentUsageList();
  }
  /**
   * Cette m�thode permet de d�finir la position des contenus r�f�renc�s en fonction
   * de leur ordre dans la liste
   * @throws ProtectionException Si l'un des contenus r�f�renc�s est prot�g�
   */
  private void reorderInnerContentUsageList() throws ProtectionException
  {
    int position = 0;
    for(InnerContentUsage usage : this.getInnerContentUsageListInternal())
    {
      usage.definePosition(position++);
    }
  }
  /**
   * Getter de la liste des utilisations de contenu du produit courant
   * @return La liste des utilisations de contenu du produit courant
   */
  private Bid4WinList<InnerContentUsage> getInnerContentUsageList()
  {
    return this.innerContentUsageList;
  }
  /**
   * Setter de la liste des utilisations de contenu du produit courant
   * @param innerContentUsageList Liste des utilisations de contenu � positionner
   */
  private void setInnerContentUsageList(Bid4WinList<InnerContentUsage> innerContentUsageList)
  {
    this.innerContentUsageList = innerContentUsageList;
  }

  /**
   *
   * TODO A COMMENTER
   * @param link {@inheritDoc}
   * @param backLink {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#addRelationCollection(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void addRelationCollection(Bid4WinRelation link,
                                       Bid4WinRelation backLink,
                                       Bid4WinEntity<?, ?> toBeAdded)
            throws ProtectionException, UserException
  {
    super.addRelationCollection(link, backLink, toBeAdded);
    if(link.equals(Product_Relations.RELATION_IMAGE_USAGE_LIST))
    {
      this.reorderImageUsageList();
    }
    else if(link.equals(Product_Relations.RELATION_INNER_CONTENT_USAGE_LIST))
    {
      this.reorderInnerContentUsageList();
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param link {@inheritDoc}
   * @param backLink {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#removeRelationCollection(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void removeRelationCollection(Bid4WinRelation link,
                                          Bid4WinRelation backLink,
                                          Bid4WinEntity<?, ?> toBeAdded)
            throws ProtectionException, UserException
  {
    super.removeRelationCollection(link, backLink, toBeAdded);
    if(link.equals(Product_Relations.RELATION_IMAGE_USAGE_LIST))
    {
      this.reorderImageUsageList();
    }
    else if(link.equals(Product_Relations.RELATION_INNER_CONTENT_USAGE_LIST))
    {
      this.reorderInnerContentUsageList();
    }
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du champs permettant le for�age de la modification du produit
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getUpdateForce()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "UPDATE_FORCE", length = 3, nullable = false, unique = false)
  public int getUpdateForce()
  {
    return super.getUpdateForce();
  }

  /**
   * Getter de la r�f�rence du produit
   * @return La r�f�rence du produit
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "REFERENCE", length = 30, nullable = true, unique = false)
  public String getReference()
  {
    return this.reference;
  }
  /**
   * Setter de la r�f�rence du produit
   * @param reference R�f�rence du produit � positionner
   */
  private void setReference(String reference)
  {
    this.reference = reference;
  }

  /**
   * Getter du nom du produit dans diff�rentes langues
   * @return Le nom du produit dans diff�rentes langues
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  @AttributeOverride(name = "embeddedMap", column = @Column(name = "NAMES"))
  public I18nGroup getNames()
  {
    return this.names;
  }
  /**
   * Setter du nom du produit dans diff�rentes langues
   * @param names Nom du produit dans diff�rentes langues � positionner
   */
  private void setNames(I18nGroup names)
  {
    this.names = names;
  }

  /**
   * Getter du r�sum� de la description du produit dans diff�rentes langues
   * @return Le r�sum� de la description du produit dans diff�rentes langues
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  @AttributeOverride(name = "embeddedMap", column = @Column(name = "SUMMARIES"))
  public I18nGroup getSummaries()
  {
    return this.summaries;
  }
  /**
   * Setter du r�sum� de la description du produit dans diff�rentes langues
   * @param summaries R�sum� de la description du produit dans diff�rentes langues
   * � positionner
   */
  private void setSummaries(I18nGroup summaries)
  {
    this.summaries = summaries;
  }

  /**
   * Getter du prix du produit dans diff�rentes monnaies
   * @return Le prix du produit dans diff�rentes monnaies
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  @AttributeOverride(name = "embeddedMap", column = @Column(name = "PRICES"))
  public Price getPrice()
  {
    return this.price;
  }
  /**
   * Setter du prix du produit dans diff�rentes monnaies
   * @param price Prix du produit dans diff�rentes monnaies � positionner
   */
  private void setPrice(Price price)
  {
    this.price = price;
  }

  /**
   * Getter de la liste interne des utilisations d'image du produit courant
   * @return La liste interne des utilisations d'image du produit courant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {})
  @OrderBy(value = "position")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private List<ImageUsage> getImageUsageListInternal()
  {
    return this.getImageUsageList().getInternal();
  }
  /**
   * Setter de la liste interne des utilisations d'image du produit courant
   * @param imageUsageList Liste interne des utilisations d'image � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setImageUsageListInternal(List<ImageUsage> imageUsageList)
  {
    this.setImageUsageList(new Bid4WinList<ImageUsage>(imageUsageList, true));
  }

  /**
   * Getter de la liste interne des utilisations de contenu du produit courant
   * @return La liste interne des utilisations de contenu du produit courant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {})
  @OrderBy(value = "position")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private List<InnerContentUsage> getInnerContentUsageListInternal()
  {
    return this.getInnerContentUsageList().getInternal();
  }
  /**
   * Setter de la liste interne des utilisations de contenu du produit courant
   * @param innerContentUsageList Liste interne des utilisations de contenu � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setInnerContentUsageListInternal(List<InnerContentUsage> innerContentUsageList)
  {
    this.setInnerContentUsageList(new Bid4WinList<InnerContentUsage>(innerContentUsageList, true));
  }
}
