package com.bid4win.commons.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.bid4win.commons.core.collection.Bid4WinSameTypeMap;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les chaînes de caractères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilString
{
  /** Chaîne de caractères vide */
  public final static String EMPTY = "";
  /** Caractère par défaut représentant une nouvelle ligne */
  public final static String SEPARATOR_NEW_LINE = "\n";
  /** Caractère par défaut représentant un retour à la ligne (spécifique windows) */
  public final static String SEPARATOR_CARRIAGE_RETURN = new String(new char[]{(char)13});
  /** Caractère par défaut représentant une tabulation */
  public final static String SEPARATOR_INDENTATION = "  ";
  /** Map des caractères spéciaux par leurs codes HTML correspondants */
  public static Bid4WinSameTypeMap<String> HTML_MAP = new Bid4WinSameTypeMap<String>(new String[][]
    { {"&", "&amp;"},       {"¦", "&brvbar;"},  {"‚", "&#130;"},   {"ƒ", "&#131;"},
      {"„", "&#132;"},      {"…", "&#133;"},   {"†", "&#134;"},   {"‡", "&#135;"},
      {"ˆ", "&#136;"},      {"‰", "&#137;"},   {"Š", "&#138;"},   {"‹", "&lt;"},
      {"Œ", "&#140;"},      {"Ž", "&#142;"},   {"‘", "&#145;"},   {"’", "&#146;"},
      {"“", "&#147;"},      {"”", "&#148;"},   {"•", "&#149;"},   {"–", "&#150;"},
      {"—", "&#151;"},      {"˜", "&#152;"},   {"™", "&#153;"},   {"š", "&#154;"},
      {"›", "&gt;"},        {"§", "&sect;"},   {"œ", "&oelig;"},  {"ž", "&#158;"},
      {"espace", "&nbsp;"}, {"¡", "&iexcl;"},  {"¢", "&cent;"},   {"¤", "&curren;"},
      {"£", "&pound;"},     {"¥", "&yen"},     {"€", "&euro;"},   {"$", "$"},
      {"¨", "&uml;"},       {"©", "&copy;"},   {"ª", "&ordf;"},   {"«", "&laquo;"},
      {"¬", "&not;"},       {"­", "&shy;"},    {"®", "&reg;"},    {"¯", "&masr;"},
      {"°", "&deg;"},       {"±", "&plusmn;"}, {"²", "&sup2;"},   {"³", "&sup3;"},
      {"´", "&acute;"},     {"µ", "&micro;"},  {"¶", "&para;"},   {"·", "&middot;"},
      {"¸", "&cedil;"},     {"¹", "&sup1;"},   {"º", "&ordm;"},   {"»", "&raquo;"},
      {"¼", "&frac14;"},    {"½", "&frac12;"}, {"¾", "&frac34;"}, {"¿", "&iquest;"},
      {"À", "&Agrave;"},    {"Á", "&Aacute;"}, {"Â", "&Acirc;"},  {"Ã", "&Atilde;"},
      {"Ä", "&Auml;"},      {"Å", "&Aring;"},  {"Æ", "&Aelig"},   {"Ç", "&Ccedil;"},
      {"È", "&Egrave;"},    {"É", "&Eacute;"}, {"Ê", "&Ecirc;"},  {"Ë", "&Euml;"},
      {"Ì", "&Igrave;"},    {"Í", "&Iacute;"}, {"Î", "&Icirc;"},  {"Ï", "&Iuml;"},
      {"Ð", "&eth;"},       {"Ñ", "&Ntilde;"}, {"Ò", "&Ograve;"}, {"Ó", "&Oacute;"},
      {"Ô", "&Ocirc;"},     {"Õ", "&Otilde;"}, {"Ö", "&Ouml;"},   {"×", "&times;"},
      {"Ø", "&Oslash;"},    {"Ù", "&Ugrave;"}, {"Ú", "&Uacute;"}, {"Û", "&Ucirc;"},
      {"Ü", "&Uuml;"},      {"Ý", "&Yacute;"}, {"Þ", "&thorn;"},  {"ß", "&szlig;"},
      {"à", "&agrave;"},    {"á", "&aacute;"}, {"â", "&acirc;"},  {"ã", "&atilde;"},
      {"ä", "&auml;"},      {"å", "&aring;"},  {"æ", "&aelig;"},  {"ç", "&ccedil;"},
      {"è", "&egrave;"},    {"é", "&eacute;"}, {"ê", "&ecirc;"},  {"ë", "&euml;"},
      {"ì", "&igrave;"},    {"í", "&iacute;"}, {"î", "&icirc;"},  {"ï", "&iuml;"},
      {"ð", "&eth;"},       {"ñ", "&ntilde;"}, {"ò", "&ograve;"}, {"ó", "&oacute;"},
      {"ô", "&ocirc;"},     {"õ", "&otilde;"}, {"ö", "&ouml;"},   {"÷", "&divide;"},
      {"ø", "&oslash;"},    {"ù", "&ugrave;"}, {"ú", "&uacute;"}, {"û", "&ucirc;"},
      {"ü", "&uuml;"},      {"ý", "&yacute;"}, {"þ", "&thorn;"},  {"ÿ", "&yuml;"},
      {"Ÿ", "&Yuml;"}});

  /**
   * Permet de supprimer les blancs non intéressants d'une chaîne de caractères
   * (cad en début et fin de celle-ci) tout en s'affranchissant de tester si la
   * chaîne est nulle. Dans le cas où celle-ci serait nulle, retourne une chaîne
   * vide
   * @param toTrim Chaîne de caractères qui doit être nettoyée de ses blancs inutiles
   * @return La chaîne de caractères nettoyée ou une chaîne vide si null en argument
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
   * Cette méthode permet de tester le pattern d'une chaîne de caractères
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param pattern Pattern attendue pour la chaîne de caractères
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La chaîne de caractères testée
   * @throws ModelArgumentException Si le pattern n'est pas respecté par la chaîne
   * de caractères
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
   * Cette méthode permet de tester le pattern d'une chaîne de caractères
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param pattern Pattern attendue pour la chaîne de caractères
   * @return La chaîne de caractères testée
   * @throws ModelArgumentException Si le pattern n'est pas respecté par la chaîne
   * de caractères
   */
  public static String checkPattern(String name, String string, String pattern)
         throws ModelArgumentException
  {
    return UtilString.checkPattern(name, string, pattern, 1);
  }
  /**
   * Cette méthode permet de tester le pattern d'une chaîne de caractères
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param pattern Pattern attendue pour la chaîne de caractères
   * @param ref La référence du message à utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La chaîne de caractères testée
   * @throws UserException Si le pattern n'est pas respecté par la chaîne de caractères
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
   * Cette méthode permet de tester le pattern d'une chaîne de caractères
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param pattern Pattern attendue pour la chaîne de caractères
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La chaîne de caractères testée
   * @throws UserException Si le pattern n'est pas respecté par la chaîne de caractères
   */
  public static String checkPattern(String name, String string, String pattern,
                                    MessageRef ref)
         throws UserException
  {
    return UtilString.checkPattern(name, string, pattern, ref, 1);
  }
  /**
   * Cette méthode permet de tester si une chaîne de caractères est vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La chaîne de caractères testée
   * @throws ModelArgumentException Si la chaîne de caractères n'est pas vide
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
   * Cette méthode permet de tester si une chaîne de caractères est vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @return La chaîne de caractères testée
   * @throws ModelArgumentException Si la chaîne de caractères n'est pas vide
   */
  public static String checkEmpty(String name, String string) throws ModelArgumentException
  {
    return UtilString.checkEmpty(name, string, 1);
  }
  /**
   * Cette méthode permet de tester si une chaîne de caractères est vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La chaîne de caractères testée
   * @throws UserException Si la chaîne de caractères n'est pas vide
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
   * Cette méthode permet de tester si une chaîne de caractères est vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La chaîne de caractères testée
   * @throws UserException Si la chaîne de caractères n'est pas vide
   */
  public static String checkEmpty(String name, String string, MessageRef ref)
         throws UserException
  {
    return UtilString.checkEmpty(name, string, ref, 1);
  }
  /**
   * Cette méthode permet de tester si une chaîne de caractères n'est pas vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La chaîne de caractères testée
   * @throws ModelArgumentException Si la chaîne de caractères est pas vide
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
   * Cette méthode permet de tester si une chaîne de caractères n'est pas vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @return La chaîne de caractères testée
   * @throws ModelArgumentException Si la chaîne de caractères est pas vide
   */
  public static String checkNotEmpty(String name, String string) throws ModelArgumentException
  {
    return UtilString.checkNotEmpty(name, string, 1);
  }
  /**
   * Cette méthode permet de tester si une chaîne de caractères n'est pas vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La chaîne de caractères testée
   * @throws UserException Si la chaîne de caractères est pas vide
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
   * Cette méthode permet de tester si une chaîne de caractères n'est pas vide
   * @param name Nom de la chaîne de caractères à tester
   * @param string Chaîne de caractères à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La chaîne de caractères testée
   * @throws UserException Si la chaîne de caractères est pas vide
   */
  public static String checkNotEmpty(String name, String string, MessageRef ref)
         throws UserException
  {
    return UtilString.checkNotEmpty(name, string, ref, 1);
  }

  /**
   * Cette méthode convertie une string codée en UTF-8
   * @param string La chaîne de caractères à convertir
   * @return La chaîne de caractères convertie
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
   * Cette méthode permet de remplacer, au sein d'une chaîne de caractères, les
   * caractères spéciaux par leur code HTML équivalent.
   * @param string La chaîne de caractères à convertir
   * @return La chaîne de caractères convertie
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
   * Cette méthode permet de tester si le buffer en argument commence par le prefix
   * défini
   * @param buffer Buffer dont on doit tester le commencement
   * @param prefix Prefix avec lequel on doit tester le commencement du buffer
   * @return True si le buffer commence par le prefix défini, false sinon
   */
  public static boolean startsWith(StringBuffer buffer, String prefix)
  {
    return buffer.indexOf(prefix) == 0;
  }
  /**
   * Cette méthode permet de tester si le buffer en argument termine par le suffix
   * défini
   * @param buffer Buffer dont on doit tester la terminaison
   * @param suffix Suffix avec lequel on doit tester la terminaison du buffer
   * @return True si le buffer termine par le suffix défini, false sinon
   */
  public static boolean endsWith(StringBuffer buffer, String suffix)
  {
    return buffer.lastIndexOf(suffix) == buffer.length() - suffix.length();
  }

  /**
   * Cette fonction permet de compléter la chaîne de caractères en argument par
   * la gauche avec le caractère choisi jusqu'à obtenir la taille désirée. Si la
   * chaîne de caractères en argument a une taille supérieure ou égale à celle
   * attendue, celle-ci ne sera pas modifiée en sortie
   * @param toBeFilled Chaîne de caractères à completer
   * @param filler Caractère à utiliser pour completer la chaîne
   * @param desiredLength Longueur désirée après completion
   * @return La chaîne de caractères complétée
   */
  public static StringBuffer fillBefore(StringBuffer toBeFilled, char filler, int desiredLength)
  {
    return toBeFilled.insert(0, UtilString.createRepeatedString(
        filler, desiredLength - toBeFilled.length()));
  }

  /**
   * Cette fonction permet de compléter la chaîne de caractères en argument par
   * la droite avec le caractère choisi jusqu'à obtenir la taille désirée. Si la
   * chaîne de caractères en argument a une taille supérieure ou égale à celle
   * attendue, celle-ci ne sera pas modifiée en sortie
   * @param toBeFilled Chaîne de caractères à completer
   * @param filler Caractère à utiliser pour completer la chaîne
   * @param desiredLength Longueur désirée après completion
   * @return La chaîne de caractères complétée
   */
  public static StringBuffer fillAfter(StringBuffer toBeFilled, char filler, int desiredLength)
  {
    return toBeFilled.append(UtilString.createRepeatedString(
        filler, desiredLength - toBeFilled.length()));
  }

  /**
   * Permet de supprimer les lignes vides non intéressantes d'une chaîne de caractères
   * (cad en début et fin de celle-ci) tout en s'affranchissant de tester si la
   * chaîne est nulle. Dans le cas ou celle si serait nulle, retourne une chaîne
   * vide
   * @param toTrim Chaîne de caractères qui doit être nettoyée de ses lignes vides
   * inutiles
   * @return La chaîne de caractères nettoyée
   */
  public static StringBuffer trimEmptyLine(StringBuffer toTrim)
  {
    return UtilString.trimEmptyLine(toTrim, UtilString.SEPARATOR_NEW_LINE);
  }
  /**
   * Permet de supprimer les lignes vides (la chaîne de caractères représentant un
   * changement de ligne étant fournie en argument) non intéressantes d'une chaîne
   * de caractères (cad en début et fin de celle-ci) tout en s'affranchissant de
   * tester si la chaîne est nulle. Dans le cas ou celle si serait nulle, retourne
   * une chaîne vide
   * @param toTrim Chaîne de caractères qui doit être nettoyée de ses lignes vides
   * inutiles
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @return La chaîne de caractères nettoyée
   */
  public static StringBuffer trimEmptyLine(StringBuffer toTrim, String newLineSeparator)
  {
    // Évite de travailler sur une chaîne nulle
    if(toTrim == null)
    {
      return new StringBuffer();
    }
    // Supprime la ligne vide en début de chaîne
    while(UtilString.startsWith(toTrim, newLineSeparator))
    {
      toTrim.delete(0, newLineSeparator.length());
    }
    // Supprime la ligne vide en fin de chaîne
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
   * @param paragraph Paragraphe à ajouter au texte à retourner
   * @return Le texte auquel le paragraphe en argument a été ajouté
   */
  public static StringBuffer addParagraph(StringBuffer text, String paragraph)
  {
    return UtilString.addParagraph(text, new StringBuffer(paragraph), 0);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner
   * @return Le texte auquel le paragraphe en argument a été ajouté
   */
  public static StringBuffer addParagraph(StringBuffer text, StringBuffer paragraph)
  {
    return UtilString.addParagraph(text, paragraph, 0);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne en utilisant la
   * chaîne de caractères représentant un changement de ligne fournie en argument
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @return Le texte auquel le paragraphe en argument a été ajouté
   */
  public static StringBuffer addParagraph(StringBuffer text, StringBuffer paragraph, String newLineSeparator)
  {
    return UtilString.addParagraph(text, paragraph, 0, newLineSeparator);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne et indenté du
   * nombre de tabulations défini en paramètre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner une fois indenté
   * @param tabsNb Taille de l'indentation du paragraphe
   * @return Le texte auquel le paragraphe indenté en argument a été ajouté
   */
  public static StringBuffer addParagraph(StringBuffer text, String paragraph, int tabsNb)
  {
    return UtilString.addParagraph(text, new StringBuffer(paragraph), tabsNb);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne et indenté du
   * nombre de tabulations défini en paramètre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner une fois indenté
   * @param tabsNb Taille de l'indentation du paragraphe
   * @return Le texte auquel le paragraphe indenté en argument a été ajouté
   */
  public static StringBuffer addParagraph(StringBuffer text, StringBuffer paragraph, int tabsNb)
  {
    return UtilString.addParagraph(text, paragraph, tabsNb, UtilString.SEPARATOR_INDENTATION);
  }
  /**
   * Cette fonction permet d'ajouter le paragraphe au texte en argument. Un ajout
   * de paragraphe se fera automatiquement sur une nouvelle ligne en utilisant la
   * chaîne de caractères représentant un changement de ligne fournie en argument
   * et indenté du nombre de tabulations défini en paramètre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @param tabsNb Taille de l'indentation du paragraphe
   * @return Le texte auquel le paragraphe indenté en argument a été ajouté
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
   * de paragraphe se fera automatiquement sur une nouvelle ligne et indenté des
   * tabulations définies en paramètre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner une fois indenté
   * @param tabsNb Taille de l'indentation du paragraphe
   * @param tab Chaîne de caractères représentant une tabulation
   * @return Le texte auquel le paragraphe indenté en argument a été ajouté
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
   * chaîne de caractères représentant un changement de ligne fournie en argument
   * et indenté des tabulations définies en paramètre
   * @param text Texte auquel ajouter le paragraphe
   * @param paragraph Paragraphe à ajouter au texte à retourner une fois indenté
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @param tabsNb Taille de l'indentation du paragraphe
   * @param tab Chaîne de caractères représentant une tabulation
   * @return Le texte auquel le paragraphe indenté en argument a été ajouté
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
   * Cette fonction permet d'ajouter la ligne à la fin du texte en argument. La
   * ligne à ajouter peut être en fait constituée de plusieurs. Dans ce cas, la
   * première ligne sera ajoutée au texte, et les autres lignes seront ajoutées
   * en s'indentant par rapport au premier ajout
   * @param text Texte dont la dernière ligne doit être complétée de celle en argument
   * @param line Ligne à ajouter à la fin du texte à retourner
   * @return Le texte auquel la ligne en argument a été ajouté
   */
  public static StringBuffer addLine(StringBuffer text, StringBuffer line)
  {
    return UtilString.addLine(text, line, UtilString.SEPARATOR_NEW_LINE);
  }
  /**
   * Cette fonction permet d'ajouter la ligne à la fin du texte en argument. La
   * ligne à ajouter peut être en fait constituée de plusieurs. Dans ce cas, la
   * première ligne sera ajoutée au texte, et les autres lignes seront ajoutées
   * en s'indentant par rapport au premier ajout et en utilisant la chaîne de
   * caractères représentant un changement de ligne fournie en argument
   * @param text Texte dont la dernière ligne doit être complétée de celle en argument
   * @param line Ligne à ajouter à la fin du texte à retourner
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @return Le texte auquel la ligne en argument a été ajouté
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
    // On ajoute la première ligne du paragraphe
    text.append(line.substring(0, line.indexOf(newLineSeparator)));
    // On ajoute la suite du paragraphe
    line = new StringBuffer(line.substring(line.indexOf(newLineSeparator) + 1));
    return UtilString.addParagraph(text, line, newLineSeparator, tabSize, " ");
  }

  /**
   * Cette fonction permet d'indenter le paragraphe en argument du nombre de
   * tabulations défini
   * @param paragraph Paragraphe à indenter
   * @param tabsNb Nombre de tabulations à utiliser pour l'indentation
   * @return Le paragraphe correctement indenté
   */
  public static StringBuffer indent(StringBuffer paragraph, int tabsNb)
  {
    return UtilString.indent(paragraph, UtilString.SEPARATOR_NEW_LINE, tabsNb);
  }
  /**
   * Cette fonction permet d'indenter le paragraphe en argument du nombre de
   * tabulations défini en utilisant la chaîne de caractères représentant un
   * changement de ligne fournie en argument
   * @param paragraph Paragraphe à indenter
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @param tabsNb Nombre de tabulations à utiliser pour l'indentation
   * @return Le paragraphe correctement indenté
   */
  public static StringBuffer indent(StringBuffer paragraph, String newLineSeparator, int tabsNb)
  {
    return UtilString.indent(paragraph, newLineSeparator, tabsNb, UtilString.SEPARATOR_INDENTATION);
  }
  /**
   * Cette fonction permet d'indenter le paragraphe en argument de la tabulation
   * définie
   * @param paragraph Paragraphe à indenter
   * @param tabsNb Nombre de tabulations à utiliser pour l'indentation
   * @param tab Chaîne de caractères représentant une tabulation
   * @return Le paragraphe correctement indenté
   */
  public static StringBuffer indent(StringBuffer paragraph, int tabsNb, String tab)
  {
    return UtilString.indent(paragraph, UtilString.SEPARATOR_NEW_LINE, tabsNb, tab);
  }
  /**
   * Cette fonction permet d'indenter le paragraphe en argument de la tabulation
   * définie en utilisant la chaîne de caractères représentant un changement de
   * ligne fournie en argument
   * @param paragraph Paragraphe à indenter
   * @param newLineSeparator Chaîne de caractères représentant un changement de ligne
   * @param tabsNb Nombre de tabulations à utiliser pour l'indentation
   * @param tab Chaîne de caractères représentant une tabulation
   * @return Le paragraphe correctement indenté
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
   * Permet de répéter une chaîne de caractères choisie
   * @param repetition Chaîne de caractères à répéter
   * @param nbTimes Nombre de fois dont la chaîne de caractères va être répétée
   * @return La répétition de la chaîne de caractères choisie
   */
  public static StringBuffer createRepeatedString(String repetition, int nbTimes)
  {
    // La chaîne à créer doit être vide
    if(nbTimes <= 0)
    {
      return new StringBuffer();
    }
    StringBuffer repeatedString = new StringBuffer(repetition.length() * nbTimes);
    // Rempli la chaîne de caractères avec des répétitions de celle passée en argument
    for(int i = 0 ; i < nbTimes ; i++)
    {
      repeatedString.append(repetition);
    }
    return repeatedString;
  }

  /**
   * Permet de répéter un caractère choisi
   * @param repetition Caractère à répéter
   * @param nbTimes Nombre de fois dont le caractère va être répété
   * @return La répétition du caractère choisi
   */
  public static StringBuffer createRepeatedString(char repetition, int nbTimes)
  {
    return UtilString.createRepeatedString(String.valueOf(repetition), nbTimes);
  }
}
