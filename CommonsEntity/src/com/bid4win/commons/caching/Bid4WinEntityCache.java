package com.bid4win.commons.caching;

import com.bid4win.commons.caching.Bid4WinEntityCacheMap.CacheMap;
import com.bid4win.commons.caching.Bid4WinEntityCacheMap.EntityMap;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe permet de prendre en charge un cache d'entit�s. Celles-ci seront
 * class�es et conserv�es en fonction de leur classe et de leur identifiant, leur
 * version servant � d�cider laquelle conserver en cas de doublon<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityCache
{
  /** Cache servant � la conservation des entit�s */
  private CacheMap cache = new CacheMap();

  /**
   * Cette m�thode permet de r�cup�rer l'entit� potentiellement cach�e en fonction
   * de son identifiant
   * @param <ENTITY> D�finition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant de l'entit� � rechercher
   * @param entityClass Classe de l'entit� � rechercher
   * @param id Identifiant de l'entit� � rechercher
   * @return L'entit� trouv�e dans le cache gr�ce � son identifiant ou null le cas
   * �ch�ant
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ENTITY get(Class<ENTITY> entityClass, ID id)
  {
    // R�cup�re l'entit� demand�e dans le cache appropri�
    return this.getCache(entityClass).get(id);
  }
  /**
   * Cette m�thode permet de r�cup�rer les entit�s potentiellement cach�es en
   * fonction de leur identifiant
   * @param <ENTITY> D�finition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Set d'identifiant des entit�s � rechercher
   * @return La map des entit�s trouv�es dans le cache gr�ce � leur identifiant
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> get(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
  {
    // Construit la map de r�sultat
    Bid4WinMap<ID, ENTITY> result = new Bid4WinMap<ID, ENTITY>(idSet.size());
    // R�cup�re le cache appropri� aux entit�s demand�es
    EntityMap<ENTITY, ID> entityMap = this.getCache(entityClass);
    // R�cup�re les entit�s une � une et les ajoute au r�sultat
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
   * Cette m�thode permet d'ajouter une entit� au cache. Celle-ci ne sera r�ellement
   * ajout�e � la place de toute entit� potentiellement d�j� pr�sente avec le m�me
   * identifiant que si sa version lui est strictement sup�rieure
   * @param <ENTITY> D�finition du type d'entit� � cacher
   * @param <ID> D�finition du type d'identifiant de l'entit� � cacher
   * @param entityClass Classe de l'entit� � cacher
   * @param entity Entit� � cacher
   * @return L'entit� finalement cach�e, c'est � dire l'entit� en argument s'il
   * n'existait pas une r�f�rence au moins aussi r�cente dans le cache, ou l'entit�
   * d�j� pr�sente dans le cache le cas �ch�ant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas �
   * celle de l'entit�
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         ENTITY cache(Class<ENTITY> entityClass, ENTITY entity)
         throws IllegalArgumentException
  {
    if(!entityClass.equals(entity.getClass()))
    {
      throw new IllegalArgumentException("Class definition should represent entity");
    }
    // Stocke l'entit� dans le cache appropri�
    return this.getCache(entityClass).putEntity(entity);
  }
  /**
   * Cette m�thode permet d'ajouter des entit�s au cache. Celles-ci ne seront r�ellement
   * ajout�es � la place de toute entit� potentiellement d�j� pr�sente avec le m�me
   * identifiant que si leur version leur est strictement sup�rieure
   * @param <ENTITY> D�finition du type d'entit� � cacher
   * @param <ID> D�finition du type d'identifiant des entit�s � cacher
   * @param entityClass Classe des entit�s � cacher
   * @param entityMap Entit�s � cacher
   * @return Les entit� cach�es, c'est � dire les entit�s en argument s'il n'existait
   * pas une r�f�rence au moins aussi r�cente dans le cache, ou les entit�s d�j�
   * pr�sentes dans le cache le cas �ch�ant
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas �
   * celles des entit�s
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> cache(Class<ENTITY> entityClass,
                                      Bid4WinMap<ID, ENTITY> entityMap)
         throws IllegalArgumentException
  {
    // Construit la map de r�sultat
    Bid4WinMap<ID, ENTITY> cachedMap = new Bid4WinMap<ID, ENTITY>(entityMap.size());
    // Stocke les entit�s une � une et les ajoute au r�sultat
    for(ENTITY entity : entityMap.values())
    {
      cachedMap.put(entity.getId(), this.cache(entityClass, entity));
    }
    return cachedMap;
  }
  /**
   * Cette m�thode permet de retirer une entit� du cache. Celle-ci ne sera r�ellement
   * retir�e que si l'entit� potentiellement d�j� pr�sente avec le m�me identifiant
   * a la m�me version
   * @param <ENTITY> D�finition du type d'entit� � retirer du cache
   * @param <ID> D�finition du type d'identifiant de l'entit� � retirer du cache
   * @param entityClass Classe de l'entit� � retirer du cache
   * @param entity Entit� � retirer du cache
   * @throws IllegalArgumentException Si la classe fournie ne correspond pas �
   * celle de l'entit�
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
   * Cette m�thode permet de vider le cache de toutes les entit�s contenues
   */
  public void clear()
  {
    synchronized(this.getCache())
    {
      this.getCache().clear();
    }
  }

  /**
   * Cette m�thode permet de r�cup�rer le cache servant � la conservation des entit�s
   * @return Le cache servant � la conservation des entit�s
   */
  private CacheMap getCache()
  {
    return this.cache;
  }
  /**
   * Cette m�thode permet de r�cup�rer le cache du type d'entit� d�finie en argument
   * @param <ENTITY>  D�finition du type des entit�s du cache � r�cup�rer
   * @param <ID> D�finition du type d'identifiant des entit�s du cache � r�cup�rer
   * @param entityClass Classe des entit�s du cache � r�cup�rer
   * @return Le cache du type d'entit� d�finie en argument
   */
  private <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
          EntityMap<ENTITY, ID> getCache(Class<ENTITY> entityClass)
  {
    // R�cup�re le cache du type d'entit� d�finie en argument
    EntityMap<ENTITY, ID> cache = this.getCache().getEntityMap(entityClass);
    // Si celui-ci est nul, il faut le cr�er
    if(cache == null)
    {
      cache = this.getCache().addEntityMap(entityClass);
    }
    // Retourne le cache du type d'entit� d�finie en argument
    return cache;
  }
}
