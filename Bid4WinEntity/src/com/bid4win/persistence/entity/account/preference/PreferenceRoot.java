package com.bid4win.persistence.entity.account.preference;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 * Cette classe repr�sente la racine des propri�t�s de pr�f�rence d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PreferenceRoot extends PropertyRootAbstract<PreferenceRoot, Preference>
{
  /**
   * Constructeur de la racine des propri�t�s. L'identifiant pr�cis� est invalide
   * car l'entit� n'a pas vocation � �tre persist�e
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
