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
// * @author Emeric Fill�tre
// */
//@Component("I18nManagerInternal")
//@Scope("singleton")
//public class I18nManagerInternal
//       extends PropertyAbstractManagerInternal<I18n, I18nRoot, I18nDao, I18nRootDao>
//{
//  /** R�f�rence du DAO des propri�t�s d'internationalisation */
//  @Autowired
//  @Qualifier("I18nDao")
//  private I18nDao dao = null;
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle langue � la racine des propri�t�s
//   * d'internationalisation qui sera alors compl�t�e d'une copie des propri�t�s
//   * de la langue par d�faut
//   * @param code Code de la langue � ajouter � la racine des propri�t�s d'internationalisation
//   * @return La propri�t� d'internationalisation repr�sentant la langue ajout�e
//   * et compl�t�e d'une copie des propri�t�s de la langue par d�faut
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la langue est manquante, inconnue ou d�j� r�f�renc�e
//   */
//  public I18n addLanguage(String code)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // R�cup�re la langue � ajouter
//    Language language = Language.getLanguage(code);
//    // R�cup�re la racine des propri�t� d'internationalisation
//    I18nRoot root = this.getRoot();
//    // Ajoute la langue
//    I18n i18nLanguage = root.addI18n(language);
//    // Il faut la compl�ter des propri�t�s de la langue par d�faut
//    I18n defaultLanguage = root.getI18n(Language.DEFAULT);
//    // Ajoute une copie de chaque propri�t� de la langue par d�faut sur la nouvelle
//    for(I18n i18nProperty : defaultLanguage.getPropertySet())
//    {
//      new I18n(i18nProperty, i18nLanguage);
//    }
//    // Ajoute la langue et la retourne
//    return this.getDao().add(i18nLanguage);
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter une nouvelle entr�e d'internationalisation
//   * qui sera alors copi�e dans toutes les langues
//   * @param key Cl� de la propri�t� d'internationalisation � ajouter � toutes les
//   * langues
//   * @return La liste des premi�res propri�t�s ajout�es (s'il manquait des propri�t�s
//   * interm�diaires) sur chaque langue
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la cl� est invalide ou d�j� r�f�renc�e
//   */
//  public Bid4WinSet<I18n> addI18n(String key)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // R�cup�re la propri�t� racine
//    I18nRoot root = this.getRoot();
//    // Il faut ajouter la propri�t� dans toutes langues
//    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
//    for(I18n language : root.getPropertySet())
//    {
//      // Ajoute la propri�t� � la langue
//      I18n i18n = language.addProperty(key, "TODO");
//      // Ajoute la base de la propri�t� ajout�e en base et � la liste de r�sultat
//      result.add(this.getDao().add(i18n));
//    }
//    // Retourne les propri�t�s ajout�es
//    return result;
//  }
//
//  /**
//   * Cette m�thode permet de modifier la cl� d'une propri�t� d'internationalisation
//   * dans toutes les langues
//   * @param oldKey Ancienne cl� de la propri�t� d'internationalisation � modifier
//   * @param newKey Nouvelle cl� de la propri�t� d'internationalisation � modifier
//   * @return La liste des propri�t�s de chaque langue une fois leur cl� modifi�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si la propri�t� � modifier n'est pas r�f�renc�e ou la
//   * nouvelle cl� d�j� r�f�renc�e ou invalide
//   */
//  public Bid4WinSet<I18n> updateKey(String oldKey, String newKey)
//         throws PersistenceException, ModelArgumentException, UserException
//  {
//    // Valide la nouvelle cl�
//    UtilProperty.checkFullKey(newKey);
//    // R�cup�re la propri�t� racine
//    I18nRoot root = this.getRoot();
//    // Il faut modifier la cl� de la propri�t� dans toutes langues
//    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
//    for(I18n language : root.getPropertySet())
//    {
//      // R�cup�re la propri�t� � modifier
//      I18n i18n = language.getProperty(oldKey);
//      // R�cup�re la base de la nouvelle cl�
//      String newKeyBase = UtilProperty.removeLastKey(newKey);
//      this.getDao().update(i18n.unlinkFromProperty());
//      // Modifie la cl� de la propri�t�
//      i18n.defineKey(UtilProperty.getLastKey(newKey));
//      // On cr�e le lien avec la nouvelle base
//      if(newKeyBase.equals(""))
//      {
//        // La propri�t� est maintenant li�e � la langue
//        i18n.linkTo(language);
//        this.getDao().update(language);
//      }
//      else
//      {
//        I18n base = language.getProperty(newKeyBase);
//        // La nouvelle base n'existe pas encore, il faut donc la cr�er
//        if(base == null)
//        {
//          base = this.getDao().add(language.addProperty(newKeyBase, ""));
//        }
//        // La propri�t� est maintenant li�e � sa nouvelle base
//        i18n.linkTo(base);
//        this.getDao().update(base);
//      }
//      // Modifie la propri�t� et l'ajoute � la liste de r�sultat
//      result.add(this.getDao().update(i18n));
//    }
//    // Retourne les propri�t�s modifi�es
//    return result;
//  }
//
//  /**
//   * Cette m�thode permet de retirer une entr�e d'internationalisation vide de
//   * toutes les langues
//   * @param key Cl� de la propri�t� d'internationalisation � retirer de toutes les
//   * langues
//   * @return La liste des propri�t�s retir�es de chaque langue
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� n'est pas r�f�renc�e ou non vide
//   */
//  public Bid4WinSet<I18n> removeI18n(String key) throws PersistenceException, UserException
//  {
//    // R�cup�re la propri�t� racine
//    I18nRoot root = this.getRoot();
//    // Il faut retirer la propri�t� de toutes langues
//    Bid4WinSet<I18n> result = new Bid4WinSet<I18n>(root.getPropertyNb());
//    for(I18n language : root.getPropertySet())
//    {
//      // R�cup�re la propri�t� � supprimer
//      I18n i18n = UtilObject.checkNotNull("i18n", language.getProperty(key),
//                                          MessageRef.ERROR_I18N_UNKNOWN);
//      UtilNumber.checkMaxValue("propertyNb", i18n.getPropertyNb(), 0, true,
//                               MessageRef.ERROR_I18N_NOT_EMPTY);
//      // Retire la propri�t� et l'ajoute � la liste de r�sultat
//      result.add(this.getDao().remove(i18n));
//    }
//    // Retourne les propri�t�s retir�es
//    return result;
//  }
//
//  /**
//   * Cette m�thode permet d'importer toutes les propri�t�s d'internationalisation
//   * en param�tre, celles d�j� existantes voyant leur valeur mise � jour
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
//    // Ajoute ou met � jour chaque propri�t�
//    for(Enumeration<Object> enumeration = properties.keys() ; enumeration.hasMoreElements() ; )
//    {
//      String fullKey = (String)enumeration.nextElement();
//      String key = UtilProperty.removeFirstKey(fullKey);
//      String value = properties.getProperty(fullKey);
//      // R�cup�re la racine des propri�t�s
//      I18nRoot root = this.getRoot();
//      I18n i18n = root.getProperty(fullKey);
//      // Il faut ajouter la propri�t�
//      if(i18n == null)
//      {
//        // C'est une nouvelle langue
//        if(key.isEmpty())
//        {
//          this.addLanguage(fullKey);
//        }
//        // C'est une nouvelle propri�t� d'internationalisation
//        else
//        {
//          this.addI18n(key);
//        }
//      }
//      // Il faut mettre � jour la propri�t� sp�cifique
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
//   * Getter de la r�f�rence du DAO des propri�t�s d'internationalisation
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.manager.Bid4WinManagerInternal#getDao()
//   */
//  @Override
//  protected I18nDao getDao()
//  {
//    return this.dao;
//  }
//}
