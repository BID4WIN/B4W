/**
 * Class I18nManagerContent.
 * 
 * @author Maxime Ollagnier
 */
I18nManagerContent.inherits(PropertiesManagerContent);
function I18nManagerContent()
{
};

/** Name of the content */
I18nManagerContent.prototype.name = 'I18nManager';

/**
 * Initialization
 */
I18nManagerContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
I18nManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.i18n.title') + '</div>' +
            '<input type="text" value="' + this.searchString + '" class="search" />' +
            '<div title="' + I18nManager.get('admin.property.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.property.add') + '" class="add click"></div>' +
            '<div title="' + I18nManager.get('admin.property.addlanguage') + '" class="addLanguage click"></div>' +
            '<div title="' + I18nManager.get('admin.property.refresh') + '" class="refresh click"></div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<div class="subContent"></div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('.search').bind('keyup', function(){that.handleKeySearch(this);});
    jQ.find('.refresh').bind('click', function(){that.refresh(this);});
    jQ.find('.add').bind('click', function(){that.add(this);});
    jQ.find('.addLanguage').bind('click', function(){that.addLanguage(this);});
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
I18nManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    I18nManager.getInstance().getObjectMap(
    {
        searchString : this.searchString,
        admin : true
    }, function(result)
    {
        if (result.success)
        {
            var propertyMap = result.getObject();
            if (Util.checkObject(that.propertyMap))
            {
                propertyMap.foreach(function(key, property)
                {
                    that.setExpansion(property);
                });
            }
            that.propertyMap = propertyMap;
            var jQTable = $('<table>');
            that.propertyMap.foreach(function(key, property)
            {
                that.fillJQTable(jQTable, property);
            });
            callback(jQTable);
        } else
        {
            that.updateMessage(result.html());
        }
    });
};

/**
 * TODO
 */
I18nManagerContent.prototype.setExpansion = function(property)
{
    if(this.visibleProperty == property.getFullKey())
    {
        this.showProperty(property);
        this.visibleProperty = undefined;
    }
    var oldProperty = this.propertyMap.get(property.getFullKey());
    if (typeof oldProperty != 'undefined')
    {
        property.expanded = oldProperty.expanded;
    }
    var that = this;
    property.properties.foreach(function(key, subProperty)
    {
        that.setExpansion(subProperty);
    });
};

/**
 * Fills the jQuery table with the given property and recursively for its
 * children.
 */
I18nManagerContent.prototype.fillJQTable = function(jQTable, property)
{
    jQTable.append(this.getJQTr(property));
    if (property.expanded)
    {
        var that = this;
        property.properties.foreach(function(key, subProperty)
        {
            that.fillJQTable(jQTable, subProperty);
        });
    }
};

/**
 * Generates the jQuery tr corresponding to the given property
 */
I18nManagerContent.prototype.getJQTr = function(property)
{
    var key = property.key;
    var fullKey = property.getFullKey();
    var parentFullKey = '';
    if(typeof property.parentProperty == 'object')
    {
        parentFullKey = property.parentProperty.getFullKey() + '.';
    }
    var id = fullKey.str2Id();
    var selected = '';
    if (id == this.selectedItem)
    {
        selected = ' selected';
    }
    var nodeType = '';
    if (this.isLeaf(property))
    {
        nodeType = 'leaf';
    }
    else
    {
        if (property.expanded)
        {
            nodeType = 'expanded';
        }
        else
        {
            nodeType = 'reduced';
        }
    }
    var htmlKey = '<div class="parent-key">' + parentFullKey.showStr(this.searchString, 'span', 'found') + '</div>' + key.showStr(this.searchString, 'span', 'found');
    var jQTr = $('<tr id="' + id + '" class="click' + selected + '"></tr>');
    
    var jQTdPath = $('<td class="path"></td>');
    var parentKeyArray = parentFullKey.split('.');
    for(var i = 0; i < parentKeyArray.length - 1; i++) jQTdPath.append('<div class="indent"></div>');
    jQTdPath.append('<div id="' + id + '" class="node click ' + nodeType + '"> </div>');
    jQTdPath.append('<div id="' + id + '" class="key dblclick">' + htmlKey + '</div>');
    jQTr.append(jQTdPath);
    
    jQTr.append('<td id="' + id + '" class="value dblclick">' + property.value.showStr(this.searchString, 'span', 'found') + '</td>');

    var that = this;
    jQTr.find('.node').bind('click', function(){that.switchExpansion(this);});
    jQTr.find('.key').bind('dblclick', function(){that.editKey(this);});
    jQTr.find('.value').bind('dblclick', function(){that.editValue(this);});
    jQTr.bind('click', function()
    {
        var it = this;
        that.selectItem(this, function()
        {
            if (typeof that.editingPropertyKey != 'undefined' &&
                that.editingPropertyKey != $(it).attr('id'))
            {
                that.refresh();
            }
        });
    });
    
    return jQTr;
};

