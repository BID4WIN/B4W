package com.bid4win.commons.core.exception;

import java.util.Collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer;
import com.bid4win.commons.core.renderer.Bid4WinRenderer;

/**
 * Cette classe défini la classe de base de toutes les exceptions métier qui doivent
 * traiter d'un problème sur les paramètres d'une fonction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ModelArgumentException extends BusinessException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -860408080021389385L;

  /**
   * Cette méthode permet de lever une exception pour un paramètre dont la valeur
   * est inférieure à une borne définie
   * @param name Nom du paramètre refusé
   * @param value Valeur du paramètre refusé
   * @param minValue Valeur minimum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void wrongMinParameterValue(String name, String value, String minValue,
                                            boolean inclusive, int stackLevel)
          throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " value should";
    if(inclusive)
    {
      message += " not be smaller";
    }
    else
    {
      message += " be greater";
    }
    message += " than " + minValue + " for " + methodName + "() call in a " +
               className + " object and not " + value;
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un paramètre dont la valeur
   * est supérieure à une borne définie
   * @param name Nom du paramètre refusé
   * @param value Valeur du paramètre refusé
   * @param maxValue Valeur maximum autorisée pour le paramètre
   * @param inclusive Indique si la borne inférieure précisée est inclusive ou
   * exclusive
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void wrongMaxParameterValue(String name, String value, String maxValue,
                                            boolean inclusive, int stackLevel)
          throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " value should";
    if(inclusive)
    {
      message += " not be greater";
    }
    else
    {
      message += " be smaller";
    }
    message += " than " + maxValue + " for " + methodName + "() call in a " +
               className + " object and not " + value;
    throw new ModelArgumentException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour un paramètre nul
   * @param name Nom du paramètre nul
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void nullParameter(String name, int stackLevel) throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " should not be null for " + methodName +
                     "() call in a " + className  + " object";
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un paramètre non nul
   * @param name Nom du paramètre non nul
   * @param value Valeur du paramètre non nul
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void notNullParameter(String name, Object value, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " should be null for " + methodName + "() call in a " +
                     className  + " object and not " +
                     Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour un paramètre invalide
   * @param <TYPE> Définition du type du paramètre invalide
   * @param name Nom du paramètre invalide
   * @param value Valeur du paramètre invalide
   * @param expectedValue Valeur autorisée pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE> void wrongParameterValue(String name, TYPE value,
                                                TYPE expectedValue, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " value should be " +
                     Bid4WinRenderer.getInstance().toString(expectedValue) +
                     " for " + methodName + "() call in a " + className +
                     " object and not " + Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un paramètre invalide
   * @param <TYPE> Définition du type du paramètre invalide
   * @param name Nom du paramètre invalide
   * @param value Valeur du paramètre invalide
   * @param expectedValues Liste de valeurs autorisées pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE> void wrongParameterValue(String name, TYPE value,
                                                Collection<TYPE> expectedValues,
                                                int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " value should be one of " +
                     Bid4WinRenderer.getInstance().toString(expectedValues) +
                     " for " + methodName + "() call in a " + className +
                     " object and not " + Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un paramètre invalide
   * @param <TYPE> Définition du type du paramètre invalide
   * @param name Nom du paramètre invalide
   * @param value Valeur du paramètre invalide
   * @param expectedValues Tableau de valeurs autorisées pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE> void wrongParameterValue(String name, TYPE value,
                                                TYPE[] expectedValues,
                                                int stackLevel)
         throws ModelArgumentException
  {
    ModelArgumentException.wrongParameterValue(
        name, value, new Bid4WinSet<TYPE>(expectedValues), stackLevel);
  }

  /**
   * Cette méthode permet de lever une exception pour un paramètre interdit
   * @param <TYPE> Définition du type du paramètre interdit
   * @param name Nom du paramètre interdit
   * @param forbiddenValue Valeur du paramètre interdit
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE> void forbiddenParameterValue(String name, TYPE forbiddenValue,
                                                    int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " value should not be " +
                     Bid4WinRenderer.getInstance().toString(forbiddenValue) +
                     " for " + methodName + "() call in a " + className + " object";
    throw new ModelArgumentException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour un paramètre non vide
   * @param name Nom du paramètre invalide
   * @param string Valeur du paramètre invalide
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void notEmptyString(String name, String string, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " should be empty for " + methodName + "() call in a " +
                     className + " object and not be " + string;
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un paramètre vide
   * @param name Nom du paramètre invalide
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void emptyString(String name, int stackLevel) throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " should not be empty for " + methodName + "()" +
                     " call in a " + className + " object";
    throw new ModelArgumentException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour un paramètre ne respectant
   * le bon pattern
   * @param name Nom du paramètre invalide
   * @param string Valeur du paramètre invalide
   * @param expectedPattern Pattern attendu pour le paramètre
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static void wrongPattern(String name, String string,
                                  String expectedPattern, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " value should match pattern " + expectedPattern +
                     " for " + methodName + "() call in a " + className + " object" +
                     " and not be " + string;
    throw new ModelArgumentException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour un type d'objet invalide
   * @param <TYPE> Définition du type du type d'objet invalide
   * @param name Nom du type d'objet invalide
   * @param value Valeur du type d'objet invalide
   * @param expectedValue Valeur autorisée pour le type d'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         void wrongObjectTypeBelonging(String name, TYPE value, TYPE expectedValue, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " type should belong to " +
                     Bid4WinRenderer.getInstance().toString(expectedValue) +
                     " for " + methodName + "() call in a " + className +
                     " object and not be " + Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un type d'objet invalide
   * @param <TYPE> Définition du type du type d'objet invalide
   * @param name Nom du type d'objet invalide
   * @param value Valeur du type d'objet invalide
   * @param forbiddenValue Valeur interdite pour le type d'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         void wrongObjectTypeExclusion(String name, TYPE value, TYPE forbiddenValue, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " type should not belong to " +
                     Bid4WinRenderer.getInstance().toString(forbiddenValue) +
                     " for " + methodName + "() call in a " + className +
                     " object and not be " + Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour un type d'objet invalide
   * @param <TYPE> Définition du type du type d'objet invalide
   * @param name Nom du type d'objet invalide
   * @param value Valeur du type d'objet invalide
   * @param authorizedValues Valeurs autorisées pour le type d'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         void wrongObjectTypeBelonging(String name, TYPE value, Collection<TYPE> authorizedValues, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " type should belong to one of " +
                     Bid4WinCollectionRenderer.getInstanceCollection().toString(authorizedValues) +
                     " for " + methodName + "() call in a " + className +
                     " object and not be " + Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }
  /**
   * Cette méthode permet de lever une exception pour un type d'objet invalide
   * @param <TYPE> Définition du type du type d'objet invalide
   * @param name Nom du type d'objet invalide
   * @param value Valeur du type d'objet invalide
   * @param forbiddenValues Valeurs interdites pour le type d'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ModelArgumentException Exception traitant le problème d'instanciation
   */
  public static <TYPE extends Bid4WinObjectType<TYPE>>
         void wrongObjectTypeExclusion(String name, TYPE value, Collection<TYPE> forbiddenValues, int stackLevel)
         throws ModelArgumentException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String message = name + " type should not belong to one of " +
                     Bid4WinCollectionRenderer.getInstanceCollection().toString(forbiddenValues) +
                     " for " + methodName + "() call in a " + className +
                     " object and not be " + Bid4WinRenderer.getInstance().toString(value);
    throw new ModelArgumentException(message);
  }

  /**
   * Constructeur par défaut
   */
  public ModelArgumentException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public ModelArgumentException(String message)
  {
    super(message);
  }

  /**
   * Constructeur basé sur une exception précédente
   * @param throwable Exception ayant causé le problème
   */
  public ModelArgumentException(Throwable throwable)
  {
    super(throwable);
  }

  //*************************************************************************//
  //*************************************************************************//

  /**
   * Cette classe défini la classe de base de toutes les exceptions métier qui
   * doivent traiter d'un problème sur les types d'objets<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
 /* public static class ObjectTypeException extends ModelArgumentException
  {
    /**
     * Constructeur par défaut
     */
  /*  public ObjectTypeException()
    {
      super();
    }

    /**
     * Constructeur basé sur un message d'exception
     * @param message Message associé à l'exception à construire
     */
   /* public ObjectTypeException(String message)
    {
      super(message);
    }

    /**
     * Cette méthode permet de lever une exception pour un type d'objet de primitive
     * invalide
     * @param name Nom du type d'objet invalide
     * @param primitive Type d'objet primitif invalide
     * @param expectedPrimitive Type autorisé pour la primitive du type d'objet
     * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
     * au niveau de la fonction en échec que l'on souhaite tracer
     * @throws ObjectTypeException Exception traitant le problème d'instanciation
     */
 /*   public static void wrongPrimitiveGroup(String name, ObjectType primitive,
                                           ObjectType expectedPrimitive, int stackLevel)
           throws ObjectTypeException
    {
      StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
      String className = UtilSystem.getCanonicalClassName(element);
      String methodName = element.getMethodName();
      String message = name + " primitive type should be " +
                       UtilString.toString(expectedPrimitive) +
                       " for " + methodName + "() call in a " + className +
                       " object and not " + UtilString.toString(primitive);
      throw new ObjectTypeException(message);
    }

    /**
     * Cette méthode permet de lever une exception pour un type d'objet de primitive
     * invalide
     * @param name Nom du type d'objet invalide
     * @param primitive Type d'objet primitif invalide
     * @param expectedPrimitives Liste de types autorisés pour la primitive du type
     * d'objet
     * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
     * au niveau de la fonction en échec que l'on souhaite tracer
     * @throws ObjectTypeException Exception traitant le problème d'instanciation
     */
  /*  public static void wrongPrimitiveGroup(String name, ObjectType primitive,
                                           ObjectTypeList expectedPrimitives,
                                           int stackLevel)
           throws ObjectTypeException
    {
      StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
      String className = UtilSystem.getCanonicalClassName(element);
      String methodName = element.getMethodName();
      String message = name + " primitive type should be one of " +
                       UtilString.toString(expectedPrimitives) +
                       " for " + methodName + "() call in a " + className +
                       " object and not " + UtilString.toString(primitive);
      throw new ObjectTypeException(message);
    }

    /**
     * Cette méthode permet de lever une exception de mauvaise appartenance de type
     * d'objet
     * @param name Nom du type d'objet invalide
     * @param type Type d'objet invalide
     * @param expectedGroup Type d'objet autorisé pour l'appartenance
     * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
     * au niveau de la fonction en échec que l'on souhaite tracer
     * @throws ObjectTypeException Exception traitant le problème d'instanciation
     */
  /*  public static void wrongBelonging(String name, ObjectType type,
                                      ObjectType expectedGroup, int stackLevel)
           throws ObjectTypeException
    {
      StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
      String className = UtilSystem.getCanonicalClassName(element);
      String methodName = element.getMethodName();
      String message = name + " type should belong to " +
                       UtilString.toString(expectedGroup) +
                       " for " + methodName + "() call in a " + className +
                       " object and not " + UtilString.toString(type);
      throw new ObjectTypeException(message);
    }

    /**
     * Cette méthode permet de lever une exception d'interdiction appartenance de
     * type d'objet
     * @param name Nom du type d'objet invalide
     * @param forbiddenGroup Type d'objet interdit pour l'appartenance
     * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
     * au niveau de la fonction en échec que l'on souhaite tracer
     * @throws ObjectTypeException Exception traitant le problème d'instanciation
     */
  /*  public static void forbiddenBelonging(String name, ObjectType forbiddenGroup, int stackLevel)
           throws ObjectTypeException
    {
      StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
      String className = UtilSystem.getCanonicalClassName(element);
      String methodName = element.getMethodName();
      String message = name + " type should not belong to " +
                       UtilString.toString(forbiddenGroup) +
                       " for " + methodName + "() call in a " + className + " object";
      throw new ObjectTypeException(message);
    }
  }*/
}
