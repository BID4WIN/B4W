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
 * Cette classe pr�cise la gestion automatique des identifiants des entit�s non
 * uniques bas�es sur un compte utilisateur<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur sur lequel est bas�e
 * l'entit�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountBasedEntityMultipleAutoID<CLASS extends AccountBasedEntityMultipleAutoID<CLASS, ACCOUNT>,
                                              ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultiple<CLASS, Long, ACCOUNT>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedEntityMultipleAutoID()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur de l'entit�
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
   * Getter de l'identifiant de l'entit�
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
