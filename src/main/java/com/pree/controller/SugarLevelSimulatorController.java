// Controller which interacts with the user interface.
package com.pree.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pree.dao.SugarLevelDao;
import com.pree.healthmodels.SugarLevelFactor;
import com.pree.service.SugarLevelSimulatorService;

@Path("")
public class SugarLevelSimulatorController {	
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
		SugarLevelSimulatorService.setNameToFactor(nameToFactor);
		return gson.toJson(SugarLevelSimulatorService.simulateGlucoseLevels(simulatorInput));
	}
	
	@GET
	@Path("getFoodList")
	@Produces("application/json")
	public String getFoodList() {
		return ListToJSONArray(SugarLevelSimulatorService.getFoodList()).toString();
	}
	
	@GET
	@Path("getExerciseList")
	@Produces("application/json")
	public String getExerciseList() {
		return ListToJSONArray(SugarLevelSimulatorService.getExerciseList()).toString();
	}
	
	@GET
	@Path("getTimeList")
	@Produces("application/json")
	public String getTimeList() {
		return ListToJSONArray(SugarLevelSimulatorService.getTimeList()).toString();
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
