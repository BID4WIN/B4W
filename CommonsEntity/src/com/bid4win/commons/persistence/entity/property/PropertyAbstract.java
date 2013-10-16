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
 * Cette entit� repr�sente une propri�t� de base<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <PROPERTY_ROOT> Doit d�finir la classe des propri�t�s racines g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
// Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class PropertyAbstract<CLASS extends PropertyAbstract<CLASS, PROPERTY_ROOT>,
                                       PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, CLASS>>
       extends PropertyBase<CLASS, PROPERTY_ROOT, CLASS>
{
  /** Cl� de la propri�t� */
  @Transient private String key;
  /** Valeur de la propri�t� */
  @Transient private String value;
  /** Propri�t� parent de la propri�t� */
  @Transient private CLASS property = null;
  /** Propri�t� racine de la propri�t� */
  @Transient private PROPERTY_ROOT root = null;
  /** Pr�sent uniquement pour la d�finition JPA de la map persistante */
  @SuppressWarnings("unused")
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "property", fetch = FetchType.LAZY,
             cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @MapKey(name = "key")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  private Map<String, CLASS> propertyMapDatabase;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected PropertyAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param key Cl� de la propri�t�
   * @param value Valeur de la propri�t�
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public PropertyAbstract(String key, String value) throws UserException
  {
    super();
    this.defineKey(key);
    this.defineValue(value);
  }
  /**
   * Constructeur complet avec positionnement de la propri�t� parent
   * @param key Cl� de la propri�t�
   * @param value Valeur de la propri�t�
   * @param property Propri�t� parent de la propri�t�
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws UserException Si la propri�t� parent en argument est nulle ou r�f�rence
   * d�j� la propri�t� en construction ou si les param�tres entr�s sont invalides
   */
  public PropertyAbstract(String key, String value, CLASS property)
         throws ProtectionException, UserException
  {
    this(key, value);
    this.linkTo(property);
  }
  /**
   * Constructeur complet avec positionnement de la propri�t� racine
   * @param key Cl� de la propri�t�
   * @param value Valeur de la propri�t�
   * @param root Propri�t� racine de la propri�t�
   * @throws ProtectionException Si la propri�t� racine en argument est prot�g�e
   * @throws UserException Si la propri�t� racine en argument est nulle ou r�f�rence
   * d�j� la propri�t� en construction ou si les param�tres entr�s sont invalides
   */
  public PropertyAbstract(String key, String value, PROPERTY_ROOT root)
         throws ProtectionException, UserException
  {
    this(key, value);
    this.linkTo(root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Propri�t� dont on va reprendre tous les �l�ments sauf son
   * potentiel lien vers une propri�t� parent (ou racine)
   * @param property Propri�t� parent de la propri�t�
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws ModelArgumentException Si la propri�t� � copier ou la propri�t� parent
   * en argument est nulle
   * @throws UserException Si les param�tres entr�s sont invalides ou si la propri�t�
   * parent r�f�rence d�j� la propri�t� en construction
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
    // On doit effectuer le lien en dernier pour �viter la boucle infinie � cause
    // de la r�cursivit�
    this.linkTo(property);
  }

  /**
   * Cette m�thode doit �tre d�finie afin de cr�er par copie le bon type de propri�t�
   * g�r�e
   * @param toBeCopied Propri�t� � copier
   * @param property Propri�t� parent de la propri�t� � copier
   * @return La propri�t� cr��e par copie du bon type
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws ModelArgumentException TODO A COMMENTER
   * @throws UserException Si la cr�ation de la propri�t� �choue
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
   * Red�fini l'�quivalence interne de deux propri�t�s sans prise en compte de
   * leurs relations afin d'y ajouter le test de leurs cl�s et valeurs
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
   * Permet d'effectuer le rendu simple de la propri�t� courante sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute la cl� et la valeur de la propri�t�
    buffer.append(" KEY=" + this.getKey());
    buffer.append(" VALUE=" + this.getValue());
    // Retourne le rendu
    return buffer;
  }
  /**
   * Ajoute � la liste des noeuds de relations de l'entit� les liens vers sa propri�t�
   * racine et sa propri�t� parent
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
   * Permet de r�cup�rer la propri�t� racine ou parent de l'entit� si elle correspondant
   * � la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type simple � remonter
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
   * Red�fini l'exportation de la propri�t� courante afin de bien ajouter celle-ci
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
   * Cette fonction permet d'exporter la propri�t� en prenant la cl� en argument
   * comme base des cl�s g�n�r�es. Si la cl� fournie ne correspond � aucune base
   * valide, la racine des cl�s sera utilis�e
   * @param baseKey Base � utiliser pour g�n�rer les cl�s
   * @return L'exportation de la propri�t� utilisant la cl� en argument comme base
   * des cl�s g�n�r�es
   */
  public Properties toProperties(String baseKey)
  {
    Properties properties = new Properties();
    // R�cup�re la cl� compl�te de la propri�t�
    String fullKey = this.getFullKey();
    // La base est connue pour le propri�t�
    if(UtilProperty.isBaseForKey(fullKey, baseKey))
    {
      fullKey = this.getKey(baseKey);
    }
    // La base est inconnue pour la propri�t�
    else
    {
      baseKey = UtilString.EMPTY;
    }
    // Ajoute la propri�t�
    properties.put(fullKey, this.getValue());
    // Ajoute les sous-propri�t�s
    for(CLASS property : this.getProperties())
    {
      properties.putAll(property.toProperties(baseKey));
    }
    return properties;
  }

  /**
   * Getter de la cl� de la propri�t� incluant celles des propri�t�s parents
   * jusqu'� la base d�finie en argument
   * @param baseKey Base � utiliser pour g�n�rer la cl�
   * @return La cl� de la propri�t� incluant celles des propri�t�s parents jusqu'�
   * la base d�finie en argument
   */
  public String getKey(String baseKey)
  {
    // On r�cup�re la cl� compl�te
    String fullKey = this.getFullKey();
    // Si la cl� en argument est une base pour la cl� compl�te, il faut red�finir
    // celle-ci
    if(UtilProperty.isBaseForKey(fullKey, baseKey))
    {
      fullKey = fullKey.replaceFirst(baseKey, UtilProperty.getLastKey(baseKey));
    }
    return fullKey;
  }
  /**
   * Getter de la cl� compl�te de la propri�t� incluant celles des propri�t�s
   * parents jusqu'� la racine
   * @return La cl� compl�te de la propri�t�
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
   * Permet de savoir si la propri�t� a une propri�t� parent
   * @return True si la propri�t� a une propri�t� parent, false sinon
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
   * Permet de savoir si la propri�t� a une propri�t� racine
   * @return True si la propri�t� a une propri�t� racine, false sinon
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
   * Permet de r�cup�rer la racine de l'arbre de propri�t�s auquel appartient la
   * propri�t� courante en remontant de parent en parent jusqu'� celle-ci
   * @return La racine de l'arbre de propri�t�s auquel appartient la propri�t�
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
   * Permet de lier la propri�t� courante � la propri�t� parent en param�tre
   * @param property Propri�t� parent � lier � la propri�t� courante
   * @throws ProtectionException Si la propri�t� courante ou la propri�t� parent
   * en argument est prot�g�e
   * @throws UserException Si la propri�t� parent en argument est nulle ou r�f�rence
   * d�j� la propri�t� courante ou si celle-ci est d�j� li�e
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
   * Permet de lier la propri�t� courante � la propri�t� racine en param�tre
   * @param root Propri�t� racine � lier � la propri�t� courante
   * @throws ProtectionException Si la propri�t� courante ou la propri�t� racine
   * en argument est prot�g�e
   * @throws UserException Si la propri�t� racine en argument est nulle ou r�f�rence
   * d�j� la propri�t� courante ou si celle-ci est d�j� li�e
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
   * Permet de d�lier la propri�t� courante de sa propri�t� parent
   * @return L'ancienne propri�t� parent
   * @throws ProtectionException Si la propri�t� courante ou sa propri�t� parent
   * est prot�g�e
   * @throws UserException Si la propri�t� courante n'est pas li�e � une propri�t�
   * parent ou si celle-ci ne la r�f�rence pas
   */
  @SuppressWarnings("unchecked")
  public CLASS unlinkFromProperty() throws ProtectionException, UserException
  {
    return (CLASS)this.unlinkFrom(PropertyAbstract_Relations.RELATION_PROPERTY,
                                  PropertyBase_Relations.RELATION_PROPERTY_MAP);
  }
  /**
   * Permet de d�lier la propri�t� courante de sa propri�t� racine
   * @return L'ancienne propri�t� racine
   * @throws ProtectionException Si la propri�t� courante ou sa propri�t� racine
   * est prot�g�e
   * @throws UserException Si la propri�t� courante n'est pas li�e � une propri�t�
   * racine ou si celle-ci ne la r�f�rence pas
   */
  @SuppressWarnings("unchecked")
  public PROPERTY_ROOT unlinkFromRoot() throws ProtectionException, UserException
  {
    return (PROPERTY_ROOT)this.unlinkFrom(PropertyAbstract_Relations.RELATION_ROOT,
                                          PropertyBase_Relations.RELATION_PROPERTY_MAP);
  }

  /**
   * Cette m�thode permet de d�finir la cl� de la propri�t�
   * @param key D�finition de la cl� de la propri�t�
   * @throws ProtectionException Si la propri�t� courante est prot�g�e
   * @throws UserException Si on d�fini une cl� invalide ou si la propri�t� est
   * li�e � une propri�t� racine ou parent
   */
  public void defineKey(String key) throws ProtectionException, UserException
  {
    // V�rifie la protection de la propri�t� courante
    this.checkProtection();
    // V�rifie que la propri�t� n'est pas li�
    if(this.hasProperty() || this.hasRoot())
    {
      throw new UserException(this.getMessageRef(MessageRef.SUFFIX_EXISTING_ERROR));
    }
    key = UtilString.trimNotNull(key).toLowerCase();
    this.setKey(UtilProperty.checkSimpleKey(key, this.getMessageRef()));
  }
  /**
   * Cette m�thode permet de d�finir la valeur de la propri�t�
   * @param value D�finition de la valeur de la propri�t�
   * @throws ProtectionException Si la propri�t� courante est prot�g�e
   */
  public void defineValue(String value) throws ProtectionException
  {
    // V�rifie la protection de la propri�t� courante
    this.checkProtection();
    this.setValue(UtilString.trimNotNull(value));
  }

  /**
   * Cette m�thode permet de r�cup�rer la valeur enti�re de la propri�t� courante
   * @return La valeur enti�re de la propri�t� courante
   * @throws CommunicationException Si la valeur de la propri�t� courante n'est pas
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
   * Cette m�thode permet de r�cup�rer la valeur d�cimale de la propri�t� courante
   * @return La valeur d�cimale de la propri�t� courante
   * @throws CommunicationException Si la valeur de la propri�t� courante n'est pas
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
   * Cette m�thode permet de r�cup�rer la valeur bool�enne de la propri�t� courante
   * @return La valeur bool�enne de la propri�t� courante
   * @throws CommunicationException Si la valeur de la propri�t� courante n'est pas
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
   * Getter de l'identifiant de la propri�t� racine
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
   * Getter de la cl� de la propri�t�
   * @return La cl� de la propri�t�
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name="KEY_", length = 40, nullable = false, unique = false)
  public String getKey()
  {
    return this.key;
  }
  /**
   * Setter de la cl� de la propri�t�
   * @param key Cl� de la propri�t� � positionner
   */
  private void setKey(String key)
  {
    this.key = key;
  }

  /**
   * Getter de la valeur de la propri�t�
   * @return La valeur de la propri�t�
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "VALUE", length = 100, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de la propri�t�
   * @param value Valeur de la propri�t� � positionner
   */
  private void setValue(String value)
  {
    this.value = value;
  }

  /**
   * Getter de la propri�t� parent de la propri�t�
   * @return La potentielle propri�t� parent de la propri�t�
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
   * Setter de la propri�t� parent de la propri�t�
   * @param property Propri�t� parent de la propri�t� � positionner
   */
  private void setProperty(CLASS property)
  {
    this.property = property;
  }

  /**
   * Getter de la propri�t� racine de la propri�t�
   * @return La potentielle propri�t� racine de la propri�t�
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
   * Setter de la propri�t� racine de la propri�t�
   * @param root Propri�t� racine de la propri�t� � positionner
   */
  private void setRoot(PROPERTY_ROOT root)
  {
    this.root = root;
  }
}
