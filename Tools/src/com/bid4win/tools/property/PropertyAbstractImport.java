package com.bid4win.tools.property;

import java.io.File;
import java.io.FileInputStream;
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
public abstract class PropertyAbstractImport<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                             PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                             ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Item<PropertyAbstractManager<PROPERTY, PROPERTY_ROOT, ACCOUNT>>
{
  /**
   *
   * TODO A COMMENTER
   * @param menu TODO A COMMENTER
   */
  public PropertyAbstractImport(PropertyAbstractManager<PROPERTY, PROPERTY_ROOT, ACCOUNT> menu)
  {
    super("Import ", menu);
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
    File file = this.getImportFile();
    // Choisi le fichier d'import
    System.out.println("Specify import file (" + file.getName() + "):");
    String filename = KeyboardSelection.getInstance().parseString();
    System.out.println("");
    if(!UtilString.trimNotNull(filename).equals(""))
    {
      file = UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE,
                                               this.getMenu().getResourcesPath(),
                                               filename);
    }
    // Test si le fichier peut être lu
    if(!file.exists() || !file.canRead() || file.isDirectory())
    {
      System.out.println("Cannot read file !!!");
      return true;
    }
    try
    {
      FileInputStream fileInputStream = new FileInputStream(file);
      try
      {
        // Récupère les propriétés du fichier source
        Bid4WinSortedProperties properties = new Bid4WinSortedProperties();
        properties.load(fileInputStream);
        // Importe les propriétés vers le système
        this.getMenu().getPropertyService().importProperties(properties);
        System.out.println("Successfull import");
        return true;
      }
      catch(IOException ex)
      {
        System.out.println("Cannot read file !!!");
        return true;
      }
      finally
      {
        fileInputStream.close();
      }
    }
    catch(IOException ex)
    {
      System.out.println("Cannot read file !!!");
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
  protected File getImportFile() throws UserException
  {
    return UtilFile.concatAbsolutePathToFile(ResourceRef.RESOURCE,
                                             this.getMenu().getResourcesPath(),
                                             this.getMenu().getPropertyType().getCode().toLowerCase() + ".import");
  }
}
