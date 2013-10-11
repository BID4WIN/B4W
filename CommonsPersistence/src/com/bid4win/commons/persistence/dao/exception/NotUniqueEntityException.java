package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe défini une exception de persistance liée à la recherche infructueuse
 * d'une entité de manière unique<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NotUniqueEntityException extends PersistenceException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 4151723736167585559L;

  /**
   * Constructeur lié à la recherche infructueuse d'une entité de manière unique
   * @param <ENTITY> Type de l'entité recherchée de manière unique
   * @param <ID> Type de l'identifiant de l'entité recherché de manière unique
   * @param entityClass Class de l'entité recherchée de manière unique
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotUniqueEntityException(Class<ENTITY> entityClass)
  {
    super("More than one " + UtilSystem.getCanonicalClassName(entityClass) + " entity could be found");
  }
}
