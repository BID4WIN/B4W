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
 * Cette classe d�fini la base de tout compte utilisateur du projet<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Constructeur pour cr�ation par introspection ou en proxy pour chargement diff�r�
   */
  protected AccountAbstract()
  {
    super();
  }
  /**
   * Constructeur bas� sur le g�n�rateur d'identifiants par d�faut
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en param�tre est nul
   */
  protected AccountAbstract(Credential credential, Email email) throws ModelArgumentException
  {
    super((IdPattern)null);
    this.defineCredential(credential);
    this.defineEmail(email);
  }
  /**
   * Constructeur utilisant l'identifiant en argument et pr�sent uniquement pour
   * certains tests
   * @param id Identifiant du compte utilisateur
   * @param credential Certificat de connexion du compte utilisateur
   * @param email Email du compte utilisateur
   * @throws ModelArgumentException Si le certificat de connexion ou l'email du
   * compte utilisateur en param�tre est nul
   */
  protected AccountAbstract(String id, Credential credential, Email email) throws ModelArgumentException
  {
    super(id);
    this.defineCredential(credential);
    this.defineEmail(email);
  }

  /**
   * Red�fini l'�quivalence interne de deux comptes utilisateur sans prise en compte
   * de leurs relations afin d'y ajouter le test de leurs donn�es propres
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
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments du compte utilisateur
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
   * Cette m�thode permet de savoir si le compte utilisateur poss�de le r�le en argument
   * @param role R�le dont on veut savoir s'il fait parti des r�les du compte utilisateur
   * @return True si le compte utilisateur poss�de le r�le en argument, false sinon
   */
  public boolean hasRole(Role role)
  {
    return this.getCredential().getRole().belongsTo(role);
  }

  /**
   * Cette m�thode permet de d�finir le certificat de connexion du compte utilisateur
   * @param credential D�finition du certificat de connexion du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est prot�g�
   * @throws ModelArgumentException Si on d�fini un certificat de connexion nul
   */
  private void defineCredential(Credential credential) throws ProtectionException, ModelArgumentException
  {
    // V�rifie la protection du compte utilisateur courant
    this.checkProtection();
    this.setCredential(UtilObject.checkNotNull("credential", credential));
  }
  /**
   * Cette m�thode permet de d�finir l'email du compte utilisateur
   * @param email D�finition de l'email du compte utilisateur
   * @throws ProtectionException Si le compte utilisateur courant est prot�g�
   * @throws ModelArgumentException Si on d�fini un email nul
   */
  private void defineEmail(Email email) throws ProtectionException, ModelArgumentException
  {
    // V�rifie la protection du compte utilisateur courant
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
   * @param credential Certificat de connexion du compte utilisateur � positionner
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
   * @param email Email du compte utilisateur � positionner
   */
  private void setEmail(Email email)
  {
    this.email = email;
  }
}
