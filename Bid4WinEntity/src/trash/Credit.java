//package trash;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//import com.bid4win.commons.core.UtilNumber;
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
//import com.bid4win.persistence.entity.account.Account;
//
///**
// * Cette classe défini un crédit pour un compte utilisateur<BR>
// * <BR>
// * @param <CLASS> Doit définir la classe réellement instanciée<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class Credit<CLASS extends Credit<CLASS>>
//       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
//       implements Cloneable
//{
//  /** Nombre associé au crédit */
//  @Transient
//  private int nb = 0;
//
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected Credit()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du crédit
//   * @param nb Nombre associé au crédit
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou le nombre associé au crédit est inférieur à zéro
//   */
//  protected Credit(Account account, int nb) throws ModelArgumentException
//  {
//    super(account);
//    try
//    {
//      // Défini le nombre associé au crédit
//      this.defineNb(nb);
//    }
//    catch(ProtectionException ex)
//    {
//      // Ne peut pas arriver car l'objet est en construction
//      ex.printStackTrace();
//    }
//  }
//  /**
//   * Constructeur par copie
//   * @param credit Crédit à recopier
//   * @throws ModelArgumentException Si le crédit en argument est nul
//   */
//  protected Credit(Credit<?> credit) throws ModelArgumentException
//  {
//    this(UtilObject.checkNotNull("credit", credit).getAccount(), credit.getNb());
//  }
//
//  /**
//   * Redéfini l'équivalence interne de deux crédits sans prise en compte de leurs
//   * relations afin d'y ajouter le test de leurs données propres
//   * @param toBeCompared {@inheritDoc}
//   * @param identical {@inheritDoc}
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
//   */
//  @Override
//  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
//  {
//    return super.sameRelationNoneInternal(toBeCompared, identical) &&
//           this.getNb() == toBeCompared.getNb();
//
//  }
//  /**
//   * Cette méthode permet de cloner le crédit courant (sans clone des objets contenus)
//   * et de le retourner casté en tant que tel
//   * @return {@inheritDoc}
//   * @see java.lang.Object#clone()
//   */
//  @Override
//  public CLASS clone()
//  {
//    try
//    {
//      return (CLASS)super.clone();
//    }
//    catch(CloneNotSupportedException ex)
//    {
//      // La classe implémente l'interface Cloneable
//      ex.printStackTrace();
//      return null;
//    }
//  }
//
//  /**
//   * Cette méthode permet de définir le nombre associé au crédit
//   * @param nb Définition du nombre associé au crédit
//   * @throws ProtectionException Si le crédit courant est protégé
//   * @throws ModelArgumentException Si on définie un nombre inférieur à zéro
//   */
//  protected void defineNb(int nb) throws ProtectionException, ModelArgumentException
//  {
//    // Vérifie la protection du crédit courant
//    this.checkProtection();
//    this.setNb(UtilNumber.checkMinValue("nb", nb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du nombre associé au crédit
//   * @return Le nombre associé au crédit
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "NB", length = 3, nullable = false, unique = false)
//  public int getNb()
//  {
//    return this.nb;
//  }
//  /**
//   * Setter du nombre associé au crédit
//   * @param nb Nombre associé au crédit à positionner
//   */
//  private void setNb(int nb)
//  {
//    this.nb = nb;
//  }
//}
