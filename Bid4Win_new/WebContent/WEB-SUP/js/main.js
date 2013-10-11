/**
 * jQuery initialization functions
 */
$(document).ready(function()
{
    // Les composants html sont initialement cach�s
    $('#board').hide();
    $('#popup').hide();
    
    // Tentative de connection
    ConnectionManager.getInstance().init(function()
    {
        // Initialisation des propri�t�s
        PropertyManager.getInstance().init(function()
        {
            // Initialisation de l'i18n
            I18nManager.getInstance().init(function()
            {
                ConnectionManager.getInstance().refreshStatusSnippet();
                ShortcutManager.init();
                NavigationManager.init();
                AdminContentManager.getInstance().buildAdminMenu();
            });
        });
    });
});
