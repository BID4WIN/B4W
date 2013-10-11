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
// * Cette classe d�fini un lot de droits � ench�rir pour un compte utilisateur<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class BidRightBundle extends CreditBundle<BidRightBundle>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  @SuppressWarnings("unused")
//  private BidRightBundle()
//  {
//    super();
//  }
//  
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du lot de droits � ench�rir
//   * @param nb Nombre de droits � ench�rir du lot
//   * @throws ProtectionException Si le comte utilisateur en argument est prot�g�
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou si le nombre de droits � ench�rir est inf�rieur � un
//   */
//  public BidRightBundle(Account account, int nb) throws ProtectionException, ModelArgumentException
//  {
//    super(account, nb);
//  }
//
//  /**
//   * Red�fini la m�thode de cr�ation d'un lien entre un lot de cr�dits et son
//   * compte utilisateur afin d'ajouter la cr�ation du lien inverse
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
//   * Red�fini la m�thode de suppression d'un lien entre un lot de cr�dits et son
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
