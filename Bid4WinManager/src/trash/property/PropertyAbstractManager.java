package trash.property;
//package com.bid4win.manager.property.abstraction;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.entity.property.abstraction.PropertyAbstract;
//import com.bid4win.commons.entity.property.abstraction.PropertyRootAbstract;
//import com.bid4win.manager.Bid4WinManager;
//
///**
// * 
// * TODO A COMMENTER<BR>
// * <BR>
// * @param <PROPERTY> TODO A COMMENTER
// * @param <PROPERTY_ROOT> TODO A COMMENTER
// * @param <INTERNAL> TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//public abstract class PropertyAbstractManager<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
//                                              PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
//                                              INTERNAL extends PropertyAbstractManagerInternal<PROPERTY, PROPERTY_ROOT, ?, ?>>
//       extends Bid4WinManager<PROPERTY, INTERNAL>
//{
//  /**
//   * Cette m�thode permet de r�cup�rer l'unique propri�t� racine
//   * @return L'unique propri�t� racine
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   */
//  @Transactional(readOnly = false)
//  public PROPERTY_ROOT getRoot() throws PersistenceException
//  {
//    // R�cup�re la propri�t� racine
//    PROPERTY_ROOT root = this.getInternal().getRoot();
//    // Charge toutes les sous-propri�t�s et retourne la propri�t� racine
//    return root.loadRelation();
//  }
//
//}
