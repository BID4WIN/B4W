package com.bid4win.persistence.usertype.sale;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.sale.Step;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les �tapes d'une vente en base
 * de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
