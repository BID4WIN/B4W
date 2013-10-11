package trash.old;
//package com.bid4win.persistence.entity.account;
//
//import javax.persistence.metamodel.MapAttribute;
//import javax.persistence.metamodel.SingularAttribute;
//import javax.persistence.metamodel.StaticMetamodel;
//
//import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
//import com.bid4win.commons.persistence.entity.account.AccountBasedEntityMultipleAutoID_;
//
///**
// * Metamodel de la classe CreditUsage<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
//@StaticMetamodel(CreditUsage.class)
//public abstract class CreditUsage_ extends AccountBasedEntityMultipleAutoID_
//{
//  /** Définition de la map des utilisations de lots de crédits desquels sont issus les crédits utilisés */
//  @SuppressWarnings("rawtypes")
//  public static volatile MapAttribute<CreditUsage, Long, CreditBundleUsage> bundleUsageMapInternal;
//  /** Définition du nombre de crédits utilisés */
//  @SuppressWarnings("rawtypes")
//  public static volatile SingularAttribute<CreditUsage, Integer> usedNb;
//
//  // Définition de la profondeur des relations
//  static
//  {
//    Bid4WinEntity_.startProtection();
//    /** Défini la profondeur du noeud de relation existant avec les utilisation de lots de crédits */
//    CreditUsage_Relations.NODE_BUNDLE_USAGE_MAP.addNode(CreditBundleUsage_Relations.NODE_CREDIT_USAGE);
//    Bid4WinEntity_.stopProtection();
//  }
//}
