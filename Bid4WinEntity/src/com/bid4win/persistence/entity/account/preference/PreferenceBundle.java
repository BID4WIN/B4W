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
 * Cette classe d�fini d�fini le jeu de pr�f�rences d'un compte utilisateur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class PreferenceBundle extends Bid4WinEmbeddable<PreferenceBundle>
{
  /** Cl� de la pr�f�rence de langue pour la communication */
  public final static String KEY_LANGUAGE = "language";

  /** Langue de pr�f�rence pour la communication */
  @Transient private Language language = Language.DEFAULT;

  /**
   * Constructeur par d�faut
   */
  public PreferenceBundle()
  {
    super();
  }
  /**
   * Constructeur avec pr�cision d'une liste de pr�f�rences � utiliser
   * @param root Racine de la liste de pr�f�rences � utiliser
   * @throws UserException Si l'une des pr�f�rences est invalide
   */
  public PreferenceBundle(PreferenceRoot root) throws UserException
  {
    super();
    this.importPreferences(root);
  }

  /**
   * Red�fini l'�galit� interne de deux jeux de pr�f�rences par l'�galit� de leur
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
   * Red�fini la transformation en cha�ne de caract�res d'un jeu de pr�f�rence
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments du jeux de pr�f�rences
    buffer.append("LANGUAGE=").append(this.getLanguage().getCode());
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet d'exporter le jeu de pr�f�rences courant sous la forme
   * d'une racine de pr�f�rences
   * @return La racine des pr�f�rences export�es du jeu de pr�f�rence courant
   * @throws UserException Si un probl�me intervient lors de l'export des pr�f�rences
   * @throws ModelArgumentException Si un probl�me intervient lors de l'export des
   * pr�f�rences
   */
  public PreferenceRoot exportPreferences() throws UserException, ModelArgumentException
  {
    // Cr�ation de la racine des pr�f�rences
    PreferenceRoot root = new PreferenceRoot();
    // Export des pr�f�rences
    root.addProperty(KEY_LANGUAGE, this.getLanguage().getCode());
    return root;
  }
  /**
   * Cette m�thode permet d'importer dans le jeu de pr�f�rences courant les pr�f�rences
   * pr�cis�es dans la racine en argument
   * @param root Racine des pr�f�rences � importer dans le jeu de pr�f�rences courant
   * @throws ProtectionException Si le jeu de pr�f�rences courant est prot�g�
   * @throws UserException Si l'une des pr�f�rences est invalide
   */
  public void importPreferences(PreferenceRoot root) throws ProtectionException, UserException
  {
    if(root != null)
    {
      // Import des pr�f�rences
      Preference preference = root.getProperty(KEY_LANGUAGE);
      if(preference != null)
      {
        this.defineLanguage(Bid4WinObjectType.getType(Language.class,
                                                      preference.getValue()));
      }
    }
  }
  /**
   * Cette m�thode permet de d�finir la langue de pr�f�rence pour la communication
   * @param language D�finition de la langue de pr�f�rence pour la communication
   * @throws ProtectionException Si le jeu de pr�f�rences courant est prot�g�
   * @throws UserException Si on d�fini une langue nulle
   */
  public void defineLanguage(Language language) throws ProtectionException, UserException
  {
    // V�rifie la protection du jeu de pr�f�rences courant
    this.checkProtection();
    this.setLanguage(UtilObject.checkNotNull("language", language,
                                             LanguageRef.LANGUAGE_MISSING_ERROR));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la langue de pr�f�rence pour la communication
   * @return La langue de pr�f�rence pour la communication
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PREF_LANGUAGE", length = 2, nullable = false, unique = false)
  public Language getLanguage()
  {
    return this.language;
  }
  /**
   * Setter de la langue de pr�f�rence pour la communication
   * @param language Langue de pr�f�rence pour la communication � positionner
   */
  private void setLanguage(Language language)
  {
    this.language = language;
  }
}
