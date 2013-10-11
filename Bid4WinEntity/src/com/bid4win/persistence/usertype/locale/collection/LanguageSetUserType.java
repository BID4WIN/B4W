package com.bid4win.persistence.usertype.locale.collection;

import com.bid4win.commons.persistence.usertype.collection.Bid4WinObjectTypeSetUserType;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.usertype.locale.LanguageUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les sets de langues en base
 * de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
