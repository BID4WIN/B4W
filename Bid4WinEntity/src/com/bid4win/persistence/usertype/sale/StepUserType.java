package com.bid4win.persistence.usertype.sale;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.sale.Step;

/**
 * Cette classe défini le 'type utilisateur' gérant les étapes d'une vente en base
 * de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class StepUserType extends Bid4WinObjectTypeUserType<Step>
{
  /**
   * Constructeur
   */
  public StepUserType()
  {
    super(Step.class);
  }
}
