package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe d�fini une exception de persistance li�e � la tentative de manipulation
 * d'une entit� modifi�e en dehors de la transaction courante<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ConcurrentModificationException extends PersistenceException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -7656916098427246865L;

  /**
   * Constructeur li� � la manipulation d'une entit� modifi�e en dehors de la
   * transaction courante
   * @param <ENTITY> Type de l'entit� en erreur
   * @param <ID> Type de l'identifiant de l'entit� en erreur
   * @param entity Entit� modifi�e en dehors de la transaction courante
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ConcurrentModificationException(ENTITY entity)
  {
    super("Entity " + UtilSystem.getCanonicalClassName(entity.getClass()) +
          " with ID=" + entity.getId() + " was modified by another process");
  }
}
