package com.bid4win.commons.persistence.dao.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe défini une exception de persistance liée à la recherche infructueuse
 * d'une entité<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NotFoundEntityException extends PersistenceException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 4326554612499747295L;

  /**
   * Constructeur lié à la recherche infructueuse d'entités à partir de leur identifiant
   * @param <ENTITY> Type des entités recherchées
   * @param <ID> Type de l'identifiant des entités recherchés
   * @param entityClass Class des entités recherchées
   * @param idSet Identifiants des entités recherchés
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotFoundEntityException(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
  {
    super("No " + UtilSystem.getCanonicalClassName(entityClass) +
          " entity could be found with ID in " + idSet.toString());
  }
  /**
   * Constructeur lié à la recherche infructueuse d'une entité à partir de son
   * identifiant
   * @param <ENTITY> Type de l'entité recherchée
   * @param <ID> Type de l'identifiant de l'entité recherché
   * @param entityClass Class de l'entité recherchée
   * @param id Identifiant de l'entité recherché
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotFoundEntityException(Class<ENTITY> entityClass, ID id)
  {
    super("No " + UtilSystem.getCanonicalClassName(entityClass) +
          " entity could be found with ID=" + id);
  }
  /**
   * Constructeur lié à la recherche infructueuse d'une entité
   * @param <ENTITY> Type de l'entité recherchée
   * @param <ID> Type de l'identifiant de l'entité recherché
   * @param entityClass Class de l'entité recherchée
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         NotFoundEntityException(Class<ENTITY> entityClass)
  {
    super("No " + UtilSystem.getCanonicalClassName(entityClass) +
          " entity could be found");
  }
}
