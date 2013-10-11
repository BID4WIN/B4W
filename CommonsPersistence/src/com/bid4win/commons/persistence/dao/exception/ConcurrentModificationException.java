package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe défini une exception de persistance liée à la tentative de manipulation
 * d'une entité modifiée en dehors de la transaction courante<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ConcurrentModificationException extends PersistenceException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -7656916098427246865L;

  /**
   * Constructeur lié à la manipulation d'une entité modifiée en dehors de la
   * transaction courante
   * @param <ENTITY> Type de l'entité en erreur
   * @param <ID> Type de l'identifiant de l'entité en erreur
   * @param entity Entité modifiée en dehors de la transaction courante
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ConcurrentModificationException(ENTITY entity)
  {
    super("Entity " + UtilSystem.getCanonicalClassName(entity.getClass()) +
          " with ID=" + entity.getId() + " was modified by another process");
  }
}
