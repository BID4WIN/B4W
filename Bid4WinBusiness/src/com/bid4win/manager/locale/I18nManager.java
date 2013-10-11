package com.bid4win.manager.locale;

import java.util.Enumeration;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.manager.property.PropertyAbstractManager_;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.persistence.dao.locale.I18nDao;
import com.bid4win.persistence.dao.locale.I18nRootDao;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.I18nRoot;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Manager de gestion des propriétés d'internationalisation incluant leur gestion
 * métier. A la différence de la gestion générique des propriétés, les propriétés
 * d'internationalisation se basent sur un premier niveau représentant les langues.
 * Ainsi toute action "structurelle" (cad tout sauf la modification de la valeur
 * d'une propriété) est reportée sur chaque langue : ajout, suppression déplacement.
 * De même l'ajout d'une langue la remplie automatiquement d'un jeu de propriétés
 * identiques à celle déjà existantes sur la langue par défaut<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("I18nManager")
@Scope("singleton")
public class I18nManager extends PropertyAbstractManager_<I18n, I18nRoot, Account>
{
  /** Référence du DAO des racines des propriétés d'internationalisation */
  @Autowired
  @Qualifier("I18nRootDao")
  private I18nRootDao rootDao = null;
  /** Référence du DAO des propriétés d'internationalisation */
  @Autowired
  @Qualifier("I18nDao")
  private I18nDao i18nDao = null;

  /**
   * Cette méthode permet d'ajouter une nouvelle langue à la racine des propriétés
   * d'internationalisation qui sera alors complétée d'une copie des propriétés
   * de la langue par défaut
   * @param code Code de la langue à ajouter à la racine des propriétés d'internationalisation
   * @return La propriété d'internationalisation représentant la langue ajoutée
   * et complétée d'une copie des propriétés de la langue par défaut
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la langue est manquante, inconnue ou déjà référencée
   */
  public I18n addLanguage(String code)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Récupère la langue à ajouter
    Language language = Language.getLanguage(code);
    // Récupère la racine des propriété d'internationalisation
    I18nRoot root = this.getRoot();
    // Ajoute la langue
    I18n i18nLanguage = root.addI18n(language);
    // Il faut la compléter des propriétés de la langue par défaut
    I18n defaultLanguage = root.getI18n(Language.DEFAULT);
    // Ajoute une copie de chaque propriété de la langue par défaut sur la nouvelle
    for(I18n i18n : defaultLanguage.getPropertySet())
    {
      new I18n(i18n, i18nLanguage);
    }
    // Ajoute la langue et la retourne
    return this.getPropertyDao().add(i18nLanguage);
  }

  /**
   * Cette méthode permet d'ajouter une nouvelle entrée d'internationalisation
   * qui sera alors copiée dans toutes les langues
   * @param key Clé de la propriété d'internationalisation à ajouter à toutes les
   * langues
   * @return La liste des premières propriétés ajoutées (s'il manquait des propriétés
   * intermédiaires) sur chaque langue
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la clé est invalide ou déjà référencée
   */
  public Bid4WinSet<I18n> addI18n(String key)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Récupère la propriété racine
    I18nRoot root = this.getRoot();
    // Il faut ajouter la propriété dans toutes langues
    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
    for(I18n language : root.getPropertySet())
    {
      // Ajoute la propriété à la langue
      I18n i18n = language.addProperty(key);
      // Ajoute la base de la propriété ajoutée en base et à la liste de résultat
      result.add(this.getPropertyDao().add(i18n));
    }
    // Retourne les propriétés ajoutées
    return result;
  }

  /**
   * Cette méthode permet de modifier la clé d'une propriété d'internationalisation
   * dans toutes les langues
   * @param oldKey Ancienne clé de la propriété d'internationalisation à modifier
   * @param newKey Nouvelle clé de la propriété d'internationalisation à modifier
   * @return La liste des propriétés de chaque langue une fois leur clé modifiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
   * des propriétés
   * @throws UserException Si la propriété à modifier n'est pas référencée ou la
   * nouvelle clé déjà référencée ou invalide
   */
  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
         throws PersistenceException, ModelArgumentException, UserException
  {
    // Récupère la propriété racine
    I18nRoot root = this.getRoot();
    // Valide la nouvelle clé
    UtilProperty.checkFullKey(newKey, root.getMessageRefBase());
    // Il faut modifier la clé de la propriété dans toutes langues
    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
    for(I18n language : root.getPropertySet())
    {
      // Récupère la propriété à modifier
      I18n i18n = language.getProperty(oldKey);
      // Récupère la base de la nouvelle clé
      String newKeyBase = UtilProperty.removeLastKey(newKey);
      this.getPropertyDao().update(i18n.unlinkFromProperty());
      // Modifie la clé de la propriété
      i18n.defineKey(UtilProperty.getLastKey(newKey));
      // On crée le lien avec la nouvelle base
      if(newKeyBase.equals(""))
      {
        // La propriété est maintenant liée à la langue
        i18n.linkTo(language);
        this.getPropertyDao().update(language);
      }
      else
      {
        I18n base = language.getProperty(newKeyBase);
        // La nouvelle base n'existe pas encore, il faut donc la créer
        if(base == null)
        {
          base = this.getPropertyDao().add(language.addProperty(newKeyBase, ""));
        }
        // La propriété est maintenant liée à sa nouvelle base
        i18n.linkTo(base);
        this.getPropertyDao().update(base);
      }
      // Modifie la propriété et l'ajoute à la liste de résultat
      result.add(this.getPropertyDao().update(i18n));
    }
    // Retourne les propriétés modifiées
    return result;
  }

  /**
   * Cette méthode permet de retirer une entrée d'internationalisation vide de
   * toutes les langues
   * @param key Clé de la propriété d'internationalisation à retirer de toutes les
   * langues
   * @return La liste des propriétés retirées de chaque langue
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété n'est pas référencée ou vide
   */
  public Bid4WinSet<I18n> removeI18n(String key) throws PersistenceException, UserException
  {
    // Récupère la propriété racine
    I18nRoot root = this.getRoot();
    // Il faut retirer la propriété de toutes langues
    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
    for(I18n language : root.getPropertySet())
    {
      // Récupère la propriété à supprimer
      I18n i18n = UtilObject.checkNotNull("i18n", language.getProperty(key),
                                          root.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
//TODO bon message
      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
                               root.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
      // Retire la propriété et l'ajoute à la liste de résultat
      result.add(this.getPropertyDao().remove(i18n));
    }
    // Retourne les propriétés retirées
    return result;
  }


  /**
   * Cette méthode permet d'importer toutes les propriétés d'internationalisation
   * en paramètre. Les propriétés déjà existantes verront leur valeur mise à jour
   * @param properties {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#importProperties(java.util.Properties)
   */
  @Override
  public void importProperties(Properties properties)
         throws PersistenceException, UserException, ModelArgumentException
  {
    // Ajoute ou met à jour chaque propriété
    for(Enumeration<Object> enumeration = properties.keys() ; enumeration.hasMoreElements() ; )
    {
      String fullKey = (String)enumeration.nextElement();
      Language language = Language.getLanguage(UtilProperty.getFirstKey(fullKey));
      String key = UtilProperty.removeFirstKey(fullKey);
      String value = properties.getProperty(fullKey);
      // Récupère la racine des propriétés
      I18nRoot root = this.getRoot();
      // Vérifie la présence de la langue
      I18n i18n = root.getI18n(language);
      // Il faut ajouter la langue
      if(i18n == null)
      {
        this.addLanguage(language.getCode());
      }
      // Récupère la racine des propriétés
      root = this.getRoot();
      // Vérifie la présence de la propriété
      i18n = root.getProperty(fullKey);
      // Il faut ajouter la propriété
      if(i18n == null)
      {
        this.addI18n(key);
      }
      // Il faut mettre à jour la propriété spécifique
      this.updateProperty(fullKey, value);
    }
  }

  /**
   * Getter de la référence du DAO des racines des propriétés d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#getRootDao()
   */
  @Override
  protected I18nRootDao getRootDao()
  {
    return this.rootDao;
  }
  /**
   * Getter de la référence du DAO des propriétés d'internationalisation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.property.PropertyAbstractManager_#getPropertyDao()
   */
  @Override
  protected I18nDao getPropertyDao()
  {
    return this.i18nDao;
  }
}
