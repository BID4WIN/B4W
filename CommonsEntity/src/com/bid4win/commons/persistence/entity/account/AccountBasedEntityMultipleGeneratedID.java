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
 * Cette classe pr�cise la g�n�ration des identifiants des entit�s non uniques
 * bas�es sur un compte utilisateur<BR>
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
public class AccountBasedEntityMultipleGeneratedID<CLASS extends AccountBasedEntityMultipleGeneratedID<CLASS, ACCOUNT>,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntityMultiple<CLASS, String, ACCOUNT>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedEntityMultipleGeneratedID()
  {
    super();
  }
  /**
   * Constructeur avec g�n�ration de l'identifiant unique de l'entit� � partir du
   * pattern en argument ou de celui par d�faut si aucun n'est fourni
   * @param pattern Pattern � utiliser pour la g�n�ration de l'identifiant
   */
  protected AccountBasedEntityMultipleGeneratedID(IdPattern pattern)
  {
    super(Bid4WinEntityGeneratedID.generateId(pattern));
  }
  /**
   * Constructeur avec pr�cision de l'identifiant unique de l'entit�
   * @param id Identifiant unique de l'entit� � cr�er
   */
  protected AccountBasedEntityMultipleGeneratedID(String id)
  {
    super((UtilString.trimNotNull(id).equals(UtilString.EMPTY) ? null : UtilString.trimNotNull(id)));
  }

  /**
   * Constructeur sans pr�cision de l'identifiant
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultipleGeneratedID(ACCOUNT account) throws UserException
  {
    this((IdPattern)null, account);
  }
  /**
   * Constructeur avec g�n�ration de l'identifiant unique de l'entit� � partir du
   * pattern en argument ou de celui par d�faut si aucun n'est fourni
   * @param pattern Pattern � utiliser pour la g�n�ration de l'identifiant
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultipleGeneratedID(IdPattern pattern, ACCOUNT account) throws UserException
  {
    super(Bid4WinEntityGeneratedID.generateId(pattern), account);
  }
  /**
   * Constructeur avec pr�cision de l'identifiant unique de l'entit�
   * @param id Identifiant unique de l'entit� � cr�er
   * @param account Compte utilisateur de l'entit�
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
   * Getter de l'identifiant de l'entit�
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
