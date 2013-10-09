package com.bid4win.commons.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.exception.Bid4WinIOReadException;
import com.bid4win.commons.core.io.exception.Bid4WinIOWriteException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Classe utilitaire sur les fichiers et leur manipulation<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class UtilFile
{
  /** S�parateur des d�finitions d'emplacement de fichier */
  public static final String PATH_SEPARATOR = "/";
  /** S�parateur interne au nom de fichier */
  public static final String FILENAME_SEPARATOR = ".";
  /** Pattern des parties composant un nom de fichier entre deux de ses s�parateurs */
  public static final String FILENAME_PATTERN_PART =
      "[a-zA-Z0-9_\\Q-\\E]";
  /** Pattern du s�parateur interne au nom de fichier */
  public static final String FILENAME_PATTERN_SEPARATOR =
      "[\\Q" + UtilFile.FILENAME_SEPARATOR + "\\E]";
  /** Pattern des noms de fichier */
  public static final String FILENAME_PATTERN =
      UtilFile.FILENAME_PATTERN_SEPARATOR + "?" +
      "(" + UtilFile.FILENAME_PATTERN_PART + "+" + UtilFile.FILENAME_PATTERN_SEPARATOR + "{1}" + ")*" +
      UtilFile.FILENAME_PATTERN_PART + "+";
  /** Pattern des parties composant un emplacement de fichier entre deux de ses s�parateurs */
  public static final String PATH_PATTERN_PART =
      "(" + UtilFile.FILENAME_PATTERN_SEPARATOR + "{1}|(" + UtilFile.FILENAME_PATTERN + "){1})";
  /** Pattern du s�parateur interne � un emplacement de fichier */
  public static final String PATH_PATTERN_SEPARATOR =
      "[" + UtilFile.PATH_SEPARATOR + "]";
  /** Pattern des d�finitions d'emplacement de fichier */
  public static final String PATH_PATTERN =
      UtilFile.PATH_PATTERN_SEPARATOR + "{0,2}" +
      "(" + UtilFile.PATH_PATTERN_PART + "{1}" + UtilFile.PATH_PATTERN_SEPARATOR + "{1})*" +
      UtilFile.PATH_PATTERN_PART + "?" + UtilFile.PATH_PATTERN_SEPARATOR + "?";
  /** Pattern des d�finitions d'emplacement de fichier */
  public static final String ABSOLUTE_PATH_PATTERN =
      UtilFile.PATH_PATTERN_SEPARATOR + "{0,2}" +
      "([a-zA-Z]{1}:" + UtilFile.PATH_PATTERN_SEPARATOR+ ")?" +
      "(" + UtilFile.PATH_PATTERN_PART + "{1}" + UtilFile.PATH_PATTERN_SEPARATOR + "{1})*" +
      UtilFile.PATH_PATTERN_PART + "?" + UtilFile.PATH_PATTERN_SEPARATOR + "?";

  /**
   * Cette m�thode permet de nettoyer la d�finition d'emplacement relatif de fichier
   * en param�tre
   * @param path D�finition d'emplacement relatif de fichier � nettoyer
   * @return La d�finition d'emplacement relatif de fichier nettoy�e
   */
  protected static String cleanRelativePath(String path)
  {
    // Formate l'emplacement de fichier pour utiliser les bon s�parateurs
    path = UtilString.trimNotNull(path).replaceAll("[\\Q\\\\E]{1}", UtilFile.PATH_SEPARATOR);
    String douleSeparator = UtilFile.PATH_SEPARATOR + UtilFile.PATH_SEPARATOR;
    // Retire tous les doubles s�parateurs inutiles
    while(path.indexOf(douleSeparator) >= 0)
    {
      path = path.replace(douleSeparator, UtilFile.PATH_SEPARATOR);
    }
    // Retire le s�parateur qui se trouve en premi�re position de la d�finition
    // d'emplacement relatif
    if(path.startsWith(UtilFile.PATH_SEPARATOR))
    {
      path = path.substring(UtilFile.PATH_SEPARATOR.length(), path.length());
    }
    // Retire le s�parateur qui se trouve en derni�re position de la d�finition
    // d'emplacement relatif
    if(path.endsWith(UtilFile.PATH_SEPARATOR))
    {
      path = path.substring(0, path.lastIndexOf(UtilFile.PATH_SEPARATOR));
    }
    return path;
  }
  /**
   * Cette m�thode permet de nettoyer la d�finition d'emplacement absolu de fichier
   * en param�tre
   * @param path D�finition d'emplacement absolu de fichier � nettoyer
   * @return La d�finition d'emplacement absolu de fichier nettoy�e
   */
  protected static String cleanAbsolutePath(String path)
  {
    // Formate l'emplacement de fichier pour utiliser les bon s�parateurs
    path = UtilString.trimNotNull(path).replaceAll("[\\Q\\\\E]{1}", UtilFile.PATH_SEPARATOR);
    String douleSeparator = UtilFile.PATH_SEPARATOR + UtilFile.PATH_SEPARATOR;
    String start = "";
    // Conserve les s�parateurs qui se trouvent en premi�re position de la d�finition
    // d'emplacement absolu
    if(path.startsWith(douleSeparator))
    {
      start = douleSeparator;
    }
    else if(path.startsWith(UtilFile.PATH_SEPARATOR))
    {
      start = UtilFile.PATH_SEPARATOR;
    }
    return start + UtilFile.cleanRelativePath(path);
  }
  /**
   * Cette m�thode permet de cr�er l'emplacement relatif de fichier correspondant
   * � la suite d'emplacements relatifs donn�s
   * @param base Base des r�f�rences de message portant sur les ressources
   * @param paths D�finition de l'emplacement relatif de fichier � construire
   * @return L'emplacement relatif de fichier correspondant � la suite d'emplacements
   * relatifs donn�s
   * @throws UserException Si l'emplacement relatif de fichier construit ne respecte
   * pas le format attendu
   */
  public static String concatRelativePath(MessageRef base, String ... paths) throws UserException
  {
    String result = "";
    for(String path : paths)
    {
      if(result.length() != 0)
      {
        result += UtilFile.PATH_SEPARATOR;
      }
      result += UtilFile.cleanRelativePath(path);
    }
    return UtilFile.checkRelativePath(result, base);
  }
  /**
   * Cette m�thode permet de cr�er l'emplacement relatif de fichier correspondant
   * � la suite d'emplacements relatifs donn�s
   * @param base Base des r�f�rences de message portant sur les ressources
   * @param paths D�finition de l'emplacement relatif de fichier � construire
   * @return L'emplacement relatif de fichier correspondant � la suite d'emplacements
   * relatifs donn�s
   * @throws UserException Si l'emplacement relatif de fichier construit ne respecte
   * pas le format attendu
   */
  public static File concatRelativePathToFile(MessageRef base, String ... paths) throws UserException
  {
    return new File(UtilFile.concatRelativePath(base, paths));
  }
  /**
   * Cette m�thode permet de cr�er l'emplacement absolu de fichier correspondant
   * � la suite d'emplacements absolus donn�s
   * @param base Base des r�f�rences de message portant sur les ressources
   * @param paths D�finition de l'emplacement absolu de fichier � construire
   * @return L'emplacement absolu de fichier correspondant � la suite d'emplacements
   * absolus donn�s
   * @throws UserException Si l'emplacement absolu de fichier construit ne respecte
   * pas le format attendu
   */
  public static String concatAbsolutePath(MessageRef base, String ... paths) throws UserException
  {
    String result = "";
    for(String path : paths)
    {
      if(result.length() != 0)
      {
        result += UtilFile.PATH_SEPARATOR + UtilFile.cleanRelativePath(path);
      }
      else
      {
        result = UtilFile.cleanAbsolutePath(path);
      }
    }
    return UtilFile.checkAbsolutePath(result, base);
  }
  /**
   * Cette m�thode permet de cr�er l'emplacement absolu de fichier correspondant
   * � la suite d'emplacements absolus donn�s
   * @param base Base des r�f�rences de message portant sur les ressources
   * @param paths D�finition de l'emplacement absolu de fichier � construire
   * @return L'emplacement absolu de fichier correspondant � la suite d'emplacements
   * absolus donn�s
   * @throws UserException Si l'emplacement absolu de fichier construit ne respecte
   * pas le format attendu
   */
  public static File concatAbsolutePathToFile(MessageRef base, String ... paths) throws UserException
  {
    return new File(UtilFile.concatAbsolutePath(base, paths));
  }

  /**
   * Cette m�thode permet de retirer la partie correspondant � l'extension du nom
   * de fichier en argument
   * @param filename Nom de fichier � traiter
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return Le nom de fichier en argument sans son extension
   * @throws UserException Si le nom de fichier ne respecte pas le format attendu
   */
  public static String removeExtension(String filename, MessageRef base) throws UserException
  {
    filename = UtilFile.getFilename(filename, base);
    int index = filename.lastIndexOf(UtilFile.FILENAME_SEPARATOR);
    if(index > 0)
    {
      filename = filename.substring(0, index);
    }
    else if(index == 0)
    {
      filename = "";
    }
    return filename;
  }
  /**
   * Cette m�thode permet d'ajouter une extension au nom de fichier en argument
   * @param filename Nom de fichier � traiter
   * @param extension Extension � ajouter
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return Le nom de fichier en argument avec son extension
   * @throws UserException Si le nom de fichier ne respecte pas le format attendu
   */
  public static String addExtension(String filename, String extension, MessageRef base)
         throws UserException
  {
    filename = UtilFile.getFilename(filename, base);
    extension = UtilString.trimNotNull(extension);
    if(!extension.equals(""))
    {
      filename += UtilFile.FILENAME_SEPARATOR + extension;
    }
    return UtilFile.checkFilename(filename, base);
  }
  /**
   * Cette m�thode permet de r�cup�rer la partie correspondant � l'extension du
   * nom de fichier en argument
   * @param filename Nom de fichier � traiter
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return L'extension du nom de fichier en argument
   * @throws UserException Si le nom de fichier ne respecte pas le format attendu
   */
  public static String getExtension(String filename, MessageRef base) throws UserException
  {
    filename = UtilFile.getFilename(filename, base);
    int index = filename.lastIndexOf(UtilFile.FILENAME_SEPARATOR);
    if(index < 0 || index == filename.length() - UtilFile.FILENAME_SEPARATOR.length())
    {
      return "";
    }
    return filename.substring(index + UtilFile.FILENAME_SEPARATOR.length());
  }
  /**
   * Cette m�thode permet de r�cup�rer le nom du fichier � partir de la d�finition
   * de son emplacement
   * @param path D�finition de l'emplacement du fichier
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return Le nom du fichier correspondant � l'emplacement en argument
   * @throws UserException Si le nom de fichier ne respecte pas le format attendu
   */
  public static String getFilename(String path, MessageRef base) throws UserException
  {
    path = UtilFile.cleanRelativePath(path);
    int index = path.lastIndexOf(UtilFile.PATH_SEPARATOR);
    if(index > 0)
    {
      path = path.substring(index + UtilFile.PATH_SEPARATOR.length());
    }
    return UtilFile.checkFilename(path, base);
  }
  /**
   * Cette m�thode permet de r�cup�rer le chemin relatif du fichier � partir de
   * la d�finition de son emplacement relatif
   * @param path D�finition de l'emplacement relatif du fichier
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return Le chemin relatif du fichier correspondant � l'emplacement relatif
   * en argument
   * @throws UserException Si le chemin relatif du fichier ne respecte pas le format
   * attendu
   */
  public static String getRelativePath(String path, MessageRef base) throws UserException
  {
    path = UtilFile.cleanRelativePath(path);
    int index = path.lastIndexOf(UtilFile.PATH_SEPARATOR);
    if(index > 0)
    {
      path = path.substring(0, index);
    }
    return UtilFile.checkRelativePath(path, base);
  }
  /**
   * Cette m�thode permet de tester si le nom de fichier en param�tre est valide
   * @param filename Nom de fichier � tester
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return Le nom de fichier test� et nettoy�
   * @throws UserException Si le nom de fichier ne respecte pas le format attendu
   */
  public static String checkFilename(String filename, MessageRef base) throws UserException
  {
    filename = UtilString.trimNotNull(filename);
    return UtilString.checkPattern("filename", filename, UtilFile.FILENAME_PATTERN,
                                   base.getSubMessageRef(MessageRef.SUFFIX_NAME,
                                                         MessageRef.SUFFIX_INVALID_ERROR),
                                   1);
  }
  /**
   * Cette m�thode permet de tester si la d�finition d'emplacement relatif de fichier
   * en param�tre est valide
   * @param path D�finition d'emplacement relatif de fichier � tester
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return La d�finition d'emplacement relatif de fichier test�e et nettoy�e
   * @throws UserException Si la d�finition d'emplacement relatif de fichier ne
   * respecte pas le format attendu
   */
  public static String checkRelativePath(String path, MessageRef base) throws UserException
  {
    path = UtilFile.cleanRelativePath(path);
    return UtilString.checkPattern("path", path, UtilFile.PATH_PATTERN,
                                   base.getSubMessageRef(MessageRef.SUFFIX_PATH,
                                                         MessageRef.SUFFIX_INVALID_ERROR),
                                   1);
  }
  /**
   * Cette m�thode permet de tester si la d�finition d'emplacement relatif de fichier
   * en param�tre est valide
   * @param path D�finition d'emplacement relatif de fichier � tester
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return La d�finition d'emplacement relatif de fichier test�e et nettoy�e
   * @throws UserException Si la d�finition d'emplacement relatif de fichier ne
   * respecte pas le format attendu
   */
  public static File checkRelativePathToFile(String path, MessageRef base) throws UserException
  {
    return new File(UtilFile.checkRelativePath(path, base));
  }
  /**
   * Cette m�thode permet de tester si la d�finition d'emplacement absolu de fichier
   * en param�tre est valide
   * @param path D�finition d'emplacement absolu de fichier � tester
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return La d�finition d'emplacement absolu de fichier test�e et nettoy�e
   * @throws UserException Si la d�finition d'emplacement absolu de fichier ne
   * respecte pas le format attendu
   */
  public static String checkAbsolutePath(String path, MessageRef base) throws UserException
  {
    path = UtilFile.cleanAbsolutePath(path);
    return UtilString.checkPattern("path", path, UtilFile.ABSOLUTE_PATH_PATTERN,
                                   base.getSubMessageRef(MessageRef.SUFFIX_PATH,
                                                         MessageRef.SUFFIX_INVALID_ERROR),
                                   1);
  }
  /**
   * Cette m�thode permet de tester si la d�finition d'emplacement absolu de fichier
   * en param�tre est valide
   * @param path D�finition d'emplacement absolu de fichier � tester
   * @param base Base des r�f�rences de message portant sur les ressources
   * @return La d�finition d'emplacement absolu de fichier test�e et nettoy�e
   * @throws UserException Si la d�finition d'emplacement absolu de fichier ne
   * respecte pas le format attendu
   */
  public static File checkAbsolutePathToFile(String path, MessageRef base) throws UserException
  {
    return new File(UtilFile.checkAbsolutePath(path, base));
  }
  /**
   * Cette m�thode permet de cr�er le fichier d�fini en argument ainsi que son
   * arborescence si n�cessaire
   * @param file D�finition du fichier � cr�er
   * @return True seulement si le fichier n'existait pas et a pu �tre cr��
   * @throws PersistenceException Si un probl�me intervient lors de la cr�ation
   * du fichier ou de son arborescence
   */
  public static boolean createFile(File file) throws PersistenceException
  {
    try
    {
      // Cr�e l'arborescence si n�cessaire
      file.getParentFile().mkdirs();
      // Cr�e le fichier et retourne true si le fichier n'existait pas d�j�
      return file.createNewFile();
    }
    catch(Throwable th)
    {
      throw new PersistenceException(th);
    }
  }
  /**
   * Cette m�thode permet de supprimer le fichier d�fini en argument ainsi que son
   * arborescence tant que celle-ci est vide
   * @param file D�finition du fichier � supprimer
   * @return True seulement si le fichier existait et a pu �tre supprim�
   * @throws PersistenceException Si un probl�me intervient lors de la suppression
   * du fichier
   */
  public static boolean deleteFile(File file) throws PersistenceException
  {
    try
    {
      // On supprime le fichier et son arborescence tant qu'elle est vide
      if(file.delete())
      {
        UtilFile.deleteFile(file.getParentFile());
        return true;
      }
      // La suppression a �chou�
      return false;
    }
    catch(Throwable th)
    {
      // Si le probl�me a eu lieu sur l'arborescence, on ne le remonte pas
      if(file.exists() && file.isFile())
      {
        throw new PersistenceException(th);
      }
      return false;
    }
  }
  /**
   * Cette m�thode permet de copier un flux de lecture dans un flux d'�criture
   * @param inputStream Flux de lecture � copier dans le flux d'�criture
   * @param outputStream Flux d'�criture � copier du flux de lecture
   * @return Le nombre de bytes copi�s
   * @throws Bid4WinIOReadException Si un probl�me intervient sur le flux de lecture
   * @throws Bid4WinIOWriteException Si un probl�me intervient sur le flux d'�criture
   */
  public static int copy(InputStream inputStream, OutputStream outputStream)
         throws Bid4WinIOReadException, Bid4WinIOWriteException
  {
    if(inputStream == null)
    {
      throw new Bid4WinIOReadException("Input stream from which to copy should not be null");
    }
    if(outputStream == null)
    {
      throw new Bid4WinIOWriteException("Output stream to which to copy should not be null");
    }
    int byteCount = 0;
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
    try
    {
      while((bytesRead = inputStream.read(buffer)) != -1)
      {
        try
        {
          outputStream.write(buffer, 0, bytesRead);
        }
        catch(IOException ex)
        {
          throw new Bid4WinIOWriteException(ex);
        }
        byteCount += bytesRead;
      }
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
    try
    {
      outputStream.flush();
      return byteCount;
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOWriteException(ex);
    }
  }
  /**
   * Cette m�thode permet de copier un fichier
   * @param source Fichier � copier
   * @param destination Fichier destination
   * @throws Bid4WinIOReadException Si un probl�me intervient sur la lecture du
   * fichier � copier
   * @throws Bid4WinIOWriteException Si un probl�me intervient sur l'�criture du
   * fichier � copier
   */
  public static void copy(File source, File destination)
         throws Bid4WinIOReadException, Bid4WinIOWriteException
  {
    try
    {
      InputStream inputStream = new FileInputStream(source);
      try
      {
        try
        {
          OutputStream outputStream = new FileOutputStream(destination);
          try
          {
            UtilFile.copy(inputStream, outputStream);
          }
          finally
          {
            outputStream.close();
          }
        }
        catch(IOException ex)
        {
          throw new Bid4WinIOWriteException(ex);
        }
      }
      finally
      {
        inputStream.close();
      }
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Cette m�thode permet de vider enti�rement le r�pertoire en argument
   * @param directory R�pertoire � vider
   * @return True si le fichier correspond bien � un repertoire existant et a pu
   * �tre vid�, false sinon
   */
  public static boolean empty(File directory)
  {
    if(!directory.isDirectory())
    {
      return false;
    }
    boolean result = true;
    for(File included : directory.listFiles())
    {
      if(!UtilFile.removeAll(included))
      {
        result = false;
      }
    }
    return result;
  }
  /**
   * Cette m�thode permet de supprimer le fichier en argument. S'il s'agit d'un
   * repertoire, son contenu sera lui aussi r�cursivement supprim�
   * @param file Fichier � supprimer
   * @return True si le fichier a bien pu �tre supprim�, false sinon (Dans le cas
   * d'un r�pertoire, il se peut alors qu'une partie seulement des fichiers contenus
   * ait �t� supprim�e)
   */
  public static boolean removeAll(File file)
  {
    if(file.isDirectory())
    {
      for(File included : file.listFiles())
      {
        UtilFile.removeAll(included);
      }
    }
    return file.delete();
  }

  /**
   * Cette m�thode permet de r�cup�rer la hi�rarchie de sous-r�pertoires � partir
   * d'un emplacement donn�. Une map vide sera retourn�e si l'emplacement en param�tre
   * ne correspond pas � un r�pertoire ou n'existe pas
   * @param absoluteParentPath D�finition de l'emplacement absolu � partir duquel
   * r�cup�rer la hi�rarchie de sous-r�pertoires
   * @return La hi�rarchie de sous-r�pertoires � partir de l'emplacement donn�
   * @throws UserException Si l'emplacement en argument est invalide
   */
  public static Bid4WinStringRecursiveMap getSubdirectories(String absoluteParentPath)
         throws UserException
  {
    File directory = UtilFile.checkAbsolutePathToFile(absoluteParentPath,
                                                      ResourceRef.RESOURCE);
    if(!directory.exists() || !directory.isDirectory())
    {
      return new Bid4WinStringRecursiveMap(0);
    }
    // R�cup�ration des sous-r�pertoires
    Bid4WinStringRecursiveMap subDirectories = new Bid4WinStringRecursiveMap();
    for(File file : directory.listFiles())
    {
      if(file.isDirectory())
      {
        String subDirectory = UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                                          absoluteParentPath,
                                                          file.getName());
        subDirectories.put(file.getName(), UtilFile.getSubdirectories(subDirectory));
      }
    }
    return subDirectories;
  }
  /**
   * Cette m�thode permet de construire la hi�rarchie correspondant aux sous-r�pertoires
   * en argument
   * @param relativePathList Sous-r�pertoires dont on souhaite obtenir la hi�rarchie
   * @return La hi�rarchie correspondant aux sous-r�pertoires en argument
   * @throws UserException Si l'un des emplacements en argument est invalide ou
   * ne fait pas parti TODO
   */
  public static Bid4WinStringRecursiveMap computeSubdirectories(Bid4WinList<String> relativePathList)
         throws UserException
  {
    return UtilFile.computeSubdirectories(relativePathList, "");
  }
  /**
   * Cette m�thode permet de construire la hi�rarchie correspondant aux sous-r�pertoires
   * en argument calcul�e � partir de parent donn�
   * @param relativePathList Sous-r�pertoires dont on souhaite obtenir la hi�rarchie
   * @param relativePathParent Path parent � partir duquel r�cup�rer la hi�rarchie
   * de sous-r�pertoires
   * @return La hi�rarchie correspondant aux sous-r�pertoires en argument calcul�e
   * � partir de parent donn�
   * @throws UserException Si l'un des emplacements en argument est invalide ou
   * ne fait pas parti TODO
   */
  public static Bid4WinStringRecursiveMap computeSubdirectories(Bid4WinList<String> relativePathList,
                                                                String relativePathParent)
         throws UserException
  {
    Bid4WinStringRecursiveMap subDirectories = new Bid4WinStringRecursiveMap();
    for(String path : relativePathList)
    {
      if(!path.startsWith(relativePathParent))
      {
        throw new UserException(ResourceRef.RESOURCE_PATH_INVALID_ERROR);
      }
      UtilFile.computeSubdirectories(subDirectories, path.substring(relativePathParent.length()));
    }
    return subDirectories;
  }
  /**
   * Cette m�thode permet de construire la hi�rarchie correspondant au sous-r�pertoire
   * en argument et de la positionner dans celle en argument
   * @param subDirectories Hi�rarchie
   * @param relativePath TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public static Bid4WinStringRecursiveMap computeSubdirectories(Bid4WinStringRecursiveMap subDirectories,
                                                                String relativePath)
         throws UserException
  {
    File file = UtilFile.checkRelativePathToFile(relativePath, ResourceRef.RESOURCE);
    if(file.getName().equals(""))
    {
      return new Bid4WinStringRecursiveMap(0);
    }
    File parent = file.getParentFile();
    Bid4WinStringRecursiveMap result = new Bid4WinStringRecursiveMap();
    if(parent != null)
    {
      subDirectories = UtilFile.computeSubdirectories(subDirectories, parent.getPath());
    }
    return subDirectories.add(file.getName(), result);
  }
}
