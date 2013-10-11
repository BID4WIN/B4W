/**
 * Class StatusSnippetContent.
 * 
 * @author Maxime Ollagnier
 */
StatusSnippetContent.inherits(Content);
function StatusSnippetContent()
{
};

/** Name of the content */
StatusSnippetContent.prototype.name = 'StatusSnippet';

/**
 * Initialization
 */
StatusSnippetContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
StatusSnippetContent.prototype.getJQ = function(callback)
{
    if(ConnectionManager.getInstance().isLoggedIn())
    {
        callback(this.getJQConnected());  
    }
    else
    {
        callback(this.getJQNotConnected());  
    }
};

/**
 * Generates the jQuery object for a visitor
 */
StatusSnippetContent.prototype.getJQNotConnected = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="loading">&nbsp;</div>' +
            '<div class="link subscription">' + I18nManager.get('status.subscription') + '</div>' +
            '<div class="vSeparator">|</div>' +
            '<div class="link connection">' + I18nManager.get('status.connection') + '</div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('.subscription').bind('click', function(){that.displaySubscriptionForm(this);});
    jQ.find('.connection').bind('click', function(){that.displayConnectionForm(this);});
    
    return jQ;       
};

/**
 * Generates the jQuery object for a connected user
 */
StatusSnippetContent.prototype.getJQConnected = function(callback)
{
    var account = ConnectionManager.getInstance().account;
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="loading">&nbsp;</div>' +
            '<div class="link login">' + account.login + '</div>' +
            '<div class="vSeparator">|</div>' +
            '<div class="link bids">(' + account.bidRightsNb + ')</div>' +
            '<div class="vSeparator">|</div>' +
            '<div class="link disconnection">' + I18nManager.get('status.disconnection') + '</div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('.login').bind('click', function(){that.displayMyAccount(this);});
    jQ.find('.bids').bind('click', function(){that.displayBidMarket(this);});
    jQ.find('.disconnection').bind('click', function(){that.disconnect(this);});
    
    return jQ;       
};

/**
 * Displays the connection form
 */
StatusSnippetContent.prototype.displayConnectionForm = function()
{
    ConnectionFormContent.getInstance().buildPopup();
};

/**
 * Displays the subscription form
 */
StatusSnippetContent.prototype.displaySubscriptionForm = function()
{
    SubscriptionFormContent.getInstance().buildPopup();
};

/**
 * Disconnect the user
 */
StatusSnippetContent.prototype.disconnect = function()
{
    var that = this;
    ConnectionManager.getInstance().disconnectAJAX(function(result)
    {
        if(result.success)
        {
            NavigationManager.reload();
        }
        else
        {
            that.updateMessage(result.html());
        }
    });
};