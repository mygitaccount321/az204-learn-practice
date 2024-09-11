
//input :
//[
//  {
//    "id": "219e7a8c-fcc7-4376-9643-15ee79878e9w91",
//    "grade": "11",
//    "name": "test1",
//    "ttl": 5
//  }
//]
//
//out:11 (console log)

function sample(arr) {
    if (typeof arr === "string") arr = JSON.parse(arr);

    arr.forEach(function(a) {
        // do something here
        console.log(a.grade);
    });
}