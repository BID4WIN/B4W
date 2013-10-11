package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe d�fini une entit� non unique bas�e sur un compte utilisateur. Elle
 * ne sera donc obligatoirement pas la seule � r�f�rencer un compte d�fini<BR>
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
public class AccountBasedEntityMultiple<CLASS extends AccountBasedEntityMultiple<CLASS, ID, ACCOUNT>,
                                        ID,
                                        ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntity<CLASS, ID, ACCOUNT>
{
  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedEntityMultiple()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   */
  protected AccountBasedEntityMultiple(ID id)
  {
    super(id);
  }
  /**
   * Constructeur sans pr�cision de l'identifiant
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultiple(ACCOUNT account) throws UserException
  {
    super(account);
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultiple(ID id, ACCOUNT account) throws UserException
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
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  @JoinColumn(name = "ACCOUNT_ID", nullable = false, unique = false)
  public ACCOUNT getAccount()
  {
    return super.getAccount();
  }
}
