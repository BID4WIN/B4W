package com.bid4win.persistence.entity.locale;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.LanguageRef;

/**
 * Cette classe défini une langue comparable à une énumération<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Language extends Bid4WinObjectType<Language>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 674620443487294844L;
  /** Cette langue correspond à l'anglais et est la langue par défaut*/
  public final static Language ENGLISH = new Language("en", "English");
  /** Cette langue correspond au français */
  public final static Language FRENCH = new Language("fr", "Francais");
  /** Cette langue correspond à l'italien */
  public final static Language ITALIAN = new Language("it", "Italiano");
  /** Cette langue correspond à l'espagnol */
  public final static Language SPANISH = new Language("es", "Espingoing");

  /** Définition de la langue par défaut */
  public final static Language DEFAULT = Bid4WinObjectType.getDefaultType(Language.class);

  /**
   * Cette méthode permet de récupérer la langue dont le code est donné en argument
   * @param code Code de la langue à récupérer
   * @return La langue correspondant au code en argument
   * @throws UserException Si le code en argument ne correspond à aucune langue
   * connue
   */
  public static Language getLanguage(String code) throws UserException
  {
    return Bid4WinObjectType.getType(Language.class, code.toLowerCase());
  }
  /**
   * Cette méthode permet de récupérer toutes les langues existantes
   * @return Le set complet de toutes les langues définies
   */
  public static Bid4WinSet<Language> getLanguageSet()
  {
    return Bid4WinObjectType.getTypeSet(Language.class);
  }

  /** Nom de la langue */
  private String name = null;

  /**
   * Constructeur
   * @param code Code de la langue
   * @param name Nom de la langue
   */
  private Language(String code, String name)
  {
    super(code);
    this.setName(name);
  }
  /**
   * Getter du nom de la langue
   * @return Le nom de la langue
   */
  public String getName()
  {
    return this.name;
  }
  /**
   * Setter du nom de la langue
   * @param name Nom de la langue à positionner
   */
  private void setName(String name)
  {
    this.name = UtilString.trimNotNull(name);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.resource.ResourceType#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return LanguageRef.LANGUAGE;
  }
}
