package com.bid4win.communication.action.i18n;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.LanguageRef;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.property.JSONPropertyFactory;
import com.bid4win.communication.json.model.JSONMessage.Type;
import com.bid4win.persistence.entity.locale.I18n;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.service.locale.I18nService;

/**
 * Classe d'actions relatives aux propriétés d'i18n.
 *
 * @author Maxime Ollagnier
 */
public class I18nAction extends JSONAction
{
  /** Serial */
  private static final long serialVersionUID = 2666694152189539698L;

  /** Référence du I18nService */
  @Autowired
  @Qualifier("I18nService")
  private I18nService i18nService = null;

  /**
   * Renvoi les i18n d'un seul langage ou de tous les langages si le paramètre
   * "admin" est trouvé et égal à "true"
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("GetI18nAction")
  public String GetI18nAction() throws UserException, Throwable
  {
    boolean admin = UtilString.trimNotNull(this.findParameter("admin")).equals("true");
    if(admin)
    {
      this.GetI18nAdmin();
    }
    else
    {
      this.GetI18nLanguage();
    }
    return SUCCESS;
  }

  /**
   * Toutes i18n d'un langage sont renvoyées. Le langage ainsi déterminé est
   * ajouté à la session et aux cookies Échoue et renvoi un message d'erreur si
   * une erreur utilisateur ou technique se produit, ou si les langages du
   * compte utilisateur et de la session sont tous deux trouvés et égaux entre
   * eux.
   *
   * @throws Throwable si une erreur technique se produit.
   */
  private void GetI18nLanguage() throws Throwable
  {
    try
    {
      // Le langage à utiliser est déterminé
      Language language = this.getSessionLanguage();
      // Le langage déterminé est ajouté dans la session
      this.getSession().setLanguage(language);
      // Le langage déterminé est ajouté aux cookies
      this.getCookieManager().putLanguage(language);
      // Récupération des i18n du langage concerné
      Bid4WinSet<I18n> i18nSet = this.getI18nService().getLanguage(language).getPropertySet();
      this.putJSONObject(JSONPropertyFactory.getInstance().get(i18nSet));
      this.setSuccess(true);
    }
    catch(UserException e)
    {
      this.putInfoMessage(e);
    }
  }

  /**
   * Si le paramètre "searchString" est donné, les i18n contenant cette chaîne
   * de caractères dans leur clé, leur valeur ou dans l'une de leurs i18n
   * enfants sont renvoyées. Sinon, toutes i18n sont renvoyées.
   *
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  private void GetI18nAdmin() throws UserException, Throwable
  {
    String searchString = UtilString.trimNotNull(this.findParameter("searchString"));
    Bid4WinSet<I18n> i18nSet = null;
    if(searchString.equals(""))
    {
      i18nSet = this.getI18nService().getPropertySet();
    }
    else
    {
      i18nSet = this.getI18nService().getFilteredPropertySet(searchString);
    }
    this.putJSONObject(JSONPropertyFactory.getInstance().get(i18nSet));
    this.setSuccess(true);
  }

  /**
   * Crée une nouvelle I18n DANS CHAQUE LANGUE si la clef en paramètre ne
   * référence aucune I18n existante.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("AddI18nAction")
  public String AddI18nAction() throws UserException, Throwable
  {
    String fullFullKey = JSONPropertyFactory.getInstance().getKey(this, Type.WARN);
    String genericFullKey = UtilProperty.removeFirstKey(fullFullKey);
    if(!this.hasWarningMessage())
    {
      this.getI18nService().addI18n(genericFullKey);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * L'i18n existante est modifiée avec la valeur en paramètre UNIQUEMENT DANS
   * SA LANGUE.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("SaveI18nAction")
  public String SaveI18nAction() throws UserException, Throwable
  {
    String fullFullKey = JSONPropertyFactory.getInstance().getKey(this, Type.WARN);
    String value = JSONPropertyFactory.getInstance().getValue(this, Type.WARN);
    if(!this.hasWarningMessage())
    {
      value = UtilString.decodeUTF8Parameter(value);
      this.getI18nService().updateProperty(fullFullKey, value);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Supprime l'I18n référencée par le paramètre "id" DANS TOUTES LES LANGUES.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("RemoveI18nAction")
  public String RemoveI18nAction() throws UserException, Throwable
  {
    String fullFullKey = this.getParameter("id", PropertyRef.PROPERTY_KEY_MISSING_ERROR, Type.WARN);
    String genericFullKey = UtilProperty.removeFirstKey(fullFullKey);
    if(!this.hasWarningMessage())
    {
      this.getI18nService().removeI18n(genericFullKey);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Modifie la clé de l'I18n DANS TOUTES LES LANGUES.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("MoveI18nAction")
  public String MoveI18nAction() throws UserException, Throwable
  {
    String fullFullOldKey = this.getParameter("oldId", PropertyRef.PROPERTY_KEY_OLD_MISSING_ERROR, Type.WARN);
    String fullFullNewKey = this.getParameter("newId", PropertyRef.PROPERTY_KEY_NEW_MISSING_ERROR, Type.WARN);
    String genericFullOldKey = UtilProperty.removeFirstKey(fullFullOldKey);
    String genericFullNewKey = UtilProperty.removeFirstKey(fullFullNewKey);
    if(!this.hasWarningMessage())
    {
      this.getI18nService().updateKey(genericFullOldKey, genericFullNewKey);
      this.setSuccess(true);
    }
    return SUCCESS;
  }
  /**
   * Ajoute une langue.
   *
   * @return SUCCESS
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("AddLanguageAction")
  public String AddLanguageAction() throws Throwable
  {
    String language = this.getParameter("language", LanguageRef.LANGUAGE_MISSING_ERROR, Type.WARN);
    if(!this.hasWarningMessage())
    {
      this.getI18nService().addLanguage(language);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Renvoi un suffix unique de 3 caractères numériques
   *
   * @return un suffix unique de 3 caractères numériques
   */
  protected static String getDuplicateSuffix()
  {
    String dateInMs = String.valueOf(new Bid4WinDate().getTime());
    return "_" + dateInMs.substring(dateInMs.length() - 3);
  }

  /**
   * Renvoi le I18nService
   *
   * @return Le I18nService
   */
  protected I18nService getI18nService()
  {
    return this.i18nService;
  }
}
