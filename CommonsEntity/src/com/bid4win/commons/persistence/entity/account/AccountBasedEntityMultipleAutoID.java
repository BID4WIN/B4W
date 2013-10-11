package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe précise la gestion automatique des identifiants des entités non
 * uniques basées sur un compte utilisateur<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur sur lequel est basée
 * l'entité<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountBasedEntityMultipleAutoID<CLASS extends AccountBasedEntityMultipleAutoID<CLASS, ACCOUNT>,
                                              ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultiple<CLASS, Long, ACCOUNT>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedEntityMultipleAutoID()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultipleAutoID(ACCOUNT account) throws UserException
  {
    super(account);
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
  public Long getId()
  {
    return super.getId();
  }
}
