package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 * Cette classe défini une entité basée sur un compte utilisateur, c'est à dire
 * qui possède un lien vers ce dernier<BR>
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
public class AccountBasedEntity<CLASS extends AccountBasedEntity<CLASS, ID, ACCOUNT>,
                                ID,
                                ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinEntity<CLASS, ID>
{
  /** Compte utilisateur de l'entité */
  @Transient
  private ACCOUNT account = null;

  /**
   * Constructeur pour création par introspection
   */
  protected AccountBasedEntity()
  {
    super();
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de l'entité
   */
  protected AccountBasedEntity(ID id)
  {
    super(id);
  }
  /**
   * Constructeur sans précision de l'identifiant
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntity(ACCOUNT account) throws UserException
  {
    this();
    this.linkToAccount(account);
  }
  /**
   * Constructeur avec précision de l'identifiant
   * @param id Identifiant de l'entité
   * @param account Compte utilisateur de l'entité
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntity(ID id, ACCOUNT account) throws UserException
  {
    this(id);
    this.linkToAccount(account);
  }

  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected MessageRef getMessageRefBase(Bid4WinRelation relation)
  {
    if(relation.equals(AccountBasedEntity_Relations.RELATION_ACCOUNT))
    {
      return AccountRef.ACCOUNT;
    }
    return super.getMessageRefBase(relation);
  }

  /**
   * Ajoute à la liste des noeuds de relations de l'entité le lien vers son compte
   * utilisateur
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    Bid4WinList<Bid4WinRelationNode> nodeList = super.getFullRelationNodeList();
    nodeList.add(AccountBasedEntity_Relations.NODE_ACCOUNT);
    return nodeList;
  }
  /**
   * Permet de récupérer le compte utilisateur lié à l'entité s'il correspondant
   * à la relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à remonter
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected Bid4WinEntity<?, ?> getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(AccountBasedEntity_Relations.RELATION_ACCOUNT))
    {
      return this.getAccount();
    }
    return super.getRelationSimple(relation);
  }
  /**
   * Permet de positionner le compte utilisateur lié à l'entité s'il correspondant
   * à la relation en argument. Elle doit être redéfinie pour toute nouvelle relation
   * de type simple à positionner
   * @param relation {@inheritDoc}
   * @param entity {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#setRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation, com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setRelationSimple(Bid4WinRelation relation, Bid4WinEntity<?, ?> entity)
  {
    if(relation.equals(AccountBasedEntity_Relations.RELATION_ACCOUNT))
    {
      this.setAccount((ACCOUNT)entity);
    }
    else
    {
      super.setRelationSimple(relation, entity);
    }
  }

  /**
   * Cette méthode permet de définir le compte utilisateur de l'entité
   * @param account Définition du compte utilisateur de l'entité
   * @throws ProtectionException Si l'entité courante est protégée
   * @throws UserException Si on défini un compte utilisateur nul ou déjà défini
   */
  protected void linkToAccount(ACCOUNT account) throws ProtectionException, UserException
  {
    this.linkTo(AccountBasedEntity_Relations.RELATION_ACCOUNT, account);
  }
  /**
   * Cette méthode permet de retirer le compte utilisateur de l'entité
   * @return Le compte utilisateur anciennement présent dans l'entité
   * @throws ProtectionException Si l'entité courante est protégée
   * @throws UserException Si aucun compte utilisateur n'est défini
   */
  @SuppressWarnings("unchecked")
  protected ACCOUNT unlinkFromAccount() throws ProtectionException, UserException
  {
    return (ACCOUNT)this.unlinkFrom(AccountBasedEntity_Relations.RELATION_ACCOUNT);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du compte utilisateur de l'entité
   * @return Le compte utilisateur de l'entité
   */
  public ACCOUNT getAccount()
  {
    return this.account;
  }
  /**
   * Setter du compte utilisateur de l'entité
   * @param account Compte utilisateur de l'entité à positionner
   */
  private void setAccount(ACCOUNT account)
  {
    this.account = account;
  }
}
