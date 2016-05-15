package com.rizzutih.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ObjectSplitter {

	private List<Task> taskList;
	private List<People> peopleList;
	private Map<String, List<String>> taskPeopleMap;
	private TreeMap<Date, Map<String, List<String>>> dateTaskPeopleMap;
	private SimpleDateFormat dt;
	private Date sd= null;
	private Date ed = null;
	private List<People> availablePeople;
	private List<People> favouriteTaskPeopleAvailable;

	public ObjectSplitter() {
		taskList = new ArrayList<Task>();
		peopleList = new ArrayList<People>();
		dt = new SimpleDateFormat("dd/MM/yyyy");
		dateTaskPeopleMap = new TreeMap<Date, Map<String,List<String>>>();
		availablePeople =  new ArrayList<People>();
		favouriteTaskPeopleAvailable = new ArrayList<People>();
	}

	public List<Task> splittasklist(String taskString) {

		String[] tasks = taskString.split(";");
		String[] taskandpeople;
		for(int i=0; i<=tasks.length-1; i++){
			if(i==0){
				String[] date =  tasks[i].split("until");
				try {
					sd = dt.parse(date[0].trim());
					ed = dt.parse(date[1].trim());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				taskandpeople= tasks[i].split(",");
				taskList.add(new Task(taskandpeople[0], Integer.parseInt(taskandpeople[1].trim()),sd,ed));
			}
		}
		return taskList;
	}

	public List<People> splitPeopleListSameDate(String peopleString) {
		String[] nameDatesAndTask = peopleString.split(";");
		String [] nameAndFavoutireTask;
		String name = null;
		String favouriteTask = null;
		for(int i=0; i<=nameDatesAndTask.length-1; i++){
			if(i==0){
				String[] date =  nameDatesAndTask[i].split("until");
				try {
					sd = dt.parse(date[0].trim());
					ed = dt.parse(date[1].trim());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				nameAndFavoutireTask= nameDatesAndTask[i].split(",");
				name = nameAndFavoutireTask[0];
				if(!nameAndFavoutireTask[1].equals("none")){
					favouriteTask = nameAndFavoutireTask[1];	
				}
				People p_temp = new People(name, sd, ed, favouriteTask);
				long totalDays = calculateDates(sd, ed);
				p_temp.setTotalDates(totalDays);
				if(favouriteTask!=null){
					p_temp.setFavouriteTaskDates(calculateFavouriteTaskDays(totalDays));
				}
				peopleList.add(p_temp);			
			}
		}
		return peopleList;
	}

	public List<People> splitPeopleList(String peopleString) {

		String[] nameDatesAndTask = peopleString.split(";");
		for(String ndts: nameDatesAndTask){
			String name = null;
			String favouriteTask = null;
			String[] ndt = ndts.split(",");
			for(int i=0; i<=2; i++){
				if(i==0){
					name = ndt[i];
				}else if(i==1){
					String[] date =  ndt[i].split("until");
					try {
						sd = dt.parse(date[0].trim());
						ed = dt.parse(date[1].trim());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					if(!ndt[i].equals("none")){
						favouriteTask = ndt[i];
					}
				}
			}
			People p_temp = new People(name, sd, ed, favouriteTask);
			long totalDays=calculateDates(sd, ed);
			p_temp.setTotalDates(totalDays);
			if(favouriteTask!=null){
				p_temp.setFavouriteTaskDates(calculateFavouriteTaskDays(totalDays));
			}
			peopleList.add(p_temp);
		}
		return peopleList;
	}

	public long calculateDates(Date startDate, Date endDate) {
		long diff = endDate.getTime() - startDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

	public long calculateFavouriteTaskDays(long totalDays) {
		double d = (double)totalDays;
		return Math.round(d*60/100);
	}

	public Date addDay(Date currentDate, int day) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(currentDate); 
		c.add(Calendar.DATE, day);
		currentDate = c.getTime();
		return currentDate;
	}

	public List<String> getFavourityTaskPeopleList(int taskpeopleNum, List<People> favouriteTaskAvailablePeople) {
		List<String> calPeopleList = new ArrayList<String>();
		if(favouriteTaskAvailablePeople!=null){
			if(favouriteTaskAvailablePeople.size()!=0){//check for favourite task available people
				Iterator<People> iterator = favouriteTaskAvailablePeople.iterator();
				while(iterator.hasNext()){
					People favTaskAvailableppl = iterator.next();
					//checking if the number of people for the current task have not fulfilled the number of people for that task
					if(calPeopleList.size() < taskpeopleNum){
						if(favTaskAvailableppl.getFavouriteTaskDays()!=0){
							calPeopleList.add(favTaskAvailableppl.getName());
							//removing one day from favourite task
							favTaskAvailableppl.setFavouriteTaskDates(favTaskAvailableppl.getFavouriteTaskDays()-1);
							favTaskAvailableppl.setTotalDates(favTaskAvailableppl.getTotalDates()-1);
							iterator.remove();//removing person
						}
					}
				}
			}
		}
		return calPeopleList;
	}

	public List<People> getPeopleAvailable(List<People> everyone, Date currentDate) {
		availablePeople.clear();
		for(People p : everyone){
			if(currentDate.equals(p.getStartDate()) || currentDate.after(p.getStartDate()) && currentDate.before(p.getEndDate())){
				availablePeople.add(p);
			}
		}
		return availablePeople;
	}

	public List<People> getFavouriteTaskPeopleAvailable(List<People> peopleAvailable, String taskName) {
		favouriteTaskPeopleAvailable.clear();
		for(People p : peopleAvailable){
			if(p.getFavouriteTask() !=null){
				if(p.getFavouriteTask().equals(taskName)){
					favouriteTaskPeopleAvailable.add(p);
				}
			}
		}
		return favouriteTaskPeopleAvailable;
	}

	public TreeMap<Date, Map<String, List<String>>> addPeopleFavouriteTaskToList(List<Task> taskList, List<People> peopleList) {
		Date currentDate = taskList.get(0).getStartDate();
		Date endDate = taskList.get(0).getEndDate();

		while(currentDate.before((endDate))){//is the date before the end date
			taskPeopleMap = new HashMap<String, List<String>>();
			for(int i=0;i<=taskList.size()-1;i++){
				String taskName = taskList.get(i).getTaskName(); //current task
				int taskpeopleNum = taskList.get(i).getPeople();// number of people for that task
				List<People> availablePeople = getPeopleAvailable(peopleList, currentDate); //all available people for that date
				//available people who have the current task as favourite task
				List<People> favouriteTaskAvailablePeople = getFavouriteTaskPeopleAvailable(availablePeople, taskName);
				taskPeopleMap.put(taskName, getFavourityTaskPeopleList(taskpeopleNum, favouriteTaskAvailablePeople));
			}
			dateTaskPeopleMap.put(currentDate, taskPeopleMap);
			currentDate = addDay(currentDate, 1);
		}
		return dateTaskPeopleMap;
	}

	public TreeMap<Date, Map<String, List<String>>> addPeopleTaskToList(List<Task> taskList, List<People> peopleList, TreeMap<Date, Map<String, List<String>>> peopleFavouriteTaskList) {
		//Map of dates with tasks and people allocated to the corresponding task
		TreeMap<Date, Map<String,List<String>>> dateTaskPeopleMap = peopleFavouriteTaskList;
		Date currentDate = taskList.get(0).getStartDate();
		Date endDate = taskList.get(0).getEndDate();

		while(currentDate.before((endDate))){//is the date before the end date
			//Map of tasks and people allocated to the corresponding task 
			Map<String, List<String>> taskPeopleMap = dateTaskPeopleMap.get(currentDate);
			for(int i=0;i<=taskPeopleMap.size()-1;i++){
				String taskName = taskList.get(i).getTaskName(); //current task
				//List of people's name
				List<String> currentTaskPeopleList = taskPeopleMap.get(taskName);
				int taskPeopleNum = taskList.get(i).getPeople();// number of people for that task
				List<People> availablePeople = getPeopleAvailable(peopleList, currentDate); //all available people for that date
				//randomise available people
				Collections.shuffle(availablePeople);
				Iterator<People> iterator = availablePeople.iterator();
				int count =0;
				while(iterator.hasNext()){
					People availableppl = iterator.next();
					//checking that the number of people's name matches the number of people for that task
					if(currentTaskPeopleList.size()==0){
						addAvailablePeople(taskPeopleMap, currentTaskPeopleList, iterator, count, availableppl);
					}else if(currentTaskPeopleList.size()<taskPeopleNum){
						//see if the name matches
						if(!currentTaskPeopleList.get(count).equals(availableppl.getName())){
							addAvailablePeople(taskPeopleMap, currentTaskPeopleList, iterator, count, availableppl);
						}else {
							iterator.remove();//removing person
						}
					}else{
						break;
					}
				}
				taskPeopleMap.put(taskName, currentTaskPeopleList);
			}
			dateTaskPeopleMap.put(currentDate, taskPeopleMap);
			currentDate = addDay(currentDate,1);
		}
		return dateTaskPeopleMap;
	}

	public void addAvailablePeople(Map<String, List<String>> taskPeopleMap, List<String> currentTaskPeopleList,
			Iterator<People> iterator, int count, People availableppl) {
		boolean isBooked = false;
		/*check if the person is in another task on the same day*/
		outerloop:
			for(Map.Entry<String, List<String>> entry:taskPeopleMap.entrySet()){
				for(String name: entry.getValue()){
					if(name.equals(availableppl.getName())){
						isBooked = true;
						break outerloop;
					}
				}
			}
		if(isBooked==false && availableppl.getTotalDates() > 0){
			currentTaskPeopleList.add(availableppl.getName());
			availableppl.setTotalDates(availableppl.getTotalDates()-1);
			count++;
		}
		iterator.remove();//removing person
	}

	public TreeMap<Date, Map<String, List<String>>> handler(String task, String people) {
		if(task==null || people ==null){
			System.err.println("ERROR: There is a problem with the task or people list.");
			return null;
		}else if(task.length()==0 || people.length()==0){
			System.err.println("ERROR: There is a problem with the number of people or tasks.");
			return null;
		}else if(countPeopleInTask(task) != countPeople(people)){
			System.err.println("ERROR: The total number of people and task is not the same.");
			return null;
		}
		else{
			List <Task> taskls = splittasklist(task);
			//List <People> peoplels = splitPeopleList(people);
			List <People> peoplels = splitPeopleListSameDate(people);
			TreeMap<Date,Map<String,List<String>>> peopleFavTaskLs = addPeopleFavouriteTaskToList(taskList,peopleList);
			TreeMap<Date,Map<String,List<String>>> calList = addPeopleTaskToList(taskls, peoplels, peopleFavTaskLs);
			return calList;
		}
	}

	public int countPeopleInTask(String task) {
		List <Task> taskls= splittasklist(task);
		int total = 0;
		for(Task t: taskls){
			total = total + t.getPeople();
		}
		taskls.clear();
		return total;
	}

	public int countPeople(String people) {
		int total = 0;
		//		List<People> peoplels = splitPeopleList(people);
		List<People> peoplels = splitPeopleListSameDate(people);
		Date temp_start_date = null;
		Date temp_end_date = null;
		for(int i=0;i<=peoplels.size()-1;i++){
			if(i==0){
				temp_start_date = peoplels.get(i).getStartDate();	
				temp_end_date = peoplels.get(i).getEndDate();
				total++;
			}else{
				if(temp_start_date.equals(peoplels.get(i).getStartDate())
						&&temp_end_date.equals(peoplels.get(i).getEndDate())){
					total++;
				}

			}			
		}
		peoplels.clear();
		return total;
	}


}