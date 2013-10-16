package com.bid4win.commons.persistence.request;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;

/**
 * Cette classe défini un ordonnancement potentiel de résultat de requête<BR>
 * <BR>
 * @param <ENTITY> Entité pour laquelle la classe défini un ordonnancement<BR>
 * @param <FIELD> Type du champ concerné par l'ordonnancement<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinOrder<ENTITY extends Bid4WinEntity<?, ?>,
                          FIELD extends Comparable<?  super FIELD>>
{
  /** Définition du champ concerné par l'ordonnancement */
  private Bid4WinField<? super ENTITY, ?, FIELD, ?> field = null;
  /** Sens de l'ordonnancement */
  private boolean ascendant = false;

  /**
   * Constructeur d'un ordonnancement croissant
   * @param field Définition du champ concerné par l'ordonnancement
   */
  public Bid4WinOrder(Bid4WinField<? super ENTITY, ?, FIELD, ?> field)
  {
    this(field, true);
  }
  /**
   * Constructeur
   * @param field Définition du champ concerné par l'ordonnancement
   * @param ascendant Défini un ordonnancement croissant si à true, décroissant sinon
   */
  public Bid4WinOrder(Bid4WinField<? super ENTITY, ?, FIELD, ?> field, boolean ascendant)
  {
    this.setField(field);
    this.setAscendant(ascendant);
  }

  /**
   * Cette méthode permet de construire l'ordonnancement à utiliser sur la définition
   * d'entité en paramètre
   * @param builder Builder à utiliser pour construire l'ordonnancement
   * @param from_ Définition d'entité pour laquelle construire l'ordonnancement
   * @return L'ordonnancement à utiliser sur la définition d'entité en paramètre
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Order buildOrder(CriteriaBuilder builder, From<?, ? extends ENTITY> from_)
  {
    Bid4WinField field = this.getField();
    Path<FIELD> field_ = field.getPath(from_);
    if(this.isAscendant())
    {
      return builder.asc(field_);
    }
    return builder.desc(field_);
  }

  /**
   * Getter de la définition du champ concerné par l'ordonnancement
   * @return La définition du champ concerné par l'ordonnancement
   */
  public Bid4WinField<? super ENTITY, ?, FIELD, ?> getField()
  {
    return this.field;
  }
  /**
   * Setter de la définition du champ concerné par l'ordonnancement
   * @param field Définition du champ concerné par l'ordonnancement à positionner
   */
  private void setField(Bid4WinField<? super ENTITY, ?, FIELD, ?> field)
  {
    this.field = field;
  }

  /**
   * Getter du sens de l'ordonnancement
   * @return Le sens de l'ordonnancement
   */
  public boolean isAscendant()
  {
    return this.ascendant;
  }
  /**
   * Setter du sens de l'ordonnancement
   * @param ascendant Sens de l'ordonnancement à positionner
   */
  private void setAscendant(boolean ascendant)
  {
    this.ascendant = ascendant;
  }
}
