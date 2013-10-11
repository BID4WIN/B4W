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
// * Cette classe d�fini un cr�dit pour un compte utilisateur<BR>
// * <BR>
// * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
////Annotation pour la persistence
//@MappedSuperclass
//@Access(AccessType.FIELD)
//public class Credit<CLASS extends Credit<CLASS>>
//       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
//       implements Cloneable
//{
//  /** Nombre associ� au cr�dit */
//  @Transient
//  private int nb = 0;
//
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected Credit()
//  {
//    super();
//  }
//
//  /**
//   * Constructeur complet
//   * @param account Compte utilisateur du cr�dit
//   * @param nb Nombre associ� au cr�dit
//   * @throws ModelArgumentException Si le compte utilisateur en argument est nul
//   * ou le nombre associ� au cr�dit est inf�rieur � z�ro
//   */
//  protected Credit(Account account, int nb) throws ModelArgumentException
//  {
//    super(account);
//    try
//    {
//      // D�fini le nombre associ� au cr�dit
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
//   * @param credit Cr�dit � recopier
//   * @throws ModelArgumentException Si le cr�dit en argument est nul
//   */
//  protected Credit(Credit<?> credit) throws ModelArgumentException
//  {
//    this(UtilObject.checkNotNull("credit", credit).getAccount(), credit.getNb());
//  }
//
//  /**
//   * Red�fini l'�quivalence interne de deux cr�dits sans prise en compte de leurs
//   * relations afin d'y ajouter le test de leurs donn�es propres
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
//   * Cette m�thode permet de cloner le cr�dit courant (sans clone des objets contenus)
//   * et de le retourner cast� en tant que tel
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
//      // La classe impl�mente l'interface Cloneable
//      ex.printStackTrace();
//      return null;
//    }
//  }
//
//  /**
//   * Cette m�thode permet de d�finir le nombre associ� au cr�dit
//   * @param nb D�finition du nombre associ� au cr�dit
//   * @throws ProtectionException Si le cr�dit courant est prot�g�
//   * @throws ModelArgumentException Si on d�finie un nombre inf�rieur � z�ro
//   */
//  protected void defineNb(int nb) throws ProtectionException, ModelArgumentException
//  {
//    // V�rifie la protection du cr�dit courant
//    this.checkProtection();
//    this.setNb(UtilNumber.checkMinValue("nb", nb, 0, true));
//  }
//
//  /** #################################################################### **/
//  /** ########################### PERSISTENCE ############################ **/
//  /** #################################################################### **/
//  /**
//   * Getter du nombre associ� au cr�dit
//   * @return Le nombre associ� au cr�dit
//   */
//  // Annotation pour la persistence
//  @Access(AccessType.PROPERTY)
//  @Column(name = "NB", length = 3, nullable = false, unique = false)
//  public int getNb()
//  {
//    return this.nb;
//  }
//  /**
//   * Setter du nombre associ� au cr�dit
//   * @param nb Nombre associ� au cr�dit � positionner
//   */
//  private void setNb(int nb)
//  {
//    this.nb = nb;
//  }
//}
