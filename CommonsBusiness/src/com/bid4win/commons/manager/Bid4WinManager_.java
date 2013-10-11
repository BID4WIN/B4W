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
 * Cette classe d�fini la base de tous les managers du projet. Ces managers devront
 * directement assurer les gestions m�tier dont ils ont la charge<BR>
 * <BR>
 * @param <ACCOUNT> D�finition de type de compte utilisateur utilis� par le projet<BR>
 * @param <MANAGER> D�finition du manager impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinManager_<ACCOUNT extends AccountAbstract<ACCOUNT>,
                                      MANAGER extends Bid4WinManager_<ACCOUNT, MANAGER>>
       extends Bid4WinSelfReferencedBean<MANAGER>
{
  /** R�f�rence du manager des comptes utilisateur */
  private AccountAbstractManager_<ACCOUNT> accountManager;

  /**
   * Cette m�thode permet de r�cup�rer de mani�re g�n�rique la version des entit�s
   * d�finies en argument
   * @param <ENTITY> D�finition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Identifiants des entit�s � rechercher
   * @return La map de version des entit�s recherch�es (si une entit� n'est pas
   * trouv�e, sa version sera n�gative)
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public <ENTITY extends Bid4WinEntity<ENTITY, ID>, ID>
         Bid4WinMap<ID, Integer> getVersion(Class<ENTITY> entityClass,
                                            Bid4WinSet<ID> idSet)
         throws PersistenceException
  {
    Bid4WinMap<ID, Integer> versionMap = new Bid4WinMap<ID, Integer>(idSet.size());
    // TODO voir si plus int�ressant d'utiliser tuplequery
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
   * Cette m�thode permet de r�cup�rer de mani�re g�n�rique les entit�s d�finies
   * en argument. Les relations d�finies par l'entit� seront charg�es automatiquement
   * @param <ENTITY> D�finition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Identifiants des entit�s � rechercher
   * @return Les entit�s trouv�es et charg�es gr�ce � leur identifiant
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode doit permettre de r�cup�rer de mani�re g�n�rique les entit�s
   * d�finies en argument. Elle doit est red�finie � chaque fois que n�cessaire
   * @param <ENTITY> D�finition du type d'entit� � rechercher
   * @param <ID> D�finition du type d'identifiant des entit�s � rechercher
   * @param entityClass Classe des entit�s � rechercher
   * @param idSet Identifiants des entit�s � rechercher
   * @return Les entit�s trouv�es gr�ce � leur identifiant
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
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
   * Getter de la r�f�rence du manager des comptes utilisateur
   * @return La r�f�rence du manager des comptes utilisateur
   */
  protected AccountAbstractManager_<ACCOUNT> getAccountManager()
  {
    return this.accountManager;
  }
  /**
   * Setter de la r�f�rence du manager des comptes utilisateur. L'injection se fait
   * gr�ce � un setter pour pouvoir la d�brayer dans le cas de d�pendance circulaire
   * @param accountManager R�f�rence du manager des comptes utilisateur � positionner
   * @throws ModelArgumentException Si un manager est d�j� positionn�
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
