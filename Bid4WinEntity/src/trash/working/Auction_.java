//package trash.working;
//
//import javax.persistence.metamodel.MapAttribute;
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID_;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.product.Product;
//
///**
// * Metamodel de la classe Auction<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@StaticMetamodel(Auction.class)
//public abstract class Auction_ extends Bid4WinEntityStub_
//{
//  /** Définition du produit vendu aux enchères */
//  public static volatile SingularAttribute<Auction<?, ?>, Product> product;
//  /** Définition du nombre d'enchères placées sur la vente */
//  public static volatile SingularAttribute<Auction<?, ?>, Integer> bidNb;
//  /** Définition de la map des dernières enchères placées sur la vente */
//  public static volatile MapAttribute<Auction<?, ?>, Integer, Bid> lastBidMapInternal;
//
//  /** Défini la relation existant avec le produit vendu enchères */
//  public final static Bid4WinRelation RELATION_PRODUCT = new Bid4WinRelation("PRODUCT",
//                                                                             Type.SIMPLE);
//  /** Défini le noeud de relation existant avec le produit vendu aux enchères */
//  public final static Bid4WinRelationNode NODE_PRODUCT = new Bid4WinRelationNode(RELATION_PRODUCT);
//  /** Défini la relation existant avec la map des dernières enchères */
//  public final static Bid4WinRelation RELATION_LAST_BID_MAP = new Bid4WinRelation("LAST_BID_MAP",
//                                                                                  Type.MAP);
//  /** Défini le noeud de relation existant avec la map des dernières enchères */
//  public final static Bid4WinRelationNode NODE_LAST_BID_MAP = new Bid4WinRelationNode(RELATION_LAST_BID_MAP);
//}
