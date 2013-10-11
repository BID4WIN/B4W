package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.account.Account;

/**
 * Metamodel de la classe CreditBundle<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(CreditBundle.class)
public abstract class CreditBundle_ extends CreditBundleAbstract_
{
  /** Définition de la provenance des crédits du lot */
  // Suite à un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si défini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<CreditBundle, CreditOrigin> origin;

  /** Définition du lien vers le compte utilisateur du lot de crédits */
  public static volatile SingularAttribute<CreditBundle, Account> accountLink;
  /** Définition de l'identifiant de l'historisation du lot de crédits */
  public static volatile SingularAttribute<CreditBundle, Long> historyId;
  /** Définition de l'historisation du lot de crédits */
  public static volatile SingularAttribute<CreditBundle, CreditBundleHistory> history;
  /** Définition du nombre courant de crédits du lot */
  public static volatile SingularAttribute<CreditBundle, Integer> currentNb;
  // Définition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** Défini la profondeur du noeud de relation existant avec l'historisation du lot de crédits */
    CreditBundle_Relations.NODE_HISTORY.addNode(CreditBundleHistory_Relations.NODE_ACCOUNT);
    Bid4WinEntity_.stopProtection();
  }
}
