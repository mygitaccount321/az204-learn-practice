function trigger(){

    var context = getContext();
    var container = context.getCollection();
    var response = context.getResponse();

    // item that was created
    var createdItem = response.getBody();

    var filterQuery = 'SELECT * FROM student r WHERE r.grade = "1111"';
    var accept = container.queryDocuments(container.getSelfLink(), filterQuery,
        updateMetadataCallback);
    if(!accept) throw "Unable to update metadata, abort";

    function updateMetadataCallback(err, items, responseOptions) {
        if(err) throw new Error("Error" + err.message);

        if(items.length != 1) throw 'Unable to find metadata document'+items.length;

        var metadataItem = items[0];

        // update metadata
        metadataItem.createdItems = 1;
        metadataItem.trigger="updateProperties";
        var accept = container.replaceDocument(metadataItem._self,
            metadataItem, function(err, itemReplaced) {
                    if(err) throw "Unable to update metadata, abort";
            });

        if(!accept) throw "Unable to update metadata, abort";
        return;
    }
}