package com.bid4win.commons.persistence.dao.resource;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.ConcurrentModificationException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;

/**
 * DAO pour les utilisations de ressource<BR>
 * <BR>
 * @param <USAGE> Doit définir la classe des utilisations de ressources gérées<BR>
 * @param <TYPE> Définition de la classe des types associés aux utilisations de
 * ressources gérées<BR>
 * @param <STORAGE> Doit définir la classe de stockage des utilisations de ressources
 * gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceUsageDao_<USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                        TYPE extends ResourceType<TYPE>,
                                        STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>>
       extends ResourceDao_<USAGE, TYPE>
{
  /**
   * Constructeur
   * @param usageClass Classe des utilisations de ressources gérées par le DAO
   */
  protected ResourceUsageDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }
  /**
   * Cette fonction permet de verrouiller l'utilisation de ressource en paramètre
   * et de récupérer son dernier état persisté en verrouillant son stockage
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws ConcurrentModificationException Si l'utilisation de ressource est
   * supprimée lors de la tentative de blocage de son stockage
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#lock(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  public USAGE lock(USAGE usage) throws PersistenceException, ConcurrentModificationException
  {
    try
    {
      return this.getStorageDao().lock(usage.getStorage()).getUsage(usage.getId());
    }
    catch(UserException ex)
    {
      throw new ConcurrentModificationException(usage);
    }
  }
  /**
   * Cette fonction permet de verrouiller l'utilisation de ressource dont l'identifiant
   * est passé en paramètre et de récupérer son dernier état persisté en verrouillant
   * son stockage
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws ConcurrentModificationException Si l'utilisation de ressource est
   * supprimée lors de la tentative de blocage de son stockage
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#lockById(java.lang.Long)
   */
  @Override
  public USAGE lockById(Long id)
         throws PersistenceException, NotFoundEntityException, ConcurrentModificationException
  {
    return this.lock(this.getById(id));
  }

  /**
   * Cette méthode permet d'ajouter une utilisation de ressource, son stockage
   * étant mis à jour en même temps
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public USAGE add(USAGE usage) throws PersistenceException
  {
    // Ajoute l'utilisation de ressource
    usage = super.add(usage);
    // Met à jour son stockage
    this.getStorageDao().update(usage.getStorage());
    // Retourne l'utilisation de ressource ajoutée
    return usage;
  }
  /**
   * Cette méthode permet de supprimer une utilisation de ressource, son stockage
   * étant mis à jour en même temps
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#remove(com.bid4win.commons.persistence.entity.resource.Resource)
   */
  @Override
  public USAGE remove(USAGE usage) throws PersistenceException
  {
    try
    {
      // Met à jour son stockage
      this.getStorageDao().update(usage.unlinkFromStorage());
    }
    catch(UserException ex)
    {
      throw new PersistenceException(ex);
    }
    // Supprime l'utilisation de ressource en paramètre
    return super.remove(usage);
  }

  /**
   * Getter du DAO des stockages de ressources
   * @return Le DAO des stockages de ressources
   */
  public abstract ResourceStorageDao_<STORAGE, TYPE, USAGE> getStorageDao();
}
