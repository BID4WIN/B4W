/**
 * Class ConnectionManager. Handles the whole connection issue.
 *
 * @author Maxime Ollagnier
 */
function ConnectionManager()
{
    // The logged in flag
    this.account = undefined;
};

/**
 * Initializes the connection manager.
 * Tries to reconnect to an account.
 * Rebuild the status snippet.
 */
ConnectionManager.prototype.init = function(callback)
{
    this.reconnectAJAX(callback);
};

// Hash à la manière de java.lang.String.hashCode()
ConnectionManager.prototype.hashPassword = function(password)
{
    var hash = 0;
    if (password.length == 0) return hash;
    for(var i = 0; i < password.length; i++)
    {
        char = password.charCodeAt(i);
        hash = ((hash<<5)-hash)+char;
        hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
};


/**
 * Tries to reconnect.
 * If a login/fingerPrint is given, the server will try to recover a former
 * connection. If no parameter is given, the server will try to return the
 * account already connected within the same session Id
 */
ConnectionManager.prototype.reconnectAJAX = function(callback)
{
    callback = Function.getFunction(callback);
    var that = this;
    AjaxManager.getJSON('ReconnectAction', {}, function(result)
    {
        if (result.success && result.checkObject()) 
        {
            that.account = new Account(result.getObject());
        }
        callback();
    });
};

/**
 * Tries to connect with the given login/password or email/password.
 */
ConnectionManager.prototype.connectAJAX = function(loginOrEmail, password, rememberMe, reconnect, callback)
{
	password = this.hashPassword(password);
    callback = Function.getFunction(callback);
    var parameters = 
    {
        loginOrEmail: loginOrEmail,
        password: password,
        rememberMe: rememberMe,
        reconnect: reconnect
    };
    var that = this;
    AjaxManager.getJSON('ConnectAction', parameters, function(result)
    {
        if (result.success && result.checkObject()) 
        {
            that.account = new Account(result.getObject());
            that.refreshStatusSnippet();
        }
        callback(result);
    });
};

/**
 * Tries to disconnect the account.
 */
ConnectionManager.prototype.disconnectAJAX = function(callback)
{
    callback = Function.getFunction(callback);
    if (this.isLoggedIn()) 
    {
        var that = this;
        AjaxManager.getJSON('DisconnectAction', {}, function(result)
        {
            if (result.success) 
            {
                that.account = undefined;
                that.refreshStatusSnippet();
            }
            callback(result);
        });
    }
    else 
    {
        callback();
    }
};

/**
 * Sends an email with a password reinitialization link
 */
ConnectionManager.prototype.passwordReinitMailAJAX = function(loginOrEmail, callback)
{
    var parameters = 
    {
        loginOrEmail: loginOrEmail
    };
    var that = this;
    AjaxManager.getJSON('PasswordReinitMailAction', parameters, function(result)
    {
        callback(result);
    });
};

/**
 * Reinitializes the password for the account whose login is given and with the
 * reinitKey. Those two variables should have been set within the jsp page from
 * the ReinitPasswordPromptAction
 */
ConnectionManager.prototype.passwordReinitAJAX = function(password, callback)
{
    var parameters = 
    {
        password: password,
        accountId: accountIdJSP,
        reinitKey: reinitKeyJSP
    };
    var that = this;
    AjaxManager.getJSON('PasswordReinitAction', parameters, function(result)
    {
        if (result.success && result.checkObject()) 
        {
            var account = new Account(result.getObject());
            that.connectAJAX(account.login, password, true, false, function(result)
            {
                callback(result);
            });
        }
        else 
        {
            callback(result);
        }
    });
};

/**
 * Returns the logged in flag
 */
ConnectionManager.prototype.isLoggedIn = function()
{
    return Util.checkObject(this.account);
};

/**
 * Rebuilds the status snippet
 */
ConnectionManager.prototype.refreshStatusSnippet = function()
{
    StatusSnippetContent.getInstance().buildSnippet();
};
