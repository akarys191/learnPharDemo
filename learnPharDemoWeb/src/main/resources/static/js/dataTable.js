
 const $tableID = $('#table');
 const $totalPages = $('#totalPages').val();

//TODO check it
 $('#pagination-inventory-list').twbsPagination({
      totalPages: $totalPages == 0 ? 1 : $totalPages,
      visiblePages: $totalPages == 0 ? 2 : $totalPages%5,
      next: $nextPaginationMessage,
      prev: $prevPaginationMessage,
      first: $firstPaginationMessage,
      last: $lastPaginationMessage,
      initiateStartPageClick: false,
      onPageClick: function (event, page) {
        var searchValue = $('#listInventory').val();
        console.log("listInventory: "+searchValue);
        var urlLink = $urlLinklistInventory+'?page='+page+'&cashRegistryId='+$cashRegistryId;
        $tableID.load(urlLink);
      }
 });
/*
$(document).ready( function () {
    $('#dataTable').DataTable( {
        "processing": true,
        "serverSide": true,
         "ajax": {
             "url": "/rest/inventory/listDataTable",
              "data": function (data) {
                    data.myKey = "myValue";
                    planify(data);
               }
         }
    } );
});

function planify(data) {
var map = {0:"inventoryId", 1:"inventoryVersionNumber",
2: "medicine", 3: "invoiceInventoryItemsSize", 4: "salesSize",
5: "totalActiveQuantity", 6: "totalActiveCost", 7: "totalActivePriceSum"};

    for (var i = 0; i < data.columns.length; i++) {
        column = data.columns[i];
        column.searchRegex = column.search.regex;
        column.searchValue = column.search.value;
        column.data = map[i];
        delete(column.search);
    }
 }



{
  "draw":"35",
  "columns":{
    "0":{
      "data":"0",
      "name":"",
      "searchable":"true",
      "orderable":"true",
      "search":{"value":"","regex":"false"}
    },
    "1":{
      "data":"1",
      "name":"",
      "searchable":"true",
      "orderable":"true",
      "search":{"value":"","regex":"false"}
    },
    "2":{
      "data":"2",
      "name":"",
      "searchable":"true",
      "orderable":"true",
      "search":{"value":"","regex":"false"}
    },
    "3":{
      "data":"3",
      "name":"",
      "searchable":"true",
      "orderable":"true",
      "search":{"value":"","regex":"false"}
    },
    "4":{
      "data":"4",
      "name":"",
      "searchable":"true",
      "orderable":"true",
      "search":{"value":"","regex":"false"}
    },
    "5":{
      "data":"5",
      "name":"",
      "searchable":"true",
      "orderable":"true",
      "search":{"value":"","regex":"false"}
    }
  },
  "order":[{"column":"0","dir":"asc"}],
  "start":"0",
  "length":"10",
  "search":{"value":"8","regex":"false"},
  "myKey":"myValue",
  "_":"1578240925333"
}
*/

