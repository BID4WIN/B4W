package com.bid4win.commons.persistence.entity.property;

import java.util.Map;
import java.util.Properties;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Cette entité représente une propriété de base<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <PROPERTY_ROOT> Doit définir la classe des propriétés racines gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
// Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class PropertyAbstract<CLASS extends PropertyAbstract<CLASS, PROPERTY_ROOT>,
                                       PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, CLASS>>
       extends PropertyBase<CLASS, PROPERTY_ROOT, CLASS>
{
  /** Clé de la propriété */
  @Transient private String key;
  /** Valeur de la propriété */
  @Transient private String value;
  /** Propriété parent de la propriété */
  @Transient private CLASS property = null;
  /** Propriété racine de la propriété */
  @Transient private PROPERTY_ROOT root = null;
  /** Présent uniquement pour la définition JPA de la map persistante */
  @SuppressWarnings("unused")
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY,
             cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @MapKey(name = "key")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  private Map<String, CLASS> propertyMapDatabase;

  /**
   * Constructeur pour création par introspection
   */
  protected PropertyAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public PropertyAbstract(String key, String value) throws UserException
  {
    super();
    this.defineKey(key);
    this.defineValue(value);
  }
  /**
   * Constructeur complet avec positionnement de la propriété parent
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @param property Propriété parent de la propriété
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws UserException Si la propriété parent en argument est nulle ou référence
   * déjà la propriété en construction ou si les paramètres entrés sont invalides
   */
  public PropertyAbstract(String key, String value, CLASS property)
         throws ProtectionException, UserException
  {
    this(key, value);
    this.linkTo(property);
  }
  /**
   * Constructeur complet avec positionnement de la propriété racine
   * @param key Clé de la propriété
   * @param value Valeur de la propriété
   * @param root Propriété racine de la propriété
   * @throws ProtectionException Si la propriété racine en argument est protégée
   * @throws UserException Si la propriété racine en argument est nulle ou référence
   * déjà la propriété en construction ou si les paramètres entrés sont invalides
   */
  public PropertyAbstract(String key, String value, PROPERTY_ROOT root)
         throws ProtectionException, UserException
  {
    this(key, value);
    this.linkTo(root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Propriété dont on va reprendre tous les éléments sauf son
   * potentiel lien vers une propriété parent (ou racine)
   * @param property Propriété parent de la propriété
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws ModelArgumentException Si la propriété à copier ou la propriété parent
   * en argument est nulle
   * @throws UserException Si les paramètres entrés sont invalides ou si la propriété
   * parent référence déjà la propriété en construction
   */
  @SuppressWarnings("unchecked")
  public PropertyAbstract(CLASS toBeCopied, CLASS property)
         throws ProtectionException, ModelArgumentException, UserException
  {
    this(UtilObject.checkNotNull("toBeCopied", toBeCopied).getKey(), toBeCopied.getValue());
    for(CLASS current : toBeCopied.getProperties())
    {
      this.createProperty(current, (CLASS)this);
    }
    // On doit effectuer le lien en dernier pour éviter la boucle infinie à cause
    // de la récursivité
    this.linkTo(property);
  }

  /**
   * Cette méthode doit être définie afin de créer par copie le bon type de propriété
   * gérée
   * @param toBeCopied Propriété à copier
   * @param property Propriété parent de la propriété à copier
   * @return La propriété créée par copie du bon type
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws ModelArgumentException TODO A COMMENTER
   * @throws UserException Si la création de la propriété échoue
   */
  protected abstract CLASS createProperty(CLASS toBeCopied, CLASS property)
            throws ProtectionException, ModelArgumentException, UserException;
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(PropertyAbstract_Relations.NODE_ROOT))
    {
      return PropertyRef.PROPERTY;
    }
    else if(relation.equals(PropertyAbstract_Relations.NODE_PROPERTY))
    {
      return PropertyRef.PROPERTY;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   * Redéfini l'équivalence interne de deux propriétés sans prise en compte de
   * leurs relations afin d'y ajouter le test de leurs clés et valeurs
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getKey(), toBeCompared.getKey()) &&
           Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue());
  }
  /**
   * Permet d'effectuer le rendu simple de la propriété courante sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute la clé et la valeur de la propriété
    buffer.append(" KEY=" + this.getKey());
    buffer.append(" VALUE=" + this.getValue());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute à la liste des noeuds de relations de l'entité les liens vers sa propriété
   * racine et sa propriété parent
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(0, PropertyAbstract_Relations.NODE_ROOT);
    nodeList.add(1, PropertyAbstract_Relations.NODE_PROPERTY);
    return nodeList;
  }
  /**
   * Permet de récupérer la propriété racine ou parent de l'entité si elle correspondant
   * à la relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(PropertyAbstract_Relations.RELATION_ROOT))
    {
      return this.getRoot();
    }
    else if(relation.equals(PropertyAbstract_Relations.RELATION_PROPERTY))
    {
      return this.getProperty();
    }
    return super.getRelationSimple(relation);
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(PropertyAbstract_Relations.RELATION_ROOT))
    {
      this.setRoot((PROPERTY_ROOT)entity);
    }
    else if(relation.equals(PropertyAbstract_Relations.RELATION_PROPERTY))
    {
      this.setProperty((CLASS)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Redéfini l'exportation de la propriété courante afin de bien ajouter celle-ci
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#toProperties()
   */
  @Override
  public Properties toProperties()
  {
    Properties properties = super.toProperties();
    properties.put(this.getFullKey(), this.getValue());
    return properties;
  }
  /**
   * Cette fonction permet d'exporter la propriété en prenant la clé en argument
   * comme base des clés générées. Si la clé fournie ne correspond à aucune base
   * valide, la racine des clés sera utilisée
   * @param baseKey Base à utiliser pour générer les clés
   * @return L'exportation de la propriété utilisant la clé en argument comme base
   * des clés générées
   */
  public Properties toProperties(String baseKey)
  {
    Properties properties = new Properties();
    // Récupère la clé complète de la propriété
    String fullKey = this.getFullKey();
    // La base est connue pour le propriété
    if(UtilProperty.isBaseForKey(fullKey, baseKey))
    {
      fullKey = this.getKey(baseKey);
    }
    // La base est inconnue pour la propriété
    else
    {
      baseKey = UtilString.EMPTY;
    }
    // Ajoute la propriété
    properties.put(fullKey, this.getValue());
    // Ajoute les sous-propriétés
    for(CLASS property : this.getProperties())
    {
      properties.putAll(property.toProperties(baseKey));
    }
    return properties;
  }

  /**
   * Getter de la clé de la propriété incluant celles des propriétés parents
   * jusqu'à la base définie en argument
   * @param baseKey Base à utiliser pour générer la clé
   * @return La clé de la propriété incluant celles des propriétés parents jusqu'à
   * la base définie en argument
   */
  public String getKey(String baseKey)
  {
    // On récupère la clé complète
    String fullKey = this.getFullKey();
    // Si la clé en argument est une base pour la clé complète, il faut redéfinir
    // celle-ci
    if(UtilProperty.isBaseForKey(fullKey, baseKey))
    {
      fullKey = fullKey.replaceFirst(baseKey, UtilProperty.getLastKey(baseKey));
    }
    return fullKey;
  }
  /**
   * Getter de la clé complète de la propriété incluant celles des propriétés
   * parents jusqu'à la racine
   * @return La clé complète de la propriété
   */
  public String getFullKey()
  {
    if(!this.hasProperty())
    {
      return this.getKey();
    }
    return UtilProperty.addKey(this.getProperty().getFullKey(), this.getKey());
  }

  /**
   * Permet de savoir si la propriété a une propriété parent
   * @return True si la propriété a une propriété parent, false sinon
   */
  public boolean hasProperty()
  {
    if(this.getProperty() == null)
    {
      return false;
    }
    return true;
  }
  /**
   * Permet de savoir si la propriété a une propriété racine
   * @return True si la propriété a une propriété racine, false sinon
   */
  public boolean hasRoot()
  {
    if(this.getRoot() == null)
    {
      return false;
    }
    return true;
  }
  /**
   * Permet de récupérer la racine de l'arbre de propriétés auquel appartient la
   * propriété courante en remontant de parent en parent jusqu'à celle-ci
   * @return La racine de l'arbre de propriétés auquel appartient la propriété
   * courante
   */
  public PROPERTY_ROOT getTreeRoot()
  {
    if(this.hasRoot())
    {
      return this.getRoot();
    }
    else if(this.hasProperty())
    {
      return this.getProperty().getTreeRoot();
    }
    else
    {
      return null;
    }
  }

  /**
   * Permet de lier la propriété courante à la propriété parent en paramètre
   * @param property Propriété parent à lier à la propriété courante
   * @throws ProtectionException Si la propriété courante ou la propriété parent
   * en argument est protégée
   * @throws UserException Si la propriété parent en argument est nulle ou référence
   * déjà la propriété courante ou si celle-ci est déjà liée
   */
  public void linkTo(CLASS property) throws ProtectionException, UserException
  {
    UtilBoolean.checkFalse("linked", this.hasRoot(),
                           this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    this.linkTo(PropertyAbstract_Relations.RELATION_PROPERTY,
                PropertyBase_Relations.RELATION_PROPERTY_MAP,
                property);
  }
  /**
   * Permet de lier la propriété courante à la propriété racine en paramètre
   * @param root Propriété racine à lier à la propriété courante
   * @throws ProtectionException Si la propriété courante ou la propriété racine
   * en argument est protégée
   * @throws UserException Si la propriété racine en argument est nulle ou référence
   * déjà la propriété courante ou si celle-ci est déjà liée
   */
  public void linkTo(PROPERTY_ROOT root) throws ProtectionException, UserException
  {
    UtilBoolean.checkFalse("linked", this.hasProperty(),
                           this.getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
    this.linkTo(PropertyAbstract_Relations.RELATION_ROOT,
                PropertyBase_Relations.RELATION_PROPERTY_MAP,
                root);
  }
  /**
   * Permet de délier la propriété courante de sa propriété parent
   * @return L'ancienne propriété parent
   * @throws ProtectionException Si la propriété courante ou sa propriété parent
   * est protégée
   * @throws UserException Si la propriété courante n'est pas liée à une propriété
   * parent ou si celle-ci ne la référence pas
   */
  @SuppressWarnings("unchecked")
  public CLASS unlinkFromProperty() throws ProtectionException, UserException
  {
    return (CLASS)this.unlinkFrom(PropertyAbstract_Relations.RELATION_PROPERTY,
                                  PropertyBase_Relations.RELATION_PROPERTY_MAP);
  }
  /**
   * Permet de délier la propriété courante de sa propriété racine
   * @return L'ancienne propriété racine
   * @throws ProtectionException Si la propriété courante ou sa propriété racine
   * est protégée
   * @throws UserException Si la propriété courante n'est pas liée à une propriété
   * racine ou si celle-ci ne la référence pas
   */
  @SuppressWarnings("unchecked")
  public PROPERTY_ROOT unlinkFromRoot() throws ProtectionException, UserException
  {
    return (PROPERTY_ROOT)this.unlinkFrom(PropertyAbstract_Relations.RELATION_ROOT,
                                          PropertyBase_Relations.RELATION_PROPERTY_MAP);
  }

  /**
   * Cette méthode permet de définir la clé de la propriété
   * @param key Définition de la clé de la propriété
   * @throws ProtectionException Si la propriété courante est protégée
   * @throws UserException Si on défini une clé invalide ou si la propriété est
   * liée à une propriété racine ou parent
   */
  public void defineKey(String key) throws ProtectionException, UserException
  {
    // Vérifie la protection de la propriété courante
    this.checkProtection();
    // Vérifie que la propriété n'est pas lié
    if(this.hasProperty() || this.hasRoot())
    {
      throw new UserException(this.getMessageRef(MessageRef.SUFFIX_EXISTING_ERROR));
    }
    key = UtilString.trimNotNull(key).toLowerCase();
    this.setKey(UtilProperty.checkSimpleKey(key, this.getMessageRef()));
  }
  /**
   * Cette méthode permet de définir la valeur de la propriété
   * @param value Définition de la valeur de la propriété
   * @throws ProtectionException Si la propriété courante est protégée
   */
  public void defineValue(String value) throws ProtectionException
  {
    // Vérifie la protection de la propriété courante
    this.checkProtection();
    this.setValue(UtilString.trimNotNull(value));
  }

  /**
   * Cette méthode permet de récupérer la valeur entière de la propriété courante
   * @return La valeur entière de la propriété courante
   * @throws CommunicationException Si la valeur de la propriété courante n'est pas
   * du bon type
   */
  public int getIntValue() throws CommunicationException
  {
    try
    {
      return Integer.valueOf(this.getValue());
    }
    catch(NumberFormatException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Cette méthode permet de récupérer la valeur décimale de la propriété courante
   * @return La valeur décimale de la propriété courante
   * @throws CommunicationException Si la valeur de la propriété courante n'est pas
   * du bon type
   */
  public double getDoubleValue() throws CommunicationException
  {
    try
    {
      return Double.valueOf(this.getValue());
    }
    catch(NumberFormatException ex)
    {
      throw new CommunicationException(ex);
    }
  }
  /**
   * Cette méthode permet de récupérer la valeur booléenne de la propriété courante
   * @return La valeur booléenne de la propriété courante
   * @throws CommunicationException Si la valeur de la propriété courante n'est pas
   * du bon type
   */
  public boolean getBooleanValue() throws CommunicationException
  {
    try
    {
      return Boolean.valueOf(this.getValue());
    }
    catch(NumberFormatException ex)
    {
      throw new CommunicationException(ex);
    }
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de la propriété racine
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Id()
  @Column(name = "ID", length = 5, nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId()
  {
    return super.getId();
  }

  /**
   * Getter de la clé de la propriété
   * @return La clé de la propriété
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name="KEY_", length = 40, nullable = false, unique = false)
  public String getKey()
  {
    return this.key;
  }
  /**
   * Setter de la clé de la propriété
   * @param key Clé de la propriété à positionner
   */
  private void setKey(String key)
  {
    this.key = key;
  }

  /**
   * Getter de la valeur de la propriété
   * @return La valeur de la propriété
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "VALUE", length = 100, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de la propriété
   * @param value Valeur de la propriété à positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }

  /**
   * Getter de la propriété parent de la propriété
   * @return La potentielle propriété parent de la propriété
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "PROPERTY_ID", nullable = true, unique = false)
  public CLASS getProperty()
  {
    return this.property;
  }
  /**
   * Setter de la propriété parent de la propriété
   * @param property Propriété parent de la propriété à positionner
   */
  private void setProperty(CLASS property)
  {
    this.property = property;
  }

  /**
   * Getter de la propriété racine de la propriété
   * @return La potentielle propriété racine de la propriété
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstract#getRoot()
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "ROOT_ID", nullable = true, unique = false)
  public PROPERTY_ROOT getRoot()
  {
    return this.root;
  }
  /**
   * Setter de la propriété racine de la propriété
   * @param root Propriété racine de la propriété à positionner
   */
  private void setRoot(PROPERTY_ROOT root)
  {
    this.root = root;
  }
}
