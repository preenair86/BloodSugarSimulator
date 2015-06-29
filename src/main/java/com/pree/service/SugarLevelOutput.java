package com.pree.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

// Amount of different constituents in blood sugar
// at different instances of time.
public class SugarLevelOutput {
	// Times at which the constituents are simulated.
	List<LocalTime> times = new ArrayList<LocalTime>();
	
	// Amount of glucose in blood.
	List<Float> glucose = new ArrayList<Float>();
	
	// Amount of glycation in blood.
	List<Integer> glycation = new ArrayList<Integer>();

	public List<LocalTime> getTimes() {
		return times;
	}

	public void setTimes(List<LocalTime> times) {
		this.times = times;
	}

	public List<Float> getGlucose() {
		return glucose;
	}

	public void setGlucose(List<Float> glucose) {
		this.glucose = glucose;
	}

	public List<Integer> getGlycation() {
		return glycation;
	}

	public void setGlycation(List<Integer> glycation) {
		this.glycation = glycation;
	}
	
}
