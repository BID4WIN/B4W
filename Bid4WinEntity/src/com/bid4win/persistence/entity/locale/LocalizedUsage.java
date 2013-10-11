package com.bid4win.persistence.entity.locale;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.resource.FileResourceUsageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.persistence.entity.locale.resource.LocalizedResource;

/**
 * Cette classe défini la base d'une utilisation de ressource internationalisé<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe des types d'utilisation ressource<BR>
 * @param <STORAGE> Doit définir le stockage de la ressource associée à cette utilisation<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "CONTENT_TYPE"))
public abstract class LocalizedUsage<CLASS extends LocalizedUsage<CLASS, TYPE, STORAGE, PART>,
                                     TYPE extends ResourceType<TYPE>,
                                     STORAGE extends LocalizedStorage<STORAGE, TYPE, CLASS, PART>,
                                     PART extends LocalizedResource<PART, TYPE>>
       extends FileResourceUsageMultiPart<CLASS, TYPE, STORAGE, Language, PART>
{
  /**
   * Constructeur pour création par introspection
   */
  protected LocalizedUsage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param storage Stockage de la ressource associée à cette utilisation
   * @throws ProtectionException Si le stockage de ressource en argument est protégé
   * @throws ModelArgumentException Si le stockage de ressource en argument est nul
   * @throws UserException Si le nom ou l'emplacement d'utilisation de la ressource
   * est invalide
   */
  public LocalizedUsage(String path, String name, STORAGE storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
  }

  /**
   * Getter du set des langues de l'utilisation de ressources internationalisées
   * @return Le set des langues de l'utilisation de ressources internationalisées
   */
  public Bid4WinSet<Language> getLanguageSet()
  {
    return this.getPartTypeSet().clone();
  }
  /**
   * Getter de la langue par défaut des ressources internationalisées
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart#getPartTypeDefault()
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
   * Getter du set des langues de l'utilisation de ressources internationalisées
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart#getPartTypeSet()
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
