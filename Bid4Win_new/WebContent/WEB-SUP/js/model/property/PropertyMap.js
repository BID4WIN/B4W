/**
 * Class PropertyMap.
 * @author Maxime Ollagnier
 */

PropertyMap.inherits(Map);
function PropertyMap(jsonPropertyMap, parentProperty)
{
    /** Calls the parent constructor */
    this.parentConstructor();
    
    /** Specifies the value type of the Map */
    this.valueConstructor = Property;
    
    /** Initialization with a given JSON object */
    this.init(jsonPropertyMap, parentProperty);
};

/**
 * Initializes the map with the given JSON object
 * @param jsonPropertyMap The JSON object
 */
PropertyMap.prototype.init = function(jsonPropertyMap, parentProperty)
{
    for ( var key in jsonPropertyMap)
    {
        this.put(key, new Property(jsonPropertyMap[key], parentProperty));
    }
};

/**
 * Returns the property with the given full hierarchical key ex: key =
 * 'parentKey.childKey'
 * @param fullKey The full hierarchical key
 */
PropertyMap.prototype.get = function(fullKey)
{
    var firstSeparatorIndex = fullKey.indexOf('.');
    if (firstSeparatorIndex == -1)
    {
        return this.map[fullKey];
    }
    var property = this.map[fullKey.substring(0, firstSeparatorIndex)];
    if (typeof property == 'undefined')
    {
        return undefined;
    }
    var subFullKey = fullKey.substring(firstSeparatorIndex + 1, fullKey.length);
    return property.properties.get(subFullKey);
};

/**
 * TODO
 * @param fullKey The full hierarchical key
 */
PropertyMap.prototype.put = function(fullKey, property)
{
    var firstSeparatorIndex = fullKey.indexOf('.');
    if (firstSeparatorIndex == -1)
    {
        this.parent.put.call(this, fullKey, property);
    }
    else
    {
        var rootProperty = this.map[fullKey.substring(0, firstSeparatorIndex)];
        var subFullKey = fullKey.substring(firstSeparatorIndex + 1, fullKey.length);
        rootProperty.properties.put(subFullKey, property);
    }
};

/**
 * TODO
 * @param propertyMap A map of properties to be added
 */
PropertyMap.prototype.putAll = function(propertyMap)
{
    var that = this;
    propertyMap.foreach(function(key, property)
    {
        that.put(property.getFullKey(), property);
    });
};

/**
 * TODO
 * @param fullKey The full hierarchical key
 */
PropertyMap.prototype.remove = function(fullKey)
{
    var firstSeparatorIndex = fullKey.indexOf('.');
    if (firstSeparatorIndex == -1)
    {
        this.parent.remove.call(this, fullKey);
    }
    else
    {
        var rootProperty = this.map[fullKey.substring(0, firstSeparatorIndex)];
        var subFullKey = fullKey.substring(firstSeparatorIndex + 1, fullKey.length);
        rootProperty.properties.remove(subFullKey);
    }
};