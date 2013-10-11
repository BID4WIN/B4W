/**
 * Class ConnectionFormContent.
 * 
 * @author Maxime Ollagnier
 */
ConnectionFormContent.inherits(Content);
function ConnectionFormContent()
{
};

/** Name of the content */
ConnectionFormContent.prototype.name = 'ConnectionForm';

/** Send password block display flag */
ConnectionFormContent.prototype.sendPasswordBlockDisplayed = false;

/**
 * Initialization
 */
ConnectionFormContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
ConnectionFormContent.prototype.getJQ = function(callback)
{
    var login = $.cookie('login');
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="loading">&nbsp;</div>' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('form.connection') + '</div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<form onsubmit="javascript:return false;">' +
            '<table>' +
            '<tr class="login">' +
            '<td><div class="label">' + I18nManager.get('form.connection.login') + '</div></td>' +
            '<td><input class="input" type="text" value="' + (login?login:'') + '" /></td>' +
            '</tr>' +
            '<tr class="password">' +
            '<td><div class="label">' + I18nManager.get('form.connection.password') + '</div></td>' +
            '<td><input class="input" type="password" value="" /></td>' +
            '</tr>' +
            '<tr class="rememberMe">' +
            '<td><input class="input" type="checkbox" checked/></td>' +
            '<td>' +
            '<div class="label">' + I18nManager.get('form.connection.remember_me') + '</div>' +
            '</td>' +
            '</tr>' +
            '<tr class="reconnect">' +
            '<td><input class="input" type="checkbox" /></td>' +
            '<td>' +
            '<div class="label">' + I18nManager.get('form.connection.reconnect') + '</div>' +
            '<div class="warning">' + I18nManager.get('form.connection.reconnect_warning') + '</div>' +
            '</td>' +
            '</tr>' +
            '</table>' +
            '<input class="button" type="submit" value="' + I18nManager.get('form.connection.ok') + '">' +
            '</form>' +
            '<div class="noAccount">' + I18nManager.get('form.connection.no_account') + '</div>' +
            '<div class="passwordForgotten">' + I18nManager.get('form.connection.password_forgotten') + '</div>' +
            '<div class="sendPassword">' +
            '<div class="title">' + I18nManager.get('form.connection.send_password.title') + '</div>' +
            '<div class="description">' + I18nManager.get('form.connection.send_password.description') + '</div>' +
            '<input class="button" type="submit" value="' + I18nManager.get('form.connection.send_password.button') + '" />' +
            '</div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('form .button').bind('click', function(){that.connection(this);});
    jQ.find('.noAccount').bind('click', function(){that.displaySubscriptionPopup(this);});
    jQ.find('.passwordForgotten').bind('click', function(){that.displaySendPasswordBlock(this);});
    jQ.find('.sendPassword .button').bind('click', function(){that.sendPasswordReinitMail(this);});
    
    callback(jQ);       
};

/**
 * Is executed just after the build
 */
ConnectionFormContent.prototype.postBuild = function()
{
    var login = $.cookie('login');
    if (!Util.checkStringNotEmpty(login))
    {
        this.getContent().find('.login .input').focus();
    }
    else
    {
        this.getContent().find('.password .input').focus();
    }
    this.sendPasswordBlockDisplayed = false;
};

/**
 * Tries to connect to the account
 */
ConnectionFormContent.prototype.connection = function()
{
    var loginOrEmail = this.getContent().find('.login .input').val();
    var password = this.getContent().find('.password .input').val().hash();
    
    if(Util.checkStringEmpty(password))
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
    if(Util.checkStringEmpty(loginOrEmail))
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
    if(Util.checkStringEmpty(loginOrEmail) || Util.checkStringEmpty(password) || this.processing)
    {
        return false;
    }
    var rememberMe = this.getContent().find('.rememberMe .input').attr('checked') == 'checked';
    var reconnect = this.getContent().find('.reconnect .input').attr('checked') == 'checked';
    this.showLoadingGIF();
    var that = this;
    ConnectionManager.getInstance().connectAJAX(loginOrEmail, password, rememberMe, reconnect, function(result)
    {
        that.hideLoadingGIF();
        if(result.success && result.checkObject())
        {
            that.hide();
        }
        else
        {
            that.updateMessage(result.html());
            that.getContent().find('.password .input').val('').focus();
        }
    });
};

/**
 * Displays the subscription popup
 */
ConnectionFormContent.prototype.displaySubscriptionPopup = function()
{
    SubscriptionFormContent.getInstance().buildPopup({});
};

/**
 * Displays the send password block
 */
ConnectionFormContent.prototype.displaySendPasswordBlock = function()
{
    if(!this.sendPasswordBlockDisplayed)
    {
        this.getContent().find('.sendPassword').css('display', 'block');
        this.sendPasswordBlockDisplayed = true;
    }
    else
    {
        this.getContent().find('.sendPassword').css('display', 'none');
        this.sendPasswordBlockDisplayed = false;
    }
};

/**
 * Sends an email to the address of the account related to the given login or
 * email
 */
ConnectionFormContent.prototype.sendPasswordReinitMail = function()
{
    var loginOrEmail = this.getContent().find('.login .input').val();
    if(!Util.checkString(loginOrEmail))
    {
        this.getContent().find('.login .label').css('color', '#d00');
        this.getContent().find('.login .input').css('border-color', '#d00');
        this.getContent().find('.login .input').focus();
    }
    else
    {
        this.getContent().find('.login .label').css('color', '#444');
        this.getContent().find('.login .input').css('border-color', '#444');
        this.showLoadingGIF();
        var that = this;
        ConnectionManager.getInstance().passwordReinitMailAJAX(loginOrEmail, function(result)
        {
            that.hideLoadingGIF();
            if(result.success)
            {
                that.updateMessage(I18nManager.get('form.connection.send_password.sent'));
            }
            else
            {
                that.updateMessage(result.html());
                that.getContent().find('.login .input').val('').focus();
            }
        });
    }
};