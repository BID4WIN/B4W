//package trash;
//
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.exception.ModelArgumentException;
////import com.bid4win.commons.entity.Bid4WinEntity;
//
//public abstract class Bid4WinCache<KEY, VALUE>
//{
//  private Synchronizer<KEY> synchronizer = new Synchronizer<KEY>();
//  private Bid4WinMap<KEY, VALUE> entityCache = new Bid4WinMap<KEY, VALUE>();
//
//  public VALUE get(KEY id) throws ModelArgumentException
//  {
//    // Récupère
//    VALUE entity = this.getEntityCache().get(id);
//    if(entity == null)
//    {
//      synchronized(this.getSynchronizer().claimToken(id))
//      {
//        entity = this.getEntityCache().get(id);
//        if(entity == null)
//        {
//          //entity = this.getFromDatabase(id);
//        }
//      }
//      //this.returnToken(id);
//    }
//    return null;
//  }
//
//  public VALUE remove(KEY id)
//  {
//    return null;
//  }
//  protected abstract VALUE load(KEY id);
//
//
//  private Synchronizer<KEY> getSynchronizer()
//  {
//    return this.synchronizer;
//  }
//  private Bid4WinMap<KEY, VALUE> getEntityCache()
//  {
//    return this.entityCache;
//  }
//}
