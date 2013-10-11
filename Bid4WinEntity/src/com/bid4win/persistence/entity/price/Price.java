package com.bid4win.persistence.entity.price;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;

/**
 * Cette classe défini un prix basé sur des montants dans différentes monnaies
 * dont celle définie par défaut<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class Price extends AmountMap<Price>
{
  /**
   * Constructeur pour création par introspection
   */
  protected Price()
  {
    super();
  }
  /**
   * Constructeur d'un prix défini uniquement dans la monnaie par défaut
   * @param value Valeur du montant dans la monnaie par défaut
   * @throws UserException Si les paramètres entrés sont invalides
   * @throws UserException Si la construction du montant à ajouter s'avère impossible
   */
  public Price(double value) throws UserException
  {
    super(new Amount(value));
  }
  /**
   * Constructeur d'un prix basé sur des montants dans différentes monnaies dont
   * celle définie par défaut
   * @param amounts Montants du prix dans différentes monnaies
   * @throws UserException Si les paramètres entrés sont invalides
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
