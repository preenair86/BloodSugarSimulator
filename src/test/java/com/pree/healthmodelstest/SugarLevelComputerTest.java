package com.pree.healthmodelstest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import com.pree.healthmodels.SugarLevelComputer;
import com.pree.healthmodels.SugarLevelEvent;
import com.pree.healthmodels.SugarLevelFactor;

public class SugarLevelComputerTest {
	private Date startTime = null, endTime = null;
	private float timeStep;
	@Before
	public void Initialize() {
		try {
			startTime = new SimpleDateFormat("hh:mm:ss").parse("07:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			endTime = new SimpleDateFormat("hh:mm:ss").parse("11:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		timeStep = 1.0f;
	}
	
	@Test
	public void singleEventTest() {
		Date eventTime = null;
		List<SugarLevelEvent> events = new ArrayList<SugarLevelEvent>();
		try {
			eventTime = new SimpleDateFormat("hh:mm:ss").parse("08:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SugarLevelFactor eventFactor = new SugarLevelFactor();
		eventFactor.setName("Food");
		eventFactor.setDoesIncrease(true);
		eventFactor.setDuration(120.0f);
		eventFactor.setRate(240);
		
		events.add(new SugarLevelEvent(eventFactor, eventTime));
		List<Pair<Date, Float>> actualOutput = SugarLevelComputer.getGlucoseLevels(startTime, endTime, timeStep, events);
		System.out.println("Expected output of singleEventTest is ");
		for(int i = 0; i < actualOutput.size(); ++i) {
			System.out.println("Time: " + actualOutput.get(i).getValue0() + ", Glucose Level is " + actualOutput.get(i).getValue1());
		}
	}
	
	@Test
	public void twoEventTest() {
		List<SugarLevelEvent> events = new ArrayList<SugarLevelEvent>();
		Date eventTime1 = null, eventTime2 = null;
		try {
			eventTime1 = new SimpleDateFormat("hh:mm:ss").parse("08:00:00");
			eventTime2 = new SimpleDateFormat("hh:mm:ss").parse("09:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		List<Pair<Date, Float>> actualOutput = SugarLevelComputer.getGlucoseLevels(startTime, endTime, timeStep, events);
		System.out.println("Expected output of twoEventTest is ");
		for(int i = 0; i < actualOutput.size(); ++i) {
			System.out.println("Time: " + actualOutput.get(i).getValue0() + ", Glucose Level is " + actualOutput.get(i).getValue1());
		}
	}
}
