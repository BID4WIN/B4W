package com.bid4win.persistence.entity.price;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AmountMap<CLASS extends AmountMap<CLASS>>
       extends Bid4WinEmbeddableWithTypeMap<CLASS, Amount, Currency>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AmountMap()
  {
    super();
  }
  /**TODO A COMMENTER
   * Constructeur
   * @param amounts Objets associés à des types définis à référencer
   * @throws UserException Si plus d'un des objets en argument est lié au même
   * type ou si aucun n'est lié au type défini par défaut
   */
  public AmountMap(Amount ... amounts) throws UserException
  {
    super(amounts);
  }

  /**
   * Cette méthode défini la classe de monnaie liée aux montants du prix
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getTypeClass()
   */
  @Override
  public Class<Currency> getTypeClass()
  {
    return Currency.class;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getDefaultType()
   */
  @Override
  public Currency getDefaultType()
  {
    return Currency.DEFAULT;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  @SuppressWarnings("unchecked")
  public CLASS copy()
  {
    try
    {
      CLASS result = (CLASS)this.getClass().newInstance();
      for(Amount amount : this.getEmbeddedMap().values())
      {
        result.addEmbedded(amount);
      }
      return result;
    }
    catch(Exception ex)
    {
      // TODO Auto-generated catch block
      ex.printStackTrace();
      return null;
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public double getValue()
  {
    return this.getEmbedded().getValue();
  }
  /**
   *
   * TODO A COMMENTER
   * @param currency TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public double getValue(Currency currency) throws UserException
  {
    Amount amount = this.getEmbedded(currency);
    if(!amount.getCurrency().equals(currency))
    {
      throw new UserException(CurrencyRef.CURRENCY_UNDEFINED_ERROR);
    }
    return amount.getValue();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Amount getAmount()
  {
    return this.getEmbedded();
  }
  /**
   *
   * TODO A COMMENTER
   * @param currency TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Amount getAmount(Currency currency)
  {
    return this.getEmbedded(currency);
  }
  /**
   *
   * TODO A COMMENTER
   * @param currency TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws UserException Si la construction du montant à ajouter s'avère impossible
   */
  public Amount addAmout(Currency currency, double value)
         throws ProtectionException, UserException
  {
    Amount amount = new Amount(currency, value);
    this.addEmbedded(amount);
    return amount;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la map des montants associés à des monnaies
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#getEmbeddedMap()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "AMOUNT_MAP", length = 250, nullable = false, unique = false)
  @Type(type = "AMOUNT_MAP")
  protected Bid4WinMap<Currency, Amount> getEmbeddedMap()
  {
    return super.getEmbeddedMap();
  }
}
