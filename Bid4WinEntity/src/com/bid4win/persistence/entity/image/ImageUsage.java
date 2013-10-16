package com.bid4win.persistence.entity.image;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilObjectType;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.resource.FileResourceUsageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage_Relations;
import com.bid4win.persistence.entity.image.resource.Format;
import com.bid4win.persistence.entity.image.resource.Image;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.persistence.entity.product.Product_Relations;

/**
 * Cette classe défini la référence d'utilisation d'une image dans les différentes
 * partie de l'application<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@AttributeOverride(name = "type", column = @Column(name = "IMAGE_TYPE"))
public class ImageUsage
       extends FileResourceUsageMultiPart<ImageUsage, ImageType, ImageStorage, Format, Image>
{
  /** Type de l'utilisation d'image */
  @Transient private UsageType usageType = null;
  /** Position de l'utilisation d'image */
  @Transient private int position = 0;
  /** Produit potentiellement associé à cette utilisation d'image */
  @Transient private Product product = null;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private ImageUsage()
  {
    super();
  }
  /**
   * Constructeur de base d'une utilisation d'image
   * @param path Emplacement d'utilisation de l'image
   * @param name Nom d'utilisation de l'image
   * @param storage Stockage de l'image associée à cette utilisation
   * @param usageType Type de l'utilisation d'image
   * @throws ProtectionException Si le stockage d'image en argument est protégé
   * @throws ModelArgumentException Si le stockage d'image ou son type d'utilisation
   * en argument est nul
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de l'
   * image est invalide
   */
  private ImageUsage(String path, String name, ImageStorage storage, UsageType usageType)
          throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
    // Défini le type d'utilisation
    this.defineUsageType(usageType);
  }
  /**
   * Constructeur d'une utilisation d'image simple
   * @param path Emplacement d'utilisation de l'image
   * @param name Nom d'utilisation de l'image
   * @param storage Stockage de l'image associée à cette utilisation
   * @throws ProtectionException Si le stockage d'image en argument est protégé
   * @throws ModelArgumentException Si le stockage d'image en argument est nul
   * @throws UserException Si le nom ou l'emplacement d'utilisation de l'image
   * est invalide
   */
  public ImageUsage(String path, String name, ImageStorage storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    this(path, name, storage, UsageType.SIMPLE);
  }
  /**
   * Constructeur d'une utilisation d'image sur un produit
   * @param product Produit associé à cette utilisation d'image
   * @param storage Stockage de l'image associée à cette utilisation
   * @throws ProtectionException Si le stockage d'image ou le produit en argument
   * est protégé
   * @throws ModelArgumentException Si le stockage d'image ou le produit en argument
   * est nul
   * @throws UserException Si le nom ou l'emplacement d'utilisation de l'image
   * est invalide
   */
  public ImageUsage(Product product, ImageStorage storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    // Construit l'utilisation type
    this("temporaryPath", "temporaryName", storage, UsageType.PRODUCT);
    try
    {
      // Crée le lien avec le produit
      this.linkToProduct(product);
    }
    catch(ProtectionException ex)
    {
      this.unlinkFromStorage();
      throw ex;
    }
    catch(UserException ex)
    {
      this.unlinkFromStorage();
      throw ex;
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return ResourceRef.IMAGE;
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsage#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(ResourceUsage_Relations.RELATION_STORAGE))
    {
      return ResourceRef.IMAGE;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart#getPartTypeDefault()
   */
  @Override
  public Format getPartTypeDefault()
  {
    return ImageStorage.DEFAULT_FORMAT;
  }
  /**
   * Getter de la portion d'image correspondant au format en paramètre
   * @param format {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public Image getPart(Format format) throws UserException
  {
    try
    {
      return new Image(this, format);
    }
    catch(ModelArgumentException ex)
    {
      // Cette exception n'est lancée que si la ressource en argument est nulle
      return null;
    }
  }

  /**
   * Ajoute à la liste des noeuds de relations de l'utilisation d'image le lien
   * vers son produit si tel est le cas
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsage#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    if(this.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      nodeList.add(ImageUsage_Relations.NODE_PRODUCT);
    }
    return nodeList;
  }
  /**
   * Permet de récupérer le produit de l'utilisation d'image s'il correspond à la
   * relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsage#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(ImageUsage_Relations.RELATION_PRODUCT))
    {
      return this.getProduct();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le produit utilisant l'image s'il correspondant à la
   * relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsage#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(ImageUsage_Relations.RELATION_PRODUCT))
    {
      this.setProduct((Product)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Cette méthode permet de lier l'utilisation d'image courante au produit en
   * argument et d'en définir son nom et son emplacement de stockage
   * @param product Produit à lier à l'utilisation d'image courante
   * @throws ProtectionException Si l'utilisation d'image courante ou le produit
   * en argument est protégé
   * @throws UserException Si le produit en argument est nul ou si l'utilisation
   * d'image courante n'est pas de type produit ou est déjà liée ou référencée
   * par le produit en argument ou si le nom ou l'emplacement de stockage défini
   * est invalide
   */
  private void linkToProduct(Product product) throws ProtectionException, UserException
  {
    // Vérifie le type d'utilisation d'image
    UtilObjectType.checkBelongsTo("usageType", this.getUsageType(), UsageType.PRODUCT,
                                  ResourceRef.IMAGE_USAGE_INVALID_ERROR);
    // Crée le lien
    this.linkTo(ImageUsage_Relations.RELATION_PRODUCT,
                Product_Relations.RELATION_IMAGE_USAGE_LIST,
                product);
    // Défini le nom et l'emplacement de stockage de l'utilisation d'image
    this.definePath(product.getId());
    this.defineName(product.getName());
  }
  /**
   * Cette méthode permet de délier l'utilisation d'image courante de son produit
   * @return Le produit précédemment lié à l'utilisation d'image courante
   * @throws ProtectionException Si l'utilisation d'image courante ou son produit
   * est protégé
   * @throws UserException Si l'utilisation d'image courante n'est pas de type
   * produit ou n'est pas liée ou n'est pas référencée par son produit
   */
  public Product unlinkFromProduct() throws ProtectionException, UserException
  {
    // Vérifie le type d'utilisation d'image
    UtilObjectType.checkBelongsTo("usageType", this.getUsageType(), UsageType.PRODUCT,
                                  ResourceRef.IMAGE_USAGE_INVALID_ERROR);
    // Supprime le lien
    return (Product)this.unlinkFrom(ImageUsage_Relations.RELATION_PRODUCT,
                                    Product_Relations.RELATION_IMAGE_USAGE_LIST);
  }

  /**
   * Cette méthode permet de définir le type de l'utilisation d'image courante
   * @param usageType Définition du type de l'utilisation d'image courante
   * @throws ProtectionException Si l'utilisation d'image courante est protégée
   * @throws ModelArgumentException Si l'utilisation d'image courante a déjà un
   * type ou si celui en argument est nul
   */
  private void defineUsageType(UsageType usageType) throws ProtectionException, ModelArgumentException
  {
    // Vérifie la protection de l'utilisation d'image courante
    this.checkProtection();
    UtilObject.checkNull("getUsageType()", this.getUsageType());
    this.setUsageType(UtilObject.checkNotNull("UsageType", usageType));
  }
  /**
   * Cette méthode permet de définir la position de l'utilisation d'image courante
   * @param position Définition de la position de l'utilisation d'image courante
   * @throws ProtectionException Si l'utilisation d'image courante est protégée
   */
  public void definePosition(int position) throws ProtectionException
  {
    // Vérifie la protection de l'utilisation d'image courante
    this.checkProtection();
    // Dans le cas d'une utilisation par un produit, le nom de celle-ci est basé
    // aussi sur sa position
    if(this.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      try
      {
        this.setPosition(position);
        this.defineName(this.getProduct().getName());
      }
      catch(UserException ex)
      {
        // Impossible car le nom de l'utilisation a déjà été validé avant la
        // définition de sa position
        ex.printStackTrace();
      }
    }
  }
  /**
   * Cette fonction est redéfinie afin d'empécher la modification directe de l'
   * emplacement de stockage de l'utilisation d'image pour certains types et de
   * définir à la place l'emplacement de stockage en fonction du type d'utilisation
   * (identifiant de son produit par exemple)
   * @param path {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#definePath(java.lang.String)
   */
  @Override
  public void definePath(String path) throws ProtectionException, UserException
  {
    if(this.getUsageType() != null && this.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      UtilObject.checkEquals("path", path, this.getProduct().getId(),
                             ResourceRef.IMAGE_PATH_INVALID_ERROR);
    }
    super.definePath(path);
  }
  /**
   * Cette fonction est redéfinie afin d'empécher la modification directe du nom
   * de l'utilisation d'image pour certains types et de définir à la place le nom
   * en fonction du type d'utilisation (nom de son produit par exemple)
   * @param name {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#defineName(java.lang.String)
   */
  @Override
  public void defineName(String name) throws ProtectionException, UserException
  {
    if(this.getUsageType() != null && this.getUsageType().belongsTo(UsageType.PRODUCT))
    {
      UtilObject.checkEquals("name", name, this.getProduct().getName(),
                             ResourceRef.IMAGE_NAME_INVALID_ERROR);
      name += "_" + this.getPosition();
    }
    super.defineName(name);
  }
  /**
   * Redéfini l'emplacement de stockage réel de la ressource utilisé pour construire
   * son chemin d'accès complet afin de classer différemment celle-ci suivant son
   * type d'utilisation
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.Resource#getRealPath()
   */
  @Override
  public String getRealPath() throws UserException
  {
    return UtilFile.concatRelativePath(
        this.getMessageRef(), this.getUsageType().getCode(), super.getRealPath());
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du type de l'utilisation d'image
   * @return Le type de l'utilisation d'image
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "USAGE_TYPE", nullable = false, unique = false)
  public UsageType getUsageType()
  {
    return this.usageType;
  }
  /**
   * Setter du type de l'utilisation d'image
   * @param usageType Type de l'utilisation d'image à positionner
   */
  private void setUsageType(UsageType usageType)
  {
    this.usageType = usageType;
  }

  /**
   * Getter de la position de l'utilisation d'image
   * @return La position de l'utilisation d'image
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "POSITION", length = 3, nullable = false, unique = false)
  public int getPosition()
  {
    return this.position;
  }
  /**
   * Setter de la position de l'utilisation d'image
   * @param position Position de l'utilisation d'image à positionner
   */
  private void setPosition(int position)
  {
    this.position = position;
  }

  /**
   * Getter du produit potentiel de l'utilisation d'image
   * @return Le produit potentiel de l'utilisation d'image
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "PRODUCT_ID", nullable = true, unique = false)
  public Product getProduct()
  {
    return this.product;
  }
  /**
   * Setter du produit potentiel de l'utilisation d'image
   * @param product Produit potentiel de l'utilisation d'image à positionner
   */
  private void setProduct(Product product)
  {
    this.product = product;
  }
}
