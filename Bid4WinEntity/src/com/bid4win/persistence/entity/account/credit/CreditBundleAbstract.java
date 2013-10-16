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
 * Cette classe d�fini un lot de cr�dits pour un compte utilisateur<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class CreditBundleAbstract<CLASS extends CreditBundleAbstract<CLASS>>
       extends AccountBasedEntityMultipleAutoID<CLASS, Account>
{
  /** D�finition de la pr�cision apr�s la virgule par d�faut de la valeur r�elle unitaire des cr�dits */
  public final static int PRECISION = 4;

  /** Provenance des cr�dits du lot */
  @Transient private CreditOrigin origin = null;
  /** Valeur r�elle unitaire des cr�dits */
  @Transient private Amount unitValue = null;
  /** Nombre de cr�dits du lot */
  @Transient private int nb = 0;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected CreditBundleAbstract()
  {
    super();
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param totalValue Valeur r�elle totale des cr�dits du lot dans la monnaie par
   * d�faut
   * @param nb Nombre de cr�dits du lot
   * @throws UserException Si le compte utilisateur des cr�dits ou leur provenance
   * est nul ou si leur valeur r�elle unitaire est n�gative ou si leur nombre est
   * inf�rieur � un
   */
  protected CreditBundleAbstract(Account account, CreditOrigin origin,
                                 double totalValue, int nb)
            throws UserException
  {
    super(account);
    // D�fini la provenance des cr�dits du lot
    this.defineOrigin(origin);
    // D�fini le nombre de cr�dits du lot
    this.defineNb(nb);
    // D�fini la valeur r�elle totale des cr�dits du lot
    this.defineTotalValue(totalValue);
  }
  /**
   * Constructeur complet
   * @param account Compte utilisateur du lot de cr�dits
   * @param origin Provenance des cr�dits du lot
   * @param unitValue Valeur r�elle unitaire des cr�dits du lot
   * @param nb Nombre de cr�dits du lot
   * @throws UserException Si le compte utilisateur des cr�dits, leur provenance
   * ou leur valeur r�elle unitaire est null ou si leur nombre est inf�rieur � un
   */
  protected CreditBundleAbstract(Account account, CreditOrigin origin,
                                 Amount unitValue, int nb)
            throws UserException
  {
    super(account);
    // D�fini la provenance des cr�dits du lot
    this.defineOrigin(origin);
    // D�fini la valeur unitaire des cr�dits du lot
    this.defineUnitValue(unitValue);
    // D�fini le nombre de cr�dits du lot
    this.defineNb(nb);
  }

  /**
   * Red�fini l'�quivalence interne de deux lots de cr�dits sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
   * Permet d'effectuer le rendu simple du lot de cr�dits courant sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments du lot de cr�dits
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
   * Cette m�thode permet de d�finir la provenance des cr�dits du lot
   * @param origin D�finition due la provenance des cr�dits du lot
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si on d�fini une provenance de cr�dits nulle
   */
  private void defineOrigin(CreditOrigin origin) throws ProtectionException, UserException
  {
    // V�rifie la protection du lot de cr�dits courant
    this.checkProtection();
    this.setOrigin(UtilObject.checkNotNull("origin", origin,
                                           AccountRef.CREDIT_REFERENCE_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir la valeur r�elle unitaire des cr�dits
   * @param totalValue D�finition de la valeur r�elle totale des cr�dits dans la
   * monnaie par d�faut
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si la valeur r�elle unitaire des cr�dits est n�gative
   */
  protected void defineTotalValue(double totalValue) throws ProtectionException, UserException
  {
    // V�rifie la protection du lot de cr�dits courant
    this.checkProtection();
    this.setUnitValue(new Amount(totalValue / this.getNb(), CreditBundleAbstract.PRECISION));
  }
  /**
   * Cette m�thode permet de d�finir la valeur r�elle unitaire des cr�dits
   * @param unitValue D�finition de la valeur r�elle unitaire des cr�dits dans la
   * monnaie par d�faut
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si la valeur r�elle unitaire des cr�dits est nulle
   */
  private void defineUnitValue(Amount unitValue) throws ProtectionException, UserException
  {
    // V�rifie la protection du lot de cr�dits courant
    this.checkProtection();
    this.setUnitValue(UtilObject.checkNotNull("unitValue", unitValue,
                                              CurrencyRef.AMOUNT_MISSING_ERROR));
  }
  /**
   * Cette m�thode permet de d�finir le nombre de cr�dits du lot
   * @param nb D�finition du nombre de cr�dits du lot
   * @throws ProtectionException Si le lot de cr�dits courant est prot�g�
   * @throws UserException Si on d�fini un nombre de cr�dits inf�rieur � un
   */
  private void defineNb(int nb) throws ProtectionException, UserException
  {
    // V�rifie la protection du lot de cr�dits courant
    this.checkProtection();
    this.setNb(UtilNumber.checkMinValue("nb", nb, 1, true,
                                        AccountRef.CREDIT_NB_INVALID_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la provenance des cr�dits du lot
   * @return La provenance des cr�dits du lot
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded
  public CreditOrigin getOrigin()
  {
    return this.origin;
  }
  /**
   * Setter de la provenance des cr�dits du lot
   * @param origin Provenance des cr�dits du lot � positionner
   */
  private void setOrigin(CreditOrigin  origin)
  {
    this.origin = origin;
  }

  /**
   * Getter de la valeur r�elle unitaire des cr�dits
   * @return La valeur r�elle unitaire des cr�dits
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
   * Setter de la valeur r�elle unitaire des cr�dits
   * @param unitValue Valeur r�elle unitaire des cr�dits � positionner
   */
  private void setUnitValue(Amount unitValue)
  {
    this.unitValue = unitValue;
  }

  /**
   * Getter du nombre de cr�dits du lot
   * @return Le nombre de cr�dits du lot
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "CREDIT_NB", length = 3, nullable = false, unique = false)
  public int getNb()
  {
    return this.nb;
  }
  /**
   * Setter du nombre de cr�dits du lot
   * @param nb Nombre de cr�dits du lot � positionner
   */
  private void setNb(int nb)
  {
    this.nb = nb;
  }
}
