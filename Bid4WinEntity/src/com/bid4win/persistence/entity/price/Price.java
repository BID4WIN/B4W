package com.bid4win.persistence.entity.price;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;

/**
 * Cette classe d�fini un prix bas� sur des montants dans diff�rentes monnaies
 * dont celle d�finie par d�faut<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Price extends AmountMap<Price>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Price()
  {
    super();
  }
  /**
   * Constructeur d'un prix d�fini uniquement dans la monnaie par d�faut
   * @param value Valeur du montant dans la monnaie par d�faut
   * @throws UserException Si les param�tres entr�s sont invalides
   * @throws UserException Si la construction du montant � ajouter s'av�re impossible
   */
  public Price(double value) throws UserException
  {
    super(new Amount(value));
  }
  /**
   * Constructeur d'un prix bas� sur des montants dans diff�rentes monnaies dont
   * celle d�finie par d�faut
   * @param amounts Montants du prix dans diff�rentes monnaies
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Price(Amount ... amounts) throws UserException
  {
    super(amounts);
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
    return CurrencyRef.CURRENCY_PRICE;
  }
}
