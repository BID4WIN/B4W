/**
 * Class ImageManager. Handles synchrone and asynchrone actions on image.
 * 
 * @author Maxime Ollagnier
 */
ImageManager.inherits(AjaxObjectManager);
function ImageManager()
{
    this.objectName = 'Image';
    this.objectType = Image;
    this.map = new Map();
};

/** Properties keys */
ImageManager.ROOT_LOCATION = 'server.root_location';

/** Image formats */
ImageManager.SMALL = 'SMALL';
ImageManager.MEDIUM = 'MEDIUM';
ImageManager.LARGE = 'LARGE';
ImageManager.SOURCE = 'SOURCE';

/**
 * Fetches an image from the local image map.
 */
ImageManager.prototype.getImage = function(id)
{
    if (typeof id == 'string')
    {
        return this.map.get(id);
    }
    return undefined;
};

/**
 * Returns the URL of the image stream.
 */
ImageManager.prototype.getImageURL = function(id, format)
{
    if (typeof id == 'string' && typeof format == 'string')
    {
        return PropertyManager.getInstance().get(ImageManager.ROOT_LOCATION) + 'GetImageStreamAction?id=' + id + "&format=" + format;
    }
    return undefined;
};

/**
 * Fetches an image map from the server with the given path.
 */
ImageManager.prototype.getImageMapAJAX = function(path, callback)
{
    this.getObjectMap(
    {
        path : path
    }, callback);
};

/**
 * Saves the given image into sever persistence system.
 */
ImageManager.prototype.saveImageAJAX = function(fileIntputId, imageName, path, callback)
{
    this.saveFile(fileIntputId, imageName, path, callback);
};

/**
 * Deletes the image pointed by the given id from the server persistence
 * system.
 */
ImageManager.prototype.removeImageAJAX = function(id, callback)
{
    this.removeObject(id, callback);
};

/**
 * Moves the image to the new path and name
 */
ImageManager.prototype.moveImageAJAX = function(id, path, name, callback)
{
    var that = this;
    var actionName = 'Move' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName,
    {
        id : id,
        path : path,
        name : name
    }, function(result)
    {
        if (!result.success)
        {
            result.addWarn('Error moving ' + that.objectName.toFirstUpperCase() + '.');
        }
        callback(result);
    });
};

/**
 * Fetches the list of subdirectories of the specified directory path
 */
ImageManager.prototype.getDirectoryMapAJAX = function(parentPath, callback)
{
    this.getStringMap("GetSubdirectoriesAction", {
        path : parentPath
    }, callback);
};