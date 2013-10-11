package trash.commons;
//package com.bid4win.commons.manager.property.abstraction;
//
//import java.util.Properties;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bid4win.commons.core.collection.Bid4WinSet;
//import com.bid4win.commons.core.exception.Bid4WinException;
//import com.bid4win.commons.core.exception.ModelArgumentException;
//import com.bid4win.commons.core.exception.PersistenceException;
//import com.bid4win.commons.core.exception.UserException;
//import com.bid4win.commons.entity.property.abstraction.PropertyAbstract;
//import com.bid4win.commons.entity.property.abstraction.PropertyRootAbstract;
//import com.bid4win.commons.manager.Bid4WinManager;
//import com.bid4win.commons.manager.exception.AuthenticationException;
//import com.bid4win.commons.persistence.entity.account.security.Role;
//
///**
// * Manager de gestion des propri�t�s incluant la gestion des transactions<BR>
// * <BR>
// * @param <PROPERTY> Type des propri�t�s g�r�es par le manager
// * @param <PROPERTY_ROOT> Type des propri�t�s racines des propri�t�s g�r�es par
// * le manager
// * @param <INTERNAL> Manager interne utilis� pour la gestion m�tier des propri�t�s<BR>
// * <BR>
// * @author Emeric Fill�tre
// */
//@Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
//public abstract class PropertyAbstractManager<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
//                                              PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
//                                              INTERNAL extends PropertyAbstractManagerInternal<PROPERTY, PROPERTY_ROOT, ?, ?>>
//       extends Bid4WinManager<PROPERTY, INTERNAL>
//{
//  /**
//   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�. Attention
//   * � ne pas acc�der aux sous-propri�t�s car celles-ci ne seront pas charg�es
//   * @param key Cl� de la propri�t� � r�cup�rer
//   * @return La propri�t� r�cup�r�e en fonction de sa cl�
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� demand�e est inexistante
//   */
//  public PROPERTY getProperty(String key) throws PersistenceException, UserException
//  {
//    // R�cup�re et retourne la propri�t�
//    return this.getInternal().getProperty(key);
//  }
//  
//  /**
//   * Cette m�thode permet de r�cup�rer une propri�t� compl�tement charg�e en fonction
//   * de sa cl�
//   * @param key Cl� de la propri�t� � r�cup�rer
//   * @return La propri�t� compl�tement charg�es r�cup�r�e en fonction de sa cl�
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� demand�e est inexistante
//   */
//  public PROPERTY getProperties(String key) throws PersistenceException, UserException
//  {
//    // R�cup�re, charge compl�tement et retourne la propri�t�
//    return this.getInternal().getProperty(key).loadRelation();
//  }
//
//  /**
//   * Cette m�thode permet de r�cup�rer toutes les propri�t�s du manager
//   * @return La liste de toutes les propri�t�s du manager
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  public Bid4WinSet<PROPERTY> getPropertySet()
//         throws PersistenceException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // R�cup�re la propri�t� racine et retourne le set de ses propri�t�s compl�tement charg�
//    return this.getInternal().getRoot().loadRelation().getPropertySet();
//  }
//  
//  /**
//   * Cette m�thode permet de r�cup�rer toutes les propri�t�s du manager
//   * @return Les propri�t�s du manager
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  public Properties getProperties() throws PersistenceException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // La transformation en propri�t�s chargera enti�rement la propri�t� racine
//    return this.getInternal().getRoot().toProperties();
//  }
//
//  /**
//   * Cette m�thode permet de modifier la valeur d'une propri�t�
//   * @param key Cl� de la propri�t� � modifier
//   * @param value La nouvelle valeur de la propri�t�
//   * @return La propri�t� une fois modifi�e
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws UserException Si la propri�t� � modifier est inexistante
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public PROPERTY updateProperty(String key, String value)
//         throws PersistenceException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Modifie la propri�t� et charge enti�rement ses relations
//    return this.getInternal().updateProperty(key, value).loadRelation();
//  }
//
//  /**
//   * Cette m�thode permet d'importer toutes les propri�t�s en param�tre. Celles
//   * d�j� existantes verront leur valeur mise � jour
//   * @param properties Propri�t�s � importer
//   * @throws RuntimeException Si un probl�me intervient lors de la validation de
//   * la transaction
//   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
//   * de la couche persistante
//   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
//   * des propri�t�s
//   * @throws UserException Si une cl� est invalide
//   * @throws AuthenticationException Si le niveau d'autorisation de l'utilisateur
//   * connect� n'est pas suffisant
//   */
//  @Transactional(readOnly = false)
//  public void importProperties(Properties properties)
//         throws PersistenceException, ModelArgumentException, UserException, AuthenticationException
//  {
//    // V�rifie le niveau d'autorisation de l'utilisateur connect�
//    this.checkRole(Role.ADMIN);
//    // Importe les propri�t�s en param�tre
//    this.getInternal().importProperties(properties);
//  }
//}
