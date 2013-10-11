package com.bid4win.communication.action.property;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.PropertyRef;
import com.bid4win.communication.action.json.JSONAction;
import com.bid4win.communication.json.factory.property.JSONPropertyFactory;
import com.bid4win.communication.json.model.JSONMessage.Type;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.service.property.PropertyService;

/**
 * Classe d'actions relatives aux propriétés.
 *
 * @author Maxime Ollagnier
 */
public class PropertyAction extends JSONAction
{
  /** Serial */
  private static final long serialVersionUID = -338513292749735998L;

  /** Référence du PropertyService */
  @Autowired
  @Qualifier("PropertyService")
  private PropertyService propertyService = null;

  /**
   * Si le paramètre "searchString" est donné les propriétés contenant cette
   * chaîne de caractères dans leur clé, leur valeur ou dans l'une de leurs
   * propriétés enfants sont renvoyées. Sinon, toutes propriétés sont renvoyées.
   * Échoue et renvoi un message d'erreur si une erreur technique se produit.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("GetPropertyAction")
  public String GetPropertyAction() throws UserException, Throwable
  {
    String searchString = UtilString.trimNotNull(this.findParameter("searchString"));
    Bid4WinSet<Property> propertySet = null;
    if(searchString.equals(""))
    {
      propertySet = this.getPropertyService().getPropertySet();
    }
    else
    {
      propertySet = this.getPropertyService().getFilteredPropertySet(searchString);
    }
    this.putJSONObject(JSONPropertyFactory.getInstance().get(propertySet));
    this.setSuccess(true);
    return SUCCESS;
  }

  /**
   * Crée une nouvelle propriété si la clef en paramètre ne référence aucune
   * propriété existante. Sinon la propriété existante est modifiée avec la
   * valeur en paramètre.
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("SavePropertyAction")
  public String SavePropertyAction() throws UserException, Throwable
  {
    String fullKey = JSONPropertyFactory.getInstance().getKey(this, Type.WARN);
    String value = JSONPropertyFactory.getInstance().getValue(this, Type.WARN);
    if(!this.hasWarningMessage())
    {
      value = UtilString.decodeUTF8Parameter(value);
      if(this.getPropertyService().hasProperty(fullKey))
      {
        this.getPropertyService().updateProperty(fullKey, value);
      }
      else
      {
        this.getPropertyService().addProperty(fullKey, UtilString.text2HTML(value));
      }
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Supprime la propriété référencée par le paramètre "id".
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("RemovePropertyAction")
  public String RemovePropertyAction() throws UserException, Throwable
  {
    String key = this.getParameter("id", PropertyRef.PROPERTY_KEY_MISSING_ERROR, Type.WARN);
    if(!this.hasWarningMessage())
    {
      this.getPropertyService().removeProperty(key);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Copie la propriété référencée par le paramètre "oldId" dans une nouvelle
   * propriété référencée par le paramètre "newId".
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("CopyPropertyAction")
  public String CopyPropertyAction() throws UserException, Throwable
  {
    String oldFullKey = this.getParameter("oldId", PropertyRef.PROPERTY_KEY_OLD_MISSING_ERROR, Type.WARN);
    String newFullKey = this.getParameter("newId", PropertyRef.PROPERTY_KEY_NEW_MISSING_ERROR, Type.WARN);
    if(!this.hasWarningMessage())
    {
      String finalNewFullKey = newFullKey;
      while(this.getPropertyService().hasProperty(finalNewFullKey))
      {
        finalNewFullKey = newFullKey + PropertyAction.getDuplicateSuffix();
      }
      this.getPropertyService().copyProperty(oldFullKey, finalNewFullKey);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Change la clef de la propriété référencée par le paramètre "oldId" en une
   * nouvelle clef égale au paramètre "newId".
   *
   * @return SUCCESS
   * @throws UserException si une erreur utilisateur se produit.
   * @throws Throwable si une erreur technique se produit.
   */
  @Action("MovePropertyAction")
  public String MovePropertyAction() throws UserException, Throwable
  {
    String oldFullKey = this.getParameter("oldId", PropertyRef.PROPERTY_KEY_OLD_MISSING_ERROR, Type.WARN);
    String newFullKey = this.getParameter("newId", PropertyRef.PROPERTY_KEY_NEW_MISSING_ERROR, Type.WARN);
    if(!this.hasWarningMessage())
    {
      String finalNewFullKey = newFullKey;
      while(this.getPropertyService().hasProperty(finalNewFullKey))
      {
        finalNewFullKey = newFullKey + PropertyAction.getDuplicateSuffix();
      }
      this.getPropertyService().updateKey(oldFullKey, finalNewFullKey);
      this.setSuccess(true);
    }
    return SUCCESS;
  }

  /**
   * Renvoi le PropertyService
   *
   * @return Le PropertyService
   */
  protected PropertyService getPropertyService()
  {
    return this.propertyService;
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
}
