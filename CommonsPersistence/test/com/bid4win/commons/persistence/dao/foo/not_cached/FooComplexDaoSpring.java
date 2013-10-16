package com.bid4win.commons.persistence.dao.foo.not_cached;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex_Fields;
import com.bid4win.commons.persistence.request.Bid4WinRequest;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO pour les entit�s de la classe FooComplex<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Cette fonction permet de r�cup�rer la liste d'entit�s dont l'objets inclus
   * correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<FooComplex> findListByEmbeddedKey(String key) throws PersistenceException
  {
    return super.findList(this.getRequestForEmbeddedKey(key));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s dont l'objets inclus correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de la
   * cl� de leur objet inclus
   */
  protected Bid4WinRequest<FooComplex> getRequestForEmbeddedKey(String key)
  {
    return new Bid4WinRequest<FooComplex>(new Bid4WinData<FooComplex, String>(
        FooComplex_Fields.EMBEDDED_KEY, key));
  }

  /**
   * Cette fonction permet de r�cup�rer la liste d'entit�s dont un des objets inclus
   * dans sa liste correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return La liste d'entit�s r�cup�r�es
   * @throws PersistenceException Si une exception non attendue est lev�e
   */
  public Bid4WinList<FooComplex> findListByEmbeddedSetKey(String key) throws PersistenceException
  {
    return super.findList(this.getEmbeddedSetKeyData(key), null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher des
   * entit�s dont un des objets inclus dans sa liste correspondant � la cl� en argument
   * @param key Cl� des objets inclus � rechercher
   * @return Les crit�res permettant de rechercher des entit�s en fonction de la
   * cl� de leurs objets inclus
   */
  protected Bid4WinData<FooComplex, String> getEmbeddedSetKeyData(String key)
  {
    return new Bid4WinData<FooComplex, String>(FooComplex_Fields.EMBEDDED_SET_KEY_JOINED, key);
    //return new FooComplexEmbeddedSetKeyRequest(key);
  }

}
