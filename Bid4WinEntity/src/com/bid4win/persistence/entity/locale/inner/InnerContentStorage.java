package com.bid4win.persistence.entity.locale.inner;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.LocalizedStorage;
import com.bid4win.persistence.entity.locale.resource.InnerContent;

/**
 * Cette classe défini la référence de stockage d'un contenu internationalisé interne
 * à l'application tel une page JSP<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@AttributeOverride(name = "type", column = @Column(name = "CONTENT_TYPE"))
public class InnerContentStorage extends LocalizedStorage<InnerContentStorage, InnerContentType,
                                                          InnerContentUsage, InnerContent>
{
  /**
   * Constructeur pour création par introspection
   */
  protected InnerContentStorage()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage du contenu internationalisé
   * @param name Nom du contenu internationalisé
   * @param type Type du contenu internationalisé
   * @throws UserException Si le nom, l'emplacement de stockage ou le type du contenu
   * internationalisé est invalide
   */
  public InnerContentStorage(String path, String name, InnerContentType type) throws UserException
  {
    super(path, name, type);
  }

  /**
   * Getter de la portion de page HTML correspondant à la langue en paramètre
   * @param language {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  public InnerContent getPart(Language language) throws UserException
  {
    try
    {
      return new InnerContent(this, language);
    }
    catch(ModelArgumentException ex)
    {
      // Cette exception n'est lancée que si la ressource en argument est nulle
      return null;
    }
  }
}
