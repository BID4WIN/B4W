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
// * Cette classe d�fini les conditions de base d'une vente aux ench�res<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class TermsAbstract<CLASS extends TermsAbstract<CLASS>> extends Bid4WinEmbeddable<CLASS>
//{
//  /** Nombre de cr�dits � utiliser par l'ench�risseur */
//  @Transient
//  private int creditNb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected TermsAbstract()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param creditNb Nombre de cr�dits � utiliser par l'ench�risseur
//   * @throws ModelArgumentException Si le nombre de cr�dits � utiliser est n�gatif
//   */
//  public TermsAbstract(int creditNb) throws ModelArgumentException
//  {
//    super();
//    this.defineCreditNb(creditNb);
//  }
//
//  /**
//   * Red�fini l'�galit� interne de deux jeux de conditions de vente aux ench�res
//   * par l'�galit� de leur contenu
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
//   * Red�fini la transformation en cha�ne de caract�res d'un jeu de condition de
//   * vente aux ench�res lisiblement
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
//   * Cette m�thode permet de d�finir le nombre de cr�dits � utiliser l'ench�risseur
//   * @param creditNb D�finition du nombre de cr�dits � utiliser par l'ench�risseur
//   * @throws ProtectionException Si les conditions d'ench�re courantes sont prot�g�es
//   * @throws ModelArgumentException Si on d�finie un nombre de cr�dits n�gatif
//   */
//  private void defineCreditNb(int creditNb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection des conditions d'ench�re courantes
//    this.checkProtection();
//    this.setCreditNb(UtilNumber.checkMinValue("creditNb", creditNb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du nombre de cr�dits � utiliser par l'ench�risseur
//   * @return Le nombre de cr�dits � utiliser par l'ench�risseur
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "CREDIT_NB", length = 3, nullable = false, unique = false)
//  public int getCreditNb()
//  {
//    return this.creditNb;
//  }
//  /**
//   * Setter du nombre de cr�dits � utiliser par l'ench�risseur
//   * @param creditNb Nombre de cr�dits � utiliser par l'ench�risseur � positionner
//   */
//  private void setCreditNb(int creditNb)
//  {
//    this.creditNb = creditNb;
//  }
//}
