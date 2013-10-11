package trash;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Stub du DAO pour les entités de la classe Auction<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class AuctionDaoStub //extends AuctionDao
{
//  /////////////////////////////////////////////////////////////////////////////
//  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
//  ////////////  de la transaction et le chargement des relations   ////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   *
//   * TODO A COMMENTER
//   * @param id TODO A COMMENTER
//   * @return TODO A COMMENTER
//   * @throws PersistenceException TODO A COMMENTER
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.auction.AuctionDao#getById(java.lang.String)
//   */
//  @Override
//  public Auction getById(String id) throws PersistenceException, NotFoundEntityException
//  {
//    return super.getById(id).loadRelation();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.auction.AuctionDao#add(com.bid4win.commons.entity.auction.Auction)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Auction add(Auction auction) throws PersistenceException
//  {
//    return super.add(auction);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param auction {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.auction.AuctionDao#update(com.bid4win.commons.entity.auction.Auction)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Auction update(Auction auction) throws PersistenceException
//  {
//    return super.update(auction);
//  }
//  /////////////////////////////////////////////////////////////////////////////
//  ////////////////////////// Ajoutées pour les tests //////////////////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   * Cette fonction permet de supprimer une vente aux enchères en fonction de son
//   * identifiant
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao#removeById(java.lang.Object)
//   */
//  @Override
//  @Transactional(readOnly = false)
//  public Auction removeById(String id) throws PersistenceException
//  {
//    return super.removeById(id);
//  }
//
//  /**
//   * Cette fonction permet de supprimer la liste de toutes les ventes aux enchères
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao#removeAll()
//   */
//  @Override
//  @Transactional(readOnly = false)
//  public List<Auction> removeAll() throws PersistenceException
//  {
//    return super.removeAll();
//  }
}
