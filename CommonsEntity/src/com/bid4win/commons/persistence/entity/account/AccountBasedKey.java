package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.core.security.IdPattern;

/**
 * Cette classe défini une clé unique à lier à un compte utilisateur<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur sur lequel est basée
 * l'entité<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountBasedKey<CLASS extends AccountBasedKey<CLASS, ACCOUNT>,
                             ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntitySingle<CLASS, String, ACCOUNT>
{
  /** Pattern utilisé pour la génération des clés */
  public final static IdPattern KEY_PATTERN = IdPattern.createNoCheck("BBBBBBBBBBBBBBBB");

  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedKey()
  {
    super();
  }
  /**
   * Constructeur
   * @param idPattern Pattern à utiliser pour la génération des clés
   * @param account Compte utilisateur à lier à la clé
   * @throws UserException Si le pattern à utiliser pour la génération des clés
   * ou le compte utilisateur en argument est nul
   */
  protected AccountBasedKey(IdPattern idPattern, ACCOUNT account) throws UserException
  {
    super(IdGenerator.generateId(UtilObject.checkNotNull("idPattern", idPattern,
                                                         MessageRef.UNKNOWN_MISSING_ERROR)),
          account);
  }
  /**
   * Constructeur
   * @param account Compte utilisateur à lier à la clé
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public AccountBasedKey(ACCOUNT account) throws UserException
  {
    this(KEY_PATTERN, account);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de la clé
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Id()
  @Column(name = "ID", length = 16, nullable = false, unique = true)
  public String getId()
  {
    return super.getId();
  }
}
