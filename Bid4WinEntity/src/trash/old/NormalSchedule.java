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
// * @author Emeric Fillâtre
// */
//@Embeddable
//@Access(AccessType.FIELD)
//public class NormalSchedule extends Schedule<NormalSchedule>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected NormalSchedule()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture prévue d'une vente aux enchères normale
//   * @param initialCountdown Compte à rebours initial de fermeture d'une vente aux
//   * enchères normale en secondes
//   * @param aditionalCountdown Compte à rebours additionnel de fermeture d'une
//   * vente aux enchères normale en secondes
//   * @throws ModelArgumentException Si la date d'ouverture prévue est nulle ou si
//   * le compte à rebours initial ou additionnel est inférieur à un
//   */
//  public NormalSchedule(Bid4WinDate openingDate, int initialCountdown, int aditionalCountdown)
//         throws ModelArgumentException
//  {
//    super(openingDate, initialCountdown, aditionalCountdown);
//  }
//}
