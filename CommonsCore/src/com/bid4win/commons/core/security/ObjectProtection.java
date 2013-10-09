package com.bid4win.commons.core.security;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe d�fini la protection d'un objet<BR>
 * <BR>
 * @param <OBJECT> D�finition de l'objet prot�g�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ObjectProtection<OBJECT>
{
  /** Identifiant de protection */
  private String id = null;
  /** Objet prot�g� */
  private OBJECT object = null;
  
  /**
   * Constructeur
   * @param object Objet prot�g�
   */
  public ObjectProtection(OBJECT object)
  {
    this.setId(ObjectProtector.getProtectionId());
    this.setObject(object);
  }
  
  /**
   * Cette m�thode permet de v�rifier si la protection est active
   * @return True si la protection est active, false sinon
   */
  public boolean isEnabled()
  {
    return this.getId() != null;
  }
  /**
   * Cette m�thode permet de v�rifier la protection de l'objet
   * @throws ProtectionException Si la protection n'est pas v�rifi�e
   */
  public void check() throws ProtectionException
  {
    this.check(1);
  }
  /**
   * Cette m�thode permet de v�rifier la protection de l'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en �chec que l'on souhaite tracer
   * @throws ProtectionException Si la protection n'est pas v�rifi�e
   */
  public void check(int stackLevel) throws ProtectionException
  {
    // L'objet est prot�g�
    if(this.isEnabled())
    {
      // La protection n'est pas v�rifi�e
      if(!ObjectProtector.hasProtectionId(this.getId()))
      {
        ProtectionException.protectedObject(this.getObject().getClass(),
                                            1 + stackLevel);
      }
    }
  }
  
  /**
   * Getter de l'identifiant de protection
   * @return L'identifiant de protection
   */
  private String getId()
  {
    return this.id;
  }
  /**
   * Getter de l'objet prot�g�
   * @return L'objet prot�g�
   */
  private OBJECT getObject()
  {
    return this.object;
  }
  /**
   * Setter de l'identifiant de protection
   * @param id Identifiant de protection � positionner
   */
  private void setId(String id)
  {
    this.id = id;
  }
  /**
   * Setter de l'objet prot�g�
   * @param object Objet prot�g� � positionner
   */
  private void setObject(OBJECT object)
  {
    this.object = object;
  }
}
