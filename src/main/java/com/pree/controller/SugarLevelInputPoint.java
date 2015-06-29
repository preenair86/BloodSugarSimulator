package com.pree.controller;

public class SugarLevelInputPoint {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFood() {
		return isFood;
	}

	public void setFood(boolean isFood) {
		this.isFood = isFood;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private String name;
	private boolean isFood;
	private String time;

	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append("Name:").append(name).append(".isFood:")
				.append(isFood).append(".Time:").append(time).toString();
	}
}
