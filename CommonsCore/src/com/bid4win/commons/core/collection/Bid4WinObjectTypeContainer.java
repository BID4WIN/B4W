package com.bid4win.commons.core.collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;

/**
 * Cette classe d�fini un conteneur de type d'objets<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectTypeContainer
{
  /** Map interne du conteneur */
  private Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>> internalMap =
      new Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>>();

  /**
   * Cette m�thode permet d'ajouter un type d'objet d�fini au conteneur. Si un
   * m�me type est d�j� r�f�renc� avec le code de celui en argument, l'ajout �choue
   * @param <TYPE> D�finition du type d'objet � ajouter
   * @param typeClass Classe du type d'objet � ajouter
   * @param type Type d'objet � ajouter
   * @throws ModelArgumentException Si un m�me type est d�j� r�f�renc� avec le
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
   * Cette m�thode permet de r�cup�rer un type d'objet d�fini en fonction de son
   * code
   * @param <TYPE> D�finition du type d'objet � rechercher
   * @param typeClass Classe du type d'objet � rechercher
   * @param code Code du type d'objet � rechercher
   * @return Le type d'objet d�fini correspondant au code en argument ou null s'il
   * n'est pas r�f�renc�
   */
  public <TYPE extends Bid4WinObjectType<TYPE>> TYPE get(Class<TYPE> typeClass,
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
   * Cette m�thode permet de r�cup�rer le set de types d'objet d�fini
   * @param <TYPE> D�finition du type d'objet � rechercher
   * @param typeClass Classe du type d'objet � rechercher
   * @return Le set de types d'objet d�fini
   */
  public <TYPE extends Bid4WinObjectType<TYPE>> Bid4WinSet<TYPE> getSet(Class<TYPE> typeClass)
  {
    Bid4WinMap<String, TYPE> typeMap = this.getTypeMap(typeClass);
    if(typeMap == null)
    {
      return new Bid4WinSet<TYPE>();
    }
    return new Bid4WinSet<TYPE>(typeMap.values());
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
   * Cette m�thode permet de r�cup�rer la map existante correspondant au type d'
   * objet d�fini en argument
   * @param <TYPE> D�finition du type d'objet de la map � rechercher
   * @param typeClass Classe du type d'objet de la map � rechercher
   * @return La map correspondant au type d'objet d�fini en argument si elle existe
   */
  @SuppressWarnings("unchecked")
  private <TYPE extends Bid4WinObjectType<TYPE>> Bid4WinMap<String, TYPE> getTypeMap(Class<TYPE> typeClass)
  {
    return (Bid4WinMap<String, TYPE>)this.getInternalMap().get(typeClass);
  }
  /**
   * Cette m�thode permet de r�cup�rer la map correspondant au type d'objet d�fini
   * en argument et de la cr�er si elle n'existe pas d�j�
   * @param <TYPE> D�finition du type d'objet de la map � rechercher
   * @param typeClass Classe du type d'objet de la map � rechercher
   * @return La map correspondant au type d'objet d�fini en argument, potentiellement
   * cr��e pour l'occasion
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
