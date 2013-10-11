package com.bid4win.persistence.entity.locale.resource;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.inner.InnerContentUsage;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class InnerContent extends LocalizedResource<InnerContent, InnerContentType>
{
  /**
   * Constructeur à partir de la référence d'un stockage de contenu internationalisé
   * @param storage Référence du stockage de contenu internationalisé
   * @param language Langue de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public InnerContent(InnerContentStorage storage, Language language)
         throws ModelArgumentException, UserException
  {
    super(storage, language);
  }
  /**
   * Constructeur à partir de la référence d'une utilisation de contenu internationalisé
   * @param usage Référence de l'utilisation de contenu internationalisé
   * @param language Langue de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public InnerContent(InnerContentUsage usage, Language language)
         throws ModelArgumentException, UserException
  {
    super(usage, language);
  }
  /**
   * Constructeur à partir d'une portion de contenu
   * @param part Portion de contenu à partir de laquelle construire la nouvelle
   * @param language Langue de la portion de contenu
   * @throws ModelArgumentException Si la portion de ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public InnerContent(InnerContent part, Language language)
         throws ModelArgumentException, UserException
  {
    super(part, language);
  }

  /**
   * Getter de la portion de contenu correspondant à celle courante pour la langue
   * donnée
   * @param language {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourcePart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public InnerContent getPart(Language language) throws UserException
  {
    try
    {
      return new InnerContent(this, language);
    }
    catch(ModelArgumentException ex)
    {
      // Cette exception n'est lancée que si la ressource en argument est nulle
      return null;
    }
  }
}
