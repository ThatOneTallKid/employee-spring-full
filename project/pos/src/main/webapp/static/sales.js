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

function init() {
    $('#refresh-data').click(getSalesList);

}

$(document).ready(init);
$(document).ready(getSalesList);