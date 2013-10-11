/**
 * Class AccountManager. Handles synchrone and asynchrone actions on account.
 * 
 * @author Maxime Ollagnier
 */
AccountManager.inherits(AjaxObjectManager);
function AccountManager()
{
    this.objectName = 'Account';
    this.objectType = Account;
    this.map = new Map();
};

/**
 * Fetches an account from the local account map.
 */
AccountManager.prototype.getAccount = function(accountId)
{
    if (typeof accountId == 'string')
    {
        return this.map.get(accountId);
    }
    return undefined;
};

/**
 * Fetches an account from the server with the given id.
 */
AccountManager.prototype.getAccountAJAX = function(id, callback)
{    
    if (typeof callback != 'function')
    {
        callback = function()
        {
        };
    }
    this.getObjectMap(
    {
        id : id
    }, function(accountMap, message)
    {
        var account = undefined;
        if (typeof accountMap != 'undefined' && accountMap.size > 0)
        {
            account = accountMap.get(id);
        }
        callback(account, message);
    });
};

/**
 * Fetches an account map from the server with the given searchString.
 */
AccountManager.prototype.getAccountMapAJAX = function(searchString, callback)
{
    this.getObjectMap(
    {
        searchString : searchString
    }, callback);
};

/**
 * Saves the given account into sever persistence system.
 */
AccountManager.prototype.saveAccountAJAX = function(account, callback)
{
    this.saveObject(account, callback);
};

/**
 * Sets the status of the account pointed by the given ID to 'deleted' in the
 * server persistence system.
 */
AccountManager.prototype.deleteAccountAJAX = function(accountId, callback)
{
    this.deleteObject(accountId, callback);
};

/**
 * Deletes the account pointed by the given ID from the server persistence
 * system.
 */
AccountManager.prototype.removeAccountAJAX = function(accountId, callback)
{
    this.removeObject(accountId, callback);
};