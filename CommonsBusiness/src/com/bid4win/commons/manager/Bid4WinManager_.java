package com.bid4win.commons.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean;
import com.bid4win.commons.manager.account.AccountAbstractManager_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinEntityLoader;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 * Cette classe défini la base de tous les managers du projet. Ces managers devront
 * directement assurer les gestions métier dont ils ont la charge<BR>
 * <BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * @param <MANAGER> Définition du manager implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinManager_<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                      MANAGER extends Bid4WinManager_<ACCOUNT, MANAGER>>
       extends Bid4WinSelfReferencedBean<MANAGER>
{
  /** Référence du manager des comptes utilisateur */
  private AccountAbstractManager_<ACCOUNT> accountManager;

  /**
   * Cette méthode permet de récupérer de manière générique la version des entités
   * définies en argument
   * @param <ENTITY> Définition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Identifiants des entités à rechercher
   * @return La map de version des entités recherchées (si une entité n'est pas
   * trouvée, sa version sera négative)
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, Integer> getVersion(Class<ENTITY> entityClass,
                                            Bid4WinSet<ID> idSet)
         throws PersistenceException
  {
    Bid4WinMap<ID, Integer> versionMap = new Bid4WinMap<ID, Integer>(idSet.size());
    // TODO voir si plus intéressant d'utiliser tuplequery
    Bid4WinMap<ID, ENTITY> entityMap = this.findById(entityClass, idSet);
    for(ID id : idSet)
    {
      ENTITY entity = entityMap.get(id);
      if(entity == null)
      {
        versionMap.put(id, -1);
      }
      else
      {
        versionMap.put(id, entity.getVersion());
      }
    }
    return versionMap;
  }
  /**
   * Cette méthode permet de récupérer de manière générique les entités définies
   * en argument. Les relations définies par l'entité seront chargées automatiquement
   * @param <ENTITY> Définition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Identifiants des entités à rechercher
   * @return Les entités trouvées et chargées grâce à leur identifiant
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, ENTITY> load(Class<ENTITY> entityClass,
                                     Bid4WinSet<ID> idSet)
         throws PersistenceException
  {
    return Bid4WinEntityLoader.getInstance().loadRelation(this.findById(entityClass,
                                                                        idSet));
  }
  /**
   * Cette méthode doit permettre de récupérer de manière générique les entités
   * définies en argument. Elle doit est redéfinie à chaque fois que nécessaire
   * @param <ENTITY> Définition du type d'entité à rechercher
   * @param <ID> Définition du type d'identifiant des entités à rechercher
   * @param entityClass Classe des entités à rechercher
   * @param idSet Identifiants des entités à rechercher
   * @return Les entités trouvées grâce à leur identifiant
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   */
  @SuppressWarnings("unused")
  protected <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
            Bid4WinMap<ID, ENTITY> findById(Class<ENTITY> entityClass, Bid4WinSet<ID> idSet)
            throws PersistenceException
  {
    return new Bid4WinMap<ID, ENTITY>(0);
  }

  /**
   * Getter de la référence du manager des comptes utilisateur
   * @return La référence du manager des comptes utilisateur
   */
  protected AccountAbstractManager_<ACCOUNT> getAccountManager()
  {
    return this.accountManager;
  }
  /**
   * Setter de la référence du manager des comptes utilisateur. L'injection se fait
   * grâce à un setter pour pouvoir la débrayer dans le cas de dépendance circulaire
   * @param accountManager Référence du manager des comptes utilisateur à positionner
   * @throws ModelArgumentException Si un manager est déjà positionné
   */
  @Autowired
  @Qualifier("AccountManager")
  protected void setAccountManager(AccountAbstractManager_<ACCOUNT> accountManager)
            throws ModelArgumentException
  {
    UtilObject.checkNull("accountManager", this.getAccountManager());
    this.accountManager = accountManager;
  }
}
