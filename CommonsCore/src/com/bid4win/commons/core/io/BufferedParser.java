package com.bid4win.commons.core.io;

import java.io.BufferedReader;
import java.io.IOException;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.TechnicalException;
import com.bid4win.commons.core.io.exception.Bid4WinIOReadException;

/**
 * @author Emeric Fillâtre
 *
 * Cette classe permet de récupérer de variables en fonction d'un reader a définir
 * dans les sous classes
 */
public abstract class BufferedParser
{
  /**
   * Cette méthode doit être définie afin de retourner le reader à utiliser pour
   * récupérer les informations a parser
   * @return Le reader à utiliser
   */
  protected abstract BufferedReader getBufferedReader();

  /**
   * Cette fonction permet de récupérer sur une ligne du reader une chaîne de caractères
   * @return La chaîne de caractères lue sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   */
  public String parseString() throws Bid4WinIOReadException
  {
    try
    {
      // Lit la ligne et la retourne
      return this.getBufferedReader().readLine();
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Cette fonction permet de récupérer sur le reader une chaîne de caractères de
   * longueur définie. Si le reste de la ligne en cours est plus court que la longueur
   * définie, on s'arretera a la fin de la ligne
   * @param length Longueur de la chaîne de caractères à lire
   * @return La chaîne de caractères de longueur définie lue sur le reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   */
  public String parseString(int length) throws Bid4WinIOReadException
  {
    try
    {
      String result = UtilString.EMPTY;
      // Lit caractère par caractère jusqu'a atteindre la longueur choisie
      for(int i = 0 ; i < length ; i++)
      {
        // Lit le prochain caractère ascii
        int ascii = this.getBufferedReader().read();
        // On a atteint la fin de fichier
        if(ascii == -1)
        {
          return null;
        }
        String read = UtilString.EMPTY + (char)ascii;
        // En mode windows, une ligne se termine d'abord par un retour chariot
        if(read.equals(UtilString.SEPARATOR_CARRIAGE_RETURN))
        {
          i--;
        }
        // Si on termine une ligne, on arrête la récupération de la chaîne
        else if(read.equals(UtilString.SEPARATOR_NEW_LINE))
        {
          // Si le caractère de fin de ligne est le premier récupéré, on continuera
          // pour commencer une nouvelle ligne
          if(i == 0)
          {
            i--;
          }
          else
          {
            break;
          }
        }
        else
        {
          result += read;
        }
      }
      return result;
    }
    catch (IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Cette fonction permet de récupérer sur une ligne du reader une chaîne de
   * caractères épurée de ses blancs en début et fin de chaîne
   * @return La chaîne de caractères épurée lue sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   */
  public String parseStringTrimed() throws Bid4WinIOReadException
  {
    return UtilString.trimNotNull(this.parseString());
  }

  /**
   * Cette fonction permet de récupérer sur le reader une chaîne de caractères de
   * longueur définie épurée de ses blancs en début et fin de chaîne
   * @param length Longueur de la chaîne de caractères épurée à lire
   * @return La chaîne de caractères épurée de longueur définie lue sur le reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   */
  public String parseStringTrimed(int length) throws Bid4WinIOReadException
  {
    return UtilString.trimNotNull(this.parseString(length));
  }

  /**
   * Cette fonction permet de parser une chaîne de caractères pour récupérer un
   * entier
   * @param toBeParsed La chaîne de caractères à parser pour récupérer l'entier
   * qu'elle contient
   * @return L'entier issu du parsing de la chaîne de caractères en argument
   * @throws TechnicalException Quand un problème intervient lors de la récupération
   * de l'entier
   */
  public int parseInt(String toBeParsed) throws TechnicalException
  {
    try
    {
      return Integer.parseInt(toBeParsed);
    }
    catch (NumberFormatException ex)
    {
      throw new TechnicalException(ex);
    }
  }

  /**
   * Cette fonction permet de récupérer un entier sur une ligne du reader
   * @return L'entier lu sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   * @throws TechnicalException Quand un problème intervient lors de la récupération
   * de l'entier
   */
  public int parseInt() throws Bid4WinIOReadException, TechnicalException
  {
  	return this.parseInt(this.parseStringTrimed());
  }

  /**
   * Cette fonction permet de récupérer sur le reader un entier de longueur définie
   * @param length Longueur de l'entier a lire
   * @return L'entier de longueur définie lu sur le reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   * @throws TechnicalException Quand un problème intervient lors de la récupération
   * de l'entier
   */
  public int parseInt(int length) throws Bid4WinIOReadException, TechnicalException
  {
    return this.parseInt(this.parseStringTrimed(length));
  }

  /**
   * Cette fonction permet parser une chaîne de caractères pour récupérer un entier
   * borné par les valeurs minimum et maximum fournies
   * @param toBeParsed Chaîne de caractères à parser pour récupérer l'entier borné
   * @param min Valeur minimum admise pour l'entier à récupérer
   * @param max Valeur maximum admise pour l'entier à récupérer
   * @return L'entier issu du parsing de la chaîne de caractères en argument
   * @throws TechnicalException Quand un problème intervient lors de la récupération
   * de l'entier
   * @throws ModelArgumentException Quand l'entier récupéré est en dehors des bornes
   * fournies en argument
   */
  public int parseIntBounds(String toBeParsed, int min, int max)
         throws TechnicalException, ModelArgumentException
  {
    int result = this.parseInt(toBeParsed);
    UtilNumber.checkMinValue("Parsed", result, min, true);
    return UtilNumber.checkMaxValue("Parsed", result, max, true);
  }

  /**
   * Cette fonction permet de récupérer sur une ligne du reader un entier borné
   * des deux côtés
   * @param min Valeur minimum admise pour l'entier à récupérer
   * @param max Valeur maximum admise pour l'entier à récupérer
   * @return L'entier borné des deux côtés lu sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   * @throws TechnicalException Quand un problème intervient lors de la récupération
   * de l'entier
   * @throws ModelArgumentException Quand l'entier récupéré est en dehors des bornes
   * fournies en argument
   */
  public int parseIntBounds(int min, int max)
         throws Bid4WinIOReadException, TechnicalException, ModelArgumentException
  {
    return this.parseIntBounds(this.parseStringTrimed(), min, max);
  }

  /**
   * Cette fonction permet de récupérer sur le reader un entier borné des deux côtés
   * de longueur définie
   * @param length Longueur de l'entier à lire
   * @param min Valeur minimum admise pour l'entier à récupérer
   * @param max Valeur maximum admise pour l'entier à récupérer
   * @return L'entier borné des deux côtés de longueur définie lu sur le reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   * @throws TechnicalException Quand un problème intervient lors de la récupération
   * de l'entier
   * @throws ModelArgumentException Quand l'entier récupéré est en dehors des bornes
   * fournies en argument
   */
  public int parseIntBounds(int length, int min, int max)
         throws Bid4WinIOReadException, TechnicalException, ModelArgumentException
  {
    return this.parseIntBounds(this.parseStringTrimed(length), min, max);
  }

  /**
   * Cette fonction permet parser une chaine de caracteres pour recuperer un entier
   * borne par sa valeur minimale
   * @param toBeParsed Chaine de caracteres a parser pour recuperer l'entier borne
   * @param min Valeur minimum admise pour l'entier a recuperer
   * @return l'entier issu du parsing de la chaine de caracteres en argument
   * @throws IntegerParserException quand un probleme intervient lors du parsing
   * de l'entier
   * @throws IntegerMinParserException quand l'entier a tester est inferieur au
   * minimum fourni en argument
   */
/*  public int parseIntBorneInf(String toBeParsed, int min)
         throws IntegerParserException, IntegerMinParserException
  {
    int result = this.parseInt(toBeParsed);
    this.checkMinInt(min, result);
    return result;
  }

  /**
   * Cette fonction permet de recuperer un entier borne par sa valeur minimale
   * sur une ligne du reader
   * @param min Valeur minimum admise pour l'entier a recuperer
   * @return l'entier borne par sa valeur minimale lu sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de l'entier borne
   * @throws IntegerParserException quand un probleme intervient lors de la recuperation
   * de l'entier
   * @throws IntegerMinParserException quand l'entier recupere est inferieur au
   * minimum fourni en argument
   */
 /* public int parseIntBorneInf(int min)
         throws IOParserException, IntegerParserException, IntegerMinParserException
  {
    return this.parseIntBorneInf(this.parseStringTrimed(), min);
  }

  /**
   * Cette fonction permet de recuperer un entier borne par sa valeur minimale de
   * longueur definie sur le reader reader
   * @param length Longueur de l'entier a lire
   * @param min Valeur minimum admise pour l'entier a recuperer
   * @return l'entier borne par sa valeur minimale de longueur definie lu sur le
   * reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la chaine de caracteres epuree
   * @throws IntegerParserException quand un probleme intervient lors de la recuperation
   * de l'entier
   * @throws IntegerMinParserException quand l'entier recupere est inferieur au
   * minimum fourni en argument
   */
 /* public int parseIntBorneInf(int length, int min)
         throws IOParserException, IntegerParserException, IntegerMinParserException
  {
    return this.parseIntBorneInf(this.parseStringTrimed(length), min);
  }

  /**
   * Cette fonction permet parser une chaine de caracteres pour recuperer un entier
   * borne par sa valeur maximale
   * @param toBeParsed Chaine de caracteres a parser pour recuperer l'entier borne
   * @param max Valeur maximum admise pour l'entier a recuperer
   * @return l'entier issu du parsing de la chaine de caracteres en argument
   * @throws IntegerParserException quand un probleme intervient lors du parsing
   * de l'entier
   * @throws IntegerMaxParserException quand l'entier a tester est inferieur au
   * minimum fourni en argument
   */
/*  public int parseIntBorneSup(String toBeParsed, int max)
         throws IntegerParserException, IntegerMaxParserException
  {
    int result = this.parseInt(toBeParsed);
    this.checkMaxInt(max, result);
    return result;
  }

  /**
   * Cette fonction permet de recuperer un entier borne par sa valeur maximale
   * sur une ligne du reader
   * @param max Valeur maximum admise pour l'entier a recuperer
   * @return l'entier borne par sa valeur maximale lu sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de l'entier borne
   * @throws IntegerParserException quand un probleme intervient lors de la recuperation
   * de l'entier
   * @throws IntegerMaxParserException quand l'entier recupere est superieur au
   * maximum fourni en argument
   */
 /* public int parseIntBorneSup(int max)
         throws IOParserException, IntegerParserException, IntegerMaxParserException
  {
    return this.parseIntBorneSup(this.parseStringTrimed(), max);
  }

  /**
   * Cette fonction permet de recuperer un entier borne par sa valeur maximale de
   * longueur definie sur le reader
   * @param length Longueur de l'entier a lire
   * @param max Valeur maximum admise pour l'entier a recuperer
   * @return l'entier borne par sa valeur maximale de longueur definie lu sur le
   * reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la chaine de caracteres epuree
   * @throws IntegerParserException quand un probleme intervient lors de la recuperation
   * de l'entier
   * @throws IntegerMaxParserException quand l'entier recupere est superieur au
   * maximum fourni en argument
   */
 /* public int parseIntBorneSup(int length, int max)
         throws IOParserException, IntegerParserException, IntegerMaxParserException
  {
    return this.parseIntBorneSup(this.parseStringTrimed(length), max);
  }*/

  /**
   * Cette fonction permet de récupérer une chaîne de caractères de longueur minimale
   * sur une ligne du reader
   * @param min Longueur minimum pour la chaîne à récupérer
   * @return La chaîne de caractères lue sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un problème intervient lors de la récupération
   * de la chaîne de caractères
   * @throws ModelArgumentException Quand la chaîne de caractères récupérée a une
   * taille inférieure à celle fournie en argument
   */
  public String parseStringMinLength(int min)
         throws Bid4WinIOReadException, ModelArgumentException
  {
    String result = this.parseStringTrimed();
    UtilNumber.checkMinValue("Parsed size", result.length(), min, true);
    return result;
  }

  /*
   * Cette fonction permet de recuperer une liste de chaines de caracteres
   * sur une ligne du reader avec separateur.
   * @param min Longueur minimum pour la chaine a recuperer
   * @param separator Separateurs des chaines de caracteres
   * @return La liste de chaines de caracteres lue sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la chaine de caracteres
   * @throws IntegerMinParserException quand la longueur de la chaine recuperee
   * est inferieure au minimum fourni en argument
   */
 /* public StringList parseStringListMinLength(int min, String separator)
    throws IOParserException, IntegerMinParserException
  {
      String selection = this.parseStringTrimed();
      StringList result = UtilString.splitNotEmpty(selection, separator);
      for (int i=0; i < result.size(); i++)
      {
        this.checkMinInt(min, result.getString(i).length());
      }
      return result;
  }

  /**
   * Cette fonction permet de recuperer une chaine de caracteres dont la longueur
   * est limitee sur une ligne du reader
   * @param min Longueur minimum pour la chaine a recuperer
   * @param max Longueur maximum pour la chaine a recuperer
   * @return la chaine de caracteres lue sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la chaine de caracteres
   * @throws IntegerMinParserException quand la longueur de la chaine recuperee
   * est inferieure au minimum fourni en argument
   * @throws IntegerMaxParserException quand la longueur de la chaine recuperee
   * est superieure au minimum fourni en argument
   */
 /* public String parseStringMinMaxLength(int min, int max)
         throws IOParserException, IntegerMinParserException, IntegerMaxParserException
  {
    String result = this.parseStringTrimed();
    this.checkMinInt(min, result.length());
    this.checkMaxInt(max, result.length());
    return result;
  }
  */
  /**
   * Cette fonction permet de parser une chaîne de caractéres pour récupérer un
   * boolean dont les valeurs true et false sont passées en argument
   * @param toBeParsed Chaine de caractéres à parser pour récupérer le boolean
   * @param stringForTrue Chaîne de caractéres représentant la valeur true
   * @param stringForFalse Chaîne de caractéres représentant la valeur false
   * @return Le boolean issu du parsing de la chaîne de caractéres en argument
   * @throws ModelArgumentException Quand la chaîne en argument ne correspond à
   * aucune des deux valeurs de boolean
   */
  public boolean parseBoolean(String toBeParsed, String stringForTrue, String stringForFalse)
         throws ModelArgumentException
  {
    UtilObject.checkAmong("Parsed", toBeParsed.toUpperCase(), new Bid4WinSet<String>(
        stringForTrue.toUpperCase(), stringForFalse.toUpperCase()));
    if(toBeParsed.equalsIgnoreCase(stringForTrue))
    {
      return true;
    }
    return false;
  }

  /**
   * Cette fonction permet de récupérer un boolean sur une ligne du reader dont
   * les valeurs true et false sont passées en argument
   * @param stringForTrue Chaîne de caractéres représentant la valeur true
   * @param stringForFalse Chaîne de caractéres représentant la valeur false
   * @return Le boolean lu sur une ligne du reader
   * @throws Bid4WinIOReadException Si un problème de lecture dans le reader intervient
   * @throws ModelArgumentException Quand la chaîne récupérée ne correspond à aucune
   * des deux valeurs de boolean
   */
  public boolean parseBoolean(String stringForTrue, String stringForFalse)
         throws Bid4WinIOReadException, ModelArgumentException
  {
    return this.parseBoolean(this.parseStringTrimed(), stringForTrue, stringForFalse);
  }

  /**
   * Cette fonction permet de recuperer un boolean de longueur definie du reader
   * dont les valeurs true et false sont passees en argument
   * @param length Longueur du boolean a lire
   * @param stringForTrue Chaine de caracteres representant la valeur true
   * @param stringForFalse Chaine de caracteres representant la valeur false
   * @return le boolean de longueur definie lu sur le reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * du boolean
   * @throws BooleanParserException quand la chaine recuperee ne correspond a
   * aucune des deux valeurs de boolean
   */
/*  public boolean parseBoolean(int length, String stringForTrue, String stringForFalse)
         throws IOParserException, BooleanParserException
  {
    return this.parseBoolean(this.parseString(length), stringForTrue, stringForFalse);
  }*/

  /**
   * Cette fonction permet de récupérer un boolean sur une ligne du reader dont
   * les valeurs attendues sont 'y' pour true et 'n' pour false
   * @return Le boolean lu sur une ligne du reader
   * @throws Bid4WinIOReadException Si un problème de lecture dans le reader intervient
   * @throws ModelArgumentException Quand la chaîne récupérée ne correspond à aucune
   * des deux valeurs de boolean
   */
  public boolean parseBooleanYesOrNo() throws Bid4WinIOReadException, ModelArgumentException
  {
    return this.parseBoolean("y", "n");
  }

  /**
   * Cette fonction permet de recuperer un boolean sur une ligne du reader dont
   * les valeurs attendues sont 'o' pour true et 'n' pour false
   * @return Le boolean lu sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * du boolean
   * @throws BooleanParserException quand la chaine recuperee ne correspond a
   * aucune des deux valeurs de boolean
   */
 /* public boolean parseBooleanOuiOrNon() throws IOParserException, BooleanParserException
  {
    return this.parseBoolean("o", "n");
  }

  /**
   * Cette fonction permet de recuperer un boolean de longueur definie du reader
   * dont les valeurs attendues sont 'y' pour true et 'n' pour false
   * @param length Longueur du boolean a lire
   * @return le boolean de longueur definie lu sur le reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * du boolean
   * @throws BooleanParserException quand la chaine recuperee ne correspond a
   * aucune des deux valeurs de boolean
   */
 /* public boolean parseBooleanYesOrNo(int length) throws IOParserException, BooleanParserException
  {
	return this.parseBoolean(length, "y", "n");
  }
  /**
   * Cette fonction permet de recuperer une heure sur une ligne du reader dont le
   * format sera HHmm
   * @return l'heure lue sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de l'heure
   * @throws TimeParserException quand un probleme intervient lors de la
   * recuperation d'heure
   */
/*  public Time parseTimeHHmm() throws TimeParserException, IOParserException
  {
    return this.parseTimeHHmm(this.parseString());
  }
  /**
   * Cette fonction permet parser une chaine de caracteres pour recuperer une
   * heure au format HHmm
   * @param toBeParsed Chaine de caracteres a parser pour recuperer l'heure
   * @return l'heure issue du parsing de la chaine de caracteres en argument
   * @throws TimeParserException quand un probleme intervient lors de la
   * recuperation de l'heure
   */
 /* public Time parseTimeHHmm(String toBeParsed) throws TimeParserException
  {
    try
    {
      return SimpleTimeFormat.parseHHmm(toBeParsed);
    }
    catch(java.text.ParseException ex)
    {
      throw new TimeParserException("HHMM");
    }
  }

  /**
   * Cette fonction permet parser une chaine de caracteres pour recuperer une
   * date au format dd/mm/yyyy
   * @param toBeParsed Chaine de caracteres a parser pour recuperer la date
   * @return la date issue du parsing de la chaine de caracteres en argument
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDIMMIYYYY(String toBeParsed) throws DateParserException
  {
    try
    {
      return SimpleDateFormat.parseDDIMMIYYYY(toBeParsed);
    }
    catch(java.text.ParseException ex)
    {
      throw new DateParserException("DD/MM/YYYY");
    }
  }

  /**
   * Cette fonction permet de recuperer une date sur une ligne du reader dont le
   * format sera dd/mm/yyyy
   * @return la date lue sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la date
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDIMMIYYYY() throws DateParserException, IOParserException
  {
  	return this.parseDateDDIMMIYYYY(this.parseString());
  }

  /**
   * Cette fonction permet de recuperer une date de longueur definie sur le reader
   * dont le format sera dd/mm/yyyy
   * @param length Longueur de la date a lire
   * @return la date de longueur definie lue sur le reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la date
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDIMMIYYYY(int length) throws DateParserException, IOParserException
  {
    return this.parseDateDDIMMIYYYY(this.parseString(length));
  }

  /**
   * Cette fonction permet parser une chaine de caracteres pour recuperer une
   * date au format ddMMMyy
   * @param toBeParsed Chaine de caracteres a parser pour recuperer la date
   * @return la date issue du parsing de la chaine de caracteres en argument
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDMMMYY(String toBeParsed) throws DateParserException
  {
    try
    {
      return SimpleDateFormat.parseDDMMMYY(toBeParsed);
    }
    catch(ParseException ex)
    {
      throw new DateParserException("DDMMMYY");
    }
  }

  /**
   * Cette fonction permet de recuperer une date sur une ligne du reader dont le
   * format sera DDMMMYY
   * @return la date lue sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la date
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDMMMYY() throws DateParserException, IOParserException
  {
    return this.parseDateDDMMMYY(this.parseString());
  }

  /**
   * Cette fonction permet de recuperer une date de longueur definie sur le reader
   * dont le format sera DDMMMYY
   * @param length Longueur de la date a lire
   * @return la date de longueur definie lue sur le reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la date
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDMMMYY(int length) throws DateParserException, IOParserException
  {
    return this.parseDateDDMMMYY(this.parseString(length));
  }

  /**
   * Cette fonction permet de recuperer une date sur une ligne du reader dont le
   * format sera dd/mm/yyyy hh:mm:ss
   * @return la date lue sur une ligne du reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la date
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDIMMIYYYY_HHIMMISS() throws DateParserException, IOParserException
  {
    return this.parseDateDDIMMIYYYY_HHIMMISS(this.parseString());
  }
  /**
   * Cette fonction permet de recuperer une date de longueur definie sur le reader
   * dont le format sera dd/mm/yyyy HH:mm:ss
   * @param length Longueur de la date a lire
   * @return la date de longueur definie lue sur le reader
   * @throws IOParserException quand un probleme intervient lors de la recuperation
   * de la date
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDIMMIYYYY_HHIMMISS(int length) throws DateParserException, IOParserException
  {
    return this.parseDateDDIMMIYYYY_HHIMMISS(this.parseString(length));
  }
  /**
   * Cette fonction permet parser une chaine de caracteres pour recuperer une
   * date au format dd/mm/yyyy HH:mm:ss
   * @param toBeParsed Chaine de caracteres a parser pour recuperer la date
   * @return la date issue du parsing de la chaine de caracteres en argument
   * @throws DateParserException quand un probleme intervient lors de la
   * recuperation de la date
   */
 /* public Date parseDateDDIMMIYYYY_HHIMMISS(String toBeParsed) throws DateParserException
  {
    try
    {
      return SimpleDateFormat.parseDDIMMIYYYY_HHIMMISS(toBeParsed);
    }
    catch(java.text.ParseException ex)
    {
      throw new DateParserException("DD/MM/YYYY HH:MM:SS");
    }
  }*/
}
