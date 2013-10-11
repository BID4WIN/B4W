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
// * @author Emeric Fill�tre
// */
//public class CurrencyManager
//{
//  /** C'est l'instance unique utilis�e comme singleton */
//  private final static CurrencyManager instance = new CurrencyManager();
//  /**
//   * Le manager est un singleton. On passe donc par cette m�thode pour r�cup�rer
//   * l'unique instance en m�moire
//   * @return L'instance du manager
//   */
//  public static CurrencyManager getInstance()
//  {
//    return CurrencyManager.instance;
//  }
//
//  /** Marqueur d'initialisation */
//  private boolean initialized = false;
//  /** Map des devises class�es selon leur code */
//  private CurrencyByCodeMap currencyMap = new CurrencyByCodeMap();
//
//  /**
//   * Constructeur prot�g� car accessible seulement comme un singleton
//   */
//  protected CurrencyManager()
//  {
//    super();
//  }
//
//  /**
//   * Cette m�thode permet d'initialiser le manager avec les devises d�j� d�finies
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
//   * Cette m�thode permet de r�cup�rer une devise en fonction de son code
//   * @param code Code de la devise � r�cup�rer
//   * @return La devise r�cup�r�e en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration de la devise �choue
//   */
//  public Currency getCurrency(Code code) throws ModelArgumentException
//  {
//    Currency currency = this.findCurrency(code);
//    UtilObject.checkNotNull("currency", currency);
//    return currency;
//  }
//  /**
//   * Cette m�thode permet de r�cup�rer une devise en fonction de son code
//   * @param code Code de la devise � r�cup�rer
//   * @return La devise r�cup�r�e en fonction de son code
//   * @throws ModelArgumentException Si la r�cup�ration de la devise �choue
//   */
//  public Currency getCurrency(String code) throws ModelArgumentException
//  {
//    return this.getCurrency(Code.getCode(code));
//  }
//
//  /**
//   * Cette m�thode permet d'ajouter une devise au manager
//   * @param currency Devise � ajouter
//   * @throws ModelArgumentException Si la devise est d�j� pr�sente ou nulle
//   */
//  public synchronized void addCurrency(Currency currency) throws ModelArgumentException
//  {
//    UtilObject.checkNotNull("currency", currency);
//    UtilObject.checkNull("getCurrency", this.findCurrency(currency.getCode()));
//    this.setCurrency(currency);
//  }
//
//  /**
//   * Cette m�thode permet de modifier une devise du manager
//   * @param currency Devise � modifier
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
//   * Cette m�thode permet de trouver s'il existe une devise en fonction de son code
//   * @param code Code de la devise � trouver
//   * @return La devise trouv�e en fonction de son code ou null sinon
//   */
//  private Currency findCurrency(Code code)
//  {
//    return this.currencyMap.get(code);
//  }
//  /**
//   * Cette m�thode permet de d�finir la devise en argument dans la map du manager
//   * @param currency Devise � d�finir dans la map du manager
//   * @return La potentielle ancienne valeur de la devise
//   */
//  private Currency setCurrency(Currency currency)
//  {
//    return this.currencyMap.put(currency);
//  }
//  /**
//   * Cette m�thode permet de d�finir la liste de devises en argument dans la map
//   * du manager
//   * @param currencyList Liste de devises � d�finir dans la map du manager
//   */
//  private void setCurrencyList(CurrencyList currencyList)
//  {
//    this.currencyMap.putAll(currencyList);
//  }
//}
