package com.rizzutih.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

public class ObjectSplitterTest {
	
	private ObjectSplitter os;
	private SimpleDateFormat dt;
	private ArrayList<Map<Date, Map<String, List<String>>>> calendarList;
	
	@Before
	public void setUp(){
		os = new ObjectSplitter();
		dt = new SimpleDateFormat("dd/MM/yyyy");
		calendarList = new ArrayList<Map<Date, Map<String, List<String>>>>();
	}

	@Test
	public void testSplitTaskListBySemiColon() throws ParseException {
		List <Task> taskList =  new ArrayList<Task>();
		taskList.add(new Task("Cooking", 2, dt.parse("30/07/2016"), dt.parse("06/08/2016")));
		taskList.add(new Task("Drink Preparation", 1,dt.parse("30/07/2016"), dt.parse("06/08/2016")));
		taskList.add(new Task("House Keeping", 2, dt.parse("30/07/2016"), dt.parse("06/08/2016")));	
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		assertEquals(taskList.get(0).getTaskName(),actualTaskList.get(0).getTaskName());
		assertEquals(taskList.get(1).getPeople(),actualTaskList.get(1).getPeople());
		assertEquals(taskList.get(0).getEndDate(),actualTaskList.get(0).getEndDate());
		assertEquals(taskList.get(0).getStartDate(),actualTaskList.get(0).getStartDate());	
	}
	
	@Test
	public void testSplitPeopleListBySemiColon() throws ParseException {

		List <People> expectedPeopleList =  new ArrayList<People>();
		expectedPeopleList.add(new People("Nico", dt.parse("30/07/2016"), dt.parse("06/08/2016"), "House Keeping"));
		expectedPeopleList.add(new People("Yan", dt.parse("30/07/2016"), dt.parse("06/08/2016"), "Drink Preparation"));
		expectedPeopleList.add(new People("Hernan", dt.parse("30/07/2016"), dt.parse("06/08/2016"), "Cooking"));
		expectedPeopleList.add(new People("Simon", dt.parse("30/07/2016"), dt.parse("06/08/2016"), null));
		List <People> actualPeopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		assertEquals(expectedPeopleList.get(0).getName(), actualPeopleList.get(0).getName());
		assertEquals(expectedPeopleList.get(1).getName(), actualPeopleList.get(1).getName());
		assertEquals(expectedPeopleList.get(2).getFavouriteTask(), actualPeopleList.get(2).getFavouriteTask());
		assertEquals(expectedPeopleList.get(3).getFavouriteTask(), actualPeopleList.get(3).getFavouriteTask());
	}
	
	@Test
	public void testCalculateDatesReturnsNumberOfDates() throws ParseException{
		long actualDatesNum=os.calculateDates(dt.parse("30/07/2016"),dt.parse("06/08/2016"));
		assertEquals(7,actualDatesNum);
	}
	
	@Test
	public void testCalculateFavourityTaskDatesReturnsNumberOfDates() throws ParseException{
		long actualDatesNum=os.calculateFavouriteTaskDays(7);
		assertEquals(4,actualDatesNum);
		actualDatesNum=os.calculateFavouriteTaskDays(6);
		assertEquals(4,actualDatesNum);
		actualDatesNum=os.calculateFavouriteTaskDays(5);
		assertEquals(3,actualDatesNum);
	}
	
