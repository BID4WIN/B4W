package com.bid4win.persistence.entity.locale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.persistence.entity.locale.resource.LocalizedResource;

/**
 * Cette classe d�fini la base d'un stockage de ressource internationalis�<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de stockage de ressource<BR>
 * @param <USAGE> Doit d�finir les utilisations de ressources associ�es � ce stockage<BR>
 * @param <PART> Doit d�finir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "CONTENT_TYPE"))
public abstract class LocalizedStorage<CLASS extends LocalizedStorage<CLASS, TYPE, USAGE, PART>,
                                       TYPE extends ResourceType<TYPE>,
                                       USAGE extends LocalizedUsage<USAGE, TYPE, CLASS, PART>,
                                       PART extends LocalizedResource<PART, TYPE>>
       extends FileResourceStorageMultiPart<CLASS, TYPE, USAGE, Language, PART>
{
  /** D�finition de la langue par d�faut des ressource internationalis�es */
  public final static Language DEFAULT_LANGUAGE = Language.DEFAULT;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected LocalizedStorage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
   * ressource est invalide
   */
  public LocalizedStorage(String path, String name, TYPE type) throws UserException
  {
    super(path, name, type);
  }

  /**
   * Getter du set des langues du stockage de ressources internationalis�es
   * @return Le set des langues du stockage de ressources internationalis�es
   */
  public Bid4WinSet<Language> getLanguageSet()
  {
    return this.getPartTypeSet().clone();
  }
  /**
   * Getter de la langue par d�faut des ressources internationalis�es
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.FileResourceStorageMultiPart#getPartTypeDefault()
   */
  @Override
  public Language getPartTypeDefault()
  {
    return LocalizedStorage.DEFAULT_LANGUAGE;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du set des langues du stockage de ressources internationalis�es
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart#getPartTypeSet()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "LANGUAGES", length = 30, nullable = false, unique = false)
  @Type(type = "LANGUAGE_SET")
  protected Bid4WinSet<Language> getPartTypeSet()
  {
    return super.getPartTypeSet();
  }
}
