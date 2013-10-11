/**
 * Class Content.
 * Handles any type of container (e.g. "page" or "popup").
 * To create a new content, make a new class that inherits from the Content
 * class giving a type among the type list.
 *
 * @author Maxime Ollagnier
 */
function Content()
{
};

/** Content container types */
Content.TYPE_PAGE = "page";
Content.TYPE_POPUP = "popup";
Content.TYPE_SNIPPET = "snippet";
Content.TYPE_BOARD = "board";

/** Current content */
Content.currentContent = undefined;

/** Popup stack */
Content.popupStack = new List();

/** Content container type */
Content.prototype.containerType = Content.TYPE_PAGE;
/** Content name */
Content.prototype.name = undefined;
/** Building parameters */
Content.prototype.parameters = {};
/** Logging message */
Content.prototype.message = '';
/** Selected item */
Content.prototype.selectedItem = undefined;
/** String to search */
Content.prototype.searchString = '';
/** Processing flag */
Content.prototype.processing = false;
/** Content shortcut list */
Content.prototype.shortcuts = new Array();

/**
 * Makes verification
 */
Content.prototype.check = function()
{
    if (typeof this.containerType != 'string' || this.containerType == '') 
    {
        throw new Error('Contant container type not defined.');
    }
    if (typeof this.name != 'string' || this.name == '') 
    {
        throw new Error('Content name not defined.');
    }
    if (typeof this.init != 'function') 
    {
        throw new Error('Content "init" method not defined.');
    }
    if (typeof this.getJQ != 'function') 
    {
        throw new Error('Content "getJQ" method not defined.');
    }
};

/**
 * Builds the content
 */
Content.prototype.build = function(parameters, callback)
{
    this.check();
    this.shortcuts = new Array();
    this.parameters = parameters || {};
    this.init();
    var that = this;
    that.getJQ(function(jQ)
    {
        var jQContainer = that.getContainer();
        jQContainer.find('.click').unbind('click');
        jQContainer.find('.dblclick').unbind('dblclick');
        jQContainer.find('.keypress').unbind('keypress');
        jQContainer.find('.keyup').unbind('keyup');
        var jQContent = that.getContent();
        jQContent.empty();
        jQContent.append(jQ);
        that.hideLoadingGIF();
        ShortcutManager.shortcuts = that.shortcuts;
        if (that.containerType == Content.TYPE_POPUP && Content.popupStack.getLast() != that) 
        {
            Content.popupStack.push(that);
        }
        if (that.containerType != Content.TYPE_POPUP) 
        {
            that.postBuild();
        }
        Content.currentContent = that;
        if (typeof callback == 'function') 
        {
            callback();
        }
    });
};

/**
 * Is executed just after the build
 */
Content.prototype.postBuild = function()
{
};

/**
 * Builds the content as a page
 */
Content.prototype.buildPage = function(parameters, callback)
{
    this.containerType = Content.TYPE_PAGE;
    $.historyLoad(this.name);
};

/**
 * Builds the content as a popup
 */
Content.prototype.buildPopup = function(parameters, callback)
{
    this.containerType = Content.TYPE_POPUP;
    var that = this;
    this.build(parameters, function()
    {
        that.show();
        that.postBuild();
        
        var jQContainer = that.getContainer();
        var jQFog = jQContainer.find('.fog');
        var jQWindow = jQContainer.find('.window');
        
        var top = 100; //Math.max(50, (screen.height - jQWindow.height()) / 4);
        var left = (screen.width - jQWindow.width()) / 2;
        jQWindow.css('top', top);
        jQWindow.css('left', left);
        
        var fogHeight = Math.max(screen.height, parseInt(jQWindow.height()) + 50);
        jQFog.css('height', fogHeight);
        jQFog.bind('click', function()
        {
            that.hide();
        });
        
        that.addShortcut(new Shortcut('escape', function()
        {
            that.back();
        }));
        if (typeof callback == 'function') 
        {
            callback();
        }
    });
};

/**
 * Builds the content as a snippet
 */
Content.prototype.buildSnippet = function(parameters, callback)
{
    this.containerType = Content.TYPE_SNIPPET;
    this.build(parameters, function()
    {
        if (typeof callback == 'function') 
        {
            callback();
        }
    });
};

/**
 * Rebuilds the content
 */
Content.prototype.rebuild = function(callback)
{
    if(this.containerType == Content.TYPE_POPUP)
        this.rebuildPopup(callback);
    else
        this.build(this.parameters, callback);
};

/**
 * Rebuilds the content as a popup
 */
Content.prototype.rebuildPopup = function(callback)
{
    this.buildPopup(this.parameters, callback);
};

/**
 * Builds the sub content
 */
