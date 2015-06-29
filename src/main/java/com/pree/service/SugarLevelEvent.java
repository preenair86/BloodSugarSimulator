package com.pree.service;

import java.util.Date;

import org.joda.time.LocalTime;

// SugarLevelEvent is an event that occurred during the day, which
// influence the blood sugar levels. It includes a SugarLevelFactor
// and the time at which it occurred.
public class SugarLevelEvent {
	public SugarLevelEvent(SugarLevelFactor factor, LocalTime startTime) {
		this.factor = factor;
		this.startTime = startTime;
	}

	private SugarLevelFactor factor;
	private LocalTime startTime;

	public SugarLevelFactor getFactor() {
		return factor;
	}

	public void setFactor(SugarLevelFactor factor) {
		this.factor = factor;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
}
