package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.io.resource.Bid4WinResource;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityAutoID;

/**
 * Cette classe repr�sente une ressource accessible par le syst�me avec son emplacement
 * de stockage, son nom et son type<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Resource<CLASS extends Resource<CLASS, TYPE>,
                               TYPE extends ResourceType<TYPE>>
       extends Bid4WinEntityAutoID<CLASS> implements Bid4WinResource<TYPE>, Cloneable
{
  /** Emplacement de stockage de la ressource */
  @Transient private String path = null;
  /** Nom de la ressource */
  @Transient private String name = null;
  /** Type de la ressource */
  @Transient private TYPE type = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Resource()
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
  protected Resource(String path, String name, TYPE type) throws UserException
  {
    super();
    this.definePath(path);
    this.defineName(name);
    this.defineType(type);
  }

  /**
   * Red�fini l'�quivalence interne de deux ressources sans prise en compte de
   * leurs relations afin d'y ajouter le test de leurs emplacements de stockage
   * et noms et types
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(CLASS toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getPath(), toBeCompared.getPath()) &&
           Bid4WinComparator.getInstance().equals(this.getName(), toBeCompared.getName()) &&
           Bid4WinComparator.getInstance().equals(this.getType(), toBeCompared.getType());
  }
  /**
   * Permet d'effectuer le rendu simple de la ressource courante sans prise en
   * compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute la cl� et la valeur de la propri�t�
    try
    {
      buffer.append(" FULL_PATH=" + this.getFullPath());
    }
    catch(UserException ex)
    {
      buffer.append(" FULL_PATH=" + ex.getMessage());
    }
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
  protected MessageRef getMessageRefBase()
  {
    return ResourceRef.RESOURCE;
  }

  /**
   * Cette m�thode permet de d�finir l'emplacement de stockage de la ressource
   * @param path D�finition de l'emplacement de stockage de la ressource
   * @throws ProtectionException Si la ressource courante est prot�g�e
   * @throws UserException Si on d�fini un emplacement de stockage invalide
   */
  public void definePath(String path) throws ProtectionException, UserException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setPath(UtilFile.checkRelativePath(path, this.getMessageRef()));
  }
  /**
   * Cette m�thode permet de d�finir le nom de la ressource
   * @param name D�finition du nom de la ressource
   * @throws ProtectionException Si la ressource courante est prot�g�e
   * @throws UserException Si on d�fini un nom de ressource invalide
   */
  public void defineName(String name) throws ProtectionException, UserException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setName(UtilResource.checkName(name, this.getMessageRef()));
  }
  /**
   * Cette m�thode permet de d�finir le type de la ressource
   * @param type D�finition du type de la ressource
   * @throws ProtectionException Si la ressource courante est prot�g�e
   * @throws UserException Si on d�fini un type de ressource nul
   */
  public void defineType(TYPE type) throws ProtectionException, UserException
  {
    // V�rifie la protection de la ressource courante
    this.checkProtection();
    this.setType(UtilObject.checkNotNull("type", type,
                                         this.getMessageRef(MessageRef.SUFFIX_TYPE,
                                                            MessageRef.SUFFIX_MISSING_ERROR)));
  }
  /**
   * Cette m�thode permet de r�cup�rer l'emplacement de stockage r�el de la ressource
   * utilis� pour construire son chemin d'acc�s complet. Cette m�thode peut �tre
   * surcharg�e pour permettre de sp�cifier l'emplacement pr�cis�
   * @return L'emplacement de stockage r�el de la ressource utilis� pour construire
   * son chemin d'acc�s complet
   * @throws UserException Si l'emplacement de stockage r�el est invalide
   */
  @SuppressWarnings("unused")
  public String getRealPath() throws UserException
  {
    return this.getPath();
  }
  /**
   * Cette m�thode permet de r�cup�rer le nom r�el de stockage de la ressource
   * (sans son extension) utilis� pour construire son chemin d'acc�s complet.
   * Cette m�thode peut �tre surcharg�e pour permettre de sp�cifier le nom pr�cis�
   * @return Le nom r�el de stockage de la ressource (sans son extension) utilis�
   * pour construire son chemin d'acc�s complet
   */
  public String getRealName()
  {
    return this.getName();
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du champ permettant le for�age de la modification de la ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getUpdateForce()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "UPDATE_FORCE", length = 3, nullable = false, unique = false)
  public int getUpdateForce()
  {
    return super.getUpdateForce();
  }

  /**
   * Getter de l'emplacement de stockage de la ressource
   * @return L'emplacement de stockage de la ressource
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "PATH", length = 150, nullable = false, unique = false)
  public String getPath()
  {
    return this.path;
  }
  /**
   * Setter de l'emplacement de stockage de la ressource
   * @param path Emplacement de stockage de la ressource � positionner
   */
  private void setPath(String path)
  {
    this.path = path;
  }

  /**
   * Getter du nom de la ressource
   * @return Le nom de la ressource
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "NAME", length = 50, nullable = false, unique = false)
  public String getName()
  {
    return this.name;
  }
  /**
   * Setter du nom de la ressource
   * @param name Nom de la ressource � positionner
   */
  private void setName(String name)
  {
    this.name = name;
  }

  /**
   * Getter du type de la ressource
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResource#getType()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "RESOURCE_TYPE", nullable = false, unique = false)
  public TYPE getType()
  {
    return this.type;
  }
  /**
   * Setter du type de la ressource
   * @param type Type de la ressource � positionner
   */
  private void setType(TYPE type)
  {
    this.type = type;
  }

  /**
   * Cette m�thode permet de r�cup�rer le chemin d'acc�s complet � la ressource.
   * Il est construit en ajoutant le nom physique et l'extension correspondant au
   * type de la ressource � son emplacement de stockage physique
   * @return Le chemin d'acc�s complet � la ressource en minuscule
   * @throws UserException Si le chemin d'acc�s complet � la ressource est invalide
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "FULL_PATH", length = 250, nullable = false, unique = false)
  public String getFullPath() throws UserException
  {
    String name = UtilFile.addExtension(
        this.getRealName(), this.getType().getExtension(), this.getMessageRef());
    return UtilFile.concatRelativePath(
        this.getMessageRef(), this.getRealPath(), name).toLowerCase();
  }
  /**
   * Cette m�thode est seulement pr�sente pour ne pas rompre le contrat des beans
   * @param fullPath Param�tre non utilis�
   */
  @SuppressWarnings("unused")
  private void setFullPath(String fullPath)
  {
    // Seulement pr�sent pour ne pas rompre le contrat des beans
  }
}
