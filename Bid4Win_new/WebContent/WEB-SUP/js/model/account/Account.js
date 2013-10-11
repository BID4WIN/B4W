/**
 * Class Account.
 * 
 * @author Maxime Ollagnier
 */

Account.inherits(Bean);
function Account(jsonAccount)
{
    if(typeof jsonAccount != 'object')
    {
        this.id = '';
        this.status = '';
        this.login = '';
        this.password = '';
        this.email = '';
        this.bidRightsNb = 0;
        this.bidRightsValue = 0;
        this.firstName = '';
        this.middleName = '';
        this.lastName = '';
        this.birthDate = Date.parseDDIMMIYYYY('01/01/1900');
        this.sponsorId = '';
    }
    else
    {
        this.id = jsonAccount.id;
        this.status = jsonAccount.status;
        this.login = jsonAccount.login;
        this.password = jsonAccount.password;
        this.email = jsonAccount.email;
        this.bidRightsNb = jsonAccount.bidRightsNb;
        this.bidRightsValue = jsonAccount.bidRightsValue;
        this.firstName = jsonAccount.firstName;
        this.middleName = jsonAccount.middleName;
        this.lastName = jsonAccount.lastName;
        
        if(typeof jsonAccount.birthDate == 'string')
        {
            this.birthDate = Date.parseDDIMMIYYYY(jsonAccount.birthDate);
        }
        else
        {
            this.birthDate = jsonAccount.birthDate;
        }
        
        this.sponsorId = jsonAccount.sponsorId;
    }
}

/**
 * Returns the JSON object
 */
Account.prototype.getJSON = function()
{
    var json =
    {
        'id' : this.id,
        'status' : this.status,
        'login' : this.login,
        'password' : this.password,
        'email' : this.email,
        'bidRightsNb' : this.bidRightsNb,
        'bidRightsValue' : this.bidRightsValue,
        'firstName' : this.firstName,
        'middleName' : this.middleName,
        'lastName' : this.lastName,
        'birthDate' : this.birthDate,
        'sponsorId' : this.sponsorId
    };
    return json;
};