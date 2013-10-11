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
 * Cette classe représente une map d'objets associés à des types définis (dont
 * celui défini par défaut), incluse dans une entité<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <EMBEDDED> Doit définir la classe des objets de la map
 * @param <TYPE> Doit définir la classe du type lié aux objets de la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEmbeddableWithTypeMap<CLASS extends Bid4WinEmbeddableWithTypeMap<CLASS, EMBEDDED, TYPE>,
                                                   EMBEDDED extends Bid4WinEmbeddableWithType<EMBEDDED, TYPE>,
                                                   TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinEmbeddable<CLASS>
{
  /** Map d'objets associés à des types définis */
  @Transient
  private Bid4WinMap<TYPE, EMBEDDED> embeddedMap = new Bid4WinMap<TYPE, EMBEDDED>();

  /**
   * Constructeur pour création par introspection
   */
  protected Bid4WinEmbeddableWithTypeMap()
  {
    super();
  }
  /**
   * Constructeur
   * @param embeddeds Objets associés à des types définis à référencer
   * @throws UserException Si plus d'un des objets en argument est lié au même
   * type ou si aucun n'est lié au type défini par défaut
   */
  public Bid4WinEmbeddableWithTypeMap(EMBEDDED ... embeddeds) throws UserException
  {
    super();
    this.defineEmbeddedMap(embeddeds);
  }

  /**
   * Redéfini l'égalité interne de deux objets par l'égalité de leur contenu
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
   * Redéfini la transformation en chaîne de caractères d'un objet lisiblement
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
   * Cette méthode doit définir la classe du type lié aux objets de la map
   * @return La classe du type lié aux objets de la map
   */
  public abstract Class<TYPE> getTypeClass();
  /**
   * Cette méthode permet de récupérer le type défini par défaut
   * @return Le type défini par défaut
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
   * Getter de l'objet associé au type défini par défaut
   * @return L'objet associé au type défini par défaut
   */
  public EMBEDDED getEmbedded()
  {
    return this.getEmbeddedMap().get(this.getDefaultType());
  }
  /**
   * Getter de l'objet associé au type en argument. Si le type n'est pas référencé,
   * l'objet associé au type défini par défaut sera retourné
   * @param type Type associé à l'objet à récupérer
   * @return L'objet associé au type en argument
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
   * Getter du set d'objets référencés
   * @return Le set d'objets référencés
   */
  public Bid4WinSet<EMBEDDED> getEmbeddedSet()
  {
    return new Bid4WinSet<EMBEDDED>(this.getEmbeddedMap().values());
  }
  /**
   * Cette méthode permet de référencer l'objet en argument
   * @param embedded Objet à référencer
   * @throws ProtectionException Si l'objet courant est protégé
   * @throws UserException Si le type associé à l'objet en argument est déjà référencé
   */
  public void addEmbedded(EMBEDDED embedded) throws ProtectionException, UserException
  {
    this.addEmbedded(this.getEmbeddedMap(), embedded);
  }
  /**
   * Cette méthode permet de définir la map d'objets associés à des types définis
   * grâce à ceux en argument
   * @param embeddeds Définition des objets associés à des types définis de la map
   * @throws ProtectionException Si l'objet courant est protégé
   * @throws UserException Si plus d'un des objets en argument est lié au même
   * type ou si aucun n'est lié au type défini par défaut
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
   * Cette méthode permet de référencer l'objet en argument à la map donnée
   * @param embeddedMap Map dans laquelle référencer l'objet en paramètre
   * @param embedded Objet à référencer dans la map en paramètre
   * @throws UserException Si le type associé à l'objet en argument est déjà référencé
   * dans la map en paramètre
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
   * Getter de la map d'objets associés à des types définis
   * @return La map d'objets associés à des types définis
   */
  protected Bid4WinMap<TYPE, EMBEDDED> getEmbeddedMap()
  {
    return this.embeddedMap;
  }
  /**
   * Setter de la map d'objets associés à des types définis
   * @param embeddedMap Map d'objets associés à des types définis à positionner
   */
  private void setEmbeddedMap(Bid4WinMap<TYPE, EMBEDDED> embeddedMap)
  {
    this.embeddedMap = embeddedMap;
  }
}
