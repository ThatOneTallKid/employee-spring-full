function getSalesUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/sales";
}

function displaySalesList(data) {
    var $tbody = $('#Sales-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        var row = '<tr>'
		+ '<td>' + e.date + '</td>'
		+ '<td>'  + e.invoicedOrderCount + '</td>'
		+ '<td>' + e.invoicedItemsCount + '</td>'
		+ '<td>' + e.totalRevenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
    }
}

function getSalesList() {
    var url = getSalesUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displaySalesList(data);
	   },
	   error: handleAjaxError
	});
}

function resetForm() {
    var element = document.getElementById("sales-form");
    element.reset()
}



function getFilteredList(event) {
    var $form = $("#sales-form");
    var json = toJson($form);
    console.log(json);
    var url = getSalesUrl()+ "/filter";
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
         success: function (response) {
             resetForm();
            displaySalesList(response);
            console.log("success BC");
        },
        error: handleAjaxError
     });
 
     return false;
}

function init() {
    $('#refresh-data').click(getSalesList);
    $('#apply-filter').click(getFilteredList);

}

$(document).ready(init);
$(document).ready(getSalesList);