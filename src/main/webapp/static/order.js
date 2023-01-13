var wholeOrder = []

function getOrderItemUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderitem";
}

function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}

function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
}

function resetForm() {
    var element = document.getElementById("order-item-form");
    element.reset()
}


function displayOrderItemList(data){
	var $tbody = $('#order-item-table').find('tbody');
	$tbody.empty();

	// logic is flawedshould be in a loop
	for(var i in wholeOrder) {
        var e = wholeOrder[i];  
        var buttonHtml = '<button class="btn"><i class="fa-regular fa-circle-xmark"></i></button>';
        var row = '<tr>'
            + '<td>' + JSON.parse(wholeOrder[i]).barcode + '</td>'
            + '<td>'  + JSON.parse(wholeOrder[i]).qty + '</td>'
            + '<td>'  + JSON.parse(wholeOrder[i]).sellingPrice + '</td>'
            + '<td>'  + buttonHtml + '</td>'
            + '</tr>';

        $tbody.append(row);
    }

}

function checkOrderItemExist() {
    for(i in wholeOrder) {
        var barcode = JSON.parse(wholeOrder[i]).barcode;
        console.log(barcode);
        var temp_barcode = $("#order-item-form input[name=barcode]").val();
        console.log(temp_barcode);
        if(temp_barcode == barcode) {
            console.log("Exist");
            return true;
        }
    }
    return false;
}


function changeQty(vars) {
    var barcode = vars[0];
    console.log(barcode);
    var qty = parseInt(vars[1]);
    //console.log(qty);
    // console.log(wholeOrder);
    for(i in wholeOrder) {
        let data = {}
        var temp_barcode = JSON.parse(wholeOrder[i]).barcode;
        if(temp_barcode == barcode) {
            var prev = parseInt(JSON.parse(wholeOrder[i]).qty);
            var new_qty = prev + qty;
            //console.log(new_qty);
            //var string1 = new_qty.toString();
            console.log(string1);
            data["barcode"]=JSON.parse(wholeOrder[i]).barcode;
            data["qty"]=string1;
            data["sellingPrice"]=JSON.parse(wholeOrder[i]).sellingPrice;
            console.log(data);
            var new_data = JSON.stringify(data);
            wholeOrder[i] = new_data;
        }
    }
    console.log(wholeOrder);
}

function checkSellingPrice(vars) {
    var barcode = vars[0];
    var sp = vars[2];
    for (i in wholeOrder) {
        var temp_barcode = JSON.parse(wholeOrder[i]).barcode;
        if (temp_barcode == barcode) {
            var cur_sp = parseInt(JSON.parse(wholeOrder[i]).sellingPrice);
            if (cur_sp == sp) {
                return true;
            }
        }
    }
    return false;
}
var barcode = []
function getBarcode(data) {
    for (i in data) {
        var b = data[i].barcode;
        barcode.push(b);
    }
    //console.log(barcode);
}

function getProductList() {
    var url = getProductUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            barcode = []
            getBarcode(data);
            
        },
        error: handleAjaxError
     });
}

function checkBarcode(data) {
    //console.log(data);
    for (i in barcode) {
        if (barcode[i] == data) {
            //console.log(barcode[i]);
            //console.log(data);
            
            return true;
        }

    }
    return false;
}

function addOrderItem(event) {
    var $form = $("#order-item-form");
    var json = toJson($form);
    var jsonObj = $.parseJSON(json);
    var barcode1 = $("#order-item-form input[name=barcode]").val();

    if(checkOrderItemExist() ) {
        console.log("inside check");
        let vars = []
        
        var barcode = $("#order-item-form input[name=barcode]").val();
        var qty = $("#order-item-form input[name=qty]").val();
        var sp = $("#order-item-form input[name=sellingPrice]").val();
        
        vars.push(barcode);
        vars.push(qty);
        vars.push(sp);
        if (checkSellingPrice(vars) == false) {
            alert("Selling price cannot be different");
        }
        else {
            changeQty(vars);
        }
    }
    else {
        if (checkBarcode(barcode1) == false) {
            alert("Barcode does not exist in the inventory");
        }
        else {
            wholeOrder.push(json)
        }
    }
    resetForm();

    displayOrderItemList(wholeOrder)

}

function displayCart() {
    $('#add-order-item-modal').modal('toggle');
    // table should be empty
    var $tbody = $('#order-item-table').find('tbody');
    $tbody.empty();
}

function getOrderItemList() {
    var jsonObj = $.parseJSON('[' + wholeOrder + ']');
    console.log(jsonObj);
}

function displayOrderList(data) {
    
    var $tbody = $('#Order-table').find('tbody');
    $tbody.empty();
    for(var i in data){
        var e = data[i];
        console.log(data[i].orderDate); 
   		var buttonHtml = ' <button onclick="displayEditInventory(' + e.id + ')">edit</button>'
   		var row = '<tr>'
   		+ '<td>' + e.id + '</td>'
    	+ '<td>'  + e.orderDate + '</td>'
    	+ '<td>' + buttonHtml + '</td>'
   		+ '</tr>';
   		$tbody.append(row);
    }
}


function getOrderList() {
    var url = getOrderUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
        success: function (data) {
            console.log(data);
    	   		displayOrderList(data);
    	   },
    	   error: handleAjaxError
    });
}

function arrayToJson() {
    let json = [];
    for(i in wholeOrder) {
        let data = {};
        data["barcode"]=JSON.parse(wholeOrder[i]).barcode;
        data["qty"]=JSON.parse(wholeOrder[i]).qty;
        data["sellingPrice"]=JSON.parse(wholeOrder[i]).sellingPrice;
        json.push(data);
    }
    return JSON.stringify(json);
}

function placeOrder() {
    var url = getOrderItemUrl();

    var jsonObj = arrayToJson();
    console.log(jsonObj);
    $.ajax({
        url: url,
        type: 'POST',
        data: jsonObj,
        headers: {
       	 'Content-Type': 'application/json'
        },
        success: function(response) {
            $('#add-order-item-modal').modal('toggle');
            getOrderList();
            wholeOrder = []
        },
        error: handleAjaxError
    });

    return false;
}

function init(){
	$('#add-order').click(displayCart);
	$('#add-order-item').click(addOrderItem);
	$('#place-order').click(placeOrder);
	$('#refresh-data').click(getOrderList);
//	$('#update-inventory').click(updateInventory);
//	$('#refresh-data').click(getInventoryList);
//	$('#upload-data').click(displayUploadData);
//	$('#process-data').click(processData);
//	$('#download-errors').click(downloadErrors);
//    $('#inventoryFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getOrderItemList)
$(document).ready(getOrderList)
$(document).ready(getProductList)
//$(document).ready(getInventoryList);