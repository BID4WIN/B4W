package com.bid4win.commons.caching;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <VALUE> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityCacheMap<KEY, VALUE>
{
  /** TODO A COMMENTER */
  private Map<KEY, VALUE> map = new ConcurrentHashMap<KEY, VALUE>();
  /**
   *
   * TODO A COMMENTER
   * @param key TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected VALUE get(KEY key)
  {
    return this.map.get(key);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected Map<KEY, VALUE> getMap()
  {
    return this.map;
  }
  /**
   *
   * TODO A COMMENTER
   */
  protected void clear()
  {
    this.getMap().clear();
  }

  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <ENTITY> TODO A COMMENTER<BR>
   * @param <ID> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class EntityMap <ENTITY extends Bid4WinEntity<?, ID>, ID>
         extends Bid4WinEntityCacheMap<ID, ENTITY>
  {
    /**
     *
     * TODO A COMMENTER
     * @param entity TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    protected synchronized ENTITY putEntity(ENTITY entity)
    {
      // R�cup�re la potentielle entit� cach�e
      ENTITY cached = this.get(entity.getId());
      // L'entit� cach� est moins r�cente que l'entit� � cacher, on la met � jour
      if(cached == null || cached.getVersion() < entity.getVersion())
      {
        this.getMap().put(entity.getId(), entity);
        cached = entity;
      }
      return cached;
    }
    /**
     *
     * TODO A COMMENTER
     * @param entity TODO A COMMENTER
     */
    protected synchronized void removeEntity(ENTITY entity)
    {
      // R�cup�re l'entit� cach�e
      ENTITY cached = this.get(entity.getId());
      // L'entit� cach�e a bien la version demand�e, on la retire du cache
      if(cached != null && cached.getVersion() == entity.getVersion())
      {
        this.getMap().remove(entity.getId());
      }

    }
  }

  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class CacheMap extends Bid4WinEntityCacheMap<Class<?>, EntityMap<?, ?>>
  {
    /**
     *
     * TODO A COMMENTER
     * @param entityClass TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    @SuppressWarnings("unchecked")
    protected <ENTITY extends Bid4WinEntity<?, ID>, ID>
              EntityMap<ENTITY, ID> getEntityMap(Class<ENTITY> entityClass)
    {
      return (EntityMap<ENTITY, ID>)this.get(entityClass);
    }
    /**
     *
     * TODO A COMMENTER
     * @param entityClass TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    @SuppressWarnings("unchecked")
    protected synchronized <ENTITY extends Bid4WinEntity<?, ID>, ID>
              EntityMap<ENTITY, ID> addEntityMap(Class<ENTITY> entityClass)
    {
      // R�cup�re le cache du type d'entit� d�finie en argument
      EntityMap<?, ?> entityMap = this.get(entityClass);
      // Si celui-ci est nul, il faut le cr�er
      if(entityMap == null)
      {
        entityMap = new EntityMap<ENTITY, ID>();
        this.getMap().put(entityClass, entityMap);
      }
      // Retourne le cache du type d'entit� d�finie en argument
      return (EntityMap<ENTITY, ID>)entityMap;
    }
  }
}
