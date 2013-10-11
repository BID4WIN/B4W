package com.bid4win.persistence.usertype.locale;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType;
import com.bid4win.persistence.entity.locale.I18nElement;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les �l�ments d'internationalisation
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class I18nElementUserType
       extends Bid4WinEmbeddableWithTypeUserType<I18nElement, Language>
{
  /**
   * Constructeur
   */
  public I18nElementUserType()
  {
    super(I18nElement.class, Language.class);
  }

  /**
   * Cette fonction est d�finie afin de construire l'�l�ment d'internationalisation
   * correspondant aux cha�nes de caract�res en argument associ� � la langue donn�e
   * @param language {@inheritDoc}
   * @param strings {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType#typeFromStrings(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType, java.lang.String[])
   */
  @Override
  public I18nElement typeFromStrings(Language language, String... strings)
         throws Bid4WinException
  {
    UtilObject.checkEquals("strings", strings.length, 1);
    return new I18nElement(language, strings[0]);
  }
  /**
   * Cette fonction est d�finie afin de r�cup�rer la liste des champs de l'�l�ment
   * d'internationalisation en param�tre autres que sa langue sous la forme de
   * cha�nes de caract�res
   * @param embedded {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType#stringsFromType(com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType)
   */
  @Override
  public Bid4WinList<String> stringsFromType(I18nElement embedded)
  {
    return new Bid4WinList<String>(embedded.getValue());
  }
}
