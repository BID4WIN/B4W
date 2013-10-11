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
 * Cette classe d�fini une cl� unique � lier � un compte utilisateur<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur sur lequel est bas�e
 * l'entit�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountBasedKey<CLASS extends AccountBasedKey<CLASS, ACCOUNT>,
                             ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntitySingle<CLASS, String, ACCOUNT>
{
  /** Pattern utilis� pour la g�n�ration des cl�s */
  public final static IdPattern KEY_PATTERN = IdPattern.createNoCheck("BBBBBBBBBBBBBBBB");

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedKey()
  {
    super();
  }
  /**
   * Constructeur
   * @param idPattern Pattern � utiliser pour la g�n�ration des cl�s
   * @param account Compte utilisateur � lier � la cl�
   * @throws UserException Si le pattern � utiliser pour la g�n�ration des cl�s
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
   * @param account Compte utilisateur � lier � la cl�
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
   * Getter de l'identifiant de la cl�
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
