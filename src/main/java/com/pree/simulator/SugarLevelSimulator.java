package com.pree.simulator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import com.pree.healthmodels.SugarLevelComputer;
import com.pree.healthmodels.SugarLevelEvent;
import com.pree.healthmodels.SugarLevelFactor;

public class SugarLevelSimulator {
	private static Date startTime, endTime;
	private static float timeStep;
	private static Map<String, SugarLevelFactor> nameToFactor;

	public static Map<String, SugarLevelFactor> getNameToFactor() {
		return nameToFactor;
	}

	public static void setNameToFactor(
			Map<String, SugarLevelFactor> factorToProperties) {
		SugarLevelSimulator.nameToFactor = factorToProperties;
	}

	public SugarLevelSimulator() {
		try {
			startTime = new SimpleDateFormat("hh:mm:ss").parse("07:00:00");
			endTime = new SimpleDateFormat("hh:mm:ss").parse("11:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		timeStep = 10.0f;
	}

	public static SugarLevelSimulatorOutputs simulateGlucoseLevels(
			SugarLevelSimulatorInputs input) {
		List<SugarLevelEvent> events = toSugarLevelEvents(input);
		List<Pair<Date, Float>> glucoseLevels = SugarLevelComputer
				.getGlucoseLevels(startTime, endTime, timeStep, events);
		return toSugarLevelSimulatorOutput(glucoseLevels);
	}

	private static List<SugarLevelEvent> toSugarLevelEvents(
			SugarLevelSimulatorInputs input) {
		List<SugarLevelEvent> output = new ArrayList<SugarLevelEvent>();
		for (SugarLevelInputPoint point : input.getData()) {
			SugarLevelFactor currentFactor = nameToFactor.get(point.getName()); 
			Date currentTime = null;
			try {
				currentTime = new SimpleDateFormat("hh:mm:ss").parse(point
						.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SugarLevelEvent currentEvent = new SugarLevelEvent(currentFactor,
					currentTime);
			output.add(currentEvent);
		}
		return output;
	}
	
	private static SugarLevelSimulatorOutputs toSugarLevelSimulatorOutput(
			List<Pair<Date, Float>> input) {
		List<SugarLevelOutputPoint> points = new ArrayList<SugarLevelOutputPoint>();
		for(Pair<Date, Float> glucoseLevel : input) {
			SugarLevelOutputPoint currentPoint = new SugarLevelOutputPoint();
			currentPoint.setGlucoselevel(glucoseLevel.getValue1());
			currentPoint.setTime(glucoseLevel.getValue0().toString());
			points.add(currentPoint);
		}
		SugarLevelSimulatorOutputs output = new SugarLevelSimulatorOutputs();
		output.setData(points);
		return output;
	}
}
