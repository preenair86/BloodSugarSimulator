// Compute levels of important quantities in blood like glucose
// and glycation.
package com.pree.healthmodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.joda.time.LocalTime;

import com.pree.service.SugarLevelEvent;
import com.pree.service.SugarLevelOutput;

public class SugarLevelComputer {
	public static final float INITIAL_LEVEL = 80.0f;
	public static final float NORMALIZATION_LEVEL = 80.0f;
	public static final float GLYCATION_THRESHOLD = 150.0f;

	public static SugarLevelOutput getGlucoseLevels(
			List<LocalTime> timeSamples, List<SugarLevelEvent> events) {
		List<Triplet<LocalTime, Boolean, SugarLevelEvent>> keyedEvents = keyByTime(events);
		Collections.sort(keyedEvents,
				new Comparator<Triplet<LocalTime, Boolean, SugarLevelEvent>>() {
					public int compare(
							Triplet<LocalTime, Boolean, SugarLevelEvent> o1,
							Triplet<LocalTime, Boolean, SugarLevelEvent> o2) {
						return o1.getValue0().isEqual(o2.getValue0()) ? 0 : (o1
								.getValue0().isBefore(o2.getValue0()) ? -1 : 1);
					}
				});

		float netGlycemicRate = 0.0f;
		float normalizationRate = 1.0f;
		float currentGlucoseLevel = INITIAL_LEVEL;
		int nextEventIndex = 0;
		int numCurrentEvents = 0;
		int currentGlycationLevel = 0;
		
		// Outputs
		List<LocalTime> outputTimes = new ArrayList<LocalTime>();
		List<Float> outputGlucose = new ArrayList<Float>();
		List<Integer> outputGlycation = new ArrayList<Integer>();
		
		for (int i = 0; i < timeSamples.size(); ++i) {
			if (i != 0) {
				// Compute the current level based on food exercise and normalization
				// factors. For i == 0 case, we set the level to INITIAL_LEVEL.
				
				// Find all the events which are within the new time sample.
				while (nextEventIndex < keyedEvents.size()
						&& !keyedEvents.get(nextEventIndex).getValue0()
								.isAfter(timeSamples.get(i))) {
					Triplet<LocalTime, Boolean, SugarLevelEvent> nextEventTriplet = keyedEvents
							.get(nextEventIndex);
					SugarLevelEvent event = nextEventTriplet.getValue2();
					float changeInGlycemicRate = event.getFactor().getRate() / 60.0f;
					// Find if glycemic rate change is positive of negative
					// depending on whether the event id a food
					// or exercise event, and whether the current point is a
					// start or end point.
					netGlycemicRate += changeInGlycemicRate
							* (nextEventTriplet.getValue1() ? 1 : -1)
							* (event.getFactor().isDoesIncrease() ? 1 : -1);
					// Update the number of events.
					if (nextEventTriplet.getValue1()) {
						++numCurrentEvents;
					} else {
						--numCurrentEvents;
					}
					++nextEventIndex;
				}
				// Find step between the current and last time sample.
				float timeStepInMinutes = (timeSamples.get(i).getMillisOfDay() - timeSamples.get(i - 1).getMillisOfDay())
						/ (1000 * 60);
				
				// Check if normalization has effect.
				if (numCurrentEvents == 0) {
					if (currentGlucoseLevel > NORMALIZATION_LEVEL) {
						currentGlucoseLevel -= timeStepInMinutes * normalizationRate;
					} else {
						currentGlucoseLevel += timeStepInMinutes * normalizationRate;
					}
				}
				// Find change in level due to food or exercise.
				currentGlucoseLevel = currentGlucoseLevel + netGlycemicRate * timeStepInMinutes;
			}
			if(currentGlucoseLevel > GLYCATION_THRESHOLD) {
				++currentGlycationLevel;
			}
			outputTimes.add(timeSamples.get(i));
			outputGlucose.add(currentGlucoseLevel);
			outputGlycation.add(currentGlycationLevel);
		}
		SugarLevelOutput output = new SugarLevelOutput();
		output.setGlucose(outputGlucose);
		output.setGlycation(outputGlycation);
		output.setTimes(outputTimes);
		return output;
	}

	private static List<Triplet<LocalTime, Boolean /* isStartTime */, SugarLevelEvent>> keyByTime(
			List<SugarLevelEvent> events) {
		List<Triplet<LocalTime, Boolean, SugarLevelEvent>> output = new ArrayList<Triplet<LocalTime, Boolean, SugarLevelEvent>>();
		for (SugarLevelEvent e : events) {
			LocalTime currentTime = e.getStartTime();
			output.add(Triplet.with(currentTime, true, e));
			System.out.println("Time is " + currentTime.toString());
			System.out.println(". Current duration is "
					+ (int) e.getFactor().getDuration());
			currentTime = currentTime.plusMinutes((int) e.getFactor()
					.getDuration());
			output.add(Triplet.with(currentTime, false, e));
		}
		return output;
	}
}
