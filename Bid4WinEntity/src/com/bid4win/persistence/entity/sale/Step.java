package com.bid4win.persistence.entity.sale;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;

/**
 * Cette classe défini l'étape d'une vente<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Step extends Bid4WinObjectType<Step>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -7737281385355779543L;

  /** Étape d'une vente en construction */
  public final static Step PROCESSING = new Step("PROCESSING");
  /** Étape d'une vente acceptée */
  public final static Step OK = new Step("OK");
  /** Étape d'une vente payée */
  public final static Step PAID = new Step("PAID", OK);
  /** Étape d'une vente expédiée */
  public final static Step SHIPPED = new Step("SHIPPED", OK);
  /** Étape d'une vente terminée */
  public final static Step DONE = new Step("DONE", OK);
  /** Étape d'une vente refusée */
  public final static Step KO = new Step("KO");
  /** Étape d'une vente annulée */
  public final static Step CANCELLED = new Step("CANCELLED", KO);
  /** Étape d'une vente rejetée */
  public final static Step REJECTED = new Step("REJECTED", KO);

  /**
   * Cette méthode permet de récupérer toutes les étapes de vente
   * @return Le set complet de toutes les étapes de vente définies
   */
  public static Bid4WinSet<Step> getStepSet()
  {
    return Bid4WinObjectType.getTypeSet(Step.class);
  }

  /**
   * Constructeur
   * @param code Code de l'étape de vente
   */
  private Step(String code)
  {
    super(code);
  }
  /**
   * Constructeur
   * @param code Code de l'étape de vente
   * @param parent Parent de l'étape de vente
   */
  private Step(String code, Step parent)
  {
    super(code, parent);
  }
}
