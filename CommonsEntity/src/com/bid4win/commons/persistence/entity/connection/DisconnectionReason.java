package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;

/**
 * Cette classe d�fini une raison de d�connexion comparable � une �num�ration ayant
 * la notion d'appartenance � une raison plus g�n�rique<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class DisconnectionReason extends Bid4WinObjectType<DisconnectionReason>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 2377614529005618273L;

  /** Aucune connexion */
  public final static DisconnectionReason NONE = new DisconnectionReason("NONE");

  /** Fin de connexion pour raison normale */
  public final static DisconnectionReason NORMAL = new DisconnectionReason("NORMAL");
  /** Fin de connexion automatique � la fin de session */
  public final static DisconnectionReason AUTO = new DisconnectionReason("AUTO", NORMAL);
  /** D�connexion manuelle de l'utilisateur */
  public final static DisconnectionReason MANUAL = new DisconnectionReason("MANUAL", NORMAL);
  /** D�connexion due au changement de mot de passe sur une autre session */
  public final static DisconnectionReason PASSWORD = new DisconnectionReason("PASSWORD", NORMAL);

  /** D�connexion pour raison technique */
  public final static DisconnectionReason TECHNICAL = new DisconnectionReason("TECHNICAL");
  /** D�connexion pour utilisation d'une adresse IP diff�rente */
  public final static DisconnectionReason IP = new DisconnectionReason("IP", TECHNICAL);

  /** D�connexion pour raison de s�curit� */
  public final static DisconnectionReason SECURITY = new DisconnectionReason("SECURITY");
  /** D�connexion pour probl�me de r�manence invalide */
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
