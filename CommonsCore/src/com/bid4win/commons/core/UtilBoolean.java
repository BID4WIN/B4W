package com.bid4win.commons.core;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les booléens<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilBoolean
{
  /**
   * Cette méthode permet de tester si le booléen en paramètre vaut true
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du booléen testée
   * @throws ModelArgumentException Si le booléen en paramètre vaut false
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
   * Cette méthode permet de tester si le booléen en paramètre vaut true
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @return La valeur du booléen testée
   * @throws ModelArgumentException Si le booléen en paramètre vaut false
   */
  public static boolean checkTrue(String name, boolean value) throws ModelArgumentException
  {
    return UtilBoolean.checkTrue(name, value, 1);
  }
  /**
   * Cette méthode permet de tester si le booléen en paramètre vaut true
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du booléen testée
   * @throws UserException Si le booléen en paramètre vaut false
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
   * Cette méthode permet de tester si le booléen en paramètre vaut true
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du booléen testée
   * @throws UserException Si le booléen en paramètre vaut false
   */
  public static boolean checkTrue(String name, boolean value, MessageRef ref)
         throws UserException
  {
    return UtilBoolean.checkTrue(name, value, ref, 1);
  }
  /**
   * Cette méthode permet de tester si le booléen en paramètre vaut false
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du booléen testée
   * @throws ModelArgumentException Si le booléen en paramètre vaut true
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
   * Cette méthode permet de tester si le booléen en paramètre vaut false
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @return La valeur du booléen testée
   * @throws ModelArgumentException Si le booléen en paramètre vaut true
   */
  public static boolean checkFalse(String name, boolean value) throws ModelArgumentException
  {
    return UtilBoolean.checkFalse(name, value, 1);
  }
  /**
   * Cette méthode permet de tester si le booléen en paramètre vaut false
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @return La valeur du booléen testée
   * @param ref La référence du message à utiliser en cas d'echec
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws UserException Si le booléen en paramètre vaut true
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
   * Cette méthode permet de tester si le booléen en paramètre vaut false
   * @param name Nom du booléen à tester
   * @param value Valeur du booléen à tester
   * @return La valeur du booléen testée
   * @param ref La référence du message à utiliser en cas d'echec
   * @throws UserException Si le booléen en paramètre vaut true
   */
  public static boolean checkFalse(String name, boolean value, MessageRef ref)
         throws UserException
  {
    return UtilBoolean.checkFalse(name, value, ref, 1);
  }
}
