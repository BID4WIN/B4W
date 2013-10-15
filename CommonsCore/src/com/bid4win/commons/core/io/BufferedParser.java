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
 * @author Emeric Fill�tre
 *
 * Cette classe permet de r�cup�rer de variables en fonction d'un reader a d�finir
 * dans les sous classes
 */
public abstract class BufferedParser
{
  /**
   * Cette m�thode doit �tre d�finie afin de retourner le reader � utiliser pour
   * r�cup�rer les informations a parser
   * @return Le reader � utiliser
   */
  protected abstract BufferedReader getBufferedReader();

  /**
   * Cette fonction permet de r�cup�rer sur une ligne du reader une cha�ne de caract�res
   * @return La cha�ne de caract�res lue sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
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
   * Cette fonction permet de r�cup�rer sur le reader une cha�ne de caract�res de
   * longueur d�finie. Si le reste de la ligne en cours est plus court que la longueur
   * d�finie, on s'arretera a la fin de la ligne
   * @param length Longueur de la cha�ne de caract�res � lire
   * @return La cha�ne de caract�res de longueur d�finie lue sur le reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   */
  public String parseString(int length) throws Bid4WinIOReadException
  {
    try
    {
      String result = UtilString.EMPTY;
      // Lit caract�re par caract�re jusqu'a atteindre la longueur choisie
      for(int i = 0 ; i < length ; i++)
      {
        // Lit le prochain caract�re ascii
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
        // Si on termine une ligne, on arr�te la r�cup�ration de la cha�ne
        else if(read.equals(UtilString.SEPARATOR_NEW_LINE))
        {
          // Si le caract�re de fin de ligne est le premier r�cup�r�, on continuera
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
   * Cette fonction permet de r�cup�rer sur une ligne du reader une cha�ne de
   * caract�res �pur�e de ses blancs en d�but et fin de cha�ne
   * @return La cha�ne de caract�res �pur�e lue sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   */
  public String parseStringTrimed() throws Bid4WinIOReadException
  {
    return UtilString.trimNotNull(this.parseString());
  }

  /**
   * Cette fonction permet de r�cup�rer sur le reader une cha�ne de caract�res de
   * longueur d�finie �pur�e de ses blancs en d�but et fin de cha�ne
   * @param length Longueur de la cha�ne de caract�res �pur�e � lire
   * @return La cha�ne de caract�res �pur�e de longueur d�finie lue sur le reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   */
  public String parseStringTrimed(int length) throws Bid4WinIOReadException
  {
    return UtilString.trimNotNull(this.parseString(length));
  }

  /**
   * Cette fonction permet de parser une cha�ne de caract�res pour r�cup�rer un
   * entier
   * @param toBeParsed La cha�ne de caract�res � parser pour r�cup�rer l'entier
   * qu'elle contient
   * @return L'entier issu du parsing de la cha�ne de caract�res en argument
   * @throws TechnicalException Quand un probl�me intervient lors de la r�cup�ration
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
   * Cette fonction permet de r�cup�rer un entier sur une ligne du reader
   * @return L'entier lu sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   * @throws TechnicalException Quand un probl�me intervient lors de la r�cup�ration
   * de l'entier
   */
  public int parseInt() throws Bid4WinIOReadException, TechnicalException
  {
  	return this.parseInt(this.parseStringTrimed());
  }

  /**
   * Cette fonction permet de r�cup�rer sur le reader un entier de longueur d�finie
   * @param length Longueur de l'entier a lire
   * @return L'entier de longueur d�finie lu sur le reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   * @throws TechnicalException Quand un probl�me intervient lors de la r�cup�ration
   * de l'entier
   */
  public int parseInt(int length) throws Bid4WinIOReadException, TechnicalException
  {
    return this.parseInt(this.parseStringTrimed(length));
  }

  /**
   * Cette fonction permet parser une cha�ne de caract�res pour r�cup�rer un entier
   * born� par les valeurs minimum et maximum fournies
   * @param toBeParsed Cha�ne de caract�res � parser pour r�cup�rer l'entier born�
   * @param min Valeur minimum admise pour l'entier � r�cup�rer
   * @param max Valeur maximum admise pour l'entier � r�cup�rer
   * @return L'entier issu du parsing de la cha�ne de caract�res en argument
   * @throws TechnicalException Quand un probl�me intervient lors de la r�cup�ration
   * de l'entier
   * @throws ModelArgumentException Quand l'entier r�cup�r� est en dehors des bornes
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
   * Cette fonction permet de r�cup�rer sur une ligne du reader un entier born�
   * des deux c�t�s
   * @param min Valeur minimum admise pour l'entier � r�cup�rer
   * @param max Valeur maximum admise pour l'entier � r�cup�rer
   * @return L'entier born� des deux c�t�s lu sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   * @throws TechnicalException Quand un probl�me intervient lors de la r�cup�ration
   * de l'entier
   * @throws ModelArgumentException Quand l'entier r�cup�r� est en dehors des bornes
   * fournies en argument
   */
  public int parseIntBounds(int min, int max)
         throws Bid4WinIOReadException, TechnicalException, ModelArgumentException
  {
    return this.parseIntBounds(this.parseStringTrimed(), min, max);
  }

  /**
   * Cette fonction permet de r�cup�rer sur le reader un entier born� des deux c�t�s
   * de longueur d�finie
   * @param length Longueur de l'entier � lire
   * @param min Valeur minimum admise pour l'entier � r�cup�rer
   * @param max Valeur maximum admise pour l'entier � r�cup�rer
   * @return L'entier born� des deux c�t�s de longueur d�finie lu sur le reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   * @throws TechnicalException Quand un probl�me intervient lors de la r�cup�ration
   * de l'entier
   * @throws ModelArgumentException Quand l'entier r�cup�r� est en dehors des bornes
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
   * Cette fonction permet de r�cup�rer une cha�ne de caract�res de longueur minimale
   * sur une ligne du reader
   * @param min Longueur minimum pour la cha�ne � r�cup�rer
   * @return La cha�ne de caract�res lue sur une ligne du reader
   * @throws Bid4WinIOReadException Quand un probl�me intervient lors de la r�cup�ration
   * de la cha�ne de caract�res
   * @throws ModelArgumentException Quand la cha�ne de caract�res r�cup�r�e a une
   * taille inf�rieure � celle fournie en argument
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
   * Cette fonction permet de parser une cha�ne de caract�res pour r�cup�rer un
   * boolean dont les valeurs true et false sont pass�es en argument
   * @param toBeParsed Chaine de caract�res � parser pour r�cup�rer le boolean
   * @param stringForTrue Cha�ne de caract�res repr�sentant la valeur true
   * @param stringForFalse Cha�ne de caract�res repr�sentant la valeur false
   * @return Le boolean issu du parsing de la cha�ne de caract�res en argument
   * @throws ModelArgumentException Quand la cha�ne en argument ne correspond �
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
   * Cette fonction permet de r�cup�rer un boolean sur une ligne du reader dont
   * les valeurs true et false sont pass�es en argument
   * @param stringForTrue Cha�ne de caract�res repr�sentant la valeur true
   * @param stringForFalse Cha�ne de caract�res repr�sentant la valeur false
   * @return Le boolean lu sur une ligne du reader
   * @throws Bid4WinIOReadException Si un probl�me de lecture dans le reader intervient
   * @throws ModelArgumentException Quand la cha�ne r�cup�r�e ne correspond � aucune
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
   * Cette fonction permet de r�cup�rer un boolean sur une ligne du reader dont
   * les valeurs attendues sont 'y' pour true et 'n' pour false
   * @return Le boolean lu sur une ligne du reader
   * @throws Bid4WinIOReadException Si un probl�me de lecture dans le reader intervient
   * @throws ModelArgumentException Quand la cha�ne r�cup�r�e ne correspond � aucune
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
