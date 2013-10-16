package com.bid4win.persistence.entity.account.preference;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef.LanguageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;
import com.bid4win.persistence.entity.locale.Language;


/**
 * Cette classe défini défini le jeu de préférences d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class PreferenceBundle extends Bid4WinEmbeddable<PreferenceBundle>
{
  /** Clé de la préférence de langue pour la communication */
  public final static String KEY_LANGUAGE = "language";

  /** Langue de préférence pour la communication */
  @Transient private Language language = Language.DEFAULT;

  /**
   * Constructeur par défaut
   */
  public PreferenceBundle()
  {
    super();
  }
  /**
   * Constructeur avec précision d'une liste de préférences à utiliser
   * @param root Racine de la liste de préférences à utiliser
   * @throws UserException Si l'une des préférences est invalide
   */
  public PreferenceBundle(PreferenceRoot root) throws UserException
  {
    super();
    this.importPreferences(root);
  }

  /**
   * Redéfini l'égalité interne de deux jeux de préférences par l'égalité de leur
   * contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(PreferenceBundle toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getLanguage() == toBeCompared.getLanguage();
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'un jeu de préférence
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments du jeux de préférences
    buffer.append("LANGUAGE=").append(this.getLanguage().getCode());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette méthode permet d'exporter le jeu de préférences courant sous la forme
   * d'une racine de préférences
   * @return La racine des préférences exportées du jeu de préférence courant
   * @throws UserException Si un problème intervient lors de l'export des préférences
   * @throws ModelArgumentException Si un problème intervient lors de l'export des
   * préférences
   */
  public PreferenceRoot exportPreferences() throws UserException, ModelArgumentException
  {
    // Création de la racine des préférences
    PreferenceRoot root = new PreferenceRoot();
    // Export des préférences
    root.addProperty(KEY_LANGUAGE, this.getLanguage().getCode());
    return root;
  }
  /**
   * Cette méthode permet d'importer dans le jeu de préférences courant les préférences
   * précisées dans la racine en argument
   * @param root Racine des préférences à importer dans le jeu de préférences courant
   * @throws ProtectionException Si le jeu de préférences courant est protégé
   * @throws UserException Si l'une des préférences est invalide
   */
  public void importPreferences(PreferenceRoot root) throws ProtectionException, UserException
  {
    if(root != null)
    {
      // Import des préférences
      Preference preference = root.getProperty(KEY_LANGUAGE);
      if(preference != null)
      {
        this.defineLanguage(Bid4WinObjectType.getType(Language.class,
                                                      preference.getValue()));
      }
    }
  }
  /**
   * Cette méthode permet de définir la langue de préférence pour la communication
   * @param language Définition de la langue de préférence pour la communication
   * @throws ProtectionException Si le jeu de préférences courant est protégé
   * @throws UserException Si on défini une langue nulle
   */
  public void defineLanguage(Language language) throws ProtectionException, UserException
  {
    // Vérifie la protection du jeu de préférences courant
    this.checkProtection();
    this.setLanguage(UtilObject.checkNotNull("language", language,
                                             LanguageRef.LANGUAGE_MISSING_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la langue de préférence pour la communication
   * @return La langue de préférence pour la communication
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PREF_LANGUAGE", length = 2, nullable = false, unique = false)
  public Language getLanguage()
  {
    return this.language;
  }
  /**
   * Setter de la langue de préférence pour la communication
   * @param language Langue de préférence pour la communication à positionner
   */
  private void setLanguage(Language language)
  {
    this.language = language;
  }
}
