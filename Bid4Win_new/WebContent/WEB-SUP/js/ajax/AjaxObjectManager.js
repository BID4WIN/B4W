/**
 * Class AjaxObjectManager. Parent class of managers handling AJAX actions on
 * cached objects.
 * 
 * @author Maxime Ollagnier
 */
function AjaxObjectManager()
{
    /** Object name */
    this.objectName = undefined;
    /** Object type (The object must have a getId() method defined */
    this.objectType = undefined;
    /** Object map */
    this.map = undefined;
};

/**
 * Fetches an object map from the server with the given parameters and updates
 * the local object map.
 */
AjaxObjectManager.prototype.getObjectMap = function(parameters, callback)
{
    parameters = Object.getObject(parameters);
    var that = this;
    var actionName = 'Get' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName, parameters, function(result)
    {
        if (result.success && result.checkObject())
        {
            var objectMap = result.json2Model(that, that.getObjectMapFromJsonMap);
            if (parameters.cache)
            {
                that.cacheObjectMap(objectMap);
            }
            result.addInfo(objectMap.size + ' ' + that.objectName.toFirstUpperCase() + '(s) found.');
        } else
        {
            result.success = false;
            result.addWarn('Error getting ' + that.objectName.toFirstUpperCase());
        }
        callback(result);
    });
};

/**
 * Adds the given object into sever persistence system
 */
AjaxObjectManager.prototype.addObject = function(object, callback)
{
    var that = this;
    var actionName = 'Add' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName, object.getJSON(), function(result)
    {
        if (!result.success)
        {
            result.addWarn('Error adding ' + that.objectName.toFirstUpperCase() + '.');
        }
        callback(result);
    });
};

/**
 * Saves the given object into sever persistence system
 */
AjaxObjectManager.prototype.saveObject = function(object, callback)
{
    var that = this;
    var actionName = 'Save' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName, object.getJSON(), function(result)
    {
        if(result.success)
            result.json2Model(that, that.getObjectMapFromJsonMap);
        else
            result.addWarn('Error saving ' + that.objectName.toFirstUpperCase() + '.');
        callback(result);
    });
};

/**
 * Removes the object pointed by the given ID from the server persistence
 * system.
 */
AjaxObjectManager.prototype.removeObject = function(objectId, callback)
{
    var that = this;
    var actionName = 'Remove' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName,
    {
        id : objectId
    }, function(result)
    {
        if (!result.success)
        {
            result.addWarn('Error removing ' + that.objectName.toFirstUpperCase() + '.');
        }
        callback(result);
    });
};

/**
 * Sets the status of the object pointed by the given ID to 'deleted' in the
 * server persistence system.
 */
AjaxObjectManager.prototype.deleteObject = function(objectId, callback)
{
    var that = this;
    var actionName = 'Delete' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName,
    {
        id : objectId
    }, function(result)
    {
        if (!result.success)
        {
            result.addWarn('Error setting as deleted ' + that.objectName.toFirstUpperCase() + '.');
        }
        callback(result);
    });
};

/**
 * Copies the object from the oldObjectId to the newObjectId on the server
 * persistence system.
 */
AjaxObjectManager.prototype.copyObject = function(oldObjectId, newObjectId, callback)
{
    var that = this;
    var actionName = 'Copy' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName,
    {
        oldId : oldObjectId,
        newId : newObjectId
    }, function(result)
    {
        if (!result.success)
        {
            result.addWarn('Error copying ' + that.objectName.toFirstUpperCase() + '.');
        }
        callback(result);
    });
};

/**
 * Moves the object from the oldObjectId to the newObjectId on the server
 * persistence system.
 */
AjaxObjectManager.prototype.moveObject = function(oldObjectId, newObjectId, callback)
{
    var that = this;
    var actionName = 'Move' + this.objectName.toFirstUpperCase() + 'Action';
    AjaxManager.getJSON(actionName,
    {
        oldId : oldObjectId,
        newId : newObjectId
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
 * Saves file chosen into the input field pointed by the given fileInputId into
 * sever persistence system.
 */
AjaxObjectManager.prototype.saveFile = function(fileInputId, fileName, path, callback)
{
    var actionName = 'Save' + this.objectName.toFirstUpperCase() + 'FileAction';
    AjaxManager.uploadFile(actionName, fileInputId, fileName, path, function()
    {
        callback();
    });
};

/**
 * Transforms a JSON object into a bean object.
 */
AjaxObjectManager.prototype.getObjectFromJson = function(json)
{
    var object = undefined;
    if (typeof json != 'undefined')
    {
        object = new this.objectType(json);
    }
    return object;
};

/**
 * Transforms a JSON map into a bean map.
 */
AjaxObjectManager.prototype.getObjectMapFromJsonMap = function(jsonMap)
{
    var objectMap = new Map();
    if (typeof jsonMap != 'undefined')
    {
        for ( var jsonId in jsonMap)
        {
            var object = this.getObjectFromJson(jsonMap[jsonId]);
            objectMap.put(object.getId(), object);
        }
    }
    return objectMap;
};

/**
 * Puts the given object into the object map of the manager.
 */
AjaxObjectManager.prototype.cacheObject = function(object)
{
    if (typeof object == 'object')
    {
        this.map.put(object.getId(), object);
    }
};

/**
 * Puts all the objects of the given map into the object map of the manager.
 */
AjaxObjectManager.prototype.cacheObjectMap = function(objectMap)
{
    if (typeof objectMap != 'undefined')
    {
        var that = this;
        objectMap.foreach(function(key, value)
        {
            that.cacheObject(value);
        });
    }
};
