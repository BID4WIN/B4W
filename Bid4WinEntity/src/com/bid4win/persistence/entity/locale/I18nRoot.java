package com.bid4win.persistence.entity.locale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 * Cette classe d�fini la racine des propri�t�s d'internationalisation. Aucune
 * propri�t� autre qu'une propri�t� repr�sentant une langue 'vide' ne peut �tre
 * ajout�e directement � cette classe.<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class I18nRoot extends PropertyRootAbstract<I18nRoot, I18n>
{
  /** D�finition de l'identifiant unique de la racine des propri�t�s */
  public final static int UNIQUE_ID = 1;

  /**
   * Constructeur de la racine des propri�t�s d'internationalisation. L'identifiant
   * y est directement pr�cis� car celui-ci se doit d'�tre unique.La propri�t�
   * d'internationalisation repr�sentant la langue par d�faut sera automatiquement
   * ajout�e
   */
  public I18nRoot()
  {
    super(I18nRoot.UNIQUE_ID);
    try
    {
      this.addI18n(Language.DEFAULT);
    }
    catch(Bid4WinException ex)
    {
      // Ne peut pas arriver car les objets sont en construction
      ex.printStackTrace();
    }
  }

  /**
   * Cette m�thode permet de cr�er une propri�t� d'internationalisation
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected I18n createProperty(String key, String value) throws UserException
  {
    return new I18n(key, value);
  }

  /**
   * Cette m�thode permet d'ajouter la langue en argument � la racine des propri�t�s
   * d'internationalisation
   * @param language Langue � ajouter � la racine des propri�t�s d'internationalisation
   * @return La propri�t� d'internationalisation repr�sentant la langue ajout�e
   * @throws ProtectionException Si la racine des propri�t�s d'internationalisation
   * courante est prot�g�e
   * @throws ModelArgumentException Si on positionne une langue nulle
   * @throws UserException Si on positionne une langue d�j� r�f�renc�e
   */
  public I18n addI18n(Language language)
         throws ProtectionException, ModelArgumentException, UserException
  {
    UtilObject.checkNotNull("language", language);
    return this.addProperty(language.getCode(), language.getName());
  }
  /**
   * Cette m�thode permet de r�cup�rer la propri�t� d'internationalisation repr�sentant
   * la langue en argument
   * @param language Langue de la propri�t� d'internationalisation � r�cup�rer
   * @return La propri�t� d'internationalisation repr�sentant la langue en argument
   * @throws ModelArgumentException Si la langue en argument est nulle
   */
  public I18n getI18n(Language language) throws ModelArgumentException
  {
    UtilObject.checkNotNull("language", language);
    return this.getProperty(language.getCode());
  }
  /**
   * Cette fonction est red�finie afin de ne permettre que d'ajouter une propri�t�
   * d'internationalisation vide repr�sentant une langue
   * @param link {@inheritDoc}
   * @param backLink {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc} ou n'est pas vide ou ne correspondant pas
   * � une langue connue
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#addRelationMap(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  protected <KEY, VALUE> void add(Bid4WinMap<KEY, VALUE> map, KEY key,
                                  VALUE value, MessageRef base)
            throws ProtectionException, UserException
  {
    if(this.isPropertyMapInternal(map) && value != null)
    {
      I18n i18n = (I18n)value;
      // On doit obligatoirement ajouter une langue
      Language language = Language.getLanguage(i18n.getKey());
      // V�rifie que la propri�t� en argument est bien vide
      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
                               PropertyRef.I18N_NOT_EMPTY_ERROR);
      // D�fini la valeur de la langue si n�cessaire
      if(i18n.getValue().equals(""))
      {
        i18n.defineValue(language.getName());
      }
    }
    // Ajoute la propri�t�
    super.add(map, key, value, base);
  }

/*  protected void addRelationMap(Bid4WinRelation link,
                                Bid4WinRelation backLink,
                                Bid4WinEntity<?, ?> toBeAdded)
            throws ProtectionException, UserException
  {
    if(link.equals(PropertyBase_Relations.RELATION_PROPERTY_MAP) && toBeAdded != null)
    {
      I18n i18n = (I18n)toBeAdded;
      // On doit obligatoirement ajouter une langue
      Language language = Language.getLanguage(i18n.getKey());
      // V�rifie que la propri�t� en argument est bien vide
      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
                               PropertyRef.I18N_NOT_EMPTY_ERROR);
      // D�fini la valeur de la langue si n�cessaire
      if(i18n.getValue().equals(""))
      {
        i18n.defineValue(language.getName());
      }
    }
    // Ajoute la propri�t�
    super.addRelationMap(link, backLink, toBeAdded);
  }*/
}
