package com.rizzutih.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class IOHandlerTest {

	@Test
	public void testGenJsonReturnsJsonFromTextPanelStringwithOnePairOfText(){
		IOHandler io = new IOHandler();
		String textpanel = "tasks: 30-07-2016 until 30-07-2016, Cooking, 2;Drink Preparation, 1;House Keeping, 2;Photo Taking, 1;Washing Up, 2.people: 30-07-2016 until 30-07-2016, Nico, House Keeping;Yann, Drink  Preparation;Hernan, Cooking;Andy, none;Simon, none;Bilal, none;Faz, Photo Taking;Antoine, none.";
		String json = io.getJson(textpanel);
		System.out.println(json);
	}
	
	@Test
	public void testGenJsonReturnsJsonFromTextPanelStringwithTwoPairsOfText(){
		IOHandler io = new IOHandler();
		String textpanel = "tasks: 30-07-2016 until 30-07-2016, Cooking, 2;Drink Preparation, 1;House Keeping, 2;Photo Taking, 1;Washing Up, 2."
				+ "people: 30-07-2016 until 30-07-2016, Nico, House Keeping;Yann, Drink  Preparation;Hernan, Cooking;Andy, none;Simon, none;Bilal, none;Faz, Photo Taking;Antoine, none."
				+ "tasks:01/08/2016 until 05/08/2016;Cooking,2;Drink Preparation,1;House Keeping,1;Photo Taking,1; Washing Up,2."
				+ "people:01/08/2016 until 05/08/2016;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none.";
		String json = io.getJson(textpanel);
		System.out.println(json);
	}	
	@Test
	public void testGenJsonReturnsJsonFromTextPanelStringwithThreePairsOfText(){
		IOHandler io = new IOHandler();
		String textpanel = "Tasks:05/08/2016 until 06/08/2016;Cooking,1;Drink Preparation,1;House Keeping,1;Washing Up,1."
				+ "People:05/08/2016 until 06/08/2016;Nico,House Keeping;Yann,Drink Preparation;Simon,none;Antoine,none."
				+ "Tasks: 30-07-2016 until 30-07-2016, Cooking, 2;Drink Preparation, 1;House Keeping, 2;Photo Taking, 1;Washing Up, 2."
				+ "People: 30-07-2016 until 30-07-2016, Nico, House Keeping;Yann, Drink  Preparation;Hernan, Cooking;Andy, none;Simon, none;Bilal, none;Faz, Photo Taking;Antoine, none."
				+ "Tasks:01/08/2016 until 05/08/2016;Cooking,2;Drink Preparation,1;House Keeping,1;Photo Taking,1; Washing Up,2."
				+ "People:01/08/2016 until 05/08/2016;Nico,House Keeping;Yann,Drink Preparation;Hernan,Cooking;Simon,none;Bilal,none;Faz,Photo Taking;Antoine,none.";
				String json = io.getJson(textpanel);
		System.out.println(json);
	}
	
}
