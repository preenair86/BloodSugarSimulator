package com.pree.simulatortest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;
import org.junit.Test;

import com.pree.controller.SugarLevelInputPoint;
import com.pree.controller.SugarLevelControllerInputs;
import com.pree.controller.SugarLevelControllerOutputs;
import com.pree.service.SugarLevelEvent;
import com.pree.service.SugarLevelFactor;
import com.pree.service.SugarLevelSimulatorService;

public class SugarLevelSimulatorTest {
	@Test
	public void simulateGlucoseLevelsTest() {
		String[] names = {"A"};
		String[] times = {"09:00:00"};
		boolean[] isFood = {true, false, true};
		
		List<SugarLevelInputPoint> input = new ArrayList<SugarLevelInputPoint>();
		List<SugarLevelEvent> expectedOutput = new ArrayList<SugarLevelEvent>();
		Map<String, SugarLevelFactor> nameToFactor = new HashMap<String, SugarLevelFactor>();
		SugarLevelFactor factor1 = new SugarLevelFactor();
		factor1.setName("A");
		factor1.setRate(120);
		factor1.setDuration(60.0f);
		factor1.setDoesIncrease(true);
		nameToFactor.put(factor1.getName(), factor1);

		// Populate input and expected outputs.
		for(int i = 0; i < names.length; ++i) {
			SugarLevelInputPoint point = new SugarLevelInputPoint();
			point.setFood(isFood[i]);
			point.setName(names[i]);
			point.setTime(times[i]);
			input.add(point);
		}
		SugarLevelSimulatorService simulator = new SugarLevelSimulatorService();
		simulator.setNameToFactor(nameToFactor);
		SugarLevelControllerInputs simulatorInputs = new SugarLevelControllerInputs();
		simulatorInputs.setData(input);
		System.out.println("Input is ");
		System.out.println(simulatorInputs.toString());
		SugarLevelControllerOutputs simulatorOutputs = simulator.simulateGlucoseLevels(simulatorInputs);
		System.out.println("Output is " + simulatorOutputs.toString());
	}
}
