package com.bid4win.persistence.entity.price;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;

/**
 * Cette classe d�fini un montant dans une certaine monnaie<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "AMOUNT_CURRENCY"))
public class Amount extends Bid4WinEmbeddableWithType<Amount, Currency>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -6790116682753527223L;
  /** D�finition de la pr�cision apr�s la virgule par d�faut d'un montant */
  public final static int PRECISION = 2;

  /** Valeur du montant */
  @Transient private double value = 0;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Amount()
  {
    super();
  }
  /**
   * Constructeur d'un montant dans la monnaie par d�faut
   * @param value Valeur du montant dans la monnaie par d�faut
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Amount(double value) throws UserException
  {
    this(Currency.DEFAULT, value);
  }
  /**
   * Constructeur dans une monnaie d�finie
   * @param currency Monnaie du montant
   * @param value Valeur du montant dans la monnaie d�finie
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Amount(Currency currency, double value) throws UserException
  {
    this(currency, value, Amount.PRECISION);
  }
  /**
   * Constructeur d'un montant dans la monnaie par d�faut
   * @param value Valeur du montant dans la monnaie par d�faut
   * @param precision Pr�cision apr�s la virgule attendue pour la valeur du montant
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Amount(double value, int precision) throws UserException
  {
    this(Currency.DEFAULT, value, precision);
  }
  /**
   * Constructeur dans une monnaie d�finie
   * @param currency Monnaie du montant
   * @param value Valeur du montant dans la monnaie d�finie
   * @param precision Pr�cision apr�s la virgule attendue pour la valeur du montant
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Amount(Currency currency, double value, int precision) throws UserException
  {
    super(currency);
    this.defineValue(value, precision);
  }

  /**
   * Red�fini l'�galit� interne de deux montants par l'�galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType)
   */
  @Override
  protected boolean equalsInternal(Amount toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getValue() == toBeCompared.getValue();
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un montant lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append("|" + this.getValue());
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#getMessageRefBase()
   */
  @Override
  protected MessageRef getMessageRefBase()
  {
    return CurrencyRef.AMOUNT;
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeAdded TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public Amount add(Amount toBeAdded) throws UserException
  {
    if(toBeAdded == null)
    {
      return this;
    }
    UtilObject.checkEquals("currency", toBeAdded.getCurrency(), this.getCurrency(),
                           CurrencyRef.CURRENCY_INVALID_ERROR);
    double value = this.getValue() + toBeAdded.getValue();
    return new Amount(this.getCurrency(), value, UtilNumber.getDecimalNb(value));
  }
  /**
   *
   * TODO A COMMENTER
   * @param factor TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public Amount multiply(int factor) throws UserException
  {
    double value = this.getValue() * factor;
    return new Amount(this.getCurrency(), value, UtilNumber.getDecimalNb(value));
  }

  /**
   * Getter de la monnaie du montant
   * @return La monnaie du montant
   */
  public Currency getCurrency()
  {
    return this.getType();
  }
  /**
   * Cette m�thode permet de d�finir la valeur du montant
   * @param value D�finition de la valeur du montant
   * @param precision Pr�cision apr�s la virgule attendue pour la valeur du montant
   * @throws ProtectionException Si le montant courant est prot�g�
   * @throws UserException Si la valeur du montant est n�gative
   */
  private void defineValue(double value, int precision) throws ProtectionException, UserException
  {
    this.checkProtection();
    value = UtilNumber.checkMinValue("value", value, 0, true,
                                     CurrencyRef.AMOUNT_INVALID_ERROR);
    precision = Math.max(0, precision);
    this.setValue(Math.round(value * Math.pow(10, precision)) /
                  Math.pow(10, precision));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la valeur du montant
   * @return La valeur du montant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "AMOUNT_VALUE", length = 10, nullable = false, unique = false)
  public double getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur du montant
   * @param value Valeur du montant � positionner
   */
  private void setValue(double value)
  {
    this.value = value;
  }
}
