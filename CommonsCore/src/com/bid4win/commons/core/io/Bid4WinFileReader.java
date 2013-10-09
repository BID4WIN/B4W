package com.bid4win.commons.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.io.exception.Bid4WinIOReadException;

/**
 * Classe de gestion de la lecture d'un fichier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinFileReader
{
  /** Reader utilisé en interne pour lire dans le fichier concerné */
  private BufferedReader reader = null;

  /**
   * Constructeur
   * @param file Fichier à lire
   * @throws PersistenceException Si le fichier ne peut être trouvé
   */
  public Bid4WinFileReader(File file) throws PersistenceException
  {
    this(file.getAbsolutePath());
  }
  /**
   * Constructeur
   * @param filename Nom complet du fichier à lire
   * @throws PersistenceException Si le fichier ne peut être trouvé
   */
  public Bid4WinFileReader(String filename) throws PersistenceException
  {
    try
    {
      FileReader reader = new FileReader(filename);
      this.reader = new BufferedReader(reader);
    }
    catch(FileNotFoundException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Permet de positionner un marqueur de la position actuelle dans le fichier afin
   * d'y revenir dans le cas d'un reset
   * @param readAheadLimit Nombre de caractères lus à partir de la position du marqueur
   * avant l'invalidation de ce dernier
   * @throws IllegalArgumentException Si la limite fournie est négative
   * @throws Bid4WinIOReadException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized void mark(int readAheadLimit) throws Bid4WinIOReadException, IllegalArgumentException
  {
    try
    {
      this.reader.mark(readAheadLimit);
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Permet de revenir à la dernière position marquée du fichier
   * @throws Bid4WinIOReadException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized void reset() throws Bid4WinIOReadException
  {
    try
    {
      this.reader.reset();
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Cette méthode permet de lire une ligne entière du fichier
   * @return La ligne lue dans le fichier ou null si la fin du fichier est atteinte
   * @throws Bid4WinIOReadException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized String readLine() throws Bid4WinIOReadException
  {
    try
    {
      return this.reader.readLine();
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOReadException(ex);
    }
  }

  /**
   * Cette méthode permet de lire entièrement le fichier sous la forme de lignes
   * @return La liste des lignes lues dans le fichier
   * @throws Bid4WinIOReadException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized Bid4WinList<String> readAll() throws Bid4WinIOReadException
  {
    Bid4WinList<String> lineList = new Bid4WinList<String>();
    String line;
    while((line = this.readLine()) != null)
    {
      lineList.add(line);
    }
    return lineList;
  }

  /**
   * Cette méthode permet de fermer le fichier
   * @throws PersistenceException Si le fichier ne peut être fermé
   */
  public synchronized void close() throws PersistenceException
  {
    try
    {
      this.reader.close();
    }
    catch(IOException ex)
    {
      throw new PersistenceException(ex);
    }
  }
}
