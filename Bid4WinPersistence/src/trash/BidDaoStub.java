package trash;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Stub du DAO pour les entit�s de la classe Bid<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
public class BidDaoStub //extends BidDao
{
//  /////////////////////////////////////////////////////////////////////////////
//  //////////// Red�finies pour �tre test�es en ajoutant la gestion ////////////
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
//  ////////////////////////// Ajout�es pour les tests //////////////////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   * Cette fonction permet de r�cup�rer l'unique ench�re en fonction de son identifiant
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
//   * Il est n�cessaire de d�-r�f�rencer le lot de droits � ench�rir avant de le supprimer
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
//   * Cette fonction permet de supprimer la liste de toutes les ench�res dont l'
//   * identifiant de la vente est pr�cis� en argument
//   * @param auctionId Identifiant de la vente des ench�res � supprimer
//   * @return La liste de toutes les ench�res supprim�es dont l'identifiant de la
//   * vente est pr�cis� en argument
//   * @throws PersistenceException Si une exception non attendue est lev�e
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public List<Bid> removeListByAuctionId(String auctionId) throws PersistenceException
//  {
//    return this.removeList(this.getCriteriaForAuctionId(auctionId));
//  }
//  /**
//   * Cette fonction permet de r�cup�rer la liste d'ench�res dont l'identifiant de
//   * la vente est pr�cis� en argument
//   * @param auctionId Identifiant de la vente des ench�res � rechercher
//   * @return La liste d'ench�res r�cup�r�es
//   * @throws PersistenceException Si une exception non attendue est lev�e
//   */
//  public List<Bid> findListByAuctionId(String auctionId) throws PersistenceException
//  {
//    List<Bid> list = super.findList(this.getCriteriaForAuctionId(auctionId));
//    return Bid4WinEntityLoader.getInstance().loadRelation(list);
//  }
//  /**
//   * Cette m�thode permet de construire les crit�res permettant de rechercher des
//   * ench�res dont l'identifiant de la vente est pr�cis� en argument
//   * @param auctionId Identifiant de la vente des ench�res � rechercher
//   * @return Les crit�res permettant de rechercher des ench�res en fonction de leur
//   * identifiant de vente li�e
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
//   * Cette fonction permet de r�cup�rer la liste d'ench�res dont la vente li�e
//   * correspondant � l'identifiant en argument
//   * @param auctionId Identifiant de la vente li�e � rechercher
//   * @return La liste d'ench�res r�cup�r�es
//   * @throws PersistenceException Si une exception non attendue est lev�e
//   */
//  public List<Bid> findListByAuctionLink(String auctionId) throws PersistenceException
//  {
//    List<Bid> list = super.findList(this.getCriteriaForAuctionLink(auctionId));
//    return Bid4WinEntityLoader.getInstance().loadRelation(list);
//  }
//  /**
//   * Cette m�thode permet de construire les crit�res permettant de rechercher des
//   * ench�res dont la vente li�e correspondant � l'identifiant en argument
//   * @param auctionId Identifiant de la vente li�e � rechercher
//   * @return Les crit�res permettant de rechercher des ench�res en fonction de leur
//   * vente li�e
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
//   * Cette fonction permet de supprimer la liste de toutes les ench�res non li�es
//   * @return La liste de toutes les ench�res non li�es supprim�es
//   * @throws PersistenceException Si une exception non attendue est lev�e
//   */
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public List<Bid> removeListByNullAuctionLink() throws PersistenceException
//  {
//    return this.removeList(this.getCriteriaForNullAuctionLink());
//  }
//  /**
//   * Cette fonction permet de r�cup�rer la liste d'ench�res dont la vente li�e est
//   * nulle
//   * @return La liste d'ench�res r�cup�r�es
//   * @throws PersistenceException Si une exception non attendue est lev�e
//   */
//  public List<Bid> findListByNullAuctionLink() throws PersistenceException
//  {
//    List<Bid> list = super.findList(this.getCriteriaForNullAuctionLink());
//    return Bid4WinEntityLoader.getInstance().loadRelation(list);
//  }
//  /**
//   * Cette m�thode permet de construire les crit�res permettant de rechercher des
//   * ench�res dont la vente li�e est nulle
//   * @return Les crit�res permettant de rechercher des ench�res dont la vente li�e
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
