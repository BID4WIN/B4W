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
 * Cette classe défini la racine des propriétés d'internationalisation. Aucune
 * propriété autre qu'une propriété représentant une langue 'vide' ne peut être
 * ajoutée directement à cette classe.<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class I18nRoot extends PropertyRootAbstract<I18nRoot, I18n>
{
  /** Définition de l'identifiant unique de la racine des propriétés */
  public final static int UNIQUE_ID = 1;

  /**
   * Constructeur de la racine des propriétés d'internationalisation. L'identifiant
   * y est directement précisé car celui-ci se doit d'être unique.La propriété
   * d'internationalisation représentant la langue par défaut sera automatiquement
   * ajoutée
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
   * Cette méthode permet de créer une propriété d'internationalisation
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
   * Cette méthode permet d'ajouter la langue en argument à la racine des propriétés
   * d'internationalisation
   * @param language Langue à ajouter à la racine des propriétés d'internationalisation
   * @return La propriété d'internationalisation représentant la langue ajoutée
   * @throws ProtectionException Si la racine des propriétés d'internationalisation
   * courante est protégée
   * @throws ModelArgumentException Si on positionne une langue nulle
   * @throws UserException Si on positionne une langue déjà référencée
   */
  public I18n addI18n(Language language)
         throws ProtectionException, ModelArgumentException, UserException
  {
    UtilObject.checkNotNull("language", language);
    return this.addProperty(language.getCode(), language.getName());
  }
  /**
   * Cette méthode permet de récupérer la propriété d'internationalisation représentant
   * la langue en argument
   * @param language Langue de la propriété d'internationalisation à récupérer
   * @return La propriété d'internationalisation représentant la langue en argument
   * @throws ModelArgumentException Si la langue en argument est nulle
   */
  public I18n getI18n(Language language) throws ModelArgumentException
  {
    UtilObject.checkNotNull("language", language);
    return this.getProperty(language.getCode());
  }
  /**
   * Cette fonction est redéfinie afin de ne permettre que d'ajouter une propriété
   * d'internationalisation vide représentant une langue
   * @param link {@inheritDoc}
   * @param backLink {@inheritDoc}
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc} ou n'est pas vide ou ne correspondant pas
   * à une langue connue
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
      // Vérifie que la propriété en argument est bien vide
      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
                               PropertyRef.I18N_NOT_EMPTY_ERROR);
      // Défini la valeur de la langue si nécessaire
      if(i18n.getValue().equals(""))
      {
        i18n.defineValue(language.getName());
      }
    }
    // Ajoute la propriété
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
      // Vérifie que la propriété en argument est bien vide
      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
                               PropertyRef.I18N_NOT_EMPTY_ERROR);
      // Défini la valeur de la langue si nécessaire
      if(i18n.getValue().equals(""))
      {
        i18n.defineValue(language.getName());
      }
    }
    // Ajoute la propriété
    super.addRelationMap(link, backLink, toBeAdded);
  }*/
}
