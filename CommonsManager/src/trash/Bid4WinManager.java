//package trash;
//
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.persistence.entity.Bid4WinEntity;
//
//public abstract class Bid4WinManager<ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
//{
//  private Synchronizer<ID> synchronizer = new Synchronizer<ID>();
//  private int cacheSize = 10;
//  private Bid4WinMap<ID, ENTITY> entityCache = new Bid4WinMap<ID, ENTITY>(this.cacheSize);
//
//
//  protected Bid4WinManager()
//  {
//
//  }
//
//
//  protected ENTITY get(ID id) throws ModelArgumentException
//  {
//    ENTITY entity = this.getFromCache(id);
//    if(entity == null)
//    {
//      synchronized(this.claimToken(id))
//      {
//        entity = this.getFromCache(id);
//        if(entity == null)
//        {
//          entity = this.getFromDatabase(id);
//        }
//      }
//      this.returnToken(id);
//    }
//    return null;
//  }
//  protected ENTITY getFromCache(ID id)
//  {
//    return this.entityCache.get(id);
//  }
// /* protected ENTITY removeFromCache(ID id)
//  {
//    return this.entityCache.remove(id);
//  }*/
//  protected abstract ENTITY getFromDatabase(ID id);
//
//
//  protected ID claimToken(ID id) throws ModelArgumentException
//  {
//    return this.synchronizer.claimToken(id);
//  }
//  protected void returnToken(ID id) throws ModelArgumentException
//  {
//    this.synchronizer.returnToken(id);
//  }
//}
