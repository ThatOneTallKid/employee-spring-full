var wholeOrder = [];

function getOrderItemUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/order/item";
}

function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/order";
}

function getProductUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/product";
}

function OrderViewUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/order/view";
}

function getInventoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  console.log(baseUrl);
  return baseUrl + "/api/inventory";
}

function getInvoiceUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  console.log(baseUrl);
  return baseUrl + "/api/order/invoice";
}

function resetForm() {
  var element = document.getElementById("order-item-form");
  element.reset();
}

function deleteOrderItem(id) {
  wholeOrder.splice(id, 1);
  displayOrderItemList(wholeOrder);
}

function editOrderItem(id) {

   var $form= $("#order-item-form");
   console.log($form);
    var json = toJson($form);
    console.log(json);
    var bCode = JSON.parse(json).barcode;
    var qty = JSON.parse(json).qty;
    var sellingPrice = JSON.parse(json).sellingPrice;
    // check if form is empty
    if (bCode == "" && qty == "" && sellingPrice == "") {
        console.log(JSON.parse(wholeOrder[id]).barcode);
          $("#order-item-form input[name=barcode]").val(
            JSON.parse(wholeOrder[id]).barcode);
          $("#order-item-form input[name=qty]").val(JSON.parse(wholeOrder[id]).qty);
          $("#order-item-form input[name=sellingPrice]").val(
            JSON.parse(wholeOrder[id]).sellingPrice
          );
          deleteOrderItem(id);
    }
    else{
        toastr.error("Please add item to cart before editing");
        return;
    }

}

function displayOrderItemList(data) {
  var $tbody = $("#order-item-table").find("tbody");
  $tbody.empty();
  for (var i in wholeOrder) {


    var e = wholeOrder[i];
    var buttonHtml =
      '<button onclick="deleteOrderItem(' +
      i +
      ')" class="btn"><i class="fa-regular fa-circle-xmark"></i></button>';
    var editHtml =
      '<button onclick="editOrderItem(' +
      i +
      ')" class="btn"><i class="fa-solid fa-pen-to-square"></i></button>';
    var row =
      "<tr>" +
      "<td>" +
      JSON.parse(wholeOrder[i]).barcode +
      "</td>" +
      "<td>" +
      JSON.parse(wholeOrder[i]).qty +
      "</td>" +
      "<td>" +
      parseFloat(JSON.parse(wholeOrder[i]).sellingPrice).toFixed(2) +
      "</td>" +
      "<td>" +
      editHtml +
      buttonHtml +
      "</td>" +
      "</tr>";

    $tbody.append(row);
  }
}

function checkOrderItemExist() {
  for (i in wholeOrder) {
    var barcode = JSON.parse(wholeOrder[i]).barcode;
    console.log(barcode);
    var temp_barcode = $("#order-item-form input[name=barcode]").val();
    console.log(temp_barcode);
    if (temp_barcode == barcode) {
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
  for (i in wholeOrder) {
    let data = {};
    var temp_barcode = JSON.parse(wholeOrder[i]).barcode;
    if (temp_barcode == barcode) {
      var prev = parseInt(JSON.parse(wholeOrder[i]).qty);
      var new_qty = prev + qty;
      if (new_qty > barcode_qty.get(barcode)) {
        toastr.error("Quantity not available in the inventory");
        return;
      }

      //console.log(new_qty);
      var string1 = new_qty.toString();
      console.log(string1);
      data["barcode"] = JSON.parse(wholeOrder[i]).barcode;
      data["qty"] = string1;
      data["sellingPrice"] = JSON.parse(wholeOrder[i]).sellingPrice;
      console.log(data);
      var new_data = JSON.stringify(data);
      wholeOrder[i] = new_data;
    }
  }
  console.log(wholeOrder);
}

function checkSellingPrice(vars) {
  var barcode = vars[0];
  var sp = parseFloat(vars[2]).toFixed(2);
  for (i in wholeOrder) {
    var temp_barcode = JSON.parse(wholeOrder[i]).barcode;
    if (temp_barcode == barcode) {
      var cur_sp = parseFloat(JSON.parse(wholeOrder[i]).sellingPrice).toFixed(2);
      if (cur_sp == sp) {
        return true;
      }
    }
  }
  return false;
}

const barcode_qty = new Map();
function getBarcode(data) {
  for (i in data) {
    let vars = [];
    vars.push(data[i].id);
    vars.push(data[i].barcode);
    getQtyFromInventory(vars);
  }
  console.log(barcode_qty);
}

function getQtyFromInventory(vars) {
  var url = getInventoryUrl() + "/" + vars[0];
  var temp_qty;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      console.log(data.qty);
      barcode_qty.set(vars[1], data.qty);
    },
    error: handleAjaxError,
  });

  return temp_qty;
}

