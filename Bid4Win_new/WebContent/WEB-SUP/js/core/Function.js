/**
 * Function.js
 *
 * Extends the Function object.
 *
 * @author Maxime Ollagnier
 */
/**
 * Simulates the class inheritance
 */
Function.prototype.inherits = function(parentClass)
{
    if (typeof parentClass != 'undefined') 
    {
        this.prototype = new parentClass();
        this.prototype.constructor = this;
        this.prototype.parentClass = parentClass;
        this.prototype.parent = parentClass.prototype;
        
        // Calls the constructor of the parent class on this object
        this.prototype.parentConstructor = function()
        {
            this.parentClass.apply(this, arguments);
        };
        
        // Call the named method from the super class on this object
        this.prototype.parentMethod = function(methodName)
        {
            if (methodName == undefined) throw new Error('parentClass method name missing.');
            if (this.parent[methodName] == undefined) throw new Error('Method ' + methodName + ' does not exist in parentClass ' + this.parentClass.name + '.');
            return this.parent[methodName].apply(this, Array.prototype.slice.call(arguments, 1));
        };
        
        return this;
    }
};

/**
 * Returns the singleton's instance
 */
Function.prototype.getInstance = function()
{
    var instanceName = '_instance' + this.name;
    if (typeof this.prototype[instanceName] == 'undefined') 
    {
        this.prototype[instanceName] = new this.prototype.constructor();
    }
    return this.prototype[instanceName];
};

/**
 * Checks if the argument "arg" named "name" is a function.
 * Throws an exception otherwise
 */
Function.checkIsFunction = function(name, arg)
{
    if (typeof arg != 'function') 
    {
        throw name + 'should be a function.';
    }
};

/**
 * Checks if the argument "arg" is a function. If it is the argument is returned
 * otherwise the method returns an empty function
 */
Function.getFunction = function(arg, warnMessage)
{
    if (typeof arg == 'function') 
    {
        return arg;
    }
    if(Util.checkString(warnMessage))
    {
        Logger.warn(warnMessage);
    }
    return function()
    {
    };
};

