package com.bid4win.commons.core;

/**
 * Classe utilitaire système<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilSystem
{
  /**
   * Cette méthode permet de récupérer l'élément de pile au niveau indiqué
   * @param stackTraceLevel Niveau de l'élément de pile à récupérer
   * @return L'élément de pile récupéré au indiqué
   */
  public static StackTraceElement getStackTraceElement(int stackTraceLevel)
  {
    return UtilSystem.getStackTraceElement(new Exception(), stackTraceLevel + 1);
  }

  /**
   * Cette méthode permet de récupérer l'élément de la pile donnée au niveau indiqué
   * @param source Base de la pile à partir de laquelle récupérer l'élément
   * @param stackTraceLevel Niveau de l'élément de pile à récupérer
   * @return L'élément de la pile donnée récupéré au indiqué
   */
  public static StackTraceElement getStackTraceElement(Throwable source,
                                                       int stackTraceLevel)
  {
    StackTraceElement[] stackTraceElements = source.getStackTrace();
    return stackTraceElements[stackTraceLevel];
  }

  /**
   * Permet de récupérer le nom de la classe de l'objet en paramètre sans le package
   * @param object Objet dont on recherche le nom de la classe
   * @return Le nom de la classe de l'objet en paramètre
   */
  public static String getCanonicalClassName(Object object)
  {
    return UtilSystem.getCanonicalClassName(object.getClass());
  }

  /**
   * Permet de récupérer le nom de la classe en paramètre sans le package
   * @param classObject Classe dont on recherche le nom
   * @return Le nom de la classe en paramètre
   */
  public static String getCanonicalClassName(Class<?> classObject)
  {
    return UtilSystem.getCanonicalClassName(classObject.getName());
  }

  /**
   * Permet de récupérer le nom de la classe de l'objet référencé par l'élément
   * de stack en paramètre sans le package
   * @param element Element de stack dont on recherche le nom de la classe de
   * l'objet référencé
   * @return Le nom de la classe de l'objet référencé par l'élément de stack en
   * paramètre
   */
  public static String getCanonicalClassName(StackTraceElement element)
  {
    return UtilSystem.getCanonicalClassName(element.getClassName());
  }

  /**
   * Permet de récupérer le nom de la classe en paramètre sans le package
   * @param classObject Classe dont on recherche le nom
   * @return Le nom de la classe en paramètre
   */
  public static String getCanonicalClassName(String classObject)
  {
    return classObject.substring(classObject.lastIndexOf('.') + 1);
  }

  /**
   * Constructeur privé
   */
  private UtilSystem()
  {
    super();
  }
}
