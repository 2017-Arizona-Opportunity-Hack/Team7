var httpRequest = new XMLHttpRequest();
var api = "http://localhost:8080/Users";
httpRequest.open("GET", api, false);
httpRequest.send();
console.log(httpRequest.responseText);

var json = JSON.stringify(JSON.parse(httpRequest.responseText));
//console.log(json);



var res = [];
json = json.split(":")[1];
json = json.substr(0, json.length - 1);

var inventoryArray = json.split(",");

inventoryArray[0] = inventoryArray[0].substr(1);
inventoryArray[inventoryArray.length - 1] = inventoryArray[inventoryArray.length - 1].substr(0, inventoryArray[inventoryArray.length - 1].length - 1)

for (var i = 0; i < inventoryArray.length; i++) {
//    var temp = inventoryArray[i].split(":");
//    var string = "{" + "\"name\":" + temp[0] + "," + "\"quantity\":" + temp[1] + "}";
//    console.log(string);
//    
//    res.push(JSON.parse(string));
    
    var string = "{\"userName\":" + inventoryArray[i] + "}";
    console.log(string);
    res.push(JSON.parse(string));
}

//console.log("-----");
//console.log(json);

$("h5").html(res);

$(function () {
    $('#table').bootstrapTable({
        data: res
    });
});