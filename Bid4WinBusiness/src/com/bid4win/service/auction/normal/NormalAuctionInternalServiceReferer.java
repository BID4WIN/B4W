package com.bid4win.service.auction.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.j2ee.Bid4WinSelfReferer;

/***
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("NormalAuctionInternalServiceReferer")
@Scope("singleton")
public class NormalAuctionInternalServiceReferer extends Bid4WinSelfReferer<NormalAuctionInternalService>
{
  /**
   *
   * TODO A COMMENTER
   * @param bean {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferer#setBean(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  @Autowired
  @Qualifier("NormalAuctionInternalService")
  public void setBean(NormalAuctionInternalService bean)
  {
    super.setBean(bean);
  }
}
