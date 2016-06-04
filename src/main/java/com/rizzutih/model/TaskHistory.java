package com.rizzutih.model;

import java.util.Map;

public class TaskHistory {

	private Map<String, Integer> map;
	private Map<String, Integer> countmap;

	public TaskHistory(Map<String, Integer> map, Map<String, Integer> countmap) {
		this.map =map;
		this.countmap=countmap;
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
		countmap.clear();
	}

	public int countfavourityTask(String taskName) {
		int total =0;
		if(countmap.containsKey(taskName)){
			total = countmap.put(taskName,
					countmap.get(taskName)+1);
		}else{
			total = countmap.put(taskName,1);
		}
		return total;
	}



}
