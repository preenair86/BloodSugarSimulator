package com.pree.controller;

public class SugarLevelOutputPoint {
	public int getGlycationLevel() {
		return glycationLevel;
	}

	public void setGlycationLevel(int glycationLevel) {
		this.glycationLevel = glycationLevel;
	}

	private String time;
	private float glucoselevel;
	private int glycationLevel;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public float getGlucoselevel() {
		return glucoselevel;
	}

	public void setGlucoselevel(float glucoselevel) {
		this.glucoselevel = glucoselevel;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Time:").append(time).append(".Glucose:").append(glucoselevel).toString();
	}
}