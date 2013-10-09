package com.bid4win.commons.core;

import java.util.Collection;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les objets<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilObject
{
  /**
   * Cette méthode permet de tester la non nullité d'un paramètre
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param object Paramètre à tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return Le paramètre testé
   * @throws ModelArgumentException Si le paramètre est nul
   */
  public static <TYPE> TYPE checkNotNull(String name, TYPE object, int stackLevel)
         throws ModelArgumentException
  {
    if(object == null)
    {
      ModelArgumentException.nullParameter(name, 1 + stackLevel);
    }
    return object;
  }
  /**
   * Cette méthode permet de tester la non nullité d'un paramètre
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param object Paramètre à tester
   * @return Le paramètre testé
   * @throws ModelArgumentException Si le paramètre est nul
   */
  public static <TYPE> TYPE checkNotNull(String name, TYPE object) throws ModelArgumentException
  {
    return UtilObject.checkNotNull(name, object, 1);
  }
  /**
   * Cette méthode permet de tester la non nullité d'un paramètre
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param object Paramètre à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @return Le paramètre testé
   * @throws UserException Si le paramètre est nul
   */
  public static <TYPE> TYPE checkNotNull(String name, TYPE object, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObject.checkNotNull(name, object, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la nullité d'un paramètre
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param object Paramètre à tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return Le paramètre testé
   * @throws ModelArgumentException Si le paramètre n'est pas nul
   */
  public static <TYPE> TYPE checkNull(String name, TYPE object, int stackLevel)
         throws ModelArgumentException
  {
    if(object != null)
    {
      ModelArgumentException.notNullParameter(name, object, 1 + stackLevel);
    }
    return object;
  }
  /**
   * Cette méthode permet de tester la nullité d'un paramètre
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param object Paramètre à tester
   * @return Le paramètre testé
   * @throws ModelArgumentException Si le paramètre n'est pas nul
   */
  public static <TYPE> TYPE checkNull(String name, TYPE object) throws ModelArgumentException
  {
    return UtilObject.checkNull(name, object, 1);
  }
  /**
   * Cette méthode permet de tester la nullité d'un paramètre
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param object Paramètre à tester
   * @param ref La référence du message à utiliser en cas d'echec
   * @return Le paramètre testé
   * @throws UserException Si le paramètre n'est pas nul
   */
  public static <TYPE> TYPE checkNull(String name, TYPE object, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObject.checkNull(name, object, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester l'égalité d'un paramètre avec une valeur définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValue Valeur attendue pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'a pas la valeur attendue
   */
  public static <TYPE> TYPE checkEquals(String name, TYPE value,
                                        TYPE expectedValue, int stackLevel)
         throws ModelArgumentException
  {
    if(!Bid4WinComparator.getInstance().equals(value, expectedValue))
    {
      ModelArgumentException.wrongParameterValue(name, value, expectedValue, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette méthode permet de tester l'égalité d'un paramètre avec une valeur définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValue Valeur attendue pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'a pas la valeur attendue
   */
  public static <TYPE> TYPE checkEquals(String name, TYPE value, TYPE expectedValue)
         throws ModelArgumentException
  {
    return UtilObject.checkEquals(name, value, expectedValue, 1);
  }
  /**
   * Cette méthode permet de tester l'égalité d'un paramètre avec une valeur définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValue Valeur attendue pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre n'a pas la valeur attendue
   */
  public static <TYPE> TYPE checkEquals(String name, TYPE value,
                                        TYPE expectedValue, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObject.checkEquals(name, value, expectedValue, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la différence entre un paramètre et une valeur
   * définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValue Valeur interdite pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre a la valeur interdite
   */
  public static <TYPE> TYPE checkDiffers(String name, TYPE value,
                                         TYPE forbiddenValue, int stackLevel)
         throws ModelArgumentException
  {
    if(Bid4WinComparator.getInstance().equals(value, forbiddenValue))
    {
      ModelArgumentException.forbiddenParameterValue(name, forbiddenValue, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette méthode permet de tester la différence entre un paramètre et une valeur
   * définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValue Valeur interdite pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre a la valeur interdite
   */
  public static <TYPE> TYPE checkDiffers(String name, TYPE value, TYPE forbiddenValue)
         throws ModelArgumentException
  {
    return UtilObject.checkDiffers(name, value, forbiddenValue, 1);
  }
  /**
   * Cette méthode permet de tester la différence entre un paramètre et une valeur
   * définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValue Valeur interdite pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre a la valeur interdite
   */
  public static <TYPE> TYPE checkDiffers(String name, TYPE value,
                                         TYPE forbiddenValue, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObject.checkDiffers(name, value, forbiddenValue, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester l'égalité d'un paramètre avec une des valeurs
   * d'une liste définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValues Liste de valeurs attendues pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'a pas une des valeurs attendues
   */
  public static <TYPE> TYPE checkAmong(String name, TYPE value,
                                       Collection<TYPE> expectedValues,
                                       int stackLevel)
         throws ModelArgumentException
  {
    if(!expectedValues.contains(value))
    {
      ModelArgumentException.wrongParameterValue(name, value, expectedValues, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette méthode permet de tester l'égalité d'un paramètre avec une des valeurs
   * d'une liste définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValues Liste de valeurs attendues pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'a pas une des valeurs attendues
   */
  public static <TYPE> TYPE checkAmong(String name, TYPE value, Collection<TYPE> expectedValues)
         throws ModelArgumentException
  {
    return UtilObject.checkAmong(name, value, expectedValues, 1);
  }
  /**
   * Cette méthode permet de tester l'égalité d'un paramètre avec une des valeurs
   * d'une liste définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValues Liste de valeurs attendues pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre n'a pas une des valeurs attendues
   */
  public static <TYPE> TYPE checkAmong(String name, TYPE value,
                                       Collection<TYPE> expectedValues,
                                       MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObject.checkAmong(name, value, expectedValues, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
  /**
   * Cette méthode permet de tester la différence d'un paramètre avec toutes les
   * valeurs d'une liste définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValues Liste de valeurs interdites pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre a une des valeurs interdites
   */
  public static <TYPE> TYPE checkExcept(String name, TYPE value,
                                        Collection<TYPE> forbiddenValues,
                                        int stackLevel)
         throws ModelArgumentException
  {
    if(forbiddenValues.contains(value))
    {
      ModelArgumentException.forbiddenParameterValue(name, value, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette méthode permet de tester la différence d'un paramètre avec toutes les
   * valeurs d'une liste définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValues Liste de valeurs interdites pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre a une des valeurs interdites
   */
  public static <TYPE> TYPE checkExcept(String name, TYPE value, Collection<TYPE> forbiddenValues)
         throws ModelArgumentException
  {
    return UtilObject.checkExcept(name, value, forbiddenValues, 1);
  }
  /**
   * Cette méthode permet de tester la différence d'un paramètre avec toutes les
   * valeurs d'une liste définie
   * @param <TYPE> Définition du type du paramètre à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValues Liste de valeurs interdites pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre a une des valeurs interdites
   */
  public static <TYPE> TYPE checkExcept(String name, TYPE value, Collection<TYPE> forbiddenValues, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObject.checkExcept(name, value, forbiddenValues, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
}
