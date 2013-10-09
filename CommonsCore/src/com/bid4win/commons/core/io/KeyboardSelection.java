package com.bid4win.commons.core.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Cette classe permet de récupérer des saisies utilisateur via le clavier.<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class KeyboardSelection extends BufferedParser
{
  /** C'est l'instance utilisée comme singleton. */
  private static KeyboardSelection instance = null;
  static
  {
    KeyboardSelection.instance = new KeyboardSelection();
  }
  /**
   * La classe est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique instance en mémoire.
   * @return l'instance de la classe
   */
  public static KeyboardSelection getInstance()
  {
    return KeyboardSelection.instance;
  }
  
  /**
   * Le constructeur est privé car la classe doit être accédée comme un singleton
   */
  private KeyboardSelection()
  {
    super();
  }
  /**
   * Redéfini la fonction de la classe parente afin que le buffer de lecture récupère
   * les données saisies au clavier.
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.BufferedParser#getBufferedReader()
   */
  @Override
  protected BufferedReader getBufferedReader()
  {
    return new BufferedReader(new InputStreamReader(System.in));
  }
  
  /**
   * Cette fonction permet de recuperer via le clavier une chaine de caracteres
   * saisie par l'utilisateur.
   * @return la chaine de caracteres saisie par l'utilisateur
   * @throws SelectionException quand un probleme intervient lors de la recuperation
   * de la chaine de caracteres
   */
//  public String selectString() throws SelectionException
//  {
//    try
//    {
//      return this.parseStringTrimed();
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//
//  /**
//   * Cette fonction permet de recuperer via le clavier une chaine de caracteres
//   * saisie par l'utilisateur qui devra avoir une taille situee entre les deux
//   * bornes en parametre.
//   * @param min Taille minimum acceptable pour la selection
//   * @param max Taille maximum acceptable pour la selection
//   * @return La chaine de caracteres saisie par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la chaine de caracteres
//   */
//  public String selectString(int min, int max) throws SelectionException
//  {
//    try
//    {
//      return this.parseStringMinMaxLength(min, max);
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }  
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier une chaine de caracteres
//   * saisie par l'utilisateur qui devra avoir une taille au moins egale a la taille
//   * en parametre.
//   * @param min Taille minimum acceptable pour la selection
//   * @return La chaine de caracteres saisie par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la chaine de caracteres
//   */
//  public String selectStringMinLength(int min) throws SelectionException
//  {
//    try
//    {
//      return this.parseStringMinLength(min);
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  /**
//   * Cette fonction permet de recuperer une liste de chaines de caracteres
//   * a partir d'une chaine de caractere saisies avec separateur via le clavier par l'utilisateur.
//   * @param min Taille minimum acceptable pour la selection
//   * @param separator Separateurs des chaines de caracteres saisies
//   * @return La liste de chaines de caracteres saisies par l'utilisateur
//   * @throws SelectionException quand un probleme intervient lors de la recuperation
//   * de la chaine de caracteres
//   */
//  public StringList selectStringListMinLength(int min, String separator) throws SelectionException
//  {
//    try
//    {
//      return this.parseStringListMinLength(min, separator);
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//
//  /**
//   * Cette fonction permet de recuperer via le clavier une chaine de caracteres
//   * saisie par l'utilisateur qui devra avoir une taille ne depassant pas la taille
//   * en parametre.
//   * @param max Taille maximum acceptable pour la selection
//   * @return La chaine de caracteres saisie par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la chaine de caracteres
//   */
//  public String selectStringMaxLength(int max) throws SelectionException
//  {
//    try
//    {
//      return this.parseStringMinMaxLength(0, max);
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//    
//  /**
//   * Cette fonction permet de recuperer via le clavier un entier borne par les
//   * deux cote saisi par l'utilisateur.
//   * @param min Valeur minimum acceptable pour la selection
//   * @param max Valeur maximum acceptable pour la selection
//   * @return L'entier saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de l'entier
//   */
//  public int selectInt(int min, int max) throws SelectionException
//  {
//    try
//    {
//      return this.parseIntBounds(min, max);
//    }
//    catch(ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier un entier borne par sa
//   * valeur minimale saisi par l'utilisateur.
//   * @param min Valeur minimum acceptable pour la selection
//   * @return L'entier saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de l'entier
//   */
//  public int selectIntBorneInf(int min) throws SelectionException
//  {
//    try
//    {
//      return this.parseIntBorneInf(min);
//    }
//    catch(ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//
//  /**
//   * Cette fonction permet de recuperer via le clavier un entier borne par sa
//   * valeur maximale saisi par l'utilisateur.
//   * @param max Valeur maximum acceptable pour la selection
//   * @return L'entier saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de l'entier
//   */
//  public int selectIntBorneSup(int max) throws SelectionException
//  {
//    try
//    {
//      return this.parseIntBorneSup(max);
//    }
//    catch(ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier un booleen saisi par
//   * l'utilisateur. Le valeur attendue pour les deux etats du booleen ('y' pour
//   * true et 'n' pour false) sera donc testee. Si la selection ne correspond a
//   * aucune des deux valeurs pre-definies, une exception de selection sera lancee.
//   * @return Le booleen correspondant a la saisie de l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * du booleen
//   */
//  public boolean selectBooleanStringYesOrNo() throws SelectionException
//  {
//    try
//    {
//      return this.parseBooleanYesOrNo();
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier un booleen saisi par
//   * l'utilisateur. Le valeur attendue pour les deux etats du booleen ('o' pour
//   * true et 'n' pour false) sera donc testee. Si la selection ne correspond a
//   * aucune des deux valeurs pre-definies, une exception de selection sera lancee.
//   * @return Le booleen correspondant a la saisie de l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * du booleen
//   */
//  public boolean selectBooleanStringOuiOrNon() throws SelectionException
//  {
//    try
//    {
//      return this.parseBooleanOuiOrNon();
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//
//  /**
//   * Cette fonction permet de recuperer via le clavier une date saisie par l'
//   * utilisateur au format DD/MM/YYYY. Si la selection ne correspond pas au format,
//   * une exception de selection sera lancee.
//   * @return La date correspondante a la saisie de l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la date
//   */
//  public Date selectDateDDIMMIYYYY() throws SelectionException
//  {
//    try
//    {
//      return this.parseDateDDIMMIYYYY();
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  /**
//   * Cette fonction permet de recuperer via le clavier une heure saisie par l'
//   * utilisateur au format HHmm. Si la selection ne correspond pas au format,
//   * une exception de selection sera lancee.
//   * @return La date correspondante a la saisie de l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la date
//   */
//  public Time selectTimeHHmm() throws SelectionException
//  {
//    try
//    {
//      return this.parseTimeHHmm();
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }  
//  /**
//   * Cette fonction permet de recuperer via le clavier une date saisie par l'
//   * utilisateur au format DD/MM/YYYY/HH:MM:SS. Si la selection ne correspond pas au format,
//   * une exception de selection sera lancee.
//   * @return la date correspondante a la saisie de l'utilisateur
//   * @throws SelectionException quand un probleme intervient lors de la recuperation
//   * de la date
//   */
//  public Date selectDateDDIMMIYYYY_HHIMMISS() throws SelectionException
//  {
//    try
//    {
//      Date selection = this.parseDateDDIMMIYYYY_HHIMMISS();
//      return selection;
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier une frequence saisie par l'
//   * utilisateur au format numerique. Si la selection ne correspond pas au format,
//   * une exception de selection sera lancee.
//   * @return La frequence correspondante a la saisie de l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la frequence
//   */
//  public BinaryFrequency selectNumericalFrequency() throws SelectionException
//  {
//    try
//    {
//      return this.parseNumericalFrequency();
//    }
//    catch (ParserException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier une periode saisie par l'
//   * utilisateur au format numerique. Si la selection ne correspond pas au format,
//   * une exception de selection sera lancee.
//   * @return La periode correspondante a la saisie de l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la periode
//   */
//  public BinaryPeriod selectNumericalPeriod() throws SelectionException
//  {
//    ExecutionLogger.getInstance().info("Veuillez entrer la date de debut de la periode au format DD/MM/YYYY");
//    Date begin = this.selectDateDDIMMIYYYY();
//    ExecutionLogger.getInstance().info("Veuillez entrer la date de fin de la periode au format DD/MM/YYYY");
//    Date end = this.selectDateDDIMMIYYYY();
//    ExecutionLogger.getInstance().info("Veuillez entrer la frequence de la periode au format 1.3.5.7");
//    BinaryFrequency frequency = this.selectNumericalFrequency();
//    try
//    {
//      return new BinaryPeriod(begin, end, frequency);
//    }
//    catch(InvalidBinaryPeriodException ex)
//    {
//      throw new PeriodSelectionException(ex);
//    }
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier une LJA saisie par l'utilisateur
//   * sous la forme d'une liste de periodes. Attention, aucune periode ne devra se
//   * chevaucher.
//   * @return La LJA correspondant a la liste de periodes saisie par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la LJA
//   */
//  public BinaryLja selectLja() throws SelectionException
//  {
//    BinaryPeriodList periodList = new BinaryPeriodList();
//    boolean finished = false;
//    while(!finished)
//    {
//      periodList.add(this.selectNumericalPeriod());
//      ExecutionLogger.getInstance().info("");
//      ExecutionLogger.getInstance().info("Voici les periodes actuellement rentrees");
//      ExecutionLogger.getInstance().info(periodList);
//      ExecutionLogger.getInstance().info("Souhaitez vous ajouter une periode (y/n)");
//      finished = !KeyboardSelection.getInstance().selectBooleanStringYesOrNo();
//    }
//    try
//    {
//      return periodList.toLja();
//    }
//    catch(BusinessException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier une compagnie.
//   * @return La compagnie saisie par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la compagnie
//   */
//  public Company selectCompany() throws SelectionException
//  {
//    ExecutionLogger.getInstance().info("Veuillez entrer le code compagnie :");
//    String code = this.selectString(2, 2);
//    try
//    {
//      return new Company(code);
//    }
//    catch(InvalidCompanyException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  /**
//   * Cette fonction permet de recuperer via le clavier un designateur.
//   * @return Le designateur saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * du designateur
//   */
//  public Designator selectDesignator() throws SelectionException
//  {
//    Company company = this.selectCompany();
//    ExecutionLogger.getInstance().info("Veuillez entrer le numero de vol :");
//    String number = this.selectString(1, 4);
//    ExecutionLogger.getInstance().info("Veuillez entrer le suffixe operationnel :");
//    String suffix = this.selectString(0, 1);
//    try
//    {
//      return new Designator(company, number, suffix);
//    }
//    catch(BusinessException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//
//  /**
//   * Cette fonction permet de recuperer via le clavier une liste de designateurs.
//   * @return La liste de designateurs saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la liste de designateurs
//   */
//  public DesignatorList selectDesignatorList() throws SelectionException 
//  {
//    DesignatorList designatorList = new DesignatorList();
//    boolean finished = false;
//    while(!finished)
//    {
//      designatorList.add(this.selectDesignator());
//      ExecutionLogger.getInstance().info("");
//      ExecutionLogger.getInstance().info("Voici les designateurs actuellement rentrees");
//      ExecutionLogger.getInstance().info(designatorList);
//      ExecutionLogger.getInstance().info("Souhaitez vous ajouter un designateur (y/n)");
//      finished = !KeyboardSelection.getInstance().selectBooleanStringYesOrNo();
//    }
//    return designatorList;
//  } 
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier un pays.
//   * @return Le pays saisie par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * du pays
//   */
//  public Country selectCountry() throws SelectionException
//  {
//    ExecutionLogger.getInstance().info("Veuillez entrer le code pays :");
//    String code = this.selectString(2, 2);
//    try
//    {
//      return new Country(code);
//    }
//    catch(InvalidCountryException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier un aeroport
//   * @return L'aeroport saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de l'aeroport
//   */
//  public Airport selectAirport() throws SelectionException
//  {
//    return this.selectAirport("Veuillez entrer le code aeroport :");
//  }
//  
//  /**
//   * Cette fonction permet de recuperer via le clavier un aeroport.
//   * @param message Message a afficher pour demander la saisie du code aeroport
//   * @return L'aeroport saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de l'aeroport
//   */
//  public Airport selectAirport(String message) throws SelectionException 
//  {
//    ExecutionLogger.getInstance().info(message);
//    String code = this.selectString(3, 3);
//    try
//    {
//      return new Airport(code);
//    }
//    catch(InvalidAirportException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }
//  }
//  /**
//   * Cette fonction permet de recuperer via le clavier un couple d'aeroports.
//   * @return Le couple d'aeroports saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * du couple d'aeroports
//   */
//  public AirportPair selectAirportPair() throws SelectionException 
//  {
//    Airport departure = this.selectAirport("Veuillez entrer le code aeroport de depart :");
//    Airport arrival = this.selectAirport("Veuillez entrer le code aeroport d'arrivee :");
//    try
//    {
//      return new AirportPair(departure,arrival);
//    }
//    catch (InvalidAirportPairException ex)
//    {
//      throw new SelectionException(ex.getMessage());
//    }    
//  }  
//  /**
//   * Cette fonction permet de recuperer via le clavier une liste de couples d'aeroports.
//   * @return La liste de couples d'aeroports saisi par l'utilisateur
//   * @throws SelectionException Quand un probleme intervient lors de la recuperation
//   * de la liste de couples d'aeroports
//   */
//  public AirportPairList selectAirportPairList() throws SelectionException 
//  {
//    AirportPairList airportPairList = new AirportPairList();
//    boolean finished = false;
//    while(!finished)
//    {
//      airportPairList.add(this.selectAirportPair());
//      ExecutionLogger.getInstance().info("");
//      ExecutionLogger.getInstance().info("Voici les escales actuellement rentrees");
//      ExecutionLogger.getInstance().info(airportPairList.toString());
//      ExecutionLogger.getInstance().info("Souhaitez vous ajouter une escale (y/n)");
//      finished = !KeyboardSelection.getInstance().selectBooleanStringYesOrNo();
//    }
//    return airportPairList;
//  } 
}
