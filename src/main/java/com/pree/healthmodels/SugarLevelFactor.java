package com.pree.healthmodels;

// A factor that influences the blood sugar level.
public class SugarLevelFactor {
	public SugarLevelFactor(String name, int rate, boolean doesIncrease,
			float duration) {
		super();
		this.name = name;
		this.rate = rate;
		this.doesIncrease = doesIncrease;
		this.duration = duration;
	}

	private String name;
	// Rate of change of blood sugar level per minute.
	private int rate;
	// False indicates that the factor reduces blood sugar.
	private boolean doesIncrease;
	// Number of minutes the factor affects the blood sugar.
	private float duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isDoesIncrease() {
		return doesIncrease;
	}

	public void setDoesIncrease(boolean doesIncrease) {
		this.doesIncrease = doesIncrease;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}
}
