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
// * @author Emeric Fill�tre
// */
//@StaticMetamodel(Auction.class)
//public abstract class Auction_ extends Bid4WinEntityStub_
//{
//  /** D�finition du produit vendu aux ench�res */
//  public static volatile SingularAttribute<Auction<?, ?>, Product> product;
//  /** D�finition du nombre d'ench�res plac�es sur la vente */
//  public static volatile SingularAttribute<Auction<?, ?>, Integer> bidNb;
//  /** D�finition de la map des derni�res ench�res plac�es sur la vente */
//  public static volatile MapAttribute<Auction<?, ?>, Integer, Bid> lastBidMapInternal;
//
//  /** D�fini la relation existant avec le produit vendu ench�res */
//  public final static Bid4WinRelation RELATION_PRODUCT = new Bid4WinRelation("PRODUCT",
//                                                                             Type.SIMPLE);
//  /** D�fini le noeud de relation existant avec le produit vendu aux ench�res */
//  public final static Bid4WinRelationNode NODE_PRODUCT = new Bid4WinRelationNode(RELATION_PRODUCT);
//  /** D�fini la relation existant avec la map des derni�res ench�res */
//  public final static Bid4WinRelation RELATION_LAST_BID_MAP = new Bid4WinRelation("LAST_BID_MAP",
//                                                                                  Type.MAP);
//  /** D�fini le noeud de relation existant avec la map des derni�res ench�res */
//  public final static Bid4WinRelationNode NODE_LAST_BID_MAP = new Bid4WinRelationNode(RELATION_LAST_BID_MAP);
//}
