/**
 * Object.js
 *
 * Extends the Object object.
 *
 * @author Maxime Ollagnier
 */
/**
 * Clones the given object
 */
Object.clone = function clone(obj)
{
    if (null == obj || "object" != typeof obj) 
    {
        return obj;
    }
    // Handle Date
    if (obj instanceof Date) 
    {
        var copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }
    // Handle Array
    if (obj instanceof Array) 
    {
        var copy = [];
        for (var i = 0; i < obj.length; i++) 
        {
            copy[i] = clone(obj[i]);
        }
        return copy;
    }
    // Handle Object
    if (obj instanceof Object) 
    {
        var copy = {};
        for (var attr in obj) 
        {
            if (obj.hasOwnProperty(attr)) 
            {
                copy[attr] = clone(obj[attr]);
            }
        }
        return copy;
    }
    Logger.error('Unable to copy obj. Its type is not supported.');
};

/**
 * Checks if the argument "arg" is an object. If it is the argument is returned
 * otherwise the method returns an empty object
 */
Object.getObject = function(arg)
{
    if (typeof arg == 'object') 
    {
        return arg;
    }
    return {};
};
