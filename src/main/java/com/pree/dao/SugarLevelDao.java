package com.pree.dao;

import org.joda.time.LocalTime;

public class SugarLevelDao {
	private LocalTime startTime;
	private LocalTime endTime;
	private float timeStep;

	public SugarLevelDao() {
		startTime = new LocalTime(7, 0);
		endTime = new LocalTime(12, 0);
		timeStep = 30.0f;
	}

	public float getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(float timeStep) {
		this.timeStep = timeStep;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
}
