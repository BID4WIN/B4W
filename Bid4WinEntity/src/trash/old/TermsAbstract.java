package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;
//
///**
// * Cette classe défini les conditions de base d'une vente aux enchères<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class TermsAbstract<CLASS extends TermsAbstract<CLASS>> extends Bid4WinEmbeddable<CLASS>
//{
//  /** Nombre de crédits à utiliser par l'enchérisseur */
//  @Transient
//  private int creditNb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected TermsAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param creditNb Nombre de crédits à utiliser par l'enchérisseur
//   * @throws ModelArgumentException Si le nombre de crédits à utiliser est négatif
//   */
//  public TermsAbstract(int creditNb) throws ModelArgumentException
//  {
//    super();
//    this.defineCreditNb(creditNb);
//  }
//
//  /**
//   * Redéfini l'égalité interne de deux jeux de conditions de vente aux enchères
//   * par l'égalité de leur contenu
//   * @param toBeCompared {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
//   */
//  @Override
//  protected boolean equalsInternal(CLASS toBeCompared)
//  {
//    return this.getCreditNb() == toBeCompared.getCreditNb();
//  }
//  /**
//   * Redéfini la transformation en chaîne de caractères d'un jeu de condition de
//   * vente aux enchères lisiblement
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.core.Bid4WinObject#render()
//   */
//  @Override
//  public StringBuffer render()
//  {
//    return new StringBuffer("CREDIT_NB=" + this.getCreditNb());
//  }
//
//  /**
//   * Cette méthode permet de définir le nombre de crédits à utiliser l'enchérisseur
//   * @param creditNb Définition du nombre de crédits à utiliser par l'enchérisseur
//   * @throws ProtectionException Si les conditions d'enchère courantes sont protégées
//   * @throws ModelArgumentException Si on définie un nombre de crédits négatif
//   */
//  private void defineCreditNb(int creditNb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection des conditions d'enchère courantes
//    this.checkProtection();
//    this.setCreditNb(UtilNumber.checkMinValue("creditNb", creditNb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du nombre de crédits à utiliser par l'enchérisseur
//   * @return Le nombre de crédits à utiliser par l'enchérisseur
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "CREDIT_NB", length = 3, nullable = false, unique = false)
//  public int getCreditNb()
//  {
//    return this.creditNb;
//  }
//  /**
//   * Setter du nombre de crédits à utiliser par l'enchérisseur
//   * @param creditNb Nombre de crédits à utiliser par l'enchérisseur à positionner
//   */
//  private void setCreditNb(int creditNb)
//  {
//    this.creditNb = creditNb;
//  }
//}
