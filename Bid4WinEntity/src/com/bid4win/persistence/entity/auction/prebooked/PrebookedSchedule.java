package com.bid4win.persistence.entity.auction.prebooked;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.auction.Schedule;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class PrebookedSchedule extends Schedule<PrebookedSchedule>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected PrebookedSchedule()
  {
    super();
  }
  /**
   * Constructeur
   * @param startDate Date de d�marrage pr�vue d'une vente aux ench�res avec pr�-
   * inscription
   * @param initialCountdown Compte � rebours initial de fermeture d'une vente aux
   * ench�res avec pr�-inscription en secondes
   * @param additionalCountdown Compte � rebours additionnel de fermeture d'une
   * vente aux ench�res avec pr�-inscription en secondes
   * @throws UserException Si la date d'ouverture pr�vue est nulle ou si le compte
   * � rebours initial ou additionnel est inf�rieur � un
   */
  public PrebookedSchedule(Bid4WinDate startDate, int initialCountdown, int additionalCountdown)
         throws UserException
  {
    super(startDate, initialCountdown, additionalCountdown);
  }
}
