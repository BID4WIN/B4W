package com.bid4win.commons.core.security;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.core.security.exception.ProtectionException;


/**
 * Cette classe permet de gérer une protection d'objet contre des utilisations non
 * permises<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ObjectProtector
{
  /** Identifiant de protection */
  private final static ThreadLocal<Bid4WinList<String>> PROTECTION_ID_LIST = new ThreadLocal<Bid4WinList<String>>();

  /**
   * Vérifie si le thread courant possède l'identifiant de protection en paramètre
   * @param protectionId Identifiant de protection à rechercher
   * @return True si le thread courant possède l'identifiant de protection en
   * paramètre, false sinon
   */
  public static boolean hasProtectionId(String protectionId)
  {
    Bid4WinList<String> protectionIdList = ObjectProtector.findProtectionIdList();
    return protectionIdList != null && protectionIdList.contains(protectionId);
  }

  /**
   * Cette méthode permet de démarrer la protection des objets pour le thread courant
   * avec un identifiant automatiquement généré
   * @return L'identifiant automatiquement généré à utiliser pour stopper la protection
   * des objets pour le thread courant
   */
  public static String startProtection()
  {
    String protectionId = IdGenerator.generateId(32);
    ObjectProtector.startProtection(protectionId);
    return protectionId;
  }
  /**
   * Cette méthode permet de démarrer la protection des objets pour le thread courant
   * @param protectionId Identifiant à utiliser pour la protection des objets
   */
  public static void startProtection(String protectionId)
  {
    Bid4WinList<String> protectionIdList = ObjectProtector.findProtectionIdList();
    if(protectionIdList == null)
    {
      protectionIdList = new Bid4WinList<String>();
      ObjectProtector.PROTECTION_ID_LIST.set(protectionIdList);
    }
    protectionIdList.add(protectionId);
  }
  /**
   * Cette méthode permet d'arrêter la protection des objets pour le thread courant
   * @param protectionId Identifiant utilisé pour la protection des objets
   * @throws ProtectionException Si l'identifiant de protection n'est pas valide
   */
  public static void stopProtection(String protectionId) throws ProtectionException
  {
    Bid4WinList<String> protectionIdList = ObjectProtector.findProtectionIdList();
    if(protectionIdList == null ||
       !Bid4WinComparator.getInstance().equals(protectionId, protectionIdList.getLast()))
    {
      ProtectionException.stopId();
    }
    else
    {
      if(protectionIdList.size() == 1)
      {
        ObjectProtector.PROTECTION_ID_LIST.remove();
      }
      else
      {
        protectionIdList.removeLast();
      }
    }
  }
  /**
   * Cette méthode permet de savoir si la protection des objets est démarrée
   * @return True si la protection des objets est démarrée, false sinon
   */
  public static boolean isProtectionStarted()
  {
    return ObjectProtector.findProtectionIdList() != null;
  }
  /**
   * Getter de l'identifiant de protection à utiliser pour le thread courant
   * @return L'identifiant de protection à utiliser pour le thread courant
   */
  protected static String getProtectionId()
  {
    Bid4WinList<String> protectionIdList = ObjectProtector.findProtectionIdList();
    if(protectionIdList == null)
    {
      return null;
    }
    return protectionIdList.getLast();
  }

  /**
   * Getter de la liste d'identifiants de protection définis pour le thread courant
   * @return La liste d'identifiants de protection définis pour le thread courant
   * ou null si aucun identifiant de protection n'a encore été défini
   */
  protected static Bid4WinList<String> findProtectionIdList()
  {
    return ObjectProtector.PROTECTION_ID_LIST.get();
  }
}
