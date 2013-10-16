package com.bid4win.commons.persistence.request;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe permet de d�finir comment construire la condition correspondant
 * aux crit�res courants sur l'entit� donn�e<BR>
 * <BR>
 * @param <ENTITY> Entit� pour laquelle la classe d�fini des crit�res<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinCriteria<ENTITY extends Bid4WinEntity<?, ?>>
{
  /** Boolean indiquant si les crit�res sont exhaustifs (AND) ou non (OR) */
  private boolean exhaustive = false;

  /**
   * Constructeur dont les crit�res ne sont pas exhaustifs
   */
  public Bid4WinCriteria()
  {
    super();
  }
  /**
   * Constructeur
   * @param exhaustive Boolean indiquant si les crit�res sont exhaustifs (AND) ou
   * non (OR)
   */
  public Bid4WinCriteria(boolean exhaustive)
  {
    this.setExhaustive(exhaustive);
  }

  /**
   * Cette m�thode doit �tre impl�ment�e afin de construire la condition correspondant
   * aux crit�res courants
   * @param builder Builder � utiliser pour construire la condition
   * @param from_ D�finition du chemin d'acc�s � l'entit� sur laquelle portera la
   * condition
   * @return La condition correspondant aux crit�res courants � appliquer sur la
   * d�finition d'entit� en param�tre
   */
  protected abstract Predicate buildCondition(CriteriaBuilder builder, From<?, ? extends ENTITY> from_);

  /**
   * Cette m�thode est celle qui est utilis�e afin de combiner les conditions en
   * param�tre entre elle. Elle d�pend de la valeur d'exhaustivit� positionn�e
   * et correspondra � un "ou inclusive" (resp. "et") si elle vaut false (resp. true)
   * @param builder Builder � utiliser pour construire la condition
   * @param conditions Conditions � combiner
   * @return La condition globale combinant toutes celle d�finies en argument
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
   * Getter du boolean indiquant si les crit�res sont exhaustifs (AND) ou non (OR)
   * @return Le boolean indiquant si les crit�res sont exhaustifs (AND) ou non (OR)
   */
  public boolean isExhaustive()
  {
    return this.exhaustive;
  }
  /**
   * Setter du boolean indiquant si les crit�res sont exhaustifs (AND) ou non (OR)
   * @param exhaustive Boolean � positionner indiquant si les crit�res sont exhaustifs
   * (AND) ou non (OR)
   */
  private void setExhaustive(boolean exhaustive)
  {
    this.exhaustive = exhaustive;
  }
}
