package com.pree.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pree.dao.SugarLevelDao;
import com.pree.healthmodels.SugarLevelFactor;
import com.pree.simulator.SugarLevelSimulator;
import com.pree.simulator.SugarLevelSimulatorInputs;
import com.pree.simulator.SugarLevelSimulatorOutputs;

@Path("")
public class SugarLevelSimulatorService {
	@GET
	@Path("help")
	@Produces("text/html")
	public String displayHelp() {
		return "<h1>Blood Sugar Simulator Service</h1>"
				+ "<p>Hello, welcome to blood sugar simulator service.</p>"
				+ "Supported operations are "
				+ "<ul><li>/simulator/help - displays the supported api.</li></ul>";
	}

	@POST
	@Path("plotGraph")
	@Produces("application/json")
	@Consumes("application/json")
	public String plotGraph(String input) {
		Gson gson = new Gson();
		SugarLevelSimulatorInputs simulatorInput = gson.fromJson(input,SugarLevelSimulatorInputs.class);
		System.out.println("Input is " + simulatorInput.toString());
		SugarLevelDao dao = new SugarLevelDao();
		Map<String, SugarLevelFactor> nameToFactor = dao.getNameToFactor();
		SugarLevelSimulator.setNameToFactor(nameToFactor);
		return gson.toJson(SugarLevelSimulator.simulateGlucoseLevels(simulatorInput));
	}
	
	@GET
	@Path("getFoodList")
	@Produces("application/json")
	public String getFoodList() {
		SugarLevelDao dao = new SugarLevelDao();
		List<String> foodList = dao.getFoodList();
		System.out.println("Returning " + ListToJSONArray(foodList));
		return ListToJSONArray(foodList).toString();
	}
	
	@GET
	@Path("getExerciseList")
	@Produces("application/json")
	public String getExerciseList() {
		SugarLevelDao dao = new SugarLevelDao();
		List<String> exerciseList = dao.getExerciseList();
		System.out.println("Returning " + ListToJSONArray(exerciseList));
		return ListToJSONArray(exerciseList).toString();
	}
	
	@GET
	@Path("getTimeList")
	@Produces("application/json")
	public String getTimeList() {
		List<String> timeList = new ArrayList<String>();
		SugarLevelDao dao = new SugarLevelDao();
		LocalTime startTime = dao.getStartTime();
		LocalTime endTime = dao.getEndTime();
		float timeStep = dao.getTimeStep();
		while(startTime.isBefore(endTime)) {
			timeList.add(startTime.toString("HH:mm"));
			System.out.println(startTime.toString());
			startTime = startTime.plusMinutes((int)timeStep);
		}
		System.out.println("Returning " + ListToJSONArray(timeList));
		return ListToJSONArray(timeList).toString();
	}
	
	private JsonArray ListToJSONArray(List<String> data) {
		JsonArray jsonArray = new JsonArray();
	    for(int i = 0; i < data.size(); ++i) {
	        JsonObject obj = new JsonObject();
	        obj.addProperty("id", i);
	        obj.addProperty("value", data.get(i));
	       jsonArray.add(obj);
	    }
	    return jsonArray;
	}
}
