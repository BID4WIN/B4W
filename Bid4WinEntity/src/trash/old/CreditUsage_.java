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
// * @author Emeric Fill�tre
// */
//@StaticMetamodel(CreditUsage.class)
//public abstract class CreditUsage_ extends AccountBasedEntityMultipleAutoID_
//{
//  /** D�finition de la map des utilisations de lots de cr�dits desquels sont issus les cr�dits utilis�s */
//  @SuppressWarnings("rawtypes")
//  public static volatile MapAttribute<CreditUsage, Long, CreditBundleUsage> bundleUsageMapInternal;
//  /** D�finition du nombre de cr�dits utilis�s */
//  @SuppressWarnings("rawtypes")
//  public static volatile SingularAttribute<CreditUsage, Integer> usedNb;
//
//  // D�finition de la profondeur des relations
//  static
//  {
//    Bid4WinEntity_.startProtection();
//    /** D�fini la profondeur du noeud de relation existant avec les utilisation de lots de cr�dits */
//    CreditUsage_Relations.NODE_BUNDLE_USAGE_MAP.addNode(CreditBundleUsage_Relations.NODE_CREDIT_USAGE);
//    Bid4WinEntity_.stopProtection();
//  }
//}
