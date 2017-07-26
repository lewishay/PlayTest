function deleteDrink() {
  var path = "/editDrinks/" + document.getElementById("drinksList2").value;
  console.log(path);
  $("#deleteForm").attr("action", path);
}