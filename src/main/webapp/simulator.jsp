<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Blood glucose level Simulator</title>

<script type="text/javascript"
	src="https://www.google.com/jsapi?autoload={
            'modules':[{
              'name':'visualization',
              'version':'1',
              'packages':['corechart']
            }]
          }">
	
</script>
<script type="text/javascript" src="simulator.js"></script>
</head>

<body onload="onLoad()">
	<div class="container">
		<div class="row">
			<div class="container">
				<h2 class="text-center">Blood glucose level simulator</h2>
				<div class="col-lg-3 col-md-3 col-sm-3">
					<div class="panel panel-default">
						<div class="panel-heading">Food consumption</div>
						<div class="panel-body">
							<div class="form-group">
								<label for="sel1">Select list:</label> 
								<select class="form-control" id="food_selector"></select>
								<label for="sel1">Time:</label>
								<select class="form-control" id="food_time_selector"></select>
								<button type="button" class="btn btn-primary"
										onclick="addFoodConsumption()">Add Food</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-md-3 col-sm-3">
					<div class="panel panel-default">
						<div class="panel-heading">Exercise Activity</div>
						<div class="panel-body">
							<div class="form-group">
								<label for="sel1">Select list:</label> <select
									class="form-control" id="exercise_selector">
								</select> <label for="sel1">Time:</label> <select class="form-control"
									id="exercise_time_selector">
								</select>
								<button type="button" class="btn btn-primary"
										onclick="addExerciseActivity()">Add Exercise</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="panel panel-default">
						<div class="panel-heading">Current Entries</div>
						<div class="panel-body">
							<textarea class="form-control" rows="10" id="current_entries"></textarea>
						</div>
						<div class="centered">
							<button type="button" class="btn btn-primary" onclick="simulate()">Plot</button>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="panel panel-default">
					<div class="panel-heading">Glucose Level Simulation</div>
					<div class="panel-body">
						<div id="glucose_plot"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>