function getProductList() {
  var url = getInventoryUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      barcode_qty.clear();
      getBarcode(data);
    },
    error: handleAjaxError,
  });
}

function checkBarcode(data) {
  console.log(data);
  console.log(barcode_qty[data]);
  console.log(barcode_qty.has(data));
  if (barcode_qty.has(data)) {
    return true;
  }
  return false;
}
var inv_qty = null;
var inv_barcode = null;
var mrp = null;

var check = 1;
function getInventory(barcode) {
    mrp = null;
  var url = getInventoryUrl() + "/barcode?barcode=" + barcode;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      inv_barcode = data.barcode;
      inv_qty = data.qty;
        mrp=data.mrp;
      barcode_qty.set(data.barcode, data.qty);
      addItem();

    },
    error: function (data) {
      toastr.error("Barcode does not exist in the Inventory");
        return;
    },
  });
}

function addItem() {
  var $form = $("#order-item-form");

var qty = $("#order-item-form input[name=qty]").val();
  if(qty.includes("-") || qty.includes("+") || qty.includes("*") || qty.includes("/") || qty.includes(".")){
    toastr.error("Invalid Quantity");
    return;
  }
  var sellP = $("#order-item-form input[name=sellingPrice]").val();
 if(sellP > mrp){
    toastr.error("Selling Price cannot be greater than MRP");
    return;
    }

  var json = toJson($form);
  var jsonObj = $.parseJSON(json);
  var barcode1 = $("#order-item-form input[name=barcode]").val();


  var qty = $("#order-item-form input[name=qty]").val();

  if (qty > barcode_qty.get(barcode1)) {
    toastr.error("Quantity is not present in inventory");
    return;

  } else {
    var _qty = barcode_qty.get(barcode1) - qty;
    var sp = $("#order-item-form input[name=sellingPrice]").val();

    if (sp <= 0) {
      toastr.error("Price cannot be negative or zero");
    } else if (qty <= 0) {
      toastr.error("Quantity cannot be negative or zero");
    } else {
      if (checkOrderItemExist()) {
        console.log("inside check");
        let vars = [];

        var barcode = $("#order-item-form input[name=barcode]").val();
        var qty = $("#order-item-form input[name=qty]").val();
        var sp = $("#order-item-form input[name=sellingPrice]").val();

        vars.push(barcode);
        vars.push(qty);
        vars.push(sp);
        if (checkSellingPrice(vars) == false) {
          toastr.error("Selling price cannot be different");
        } else {
          changeQty(vars);
        }
      } else {
        wholeOrder.push(json);
        resetForm();
      }


      displayOrderItemList(wholeOrder);
    }
  }
}

function addOrderItem(event) {
  check = 1;
  var $form = $("#order-item-form");
  if(!validateForm($form)){
      return;
  }
  var barcode1 = $("#order-item-form input[name=barcode]").val();
  getInventory(barcode1);
}

function displayCart() {
  emptyCart();

  $("#add-order-item-modal").modal("toggle");
  // table should be empty
  var $tbody = $("#order-item-table").find("tbody");
  $tbody.empty();
  resetForm();
}