/**
 * Refreshes the collection with items found on the server
 */
I18nManagerContent.prototype.refresh = function(callback)
{
    var that = this;
    this.rebuildSubContent(function()
    {
        that.editingPropertyKey = undefined;
        that.editingPropertyValue = undefined;
        if(typeof callback == 'function')
        {
            callback();
        }
    });
};

/**
 * Switches between expand and reduce for the property of the TR
 */
I18nManagerContent.prototype.switchExpansion = function(elem)
{
    var fullKey = $(elem).attr('id').id2Str();
    var property = this.propertyMap.get(fullKey);
    property.expanded = !property.expanded;
    this.refresh();
};

/**
 * Adds a new language with the two first letters of the search input
 */
I18nManagerContent.prototype.addLanguage = function()
{
    if (this.searchString.length != 2)
    {
        this.updateMessage('Please enter the two letters country code in the magic box.');
    } else
    {
        var that = this;
        var exists = false;
        this.propertyMap.foreach(function(key, value)
        {
            if (that.searchString == key)
            {
                that.updateMessage('This language already exists.');
                exists = true;
            }
        });
        if (!exists)
        {
            I18nManager.getInstance().addLanguage(that.searchString, function(result)
            {
                if (result.success)
                {
                    that.refresh();
                }
                that.updateMessage(result.html());
            });
        }
    }
};

/**
 * Adds a new property with default key and value as a child of the selected
 * property
 */
