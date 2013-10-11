package com.bid4win.persistence.usertype.locale;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Cette classe défini le 'type utilisateur' gérant les langues en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class LanguageUserType extends Bid4WinObjectTypeUserType<Language>
{
  /**
   * Constructeur
   */
  public LanguageUserType()
  {
    super(Language.class);
  }
}
