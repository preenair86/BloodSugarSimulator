package com.pree.healthmodels;

import java.util.Date;

// SugarLevelEvent is an event that occurred during the day, which
// influence the blood sugar levels. It includes a SugarLevelFactor
// and the time at which it occurred.
public class SugarLevelEvent {
  public SugarLevelEvent(SugarLevelFactor factor, Date startTime) {
		this.factor = factor;
		this.startTime = startTime;
	}
private SugarLevelFactor factor;
  // Number of minutes from the beginning of the day where the event occurred.
  private Date startTime;
public SugarLevelFactor getFactor() {
	return factor;
}
public void setFactor(SugarLevelFactor factor) {
	this.factor = factor;
}
public Date getStartTime() {
	return startTime;
}
public void setStartTime(Date startTime) {
	this.startTime = startTime;
}
}
