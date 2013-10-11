package com.bid4win.commons.persistence.entity.property;

import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
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
  @Transient
  private Bid4WinMap<String, PROPERTY> propertyMap = new Bid4WinMap<String, PROPERTY>();

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
    for(PROPERTY property : this.getPropertySet())
    {
      properties.putAll(property.toProperties(""));
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
   * Permet de r�cup�rer la map de sous-propri�t�s de l'entit� si elle correspondant
   * � la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type map � remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Map<?, ? extends Bid4WinEntity<?, ?>> getRelationMap(Bid4WinRelation relation)
  {
    if(relation.equals(PropertyBase_Relations.RELATION_PROPERTY_MAP))
    {
      return this.getPropertyMapInternal();
    }
    return super.getRelationMap(relation);
  }
  /**
   *
   * TODO A COMMENTER
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
   *
   * TODO A COMMENTER
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
    PROPERTY property = this.getPropertyMap().get(UtilProperty.getFirstKey(key));
    // On doit continuer � descendre l'arbre de sous-propri�t�s
    if(property != null && !UtilProperty.removeFirstKey(key).equals(""))
    {
      property = property.getProperty(UtilProperty.removeFirstKey(key));
    }
    // La cl� en argument est incompl�te
    else if(UtilProperty.getLastKey(key).equals(""))
    {
      property = null;
    }
    return property;
  }
  /**
   * Cette m�thode doit permettre d'ajouter une propri�t� � la propri�t� courante
   * @param property Propri�t� � ajouter � la propri�t� courante
   * @throws ProtectionException Si la propri�t� courante ou celle en argument est
   * prot�g�e
   * @throws ModelArgumentException Si la propri�t� en argument est nulle ou d�j�
   * li�e
   * @throws UserException Si la propri�t� en argument est d�j� r�f�renc�e par la
   * propri�t� courante
   */
  public abstract void addProperty(PROPERTY property)
         throws ProtectionException, ModelArgumentException, UserException;
  /**
   * Cette m�thode doit permettre d'ajouter un set de propri�t�s � la propri�t�
   * courante
   * @param propertySet Set de propri�t�s � ajouter � la propri�t� courante
   * @throws ProtectionException Si la propri�t� courante ou une de celles en argument
   * est prot�g�e
   * @throws ModelArgumentException Si une des propri�t�s en argument est nulle
   * ou d�j� li�e
   * @throws UserException Si une des propri�t�s en argument est d�j� r�f�renc�e
   * par la propri�t� courante
   */
  public void addPropertySet(Bid4WinSet<PROPERTY> propertySet)
         throws ProtectionException, ModelArgumentException, UserException
  {
    // V�rifie la protection de la propri�t� courante
    this.checkProtection();
    UtilObject.checkNotNull("propertySet", propertySet);
    for(PROPERTY property : propertySet)
    {
      this.addProperty(property);
    }
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
    if(key.equals("") || property == null)
    {
      // On cr�e la derni�re propri�t� de l'arbre
      if(key.equals(""))
      {
        property = this.createProperty(firstKey, value);
      }
      // On cr�e � partir de la propri�t� interm�diaire
      else
      {
        property = this.createProperty(firstKey, null);
        property.addProperty(key, value);
      }
      // On ajoute la propri�t� � la propri�t� courante
      this.addProperty(property);
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
    for(Entry<String, PROPERTY> entry : this.getPropertyMapInternal().entrySet())
    {
      totalNb += entry.getValue().getTotalPropertyNb();
    }
    return totalNb;
  }
  /**
   * Getter du nombre de sous-propri�t�s de la propri�t� courante
   * @return Le nombre de sous-propri�t�s de la propri�t� courante
   */
  public int getPropertyNb()
  {
    return this.getPropertyMap().size();
  }
  /**
   * Getter du set de sous-propri�t�s de la propri�t� courante
   * @return Le set de sous-propri�t�s de la propri�t� courante
   */
  public Bid4WinSet<PROPERTY> getPropertySet()
  {
    return new Bid4WinSet<PROPERTY>(this.getPropertyMap().values());
  }

  /**
   * Getter de la map de sous-propri�t�s de la propri�t� courante
   * @return La map de sous-propri�t�s de la propri�t� courante
   */
  private Bid4WinMap<String, PROPERTY> getPropertyMap()
  {
    return this.propertyMap;
  }
  /**
   * Setter de la map de sous-propri�t�s de la propri�t� courante
   * @param propertyMap Map de sous-propri�t�s de la propri�t� courante � positionner
   */
  private void setPropertyMap(Bid4WinMap<String, PROPERTY> propertyMap)
  {
    this.propertyMap = propertyMap;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map interne de sous-propri�t�s de la propri�t� courante
   * @return La map interne de sous-propri�t�s de la propri�t� courante
   */
  protected Map<String, PROPERTY> getPropertyMapInternal()
  {
    return this.getPropertyMap().getInternal();
  }
  /**
   * Setter de la map interne de sous-propri�t�s de la propri�t� courante
   * @param propertyMap Map interne de sous-propri�t�s de la propri�t� courante
   * � positionner
   */
  @SuppressWarnings(value = "unused")
  private void setPropertyMapInternal(Map<String, PROPERTY> propertyMap)
  {
    this.setPropertyMap(new Bid4WinMap<String, PROPERTY>(propertyMap, true));
  }
}
