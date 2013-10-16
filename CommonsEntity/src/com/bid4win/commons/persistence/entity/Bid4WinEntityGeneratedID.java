package com.bid4win.commons.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.core.security.IdPattern;

/**
 * Cette classe est la classe de base de toute entité du projet avec identifiant
 * construit grace à un générateur spécifique<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class Bid4WinEntityGeneratedID<CLASS extends Bid4WinEntityGeneratedID<CLASS>>
       extends Bid4WinEntity<CLASS, String>
{
  /** Pattern par défaut utilisé pour la génération des identifiants */
  public final static IdPattern ID_PATTERN = IdPattern.createNoCheck("BBBBBBBBBBBB");
  /**
   * Cette fonction permet de générer un identifiant en utilisant le pattern en
   * argument ou celui par défaut si aucun n'est fourni
   * @param pattern Pattern à utiliser pour la génération de l'identifiant
   * @return L'identifiant généré
   */
  public static String generateId(IdPattern pattern)
  {
    if(pattern == null)
    {
      pattern = Bid4WinEntityGeneratedID.ID_PATTERN;
    }
    return IdGenerator.generateId(pattern);
  }

  /**
   * Constructeur pour création par introspection
   */
  protected Bid4WinEntityGeneratedID()
  {
    super();
  }
  /**
   * Constructeur avec génération de l'identifiant unique de l'entité à partir du
   * pattern en argument ou de celui par défaut si aucun n'est fourni
   * @param pattern Pattern à utiliser pour la génération de l'identifiant
   */
  protected Bid4WinEntityGeneratedID(IdPattern pattern)
  {
    super(Bid4WinEntityGeneratedID.generateId(pattern));
  }
  /**
   * Constructeur avec précision de l'identifiant unique de l'entité
   * @param id Identifiant unique de l'entité à créer
   */
  protected Bid4WinEntityGeneratedID(String id)
  {
    super((UtilString.trimNotNull(id).equals(UtilString.EMPTY) ? null : UtilString.trimNotNull(id)));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de l'entité
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Id()
  @Column(name = "ID", length = 12, nullable = false, unique = true)
  public String getId()
  {
    return super.getId();
  }
}
