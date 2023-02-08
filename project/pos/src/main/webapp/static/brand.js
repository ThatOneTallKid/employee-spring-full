var wholeBrand = []
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

function getBrandReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    console.log(baseUrl);
    return baseUrl + "/api/brand/exportcsv";
}

function resetForm() {
    var element = document.getElementById("brand-form");
    element.reset()
}

function arrayToJson() {
    let json = [];
    for(i in wholeBrand) {
        let data = {};
        data["brand"]=JSON.parse(wholeBrand[i]).brand;
        data["category"]=JSON.parse(wholeBrand[i]).category;
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
function addBrand(event){
	//Set the values to update
	var $form = $("#brand-form");
	var json = toJson($form);
	wholeBrand.push(json)
	var url = getBrandUrl();
		var jsonObj = arrayToJson();
    console.log(url);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: jsonObj,
	   headers: {
       	'Content-Type': 'application/json'
       },
		success: function (response) {
		    wholeBrand=[];
			resetForm();
	   		getBrandList();
			toastr.success("Brand Added Successfully", "Success : ");
	   },
	   error: function (response) {
	       console.log(response);
	       if(response.status == 403) {
	            toastr.error("Error: 403 unauthorized");
	       }
	       else {
	        var resp = JSON.parse(response.responseText);
	        if(isJson(resp.message) == true){
	            var jsonObj = JSON.parse(resp.message);
      		    console.log(jsonObj);
                toastr.error(jsonObj[0].message, "Error : ");
	        }
	        else {
	        handleAjaxError(response);
	        }
	       }
           wholeBrand=[];

	   }
	});

	return false;
}

function updateBrand(event){
	//Get the ID

	var id = $("#brand-edit-form input[name=id]").val();
	var url = getBrandUrl() + "/" + id;

	//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
		success: function (response) {
	$('#edit-brand-modal').modal('toggle');
			toastr.success("Brand Updated Successfully", "Success : ");
	   		getBrandList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);
	   },
	   error: handleAjaxError
	});
}


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandFile')[0].files[0];
	console.log(file);
	if(file.name.split('.').pop() != "tsv"){
	    toastr.error("file format is not tsv, Not Allowed");
	}
	else {
	readFileData(file, readFileDataCallback);
	}
}

function readFileDataCallback(results){
	fileData = results.data;
	var filelen = fileData.length;
	if(filelen == 0) {
	    toastr.error("file is empty, Not Allowed");
	}
	else if(filelen > 5000) {
	    toastr.error("file length exceeds 5000, Not Allowed");
	}
	else {

	    uploadRows();
	}
}

function uploadRows(){
	//Update progress
	updateUploadDialog();

    $("#process-data").prop('disabled', true);

	if(processCount==fileData.length){
		return;
	}


	var json = JSON.stringify(fileData);
	var headers = ["brand", "category"];
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
	console.log(json);
	var url = getBrandUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		console.log(response);
            errorData = response;
            resetForm();
            getBrandList();
            toastr.success("Brand.tsv uploaded Successfully");
	   },
		error: function (response) {
		    if(response.status == 403){
                toastr.error("403 FOrbidden");
            }
            else {
			var resp = JSON.parse(response.responseText);
			var jsonObj = JSON.parse(resp.message);
			console.log(jsonObj);
	        errorData = jsonObj;
			processCount = fileData.length;
			console.log(response);
			toastr.error("Error in uploading Brand.tsv, Download Error File");
			$("#download-errors").prop('disabled', false);
			resetForm();
			}
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayBrandList(data){
	var $tbody = $('#Brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button onclick="displayEditBrand(' + e.id + ')" class="btn"><i class="fa-solid fa-pen"></i></button>'
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditBrand(id){
	var url = getBrandUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		   displayBrand(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
    console.log("hello");
 	resetUploadDialog();
	$('#upload-brand-modal').modal('toggle');
    $("#download-errors").prop('disabled', true);
    $("#process-data").prop('disabled', true);

}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);
	$("#brand-edit-form input[name=category]").val(data.category);
	$("#brand-edit-form input[name=id]").val(data.id);
	$('#edit-brand-modal').modal('toggle');
}
function printReport() {
    window.location.href = getBrandReportUrl();
}

function activateUpload() {
    $("#process-data").prop('disabled', false);
    $("#download-errors").prop('disabled', true);
}


//INITIALIZATION CODE
function init(){
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName)
    $('#print-report').click(printReport);
    $('#brandFile').click(activateUpload);
}

$(document).ready(init);
$(document).ready(getBrandList);

