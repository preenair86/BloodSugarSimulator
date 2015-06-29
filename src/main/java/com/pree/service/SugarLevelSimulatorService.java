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
import com.pree.controller.SugarLevelControllerInputs;
import com.pree.controller.SugarLevelControllerOutputs;
import com.pree.dao.SugarLevelDao;
import com.pree.healthmodels.SugarLevelComputer;

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

	public static SugarLevelControllerOutputs simulateGlucoseLevels(
			SugarLevelControllerInputs input) {
		System.out.println("Simulator input is " + input.toString());
		List<SugarLevelEvent> events = toSugarLevelEvents(input);
		SugarLevelDao dao = new SugarLevelDao();
		List<LocalTime> times = getSampledTimeList(dao.getStartTime(), dao.getEndTime(), dao.getDisplayTimeStep());
		SugarLevelOutput glucoseLevels = SugarLevelComputer.getGlucoseLevels(times, events);
		return toSugarLevelSimulatorOutput(glucoseLevels);
	}

	// Convert controller input to service input.
	private static List<SugarLevelEvent> toSugarLevelEvents(
			SugarLevelControllerInputs input) {
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

	// Convert service output to controller output.
	private static SugarLevelControllerOutputs toSugarLevelSimulatorOutput(
			SugarLevelOutput output) {
		List<SugarLevelOutputPoint> points = new ArrayList<SugarLevelOutputPoint>();
		for(int i = 0; i < output.getGlucose().size(); ++i) {
			SugarLevelOutputPoint currentPoint = new SugarLevelOutputPoint();
			currentPoint.setGlucoselevel(output.getGlucose().get(i));
			currentPoint.setGlycationLevel(output.getGlycation().get(i));
			currentPoint.setTime(output.getTimes().get(i).toString());
			points.add(currentPoint);
		}
		SugarLevelControllerOutputs controllerOutput = new SugarLevelControllerOutputs();
		controllerOutput.setData(points);
		return controllerOutput;
	}

	// Get a list of times given startTime, endTime and timeStep.
	public static List<LocalTime> getSampledTimeList(LocalTime startTime,
			LocalTime endTime, float timeStep) {
		List<LocalTime> output = new ArrayList<LocalTime>();
		LocalTime currentTime = startTime;
		while (!currentTime.isAfter(endTime)) {
			output.add(currentTime);
			currentTime = currentTime.plusMinutes((int) timeStep);
		}
		return output;
	}
}
