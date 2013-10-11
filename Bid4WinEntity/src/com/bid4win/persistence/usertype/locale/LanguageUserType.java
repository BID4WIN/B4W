package com.bid4win.persistence.usertype.locale;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les langues en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
