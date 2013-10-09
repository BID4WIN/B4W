package com.bid4win.commons.core;

import java.util.Collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les types d'objets<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilObjectType
{
  /**
   * Cette m�thode permet de tester l'appartenance d'un type d'objet � celui d�fini
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValue Valeur attendue pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'appartient pas � celui attendu
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, TYPE expectedValue, int stackLevel)
         throws ModelArgumentException
  {
    if(value == null || !value.belongsTo(expectedValue))
    {
      ModelArgumentException.wrongObjectTypeBelonging(name, value, expectedValue, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette m�thode permet de tester l'appartenance d'un type d'objet � celui d�fini
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValue Valeur attendue pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'appartient pas � celui attendu
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, TYPE expectedValue)
         throws ModelArgumentException
  {
    return UtilObjectType.checkBelongsTo(name, value, expectedValue, 1);
  }
  /**
   * Cette m�thode permet de tester l'appartenance d'un type d'objet � celui d�fini
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param expectedValue Valeur attendue pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre n'appartient pas � celui attendu
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, TYPE expectedValue, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObjectType.checkBelongsTo(name, value, expectedValue, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }

  /**
   * Cette m�thode permet de tester la non-appartenance d'un type d'objet � celui
   * d�fini
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValue Valeur interdite pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre appartient � celui interdit
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, TYPE forbiddenValue, int stackLevel)
         throws ModelArgumentException
  {
    if(value != null && value.belongsTo(forbiddenValue))
    {
      ModelArgumentException.wrongObjectTypeExclusion(name, value, forbiddenValue, 1 + stackLevel);
    }
    return value;
  }
  /**
   * Cette m�thode permet de tester la non-appartenance d'un type d'objet � celui
   * d�fini
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValue Valeur interdite pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre appartient � celui interdit
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, TYPE forbiddenValue)
         throws ModelArgumentException
  {
    return UtilObjectType.checkNotBelongsTo(name, value, forbiddenValue, 1);
  }
  /**
   * Cette m�thode permet de tester la non-appartenance d'un type d'objet � celui
   * d�fini
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValue Valeur interdite pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre appartient � celui interdit
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, TYPE forbiddenValue, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObjectType.checkNotBelongsTo(name, value, forbiddenValue, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }

  /**
   * Cette m�thode permet de tester l'appartenance d'un type d'objet � un de ceux
   * d�finis
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param authorizedValues Valeurs autoris�es pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'appartient pas aucun de ceux
   * attendus
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, Collection<TYPE> authorizedValues, int stackLevel)
         throws ModelArgumentException
  {
    if(value != null)
    {
      for(TYPE type : authorizedValues)
      {
        if(value.belongsTo(type))
        {
          return value;
        }
      }
    }
    ModelArgumentException.wrongObjectTypeBelonging(name, value, authorizedValues, 1 + stackLevel);
    return null;
  }
  /**
   * Cette m�thode permet de tester l'appartenance d'un type d'objet � un de ceux
   * d�finis
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param authorizedValues Valeurs autoris�es pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre n'appartient pas aucun de ceux
   * attendus
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, Collection<TYPE> authorizedValues)
         throws ModelArgumentException
  {
    return UtilObjectType.checkBelongsTo(name, value, authorizedValues, 1);
  }
  /**
   * Cette m�thode permet de tester l'appartenance d'un type d'objet � un de ceux
   * d�finis
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param authorizedValues Valeurs autoris�es pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre n'appartient pas aucun de ceux attendus
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, Collection<TYPE> authorizedValues, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObjectType.checkBelongsTo(name, value, authorizedValues, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }

  /**
   * Cette m�thode permet de tester la non-appartenance d'un type d'objet � ceux
   * d�finis
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValues Valeurs interdites pour le param�tre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre appartient � un de ceux interdits
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, Collection<TYPE> forbiddenValues, int stackLevel)
         throws ModelArgumentException
  {
    if(value != null)
    {
      for(TYPE type : forbiddenValues)
      {
        if(value.belongsTo(type))
        {
          ModelArgumentException.wrongObjectTypeExclusion(name, value, forbiddenValues, 1 + stackLevel);
        }
      }
    }
    return value;
  }
  /**
   * Cette m�thode permet de tester la non-appartenance d'un type d'objet � ceux
   * d�finis
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValues Valeurs interdites pour le param�tre
   * @return La valeur du param�tre test�e
   * @throws ModelArgumentException Si le param�tre appartient � un de ceux interdits
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, Collection<TYPE> forbiddenValues)
         throws ModelArgumentException
  {
    return UtilObjectType.checkNotBelongsTo(name, value, forbiddenValues, 1);
  }
  /**
   * Cette m�thode permet de tester la non-appartenance d'un type d'objet � ceux
   * d�finis
   * @param <TYPE> D�finition du type d'objet � tester
   * @param name Nom du param�tre � tester
   * @param value Valeur du param�tre � tester
   * @param forbiddenValues Valeurs interdites pour le param�tre
   * @param ref La r�f�rence du message � utiliser en cas d'echec
   * @return La valeur du param�tre test�e
   * @throws UserException Si le param�tre appartient � un de ceux interdits
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, Collection<TYPE> forbiddenValues, MessageRef ref)
         throws UserException
  {
    try
    {
      return UtilObjectType.checkNotBelongsTo(name, value, forbiddenValues, 1);
    }
    catch(ModelArgumentException ex)
    {
      throw new UserException(ref, ex);
    }
  }
}
