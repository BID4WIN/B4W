package com.bid4win.commons.persistence.entity.core;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Cette classe d�fini une date pouvant �tre incluse dans des entit�s. En effet,
 * il semble impossible d'effectuer des requ�tes JPA se basant directement sur des
 * objets g�r�s gr�ce � des UserType. Mais si ceux-ci font partis d'un embeddable,
 * la requ�te est possible<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class EmbeddableDate extends Bid4WinEmbeddable<EmbeddableDate>
       implements Comparable<EmbeddableDate>
{
  /** Date embarqu�e */
  @Transient
  private Bid4WinDate date = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected EmbeddableDate()
  {
    super();
  }
  /**
   * Constructeur
   * @param date Date embarqu�e
   * @throws IllegalArgumentException Si les param�tres entr�s sont invalides
   */
  public EmbeddableDate(Bid4WinDate date) throws IllegalArgumentException
  {
    super();
    this.defineDate(date);
  }

  /**
   * Red�fini l'�galit� interne de deux identifiants de connexion par l'�galit�
   * de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(EmbeddableDate toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           Bid4WinComparator.getInstance().equals(this.getDate(), toBeCompared.getDate());
  }
  /**
   * Red�fini la transformation en cha�ne de caract�res d'un identifiant de connexion
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les �l�ments de l'identifiant de connexion
    buffer.append("DATE=" + this.getDate());
    // Retourne le rendu
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(EmbeddableDate toBeCompared)
  {
    return this.getDate().compareTo(toBeCompared.getDate());
  }

  /**
   * Cette m�thode permet de d�finir la date embarqu�e
   * @param date D�finition de la date embarqu�e
   * @throws ProtectionException Si l'objet courant est prot�g�
   * @throws IllegalArgumentException Si on d�fini une date embarqu�e nulle
   */
  private void defineDate(Bid4WinDate date) throws ProtectionException, IllegalArgumentException
  {
    // V�rifie la protection de l'objet courant
    this.checkProtection();
    if(date == null)
    {
      throw new IllegalArgumentException("Embedded date cannot be null");
    }
    this.setDate(date);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la date embarqu�e
   * @return La date embarqu�e
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "EMBEDDED_DATE", nullable = false, unique = false)
  public Bid4WinDate getDate()
  {
    return this.date;
  }
  /**
   * Setter de la date embarqu�e
   * @param date Date embarqu�e � positionner
   */
  private void setDate(Bid4WinDate date)
  {
    this.date= date;
  }
}
