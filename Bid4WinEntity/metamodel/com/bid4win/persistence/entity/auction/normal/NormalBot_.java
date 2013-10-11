package com.bid4win.persistence.entity.auction.normal;

import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.auction.Bot_;

/**
 * Metamodel de la classe NormalBot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(NormalBot.class)
public class NormalBot_ extends Bot_
{
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    Bid4WinEntity_.stopProtection();
  }
}
