/**
 * Class SubscriptionManager. Handles the whole subscription issue.
 *
 * @author Maxime Ollagnier
 */
function SubscriptionManager()
{
};

/**
 * Initializes the subscription manager.
 */
SubscriptionManager.prototype.subscribeAJAX = function(account, callback)
{
    var parameters =
    {
        email: account.email,
        login: account.login,
        password: account.password
    };
    AjaxManager.getJSON('SubscribeAction', parameters, function(result)
    {
        callback(result);
    });
};