package com.bid4win.commons.core;

import java.util.Collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;

/**
 * Classe utilitaire sur les types d'objets<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilObjectType
{
  /**
   * Cette méthode permet de tester l'appartenance d'un type d'objet à celui défini
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValue Valeur attendue pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'appartient pas à celui attendu
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
   * Cette méthode permet de tester l'appartenance d'un type d'objet à celui défini
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValue Valeur attendue pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'appartient pas à celui attendu
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, TYPE expectedValue)
         throws ModelArgumentException
  {
    return UtilObjectType.checkBelongsTo(name, value, expectedValue, 1);
  }
  /**
   * Cette méthode permet de tester l'appartenance d'un type d'objet à celui défini
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param expectedValue Valeur attendue pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre n'appartient pas à celui attendu
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
   * Cette méthode permet de tester la non-appartenance d'un type d'objet à celui
   * défini
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValue Valeur interdite pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre appartient à celui interdit
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
   * Cette méthode permet de tester la non-appartenance d'un type d'objet à celui
   * défini
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValue Valeur interdite pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre appartient à celui interdit
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, TYPE forbiddenValue)
         throws ModelArgumentException
  {
    return UtilObjectType.checkNotBelongsTo(name, value, forbiddenValue, 1);
  }
  /**
   * Cette méthode permet de tester la non-appartenance d'un type d'objet à celui
   * défini
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValue Valeur interdite pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre appartient à celui interdit
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
   * Cette méthode permet de tester l'appartenance d'un type d'objet à un de ceux
   * définis
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param authorizedValues Valeurs autorisées pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'appartient pas aucun de ceux
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
   * Cette méthode permet de tester l'appartenance d'un type d'objet à un de ceux
   * définis
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param authorizedValues Valeurs autorisées pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre n'appartient pas aucun de ceux
   * attendus
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkBelongsTo(String name, TYPE value, Collection<TYPE> authorizedValues)
         throws ModelArgumentException
  {
    return UtilObjectType.checkBelongsTo(name, value, authorizedValues, 1);
  }
  /**
   * Cette méthode permet de tester l'appartenance d'un type d'objet à un de ceux
   * définis
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param authorizedValues Valeurs autorisées pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre n'appartient pas aucun de ceux attendus
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
   * Cette méthode permet de tester la non-appartenance d'un type d'objet à ceux
   * définis
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValues Valeurs interdites pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre appartient à un de ceux interdits
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
   * Cette méthode permet de tester la non-appartenance d'un type d'objet à ceux
   * définis
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValues Valeurs interdites pour le paramètre
   * @return La valeur du paramètre testée
   * @throws ModelArgumentException Si le paramètre appartient à un de ceux interdits
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         TYPE checkNotBelongsTo(String name, TYPE value, Collection<TYPE> forbiddenValues)
         throws ModelArgumentException
  {
    return UtilObjectType.checkNotBelongsTo(name, value, forbiddenValues, 1);
  }
  /**
   * Cette méthode permet de tester la non-appartenance d'un type d'objet à ceux
   * définis
   * @param <TYPE> Définition du type d'objet à tester
   * @param name Nom du paramètre à tester
   * @param value Valeur du paramètre à tester
   * @param forbiddenValues Valeurs interdites pour le paramètre
   * @param ref La référence du message à utiliser en cas d'echec
   * @return La valeur du paramètre testée
   * @throws UserException Si le paramètre appartient à un de ceux interdits
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
