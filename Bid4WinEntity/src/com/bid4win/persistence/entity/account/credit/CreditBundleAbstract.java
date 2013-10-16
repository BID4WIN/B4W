package com.bid4win.persistence.entity.account.credit;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.price.Amount;

/**
 * Cette classe défini un lot de crédits pour un compte utilisateur<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class CreditBundleAbstract<CLASS extends CreditBundleAbstract<CLASS>>
       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
{
  /** Définition de la précision après la virgule par défaut de la valeur réelle unitaire des crédits */
  public final static int PRECISION = 4;

  /** Provenance des crédits du lot */
  @Transient private CreditOrigin origin = null;
  /** Valeur réelle unitaire des crédits */
  @Transient private Amount unitValue = null;
  /** Nombre de crédits du lot */
  @Transient private int nb = 0;

  /**
   * Constructeur pour création par introspection
   */
  protected CreditBundleAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param totalValue Valeur réelle totale des crédits du lot dans la monnaie par
   * défaut
   * @param nb Nombre de crédits du lot
   * @throws UserException Si le compte utilisateur des crédits ou leur provenance
   * est nul ou si leur valeur réelle unitaire est négative ou si leur nombre est
   * inférieur à un
   */
  protected CreditBundleAbstract(Account account, CreditOrigin origin,
                                 double totalValue, int nb)
            throws UserException
  {
    super(account);
    // Défini la provenance des crédits du lot
    this.defineOrigin(origin);
    // Défini le nombre de crédits du lot
    this.defineNb(nb);
    // Défini la valeur réelle totale des crédits du lot
    this.defineTotalValue(totalValue);
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de crédits
   * @param origin Provenance des crédits du lot
   * @param unitValue Valeur réelle unitaire des crédits du lot
   * @param nb Nombre de crédits du lot
   * @throws UserException Si le compte utilisateur des crédits, leur provenance
   * ou leur valeur réelle unitaire est null ou si leur nombre est inférieur à un
   */
  protected CreditBundleAbstract(Account account, CreditOrigin origin,
                                 Amount unitValue, int nb)
            throws UserException
  {
    super(account);
    // Défini la provenance des crédits du lot
    this.defineOrigin(origin);
    // Défini la valeur unitaire des crédits du lot
    this.defineUnitValue(unitValue);
    // Défini le nombre de crédits du lot
    this.defineNb(nb);
  }

  /**
   * Redéfini l'équivalence interne de deux lots de crédits sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs données propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getOrigin(),
                                                              toBeCompared.getOrigin()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getUnitValue(),
                                                              toBeCompared.getUnitValue()) &&
           this.getNb() == toBeCompared.getNb();
  }
  /**
   * Permet d'effectuer le rendu simple du lot de crédits courant sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments du lot de crédits
    buffer.append(" ").append(this.getOrigin().render());
    buffer.append(" UNIT_VALUE=").append(this.getUnitValue().render());
    buffer.append(" NB=" + this.getNb());
    // Retourne le rendu
    return buffer;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return AccountRef.CREDIT;
  }

  /**
   * Cette méthode permet de définir la provenance des crédits du lot
   * @param origin Définition due la provenance des crédits du lot
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si on défini une provenance de crédits nulle
   */
  private void defineOrigin(CreditOrigin origin) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    this.setOrigin(UtilObject.checkNotNull("origin", origin,
                                           AccountRef.CREDIT_REFERENCE_MISSING_ERROR));
  }
  /**
   * Cette méthode permet de définir la valeur réelle unitaire des crédits
   * @param totalValue Définition de la valeur réelle totale des crédits dans la
   * monnaie par défaut
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si la valeur réelle unitaire des crédits est négative
   */
  protected void defineTotalValue(double totalValue) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    this.setUnitValue(new Amount(totalValue / this.getNb(), CreditBundleAbstract.PRECISION));
  }
  /**
   * Cette méthode permet de définir la valeur réelle unitaire des crédits
   * @param unitValue Définition de la valeur réelle unitaire des crédits dans la
   * monnaie par défaut
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si la valeur réelle unitaire des crédits est nulle
   */
  private void defineUnitValue(Amount unitValue) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    this.setUnitValue(UtilObject.checkNotNull("unitValue", unitValue,
                                              CurrencyRef.AMOUNT_MISSING_ERROR));
  }
  /**
   * Cette méthode permet de définir le nombre de crédits du lot
   * @param nb Définition du nombre de crédits du lot
   * @throws ProtectionException Si le lot de crédits courant est protégé
   * @throws UserException Si on défini un nombre de crédits inférieur à un
   */
  private void defineNb(int nb) throws ProtectionException, UserException
  {
    // Vérifie la protection du lot de crédits courant
    this.checkProtection();
    this.setNb(UtilNumber.checkMinValue("nb", nb, 1, true,
                                        AccountRef.CREDIT_NB_INVALID_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la provenance des crédits du lot
   * @return La provenance des crédits du lot
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded
  public CreditOrigin getOrigin()
  {
    return this.origin;
  }
  /**
   * Setter de la provenance des crédits du lot
   * @param origin Provenance des crédits du lot à positionner
   */
  private void setOrigin(CreditOrigin  origin)
  {
    this.origin = origin;
  }

  /**
   * Getter de la valeur réelle unitaire des crédits
   * @return La valeur réelle unitaire des crédits
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "type", column = @Column(name = "UNIT_CURRENCY")),
                       @AttributeOverride(name = "value", column = @Column(name = "UNIT_VALUE"))})
  public Amount getUnitValue()
  {
    return this.unitValue;
  }
  /**
   * Setter de la valeur réelle unitaire des crédits
   * @param unitValue Valeur réelle unitaire des crédits à positionner
   */
  private void setUnitValue(Amount unitValue)
  {
    this.unitValue = unitValue;
  }

  /**
   * Getter du nombre de crédits du lot
   * @return Le nombre de crédits du lot
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB", length = 3, nullable = false, unique = false)
  public int getNb()
  {
    return this.nb;
  }
  /**
   * Setter du nombre de crédits du lot
   * @param nb Nombre de crédits du lot à positionner
   */
  private void setNb(int nb)
  {
    this.nb = nb;
  }
}
