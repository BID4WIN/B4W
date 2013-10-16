package com.bid4win.commons.persistence.request;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe permet de définir comment construire la condition correspondant
 * aux critères courants sur l'entité donnée<BR>
 * <BR>
 * @param <ENTITY> Entité pour laquelle la classe défini des critères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinCriteria<ENTITY extends Bid4WinEntity<?, ?>>
{
  /** Boolean indiquant si les critères sont exhaustifs (AND) ou non (OR) */
  private boolean exhaustive = false;

  /**
   * Constructeur dont les critères ne sont pas exhaustifs
   */
  public Bid4WinCriteria()
  {
    super();
  }
  /**
   * Constructeur
   * @param exhaustive Boolean indiquant si les critères sont exhaustifs (AND) ou
   * non (OR)
   */
  public Bid4WinCriteria(boolean exhaustive)
  {
    this.setExhaustive(exhaustive);
  }

  /**
   * Cette méthode doit être implémentée afin de construire la condition correspondant
   * aux critères courants
   * @param builder Builder à utiliser pour construire la condition
   * @param from_ Définition du chemin d'accès à l'entité sur laquelle portera la
   * condition
   * @return La condition correspondant aux critères courants à appliquer sur la
   * définition d'entité en paramètre
   */
  protected abstract Predicate buildCondition(CriteriaBuilder builder, From<?, ? extends ENTITY> from_);

  /**
   * Cette méthode est celle qui est utilisée afin de combiner les conditions en
   * paramètre entre elle. Elle dépend de la valeur d'exhaustivité positionnée
   * et correspondra à un "ou inclusive" (resp. "et") si elle vaut false (resp. true)
   * @param builder Builder à utiliser pour construire la condition
   * @param conditions Conditions à combiner
   * @return La condition globale combinant toutes celle définies en argument
   */
  protected Predicate combineConditions(CriteriaBuilder builder, Predicate ... conditions)
  {
    if(conditions.length == 0)
    {
      return null;
    }
    if(this.isExhaustive())
    {
      return builder.and(conditions);
    }
    return builder.or(conditions);
  }

  /**
   *
   * TODO A COMMENTER
   * @param from_ TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract Expression<?>[] getGroups(From<?, ? extends ENTITY> from_);

  /**
   * Getter du boolean indiquant si les critères sont exhaustifs (AND) ou non (OR)
   * @return Le boolean indiquant si les critères sont exhaustifs (AND) ou non (OR)
   */
  public boolean isExhaustive()
  {
    return this.exhaustive;
  }
  /**
   * Setter du boolean indiquant si les critères sont exhaustifs (AND) ou non (OR)
   * @param exhaustive Boolean à positionner indiquant si les critères sont exhaustifs
   * (AND) ou non (OR)
   */
  private void setExhaustive(boolean exhaustive)
  {
    this.exhaustive = exhaustive;
  }
}
