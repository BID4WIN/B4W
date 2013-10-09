package com.bid4win.commons.core.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.io.exception.Bid4WinIOWriteException;

/**
 * Classe de gestion de l'�criture d'un fichier<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinFileWriter
{
  /** Format � utiliser sous unix/linux */
  public static String FORMAT_UNIX = "UTF8";

  /** Reader utilis� en interne pour �crire dans le fichier concern� */
  private BufferedWriter writer = null;

  /**
   * Constructeur dont le format par d�faut dans le mode d'ex�cution courante (UNIX,
   * WINDOWS ...)
   * @param filename Nom complet du fichier � �crire
   * @throws PersistenceException Si le fichier ne peut �tre ouvert (ou cr��)
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
   * @param filename Nom complet du fichier � �crire
   * @param format Format d'�criture � utiliser
   * @throws PersistenceException Si le fichier ne peut �tre ouvert (ou cr��)
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
   * Cette m�thode permet d'ajouter une cha�ne de caract�res au fichier
   * @param string Cha�ne de caract�res � ajouter au fichier
   * @throws Bid4WinIOWriteException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet d'ajouter une ligne enti�re au fichier
   * @param line Ligne � ajouter au fichier
   * @throws Bid4WinIOWriteException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet de passer � la ligne suivante du fichier
   * @throws Bid4WinIOWriteException Si un probl�me intervient lors de la manipulation
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
   * Cette m�thode permet de fermer le fichier
   * @throws PersistenceException Si le fichier ne peut �tre ferm�
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
