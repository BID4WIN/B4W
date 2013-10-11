/**
 * NameSpace NavigationManager. Handles site navigation, navigation history,
 * etc...
 * 
 * @author Maxime Ollagnier
 */
NavigationManager = function()
{
};

/** Default content */
NavigationManager.DEFAULT_PAGE = "PropertiesManager";

/** Popup list */
NavigationManager.popupList = new List();

/**
 * Initializes the navigation functionalities
 */
NavigationManager.init = function()
{
    // Initializes the navigation history
    $.history.init(NavigationManager.requestContentLoad);
    
    // Binds all AJAX related links
    $('a.click').click(function()
    {
        $.history.load(this.href.replace(/^.*#/, ''));
        return false;
    });
};

/**
 * Requests the rebuild of the appropriate content
 */
NavigationManager.requestContentLoad = function(contentName)
{
    // If no content name is given, then the default one is used
    if(typeof contentName == 'undefined' || contentName == '')
    {
        contentName = NavigationManager.DEFAULT_PAGE;
    }
    // Retrieves the appropriate content
    var content = window[contentName + 'Content'];
    if(typeof content == 'undefined')
    {
        Logger.error('Could not retrieve the content "' + contentName + '".');
    }
    
    // Requests the build of the content
    content.getInstance().containerType = Content.TYPE_PAGE; 
    content.getInstance().build();
};

/**
 * Reload the webApp to the given page
 */
NavigationManager.reload = function(contentName)
{
    // If no content name is given, then the root page is reloaded
    if(typeof contentName == 'undefined')
    {
        contentName = '';
    }
    else
    {
        contentName = '#' + contentName;
    }
    window.location.replace(PropertyManager.getInstance().getString('server.root_location') + contentName);
};