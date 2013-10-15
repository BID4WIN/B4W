package com.bid4win.commons.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.bid4win.commons.core.collection.Bid4WinSameTypeMap;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les cha�nes de caract�res<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilString
{
  /** Cha�ne de caract�res vide */
  public final static String EMPTY = "";
  /** Caract�re par d�faut repr�sentant une nouvelle ligne */
  public final static String SEPARATOR_NEW_LINE = "\n";
  /** Caract�re par d�faut repr�sentant un retour � la ligne (sp�cifique windows) */
  public final static String SEPARATOR_CARRIAGE_RETURN = new String(new char[]{(char)13});
  /** Caract�re par d�faut repr�sentant une tabulation */
  public final static String SEPARATOR_INDENTATION = "  ";
  /** Map des caract�res sp�ciaux par leurs codes HTML correspondants */
  public static Bid4WinSameTypeMap<String> HTML_MAP = new Bid4WinSameTypeMap<String>(new String[][]
    { {"&", "&amp;"},       {"�", "&brvbar;"},  {"�", "&#130;"},   {"�", "&#131;"},
      {"�", "&#132;"},      {"�", "&#133;"},   {"�", "&#134;"},   {"�", "&#135;"},
      {"�", "&#136;"},      {"�", "&#137;"},   {"�", "&#138;"},   {"�", "&lt;"},
      {"�", "&#140;"},      {"�", "&#142;"},   {"�", "&#145;"},   {"�", "&#146;"},
      {"�", "&#147;"},      {"�", "&#148;"},   {"�", "&#149;"},   {"�", "&#150;"},
      {"�", "&#151;"},      {"�", "&#152;"},   {"�", "&#153;"},   {"�", "&#154;"},
      {"�", "&gt;"},        {"�", "&sect;"},   {"�", "&oelig;"},  {"�", "&#158;"},
      {"espace", "&nbsp;"}, {"�", "&iexcl;"},  {"�", "&cent;"},   {"�", "&curren;"},
      {"�", "&pound;"},     {"�", "&yen"},     {"�", "&euro;"},   {"$", "$"},
      {"�", "&uml;"},       {"�", "&copy;"},   {"�", "&ordf;"},   {"�", "&laquo;"},
      {"�", "&not;"},       {"�", "&shy;"},    {"�", "&reg;"},    {"�", "&masr;"},
      {"�", "&deg;"},       {"�", "&plusmn;"}, {"�", "&sup2;"},   {"�", "&sup3;"},
      {"�", "&acute;"},     {"�", "&micro;"},  {"�", "&para;"},   {"�", "&middot;"},
      {"�", "&cedil;"},     {"�", "&sup1;"},   {"�", "&ordm;"},   {"�", "&raquo;"},
      {"�", "&frac14;"},    {"�", "&frac12;"}, {"�", "&frac34;"}, {"�", "&iquest;"},
      {"�", "&Agrave;"},    {"�", "&Aacute;"}, {"�", "&Acirc;"},  {"�", "&Atilde;"},
      {"�", "&Auml;"},      {"�", "&Aring;"},  {"�", "&Aelig"},   {"�", "&Ccedil;"},
      {"�", "&Egrave;"},    {"�", "&Eacute;"}, {"�", "&Ecirc;"},  {"�", "&Euml;"},
      {"�", "&Igrave;"},    {"�", "&Iacute;"}, {"�", "&Icirc;"},  {"�", "&Iuml;"},
      {"�", "&eth;"},       {"�", "&Ntilde;"}, {"�", "&Ograve;"}, {"�", "&Oacute;"},
      {"�", "&Ocirc;"},     {"�", "&Otilde;"}, {"�", "&Ouml;"},   {"�", "&times;"},
      {"�", "&Oslash;"},    {"�", "&Ugrave;"}, {"�", "&Uacute;"}, {"�", "&Ucirc;"},
      {"�", "&Uuml;"},      {"�", "&Yacute;"}, {"�", "&thorn;"},  {"�", "&szlig;"},
      {"�", "&agrave;"},    {"�", "&aacute;"}, {"�", "&acirc;"},  {"�", "&atilde;"},
      {"�", "&auml;"},      {"�", "&aring;"},  {"�", "&aelig;"},  {"�", "&ccedil;"},
      {"�", "&egrave;"},    {"�", "&eacute;"}, {"�", "&ecirc;"},  {"�", "&euml;"},
      {"�", "&igrave;"},    {"�", "&iacute;"}, {"�", "&icirc;"},  {"�", "&iuml;"},
      {"�", "&eth;"},       {"�", "&ntilde;"}, {"�", "&ograve;"}, {"�", "&oacute;"},
      {"�", "&ocirc;"},     {"�", "&otilde;"}, {"�", "&ouml;"},   {"�", "&divide;"},
      {"�", "&oslash;"},    {"�", "&ugrave;"}, {"�", "&uacute;"}, {"�", "&ucirc;"},
      {"�", "&uuml;"},      {"�", "&yacute;"}, {"�", "&thorn;"},  {"�", "&yuml;"},
      {"�", "&Yuml;"}});

  /**
   * Permet de supprimer les blancs non int�ressants d'une cha�ne de caract�res
   * (cad en d�but et fin de celle-ci) tout en s'affranchissant de tester si la
   * cha�ne est nulle. Dans le cas o� celle-ci serait nulle, retourne une cha�ne
   * vide
   * @param toTrim Cha�ne de caract�res qui doit �tre nettoy�e de ses blancs inutiles
   * @return La cha�ne de caract�res nettoy�e ou une cha�ne vide si null en argument
   */
  public static String trimNotNull(String toTrim)
  {
    if(toTrim == null)
    {
      return UtilString.EMPTY;
    }
    return toTrim.trim();
  }

  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @param stackLevel TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public static String checkContains(String name, String string,
                                     String pattern, int stackLevel)
         throws ModelArgumentException
  {
    if(string.indexOf(pattern) < 0)
    {
      ModelArgumentException.wrongPattern(name, string, pattern, 1 + stackLevel);
    }
    return string;
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public static String checkContains(String name, String string, String pattern)
         throws ModelArgumentException
  {
    return UtilString.checkContains(name, string, pattern, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @param ref TODO A COMMENTER
   * @param stackLevel TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static String checkContains(String name, String string, String pattern,
                                     MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilString.checkContains(name, string, pattern, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @param ref TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static String checkContains(String name, String string,
                                     String pattern, MessageRef ref)
         throws UserException
  {
    return UtilString.checkContains(name, string, pattern, ref, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @param stackLevel TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public static String checkNotContains(String name, String string,
                                        String pattern, int stackLevel)
         throws ModelArgumentException
  {
    if(string.indexOf(pattern) >= 0)
    {
      ModelArgumentException.wrongPattern(name, string, pattern, 1 + stackLevel);
    }
    return string;
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public static String checkNotContains(String name, String string, String pattern)
         throws ModelArgumentException
  {
    return UtilString.checkNotContains(name, string, pattern, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @param ref TODO A COMMENTER
   * @param stackLevel TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static String checkNotContains(String name, String string, String pattern,
                                        MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilString.checkNotContains(name, string, pattern, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param name TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param pattern TODO A COMMENTER
   * @param ref TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static String checkNotContains(String name, String string,
                                        String pattern, MessageRef ref)
         throws UserException
  {
    return UtilString.checkNotContains(name, string, pattern, ref, 1);
  }
  /**
   * Cette m�thode permet de tester le pattern d'une cha�ne de caract�res
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param pattern Pattern attendue pour la cha�ne de caract�res
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La cha�ne de caract�res test�e
   * @throws ModelArgumentException Si le pattern n'est pas respect� par la cha�ne
   * de caract�res
   */
  public static String checkPattern(String name, String string,
                                    String pattern, int stackLevel)
         throws ModelArgumentException
  {
    if(!string.matches(pattern))
    {
      ModelArgumentException.wrongPattern(name, string, pattern, 1 + stackLevel);
    }
    return string;
  }
  /**
   * Cette m�thode permet de tester le pattern d'une cha�ne de caract�res
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param pattern Pattern attendue pour la cha�ne de caract�res
   * @return La cha�ne de caract�res test�e
   * @throws ModelArgumentException Si le pattern n'est pas respect� par la cha�ne
   * de caract�res
   */
  public static String checkPattern(String name, String string, String pattern)
         throws ModelArgumentException
  {
    return UtilString.checkPattern(name, string, pattern, 1);
  }
  /**
   * Cette m�thode permet de tester le pattern d'une cha�ne de caract�res
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param pattern Pattern attendue pour la cha�ne de caract�res
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si le pattern n'est pas respect� par la cha�ne de caract�res
   */
  public static String checkPattern(String name, String string, String pattern,
                                    MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilString.checkPattern(name, string, pattern, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette m�thode permet de tester le pattern d'une cha�ne de caract�res
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param pattern Pattern attendue pour la cha�ne de caract�res
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si le pattern n'est pas respect� par la cha�ne de caract�res
   */
  public static String checkPattern(String name, String string, String pattern,
                                    MessageRef ref)
         throws UserException
  {
    return UtilString.checkPattern(name, string, pattern, ref, 1);
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res est vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La cha�ne de caract�res test�e
   * @throws ModelArgumentException Si la cha�ne de caract�res n'est pas vide
   */
  public static String checkEmpty(String name, String string, int stackLevel)
         throws ModelArgumentException
  {
    if(!UtilString.EMPTY.equals(string))
    {
      ModelArgumentException.notEmptyString(name, string, 1 + stackLevel);
    }
    return string;
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res est vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @return La cha�ne de caract�res test�e
   * @throws ModelArgumentException Si la cha�ne de caract�res n'est pas vide
   */
  public static String checkEmpty(String name, String string) throws ModelArgumentException
  {
    return UtilString.checkEmpty(name, string, 1);
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res est vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si la cha�ne de caract�res n'est pas vide
   */
  public static String checkEmpty(String name, String string,
                                  MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilString.checkEmpty(name, string, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res est vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si la cha�ne de caract�res n'est pas vide
   */
  public static String checkEmpty(String name, String string, MessageRef ref)
         throws UserException
  {
    return UtilString.checkEmpty(name, string, ref, 1);
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res n'est pas vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La cha�ne de caract�res test�e
   * @throws ModelArgumentException Si la cha�ne de caract�res est pas vide
   */
  public static String checkNotEmpty(String name, String string, int stackLevel)
         throws ModelArgumentException
  {
    if(UtilString.EMPTY.equals(string))
    {
      ModelArgumentException.emptyString(name, 1 + stackLevel);
    }
    return string;
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res n'est pas vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @return La cha�ne de caract�res test�e
   * @throws ModelArgumentException Si la cha�ne de caract�res est pas vide
   */
  public static String checkNotEmpty(String name, String string) throws ModelArgumentException
  {
    return UtilString.checkNotEmpty(name, string, 1);
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res n'est pas vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si la cha�ne de caract�res est pas vide
   */
  public static String checkNotEmpty(String name, String string,
                                     MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilString.checkNotEmpty(name, string, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette m�thode permet de tester si une cha�ne de caract�res n'est pas vide
   * @param name Nom de la cha�ne de caract�res � tester
   * @param string Cha�ne de caract�res � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La cha�ne de caract�res test�e
   * @throws UserException Si la cha�ne de caract�res est pas vide
   */
  public static String checkNotEmpty(String name, String string, MessageRef ref)
         throws UserException
  {
    return UtilString.checkNotEmpty(name, string, ref, 1);
  }

  /**
   * Cette m�thode convertie une string cod�e en UTF-8
   * @param string La cha�ne de caract�res � convertir
   * @return La cha�ne de caract�res convertie
   */
  public static String decodeUTF8Parameter(String string)
  {
    try
    {
      return URLDecoder
          .decode(URLEncoder.encode(string, "ISO-8859-1"), "UTF-8");
    }
    catch(UnsupportedEncodingException e)
    {
      return null;
    }
  }

  /**
   * Cette m�thode permet de remplacer, au sein d'une cha�ne de caract�res, les
   * caract�res sp�ciaux par leur code HTML �quivalent.
   * @param string La cha�ne de caract�res � convertir
   * @return La cha�ne de caract�res convertie
   */
  public static String text2HTML(String string)
  {
    StringBuffer HTMLString = new StringBuffer();
    for(int i = 0 ; i < string.length() ; i++)
    {
      char textChar = string.charAt(i);
      String HTMLChar = HTML_MAP.get(Character.toString(textChar));
      if(HTMLChar != null)
      {
        HTMLString.append(HTMLChar);
      }
      else
      {
        HTMLString.append(textChar);
      }
    }
    return HTMLString.toString();
  }

  /**
   * Cette m�thode permet de tester si le buffer en argument commence par le prefix
   * d�fini
   * @param buffer Buffer dont on doit tester le commencement
   * @param prefix Prefix avec lequel on doit tester le commencement du buffer
   * @return True si le buffer commence par le prefix d�fini, false sinon
   */
  public static boolean startsWith(StringBuffer buffer, String prefix)
  {
    return buffer.indexOf(prefix) == 0;
  }
  /**
   * Cette m�thode permet de tester si le buffer en argument termine par le suffix
   * d�fini
   * @param buffer Buffer dont on doit tester la terminaison
   * @param suffix Suffix avec lequel on doit tester la terminaison du buffer
   * @return True si le buffer termine par le suffix d�fini, false sinon
   */
  public static boolean endsWith(StringBuffer buffer, String suffix)
  {
    return buffer.lastIndexOf(suffix) == buffer.length() - suffix.length();
  }

  /**
   * Cette fonction permet de compl�ter la cha�ne de caract�res en argument par
   * la gauche avec le caract�re choisi jusqu'� obtenir la taille d�sir�e. Si la
   * cha�ne de caract�res en argument a une taille sup�rieure ou �gale � celle
   * attendue, celle-ci ne sera pas modifi�e en sortie
   * @param toBeFilled Cha�ne de caract�res � completer
   * @param filler Caract�re � utiliser pour completer la cha�ne
   * @param desiredLength Longueur d�sir�e apr�s completion
   * @return La cha�ne de caract�res compl�t�e
   */
  public static StringBuffer fillBefore(StringBuffer toBeFilled, char filler, int desiredLength)
  {
    return toBeFilled.insert(0, UtilString.createRepeatedString(
        filler, desiredLength - toBeFilled.length()));
  }

  /**
   * Cette fonction permet de compl�ter la cha�ne de caract�res en argument par
   * la droite avec le caract�re choisi jusqu'� obtenir la taille d�sir�e. Si la
   * cha�ne de caract�res en argument a une taille sup�rieure ou �gale � celle
   * attendue, celle-ci ne sera pas modifi�e en sortie
   * @param toBeFilled Cha�ne de caract�res � completer
   * @param filler Caract�re � utiliser pour completer la cha�ne
   * @param desiredLength Longueur d�sir�e apr�s completion
   * @return La cha�ne de caract�res compl�t�e
   */
  public static StringBuffer fillAfter(StringBuffer toBeFilled, char filler, int desiredLength)
  {
    return toBeFilled.append(UtilString.createRepeatedString(
        filler, desiredLength - toBeFilled.length()));
  }

  /**
   * Permet de supprimer les lignes vides non int�ressantes d'une cha�ne de caract�res
   * (cad en d�but et fin de celle-ci) tout en s'affranchissant de tester si la
   * cha�ne est nulle. Dans le cas ou celle si serait nulle, retourne une cha�ne
   * vide
   * @param toTrim Cha�ne de caract�res qui doit �tre nettoy�e de ses lignes vides
   * inutiles
   * @return La cha�ne de caract�res nettoy�e
   */
  public static StringBuffer trimEmptyLine(StringBuffer toTrim)
  {
    return UtilString.trimEmptyLine(toTrim, UtilString.SEPARATOR_NEW_LINE);
  }
  /**
   * Permet de supprimer les lignes vides (la cha�ne de caract�res repr�sentant un
   * changement de ligne �tant fournie en argument) non int�ressantes d'une cha�ne
   * de caract�res (cad en d�but et fin de celle-ci) tout en s'affranchissant de
   * tester si la cha�ne est nulle. Dans le cas ou celle si serait nulle, retourne
   * une cha�ne vide
   * @param toTrim Cha�ne de caract�res qui doit �tre nettoy�e de ses lignes vides
   * inutiles
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @return La cha�ne de caract�res nettoy�e
   */
  public static StringBuffer trimEmptyLine(StringBuffer toTrim, String newLineSeparator)
  {
    // �vite de travailler sur une cha�ne nulle
    if(toTrim == null)
    {
      return new StringBuffer();
    }
    // Supprime la ligne vide en d�but de cha�ne
    while(UtilString.startsWith(toTrim, newLineSeparator))
    {
      toTrim.delete(0, newLineSeparator.length());
    }
    // Supprime la ligne vide en fin de cha�ne
    while(UtilString.endsWith(toTrim, newLineSeparator) && toTrim.length() != 0)
    {
      toTrim.delete(toTrim.length() - newLineSeparator.length(), toTrim.length());
    }
    return toTrim;
  }

  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner
   * @return Le texte auquel le paragraphe en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text, String paragraph)
  {
    return UtilString.addParagraph(text, new StringBuffer(paragraph), 0);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner
   * @return Le texte auquel le paragraphe en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text, StringBuffer paragraph)
  {
    return UtilString.addParagraph(text, paragraph, 0);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne en utilisant la
   * cha�ne de caract�res repr�sentant un changement de ligne fournie en argument
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @return Le texte auquel le paragraphe en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text, StringBuffer paragraph, String newLineSeparator)
  {
    return UtilString.addParagraph(text, paragraph, 0, newLineSeparator);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne et indent� du
   * nombre de tabulations d�fini en param�tre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner une fois indent�
   * @param tabsNb Taille de l'indentation du paragraphe
   * @return Le texte auquel le paragraphe indent� en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text, String paragraph, int tabsNb)
  {
    return UtilString.addParagraph(text, new StringBuffer(paragraph), tabsNb);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne et indent� du
   * nombre de tabulations d�fini en param�tre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner une fois indent�
   * @param tabsNb Taille de l'indentation du paragraphe
   * @return Le texte auquel le paragraphe indent� en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text, StringBuffer paragraph, int tabsNb)
  {
    return UtilString.addParagraph(text, paragraph, tabsNb, UtilString.SEPARATOR_INDENTATION);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne en utilisant la
   * cha�ne de caract�res repr�sentant un changement de ligne fournie en argument
   * et indent� du nombre de tabulations d�fini en param�tre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @param tabsNb Taille de l'indentation du paragraphe
   * @return Le texte auquel le paragraphe indent� en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text,
                                          StringBuffer paragraph,
                                          String newLineSeparator,
                                          int tabsNb)
  {
    return text.append(newLineSeparator).append(UtilString.indent(paragraph,
                                                                  newLineSeparator,
                                                                  tabsNb));
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne et indent� des
   * tabulations d�finies en param�tre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner une fois indent�
   * @param tabsNb Taille de l'indentation du paragraphe
   * @param tab Cha�ne de caract�res repr�sentant une tabulation
   * @return Le texte auquel le paragraphe indent� en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text,
                                          StringBuffer paragraph,
                                          int tabsNb,
                                          String tab)
  {
    return UtilString.addParagraph(text, paragraph, UtilString.SEPARATOR_NEW_LINE, tabsNb, tab);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne en utilisant la
   * cha�ne de caract�res repr�sentant un changement de ligne fournie en argument
   * et indent� des tabulations d�finies en param�tre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe � ajouter au texte � retourner une fois indent�
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @param tabsNb Taille de l'indentation du paragraphe
   * @param tab Cha�ne de caract�res repr�sentant une tabulation
   * @return Le texte auquel le paragraphe indent� en argument a �t� ajout�
   */
  public static StringBuffer addParagraph(StringBuffer text,
                                          StringBuffer paragraph,
                                          String newLineSeparator,
                                          int tabsNb,
                                          String tab)
  {
    return text.append(newLineSeparator).append(UtilString.indent(paragraph,
                                                                  newLineSeparator,
                                                                  tabsNb,
                                                                  tab));
  }

  /**
   * Cette fonction permet d'ajouter la ligne � la fin du texte en argument. La
   * ligne � ajouter peut �tre en fait constitu�e de plusieurs. Dans ce cas, la
   * premi�re ligne sera ajout�e au texte, et les autres lignes seront ajout�es
   * en s'indentant par rapport au premier ajout
   * @param text Texte dont la derni�re ligne doit �tre compl�t�e de celle en argument
   * @param line Ligne � ajouter � la fin du texte � retourner
   * @return Le texte auquel la ligne en argument a �t� ajout�
   */
  public static StringBuffer addLine(StringBuffer text, StringBuffer line)
  {
    return UtilString.addLine(text, line, UtilString.SEPARATOR_NEW_LINE);
  }
  /**
   * Cette fonction permet d'ajouter la ligne � la fin du texte en argument. La
   * ligne � ajouter peut �tre en fait constitu�e de plusieurs. Dans ce cas, la
   * premi�re ligne sera ajout�e au texte, et les autres lignes seront ajout�es
   * en s'indentant par rapport au premier ajout et en utilisant la cha�ne de
   * caract�res repr�sentant un changement de ligne fournie en argument
   * @param text Texte dont la derni�re ligne doit �tre compl�t�e de celle en argument
   * @param line Ligne � ajouter � la fin du texte � retourner
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @return Le texte auquel la ligne en argument a �t� ajout�
   */
  public static StringBuffer addLine(StringBuffer text, StringBuffer line, String newLineSeparator)
  {
    // La ligne est simple, on l'ajoute juste
    if(line.indexOf(newLineSeparator) < 0)
    {
      return text.append(line);
    }
    // On calcule la taille de l'indentation du paragraphe
    int tabSize = text.lastIndexOf(newLineSeparator);
    if(tabSize < 0)
    {
      tabSize = text.length();
    }
    else
    {
      tabSize = text.length() - 1 - tabSize;
    }
    // On ajoute la premi�re ligne du paragraphe
    text.append(line.substring(0, line.indexOf(newLineSeparator)));
    // On ajoute la suite du paragraphe
    line = new StringBuffer(line.substring(line.indexOf(newLineSeparator) + 1));
    return UtilString.addParagraph(text, line, newLineSeparator, tabSize, " ");
  }

  /**
   * Cette fonction permet d'indenter le paragraphe en argument du nombre de
   * tabulations d�fini
   * @param paragraph Paragraphe � indenter
   * @param tabsNb Nombre de tabulations � utiliser pour l'indentation
   * @return Le paragraphe correctement indent�
   */
  public static StringBuffer indent(StringBuffer paragraph, int tabsNb)
  {
    return UtilString.indent(paragraph, UtilString.SEPARATOR_NEW_LINE, tabsNb);
  }
  /**
   * Cette fonction permet d'indenter le paragraphe en argument du nombre de
   * tabulations d�fini en utilisant la cha�ne de caract�res repr�sentant un
   * changement de ligne fournie en argument
   * @param paragraph Paragraphe � indenter
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @param tabsNb Nombre de tabulations � utiliser pour l'indentation
   * @return Le paragraphe correctement indent�
   */
  public static StringBuffer indent(StringBuffer paragraph, String newLineSeparator, int tabsNb)
  {
    return UtilString.indent(paragraph, newLineSeparator, tabsNb, UtilString.SEPARATOR_INDENTATION);
  }
  /**
   * Cette fonction permet d'indenter le paragraphe en argument de la tabulation
   * d�finie
   * @param paragraph Paragraphe � indenter
   * @param tabsNb Nombre de tabulations � utiliser pour l'indentation
   * @param tab Cha�ne de caract�res repr�sentant une tabulation
   * @return Le paragraphe correctement indent�
   */
  public static StringBuffer indent(StringBuffer paragraph, int tabsNb, String tab)
  {
    return UtilString.indent(paragraph, UtilString.SEPARATOR_NEW_LINE, tabsNb, tab);
  }
  /**
   * Cette fonction permet d'indenter le paragraphe en argument de la tabulation
   * d�finie en utilisant la cha�ne de caract�res repr�sentant un changement de
   * ligne fournie en argument
   * @param paragraph Paragraphe � indenter
   * @param newLineSeparator Cha�ne de caract�res repr�sentant un changement de ligne
   * @param tabsNb Nombre de tabulations � utiliser pour l'indentation
   * @param tab Cha�ne de caract�res repr�sentant une tabulation
   * @return Le paragraphe correctement indent�
   */
  public static StringBuffer indent(StringBuffer paragraph,
                                    String newLineSeparator,
                                    int tabsNb,
                                    String tab)
  {
    tab = UtilString.createRepeatedString(tab, tabsNb).toString();
    StringBuffer result = new StringBuffer(tab);
    int index = -1;
    while(index < paragraph.length())
    {
      index++;
      int next = paragraph.indexOf(newLineSeparator, index);
      if(next < 0)
      {
        result.append(paragraph.substring(index));
        index = paragraph.length();
      }
      else
      {
        next += newLineSeparator.length();
        result.append(paragraph.substring(index, next)).append(tab);
        index = next-1;
      }
    }
    return result;
  }

  /**
   * Permet de r�p�ter une cha�ne de caract�res choisie
   * @param repetition Cha�ne de caract�res � r�p�ter
   * @param nbTimes Nombre de fois dont la cha�ne de caract�res va �tre r�p�t�e
   * @return La r�p�tition de la cha�ne de caract�res choisie
   */
  public static StringBuffer createRepeatedString(String repetition, int nbTimes)
  {
    // La cha�ne � cr�er doit �tre vide
    if(nbTimes <= 0)
    {
      return new StringBuffer();
    }
    StringBuffer repeatedString = new StringBuffer(repetition.length() * nbTimes);
    // Rempli la cha�ne de caract�res avec des r�p�titions de celle pass�e en argument
    for(int i = 0 ; i < nbTimes ; i++)
    {
      repeatedString.append(repetition);
    }
    return repeatedString;
  }

  /**
   * Permet de r�p�ter un caract�re choisi
   * @param repetition Caract�re � r�p�ter
   * @param nbTimes Nombre de fois dont le caract�re va �tre r�p�t�
   * @return La r�p�tition du caract�re choisi
   */
  public static StringBuffer createRepeatedString(char repetition, int nbTimes)
  {
    return UtilString.createRepeatedString(String.valueOf(repetition), nbTimes);
  }
}
