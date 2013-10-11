package com.bid4win.persistence.dao.auction.normal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.persistence.dao.auction.BotDao_;
import com.bid4win.persistence.entity.auction.normal.NormalAuction;
import com.bid4win.persistence.entity.auction.normal.NormalBid;
import com.bid4win.persistence.entity.auction.normal.NormalBot;

/**
 * DAO pour les entités de la classe NormalBot<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalBotDao")
@Scope("singleton")
public class NormalBotDao extends BotDao_<NormalBot, NormalAuction, NormalBid>
{
  /**
   * Constructeur
   */
  protected NormalBotDao()
  {
    super(NormalBot.class);
  }
}
