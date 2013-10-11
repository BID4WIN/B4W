//package trash;
//
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
//import com.bid4win.persistence.entity.account.BidRight_;
//
///**
// * Metamodel de la classe Bid<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@StaticMetamodel(Bid.class)
//public abstract class Bid_ extends BidRight_
//{
//  /** D�finition de la position de l'ench�re sur la vente */
//  public static volatile SingularAttribute<Bid, Integer> position;
//  /** D�finition de l'identifiant de la vente de l'ench�re */
//  public static volatile SingularAttribute<Bid, String> auctionId;
//  /** D�finition du lien vers la vente de l'ench�re */
//  public static volatile SingularAttribute<Bid, Auction> auctionLink;
//
//  /** D�fini la relation existant avec la vente */
//  public final static Bid4WinRelation RELATION_AUCTION_LINK = new Bid4WinRelation("AUCTION_LINK",
//                                                                                  Type.SIMPLE);
//  /** D�fini le noeud de relation existant avec la vente */
//  public final static Bid4WinRelationNode NODE_AUCTION_LINK = new Bid4WinRelationNode(RELATION_AUCTION_LINK);
//}
