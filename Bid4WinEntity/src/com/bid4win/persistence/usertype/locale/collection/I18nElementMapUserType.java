package com.bid4win.persistence.usertype.locale.collection;

import com.bid4win.commons.persistence.usertype.collection.Bid4WinEmbeddableWithTypeMapUserType;
import com.bid4win.persistence.entity.locale.I18nElement;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.usertype.locale.I18nElementUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les maps d'�l�ments d'internationalisation
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class I18nElementMapUserType
       extends Bid4WinEmbeddableWithTypeMapUserType<I18nElement, Language>
{
  /**
   * Constructeur
   */
  public I18nElementMapUserType()
  {
    super(new I18nElementUserType());
  }
}
