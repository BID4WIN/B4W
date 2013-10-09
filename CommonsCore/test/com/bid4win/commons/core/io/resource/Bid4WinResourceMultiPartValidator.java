package com.bid4win.commons.core.io.resource;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinSet;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinResourceMultiPartValidator<RESOURCE extends Bid4WinResourceMultiPart<TYPE, PART_TYPE, PART>,
                                                        TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                        PART extends Bid4WinResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinResourceValidator<RESOURCE, TYPE>
{
  /**
   * Cette méthode permet de valider que la ressource en argument correspond bien
   * au résultat attendu pour la portion par défaut
   * @param testPath {@inheritDoc}
   * @param expected {@inheritDoc}
   * @param resource {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#assertEquals(java.lang.String, java.io.File, com.bid4win.commons.core.io.resource.Bid4WinResource)
   */
  @Override
  public void assertEquals(String testPath, File expected, RESOURCE resource)
         throws Exception
  {
    this.assertEquals(testPath, expected, resource, this.getStore().getPartTypeDefault());
  }
  /**
   * Cette méthode permet de valider que la ressource en argument correspond bien
   * au résultat attendu pour les portions données
   * @param testPath Repertoire de test
   * @param expected Résultat d'origine attendu
   * @param resource Ressource à valider
   * @param partTypeSet Types de portion à valider
   * @throws Exception Problème lors de la validation de la ressource
   */
  public void assertEquals(String testPath, File expected, RESOURCE resource, Bid4WinSet<PART_TYPE> partTypeSet)
         throws Exception
  {
    for(PART_TYPE partType : partTypeSet)
    {
      this.assertEquals(testPath, expected, resource, partType);
    }
  }
  /**
   * Cette méthode permet de valider que la ressource en argument correspond bien
   * au résultat attendu pour la portion donnée
   * @param testPath Repertoire de test
   * @param expected Résultat d'origine attendu
   * @param resource Ressource à valider
   * @param partType Type de la portion à valider
   * @throws Exception Problème lors de la validation de la ressource
   */
  public void assertEquals(String testPath, File expected, RESOURCE resource, PART_TYPE partType)
            throws Exception
  {
    File result = this.getResultFile(testPath, "result" + partType.getCode() + ".file");
    if(!result.getParentFile().exists())
    {
      result.getParentFile().mkdirs();
    }
    FileOutputStream outputStream = new FileOutputStream(result);
    try
    {
      this.getStore().retreive(outputStream, resource.getPart(partType));
    }
    finally
    {
      outputStream.close();
    }
    this.checkResult(expected, result, resource, partType);
  }
  /**
   *
   * TODO A COMMENTER
   * @param expected TODO A COMMENTER
   * @param result TODO A COMMENTER
   * @param resource TODO A COMMENTER
   * @param partType TODO A COMMENTER
   * @throws Exception TODO A COMMENTER
   */
  protected void checkResult(File expected, File result, RESOURCE resource, PART_TYPE partType)
            throws Exception
  {
    assertTrue("Wrong result for " + partType + " portion of " + this.getName(resource),
        this.isSame(expected, result));
  }
  /**
   *
   * TODO A COMMENTER
   * @param resource TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected abstract String getName(RESOURCE resource);

  /**
   * Getter du magasin des ressources à valider
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public abstract Bid4WinResourceMultiPartStore<RESOURCE, TYPE, PART_TYPE, PART> getStore();
}
