package com.bid4win.commons.persistence.entity.property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinCollection;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les propriétés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilProperty
{
  /** Pattern des clés de propriétés */
  private final static String KEY_PATTERN = "[0-9a-z]+([_][0-9a-z]+)*";
  /** Séparateur utilisé entre les clés */
  public final static String KEY_SEPARATOR = ".";

  /**
   * Cette méthode permet de récupérer la première clé de la clé complète en argument
   * @param fullKey Clé complète dont il faut extraire la première clé
   * @return La première clé contenue dans la clé complète en argument
   */
  public static String getFirstKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.indexOf(UtilProperty.KEY_SEPARATOR);
    // La clé est simple
    if(index < 0)
    {
      return fullKey;
    }
    // La clé est composée
    return fullKey.substring(0, index);
  }
  /**
   * Cette méthode permet de récupérer la dernière clé de la clé complète en argument
   * @param fullKey Clé complète dont il faut extraire la dernière clé
   * @return La dernière clé contenue dans la clé complète en argument
   */
  public static String getLastKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.lastIndexOf(UtilProperty.KEY_SEPARATOR);
    // La clé est simple
    if(index < 0)
    {
      return fullKey;
    }
    // La clé est composée
    return fullKey.substring(index + UtilProperty.KEY_SEPARATOR.length());
  }
  /**
   * Cette méthode permet de retirer la première clé de la clé complète en argument
   * @param fullKey Clé complète dont il faut retirer la première clé
   * @return La clé complète en argument de laquelle on a retirer la première clé
   * ou une chaîne vide si la clé n'était pas composée
   */
  public static String removeFirstKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.indexOf(UtilProperty.KEY_SEPARATOR);
    // La clé est simple
    if(index < 0)
    {
      return UtilString.EMPTY;
    }
    // La clé est composée
    return fullKey.substring(index + UtilProperty.KEY_SEPARATOR.length());
  }
  /**
   * Cette méthode permet de retirer la dernière clé de la clé complète en argument
   * @param fullKey Clé complète dont il faut retirer la dernière clé
   * @return La clé complète en argument de laquelle on a retirer la v clé
   * ou une chaîne vide si la clé n'était pas composée
   */
  public static String removeLastKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.lastIndexOf(UtilProperty.KEY_SEPARATOR);
    // La clé est simple
    if(index < 0)
    {
      return UtilString.EMPTY;
    }
    // La clé est composée
    return fullKey.substring(0, index);
  }
  /**
   * Cette méthode permet d'ajouter une clé de propriété sur une base déjà constituée
   * @param baseKey Base de la clé à construire
   * @param key Clé à ajouter à la base en argument
   * @return La clé complète créée à partir des informations en argument
   */
  public static String addKey(String baseKey, String key)
  {
    baseKey = UtilString.trimNotNull(baseKey);
    // La clé complète doit être définie à partir de la base donnée
    if(!baseKey.equals(UtilString.EMPTY))
    {
      key = baseKey + UtilProperty.KEY_SEPARATOR + key;
    }
    return key;
  }
  /**
   *
   * TODO A COMMENTER
   * @param keys TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static String computeKey(String ... keys)
  {
    String result = UtilString.EMPTY;
    for(String key : keys)
    {
      result = UtilProperty.addKey(result, key);
    }
    return result;
  }
  /**
   * Cette méthode permet de tester si la clé donnée peut être considérée comme
   * base de la clé complète en argument
   * @param fullKey Clé complète dont on doit tester la potentielle base
   * @param testedBase Base à tester sur la clé complète
   * @return True si la clé donnée peut être considérée comme base de la clé
   * complète en argument, false sinon
   */
  public static boolean isBaseForKey(String fullKey, String testedBase)
  {
    return fullKey.equals(testedBase) || fullKey.startsWith(testedBase + UtilProperty.KEY_SEPARATOR);
  }

  /**
   * Cette méthode permet de tester que la clé simple en paramètre est valide
   * @param key Clé simple à tester
   * @param base Base des références de message portant sur les propriétés
   * @return La clé simple testée
   * @throws UserException Si la clé ne respecte pas le format attendu
   */
  public static String checkSimpleKey(String key, MessageRef base) throws UserException
  {
    return UtilString.checkPattern("key", key, UtilProperty.KEY_PATTERN,
                                   base.getSubMessageRef(MessageRef.SUFFIX_KEY,
                                                         MessageRef.SUFFIX_INVALID_ERROR),
                                   1);
  }
  /**
   * Cette méthode permet de tester que la clé complète en paramètre est valide
   * @param key Clé complète à tester
   * @param base Base des références de message portant sur les propriétés
   * @return La clé complète testée
   * @throws UserException Si la clé ne respecte pas le format attendu
   */
  public static String checkFullKey(String key, MessageRef base) throws UserException
  {
    return UtilString.checkPattern(
        "key", key,
        UtilProperty.KEY_PATTERN + "(\\" + UtilProperty.KEY_SEPARATOR + UtilProperty.KEY_PATTERN + ")*",
        base.getSubMessageRef(MessageRef.SUFFIX_KEY, MessageRef.SUFFIX_INVALID_ERROR),
        1);
  }

  /**
   * Cette méthode renvoi une copie de la propriété en paramètre si elle, ou
   * l'une de ses propriétés filles, passent le filtre, à savoir la présence de
   * la chaîne à rechercher dans la clé ou la valeur.
   * @param <PROPERTY> Définition du type de propriété à filter
   * @param property La propriété à filtrer
   * @param searchString La chaîne à rechercher
   * @return La propriété filtrée
   * @throws ModelArgumentException Si la manipulation d'une propriété échoue
   * @throws UserException Si la manipulation d'une propriété échoue
   */
  public static <PROPERTY extends PropertyAbstract<PROPERTY, ?>>
         PROPERTY getFilteredProperty(PROPERTY property, String searchString)
         throws ModelArgumentException, UserException
  {
    // La propriété à retourner si elle, ou une de ses filles, passe le filtre
    PROPERTY result = property.createProperty(property.getKey(), property.getValue());
    // On filtre toutes les propriétés filles
    for(PROPERTY filteredProperty : UtilProperty.getFilteredPropertySet(property.getProperties(),
                                                                        searchString))
    {
      filteredProperty.linkTo(result);
    }
    // Si la propriété n'a pas de fille ou qu'aucune de ses filles n'a passé le
    // filtre
    if(result.getPropertyNb() == 0)
    {
      // On vérifie la présence de la chaîne à rechercher dans la clé ou la
      // valeur de la propriété
      Matcher matcherKey = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getFullKey());
      Matcher matcherValue = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getValue());
      if(!matcherKey.find() && !matcherValue.find())
      {
        // Si la chaîne à rechercher n'est présente ni dans la clé, ni dans la
        // valeur de la propriété on retourne null
        return null;
      }
    }
    return result;
  }

  /**
   * Cette méthode renvoi une copie des propriétés en paramètre si elles, ou
   * l'une de leurs propriétés filles, passent le filtre, à savoir la présence de
   * la chaîne à rechercher dans la clé ou la valeur.
   * @param <PROPERTY> Définition du type de propriété à filter
   * @param properties Propriétés à filtrer
   * @param searchString La chaîne à rechercher
   * @return Le set de propriétés filtrées
   * @throws ModelArgumentException Si la manipulation d'une propriété échoue
   * @throws UserException Si la manipulation d'une propriété échoue
   */
  public static <PROPERTY extends PropertyAbstract<PROPERTY, ?>>
         Bid4WinSet<PROPERTY> getFilteredPropertySet(Bid4WinCollection<PROPERTY> properties,
                                                     String searchString)
         throws ModelArgumentException, UserException
  {
    Bid4WinSet<PROPERTY> result = new Bid4WinSet<PROPERTY>();
    // On ajoute les propriétés qui passent le filtre
    for(PROPERTY property : properties)
    {
      PROPERTY filtered = UtilProperty.getFilteredProperty(property, searchString);
      if(filtered != null)
      {
        result.add(filtered);
      }
    }
    return result;
  }
}
