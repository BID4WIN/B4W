package com.bid4win.persistence.entity.price;

import java.util.Map.Entry;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.CurrencyRef;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe défini défini les taux de change de différentes monnaies par rapport
 * à celle définie par défaut<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class ExchangeRates extends AmountMap<ExchangeRates>
{
  /** Définition de la précision après la virgule par défaut d'un taux de change */
  public final static int PRECISION = 4;

  /**
   * Constructeur pour création par introspection
   */
  protected ExchangeRates()
  {
    super();
  }
  /**
   * Constructeur
   * @param rates Map des taux de change de différentes monnaies par rapport à
   * celle définie par défaut
   * @throws UserException Si la valeur d'un des taux de change en argument n'est
   * pas supérieure à zéro
   */
  public ExchangeRates(Bid4WinMap<Currency, Double> rates) throws UserException
  {
    super(new Amount(1));
    for(Entry<Currency, Double> rate : rates.entrySet())
    {
      if(!rate.getKey().equals(Currency.DEFAULT))
      {
        this.addEmbedded(new Amount(rate.getKey(), rate.getValue(), ExchangeRates.PRECISION));
      }
    }
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
    return CurrencyRef.EXCHANGE_RATE;
  }

  /**
   * Cette méthode permet de calculer la valeur du montant en argument dans la
   * monnaie précisée. Si la monnaie choisie n'est pas défini parmi les taux de
   * changes, la valeur retournée sera calculée dans la monnaie par défaut
   * @param amount Montant à calculer dans la monnaie choisie
   * @param currency Monnaie dans laquelle calculer le montant en argument
   * @return La valeur du montant en argument dans la monnaie précisée ou dans
   * celle par défaut si la monnaie précisée n'a pas de taux de change défini
   * @throws UserException Si le taux de change de la monnaie en argument n'est
   * pas défini
   */
  public Amount changeTo(Amount amount, Currency currency) throws UserException
  {
    return this.changeTo(amount, currency, Amount.PRECISION);
  }
  /**
   * Cette méthode permet de calculer la valeur du montant en argument dans la
   * monnaie précisée. Si la monnaie choisie n'est pas défini parmi les taux de
   * changes, la valeur retournée sera calculée dans la monnaie par défaut
   * @param amount Montant à calculer dans la monnaie choisie
   * @param currency Monnaie dans laquelle calculer le montant en argument
   * @param precision Précision après la virgule attendue pour la valeur du montant
   * à retourner
   * @return La valeur du montant en argument dans la monnaie précisée ou dans
   * celle par défaut si la monnaie précisée n'a pas de taux de change défini
   * @throws UserException Si le taux de change de la monnaie en argument n'est
   * pas défini
   */
  public Amount changeTo(Amount amount, Currency currency, int precision) throws UserException
  {
    if(amount.getCurrency().equals(currency))
    {
      return amount;
    }
    double fromRate = this.getValue(amount.getCurrency());
    Amount toRate = this.getEmbedded(currency);
    double value = amount.getValue() * toRate.getValue() / fromRate;
    return new Amount(toRate.getCurrency(), value, precision);
  }

  /**
   * Redéfini l'ajout d'un taux de change pour tester le minimum de sa valeur
   * @param amount {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc} ou si la valeur du taux de change en argument
   * n'est pas supérieure à zéro
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap#addEmbedded(com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType)
   */
  @Override
  public void addEmbedded(Amount amount) throws ProtectionException, UserException
  {
    UtilNumber.checkMinValue("rate", amount.getValue(), 0, false,
                             CurrencyRef.EXCHANGE_RATE);
    super.addEmbedded(amount);
  }
}
