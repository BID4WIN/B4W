package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.exception.UserException;

/**
 * Cette classe défini une entité non unique basée sur un compte utilisateur. Elle
 * ne sera donc obligatoirement pas la seule à référencer un compte défini<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <ID> Définition du type de l'identifiant de l'entité<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur sur lequel est basée
 * l'entité<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountBasedEntityMultiple<CLASS extends AccountBasedEntityMultiple<CLASS, ID, ACCOUNT>,
                                        ID,
                                        ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntity<CLASS, ID, ACCOUNT>
{
  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedEntityMultiple()
  {
    super();
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de l'entité
   */
  protected AccountBasedEntityMultiple(ID id)
  {
    super(id);
  }
  /**
   * Constructeur sans précision de l'identifiant
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntityMultiple(ACCOUNT account) throws UserException
  {
    super(account);
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de l'entité
   * @param account Compte utilisateur de l'entité
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
   * Getter du compte utilisateur lié à l'entité
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
