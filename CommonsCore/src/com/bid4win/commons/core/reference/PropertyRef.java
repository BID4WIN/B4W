package com.bid4win.commons.core.reference;

/**
 * D�finition des r�f�rences de propri�t�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class PropertyRef extends Reference<PropertyRef>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -7816309882354072791L;

  /** TODO A COMMENTER */
  public final static String SUFFIX_EXCHANGE_RATE = "exchange_rate";

  /** D�finition de la r�f�rence des propri�t�s serveur */
  public final static PropertyRef SERVER = new PropertyRef("server");
    /** D�finition de la r�f�rence de propri�t� de l'url racine de la web app */
  public static final PropertyRef SERVER_ROOT_LOCATION = new PropertyRef("root_location", SERVER);

  /** D�finition de la r�f�rence des propri�t�s de session */
  public final static PropertyRef SERVER_SESSION = new PropertyRef("session", SERVER);
  /** D�finition de la r�f�rence de propri�t� de timeout de session */
  public final static PropertyRef SERVER_SESSION_TIMEOUT = new PropertyRef("timeout", SERVER_SESSION);

  /** D�finition de la r�f�rence des propri�t�s du magasin de ressources */
  public final static PropertyRef SERVER_STORE = new PropertyRef("store", SERVER);
  /** D�finition de la r�f�rence de propri�t� de l'emplacement racine du magasin de ressources */
  public static final PropertyRef SERVER_STORE_ROOT_LOCATION = new PropertyRef("root_location", SERVER_STORE);

  /** D�finition de la r�f�rence de toutes les d�finitions */
  public static final PropertyRef SERVER_DEFINITION = new PropertyRef("definition", SERVER);
  /** D�finition de la r�f�rence de d�finition des langues */
  public static final PropertyRef SERVER_DEFINITION_LANGUAGES = new PropertyRef("languages", SERVER_DEFINITION);
  /** D�finition de la r�f�rence de d�finition des monnaies */
  public static final PropertyRef SERVER_DEFINITION_CURRENCIES = new PropertyRef("currencies", SERVER_DEFINITION);

  /** TODO A COMMENTER */
  public static final PropertyRef SERVER_AUCTION = new PropertyRef("auction", SERVER);

  /**
   * Constructeur
   * @param code Code de la r�f�rence de message
   */
  private PropertyRef(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de la r�f�rence de message
   * @param parent Parent de la r�f�rence de message
   */
  private PropertyRef(String code, PropertyRef parent)
  {
    super(code, parent);
  }
}
