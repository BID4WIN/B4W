/**
 * Class PropertyManager. Handles synchrone and asynchrone actions on property.
 * 
 * @author Maxime Ollagnier
 */
PropertyManager.inherits(AjaxObjectManager);
function PropertyManager()
{
    this.objectName = 'Property';
    this.objectType = Property;
    this.map = new PropertyMap();
};

/**
 * Initializes the manager by fetching and caching all the properties from the
 * server.
 */
PropertyManager.prototype.init = function(callback)
{
    callback = Function.getFunction(callback);
    this.getObjectMap(
    {
        cache : true
    }, function(result)
    {
        if (!result.success)
        {
            result.log();
        }
        callback();
    });
};

/**
 * Returns true if the property pointed by the given key exists in the local
 * property map. Returns false otherwise.
 */
PropertyManager.prototype.exists = function(key)
{
    return this.map.contains(key);
};

/**
 * Fetches a property from the local property map.
 */
PropertyManager.prototype.getProperty = function(key)
{
    if (typeof key == 'string')
    {
        if (this.map.contains(key))
        {
            return this.map.get(key);
        } else
        {
            return key;
        }
    }
    throw new Error('Error - Property "' + key + '" is not a string.');
};

/**
 * Fetches the String value of a property from the local property map.
 */
PropertyManager.prototype.get = function(key)
{
    return this.getString(key);
};

/**
 * Fetches the String value of a property from the local property map.
 */
PropertyManager.prototype.getString = function(key)
{
    var property = this.getProperty(key);
    if (typeof property == 'object')
    {
        return property.value;
    }
    return undefined;
};

/**
 * Fetches the integer value of a property from the local property map.
 */
PropertyManager.prototype.getInt = function(key)
{
    var property = this.getProperty(key);
    if (typeof property == 'object')
    {
        return parseInt(property.value);
    }
    return undefined;
};

/**
 * Transforms a JSON property into a property.
 */
PropertyManager.prototype.getObjectFromJson = function(jsonProperty)
{
    var property = undefined;
    if (typeof jsonProperty != 'undefined')
    {
        var parentProperty = undefined;
        try
        {
            parentProperty = this.getProperty(jsonProperty['fullParentKey']);
        } catch (errorMessage)
        {
        }
        property = new Property(jsonProperty, parentProperty);
    }
    return property;
};

/**
 * Transforms a JSON property map into a property map.
 */
PropertyManager.prototype.getObjectMapFromJsonMap = function(jsonPropertyMap)
{
    if (typeof jsonPropertyMap != 'undefined')
    {
        return new PropertyMap(jsonPropertyMap);
    }
    return new PropertyMap();
};
