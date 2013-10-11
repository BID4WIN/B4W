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
  /** Énumération des types de filtrage d'un compte utilisateur */
  public static enum Filter
  {
    /** Filtrage par login */
    LOGIN,
    /** Filtrage par adresse e-mail */
    EMAIL,
    /** Filtrage par prénom */
    FIRST_NAME,
    /** Filtrage par nom de famille */
    LAST_NAME,
    /** Filtrage par adresse de livraison */
    ADDRESS,
    /** Filtrage par numéro de téléphone */
    PHONE_NUMBER,
    /** Filtrage par filtrage par pays de connexion */
    COUNTRY;
  }

  /**
   * Cette méthode vérifie la présence de la chaîne à rechercher dans les
   * attributs, du compte utilisateur, présents dans la liste de filtres passée
   * en paramètre
   * @param account le compte utilisateur à filtrer
   * @param searchString la chaîne de caractères à rechercher
   * @param filterList la liste des filtres à appliquer
   * @return Le compte utilisateur s'il a passé le filtre, null sinon échoue
   * @throws ModelArgumentException losqu'un filtre non attendu est fournit
   */
  @SuppressWarnings("null")
  public static Account getFilteredAccount(Account account, String searchString, Bid4WinList<UtilAccount.Filter> filterList) throws ModelArgumentException
  {
    // Si la chaîne à rechercher est nulle ou vide on renvoi le compte
    // utilisateur tel quel
    if(searchString == null || searchString.equals(""))
    {
      return account;
    }
    // Le pattern issue de la chaîne de caractères à rechercher
    Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
    // Le type de filtre utilisé par la méthode pour filtrer le compte
    // utilisateur
    UtilAccount.Filter filter = filterList.getFirst();
    // Le matcher à utiliser est déterminé en fonction du filtre
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
    // Si le compte utilisateur passe le filtre il est renvoyé sinon on renvoi
    // le résultat du filtrage par les filtres suivants
    if(matcher.find())
    {
      return account;
    }
    return UtilAccount.getFilteredAccount(account, searchString, filterList.subList(1));
  }

  /**
   * Cette méthode vérifie la présence de la chaîne à rechercher dans les
   * attributs des comptes utilisateur. La liste de filtres à utiliser permet de
   * préciser dans quels attributs des comptes utilisateur la chaîne de
   * caractère devra être présente.
   * @param accountList la liste de comptes utilisateur à filtrer
   * @param searchString La chaîne à rechercher
   * @param filterList la liste des filtres à appliquer
   * @return la liste des comptes utilisateur ayant passé le(s) filtre(s)
   * @throws ModelArgumentException Si la manipulation d'une propriété échoue
   */
  public static Bid4WinList<Account> getFilteredAccountList(Bid4WinList<Account> accountList, String searchString, Bid4WinList<UtilAccount.Filter> filterList) throws ModelArgumentException
  {
    // Une nouvelle liste est remplie avec les comptes utilisateur filtrés
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
    // La nouvelle liste de comptes utilisateurs filtrés est renvoyée
    return filteredAccountList;
  }
}
