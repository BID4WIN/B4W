package com.bid4win.commons.core.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.io.exception.Bid4WinIOWriteException;

/**
 * Classe de gestion de l'écriture d'un fichier<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinFileWriter
{
  /** Format à utiliser sous unix/linux */
  public static String FORMAT_UNIX = "UTF8";

  /** Reader utilisé en interne pour écrire dans le fichier concerné */
  private BufferedWriter writer = null;

  /**
   * Constructeur dont le format par défaut dans le mode d'exécution courante (UNIX,
   * WINDOWS ...)
   * @param filename Nom complet du fichier à écrire
   * @throws PersistenceException Si le fichier ne peut être ouvert (ou créé)
   */
  public Bid4WinFileWriter(String filename) throws PersistenceException
  {
    try
    {
      FileOutputStream writer = new FileOutputStream(filename);
      this.writer = new BufferedWriter(new OutputStreamWriter(writer));
    }
    catch(IOException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Constructeur
   * @param filename Nom complet du fichier à écrire
   * @param format Format d'écriture à utiliser
   * @throws PersistenceException Si le fichier ne peut être ouvert (ou créé)
   */
  public Bid4WinFileWriter(String filename, String format) throws PersistenceException
  {
    try
    {
      FileOutputStream writer = new FileOutputStream(filename);
      this.writer = new BufferedWriter(new OutputStreamWriter(writer, format));
    }
    catch(IOException ex)
    {
      throw new PersistenceException(ex);
    }
  }

  /**
   * Cette méthode permet d'ajouter une chaîne de caractères au fichier
   * @param string Chaîne de caractères à ajouter au fichier
   * @throws Bid4WinIOWriteException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized void write(String string) throws Bid4WinIOWriteException
  {
    try
    {
      this.writer.write(string);
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOWriteException(ex);
    }
  }
  /**
   * Cette méthode permet d'ajouter une ligne entière au fichier
   * @param line Ligne à ajouter au fichier
   * @throws Bid4WinIOWriteException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized void writeLine(String line) throws Bid4WinIOWriteException
  {
    try
    {
      this.writer.write(line + "\n");
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOWriteException(ex);
    }
  }
  /**
   * Cette méthode permet de passer à la ligne suivante du fichier
   * @throws Bid4WinIOWriteException Si un problème intervient lors de la manipulation
   * du fichier
   */
  public synchronized void newLine() throws Bid4WinIOWriteException
  {
    try
    {
      this.writer.write("\n");
    }
    catch(IOException ex)
    {
      throw new Bid4WinIOWriteException(ex);
    }
  }

  /**
   * Cette méthode permet de fermer le fichier
   * @throws PersistenceException Si le fichier ne peut être fermé
   */
  public synchronized void close() throws PersistenceException
  {
    try
    {
      this.writer.close();
    }
    catch(IOException ex)
    {
      throw new PersistenceException(ex);
    }
  }
}
