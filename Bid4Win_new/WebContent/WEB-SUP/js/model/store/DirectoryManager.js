/**
 * Class DirectoryManager. Handles synchrone and asynchrone actions on directories.
 * 
 * @author Maxime Ollagnier
 */
DirectoryManager.inherits(AjaxObjectManager);
function DirectoryManager()
{
    this.objectName = 'Directory';
    this.objectType = Directory;
};

/** Store types */
DirectoryManager.IMAGE = 'image';
DirectoryManager.HTML_PAGE = 'html_page';

/**
 * Fetches the list of subdirectories of the specified directory path
 */
DirectoryManager.prototype.getDirectoryMapAJAX = function(storeType, parentPath, callback)
{
    this.getObjectMap(
    {
        storeType : storeType,
        path : parentPath
    }, callback);
};

/**
 * Transforms a JSON map into a bean map.
 */
DirectoryManager.prototype.getObjectMapFromJsonMap = function(jsonMap, parentDirectory)
{
    var objectMap = new Map();
    if (typeof jsonMap != 'undefined')
    {
        for ( var jsonId in jsonMap)
        {
            var object = new this.objectType(jsonId, parentDirectory);
            object.directoryMap = this.getObjectMapFromJsonMap(jsonMap[jsonId], object);
            objectMap.put(object.getId(), object);
        }
    }
    return objectMap;
};