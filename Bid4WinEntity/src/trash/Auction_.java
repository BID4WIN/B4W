//package trash;
//
//import javax.persistence.metamodel.MapAttribute;
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
//
///**
// * Metamodel de la classe Auction<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@StaticMetamodel(Auction.class)
//public abstract class Auction_ extends Bid4WinEntity_
//{
//  /** D�finition de l'identifiant de la vente aux ench�res */
//  public static volatile SingularAttribute<Auction, String> id;
//  /** D�finition du nombre d'ench�res plac�es sur la vente */
//  public static volatile SingularAttribute<Auction, Integer> bidNb;
//  /** D�finition de la map des derni�res ench�res plac�es sur la vente */
//  public static volatile MapAttribute<Auction, Integer, Bid> lastBidMapInternal;
//
//  /** D�fini la relation existant avec la map des derni�res ench�res */
//  public final static Bid4WinRelation RELATION_LAST_BID_MAP = new Bid4WinRelation("LAST_BID_MAP",
//                                                                                  Type.MAP);
//  /** D�fini le noeud de relation existant avec la map des derni�res ench�res */
//  public final static Bid4WinRelationNode NODE_LAST_BID_MAP = new Bid4WinRelationNode(RELATION_LAST_BID_MAP);
//}
