package com.bid4win.commons.core.io.resource;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;

/**
 * Cette classe est la classe de base pour valider les tests des magasins de ressources<BR>
 * <BR>
 * @param <RESOURCE> D�finition des ressources � valider<BR>
 * @param <TYPE> D�finition de la classe de type des ressources � valider<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinResourceValidator<RESOURCE extends Bid4WinResource<TYPE>,
                                               TYPE>
{
  /**
   * Cette m�thode permet de valider que la ressource en argument correspond bien
   * au r�sultat attendu
   * @param testPath Repertoire de test
   * @param expected R�sultat attendu
   * @param resource Ressource � valider
   * @throws Exception Probl�me lors de la validation de la ressource
   */
  public void assertEquals(String testPath, File expected, RESOURCE resource) throws Exception
  {
    // R�cup�ration de la ressource dans le magasin de donn�es
    File result = this.getResultFile(testPath, "result.file");
    if(!result.getParentFile().exists())
    {
      result.getParentFile().mkdirs();
    }
    FileOutputStream outputStream = new FileOutputStream(result);
    try
    {
      this.getStore().retreive(outputStream, resource);
    }
    finally
    {
      outputStream.close();
    }
    assertTrue("Wrong result for resource", this.isSame(expected, result));
  }
  /**
   * Cette m�thode permet de comparer les deux fichiers en arguments
   * @param expected Fichier de r�f�rence
   * @param result Fichier � comparer � la r�f�rence
   * @return True si les fichier sont les m�me, false sinon
   * @throws IOException Si un probl�me intervient lors de la lecture d'un des
   * deux fichiers
   */
  protected boolean isSame(File expected, File result) throws IOException
  {
    // Validation de la ressource
    FileInputStream inputStream1 = new FileInputStream(expected);
    try
    {
      FileInputStream inputStream2 = new FileInputStream(result);
      try
      {
        return this.isSame(inputStream1, inputStream2);
      }
      finally
      {
        inputStream2.close();
      }
    }
    finally
    {
      inputStream1.close();
    }
  }
  /**
   * Cette m�thode permet de comparer les deux flux en arguments
   * @param expected Flux de r�f�rence
   * @param input Flux � comparer � la r�f�rence
   * @return True si les flux sont les m�me, false sinon
   * @throws IOException Si un probl�me intervient lors de la lecture d'un des
   * deux flux
   */
  protected boolean isSame(InputStream expected, InputStream input) throws IOException
  {
    byte[] buffer1 = new byte[1024];
    byte[] buffer2 = new byte[1024];
    int numRead1 = 0;
    int numRead2 = 0;
    while(true)
    {
      numRead1 = expected.read(buffer1);
      numRead2 = input.read(buffer2);
      if(numRead1 > -1)
      {
        if(numRead2 != numRead1)
        {
          return false;
        }
        // Otherwise same number of bytes read
        if(!Arrays.equals(buffer1, buffer2))
        {
          return false;
        // Otherwise same bytes read, so continue ...
        }
      }
      else
      {
        // Nothing more in stream 1 ...
        return numRead2 < 0;
      }
    }
  }

  /**
   * Getter du r�pertoire de r�sultat
   * @param testPath Repertoire de test
   * @return Le r�pertoire de r�sultat
   * @throws UserException Si l'emplacement du r�pertoire de r�sultat est invalide
   */
  protected String getResultPath(String testPath) throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE, testPath, "result");
  }
  /**
   * Getter du fichier de r�sultat d�fini en argument
   * @param testPath Repertoire de test
   * @param filename Nom du fichier de r�sultat � r�cup�rer
   * @return Le fichier de r�sultat d�fini en argument
   * @throws UserException Si l'emplacement du fichier de r�sultat est invalide
   */
  protected File getResultFile(String testPath, String filename) throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE, this.getResultPath(testPath), filename);
  }

  /**
   * Cette m�thode permet de nettoyer compl�tement le magasin des ressources �
   * valider ainsi que le r�pertoire de r�sultat
   * @param testPath Repertoire de test
   * @throws Bid4WinException Si un probl�me intervient lors du nettoyage
   */
  public void cleanAll(String testPath) throws Bid4WinException
  {
    this.cleanResult(testPath);
    this.cleanStore();
  }
  /**
   * Cette m�thode permet de nettoyer le r�pertoire de r�sultat
   * @param testPath Repertoire de test
   * @throws UserException Si l'emplacement du r�pertoire de r�sultat est invalide
   */
  private void cleanResult(String testPath) throws UserException
  {
    UtilFile.removeAll(new File(this.getResultPath(testPath)));
  }
  /**
   * Cette m�thode doit compl�tement nettoyer le magasin des ressources � valider
   * @throws Bid4WinException Si un probl�me intervient lors du nettoyage du magasin
   * des ressources � valider
   */
  protected abstract void cleanStore() throws Bid4WinException;

  /**
   * Getter du magasin des ressources � valider
   * @return Le magasin des ressources � valider
   */
  public abstract Bid4WinResourceStore<RESOURCE, TYPE> getStore();
}
