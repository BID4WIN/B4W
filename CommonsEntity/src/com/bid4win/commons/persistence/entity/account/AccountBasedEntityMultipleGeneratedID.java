package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;

/**
 * Cette classe précise la génération des identifiants des entités non uniques
 * basées sur un compte utilisateur<BR>
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
public class AccountBasedEntityMultipleGeneratedID<CLASS extends AccountBasedEntityMultipleGeneratedID<CLASS, ACCOUNT>,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultiple<CLASS, String, ACCOUNT>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedEntityMultipleGeneratedID()
  {
    super();
  }
  /**
   * Constructeur avec génération de l'identifiant unique de l'entité à partir du
   * pattern en argument ou de celui par défaut si aucun n'est fourni
   * @param pattern Pattern à utiliser pour la génération de l'identifiant
   */
  protected AccountBasedEntityMultipleGeneratedID(IdPattern pattern)
  {
    super(Bid4WinEntityGeneratedID.generateId(pattern));
  }
  /**
   * Constructeur avec précision de l'identifiant unique de l'entité
   * @param id Identifiant unique de l'entité à créer
   */
  protected AccountBasedEntityMultipleGeneratedID(String id)
  {
    super((UtilString.trimNotNull(id).equals(UtilString.EMPTY) ? null : UtilString.trimNotNull(id)));
  }

  /**
   * Constructeur sans précision de l'identifiant
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultipleGeneratedID(ACCOUNT account) throws UserException
  {
    this((IdPattern)null, account);
  }
  /**
   * Constructeur avec génération de l'identifiant unique de l'entité à partir du
   * pattern en argument ou de celui par défaut si aucun n'est fourni
   * @param pattern Pattern à utiliser pour la génération de l'identifiant
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultipleGeneratedID(IdPattern pattern, ACCOUNT account) throws UserException
  {
    super(Bid4WinEntityGeneratedID.generateId(pattern), account);
  }
  /**
   * Constructeur avec précision de l'identifiant unique de l'entité
   * @param id Identifiant unique de l'entité à créer
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultipleGeneratedID(String id, ACCOUNT account) throws UserException
  {
    super(id, account);
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
  @Column(name = "ID", length = 12, nullable = false, unique = true)
  public String getId()
  {
    return super.getId();
  }
}
