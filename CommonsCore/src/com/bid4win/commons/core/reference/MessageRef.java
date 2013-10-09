package com.bid4win.commons.core.reference;



/**
 * D�finition des r�f�rences de message<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@SuppressWarnings("javadoc")
public class MessageRef extends Reference<MessageRef>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -8289548483955677846L;

  /** TODO A COMMENTER */
  public final static String SUFFIX_KEY = "key";
  /** TODO A COMMENTER */
  public final static String SUFFIX_TYPE = "type";
  /** TODO A COMMENTER */
  public final static String SUFFIX_NAME = "name";
  /** TODO A COMMENTER */
  public final static String SUFFIX_VALUE = "value";
  /** TODO A COMMENTER */
  public final static String SUFFIX_PATH = "path";
  /** TODO A COMMENTER */
  public final static String SUFFIX_USAGE = "usage";
  /** TODO A COMMENTER */
  public final static String SUFFIX_FORMAT = "format";
  /** TODO A COMMENTER */
  public final static String SUFFIX_REFERENCE = "reference";
  /** TODO A COMMENTER */
  public final static String SUFFIX_STATUS = "status";
  /** TODO A COMMENTER */
  public final static String SUFFIX_STEP = "step";
  /** TODO A COMMENTER */
  public final static String SUFFIX_UNKNOWN = "unknown";
  /** TODO A COMMENTER */
  public final static String SUFFIX_ERROR = "error";
  /** TODO A COMMENTER */
  public final static String SUFFIX_CREDIT = "credit";
  /** TODO A COMMENTER */
  public final static String SUFFIX_USER = "user";

  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur inconnue */
  public final static String SUFFIX_UNKNOWN_ERROR = SUFFIX_UNKNOWN + "_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur manquante */
  public final static String SUFFIX_MISSING_ERROR = "missing_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur d�j� existante */
  public final static String SUFFIX_EXISTING_ERROR = "existing_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur non d�finie */
  public final static String SUFFIX_UNDEFINED_ERROR = "undefined_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur d�j� d�finie */
  public final static String SUFFIX_DEFINED_ERROR = "defined_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur invalide */
  public final static String SUFFIX_INVALID_ERROR = "invalid_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur historis�e */
  public final static String SUFFIX_NOT_HISTORIZED_ERROR = "not_historized_" + SUFFIX_ERROR;
  /** Suffix utilis� pour construire les r�f�rences de message d'erreur concernant une valeur historis�e */
  public final static String SUFFIX_HISTORIZED_ERROR = "historized_" + SUFFIX_ERROR;

  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN = new MessageRef(SUFFIX_UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_ERROR = new MessageRef(SUFFIX_ERROR, UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_EXISTING_ERROR = new MessageRef(SUFFIX_EXISTING_ERROR, UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, UNKNOWN);
  /** TODO A COMMENTER */
  public final static MessageRef UNKNOWN_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, UNKNOWN);

  /* ############################### CURRENCY ############################### */
  /**
   * TODO A COMMENTER
   */
  public static class CurrencyRef
  {
    /** D�finition de la r�f�rence de message li�e aux monnaies */
    public final static MessageRef CURRENCY = new MessageRef("currency");
    /** D�finition de la r�f�rence de message d'erreur d'une monnaie inconnue */
    public final static MessageRef CURRENCY_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, CURRENCY);
    /** D�finition de la r�f�rence de message d'erreur d'une monnaie manquante */
    public final static MessageRef CURRENCY_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CURRENCY);
    /** D�finition de la r�f�rence de message d'erreur d'une monnaie non d�finie */
    public final static MessageRef CURRENCY_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, CURRENCY);
    /** D�finition de la r�f�rence de message d'erreur d'une monnaie d�j� d�finie */
    public final static MessageRef CURRENCY_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, CURRENCY);
    /** D�finition de la r�f�rence de message d'erreur d'une monnaie invalide */
    public final static MessageRef CURRENCY_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CURRENCY);

    /** D�finition de la r�f�rence de message li�e aux montants */
    public final static MessageRef CURRENCY_AMOUNT = new MessageRef("amount", CURRENCY);
    /** D�finition de la r�f�rence de message d'erreur d'un montant invalide*/
    public final static MessageRef CURRENCY_AMOUNT_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CURRENCY_AMOUNT);
    /** D�finition de la r�f�rence de message d'erreur d'un montant invalide*/
    public final static MessageRef CURRENCY_AMOUNT_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CURRENCY_AMOUNT);

    /** D�finition de la r�f�rence de message li�e aux prix */
    public final static MessageRef CURRENCY_PRICE = new MessageRef("price", CURRENCY);
    /** TODO A COMMENTER */
    public final static MessageRef CURRENCY_PRICE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CURRENCY_PRICE);

    /** D�finition de la r�f�rence de message li�e aux taux de change */
    public final static MessageRef CURRENCY_EXCHANGE_RATE = new MessageRef("exchange_rate", CURRENCY);
    /** TODO A COMMENTER */
    public final static MessageRef CURRENCY_EXCHANGE_RATE_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, CURRENCY_EXCHANGE_RATE);
    /** TODO A COMMENTER */
    public final static MessageRef CURRENCY_EXCHANGE_RATE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CURRENCY_EXCHANGE_RATE);
    /** TODO A COMMENTER */
    public final static MessageRef CURRENCY_EXCHANGE_RATE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CURRENCY_EXCHANGE_RATE);
  }
  /* ############################### LANGUAGE ############################### */
  /**
   * TODO A COMMENTER
   */
  public static class LanguageRef
  {
    /** D�finition de la r�f�rence de message li�e aux langues */
    public final static MessageRef LANGUAGE = new MessageRef("language");
    /** D�finition de la r�f�rence de message d'erreur d'une langue inconnue */
    public final static MessageRef LANGUAGE_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, LANGUAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une langue manquante */
    public final static MessageRef LANGUAGE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, LANGUAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une langue non d�finie */
    public final static MessageRef LANGUAGE_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, LANGUAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une langue d�j� d�finie */
    public final static MessageRef LANGUAGE_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, LANGUAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une langue invalide */
    public final static MessageRef LANGUAGE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, LANGUAGE);
  }
  /* ############################### RESOURCE ############################### */
  /**
   * TODO A COMMENTER
   */
  public static class ResourceRef
  {
    /** D�finition de la r�f�rence de message li�e aux ressources */
    public final static MessageRef RESOURCE = new MessageRef("resource");
    public final static MessageRef IMAGE = new MessageRef("image");
    /** D�finition de la r�f�rence de message d'erreur d'une ressource inconnue */
    public final static MessageRef RESOURCE_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, RESOURCE);
    /** D�finition de la r�f�rence de message d'erreur d'une ressource non d�finie */
    public final static MessageRef RESOURCE_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, RESOURCE);
    public final static MessageRef IMAGE_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une ressource d�j� d�finie */
    public final static MessageRef RESOURCE_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, RESOURCE);
    public final static MessageRef IMAGE_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur de l'abscence du repertoire de travail d'un magasin de ressources */
    public final static MessageRef RESOURCE_WORKING_PATH_MISSING_ERROR = new MessageRef("working_path." + SUFFIX_MISSING_ERROR, RESOURCE);

    /** D�finition de la r�f�rence de message li�e aux emplacements de stockage de ressources */
    public final static MessageRef RESOURCE_PATH = new MessageRef(SUFFIX_PATH, RESOURCE);
    public final static MessageRef IMAGE_PATH = new MessageRef(SUFFIX_PATH, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur d'un emplacement de stockage de ressource invalide */
    public final static MessageRef RESOURCE_PATH_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, RESOURCE_PATH);
    public final static MessageRef IMAGE_PATH_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, IMAGE_PATH);

    /** D�finition de la r�f�rence de message li�e aux noms de ressources */
    public final static MessageRef RESOURCE_NAME = new MessageRef(SUFFIX_NAME, RESOURCE);
    public final static MessageRef IMAGE_NAME = new MessageRef(SUFFIX_NAME, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur d'un nom de ressource invalide */
    public final static MessageRef RESOURCE_NAME_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, RESOURCE_NAME);
    public final static MessageRef IMAGE_NAME_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, IMAGE_NAME);

    /** D�finition de la r�f�rence de message li�e aux types de ressources */
    public final static MessageRef RESOURCE_TYPE = new MessageRef(SUFFIX_TYPE, RESOURCE);
    public final static MessageRef IMAGE_TYPE = new MessageRef(SUFFIX_TYPE, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur d'un type de ressource manquant */
    public final static MessageRef RESOURCE_TYPE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, RESOURCE_TYPE);
    public final static MessageRef IMAGE_TYPE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, IMAGE_TYPE);
    /** D�finition de la r�f�rence de message d'erreur d'un type de ressource invalide */
    public final static MessageRef RESOURCE_TYPE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, RESOURCE_TYPE);
    public final static MessageRef IMAGE_TYPE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, IMAGE_TYPE);

    /** D�finition de la r�f�rence de message li�e aux stockages de ressources */
    public final static MessageRef RESOURCE_STORAGE = new MessageRef("storage", RESOURCE);
    /** D�finition de la r�f�rence de message d'erreur de l'abscence de la propri�t� d�finissant la racine du magasin des stockages de ressources */
    public final static MessageRef RESOURCE_STORAGE_ROOT_PROPERTY_MISSING_ERROR = new MessageRef("root_property." + SUFFIX_MISSING_ERROR, RESOURCE_STORAGE);

    /** D�finition de la r�f�rence de message li�e aux utilisations de ressources */
    public final static MessageRef RESOURCE_USAGE = new MessageRef(SUFFIX_USAGE, RESOURCE);
    public final static MessageRef IMAGE_USAGE = new MessageRef(SUFFIX_USAGE, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une utilisation de ressource inconnue */
    public final static MessageRef RESOURCE_USAGE_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, RESOURCE_USAGE);
    public final static MessageRef IMAGE_USAGE_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, IMAGE_USAGE);
    /** D�finition de la r�f�rence de message d'erreur d'une utilisation de ressource invalide */
    public final static MessageRef RESOURCE_USAGE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, RESOURCE_USAGE);
    public final static MessageRef IMAGE_USAGE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, IMAGE_USAGE);

    /** D�finition de la r�f�rence de message li�e aux formats d'image */
    public final static MessageRef IMAGE_FORMAT = new MessageRef(SUFFIX_FORMAT, IMAGE);
    /** D�finition de la r�f�rence de message d'erreur d'un format d'image inconnu */
    public final static MessageRef IMAGE_FORMAT_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, IMAGE_FORMAT);
    /** D�finition de la r�f�rence de message d'erreur d'un format d'image manquant */
    public final static MessageRef IMAGE_FORMAT_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, IMAGE_FORMAT);
    /** D�finition de la r�f�rence de message d'erreur d'un format d'image d�j� d�fini */
    public final static MessageRef IMAGE_FORMAT_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, IMAGE_FORMAT);
    /** D�finition de la r�f�rence de message d'erreur d'un format d'image non d�fini */
    public final static MessageRef IMAGE_FORMAT_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, IMAGE_FORMAT);
    /** D�finition de la r�f�rence de message d'erreur d'un format d'image invalide */
    public final static MessageRef IMAGE_FORMAT_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, IMAGE_FORMAT);

  }
  /** TODO A COMMENTER */
  public final static MessageRef ERROR_COOKIE_MISSING = new MessageRef("error.cookie.missing");

  /* ############################### PROPERTY ############################### */
  /**
   * TODO A COMMENTER
   */
  public static class PropertyRef
  {
    /** D�finition de la r�f�rence de message li�e aux propri�t�s */
    public final static MessageRef PROPERTY = new MessageRef("property");
    public final static MessageRef I18N = new MessageRef("i18n");
    /** D�finition de la r�f�rence de message d'erreur d'une propri�t� inconnue */
    public final static MessageRef PROPERTY_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, PROPERTY);
    public final static MessageRef I18N_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, I18N);
    /** D�finition de la r�f�rence de message d'erreur d'une propri�t� manquante */
    public final static MessageRef PROPERTY_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, PROPERTY);
    public final static MessageRef I18N_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, I18N);
    /** D�finition de la r�f�rence de message d'erreur d'une propri�t� d�j� existante */
    public final static MessageRef PROPERTY_EXISTING_ERROR = new MessageRef(SUFFIX_EXISTING_ERROR, PROPERTY);
    public final static MessageRef I18N_EXISTING_ERROR = new MessageRef(SUFFIX_EXISTING_ERROR, I18N);
    /** D�finition de la r�f�rence de message d'erreur d'une propri�t� non d�finie */
    public final static MessageRef PROPERTY_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, PROPERTY);
    public final static MessageRef I18N_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, I18N);
    /** D�finition de la r�f�rence de message d'erreur d'une propri�t� d�j� d�finie */
    public final static MessageRef PROPERTY_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, PROPERTY);
    public final static MessageRef I18N_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, I18N);

    /** D�finition de la r�f�rence de message li�e aux cl�s de propri�t�s */
    public final static MessageRef PROPERTY_KEY = new MessageRef(SUFFIX_KEY, PROPERTY);
    public final static MessageRef I18N_KEY = new MessageRef(SUFFIX_KEY, I18N);
    /** D�finition de la r�f�rence de message d'erreur d'une cl� de propri�t� manquante */
    public final static MessageRef PROPERTY_KEY_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, PROPERTY_KEY);
    /** D�finition de la r�f�rence de message d'erreur d'une ancienne cl� de propri�t� manquante */
    public final static MessageRef PROPERTY_KEY_OLD_MISSING_ERROR = new MessageRef("old." + SUFFIX_MISSING_ERROR, PROPERTY_KEY);
    /** D�finition de la r�f�rence de message d'erreur d'une nouvelle cl� de propri�t� manquante */
    public final static MessageRef PROPERTY_KEY_NEW_MISSING_ERROR = new MessageRef("new." + SUFFIX_MISSING_ERROR, PROPERTY_KEY);
    /** D�finition de la r�f�rence de message d'erreur d'une cl� de propri�t� invalide */
    public final static MessageRef PROPERTY_KEY_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, PROPERTY_KEY);
    public final static MessageRef I18N_KEY_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, I18N_KEY);

    /** D�finition de la r�f�rence de message li�e aux valeurs de propri�t�s */
    public final static MessageRef PROPERTY_VALUE = new MessageRef("value", PROPERTY);
    /** D�finition de la r�f�rence de message d'erreur d'une valeur de propri�t� manquante */
    public final static MessageRef PROPERTY_VALUE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, PROPERTY_VALUE);
    /** D�finition de la r�f�rence de message d'erreur d'une valeur de propri�t� invalide */
    public final static MessageRef PROPERTY_VALUE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, PROPERTY_VALUE);

    /** TODO A COMMENTER */
    public final static MessageRef I18N_NOT_EMPTY_ERROR = new MessageRef("not_empty_" + SUFFIX_ERROR, I18N);
  }
  /* ############################## CONNECTION ############################## */
  /**
   * TODO A COMMENTER
   */
  public static class ConnectionRef
  {
    /** D�finition de la r�f�rence de message li�e aux connexions */
    public final static MessageRef CONNECTION = new MessageRef("connection");

    public final static MessageRef CONNECTION_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION);

    /** D�finition de la r�f�rence de message li�e aux logins/emails de connexion */
    public final static MessageRef CONNECTION_LOGIN_OR_EMAIL = new MessageRef("login_or_email", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_LOGIN_OR_EMAIL_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, CONNECTION_LOGIN_OR_EMAIL);

    /** D�finition de la r�f�rence de message li�e aux logins de connexion */
    public final static MessageRef CONNECTION_LOGIN = new MessageRef("login", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_LOGIN_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CONNECTION_LOGIN);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_LOGIN_EXISTING_ERROR = new MessageRef(SUFFIX_EXISTING_ERROR, CONNECTION_LOGIN);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_LOGIN_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION_LOGIN);

    /** D�finition de la r�f�rence de message li�e aux mots de passes de connexion */
    public final static MessageRef CONNECTION_PASSWORD = new MessageRef("password", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_PASSWORD_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CONNECTION_PASSWORD);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_PASSWORD_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION_PASSWORD);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_PASSWORD_WRONG_ERROR = new MessageRef("wrong_" + SUFFIX_ERROR, CONNECTION_PASSWORD);

    /** D�finition de la r�f�rence de message li�e aux emails de connexion */
    public final static MessageRef CONNECTION_EMAIL = new MessageRef("email", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_EMAIL_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CONNECTION_EMAIL);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_EMAIL_EXISTING_ERROR = new MessageRef(SUFFIX_EXISTING_ERROR, CONNECTION_EMAIL);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_EMAIL_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION_EMAIL);

    /** D�finition de la r�f�rence de message li�e aux r�manences de connexion */
    public final static MessageRef CONNECTION_REMANENCE = new MessageRef("remanence", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_REMANENCE_UNKNOWN_ERROR = new MessageRef(SUFFIX_UNKNOWN_ERROR, CONNECTION_REMANENCE);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_REMANENCE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION_REMANENCE);

    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SUBSCRIPTION = new MessageRef("subscription", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SUBSCRIPTION_NOT_VALIDATED_ERROR = new MessageRef("not_validated_" + SUFFIX_ERROR, CONNECTION_SUBSCRIPTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SUBSCRIPTION_VALIDATED_ERROR = new MessageRef("validated_" + SUFFIX_ERROR, CONNECTION_SUBSCRIPTION);

    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SESSION = new MessageRef("session", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SESSION_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, CONNECTION_SESSION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SESSION_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, CONNECTION_SESSION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_SESSION_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION_SESSION);
    public final static MessageRef CONNECTION_PERMISSION_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CONNECTION_SESSION);
    public final static MessageRef CONNECTION_PERMISSION_EXISTING_ERROR = new MessageRef(SUFFIX_EXISTING_ERROR, CONNECTION_SESSION);

    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_PERMISSION = new MessageRef("permission", CONNECTION);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_PERMISSION_NOT_GRANTED_ERROR = new MessageRef("not_granted_" + SUFFIX_ERROR, CONNECTION_PERMISSION);

    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_IP = new MessageRef("ip", CONNECTION);
    public final static MessageRef CONNECTION_IP_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CONNECTION_IP);
    public final static MessageRef CONNECTION_IP_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, CONNECTION_IP);
    /** TODO A COMMENTER */
    public final static MessageRef CONNECTION_FINGERPRINT = new MessageRef("fingerprint", CONNECTION);
    public final static MessageRef CONNECTION_FINGERPRINT_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, CONNECTION_FINGERPRINT);
  }
  /* ############################### PRODUCT ################################ */
  /**
   * TODO A COMMENTER
   */
  public static class ProductRef
  {
    /** D�finition de la r�f�rence de message li�e aux produits */
    public final static MessageRef PRODUCT = new MessageRef("product");

    /** D�finition de la r�f�rence de message li�e aux noms de produit */
    public final static MessageRef PRODUCT_NAME = new MessageRef(SUFFIX_NAME, PRODUCT);
    /** TODO A COMMENTER */
    public final static MessageRef PRODUCT_NAME_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, PRODUCT_NAME);
    /** TODO A COMMENTER */
    public final static MessageRef PRODUCT_NAME_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, PRODUCT_NAME);

    /** D�finition de la r�f�rence de message li�e aux r�sum�s de description de produit */
    public final static MessageRef PRODUCT_SUMMARY = new MessageRef("summary", PRODUCT);
    /** TODO A COMMENTER */
    public final static MessageRef PRODUCT_SUMMARY_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, PRODUCT_SUMMARY);
  }

  /* ############################### ACCOUNT ################################ */
  /**
   * TODO A COMMENTER
   */
  public static class AccountRef
  {
    /** D�finition de la r�f�rence de message li�e aux comptes utilisateurs */
    public final static MessageRef ACCOUNT = new MessageRef("account");
    /** D�finition de la r�f�rence de message d'erreur d'un compte utilisateur manquant */
    public final static MessageRef ACCOUNT_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, ACCOUNT);
    /** D�finition de la r�f�rence de message d'erreur d'un compte utilisateur non d�fini */
    public final static MessageRef ACCOUNT_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, ACCOUNT);
    /** D�finition de la r�f�rence de message d'erreur d'un compte utilisateur d�j� d�fini */
    public final static MessageRef ACCOUNT_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, ACCOUNT);
    /** D�finition de la r�f�rence de message d'erreur d'un compte utilisateur invalide */
    public final static MessageRef ACCOUNT_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, ACCOUNT);

    /** D�finition de la r�f�rence de message li�e aux informations utilisateur d'un compte */
    public final static MessageRef ACCOUNT_USER = new MessageRef(SUFFIX_USER, ACCOUNT);
    /** D�finition de la r�f�rence de message li�e au pr�nom d'un utilisateur */
    public final static MessageRef ACCOUNT_USER_FIRST_NAME = new MessageRef("first_name", ACCOUNT_USER);
    public final static MessageRef ACCOUNT_USER_MIDDLE_NAME = new MessageRef("middle_name", ACCOUNT_USER);
    public final static MessageRef ACCOUNT_USER_LAST_NAME = new MessageRef("last_name", ACCOUNT_USER);

    public final static MessageRef ACCOUNT_PHONE = new MessageRef("phone", ACCOUNT);
    public final static MessageRef ACCOUNT_PHONE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, ACCOUNT_PHONE);

    /** D�finition de la r�f�rence de message li�e aux cr�dits d'un compte utilisateur */
    public final static MessageRef ACCOUNT_CREDIT = new MessageRef(SUFFIX_CREDIT, ACCOUNT);

    public final static MessageRef ACCOUNT_CREDIT_NOT_HISTORIZED_ERROR = new MessageRef(SUFFIX_NOT_HISTORIZED_ERROR, ACCOUNT_CREDIT);
    public final static MessageRef ACCOUNT_CREDIT_HISTORIZED_ERROR = new MessageRef(SUFFIX_HISTORIZED_ERROR, ACCOUNT_CREDIT);

    /** D�finition de la r�f�rence de message li�e aux nombres de cr�dits d'un compte utilisateur */
    public final static MessageRef ACCOUNT_CREDIT_NB = new MessageRef("nb", ACCOUNT_CREDIT);
    public final static MessageRef ACCOUNT_CREDIT_NB_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, ACCOUNT_CREDIT_NB);
    public final static MessageRef ACCOUNT_CREDIT_NB_INSUFFICIENT_ERROR = new MessageRef("not_enought_error", ACCOUNT_CREDIT_NB);

    /** D�finition de la r�f�rence de message li�e aux r�f�rences de cr�dits d'un compte utilisateur */
    public final static MessageRef ACCOUNT_CREDIT_REFERENCE = new MessageRef("reference", ACCOUNT_CREDIT);
    public final static MessageRef ACCOUNT_CREDIT_REFERENCE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, ACCOUNT_CREDIT_REFERENCE);
    public final static MessageRef ACCOUNT_CREDIT_REFERENCE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, ACCOUNT_CREDIT_REFERENCE);
  }


  /* ################################# SALE ################################# */
  /**
   * TODO A COMMENTER
   */
  public static class SaleRef
  {
    /** D�finition de la r�f�rence de message li�e aux ventes */
    public final static MessageRef SALE = new MessageRef("sale");
    /** D�finition de la r�f�rence de message li�e aux �tapes d'une vente */
    public final static MessageRef SALE_STEP = new MessageRef(SUFFIX_STEP, SALE);
    /** D�finition de la r�f�rence de message d'erreur d'une �tape de vente manquante */
    public final static MessageRef SALE_STEP_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, SALE_STEP);
    /** D�finition de la r�f�rence de message d'erreur d'une �tape de vente invalide */
    public final static MessageRef SALE_STEP_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, SALE_STEP);
  }
  /* ############################### AUCTION ################################ */
  /**
   * TODO A COMMENTER
   */
  public static class AuctionRef
  {
    /** D�finition de la r�f�rence de message li�e aux ventes aux ench�res */
    public final static MessageRef AUCTION = new MessageRef("auction");
    /** D�finition de la r�f�rence de message d'erreur d'une ventes aux ench�res manquante */
    public final static MessageRef AUCTION_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'une ventes aux ench�res non d�finie */
    public final static MessageRef AUCTION_UNDEFINED_ERROR = new MessageRef(SUFFIX_UNDEFINED_ERROR, AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'une ventes aux ench�res d�j� d�finie */
    public final static MessageRef AUCTION_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'une ventes aux ench�res invalide */
    public final static MessageRef AUCTION_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'une ventes aux ench�res d�j� historis�e */
    public final static MessageRef AUCTION_HISTORIZED_ERROR = new MessageRef(SUFFIX_HISTORIZED_ERROR, AUCTION);

    /** D�finition de la r�f�rence de message li�e aux status d'une vente aux ench�res */
    public final static MessageRef AUCTION_STATUS = new MessageRef(SUFFIX_STATUS, AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'un status de vente aux ench�res manquant */
    public final static MessageRef AUCTION_STATUS_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, AUCTION_STATUS);
    /** D�finition de la r�f�rence de message d'erreur d'un status de vente aux ench�res invalide */
    public final static MessageRef AUCTION_STATUS_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, AUCTION_STATUS);
    /** D�finition de la r�f�rence de message d'erreur d'une vente aux ench�res non d�marr�e */
    public final static MessageRef AUCTION_STATUS_NOT_STARTED_ERROR = new MessageRef("not_started_" + SUFFIX_ERROR, AUCTION_STATUS);
    /** D�finition de la r�f�rence de message d'erreur d'une vente aux ench�res non termin�e */
    public final static MessageRef AUCTION_STATUS_NOT_ENDED_ERROR = new MessageRef("not_ended_" + SUFFIX_ERROR, AUCTION_STATUS);
    /** D�finition de la r�f�rence de message d'erreur d'une vente aux ench�res termin�e */
    public final static MessageRef AUCTION_STATUS_ENDED_ERROR = new MessageRef("ended_" + SUFFIX_ERROR, AUCTION_STATUS);

    public final static MessageRef AUCTION_VALUE= new MessageRef(SUFFIX_VALUE, AUCTION);
    public final static MessageRef AUCTION_VALUE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, AUCTION_VALUE);

    /** D�finition de la r�f�rence de message li�e aux ench�res */
    public final static MessageRef AUCTION_BID = new MessageRef("bid", AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'une ench�re manquante */
    public final static MessageRef AUCTION_BID_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, AUCTION_BID);
    /** D�finition de la r�f�rence de message d'erreur d'une ench�re d�j� d�finie */
    public final static MessageRef AUCTION_BID_DEFINED_ERROR = new MessageRef(SUFFIX_DEFINED_ERROR, AUCTION_BID);
    /** D�finition de la r�f�rence de message d'erreur d'une ench�re invalide */
    public final static MessageRef AUCTION_BID_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, AUCTION_BID);

    /** D�finition de la r�f�rence de message li�e aux nombres d'ench�res */
    public final static MessageRef AUCTION_BID_NB = new MessageRef("nb", AUCTION_BID);
    /** D�finition de la r�f�rence de message d'erreur d'un nombres d'ench�res invalide */
    public final static MessageRef AUCTION_BID_NB_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, AUCTION_BID_NB);

    /** D�finition de la r�f�rence de message li�e aux conditions de vente aux ench�res */
    public final static MessageRef AUCTION_TERMS = new MessageRef("terms", AUCTION);
    /** D�finition de la r�f�rence de message d'erreur de conditions de vente aux ench�res manquantes */
    public final static MessageRef AUCTION_TERMS_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, AUCTION_TERMS);
    /** D�finition de la r�f�rence de message d'erreur de conditions de vente aux ench�res invalides */
    public final static MessageRef AUCTION_TERMS_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, AUCTION_TERMS);

    /** D�finition de la r�f�rence de message li�e � la planification de vente aux ench�res */
    public final static MessageRef AUCTION_SCHEDULE = new MessageRef("schedule", AUCTION);
    /** D�finition de la r�f�rence de message d'erreur d'une planification de vente aux ench�res manquante */
    public final static MessageRef AUCTION_SCHEDULE_MISSING_ERROR = new MessageRef(SUFFIX_MISSING_ERROR, AUCTION_SCHEDULE);
    /** D�finition de la r�f�rence de message d'erreur d'une planification de vente aux ench�res invalide */
    public final static MessageRef AUCTION_SCHEDULE_INVALID_ERROR = new MessageRef(SUFFIX_INVALID_ERROR, AUCTION_SCHEDULE);
  }
  /** TODO A COMMENTER */
  //public final static MessageRef ERROR_AUCTION_NOT_BOOKED = new MessageRef("error.auction.not_booked");
  /** TODO A COMMENTER */
  //public final static MessageRef ERROR_AUCTION_ALREADY_BOOKED = new MessageRef("error.auction.already_booked");

  /** TODO A COMMENTER */
  public final static MessageRef INFO_LANGUAGE_CHANGE_NOT_NEEDED = new MessageRef("info.language.change_not_needed");

  /**
   * Constructeur
   * @param code Code de la r�f�rence de message
   */
  protected MessageRef(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de la r�f�rence de message
   * @param parent Parent de la r�f�rence de message
   */
  protected MessageRef(String code, MessageRef parent)
  {
    super(code, parent);
  }
}