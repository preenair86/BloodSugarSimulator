package com.pree.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
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
		SugarLevelSimulator simulator = new SugarLevelSimulator();
		
		Map<String, SugarLevelFactor> nameToFactor = new HashMap<String, SugarLevelFactor>();
		SugarLevelFactor factor1 = new SugarLevelFactor();
		factor1.setName("Food");
		factor1.setRate(120);
		factor1.setDuration(60.0f);
		factor1.setDoesIncrease(true);
		nameToFactor.put(factor1.getName(), factor1);
		simulator.setNameToFactor(nameToFactor);
		return gson.toJson(simulator.simulateGlucoseLevels(simulatorInput));
	}
}
