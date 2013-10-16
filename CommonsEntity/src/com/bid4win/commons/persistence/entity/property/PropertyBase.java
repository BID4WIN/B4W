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
 * Cette entit� repr�sente la base des propri�t�s<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <PROPERTY_ROOT> Doit d�finir la classe des propri�t�s racines g�r�es<BR>
 * @param <PROPERTY> Doit d�finir la classe des propri�t�s g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class PropertyBase<CLASS extends PropertyBase<CLASS, PROPERTY_ROOT, PROPERTY>,
                                   PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                   PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>>
       extends Bid4WinEntity<CLASS, Integer>
{
  /** Map de sous-propri�t�s de la propri�t� */
  @Transient private Bid4WinMap<String, PROPERTY> propertyMap;
  {
    this.setPropertyMapInternal(new Bid4WinMap<String, PROPERTY>());
  }

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected PropertyBase()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de la propri�t�
   */
  protected PropertyBase(int id)
  {
    super(id);
  }

  /**
   * Cette fonction permet d'exporter la propri�t� courante ainsi que toutes ses
   * sous-propri�t�s
   * @return L'exportation de la propri�t� courante avec toutes ses sous-propri�t�s
   */
  public Properties toProperties()
  {
    Properties properties = new Properties();
    // Ajoute les sous-propri�t�s
    for(PROPERTY property : this.getProperties())
    {
      properties.putAll(property.toProperties(UtilString.EMPTY));
    }
    return properties;
  }

  /**
   * Ajoute � la liste des noeuds de relations de l'entit� le lien vers sa map de
   * sous-propri�t�s
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
   * Permet de r�cup�rer la map de sous-propri�t�s de l'entit� si elle correspond
   * � la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type map � remonter
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
   * Permet de r�cup�rer la cl� associ�e � la propri�t� en param�tre pour son classement
   * dans la map si elle correspond � la relation en argument
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
   * Permet de d�finir la base des r�f�rences de messages li�es aux propri�t�s
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
   * Getter de la sous-propri�t� associ�e dont la cl� est pass�e en param�tre
   * @param key Cl� de la sous-propri�t� associ�e � r�cup�rer
   * @return La sous-propri�t� associ�e dont la cl� correspond � celle en param�tre
   * ou null si la sous-propri�t� n'a pas pu �tre trouv�e
   */
  public PROPERTY getProperty(String key)
  {
    PROPERTY property = this.getPropertyMapInternal().get(UtilProperty.getFirstKey(key));
    // On doit continuer � descendre l'arbre de sous-propri�t�s
    if(property != null && !UtilProperty.removeFirstKey(key).equals(UtilString.EMPTY))
    {
      property = property.getProperty(UtilProperty.removeFirstKey(key));
    }
    // La cl� en argument est incompl�te
    else if(UtilProperty.getLastKey(key).equals(UtilString.EMPTY))
    {
      property = null;
    }
    return property;
  }
  /**
   * Ajoute une sous-propri�t� � la propri�t� courante et cr�e les propri�t�s
   * interm�diaires si besoin
   * @param key Cl� de la sous-propri�t� � ajouter
   * @param value Valeur de la sous-propri�t� � ajouter
   * @return La base de l'arbre de sous-propri�t�s ajout� � la propri�t� courante
   * @throws ProtectionException Si la propri�t� courante est prot�g�e
   * @throws ModelArgumentException Si on positionne une propri�t� nulle
   * @throws UserException Si la cl� est invalide ou si on positionne une propri�t�
   * d�j� r�f�renc�e
   */
  @SuppressWarnings("unchecked")
  public PROPERTY addProperty(String key, String value)
         throws ProtectionException, ModelArgumentException, UserException
  {
    // V�rifie la validit� de la cl�
    UtilProperty.checkFullKey(key, this.getMessageRef());
    // R�cup�re le premier niveau de la cl�
    String firstKey = UtilProperty.getFirstKey(key);
    key = UtilProperty.removeFirstKey(key);
    // R�cup�re la propri�t� potentiellement existante
    PROPERTY property = this.getProperty(firstKey);
    // On est sur la derni�re cl� ou la propri�t� n'existe pas
    if(key.equals(UtilString.EMPTY) || property == null)
    {
      // On cr�e la derni�re propri�t� de l'arbre
      if(key.equals(UtilString.EMPTY))
      {
        property = this.createProperty(firstKey, value);
      }
      // On cr�e � partir de la propri�t� interm�diaire
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
    // On retourne la premi�re propri�t� cr��e dans l'arbre
    return property;
  }

  /**
   * Cette m�thode doit �tre d�finie afin de cr�er le bon type de propri�t� g�r�e
   * @param key Cl� de la propri�t� � cr�er
   * @param value Valeur de la propri�t� � cr�er
   * @return La propri�t� cr��e du bon type
   * @throws UserException Si la cr�ation de la propri�t� �choue
   */
  protected abstract PROPERTY createProperty(String key, String value) throws UserException;

  /**
   * Getter du nombre total de sous-propri�t�s de la propri�t� courante, cad inclus
   * r�cursivement le nombre de sous-propri�t�s de chaque sous-propri�t�s
   * @return Le nombre total de sous-propri�t�s de la propri�t� courante
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
   * Getter du nombre de sous-propri�t�s de la propri�t� courante
   * @return Le nombre de sous-propri�t�s de la propri�t� courante
   */
  public int getPropertyNb()
  {
    return this.getPropertyMapInternal().size();
  }
  /**
   * Getter des sous-propri�t�s de la propri�t� courante
   * @return Les sous-propri�t�s de la propri�t� courante
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
   * Getter de la map interne de sous-propri�t�s de la propri�t� courante
   * @return La map interne de sous-propri�t�s de la propri�t� courante
   */
  private Bid4WinMap<String, PROPERTY> getPropertyMapInternal()
  {
    return this.propertyMap;
  }
  /**
   * Setter de la map interne de sous-propri�t�s de la propri�t� courante
   * @param propertyMap Map interne de sous-propri�t�s de la propri�t� courante
   * � positionner
   * @throws ProtectionException Si la protection de la map en param�tre �choue
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
   * Getter de la map persistante de sous-propri�t�s de la propri�t� courante
   * @return La map persistante de sous-propri�t�s de la propri�t� courante
   */
  @SuppressWarnings("unused")
  private Map<String, PROPERTY> getPropertyMapDatabase()
  {
    return this.getPropertyMapInternal().getInternal();
  }
  /**
   * Setter de la map persistante de sous-propri�t�s de la propri�t� courante
   * @param propertyMap Map persistante de sous-propri�t�s de la propri�t� courante
   * � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setPropertyMapDatabase(Map<String, PROPERTY> propertyMap)
  {
    this.setPropertyMapInternal(new Bid4WinMap<String, PROPERTY>(propertyMap, true));
  }
}
