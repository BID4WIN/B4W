package com.bid4win.commons.persistence.dao.account;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;

/**
 * DAO générique pour les entités de la classe AccountBasedKey<BR>
 * <BR>
 * @param <KEY> Doit définir la classe des clés gérées par le DAO<BR>
 * @param <ACCOUNT> Définition du type de compte utilisateur lié à l'entité gérée
 * par le DAO<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class AccountBasedKeyDao_<KEY extends AccountBasedKey<KEY, ACCOUNT>,
                                          ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends AccountBasedEntitySingleDao_<KEY, String, ACCOUNT>
{
  /**
   * Constructeur
   * @param keyClass Classe de la propriété gérée par le DAO
   */
  protected AccountBasedKeyDao_(Class<KEY> keyClass)
  {
    super(keyClass);
  }

  /**
   * Cette fonction permet de récupérer l'unique clé en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public KEY getById(String id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(UtilString.trimNotNull(id).toUpperCase());
  }
}
