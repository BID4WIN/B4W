//package trash;
//
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinRelation;
//import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
//import com.bid4win.commons.persistence.entity.Bid4WinRelation.Type;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Metamodel de la classe CreditBundle<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@StaticMetamodel(CreditBundle.class)
//public abstract class CreditBundle_ extends Credit_
//{
//  /** D�finition du lien vers le compte utilisateur du lot de cr�dits */
//  public static volatile SingularAttribute<CreditBundle<?>, Account> accountLink;
//  /** D�finition du nombre initial de cr�dits du lot */
//  public static volatile SingularAttribute<CreditBundle<?>, Integer> initialNb;
//
//  /** D�fini la relation existant avec le compte utilisateur */
//  public final static Bid4WinRelation RELATION_ACCOUNT_LINK = new Bid4WinRelation("ACCOUNT_LINK",
//                                                                                  Type.SIMPLE);
//  /** D�fini le noeud de relation existant avec le compte utilisateur */
//  public final static Bid4WinRelationNode NODE_ACCOUNT_LINK = new Bid4WinRelationNode(CreditBundle_.RELATION_ACCOUNT_LINK);
//}
