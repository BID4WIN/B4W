package com.bid4win.commons.core.io.resource;

import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface repr�sente la d�finition de tout magasin de gestion de stockage
 * de ressources. Les m�thodes de manipulation devront donc �tre d�finies afin de
 * remplir le contrat<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir le type de ressources g�r�es<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinResourceStore<RESOURCE extends Bid4WinResource<TYPE>, TYPE>
{
  /**
   * Cette m�thode doit permettre de savoir si la ressource d�finie en argument
   * est r�f�renc�e dans le magasin
   * @param resource D�finition de la ressource � rechercher
   * @return True si la ressource d�finie en argument est r�f�renc�e dans le magasin,
   * false sinon
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public boolean exists(RESOURCE resource) throws UserException;
  /**
   * Cette m�thode doit permettre de r�cup�rer la ressource d�finie en argument
   * @param outputStream Ressource � fournir
   * @param resource D�finition de la ressource � r�cup�rer
   * @throws PersistenceException Si la ressource n'existe pas ou correspond � un
   * r�pertoire ou si un probl�me intervient lors de son acc�s
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public void retreive(OutputStream outputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de r�cup�rer la ressource d�finie en argument
   * @param resource D�finition de la ressource � r�cup�rer
   * @return Ressource r�cup�r�e
   * @throws PersistenceException Si la ressource n'existe pas ou correspond � un
   * r�pertoire ou si un probl�me intervient lors de son acc�s
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � fournir
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public InputStream retreive(RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de stocker la ressource d�finie en argument
   * @param inputStream Ressource � stocker
   * @param resource D�finition de la ressource � stocker
   * @throws PersistenceException Si la ressource est d�j� r�f�renc�e ou si un
   * probl�me intervient lors sa cr�ation
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public void store(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de remplacer la ressource d�finie en argument
   * @param inputStream Ressource � stocker
   * @param resource D�finition de la ressource � remplacer
   * @throws PersistenceException Si la ressource n'existe pas ou correspond � un
   * r�pertoire ou si un probl�me intervient lors de son remplacement
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public void replace(InputStream inputStream, RESOURCE resource)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de copier la ressource d�finie en argument
   * @param from D�finition de la ressource � copier
   * @param to D�finition de la ressource cible
   * @throws PersistenceException Si la ressource � copier n'existe pas ou correspond
   * � un r�pertoire ou si la ressource cible est d�j� r�f�renc�e ou ne peut �tre
   * cr��e ou si un probl�me intervient lors de la copie
   * @throws UserException Si l'emplacement d�fini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public void copy(RESOURCE from, RESOURCE to) throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de copier une ressource � partir du magasin d�fini
   * en argument
   * @param <STORE> D�finition du type de magasin de ressources � utiliser
   * @param <SOURCE> D�finition du type de ressource � copier
   * @param store D�finition du magasin de ressources � utiliser
   * @param source D�finition de la ressource � copier
   * @param resource D�finition de la ressource cible
   * @throws PersistenceException Si la ressource � copier n'existe pas ou correspond
   * � un r�pertoire ou si la ressource cible est d�j� r�f�renc�e ou ne peut �tre
   * cr��e ou si un probl�me intervient lors de la copie
   * @throws UserException Si l'emplacement d�fini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>,
          SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de d�placer la ressource d�finie en argument
   * @param from D�finition de la ressource � d�placer
   * @param to D�finition de la ressource cible
   * @throws PersistenceException Si la ressource � d�placer n'existe pas ou correspond
   * � un r�pertoire ou si la ressource cible est d�j� r�f�renc�e ou ne peut �tre
   * cr��e ou si un probl�me intervient lors du d�placement
   * @throws UserException Si l'emplacement d�fini par l'une des deux ressources
   * est invalide
   */
  public void move(RESOURCE from, RESOURCE to) throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de supprimer la ressource d�finie en argument
   * @param resource D�finition de la ressource � supprimer
   * @throws PersistenceException Si la ressource � supprimer n'existe pas ou
   * correspond � un r�pertoire ou si un probl�me intervient lors de la suppression
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   */
  public void remove(RESOURCE resource) throws PersistenceException, UserException;
}
