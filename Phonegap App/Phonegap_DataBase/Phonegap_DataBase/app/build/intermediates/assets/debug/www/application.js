var application = {
  // Application Constructor
  initialize: function() {
    this.bindEvents();
  },
  // Bind Event Listeners
  //
  // Bind any events that are required on startup. Common events are:
  // 'load', 'deviceready', 'offline', and 'online'.
  bindEvents: function() {
    document.addEventListener('DOMContentLoaded', this.onDeviceReady);
  },
  // deviceready Event Handler
  //
  // The scope of 'this' is the event. In order to call the 'receivedEvent'
  // function, we must explicity call 'app.receivedEvent(...);'
  onDeviceReady: function() {
    onBodyLoad();
  },
};
// global variables
var db;
var shortName = 'mydb';
var version = '1.0';
var displayName = 'Test DB';
var maxSize = 2 * 1024 * 1024;
//sends user to new page
function callAddProperty(){
window.location = "AddProperty.html";
}
//sends user to new page
function goHome() {
	window.location = "homePage.html";
}
// this is called when an error happens in a transaction
function errorHandler(transaction, error) {
  alert('Error: ' + error.message + ' code: ' + error.code);
}

// this is called when a successful transaction happens
function successCallBack() {
  alert("Information has been submitted");
}
//alerts the user what has happened
function deletesuccess(){
	alert("Information has been deleted");
}
//alerts the user what has happened
function success(){
	 alert("Debug success!");
}
function nullHandler() {}
//sends user to new page
function callSearch(){
	window.location = "searchProperty.html";
}
//sends user to new page
function callList(){
	window.location = "ListProperty.html";
}



function onBodyLoad() {


  if (!window.openDatabase) {
    // not all mobile devices support databases  if it does not, the following alert will display
    // indicating the device will not be albe to run this application
    alert('Databases are not supported in this browser.');
    return;
  }

  // this line tries to open the database base locally on the device
  // if it does not exist, it will create it and return a database object stored in variable db
  //open the database when the function has been called
var db = openDatabase(shortName, version, displayName, maxSize);

  // this line will try to create the table User in the database just created/openned
  db.transaction(function(tx){


  // this line actually creates the table User if it does not exist and sets up the three columns and their types
  // note the UserId column is an auto incrementing column which is useful if you want to pull back distinct rows
  // easily from the table.
 //creates the database property
tx.executeSql('CREATE TABLE IF NOT EXISTS property (UserId INTEGER NOT NULL PRIMARY KEY, forename, surname, type, bedroom, furniture, date, time, rent, notes)',
    [], nullHandler, errorHandler);
    }, errorHandler, success);
}

//updates the notes of selected rowid
function addNotes () {
	 var db = openDatabase(shortName, version, displayName, maxSize);
	 
	 db.transaction(function (transaction) {
 var chooseProperty = document.getElementById("chooseProperty").value;
 var propertyNotes = document.getElementById("propertyNotes").value;

transaction.executeSql('UPDATE property SET notes = ? WHERE rowid = ?', [ propertyNotes, chooseProperty], successCallBack, errorHandler);
});
	
}

//funcation to help search property through what the user entered 
function search(){
	 var db = openDatabase(shortName, version, displayName, maxSize);
         var msg;
		 
		$('#output').html('')
		 
		   db.transaction(function (transaction) {
			   var searchProperty = document.getElementById("searchProperty").value;
            transaction.executeSql('SELECT * FROM property WHERE  UserId = (?) OR forename = (?) OR surname = (?) OR type = (?) OR bedroom = (?) OR furniture = (?) OR date = (?) OR time = (?) OR rent = (?) OR notes = (?)', [searchProperty, searchProperty, searchProperty, searchProperty, searchProperty, searchProperty, searchProperty, searchProperty, searchProperty, searchProperty ], function (transaction, results) {
               var len = results.rows.length, i;
               msg = "<p>Found rows: " + len + "</p>";
               document.querySelector('#output').innerHTML +=  msg;
					
               for (var i = 0; i < results.rows.length; i++){
               var row = "<p>" + "<b> User id number:  </b>"  + results.rows.item(i).UserId +  "    <b>Forename:</b> " + results.rows.item(i).forename + " <b>Surname:</b>  " + results.rows.item(i).surname + "  <b>Property type:</b> " + results.rows.item(i).type + " <b>How many bedrooms:</b>  " + results.rows.item(i).bedroom + "  <b>Furniture type:</b> " + results.rows.item(i).furniture + "  <b>Date of property:</b>  " + results.rows.item(i).date + "  <b>Time of property:</b>  " + results.rows.item(i).time+ "  <b>Rent price:</b> " + results.rows.item(i).rent + "  <b>Property notes:</b> " + results.rows.item(i).notes;
				 
         
        document.querySelector('#output').innerHTML +=  row;
		}
            }, null);
         }, errorHandler, nullHandler);
  return;
		 
}

