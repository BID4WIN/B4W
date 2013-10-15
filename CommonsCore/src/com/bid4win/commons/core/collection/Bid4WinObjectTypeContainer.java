package com.bid4win.commons.core.collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.security.ObjectProtection;
import com.bid4win.commons.core.security.ProtectableObject;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe défini un conteneur de type d'objets<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectTypeContainer extends ProtectableObject
{
  /** Map interne du conteneur */
  private Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>> internalMap =
      new Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>>();

  /**
   * Constructeur de base avec précision de la potentielle protection
   * @param protection Potentielle protection de l'objet
   */
  public Bid4WinObjectTypeContainer(ObjectProtection protection)
  {
    super(null);
    this.protect(protection);
  }

  /**
   * Cette méthode permet d'ajouter un type d'objet défini au conteneur. Si un
   * même type est déjà référencé avec le code de celui en argument, l'ajout échoue
   * @param <TYPE> Définition du type d'objet à ajouter
   * @param typeClass Classe du type d'objet à ajouter
   * @param type Type d'objet à ajouter
   * @throws ModelArgumentException Si un même type est déjà référencé avec le
   * code de celui en argument
   */
  public synchronized <TYPE extends Bid4WinObjectType<TYPE>>
         void put(Class<TYPE> typeClass, TYPE type) throws ModelArgumentException
  {
    UtilObject.checkEquals("typeClass", type.getClass(), typeClass);
    Bid4WinMap<String, TYPE> typeMap = this.createTypeMap(typeClass);
    UtilObject.checkExcept("code", type.getCode(), typeMap.keySet());
    typeMap.put(type.getCode(), type);
  }

  /**
   * Cette méthode permet de récupérer un type d'objet défini en fonction de son
   * code
   * @param <TYPE> Définition du type d'objet à rechercher
   * @param typeClass Classe du type d'objet à rechercher
   * @param code Code du type d'objet à rechercher
   * @return Le type d'objet défini correspondant au code en argument ou null s'il
   * n'est pas référencé
   */
  public <TYPE extends Bid4WinObjectType<TYPE>> TYPE getType(Class<TYPE> typeClass,
                                                             String code)
  {
    Bid4WinMap<String, TYPE> typeMap = this.getTypeMap(typeClass);
    if(typeMap == null)
    {
      return null;
    }
    return typeMap.get(code);
  }
  /**
   * Cette méthode permet de récupérer la collection de types d'objet défini. Attention,
   * la collection sera protégée comme le conteneur courant
   * @param <TYPE> Définition du type d'objet à rechercher
   * @param typeClass Classe du type d'objet à rechercher
   * @return La collection de types d'objet défini
   */
  public <TYPE extends Bid4WinObjectType<TYPE>> Bid4WinCollection<TYPE> getTypes(Class<TYPE> typeClass)
  {
    Bid4WinMap<String, TYPE> typeMap = this.getTypeMap(typeClass);
    if(typeMap == null)
    {
      return new Bid4WinCollection<TYPE>();
    }
    return typeMap.values();
  }

  /**
   *
   * TODO A COMMENTER
   * @param protection {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObject#protect(com.bid4win.commons.core.security.ObjectProtection)
   */
  @Override
  public synchronized void protect(ObjectProtection protection) throws ProtectionException
  {
    super.protect(protection);
    this.getInternalMap().protect(protection);
  }

  /**
   * Getter de la map interne du conteneur
   * @return La map interne du conteneur
   */
  private Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>> getInternalMap()
  {
    return this.internalMap;
  }
  /**
   * Cette méthode permet de récupérer la map existante correspondant au type d'
   * objet défini en argument
   * @param <TYPE> Définition du type d'objet de la map à rechercher
   * @param typeClass Classe du type d'objet de la map à rechercher
   * @return La map correspondant au type d'objet défini en argument si elle existe
   */
  @SuppressWarnings("unchecked")
  private <TYPE extends Bid4WinObjectType<TYPE>> Bid4WinMap<String, TYPE> getTypeMap(Class<TYPE> typeClass)
  {
    return (Bid4WinMap<String, TYPE>)this.getInternalMap().get(typeClass);
  }
  /**
   * Cette méthode permet de récupérer la map correspondant au type d'objet défini
   * en argument et de la créer si elle n'existe pas déjà
   * @param <TYPE> Définition du type d'objet de la map à rechercher
   * @param typeClass Classe du type d'objet de la map à rechercher
   * @return La map correspondant au type d'objet défini en argument, potentiellement
   * créée pour l'occasion
   */
  private <TYPE extends Bid4WinObjectType<TYPE>> Bid4WinMap<String, TYPE> createTypeMap(Class<TYPE> typeClass)
  {
    Bid4WinMap<String, TYPE> typeMap = this.getTypeMap(typeClass);
    if(typeMap == null)
    {
      typeMap = new Bid4WinMap<String, TYPE>();
      this.getInternalMap().put(typeClass, typeMap);
    }
    return typeMap;
  }
}
