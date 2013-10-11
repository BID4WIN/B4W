package com.bid4win.persistence.entity.account.preference;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 * Cette classe représente la racine des propriétés de préférence d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PreferenceRoot extends PropertyRootAbstract<PreferenceRoot, Preference>
{
  /**
   * Constructeur de la racine des propriétés. L'identifiant précisé est invalide
   * car l'entité n'a pas vocation à être persistée
   */
  public PreferenceRoot()
  {
    super(-1);
  }

  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected Preference createProperty(String key, String value)
      throws UserException
  {
    return new Preference(key, value);
  }

}
