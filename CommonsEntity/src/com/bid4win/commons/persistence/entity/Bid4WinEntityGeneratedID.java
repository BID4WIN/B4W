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
 * Cette classe est la classe de base de toute entit� du projet avec identifiant
 * construit grace � un g�n�rateur sp�cifique<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class Bid4WinEntityGeneratedID<CLASS extends Bid4WinEntityGeneratedID<CLASS>>
       extends Bid4WinEntity<CLASS, String>
{
  /** Pattern par d�faut utilis� pour la g�n�ration des identifiants */
  public final static IdPattern ID_PATTERN = IdPattern.createNoCheck("BBBBBBBBBBBB");
  /**
   * Cette fonction permet de g�n�rer un identifiant en utilisant le pattern en
   * argument ou celui par d�faut si aucun n'est fourni
   * @param pattern Pattern � utiliser pour la g�n�ration de l'identifiant
   * @return L'identifiant g�n�r�
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
   * Constructeur pour cr�ation par introspection
   */
  protected Bid4WinEntityGeneratedID()
  {
    super();
  }
  /**
   * Constructeur avec g�n�ration de l'identifiant unique de l'entit� � partir du
   * pattern en argument ou de celui par d�faut si aucun n'est fourni
   * @param pattern Pattern � utiliser pour la g�n�ration de l'identifiant
   */
  protected Bid4WinEntityGeneratedID(IdPattern pattern)
  {
    super(Bid4WinEntityGeneratedID.generateId(pattern));
  }
  /**
   * Constructeur avec pr�cision de l'identifiant unique de l'entit�
   * @param id Identifiant unique de l'entit� � cr�er
   */
  protected Bid4WinEntityGeneratedID(String id)
  {
    super((UtilString.trimNotNull(id).equals(UtilString.EMPTY) ? null : UtilString.trimNotNull(id)));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de l'entit�
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
