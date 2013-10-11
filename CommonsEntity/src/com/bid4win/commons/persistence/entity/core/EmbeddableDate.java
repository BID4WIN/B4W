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
 * Cette classe défini une date pouvant être incluse dans des entités. En effet,
 * il semble impossible d'effectuer des requêtes JPA se basant directement sur des
 * objets gérés grâce à des UserType. Mais si ceux-ci font partis d'un embeddable,
 * la requête est possible<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Embeddable
@Access(AccessType.FIELD)
public class EmbeddableDate extends Bid4WinEmbeddable<EmbeddableDate>
       implements Comparable<EmbeddableDate>
{
  /** Date embarquée */
  @Transient
  private Bid4WinDate date = null;

  /**
   * Constructeur pour création par introspection
   */
  protected EmbeddableDate()
  {
    super();
  }
  /**
   * Constructeur
   * @param date Date embarquée
   * @throws IllegalArgumentException Si les paramètres entrés sont invalides
   */
  public EmbeddableDate(Bid4WinDate date) throws IllegalArgumentException
  {
    super();
    this.defineDate(date);
  }

  /**
   * Redéfini l'égalité interne de deux identifiants de connexion par l'égalité
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
   * Redéfini la transformation en chaîne de caractères d'un identifiant de connexion
   * lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    // Effectue le rendu de base d'un objet
    StringBuffer buffer = super.render();
    // Ajoute les éléments de l'identifiant de connexion
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
   * Cette méthode permet de définir la date embarquée
   * @param date Définition de la date embarquée
   * @throws ProtectionException Si l'objet courant est protégé
   * @throws IllegalArgumentException Si on défini une date embarquée nulle
   */
  private void defineDate(Bid4WinDate date) throws ProtectionException, IllegalArgumentException
  {
    // Vérifie la protection de l'objet courant
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
   * Getter de la date embarquée
   * @return La date embarquée
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "EMBEDDED_DATE", nullable = false, unique = false)
  public Bid4WinDate getDate()
  {
    return this.date;
  }
  /**
   * Setter de la date embarquée
   * @param date Date embarquée à positionner
   */
  private void setDate(Bid4WinDate date)
  {
    this.date= date;
  }
}
