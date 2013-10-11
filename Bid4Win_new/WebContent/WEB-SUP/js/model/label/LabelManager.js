/**
 * Class LabelManager. Handles synchrone and asynchrone actions on label.
 * 
 * @author Maxime Ollagnier
 */
LabelManager.inherits(AjaxObjectManager);
function LabelManager()
{
    this.objectName = 'Label';
    this.objectType = Label;
    this.map = new Map();
};

/**
 * Fetches a label from the local label map.
 */
LabelManager.prototype.getLabel = function(labelId)
{
    if (typeof labelId == 'string')
    {
        return this.map.get(labelId);
    }
    return undefined;
};

/**
 * Fetches a label from the server with the given id.
 */
LabelManager.prototype.getLabelAJAX = function(id, callback)
{
    this.getObjectMap(
    {
        id : id
    }, callback);
};

/**
 * Fetches a label map from the server with the given searchString.
 */
LabelManager.prototype.getLabelMapAJAX = function(searchString, callback)
{
    this.getObjectMap(
    {
        searchString : searchString
    }, callback);
};

/**
 * Saves the given label into sever persistence system.
 */
LabelManager.prototype.saveLabelAJAX = function(label, callback)
{
    this.saveObject(label, callback);
};

/**
 * Deletes the label pointed by the given ID from the server persistence system.
 */
LabelManager.prototype.removeLabelAJAX = function(labelId, callback)
{
    this.removeObject(labelId, callback);
};

/**
 * Returns the HTML select string.
 * @param labelMap The map of labels to exclude from the select
 */
LabelManager.prototype.getHTMLSelect = function(labelMap, addLink)
{
    var html = '<select class="label-select">';
    html += '<option class="instructions" value="">Select a label</option>';
    this.map.foreach(function(labelId, label)
    {
        if(!labelMap.contains(labelId))
        {
            html += '<option value="' + label.getId() + '" title="' + label.description + '">' + label.name + '</option>';
        }
    });
    if(typeof addLink == 'boolean' && addLink == true)
    {
        html += '<option class="label-new" value="-1" title="Create a new label.">New label</option>';
    }
    html += '</select>';
    return html;
};