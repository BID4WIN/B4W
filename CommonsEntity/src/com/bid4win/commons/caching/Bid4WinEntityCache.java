package com.bid4win.commons.caching;

import com.bid4win.commons.caching.Bid4WinEntityCacheMap.CacheMap;
import com.bid4win.commons.caching.Bid4WinEntityCacheMap.EntityMap;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe permet de prendre en charge un cache d'entités. Celles-ci seront
 * classées et conservées en fonction de leur classe et de leur identifiant, leur
 * version servant à décider laquelle conserver en cas de doublon<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityCache
{
  /** Cache servant à la conservation des entités */
  private CacheMap cache = new CacheMap();

  /**
   * Cette méthode permet de récupérer l'entité potentiellement cachée en fonction
   * de son identifiant
   * @param <ENTITY> Définition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant de l'entité à rechercher
   * @param entityClass Classe de l'entité à rechercher
   * @param id Identifiant de l'entité à rechercher
   * @return L'entité trouvée dans le cache grâce à son identifiant ou null le cas
   * échéant
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ENTITY get(Class<ENTITY> entityClass, ID id)
  {
    // Récupère l'entité demandée dans le cache approprié
    return this.getCache(entityClass).get(id);
  }
  /**
   * Cette méthode permet de récupérer les entités potentiellement cachées en
   * fonction de leur identifiant
   * @param <ENTITY> Définition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Set d'identifiant des entités à rechercher
   * @return La map des entités trouvées dans le cache grâce à leur identifiant
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> get(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
  {
    // Construit la map de résultat
    Bid4WinMap<ID, ENTITY> result = new Bid4WinMap<ID, ENTITY>(idSet.size());
    // Récupère le cache approprié aux entités demandées
    EntityMap<ENTITY, ID> entityMap = this.getCache(entityClass);
    // Récupère les entités une à une et les ajoute au résultat
    for(ID id : idSet)
    {
      ENTITY cached = entityMap.get(id);
      if(cached != null)
      {
        result.put(id, cached);
      }
    }
    return result;
  }
  /**
   * Cette méthode permet d'ajouter une entité au cache. Celle-ci ne sera réellement
   * ajoutée à la place de toute entité potentiellement déjà présente avec le même
   * identifiant que si sa version lui est strictement supérieure
   * @param <ENTITY> Définition du type d'entité à cacher
   * @param <ID> Définition du type d'identifiant de l'entité à cacher
   * @param entityClass Classe de l'entité à cacher
   * @param entity Entité à cacher
   * @return L'entité finalement cachée, c'est à dire l'entité en argument s'il
   * n'existait pas une référence au moins aussi récente dans le cache, ou l'entité
   * déjà présente dans le cache le cas échéant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas à
   * celle de l'entité
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ENTITY cache(Class<ENTITY> entityClass, ENTITY entity)
         throws IllegalArgumentException
  {
    if(!entityClass.equals(entity.getClass()))
    {
      throw new IllegalArgumentException("Class definition should represent entity");
    }
    // Stocke l'entité dans le cache approprié
    return this.getCache(entityClass).putEntity(entity);
  }
  /**
   * Cette méthode permet d'ajouter des entités au cache. Celles-ci ne seront réellement
   * ajoutées à la place de toute entité potentiellement déjà présente avec le même
   * identifiant que si leur version leur est strictement supérieure
   * @param <ENTITY> Définition du type d'entité à cacher
   * @param <ID> Définition du type d'identifiant des entités à cacher
   * @param entityClass Classe des entités à cacher
   * @param entityMap Entités à cacher
   * @return Les entité cachées, c'est à dire les entités en argument s'il n'existait
   * pas une référence au moins aussi récente dans le cache, ou les entités déjà
   * présentes dans le cache le cas échéant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas à
   * celles des entités
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> cache(Class<ENTITY> entityClass,
                                      Bid4WinMap<ID, ENTITY> entityMap)
         throws IllegalArgumentException
  {
    // Construit la map de résultat
    Bid4WinMap<ID, ENTITY> cachedMap = new Bid4WinMap<ID, ENTITY>(entityMap.size());
    // Stocke les entités une à une et les ajoute au résultat
    for(ENTITY entity : entityMap.values())
    {
      cachedMap.put(entity.getId(), this.cache(entityClass, entity));
    }
    return cachedMap;
  }
  /**
   * Cette méthode permet de retirer une entité du cache. Celle-ci ne sera réellement
   * retirée que si l'entité potentiellement déjà présente avec le même identifiant
   * a la même version
   * @param <ENTITY> Définition du type d'entité à retirer du cache
   * @param <ID> Définition du type d'identifiant de l'entité à retirer du cache
   * @param entityClass Classe de l'entité à retirer du cache
   * @param entity Entité à retirer du cache
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas à
   * celle de l'entité
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         void uncache(Class<ENTITY> entityClass, ENTITY entity)
         throws IllegalArgumentException
  {
    if(!entityClass.equals(entity.getClass()))
    {
      throw new IllegalArgumentException("Class definition should represent entity");
    }
    this.getCache(entityClass).removeEntity(entity);
  }
  /**
   * Cette méthode permet de vider le cache de toutes les entités contenues
   */
  public void clear()
  {
    synchronized(this.getCache())
    {
      this.getCache().clear();
    }
  }

  /**
   * Cette méthode permet de récupérer le cache servant à la conservation des entités
   * @return Le cache servant à la conservation des entités
   */
  private CacheMap getCache()
  {
    return this.cache;
  }
  /**
   * Cette méthode permet de récupérer le cache du type d'entité définie en argument
   * @param <ENTITY>  Définition du type des entités du cache à récupérer
   * @param <ID> Définition du type d'identifiant des entités du cache à récupérer
   * @param entityClass Classe des entités du cache à récupérer
   * @return Le cache du type d'entité définie en argument
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          EntityMap<ENTITY, ID> getCache(Class<ENTITY> entityClass)
  {
    // Récupère le cache du type d'entité définie en argument
    EntityMap<ENTITY, ID> cache = this.getCache().getEntityMap(entityClass);
    // Si celui-ci est nul, il faut le créer
    if(cache == null)
    {
      cache = this.getCache().addEntityMap(entityClass);
    }
    // Retourne le cache du type d'entité définie en argument
    return cache;
  }
}
