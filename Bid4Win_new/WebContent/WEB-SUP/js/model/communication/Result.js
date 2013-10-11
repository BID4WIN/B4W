/**
 * Class Result. Matches the JSON ajax result
 *
 * @author Maxime Ollagnier
 */
/**
 * Class Result
 * @param {Object} jsonResult
 */
function Result(actionName, jsonResult)
{
    this.actionName = actionName;
    this.success = jsonResult.success ? jsonResult.success : false;
    this.objectMap = jsonResult.jsonObjectMap ? jsonResult.jsonObjectMap : new Object();
    this.infoMessageArray = jsonResult.jsonInfoMessageArray ? Message.getMessageArray(jsonResult.jsonInfoMessageArray) : new Array();
    this.warnMessageArray = jsonResult.jsonWarnMessageArray ? Message.getMessageArray(jsonResult.jsonWarnMessageArray) : new Array();
    this.errorMessageArray = jsonResult.jsonErrorMessageArray ? Message.getMessageArray(jsonResult.jsonErrorMessageArray) : new Array();
}

/** Standard object name */
Result.OBJECT_STD = "object";

/**
 * Logs the result until DEBUG logging level, logs the infos until INFO logging
 * level and does nothing in NONE logging level.
 */
Result.prototype.log = function()
{
    var title = (this.success ? 'SUCCESS' : 'FAIL') + ' [' + this.actionName + '] - ';
    title += ' (' + this.infoMessageArray.length + ' info, ';
    title += this.warnMessageArray.length + ' warning, ';
    title += this.errorMessageArray.length + ' error)';
    Logger.groupCollapsed(title);
    for (var i = 0; i < this.infoMessageArray.length; i++) 
    {
        this.infoMessageArray[i].log();
    }
    for (var i = 0; i < this.warnMessageArray.length; i++) 
    {
        this.warnMessageArray[i].log();
    }
    for (var i = 0; i < this.errorMessageArray.length; i++) 
    {
        this.errorMessageArray[i].log();
    }
    Logger.groupEnd();
};

/**
 * Returns the HTML code of the jQuery object of the messages to display to the
 * client
 */
Result.prototype.html = function()

{
    var jQ = $('<div></div>');
    var jQInfos = $('<div class="infos"></div>');
    var jQWarns = $('<div class="warns"></div>');
    for (var i = 0; i < this.infoMessageArray.length; i++) 
    {
        jQInfos.append(this.infoMessageArray[i].getJQ());
    }
    for (var i = 0; i < this.warnMessageArray.length; i++) 
    {
        jQWarns.append(this.warnMessageArray[i].getJQ());
    }
    jQ.append(jQInfos).append(jQWarns);
    return jQ.html();
};

/**
 * Set the success flag
 */
Result.prototype.setSuccess = function(success)
{
    this.success = success;
    if(!this.success)
    {
        this.log();
    }
};

/**
 * Adds an info message
 */
Result.prototype.addInfo = function(message, i18n)
{
    this.infoMessageArray.push(new Message(
    {
        message: message,
        type: Message.INFO,
        i18n: i18n ? i18n : false
    }));
};

/**
 * Adds a warn message
 */
Result.prototype.addWarn = function(message, i18n)
{
    this.warnMessageArray.push(new Message(
    {
        message: message,
        type: Message.WARN,
        i18n: i18n ? i18n : false
    }));
};

/**
 * Adds an error message
 */
Result.prototype.addError = function(message, i18n)
{
    this.errorMessageArray.push(new Message(
    {
        message: message,
        type: Message.ERROR,
        i18n: i18n ? i18n : false
    }));
};

/**
 * Removes every info message
 */
Result.prototype.clearInfo = function()
{
    this.infoMessageArray = new Array();
};

/**
 * Removes every warn message
 */
Result.prototype.clearWarn = function()
{
    this.warnMessageArray = new Array();
};

/**
 * Removes every error message
 */
Result.prototype.clearError = function()
{
    this.errorMessageArray = new Array();
};

/**
 * Returns the object pointed by the given objectName, or the object
 * pointed by the Result.OBJECT_STD name. Returns undefined if not found
 */
Result.prototype.getObject = function(objectName)
{
    if (!Util.checkString(objectName)) 
    {
        objectName = Result.OBJECT_STD;
    }
    return this.objectMap[objectName];
};

/**
 * Tells if the object named 'objectName' or Result.OBJECT_STD exists.
 * If not it sets the success status of the result to false and return false;
 */
Result.prototype.checkObject = function(objectName)
{
    if (!Util.checkString(objectName)) 
    {
        objectName = Result.OBJECT_STD;
    }
    if (!this.hasObject(objectName)) 
    {
        this.addError('[' + this.actionName + '] The (json) object named "' + objectName + '" should exist in the result');
        this.setSuccess(false);
        return false;
    }
    return true;
};

/**
 *
 */
Result.prototype.json2Model = function(that, json2Model, objectName)
{
    if (!Util.checkString(objectName)) 
    {
        objectName = Result.OBJECT_STD;
    }
    if (!Util.checkFunction(json2Model)) 
    {
        this.success = false;
        this.addError('[' + this.actionName + '] Transformation method for "' + objectName + '" should be provided to Result.json2Model()');
        return undefined;
    }
    if (!this.hasObject(objectName)) 
    {
        this.success = false;
        this.addError('[' + this.actionName + '] The (json) object named "' + objectName + '" should exist in the result');
        return undefined;
    }
    this.objectMap[objectName] = json2Model.call(that, this.objectMap[objectName]);
    return this.objectMap[objectName];
};

/**
 * Tells if the object named 'objectName' or Result.OBJECT_STD exists
 */
Result.prototype.hasObject = function(objectName)
{
    if (!Util.checkString(objectName)) 
    {
        objectName = Result.OBJECT_STD;
    }
    return Util.checkObject(this.objectMap[objectName]);
};

/**
 * Tells if the result has any info message
 */
Result.prototype.hasInfo = function()
{
    return this.infoMessageArray.length > 0;
};

/**
 * Tells if the result has any warning message
 */
Result.prototype.hasWarn = function()
{
    return this.warnMessageArray.length > 0;
};

/**
 * Tells if the result has any error message
 */
Result.prototype.hasError = function()
{
    return this.errorMessageArray.length > 0;
};
