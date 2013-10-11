//package trash.old;
//
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Metamodel de la classe BidRight<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@StaticMetamodel(BidRight.class)
//public abstract class BidRight_ extends AccountBasedEntityMultipleAutoID_
//{
//  /** Définition du lien vers le compte utilisateur du droit à enchérir */
//  @SuppressWarnings("all")
//  public static volatile SingularAttribute<BidRight, Account> accountLink;
//
//  /** Défini la relation existant avec le compte utilisateur */
//  public final static Bid4WinRelation RELATION_ACCOUNT_LINK = new Bid4WinRelation("ACCOUNT_LINK",
//                                                                                  Type.SIMPLE);
//  /** Défini le noeud de relation existant avec le compte utilisateur */
//  public final static Bid4WinRelationNode NODE_ACCOUNT_LINK = new Bid4WinRelationNode(BidRight_.RELATION_ACCOUNT_LINK);
//}
