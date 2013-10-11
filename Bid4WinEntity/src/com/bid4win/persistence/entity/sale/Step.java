package com.bid4win.persistence.entity.sale;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;

/**
 * Cette classe d�fini l'�tape d'une vente<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Step extends Bid4WinObjectType<Step>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = -7737281385355779543L;

  /** �tape d'une vente en construction */
  public final static Step PROCESSING = new Step("PROCESSING");
  /** �tape d'une vente accept�e */
  public final static Step OK = new Step("OK");
  /** �tape d'une vente pay�e */
  public final static Step PAID = new Step("PAID", OK);
  /** �tape d'une vente exp�di�e */
  public final static Step SHIPPED = new Step("SHIPPED", OK);
  /** �tape d'une vente termin�e */
  public final static Step DONE = new Step("DONE", OK);
  /** �tape d'une vente refus�e */
  public final static Step KO = new Step("KO");
  /** �tape d'une vente annul�e */
  public final static Step CANCELLED = new Step("CANCELLED", KO);
  /** �tape d'une vente rejet�e */
  public final static Step REJECTED = new Step("REJECTED", KO);

  /**
   * Cette m�thode permet de r�cup�rer toutes les �tapes de vente
   * @return Le set complet de toutes les �tapes de vente d�finies
   */
  public static Bid4WinSet<Step> getStepSet()
  {
    return Bid4WinObjectType.getTypeSet(Step.class);
  }

  /**
   * Constructeur
   * @param code Code de l'�tape de vente
   */
  private Step(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de l'�tape de vente
   * @param parent Parent de l'�tape de vente
   */
  private Step(String code, Step parent)
  {
    super(code, parent);
  }
}
