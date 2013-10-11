//package com.bid4win.persistence.entity.locale.html;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Cacheable;
//import javax.persistence.Entity;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//
//import com.bid4win.commons.core.UtilObject;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.core.reference.MessageRef;
//import com.bid4win.commons.core.security.exception.ProtectionException;
//import com.bid4win.persistence.entity.locale.Language;
//import com.bid4win.persistence.entity.locale.LocalizedStorage;
//import com.bid4win.persistence.entity.locale.resource.HtmlPage;
//
///**
// * Cette classe défini la référence de stockage d'une page HTML<BR>
// * <BR>
// * @author Emeric Fillâtre
// */
////Annotation pour la persistence
//@Entity
//@Access(AccessType.FIELD)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
//public class HtmlPageStorage extends LocalizedStorage<HtmlPageStorage, HtmlPageType, HtmlPageUsage, HtmlPage>
//{
//  /**
//   * Constructeur pour création par introspection
//   */
//  protected HtmlPageStorage()
//  {
//    super();
//  }
//  /**
//   * Constructeur
//   * @param path Emplacement de stockage de la page HTML
//   * @param name Nom de la page HTML
//   * @param type Type de la page HTML
//   * @throws UserException Si le nom, l'emplacement de stockage ou le type de la
//   * page HTML est invalide
//   */
//  public HtmlPageStorage(String path, String name, HtmlPageType type) throws UserException
//  {
//    super(path, name, type);
//  }
//
//  /**
//   * Getter de la portion de page HTML correspondant à la langue en paramètre
//   * @param language {@inheritDoc}
//   * @return {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart#getPart(com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
//   */
//  @Override
//  public HtmlPage getPart(Language language) throws UserException
//  {
//    try
//    {
//      return new HtmlPage(this, language);
//    }
//    catch(ModelArgumentException ex)
//    {
//      // Cette exception n'est lancée que si la ressource en argument est nulle
//      return null;
//    }
//  }
//
//  /**
//   * Redéfini la méthode car un seul type est valide pour les pages HTML donc il
//   * est inutile de le stocker
//   * @param type {@inheritDoc}
//   * @throws ProtectionException {@inheritDoc}
//   * @throws UserException {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.resource.ResourceStorage#defineType(com.bid4win.commons.persistence.entity.resource.ResourceType)
//   */
//  @Override
//  public void defineType(HtmlPageType type) throws ProtectionException, UserException
//  {
//    UtilObject.checkEquals("type", type, HtmlPageType.HTML,
//                           this.getMessageRef(MessageRef.SUFFIX_TYPE,
//                                              MessageRef.SUFFIX_INVALID_ERROR));
//  }
//  /**
//   * Redéfini la méthode car un seul type est valide pour les pages HTML donc il
//   * est inutile de le stocker
//   * @return {@inheritDoc}
//   * @see com.bid4win.commons.persistence.entity.resource.Resource#getType()
//   */
//  @Override
//  public HtmlPageType getType()
//  {
//    return HtmlPageType.HTML;
//  }
//}
