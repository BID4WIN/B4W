package com.bid4win.commons.core;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les nombres décimaux<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilNumber
{
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static int checkMinValue(String name, int value, int minValue,
                                  boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    return (int)UtilNumber.checkMinValue(
        name, (double)value, (double)minValue, inclusive, 1 + stackLevel);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static int checkMinValue(String name, int value, int minValue,
                                  boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static int checkMinValue(String name, int value, int minValue,
                                  boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static long checkMinValue(String name, long value, long minValue,
                                   boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    return (long)UtilNumber.checkMinValue(
        name, (double)value, (double)minValue, inclusive, 1 + stackLevel);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static long checkMinValue(String name, long value, long minValue,
                                   boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static long checkMinValue(String name, long value, long minValue,
                                   boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static float checkMinValue(String name, float value, float minValue,
                                    boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    return (float)UtilNumber.checkMinValue(
        name, (double)value, (double)minValue, inclusive, 1 + stackLevel);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static float checkMinValue(String name, float value, float minValue, boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static float checkMinValue(String name, float value, float minValue, boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static double checkMinValue(String name, double value, double minValue,
                                     boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    if(value < minValue || (!inclusive && value == minValue))
    {
      ModelArgumentException.wrongMinParameterValue(
          name, UtilString.EMPTY + value, UtilString.EMPTY + minValue, inclusive, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static double checkMinValue(String name, double value, double minValue,
                                     boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
  public static double checkMinValue(String name, double value, double minValue, boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMinValue(name, value, minValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur minimum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est inférieure au
   * minimum autorisé
   */
 /* public static Decimal checkMinValue(String name, Decimal value, Decimal minValue, boolean inclusive)
         throws ModelArgumentException
  {
    if(value.smallerThan(minValue) || (!inclusive && value.sameAs(minValue)))
    {
      ModelArgumentException.wrongMinParameterValue(
      name, value.toString(), minValue.toString(), inclusive, 1);
    }
    return value;
  }*/
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static int checkMaxValue(String name, int value, int maxValue,
                                  boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    return (int)UtilNumber.checkMaxValue(
        name, (double)value, (double)maxValue, inclusive, 1 + stackLevel);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static int checkMaxValue(String name, int value, int maxValue,
                                  boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static int checkMaxValue(String name, int value, int maxValue,
                                  boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static long checkMaxValue(String name, long value, long maxValue,
                                   boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    return (long)UtilNumber.checkMaxValue(
        name, (double)value, (double)maxValue, inclusive, 1 + stackLevel);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static long checkMaxValue(String name, long value, long maxValue,
                                   boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static long checkMaxValue(String name, long value, long maxValue,
                                   boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static float checkMaxValue(String name, float value, float maxValue,
                                    boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    return (float)UtilNumber.checkMaxValue(
        name, (double)value, (double)maxValue, inclusive, 1 + stackLevel);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static float checkMaxValue(String name, float value, float maxValue,
                                    boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static float checkMaxValue(String name, float value, float maxValue,
                                    boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static double checkMaxValue(String name, double value, double maxValue,
                                     boolean inclusive, int stackLevel)
         throws ModelArgumentException
  {
    if(value > maxValue || (!inclusive && value == maxValue))
    {
      ModelArgumentException.wrongMaxParameterValue(
          name, UtilString.EMPTY + value, UtilString.EMPTY + maxValue, inclusive, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static double checkMaxValue(String name, double value, double maxValue,
                                     boolean inclusive)
         throws ModelArgumentException
  {
    return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
  public static double checkMaxValue(String name, double value, double maxValue,
                                     boolean inclusive, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilNumber.checkMaxValue(name, value, maxValue, inclusive, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la valeur maximum d'un paramètre
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne supérieur précisée est inclusive ou
   * exclusive
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si la valeur du paramètre est supérieure au
   * maximum autorisé
   */
 /* public static Decimal checkMaxValue(String name, Decimal value, Decimal maxValue, boolean inclusive)
         throws ModelArgumentException
  {
    if(value.greaterThan(maxValue) || (!inclusive && value.sameAs(maxValue)))
    {
      ModelArgumentException.wrongMaxParameterValue(
          name, value.toString(), maxValue.toString(), inclusive, 1);
    }
    return value;
  }*/

  /**
   * Cette méthode permet de connaître le nombre de décimales après la virgule de
   * la valeur en argument
   * @param value Valeur dont on souhaite connaître le nombre de décimales après
   * la virgule
   * @return Le nombre de décimales après la virgule de la valeur en argument
   */
  public static int getDecimalNb(double value)
  {
    if(value - (int)value == 0)
    {
      return 0;
    }
    String string = String.valueOf(value);
    return string.length() - string.indexOf(".") - 1;
  }
}
