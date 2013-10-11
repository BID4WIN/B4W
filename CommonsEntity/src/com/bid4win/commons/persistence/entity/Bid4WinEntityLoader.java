package com.bid4win.commons.persistence.entity;

import java.util.Collection;
import java.util.Map;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.persistence.entity.collection.Bid4WinEntityReferenceSet;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityLoader
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinEntityLoader instance = new Bid4WinEntityLoader();
  /**
   * Le loader est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du loader
   */
  public static Bid4WinEntityLoader getInstance()
  {
    return Bid4WinEntityLoader.instance;
  }
  /**
   * Le constructeur est privé car le loader doit être accédé comme un singleton
   */
  private Bid4WinEntityLoader()
  {
    super();
  }

  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<?, ?>> ENTITY loadRelation(ENTITY entity)
  {
    return this.loadRelation(entity, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<?, ?>>
         ENTITY loadRelation(ENTITY entity, Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.loadRelation(entity, nodeList, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <ENTITY> TODO A COMMENTER
   * @param entity TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @param referenced TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <ENTITY extends Bid4WinEntity<?, ?>>
         ENTITY loadRelation(ENTITY entity,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced)
  {
    if(entity != null)
    {
      if(nodeList == null)
      {
        entity.loadRelation();
      }
      else
      {
        if(referenced == null)
        {
          entity.loadRelation(nodeList);
        }
        else
        {
          entity.loadRelation(nodeList, referenced);
        }
      }
    }
    return entity;
  }

  /**
   *
   * TODO A COMMENTER
   * @param <COLLECTION> TODO A COMMENTER
   * @param collection TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <COLLECTION extends Collection<? extends Bid4WinEntity<?, ?>>>
         COLLECTION loadRelation(COLLECTION collection)
  {
    return this.loadRelation(collection, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <COLLECTION> TODO A COMMENTER
   * @param collection TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <COLLECTION extends Collection<? extends Bid4WinEntity<?, ?>>>
         COLLECTION loadRelation(COLLECTION collection, Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.loadRelation(collection, nodeList, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <COLLECTION> TODO A COMMENTER
   * @param collection TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @param referenced TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <COLLECTION extends Collection<? extends Bid4WinEntity<?, ?>>>
         COLLECTION loadRelation(COLLECTION collection,
                                 Bid4WinList<Bid4WinRelationNode> nodeList,
                                 Bid4WinEntityReferenceSet referenced)
  {
    if(collection != null)
    {
      // TODO verifier pour le null ...
      if(nodeList == null || nodeList.size() != 0)
      {
        for(Bid4WinEntity<?, ?> entity : collection)
        {
          this.loadRelation(entity, nodeList, referenced);
        }
      }
      else
      {
        collection.size();
      }
    }
    return collection;
  }

  /**
   *
   * TODO A COMMENTER
   * @param <MAP> TODO A COMMENTER
   * @param <KEY> TODO A COMMENTER
   * @param entityMap TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <MAP extends Map<KEY, ? extends Bid4WinEntity<?, ?>>, KEY>
         MAP loadRelation(MAP entityMap)
  {
    return this.loadRelation(entityMap, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <MAP> TODO A COMMENTER
   * @param <KEY> TODO A COMMENTER
   * @param entityMap TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <MAP extends Map<KEY, ? extends Bid4WinEntity<?, ?>>, KEY>
         MAP loadRelation(MAP entityMap, Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.loadRelation(entityMap, nodeList, null);
  }
  /**
   *
   * TODO A COMMENTER
   * @param <MAP> TODO A COMMENTER
   * @param <KEY> TODO A COMMENTER
   * @param entityMap TODO A COMMENTER
   * @param nodeList TODO A COMMENTER
   * @param referenced TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public <MAP extends Map<KEY, ? extends Bid4WinEntity<?, ?>>, KEY>
         MAP loadRelation(MAP entityMap,
                          Bid4WinList<Bid4WinRelationNode> nodeList,
                          Bid4WinEntityReferenceSet referenced)
  {
    if(entityMap != null)
    {
      // TODO verifier pour le null ...
      if(nodeList == null || nodeList.size() != 0)
      {
        for(Bid4WinEntity<?, ?> entity : entityMap.values())
        {
          this.loadRelation(entity, nodeList, referenced);
        }
      }
      else
      {
        entityMap.size();
      }
    }
    return entityMap;
  }
}
