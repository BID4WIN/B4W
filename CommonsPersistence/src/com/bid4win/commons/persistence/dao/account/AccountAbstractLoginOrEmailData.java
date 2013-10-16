//package com.bid4win.commons.persistence.dao.account;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.From;
//import javax.persistence.criteria.Predicate;
//
//import com.bid4win.commons.persistence.entity.Bid4WinEntity_.Bid4WinField;
//import com.bid4win.commons.persistence.entity.account.AccountAbstract;
//import com.bid4win.commons.persistence.entity.account.security.Login;
//import com.bid4win.commons.persistence.entity.account.security.Login_;
//import com.bid4win.commons.persistence.entity.contact.Email;
//import com.bid4win.commons.persistence.entity.contact.Email_;
//import com.bid4win.commons.persistence.request.Bid4WinData;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <ACCOUNT> TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class AccountAbstractLoginOrEmailData<ACCOUNT extends AccountAbstract<?>>
//       extends Bid4WinData<ACCOUNT, String>
//{
//  /**
//   * A définir car :
//   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
//   * en compte si défini dans le metamodel de la super class : bug HHH-5024
//   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
//   */
//  private Bid4WinField<? super ACCOUNT, Login> loginField = null;
//  /**
//   * A définir car :
//   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
//   * en compte si défini dans le metamodel de la super class : bug HHH-5024
//   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
//   */
//  private Bid4WinField<? super ACCOUNT, Email> emailField = null;
//
//  /**
//   *
//   * TODO A COMMENTER
//   * les 2 champs sont à définir car :
//   * Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
//   * en compte si défini dans le metamodel de la super class : bug HHH-5024
//   * TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
//   * @param loginField TODO A COMMENTER
//   * @param emailField TODO A COMMENTER
//   * @param loginOrEmail TODO A COMMENTER
//   */
//  public AccountAbstractLoginOrEmailData(Bid4WinField<? super ACCOUNT, Login> loginField,
//                                         Bid4WinField<? super ACCOUNT, Email> emailField,
//                                         String loginOrEmail)
//  {
//    super(null, loginOrEmail);
//    this.loginField = loginField;
//    this.emailField = emailField;
//  }
//
//  /**
//   *
//   * TODO A COMMENTER
//   * @param builder {@inheritDoc}
//   * @param from_ {@inheritDoc}
//   * @param value {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.request.Bid4WinData#buildCondition(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.From, java.lang.Object)
//   */
//  @Override
//  protected Predicate buildCondition(CriteriaBuilder builder,
//                                     From<? extends ACCOUNT, ? extends ACCOUNT> from_,
//                                     String value)
//  {
//    return this.combineConditions(
//        builder, this.buildCondition(builder, this.loginField.getPath(from_).get(Login_.value), value),
//                 this.buildCondition(builder, this.emailField.getPath(from_).get(Email_.address), value));
//  }
//}
