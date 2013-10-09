package com.bid4win.commons.core.security;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe défini la protection d'un objet<BR>
 * <BR>
 * @param <OBJECT> Définition de l'objet protégé<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ObjectProtection<OBJECT>
{
  /** Identifiant de protection */
  private String id = null;
  /** Objet protégé */
  private OBJECT object = null;
  
  /**
   * Constructeur
   * @param object Objet protégé
   */
  public ObjectProtection(OBJECT object)
  {
    this.setId(ObjectProtector.getProtectionId());
    this.setObject(object);
  }
  
  /**
   * Cette méthode permet de vérifier si la protection est active
   * @return True si la protection est active, false sinon
   */
  public boolean isEnabled()
  {
    return this.getId() != null;
  }
  /**
   * Cette méthode permet de vérifier la protection de l'objet
   * @throws ProtectionException Si la protection n'est pas vérifiée
   */
  public void check() throws ProtectionException
  {
    this.check(1);
  }
  /**
   * Cette méthode permet de vérifier la protection de l'objet
   * @param stackLevel Niveau de la fonction appelante dans la trace par rapport
   * au niveau de la fonction en échec que l'on souhaite tracer
   * @throws ProtectionException Si la protection n'est pas vérifiée
   */
  public void check(int stackLevel) throws ProtectionException
  {
    // L'objet est protégé
    if(this.isEnabled())
    {
      // La protection n'est pas vérifiée
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
   * Getter de l'objet protégé
   * @return L'objet protégé
   */
  private OBJECT getObject()
  {
    return this.object;
  }
  /**
   * Setter de l'identifiant de protection
   * @param id Identifiant de protection à positionner
   */
  private void setId(String id)
  {
    this.id = id;
  }
  /**
   * Setter de l'objet protégé
   * @param object Objet protégé à positionner
   */
  private void setObject(OBJECT object)
  {
    this.object = object;
  }
}
