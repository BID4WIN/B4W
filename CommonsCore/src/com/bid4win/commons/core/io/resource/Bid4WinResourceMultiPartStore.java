package com.bid4win.commons.core.io.resource;

import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;

/**
 * Cette interface repr�sente la d�finition de tout magasin de gestion de stockage
 * de ressources divis�es en portion. Les m�thodes de manipulation devront donc
 * �tre d�finies afin de remplir le contrat<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir le type de ressources g�r�es<BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * @param <PART_TYPE> Doit d�finir la classe des types de portion de ressource<BR>
 * @param <PART> Doit d�finir la classe des portions de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinResourceMultiPartStore<RESOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>,
                                               TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                               PART extends Bid4WinResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResourceStore<RESOURCE, TYPE>
{
  /**
   * Cette m�thode doit permettre de savoir si la portion de ressource d�finie en
   * argument est r�f�renc�e dans le magasin
   * @param part D�finition de la portion de ressource � rechercher
   * @return True si la portion de ressource d�finie en argument est r�f�renc�e
   * dans le magasin, false sinon
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public boolean exists(PART part) throws UserException;
  /**
   * Cette m�thode doit permettre de r�cup�rer la portion de ressource d�finie en
   * argument. Si celle-ci n'est pas r�f�renc�e, la portion de ressource par d�faut
   * sera r�cup�r�e
   * @param outputStream Portion de Ressource � fournir
   * @param part D�finition de la portion de ressource � r�cup�rer
   * @throws PersistenceException Si la portion de ressource (en argument ou par
   * d�faut) n'existe pas ou correspond � un r�pertoire ou si un probl�me intervient
   * lors de son acc�s
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la portion de ressource � fournir
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public void retreive(OutputStream outputStream, PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de r�cup�rer la portion de ressource d�finie en
   * argument. Si celle-ci n'est pas r�f�renc�e, la portion de ressource par d�faut
   * sera r�cup�r�e
   * @param part D�finition de la portion de ressource � r�cup�rer
   * @return Portion de ressource r�cup�r�e
   * @throws PersistenceException Si la portion de ressource (en argument ou par
   * d�faut) n'existe pas ou correspond � un r�pertoire ou si un probl�me intervient
   * lors de son acc�s
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � fournir
   * @throws UserException  Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public InputStream retreive(PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de stocker la portion de ressource d�finie en
   * argument
   * @param inputStream Portion de ressource � stocker
   * @param part D�finition de la portion de ressource � stocker
   * @throws PersistenceException Si la portion de ressource est d�j� r�f�renc�e
   * (ou celle par d�faut inexistante) ou si un probl�me intervient lors sa cr�ation
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public void store(InputStream inputStream, PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de remplacer la portion de ressource d�finie en
   * argument
   * @param inputStream Portion de ressource � stocker
   * @param part D�finition de la portion de ressource � remplacer
   * @throws PersistenceException Si la portion de ressource n'existe pas ou correspond
   * � un r�pertoire ou si un probl�me intervient lors de son remplacement
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public void replace(InputStream inputStream, PART part)
         throws PersistenceException, CommunicationException, UserException;
  /**
   * Cette m�thode doit permettre de copier la portion de ressource d�finie en
   * argument
   * @param fromPart D�finition de la portion de ressource � copier
   * @param toPart D�finition de la portion de ressource cible
   * @throws PersistenceException Si la portion de ressource � copier n'existe pas
   * ou correspond � un r�pertoire ou si la portion de ressource cible est d�j�
   * r�f�renc�e (ou celle par d�faut inexistante) ou ne peut �tre cr��e ou si un
   * probl�me intervient lors de la copie
   * @throws UserException Si l'emplacement d�fini par l'une des deux portions de
   * ressource est invalide ou si leur type n'est pas identique
   */
  public void copy(PART fromPart, PART toPart) throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de copier une portion de ressource � partir du
   * magasin d�fini en argument
   * @param <STORE> D�finition du type de magasin de ressources � utiliser
   * @param <SOURCE> D�finition du type de ressource � copier
   * @param store D�finition du magasin de ressources � utiliser
   * @param source D�finition de la ressource � copier
   * @param part D�finition de la portion de ressource cible
   * @throws PersistenceException Si la ressource � copier n'existe pas ou correspond
   * � un r�pertoire ou si la portion de ressource cible est d�j� r�f�renc�e (ou
   * celle par d�faut inexistante) ou ne peut �tre cr��e ou si un probl�me intervient
   * lors de la copie
   * @throws UserException Si l'emplacement d�fini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceStore<SOURCE, TYPE>, SOURCE extends Bid4WinResource<TYPE>>
         void copy(STORE store, SOURCE source, PART part)
         throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de copier une ressource (ie toutes ses portions)
   * � partir du magasin d�fini en argument
   * @param <STORE> D�finition du type de magasin de ressources � utiliser
   * @param <SOURCE> D�finition du type de ressource � copier
   * @param <SOURCE_PART> D�finition du type de portion des ressources � copier
   * @param store D�finition du magasin de ressources � utiliser
   * @param source D�finition de la ressource � copier
   * @param resource D�finition de la ressource cible
   * @throws PersistenceException Si la ressource � copier n'existe pas ou correspond
   * � un r�pertoire ou si la ressource cible est d�j� r�f�renc�e ou ne peut �tre
   * cr��e ou si un probl�me intervient lors de la copie
   * @throws UserException Si l'emplacement d�fini par l'une des deux ressources
   * est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceMultiPartStore<SOURCE, TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE_PART extends Bid4WinResourcePart<SOURCE_PART, TYPE, PART_TYPE>>
         void copy(STORE store, SOURCE source, RESOURCE resource)
         throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de copier une portion de ressource � partir du
   * magasin d�fini en argument
   * @param <STORE> D�finition du type de magasin de ressources � utiliser
   * @param <SOURCE> D�finition du type de ressource de la portion � copier
   * @param <SOURCE_PART> D�finition du type de portion de ressource � copier
   * @param store D�finition du magasin de ressources � utiliser
   * @param sourcePart D�finition de la portion de ressource � copier
   * @param resourcePart D�finition de la portion de ressource cible
   * @throws PersistenceException Si la ressource � copier n'existe pas ou correspond
   * � un r�pertoire ou si la ressource cible est d�j� r�f�renc�e (ou celle par
   * d�faut inexistante) ou ne peut �tre cr��e ou si un probl�me intervient lors
   * de la copie
   * @throws UserException Si l'emplacement d�fini par l'une des deux portions de
   * ressource est invalide ou si leur type n'est pas identique
   */
  public <STORE extends Bid4WinResourceMultiPartStore<SOURCE, TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, SOURCE_PART>,
          SOURCE_PART extends Bid4WinResourcePart<SOURCE_PART, TYPE, PART_TYPE>>
         void copy(STORE store, SOURCE_PART sourcePart, PART resourcePart)
         throws PersistenceException, UserException;
  /**
   * Cette m�thode doit permettre de supprimer la portion de ressource d�finie en
   * argument
   * @param part D�finition de la portion de ressource � supprimer
   * @throws PersistenceException Si la portion de ressource � supprimer n'existe
   * pas ou correspond � un r�pertoire ou si un probl�me intervient lors de la
   * suppression
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public void remove(PART part) throws PersistenceException, UserException;
  /**
   * Getter du magasin de gestion de stockage des portions de ressource
   * @return Le magasin de gestion de stockage des portions de ressource
   */
  public Bid4WinResourceStore<PART, TYPE> getPartStore();

  /**
   * Getter du type de portion de ressource par d�faut
   * @return Le type de portion de ressource par d�faut
   */
  public PART_TYPE getPartTypeDefault();
  /**
   * Getter du set de types de portions de ressource autre que celui par d�faut
   * @return Le set de types de portions de ressource autre que celui par d�faut
   */
  public Bid4WinSet<PART_TYPE> getPartTypeSet();
  /**
   * Getter du set de tous les types de portions de ressource
   * @return Le set de tous les types de portions de ressource
   */
  public Bid4WinSet<PART_TYPE> getPartTypeSetFull();
}
