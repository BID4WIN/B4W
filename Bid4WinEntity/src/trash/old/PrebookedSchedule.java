package trash.old;
//package com.bid4win.persistence.entity.auction.old;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Embeddable;
//
//import com.bid4win.commons.core.Bid4WinDate;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.persistence.entity.auction.Schedule;
//
///**
// *
// * TODO A COMMENTER<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class PrebookedSchedule extends Schedule<PrebookedSchedule>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected PrebookedSchedule()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture pr�vue d'une vente aux ench�res avec pr�-
//   * inscription
//   * @param initialCountdown Compte � rebours initial de fermeture d'une vente aux
//   * ench�res avec pr�-inscription en secondes
//   * @param aditionalCountdown Compte � rebours additionnel de fermeture d'une
//   * vente aux ench�res avec pr�-inscription en secondes
//   * @throws ModelArgumentException Si la date d'ouverture pr�vue est nulle ou si
//   * le compte � rebours initial ou additionnel est inf�rieur � un
//   */
//  public PrebookedSchedule(Bid4WinDate openingDate, int initialCountdown, int aditionalCountdown)
//         throws ModelArgumentException
//  {
//    super(openingDate, initialCountdown, aditionalCountdown);
//  }
//}
