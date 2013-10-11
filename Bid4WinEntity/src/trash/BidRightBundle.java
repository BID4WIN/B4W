//package trash;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Cette classe défini un lot de droits à enchérir pour un compte utilisateur<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class BidRightBundle extends CreditBundle<BidRightBundle>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  @SuppressWarnings("unused")
//  private BidRightBundle()
//  {
//    super();
//  }
//  
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du lot de droits à enchérir
//   * @param nb Nombre de droits à enchérir du lot
//   * @throws ProtectionException Si le comte utilisateur en argument est protégé
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou si le nombre de droits à enchérir est inférieur à un
//   */
//  public BidRightBundle(Account account, int nb) throws ProtectionException, ModelArgumentException
//  {
//    super(account, nb);
//  }
//
//  /**
//   * Redéfini la méthode de création d'un lien entre un lot de crédits et son
//   * compte utilisateur afin d'ajouter la création du lien inverse
//   * @throws ProtectionException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see trash.CreditBundle#linkToAccount()
//   */
//  @Override
//  protected void linkToAccount() throws ProtectionException, ModelArgumentException
//  {
//    super.linkToAccount();
//    //this.getAccountLink().addBidRightBundle(this);
//  }
//  /**
//   * Redéfini la méthode de suppression d'un lien entre un lot de crédits et son
//   * compte utilisateur afin d'ajouter la suppression du lien inverse
//   * @return {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws ModelArgumentException {@inheritDoc}
//   * @see com.bid4win.persistence.entity.account.CreditBundle#unlinkFromAccount()
//   */
//  @Override
//  public Account unlinkFromAccount() throws ProtectionException, ModelArgumentException
//  {
//    Account account = super.unlinkFromAccount();
//    //account.removeBidRightBundle(this);
//    return account;
//  }
//}
