package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.core.security.ObjectProtector;

/**
 * Définition des relations de la classe Bid4WinEntity vers les autres entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntity_Relations
{
  // Démarre la protection des relations
  static
  {
    Bid4WinEntity_Relations.startProtection();
  }
  // Arrête la protection des relations
  static
  {
    Bid4WinEntity_Relations.stopProtection();
  }

  /** Identifiant de protection des relations */
  private static String protectionId = IdGenerator.generateId(16);
  /**
   * Permet de démarrer la protection des relations
   */
  protected static void startProtection()
  {
    ObjectProtector.startProtection(Bid4WinEntity_Relations.getProtectionId());
  }
  /**
   * Permet d'arréter la protection des relations
   */
  protected static void stopProtection()
  {
    ObjectProtector.stopProtection(Bid4WinEntity_Relations.getProtectionId());
  }
  /**
   * Getter de l'identifiant de protection des relations
   * @return L''identifiant de protection des relations
   */
  private static String getProtectionId()
  {
    return Bid4WinEntity_Relations.protectionId;
  }
}
