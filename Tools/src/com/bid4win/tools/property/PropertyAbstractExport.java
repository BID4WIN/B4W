package com.bid4win.tools.property;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinSortedProperties;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.KeyboardSelection;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.tools.console.Item;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractExport<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                             PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Item<PropertyAbstractManager<PROPERTY, PROPERTY_ROOT, ACCOUNT>>
{
  /**
   *
   * TODO A COMMENTER
   * @param menu TODO A COMMENTER
   */
  public PropertyAbstractExport(PropertyAbstractManager<PROPERTY, PROPERTY_ROOT, ACCOUNT> menu)
  {
    super("Export ", menu);
  }

  /**
   * Cette méthode permet d'execute l'item courant
   * @throws Bid4WinException {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#execute()
   */
  @Override
  public boolean execute() throws Bid4WinException
  {
    // Affiche le libellé de l'action
    System.out.println(this.getWording());
    File file = this.getExportFile();
    // Choisi le fichier d'export
    System.out.println("Specify export file (" + file.getName() + "):");
    String filename = KeyboardSelection.getInstance().parseString();
    System.out.println("");
    if(!UtilString.trimNotNull(filename).equals(""))
    {
      file = UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE,
                                               this.getMenu().getResourcesPath(),
                                               filename);
    }
    // Si le fichier existe déjà, faut il l'écraser
    if(file.exists())
    {
      System.out.println("File already exists, overwrite it (y/n):");
      boolean overwrite = KeyboardSelection.getInstance().parseBooleanYesOrNo();
      if(!overwrite)
      {
        return false;
      }
    }
    else
    {
      try
      {
        file.createNewFile();
      }
      catch(IOException ex)
      {
        System.out.println("Cannot write file !!!");
        return true;
      }
    }
    // Test si le fichier peut être écrit
    if(!file.canWrite() || file.isDirectory())
    {
      System.out.println("Cannot write file !!!");
      return true;
    }
    Bid4WinSortedProperties properties = new Bid4WinSortedProperties();
    // Récupère les propriétés
    try
    {
      properties.putAll(this.getMenu().getPropertyService().exportProperties());
    }
    catch(Bid4WinException ex)
    {
      System.out.println("Export failed !!!");
      System.out.println(ex.getMessage());
      return true;
    }
    try
    {
      // Exporte les propriétés vers le fichier de destination
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      try
      {
        properties.store(fileOutputStream, "");
        fileOutputStream.flush();
        System.out.println("Successfull export");
        return true;
      }
      catch(IOException ex)
      {
        System.out.println("Cannot write file !!!");
        return true;
      }
      finally
      {
        fileOutputStream.close();
      }
    }
    catch(IOException ex)
    {
      System.out.println("Cannot write file !!!");
      return true;
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#getWording()
   */
  @Override
  public String getWording()
  {
    return super.getWording() + this.getMenu().getPropertyType().getWording();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected File getExportFile() throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE,
                                             this.getMenu().getResourcesPath(),
                                             this.getMenu().getPropertyType().getCode().toLowerCase() + ".export");
  }
}
