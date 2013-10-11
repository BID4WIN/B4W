package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe d�fini une exception de persistance li�e � la recherche infructueuse
 * d'une entit� de mani�re unique<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class NotUniqueEntityException extends PersistenceException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 4151723736167585559L;

  /**
   * Constructeur li� � la recherche infructueuse d'une entit� de mani�re unique
   * @param <ENTITY> Type de l'entit� recherch�e de mani�re unique
   * @param <ID> Type de l'identifiant de l'entit� recherch� de mani�re unique
   * @param entityClass Class de l'entit� recherch�e de mani�re unique
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotUniqueEntityException(Class<ENTITY> entityClass)
  {
    super("More than one " + UtilSystem.getCanonicalClassName(entityClass) + " entity could be found");
  }
}
