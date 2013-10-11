/**
 * Class Logger. Handles the whole logging issue
 *
 * @author Maxime Ollagnier
 */
Logger = function()
{
}

/** Nothing logged */
Logger.NONE = '3_NONE';
/** Info only logged */
Logger.INFO = '2_INFO';
/** Everything beside traces logged */
Logger.DEBUG = '1_DEBUG';
/** Everything logged */
Logger.TRACE = '0_TRACE';

/** Logging level */
Logger.level = Logger.TRACE;

/**
 * Logs the given string as info type if the current logging level is lower than
 * INFO level
 */
Logger.info = function()
{
    Logger.callConsole('info', Logger.INFO, arguments);
};

/**
 * Logs the given string as warning type if the current logging level is lower
 * than DEBUG level
 */
Logger.warn = function()
{
    Logger.callConsole('warn', Logger.DEBUG, arguments);
};

/**
 * Logs the given string an error type if the current logging level is lower
 * than DEBUG level
 */
Logger.error = function()
{
    Logger.callConsole('error', Logger.DEBUG, arguments);
};

/**
 * Logs the given string as error type if the current logging level is lower
 * than DEBUG level
 */
Logger.log = function()
{
    Logger.callConsole('log', Logger.DEBUG, arguments);
};

/**
 * Logs the given string as error type if the current logging level is lower
 * than DEBUG level
 */
Logger.debug = function()
{
    Logger.callConsole('debug', Logger.DEBUG, arguments);
};

/**
 * Calls the 'console.group' method if the current logging level is lower
 * than DEBUG level
 */
Logger.group = function()
{
    Logger.callConsole('group', Logger.DEBUG, arguments);
};

/**
 * Calls the 'console.groupCollapse' method if the current logging level is lower
 * than DEBUG level
 */
Logger.groupCollapsed = function()
{
    Logger.callConsole('groupCollapsed', Logger.DEBUG, arguments);
};

/**
 * Calls the 'console.groupEnd' method if the current logging level is lower
 * than DEBUG level
 */
Logger.groupEnd = function()
{
    Logger.callConsole('groupEnd', Logger.DEBUG, arguments);
};

/**
 * Return whether the given logging level is higher than the current one
 */
Logger.callConsole = function(method, level, arguments)
{
    if (level >= Logger.level && typeof console == 'object') 
    {
        if (Util.checkString(method))
        {
            if (Util.checkFunction(console[method]))
            {
                console[method].apply(console, Array.prototype.slice.call(arguments));
                return true;
            }
        }
    }
    return false;
};
