
//Custom Input
//[
//  {
//    "id": "219e7a8c-fcc7-4376-9643-15ee79878e99",
//    "grade": "11",
//    "name": "test1",
//    "ttl": 5
//  }
//]
// SAMPLE STORED PROCEDURE
function createToDoItems(items) {
   var collection = getContext().getCollection();
   var collectionLink = collection.getSelfLink();

   var count = 0;

    if (!items) throw new Error("The array is undefined or null.");

    var numItems = items.length;

    if(numItems == 0) {
        getContext().getResponse().setBody(0);
        return;
    }
    tryCreate(items[count], callback);

    function tryCreate(item, callback){
        var options = {disableAutomaticIdGeneration: false};
        var isAccepted = collection.createDocument(collectionLink, item, options, callback);

        if(!isAccepted) getContext().getResponse().setBody(count);

    }

    function callback(err, item, options) {
        if(err) throw err;
        count++;
        if(count >=numItems) {
           getContext().getResponse().setBody(count);
        } else {
            tryCreate(items[count], callback);
        }
    }

}