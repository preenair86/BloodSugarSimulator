var simulatorInput = {data:[]};

var getContextPath = function() {
	return document.URL;
}

var getNewAbsolutePath = function(absolutePath, previousSuffix, newSuffix) {
	return absolutePath.replace(previousSuffix, newSuffix);
}

var getSimulatorInputText = function() {
  var output = "";
  for(var i = 0; i < simulatorInput.data.length; i++) {
	  output  = output + "Type:" + simulatorInput.data[i].name + ". Time:" + simulatorInput.data[i].time + "\n";
  }
  return output;
}

var addFoodConsumption = function() {
	var foodSelector = document.getElementById("food_selector");
	var timeSelector = document.getElementById("food_time_selector");
	var foodName = foodSelector.options[foodSelector.selectedIndex].text;
	var foodTime = timeSelector.options[timeSelector.selectedIndex].text;
	simulatorInput.data.push({name:foodName, isFood:true,time:foodTime});
	document.getElementById("current_entries").value = getSimulatorInputText();
}

var addExerciseActivity = function() {
	var exerciseSelector = document.getElementById("exercise_selector");
	var timeSelector = document.getElementById("exercise_time_selector");
	var exerciseName = exerciseSelector.options[exerciseSelector.selectedIndex].text;
	var exerciseTime = timeSelector.options[timeSelector.selectedIndex].text;
	simulatorInput.data.push({name:exerciseName, isFood:false,time:exerciseTime});
	document.getElementById("current_entries").value = getSimulatorInputText();
}

var loadFoodList = function(foodList) {
	var foodSelector = document.getElementById("food_selector");
	for(var i = 0; i < foodList.length; i++) {
		var option = document.createElement("option");
		option.text = foodList[i].value;
		foodSelector.add(option);
	}
}

var loadExerciseList = function(exerciseList) {
	var exerciseSelector = document.getElementById("exercise_selector");
	for(var i = 0; i < exerciseList.length; i++) {
		var option = document.createElement("option");
		option.text = exerciseList[i].value;
		exerciseSelector.add(option);
	}
}

var loadTimeList = function(timeList) {
	var foodTimeSelector = document.getElementById("food_time_selector");
	var exerciseTimeSelector = document.getElementById("exercise_time_selector");
	for(var i = 0; i < timeList.length; i++) {
		var foodOption = document.createElement("option");
		foodOption.text = timeList[i].value;
		foodTimeSelector.add(foodOption);
		var exerciseOption = document.createElement("option");
		exerciseOption.text = timeList[i].value;
		exerciseTimeSelector.add(exerciseOption);
	}
}

var getInitialData = function() {
	var method = "GET";
	var currentPath = getContextPath();
	var url = getNewAbsolutePath(currentPath, "simulator.jsp", "simulator/getFoodList");
	//var url = "http://localhost:8080/BloodSugarSimulator/simulator/getFoodList";
	ajaxCall(url, method, null, loadFoodList);
	url = getNewAbsolutePath(currentPath, "simulator.jsp", "simulator/getExerciseList");
	//url = "http://localhost:8080/BloodSugarSimulator/simulator/getExerciseList";
	ajaxCall(url, method, null, loadExerciseList);
	url = getNewAbsolutePath(currentPath, "simulator.jsp", "simulator/getTimeList");
	//url = "http://localhost:8080/BloodSugarSimulator/simulator/getTimeList";
	ajaxCall(url, method, null, loadTimeList);
}

var onLoad = function() {
	getInitialData();
}

var plotGraph = function(object) {
	var data = new google.visualization.DataTable();
	// Add columns
	data.addColumn('string', 'Time');
	data.addColumn('number', 'Glucose Level');
	data.addRows(object.data.length);
	for (i = 0; i < object.data.length; i++) {
		data.setCell(i, 0, object.data[i].time);
		data.setCell(i, 1, object.data[i].glucoselevel);
	}
	var options = {
		title : 'Variation in glucose levels',
		curveType : 'function',
		legend : {
			position : 'bottom'
		}
	};
	var chart = new google.visualization.LineChart(document
			.getElementById('glucose_plot'));

	chart.draw(data, options);
}

var simulate = function() {
	var currentPath = getContextPath();
	var url = getNewAbsolutePath(currentPath, "simulator.jsp", "simulator/plotGraph");
	var method = "POST";
	var jsonString = JSON.stringify(simulatorInput);
	ajaxCall(url, method, jsonString, plotGraph);
}
var ajaxCall = function(urlToSend, method, jsonString, outputFunction) {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var object = JSON.parse(xmlhttp.responseText);
			outputFunction(object);
		}
	}
	xmlhttp.open(method, urlToSend, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	if(method == "GET") {
		xmlhttp.send();
	} else {
		xmlhttp.send(jsonString);	
	}
	
	/*var callbackMethod = function(data) {
		console.log("Response " + JSON.stringify(data));
		outputFunction(data);
	}
	$.ajax({
         url: urlToSend,
         type: method,
	     data: jsonString,
	     contentType: "application/json",
	     complete: callbackMethod});*/
	
	/*jsonString = '{"data":[{"name":"A","isFood":true,"time":"Thu Jan 01 07:00:00 PST 1970"}]}';
	jsonString = '{"data":[{"name":"Food","isFood":true,"time":"09:00:00"}] }';
	if (method == "GET") {
		$.get(urlToSend, function(data, status){
	        alert("Data: " + data + "\nStatus: " + status);
	        outputFunction(data);
	    });
	} else {
		$.post(urlToSend, jsonString, function(data, status) {
			console.log("Response " + JSON.stringify(data));
			outputFunction(data);
		});
	}*/
}