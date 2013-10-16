package com.bid4win.commons.persistence.entity.account;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinObjectComparator;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.AccountRef;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityGeneratedID;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Cette classe défini la base de tout compte utilisateur du projet<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AccountAbstract<CLASS extends AccountAbstract<CLASS>>
       extends Bid4WinEntityGeneratedID<CLASS>
{
  /** Certificat de connexion du compte utilisateur */
  @Transient private Credential credential = null;
  /** Email du compte utilisateur */
  @Transient private Email email = null;

  /**
   * Constructeur pour création par introspection ou en proxy pour chargement différé
   */
  protected AccountAbstract()
  {
    super();
  }
  /**
   * Constructeur basé sur le générateur d'identifiants par défaut
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en paramètre est nul
   */
  protected AccountAbstract(Credential credential, Email email) throws ModelArgumentException
  {
    super((IdPattern)null);
    this.defineCredential(credential);
    this.defineEmail(email);
  }
  /**
   * Constructeur utilisant l'identifiant en argument et présent uniquement pour
   * certains tests
   * @param id Identifiant du compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en paramètre est nul
   */
  protected AccountAbstract(String id, Credential credential, Email email) throws ModelArgumentException
  {
    super(id);
    this.defineCredential(credential);
    this.defineEmail(email);
  }

  /**
   * Redéfini l'équivalence interne de deux comptes utilisateur sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs données propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getEmail(),
                                                              toBeCompared.getEmail()) &&
           Bid4WinObjectComparator.getInstanceObject().equals(this.getCredential(),
                                                              toBeCompared.getCredential());

  }
  /**
   * Permet d'effectuer le rendu simple du compte utilisateur courant sans prise
   * en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entité
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les éléments du compte utilisateur
    buffer.append(" ").append(this.getCredential().render());
    buffer.append(" ").append(this.getEmail().render());
    // Retourne le rendu
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getMessageRefBase()
   */
  @Override
  public MessageRef getMessageRefBase()
  {
    return AccountRef.ACCOUNT;
  }

  /**
   * Cette méthode permet de savoir si le compte utilisateur possède le rôle en argument
   * @param role Rôle dont on veut savoir s'il fait parti des rôles du compte utilisateur
   * @return True si le compte utilisateur possède le rôle en argument, false sinon
   */
  public boolean hasRole(Role role)
  {
    return this.getCredential().getRole().belongsTo(role);
  }

  /**
   * Cette méthode permet de définir le certificat de connexion du compte utilisateur
   * @param credential Définition du certificat de connexion du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est protégé
   * @throws ModelArgumentException Si on défini un certificat de connexion nul
   */
  private void defineCredential(Credential credential) throws ProtectionException, ModelArgumentException
  {
    // Vérifie la protection du compte utilisateur courant
    this.checkProtection();
    this.setCredential(UtilObject.checkNotNull("credential", credential));
  }
  /**
   * Cette méthode permet de définir l'email du compte utilisateur
   * @param email Définition de l'email du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est protégé
   * @throws ModelArgumentException Si on défini un email nul
   */
  private void defineEmail(Email email) throws ProtectionException, ModelArgumentException
  {
    // Vérifie la protection du compte utilisateur courant
    this.checkProtection();
    this.setEmail(UtilObject.checkNotNull("email", email));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du certificat de connexion du compte utilisateur
   * @return Le certificat de connexion du compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public Credential getCredential()
  {
    return this.credential;
  }
  /**
   * Setter du certificat de connexion du compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur à positionner
   */
  private void setCredential(Credential credential)
  {
    this.credential = credential;
  }

  /**
   * Getter de l'email du compte utilisateur
   * @return L'email du compte utilisateur
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Embedded()
  public Email getEmail()
  {
    return this.email;
  }
  /**
   * Setter de l'email du compte utilisateur
   * @param email Email du compte utilisateur à positionner
   */
  private void setEmail(Email email)
  {
    this.email = email;
  }
}
