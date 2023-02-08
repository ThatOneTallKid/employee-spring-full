var brandData = {};
var wholeProduct = [];

function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brand";
}

function getProductUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/product";
}

function getBrandOption() {
  selectElement = document.querySelector("#inputBrand");
  output = selectElement.options[selectElement.selectedIndex].value;
  return output;
}

function resetForm() {
  var element = document.getElementById("product-form");
  element.reset();
}

function arrayToJson() {
  let json = [];
  for (i in wholeProduct) {
    let data = {};
    data["barcode"] = JSON.parse(wholeProduct[i]).barcode;
    data["brand"] = JSON.parse(wholeProduct[i]).brand;
    data["category"] = JSON.parse(wholeProduct[i]).category;
    data["name"] = JSON.parse(wholeProduct[i]).name;
    data["mrp"] = JSON.parse(wholeProduct[i]).mrp;
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
function addProduct(event) {
  //Set the values to update

  var $form = $("#product-form");
  var json = toJson($form);
  var url = getProductUrl();
  wholeProduct.push(json);
  var jsonObj = arrayToJson();
  console.log(url);
  $.ajax({
    url: url,
    type: "POST",
    data: jsonObj,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      wholeProduct = [];
      toastr.success("Product Added Successfully", "Success : ");
      resetForm();
      getProductList();
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
      wholeProduct = [];
    },
  });

  return false;
}

function updateProduct(event) {
  //Get the ID
  var id = $("#product-edit-form input[name=id]").val();
  var url = getProductUrl() + "/" + id;

  //Set the values to update
  var $form = $("#product-edit-form");
  var json = toJson($form);
  console.log(url);
  console.log(json);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
  $("#edit-product-modal").modal("toggle");
      toastr.success("Product Updated Successfully", "Success : ");
      getProductList();
    },
    error: handleAjaxError,
  });

  return false;
}

function getProductList() {
  var url = getProductUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayProductList(data);
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#productFile")[0].files[0];
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
  var filelen = fileData.length;
  if (filelen == 0) {
    toastr.error("file length is 0, Not Allowed");
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
  if (processCount == fileData.length) {
    return;
  }
  $("#process-data").prop("disabled", true);

  //Process next row

  var json = JSON.stringify(fileData);
  var headers = ["brand", "category", "name", "mrp", "barcode"];
  	jsonq = JSON.parse(json);
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

  var url = getProductUrl();

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
      processCount = fileData.length;
      resetForm();
      getProductList();
      toastr.success("Product.tsv Uploaded Successfully");
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
        toastr.error("Error in uploading Product.tsv, Download Error File");
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

function displayProductList(data){
   var $tbody = $('#Product-table').find('tbody');
   $tbody.empty();
   for(var i in data){
      var e = data[i];
      console.log(e);
      var buttonHtml = ' <button onclick="displayEditProduct(' + e.id + ')" class="btn"><i class="fa-solid fa-pen"></i></button>'
      var row = '<tr>'
      + '<td>' + e.barcode + '</td>'
      + '<td>'  + e.name + '</td>'
      + '<td>'  + e.brand + '</td>'
      + '<td>'  + e.category + '</td>'
      + '<td>'  + parseFloat(e.mrp).toFixed(2) + '</td>'
      + '<td>' + buttonHtml + '</td>'
      + '</tr>';
        $tbody.append(row);
   }
}

var editProduct = null;
function displayEditProduct(id) {
  var url = getProductUrl() + "/" + id;
  console.log(url);
  editProduct = id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayProduct(data);
      console.log(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#productFile");
  $file.val("");
  $("#productFileName").html("Choose File");
  //Reset various counts
  processCount = 0;
  fileData = [];
  errorData = [];
  //Update counts
  updateUploadDialog();
}

function updateUploadDialog() {
  $("#rowCount").html("" + fileData.length);
  $("#processCount").html("" + processCount);
  $("#errorCount").html("" + errorData.length);
}

function updateFileName() {
  var $file = $("#productFile");
  var fileName = $file.val();
  $("#productFileName").html(fileName);
}

function displayUploadData() {
  console.log("hello");
  resetUploadDialog();
  $("#upload-product-modal").modal("toggle");
  $("#download-errors").prop("disabled", true);
  $("#process-data").prop("disabled", true);
}

function displayProduct(data) {
  $("#product-edit-form input[name=barcode]").val(data.barcode);
  $("#product-edit-form input[name=name]").val(data.name);
  $("#product-edit-form input[name=mrp]").val(data.mrp);
  $("#product-edit-form input[name=id]").val(data.id);
  $("#product-edit-form input[name=brand]").val(data.brand);
  $("#product-edit-form input[name=category]").val(data.category);
  $("#edit-product-modal").modal("toggle");
}

function getBrandList() {
  var url = getBrandUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayBrandOptions(data);
    },
    error: handleAjaxError,
  });
}

function displayBrandOptions(data) {
  console.log(data);
  for (var i in data) {
    var a = data[i].brand;
    var b = data[i].category;
    if (!brandData.hasOwnProperty(a)) Object.assign(brandData, { [a]: [] });
    brandData[a].push(b);
  }
  console.log(brandData);
  var $elB = $("#inputBrand");
  $elB.empty();

  $elB.append(
    `<option value="none" selected disabled hidden>Select Brand</option>`
  );

  $.each(brandData, function (key, value) {
    $elB.append($("<option></option>").attr("value", key).text(key));
  });

  displayCategoryOptions();
}

function displayCategoryOptions() {
  var $elC = $("#inputCategory");

  $elC.empty();
  $elC.append(
    `<option value="none" selected disabled hidden>Select Category</option>`
  );
  var a = getBrandOption();
  var len = brandData[a].length;
  for (var i = 0; i < len; i++) {
    $elC.append(
      $("<option></option>")
        .attr("value", brandData[a][i])
        .text(brandData[a][i])
    );
  }
}


function activateUpload() {
  $("#process-data").prop("disabled", false);
  $("#download-errors").prop('disabled', true);
}

//INITIALIZATION CODE
function init() {
  $("#add-product").click(addProduct);
  $("#update-product").click(updateProduct);
  $("#refresh-data").click(getProductList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#productFile").on("change", updateFileName);
  $("#inputBrand").change(displayCategoryOptions);
  $("#productFile").click(activateUpload);
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(getBrandList);
