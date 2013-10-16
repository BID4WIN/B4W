package com.bid4win.persistence.entity.account;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEntity_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract_;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;
import com.bid4win.persistence.entity.account.credit.CreditBundle;
import com.bid4win.persistence.entity.account.credit.CreditBundle_Relations;
import com.bid4win.persistence.entity.account.credit.CreditInvolvement_Relations;
import com.bid4win.persistence.entity.account.credit.auction.CreditInvolvementNormal;
import com.bid4win.persistence.entity.account.preference.PreferenceBundle;

/**
 * Metamodel de la classe Account<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(Account.class)
public abstract class Account_ extends AccountAbstract_
{
  /** D�finition du certificat de connexion du compte utilisateur */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<Account, Credential> credential;
  /** D�finition de l'email du compte utilisateur */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<Account, Email> email;


  /** D�finition des informations sur l'utilisateur du compte */
  public static volatile SingularAttribute<Account, User> user;
  /** D�finition du jeu de pr�f�rence du compte utilisateur */
  public static volatile SingularAttribute<Account, PreferenceBundle> preferenceBundle;

  /** D�finition du parrain du compte utilisateur */
  public static volatile SingularAttribute<Account, Account> sponsor;

  /** D�finition du nombre de cr�dits disponibles pour le compte utilisateur */
  public static volatile SingularAttribute<Account, Integer> creditNb;
  /** D�finition du nombre de cr�dits utilis�s par le compte utilisateur */
  public static volatile SingularAttribute<Account, Integer> usedCreditNb;
  /** D�finition de la liste de lots de cr�dits du compte utilisateur */
  public static volatile ListAttribute<Account, CreditBundle> creditBundleListDatabase;
  /** D�finition de la map des implications de cr�dits dans des ventes aux ench�res normales */
  public static volatile MapAttribute<Account, String, CreditInvolvementNormal> involvementNormalMapDatabase;

  // D�finition de la profondeur des relations
  static
  {
    Bid4WinEntity_.startProtection();
    /** D�fini la profondeur du noeud de relation existant avec les lots de cr�dits */
    Account_Relations.NODE_CREDIT_BUNDLE_LIST.addNode(CreditBundle_Relations.NODE_ACCOUNT);
    Account_Relations.NODE_CREDIT_BUNDLE_LIST.addNode(CreditBundle_Relations.NODE_ACCOUNT_LINK);
    /** D�fini la profondeur du noeud de relation existant avec la map des implications de cr�dits dans des ventes aux ench�res normales */
    Account_Relations.NODE_INVOLVEMENT_NORMAL_MAP.addNode(CreditInvolvement_Relations.NODE_ACCOUNT);
    Bid4WinEntity_.stopProtection();
  }
}
