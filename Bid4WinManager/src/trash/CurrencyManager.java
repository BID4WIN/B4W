//package trash;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.core.Decimal;
//import com.bid4win.model.price.Currency;
//import com.bid4win.model.price.Currency.Code;
//import com.bid4win.model.price.collection.CurrencyByCodeMap;
//import com.bid4win.model.price.collection.CurrencyList;
//
///**
// * Manager de gestion des devises<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//public class CurrencyManager
//{
//  /** C'est l'instance unique utilisée comme singleton */
//  private final static CurrencyManager instance = new CurrencyManager();
//  /**
//   * Le manager est un singleton. On passe donc par cette méthode pour récupérer
//   * l'unique instance en mémoire
//   * @return L'instance du manager
//   */
//  public static CurrencyManager getInstance()
//  {
//    return CurrencyManager.instance;
//  }
//
//  /** Marqueur d'initialisation */
//  private boolean initialized = false;
//  /** Map des devises classées selon leur code */
//  private CurrencyByCodeMap currencyMap = new CurrencyByCodeMap();
//
//  /**
//   * Constructeur protégé car accessible seulement comme un singleton
//   */
//  protected CurrencyManager()
//  {
//    super();
//  }
//
//  /**
//   * Cette méthode permet d'initialiser le manager avec les devises déjà définies
//   * @throws Bid4WinException Si l'initialisation du manager échouer
//   */
//  public void init() throws Bid4WinException
//  {
//    // Manager déjà initialisé
//    if(this.initialized)
//    {
//      return;
//    }
//    // TODO initialisation de la map avec les données en base
//    CurrencyList list = new CurrencyList();
//    try
//    {
//      list.add(new Currency(Code.EUR, null));
//      list.add(new Currency(Code.USD, new Decimal("1.35")));
//      list.add(new Currency(Code.GBP, new Decimal("0.8")));
//      list.add(new Currency(Code.JPY, new Decimal("182.4")));
//      list.add(new Currency(Code.CHF, new Decimal("0.5")));
//    }
//    catch(Bid4WinException ex)
//    {
//      ex.printStackTrace();
//      throw ex;
//    }
//    this.setCurrencyList(list);
//  }
//
//  /**
//   * Cette méthode permet de récupérer une devise en fonction de son code
//   * @param code Code de la devise à récupérer
//   * @return La devise récupérée en fonction de son code
//   * @throws ModelArgumentException Si la récupération de la devise échoue
//   */
//  public Currency getCurrency(Code code) throws ModelArgumentException
//  {
//    Currency currency = this.findCurrency(code);
//    UtilObject.checkNotNull("currency", currency);
//    return currency;
//  }
//  /**
//   * Cette méthode permet de récupérer une devise en fonction de son code
//   * @param code Code de la devise à récupérer
//   * @return La devise récupérée en fonction de son code
//   * @throws ModelArgumentException Si la récupération de la devise échoue
//   */
//  public Currency getCurrency(String code) throws ModelArgumentException
//  {
//    return this.getCurrency(Code.getCode(code));
//  }
//
//  /**
//   * Cette méthode permet d'ajouter une devise au manager
//   * @param currency Devise à ajouter
//   * @throws ModelArgumentException Si la devise est déjà présente ou nulle
//   */
//  public synchronized void addCurrency(Currency currency) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("currency", currency);
//    UtilObject.checkNull("getCurrency", this.findCurrency(currency.getCode()));
//    this.setCurrency(currency);
//  }
//
//  /**
//   * Cette méthode permet de modifier une devise du manager
//   * @param currency Devise à modifier
//   * @return L'ancienne valeur de la devise
//   * @throws ModelArgumentException Si la devise est inexistante ou nulle
//   */
//  public synchronized Currency updateCurrency(Currency currency) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("currency", currency);
//    UtilObject.checkNotNull("getCurrency", this.findCurrency(currency.getCode()));
//    return this.setCurrency(currency);
//  }
//
//  /**
//   * Cette méthode permet de trouver s'il existe une devise en fonction de son code
//   * @param code Code de la devise à trouver
//   * @return La devise trouvée en fonction de son code ou null sinon
//   */
//  private Currency findCurrency(Code code)
//  {
//    return this.currencyMap.get(code);
//  }
//  /**
//   * Cette méthode permet de définir la devise en argument dans la map du manager
//   * @param currency Devise à définir dans la map du manager
//   * @return La potentielle ancienne valeur de la devise
//   */
//  private Currency setCurrency(Currency currency)
//  {
//    return this.currencyMap.put(currency);
//  }
//  /**
//   * Cette méthode permet de définir la liste de devises en argument dans la map
//   * du manager
//   * @param currencyList Liste de devises à définir dans la map du manager
//   */
//  private void setCurrencyList(CurrencyList currencyList)
//  {
//    this.currencyMap.putAll(currencyList);
//  }
//}
