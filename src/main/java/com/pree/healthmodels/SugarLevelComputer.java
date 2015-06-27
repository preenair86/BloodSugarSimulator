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

public class SugarLevelComputer {
  public static final float INITIAL_LEVEL = 80.0f;
  public static final float NORMALIZATION_LEVEL = 80.0f;
  // timeStep is the time between adjacent points in minutes.
  public static List<Pair<Date, Float>> getGlucoseLevels(Date startDate, Date endDate, float timeStep, List<SugarLevelEvent> events) {
	  System.out.println("Start time is " + startDate + ", End time is " + endDate + ", Step is " + timeStep);
	  List<Date> timeSamples = getSampledTime(startDate, endDate, timeStep);
	  List<Triplet<Date, Boolean, SugarLevelEvent>> keyedEvents = keyByTime(events);
	  Collections.sort(keyedEvents, new Comparator<Triplet<Date, Boolean, SugarLevelEvent>>(){
		public int compare(Triplet<Date, Boolean, SugarLevelEvent> o1,
				Triplet<Date, Boolean, SugarLevelEvent> o2) {
			return o1.getValue0().equals(o2.getValue0()) ? 0 :
				(o1.getValue0().before(o2.getValue0()) ? -1 : 1);
		}});
	  float glycemicRate = 0.0f;
	  float normalizationRate = 0.0f;
	  float currentLevel = INITIAL_LEVEL;
	  int nextEventIndex = 0;
	  List<Pair<Date, Float>> output = new ArrayList<Pair<Date, Float>>();
	  for(int i = 0; i < timeSamples.size(); ++i) {
		  if(nextEventIndex < keyedEvents.size() && !keyedEvents.get(nextEventIndex).getValue0().after(timeSamples.get(i))) {
			  Triplet<Date, Boolean, SugarLevelEvent> nextEventTriplet = keyedEvents.get(nextEventIndex);
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
  
  private static List<Triplet<Date, Boolean /* isStartTime */, SugarLevelEvent>> 
      keyByTime(List<SugarLevelEvent> events) {
	List<Triplet<Date, Boolean, SugarLevelEvent>> output = new ArrayList<Triplet<Date, Boolean, SugarLevelEvent>>();
    for(SugarLevelEvent e : events) {
    	output.add(Triplet.with(e.getStartTime(), true, e));
    	output.add(Triplet.with(DateUtils.addMinutes(e.getStartTime(), (int)e.getFactor().getDuration()), false, e));
    }
	return output;  
  }
  private static List<Date> getSampledTime(Date startTime, Date endTime, float timeStep) {
	  List<Date> output = new ArrayList<Date>();
	  Date currentTime = startTime;
	  while(!currentTime.after(endTime)) {
		  output.add(currentTime);
		  currentTime = DateUtils.addMinutes(currentTime, (int)timeStep);
	  }
	  return output;
  }
}
