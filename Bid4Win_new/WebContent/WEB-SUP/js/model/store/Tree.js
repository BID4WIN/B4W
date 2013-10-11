/**
 * Class Tree.
 * 
 * @author Maxime Ollagnier
 */
function Tree(storeType, contentManager)
{
    this.storeType = storeType;
    this.rootDirectory = new Directory('');
    this.selectedItem = '';
    this.contentManager = contentManager;
};

Tree.prototype.updateAJAX = function(callback)
{
    var that = this;
    DirectoryManager.getInstance().getDirectoryMapAJAX(this.storeType, this.rootDirectory.getFullPath(), function(result)
    {
        if (result.success && result.checkObject())
        {
            var newRootDirectory = new Directory(that.rootDirectory.getFullPath());
            newRootDirectory.directoryMap = result.getObject();
            that.copyExpanded(newRootDirectory, that.rootDirectory);
            that.rootDirectory = newRootDirectory;
        }
        callback();
    });
};

Tree.prototype.copyExpanded = function(newDirectory, oldDirectory)
{
    var that = this;
    newDirectory.expanded = oldDirectory.expanded;
    newDirectory.directoryMap.foreach(function(id, newSubDirectory)
    {
        newSubDirectory.parentDirectory = newDirectory;
        var oldSubDirectory = oldDirectory.directoryMap.get(newSubDirectory.getId());
        if(Util.checkObject(oldSubDirectory))
        {
            that.copyExpanded(newSubDirectory, oldSubDirectory);
        }
    });
};

/**
 * Generates the jQuery object
 */
Tree.prototype.getJQ = function(callback)
{
    var that = this;
    this.updateAJAX(function()
    {
        var jQTable = $('<table id="tree-table"></table>');
        that.appendJQDirectoryTR(jQTable, that.rootDirectory, 0);
        callback(jQTable);
    });
};

/**
 * Generates the jQuery object for a directory
 */
Tree.prototype.appendJQDirectoryTR = function(jQTable, directory, indentLevel)
{
    var jQTD = $('<td class="directory"></td>');
    for(var i = 0; i < indentLevel; i++)
    {
        jQTD.append('<div class="indent"></div>');
    }
    var id = directory.getFullPath().str2Id('/');
    if(directory.directoryMap.size == 0)
    {
        jQTD.append('<div class="node click inactive" id="' + id + '"></div>');
    }
    else if(directory.expanded)
    {
        jQTD.append('<div class="node click expanded" id="' + id + '"></div>');
    }
    else
    {
        jQTD.append('<div class="node click reduced" id="' + id + '"></div>');
    }
    var selected = '';
    if(directory.getFullPath() == this.selectedItem)
    {
        selected = ' selected';
    }
    var name = directory.name;
    if(name == "")
    {
        name = "/";
    }
    jQTD.append('<div class="name click' + selected + '" id="' + id + '">' + name + '</div>');

    var that = this;
    jQTD.find('.node').bind('click', function(){that.switchExpansion(this);});
    jQTD.find('.node.inactive').unbind('click');
    jQTD.find('.name').bind('click', function(){that.selectItem(this);});
    jQTD.find('.name').bind('mouseup', function(){that.moveItem(this);});
    
    jQTable.append($('<tr class="directory"></tr>').append(jQTD));
    
    if(directory.expanded)
    {
        var that = this;
        directory.directoryMap.foreach(function(id, subDirectory)
        {
            that.appendJQDirectoryTR(jQTable, subDirectory, indentLevel + 1);
        });
    }
};

/**
 * Switches between expand and reduce for a directory node
 */
Tree.prototype.switchExpansion = function(elem)
{
    var directory = this.rootDirectory.getDirectory($(elem).attr('id').id2Str('/'));
    directory.expanded = !directory.expanded;
    this.contentManager.refreshTree();
};

/**
 * Selects the clicked item
 */
Tree.prototype.selectItem = function(elem)
{
    var oldSelectedItem = this.selectedItem;
    this.selectedItem = $(elem).attr('id').id2Str('/');
    if (typeof oldSelectedItem != 'undefined') 
    {
        $('#' + oldSelectedItem.str2Id('/')).removeClass('selected');
    }
    if (typeof this.selectedItem != 'undefined') 
    {
        $('#' + this.selectedItem.str2Id('/')).addClass('selected');
    }
    if (this.selectedItem != oldSelectedItem) 
    {
        this.contentManager.refresh();
    }
};

/**
 * Moves the dragged item
 */
Tree.prototype.moveItem = function(elem)
{
    if(Util.checkString(this.contentManager.dragged) && Util.checkFunction(this.contentManager.moveItem))
    {
        var directoryFullPath = $(elem).attr('id').id2Str('/');
        var itemId = this.contentManager.dragged;
        this.contentManager.moveItem(itemId, directoryFullPath);
    }
};