package com.bid4win.commons.core.security;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.collection.IdPatternTypeList;

/**
 * Cette classe défini le pattern d'un identifiant<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class IdPattern
{
  /**
   * Cette méthode permet de créer un pattern d'identifiant
   * @param pattern Chaîne de caractères représentant la liste de types constituant
   * le pattern d'identifiant à créer
   * @return Le pattern d'identifiant créé
   */
  public static IdPattern createNoCheck(String pattern)
  {
    try
    {
      return new IdPattern(pattern);
    }
    catch(UserException ex)
    {
      ex.printStackTrace();
      return null;
    }
  }

  /** Liste de types constituant le pattern */
  private IdPatternTypeList typeList = new IdPatternTypeList();

  /**
   * Constructeur
   * @param typeList Liste de types constituant le pattern
   */
  public IdPattern(IdPatternTypeList typeList)
  {
    this.addTypeList(typeList);
  }
  /**
   * Constructeur
   * @param pattern Chaîne de caractères représentant la liste de types constituant
   * le pattern
   * @throws UserException Si le parsing de la chaîne de caractères échoue
   */
  public IdPattern(String pattern) throws UserException
  {
    for(int i = 0 ; i < pattern.length() ; i++)
    {
      Type type = Type.getType(pattern.substring(i, i+1));
      this.addType(type);
    }
  }

  /**
   * Permet de connaître l'expression régulière correspondant au pattern courant
   * @return L'expression régulière correspondant au pattern courant
   */
  public String getRegexp()
  {
    return this.typeList.getRegexp();
  }

  /**
   * Permet de savoir si l'identifiant en argument correspond au pattern courant
   * @param id Identifiant à tester
   * @return True si l'ientifiant en argument correspond au pattern courant, false
   * sinon
   */
  public boolean matches(String id)
  {
    return id.matches(this.getRegexp());
  }

  /**
   * Getter du type du pattern à l'index indiqué
   * @param index Position du type du pattern à retourner
   * @return le type du pattern à l'index indiqué
   */
  public Type getType(int index)
  {
    return this.typeList.get(index);
  }

  /**
   * Getter du nombre de types constituant le pattern courant
   * @return Le nombre de types constituant le pattern courant
   */
  public int getTypeNb()
  {
    return this.typeList.size();
  }

  /**
   * Permet d'ajouter un type au pattern courant
   * @param type Type à ajouter au pattern courant
   */
  private void addType(Type type)
  {
    this.typeList.add(type);
  }
  /**
   * Permet d'ajouter une liste de types au pattern courant
   * @param typeList Liste de types à ajouter au pattern courant
   */
  private void addTypeList(IdPatternTypeList typeList)
  {
    this.typeList.addAll(typeList);
  }

  //*************************************************************************//
  //*************************************************************************//

  /**
   * Cette classe représente les différents types que peut avoir un pattern
   */
  public static class Type extends Bid4WinObjectType<Type>
  {
    /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
     * valeur doit être modifiée si une évolution de la classe la rend incompatible
     * avec les versions précédentes */
    private static final long serialVersionUID = 4203466221669457157L;

    /** Ce type correspond à un numérique */
    public final static Type NUMERICAL = new Type("N", 10, "[0-9]");
    /** Ce type correspond à un alphabétique */
    public final static Type ALPHABETICAL = new Type("A", 26, "[A-Z]");
    /** Ce type correspond à un alphanumérique */
    public final static Type BOTH = new Type("B", 36, "[0-9A-Z]");
    /** Ce type correspond à un hexadécimal */
    public final static Type HEXADECIMAL = new Type("H", 16, "[0-9A-F]");
    /** Ce type correspond au séparateur '-' */
    public final static Type SEPARATOR = new Type("-", 1, "-");

    /**
     * Cette méthode permet de récupérer le type de pattern dont le code est donné
     * en argument
     * @param code Code du type de pattern à récupérer
     * @return Le type de pattern correspondant au code en argument
     * @throws UserException Si le code en argument ne correspond à aucun type de
     * pattern connu
     */
    public static Type getType(String code) throws UserException
    {
      return Bid4WinObjectType.getType(Type.class, code);
    }

    /** Nombre de valeurs du type de pattern */
    private int valueNb = 0;
    /** Expression régulière du type de pattern */
    private String regexp = "";

    /**
     * Constructeur
     * @param code Code du type de pattern
     * @param valueNb Nombre de valeurs du type de pattern
     */
    protected Type(String code, int valueNb, String regexp)
    {
      super(UtilString.trimNotNull(code).toUpperCase());
      this.setValueNb(valueNb);
      this.setRegexp(regexp);
    }

    /**
     * Getter du nombre de valeurs du type de pattern
     * @return Le nombre de valeurs du type de pattern
     */
    public int getValueNb()
    {
      return this.valueNb;
    }
    /**
     * Getter de l'expression régulière du type de pattern
     * @return L'expression régulière du type de pattern
     */
    public String getRegexp()
    {
      return this.regexp;
    }

    /**
     * Setter interne du nombre de valeurs du type de pattern
     * @param valueNb Nombre de valeurs du type de pattern à positionner
     */
    private void setValueNb(int valueNb)
    {
      this.valueNb = valueNb;
    }
    /**
     * Setter interne de l'expression régulière du type de pattern
     * @param regexp Expression régulière du type de pattern à positionner
     */
    private void setRegexp(String regexp)
    {
      this.regexp = regexp;
    }
  }
}
