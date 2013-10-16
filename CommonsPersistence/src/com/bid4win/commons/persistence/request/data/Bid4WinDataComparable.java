package com.bid4win.commons.persistence.request.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinField;

/**
 * Cette classe d�fini un jeu de donn�es sur un champ sp�cifique d'une entit�.
 * Ce jeu de donn�es peut �tre utilis� et combin� pour construire une condition
 * de comparaison utilis�e dans une requ�te. Par d�faut, le champ devra �tre sup�rieur
 * ou �gal � au moins une des donn�es du jeu.<BR>
 * <BR>
 * @param <ENTITY> Entit� pour laquelle la classe d�fini un jeu de donn�es<BR>
 * @param <FIELD> Type du champ concern� par le jeu de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinDataComparable<ENTITY extends Bid4WinEntity<?, ?>,
                                   FIELD extends Comparable<?  super FIELD>>
       extends Bid4WinData<ENTITY, FIELD>
{
  /** Boolean indiquant si le champ doit �tre sup�rieur aux donn�es du jeu test� */
  private boolean greaterThan = true;

  /**
   * Constructeur dont la validit� du jeu de donn�es n'est pas exhaustive et dont
   * la comparaison sera du type "sup�rieur ou �gal"
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataComparable(Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                               FIELD ... data)
  {
    this(false, field, true, true, data);
  }
  /**
   * Constructeur
   * @param field D�finition du champ concern� par le jeu de donn�es
   * @param exhaustive Boolean indiquant si la validit� du jeu de donn�es est exhaustive
   * @param greaterThan Boolean indiquant si le champ doit �tre sup�rieur aux
   * donn�es du jeu test�
   * @param equalTo Boolean indiquant si le champ peut �tre �gal aux donn�es du
   * jeu test�
   * @param data Jeu de donn�es appliqu�es au champ d�fini
   */
  public Bid4WinDataComparable(boolean exhaustive,
                               Bid4WinField<? super ENTITY, ?, FIELD, ?> field,
                               boolean greaterThan, boolean equalTo, FIELD ... data)
  {
    super(exhaustive, field, equalTo, data);
    this.setGreaterThan(greaterThan);
  }

  /**
   * Cette m�thode est surcharg�e afin de modifier comment la d�finition de champ
   * en argument doit �tre test�e vis � vis de la valeur sp�cifi�e
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
   * Getter du boolean indiquant si le champ doit �tre sup�rieur aux donn�es du
   * jeu test�
   * @return Le boolean indiquant si le champ doit �tre sup�rieur aux donn�es du
   * jeu test�
   */
  public boolean isGreaterThan()
  {
    return this.greaterThan;
  }
  /**
   * Setter du boolean indiquant si le champ doit �tre sup�rieur aux donn�es du
   * jeu test�
   * @param greaterThan Boolean � positionner indiquant si le champ doit �tre
   * sup�rieur aux donn�es du jeu test�
   */
  private void setGreaterThan(boolean greaterThan)
  {
    this.greaterThan = greaterThan;
  }
}
