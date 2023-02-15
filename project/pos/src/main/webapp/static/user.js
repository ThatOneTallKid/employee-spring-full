function getUserUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/admin/user";
}

function resetForm() {
    var element = document.getElementById("user-form");
    element.reset()
}
//BUTTON ACTIONS
function addUser(event) {
  //Set the values to update
  var $form = $("#user-form");
  var json = toJson($form);
  var url = getUserUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getUserList();
        resetForm();
    },
    error: handleAjaxError,
  });

  return false;
}
var deleteid = 0;
function getUserList() {
  var url = getUserUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayUserList(data);
    },
    error: handleAjaxError,
  });
}

function deleteUser() {
  var url = getUserUrl() + "/" + deleteid;
    console.log(url);
  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getUserList();
      $('#delete-user-modal').modal('toggle');
    },
    error: handleAjaxError,
  });
}

function deleteUserModal(id) {
$('#delete-user-modal').modal('toggle');
deleteid = id;
}



//UI DISPLAY METHODS

function displayUserList(data){
var texts = "<b>Total rows : "+ data.length +"</b>";
   $('#total-rows').empty();
    $('#total-rows').append(texts);
	console.log('Printing user data');
	var $tbody = $('#user-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteUserModal(' + e.id + ')" class="btn"><i class="fa-solid fa-trash" data-toggle="tooltip" data-placement="top" title="Delete User"></i></button>'
		var buttonDisabledHtml = '<button onclick="deleteUserModal(' + e.id + ')" class="btn" disabled><i class="fa-solid fa-trash"></i></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.email + '</td>'
		+ '<td>' + e.role + '</td>';
		if(e.role == 'supervisor'){
		    continue;
		}
		else{
		    row += '<td>' + buttonHtml + '</td>';
		}
		row+= '</tr>';
        $tbody.append(row);
	}
}

//INITIALIZATION CODE
function init() {
  $("#add-user").click(addUser);
  $("#refresh-data").click(getUserList);
    $("#delete-user").click(deleteUser);
}

$(document).ready(init);
$(document).ready(getUserList);
