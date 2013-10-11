/**
 * Class Directory.
 * 
 * @author Maxime Ollagnier
 */

Directory.inherits(Bean);
function Directory(name, parentDirectory)
{
    this.name = name;
    this.parentDirectory = parentDirectory;
    this.directoryMap = new Map();
    this.expanded = false;
}

/**
 * Returns the id
 */
Directory.prototype.getId = function()
{
    return this.name;
};

/**
 * Returns the full path
 */
Directory.prototype.getFullPath = function()
{
    var fullPath = "";
    if(Util.checkObject(this.parentDirectory))
    {
        fullPath += this.parentDirectory.getFullPath();
        if(fullPath != '')
        {
            fullPath += "/";
        }
    }
    return fullPath + this.name;
};

/**
 * Returns the directory with the specified full path
 */
Directory.prototype.getDirectory = function(fullPath)
{
    if(this.getFullPath() == fullPath)
    {
        return this;
    }
    var foundDirectory = undefined;
    this.directoryMap.foreach(function(id, subDirectory)
    {
        foundDirectory = subDirectory.getDirectory(fullPath);
        if(Util.checkObject(foundDirectory))
        {
            return true;
        } 
    });
    return foundDirectory;
};