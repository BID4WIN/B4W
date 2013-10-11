/**
 * Class Product.
 * 
 * @author Maxime Ollagnier
 */

Product.inherits(Bean);
function Product(json)
{
    this.nameMap = new Map();
    this.summaryMap = new Map();
    this.amountMap = new Map();
    this.imageMap = new Map();
    if(!Util.checkObject(json))
    {
        this.id = undefined;
        this.reference = '';
        this.name = 'Name';
    }
    else
    {
        this.id = json.id;
        this.reference = json.reference;
        this.name = json.name;
        for(var languageCode in json.nameMap) this.nameMap.put(languageCode, json.nameMap[languageCode]);
        for(var languageCode in json.summaryMap) this.summaryMap.put(languageCode, json.summaryMap[languageCode]);
        for(var currencyCode in json.amountMap) this.amountMap.put(currencyCode, json.amountMap[currencyCode]);
        for(var imageId in json.imageMap) this.imageMap.put(imageId, json.imageMap[imageId]);
    }
}

/**
 * Returns the JSON object
 */
Product.prototype.getJSON = function()
{
    var json =
    {
        'id' : this.id,
        'reference' : this.reference
    };
    this.nameMap.foreach(function(languageCode, name)
    {
        json['name' + languageCode] = name;
    });
    this.summaryMap.foreach(function(languageCode, summary)
    {
        json['summary' + languageCode] = summary;
    });
    this.amountMap.foreach(function(currencyCode, amount)
    {
        json['price' + currencyCode] = amount;
    });
    return json;
};