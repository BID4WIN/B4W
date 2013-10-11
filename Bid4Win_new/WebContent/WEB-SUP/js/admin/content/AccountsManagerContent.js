/**
 * Class AccountsManagerContent.
 * 
 * @author Maxime Ollagnier
 */
AccountsManagerContent.inherits(Content);
function AccountsManagerContent()
{
};

/** Name of the content */
AccountsManagerContent.prototype.name = 'AccountsManager';

/** Content account map */
AccountsManagerContent.prototype.accountMap = undefined;

/**
 * Initialization
 */
AccountsManagerContent.prototype.init = function()
{
    var that = this;
    this.addShortcut(new Shortcut('shift+delete', function(){that.hardRemove();}));
};

/**
 * Generates the jQuery object
 */
AccountsManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.account.title') + '</div>' +
            '<input type="text" value="' + this.searchString + '" class="search" />' +
            '<div title="' + I18nManager.get('admin.account.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.account.refresh') + '" class="refresh click"></div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<div class="subContent"></div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('.search').bind('keyup', function(){that.handleKeySearch(this);});
    jQ.find('.refresh').bind('click', function(){that.refresh(this);});
    jQ.find('.remove').bind('click', function(){that.remove(this);});

    this.getJQSubContent(function(jQSubContent)
    {
        jQ.find('.subContent').append(jQSubContent);
        callback(jQ);
    });
};

/**
 * TODO
 */
AccountsManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    AccountManager.getInstance().getAccountMapAJAX(this.searchString, function(result)
    {
        if(result.success && result.checkObject())
        {
            that.accountMap = result.getObject();
            that.accountMap.sort('lastName', 'asc');
            var jQTable = $('<table>');
            that.accountMap.foreach(function(id, account)
            {
                var jQTr = $(
                    '<tr id="' + account.id + '" class="account click dblclick">' +
                    '<td class="status"><div class="' + account.status + '">&nbsp;</div></td>' +
                    '<td class="name">' +
                    account.firstName.showStr(that.searchString, 'span', 'found') + ' ' +
                    account.middleName.showStr(that.searchString, 'span', 'found') + ' ' +
                    account.lastName.showStr(that.searchString, 'span', 'found') +
                    '</td>' +
                    '<td class="login">' + account.login.showStr(that.searchString, 'span', 'found') + '</td>' +
                    '<td class="email">' + account.email.showStr(that.searchString, 'span', 'found') + '</td>' +
                    '<td class="bidRightsNb">' + account.bidRightsNb + '</td>' +
                    '</tr>');
                
                if (that.selectedItem == account.id)
                {
                    jQTr.addClass('selected');
                }
                jQTr.bind('click', function(){that.selectItem(this);});
                jQTable.append(jQTr);
            });
            callback(jQTable);
        }
        else
        {
            that.updateMessage(result.html());
        }
    });
};

/**
 * Refreshes the collection with items found on the server
 */
AccountsManagerContent.prototype.refresh = function(callback)
{
    this.rebuildSubContent(callback);
};

/**
 * Sets the selected account to deleted status in DB
 */
AccountsManagerContent.prototype.remove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        AccountManager.getInstance().deleteAccountAJAX(this.selectedItem, function(result)
        {
            if (result.success && result.checkObject())
            {
                that.selectedItem = undefined;
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};

/**
 * Removes the selected account from DB
 */
AccountsManagerContent.prototype.hardRemove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        AccountManager.getInstance().removeAccountAJAX(this.selectedItem, function(result)
        {
            if (result.success && result.checkObject())
            {
                that.selectedItem = undefined;
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};