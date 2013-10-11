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
 * Cette classe d�fini une entit� bas�e sur un compte utilisateur, c'est � dire
 * qui poss�de un lien vers ce dernier<BR>
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
public class AccountBasedEntity<CLASS extends AccountBasedEntity<CLASS, ID, ACCOUNT>,
                                ID,
                                ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinEntity<CLASS, ID>
{
  /** Compte utilisateur de l'entit� */
  @Transient
  private ACCOUNT account = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected AccountBasedEntity()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   */
  protected AccountBasedEntity(ID id)
  {
    super(id);
  }
  /**
   * Constructeur sans pr�cision de l'identifiant
   * @param account Compte utilisateur de l'entit�
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  protected AccountBasedEntity(ACCOUNT account) throws UserException
  {
    this();
    this.linkToAccount(account);
  }
  /**
   * Constructeur avec pr�cision de l'identifiant
   * @param id Identifiant de l'entit�
   * @param account Compte utilisateur de l'entit�
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
   * Ajoute � la liste des noeuds de relations de l'entit� le lien vers son compte
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
   * Permet de r�cup�rer le compte utilisateur li� � l'entit� s'il correspondant
   * � la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type simple � remonter
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
   * Permet de positionner le compte utilisateur li� � l'entit� s'il correspondant
   * � la relation en argument. Elle doit �tre red�finie pour toute nouvelle relation
   * de type simple � positionner
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
   * Cette m�thode permet de d�finir le compte utilisateur de l'entit�
   * @param account D�finition du compte utilisateur de l'entit�
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   * @throws UserException Si on d�fini un compte utilisateur nul ou d�j� d�fini
   */
  protected void linkToAccount(ACCOUNT account) throws ProtectionException, UserException
  {
    this.linkTo(AccountBasedEntity_Relations.RELATION_ACCOUNT, account);
  }
  /**
   * Cette m�thode permet de retirer le compte utilisateur de l'entit�
   * @return Le compte utilisateur anciennement pr�sent dans l'entit�
   * @throws ProtectionException Si l'entit� courante est prot�g�e
   * @throws UserException Si aucun compte utilisateur n'est d�fini
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
   * Getter du compte utilisateur de l'entit�
   * @return Le compte utilisateur de l'entit�
   */
  public ACCOUNT getAccount()
  {
    return this.account;
  }
  /**
   * Setter du compte utilisateur de l'entit�
   * @param account Compte utilisateur de l'entit� � positionner
   */
  private void setAccount(ACCOUNT account)
  {
    this.account = account;
  }
}
