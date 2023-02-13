var wholeInventory = [];

function getInventoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/inventory";
}

function getInventoryReportUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/inventory/exportcsv";
}

function resetForm() {
  var element = document.getElementById("inventory-form");
  element.reset();
}

function arrayToJson() {
  let json = [];
  for (i in wholeInventory) {
    let data = {};
    data["barcode"] = JSON.parse(wholeInventory[i]).barcode;
    data["qty"] = JSON.parse(wholeInventory[i]).qty;
    json.push(data);
  }
  return JSON.stringify(json);
}

function isJson(str) {
  try {
    JSON.parse(str);
  } catch (e) {
    return false;
  }
  return true;
}

//BUTTON ACTIONS
function addInventory(event) {
  //Set the values to update
  var $form = $("#inventory-form");
  if(!validateForm($form)){
      return;
  }
  var qty = $("#inventory-form input[name=qty]").val();
  if(qty.includes("-") || qty.includes("+") || qty.includes("*") || qty.includes("/") || qty.includes(".")){
    toastr.error("Invalid Quantity");
    return;
  }
  var json = toJson($form);
  var url = getInventoryUrl();
  wholeInventory.push(json);
  var jsonObj = arrayToJson();
  console.log(wholeInventory);
  console.log(url);
  $.ajax({
    url: url,
    type: "POST",
    data: jsonObj,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      wholeInventory = [];
      resetForm();
      toastr.success("Inventory Added Successfully", "Success : ");
      getInventoryList();
          $("#add-inventory-modal").modal("toggle");

    },
    error: function (response) {
      console.log(response);
      if (response.status == 403) {
        toastr.error("Error: 403 unauthorized");
      } else {
        var resp = JSON.parse(response.responseText);
        if (isJson(resp.message) == true) {
          var jsonObj = JSON.parse(resp.message);
          console.log(jsonObj);
          toastr.error(jsonObj[0].message, "Error : ");
        } else {
          handleAjaxError(response);
        }
      }
      wholeInventory = [];
    },
  });

  return false;
}

function updateInventory(event) {
  //Get the ID

  var id = $("#inventory-edit-form input[name=id]").val();
  var url = getInventoryUrl() + "/" + id;

  //Set the values to update

  var $form = $("#inventory-edit-form");
  if(!validateForm($form)){
      return;
  }
  var qty = $("#inventory-edit-form input[name=qty]").val();
    if(qty.includes("-") || qty.includes("+") || qty.includes("*") || qty.includes("/") || qty.includes(".")){
      toastr.error("Invalid Quantity");
      return;
    }
  var json = toJson($form);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
  $("#edit-inventory-modal").modal("toggle");
      toastr.success("Inventory updated Successfully", "Success : ");
      getInventoryList();
    },
    error: handleAjaxError,
  });

  return false;
}

function getInventoryList() {
  var url = getInventoryUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventoryList(data);
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#inventoryFile")[0].files[0];
  console.log(file);
  if(file.name.split('.').pop() != "tsv"){
  	    toastr.error("file format is not tsv, Not Allowed");
  	}
  	else {
  	readFileData(file, readFileDataCallback);
  	}

}

function readFileDataCallback(results) {
  fileData = results.data;
  console.log(fileData);
  var filelen = fileData.length;
  if(filelen == 0){
    toastr.error("file is empty, Not Allowed");
  }
  else if (filelen > 5000) {
    toastr.error("file length exceeds 5000, Not Allowed");
  } else {
    uploadRows();
  }
}

function uploadRows() {
  //Update progress
  updateUploadDialog();
  //If everything processed then return

  $("#process-data").prop("disabled", true);

  var json = JSON.stringify(fileData);
  console.log(json);
  var headers = ["barcode", "qty"];
  var jsonq = JSON.parse(json);
    	console.log(jsonq[0]);
    	console.log(Object.keys(jsonq).length);
    	console.log(Object.keys(jsonq[0]));
    	if(Object.keys(jsonq[0]).length != headers.length){
    	    toastr.error("File column number do not match. Please check the file and try again");
            return;
    	}
    	for(var i in headers){
            if(!jsonq[0].hasOwnProperty(headers[i])){
                toastr.error('File columns do not match. Please check the file and try again');
                return;
            }
        }

  var url = getInventoryUrl();

  //Make ajax call
  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      console.log(response);
      errorData = response;
      resetForm();
      toastr.success("Inventory.tsv uploaded Successfully" );
      getInventoryList();
    },
    error: function (response) {
      if (response.status == 403) {
        toastr.error("403 FOrbidden");
      } else {
        var resp = JSON.parse(response.responseText);
        var jsonObj = JSON.parse(resp.message);
        console.log(jsonObj);
        errorData = jsonObj;
        processCount = fileData.length;
        console.log(response);
        toastr.error("Error in uploading Inventory.tsv, Download Error File");
        $("#download-errors").prop("disabled", false);
        resetForm();
      }
    },
  });
}

function downloadErrors() {
  writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button onclick="displayEditInventory(' + e.id + ')" class="btn"><i class="fa-solid fa-pen"></i></button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>'  + e.qty + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditInventory(id) {
  var url = getInventoryUrl() + "/" + id;
  console.log(url);
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventory(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#inventoryFile");
  $file.val("");
  $("#inventoryFileName").html("Choose File");
  //Reset various counts
  fileData = [];
  errorData = [];
  //Update counts
  updateUploadDialog();
}

function updateUploadDialog() {
  $("#rowCount").html("" + fileData.length);
}

function updateFileName() {
  var $file = $("#inventoryFile");
  var fileName = $file.val();
  $("#inventoryFileName").html(fileName);
}

function displayUploadData() {
  console.log("hello");
  resetUploadDialog();
  $("#upload-inventory-modal").modal("toggle");
  $("#download-errors").prop("disabled", true);
  $("#process-data").prop("disabled", true);
}

function displayInventory(data) {
  $("#inventory-edit-form input[name=barcode]").val(data.barcode);
  $("#inventory-edit-form input[name=qty]").val(data.qty);
  $("#inventory-edit-form input[name=id]").val(data.id);
  $("#edit-inventory-modal").modal("toggle");
}


function printReport() {
  window.location.href = getInventoryReportUrl();
}

function activateUpload() {
  $("#process-data").prop("disabled", false);
   $("#download-errors").prop('disabled', true);
}

function modalToggle() {
    $("#add-inventory-modal").modal("toggle");
    resetForm();
}

//INITIALIZATION CODE
function init() {
  $("#add-inventory").click(addInventory);
  $("#update-inventory").click(updateInventory);
  $("#refresh-data").click(getInventoryList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#inventoryFile").on("change", updateFileName);
  $("#print-report").click(printReport);
  $("#inventoryFile").click(activateUpload);
  $("#add-data").click(modalToggle);
}

$(document).ready(init);
$(document).ready(getInventoryList);
