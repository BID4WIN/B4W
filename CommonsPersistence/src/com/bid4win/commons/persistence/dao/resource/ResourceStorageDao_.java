package com.bid4win.commons.persistence.dao.resource;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;

/**
 * DAO pour les stockages de ressources<BR>
 * <BR>
 * @param <STORAGE> Doit d�finir la classe des stockages de ressources g�r�s<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux stockages de
 * ressources g�r�es<BR>
 * @param <USAGE> Doit d�finir la classe d'utilisation des stockages de ressources
 * g�r�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ResourceStorageDao_<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                          TYPE extends ResourceType<TYPE>,
                                          USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>>
       extends ResourceDao_<STORAGE, TYPE>
{
  /**
   * Constructeur
   * @param storageClass Classe des stockages de ressources g�r�s par le DAO
   */
  protected ResourceStorageDao_(Class<STORAGE> storageClass)
  {
    super(storageClass);
  }

  /**
   * Propage le for�age de modification du stockage de la ressource en param�tre
   * � ses utilisations pour qu'elles prennent en compte que leur ressource associ�e
   * n'est plus � jour. Cette m�thode est utilis�e afin de noter que la ressource
   * associ�e au stockage en argument a �t� modifi�e
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#forceUpdate(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  public STORAGE forceUpdate(STORAGE storage) throws PersistenceException
  {
    // Propage le for�age de modification aux utilisations de la ressource
    for(USAGE usage : storage.getUsageList())
    {
      this.getUsageDao().forceUpdate(usage);
    }
    // Force la modification de la ressource
    return super.forceUpdate(storage);
  }

  /**
   * Getter du DAO des utilisations de ressources
   * @return Le DAO des utilisations de ressources
   */
  public abstract ResourceUsageDao_<USAGE, TYPE, STORAGE> getUsageDao();
}
