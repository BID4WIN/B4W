package com.bid4win.commons.core;

/**
 * Classe utilitaire syst�me<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilSystem
{
  /**
   * Cette m�thode permet de r�cup�rer l'�l�ment de pile au niveau indiqu�
   * @param stackTraceLevel Niveau de l'�l�ment de pile � r�cup�rer
   * @return L'�l�ment de pile r�cup�r� au indiqu�
   */
  public static StackTraceElement getStackTraceElement(int stackTraceLevel)
  {
    return UtilSystem.getStackTraceElement(new Exception(), stackTraceLevel + 1);
  }

  /**
   * Cette m�thode permet de r�cup�rer l'�l�ment de la pile donn�e au niveau indiqu�
   * @param source Base de la pile � partir de laquelle r�cup�rer l'�l�ment
   * @param stackTraceLevel Niveau de l'�l�ment de pile � r�cup�rer
   * @return L'�l�ment de la pile donn�e r�cup�r� au indiqu�
   */
  public static StackTraceElement getStackTraceElement(Throwable source,
                                                       int stackTraceLevel)
  {
    StackTraceElement[] stackTraceElements = source.getStackTrace();
    return stackTraceElements[stackTraceLevel];
  }

  /**
   * Permet de r�cup�rer le nom de la classe de l'objet en param�tre sans le package
   * @param object Objet dont on recherche le nom de la classe
   * @return Le nom de la classe de l'objet en param�tre
   */
  public static String getCanonicalClassName(Object object)
  {
    return UtilSystem.getCanonicalClassName(object.getClass());
  }

  /**
   * Permet de r�cup�rer le nom de la classe en param�tre sans le package
   * @param classObject Classe dont on recherche le nom
   * @return Le nom de la classe en param�tre
   */
  public static String getCanonicalClassName(Class<?> classObject)
  {
    return UtilSystem.getCanonicalClassName(classObject.getName());
  }

  /**
   * Permet de r�cup�rer le nom de la classe de l'objet r�f�renc� par l'�l�ment
   * de stack en param�tre sans le package
   * @param element Element de stack dont on recherche le nom de la classe de
   * l'objet r�f�renc�
   * @return Le nom de la classe de l'objet r�f�renc� par l'�l�ment de stack en
   * param�tre
   */
  public static String getCanonicalClassName(StackTraceElement element)
  {
    return UtilSystem.getCanonicalClassName(element.getClassName());
  }

  /**
   * Permet de r�cup�rer le nom de la classe en param�tre sans le package
   * @param classObject Classe dont on recherche le nom
   * @return Le nom de la classe en param�tre
   */
  public static String getCanonicalClassName(String classObject)
  {
    return classObject.substring(classObject.lastIndexOf('.') + 1);
  }

  /**
   * Constructeur priv�
   */
  private UtilSystem()
  {
    super();
  }
}
