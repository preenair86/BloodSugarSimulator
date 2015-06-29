package com.pree.controller;

import java.util.List;

public class SugarLevelSimulatorOutputs {
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
