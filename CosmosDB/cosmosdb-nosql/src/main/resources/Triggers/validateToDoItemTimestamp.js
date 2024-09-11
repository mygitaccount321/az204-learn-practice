function validateToDoItemTimestamp(){
    var context = getContext();
    var request = context.getRequest();


    var itemToCreate = request.getBody();

    if(!("timestamp" in itemToCreate)) {
       var ts = new Date();
       itemToCreate["timestamp"] = ts.getTime();
    }
    request.setBody(itemToCreate);
}