package com.pree.healthmodelstest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javatuples.Pair;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import com.pree.healthmodels.SugarLevelComputer;
import com.pree.service.SugarLevelEvent;
import com.pree.service.SugarLevelFactor;
import com.pree.service.SugarLevelOutput;
import com.pree.service.SugarLevelSimulatorService;

public class SugarLevelComputerTest {
	private LocalTime startTime = new LocalTime(7, 0);
	private LocalTime endTime = new LocalTime(11, 0);
	private float timeStep = 1.0f;
	
	@Test
	public void singleEventTest() {
		LocalTime eventTime = new LocalTime(7, 0); 
		List<SugarLevelEvent> events = new ArrayList<SugarLevelEvent>();
		
		SugarLevelFactor eventFactor = new SugarLevelFactor();
		eventFactor.setName("Food");
		eventFactor.setDoesIncrease(true);
		eventFactor.setDuration(120.0f);
		eventFactor.setRate(240);
		
		events.add(new SugarLevelEvent(eventFactor, eventTime));
		List<LocalTime> times = SugarLevelSimulatorService.getSampledTimeList(startTime, endTime, timeStep);
		SugarLevelOutput actualOutput = SugarLevelComputer.getGlucoseLevels(times, events);
		System.out.println("Expected output of singleEventTest is ");
		for(int i = 0; i < actualOutput.getTimes().size(); ++i) {
			System.out.println("Time: " + actualOutput.getTimes().get(i) + 
					", Glucose Level is " + actualOutput.getGlucose().get(i) +
					", Glycation is " + actualOutput.getGlycation().get(i));
		}
	}
	
	@Test
	public void twoEventTest() {
		List<SugarLevelEvent> events = new ArrayList<SugarLevelEvent>();
		LocalTime eventTime1 = new LocalTime(8, 0), eventTime2 = new LocalTime(9, 0); ;
		SugarLevelFactor eventFactor1 = new SugarLevelFactor();
		eventFactor1.setName("Food");
		eventFactor1.setDoesIncrease(true);
		eventFactor1.setDuration(120.0f);
		eventFactor1.setRate(120);

		SugarLevelFactor eventFactor2 = new SugarLevelFactor();
		eventFactor2.setName("Exercise");
		eventFactor2.setDoesIncrease(false);
		eventFactor2.setDuration(60.0f);
		eventFactor2.setRate(60);
		
		events.add(new SugarLevelEvent(eventFactor2, eventTime2));
		events.add(new SugarLevelEvent(eventFactor1, eventTime1));
		List<LocalTime> times = SugarLevelSimulatorService.getSampledTimeList(startTime, endTime, timeStep);
		SugarLevelOutput actualOutput = SugarLevelComputer.getGlucoseLevels(times, events);
		System.out.println("Expected output of twoEventTest is ");
		for(int i = 0; i < actualOutput.getTimes().size(); ++i) {
			System.out.println("Time: " + actualOutput.getTimes().get(i) + 
					", Glucose Level is " + actualOutput.getGlucose().get(i) +
					", Glycation is " + actualOutput.getGlycation().get(i));
		}
	}
}
