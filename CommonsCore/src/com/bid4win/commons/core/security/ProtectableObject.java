package com.bid4win.commons.core.security;

import com.bid4win.commons.core.UtilSystem;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe d�fini le comportement de base de tout objet ayant besoin de m�canismes
 * de protection<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ProtectableObject
{
  /** Protection �ventuelle de l'objet */
  private ObjectProtection protection = null;

  /**
   * Constructeur de base avec initialisation de la potentielle protection � partir
   * de l'�ventuelle d�finition de protection en cours
   */
  public ProtectableObject()
  {
    super();
    this.protectFromExisting();
  }
  /**
   * Constructeur de base avec pr�cision de la potentielle protection
   * @param protection Potentielle protection de l'objet
   */
  public ProtectableObject(ObjectProtection protection)
  {
    super();
    this.protect(protection, -1);
  }

  /**
   * Cette m�thode permet de v�rifier la protection de l'objet
   * @throws ProtectionException Si la protection n'est pas v�rifi�e
   */
  public final void checkProtection() throws ProtectionException
  {
    this.checkProtection(1);
  }
  /**
   * Cette m�thode permet de v�rifier la protection de l'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace que l'on souhaite
   * tracer par rapport au niveau de la fonction de v�rification
   * @throws ProtectionException Si la protection n'est pas v�rifi�e
   */
  public final void checkProtection(int stackLevel) throws ProtectionException
  {
    if(this.getProtection() != null && !this.getProtection().check())
    {
      ProtectionException.protectedObject(this.getClass(), 1 + stackLevel);
    }
  }
  /**
   *
   * TODO A COMMENTER
   */
  /*protected void checkHierarchy()
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(1);
    try
    {
      Class<?> expectedHierarchy = Class.forName(element.getClassName());
      this.checkHierarchy(expectedHierarchy, 1);
    }
    catch(ClassNotFoundException ex)
    {
      ProtectionException.protectedObject(this.getClass(), 1);
    }

  }*/
  /**
   *
   * TODO A COMMENTER
   * @param expectedHierarchy TODO A COMMENTER
   */
  protected void checkHierarchy(Class<?> expectedHierarchy)
  {
    this.checkHierarchy(expectedHierarchy, 1);
  }
  /**
   *
   * TODO A COMMENTER
   * @param expectedHierarchy TODO A COMMENTER
   */
  private void checkHierarchy(Class<?> expectedHierarchy, int stackTraceLevel)
  {
    StackTraceElement element = UtilSystem.getStackTraceElement(stackTraceLevel + 2);
    try
    {
      Class<?> caller = Class.forName(element.getClassName());
      if(!expectedHierarchy.isAssignableFrom(caller))
      {
        ProtectionException.protectedObject(this.getClass(), stackTraceLevel + 1);
      }
    }
    catch(ClassNotFoundException ex)
    {
      ProtectionException.protectedObject(this.getClass(), stackTraceLevel + 1);
    }
  }

  /**
   * Cette m�thode permet de prot�ger l'objet contre les modifications en partant
   * de z�ro, c'est � dire sans prise en compte d'une �ventuelle d�finition de
   * protection en cours
   * @return L'identifiant de protection utilis�
   * @throws ProtectionException Si l'objet poss�de d�j� une protection
   */
  public synchronized String protectFromNothing() throws ProtectionException
  {
    String protectionId = ObjectProtector.startProtection();
    try
    {
      this.protect(ObjectProtector.getProtection(), 0);
      return protectionId;
    }
    finally
    {
      ObjectProtector.stopProtection(protectionId);
    }
  }
  /**
   * Cette m�thode permet de prot�ger l'objet contre les modifications en utilisant
   * l'�ventuelle d�finition de protection en cours
   * @throws ProtectionException Si l'objet poss�de d�j� une protection
   */
  public synchronized void protectFromExisting() throws ProtectionException
  {
    this.protect(ObjectProtector.getProtection(), 0);
  }
  /**
   * Cette m�thode permet de prot�ger l'objet contre les modifications en utilisant
   * l'�ventuelle protection en argument
   * @param protection Protection �ventuelle � utiliser
   * @throws ProtectionException Si l'objet poss�de d�j� une protection
   */
  public synchronized void protect(ObjectProtection protection) throws ProtectionException
  {
    this.protect(protection, 0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param protection TODO A COMMENTER
   * @param stackLevel TODO A COMMENTER
   * @throws ProtectionException TODO A COMMENTER
   */
  private void protect(ObjectProtection protection, int stackLevel) throws ProtectionException
  {
    if(this.getProtection() != null && this.getProtection().isEnabled())
    {
      ProtectionException.protectedObject(this.getClass(), 1 + stackLevel);
    }
    this.setProtection(protection);
  }

  /**
   * Getter de la protection de l'objet
   * @return La protection de l'objet
   */
  public ObjectProtection getProtection()
  {
    return this.protection;
  }
  /**
   * Setter de la protection de l'objet
   * @param protection Protection de l'objet � positionner
   */
  private void setProtection(ObjectProtection protection)
  {
    this.protection = protection;
  }
}
