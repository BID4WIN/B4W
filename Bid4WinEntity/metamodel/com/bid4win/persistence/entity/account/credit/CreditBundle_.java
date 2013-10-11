package com.bid4win.persistence.entity.account.credit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.persistence.entity.account.Account;

/**
 * Metamodel de la classe CreditBundle<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(CreditBundle.class)
public abstract class CreditBundle_ extends CreditBundleAbstract_
{
  /** D�finition de la provenance des cr�dits du lot */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<CreditBundle, CreditOrigin> origin;

  /** D�finition du lien vers le compte utilisateur du lot de cr�dits */
  public static volatile SingularAttribute<CreditBundle, Account> accountLink;
  /** D�finition de l'identifiant de l'historisation du lot de cr�dits */
  public static volatile SingularAttribute<CreditBundle, Long> historyId;
  /** D�finition de l'historisation du lot de cr�dits */
  public static volatile SingularAttribute<CreditBundle, CreditBundleHistory> history;
  /** D�finition du nombre courant de cr�dits du lot */
  public static volatile SingularAttribute<CreditBundle, Integer> currentNb;
  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec l'historisation du lot de cr�dits */
    CreditBundle_Relations.NODE_HISTORY.addNode(CreditBundleHistory_Relations.NODE_ACCOUNT);
    Bid4WinEntity_.stopProtection();
  }
}
