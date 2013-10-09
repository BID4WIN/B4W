package com.bid4win.commons.core.io.resource;

import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface représente la définition de tout magasin de gestion de stockage
 * de ressources. Les méthodes de manipulation devront donc être définies afin de
 * remplir le contrat<BR>
 * <BR>
 * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinResourceStore<RESOURCE extends Bid4WinResource<TYPE>, TYPE>
{
  /**
   * Cette méthode doit permettre de savoir si la ressource définie en argument
   * est référencée dans le magasin
   * @param resource Définition de la ressource à rechercher
   * @return True si la ressource définie en argument est référencée dans le magasin,
   * false sinon
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public boolean exists(RESOURCE resource) throws UserException;
  /**
   * Cette méthode doit permettre de récupérer la ressource définie en argument
   * @param outputStream Ressource à fournir
   * @param resource Définition de la ressource à récupérer
   * @throws PersistenceException Si la ressource n'existe pas ou correspond à un
   * répertoire ou si un problème intervient lors de son accès
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la ressource à fournir
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public void retreive(OutputStream outputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de récupérer la ressource définie en argument
   * @param resource Définition de la ressource à récupérer
   * @return Ressource récupérée
   * @throws PersistenceException Si la ressource n'existe pas ou correspond à un
   * répertoire ou si un problème intervient lors de son accès
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à fournir
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public InputStream retreive(RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de stocker la ressource définie en argument
   * @param inputStream Ressource à stocker
   * @param resource Définition de la ressource à stocker
   * @throws PersistenceException Si la ressource est déjà référencée ou si un
   * problème intervient lors sa création
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public void store(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de remplacer la ressource définie en argument
   * @param inputStream Ressource à stocker
   * @param resource Définition de la ressource à remplacer
   * @throws PersistenceException Si la ressource n'existe pas ou correspond à un
   * répertoire ou si un problème intervient lors de son remplacement
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la ressource à stocker
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public void replace(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de copier la ressource définie en argument
   * @param from Définition de la ressource à copier
   * @param to Définition de la ressource cible
   * @throws PersistenceException Si la ressource à copier n'existe pas ou correspond
   * à un répertoire ou si la ressource cible est déjà référencée ou ne peut être
   * créée ou si un problème intervient lors de la copie
   * @throws UserException Si l'emplacement défini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public void copy(RESOURCE from, RESOURCE to) throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de copier une ressource à partir du magasin défini
   * en argument
   * @param <STORE> Définition du type de magasin de ressources à utiliser
   * @param <SOURCE> Définition du type de ressource à copier
   * @param store Définition du magasin de ressources à utiliser
   * @param source Définition de la ressource à copier
   * @param resource Définition de la ressource cible
   * @throws PersistenceException Si la ressource à copier n'existe pas ou correspond
   * à un répertoire ou si la ressource cible est déjà référencée ou ne peut être
   * créée ou si un problème intervient lors de la copie
   * @throws UserException Si l'emplacement défini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>,
          SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de déplacer la ressource définie en argument
   * @param from Définition de la ressource à déplacer
   * @param to Définition de la ressource cible
   * @throws PersistenceException Si la ressource à déplacer n'existe pas ou correspond
   * à un répertoire ou si la ressource cible est déjà référencée ou ne peut être
   * créée ou si un problème intervient lors du déplacement
   * @throws UserException Si l'emplacement défini par l'une des deux ressources
   * est invalide
   */
  public void move(RESOURCE from, RESOURCE to) throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de supprimer la ressource définie en argument
   * @param resource Définition de la ressource à supprimer
   * @throws PersistenceException Si la ressource à supprimer n'existe pas ou
   * correspond à un répertoire ou si un problème intervient lors de la suppression
   * @throws UserException Si l'emplacement défini par la ressource est invalide
   */
  public void remove(RESOURCE resource) throws PersistenceException, UserException;
}
