//package com.bid4win.persistence.dao.locale.html;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.collection.Bid4WinList;
//import com.bid4win.commons.core.collection.Bid4WinMap;
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
//import com.bid4win.commons.persistence.dao.resource.IResourceDaoStub;
//import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
//import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;
//import com.bid4win.persistence.entity.locale.html.HtmlPageType;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Component("HtmlPageStorageDaoStub")
//@Scope("singleton")
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public class HtmlPageStorageDaoStub extends HtmlPageStorageDao
//       implements IResourceDaoStub<HtmlPageStorage, HtmlPageType>
//{
//  /////////////////////////////////////////////////////////////////////////////
//  //////////// Red�finies pour �tre test�es en ajoutant la gestion ////////////
//  ////////////  de la transaction et le chargement des relations   ////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   *
//   * TODO A COMMENTER
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#getById(java.lang.Long)
//   */
//  @Override
//  public HtmlPageStorage getById(Long id) throws PersistenceException
//  {
//    return super.getById(id).loadRelation();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param fullPath {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#getOneByFullPath(java.lang.String)
//   */
//  @Override
//  public HtmlPageStorage getOneByFullPath(String fullPath) throws PersistenceException, UserException
//  {
//    return super.getOneByFullPath(fullPath).loadRelation();
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param fullPath {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#findOneByFullPath(java.lang.String)
//   */
//  @Override
//  public HtmlPageStorage findOneByFullPath(String fullPath) throws PersistenceException, UserException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findOneByFullPath(fullPath));
//  }
//  /**
//   * Cette fonction permet de r�cup�rer la liste compl�te des entit�s
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#findAll()
//   */
//  @Override
//  public Bid4WinList<HtmlPageStorage> findAll() throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findAll());
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param storage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#add(com.bid4win.commons.persistence.entity.resource.Resource)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public HtmlPageStorage add(HtmlPageStorage storage) throws PersistenceException
//  {
//    return super.add(storage);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param storage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#update(com.bid4win.commons.persistence.entity.resource.Resource)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public HtmlPageStorage update(HtmlPageStorage storage) throws PersistenceException
//  {
//    return super.update(storage);
//  }
//  /**
//   *
//   * TODO A COMMENTER
//   * @param storage {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.resource.ResourceDao_#remove(com.bid4win.commons.persistence.entity.resource.Resource)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public HtmlPageStorage remove(HtmlPageStorage storage) throws PersistenceException
//  {
//    return super.remove(storage);
//  }
//  /////////////////////////////////////////////////////////////////////////////
//  ////////////////////////// Ajout�es pour les tests //////////////////////////
//  /////////////////////////////////////////////////////////////////////////////
//  /**
//   * Cette fonction permet de r�cup�rer l'unique entit� potentiellement nulle en
//   * fonction de son identifiant
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findById(java.lang.Object)
//   */
//  @Override
//  public HtmlPageStorage findById(Long id) throws PersistenceException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.findById(id));
//  }
//  /**
//   * Cette fonction permet de r�cup�rer le set d'entit�s dont les identifiants
//   * sont pr�cis�s en argument
//   * @param idSet {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(com.bid4win.commons.core.collection.Bid4WinSet)
//   */
//  @Override
//  public Bid4WinMap<Long, HtmlPageStorage> getById(Bid4WinSet<Long> idSet)
//         throws PersistenceException, NotFoundEntityException
//  {
//    return Bid4WinEntityLoader.getInstance().loadRelation(super.getById(idSet));
//  }
//  /**
//   * Cette fonction permet de supprimer une entit� en fonction de son identifiant
//   * @param id {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeById(java.lang.Object)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public HtmlPageStorage removeById(Long id) throws PersistenceException, NotFoundEntityException
//  {
//    return super.removeById(id);
//  }
//  /**
//   * Cette fonction permet de supprimer le set d'entit�s dont les identifiants
//   * sont pr�cis�s en argument
//   * @param idSet {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @throws NotFoundEntityException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeListById(com.bid4win.commons.core.collection.Bid4WinSet)
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid4WinSet<HtmlPageStorage> removeListById(Bid4WinSet<Long> idSet)
//         throws PersistenceException, NotFoundEntityException
//  {
//    return super.removeListById(idSet);
//  }
//  /**
//   * Cette fonction permet de supprimer la liste de toutes les propri�t�s
//   * @return {@inheritDoc}
//   * @throws PersistenceException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#removeAll()
//   */
//  @Override
//  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
//  public Bid4WinList<HtmlPageStorage> removeAll() throws PersistenceException
//  {
//    return super.removeAll();
//  }
//}
