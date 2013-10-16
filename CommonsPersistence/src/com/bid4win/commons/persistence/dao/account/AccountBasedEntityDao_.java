package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.Bid4WinDao_;
import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimple;
import com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldSimpleToSimpleParent;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountAbstract_Fields;
import com.bid4win.commons.persistence.entity.account.AccountBasedEntity;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO g�n�rique pour les entit�s de la classe AccountBasedEntity<BR>
 * <BR>
 * @param <ENTITY> Entit� g�r�e par le DAO<BR>
 * @param <ID> Identifiant de l'entit� g�r�e par le DAO<BR>
 * @param <ACCOUNT> D�finition du type de compte utilisateur li� � l'entit� g�r�e
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class AccountBasedEntityDao_<ENTITY extends AccountBasedEntity<ENTITY, ID, ACCOUNT>,
                                             ID,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Bid4WinDao_<ENTITY, ID>
{
  /**
   * Constructeur
   * @param entityClass Classe de l'entit� g�r�e par le DAO
   */
  protected AccountBasedEntityDao_(Class<ENTITY> entityClass)
  {
    super(entityClass);
  }

  /**
   * Cette fonction permet de r�cup�rer l'eventuelle entit� en fonction de son
   * compte utilisateur
   * @param account Compte utilisateur de l'entit� � rechercher
   * @return L'entit� �ventuellement trouv�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public ENTITY findOneByAccount(ACCOUNT account) throws PersistenceException
  {
    if(account == null)
    {
      return null;
    }
    return super.findOne(this.getAccountIdData(account.getId()));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * entit�s dont le compte utilisateur est pr�cis� en argument
   * @param account Compte utilisateur des entit�s � rechercher
   * @return Les crit�res permettant de rechercher les entit�s en fonction de leur
   * compte utilisateur
   */
  /*protected CriteriaQuery<ENTITY> getCriteriaForAccount(ACCOUNT account)
  {
    return this.getCriteriaForAccountId(account.getId());
  }*/
  /**
   * Cette fonction permet de r�cup�rer l'eventuelle entit� en fonction de l'
   * identifiant de son compte utilisateur
   * @param accountId Identifiant du compte utilisateur de l'entit� � rechercher
   * @return L'entit� �ventuellement trouv�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public ENTITY findOneByAccountId(String accountId) throws PersistenceException
  {
    return super.findOne(this.getAccountIdData(accountId));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher les
   * entit�s dont l'identifiant du compte utilisateur est pr�cis� en argument
   * @param accountId Identifiant du compte utilisateur des entit�s � rechercher
   * @return Les crit�res permettant de rechercher les entit�s en fonction de l'
   * identifiant de leur compte utilisateur
   */
  /*protected CriteriaQuery<ENTITY> getCriteriaForAccountId(String accountId)
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();

    CriteriaQuery<ENTITY> criteria = this.createCriteria();
    Root<ENTITY> entity_ = criteria.from(this.getEntityClass());
    Path<String> accountId_ = this.getAccountIdPath(entity_);
    Predicate condition = builder.equal(accountId_, accountId);
    criteria.where(condition);
    return criteria;
  }

  /**
   *
   * TODO A COMMENTER
   * @param root TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  //protected abstract Path<String> getAccountIdPath(Root<ENTITY> root);

  protected Bid4WinData<ENTITY, String> getAccountIdData(String accountId)
  {
    Bid4WinFieldSimpleToSimpleParent<? super ENTITY, Bid4WinEntityGeneratedID<?>, ACCOUNT, String> field =
        new Bid4WinFieldSimpleToSimpleParent(this.getAccountFied(),
                                             AccountAbstract_Fields.ID);
    return new Bid4WinData<ENTITY, String>(field, accountId);
  }
  //protected abstract Bid4WinFieldJoinedSingle<? super ENTITY, ? super ACCOUNT> getAccountFied();
  protected abstract Bid4WinFieldSimple<? super ENTITY, ? super ACCOUNT> getAccountFied();


  /**
   * Cette fonction permet d'ajouter l'entit� en argument
   * @param entity {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public ENTITY add(ENTITY entity) throws PersistenceException
  {
    // Ajoute l'entit� en param�tre
    return super.add(entity);
  }
}
