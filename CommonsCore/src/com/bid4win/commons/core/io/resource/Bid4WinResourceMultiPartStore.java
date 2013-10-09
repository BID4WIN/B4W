package com.bid4win.commons.core.io.resource;

import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface représente la définition de tout magasin de gestion de stockage
 * de ressources divisées en portion. Les méthodes de manipulation devront donc
 * être définies afin de remplir le contrat<BR>
 * <BR>
 * @param <RESOURCE> Doit définir le type de ressources gérées<BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit définir la classe des types de portion de ressource<BR>
 * @param <PART> Doit définir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinResourceMultiPartStore<RESOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>,
                                               TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                               PART extends Bid4WinResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResourceStore<RESOURCE, TYPE>
{
  /**
   * Cette méthode doit permettre de savoir si la portion de ressource définie en
   * argument est référencée dans le magasin
   * @param part Définition de la portion de ressource à rechercher
   * @return True si la portion de ressource définie en argument est référencée
   * dans le magasin, false sinon
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public boolean exists(PART part) throws UserException;
  /**
   * Cette méthode doit permettre de récupérer la portion de ressource définie en
   * argument. Si celle-ci n'est pas référencée, la portion de ressource par défaut
   * sera récupérée
   * @param outputStream Portion de Ressource à fournir
   * @param part Définition de la portion de ressource à récupérer
   * @throws PersistenceException Si la portion de ressource (en argument ou par
   * défaut) n'existe pas ou correspond à un répertoire ou si un problème intervient
   * lors de son accès
   * @throws CommunicationException Si un problème intervient lors de l'ecriture
   * de la portion de ressource à fournir
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public void retreive(OutputStream outputStream, PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de récupérer la portion de ressource définie en
   * argument. Si celle-ci n'est pas référencée, la portion de ressource par défaut
   * sera récupérée
   * @param part Définition de la portion de ressource à récupérer
   * @return Portion de ressource récupérée
   * @throws PersistenceException Si la portion de ressource (en argument ou par
   * défaut) n'existe pas ou correspond à un répertoire ou si un problème intervient
   * lors de son accès
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à fournir
   * @throws UserException  Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public InputStream retreive(PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de stocker la portion de ressource définie en
   * argument
   * @param inputStream Portion de ressource à stocker
   * @param part Définition de la portion de ressource à stocker
   * @throws PersistenceException Si la portion de ressource est déjà référencée
   * (ou celle par défaut inexistante) ou si un problème intervient lors sa création
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à stocker
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public void store(InputStream inputStream, PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de remplacer la portion de ressource définie en
   * argument
   * @param inputStream Portion de ressource à stocker
   * @param part Définition de la portion de ressource à remplacer
   * @throws PersistenceException Si la portion de ressource n'existe pas ou correspond
   * à un répertoire ou si un problème intervient lors de son remplacement
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à stocker
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public void replace(InputStream inputStream, PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette méthode doit permettre de copier la portion de ressource définie en
   * argument
   * @param fromPart Définition de la portion de ressource à copier
   * @param toPart Définition de la portion de ressource cible
   * @throws PersistenceException Si la portion de ressource à copier n'existe pas
   * ou correspond à un répertoire ou si la portion de ressource cible est déjà
   * référencée (ou celle par défaut inexistante) ou ne peut être créée ou si un
   * problème intervient lors de la copie
   * @throws UserException Si l'emplacement défini par l'une des deux portions de
   * ressource est invalide ou si leur type n'est pas identique
   */
  public void copy(PART fromPart, PART toPart) throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de copier une portion de ressource à partir du
   * magasin défini en argument
   * @param <STORE> Définition du type de magasin de ressources à utiliser
   * @param <SOURCE> Définition du type de ressource à copier
   * @param store Définition du magasin de ressources à utiliser
   * @param source Définition de la ressource à copier
   * @param part Définition de la portion de ressource cible
   * @throws PersistenceException Si la ressource à copier n'existe pas ou correspond
   * à un répertoire ou si la portion de ressource cible est déjà référencée (ou
   * celle par défaut inexistante) ou ne peut être créée ou si un problème intervient
   * lors de la copie
   * @throws UserException Si l'emplacement défini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>, SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, PART part)
         throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de copier une ressource (ie toutes ses portions)
   * à partir du magasin défini en argument
   * @param <STORE> Définition du type de magasin de ressources à utiliser
   * @param <SOURCE> Définition du type de ressource à copier
   * @param <SOURCE_PART> Définition du type de portion des ressources à copier
   * @param store Définition du magasin de ressources à utiliser
   * @param source Définition de la ressource à copier
   * @param resource Définition de la ressource cible
   * @throws PersistenceException Si la ressource à copier n'existe pas ou correspond
   * à un répertoire ou si la ressource cible est déjà référencée ou ne peut être
   * créée ou si un problème intervient lors de la copie
   * @throws UserException Si l'emplacement défini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceMultiPartStore<SOURCE, TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE_PART extends Bid4WinResourcePart<SOURCE_PART, TYPE, PART_TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de copier une portion de ressource à partir du
   * magasin défini en argument
   * @param <STORE> Définition du type de magasin de ressources à utiliser
   * @param <SOURCE> Définition du type de ressource de la portion à copier
   * @param <SOURCE_PART> Définition du type de portion de ressource à copier
   * @param store Définition du magasin de ressources à utiliser
   * @param sourcePart Définition de la portion de ressource à copier
   * @param resourcePart Définition de la portion de ressource cible
   * @throws PersistenceException Si la ressource à copier n'existe pas ou correspond
   * à un répertoire ou si la ressource cible est déjà référencée (ou celle par
   * défaut inexistante) ou ne peut être créée ou si un problème intervient lors
   * de la copie
   * @throws UserException Si l'emplacement défini par l'une des deux portions de
   * ressource est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceMultiPartStore<SOURCE, TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE_PART extends Bid4WinResourcePart<SOURCE_PART, TYPE, PART_TYPE>>
         void copy(STORE store, SOURCE_PART sourcePart, PART resourcePart)
         throws PersistenceException, UserException;
  /**
   * Cette méthode doit permettre de supprimer la portion de ressource définie en
   * argument
   * @param part Définition de la portion de ressource à supprimer
   * @throws PersistenceException Si la portion de ressource à supprimer n'existe
   * pas ou correspond à un répertoire ou si un problème intervient lors de la
   * suppression
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public void remove(PART part) throws PersistenceException, UserException;
  /**
   * Getter du magasin de gestion de stockage des portions de ressource
   * @return Le magasin de gestion de stockage des portions de ressource
   */
  public Bid4WinResourceStore<PART, TYPE> getPartStore();

  /**
   * Getter du type de portion de ressource par défaut
   * @return Le type de portion de ressource par défaut
   */
  public PART_TYPE getPartTypeDefault();
  /**
   * Getter du set de types de portions de ressource autre que celui par défaut
   * @return Le set de types de portions de ressource autre que celui par défaut
   */
  public Bid4WinSet<PART_TYPE> getPartTypeSet();
  /**
   * Getter du set de tous les types de portions de ressource
   * @return Le set de tous les types de portions de ressource
   */
  public Bid4WinSet<PART_TYPE> getPartTypeSetFull();
}
