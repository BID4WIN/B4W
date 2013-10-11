package com.bid4win.commons.persistence.entity;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntityStub;

/**
 * Metamodel de la classe Bid4WinEntityStub<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(Bid4WinEntityStub.class)
public class Bid4WinEntityStub_ extends Bid4WinEntity_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntityStub_Relations.NODE_SON.addNode(Bid4WinEntityStub_Relations.NODE_PARENT);
    Bid4WinEntityStub_Relations.NODE_SON.addNode(Bid4WinEntityStub_Relations.NODE_SON);
    Bid4WinEntityStub_Relations.NODE_SON.addNode(Bid4WinEntityStub_Relations.NODE_BOSS);
    Bid4WinEntityStub_Relations.NODE_SON.addNode(Bid4WinEntityStub_Relations.NODE_EMPLOYE);
    Bid4WinEntityStub_Relations.NODE_PARENT.addNode(Bid4WinEntityStub_Relations.NODE_PARENT);
    Bid4WinEntityStub_Relations.NODE_PARENT.addNode(Bid4WinEntityStub_Relations.NODE_SON);
    Bid4WinEntityStub_Relations.NODE_PARENT.addNode(Bid4WinEntityStub_Relations.NODE_BOSS);
    Bid4WinEntityStub_Relations.NODE_PARENT.addNode(Bid4WinEntityStub_Relations.NODE_EMPLOYE);
    Bid4WinEntityStub_Relations.NODE_EMPLOYE.addNode(Bid4WinEntityStub_Relations.NODE_PARENT);
    Bid4WinEntityStub_Relations.NODE_EMPLOYE.addNode(Bid4WinEntityStub_Relations.NODE_SON);
    Bid4WinEntityStub_Relations.NODE_EMPLOYE.addNode(Bid4WinEntityStub_Relations.NODE_BOSS);
    Bid4WinEntityStub_Relations.NODE_EMPLOYE.addNode(Bid4WinEntityStub_Relations.NODE_EMPLOYE);
    Bid4WinEntityStub_Relations.NODE_BOSS.addNode(Bid4WinEntityStub_Relations.NODE_PARENT);
    Bid4WinEntityStub_Relations.NODE_BOSS.addNode(Bid4WinEntityStub_Relations.NODE_SON);
    Bid4WinEntityStub_Relations.NODE_BOSS.addNode(Bid4WinEntityStub_Relations.NODE_BOSS);
    Bid4WinEntityStub_Relations.NODE_BOSS.addNode(Bid4WinEntityStub_Relations.NODE_EMPLOYE);
    Bid4WinEntity_.stopProtection();
  }
  
  /**
   * 
   * TODO A COMMENTER
   */
  public static void loadRelations()
  {
    // TODO A COMMENTER
  }
}
