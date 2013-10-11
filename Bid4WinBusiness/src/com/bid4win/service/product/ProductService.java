package com.bid4win.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.manager.product.ProductManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.locale.I18nGroup;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.price.Price;
import com.bid4win.persistence.entity.product.Product;
import com.bid4win.persistence.entity.product.UtilProduct;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion des produits et de leurs ressources associées incluant la
 * gestion des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("ProductService")
@Scope("singleton")
public class ProductService extends Bid4WinService_<SessionData, Account, ProductService>
{
  private static ProductService instance = null;
  public static ProductService getInstance()
  {
    return ProductService.instance;
  }


  /** Référence du manager de gestion des produits */
  @Autowired
  @Qualifier("ProductManager")
  private ProductManager manager= null;

  /**
   * Cette méthode permet de récupérer le produit en fonction de son identifiant.
   * Le chargement dans le magasin approprié des ses utilisations de ressource
   * sera vérifié et effectué le cas échéant
   * @param productId Identifiant du produit à récupérer
   * @return Le produit récupéré lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement défini par l'une des utilisations de
   * ressource est invalide
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Product loadProduct(String productId)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    return this.getManager().loadProduct(productId);
  }
  /**
   * Cette fonction permet de récupérer la liste des produits existants
   * @return La liste des produits existants sans leurs relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<Product> getProductList()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().getProductList();
  }
  /**
   * Cette fonction permet de récupérer la liste des produits existants dont le
   * le nom passe le filtre
   * @param searchedName Filtre à recherche dans le nom du produit
   * @param restrictions Restriction de langues pour lesquelles filtrer les produits
   * @return La liste des produits existants sans leurs relations filtrés sur leur
   * nom
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Bid4WinList<Product> getProductList(String searchedName, Language ... restrictions)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    Bid4WinList<Product> productList = this.self().getProductList();
    return UtilProduct.getFilteredProduct(productList, searchedName, restrictions);
  }
  /**
   * Cette méthode permet de créer le produit défini en argument
   * @param reference Référence du produit
   * @param names Nom du produit dans différentes langues dont celle définie par
   * défaut
   * @param summaries Résumé de la description du produit dans différentes langues
   * dont celle définie par défaut
   * @param price Prix du produit dans différentes monnaies dont celle définie par
   * défaut
   * @return Le produit créé
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si les paramètres du produit sont invalides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Product createProduct(String reference, I18nGroup names,
                               I18nGroup summaries, Price price)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().createProduct(reference, names, summaries, price);
  }
  /**
   * Cette méthode permet de modifier le produit défini par l'identifiant en argument.
   * Le chargement dans le magasin approprié des ses utilisations de ressource sera
   * vérifié et effectué le cas échéant
   * @param id Identifiant du produit à modifier
   * @param reference Nouvelle référence du produit
   * @param names Nouveau nom du produit dans différentes langues dont celle définie
   * par défaut
   * @param summaries Résumé de la description du produit dans différentes langues
   * dont celle définie par défaut
   * @param price Nouveau prix du produit dans différentes monnaies dont celle
   * définie par défaut
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si les paramètres du produit sont invalides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product updateProductWithResult(String id, String reference, I18nGroup names,
                                         I18nGroup summaries, Price price)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {

    this.self().updateProduct(id, reference, names, summaries, price);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(id);
  }
  /**
   * Cette méthode permet de modifier le produit défini par l'identifiant en argument
   * @param id Identifiant du produit à modifier
   * @param reference Nouvelle référence du produit
   * @param names Nouveau nom du produit dans différentes langues dont celle définie
   * par défaut
   * @param summaries Résumé de la description du produit dans différentes langues
   * dont celle définie par défaut
   * @param price Nouveau prix du produit dans différentes monnaies dont celle
   * définie par défaut
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si les paramètres du produit sont invalides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void updateProduct(String id, String reference, I18nGroup names,
                            I18nGroup summaries, Price price)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().updateProduct(id, reference, names, summaries, price);
  }
  /**
   * Cette méthode permet de supprimer le produit défini par l'identifiant en argument
   * @param id Identifiant du produit à supprimer
   * @return Le produit supprimé sans ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Product deleteProduct(String id)
         throws PersistenceException, NotFoundEntityException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().deleteProduct(id);
  }
  /**
   * Cette méthode permet d'ajouter une image à un produit. Le chargement dans le
   * magasin approprié des ses utilisations de ressource sera vérifié et effectué
   * le cas échéant
   * @param productId Identifiant du produit sur lequel ajouter l'image
   * @param storageId Identifiant du stockage sur lequel se basera l'image du produit
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage d'image
   * n'a pu être récupéré avec les identifiants en argument
   * @throws UserException Si l'emplacement défini par l'image est invalide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product addImageWithResult(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    this.self().addImage(productId, storageId);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(productId);
  }
  /**
   * Cette méthode permet d'ajouter une image à un produit
   * @param productId Identifiant du produit sur lequel ajouter l'image
   * @param storageId Identifiant du stockage sur lequel se basera l'image du produit
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage d'image
   * n'a pu être récupéré avec les identifiants en argument
   * @throws UserException Si l'emplacement défini par l'image est invalide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void addImage(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().addImage(productId, storageId);
  }
  /**
   * Modifie la position d'une image parmi celles d'un produit. Le chargement dans
   * le magasin approprié des ses utilisations de ressource sera vérifié et effectué
   * le cas échéant
   * @param productId Identifiant du produit de l'image à déplacer
   * @param oldPosition Ancienne position de l'image parmi celles du produit
   * @param newPosition Nouvelle position de l'image parmi celles du produit
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product moveImageWithResult(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    this.self().moveImage(productId, oldPosition, newPosition);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(productId);
  }
  /**
   * Modifie la position d'une image parmi celles d'un produit
   * @param productId Identifiant du produit de l'image à déplacer
   * @param oldPosition Ancienne position de l'image parmi celles du produit
   * @param newPosition Nouvelle position de l'image parmi celles du produit
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void moveImage(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().moveImage(productId, oldPosition, newPosition);
  }
  /**
   * Cette méthode permet de retirer une image d'un produit. Le chargement dans
   * le magasin approprié des ses utilisations de ressource sera vérifié et effectué
   * le cas échéant
   * @param productId Identifiant du produit duquel retirer l'image
   * @param usageId Identifiant de l'image à retirer
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si l'identifiant de l'image à retirer n'est pas référencé
   * par le produit
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product removeImageWithResult(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    this.self().removeImage(productId, usageId);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(productId);
  }
  /**
   * Cette méthode permet de retirer une image d'un produit
   * @param productId Identifiant du produit duquel retirer l'image
   * @param usageId Identifiant de l'image à retirer
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiant en argument
   * @throws UserException Si l'identifiant de l'image à retirer n'est pas référencé
   * par le produit
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void removeImage(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().removeImage(productId, usageId);
  }
  /**
   * Cette méthode permet d'ajouter une description à un produit. Le chargement dans
   * le magasin approprié des ses utilisations de ressource sera vérifié et effectué
   * le cas échéant
   * @param productId Identifiant du produit sur lequel ajouter la description
   * @param storageId Identifiant du stockage sur lequel se basera la description
   * du produit
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage de page
   * HTML n'a pu être récupéré avec les identifiants en argument
   * @throws UserException Si l'emplacement défini par la description est invalide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product addDescriptionWithResult(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    this.self().addDescription(productId, storageId);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(productId);
  }
  /**
   * Cette méthode permet d'ajouter une description à un produit
   * @param productId Identifiant du produit sur lequel ajouter la description
   * @param storageId Identifiant du stockage sur lequel se basera la description
   * du produit
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit ou aucun stockage de page
   * HTML n'a pu être récupéré avec les identifiants en argument
   * @throws UserException Si l'emplacement défini par la description est invalide
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void addDescription(String productId, long storageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().addDescription(productId, storageId);
  }
  /**
   * Modifie la position d'une description parmi celles d'un produit. Le chargement
   * dans le magasin approprié des ses utilisations de ressource sera vérifié et
   * effectué le cas échéant
   * @param productId Identifiant du produit de la description à déplacer
   * @param oldPosition Ancienne position de la description parmi celles du produit
   * @param newPosition Nouvelle position de la description parmi celles du produit
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product moveDescriptionWithResult(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    this.self().moveDescription(productId, oldPosition, newPosition);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(productId);
  }
  /**
   * Modifie la position d'une description parmi celles d'un produit
   * @param productId Identifiant du produit de la description à déplacer
   * @param oldPosition Ancienne position de la description parmi celles du produit
   * @param newPosition Nouvelle position de la description parmi celles du produit
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si les positions en argument ne sont pas valides
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void moveDescription(String productId, int oldPosition, int newPosition)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().moveDescription(productId, oldPosition, newPosition);
  }
  /**
   * Cette méthode permet de retirer une description d'un produit. Le chargement
   * dans le magasin approprié des ses utilisations de ressource sera vérifié et
   * effectué le cas échéant
   * @param productId Identifiant du produit duquel retirer la description
   * @param usageId Identifiant de la description à retirer
   * @return Le produit modifié lié à ses images et descriptions
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si l'identifiant donné pour la description à retirer
   * n'est pas référencé par le produit
   * @throws SessionException Si aucune session n'est définie
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  public Product removeDescriptionWithResult(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    this.self().removeDescription(productId, usageId);
    // Retourne le produit en s'assurant du chargement de ses utilisations de ressources
    return this.self().loadProduct(productId);
  }
  /**
   * Cette méthode permet de retirer une description d'un produit
   * @param productId Identifiant du produit duquel retirer la description
   * @param usageId Identifiant de la description à retirer
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucun produit n'a pu être récupéré avec
   * l'identifiants en argument
   * @throws UserException Si l'identifiant donné pour la description à retirer
   * n'est pas référencé par le produit
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void removeDescription(String productId, long usageId)
         throws PersistenceException, NotFoundEntityException, UserException,
                AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    this.getManager().removeDescription(productId, usageId);
  }

  /**
   * L'oscar du rôle d'administration des produits et ressources associées est
   * attribué aux gestionnaires métier
   * @return Le rôle associé à la gestion des produits et ressources associées
   * gérés par le manager
   */
  @Override
  public Role getAdminRole()
  {
    return Role.BIZ;
  }
  /**
   * Getter de la référence du manager de gestion des produits
   * @return La référence du manager de gestion des produits
   */
  @Override
  protected ProductManager getManager()
  {
    return this.manager;
  }
  /**
   *
   * TODO A COMMENTER
   * @param self {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean#setSelf(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  protected void setSelf(ProductService self)
  {
    super.setSelf(self);
    ProductService.instance = this.self();
  }
}
