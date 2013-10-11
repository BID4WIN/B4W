//package trash.commons;
//
//import java.util.Enumeration;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.exception.UserException.MessageRef;
//import com.bid4win.commons.manager.property.PropertyAbstractManagerInternal;
//import com.bid4win.commons.persistence.entity.property.UtilProperty;
//import com.bid4win.persistence.dao.locale.I18nDao;
//import com.bid4win.persistence.dao.locale.I18nRootDao;
//import com.bid4win.persistence.entity.locale.I18n;
//import com.bid4win.persistence.entity.locale.I18nRoot;
//import com.bid4win.persistence.entity.locale.Language;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Component("I18nManagerInternal")
//@Scope("singleton")
//public class I18nManagerInternal
//       extends PropertyAbstractManagerInternal<I18n, I18nRoot, I18nDao, I18nRootDao>
//{
//  /** Référence du DAO des propriétés d'internationalisation */
//  @Autowired
//  @Qualifier("I18nDao")
//  private I18nDao dao = null;
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle langue à la racine des propriétés
//   * d'internationalisation qui sera alors complétée d'une copie des propriétés
//   * de la langue par défaut
//   * @param code Code de la langue à ajouter à la racine des propriétés d'internationalisation
//   * @return La propriété d'internationalisation représentant la langue ajoutée
//   * et complétée d'une copie des propriétés de la langue par défaut
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la langue est manquante, inconnue ou déjà référencée
//   */
//  public I18n addLanguage(String code)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Récupère la langue à ajouter
//    Language language = Language.getLanguage(code);
//    // Récupère la racine des propriété d'internationalisation
//    I18nRoot root = this.getRoot();
//    // Ajoute la langue
//    I18n i18nLanguage = root.addI18n(language);
//    // Il faut la compléter des propriétés de la langue par défaut
//    I18n defaultLanguage = root.getI18n(Language.DEFAULT);
//    // Ajoute une copie de chaque propriété de la langue par défaut sur la nouvelle
//    for(I18n i18nProperty : defaultLanguage.getPropertySet())
//    {
//      new I18n(i18nProperty, i18nLanguage);
//    }
//    // Ajoute la langue et la retourne
//    return this.getDao().add(i18nLanguage);
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une nouvelle entrée d'internationalisation
//   * qui sera alors copiée dans toutes les langues
//   * @param key Clé de la propriété d'internationalisation à ajouter à toutes les
//   * langues
//   * @return La liste des premières propriétés ajoutées (s'il manquait des propriétés
//   * intermédiaires) sur chaque langue
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la clé est invalide ou déjà référencée
//   */
//  public Bid4WinSet<I18n> addI18n(String key)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Récupère la propriété racine
//    I18nRoot root = this.getRoot();
//    // Il faut ajouter la propriété dans toutes langues
//    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
//    for(I18n language : root.getPropertySet())
//    {
//      // Ajoute la propriété à la langue
//      I18n i18n = language.addProperty(key, "TODO");
//      // Ajoute la base de la propriété ajoutée en base et à la liste de résultat
//      result.add(this.getDao().add(i18n));
//    }
//    // Retourne les propriétés ajoutées
//    return result;
//  }
//
//  /**
//   * Cette méthode permet de modifier la clé d'une propriété d'internationalisation
//   * dans toutes les langues
//   * @param oldKey Ancienne clé de la propriété d'internationalisation à modifier
//   * @param newKey Nouvelle clé de la propriété d'internationalisation à modifier
//   * @return La liste des propriétés de chaque langue une fois leur clé modifiée
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un problème intervient lors de la manipulation
//   * des propriétés
//   * @throws UserException Si la propriété à modifier n'est pas référencée ou la
//   * nouvelle clé déjà référencée ou invalide
//   */
//  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Valide la nouvelle clé
//    UtilProperty.checkFullKey(newKey);
//    // Récupère la propriété racine
//    I18nRoot root = this.getRoot();
//    // Il faut modifier la clé de la propriété dans toutes langues
//    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
//    for(I18n language : root.getPropertySet())
//    {
//      // Récupère la propriété à modifier
//      I18n i18n = language.getProperty(oldKey);
//      // Récupère la base de la nouvelle clé
//      String newKeyBase = UtilProperty.removeLastKey(newKey);
//      this.getDao().update(i18n.unlinkFromProperty());
//      // Modifie la clé de la propriété
//      i18n.defineKey(UtilProperty.getLastKey(newKey));
//      // On crée le lien avec la nouvelle base
//      if(newKeyBase.equals(""))
//      {
//        // La propriété est maintenant liée à la langue
//        i18n.linkTo(language);
//        this.getDao().update(language);
//      }
//      else
//      {
//        I18n base = language.getProperty(newKeyBase);
//        // La nouvelle base n'existe pas encore, il faut donc la créer
//        if(base == null)
//        {
//          base = this.getDao().add(language.addProperty(newKeyBase, ""));
//        }
//        // La propriété est maintenant liée à sa nouvelle base
//        i18n.linkTo(base);
//        this.getDao().update(base);
//      }
//      // Modifie la propriété et l'ajoute à la liste de résultat
//      result.add(this.getDao().update(i18n));
//    }
//    // Retourne les propriétés modifiées
//    return result;
//  }
//
//  /**
//   * Cette méthode permet de retirer une entrée d'internationalisation vide de
//   * toutes les langues
//   * @param key Clé de la propriété d'internationalisation à retirer de toutes les
//   * langues
//   * @return La liste des propriétés retirées de chaque langue
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propriété n'est pas référencée ou non vide
//   */
//  public Bid4WinSet<I18n> removeI18n(String key) throws PersistenceException, UserException
//  {
//    // Récupère la propriété racine
//    I18nRoot root = this.getRoot();
//    // Il faut retirer la propriété de toutes langues
//    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
//    for(I18n language : root.getPropertySet())
//    {
//      // Récupère la propriété à supprimer
//      I18n i18n = UtilObject.checkNotNull("i18n", language.getProperty(key),
//                                          MessageRef.ERROR_I18N_UNKNOWN);
//      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
//                               MessageRef.ERROR_I18N_NOT_EMPTY);
//      // Retire la propriété et l'ajoute à la liste de résultat
//      result.add(this.getDao().remove(i18n));
//    }
//    // Retourne les propriétés retirées
//    return result;
//  }
//
//  /**
//   * Cette méthode permet d'importer toutes les propriétés d'internationalisation
//   * en paramètre, celles déjà existantes voyant leur valeur mise à jour
//   * @param properties {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.abstraction.PropertyAbstractManagerInternal#importProperties(java.util.Properties)
//   */
//  @Override
//  public void importProperties(Properties properties)
//         throws PersistenceException, UserException, ModelArgumentException
//  {
//    // Ajoute ou met à jour chaque propriété
//    for(Enumeration<Object> enumeration = properties.keys() ; enumeration.hasMoreElements() ; )
//    {
//      String fullKey = (String)enumeration.nextElement();
//      String key = UtilProperty.removeFirstKey(fullKey);
//      String value = properties.getProperty(fullKey);
//      // Récupère la racine des propriétés
//      I18nRoot root = this.getRoot();
//      I18n i18n = root.getProperty(fullKey);
//      // Il faut ajouter la propriété
//      if(i18n == null)
//      {
//        // C'est une nouvelle langue
//        if(key.isEmpty())
//        {
//          this.addLanguage(fullKey);
//        }
//        // C'est une nouvelle propriété d'internationalisation
//        else
//        {
//          this.addI18n(key);
//        }
//      }
//      // Il faut mettre à jour la propriété spécifique
//      this.updateProperty(fullKey, value);
//    }
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.property.abstraction.PropertyAbstractManagerInternal#getUnknownPropertyMessage()
//   */
//  @Override
//  protected MessageRef getUnknownPropertyMessage()
//  {
//    return MessageRef.ERROR_I18N_UNKNOWN;
//  }
//
//  /**
//   * Getter de la référence du DAO des propriétés d'internationalisation
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManagerInternal#getDao()
//   */
//  @Override
//  protected I18nDao getDao()
//  {
//    return this.dao;
//  }
//}
