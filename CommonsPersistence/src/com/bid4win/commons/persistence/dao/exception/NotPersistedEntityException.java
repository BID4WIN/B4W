package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe d�fini une exception de persistance li�e � la tentative infructueuse
 * de modification d'une entit� non persist�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class NotPersistedEntityException extends PersistenceException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 196737497063132407L;

  /**
   * Constructeur li� � la tentative infructueuse de modification d'une entit�
   * non persist�
   * @param <ENTITY> Type de l'entit� recherch�e
   * @param <ID> Type de l'identifiant de l'entit� recherch�
   * @param entityClass Class de l'entit� recherch�e
   * @param action Type de modification en �chec
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotPersistedEntityException(Class<ENTITY> entityClass, String action)
  {
    super("Could not " + action + " not persisted " +
          UtilSystem.getCanonicalClassName(entityClass) + " entity");
  }
}
