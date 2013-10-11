package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe d�fini une exception de persistance li�e � la recherche infructueuse
 * d'une entit�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class NotFoundEntityException extends PersistenceException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 4326554612499747295L;

  /**
   * Constructeur li� � la recherche infructueuse d'entit�s � partir de leur identifiant
   * @param <ENTITY> Type des entit�s recherch�es
   * @param <ID> Type de l'identifiant des entit�s recherch�s
   * @param entityClass Class des entit�s recherch�es
   * @param idSet Identifiants des entit�s recherch�s
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotFoundEntityException(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
  {
    super("No " + UtilSystem.getCanonicalClassName(entityClass) +
          " entity could be found with ID in " + idSet.toString());
  }
  /**
   * Constructeur li� � la recherche infructueuse d'une entit� � partir de son
   * identifiant
   * @param <ENTITY> Type de l'entit� recherch�e
   * @param <ID> Type de l'identifiant de l'entit� recherch�
   * @param entityClass Class de l'entit� recherch�e
   * @param id Identifiant de l'entit� recherch�
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotFoundEntityException(Class<ENTITY> entityClass, ID id)
  {
    super("No " + UtilSystem.getCanonicalClassName(entityClass) +
          " entity could be found with ID=" + id);
  }
  /**
   * Constructeur li� � la recherche infructueuse d'une entit�
   * @param <ENTITY> Type de l'entit� recherch�e
   * @param <ID> Type de l'identifiant de l'entit� recherch�
   * @param entityClass Class de l'entit� recherch�e
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotFoundEntityException(Class<ENTITY> entityClass)
  {
    super("No " + UtilSystem.getCanonicalClassName(entityClass) +
          " entity could be found");
  }
}
