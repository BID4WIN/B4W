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
//public class NormalSchedule extends Schedule<NormalSchedule>
//{
//  /**
//   * Constructeur pour cr�ation par introspection
//   */
//  protected NormalSchedule()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param openingDate Date d'ouverture pr�vue d'une vente aux ench�res normale
//   * @param initialCountdown Compte � rebours initial de fermeture d'une vente aux
//   * ench�res normale en secondes
//   * @param aditionalCountdown Compte � rebours additionnel de fermeture d'une
//   * vente aux ench�res normale en secondes
//   * @throws ModelArgumentException Si la date d'ouverture pr�vue est nulle ou si
//   * le compte � rebours initial ou additionnel est inf�rieur � un
//   */
//  public NormalSchedule(Bid4WinDate openingDate, int initialCountdown, int aditionalCountdown)
//         throws ModelArgumentException
//  {
//    super(openingDate, initialCountdown, aditionalCountdown);
//  }
//}
