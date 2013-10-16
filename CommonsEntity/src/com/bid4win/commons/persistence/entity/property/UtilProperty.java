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
 * Classe utilitaire sur les propri�t�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilProperty
{
  /** Pattern des cl�s de propri�t�s */
  private final static String KEY_PATTERN = "[0-9a-z]+([_][0-9a-z]+)*";
  /** S�parateur utilis� entre les cl�s */
  public final static String KEY_SEPARATOR = ".";

  /**
   * Cette m�thode permet de r�cup�rer la premi�re cl� de la cl� compl�te en argument
   * @param fullKey Cl� compl�te dont il faut extraire la premi�re cl�
   * @return La premi�re cl� contenue dans la cl� compl�te en argument
   */
  public static String getFirstKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.indexOf(UtilProperty.KEY_SEPARATOR);
    // La cl� est simple
    if(index < 0)
    {
      return fullKey;
    }
    // La cl� est compos�e
    return fullKey.substring(0, index);
  }
  /**
   * Cette m�thode permet de r�cup�rer la derni�re cl� de la cl� compl�te en argument
   * @param fullKey Cl� compl�te dont il faut extraire la derni�re cl�
   * @return La derni�re cl� contenue dans la cl� compl�te en argument
   */
  public static String getLastKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.lastIndexOf(UtilProperty.KEY_SEPARATOR);
    // La cl� est simple
    if(index < 0)
    {
      return fullKey;
    }
    // La cl� est compos�e
    return fullKey.substring(index + UtilProperty.KEY_SEPARATOR.length());
  }
  /**
   * Cette m�thode permet de retirer la premi�re cl� de la cl� compl�te en argument
   * @param fullKey Cl� compl�te dont il faut retirer la premi�re cl�
   * @return La cl� compl�te en argument de laquelle on a retirer la premi�re cl�
   * ou une cha�ne vide si la cl� n'�tait pas compos�e
   */
  public static String removeFirstKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.indexOf(UtilProperty.KEY_SEPARATOR);
    // La cl� est simple
    if(index < 0)
    {
      return UtilString.EMPTY;
    }
    // La cl� est compos�e
    return fullKey.substring(index + UtilProperty.KEY_SEPARATOR.length());
  }
  /**
   * Cette m�thode permet de retirer la derni�re cl� de la cl� compl�te en argument
   * @param fullKey Cl� compl�te dont il faut retirer la derni�re cl�
   * @return La cl� compl�te en argument de laquelle on a retirer la v cl�
   * ou une cha�ne vide si la cl� n'�tait pas compos�e
   */
  public static String removeLastKey(String fullKey)
  {
    fullKey = UtilString.trimNotNull(fullKey);
    int index = fullKey.lastIndexOf(UtilProperty.KEY_SEPARATOR);
    // La cl� est simple
    if(index < 0)
    {
      return UtilString.EMPTY;
    }
    // La cl� est compos�e
    return fullKey.substring(0, index);
  }
  /**
   * Cette m�thode permet d'ajouter une cl� de propri�t� sur une base d�j� constitu�e
   * @param baseKey Base de la cl� � construire
   * @param key Cl� � ajouter � la base en argument
   * @return La cl� compl�te cr��e � partir des informations en argument
   */
  public static String addKey(String baseKey, String key)
  {
    baseKey = UtilString.trimNotNull(baseKey);
    // La cl� compl�te doit �tre d�finie � partir de la base donn�e
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
   * Cette m�thode permet de tester si la cl� donn�e peut �tre consid�r�e comme
   * base de la cl� compl�te en argument
   * @param fullKey Cl� compl�te dont on doit tester la potentielle base
   * @param testedBase Base � tester sur la cl� compl�te
   * @return True si la cl� donn�e peut �tre consid�r�e comme base de la cl�
   * compl�te en argument, false sinon
   */
  public static boolean isBaseForKey(String fullKey, String testedBase)
  {
    return fullKey.equals(testedBase) || fullKey.startsWith(testedBase + UtilProperty.KEY_SEPARATOR);
  }

  /**
   * Cette m�thode permet de tester que la cl� simple en param�tre est valide
   * @param key Cl� simple � tester
   * @param base Base des r�f�rences de message portant sur les propri�t�s
   * @return La cl� simple test�e
   * @throws UserException Si la cl� ne respecte pas le format attendu
   */
  public static String checkSimpleKey(String key, MessageRef base) throws UserException
  {
    return UtilString.checkPattern("key", key, UtilProperty.KEY_PATTERN,
                                   base.getSubMessageRef(MessageRef.SUFFIX_KEY,
                                                         MessageRef.SUFFIX_INVALID_ERROR),
                                   1);
  }
  /**
   * Cette m�thode permet de tester que la cl� compl�te en param�tre est valide
   * @param key Cl� compl�te � tester
   * @param base Base des r�f�rences de message portant sur les propri�t�s
   * @return La cl� compl�te test�e
   * @throws UserException Si la cl� ne respecte pas le format attendu
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
   * Cette m�thode renvoi une copie de la propri�t� en param�tre si elle, ou
   * l'une de ses propri�t�s filles, passent le filtre, � savoir la pr�sence de
   * la cha�ne � rechercher dans la cl� ou la valeur.
   * @param <PROPERTY> D�finition du type de propri�t� � filter
   * @param property La propri�t� � filtrer
   * @param searchString La cha�ne � rechercher
   * @return La propri�t� filtr�e
   * @throws ModelArgumentException Si la manipulation d'une propri�t� �choue
   * @throws UserException Si la manipulation d'une propri�t� �choue
   */
  public static <PROPERTY extends PropertyAbstract<PROPERTY, ?>>
         PROPERTY getFilteredProperty(PROPERTY property, String searchString)
         throws ModelArgumentException, UserException
  {
    // La propri�t� � retourner si elle, ou une de ses filles, passe le filtre
    PROPERTY result = property.createProperty(property.getKey(), property.getValue());
    // On filtre toutes les propri�t�s filles
    for(PROPERTY filteredProperty : UtilProperty.getFilteredPropertySet(property.getProperties(),
                                                                        searchString))
    {
      filteredProperty.linkTo(result);
    }
    // Si la propri�t� n'a pas de fille ou qu'aucune de ses filles n'a pass� le
    // filtre
    if(result.getPropertyNb() == 0)
    {
      // On v�rifie la pr�sence de la cha�ne � rechercher dans la cl� ou la
      // valeur de la propri�t�
      Matcher matcherKey = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getFullKey());
      Matcher matcherValue = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(property.getValue());
      if(!matcherKey.find() && !matcherValue.find())
      {
        // Si la cha�ne � rechercher n'est pr�sente ni dans la cl�, ni dans la
        // valeur de la propri�t� on retourne null
        return null;
      }
    }
    return result;
  }

  /**
   * Cette m�thode renvoi une copie des propri�t�s en param�tre si elles, ou
   * l'une de leurs propri�t�s filles, passent le filtre, � savoir la pr�sence de
   * la cha�ne � rechercher dans la cl� ou la valeur.
   * @param <PROPERTY> D�finition du type de propri�t� � filter
   * @param properties Propri�t�s � filtrer
   * @param searchString La cha�ne � rechercher
   * @return Le set de propri�t�s filtr�es
   * @throws ModelArgumentException Si la manipulation d'une propri�t� �choue
   * @throws UserException Si la manipulation d'une propri�t� �choue
   */
  public static <PROPERTY extends PropertyAbstract<PROPERTY, ?>>
         Bid4WinSet<PROPERTY> getFilteredPropertySet(Bid4WinCollection<PROPERTY> properties,
                                                     String searchString)
         throws ModelArgumentException, UserException
  {
    Bid4WinSet<PROPERTY> result = new Bid4WinSet<PROPERTY>();
    // On ajoute les propri�t�s qui passent le filtre
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
