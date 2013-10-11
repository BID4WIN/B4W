/**
 * Class SubscriptionFormContent.
 * 
 * @author Maxime Ollagnier
 */
SubscriptionFormContent.inherits(Content);
function SubscriptionFormContent()
{
};

/** Name of the content */
SubscriptionFormContent.prototype.name = 'SubscriptionForm';

/**
 * Initialization
 */
SubscriptionFormContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
SubscriptionFormContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="loading">&nbsp;</div>' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('form.subscription') + '</div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<table>' +
            '<tr class="login">' +
            '<td><div class="label">' + I18nManager.get('form.subscription.login') + '</div></td>' +
            '<td><input class="input" type="text" value="' + ((this.parameters.login)?this.parameters.login:'') + '" /></td>' +
            '</tr>' +
            '<tr class="email">' +
            '<td><div class="label">' + I18nManager.get('form.subscription.email') + '</div></td>' +
            '<td><input class="input" type="text" value="" /></td>' +
            '</tr>' +
            '<tr class="password">' +
            '<td><div class="label">' + I18nManager.get('form.subscription.password') + '</div></td>' +
            '<td><input class="input" type="password" value="" /></td>' +
            '</tr>' +
            '<tr class="newsletter">' +
            '<td><input class="input" type="checkbox" /></td>' +
            '<td><div class="label">' + I18nManager.get('form.subscription.newsletter') + '</div></td>' +
            '</tr>' +
            '<tr class="cgu">' +
            '<td><input class="input" type="checkbox" /></td>' +
            '<td><div class="label">' + I18nManager.get('form.subscription.cgu') + '</div></td>' +
            '</tr>' +
            '</table>' +
            '<input class="button" type="submit" value="' + I18nManager.get('form.subscription.ok') + '">' +
            '<div class="connect">' + I18nManager.get('form.subscription.connect') + '</div>' +
            '</div>'
            );
    
    var that = this;
    jQ.find('.button').bind('click', function(){that.subscribe(this);});
    jQ.find('.connect').bind('click', function(){that.displayConnectionPopup(this);});
    
    callback(jQ);
};

/**
 * Is executed just after the build
 */
SubscriptionFormContent.prototype.postBuild = function()
{
    this.getContent().find('.login .input').focus();
};

/**
 * Tries to create a new account and to connect to it
 */
SubscriptionFormContent.prototype.subscribe = function()
{
    var login = this.getContent().find('.login .input').val();
    var email = this.getContent().find('.email .input').val();
    var password = this.getContent().find('.password .input').val().hash();
    
    if(!Util.checkPassword(password))
    {
        this.getContent().find('.password .label').css('color', '#d00');
        this.getContent().find('.password .input').css('border-color', '#d00');
        this.getContent().find('.password .input').focus();
    }
    else
    {
        this.getContent().find('.password .label').css('color', '#444');
        this.getContent().find('.password .input').css('border-color', '#444');
    }
    if(!Util.checkEmail(email))
    {
        this.getContent().find('.email .label').css('color', '#d00');
        this.getContent().find('.email .input').css('border-color', '#d00');
        this.getContent().find('.email .input').focus();
    }
    else
    {
        this.getContent().find('.email .label').css('color', '#444');
        this.getContent().find('.email .input').css('border-color', '#444');
    }
    if(!Util.checkLogin(login))
    {
        this.getContent().find('.login .label').css('color', '#d00');
        this.getContent().find('.login .input').css('border-color', '#d00');
        this.getContent().find('.login .input').focus();
    }
    else
    {
        this.getContent().find('.login .label').css('color', '#444');
        this.getContent().find('.login .input').css('border-color', '#444');
    }
    if(!Util.checkLogin(login) || !Util.checkPassword(password) || !Util.checkEmail(email) || this.processing)
    {
        return false;
    }
    this.showLoadingGIF();
    var that = this;
    var account = new Account();
    account.email = email;
    account.login = login;
    account.password = password;
    SubscriptionManager.getInstance().subscribeAJAX(account, function(result)
    {
        if(result.success && result.checkObject())
        {
            ConnectionManager.getInstance().connectAJAX(login, password, true, false, function(result2)
            {
                that.hideLoadingGIF();
                if(result2.success && result2.checkObject())
                {
                    that.hide();
                }
                else
                {
                    that.updateMessage(result2.html());
                }
            });
        }
        else
        {
            that.updateMessage(result.html());
        }
    });
};

/**
 * Displays the connection popup
 */
SubscriptionFormContent.prototype.displayConnectionPopup = function()
{
    ConnectionFormContent.getInstance().buildPopup({});
};