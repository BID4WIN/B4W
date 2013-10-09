package com.bid4win.commons.core;

import java.util.Collection;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les objets<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilObject
{
  /**
   * Cette m�thode permet de tester la non nullit� d'un param�tre
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param object Param�tre � tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return Le param�tre test�
   * @throws ModelArgumentException Si le param�tre est nul
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
   * Cette m�thode permet de tester la non nullit� d'un param�tre
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param object Param�tre � tester
   * @return Le param�tre test�
   * @throws ModelArgumentException Si le param�tre est nul
   */
  public static <TYPE> TYPE checkNotNull(String name, TYPE object) throws ModelArgumentException
  {
    return UtilObject.checkNotNull(name, object, 1);
  }
  /**
   * Cette m�thode permet de tester la non nullit� d'un param�tre
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param object Param�tre � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return Le param�tre test�
   * @throws UserException Si le param�tre est nul
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
   * Cette m�thode permet de tester la nullit� d'un param�tre
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param object Param�tre � tester
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return Le param�tre test�
   * @throws ModelArgumentException Si le param�tre n'est pas nul
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
   * Cette m�thode permet de tester la nullit� d'un param�tre
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param object Param�tre � tester
   * @return Le param�tre test�
   * @throws ModelArgumentException Si le param�tre n'est pas nul
   */
  public static <TYPE> TYPE checkNull(String name, TYPE object) throws ModelArgumentException
  {
    return UtilObject.checkNull(name, object, 1);
  }
  /**
   * Cette m�thode permet de tester la nullit� d'un param�tre
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param object Param�tre � tester
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return Le param�tre test�
   * @throws UserException Si le param�tre n'est pas nul
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
   * Cette m�thode permet de tester l'�galit� d'un param�tre avec une valeur d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValue Valeur attendue pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'a pas la valeur attendue
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
   * Cette m�thode permet de tester l'�galit� d'un param�tre avec une valeur d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValue Valeur attendue pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'a pas la valeur attendue
   */
  public static <TYPE> TYPE checkEquals(String name, TYPE value, TYPE expectedValue)
         throws ModelArgumentException
  {
    return UtilObject.checkEquals(name, value, expectedValue, 1);
  }
  /**
   * Cette m�thode permet de tester l'�galit� d'un param�tre avec une valeur d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValue Valeur attendue pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre n'a pas la valeur attendue
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
   * Cette m�thode permet de tester la diff�rence entre un param�tre et une valeur
   * d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValue Valeur interdite pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre a la valeur interdite
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
   * Cette m�thode permet de tester la diff�rence entre un param�tre et une valeur
   * d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValue Valeur interdite pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre a la valeur interdite
   */
  public static <TYPE> TYPE checkDiffers(String name, TYPE value, TYPE forbiddenValue)
         throws ModelArgumentException
  {
    return UtilObject.checkDiffers(name, value, forbiddenValue, 1);
  }
  /**
   * Cette m�thode permet de tester la diff�rence entre un param�tre et une valeur
   * d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValue Valeur interdite pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre a la valeur interdite
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
   * Cette m�thode permet de tester l'�galit� d'un param�tre avec une des valeurs
   * d'une liste d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValues Liste de valeurs attendues pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'a pas une des valeurs attendues
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
   * Cette m�thode permet de tester l'�galit� d'un param�tre avec une des valeurs
   * d'une liste d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValues Liste de valeurs attendues pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'a pas une des valeurs attendues
   */
  public static <TYPE> TYPE checkAmong(String name, TYPE value, Collection<TYPE> expectedValues)
         throws ModelArgumentException
  {
    return UtilObject.checkAmong(name, value, expectedValues, 1);
  }
  /**
   * Cette m�thode permet de tester l'�galit� d'un param�tre avec une des valeurs
   * d'une liste d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValues Liste de valeurs attendues pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre n'a pas une des valeurs attendues
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
   * Cette m�thode permet de tester la diff�rence d'un param�tre avec toutes les
   * valeurs d'une liste d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValues Liste de valeurs interdites pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre a une des valeurs interdites
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
   * Cette m�thode permet de tester la diff�rence d'un param�tre avec toutes les
   * valeurs d'une liste d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValues Liste de valeurs interdites pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre a une des valeurs interdites
   */
  public static <TYPE> TYPE checkExcept(String name, TYPE value, Collection<TYPE> forbiddenValues)
         throws ModelArgumentException
  {
    return UtilObject.checkExcept(name, value, forbiddenValues, 1);
  }
  /**
   * Cette m�thode permet de tester la diff�rence d'un param�tre avec toutes les
   * valeurs d'une liste d�finie
   * @param <TYPE> D�finition du type du param�tre � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValues Liste de valeurs interdites pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre a une des valeurs interdites
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
