package com.bid4win.commons.core;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les bool�ens<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilBoolean
{
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut true
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du bool�en test�e
   * @throws ModelArgumentException Si le bool�en en param�tre vaut false
   */
  public static boolean checkTrue(String name, boolean value, int stackLevel)
         throws ModelArgumentException
  {
    if(!value)
    {
      ModelArgumentException.wrongParameterValue(name, value, true, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut true
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @return La valeur du bool�en test�e
   * @throws ModelArgumentException Si le bool�en en param�tre vaut false
   */
  public static boolean checkTrue(String name, boolean value) throws ModelArgumentException
  {
    return UtilBoolean.checkTrue(name, value, 1);
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut true
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du bool�en test�e
   * @throws UserException Si le bool�en en param�tre vaut false
   */
  public static boolean checkTrue(String name, boolean value,
                                  MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilBoolean.checkTrue(name, value, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut true
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du bool�en test�e
   * @throws UserException Si le bool�en en param�tre vaut false
   */
  public static boolean checkTrue(String name, boolean value, MessageRef ref)
         throws UserException
  {
    return UtilBoolean.checkTrue(name, value, ref, 1);
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut false
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du bool�en test�e
   * @throws ModelArgumentException Si le bool�en en param�tre vaut true
   */
  public static boolean checkFalse(String name, boolean value, int stackLevel)
         throws ModelArgumentException
  {
    if(value)
    {
      ModelArgumentException.wrongParameterValue(name, value, false, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut false
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @return La valeur du bool�en test�e
   * @throws ModelArgumentException Si le bool�en en param�tre vaut true
   */
  public static boolean checkFalse(String name, boolean value) throws ModelArgumentException
  {
    return UtilBoolean.checkFalse(name, value, 1);
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut false
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @return La valeur du bool�en test�e
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @throws UserException Si le bool�en en param�tre vaut true
   */
  public static boolean checkFalse(String name, boolean value,
                                   MessageRef ref, int stackLevel)
         throws UserException
  {
    try
    {
      return UtilBoolean.checkFalse(name, value, 1 + stackLevel);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette m�thode permet de tester si le bool�en en param�tre vaut false
   * @param name Nom du bool�en � tester
   * @param value Valeur du bool�en � tester
   * @return La valeur du bool�en test�e
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @throws UserException Si le bool�en en param�tre vaut true
   */
  public static boolean checkFalse(String name, boolean value, MessageRef ref)
         throws UserException
  {
    return UtilBoolean.checkFalse(name, value, ref, 1);
  }
}
