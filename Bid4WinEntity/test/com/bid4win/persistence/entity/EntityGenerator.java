package com.bid4win.persistence.entity;

import java.util.Iterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.account.AccountStub;
import com.bid4win.persistence.entity.account.Gender;
import com.bid4win.persistence.entity.account.Name;
import com.bid4win.persistence.entity.account.User;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditBundleAbstractStub;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistory;
import com.bid4win.persistence.entity.account.credit.CreditBundleHistoryStub;
import com.bid4win.persistence.entity.account.credit.CreditBundleStub;
import com.bid4win.persistence.entity.account.credit.CreditOrigin;
import com.bid4win.persistence.entity.account.credit.Origin;
import com.bid4win.persistence.entity.account.preference.PreferenceBundle;
import com.bid4win.persistence.entity.auction.AuctionAbstractStub;
import com.bid4win.persistence.entity.auction.AuctionStub;
import com.bid4win.persistence.entity.auction.ScheduleAbstractStub;
import com.bid4win.persistence.entity.auction.ScheduleStub;
import com.bid4win.persistence.entity.auction.TermsAbstractStub;
import com.bid4win.persistence.entity.auction.TermsStub;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalCancelPolicy;
import com.bid4win.persistence.entity.auction.normal.NormalSchedule;
import com.bid4win.persistence.entity.auction.normal.NormalTerms;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedAuction;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedCancelPolicy;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedSchedule;
import com.bid4win.persistence.entity.auction.prebooked.PrebookedTerms;
import com.bid4win.persistence.entity.image.ImageStorage;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.locale.I18nElement;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.inner.InnerContentStorage;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;
import com.bid4win.persistence.entity.price.Amount;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.entity.price.ExchangeRates;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("EntityGenerator")
@Scope("singleton")
public class EntityGenerator extends com.bid4win.commons.persistence.entity.EntityGenerator<Account>
{
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Language getLanguage(int index)
  {
    Bid4WinSet<Language> languageSet = new Bid4WinSet<Language>(Language.getLanguages());
    if(index % languageSet.size() == 0)
    {
      return Language.DEFAULT;
    }
    languageSet.remove(Language.DEFAULT);
    Bid4WinList<Language> languageList = new Bid4WinList<Language>(languageSet);
    return languageList.get(index % languageList.size() - 1);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public User createUser()
  {
    return this.createUser(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public User createUser(int index)
  {
    return this.createUser(this.createName(index), new Bid4WinDate());
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param birthDate TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public User createUser(Name name, Bid4WinDate birthDate)
  {
    return new User(name, birthDate);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Name createName()
  {
    return this.createName(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Name createName(int index)
  {
    Bid4WinList<Gender> list = new Bid4WinList<Gender>(Gender.getGenders());
    return this.createName(list.get(index % list.size()), "firstName" + index,
                           "middleName" + index, "lastName" + index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param gender TODO A COMMENTER
   * @param firstName TODO A COMMENTER
   * @param middleName TODO A COMMENTER
   * @param lastName TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Name createName(Gender gender, String firstName, String middleName, String lastName)
  {
    return new Name(gender, firstName, middleName, lastName);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PreferenceBundle createPreferenceBundle() throws Bid4WinException
  {
    return this.createPreferenceBundle(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PreferenceBundle createPreferenceBundle(int index) throws Bid4WinException
  {
    return this.createPreferenceBundle(this.getLanguage(index).getCode());
  }
  /**
   *
   * TODO A COMMENTER
   * @param languageCode TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PreferenceBundle createPreferenceBundle(String languageCode) throws Bid4WinException
  {
    PreferenceBundle bundle = new PreferenceBundle();
    bundle.defineLanguage(Language.getLanguage(languageCode));
    return bundle;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.EntityGenerator#createAccount()
   */
  @Override
  public Account createAccount() throws Bid4WinException
  {
    return new Account(this.createCredential(), this.createEmail());
  }
  /**
   *
   * TODO A COMMENTER
   * @param index {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.EntityGenerator#createAccount(int)
   */
  @Override
  public Account createAccount(int index) throws Bid4WinException
  {
    return new Account(this.createCredential(index), this.createEmail(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.EntityGenerator#createAccount(java.lang.String)
   */
  @Override
  public Account createAccount(String id) throws Bid4WinException
  {
    return new AccountStub(id, this.createCredential(0), this.createEmail(0));
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param unitValue TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundleStub createCreditBundleStub(Account account, double unitValue, int nb)
         throws Bid4WinException
  {
    return new CreditBundleStub(account, this.createCreditOrigin(), unitValue, nb);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param unitValue TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundle createCreditBundle(Account account, double unitValue, int nb)
         throws Bid4WinException
  {
    return new CreditBundle(account, this.createCreditOrigin(), unitValue, nb);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditOrigin createCreditOrigin() throws Bid4WinException
  {
    return this.createCreditOrigin(Origin.PURCHASE,
                                   IdGenerator.generateId(Bid4WinEntityGeneratedID.ID_PATTERN));
  }
  /**
   *
   * TODO A COMMENTER
   * @param origin TODO A COMMENTER
   * @param reference TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditOrigin createCreditOrigin(Origin origin, String reference)
         throws Bid4WinException
  {
    return new CreditOrigin(origin, reference);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param unitValue TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundleHistory createCreditBundleHistory(Account account, double unitValue, int nb)
         throws Bid4WinException
  {
    return new CreditBundleAbstractStub(account, this.createCreditOrigin(), unitValue, nb).toHistory();
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param unitValue TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public CreditBundleHistoryStub createCreditBundleHistoryStub(Account account, double unitValue, int nb)
         throws Bid4WinException
  {
    return this.createCreditBundleStub(account, unitValue, nb).historize();
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
 /* public CreditUsageStub createCreditUsage(Account account) throws Bid4WinException
  {
    return new CreditUsageStub(account);
  }*/

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Product createProduct() throws Bid4WinException
  {
    return this.createProduct(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Product createProduct(int index) throws Bid4WinException
  {
    return this.createProduct("reference" + index, "name" + index, "summaries" + index, 1 + index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param reference TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param summary TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Product createProduct(String reference, String name, String summary, int index)
         throws Bid4WinException
  {
    return new Product(reference, this.createI18nGroup(name), this.createI18nGroup(summary), this.createPrice(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param reference TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param summary TODO A COMMENTER
   * @param price TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Product createProduct(String reference, String name, String summary, double price)
         throws Bid4WinException
  {
    return new Product(reference, this.createI18nGroup(name), this.createI18nGroup(summary), this.createPrice(price));
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ExchangeRates createExchangeRates() throws Bid4WinException
  {
    return this.createExchangeRates(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ExchangeRates createExchangeRates(int index) throws Bid4WinException
  {
    Double[] values = new Double[Currency.getCurrencies().size() - 1];
    for(int i = 0 ; i < values.length ; i++)
    {
      values[i] = Double.valueOf(index * values.length + i + 1);
    }
    return this.createExchangeRates(values);
  }
  /**
   *
   * TODO A COMMENTER
   * @param values TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ExchangeRates createExchangeRates(Double ... values) throws Bid4WinException
  {
    Bid4WinMap<Currency, Double> rateMap = new Bid4WinMap<Currency, Double>(values.length);
    Iterator<Currency> iterator = Currency.getCurrencies().iterator();
    for(int i = 0 ; i < values.length ; i++)
    {
      Currency currency = iterator.next();
      if(currency.equals(Currency.DEFAULT))
      {
        currency = iterator.next();
      }
      rateMap.put(currency, values[i]);
    }
    return new ExchangeRates(rateMap);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Price createPrice() throws Bid4WinException
  {
    return this.createPrice(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Price createPrice(int index) throws Bid4WinException
  {
    Double[] values = new Double[Currency.getCurrencies().size()];
    for(int i = 0 ; i < values.length ; i++)
    {
      values[i] = Double.valueOf(index * values.length + i);
    }
    return this.createPrice(values);
  }
  /**
   *
   * TODO A COMMENTER
   * @param values TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Price createPrice(Double ... values) throws Bid4WinException
  {
    Amount[] amounts = new Amount[values.length];
    amounts[0] = new Amount(values[0]);
    Iterator<Currency> iterator = Currency.getCurrencies().iterator();
    for(int i = 1 ; i < values.length ; i++)
    {
      Currency currency = iterator.next();
      if(currency.equals(Currency.DEFAULT))
      {
        currency = iterator.next();
      }
      amounts[i] = new Amount(currency, values[i]);
    }
    return new Price(amounts);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Amount createAmount() throws Bid4WinException
  {
    return this.createAmount(1.23);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Amount createAmount(double value) throws Bid4WinException
  {
    return this.createAmount(Currency.DEFAULT, value);
  }
  /**
   *
   * TODO A COMMENTER
   * @param currency TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Amount createAmount(Currency currency, double value) throws Bid4WinException
  {
    return new Amount(currency, value);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public I18nGroup createI18nGroup() throws Bid4WinException
  {
    return this.createI18nGroup(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public I18nGroup createI18nGroup(int index) throws Bid4WinException
  {
    String[] values = new String[Language.getLanguages().size()];
    for(int i = 0 ; i < values.length ; i++)
    {
      values[i] = "language" + i + "." + index;
    }
    return this.createI18nGroup(values);
  }
  /**
   *
   * TODO A COMMENTER
   * @param values TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public I18nGroup createI18nGroup(String ... values) throws Bid4WinException
  {
    I18nElement[] names = new I18nElement[values.length];
    names[0] = new I18nElement(values[0]);
    Iterator<Language> iterator = Language.getLanguages().iterator();
    for(int i = 1 ; i < values.length ; i++)
    {
      Language language = iterator.next();
      if(language.equals(Language.DEFAULT))
      {
        language = iterator.next();
      }
      names[i] = new I18nElement(language, values[i]);
    }
    return new I18nGroup(names);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ScheduleAbstractStub createScheduleAbstract() throws Bid4WinException
  {
    return this.createScheduleAbstract(new Bid4WinDate().removeSecondInfo().addTime(60 * 1000));
  }
  /**
   *
   * TODO A COMMENTER
   * @param startDate TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ScheduleAbstractStub createScheduleAbstract(Bid4WinDate startDate)
         throws Bid4WinException
  {
    return new ScheduleAbstractStub(startDate);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public TermsAbstractStub createTermsAbstract() throws Bid4WinException
  {
    return this.createTermsAbstract(2, 0.01);
  }
  /**
   *
   * TODO A COMMENTER
   * @param creditNb TODO A COMMENTER
   * @param bidIncrementValue TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public TermsAbstractStub createTermsAbstract(int creditNb, double bidIncrementValue) throws Bid4WinException
  {
    return new TermsAbstractStub(creditNb, bidIncrementValue);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AuctionAbstractStub createAuctionAbstract() throws Bid4WinException
  {
    return this.createAuctionAbstract(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AuctionAbstractStub createAuctionAbstract(int index) throws Bid4WinException
  {
    return new AuctionAbstractStub(
        this.createProduct(index), this.createScheduleAbstract(), this.createTermsAbstract());
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ScheduleStub createSchedule() throws Bid4WinException
  {
    return this.createSchedule(new Bid4WinDate().removeSecondInfo().addTime(60 * 1000), 30, 10);
  }
  /**
   *
   * TODO A COMMENTER
   * @param startDate TODO A COMMENTER
   * @return TODO A COMMENTER
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères en secondes
   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères en secondes
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ScheduleStub createSchedule(Bid4WinDate startDate, int initialCountdown, int aditionalCountdown)
         throws Bid4WinException
  {
    return new ScheduleStub(startDate, initialCountdown, aditionalCountdown);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public TermsStub createTerms() throws Bid4WinException
  {
    return this.createTerms(2, 0.01);
  }
  /**
   *
   * TODO A COMMENTER
   * @param creditNb TODO A COMMENTER
   * @param bidIncrementValue TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public TermsStub createTerms(int creditNb, double bidIncrementValue) throws Bid4WinException
  {
    return new TermsStub(creditNb, bidIncrementValue);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AuctionStub createAuction() throws Bid4WinException
  {
    return this.createAuction(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public AuctionStub createAuction(int index) throws Bid4WinException
  {
    return new AuctionStub(this.createProduct(index), this.createSchedule(), this.createTerms());
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalSchedule createNormalSchedule() throws Bid4WinException
  {
    return this.createNormalSchedule(new Bid4WinDate().removeSecondInfo().addTime(60 * 1000), 30, 10);
  }
  /**
   *
   * TODO A COMMENTER
   * @param startDate TODO A COMMENTER
   * @return TODO A COMMENTER
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères en secondes
   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères en secondes
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalSchedule createNormalSchedule(Bid4WinDate startDate, int initialCountdown, int aditionalCountdown)
         throws Bid4WinException
  {
    return new NormalSchedule(startDate, initialCountdown, aditionalCountdown);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalTerms createNormalTerms() throws Bid4WinException
  {
    return this.createNormalTerms(2, 0.01);
  }
  /**
   *
   * TODO A COMMENTER
   * @param creditNb TODO A COMMENTER
   * @param bidIncrementValue TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalTerms createNormalTerms(int creditNb, double bidIncrementValue) throws Bid4WinException
  {
    return new NormalTerms(creditNb, bidIncrementValue);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalCancelPolicy createNormalCancelPolicy() throws Bid4WinException
  {
    return this.createNormalCancelPolicy(3, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param creditNbThreshold TODO A COMMENTER
   * @param creditNbPaidBonus TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalCancelPolicy createNormalCancelPolicy(int creditNbThreshold, int creditNbPaidBonus) throws Bid4WinException
  {
    return new NormalCancelPolicy(creditNbThreshold, creditNbPaidBonus);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalAuction createNormalAuction() throws Bid4WinException
  {
    return this.createNormalAuction(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalAuction createNormalAuction(int index) throws Bid4WinException
  {
    return this.createNormalAuction(this.createProduct(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public NormalAuction createNormalAuction(Product product) throws Bid4WinException
  {
    return new NormalAuction(product, this.createNormalSchedule(), this.createNormalTerms());
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedSchedule createPrebookedSchedule() throws Bid4WinException
  {
    return this.createPrebookedSchedule(new Bid4WinDate().removeSecondInfo().addTime(60 * 1000), 60, 15);
  }
  /**
   *
   * TODO A COMMENTER
   * @param startDate TODO A COMMENTER
   * @return TODO A COMMENTER
   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
   * enchères en secondes
   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
   * vente aux enchères en secondes
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedSchedule createPrebookedSchedule(Bid4WinDate startDate, int initialCountdown, int aditionalCountdown)
         throws Bid4WinException
  {
    return new PrebookedSchedule(startDate, initialCountdown, aditionalCountdown);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedTerms createPrebookedTerms() throws Bid4WinException
  {
    return this.createPrebookedTerms(2, 0.01);
  }
  /**
   *
   * TODO A COMMENTER
   * @param creditNb TODO A COMMENTER
   * @param bidIncrementValue TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedTerms createPrebookedTerms(int creditNb, double bidIncrementValue) throws Bid4WinException
  {
    return new PrebookedTerms(creditNb, bidIncrementValue);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedAuction createPrebookedAuction() throws Bid4WinException
  {
    return this.createPrebookedAuction(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedCancelPolicy createPrebookedCancelPolicy() throws Bid4WinException
  {
    return this.createPrebookedCancelPolicy(3, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param creditNbThreshold TODO A COMMENTER
   * @param creditNbPaidBonus TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedCancelPolicy createPrebookedCancelPolicy(int creditNbThreshold, int creditNbPaidBonus) throws Bid4WinException
  {
    return new PrebookedCancelPolicy(creditNbThreshold, creditNbPaidBonus);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedAuction createPrebookedAuction(int index) throws Bid4WinException
  {
    return this.createPrebookedAuction(this.createProduct(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param product TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public PrebookedAuction createPrebookedAuction(Product product) throws Bid4WinException
  {
    return new PrebookedAuction(product, this.createPrebookedSchedule(), this.createPrebookedTerms());
  }

 /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ImageStorage createImageStorage() throws Bid4WinException
  {
    return this.createImageStorage(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ImageStorage createImageStorage(int index) throws Bid4WinException
  {
    Bid4WinList<ImageType> list = new Bid4WinList<ImageType>(ImageType.getImageTypes());
    return this.createImageStorage("storagePath" + index, "storageName" + index,
                                   list.get(index % list.size()).getCode());
  }
  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param typeCode TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public ImageStorage createImageStorage(String path, String name, String typeCode)
         throws Bid4WinException
  {
    return new ImageStorage(path, name, ImageType.getImageType(typeCode));
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public InnerContentStorage createInnerContentStorage() throws Bid4WinException
  {
    return this.createInnerContentStorage(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public InnerContentStorage createInnerContentStorage(int index) throws Bid4WinException
  {
    Bid4WinList<InnerContentType> list = new Bid4WinList<InnerContentType>(Bid4WinObjectType.getTypes(InnerContentType.class));
    return this.createInnerContentStorage("storagePath" + index, "storageName" + index,
                                          list.get(index % list.size()).getCode());
  }
  /**
   *
   * TODO A COMMENTER
   * @param path TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param typeCode TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public InnerContentStorage createInnerContentStorage(String path, String name, String typeCode)
         throws Bid4WinException
  {
    return new InnerContentStorage(path, name, Bid4WinObjectType.getType(InnerContentType.class, typeCode));
  }
}
