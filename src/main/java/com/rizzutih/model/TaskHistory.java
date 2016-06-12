package com.rizzutih.model;

import java.util.Map;

public class TaskHistory {

	@Override
	public String toString() {
		return "TaskHistory [map=" + map + ", countmap=" + countmap + "]";
	}

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
		if(map.containsKey(taskName.toLowerCase())){
			return map.get(taskName.toLowerCase());
		}
		return 0;
	}

	public void clearHistory(){
		map.clear();
		countmap.clear();
	}

	public int countfavourityTask(String taskName) {
		int total =0;
		String tempTaskName = taskName.toLowerCase();
		if(countmap.containsKey(tempTaskName)){
			total = countmap.put(tempTaskName, countmap.get(tempTaskName)+1).intValue();
		}else{
				countmap.put(tempTaskName,1);
				total = 1;
		}
		return total;
	}

	public int getTotalCountFavouriteTask(String favouriteTask) {
		if(countmap.containsKey(favouriteTask.toLowerCase())){
			return countmap.get(favouriteTask.toLowerCase());
		}
		return 0;
		
	}
	


}
