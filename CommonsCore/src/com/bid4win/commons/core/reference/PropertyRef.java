package com.bid4win.commons.core.reference;

/**
 * Définition des références de propriétés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PropertyRef extends Reference<PropertyRef>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -7816309882354072791L;

  /** TODO A COMMENTER */
  public final static String SUFFIX_EXCHANGE_RATE = "exchange_rate";

  /** Définition de la référence des propriétés serveur */
  public final static PropertyRef SERVER = new PropertyRef("server");
    /** Définition de la référence de propriété de l'url racine de la web app */
  public static final PropertyRef SERVER_ROOT_LOCATION = new PropertyRef("root_location", SERVER);

  /** Définition de la référence des propriétés de session */
  public final static PropertyRef SERVER_SESSION = new PropertyRef("session", SERVER);
  /** Définition de la référence de propriété de timeout de session */
  public final static PropertyRef SERVER_SESSION_TIMEOUT = new PropertyRef("timeout", SERVER_SESSION);

  /** Définition de la référence des propriétés du magasin de ressources */
  public final static PropertyRef SERVER_STORE = new PropertyRef("store", SERVER);
  /** Définition de la référence de propriété de l'emplacement racine du magasin de ressources */
  public static final PropertyRef SERVER_STORE_ROOT_LOCATION = new PropertyRef("root_location", SERVER_STORE);

  /** Définition de la référence de toutes les définitions */
  public static final PropertyRef SERVER_DEFINITION = new PropertyRef("definition", SERVER);
  /** Définition de la référence de définition des langues */
  public static final PropertyRef SERVER_DEFINITION_LANGUAGES = new PropertyRef("languages", SERVER_DEFINITION);
  /** Définition de la référence de définition des monnaies */
  public static final PropertyRef SERVER_DEFINITION_CURRENCIES = new PropertyRef("currencies", SERVER_DEFINITION);

  /** TODO A COMMENTER */
  public static final PropertyRef SERVER_AUCTION = new PropertyRef("auction", SERVER);

  /**
   * Constructeur
   * @param code Code de la référence de message
   */
  private PropertyRef(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de la référence de message
   * @param parent Parent de la référence de message
   */
  private PropertyRef(String code, PropertyRef parent)
  {
    super(code, parent);
  }
}
