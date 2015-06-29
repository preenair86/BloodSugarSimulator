package com.pree.service;

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

import com.pree.controller.SugarLevelInputPoint;
import com.pree.controller.SugarLevelOutputPoint;
import com.pree.controller.SugarLevelSimulatorInputs;
import com.pree.controller.SugarLevelSimulatorOutputs;
import com.pree.dao.SugarLevelDao;
import com.pree.healthmodels.SugarLevelComputer;
import com.pree.healthmodels.SugarLevelEvent;
import com.pree.healthmodels.SugarLevelFactor;

public class SugarLevelSimulatorService {
	private static Map<String, SugarLevelFactor> nameToFactor;

	public static Map<String, SugarLevelFactor> getNameToFactor() {
		return nameToFactor;
	}
	
	public static List<String> getFoodList() {
		SugarLevelDao dao = new SugarLevelDao();
		List<String> foodList = dao.getFoodList();
		return foodList;
	}
	
	public static List<String> getExerciseList() {
		SugarLevelDao dao = new SugarLevelDao();
		List<String> exerciseList = dao.getExerciseList();
		return exerciseList;
	}
	
	public static List<String> getTimeList() {
		List<String> timeList = new ArrayList<String>();
		SugarLevelDao dao = new SugarLevelDao();
		LocalTime startTime = dao.getStartTime();
		LocalTime endTime = dao.getEndTime();
		float timeStep = dao.getListTimeStep();
		while(startTime.isBefore(endTime)) {
			timeList.add(startTime.toString("HH:mm"));
			startTime = startTime.plusMinutes((int)timeStep);
		}
		return timeList;
	}

	public static void setNameToFactor(
			Map<String, SugarLevelFactor> factorToProperties) {
		SugarLevelSimulatorService.nameToFactor = factorToProperties;
	}

	public static SugarLevelSimulatorOutputs simulateGlucoseLevels(
			SugarLevelSimulatorInputs input) {
		System.out.println("Simulator input is " + input.toString());
		List<SugarLevelEvent> events = toSugarLevelEvents(input);
		SugarLevelDao dao = new SugarLevelDao();
		List<Pair<LocalTime, Float>> glucoseLevels = SugarLevelComputer
				.getGlucoseLevels(dao.getStartTime(), dao.getEndTime(), dao.getDisplayTimeStep(), events);
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