function getOrderItemList() {
  var jsonObj = $.parseJSON("[" + wholeOrder + "]");
}

function displayOrderView(data) {
    console.log(data);
    var totalAmount = 0.0;
    var $tbody = $('#order-view-table').find('tbody');
    $tbody.empty();
    for(var i in data){
        var e = data[i];
        var qty =parseInt(e.qty);
        var sp = parseFloat(e.sellingPrice).toFixed(2);
        var total = qty * sp;
        totalAmount += total;
   		var row = '<tr>'
   		+ '<td>' + e.barcode + '</td>'
   		+ '<td>' + e.name + '</td>'
    	+ '<td>'  + e.qty + '</td>'
    	+ '<td>'  + parseFloat(e.sellingPrice).toFixed(2) + '</td>'
    	+ '<td>'  + parseFloat(total).toFixed(2) + '</td>'
   		+ '</tr>';
   		$tbody.append(row);
    }
    console.log(totalAmount);
    var $tfoot = $('#order-view-table').find('tfoot');
    $tfoot.empty();
    var row = '<tr>'
            + '<td colspan=3></td>'
       		+ '<td> <b>Total</b> </td>'
        	+ '<td><b>'  + parseFloat(totalAmount).toFixed(2) + '</b></td>'
       		+ '</tr>';
    $tfoot.append(row);

}

function getOrderInfoForView(id) {

  var url = OrderViewUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayOrderView(data);
    },
    error: handleAjaxError,
  });
}

function printOrder(id) {
  window.location.href = getInvoiceUrl() + "/" + id;
}

function OrderView(id) {
  $("#order-view-modal").modal("toggle");
  var $head = $('#order-view-modal').find('h5');
    $head.empty();
    var row = 'Order: ' + id  ;
    $head.append(row);
  getOrderInfoForView(id);
}

function displayOrderList(data) {
    var $tbody = $('#Order-table').find('tbody');
    $tbody.empty();
    for(var i in data){
        var e = data[i];
   		var buttonHtml = ' <button  onclick="OrderView(' + e.id + ')" class="btn"><i class="fa-solid fa-eye"></i></button>'
   		buttonHtml += '  <button onclick="printOrder('+e.id+')" class="btn"><i class="fa-solid fa-print"></i></button>';
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
    type: "GET",
    success: function (data) {
      console.log(data);
      displayOrderList(data);
    },
    error: handleAjaxError,
  });
}

function arrayToJson() {
  let json = [];
  for (i in wholeOrder) {
    let data = {};
    data["barcode"] = JSON.parse(wholeOrder[i]).barcode;
    data["qty"] = JSON.parse(wholeOrder[i]).qty;
    data["sellingPrice"] = JSON.parse(wholeOrder[i]).sellingPrice;
    json.push(data);
  }
  return JSON.stringify(json);
}

function placeOrder() {
  var url = getOrderItemUrl();
  let len = wholeOrder.length;
  console.log(len);
  if (len == 0) {
    toastr.error("Cart empty! Order cannot be placed.");
  } else {
    var jsonObj = arrayToJson();
    console.log(jsonObj);
    $.ajax({
      url: url,
      type: "POST",
      data: jsonObj,
      headers: {
        "Content-Type": "application/json",
      },
      success: function (response) {
        $("#add-order-item-modal").modal("toggle");
        toastr.success("Order Placed Successfully", "Success : ");
        getOrderList();
        wholeOrder = [];
      },
      error: handleAjaxError,
    });
  }
  return false;
}


function emptyCart() {
  wholeOrder = [];
}

function init() {
  $("#add-order").click(displayCart);
  $("#add-order-item").click(addOrderItem);
  $("#place-order").click(placeOrder);
  $("#refresh-data").click(getOrderList);
  $("#cart-cancel").click(emptyCart);
}

$(document).ready(init);
$(document).ready(getOrderItemList);
$(document).ready(getOrderList);
