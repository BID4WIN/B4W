/**
 * Class PropertiesManagerContent.
 * 
 * @author Maxime Ollagnier
 */
PropertiesManagerContent.inherits(Content);
function PropertiesManagerContent()
{
};

/** Default constants */
PropertiesManagerContent.DEFAULT_KEY = 'x_new_x';
PropertiesManagerContent.DEFAULT_VALUE = '';
/** Trash root property key */
PropertiesManagerContent.TRASH_KEY = 'zzz';

/** Name of the content */
PropertiesManagerContent.prototype.name = 'PropertiesManager';

/** Content property map */
PropertiesManagerContent.prototype.propertyMap = undefined;

/** Key of the property being edited */
PropertiesManagerContent.prototype.editingPropertyKey = undefined;

/** Value of the property being edited */
PropertiesManagerContent.prototype.editingPropertyValue = undefined;

/** Key of the property being copied */
PropertiesManagerContent.prototype.copiedPropertyKey = undefined;

/** Key of the property that has to be visible */
PropertiesManagerContent.prototype.visibleProperty = undefined;

/**
 * Initialization
 */
PropertiesManagerContent.prototype.init = function()
{
    var that = this;
    this.addShortcut(new Shortcut('delete', function(){that.remove();}));
    this.addShortcut(new Shortcut('shift+delete', function(){that.hardRemove();}));
    this.addShortcut(new Shortcut('ctrl+c', function(){that.copy();}));
    this.addShortcut(new Shortcut('ctrl+v', function(){that.paste();}));
};

/**
 * Generates the jQuery object
 */
PropertiesManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.property.title') + '</div>' +
            '<input type="text" value="' + this.searchString + '" class="search" />' +
            '<div title="' + I18nManager.get('admin.property.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.property.add') + '" class="add click"></div>' +
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
PropertiesManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    PropertyManager.getInstance().getObjectMap(
    {
        searchString : this.searchString
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
PropertiesManagerContent.prototype.setExpansion = function(property)
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
PropertiesManagerContent.prototype.fillJQTable = function(jQTable, property)
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
PropertiesManagerContent.prototype.getJQTr = function(property)
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
PropertiesManagerContent.prototype.refresh = function(callback)
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
PropertiesManagerContent.prototype.switchExpansion = function(elem)
{
    var fullKey = $(elem).attr('id').id2Str();
    var property = this.propertyMap.get(fullKey);
    property.expanded = !property.expanded;
    this.refresh();
};

/**
 * Adds a new property with default key and value as a child of the selected
 * property
 */
PropertiesManagerContent.prototype.add = function()
{
    var parentFullKey = '';
    var separator = '.';
    if (typeof this.selectedItem != 'undefined' && this.selectedItem != '')
    {
        parentFullKey = this.selectedItem.id2Str();
    }
    if (parentFullKey == '')
    {
        separator = '';
    }
    var that = this;
    var newProperty = new Property(
    {
        key : parentFullKey + separator + PropertiesManagerContent.DEFAULT_KEY,
        value : PropertiesManagerContent.DEFAULT_VALUE
    });
    PropertyManager.getInstance().saveObject(newProperty, function(result)
    {
        if (result.success)
        {
            if (parentFullKey != '')
            {
                that.propertyMap.get(parentFullKey).expanded = true;
            }
            that.refresh();
        }
        that.updateMessage(result.html());
    });
};

/**
 * Puts the selected property in the trash property
 */
PropertiesManagerContent.prototype.remove = function()
{
    if (typeof this.selectedItem == 'string' && typeof this.editingPropertyKey == 'undefined' && typeof this.editingPropertyValue == 'undefined')
    {
        var oldFullKey = this.selectedItem.id2Str();
        if(oldFullKey.indexOf(PropertiesManagerContent.TRASH_KEY) == 0)
        {
            this.hardRemove();
        }
        else
        {
            var newFullKey = PropertiesManagerContent.TRASH_KEY + '.' + oldFullKey;
            var that = this;
            PropertyManager.getInstance().moveObject(oldFullKey, newFullKey, function(result)
            {
                if (result.success)
                {
                    that.selectedItem = undefined;
                    that.visibleProperty = newFullKey.str2Id();
                    that.refresh(function()
                    {
                        $('tr#' + newFullKey.str2Id()).css('font-weight', 'bold');
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
PropertiesManagerContent.prototype.hardRemove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        PropertyManager.getInstance().removeObject(this.selectedItem.id2Str(), function(result)
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
PropertiesManagerContent.prototype.editKey = function(elem)
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
PropertiesManagerContent.prototype.editValue = function(elem)
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
PropertiesManagerContent.prototype.handleKeyUpKey = function(event, elem)
{
    var that = this;
    var oldFullKey = this.editingPropertyKey.id2Str();
    var newFullKey = $(elem).val();
    if (event.keyCode == '27' || event.keyCode == '13')
    {
        if (event.keyCode == '13' && newFullKey != oldFullKey)
        {
            if (newFullKey == '')
            {
                newFullKey = PropertiesManagerContent.TRASH_KEY + '.' + oldFullKey;
            }
            PropertyManager.getInstance().moveObject(oldFullKey, newFullKey, function(result)
            {
                if (result.success)
                {
                    that.selectedItem = undefined;
                    that.visibleProperty = newFullKey.str2Id();
                    that.refresh(function()
                    {
                        $('tr#' + newFullKey.str2Id()).css('font-weight', 'bold');
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
PropertiesManagerContent.prototype.handleKeyUpValue = function(event, elem)
{
    var that = this;
    var fullKey = this.editingPropertyKey;
    var oldValue = this.editingPropertyValue;
    var newValue = $(elem).val();
    if (event.keyCode == '27' || event.keyCode == '13')
    {
        if (event.keyCode == '13' && newValue != oldValue)
        {
            var updatedProperty = PropertyManager.getInstance().getProperty(fullKey.id2Str());
            updatedProperty.value = newValue;
            PropertyManager.getInstance().saveObject(updatedProperty, function(result)
            {
                if (result.success)
                {
                    that.refresh();
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
 * Caches the key of the selected property
 */
PropertiesManagerContent.prototype.copy = function()
{
    if (typeof this.editingPropertyKey == 'undefined')
    {
        this.copiedPropertyKey = this.selectedItem;
    }
};

/**
 * Pastes the property whom key had been cached
 */
PropertiesManagerContent.prototype.paste = function()
{
    var that = this;
    if (typeof this.editingPropertyKey == 'undefined' && typeof this.copiedPropertyKey != 'undefined' && this.copiedPropertyKey != '')
    {
        var oldFullKey = this.copiedPropertyKey.id2Str();
        var newFullKey = '';
        if(typeof this.selectedItem == 'string' && this.selectedItem != '')
        {
            newFullKey = this.selectedItem.id2Str() + '.';
        }
        newFullKey += oldFullKey.substr(oldFullKey.lastIndexOf('.') + 1, oldFullKey.length);
        PropertyManager.getInstance().copyObject(oldFullKey, newFullKey, function(result)
        {
            if (result.success)
            {
                that.visibleProperty = newFullKey.str2Id();
                that.refresh(function()
                {
                    $('tr#' + newFullKey.str2Id()).css('font-weight', 'bold');
                });
            }
            that.updateMessage(result.html());
        });
    }
};

/**
 * Returns true if the given property has no child property.
 */
PropertiesManagerContent.prototype.isLeaf = function(property)
{
    return typeof property.properties == 'undefined' || property.properties.size == 0;
};

/**
 * Returns true if the given property can be seen in the property tree. In other
 * words if all its parents are expanded.
 */
PropertiesManagerContent.prototype.isSeen = function(property)
{
    if (typeof property.parentProperty == 'undefined')
    {
        return true;
    }
    return property.parentProperty.isSeen() && property.parentProperty.expanded;
};

/**
 * Makes the given property visible in the property tree. In other words it
 * makes all its parent properties expanded.
 */
PropertiesManagerContent.prototype.showProperty = function(property)
{
    if (typeof property.parentProperty != 'undefined')
    {
        this.showProperty(property.parentProperty);
        property.parentProperty.expanded = true;
    }
};