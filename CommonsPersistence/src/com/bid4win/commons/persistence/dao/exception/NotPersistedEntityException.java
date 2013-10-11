package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe défini une exception de persistance liée à la tentative infructueuse
 * de modification d'une entité non persisté<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NotPersistedEntityException extends PersistenceException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 196737497063132407L;

  /**
   * Constructeur lié à la tentative infructueuse de modification d'une entité
   * non persisté
   * @param <ENTITY> Type de l'entité recherchée
   * @param <ID> Type de l'identifiant de l'entité recherché
   * @param entityClass Class de l'entité recherchée
   * @param action Type de modification en échec
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotPersistedEntityException(Class<ENTITY> entityClass, String action)
  {
    super("Could not " + action + " not persisted " +
          UtilSystem.getCanonicalClassName(entityClass) + " entity");
  }
}
