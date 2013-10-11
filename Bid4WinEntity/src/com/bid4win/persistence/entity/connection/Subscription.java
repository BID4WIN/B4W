package com.bid4win.persistence.entity.connection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.renderer.Bid4WinRenderer;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.account.AccountBasedKey;
import com.bid4win.persistence.entity.account.Account;

/**
 * Cette classe d�fini une r�f�rence d'inscription<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Entity
@Access(AccessType.FIELD)
public class Subscription extends AccountBasedKey<Subscription, Account>
{
  /** Date de validation de l'inscription */
  @Transient
  private Bid4WinDate validationDate = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  @SuppressWarnings("unused")
  private Subscription()
  {
    super();
  }
  /**
   * Constructeur
   * @param account Compte utilisateur de l'inscription
   * @throws UserException Si le compte utilisateur en argument est nul
   */
  public Subscription(Account account) throws UserException
  {
    super(account);
  }

  /**
   * Red�fini l'�quivalence interne de deux r�f�rences d'inscription sans prise
   * en compte de leurs relations afin d'y ajouter le test de leurs donn�es propres
   * @param toBeCompared {@inheritDoc}
   * @param identical {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#sameRelationNoneInternal(com.bid4win.commons.persistence.entity.Bid4WinEntity, boolean)
   */
  @Override
  protected boolean sameRelationNoneInternal(Subscription toBeCompared, boolean identical)
  {
    return super.sameRelationNoneInternal(toBeCompared, identical) &&
           Bid4WinComparator.getInstance().equals(this.getValidationDate(),
                                                  toBeCompared.getValidationDate());

  }
  /**
   * Permet d'effectuer le rendu simple de la r�f�rence d'inscription courante
   * sans prise en compte de ses relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#renderRelationNone()
   */
  @Override
  protected StringBuffer renderRelationNone()
  {
    // Effectue le rendu de base sans lien d'une entit�
    StringBuffer buffer = super.renderRelationNone();
    // Ajoute les �l�ments de la r�f�rence d'inscription
    buffer.append(" VALIDATION_DATE=" + Bid4WinRenderer.getInstance().render(this.getValidationDate()));
    // Retourne le rendu
    return buffer;
  }

  /**
   * Cette m�thode permet de savoir si l'inscription a �t� valid�e
   * @return True si l'inscription a �t� valid�e, false sinon
   */
  public boolean isValidated()
  {
    return this.getValidationDate() != null;
  }
  /**
   * Cette m�thode permet de d�finir la date de validation de l'inscription
   * @throws ProtectionException Si l'inscription courante est prot�g�e
   * @throws ModelArgumentException Si une date de validation est d�j� d�finie
   */
  public void defineValidationDate() throws ProtectionException, ModelArgumentException
  {
    // V�rifie la protection de l'inscription courante
    this.checkProtection();
    UtilObject.checkNull("validationDate", this.getValidationDate());
    this.setValidationDate(new Bid4WinDate());
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la date de validation de l'inscription
   * @return La date de validation de l'inscription
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "VALIDATION_DATE", length = 23, nullable = true, unique = false)
  public Bid4WinDate getValidationDate()
  {
    return this.validationDate;
  }
  /**
   * Setter de la date de validation de l'inscription
   * @param validationDate Date de validation de l'inscription � positionner
   */
  private void setValidationDate(Bid4WinDate validationDate)
  {
    this.validationDate = validationDate;
  }
}
