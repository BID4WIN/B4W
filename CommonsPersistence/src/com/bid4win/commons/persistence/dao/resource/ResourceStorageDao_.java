package com.bid4win.commons.persistence.dao.resource;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;

/**
 * DAO pour les stockages de ressources<BR>
 * <BR>
 * @param <STORAGE> Doit définir la classe des stockages de ressources gérés<BR>
 * @param <TYPE> Définition de la classe des types associés aux stockages de
 * ressources gérées<BR>
 * @param <USAGE> Doit définir la classe d'utilisation des stockages de ressources
 * gérés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceStorageDao_<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                          TYPE extends ResourceType<TYPE>,
                                          USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>>
       extends ResourceDao_<STORAGE, TYPE>
{
  /**
   * Constructeur
   * @param storageClass Classe des stockages de ressources gérés par le DAO
   */
  protected ResourceStorageDao_(Class<STORAGE> storageClass)
  {
    super(storageClass);
  }

  /**
   * Propage le forçage de modification du stockage de la ressource en paramètre
   * à ses utilisations pour qu'elles prennent en compte que leur ressource associée
   * n'est plus à jour. Cette méthode est utilisée afin de noter que la ressource
   * associée au stockage en argument a été modifiée
   * @param storage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#forceUpdate(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  public STORAGE forceUpdate(STORAGE storage) throws PersistenceException
  {
    // Propage le forçage de modification aux utilisations de la ressource
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
