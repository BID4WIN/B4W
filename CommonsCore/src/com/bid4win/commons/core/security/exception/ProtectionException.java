package com.bid4win.commons.core.security.exception;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini les exceptions de protection d'objet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ProtectionException extends Bid4WinRuntimeException
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -7829790009750326536L;

  /**
   * Constructeur par d�faut
   */
  public ProtectionException()
  {
    super();
  }

  /**
   * Constructeur bas� sur un message d'exception
   * @param message Message associ� � l'exception � construire
   */
  public ProtectionException(String message)
  {
    super(message);
  }

  /**
   * Cette m�thode permet de lever une exception pour une action interdite car la
   * protection de l'objet n'est pas v�rifi�e
   * @param objectClass Classe de l'objet prot�g�
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @throws ProtectionException Exception traitant le probl�me de protection
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
   * Cette m�thode permet de lever une exception pour une tentative d'arr�t de
   * protection avec un mauvais identifiant
   * @throws ProtectionException Exception traitant le probl�me de protection
   */
  public static void stopId() throws ProtectionException
  {
    String message = "Cannot stop protection with ID different from which used for starting it";
    throw new ProtectionException(message);
  }
}