	@Test
	public void testgetPeopleAvailableOnTheFifthOfAugust() throws ParseException{
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		List <People> actualPeopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		Calendar c = Calendar.getInstance(); 
		c.setTime(actualTaskList.get(0).getStartDate()); 
		c.add(Calendar.DATE, 6);
		List <People> availablePeopleList  = os.getPeopleAvailable(actualPeopleList,c.getTime());//Nico, Yan, SImon
		assertEquals(3, availablePeopleList.size());
	}
	@Test
	public void testgetPeopleAvailableOnTheThirtyFirstOfJuly() throws ParseException{
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		List <People> actualPeopleList = os.splitPeopleList("Nico,01/08/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,01/08/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		Calendar c = Calendar.getInstance(); 
		c.setTime(actualTaskList.get(0).getStartDate()); 
		c.add(Calendar.DATE, 1);
		List <People> availablePeopleList  = os.getPeopleAvailable(actualPeopleList,c.getTime());
		assertEquals(2, availablePeopleList.size());
	}
	
	@Test
	public void testgetFavouriteTaskPeopleAvailableReturnNoPeopleAvaliableForThatTask() throws ParseException{
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		List <People> actualPeopleList = os.splitPeopleList("Nico,01/08/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,01/08/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		Calendar c = Calendar.getInstance(); 
		c.setTime(actualTaskList.get(0).getStartDate()); 
		c.add(Calendar.DATE, 1);
		List <People> favouriteTaskPeopleAvailable  = os.getFavouriteTaskPeopleAvailable(os.getPeopleAvailable(actualPeopleList,c.getTime()),actualTaskList.get(0).getTaskName());
		assertEquals(0, favouriteTaskPeopleAvailable.size());
	}
	
	@Test
	public void testgetFavouriteTaskPeopleAvailableReturnOnePersonAvaliableForThatTask() throws ParseException{
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		List <People> actualPeopleList = os.splitPeopleList("Nico,01/08/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		Calendar c = Calendar.getInstance(); 
		c.setTime(actualTaskList.get(0).getStartDate()); 
		c.add(Calendar.DATE, 1);
		List <People> favouriteTaskPeopleAvailable  = os.getFavouriteTaskPeopleAvailable(os.getPeopleAvailable(actualPeopleList,c.getTime()),actualTaskList.get(0).getTaskName());
		assertEquals(1, favouriteTaskPeopleAvailable.size());
	}
	
	@Test
	public void testgetCalPeopleList() throws ParseException{
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		List <People> actualPeopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		List<String> calPeopleList = new ArrayList<String>();
		calPeopleList.add(actualPeopleList.get(0).getName());//Nico
		calPeopleList.add(actualPeopleList.get(3).getName());//Simon
		List<String> actualCalPeopleList = os.getFavourityTaskPeopleList(actualTaskList.get(2).getPeople(), 
				os.getFavouriteTaskPeopleAvailable(os.getPeopleAvailable(actualPeopleList, 
						actualTaskList.get(0).getStartDate()), actualTaskList.get(2).getTaskName()));
		assertEquals(calPeopleList.get(0),actualCalPeopleList.get(0));	
	}
	
	@Test
	public void testaddPeopleFavouriteTaskToListReturnsCalendarList() throws ParseException{
		List <Task> actualTaskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
//		List <People> actualPeopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		List <People> actualPeopleList = os.splitPeopleList("Hernan,30/07/2016 until 05/08/2016,Cooking;");
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.addPeopleFavouriteTaskToList(actualTaskList,actualPeopleList);
		assertEquals("Hernan",actualCalList.get(actualTaskList.get(0).getStartDate()).get("Cooking").get(0));
		System.out.println(actualCalList);
	}
	
	@Test
	public void testaddPeopleTaskToListReturnsCalendarList() throws ParseException{
		List <Task> taskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;");
		List <People> peopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yan,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;");
		TreeMap<Date,Map<String,List<String>>> peopleFavouriteTaskList = os.addPeopleFavouriteTaskToList(taskList,peopleList);
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.addPeopleTaskToList(taskList, peopleList, peopleFavouriteTaskList);
		assertEquals("Hernan",actualCalList.get(taskList.get(0).getStartDate()).get("Cooking").get(0));
		assertNotNull(actualCalList.get(os.addDay(taskList.get(0).getEndDate(), -1)));
		System.out.println(actualCalList);
	}
	
	@Test
	public void testaddPeopleTaskToListReturnsEveryoneCalendarList() throws ParseException{
		List <Task> taskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;Photo taking,1");
		List <People> peopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yann,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Simon,30/07/2016 until 06/08/2016,none;Bilal,30/07/2016 until 05/08/2016,none;Faz,30/07/2016 until 05/08/2016,Photo taking;Antoine,30/07/2016 until 06/08/2016,none;");
		TreeMap<Date,Map<String,List<String>>> peopleFavouriteTaskList = os.addPeopleFavouriteTaskToList(taskList,peopleList);
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.addPeopleTaskToList(taskList, peopleList, peopleFavouriteTaskList);
		assertEquals("Hernan",actualCalList.get(taskList.get(0).getStartDate()).get("Cooking").get(0));
		assertNotNull(actualCalList.get(os.addDay(taskList.get(0).getEndDate(), -1)));
		System.out.println(actualCalList);
	}
	
	@Test
	public void testaddPeopleTaskToListwithTaskIsMisSpellCalendarList() throws ParseException{
		List <Task> taskList = os.splittasklist("30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;Photo Taking,1; Washing Up, 2;");
		List <People> peopleList = os.splitPeopleList("Nico,30/07/2016 until 06/08/2016,House Keeping;Yann,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Deborah,30/07/2016 until 06/08/2016,Washing Up;Simon,30/07/2016 until 06/08/2016,none;Bilal,30/07/2016 until 05/08/2016,none;Faz,30/07/2016 until 05/08/2016,Photo Taking;Antoine,30/07/2016 until 05/08/2016,none;");
		TreeMap<Date,Map<String,List<String>>> peopleFavouriteTaskList = os.addPeopleFavouriteTaskToList(taskList,peopleList);
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.addPeopleTaskToList(taskList, peopleList, peopleFavouriteTaskList);
		assertEquals("Hernan",actualCalList.get(taskList.get(0).getStartDate()).get("Cooking").get(0));
		assertNotNull(actualCalList.get(os.addDay(taskList.get(0).getEndDate(), -1)));
		System.out.println(actualCalList);
	}
	
	@Test
	public void testHandlerReturnsCalendarList() throws ParseException{
		String task = "30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;Photo Taking,1; Washing Up,2;";
		String people = "30/07/2016 until 06/08/2016;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Deborah,Washing Up;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none;";
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.handler(task,people);
		assertNotNull(actualCalList);
	}
	
	@Test
	public void testHandlerwithNoPeopleAvailable() throws ParseException{
		String task = "30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;Photo Taking,1; Washing Up, 2;";
		String people = "";
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.handler(task,people);
		assertNull(actualCalList);
	}
	
	@Test
	public void testHandlerwithNoTasksAvailable() throws ParseException{
		String task = "";
		String people = "Nico,30/07/2016 until 06/08/2016,House Keeping;Yann,30/07/2016 until 06/08/2016,Drink Preparation;Hernan,30/07/2016 until 05/08/2016,Cooking;Deborah,30/07/2016 until 06/08/2016,Washing Up;Simon,30/07/2016 until 06/08/2016,none;Bilal,30/07/2016 until 05/08/2016,none;Faz,30/07/2016 until 05/08/2016,Photo Taking;Antoine,30/07/2016 until 05/08/2016,none;";
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.handler(task,people);
		assertNull(actualCalList);
	}
	
	@Test
	public void testCountPeopleInTasksReturns6AsTheTotalNumberOfPeopleForEachTaskForTheHolidays(){
		String task = "30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;Photo Taking,1; Washing Up, 2;";
		assertEquals(8,os.countPeopleInTask(task));
	}
	
	@Test
	public void testCountPeopleReturns8AsTheTotalNumberOfPeopleAvailable(){
		String people = "30/07/2016 until 05/08/2016,;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Deborah,Washing Up;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none;";
		assertEquals(8,os.countPeople(people));
	}
	
	@Test
	public void testHandlerReturnsList() throws ParseException{
		String task = "30/07/2016 until 06/08/2016;Cooking,2;Drink Preparation,1;House Keeping,2;Photo Taking,1; Washing Up, 2;";
		String people = "30/07/2016 until 05/08/2016;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Deborah,Washing Up;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none;";
		TreeMap<Date,Map<String,List<String>>> actualCalList = os.handler(task,people);
		assertNotNull(actualCalList);
	}


	
}