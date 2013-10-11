package com.bid4win.persistence.usertype.locale.collection;

import com.bid4win.commons.persistence.usertype.collection.Bid4WinObjectTypeSetUserType;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.usertype.locale.LanguageUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les sets de langues en base
 * de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class LanguageSetUserType extends Bid4WinObjectTypeSetUserType<Language>
{
  /** Type utilisateur en charge de la gestion des langues du set */
  private LanguageUserType languageUserType = new LanguageUserType();
  /**
   * Constructeur
   */
  public LanguageSetUserType()
  {
    super();
  }

  /**
   * Getter du type utilisateur en charge de la gestion des langues du set
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.collection.Bid4WinObjectTypeSetUserType#getUserType()
   */
  @Override
  public LanguageUserType getUserType()
  {
    return this.languageUserType;
  }
}
