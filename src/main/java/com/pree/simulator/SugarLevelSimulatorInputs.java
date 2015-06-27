package com.pree.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SugarLevelSimulatorInputs {    
    private List<SugarLevelInputPoint> data;

	public List<SugarLevelInputPoint> getData() {
		return data;
	}

	public void setData(List<SugarLevelInputPoint> data) {
		this.data = data;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(SugarLevelInputPoint point : data) {
			builder.append(point.toString()).append("\n");
		}
		return builder.toString();
	}
}