I18nManagerContent.prototype.add = function()
{
    var parentFullFullKey = '';
    var separator = '.';
    if (typeof this.selectedItem != 'undefined' && this.selectedItem != '')
    {
        parentFullFullKey = this.selectedItem.id2Str();
        var that = this;
        var i18n = new Property(
        {
            key : parentFullFullKey + separator + PropertiesManagerContent.DEFAULT_KEY
        });
        I18nManager.getInstance().addObject(i18n, function(result)
        {
            if (result.success)
            {
                var parentI18n = that.propertyMap.get(parentFullFullKey);
                if (typeof parentI18n == 'object')
                {
                    parentI18n.expanded = true;
                }
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    } else
    {
        this.updateMessage('Please select a parent i18n first.');
    }
};

/**
 * Puts the selected property in the trash property
 */
I18nManagerContent.prototype.remove = function()
{
    if (typeof this.selectedItem == 'string' && typeof this.editingPropertyKey == 'undefined' && typeof this.editingPropertyValue == 'undefined')
    {
        var oldFullFullKey = this.selectedItem.id2Str();
        if(oldFullFullKey.indexOf(PropertiesManagerContent.TRASH_KEY) > 0)
        {
            this.hardRemove();
        }
        else
        {
            var firstSeparatorIndex = oldFullFullKey.indexOf('.');
            var language = oldFullFullKey.substring(0, firstSeparatorIndex);
            var genericKey = oldFullFullKey.substring(firstSeparatorIndex + 1);
            var newFullFullKey = language + '.' + PropertiesManagerContent.TRASH_KEY + '.' + genericKey;
            var that = this;
            I18nManager.getInstance().moveObject(oldFullFullKey, newFullFullKey, function(result)
            {
                if (result.success)
                {
                    that.selectedItem = undefined;
                    that.visibleProperty = newFullFullKey.str2Id();
                    that.refresh(function()
                    {
                        $('tr#' + newFullFullKey.str2Id()).css('font-weight', 'bold');
                    });
                }
                that.updateMessage(result.html());
            });
        }
    }
};

/**
 * Removes the selected property
 */
I18nManagerContent.prototype.hardRemove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        I18nManager.getInstance().removeObject(this.selectedItem.id2Str(), function(result)
        {
            if (result.success)
            {
                that.selectedItem = undefined;
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};

/**
 * Switches a property key TD from text to input field to make it editable
 */
I18nManagerContent.prototype.editKey = function(elem)
{
    if (typeof this.editingPropertyKey == 'undefined')
    {
        this.editingPropertyKey = $(elem).attr('id');

        var that = this;
        var jQInput = $('<input class="keyup" type="text" value="' + $(elem).text() + '">');
        jQInput.bind('keyup', function(event){that.handleKeyUpKey(event, this);});

        $(elem).unbind('click');
        $(elem).unbind('dblclick');
        $(elem).empty();
        $(elem).css('padding', 0);
        $(elem).append(jQInput);
        jQInput.focus();
        jQInput.select();

        if(this.selectedItem != this.editingPropertyKey)
        {
            if(typeof this.selectedItem != 'undefined' && this.selectedItem != '')
            {
                $('#' + this.selectedItem).toggleClass('selected');
            }
            this.selectedItem = this.editingPropertyKey;
            $('#' + this.selectedItem).toggleClass('selected');
        }
        $('tr#' + this.editingPropertyKey).unbind('click');
    }
};

/**
 * Switches a property value TD from text to input field to make it editable
 */
I18nManagerContent.prototype.editValue = function(elem)
{
    if (typeof this.editingPropertyKey == 'undefined')
    {
        this.editingPropertyKey = $(elem).attr('id');
        this.editingPropertyValue = $(elem).text();

        var that = this;
        var jQInput = $('<input class="keyup" type="text" value="' + $(elem).text() + '">');
        jQInput.bind('keyup', function(event){that.handleKeyUpValue(event, this);});
        
        $(elem).unbind('click');
        $(elem).unbind('dblclick');
        $(elem).empty();
        $(elem).append(jQInput);
        jQInput.focus();
        jQInput.select();

        if(this.selectedItem != this.editingPropertyKey)
        {
            if(typeof this.selectedItem != 'undefined' && this.selectedItem != '')
            {
                $('#' + this.selectedItem).toggleClass('selected');
            }
            this.selectedItem = this.editingPropertyKey;
            $('#' + this.selectedItem).toggleClass('selected');
        }
        $('tr#' + this.editingPropertyKey).unbind('click');
    }
};

/**
 * Handles the key event on a property key TD input field
 */
I18nManagerContent.prototype.handleKeyUpKey = function(event, elem)
{
    var that = this;
    var oldFullFullKey = this.editingPropertyKey.id2Str();
    var newFullFullKey = $(elem).val();
    if (event.keyCode == '27' || event.keyCode == '13')
    {
        if (event.keyCode == '13' && newFullFullKey != oldFullFullKey)
        {
            if (newFullFullKey == '')
            {
                newFullFullKey = I18nManagerContent.TRASH_KEY + '.' + oldFullFullKey;
            }
            I18nManager.getInstance().moveObject(oldFullFullKey, newFullFullKey, function(result)
            {
                if (result.success)
                {
                    that.selectedItem = undefined;
                    that.visibleProperty = newFullFullKey.str2Id();
                    that.refresh(function()
                    {
                        $('tr#' + newFullFullKey.str2Id()).css('font-weight', 'bold');
                    });
                }
                that.updateMessage(result.html());
            });
        }
        else
        {
            this.refresh();
        }
    }
};

/**
 * Handles the key event on a property value TD input field
 */
I18nManagerContent.prototype.handleKeyUpValue = function(event, elem)
{
    var that = this;
    var fullFullKey = this.editingPropertyKey;
    var oldValue = this.editingPropertyValue;
    var newValue = $(elem).val();
    if (event.keyCode == '27' || event.keyCode == '13')
    {
        if (event.keyCode == '13' && newValue != oldValue)
        {
            var updatedI18n = this.propertyMap.get(fullFullKey.id2Str());
            updatedI18n.value = newValue;
            I18nManager.getInstance().saveObject(updatedI18n, function(result)
            {
                if (result.success)
                {
                    that.refresh();
                    $('tr#' + fullFullKey).css('font-weight', 'bold');
                }
                that.updateMessage(result.html());
            });
        }
        else
        {
            this.refresh();
        }
    }
};