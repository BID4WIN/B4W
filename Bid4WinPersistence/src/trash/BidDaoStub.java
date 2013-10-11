package trash;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Stub du DAO pour les entités de la classe Bid<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class BidDaoStub //extends BidDao
{
//  /////////////////////////////////////////////////////////////////////////////
//  //////////// Redéfinies pour être testées en ajoutant la gestion ////////////
//  ////////////  de la transaction et le chargement des relations   ////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   * 
//   * TODO A COMMENTER
//   * @param bid {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.auction.BidDao#add(com.bid4win.commons.entity.auction.Bid)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid add(Bid bid) throws PersistenceException
//  {
//    return super.add(bid);
//  }
//  /**
//   * 
//   * TODO A COMMENTER
//   * @param bid {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.auction.BidDao#update(com.bid4win.commons.entity.auction.Bid)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid update(Bid bid) throws PersistenceException
//  {
//    return super.update(bid);
//  }
//  /////////////////////////////////////////////////////////////////////////////
//  ////////////////////////// Ajoutées pour les tests //////////////////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   * Cette fonction permet de récupérer l'unique enchère en fonction de son identifiant
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao#getById(java.lang.Object)
//   */
//  @Override
//  public Bid getById(Long id) throws PersistenceException
//  {
//    return super.getById(id);
//  }
//  /**
//   * Il est nécessaire de dé-référencer le lot de droits à enchérir avant de le supprimer
//   * @param bid {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao#remove(com.bid4win.commons.entity.Bid4WinEntity)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid remove(Bid bid) throws PersistenceException
//  {
//    if(bid.isLinkedToAuction())
//    {
//      try
//      {
//        this.getAuctionDao().update(bid.unlinkFromAuction());
//      }
//      catch(ModelArgumentException ex)
//      {
//        throw new PersistenceException(ex);
//      }
//    }
//    return super.remove(bid);
//  }
//  
//  /**
//   * Cette fonction permet de supprimer la liste de toutes les enchères dont l'
//   * identifiant de la vente est précisé en argument
//   * @param auctionId Identifiant de la vente des enchères à supprimer
//   * @return La liste de toutes les enchères supprimées dont l'identifiant de la
//   * vente est précisé en argument
//   * @throws PersistenceException Si une exception non attendue est levée
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public List<Bid> removeListByAuctionId(String auctionId) throws PersistenceException
//  {
//    return this.removeList(this.getCriteriaForAuctionId(auctionId));
//  }
//  /**
//   * Cette fonction permet de récupérer la liste d'enchères dont l'identifiant de
//   * la vente est précisé en argument
//   * @param auctionId Identifiant de la vente des enchères à rechercher
//   * @return La liste d'enchères récupérées
//   * @throws PersistenceException Si une exception non attendue est levée
//   */
//  public List<Bid> findListByAuctionId(String auctionId) throws PersistenceException
//  {
//    List<Bid> list = super.findList(this.getCriteriaForAuctionId(auctionId));
//    return Bid4WinEntityLoader.getInstance().loadRelation(list);
//  }
//  /**
//   * Cette méthode permet de construire les critères permettant de rechercher des
//   * enchères dont l'identifiant de la vente est précisé en argument
//   * @param auctionId Identifiant de la vente des enchères à rechercher
//   * @return Les critères permettant de rechercher des enchères en fonction de leur
//   * identifiant de vente liée
//   */
//  protected CriteriaQuery<Bid> getCriteriaForAuctionId(String auctionId)
//  {
//    CriteriaQuery<Bid> criteria = this.createCriteria();
//    Root<Bid> root = criteria.from(this.getEntityClass());
//    CriteriaBuilder builder = this.getCriteriaBuilder();
//    Predicate condition = builder.equal(root.get(Bid_.auctionId), auctionId);
//    criteria.where(condition);
//    return criteria;
//  }
//
//  /**
//   * Cette fonction permet de récupérer la liste d'enchères dont la vente liée
//   * correspondant à l'identifiant en argument
//   * @param auctionId Identifiant de la vente liée à rechercher
//   * @return La liste d'enchères récupérées
//   * @throws PersistenceException Si une exception non attendue est levée
//   */
//  public List<Bid> findListByAuctionLink(String auctionId) throws PersistenceException
//  {
//    List<Bid> list = super.findList(this.getCriteriaForAuctionLink(auctionId));
//    return Bid4WinEntityLoader.getInstance().loadRelation(list);
//  }
//  /**
//   * Cette méthode permet de construire les critères permettant de rechercher des
//   * enchères dont la vente liée correspondant à l'identifiant en argument
//   * @param auctionId Identifiant de la vente liée à rechercher
//   * @return Les critères permettant de rechercher des enchères en fonction de leur
//   * vente liée
//   */
//  protected CriteriaQuery<Bid> getCriteriaForAuctionLink(String auctionId)
//  {
//    CriteriaQuery<Bid> criteria = this.createCriteria();
//    Root<Bid> root = criteria.from(this.getEntityClass());
//    CriteriaBuilder builder = this.getCriteriaBuilder();
//    Predicate condition = builder.equal(
//        root.get(Bid_.auctionLink).get(Auction_.id), auctionId);
//    criteria.where(condition);
//    return criteria;
//  }
//
//  /**
//   * Cette fonction permet de supprimer la liste de toutes les enchères non liées
//   * @return La liste de toutes les enchères non liées supprimées
//   * @throws PersistenceException Si une exception non attendue est levée
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public List<Bid> removeListByNullAuctionLink() throws PersistenceException
//  {
//    return this.removeList(this.getCriteriaForNullAuctionLink());
//  }
//  /**
//   * Cette fonction permet de récupérer la liste d'enchères dont la vente liée est
//   * nulle
//   * @return La liste d'enchères récupérées
//   * @throws PersistenceException Si une exception non attendue est levée
//   */
//  public List<Bid> findListByNullAuctionLink() throws PersistenceException
//  {
//    List<Bid> list = super.findList(this.getCriteriaForNullAuctionLink());
//    return Bid4WinEntityLoader.getInstance().loadRelation(list);
//  }
//  /**
//   * Cette méthode permet de construire les critères permettant de rechercher des
//   * enchères dont la vente liée est nulle
//   * @return Les critères permettant de rechercher des enchères dont la vente liée
//   * est nulle
//   */
//  protected CriteriaQuery<Bid> getCriteriaForNullAuctionLink()
//  {
//    CriteriaQuery<Bid> criteria = this.createCriteria();
//    Root<Bid> root = criteria.from(this.getEntityClass());
//    CriteriaBuilder builder = this.getCriteriaBuilder();
//    Predicate condition = builder.isNull(root.get(Bid_.auctionLink));
//    criteria.where(condition);
//    return criteria;
//  }
}
