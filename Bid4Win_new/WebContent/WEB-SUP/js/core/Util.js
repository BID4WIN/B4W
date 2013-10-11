/**
 * Provides several utility methods
 *
 * @author Maxime Ollagnier
 */
function Util()
{
}

/** Pattern for email string verification */
Util.EMAIL_PATTERN = /^([A-Za-z0-9_\-\+\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
/** Pattern for login string verification */
Util.LOGIN_PATTERN = /^[A-Za-z0-9_]{5,30}$/;
/** Pattern for hashed password string verification */
Util.HASHED_PASSWORD_PATTERN = /^[0-9a-f]{5,60}$/;

/**
 * Returns true if all the parameters exist and are not null
 */
Util.check = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] == 'undefined' || arguments[i] === null) 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are null
 */
Util.checkNull = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] == 'undefined' || arguments[i] != null) 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are typified as 'string'
 */
Util.checkString = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'string') 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist, are typified as 'string' and are
 * not empty strings
 */
Util.checkStringNotEmpty = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'string' || arguments[i].length == 0) 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist, are typified as 'string' and are
 * empty strings
 */
Util.checkStringEmpty = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'string' || arguments[i].length > 0) 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are typified as 'number'
 */
Util.checkNumber = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'number') 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are typified as 'boolean'
 */
Util.checkBool = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'boolean') 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are typified as 'array'
 */
Util.checkArray = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'array') 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are typified as 'object'
 */
Util.checkObject = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'object') 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are typified as 'function'
 */
Util.checkFunction = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'function') 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are correct email strings
 */
Util.checkEmail = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'string' || !Util.EMAIL_PATTERN.test(arguments[i])) 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are correct login strings
 */
Util.checkLogin = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'string' || !Util.LOGIN_PATTERN.test(arguments[i])) 
        {
            return false;
        }
    }
    return true;
};

/**
 * Returns true if all the parameters exist and are correct hashed password
 * strings
 */
Util.checkPassword = function()
{
    for (var i = 0; i < arguments.length; i++) 
    {
        if (typeof arguments[i] != 'string' || !Util.HASHED_PASSWORD_PATTERN.test(arguments[i])) 
        {
            return false;
        }
    }
    return true;
};
