package com.bid4win.persistence.entity.account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.ModelArgumentException;

/**
 * Classe utilitaire sur les comptes utilisateur<BR>
 * <BR>
 * @author Maxime Ollagnier
 */
public class UtilAccount
{
  /** �num�ration des types de filtrage d'un compte utilisateur */
  public static enum Filter
  {
    /** Filtrage par login */
    LOGIN,
    /** Filtrage par adresse e-mail */
    EMAIL,
    /** Filtrage par pr�nom */
    FIRST_NAME,
    /** Filtrage par nom de famille */
    LAST_NAME,
    /** Filtrage par adresse de livraison */
    ADDRESS,
    /** Filtrage par num�ro de t�l�phone */
    PHONE_NUMBER,
    /** Filtrage par filtrage par pays de connexion */
    COUNTRY;
  }

  /**
   * Cette m�thode v�rifie la pr�sence de la cha�ne � rechercher dans les
   * attributs, du compte utilisateur, pr�sents dans la liste de filtres pass�e
   * en param�tre
   * @param account le compte utilisateur � filtrer
   * @param searchString la cha�ne de caract�res � rechercher
   * @param filterList la liste des filtres � appliquer
   * @return Le compte utilisateur s'il a pass� le filtre, null sinon �choue
   * @throws ModelArgumentException losqu'un filtre non attendu est fournit
   */
  @SuppressWarnings("null")
  public static Account getFilteredAccount(Account account, String searchString, Bid4WinList<UtilAccount.Filter> filterList) throws ModelArgumentException
  {
    // Si la cha�ne � rechercher est nulle ou vide on renvoi le compte
    // utilisateur tel quel
    if(searchString == null || searchString.equals(""))
    {
      return account;
    }
    // Le pattern issue de la cha�ne de caract�res � rechercher
    Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
    // Le type de filtre utilis� par la m�thode pour filtrer le compte
    // utilisateur
    UtilAccount.Filter filter = filterList.getFirst();
    // Le matcher � utiliser est d�termin� en fonction du filtre
    Matcher matcher = null;
    if(filter == UtilAccount.Filter.LOGIN)
    {
      matcher = pattern.matcher(account.getCredential().getLogin().getValue());
    }
    else if(filter == UtilAccount.Filter.EMAIL)
    {
      matcher = pattern.matcher(account.getEmail().getAddress());
    }
    else if(filter == null)
    {
      return null;
    }
    else
    {
      ModelArgumentException.wrongParameterValue("filter", filter, UtilAccount.Filter.values(), 1);
    }
    // Si le compte utilisateur passe le filtre il est renvoy� sinon on renvoi
    // le r�sultat du filtrage par les filtres suivants
    if(matcher.find())
    {
      return account;
    }
    return UtilAccount.getFilteredAccount(account, searchString, filterList.subList(1));
  }

  /**
   * Cette m�thode v�rifie la pr�sence de la cha�ne � rechercher dans les
   * attributs des comptes utilisateur. La liste de filtres � utiliser permet de
   * pr�ciser dans quels attributs des comptes utilisateur la cha�ne de
   * caract�re devra �tre pr�sente.
   * @param accountList la liste de comptes utilisateur � filtrer
   * @param searchString La cha�ne � rechercher
   * @param filterList la liste des filtres � appliquer
   * @return la liste des comptes utilisateur ayant pass� le(s) filtre(s)
   * @throws ModelArgumentException Si la manipulation d'une propri�t� �choue
   */
  public static Bid4WinList<Account> getFilteredAccountList(Bid4WinList<Account> accountList, String searchString, Bid4WinList<UtilAccount.Filter> filterList) throws ModelArgumentException
  {
    // Une nouvelle liste est remplie avec les comptes utilisateur filtr�s
    Bid4WinList<Account> filteredAccountList = new Bid4WinList<Account>();
    for(Account account : accountList)
    {
      Account filteredAccount = UtilAccount.getFilteredAccount(account,
                                                               searchString,
                                                               filterList);
      if(filteredAccount != null)
      {
        filteredAccountList.add(filteredAccount);
      }
    }
    // La nouvelle liste de comptes utilisateurs filtr�s est renvoy�e
    return filteredAccountList;
  }
}
