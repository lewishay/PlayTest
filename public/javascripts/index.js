function yourName() {
  if (document.getElementById('theName').value == "") {
    $("#nameForm").attr("action", "/hello");
  }
  else {
    var path = "/hello/" + document.getElementById('theName').value;
    $("#nameForm").attr("action", path);
  }
}

function yourNumber() {
  var path = "/numbers/" + document.getElementById('theNumber').value;
  $("#numberForm").attr("action", path);
}

function numMonths() {
  if (document.getElementById('theMonths').value == "") {
    $("#monthsForm").attr("action", "/months");
  }
  else {
    window.open("http://localhost:9000/months?numMonths=" + document.getElementById("theMonths").value);
  }
}