/** Initialize Database  **/
function inDB(){ 
   var db = openDatabase(shortName, version, displayName, maxSize);
         var msg;
		 
		$('#lbUsers').html('')
		 
		   db.transaction(function (transaction) {
            transaction.executeSql('SELECT * FROM property', [], function (transaction, results) {
               var len = results.rows.length, i;
               msg = "<p>Found rows: " + len + "</p>";
               document.querySelector('#lbUsers').innerHTML +=  msg;
					
               for (var i = 0; i < results.rows.length; i++){
               var row = "<p>" + "<b> User id number:  </b>"  + results.rows.item(i).UserId +  "    <b>Forename:</b> " + results.rows.item(i).forename + " <b>Surname:</b>  " + results.rows.item(i).surname + "  <b>Property type:</b> " + results.rows.item(i).type + " <b>How many bedrooms:</b>  " + results.rows.item(i).bedroom + "  <b>Furniture type:</b> " + results.rows.item(i).furniture + "  <b>Date of property:</b>  " + results.rows.item(i).date + "  <b>Time of property:</b>  " + results.rows.item(i).time+ "  <b>Rent price:</b> " + results.rows.item(i).rent + "  <b>Property notes:</b> " + results.rows.item(i).notes;
				 
         
        document.querySelector('#lbUsers').innerHTML +=  row;
		}
            }, null);
         }, errorHandler, nullHandler);
  return;
		 
}  
//deletes the row in which the user specified 
function deleteRow()
{
	//open the database when the function has been called
var db = openDatabase (shortName, version, displayName, maxSize);
db.transaction(function (transaction) {
 var number1 = document.getElementById("number1").value;

transaction.executeSql('DELETE FROM property WHERE rowid = ?', [number1], deletesuccess, errorHandler);

});}


//lists all information in the database
function ListDBValues() {
  if (!window.openDatabase) {
    alert('Databases are not supported in this browser.');
    return;
  }


  // this line clears out any content in the #lbUsers element on the page so that the next few lines will show updated
  // content and not just keep repeating lines
  $('#lbUsers').html('');

  // this next section will select all the content from the property table and then go through it row by row
  // appending the UserId  FirstName  LastName to the  #lbUsers element on the page
  db.transaction(function(transaction) {
    transaction.executeSql('SELECT * FROM property;', [],
    function(transaction, result) {
      if (result !== null && result.rows !== null) {
        for (var i = 0; i < result.rows.length; i++) {
          var row = result.rows.item(i);
		            ('#lbUsers').append('<br>' + row.forename + '. ' + row.surname+ ' ' + row.type);
         // $('#lbUsers').append('<br>' + row.forename + '. ' + row.surname + ' ' + row.type + ' ' + row.bedroom + ' ' + row.furniture + ' ' + row.date + ' ' + row.time + ' ' + row.rent + ' ' + row.notes);
        }
      }
    }, errorHandler);
  }, errorHandler, nullHandler);
  return;
}

// Show a custom confirmation dialog
function showConfirm() {
 var forename = document.getElementById("forename").value;
 var surname = document.getElementById("surname").value;
var type = document.getElementById("type").value;
var bedroom = document.getElementById("bedroom").value;
var furniture = document.getElementById("furniture").value;
var date = document.getElementById("date").value;
var time = document.getElementById("time").value;
var rent = document.getElementById("rent").value;
var notes = document.getElementById("notes").value;
 var r = confirm("\nForename: " + forename +  " \nSurname: " + surname+ "\nProperty type: " + type + "\nHow many bedrooms: " + bedroom + "\nFurniture type: " + furniture + "\n date of property: " + date +  "\nTime of property: " +  time + "\nProperty rent: £" + rent + "\nNotes on property: " + notes + "\nAre you sure you want to submit?\nEither press okay or Cancel ");
    if (r == true) {
        myFunction();
    } else {
       window.alert("You clicked back to change your property entry");
    }
}

//inserts all information entered by the user
function myFunction(){

//open the database when the function has been called
var db = openDatabase (shortName, version, displayName, maxSize);

db.transaction(function (transaction) {
//adds a value to elements being called.
var forename = document.getElementById("forename").value;
var surname = document.getElementById("surname").value;
var type = document.getElementById("type").value;
var bedroom = document.getElementById("bedroom").value;
var furniture = document.getElementById("furniture").value;
var date = document.getElementById("date").value;
var time = document.getElementById("time").value;
var rent = document.getElementById("rent").value;
var notes = document.getElementById("notes").value;

//creates the database property
transaction.executeSql('CREATE TABLE IF NOT EXISTS property (forename, surname, type, bedroom, furniture, date, time, rent, notes)');
//insert statement allowing information into the database from information inserted from the form
transaction.executeSql('INSERT OR REPLACE INTO property (forename, surname, type, bedroom, furniture, date, time, rent, notes ) VALUES (?, ?,?,?,?,?,?,?,?)', [forename, surname, type, bedroom, furniture, date, time, rent, notes], nullHandler, errorHandler);


});
alert("Your information has been submitted");
 
}







