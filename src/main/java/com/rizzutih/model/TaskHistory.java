package com.rizzutih.model;

import java.util.Map;

public class TaskHistory {

	private Map<String, Integer> map;

	public TaskHistory(Map<String, Integer> map) {
		this.map =map;
	}

	public void add(String taskName, int numberOfPeopleForTask) {
		map.put(taskName.toLowerCase(), numberOfPeopleForTask);
	}

	public int get(String taskName) {
		if(!map.containsKey(taskName)){
			return 0;
		}
		return map.get(taskName);
	}
	
	public void clearHistory(){
		map.clear();
	}
	
	

}
