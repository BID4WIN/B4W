package com.bid4win.commons.manager.resource.store;

import java.io.File;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.entity.resource.ResourceType;

/**
 * Cette classe représente la définition de tout magasin de stockage de ressources
 * sous forme de fichiers dont la gestion à proprement parler est effectué extérieurement
 * à celui-ci. C'est à dire que la prise en charge du blocage des ressources lors
 * de leur manipulation est faite par le process utilisant le magasin et n'est
 * plus gérée par le magasin. Deux ressources différentes peuvent dont être manipulée
 * en parallèle<BR>
 * <BR>
 * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class OutwardlyManagedFileResourceStore<RESOURCE extends Bid4WinFileResource<TYPE>,
                                                        TYPE extends ResourceType<TYPE>>
       extends Bid4WinFileResourceStore<RESOURCE, TYPE>
{
  /** Identifiant unique de lock de la session en cours */
  private static final ThreadLocal<String> LOCK_ID = new ThreadLocal<String>();

  /**
   * Redéfini la récupération de l'emplacement de travail du magasin pour se baser
   * sur l'identifiant unique de lock de la session en cours
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc} ou si aucun identifiant unique de lock
   * n'est défini pour la session en cours
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#getWorkingPath()
   */
  @Override
  protected String getWorkingPath() throws UserException
  {
    String lockId = this.getLockId();
    if(lockId.equals(UtilString.EMPTY))
    {
      throw new UserException(ResourceRef.WORKING_PATH_MISSING_ERROR);
    }
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE, this.getRootPath(), lockId);
  }
  /**
   * Getter de l'emplacement de travail du magasin
   * @return L'emplacement de travail du magasin
   * @throws UserException Si l'emplacement de travail est invalide ou si aucun
   * identifiant unique de lock n'est défini pour la session en cours
   */
  protected File getWorkingFile() throws UserException
  {
    return new File(this.getWorkingPath());
  }

  /**
   * Redéfini le blocage des ressources pour générer un identifiant unique de lock
   * (et donc un emplacement de travail unique) pour la session en cours qui ne
   * sera pas partagé par les sessions en parallèle
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#lock()
   */
  @Override
  protected int lock()
  {
    // Génère l'identifiant unique de lock si cela n'a pas déjà été fait pour la
    // session en cours
    this.generateLockId();
    // Gère le blocage des ressources en se basant sur l'identifiant unique de lock
    return super.lock();
  }
  /**
   * Redéfini le déblocage des ressources afin de retirer l'identifiant unique de
   * lock et de supprimer le répertoire de travail si le déblocage est complet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#unlock()
   */
  @Override
  protected int unlock()
  {
    // Gère le déblocage des ressources en se basant sur l'identifiant unique de lock
    int lockNb = super.unlock();
    // Si le déblocage est complet, on doit retirer l'identifiant unique de lock
    if(lockNb < 1)
    {
      try
      {
        // Supprime le répertoire de travail
        this.getWorkingFile().delete();
      }
      catch(Throwable th)
      {
        th.printStackTrace();
      }
      // Retire l'identifiant unique de lock
      this.removeLockId();
    }
    return lockNb;
  }

  /**
   * Getter de l'identifiant unique de lock de la session en cours
   * @return L'identifiant unique de lock de la session en cours ou une chaîne de
   * caractère vide si inexistant
   */
  private String getLockId()
  {
    return UtilString.trimNotNull(OutwardlyManagedFileResourceStore.LOCK_ID.get());
  }
  /**
   * Génère un identifiant unique de lock pour la session en cours si aucun n'est
   * défini
   * @return L'identifiant unique de lock de la session en cours
   */
  private String generateLockId()
  {
    String lockId = this.getLockId();
    if(lockId.equals(UtilString.EMPTY))
    {
      lockId = "." + IdGenerator.generateId(10);
      OutwardlyManagedFileResourceStore.LOCK_ID.set(lockId);
    }
    return lockId;
  }
  /**
   * Retire l'identifiant unique de lock de la session en cours
   */
  private void removeLockId()
  {
    OutwardlyManagedFileResourceStore.LOCK_ID.remove();
  }
}
