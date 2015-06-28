package com.pree.simulator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.pree.dao.SugarLevelDao;
import com.pree.healthmodels.SugarLevelComputer;
import com.pree.healthmodels.SugarLevelEvent;
import com.pree.healthmodels.SugarLevelFactor;

public class SugarLevelSimulator {
	private static Map<String, SugarLevelFactor> nameToFactor;

	public static Map<String, SugarLevelFactor> getNameToFactor() {
		return nameToFactor;
	}

	public static void setNameToFactor(
			Map<String, SugarLevelFactor> factorToProperties) {
		SugarLevelSimulator.nameToFactor = factorToProperties;
	}

	public static SugarLevelSimulatorOutputs simulateGlucoseLevels(
			SugarLevelSimulatorInputs input) {
		System.out.println("Simulator input is " + input.toString());
		List<SugarLevelEvent> events = toSugarLevelEvents(input);
		SugarLevelDao dao = new SugarLevelDao();
		List<Pair<LocalTime, Float>> glucoseLevels = SugarLevelComputer
				.getGlucoseLevels(dao.getStartTime(), dao.getEndTime(), dao.getTimeStep(), events);
		return toSugarLevelSimulatorOutput(glucoseLevels);
	}

	private static List<SugarLevelEvent> toSugarLevelEvents(
			SugarLevelSimulatorInputs input) {
		List<SugarLevelEvent> output = new ArrayList<SugarLevelEvent>();
		for (SugarLevelInputPoint point : input.getData()) {
			SugarLevelFactor currentFactor = nameToFactor.get(point.getName()); 
			LocalTime currentTime = new LocalTime();
			DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
			currentTime = LocalTime.parse(point.getTime(), formatter);
			SugarLevelEvent currentEvent = new SugarLevelEvent(currentFactor, currentTime);
			output.add(currentEvent);
		}
		return output;
	}
	
	private static SugarLevelSimulatorOutputs toSugarLevelSimulatorOutput(
			List<Pair<LocalTime, Float>> input) {
		List<SugarLevelOutputPoint> points = new ArrayList<SugarLevelOutputPoint>();
		for(Pair<LocalTime, Float> glucoseLevel : input) {
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
