package com.pree.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.LocalTime;

import com.pree.healthmodels.SugarLevelFactor;

public class SugarLevelDao {
	private static final String FOOD_DB="FoodDB.csv";
	private static final String EXERCISE_DB="ExerciseDB.csv";
	private LocalTime startTime;
	private LocalTime endTime;
	private float listTimeStep;
	private float displayTimeStep;
	
	private List<SugarLevelFactor> foodDB;
	private List<SugarLevelFactor> exerciseDB;
	
	public SugarLevelDao() {
		startTime = new LocalTime(7, 0);
		endTime = new LocalTime(22, 0);
		listTimeStep = 30.0f;
		displayTimeStep = 1.0f;
		loadFoodDB();
		loadExerciseDB();
	}
	
	private void loadFoodDB() {
		foodDB = new ArrayList<SugarLevelFactor>();
		Reader in = null;
		Iterable<CSVRecord> records = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(FOOD_DB).getFile());
			in = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			records = CSVFormat.EXCEL.withHeader().parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CSVRecord record : records) {
			SugarLevelFactor currentFactor = new SugarLevelFactor();
			currentFactor.setName(record.get("Name"));
			currentFactor.setRate(Integer.parseInt(record.get("Glycemic Index")));
			currentFactor.setDoesIncrease(true);
			currentFactor.setDuration(120.0f);
			foodDB.add(currentFactor);
		}
	}

	private void loadExerciseDB() {
		exerciseDB = new ArrayList<SugarLevelFactor>();
		Reader in = null;
		Iterable<CSVRecord> records = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(EXERCISE_DB).getFile());
			in = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			records = CSVFormat.EXCEL.withHeader().parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CSVRecord record : records) {
			SugarLevelFactor currentFactor = new SugarLevelFactor();
			currentFactor.setName(record.get("Exercise"));
			currentFactor.setRate(Integer.parseInt(record.get("Exercise Index")));
			currentFactor.setDoesIncrease(false);
			currentFactor.setDuration(60.0f);
			exerciseDB.add(currentFactor);
		}
	}
	
	public List<String> getFoodList() {
		List<String> output = new ArrayList<String>();
		for(SugarLevelFactor f : foodDB) {
			output.add(f.getName());
		}
		return output;
	}
	
	public List<String> getExerciseList() {
		List<String> output = new ArrayList<String>();
		for(SugarLevelFactor f : exerciseDB) {
			output.add(f.getName());
		}
		return output;
	}
	
	public Map<String, SugarLevelFactor> getNameToFactor() {
		Map<String, SugarLevelFactor> nameToFactor = new HashMap<String, SugarLevelFactor>();
		for(SugarLevelFactor f : foodDB) {
			nameToFactor.put(f.getName(), f);
		}
		for(SugarLevelFactor f : exerciseDB) {
			nameToFactor.put(f.getName(), f);
		}
		return nameToFactor;
	}

	public float getListTimeStep() {
		return listTimeStep;
	}

	public void setListTimeStep(float listTimeStep) {
		this.listTimeStep = listTimeStep;
	}

	public float getDisplayTimeStep() {
		return displayTimeStep;
	}

	public void setDisplayTimeStep(float displayTimeStep) {
		this.displayTimeStep = displayTimeStep;
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
