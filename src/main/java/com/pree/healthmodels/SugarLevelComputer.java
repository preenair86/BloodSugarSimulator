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

public class SugarLevelComputer {
  public static final float INITIAL_LEVEL = 80.0f;
  public static final float NORMALIZATION_LEVEL = 80.0f;
  // timeStep is the time between adjacent points in minutes.
  public static List<Pair<LocalTime, Float>> getGlucoseLevels(LocalTime startTime, LocalTime endTime, float timeStep, List<SugarLevelEvent> events) {
	  System.out.println("Start time is " + startTime + ", End time is " + endTime + ", Step is " + timeStep);
	  List<LocalTime> timeSamples = getSampledTime(startTime, endTime, timeStep);
	  List<Triplet<LocalTime, Boolean, SugarLevelEvent>> keyedEvents = keyByTime(events);
	  Collections.sort(keyedEvents, new Comparator<Triplet<LocalTime, Boolean, SugarLevelEvent>>(){
		public int compare(Triplet<LocalTime, Boolean, SugarLevelEvent> o1,
				Triplet<LocalTime, Boolean, SugarLevelEvent> o2) {
			return o1.getValue0().isEqual(o2.getValue0()) ? 0 :
				(o1.getValue0().isBefore(o2.getValue0()) ? -1 : 1);
		}});
	  float glycemicRate = 0.0f;
	  float normalizationRate = 0.0f;
	  float currentLevel = INITIAL_LEVEL;
	  int nextEventIndex = 0;
	  List<Pair<LocalTime, Float>> output = new ArrayList<Pair<LocalTime, Float>>();
	  for(int i = 0; i < timeSamples.size(); ++i) {
		  if(nextEventIndex < keyedEvents.size() && !keyedEvents.get(nextEventIndex).getValue0().isAfter(timeSamples.get(i))) {
			  Triplet<LocalTime, Boolean, SugarLevelEvent> nextEventTriplet = keyedEvents.get(nextEventIndex);
			  SugarLevelEvent event = nextEventTriplet.getValue2();
			  float changeInGlycemicRate = event.getFactor().getRate() / 60.0f * (event.getFactor().isDoesIncrease() ? 1 : -1); 
			  glycemicRate += changeInGlycemicRate * (nextEventTriplet.getValue1() ? 1 : -1);
			  ++nextEventIndex;
		  }
		  currentLevel = currentLevel + (glycemicRate + normalizationRate) * timeStep;
		  output.add(Pair.with(timeSamples.get(i), currentLevel));
		  normalizationRate = (Math.abs(currentLevel - NORMALIZATION_LEVEL) < Math.ulp(currentLevel)) ?
				  0.0f : (currentLevel > NORMALIZATION_LEVEL ? -1.0f : 1.0f);
	  }
	  return output;
  }
  
  private static List<Triplet<LocalTime, Boolean /* isStartTime */, SugarLevelEvent>> 
      keyByTime(List<SugarLevelEvent> events) {
	List<Triplet<LocalTime, Boolean, SugarLevelEvent>> output = new ArrayList<Triplet<LocalTime, Boolean, SugarLevelEvent>>();
    for(SugarLevelEvent e : events) {
    	LocalTime currentTime = e.getStartTime();
    	output.add(Triplet.with(currentTime, true, e));
    	System.out.println("Time is " + currentTime.toString());
    	System.out.println(". Current duration is " + (int)e.getFactor().getDuration());
    	currentTime = currentTime.plusMinutes((int)e.getFactor().getDuration());
    	output.add(Triplet.with(currentTime, false, e));
    }
	return output;  
  }
  private static List<LocalTime> getSampledTime(LocalTime startTime, LocalTime endTime, float timeStep) {
	  List<LocalTime> output = new ArrayList<LocalTime>();
	  LocalTime currentTime = startTime;
	  while(!currentTime.isAfter(endTime)) {
		  output.add(currentTime);
		  currentTime = currentTime.plusMinutes((int)timeStep);
	  }
	  return output;
  }
}
