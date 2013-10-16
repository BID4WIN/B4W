package com.bid4win.commons.persistence.request.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldComposite;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldMultiple;
import com.bid4win.commons.persistence.request.Bid4WinCriteria;

/**
 * Cette classe défini un jeu de données sur un champ spécifique d'une entité.
 * Ce jeu de données peut être utilisé et combiné pour construire une condition
 * d'égalité (ou non) utilisée dans une requête. Par défaut, le champ devra être
 * égal à au moins une des données du jeu.<BR>
 * <BR>
 * @param <ENTITY> Entité pour laquelle la classe défini un jeu de données<BR>
 * @param <FIELD> Type du champ concerné par le jeu de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinData<ENTITY extends Bid4WinEntity<?, ?>, FIELD> extends Bid4WinCriteria<ENTITY>
{
  /** Définition du champ concerné par le jeu de données */
  private Bid4WinField<? super ENTITY, ?, FIELD, ?> field = null;
  /** Jeu de données appliquées au champ défini */
  private FIELD[] data = null;
  /** Boolean indiquant si le champ doit être égal aux données du jeu testé */
  private boolean equalTo = true;

  /**
   * Constructeur dont la validité du jeu de données n'est pas exhaustive et le
   * test est l'égalité
   * @param field Définition du champ concerné par le jeu de données
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinData(Bid4WinField<? super ENTITY, ?, FIELD, ?> field, FIELD ... data)
  {
    this(false, field, data);
  }
  /**
   * Constructeur dont le test est l'égalité
   * @param exhaustive Boolean indiquant si la validité du jeu de données est exhaustive
   * @param field Définition du champ concerné par le jeu de données
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinData(boolean exhaustive,
                     Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                     FIELD ... data)
  {
    this(exhaustive, field, true, data);
  }
  /**
   * Constructeur dont la validité du jeu de données n'est pas exhaustive
   * @param field Définition du champ concerné par le jeu de données
   * @param equalTo Boolean indiquant si le test est l'égalité
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinData(Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                     boolean equalTo,
                     FIELD ... data)
  {
    this(false, field, equalTo, data);
  }
  /**
   * Constructeur
   * @param exhaustive Boolean indiquant si la validité du jeu de données est exhaustive
   * @param field Définition du champ concerné par le jeu de données
   * @param equalTo Boolean indiquant si le test est l'égalité
   * @param data Jeu de données appliquées au champ défini
   */
  public Bid4WinData(boolean exhaustive,
                     Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                     boolean equalTo,
                     FIELD ... data)
  {

    super(exhaustive);
    this.setField(field);
    this.setData(data);
    this.setEqualTo(equalTo);
  }

  /**
   * Cette méthode permet de construire la condition correspondant au jeu de données
   * courant à appliquer à la définition d'entité en paramètre
   * @param builder {@inheritDoc}
   * @param from_ {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.request.Bid4WinCriteria#buildCondition(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.From)
   */
  @Override
  protected final Predicate buildCondition(CriteriaBuilder builder, From<?, ? extends ENTITY> from_)
  {
    Predicate[] dataConditions = new Predicate[this.getData().length];
    int index = 0;
    Path<FIELD> field_ = this.getPath(from_);
    for(FIELD value : this.getData())
    {
      dataConditions[index++] = this.buildCondition(builder, field_, value);
    }
    return this.combineConditions(builder, dataConditions);
  }
  /**
   * Cette méthode est celle qui est utilisée pour définir comment la définition
   * de champ en argument doit être testée vis à vis de la valeur spécifiée. L'
   * égalité est le test implémenté par défaut
   * @param builder Builder à utiliser pour construire la condition
   * @param field_ Définition du champ pour lequel construire la condition
   * @param value Valeur à utiliser pour construire la condition
   * @return La condition testant la définition de champ en argument vis à vis de
   * la valeur spécifiée
   */
  protected Predicate buildCondition(CriteriaBuilder builder, Path<FIELD> field_, FIELD value)
  {
    if(value == null)
    {
      if(this.isEqualTo())
      {
        return builder.isNull(field_);
      }
      return builder.isNotNull(field_);
    }
    if(this.isEqualTo())
    {
      return builder.equal(field_, value);
    }
    return builder.notEqual(field_, value);
  }

  /**
   *
   * TODO A COMMENTER
   * @param from_ {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.request.Bid4WinCriteria#getGroups(javax.persistence.criteria.From)
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected Expression<?>[] getGroups(From<?, ? extends ENTITY> from_)
  {
    Bid4WinField field = null;
    if(this.getField() instanceof Bid4WinFieldMultiple)
    {
      field = ((Bid4WinFieldMultiple)this.getField()).getGroupField();
    }
    else if(this.getField() instanceof Bid4WinFieldComposite &&
            ((Bid4WinFieldComposite)this.getField()).getViaField() instanceof Bid4WinFieldMultiple)
    {
      field = ((Bid4WinFieldMultiple)((Bid4WinFieldComposite)this.getField()).getViaField()).getGroupField();
    }
    if(field == null)
    {
      return null;
    }
    return new Expression<?>[]{field.getPath(from_)};
  }

  /**
   *
   * TODO A COMMENTER
   * @param from_ TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected Path<FIELD> getPath(From<?, ? extends ENTITY> from_)
  {
    Bid4WinField field = this.getField();
    return field.getPath(from_);
  }

  /**
   * Getter de la définition du champ concerné par le jeu de données
   * @return La définition du champ concerné par le jeu de données
   */
  public Bid4WinField<? super ENTITY, ?, FIELD, ?> getField()
  {
    return this.field;
  }
  /**
   * Setter de la définition du champ concerné par le jeu de données
   * @param field Définition du champ concerné par le jeu de données à positionner
   */
  private void setField(Bid4WinField<? super ENTITY, ?, FIELD, ?> field)
  {
    this.field = field;
  }

  /**
   * Getter du jeu de données appliquées au champ défini
   * @return Le jeu de données appliquées au champ défini
   */
  private FIELD[] getData()
  {
    return this.data;
  }
  /**
   * Le jeu de données appliquées au champ défini
   * @param data Jeu de données appliquées au champ défini à positionner
   */
  private void setData(FIELD ... data)
  {
    this.data = data;
  }

  /**
   * Getter du boolean indiquant si le champ peut être égal aux données du jeu
   * testé
   * @return Le boolean indiquant si le champ peut être égal aux données du jeu
   * testé
   */
  public boolean isEqualTo()
  {
    return this.equalTo;
  }
  /**
   * Setter du boolean indiquant si le champ peut être égal aux données du jeu
   * testé
   * @param equalTo Boolean à positionner indiquant si le champ peut être égal
   * aux données du jeu testé
   */
  private void setEqualTo(boolean equalTo)
  {
    this.equalTo = equalTo;
  }
}
