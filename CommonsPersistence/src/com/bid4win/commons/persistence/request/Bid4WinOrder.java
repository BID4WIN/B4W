package com.bid4win.commons.persistence.request;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;

/**
 * Cette classe d�fini un ordonnancement potentiel de r�sultat de requ�te<BR>
 * <BR>
 * @param <ENTITY> Entit� pour laquelle la classe d�fini un ordonnancement<BR>
 * @param <FIELD> Type du champ concern� par l'ordonnancement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinOrder<ENTITY extends Bid4WinEntity<?, ?>,
                          FIELD extends Comparable<?  super FIELD>>
{
  /** D�finition du champ concern� par l'ordonnancement */
  private Bid4WinField<? super ENTITY, ?, FIELD, ?> field = null;
  /** Sens de l'ordonnancement */
  private boolean ascendant = false;

  /**
   * Constructeur d'un ordonnancement croissant
   * @param field D�finition du champ concern� par l'ordonnancement
   */
  public Bid4WinOrder(Bid4WinField<? super ENTITY, ?, FIELD, ?> field)
  {
    this(field, true);
  }
  /**
   * Constructeur
   * @param field D�finition du champ concern� par l'ordonnancement
   * @param ascendant D�fini un ordonnancement croissant si � true, d�croissant sinon
   */
  public Bid4WinOrder(Bid4WinField<? super ENTITY, ?, FIELD, ?> field, boolean ascendant)
  {
    this.setField(field);
    this.setAscendant(ascendant);
  }

  /**
   * Cette m�thode permet de construire l'ordonnancement � utiliser sur la d�finition
   * d'entit� en param�tre
   * @param builder Builder � utiliser pour construire l'ordonnancement
   * @param from_ D�finition d'entit� pour laquelle construire l'ordonnancement
   * @return L'ordonnancement � utiliser sur la d�finition d'entit� en param�tre
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
   * Getter de la d�finition du champ concern� par l'ordonnancement
   * @return La d�finition du champ concern� par l'ordonnancement
   */
  public Bid4WinField<? super ENTITY, ?, FIELD, ?> getField()
  {
    return this.field;
  }
  /**
   * Setter de la d�finition du champ concern� par l'ordonnancement
   * @param field D�finition du champ concern� par l'ordonnancement � positionner
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
   * @param ascendant Sens de l'ordonnancement � positionner
   */
  private void setAscendant(boolean ascendant)
  {
    this.ascendant = ascendant;
  }
}
