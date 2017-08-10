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

function colourChange() {
  if(document.getElementById("theFoot").style.background == "red") {
    document.getElementById("theHead").style.background = "#2d7df0";
    document.getElementById("theMain").style.background = "white";
    document.getElementById("theFoot").style.background = "#2d7df0";
    var x = document.getElementsByTagName("h3");
    for (var i = 0; i < x.length; i++) {
      x[i].style.color = "#2d7df0";
    }
    x = document.getElementsByTagName("p");
    for (var i = 0; i < x.length; i++) {
      x[i].style.color = "#198d35";
    }
    x = document.getElementsByClassName("capsule");
    for (var i = 0; i < x.length; i++) {
      x[i].style.background = "white";
    }
    x = document.getElementsByTagName("button");
    for (var i = 0; i < x.length; i++) {
      x[i].style.background = "#e7e7e7";
      x[i].style.color = "black";
    }
  }
  else {
    document.getElementById("theHead").style.background = "red";
    document.getElementById("theMain").style.background = "black";
    document.getElementById("theFoot").style.background = "red";
    document.getElementsByTagName("h3").color = "black";
    var x = document.getElementsByTagName("h3");
    for (var i = 0; i < x.length; i++) {
      x[i].style.color = "white";
    }
    x = document.getElementsByTagName("p");
    for (var i = 0; i < x.length; i++) {
      x[i].style.color = "red";
    }
    x = document.getElementsByClassName("capsule");
    for (var i = 0; i < x.length; i++) {
      x[i].style.background = "black";
    }
    x = document.getElementsByTagName("button");
    for (var i = 0; i < x.length; i++) {
      x[i].style.background = "red";
      x[i].style.color = "white";
    }
  }
}