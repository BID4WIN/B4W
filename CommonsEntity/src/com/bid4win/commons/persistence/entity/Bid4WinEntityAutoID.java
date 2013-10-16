package com.bid4win.commons.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Cette classe est la classe de base de toute entité du projet avec identifiant
 * généré automatiquement<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEntityAutoID<CLASS extends Bid4WinEntityAutoID<CLASS>>
       extends Bid4WinEntity<CLASS, Long>
{
  /**
   * Constructeur par défaut
   */
  protected Bid4WinEntityAutoID()
  {
    super();
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de l'entité
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Id()
  @Column(name = "ID", length = 10, nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.AUTO)
  public final Long getId()
  {
    return super.getId();
  }
}
