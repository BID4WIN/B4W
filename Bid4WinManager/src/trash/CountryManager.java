//package trash;
//
//import java.util.Locale;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.core.Decimal;
//import com.bid4win.model.locale.Country;
//import com.bid4win.model.locale.Language;
//import com.bid4win.model.locale.Country.Code;
//import com.bid4win.model.locale.collection.CountryByCodeMap;
//import com.bid4win.model.locale.collection.CountryList;
//import com.bid4win.model.price.Currency;
//import com.bid4win.model.price.Tax;
//
///**
// * Manager de gestion des pays<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public class CountryManager
//{
//  /** C'est l'instance unique utilis�e comme singleton */
//  private final static CountryManager instance = new CountryManager();
//  /**
//   * Le manager est un singleton. On passe donc par cette m�thode pour r�cup�rer
//   * l'unique instance en m�moire
//   * @return L'instance du manager
//   */
//  public static CountryManager getInstance()
//  {
//    return CountryManager.instance;
//  }
//
//  /** Marqueur d'initialisation */
//  private boolean initialized = false;
//  /** Map des pays class�s selon leur code */
//  private CountryByCodeMap countryMap = new CountryByCodeMap();
//
//  /**
//   * Constructeur prot�g� car accessible seulement comme un singleton
//   */
//  protected CountryManager()
//  {
//    super();
//  }
//
//  /**
//   * Cette m�thode permet d'initialiser le manager avec les pays d�j� d�finis
//   * @throws Bid4WinException Si l'initialisation du manager �chouer
//   */
//  public void init() throws Bid4WinException
//  {
//    // Manager d�j� initialis�
//    if(this.initialized)
//    {
//      return;
//    }
//    // TODO initialisation de la map avec les donn�es en base
//    CountryList list = new CountryList();
//    try
//    {
//      list.add(new Country(Code.FRANCE, Language.Code.FRENCH,
//                           Currency.Code.EUR, new Tax(new Decimal("0.196"))));
//      list.add(new Country(Code.USA, Language.Code.ENGLISH,
//                           Currency.Code.USD, new Tax(new Decimal("0.162"))));
//      list.add(new Country(Code.UK, Language.Code.ENGLISH,
//                           Currency.Code.GBP, new Tax(new Decimal("0.18"))));
//      list.add(new Country(Code.ITALY, Language.Code.ITALIAN,
//                           Currency.Code.EUR, new Tax(new Decimal("0.2"))));
//      list.add(new Country(Code.SPAIN, Language.Code.SPANISH,
//                           Currency.Code.EUR, new Tax(new Decimal("0.105"))));
//    }
//    catch(Bid4WinException ex)
//    {
//      ex.printStackTrace();
//      throw ex;
//    }
//    this.setCountryList(list);
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer un pays en fonction de son code
//   * @param code Code du pays � r�cup�rer
//   * @return Le pays r�cup�r� en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Country getCountry(Code code) throws ModelArgumentException
//  {
//    Country country = this.findCountry(code);
//    UtilObject.checkNotNull("country", country);
//    return country;
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer un pays en fonction de son code
//   * @param code Code du pays � r�cup�rer
//   * @return Le pays r�cup�r� en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Country getCountry(String code) throws ModelArgumentException
//  {
//    return this.getCountry(Country.Code.getCode(code));
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la localisation de pr�f�rence d'un pays en
//   * fonction de son code
//   * @param code Code du pays dont il faut r�cup�rer la localisation de pr�f�rence
//   * @return La localisation de pr�f�rence d'un pays r�cup�r� en fonction de son
//   * code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Locale getPreferedLocale(Code code) throws ModelArgumentException
//  {
//    return this.getCountry(code).getPreferedLocale();
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la localisation de pr�f�rence d'un pays en
//   * fonction de son code
//   * @param code Code du pays dont il faut r�cup�rer la localisation de pr�f�rence
//   * @return La localisation de pr�f�rence d'un pays r�cup�r� en fonction de son
//   * code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Locale getPreferedLocale(String code) throws ModelArgumentException
//  {
//    return this.getPreferedLocale(Country.Code.getCode(code));
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la localisation d'un pays en fonction de
//   * son code et d'un code de langue
//   * @param code Code du pays dont il faut r�cup�rer la localisation
//   * @param languageCode Code de langue de la localisation
//   * @return La localisation d'un pays r�cup�r� en fonction de son code et d'un
//   * code de langue
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Locale getLocale(Code code, Language.Code languageCode) throws ModelArgumentException
//  {
//    return this.getCountry(code).getLocale(languageCode);
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la localisation d'un pays en fonction de
//   * son code et d'un code de langue
//   * @param code Code du pays dont il faut r�cup�rer la localisation
//   * @param languageCode Code de langue de la localisation
//   * @return La localisation d'un pays r�cup�r� en fonction de son code et d'un
//   * code de langue
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Locale getLocale(String code, Language.Code languageCode) throws ModelArgumentException
//  {
//    return this.getLocale(Country.Code.getCode(code), languageCode);
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la taxe applicable dans un pays en fonction
//   * de son code
//   * @param code Code du pays dont il faut r�cup�rer la taxe applicable
//   * @return La taxe applicable dans un pays r�cup�r� en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Tax getTax(Code code) throws ModelArgumentException
//  {
//    return this.getCountry(code).getTax();
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la taxe applicable dans un pays en fonction
//   * de son code
//   * @param code Code du pays dont il faut r�cup�rer la taxe applicable
//   * @return La taxe applicable dans un pays r�cup�r� en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Tax getTax(String code) throws ModelArgumentException
//  {
//    return this.getTax(Country.Code.getCode(code));
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la devise d'un pays en fonction de son code
//   * @param code Code du pays dont il faut r�cup�rer la devise
//   * @return La devise d'un pays r�cup�r� en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Currency getCurrency(Code code) throws ModelArgumentException
//  {
//    return CurrencyManager.getInstance().getCurrency(this.getCountry(code).getCurrencyCode());
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer la devise d'un pays en fonction de son code
//   * @param code Code du pays dont il faut r�cup�rer la devise
//   * @return La devise d'un pays r�cup�r� en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration du pays �choue
//   */
//  public Currency getCurrency(String code) throws ModelArgumentException
//  {
//    return this.getCurrency(Country.Code.getCode(code));
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter un pays au manager
//   * @param country Pays � ajouter
//   * @throws ModelArgumentException Si le pays est d�j� pr�sent ou nul
//   */
//  public synchronized void addCountry(Country country) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("country", country);
//    UtilObject.checkNull("findCountry", this.findCountry(country.getCode()));
//    this.setCountry(country);
//  }
//
//  /**
//   * Cette m�thode permet de modifier un pays du manager
//   * @param country Pays � modifier
//   * @return L'ancienne valeur du pays
//   * @throws ModelArgumentException Si le pays est inexistant ou nul
//   */
//  public synchronized Country updateCountry(Country country) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("country", country);
//    UtilObject.checkNotNull("findCountry", this.findCountry(country.getCode()));
//    return this.setCountry(country);
//  }
//
//  /**
//   * Cette m�thode permet de trouver s'il existe un pays en fonction de son code
//   * @param code Code du pays � trouver
//   * @return Le pays trouv� en fonction de son code ou null sinon
//   */
//  private Country findCountry(Code code)
//  {
//    return this.countryMap.get(code);
//  }
//  /**
//   * Cette m�thode permet de d�finir le pays en argument dans la map du manager
//   * @param country Pays � d�finir dans la map du manager
//   * @return La potentielle ancienne valeur du pays
//   */
//  private Country setCountry(Country country)
//  {
//    return this.countryMap.put(country);
//  }
//  /**
//   * Cette m�thode permet de d�finir la liste de pays en argument dans la map du
//   * manager
//   * @param countryList Liste de pays � d�finir dans la map du manager
//   */
//  private void setCountryList(CountryList countryList)
//  {
//    this.countryMap.putAll(countryList);
//  }
//}
