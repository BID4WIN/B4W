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
 * @param <USAGE> Doit d�finir la classe des utilisations de ressources g�r�es<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux utilisations de
 * ressources g�r�es<BR>
 * @param <STORAGE> Doit d�finir la classe de stockage des utilisations de ressources
 * g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ResourceUsageDao_<USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                        TYPE extends ResourceType<TYPE>,
                                        STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>>
       extends ResourceDao_<USAGE, TYPE>
{
  /**
   * Constructeur
   * @param usageClass Classe des utilisations de ressources g�r�es par le DAO
   */
  protected ResourceUsageDao_(Class<USAGE> usageClass)
  {
    super(usageClass);
  }
  /**
   * Cette fonction permet de verrouiller l'utilisation de ressource en param�tre
   * et de r�cup�rer son dernier �tat persist� en verrouillant son stockage
   * @param usage {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws ConcurrentModificationException Si l'utilisation de ressource est
   * supprim�e lors de la tentative de blocage de son stockage
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
   * est pass� en param�tre et de r�cup�rer son dernier �tat persist� en verrouillant
   * son stockage
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @throws ConcurrentModificationException Si l'utilisation de ressource est
   * supprim�e lors de la tentative de blocage de son stockage
   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#lockById(java.lang.Long)
   */
  @Override
  public USAGE lockById(Long id)
         throws PersistenceException, NotFoundEntityException, ConcurrentModificationException
  {
    return this.lock(this.getById(id));
  }

  /**
   * Cette m�thode permet d'ajouter une utilisation de ressource, son stockage
   * �tant mis � jour en m�me temps
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
    // Met � jour son stockage
    this.getStorageDao().update(usage.getStorage());
    // Retourne l'utilisation de ressource ajout�e
    return usage;
  }
  /**
   * Cette m�thode permet de supprimer une utilisation de ressource, son stockage
   * �tant mis � jour en m�me temps
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
      // Met � jour son stockage
      this.getStorageDao().update(usage.unlinkFromStorage());
    }
    catch(UserException ex)
    {
      throw new PersistenceException(ex);
    }
    // Supprime l'utilisation de ressource en param�tre
    return super.remove(usage);
  }

  /**
   * Getter du DAO des stockages de ressources
   * @return Le DAO des stockages de ressources
   */
  public abstract ResourceStorageDao_<STORAGE, TYPE, USAGE> getStorageDao();
}
