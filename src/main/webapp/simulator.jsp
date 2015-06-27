<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Blood glucose level Simulator</title>
<script>
	var simulate = function() {
		var url = "http://localhost:8080/BloodSugarSimulator/simulator/plotGraph";
		var method = "POST";
		var data = '{"data":[{"name":"Food","isFood":true,"time":"09:00:00"}] }';
		var jsonData = JSON.parse(data);
		ajaxCall(url, method, jsonData);
	}
	var ajaxCall = function(url, method,jsonData) {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				alert(xmlhttp.responseText);
			}
		}
		xmlhttp.open(method, url, true);
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.send(JSON.stringify(jsonData));
	}
</script>
</head>
<body>
	<h1>Blood glucose level simulator</h1>
	Enter activity and time separated by comma
	<input type=text name="activity" id="activity">
	<input type="button" value="Add">
	<br>
	<input type="button" value="Simulate" onclick="simulate()">

</body>
</html>