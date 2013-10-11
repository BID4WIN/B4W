function AdminContentManager()
{
}

AdminContentManager.prototype.buildAdminMenu = function()
{
    var jQ =
        '<div id="admin-menu">' +
        '<a href="#PropertiesManager" class="click">Properties</a>::' +
        '<a href="#I18nManager" class="click">I18n</a>::' +
        '<a href="#ProductsManager" class="click">Products</a>::' +
        '<a href="#ImagesManager" class="click">Images</a>::' +
        '<a href="#LabelsManager" class="click">Labels</a>::' +
        '<a href="#AuctionsManager" class="click">Auctions</a>::' +
        '<a href="#AccountsManager" class="click">Accounts</a>' +
        '</div>';
    
    $('#header').append(jQ);
};
