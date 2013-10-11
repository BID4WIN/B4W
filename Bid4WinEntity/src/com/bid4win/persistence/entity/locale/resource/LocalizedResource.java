package com.bid4win.persistence.entity.locale.resource;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.store.FileResourcePart;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.LocalizedStorage;
import com.bid4win.persistence.entity.locale.LocalizedUsage;

/**
 *  Cette classe défini la portion de ressource internationalisée pour une langue
 *  utilisée sous forme de fichier par les magasins de stockage correspondant<BR>
 * <BR>
 * @param <CLASS>  Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class LocalizedResource<CLASS extends LocalizedResource<CLASS, TYPE>,
                                        TYPE extends ResourceType<TYPE>>
       extends  FileResourcePart<CLASS, TYPE, Language>
{
  /**
   * Constructeur à partir de la référence d'un stockage de ressource internationalisée
   * @param storage Référence du stockage de ressource internationalisée
   * @param language Langue de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public LocalizedResource(LocalizedStorage<?, TYPE, ?, CLASS> storage, Language language)
         throws ModelArgumentException, UserException
  {
    super(storage, language);
  }
  /**
   * Constructeur à partir de la référence d'une utilisation de ressource internationalisée
   * @param usage Référence de l'utilisation de ressource internationalisée
   * @param language Langue de la portion de ressource
   * @throws ModelArgumentException Si la ressource en argument est nulle
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * portion de ressource est invalide
   */
  public LocalizedResource(LocalizedUsage<?, TYPE, ?, CLASS> usage, Language language)
         throws ModelArgumentException, UserException
  {
    super(usage, language);
  }
  /**
   * Constructeur à partir d'une portion de ressource
   * @param part Portion de ressource à partir de laquelle construire la nouvelle
   * @param language Langue de la portion de ressource
   * @throws ModelArgumentException Si la portion de ressource en argument est nulle
   * @throws UserException Si le nom ou l'emplacement de stockage de la portion
   * de ressource est invalide ou le type de portion nul
   */
  public LocalizedResource(CLASS part, Language language)
         throws ModelArgumentException, UserException
  {
    super(part, language);
  }

  /**
   * Getter de la langue de la portion de ressource
   * @return La langue de la portion de ressource
   */
  public Language getLanguage()
  {
    return this.getPartType();
  }
}
