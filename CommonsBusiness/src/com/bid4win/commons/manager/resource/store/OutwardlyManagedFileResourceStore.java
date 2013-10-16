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
 * Cette classe repr�sente la d�finition de tout magasin de stockage de ressources
 * sous forme de fichiers dont la gestion � proprement parler est effectu� ext�rieurement
 * � celui-ci. C'est � dire que la prise en charge du blocage des ressources lors
 * de leur manipulation est faite par le process utilisant le magasin et n'est
 * plus g�r�e par le magasin. Deux ressources diff�rentes peuvent dont �tre manipul�e
 * en parall�le<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir le type de ressources g�r�es<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class OutwardlyManagedFileResourceStore<RESOURCE extends Bid4WinFileResource<TYPE>,
                                                        TYPE extends ResourceType<TYPE>>
       extends Bid4WinFileResourceStore<RESOURCE, TYPE>
{
  /** Identifiant unique de lock de la session en cours */
  private static final ThreadLocal<String> LOCK_ID = new ThreadLocal<String>();

  /**
   * Red�fini la r�cup�ration de l'emplacement de travail du magasin pour se baser
   * sur l'identifiant unique de lock de la session en cours
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc} ou si aucun identifiant unique de lock
   * n'est d�fini pour la session en cours
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
   * identifiant unique de lock n'est d�fini pour la session en cours
   */
  protected File getWorkingFile() throws UserException
  {
    return new File(this.getWorkingPath());
  }

  /**
   * Red�fini le blocage des ressources pour g�n�rer un identifiant unique de lock
   * (et donc un emplacement de travail unique) pour la session en cours qui ne
   * sera pas partag� par les sessions en parall�le
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#lock()
   */
  @Override
  protected int lock()
  {
    // G�n�re l'identifiant unique de lock si cela n'a pas d�j� �t� fait pour la
    // session en cours
    this.generateLockId();
    // G�re le blocage des ressources en se basant sur l'identifiant unique de lock
    return super.lock();
  }
  /**
   * Red�fini le d�blocage des ressources afin de retirer l'identifiant unique de
   * lock et de supprimer le r�pertoire de travail si le d�blocage est complet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinFileResourceStore#unlock()
   */
  @Override
  protected int unlock()
  {
    // G�re le d�blocage des ressources en se basant sur l'identifiant unique de lock
    int lockNb = super.unlock();
    // Si le d�blocage est complet, on doit retirer l'identifiant unique de lock
    if(lockNb < 1)
    {
      try
      {
        // Supprime le r�pertoire de travail
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
   * @return L'identifiant unique de lock de la session en cours ou une cha�ne de
   * caract�re vide si inexistant
   */
  private String getLockId()
  {
    return UtilString.trimNotNull(OutwardlyManagedFileResourceStore.LOCK_ID.get());
  }
  /**
   * G�n�re un identifiant unique de lock pour la session en cours si aucun n'est
   * d�fini
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
