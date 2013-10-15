package com.bid4win.commons.core.security.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini les exceptions de protection d'objet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ProtectionException extends Bid4WinRuntimeException
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -7829790009750326536L;

  /**
   * Constructeur par défaut
   */
  public ProtectionException()
  {
    super();
  }

  /**
   * Constructeur basé sur un message d'exception
   * @param message Message associé à l'exception à construire
   */
  public ProtectionException(String message)
  {
    super(message);
  }

  /**
   * Cette méthode permet de lever une exception pour une action interdite car la
   * protection de l'objet n'est pas vérifiée
   * @param objectClass Classe de l'objet protégé
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ProtectionException Exception traitant le problème de protection
   */
  public static void protectedObject(Class<?> objectClass, int stackLevel) throws ProtectionException
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1 + stackLevel);
    String className = UtilSystem.getCanonicalClassName(element);
    String methodName = element.getMethodName();
    String objectName = UtilSystem.getCanonicalClassName(objectClass);
    String message = objectName + " is protected upon " +
                     className + "." + methodName + "() call" ;
    throw new ProtectionException(message);
  }

  /**
   * Cette méthode permet de lever une exception pour une tentative d'arrêt de
   * protection avec un mauvais identifiant
   * @throws ProtectionException Exception traitant le problème de protection
   */
  public static void stopId() throws ProtectionException
  {
    String message = "Cannot stop protection with ID different from which used for starting it";
    throw new ProtectionException(message);
  }
}
