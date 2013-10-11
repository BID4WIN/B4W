package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe d�fini une entit� unique bas�e sur un compte utilisateur. Elle sera
 * donc obligatoirement la seule � r�f�rencer un compte d�fini<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <ID> D�finition du type de l'identifiant de l'entit�<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur sur lequel est bas�e
 * l'entit�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountBasedEntitySingle<CLASS extends AccountBasedEntitySingle<CLASS, ID, ACCOUNT>,
                                      ID,
                                      ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntity<CLASS, ID, ACCOUNT>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedEntitySingle()
  {
    super();
  }
  /**
   * Constructeur sans pr�cision de l'identifiant
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntitySingle(ACCOUNT account) throws UserException
  {
    super(account);
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntitySingle(ID id, ACCOUNT account) throws UserException
  {
    super(id, account);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du compte utilisateur li� � l'entit�
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.account.AccountBasedEntity#getAccount()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "ACCOUNT_ID", nullable = false, unique = true)
  public ACCOUNT getAccount()
  {
    return super.getAccount();
  }
}
