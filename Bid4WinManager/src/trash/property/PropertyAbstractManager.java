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
// * @author Emeric Fillâtre
// */
//public abstract class PropertyAbstractManager<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
//                                              PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
//                                              INTERNAL extends PropertyAbstractManagerInternal<PROPERTY, PROPERTY_ROOT, ?, ?>>
//       extends Bid4WinManager<PROPERTY, INTERNAL>
//{
//  /**
//   * Cette méthode permet de récupérer l'unique propriété racine
//   * @return L'unique propriété racine
//   * @throws RuntimeException Si un problème intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un problème intervient lors de la manipulation
//   * de la couche persistante
//   */
//  @Transactional(readOnly = false)
//  public PROPERTY_ROOT getRoot() throws PersistenceException
//  {
//    // Récupère la propriété racine
//    PROPERTY_ROOT root = this.getInternal().getRoot();
//    // Charge toutes les sous-propriétés et retourne la propriété racine
//    return root.loadRelation();
//  }
//
//}
