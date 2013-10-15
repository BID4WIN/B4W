package com.bid4win.commons.core.collection;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.security.ObjectProtection;
import com.bid4win.commons.core.security.ProtectableObject;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe d�fini un conteneur de type d'objets<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectTypeContainer extends ProtectableObject
{
  /** Map interne du conteneur */
  private Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>> internalMap =
      new Bid4WinMap<Class<?>, Bid4WinMap<String, ? extends Bid4WinObjectType<?>>>();

  /**
   * Constructeur de base avec pr�cision de la potentielle protection
   * @param protection Potentielle protection de l'objet
   */
  public Bid4WinObjectTypeContainer(ObjectProtection protection)
  {
    super(null);
    this.protect(protection);
  }

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
   * Cette m�thode permet de r�cup�rer la collection de types d'objet d�fini. Attention,
   * la collection sera prot�g�e comme le conteneur courant
   * @param <TYPE> D�finition du type d'objet � rechercher
   * @param typeClass Classe du type d'objet � rechercher
   * @return La collection de types d'objet d�fini
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
