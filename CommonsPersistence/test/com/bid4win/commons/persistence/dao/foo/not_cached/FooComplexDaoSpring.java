package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex_Fields;
import com.bid4win.commons.persistence.request.Bid4WinRequest;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO pour les entités de la classe FooComplex<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooComplexDaoSpring extends FooDaoSpring<FooComplex>
{
  /**
   * Constructeur
   */
  protected FooComplexDaoSpring()
  {
    super(FooComplex.class);
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités dont l'objets inclus
   * correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FooComplex> findListByEmbeddedKey(String key) throws PersistenceException
  {
    return super.findList(this.getRequestForEmbeddedKey(key));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités dont l'objets inclus correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de la
   * clé de leur objet inclus
   */
  protected Bid4WinRequest<FooComplex> getRequestForEmbeddedKey(String key)
  {
    return new Bid4WinRequest<FooComplex>(new Bid4WinData<FooComplex, String>(
        FooComplex_Fields.EMBEDDED_KEY, key));
  }

  /**
   * Cette fonction permet de récupérer la liste d'entités dont un des objets inclus
   * dans sa liste correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return La liste d'entités récupérées
   * @throws PersistenceException Si une exception non attendue est levée
   */
  public Bid4WinList<FooComplex> findListByEmbeddedSetKey(String key) throws PersistenceException
  {
    return super.findList(this.getEmbeddedSetKeyData(key), null);
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher des
   * entités dont un des objets inclus dans sa liste correspondant à la clé en argument
   * @param key Clé des objets inclus à rechercher
   * @return Les critères permettant de rechercher des entités en fonction de la
   * clé de leurs objets inclus
   */
  protected Bid4WinData<FooComplex, String> getEmbeddedSetKeyData(String key)
  {
    return new Bid4WinData<FooComplex, String>(FooComplex_Fields.EMBEDDED_SET_KEY_JOINED, key);
    //return new FooComplexEmbeddedSetKeyRequest(key);
  }

}
