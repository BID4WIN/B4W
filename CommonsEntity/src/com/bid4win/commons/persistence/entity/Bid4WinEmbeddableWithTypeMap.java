package com.bid4win.commons.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.renderer.Bid4WinObjectCollectionRenderer;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe repr�sente une map d'objets associ�s � des types d�finis (dont
 * celui d�fini par d�faut), incluse dans une entit�<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <EMBEDDED> Doit d�finir la classe des objets de la map
 * @param <TYPE> Doit d�finir la classe du type li� aux objets de la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEmbeddableWithTypeMap<CLASS extends Bid4WinEmbeddableWithTypeMap<CLASS, EMBEDDED, TYPE>,
                                                   EMBEDDED extends Bid4WinEmbeddableWithType<EMBEDDED, TYPE>,
                                                   TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Map d'objets associ�s � des types d�finis */
  @Transient
  private Bid4WinMap<TYPE, EMBEDDED> embeddedMap = new Bid4WinMap<TYPE, EMBEDDED>();

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Bid4WinEmbeddableWithTypeMap()
  {
    super();
  }
  /**
   * Constructeur
   * @param embeddeds Objets associ�s � des types d�finis � r�f�rencer
   * @throws UserException Si plus d'un des objets en argument est li� au m�me
   * type ou si aucun n'est li� au type d�fini par d�faut
   */
  public Bid4WinEmbeddableWithTypeMap(EMBEDDED ... embeddeds) throws UserException
  {
    super();
    this.defineEmbeddedMap(embeddeds);
  }

  /**
   * Red�fini l'�galit� interne de deux objets par l'�galit� de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getEmbeddedMap().equals(toBeCompared.getEmbeddedMap());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un objet lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append(Bid4WinObjectCollectionRenderer.getInstanceObjectCollection().render(
        this.getEmbeddedMap().values()));
    return buffer;
  }

  /**
   * Cette m�thode doit d�finir la classe du type li� aux objets de la map
   * @return La classe du type li� aux objets de la map
   */
  public abstract Class<TYPE> getTypeClass();
  /**
   * Cette m�thode permet de r�cup�rer le type d�fini par d�faut
   * @return Le type d�fini par d�faut
   */
  public TYPE getDefaultType()
  {
    return Bid4WinObjectType.getDefaultType(this.getTypeClass());
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public Bid4WinSet<TYPE> getTypeSet()
  {
    return new Bid4WinSet<TYPE>(this.embeddedMap.keySet());
  }
  /**
   * Getter de l'objet associ� au type d�fini par d�faut
   * @return L'objet associ� au type d�fini par d�faut
   */
  public EMBEDDED getEmbedded()
  {
    return this.getEmbeddedMap().get(this.getDefaultType());
  }
  /**
   * Getter de l'objet associ� au type en argument. Si le type n'est pas r�f�renc�,
   * l'objet associ� au type d�fini par d�faut sera retourn�
   * @param type Type associ� � l'objet � r�cup�rer
   * @return L'objet associ� au type en argument
   */
  public EMBEDDED getEmbedded(TYPE type)
  {
    EMBEDDED embedded = this.getEmbeddedMap().get(type);
    if(embedded == null)
    {
      embedded = this.getEmbedded();
    }
    return embedded;
  }
  /**
   * Getter du set d'objets r�f�renc�s
   * @return Le set d'objets r�f�renc�s
   */
  public Bid4WinSet<EMBEDDED> getEmbeddedSet()
  {
    return new Bid4WinSet<EMBEDDED>(this.getEmbeddedMap().values());
  }
  /**
   * Cette m�thode permet de r�f�rencer l'objet en argument
   * @param embedded Objet � r�f�rencer
   * @throws ProtectionException Si l'objet courant est prot�g�
   * @throws UserException Si le type associ� � l'objet en argument est d�j� r�f�renc�
   */
  public void addEmbedded(EMBEDDED embedded) throws ProtectionException, UserException
  {
    this.addEmbedded(this.getEmbeddedMap(), embedded);
  }
  /**
   * Cette m�thode permet de d�finir la map d'objets associ�s � des types d�finis
   * gr�ce � ceux en argument
   * @param embeddeds D�finition des objets associ�s � des types d�finis de la map
   * @throws ProtectionException Si l'objet courant est prot�g�
   * @throws UserException Si plus d'un des objets en argument est li� au m�me
   * type ou si aucun n'est li� au type d�fini par d�faut
   */
  private void defineEmbeddedMap(EMBEDDED ... embeddeds)
          throws ProtectionException, UserException
  {
    this.checkProtection();
    Bid4WinMap<TYPE, EMBEDDED> embeddedMap = new Bid4WinMap<TYPE, EMBEDDED>();
    for(EMBEDDED embedded : embeddeds)
    {
      this.addEmbedded(embeddedMap, embedded);
    }
    UtilObject.checkNotNull("default", embeddedMap.get(this.getDefaultType()),
                            this.getMessageRefBase().getPrimitive().getMessageRef(MessageRef.SUFFIX_MISSING_ERROR));
    this.setEmbeddedMap(embeddedMap);
  }
  /**
   * Cette m�thode permet de r�f�rencer l'objet en argument � la map donn�e
   * @param embeddedMap Map dans laquelle r�f�rencer l'objet en param�tre
   * @param embedded Objet � r�f�rencer dans la map en param�tre
   * @throws UserException Si le type associ� � l'objet en argument est d�j� r�f�renc�
   * dans la map en param�tre
   */
  private void addEmbedded(Bid4WinMap<TYPE, EMBEDDED> embeddedMap, EMBEDDED embedded)
          throws UserException
  {
    if(embedded != null)
    {
      UtilObject.checkNull("referenced", embeddedMap.get(embedded.getType()),
                           this.getMessageRefBase().getPrimitive().getMessageRef(MessageRef.SUFFIX_DEFINED_ERROR));
      embeddedMap.put(embedded.getType(), embedded);
    }
  }

  /**
   * Getter de la map d'objets associ�s � des types d�finis
   * @return La map d'objets associ�s � des types d�finis
   */
  protected Bid4WinMap<TYPE, EMBEDDED> getEmbeddedMap()
  {
    return this.embeddedMap;
  }
  /**
   * Setter de la map d'objets associ�s � des types d�finis
   * @param embeddedMap Map d'objets associ�s � des types d�finis � positionner
   */
  private void setEmbeddedMap(Bid4WinMap<TYPE, EMBEDDED> embeddedMap)
  {
    this.embeddedMap = embeddedMap;
  }
}
