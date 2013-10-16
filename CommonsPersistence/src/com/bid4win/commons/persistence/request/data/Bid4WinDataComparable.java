package com.bid4win.commons.persistence.request.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;

/**
 * Cette classe défini un jeu de données sur un champ spécifique d'une entité.
 * Ce jeu de données peut être utilisé et combiné pour construire une condition
 * de comparaison utilisée dans une requête. Par défaut, le champ devra être supérieur
 * ou égal à au moins une des données du jeu.<BR>
 * <BR>
 * @param <ENTITY> Entité pour laquelle la classe défini un jeu de données<BR>
 * @param <FIELD> Type du champ concerné par le jeu de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinDataComparable<ENTITY extends Bid4WinEntity<?, ?>,
                                   FIELD extends Comparable<?  super FIELD>>
       extends Bid4WinData<ENTITY, FIELD>
{
  /** Boolean indiquant si le champ doit être supérieur aux données du jeu testé */
  private boolean greaterThan = true;

  /**
   * Constructeur dont la validité du jeu de données n'est pas exhaustive et dont
   * la comparaison sera du type "supérieur ou égal"
   * @param field Définition du champ concerné par le jeu de données
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinDataComparable(Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                               FIELD ... data)
  {
    this(false, field, true, true, data);
  }
  /**
   * Constructeur
   * @param field Définition du champ concerné par le jeu de données
   * @param exhaustive Boolean indiquant si la validité du jeu de données est exhaustive
   * @param greaterThan Boolean indiquant si le champ doit être supérieur aux
   * données du jeu testé
   * @param equalTo Boolean indiquant si le champ peut être égal aux données du
   * jeu testé
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinDataComparable(boolean exhaustive,
                               Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                               boolean greaterThan, boolean equalTo, FIELD ... data)
  {
    super(exhaustive, field, equalTo, data);
    this.setGreaterThan(greaterThan);
  }

  /**
   * Cette méthode est surchargée afin de modifier comment la définition de champ
   * en argument doit être testée vis à vis de la valeur spécifiée
   * @param builder {@inheritDoc}
   * @param field_ {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.request.data.Bid4WinData#buildCondition(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Path, java.lang.Object)
   */
  @Override
  protected Predicate buildCondition(CriteriaBuilder builder, Path<FIELD> field_, FIELD value)
  {
    if(this.isGreaterThan())
    {
      if(this.isEqualTo())
      {
        return builder.greaterThanOrEqualTo(field_, value);
      }
      return builder.greaterThan(field_, value);
    }
    if(this.isEqualTo())
    {
      return builder.lessThanOrEqualTo(field_, value);
    }
    return builder.lessThan(field_, value);
  }

  /**
   * Getter du boolean indiquant si le champ doit être supérieur aux données du
   * jeu testé
   * @return Le boolean indiquant si le champ doit être supérieur aux données du
   * jeu testé
   */
  public boolean isGreaterThan()
  {
    return this.greaterThan;
  }
  /**
   * Setter du boolean indiquant si le champ doit être supérieur aux données du
   * jeu testé
   * @param greaterThan Boolean à positionner indiquant si le champ doit être
   * supérieur aux données du jeu testé
   */
  private void setGreaterThan(boolean greaterThan)
  {
    this.greaterThan = greaterThan;
  }
}
