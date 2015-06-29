package com.pree.controller;

import java.util.List;

// Output data to be passed to the user interface.
public class SugarLevelControllerOutputs {
	public List<SugarLevelOutputPoint> getData() {
		return data;
	}

	public void setData(List<SugarLevelOutputPoint> data) {
		this.data = data;
	}

	private List<SugarLevelOutputPoint> data;

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (SugarLevelOutputPoint point : data) {
			builder.append(point.toString()).append("\n");
		}
		return builder.toString();
	}
}
