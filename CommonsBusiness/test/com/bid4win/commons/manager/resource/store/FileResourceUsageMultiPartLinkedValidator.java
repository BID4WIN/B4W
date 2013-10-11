package com.bid4win.commons.manager.resource.store;

import java.io.File;
import java.io.FileOutputStream;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator;
import com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore;
import com.bid4win.commons.persistence.entity.resource.FileResourceUsageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.store.FileResourcePart;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER<BR>
 * @param <TYPE> TODO A COMMENTER<BR>
 * @param <STORAGE> TODO A COMMENTER<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FileResourceUsageMultiPartLinkedValidator<RESOURCE extends FileResourceUsageMultiPart<RESOURCE, TYPE, STORAGE, PART_TYPE, PART>,
                                                                TYPE extends ResourceType<TYPE>,
                                                                STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, RESOURCE, PART_TYPE, PART>,
                                                                PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                                PART extends FileResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinFileResourceMultiPartValidator<RESOURCE, TYPE, PART_TYPE, PART>
{
  /**
   *
   * TODO A COMMENTER
   * @param expected {@inheritDoc}
   * @param result {@inheritDoc}
   * @param resource {@inheritDoc}
   * @param partType {@inheritDoc}
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartValidator#checkResult(java.io.File, java.io.File, com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPart, com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType)
   */
  @Override
  protected void checkResult(File expected, File result, RESOURCE resource, PART_TYPE partType)
            throws Exception
  {
    expected = this.getExpected(result.getParent(), resource, partType);
    super.checkResult(expected, result, resource, partType);
  }
  /**
   * Cette méthode permet de récupérer le résultat attendu correspondant à la
   * portion de ressource à valider
   * @param testPath Repertoire de test
   * @param resource Ressource à valider
   * @param partType Portion de ressource du résultat attendu à récupérer
   * @return Le résultat attendu correspondant à la ressource à valider dans le
   * format choisi
   * @throws Exception Problème lors de la récupération du résultat attendu
   */
  protected File getExpected(String testPath, RESOURCE resource, PART_TYPE partType)
            throws Exception
  {
    File result = this.getResultFile(testPath, "expected_" + partType.getCode() + ".file");
    if(!result.getParentFile().exists())
    {
      result.getParentFile().mkdirs();
    }
    FileOutputStream outputStream = new FileOutputStream(result);
    try
    {
      this.getRepository().retreive(outputStream, resource.getStorage().getPart(partType));
      return result;
    }
    finally
    {
      outputStream.close();
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract Bid4WinResourceMultiPartStore<STORAGE, TYPE, PART_TYPE, PART> getRepository();
}
