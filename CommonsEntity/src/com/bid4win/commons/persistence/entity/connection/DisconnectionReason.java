package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette classe défini une raison de déconnexion comparable à une énumération ayant
 * la notion d'appartenance à une raison plus générique<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class DisconnectionReason extends Bid4WinObjectType<DisconnectionReason>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 2377614529005618273L;

  /** Aucune connexion */
  public final static DisconnectionReason NONE = new DisconnectionReason("NONE");

  /** Fin de connexion pour raison normale */
  public final static DisconnectionReason NORMAL = new DisconnectionReason("NORMAL");
  /** Fin de connexion automatique à la fin de session */
  public final static DisconnectionReason AUTO = new DisconnectionReason("AUTO", NORMAL);
  /** Déconnexion manuelle de l'utilisateur */
  public final static DisconnectionReason MANUAL = new DisconnectionReason("MANUAL", NORMAL);
  /** Déconnexion due au changement de mot de passe sur une autre session */
  public final static DisconnectionReason PASSWORD = new DisconnectionReason("PASSWORD", NORMAL);

  /** Déconnexion pour raison technique */
  public final static DisconnectionReason TECHNICAL = new DisconnectionReason("TECHNICAL");
  /** Déconnexion pour utilisation d'une adresse IP différente */
  public final static DisconnectionReason IP = new DisconnectionReason("IP", TECHNICAL);

  /** Déconnexion pour raison de sécurité */
  public final static DisconnectionReason SECURITY = new DisconnectionReason("SECURITY");
  /** Déconnexion pour problème de rémanence invalide */
  public final static DisconnectionReason REMANENCE = new DisconnectionReason("REMANENCE", SECURITY);

  /**
   * Constructeur
   * @param code Code de la raison de fin de connexion
   */
  private DisconnectionReason(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de la raison de fin de connexion
   * @parent Parent de la raison de fin de connexion
   */
  private DisconnectionReason(String code, DisconnectionReason parent)
  {
    super(code, parent);
  }
}
