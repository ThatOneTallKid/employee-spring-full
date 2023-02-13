    //HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}



function handleAjaxError(response){
    if(response.status == 403){
        toastr.error("403 FOrbidden");
        return;
    }
	var response = JSON.parse(response.responseText);
	//alert(response.message);
    toastr.error(response.message, "Error : ");
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}

function hideSupervisorView(){
    var appBanners = document.getElementsByClassName('supervisor');

    for (var i = 0; i < appBanners.length; i ++) {
        appBanners[i].style.display = 'none';
    }
}

function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}

function init(){

    if($("meta[name=role]").attr("content") == "operator") {

        hideSupervisorView();
    }
}

function validateForm(form){
    return form[0].reportValidity();
}

$(document).ready(init);
