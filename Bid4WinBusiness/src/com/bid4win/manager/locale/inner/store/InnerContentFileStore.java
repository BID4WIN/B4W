package com.bid4win.manager.locale.inner.store;

import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore;
import com.bid4win.commons.manager.resource.store.OutwardlyManagedFileResourceStore;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.LocalizedStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 * Cette classe d�fini le magasin de base des ressources internationalis�es stock�es
 * sous la forme de fichiers. Il permet de g�rer les diff�rentes langues utilisables
 * et leur ressources associ�es<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir le type de ressources g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class InnerContentFileStore<RESOURCE extends Bid4WinFileResourceMultiPart<InnerContentType, Language, InnerContent>>
       extends Bid4WinFileResourceMultiPartStore<RESOURCE, InnerContentType, Language, InnerContent>
{
  /**
   * Getter de la langue de page HTML par d�faut
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartTypeDefault()
   */
  @Override
  public Language getPartTypeDefault()
  {
    return LocalizedStorage.DEFAULT_LANGUAGE;
  }

  /**
   * Getter du magasin de gestion de stockage de toutes les langues de contenu stock�es
   * sous forme de fichiers
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartStore#getPartStore()
   */
  @Override
  public abstract OutwardlyManagedFileResourceStore<InnerContent, InnerContentType> getPartStore();
}
