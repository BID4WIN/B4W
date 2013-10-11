/**
 * Class PasswordReinitFormContent.
 * 
 * @author Maxime Ollagnier
 */
PasswordReinitFormContent.inherits(Content);
function PasswordReinitFormContent()
{
};

/** Name of the content */
PasswordReinitFormContent.prototype.name = 'PasswordReinitForm';

/**
 * Initialization
 */
PasswordReinitFormContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
PasswordReinitFormContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('form.password_reinit') + '</div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<form onsubmit="javascript:return false;">' +
            '<table>' +
            '<tr class="password one">' +
            '<td><div class="label">' + I18nManager.get('form.password_reinit.new_password_1') + '</div></td>' +
            '<td><input class="input" type="password" value="" /></td>' +
            '</tr>' +
            '<tr class="password two">' +
            '<td><div class="label">' + I18nManager.get('form.password_reinit.new_password_2') + '</div></td>' +
            '<td><input class="input" type="password" value="" /></td>' +
            '</tr>' +
            '</table>' +
            '<input class="button" type="submit" value="' + I18nManager.get('form.password_reinit.ok') + '">' +
            '</form>' +
            '</div>'
            );
    var  that = this;
    jQ.find('form .button').bind('click', function(){that.passwordReinit(this);});
    callback(jQ);
};

/**
 * Is executed just after the build
 */
PasswordReinitFormContent.prototype.postBuild = function()
{
    this.getContent().find('.password.one .input').focus();
};

/**
 * Calls the password reinit action. Connects the user and redirects him to the
 * home page if success
 */
PasswordReinitFormContent.prototype.passwordReinit = function()
{
    var password1 = this.getContent().find('.password.one .input').val().hash();
    var password2 = this.getContent().find('.password.two .input').val().hash();
    
    if(!Util.checkPassword(password2))
    {
        this.getContent().find('.password.two .label').css('color', '#d00');
        this.getContent().find('.password.two .input').css('border-color', '#d00');
        this.getContent().find('.password.two .input').focus();
    }
    else
    {
        this.getContent().find('.password.two .label').css('color', '#444');
        this.getContent().find('.password.two .input').css('border-color', '#444');
    }
    if(!Util.checkPassword(password1))
    {
        this.getContent().find('.password.one .label').css('color', '#d00');
        this.getContent().find('.password.one .input').css('border-color', '#d00');
        this.getContent().find('.password.one .input').focus();
    }
    else
    {
        this.getContent().find('.password.one .label').css('color', '#444');
        this.getContent().find('.password.one .input').css('border-color', '#444');
    }
    if(!Util.checkPassword(password1) || !Util.checkPassword(password2))
    {
        return false;
    }
    if(password1 != password2)
    {
        this.updateMessage(I18nManager.get('error.password_reinit.difference'));
        return false;
    }
    this.showLoadingGIF();
    var that = this;
    ConnectionManager.getInstance().passwordReinitAJAX(password1, function(result)
    {
        that.hideLoadingGIF();
        if(result.success)
        {
            NavigationManager.reload();
        }
        else
        {
            that.updateMessage(result.html());
            that.getContent().find('.password.one .input').val('').focus();
            that.getContent().find('.password.two .input').val('');
        }
    });
};