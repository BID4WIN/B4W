/**
 * Namespace AjaxManager. Handles Ajax basics.
 *
 * @author Maxime Ollagnier
 */
AjaxManager = function()
{
};

/** Cache of the current XmlHTTP requests */
AjaxManager.currentRequests = {};

/**
 * Handles the JSON request. Checks to see if the current request is already
 * being processed and handles errors.
 */
AjaxManager.getJSON = function(actionName, parameters, callback)
{
    // Makes parameters optional
    if (arguments.length == 2) 
    {
        actionName = arguments[0];
        parameters = {};
        callback = arguments[1];
    }
    
    // Checks if this request is already being processed
    if (!AjaxManager.currentRequests[actionName]) 
    {
        // Stores current request
        AjaxManager.currentRequests[actionName] = true;
        
        // Hack preventing IE from caching responses so the client gets the
        // latest version of the response
        parameters.nocache = new Date().getTime();
        
        // Make actual AJAX request.
        $.ajax(
        {
            type: 'GET',
            url: actionName,
            data: parameters,
            contentType: 'application/x-www-form-urlencoded;charset=ISO-8859-15',
            dataType: 'json',
            
            success: function(jsonString)
            {
                AjaxManager.currentRequests[actionName] = false;
                var jsonResult = AjaxManager.getObjectFromJSON(jsonString);
                var result = new Result(actionName, jsonResult);
                if(!result.success)
                {
                    result.log();
                }
                callback(result);
            },
            error: function(request)
            {
                AjaxManager.currentRequests[actionName] = false;
                var result = new Result(actionName,
                {
                    success: false
                });
                result.addError('error.ajax', true);
                result.log();
                callback(result);
            }
        });
    }
};

/**
 * TODO
 */
AjaxManager.getObjectFromJSON = function(jsonString)
{
    return eval('(' + jsonString + ')');
};

/**
 * TODO
 */
AjaxManager.getObjectFromJSON = function(jsonString)
{
    return eval('(' + jsonString + ')');
};

/** Current uploading entities */
AjaxManager.uploadingEntities = undefined;

/**
 * TODO
 */
AjaxManager.uploadFile = function(action, inputId, fileName, path, callback)
{
    try 
    {
        if (typeof AjaxManager.uploadingEntities == 'undefined') 
        {
            var id = new Date().getTime().toString();
            AjaxManager.uploadingEntities = 
            {
                iFrame: AjaxManager.createUploadIframe(id),
                form: AjaxManager.createUploadForm(id, inputId, fileName, path, 'iFrame' + id, action),
                callback: function(jsonObject)
                {
                    $(AjaxManager.uploadingEntities.iFrame).remove();
                    $(AjaxManager.uploadingEntities.form).remove();
                    AjaxManager.uploadingEntities = undefined;
                    callback();
                }
            };
            AjaxManager.uploadingEntities.form.submit();
        }
    } 
    catch (ex) 
    {
        AjaxManager.uploadingEntities = undefined;
        console.error(ex);
        callback(false);
    }
};

/**
 * TODO
 */
AjaxManager.createUploadIframe = function(id)
{
    var iFrame = document.createElement('iframe');
    iFrame.id = 'iFrame' + id;
    iFrame.name = 'iFrame' + id;
    iFrame.style.position = 'absolute';
    iFrame.style.top = '-1000px';
    iFrame.style.left = '-1000px';
    document.body.appendChild(iFrame);
    return iFrame;
};

/**
 *
 */
AjaxManager.createUploadForm = function(id, inputId, fileName, path, iFrameId, action)
{
    var form = $('<form action="' + action + '" method="POST" enctype="multipart/form-data"></form>');
    form.attr('id', 'form' + id);
    form.attr('name', 'form' + id);
    form.attr('target', iFrameId);
    form.css('position', 'absolute');
    form.css('top', '-1200px');
    form.css('left', '-1200px');
    var oldInput = $('#' + inputId);
    var newInput = $('#' + inputId).clone();
    oldInput.attr('id', 'file' + id);
    oldInput.before(newInput);
    form.append(oldInput);
    var fileNameInput = $('<input name="fileName" type="text" value="' + fileName + '"></input>');
    form.append(fileNameInput);
    var pathInput = $('<input name="path" type="text" value="' + path + '"></input>');
    form.append(pathInput);
    form.appendTo('body');
    return form;
};
