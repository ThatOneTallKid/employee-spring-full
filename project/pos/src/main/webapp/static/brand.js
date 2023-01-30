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
			toastr.success("Brand Added Successfully", "Success : ");
	   		getBrandList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateBrand(event){
	$('#edit-brand-modal').modal('toggle');
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
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	var filelen = fileData.length;
	if(filelen > 5000) {
	    alert("file length exceeds 5000, Not Allowed");
	}
	else {

	    uploadRows();
	}
}

function uploadRows(){
	//Update progress
	updateUploadDialog();

    $("#process-data").prop('disabled', true);
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row

	var json = JSON.stringify(fileData);
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
            processCount = fileData.length;
            console.log(response.length);
            if(response.length > 0) {
                $("#download-errors").prop('disabled', false);
            }
            resetForm();
            getBrandList();
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

