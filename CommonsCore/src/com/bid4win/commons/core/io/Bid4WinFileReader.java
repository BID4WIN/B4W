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
 * @author Emeric Fill�tre
 */
public class Bid4WinFileReader
{
  /** Reader utilis� en interne pour lire dans le fichier concern� */
  private BufferedReader reader = null;

  /**
   * Constructeur
   * @param file Fichier � lire
   * @throws PersistenceException Si le fichier ne peut �tre trouv�
   */
  public Bid4WinFileReader(File file) throws PersistenceException
  {
    this(file.getAbsolutePath());
  }
  /**
   * Constructeur
   * @param filename Nom complet du fichier � lire
   * @throws PersistenceException Si le fichier ne peut �tre trouv�
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
   * @param readAheadLimit Nombre de caract�res lus � partir de la position du marqueur
   * avant l'invalidation de ce dernier
   * @throws IllegalArgumentException Si la limite fournie est n�gative
   * @throws Bid4WinIOReadException Si un probl�me intervient lors de la manipulation
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
   * Permet de revenir � la derni�re position marqu�e du fichier
   * @throws Bid4WinIOReadException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet de lire une ligne enti�re du fichier
   * @return La ligne lue dans le fichier ou null si la fin du fichier est atteinte
   * @throws Bid4WinIOReadException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet de lire enti�rement le fichier sous la forme de lignes
   * @return La liste des lignes lues dans le fichier
   * @throws Bid4WinIOReadException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet de fermer le fichier
   * @throws PersistenceException Si le fichier ne peut �tre ferm�
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
