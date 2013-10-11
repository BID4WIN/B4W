package com.bid4win.communication.json.factory;

import org.json.JSONObject;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.persistence.entity.account.Account;

/**
 * Singleton Factory class. Handles the conversion of Account between model and
 * JSON
 *
 * @author Maxime Ollagnier
 */
public class JSONAccountFactory
{
  /** JSON property id attribute name */
  public final static String ID = "id";
  /** JSON property status attribute name */
  public final static String STATUS = "status";
  /** JSON property login attribute name */
  public final static String LOGIN = "login";
  /** JSON property password attribute name */
  public final static String PASSWORD = "password";
  /** JSON property email attribute name */
  public final static String EMAIL = "email";
  /** JSON property bid rights number attribute name */
  public final static String BID_RIGHTS_NB = "bidRightsNb";
  /** JSON property bid rights value attribute name */
  public final static String BID_RIGHTS_VALUE = "bidRightsValue";
  /** JSON property user first name attribute name */
  public final static String FIRST_NAME = "firstName";
  /** JSON property user middle name attribute name */
  public final static String MIDDLE_NAME = "middleName";
  /** JSON property user last name attribute name */
  public final static String LAST_NAME = "lastName";
  /** JSON property user birth date attribute name */
  public final static String BIRTH_DATE = "birthDate";
  /** JSON property sponsor user id attribute name */
  public final static String SPONSOR_ID = "sponsorId";

  /** Unique instance used as singleton */
  private static JSONAccountFactory instance;
  static
  {
    JSONAccountFactory.instance = new JSONAccountFactory();
  }

  /**
   * Getter of the unique instance in memory
   * @return The unique instance in memory
   */
  public static JSONAccountFactory getInstance()
  {
    return JSONAccountFactory.instance;
  }

  /**
   * Protected constructor
   */
  protected JSONAccountFactory()
  {
    super();
  }

  /**
   * Returns an object generated from the given json
   * @param json The json object to generate the model object from
   * @return The generated model object
   */
 /* public Account getAccount(JSONObject json)
  {
    Account account = null;
    try
    {
      Login login = new Login(JSONFactory.getInstance().getString(json, JSONAccountFactory.LOGIN));
      Password password = new Password(JSONFactory.getInstance().getString(json, JSONAccountFactory.PASSWORD));
      Credential credential = new Credential(login, password);
      Email email = new Email(JSONFactory.getInstance().getString(json, JSONAccountFactory.EMAIL));

      account = new Account(credential, email);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return account;
  }*/

  /**
   * Returns an object list generated from the given jsonArray
   * @param jsonArray The JSONArray to generate the model object list from
   * @return The generated model object list
   */
 /* public List<Account> getAccountList(JSONArray jsonArray)
  {
    List<Account> accountList = new ArrayList<Account>();
    try
    {
      for(int i = 0 ; i < jsonArray.length() ; i++)
      {
        Account account = this.getAccount(jsonArray.getJSONObject(i));
        accountList.add(account);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return accountList;
  }*/

  /**
   * Returns a JSON object generated from the given account
   * @param account The account to generate the JSON object from
   * @return The generated JSON object
   */
  public JSONObject getJSONAccount(Account account)
  {
    JSONObject jsonAccount = new JSONObject();

    jsonAccount.put(JSONAccountFactory.ID, account.getId());
    jsonAccount.put(JSONAccountFactory.STATUS, "");//account.getStatus().getCode());
    jsonAccount.put(JSONAccountFactory.LOGIN, account.getCredential().getLogin().getValue());
    jsonAccount.put(JSONAccountFactory.EMAIL, account.getEmail().getAddress());
    jsonAccount.put(JSONAccountFactory.BID_RIGHTS_NB, 3);//String.valueOf(account.getBidRights().getNb()));
    jsonAccount.put(JSONAccountFactory.BID_RIGHTS_VALUE, "0.60");//account.getBidRights().getValue().toString());
    jsonAccount.put(JSONAccountFactory.FIRST_NAME, "prenom");//account.getUser().getFirstName());
    jsonAccount.put(JSONAccountFactory.MIDDLE_NAME, "secondPrenom");//account.getUser().getMiddleName());
    jsonAccount.put(JSONAccountFactory.LAST_NAME, "nom");//account.getUser().getLastName());
    jsonAccount.put(JSONAccountFactory.BIRTH_DATE, "08/07/1983");//UtilDate.formatDDIMMIYYYY(account.getUser().getBirthDate()));
    jsonAccount.put(JSONAccountFactory.SPONSOR_ID, "1");//account.getSponsorId());

    return jsonAccount;
  }

  /**
   * Returns a JSON account map generated from the given account list
   * @param accountList The account list to generate the JSON object from
   * @return The generated JSON object
   */
  public JSONObject getJSONAccountMap(Bid4WinList<Account> accountList)
  {
    if(accountList == null)
    {
      return null;
    }
    JSONObject jsonAccountMap = new JSONObject();
    for(int i = 0 ; i < accountList.size() ; i++)
    {
      jsonAccountMap.put(String.valueOf(accountList.get(i).hashCode()), this.getJSONAccount(accountList.get(i)));
    }
    return jsonAccountMap;
  }
}
