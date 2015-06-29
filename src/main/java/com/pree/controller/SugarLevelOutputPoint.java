package com.pree.controller;

public class SugarLevelOutputPoint {
	private String time;
	private float glucoselevel;

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