//package trash.working;
//
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.persistence.entity.account.credit.Credit_;
//
///**
// * Metamodel de la classe Bid<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@StaticMetamodel(Bid.class)
//public abstract class Bid_ extends Credit_
//{
//  /** Définition de la position de l'enchère sur la vente */
//  public static volatile SingularAttribute<Bid<?, ?>, Integer> position;
//  /** Définition de la vente de l'enchère */
//  public static volatile SingularAttribute<Bid<?, ?>, Auction<?, ?>> auction;
//  /** Définition du lien vers la vente de l'enchère */
//  public static volatile SingularAttribute<Bid<?, ?>, Auction<?, ?>> auctionLink;
//
//  /** Défini la relation existant avec la vente */
//  public final static Bid4WinRelation RELATION_AUCTION = new Bid4WinRelation("AUCTION",
//                                                                             Type.SIMPLE);
//  /** Défini le noeud de relation existant avec la vente */
//  public final static Bid4WinRelationNode NODE_AUCTION = new Bid4WinRelationNode(RELATION_AUCTION);
//  /** Défini la relation existant avec la vente */
//  public final static Bid4WinRelation RELATION_AUCTION_LINK = new Bid4WinRelation("AUCTION_LINK",
//                                                                                  Type.SIMPLE);
//  /** Défini le noeud de relation existant avec la vente */
//  public final static Bid4WinRelationNode NODE_AUCTION_LINK = new Bid4WinRelationNode(RELATION_AUCTION_LINK);
//}