Content.prototype.rebuildSubContent = function(callback)
{
    if (typeof this.getJQSubContent != 'function') 
    {
        throw new Error('Content "getJQSubContent" method not defined.');
    }
    var that = this;
    this.getJQSubContent(function(jQSubContent)
    {
        var jQContent = that.getSubContent();
        jQContent.find('.click').unbind('click');
        jQContent.find('.dblclick').unbind('dblclick');
        jQContent.find('.keypress').unbind('keypress');
        jQContent.find('.keyup').unbind('keyup');
        jQContent.empty();
        jQContent.append(jQSubContent);
        if (typeof callback == 'function') 
        {
            callback();
        }
    });
};

/**
 * Builds the tree
 */
Content.prototype.rebuildTree = function(callback)
{
    if (!Util.checkObject(this.tree)) 
    {
        throw new Error('Content "tree" not defined.');
    }
    var that = this;
    this.tree.getJQ(function(jQTreeTable)
    {
        var jQTree = that.getTree();
        jQTree.find('.click').unbind('click');
        jQTree.find('.dblclick').unbind('dblclick');
        jQTree.find('.keypress').unbind('keypress');
        jQTree.find('.keyup').unbind('keyup');
        jQTree.empty();
        jQTree.append(jQTreeTable);
        if (typeof callback == 'function') 
        {
            callback();
        }
    });
};

/**
 * Is executed just after the build
 */
Content.prototype.postBuild = function()
{
};

/**
 * Returns the jQuery container of the content
 */
Content.prototype.getContainer = function()
{
    return $('#' + this.containerType);
};

/**
 * Returns the jQuery content
 */
Content.prototype.getContent = function()
{
    return this.getContainer().find('#content');
};

/**
 * Returns the jQuery content
 */
Content.prototype.getSubContent = function()
{
    return this.getContent().find('#' + this.name + ' .subContent');
};

/**
 * Returns the jQuery tree
 */
Content.prototype.getTree = function()
{
    return this.getContent().find('#' + this.name + ' .tree');
};

/**
 * Rebuilds the previous popup in the popup stack or hide if there is no popup
 * in the popup stack
 */
Content.prototype.back = function(parameters)
{
    if (this.containerType == Content.TYPE_POPUP) 
    {
        Content.popupStack.pop();
        var previousPopup = Content.popupStack.pop();
        if (typeof previousPopup == 'object') 
        {
            jQuery.extend(previousPopup.parameters, parameters);
            previousPopup.rebuildPopup();
        }
        else 
        {
            this.hide();
        }
    }
};

/**
 * Hides the whole content container
 */
Content.prototype.hide = function()
{
    this.getContainer().hide();
    this.updateMessage('');
    Content.popupStack.clear();
};

/**
 * Shows the whole content container
 */
Content.prototype.show = function()
{
    this.getContainer().show();
};

/**
 * Hides the loading GIF image(s)
 */
Content.prototype.hideLoadingGIF = function()
{
    this.processing = false;
    this.getContainer().find('.loading').hide();
};

/**
 * Shows the loading GIF image(s)
 */
Content.prototype.showLoadingGIF = function()
{
    this.processing = true;
    this.getContainer().find('.loading').show();
};

/**
 * Updates the log message
 */
Content.prototype.updateMessage = function(message)
{
    this.message = message;
    this.getContent().find('.message').html(this.message);
};

/**
 * Handles the key event for the search field
 */
Content.prototype.handleKeySearch = function(elem)
{
    var newSearchString = $(elem).val();
    if (this.searchString != newSearchString) 
    {
        this.searchString = newSearchString;
        this.refresh();
    }
};

/**
 * Selects the clicked item
 */
Content.prototype.selectItem = function(elem, callback)
{
    var oldSelectedItem = this.selectedItem;
    this.selectedItem = $(elem).attr('id');
    
    if (this.selectedItem != oldSelectedItem) 
    {
        if (typeof oldSelectedItem != 'undefined') 
        {
            $('#' + oldSelectedItem).removeClass('selected');
        }
        if (typeof this.selectedItem != 'undefined' && this.selectedItem != '') 
        {
            $('#' + this.selectedItem).addClass('selected');
        }
    }
    else if (typeof this.selectedItem != 'undefined' && this.selectedItem != '') 
    {
        $('#' + this.selectedItem).toggleClass('selected');
        if (!$('#' + this.selectedItem).hasClass('selected')) 
        {
            this.selectedItem = undefined;
        }
    }
    
    if (typeof callback == 'function') 
    {
        callback();
    }
};

/**
 * Adds the given shortcut to the shortcut list of the container
 */
Content.prototype.addShortcut = function(shortcut)
{
    this.shortcuts.push(shortcut);
};

/**
 * Returns a string for the object
 */
Content.prototype.toString = function()
{
    return this.name;
};
