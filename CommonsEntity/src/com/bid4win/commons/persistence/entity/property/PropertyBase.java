package com.bid4win.commons.persistence.entity.property;

import java.util.Map;
import java.util.Properties;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Cette entité représente la base des propriétés<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <PROPERTY_ROOT> Doit définir la classe des propriétés racines gérées<BR>
 * @param <PROPERTY> Doit définir la classe des propriétés gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class PropertyBase<CLASS extends PropertyBase<CLASS, PROPERTY_ROOT, PROPERTY>,
                                   PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                   PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>>
       extends Bid4WinEntity<CLASS, Integer>
{
  /** Map de sous-propriétés de la propriété */
  @Transient private Bid4WinMap<String, PROPERTY> propertyMap;
  {
    this.setPropertyMapInternal(new Bid4WinMap<String, PROPERTY>());
  }

  /**
   * Constructeur pour création par introspection
   */
  protected PropertyBase()
  {
    super();
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de la propriété
   */
  protected PropertyBase(int id)
  {
    super(id);
  }

  /**
   * Cette fonction permet d'exporter la propriété courante ainsi que toutes ses
   * sous-propriétés
   * @return L'exportation de la propriété courante avec toutes ses sous-propriétés
   */
  public Properties toProperties()
  {
    Properties properties = new Properties();
    // Ajoute les sous-propriétés
    for(PROPERTY property : this.getProperties())
    {
      properties.putAll(property.toProperties(UtilString.EMPTY));
    }
    return properties;
  }

  /**
   * Ajoute à la liste des noeuds de relations de l'entité le lien vers sa map de
   * sous-propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(PropertyBase_Relations.NODE_PROPERTY_MAP);
    return nodeList;
  }
  /**
   * Permet de récupérer la map de sous-propriétés de l'entité si elle correspond
   * à la relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type map à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinMap<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    if(relation.equals(PropertyBase_Relations.RELATION_PROPERTY_MAP))
    {
      return this.getPropertyMapInternal();
    }
    return super.getRelationMap(relation);
  }
  /**
   * Permet de récupérer la clé associée à la propriété en paramètre pour son classement
   * dans la map si elle correspond à la relation en argument
   * @param relation {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMapKey(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Object getRelationMapKey(Bid4WinRelation relation, Bid4WinEntity<?, ?> value)
  {
    if(relation.equals(PropertyBase_Relations.RELATION_PROPERTY_MAP))
    {
      return ((PROPERTY)value).getKey();
    }
    return super.getRelationMapKey(relation, value);
  }
  /**
   * Permet de définir la base des références de messages liées aux propriétés
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return PropertyRef.PROPERTY;
  }
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
    if(relation.equals(PropertyBase_Relations.RELATION_PROPERTY_MAP))
    {
      return PropertyRef.PROPERTY;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   * Getter de la sous-propriété associée dont la clé est passée en paramètre
   * @param key Clé de la sous-propriété associée à récupérer
   * @return La sous-propriété associée dont la clé correspond à celle en paramètre
   * ou null si la sous-propriété n'a pas pu être trouvée
   */
  public PROPERTY getProperty(String key)
  {
    PROPERTY property = this.getPropertyMapInternal().get(UtilProperty.getFirstKey(key));
    // On doit continuer à descendre l'arbre de sous-propriétés
    if(property != null && !UtilProperty.removeFirstKey(key).equals(UtilString.EMPTY))
    {
      property = property.getProperty(UtilProperty.removeFirstKey(key));
    }
    // La clé en argument est incomplète
    else if(UtilProperty.getLastKey(key).equals(UtilString.EMPTY))
    {
      property = null;
    }
    return property;
  }
  /**
   * Ajoute une sous-propriété à la propriété courante et crée les propriétés
   * intermédiaires si besoin
   * @param key Clé de la sous-propriété à ajouter
   * @param value Valeur de la sous-propriété à ajouter
   * @return La base de l'arbre de sous-propriétés ajouté à la propriété courante
   * @throws ProtectionException Si la propriété courante est protégée
   * @throws ModelArgumentException Si on positionne une propriété nulle
   * @throws UserException Si la clé est invalide ou si on positionne une propriété
   * déjà référencée
   */
  @SuppressWarnings("unchecked")
  public PROPERTY addProperty(String key, String value)
         throws ProtectionException, ModelArgumentException, UserException
  {
    // Vérifie la validité de la clé
    UtilProperty.checkFullKey(key, this.getMessageRef());
    // Récupère le premier niveau de la clé
    String firstKey = UtilProperty.getFirstKey(key);
    key = UtilProperty.removeFirstKey(key);
    // Récupère la propriété potentiellement existante
    PROPERTY property = this.getProperty(firstKey);
    // On est sur la dernière clé ou la propriété n'existe pas
    if(key.equals(UtilString.EMPTY) || property == null)
    {
      // On crée la dernière propriété de l'arbre
      if(key.equals(UtilString.EMPTY))
      {
        property = this.createProperty(firstKey, value);
      }
      // On crée à partir de la propriété intermédiaire
      else
      {
        property = this.createProperty(firstKey, null);
        property.addProperty(key, value);
      }
      if(this instanceof PropertyRootAbstract)
      {
        property.linkTo((PROPERTY_ROOT)this);
      }
      else
      {
        property.linkTo((PROPERTY)this);
      }
    }
    else
    {
      property = property.addProperty(key, value);
    }
    // On retourne la première propriété créée dans l'arbre
    return property;
  }

  /**
   * Cette méthode doit être définie afin de créer le bon type de propriété gérée
   * @param key Clé de la propriété à créer
   * @param value Valeur de la propriété à créer
   * @return La propriété créée du bon type
   * @throws UserException Si la création de la propriété échoue
   */
  protected abstract PROPERTY createProperty(String key, String value) throws UserException;

  /**
   * Getter du nombre total de sous-propriétés de la propriété courante, cad inclus
   * récursivement le nombre de sous-propriétés de chaque sous-propriétés
   * @return Le nombre total de sous-propriétés de la propriété courante
   */
  public int getTotalPropertyNb()
  {
    int totalNb = this.getPropertyNb();
    for(PROPERTY property : this.getPropertyMapInternal().values())
    {
      totalNb += property.getTotalPropertyNb();
    }
    return totalNb;
  }
  /**
   * Getter du nombre de sous-propriétés de la propriété courante
   * @return Le nombre de sous-propriétés de la propriété courante
   */
  public int getPropertyNb()
  {
    return this.getPropertyMapInternal().size();
  }
  /**
   * Getter des sous-propriétés de la propriété courante
   * @return Les sous-propriétés de la propriété courante
   */
  public Bid4WinCollection<PROPERTY> getProperties()
  {
    return this.getPropertyMapInternal().valuesProtected();
  }

  /**
   *
   * TODO A COMMENTER
   * @param map TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public boolean isPropertyMapInternal(Map<?, ?> map)
  {
    return this.getPropertyMapInternal() == map;
  }
  /**
   * Getter de la map interne de sous-propriétés de la propriété courante
   * @return La map interne de sous-propriétés de la propriété courante
   */
  private Bid4WinMap<String, PROPERTY> getPropertyMapInternal()
  {
    return this.propertyMap;
  }
  /**
   * Setter de la map interne de sous-propriétés de la propriété courante
   * @param propertyMap Map interne de sous-propriétés de la propriété courante
   * à positionner
   * @throws ProtectionException Si la protection de la map en paramètre échoue
   */
  private void setPropertyMapInternal(Bid4WinMap<String, PROPERTY> propertyMap)
          throws ProtectionException
  {
    propertyMap.protect(this.getProtection());
    this.propertyMap = propertyMap;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map persistante de sous-propriétés de la propriété courante
   * @return La map persistante de sous-propriétés de la propriété courante
   */
  @SuppressWarnings("unused")
  private Map<String, PROPERTY> getPropertyMapDatabase()
  {
    return this.getPropertyMapInternal().getInternal();
  }
  /**
   * Setter de la map persistante de sous-propriétés de la propriété courante
   * @param propertyMap Map persistante de sous-propriétés de la propriété courante
   * à positionner
   */
  @SuppressWarnings(value = "unused")
  private void setPropertyMapDatabase(Map<String, PROPERTY> propertyMap)
  {
    this.setPropertyMapInternal(new Bid4WinMap<String, PROPERTY>(propertyMap, true));
  }
}
