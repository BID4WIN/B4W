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
 * Cette classe d�fini un jeu de donn�es sur un champ sp�cifique d'une entit�.
 * Ce jeu de donn�es peut �tre utilis� et combin� pour construire une condition
 * d'�galit� (ou non) utilis�e dans une requ�te. Par d�faut, le champ devra �tre
 * �gal � au moins une des donn�es du jeu.<BR>
 * <BR>
 * @param <ENTITY> Entit� pour laquelle la classe d�fini un jeu de donn�es<BR>
 * @param <FIELD> Type du champ concern� par le jeu de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinData<ENTITY extends Bid4WinEntity<?, ?>, FIELD> extends Bid4WinCriteria<ENTITY>
{
  /** D�finition du champ concern� par le jeu de donn�es */
  private Bid4WinField<? super ENTITY, ?, FIELD, ?> field = null;
  /** Jeu de donn�es appliqu�es au champ d�fini */
  private FIELD[] data = null;
  /** Boolean indiquant si le champ doit �tre �gal aux donn�es du jeu test� */
  private boolean equalTo = true;

  /**
   * Constructeur dont la validit� du jeu de donn�es n'est pas exhaustive et le
   * test est l'�galit�
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinData(Bid4WinField<? super ENTITY, ?, FIELD, ?> field, FIELD ... data)
  {
    this(false, field, data);
  }
  /**
   * Constructeur dont le test est l'�galit�
   * @param exhaustive Boolean indiquant si la validit� du jeu de donn�es est exhaustive
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinData(boolean exhaustive,
                     Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                     FIELD ... data)
  {
    this(exhaustive, field, true, data);
  }
  /**
   * Constructeur dont la validit� du jeu de donn�es n'est pas exhaustive
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param equalTo Boolean indiquant si le test est l'�galit�
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinData(Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                     boolean equalTo,
                     FIELD ... data)
  {
    this(false, field, equalTo, data);
  }
  /**
   * Constructeur
   * @param exhaustive Boolean indiquant si la validit� du jeu de donn�es est exhaustive
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param equalTo Boolean indiquant si le test est l'�galit�
   * @param data Jeu de donn�es appliqu�es au champ d�fini
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
   * Cette m�thode permet de construire la condition correspondant au jeu de donn�es
   * courant � appliquer � la d�finition d'entit� en param�tre
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
   * Cette m�thode est celle qui est utilis�e pour d�finir comment la d�finition
   * de champ en argument doit �tre test�e vis � vis de la valeur sp�cifi�e. L'
   * �galit� est le test impl�ment� par d�faut
   * @param builder Builder � utiliser pour construire la condition
   * @param field_ D�finition du champ pour lequel construire la condition
   * @param value Valeur � utiliser pour construire la condition
   * @return La condition testant la d�finition de champ en argument vis � vis de
   * la valeur sp�cifi�e
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
   * Getter de la d�finition du champ concern� par le jeu de donn�es
   * @return La d�finition du champ concern� par le jeu de donn�es
   */
  public Bid4WinField<? super ENTITY, ?, FIELD, ?> getField()
  {
    return this.field;
  }
  /**
   * Setter de la d�finition du champ concern� par le jeu de donn�es
   * @param field D�finition du champ concern� par le jeu de donn�es � positionner
   */
  private void setField(Bid4WinField<? super ENTITY, ?, FIELD, ?> field)
  {
    this.field = field;
  }

  /**
   * Getter du jeu de donn�es appliqu�es au champ d�fini
   * @return Le jeu de donn�es appliqu�es au champ d�fini
   */
  private FIELD[] getData()
  {
    return this.data;
  }
  /**
   * Le jeu de donn�es appliqu�es au champ d�fini
   * @param data Jeu de donn�es appliqu�es au champ d�fini � positionner
   */
  private void setData(FIELD ... data)
  {
    this.data = data;
  }

  /**
   * Getter du boolean indiquant si le champ peut �tre �gal aux donn�es du jeu
   * test�
   * @return Le boolean indiquant si le champ peut �tre �gal aux donn�es du jeu
   * test�
   */
  public boolean isEqualTo()
  {
    return this.equalTo;
  }
  /**
   * Setter du boolean indiquant si le champ peut �tre �gal aux donn�es du jeu
   * test�
   * @param equalTo Boolean � positionner indiquant si le champ peut �tre �gal
   * aux donn�es du jeu test�
   */
  private void setEqualTo(boolean equalTo)
  {
    this.equalTo = equalTo;
  }
}
